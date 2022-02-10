package org.casc.lang.checker

import org.casc.lang.ast.*
import org.casc.lang.ast.Field
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.table.*
import java.io.File as JFile

class Checker(private val preference: AbstractPreference) {
    companion object {
        private val ignoreUnusedExpressions =
            listOf(AssignmentExpression::class.java, FunctionCallExpression::class.java, Constructor::class.java)
    }

    private val globalScope: Scope = Scope(preference)
    private var reports: MutableSet<Report> = mutableSetOf()

    fun check(file: File): Pair<List<Report>, File> {
        reports.clear()
        val checkedFiles = checkFile(file)

        return reports.toList() to checkedFiles
    }

    private fun checkIdentifierIsKeyword(identifierToken: Token?) {
        if (Token.keywords.contains(identifierToken?.literal)) {
            reports += Error(
                identifierToken?.pos,
                "Cannot use ${identifierToken?.literal} as identifier since it's a keyword"
            )
        }
    }

    private fun checkFile(file: File): File {
        if (file.clazz.packageReference != null) {
            val packagePath = file.clazz.packageReference!!.path.replace('.', '/')

            if (!JFile(file.path).parentFile.toPath().endsWith(packagePath)) {
                reports += Error(
                    file.clazz.packageReference!!.pos,
                    "Package path mismatch",
                    "Try rename parent folders' name or rename package name"
                )
            }
        }

        file.clazz = checkClass(file.clazz)

        return file
    }

    private fun checkClass(clazz: Class): Class {
        val classScope = Scope(
            globalScope,
            clazz.packageReference?.path?.let { "$it/${clazz.name!!.literal}" } ?: clazz.name!!.literal)

        checkIdentifierIsKeyword(clazz.name)

        clazz.usages.mapNotNull {
            it?.tokens?.forEach { token ->
                checkIdentifierIsKeyword(token)
            }

            val type = TypeUtil.asType(it, preference)

            if (type == null) {
                reports.reportUnknownTypeSymbol(it!!)

                null
            } else it
        }.forEach(classScope.usages::add)

        clazz.fields.forEach {
            checkField(it, classScope)
        }

        clazz.constructors = clazz.constructors.map {
            checkConstructor(it, classScope)
        }
        clazz.functions = clazz.functions.map {
            checkFunction(it, classScope)
        }
        clazz.functions.forEachIndexed { _, it ->
            checkFunctionBody(it, Scope(classScope, isCompScope = it.compKeyword != null))
        }

        return clazz
    }

    private fun checkField(field: Field, scope: Scope): Field {
        checkIdentifierIsKeyword(field.name)

        val fieldType = scope.findType(field.typeReference)

        if (fieldType == null) {
            reports.reportUnknownTypeSymbol(field.typeReference!!)
        } else field.type = fieldType

        scope.registerField(field)

        return field
    }

    private fun checkConstructor(constructor: Constructor, scope: Scope): Constructor {
        // Validate types first then register it to scope
        // Check if parameter has duplicate names
        val duplicateParameters = constructor.parameters
            .groupingBy {
                checkIdentifierIsKeyword(it.name)

                it.name
            }
            .eachCount()
            .filter { it.value > 1 }
        var validationPass = true

        if (duplicateParameters.isNotEmpty()) {
            duplicateParameters.forEach { (parameter, _) ->
                reports += Error(
                    parameter!!.pos,
                    "Parameter ${parameter.literal} is already declared in constructor new(${
                        constructor.parameters.mapNotNull { it.typeReference?.path }.joinToString()
                    })"
                )
            }

