package org.casc.lang.table

import org.casc.lang.ast.Accessor

data class Field(
    val ownerReference: Reference?,
    val companion: Boolean,
    val mutable: Boolean,
    val accessor: Accessor,
    val name: String,
    val type: Type
)
