package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

class PrintStatement(override val expression: Expression<*>) : Statement<PrintStatement>, Printable