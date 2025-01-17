package org.yakou.lang.bind

enum class PrimitiveType(private val typeLiteral: String) {
    Bool("bool"),
    Char("char"),
    I8("i8"),
    I16("i16"),
    I32("i32"),
    I64("i64"),
    F32("f32"),
    F64("f64");

    companion object {
        val numberTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32, I64, F32, F64)
        val integerTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32, I64)
        val floatTypes: Array<PrimitiveType> = arrayOf(F32, F64)

        fun isNumberType(typeLiteral: String): Boolean =
            numberTypes.any { it.typeLiteral == typeLiteral }

        fun isIntegerType(typeLiteral: String): Boolean =
            integerTypes.any { it.typeLiteral == typeLiteral }

        fun isFloatType(typeLiteral: String): Boolean =
            floatTypes.any { it.typeLiteral == typeLiteral }
    }

}