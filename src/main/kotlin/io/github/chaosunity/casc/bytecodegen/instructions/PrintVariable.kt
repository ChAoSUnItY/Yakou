package io.github.chaosunity.casc.bytecodegen.instructions

import io.github.chaosunity.antlr.CASCLexer
import io.github.chaosunity.casc.parse.symbol.Variable
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

class PrintVariable(private val variable: Variable) : Instruction, Opcodes {
    override fun apply(mv: MethodVisitor) {
        val type = variable.type
        val id = variable.id

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")

        when (type) {
            CASCLexer.NUMBER -> {
                mv.visitVarInsn(ILOAD, id)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)
            }
            CASCLexer.STRING -> {
                mv.visitVarInsn(ALOAD, id)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
            }
        }
    }
}