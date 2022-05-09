import org.casc.lang.compilation.Compilation
import org.casc.lang.compilation.LocalPreference
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class ExecutionTest {
    @TestFactory
    fun testExecutionOutput(): List<DynamicTest> {
        Assumptions.assumeTrue(System.getProperty("runExecutionTest").toBoolean())

        val tests = mutableListOf<DynamicTest>()
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val fileMap = File(Compilation::class.java.classLoader.getResource("execution")!!.file)
            .listFiles()
            ?.groupBy { it.extension }

        System.setOut(printStream)

        val localPref = LocalPreference(compileAndRun = true)

        fileMap?.get("casc")?.forEach {
            localPref.sourceFile = it

            val compilation = Compilation(localPref)
            compilation.compile()

            System.out.flush()

            val output = outputStream.toString().trimEnd()

            val outFile = fileMap["out"]?.find { outFile -> it.nameWithoutExtension == outFile.nameWithoutExtension }!!

            tests += DynamicTest.dynamicTest(it.name) {
                Assertions.assertEquals(outFile.readText(Charsets.UTF_8).trimEnd(), output)
            }

            outputStream.reset()
        }

        return tests
    }
}