package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.Constructor
import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.node.statement.Block
import org.casclang.casc.parsing.scope.*
import org.casclang.casc.visitor.statement.BlockVisitor

class FunctionVisitor(scope: Scope) : CASCBaseVisitor<Function<*>>() {
    private val scope = Scope(scope)

    override fun visitFunction(ctx: CASCParser.FunctionContext): Function<*> {
        val accessModifier = AccessModifier.getModifier(ctx.findFunctionDeclaration()?.findInnerAccessMods()?.text)
        val static = ctx.findFunctionDeclaration()?.COMP() != null
        val signature = ctx.findFunctionDeclaration()!!.accept(FunctionSignatureVisitor(scope))

        scope.callingScope = CallingScope.getScope(ctx)
        if (scope.callingScope == CallingScope.OBJECT) scope.addLocalVariable(LocalVariable("self", scope.classType))
        addParameterAsLocalVariable(signature)

        scope.concealNonStaticFields()

        val block = getBlock(ctx.findBlock()!!)

        scope.revealNonStaticFields()

        return Function<Function<*>>(signature, block, accessModifier, static)
    }

    override fun visitConstructor(ctx: CASCParser.ConstructorContext): Function<*> {
        val accessModifier = AccessModifier.getModifier(ctx.findConstructorDeclaration()?.findInnerAccessMods()?.text)
        val signature = ctx.findConstructorDeclaration()!!.accept(FunctionSignatureVisitor(scope))

        scope.callingScope = CallingScope.getScope(ctx)
        scope.addLocalVariable(LocalVariable("self", scope.classType))
        addParameterAsLocalVariable(signature)

        val blockCtx = ctx.findBlock()
        val block = if (blockCtx != null) getBlock(blockCtx) else Block(scope)

        return Constructor(signature, block, accessModifier)
    }

    private fun addParameterAsLocalVariable(signature: FunctionSignature) {
        signature.parameters.forEach { scope.addLocalVariable(LocalVariable(it.name, it.type)) }
    }

    private fun getBlock(block: CASCParser.BlockContext): Block =
        block.accept(BlockVisitor(scope))
}