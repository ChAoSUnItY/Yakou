package org.casc.lang.table

/**
 * Refers to function's local variable, its type might be null since its value could be null.
 */
data class Variable(
    val mutable: Boolean,
    val name: String,
    var type: Type?,
    val index: Int,
    val declaredScopeDepth: Int
) {
    override fun equals(other: Any?): Boolean =
        if (other !is Variable) false
        else other.name == name

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