            validationPass = false
        } else {
            constructor.parameterTypes = constructor.parameters.map {
                val type = checkType(it.typeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(it.typeReference!!)

                    return@map null
                }

                type
            }
        }

        if (validationPass) {
            constructor.ownerType = TypeUtil.asType(constructor.ownerReference, preference)
            constructor.parentType = TypeUtil.asType(constructor.parentReference, preference)
            scope.registerSignature(constructor)
        }

        return constructor
    }

    // TODO: Track if-else and match branches so compiler can know whether all paths have return value or not
    private fun checkFunction(function: Function, scope: Scope): Function {
        checkIdentifierIsKeyword(function.name)

        // Validate types first then register it to scope
        // Check if parameter has duplicate names
        val duplicateParameters = function.parameters
            .groupingBy {
                checkIdentifierIsKeyword(it.name)

                it.name
            }
            .eachCount()
            .filter { it.value > 1 }
        var validationPass = true

        if (duplicateParameters.isNotEmpty()) {
            duplicateParameters.forEach { (parameter, _) ->
                reports += Error(
                    parameter!!.pos,
                    "Parameter ${parameter.literal} is already declared in function ${function.name!!.literal}"
                )
            }

            validationPass = false
        } else {
            function.parameterTypes = function.parameters.map {
                val type = checkType(it.typeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(it.typeReference!!)

                    return@map null
                }

                type
            }
        }

        function.returnType =
            if (function.returnTypeReference == null) PrimitiveType.Unit
            else {
                val type = checkType(function.returnTypeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(function.returnTypeReference)
                    validationPass = false

                    null
                } else type
            }

        if (validationPass) scope.registerSignature(function)

        return function
    }

    private fun checkConstructorBody(constructor: Constructor, scope: Scope) {
        constructor.parameters.forEachIndexed { i, parameter ->
            scope.registerVariable(false, parameter.name!!.literal, constructor.parameterTypes?.get(i))
        }

        constructor.statements.forEach {
            checkStatement(it, scope, PrimitiveType.Unit)
        }
    }

    private fun checkFunctionBody(function: Function, scope: Scope) {
        function.parameters.forEachIndexed { i, parameter ->
            scope.registerVariable(false, parameter.name!!.literal, function.parameterTypes?.get(i))
        }

        function.statements.forEach {
            checkStatement(it, scope, function.returnType)
        }
    }

    private fun checkType(reference: Reference?, scope: Scope): Type? =
        scope.findType(reference)

    private fun checkStatement(
        statement: Statement?,
        scope: Scope,
        returnType: Type? = null,
        useSameScope: Boolean = false
    ) {
        when (statement) {
            is VariableDeclaration -> {
                checkIdentifierIsKeyword(statement.name)

                val expressionType = checkExpression(statement.expression, scope)

                if (!scope.registerVariable(statement.mutKeyword != null, statement.name!!.literal, expressionType)) {
                    reports += Error(
                        statement.pos!!,
                        "Variable ${statement.name.literal} is already declared in same context"
                    )
                } else {
                    if (expressionType == PrimitiveType.Unit) {
                        reports += Error(
                            statement.expression!!.pos,
                            "Could not store void type into variable"
                        )
                    }

                    statement.index = scope.findVariableIndex(statement.name.literal)
                }
            }
            is IfStatement -> {
                val conditionType = checkExpression(statement.condition, scope)

                if (TypeUtil.canCast(conditionType, PrimitiveType.Bool)) {
                    statement.condition?.castTo = PrimitiveType.Bool
                } else {
                    reports.reportTypeMismatch(
                        statement.condition?.pos,
                        PrimitiveType.Bool,
                        conditionType
                    )
                }

                checkStatement(statement.trueStatement!!, scope)

                if (statement.elseStatement != null) {
                    checkStatement(statement.elseStatement, scope)
                }
            }
            is JForStatement -> {
                val innerScope = Scope(scope)

                checkStatement(statement.initStatement, innerScope, useSameScope = true)

                if (statement.condition != null) {
                    val conditionType = checkExpression(statement.condition, innerScope)

                    if (TypeUtil.canCast(conditionType, PrimitiveType.Bool)) {
                        statement.condition.castTo = PrimitiveType.Bool
                    } else {
                        reports.reportTypeMismatch(
                            statement.condition.pos,
                            PrimitiveType.Bool,
                            conditionType
                        )
                    }
                }

                checkExpression(statement.postExpression, innerScope)

                checkStatement(statement.statement, innerScope, useSameScope = true)
            }
            is BlockStatement -> {
                statement.statements.forEach {
                    checkStatement(it!!, if (useSameScope) scope else Scope(scope))
                }
            }
            is ExpressionStatement -> {
                if (statement.expression != null && !ignoreUnusedExpressions.contains(statement.expression::class.java)) {
                    if (statement.expression is UnaryExpression &&
                        (statement.expression.operator?.type == TokenType.DoublePlus || statement.expression.operator?.type == TokenType.DoubleMinus)
                    ) {
                        checkExpression(statement.expression, scope)
                    } else reports += Error(
                        statement.pos,
                        "Unused expression",
                        "Consider remove this line"
                    )
                } else {
                    checkExpression(statement.expression, scope)
                }
            }
            is ReturnStatement -> {
                val expressionType = checkExpression(statement.expression, scope)

                if (!TypeUtil.canCast(expressionType, returnType)) {
                    reports.reportTypeMismatch(statement.pos!!, returnType, expressionType)
                } else statement.returnType = returnType
            }
            else -> {}
        }
    }

    private fun checkExpression(expression: Expression?, scope: Scope): Type? {
        return when (expression) {
            is IntegerLiteral -> {
                expression.type = when {
                    expression.isI8() -> PrimitiveType.I8
                    expression.isI16() -> PrimitiveType.I16
                    expression.isI32() -> PrimitiveType.I32
                    expression.isI64() -> PrimitiveType.I64
                    else -> null // Should not be null
                }

                expression.type
            }
            is FloatLiteral -> {
                expression.type = when {
                    expression.literal?.literal?.endsWith('D') == true -> PrimitiveType.F64
                    else -> PrimitiveType.F32
                }

                expression.type
            }
            is CharLiteral -> {
                expression.type = PrimitiveType.Char

                expression.type
            }
            is StrLiteral -> {
                expression.type = PrimitiveType.Str

                expression.type
            }
            is BoolLiteral -> {
                expression.type = PrimitiveType.Bool

                expression.type
            }
            is NullLiteral -> {
                expression.type = PrimitiveType.Null

                expression.type
            }
            is AssignmentExpression -> {
                val leftType = checkExpression(expression.leftExpression, scope)
                val rightType = checkExpression(expression.rightExpression, scope)

                if (expression.leftExpression is IdentifierCallExpression) {
                    val name = expression.leftExpression.name!!.literal
                    val variable = scope.findVariable(name)

                    if (variable == null) {
                        // Lookup local field
                        val field = scope.findField(expression.leftExpression.ownerReference?.path, name)

                        if (field == null) {
                            reports += Error(
                                expression.leftExpression.pos,
                                "Unknown identifier $name"
                            )
                        } else {
                            if (!field.mutable) {
                                reports += Error(
                                    expression.leftExpression.pos,
                                    "Field $name is not mutable",
                                    "Declare field $name with `mut` keyword"
                                )
                            }

                            if (!field.companion && field.ownerReference?.path != scope.classPath) {
                                reports += Error(
                                    expression.leftExpression.pos,
                                    "Cannot access non-companion field $name from other context"
                                )
                            }

                            expression.leftExpression.isAssignedBy = true
                        }
                    } else {
                        if (!variable.mutable) {
                            reports += Error(
                                expression.leftExpression.pos,
                                "Variable $name is not mutable",
                                "Declare variable $name with `mut` keyword"
                            )
                        }

                        if (rightType == PrimitiveType.Unit) {
                            reports += Error(
                                expression.rightExpression?.pos,
                                "Could not store void type into variable"
                            )
                        }

                        if (rightType == PrimitiveType.Null) {
                            variable.type = PrimitiveType.Null
                        }

                        expression.leftExpression.isAssignedBy = true
                    }
                } else if (expression.leftExpression is IndexExpression) {
                    expression.leftExpression.isAssignedBy = expression.rightExpression !is IndexExpression
                } else {
                    reports += Error(
                        expression.leftExpression?.pos,
                        "Cannot assign value to non-variable reference",
                        "Change this into variable"
                    )
                }

                if (!TypeUtil.canCast(rightType, expression.leftExpression?.type)) {
                    reports.reportTypeMismatch(
                        expression.rightExpression?.pos,
                        expression.leftExpression?.type,
                        rightType
                    )
                } else {
                    expression.rightExpression?.castTo = leftType
                    expression.type = leftType
                }

                expression.type
            }
            is IdentifierCallExpression -> {
                val ownerReference = expression.ownerReference

                checkIdentifierIsKeyword(expression.name)

                if (ownerReference != null) {
                    // Appointed class field
                    val field = scope.findField(ownerReference.path, expression.name!!.literal)

                    if (field == null) {
                        reports += Error(
                            expression.pos,
                            "Field ${expression.name.literal} does not exist in class ${ownerReference.path}"
                        )
                    } else {
                        if (!field.companion) {
                            reports += Error(
                                expression.pos,
                                "Field ${expression.name.literal} exists in non-companion context but is called from other context"
                            )
                        }
                        // TODO: Check accessor

                        expression.type = field.type
                    }

                    field?.type
                } else if (expression.previousExpression != null) {
                    // Chain calling
                    val previousType = checkExpression(expression.previousExpression, scope)
                    val field = scope.findField(previousType?.typeName, expression.name!!.literal)

                    if (field == null) {
                        reports += Error(
                            expression.pos,
                            "Field ${expression.name.literal} does not exist in class ${previousType?.typeName}"
                        )
                    } else {
                        if (!field.companion && scope.isCompScope) {
                            reports += Error(
                                expression.name.pos,
                                "Cannot access non-companion field ${expression.name.literal} from companion context"
                            )
                        }

                        expression.type = field.type
                        expression.isCompField = field.companion
                        expression.ownerReference = field.ownerReference
                    }

                    expression.type
                } else {
                    // Check identifier is class name or not
                    val classType = scope.findType(expression.name?.literal)

                    if (classType != null) {
                        // Class companion member call

                        expression.type = classType
                        expression.isClassName = true

                        classType
                    } else {
                        // Local variable / current class field
                        val variable = scope.findVariable(expression.name!!.literal)

                        if (variable == null) {
                            // Lookup for current class' field
                            val field = scope.findField(null, expression.name.literal)

                            if (field != null) {
                                if (!field.companion && scope.isCompScope) {
                                    reports += Error(
                                        expression.name.pos,
                                        "Cannot access non-companion field ${expression.name.literal} from companion context"
                                    )
                                }

                                expression.type = field.type
                                expression.isCompField = field.companion
                                expression.ownerReference = field.ownerReference
                            } else {
                                reports += Error(
                                    expression.name.pos,
                                    "Unknown identifier ${expression.name.literal}"
                                )
                            }
                        } else {
                            expression.type = variable.type
                            expression.index = scope.findVariableIndex(expression.name.literal)
                        }

                        expression.type
                    }
                }
            }
            is FunctionCallExpression -> {
                // TODO: Support auto promotion parameter checking
                val argumentTypes = expression.arguments.map {
                    checkExpression(it, scope)
                }

                val previousType = checkExpression(expression.previousExpression, scope)

                // Check function call expression's context, e.g companion context
                val functionSignature =
                    scope.findFunction(
                        expression.ownerReference?.path ?: previousType?.typeName,
                        expression.name!!.literal,
                        argumentTypes
                    )

                if (functionSignature == null) {
                    // No function matched
                    reports += Error(
                        expression.pos!!,
                        "Function ${expression.name.literal} does not exist in current context"
                    )
                } else {
                    if (functionSignature.ownerReference == expression.ownerReference) {
                        // Function's owner class is same as current class
                        if (expression.previousExpression == null) {
                            if (expression.inCompanionContext && !functionSignature.companion) {
                                reports += Error(
                                    expression.pos!!,
                                    "Function ${expression.name.literal} exists in non-companion context but it's called from companion context",
                                    "Consider move its declaration into companion context"
                                )
                            }
                        }
                        // TODO: Check accessor for chain calling
                    } else {
                        // Function's owner class is outside this context
                        if (expression.previousExpression == null) {
                            if (!functionSignature.companion) {
                                reports += Error(
                                    expression.pos!!,
                                    "Function ${expression.name.literal} exists in non-companion context but is called from other context",
                                    "Consider move its declaration into companion context"
                                )
                            }
                            // TODO: Check accessor
                        }
                        // TODO: Check accessor for chain calling
                    }

                    expression.type = functionSignature.returnType
                    expression.referenceFunctionSignature = functionSignature
                }

                expression.type
            }
            is ConstructorCallExpression -> {
                val argumentTypes = expression.arguments.map {
                    checkExpression(it, scope)
                }

                // Check owner type has matched signature
                val signature = scope.findFunction(
                    expression.constructorOwnerReference?.path,
                    "<init>",
                    argumentTypes
                )

                if (signature == null) {
                    // No match
                    reports += Error(
                        expression.pos,
                        "Constructor ${expression.constructorOwnerReference?.asCascStyle()}#new(${
                            argumentTypes.map { it?.typeName }.joinToString()
                        }) does not exist"
                    )
                } else {
                    expression.type = signature.returnType
                    expression.referenceFunctionSignature = signature
                }

                expression.type
            }
            is IndexExpression -> {
                val previousExpressionType = checkExpression(expression.previousExpression, scope)
                val indexExpressionType = checkExpression(expression.indexExpression, scope)

                if (previousExpressionType !is ArrayType) {
                    reports += Error(
                        expression.previousExpression?.pos,
                        "Could not index non-array type"
                    )
                } else expression.type = previousExpressionType.baseType

                if (!TypeUtil.canCast(indexExpressionType, PrimitiveType.I32)) {
                    reports.reportTypeMismatch(
                        expression.indexExpression?.pos,
                        PrimitiveType.I32,
                        indexExpressionType
                    )
                } else expression.indexExpression?.castTo = PrimitiveType.I32

                expression.type
            }
            is UnaryExpression -> {
                when (val type = checkExpression(expression.expression, scope)) {
                    is PrimitiveType -> when (expression.operator?.type) {
                        TokenType.Plus, TokenType.Minus, TokenType.DoublePlus, TokenType.DoubleMinus -> {
                            if (!type.isNumericType()) {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-numeric type",
                                    "Remove this operator"
                                )
                            } else expression.type = type
                        }
                        TokenType.Tilde -> {
                            if ((PrimitiveType.promotionTable[type] ?: 2) > 2) {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-integer type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.Bang -> {
                            if (type != PrimitiveType.Bool) {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-bool type",
                                    "Remove this operator"
                                )
                            } else expression.type = type
                        }
                        else -> {} // Should not be here
                    }
                    else -> {
                        reports += Error(
                            expression.operator?.pos,
                            "Could not apply unary operator on object type",
                            "Remove this operator"
                        )
                    }
                }

                expression.type
            }
            is BinaryExpression -> {
                val operator = expression.operator?.type
                val leftType = checkExpression(expression.left, scope)
                val rightType = checkExpression(expression.right, scope)

                if (operator != TokenType.EqualEqual && operator != TokenType.BangEqual && (leftType !is PrimitiveType || rightType !is PrimitiveType)) {
                    reports += Error(
                        expression.operator?.pos,
                        "Could not apply binary operator on object type",
                        "Remove this operator"
                    )
                } else {
                    when (operator) {
                        TokenType.Plus, TokenType.Minus, TokenType.Star, TokenType.Slash, TokenType.Percentage -> {
                            if (leftType is PrimitiveType && rightType is PrimitiveType && leftType.isNumericType() && rightType.isNumericType()) {
                                expression.promote()
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-numeric type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.Greater, TokenType.GreaterEqual, TokenType.Lesser, TokenType.LesserEqual -> {
                            if (leftType is PrimitiveType && rightType is PrimitiveType && leftType.isNumericType() && rightType.isNumericType()) {
                                expression.promote()
                                expression.type = PrimitiveType.Bool
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-numeric type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.DoublePipe, TokenType.DoubleAmpersand -> {
                            if (leftType == PrimitiveType.Bool && rightType == PrimitiveType.Bool) {
                                expression.type = PrimitiveType.Bool
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-bool type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.Pipe, TokenType.Hat, TokenType.Ampersand,
                        TokenType.DoubleGreater, TokenType.TripleGreater, TokenType.DoubleLesser -> {
                            if ((PrimitiveType.promotionTable[leftType] ?: 2) <= 2 &&
                                (PrimitiveType.promotionTable[rightType] ?: 2) <= 2
                            ) {
                                expression.promote()
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-integer type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.EqualEqual, TokenType.BangEqual -> {
                            if (leftType == PrimitiveType.Null) {
                                if (PrimitiveType.isJvmPrimitive(rightType)) {
                                    reports += Error(
                                        expression.right?.pos,
                                        "Primitive types are always not null",
                                        "Only primitive wrapper classes could be determined to be null"
                                    )
                                }
                            } else if (rightType == PrimitiveType.Null) {
                                if (PrimitiveType.isJvmPrimitive(leftType)) {
                                    reports += Error(
                                        expression.left?.pos,
                                        "Primitive types are always not null",
                                        "Only primitive wrapper classes could be determined to be null"
                                    )
                                }
                            }

                            expression.type = PrimitiveType.Bool
                        }
                        else -> {}
                    }
                }

                expression.type
            }
            is ParenthesizedExpression -> {
                expression.type = checkExpression(expression.expression, scope)
                expression.type
            }
            is ArrayInitialization -> {
                if (expression.inferTypeReference != null) {
                    when (val inferType = checkType(expression.inferTypeReference, scope)) {
                        null -> reports.reportUnknownTypeSymbol(expression.inferTypeReference)
                        !is ArrayType -> {
                            reports += Error(
                                expression.inferTypeReference.pos,
                                "Inferred type must be array type",
                                "Consider add [] after type name"
                            )
                        }
                        else -> checkArrayType(
                            expression,
                            scope,
                            inferType.baseType,
                            expression.inferTypeReference.pos
                        )
                    }
                } else {
                    if (expression.elements.isEmpty()) {
                        reports += Error(
                            expression.pos,
                            "Could not infer type from an empty array",
                            "Consider adding at least one element or declare its type"
                        )
                    } else {
                        checkArrayType(expression, scope)
                    }
                }

                expression.type
            }
            is ArrayDeclaration -> {
                expression.type = checkType(expression.baseTypeReference, scope)

                expression.dimensionExpressions.forEach {
                    // Dimension expression's type must be able to cast into i32
                    val type = checkExpression(it, scope)

                    if (!TypeUtil.canCast(type, PrimitiveType.I32)) {
                        reports.reportTypeMismatch(
                            it?.pos,
                            PrimitiveType.I32,
                            type
                        )
                    } else it?.castTo = PrimitiveType.I32
                }

                expression.type
            }
            null -> null
        }
    }

    private fun checkArrayType(
        expression: ArrayInitialization,
        scope: Scope,
        forcedFinalType: Type? = null,
        inferTypePos: Position? = null
    ) {
        // TODO: Support Object's promotion?
        val forceFinalType = forcedFinalType != null
        val expressionTypes = mutableListOf<Type?>()

        expression.elements.forEach {
            expressionTypes += checkExpression(it, scope)
        }

        val firstInferredType = if (forceFinalType) forcedFinalType else expressionTypes.first()
        var latestInferredType = firstInferredType

        if (firstInferredType is ArrayType && expressionTypes.any { it !is ArrayType }) {
            // Must be non-castable type relationship since any other types (including array itself)
            // cannot automatically cast into array.
            expressionTypes.forEachIndexed { i, type ->
                reports.reportTypeMismatch(
                    expression.elements[i]?.pos,
                    firstInferredType,
                    type
                )
            }
        } else {
            when (firstInferredType) {
                is ArrayType -> {
                    // Checks all types' dimension is same as first element's dimension
                    val firstTypeDimension = firstInferredType.getDimension()

                    expressionTypes.forEachIndexed { i, type ->
                        // Check their dimensions first
                        val dimension = (type as ArrayType).getDimension() // Already checked

                        if (firstTypeDimension != dimension) {
                            reports += Error(
                                expression.elements[i]?.pos,
                                "Dimension mismatch, requires $firstTypeDimension-dimension array but got $dimension-array"
                            )
                        } else {
                            // Then tries to infer their final type
                            // TODO: Support Object's promotion here
                            val latestFoundationType =
                                (latestInferredType as ArrayType).getFoundationType()
                            val currentFoundationType = type.getFoundationType()

                            if (latestFoundationType !is PrimitiveType && currentFoundationType !is PrimitiveType) {
                                // TODO: Support Object's Promotion here
                            } else {
                                if (!TypeUtil.canCast(latestFoundationType, currentFoundationType)) {
                                    if (!forceFinalType && latestFoundationType is PrimitiveType && currentFoundationType is PrimitiveType) {
                                        if (!latestFoundationType.isNumericType() || !currentFoundationType.isNumericType()) {
                                            reports.reportTypeMismatch(
                                                expression.elements[i]?.pos,
                                                latestFoundationType,
                                                expressionTypes[i]
                                            )
                                        }
                                        // If both are numeric types, it would be fine since current type is able to promote into latest inferred type
                                    } else {
                                        reports.reportTypeMismatch(
                                            expression.elements[i]?.pos,
                                            latestFoundationType,
                                            expressionTypes[i]
                                        )
                                    }
                                    // Boxed type cannot be promoted, and it's checked in canCast
                                } else {
                                    if (i == expressionTypes.lastIndex &&
                                        currentFoundationType is ClassType
                                    ) {
                                        // Covert boxed type into primitive type
                                        val currentPrimitiveType = PrimitiveType.fromClass(currentFoundationType.type())

                                        if (currentPrimitiveType != null) {
                                            if (!forceFinalType) {
                                                type.setFoundationType(currentPrimitiveType)
                                                latestInferredType = type
                                            } else {
                                                type.setFoundationType(forcedFinalType!!)
                                            }
                                        }
                                    } else if (!forceFinalType) latestInferredType = type
                                }
                            }
                        }
                    }
                }
                is PrimitiveType -> {
                    expressionTypes.forEachIndexed { i, type ->
                        if (type is ArrayType) {
                            reports += Error(
                                expression.elements[i]?.pos,
                                "Dimension mismatch, requires 1-dimension array but got ${type.getDimension()}-array"
                            )
                        } else if (type is PrimitiveType && !TypeUtil.canCast(latestInferredType, type)) {
                            if (!type.isNumericType() || !type.isNumericType()) {
                                reports.reportTypeMismatch(
                                    expression.elements[i]?.pos,
                                    expressionTypes[i],
                                    latestInferredType
                                )
                            }
                            // If both are numeric types, it would be fine since current type is able to promote into latest inferred type
                        } else latestInferredType = type
                    }
                }
                else -> {
                    // TODO: Support Object's Promotion
                }
            }
        }

        if (latestInferredType != null)
            expression.type = ArrayType(latestInferredType!!)

        val latestFoundationType =
            if (latestInferredType is ArrayType) (latestInferredType as ArrayType).getFoundationType()
            else latestInferredType

        if (latestFoundationType != null) {
            expression.elements.forEach {
                traverseArrayTree(it, { expr ->
                    expr.castTo = latestFoundationType
                }, { expr ->
                    val currentNodeType = (expr.type as ArrayType)

                    currentNodeType.baseType = ArrayType.fromDimension(
                        latestFoundationType,
                        currentNodeType.getDimension() - 1
                    )
                    currentNodeType.setFoundationType(latestFoundationType)
                })
            }
        }
    }

    // Used to traverse through array structure and do specific job, e.g. assigning final cast type
    private fun traverseArrayTree(
        expression: Expression?,
        lastNodeAction: (Expression) -> Unit,
        nodeAction: (Expression) -> Unit
    ) {
        if (expression != null) {
            if (expression.type is ArrayType) {
                val expressions = expression.getExpressions()

                if (expressions != null && expressions.isNotEmpty()) {
                    nodeAction(expression)

                    expressions.forEach {
                        traverseArrayTree(it, lastNodeAction, nodeAction)
                    }
                } else lastNodeAction(expression)
            } else lastNodeAction(expression)
        }
    }
}