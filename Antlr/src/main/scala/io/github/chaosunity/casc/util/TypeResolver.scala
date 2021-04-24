package io.github.chaosunity.casc.util

import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, ClassType, Type}
import org.apache.commons.lang3.StringUtils

object TypeResolver {
    def getFromTypeName(typeContext: CASCParser.TypeContext): Type = {
        if (typeContext == null) return BuiltInType.VOID

        val typeName = typeContext.getText
        val builtInType = getBuiltInType(typeName)

        if (builtInType.isDefined) return builtInType.get

        new ClassType(typeName)
    }

    def getFromValue(value: String): Type = {
        if (StringUtils.isEmpty(value)) return BuiltInType.VOID
        if (StringUtils.isNumeric(value)) return BuiltInType.INT

        BuiltInType.STRING
    }

    private def getBuiltInType(typeName: String): Option[Type] =
        BuiltInType.enumSet.find(_.name.equals(typeName))
}
