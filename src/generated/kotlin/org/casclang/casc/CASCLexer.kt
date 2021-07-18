// Generated from java-escape by ANTLR 4.7.1
package org.casclang.casc

import com.strumenta.kotlinmultiplatform.asCharArray
import org.antlr.v4.kotlinruntime.CharStream
import org.antlr.v4.kotlinruntime.Lexer
import org.antlr.v4.kotlinruntime.ThreadLocal
import org.antlr.v4.kotlinruntime.Vocabulary
import org.antlr.v4.kotlinruntime.VocabularyImpl
import org.antlr.v4.kotlinruntime.atn.ATN
import org.antlr.v4.kotlinruntime.atn.ATNDeserializer
import org.antlr.v4.kotlinruntime.atn.LexerATNSimulator
import org.antlr.v4.kotlinruntime.atn.PredictionContextCache
import org.antlr.v4.kotlinruntime.dfa.DFA

class CASCLexer(val input: CharStream) : Lexer(input) {
	// TODO Verify the runtime version is correct

    override val ruleNames: Array<String>?
        get() = Rules.values().map { it.name }.toTypedArray()

    override val grammarFileName: String
        get() = "CASC.g4"

    override val atn: ATN
		get() = CASCLexer.Companion.ATN

    override val vocabulary: Vocabulary
        get() = CASCLexer.Companion.VOCABULARY

	@ThreadLocal
	companion object {
		val decisionToDFA : Array<DFA>
		val sharedContextCache = PredictionContextCache()

		private val LITERAL_NAMES: List<String?> = listOf(null, "'::'", "'{'", 
		                                                  "','", "'}'", "'('", 
		                                                  "')'", "'param'", "'[]'", 
		                                                  "';'", "'->'", "'<-'", 
		                                                  "'|>'", "'<|'", "'.'", 
		                                                  "'['", "']'", "'mod'", 
		                                                  "'use'", "'class'", 
		                                                  "'fn'", "'ctor'", "'self'", 
		                                                  "'comp'", "'if'", "'else'", 
		                                                  "'as'", "'return'", 
		                                                  "'for'", "'down'", "'to'", 
		                                                  "'until'", "'pub'", 
		                                                  "'prot'", "'intl'", 
		                                                  "'priv'", "'mut'", "'print'", 
		                                                  "'println'", "'+'", 
		                                                  "'-'", "'*'", "'/'", 
		                                                  "'='", "':='", "'?'", 
		                                                  "'!'", "':'", "'>'", 
		                                                  "'<'", "'>='", "'<='", 
		                                                  "'=='", "'!='", null, 
		                                                  null, null, null, "'null'")
		private val SYMBOLIC_NAMES: List<String?> = listOf(null, null, null, null, 
		                                                   null, null, null, null, 
		                                                   null, null, null, null, 
		                                                   null, null, null, null, 
		                                                   null, "MOD", "USE", 
		                                                   "CLASS", "FN", "CTOR", 
		                                                   "SELF", "COMP", "IF", 
		                                                   "ELSE", "AS", "RETURN", 
		                                                   "FOR", "DOWN", "TO", 
		                                                   "UNTIL", "PUB", "PROT", 
		                                                   "INTL", "PRIV", "MUT", 
		                                                   "PRINT", "PRINTLN", 
		                                                   "PLUS", "MINUS", "STAR", 
		                                                   "SLASH", "EQUALS", 
		                                                   "ASSIGN_EQ", "QUETION_MK", 
		                                                   "EXCLAMATION_MK", "COLON", 
		                                                   "GREATER", "LESS", 
		                                                   "GREATER_EQ", "LESS_EQ", 
		                                                   "EQ", "NOT_EQ", "TYPES", 
		                                                   "NUMBER", "STRING", 
		                                                   "BOOL", "NULL", "ID", 
		                                                   "WS")

		val VOCABULARY = VocabularyImpl(LITERAL_NAMES.toTypedArray(), SYMBOLIC_NAMES.toTypedArray())

        val tokenNames: Array<String?> = Array<String?>(SYMBOLIC_NAMES.size) {
            var el = VOCABULARY.getLiteralName(it)
            if (el == null) {
                el = VOCABULARY.getSymbolicName(it)
            }

            if (el == null) {
                el = "<INVALID>"
            }
            el
        }

		private const val serializedATN : String = "\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\u0002\u003e\u0190\u0008\u0001\u0004\u0002\u0009\u0002\u0004\u0003\u0009\u0003\u0004\u0004\u0009\u0004\u0004\u0005\u0009\u0005\u0004\u0006\u0009\u0006\u0004\u0007\u0009\u0007\u0004\u0008\u0009\u0008\u0004\u0009\u0009\u0009\u0004\u000a\u0009\u000a\u0004\u000b\u0009\u000b\u0004\u000c\u0009\u000c\u0004\u000d\u0009\u000d\u0004\u000e\u0009\u000e\u0004\u000f\u0009\u000f\u0004\u0010\u0009\u0010\u0004\u0011\u0009\u0011\u0004\u0012\u0009\u0012\u0004\u0013\u0009\u0013\u0004\u0014\u0009\u0014\u0004\u0015\u0009\u0015\u0004\u0016\u0009\u0016\u0004\u0017\u0009\u0017\u0004\u0018\u0009\u0018\u0004\u0019\u0009\u0019\u0004\u001a\u0009\u001a\u0004\u001b\u0009\u001b\u0004\u001c\u0009\u001c\u0004\u001d\u0009\u001d\u0004\u001e\u0009\u001e\u0004\u001f\u0009\u001f\u0004\u0020\u0009\u0020\u0004\u0021\u0009\u0021\u0004\u0022\u0009\u0022\u0004\u0023\u0009\u0023\u0004\u0024\u0009\u0024\u0004\u0025\u0009\u0025\u0004\u0026\u0009\u0026\u0004\u0027\u0009\u0027\u0004\u0028\u0009\u0028\u0004\u0029\u0009\u0029\u0004\u002a\u0009\u002a\u0004\u002b\u0009\u002b\u0004\u002c\u0009\u002c\u0004\u002d\u0009\u002d\u0004\u002e\u0009\u002e\u0004\u002f\u0009\u002f\u0004\u0030\u0009\u0030\u0004\u0031\u0009\u0031\u0004\u0032\u0009\u0032\u0004\u0033\u0009\u0033\u0004\u0034\u0009\u0034\u0004\u0035\u0009\u0035\u0004\u0036\u0009\u0036\u0004\u0037\u0009\u0037\u0004\u0038\u0009\u0038\u0004\u0039\u0009\u0039\u0004\u003a\u0009\u003a\u0004\u003b\u0009\u003b\u0004\u003c\u0009\u003c\u0004\u003d\u0009\u003d\u0004\u003e\u0009\u003e\u0004\u003f\u0009\u003f\u0004\u0040\u0009\u0040\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0009\u0003\u0009\u0003\u0009\u0003\u000a\u0003\u000a\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000c\u0003\u000c\u0003\u000c\u0003\u000d\u0003\u000d\u0003\u000d\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0012\u0005\u0012\u00ad\u000a\u0012\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0022\u0003\u0022\u0003\u0022\u0003\u0023\u0003\u0023\u0003\u0023\u0003\u0023\u0003\u0023\u0003\u0023\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0026\u0003\u0026\u0003\u0026\u0003\u0026\u0003\u0026\u0003\u0027\u0003\u0027\u0003\u0027\u0003\u0027\u0003\u0027\u0003\u0028\u0003\u0028\u0003\u0028\u0003\u0028\u0003\u0029\u0003\u0029\u0003\u0029\u0003\u0029\u0003\u0029\u0003\u0029\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002b\u0003\u002b\u0003\u002c\u0003\u002c\u0003\u002d\u0003\u002d\u0003\u002e\u0003\u002e\u0003\u002f\u0003\u002f\u0003\u0030\u0003\u0030\u0003\u0030\u0003\u0031\u0003\u0031\u0003\u0032\u0003\u0032\u0003\u0033\u0003\u0033\u0003\u0034\u0003\u0034\u0003\u0035\u0003\u0035\u0003\u0036\u0003\u0036\u0003\u0036\u0003\u0037\u0003\u0037\u0003\u0037\u0003\u0038\u0003\u0038\u0003\u0038\u0003\u0039\u0003\u0039\u0003\u0039\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0003\u003a\u0005\u003a\u015f\u000a\u003a\u0003\u003b\u0006\u003b\u0162\u000a\u003b\u000d\u003b\u000e\u003b\u0163\u0003\u003b\u0005\u003b\u0167\u000a\u003b\u0003\u003c\u0003\u003c\u0007\u003c\u016b\u000a\u003c\u000c\u003c\u000e\u003c\u016e\u000b\u003c\u0003\u003c\u0003\u003c\u0003\u003d\u0003\u003d\u0003\u003d\u0003\u003d\u0003\u003d\u0003\u003d\u0003\u003d\u0003\u003d\u0003\u003d\u0005\u003d\u017b\u000a\u003d\u0003\u003e\u0003\u003e\u0003\u003e\u0003\u003e\u0003\u003e\u0003\u003f\u0003\u003f\u0003\u003f\u0003\u003f\u0006\u003f\u0186\u000a\u003f\u000d\u003f\u000e\u003f\u0187\u0003\u0040\u0006\u0040\u018b\u000a\u0040\u000d\u0040\u000e\u0040\u018c\u0003\u0040\u0003\u0040\u0002\u0002\u0041\u0003\u0003\u0005\u0004\u0007\u0005\u0009\u0006\u000b\u0007\u000d\u0008\u000f\u0009\u0011\u000a\u0013\u000b\u0015\u000c\u0017\u000d\u0019\u000e\u001b\u000f\u001d\u0010\u001f\u0011\u0021\u0012\u0023\u0002\u0025\u0002\u0027\u0002\u0029\u0013\u002b\u0014\u002d\u0015\u002f\u0016\u0031\u0017\u0033\u0018\u0035\u0019\u0037\u001a\u0039\u001b\u003b\u001c\u003d\u001d\u003f\u001e\u0041\u001f\u0043\u0020\u0045\u0021\u0047\u0022\u0049\u0023\u004b\u0024\u004d\u0025\u004f\u0026\u0051\u0027\u0053\u0028\u0055\u0029\u0057\u002a\u0059\u002b\u005b\u002c\u005d\u002d\u005f\u002e\u0061\u002f\u0063\u0030\u0065\u0031\u0067\u0032\u0069\u0033\u006b\u0034\u006d\u0035\u006f\u0036\u0071\u0037\u0073\u0038\u0075\u0039\u0077\u003a\u0079\u003b\u007b\u003c\u007d\u003d\u007f\u003e\u0003\u0002\u0007\u0004\u0002\u0043\u005c\u0063\u007c\u0004\u0002\u0030\u0030\u0032\u003b\u0006\u0002\u0048\u0048\u004e\u004e\u0068\u0068\u006e\u006e\u0005\u0002\u000c\u000c\u000f\u000f\u0024\u0024\u0005\u0002\u000b\u000c\u000f\u000f\u0022\u0022\u0002\u019e\u0002\u0003\u0003\u0002\u0002\u0002\u0002\u0005\u0003\u0002\u0002\u0002\u0002\u0007\u0003\u0002\u0002\u0002\u0002\u0009\u0003\u0002\u0002\u0002\u0002\u000b\u0003\u0002\u0002\u0002\u0002\u000d\u0003\u0002\u0002\u0002\u0002\u000f\u0003\u0002\u0002\u0002\u0002\u0011\u0003\u0002\u0002\u0002\u0002\u0013\u0003\u0002\u0002\u0002\u0002\u0015\u0003\u0002\u0002\u0002\u0002\u0017\u0003\u0002\u0002\u0002\u0002\u0019\u0003\u0002\u0002\u0002\u0002\u001b\u0003\u0002\u0002\u0002\u0002\u001d\u0003\u0002\u0002\u0002\u0002\u001f\u0003\u0002\u0002\u0002\u0002\u0021\u0003\u0002\u0002\u0002\u0002\u0029\u0003\u0002\u0002\u0002\u0002\u002b\u0003\u0002\u0002\u0002\u0002\u002d\u0003\u0002\u0002\u0002\u0002\u002f\u0003\u0002\u0002\u0002\u0002\u0031\u0003\u0002\u0002\u0002\u0002\u0033\u0003\u0002\u0002\u0002\u0002\u0035\u0003\u0002\u0002\u0002\u0002\u0037\u0003\u0002\u0002\u0002\u0002\u0039\u0003\u0002\u0002\u0002\u0002\u003b\u0003\u0002\u0002\u0002\u0002\u003d\u0003\u0002\u0002\u0002\u0002\u003f\u0003\u0002\u0002\u0002\u0002\u0041\u0003\u0002\u0002\u0002\u0002\u0043\u0003\u0002\u0002\u0002\u0002\u0045\u0003\u0002\u0002\u0002\u0002\u0047\u0003\u0002\u0002\u0002\u0002\u0049\u0003\u0002\u0002\u0002\u0002\u004b\u0003\u0002\u0002\u0002\u0002\u004d\u0003\u0002\u0002\u0002\u0002\u004f\u0003\u0002\u0002\u0002\u0002\u0051\u0003\u0002\u0002\u0002\u0002\u0053\u0003\u0002\u0002\u0002\u0002\u0055\u0003\u0002\u0002\u0002\u0002\u0057\u0003\u0002\u0002\u0002\u0002\u0059\u0003\u0002\u0002\u0002\u0002\u005b\u0003\u0002\u0002\u0002\u0002\u005d\u0003\u0002\u0002\u0002\u0002\u005f\u0003\u0002\u0002\u0002\u0002\u0061\u0003\u0002\u0002\u0002\u0002\u0063\u0003\u0002\u0002\u0002\u0002\u0065\u0003\u0002\u0002\u0002\u0002\u0067\u0003\u0002\u0002\u0002\u0002\u0069\u0003\u0002\u0002\u0002\u0002\u006b\u0003\u0002\u0002\u0002\u0002\u006d\u0003\u0002\u0002\u0002\u0002\u006f\u0003\u0002\u0002\u0002\u0002\u0071\u0003\u0002\u0002\u0002\u0002\u0073\u0003\u0002\u0002\u0002\u0002\u0075\u0003\u0002\u0002\u0002\u0002\u0077\u0003\u0002\u0002\u0002\u0002\u0079\u0003\u0002\u0002\u0002\u0002\u007b\u0003\u0002\u0002\u0002\u0002\u007d\u0003\u0002\u0002\u0002\u0002\u007f\u0003\u0002\u0002\u0002\u0003\u0081\u0003\u0002\u0002\u0002\u0005\u0084\u0003\u0002\u0002\u0002\u0007\u0086\u0003\u0002\u0002\u0002\u0009\u0088\u0003\u0002\u0002\u0002\u000b\u008a\u0003\u0002\u0002\u0002\u000d\u008c\u0003\u0002\u0002\u0002\u000f\u008e\u0003\u0002\u0002\u0002\u0011\u0094\u0003\u0002\u0002\u0002\u0013\u0097\u0003\u0002\u0002\u0002\u0015\u0099\u0003\u0002\u0002\u0002\u0017\u009c\u0003\u0002\u0002\u0002\u0019\u009f\u0003\u0002\u0002\u0002\u001b\u00a2\u0003\u0002\u0002\u0002\u001d\u00a5\u0003\u0002\u0002\u0002\u001f\u00a7\u0003\u0002\u0002\u0002\u0021\u00a9\u0003\u0002\u0002\u0002\u0023\u00ac\u0003\u0002\u0002\u0002\u0025\u00ae\u0003\u0002\u0002\u0002\u0027\u00b0\u0003\u0002\u0002\u0002\u0029\u00b2\u0003\u0002\u0002\u0002\u002b\u00b6\u0003\u0002\u0002\u0002\u002d\u00ba\u0003\u0002\u0002\u0002\u002f\u00c0\u0003\u0002\u0002\u0002\u0031\u00c3\u0003\u0002\u0002\u0002\u0033\u00c8\u0003\u0002\u0002\u0002\u0035\u00cd\u0003\u0002\u0002\u0002\u0037\u00d2\u0003\u0002\u0002\u0002\u0039\u00d5\u0003\u0002\u0002\u0002\u003b\u00da\u0003\u0002\u0002\u0002\u003d\u00dd\u0003\u0002\u0002\u0002\u003f\u00e4\u0003\u0002\u0002\u0002\u0041\u00e8\u0003\u0002\u0002\u0002\u0043\u00ed\u0003\u0002\u0002\u0002\u0045\u00f0\u0003\u0002\u0002\u0002\u0047\u00f6\u0003\u0002\u0002\u0002\u0049\u00fa\u0003\u0002\u0002\u0002\u004b\u00ff\u0003\u0002\u0002\u0002\u004d\u0104\u0003\u0002\u0002\u0002\u004f\u0109\u0003\u0002\u0002\u0002\u0051\u010d\u0003\u0002\u0002\u0002\u0053\u0113\u0003\u0002\u0002\u0002\u0055\u011b\u0003\u0002\u0002\u0002\u0057\u011d\u0003\u0002\u0002\u0002\u0059\u011f\u0003\u0002\u0002\u0002\u005b\u0121\u0003\u0002\u0002\u0002\u005d\u0123\u0003\u0002\u0002\u0002\u005f\u0125\u0003\u0002\u0002\u0002\u0061\u0128\u0003\u0002\u0002\u0002\u0063\u012a\u0003\u0002\u0002\u0002\u0065\u012c\u0003\u0002\u0002\u0002\u0067\u012e\u0003\u0002\u0002\u0002\u0069\u0130\u0003\u0002\u0002\u0002\u006b\u0132\u0003\u0002\u0002\u0002\u006d\u0135\u0003\u0002\u0002\u0002\u006f\u0138\u0003\u0002\u0002\u0002\u0071\u013b\u0003\u0002\u0002\u0002\u0073\u015e\u0003\u0002\u0002\u0002\u0075\u0161\u0003\u0002\u0002\u0002\u0077\u0168\u0003\u0002\u0002\u0002\u0079\u017a\u0003\u0002\u0002\u0002\u007b\u017c\u0003\u0002\u0002\u0002\u007d\u0185\u0003\u0002\u0002\u0002\u007f\u018a\u0003\u0002\u0002\u0002\u0081\u0082\u0007\u003c\u0002\u0002\u0082\u0083\u0007\u003c\u0002\u0002\u0083\u0004\u0003\u0002\u0002\u0002\u0084\u0085\u0007\u007d\u0002\u0002\u0085\u0006\u0003\u0002\u0002\u0002\u0086\u0087\u0007\u002e\u0002\u0002\u0087\u0008\u0003\u0002\u0002\u0002\u0088\u0089\u0007\u007f\u0002\u0002\u0089\u000a\u0003\u0002\u0002\u0002\u008a\u008b\u0007\u002a\u0002\u0002\u008b\u000c\u0003\u0002\u0002\u0002\u008c\u008d\u0007\u002b\u0002\u0002\u008d\u000e\u0003\u0002\u0002\u0002\u008e\u008f\u0007\u0072\u0002\u0002\u008f\u0090\u0007\u0063\u0002\u0002\u0090\u0091\u0007\u0074\u0002\u0002\u0091\u0092\u0007\u0063\u0002\u0002\u0092\u0093\u0007\u006f\u0002\u0002\u0093\u0010\u0003\u0002\u0002\u0002\u0094\u0095\u0007\u005d\u0002\u0002\u0095\u0096\u0007\u005f\u0002\u0002\u0096\u0012\u0003\u0002\u0002\u0002\u0097\u0098\u0007\u003d\u0002\u0002\u0098\u0014\u0003\u0002\u0002\u0002\u0099\u009a\u0007\u002f\u0002\u0002\u009a\u009b\u0007\u0040\u0002\u0002\u009b\u0016\u0003\u0002\u0002\u0002\u009c\u009d\u0007\u003e\u0002\u0002\u009d\u009e\u0007\u002f\u0002\u0002\u009e\u0018\u0003\u0002\u0002\u0002\u009f\u00a0\u0007\u007e\u0002\u0002\u00a0\u00a1\u0007\u0040\u0002\u0002\u00a1\u001a\u0003\u0002\u0002\u0002\u00a2\u00a3\u0007\u003e\u0002\u0002\u00a3\u00a4\u0007\u007e\u0002\u0002\u00a4\u001c\u0003\u0002\u0002\u0002\u00a5\u00a6\u0007\u0030\u0002\u0002\u00a6\u001e\u0003\u0002\u0002\u0002\u00a7\u00a8\u0007\u005d\u0002\u0002\u00a8\u0020\u0003\u0002\u0002\u0002\u00a9\u00aa\u0007\u005f\u0002\u0002\u00aa\u0022\u0003\u0002\u0002\u0002\u00ab\u00ad\u0009\u0002\u0002\u0002\u00ac\u00ab\u0003\u0002\u0002\u0002\u00ad\u0024\u0003\u0002\u0002\u0002\u00ae\u00af\u0004\u0032\u003b\u0002\u00af\u0026\u0003\u0002\u0002\u0002\u00b0\u00b1\u0004\u0082\u0001\u0002\u00b1\u0028\u0003\u0002\u0002\u0002\u00b2\u00b3\u0007\u006f\u0002\u0002\u00b3\u00b4\u0007\u0071\u0002\u0002\u00b4\u00b5\u0007\u0066\u0002\u0002\u00b5\u002a\u0003\u0002\u0002\u0002\u00b6\u00b7\u0007\u0077\u0002\u0002\u00b7\u00b8\u0007\u0075\u0002\u0002\u00b8\u00b9\u0007\u0067\u0002\u0002\u00b9\u002c\u0003\u0002\u0002\u0002\u00ba\u00bb\u0007\u0065\u0002\u0002\u00bb\u00bc\u0007\u006e\u0002\u0002\u00bc\u00bd\u0007\u0063\u0002\u0002\u00bd\u00be\u0007\u0075\u0002\u0002\u00be\u00bf\u0007\u0075\u0002\u0002\u00bf\u002e\u0003\u0002\u0002\u0002\u00c0\u00c1\u0007\u0068\u0002\u0002\u00c1\u00c2\u0007\u0070\u0002\u0002\u00c2\u0030\u0003\u0002\u0002\u0002\u00c3\u00c4\u0007\u0065\u0002\u0002\u00c4\u00c5\u0007\u0076\u0002\u0002\u00c5\u00c6\u0007\u0071\u0002\u0002\u00c6\u00c7\u0007\u0074\u0002\u0002\u00c7\u0032\u0003\u0002\u0002\u0002\u00c8\u00c9\u0007\u0075\u0002\u0002\u00c9\u00ca\u0007\u0067\u0002\u0002\u00ca\u00cb\u0007\u006e\u0002\u0002\u00cb\u00cc\u0007\u0068\u0002\u0002\u00cc\u0034\u0003\u0002\u0002\u0002\u00cd\u00ce\u0007\u0065\u0002\u0002\u00ce\u00cf\u0007\u0071\u0002\u0002\u00cf\u00d0\u0007\u006f\u0002\u0002\u00d0\u00d1\u0007\u0072\u0002\u0002\u00d1\u0036\u0003\u0002\u0002\u0002\u00d2\u00d3\u0007\u006b\u0002\u0002\u00d3\u00d4\u0007\u0068\u0002\u0002\u00d4\u0038\u0003\u0002\u0002\u0002\u00d5\u00d6\u0007\u0067\u0002\u0002\u00d6\u00d7\u0007\u006e\u0002\u0002\u00d7\u00d8\u0007\u0075\u0002\u0002\u00d8\u00d9\u0007\u0067\u0002\u0002\u00d9\u003a\u0003\u0002\u0002\u0002\u00da\u00db\u0007\u0063\u0002\u0002\u00db\u00dc\u0007\u0075\u0002\u0002\u00dc\u003c\u0003\u0002\u0002\u0002\u00dd\u00de\u0007\u0074\u0002\u0002\u00de\u00df\u0007\u0067\u0002\u0002\u00df\u00e0\u0007\u0076\u0002\u0002\u00e0\u00e1\u0007\u0077\u0002\u0002\u00e1\u00e2\u0007\u0074\u0002\u0002\u00e2\u00e3\u0007\u0070\u0002\u0002\u00e3\u003e\u0003\u0002\u0002\u0002\u00e4\u00e5\u0007\u0068\u0002\u0002\u00e5\u00e6\u0007\u0071\u0002\u0002\u00e6\u00e7\u0007\u0074\u0002\u0002\u00e7\u0040\u0003\u0002\u0002\u0002\u00e8\u00e9\u0007\u0066\u0002\u0002\u00e9\u00ea\u0007\u0071\u0002\u0002\u00ea\u00eb\u0007\u0079\u0002\u0002\u00eb\u00ec\u0007\u0070\u0002\u0002\u00ec\u0042\u0003\u0002\u0002\u0002\u00ed\u00ee\u0007\u0076\u0002\u0002\u00ee\u00ef\u0007\u0071\u0002\u0002\u00ef\u0044\u0003\u0002\u0002\u0002\u00f0\u00f1\u0007\u0077\u0002\u0002\u00f1\u00f2\u0007\u0070\u0002\u0002\u00f2\u00f3\u0007\u0076\u0002\u0002\u00f3\u00f4\u0007\u006b\u0002\u0002\u00f4\u00f5\u0007\u006e\u0002\u0002\u00f5\u0046\u0003\u0002\u0002\u0002\u00f6\u00f7\u0007\u0072\u0002\u0002\u00f7\u00f8\u0007\u0077\u0002\u0002\u00f8\u00f9\u0007\u0064\u0002\u0002\u00f9\u0048\u0003\u0002\u0002\u0002\u00fa\u00fb\u0007\u0072\u0002\u0002\u00fb\u00fc\u0007\u0074\u0002\u0002\u00fc\u00fd\u0007\u0071\u0002\u0002\u00fd\u00fe\u0007\u0076\u0002\u0002\u00fe\u004a\u0003\u0002\u0002\u0002\u00ff\u0100\u0007\u006b\u0002\u0002\u0100\u0101\u0007\u0070\u0002\u0002\u0101\u0102\u0007\u0076\u0002\u0002\u0102\u0103\u0007\u006e\u0002\u0002\u0103\u004c\u0003\u0002\u0002\u0002\u0104\u0105\u0007\u0072\u0002\u0002\u0105\u0106\u0007\u0074\u0002\u0002\u0106\u0107\u0007\u006b\u0002\u0002\u0107\u0108\u0007\u0078\u0002\u0002\u0108\u004e\u0003\u0002\u0002\u0002\u0109\u010a\u0007\u006f\u0002\u0002\u010a\u010b\u0007\u0077\u0002\u0002\u010b\u010c\u0007\u0076\u0002\u0002\u010c\u0050\u0003\u0002\u0002\u0002\u010d\u010e\u0007\u0072\u0002\u0002\u010e\u010f\u0007\u0074\u0002\u0002\u010f\u0110\u0007\u006b\u0002\u0002\u0110\u0111\u0007\u0070\u0002\u0002\u0111\u0112\u0007\u0076\u0002\u0002\u0112\u0052\u0003\u0002\u0002\u0002\u0113\u0114\u0007\u0072\u0002\u0002\u0114\u0115\u0007\u0074\u0002\u0002\u0115\u0116\u0007\u006b\u0002\u0002\u0116\u0117\u0007\u0070\u0002\u0002\u0117\u0118\u0007\u0076\u0002\u0002\u0118\u0119\u0007\u006e\u0002\u0002\u0119\u011a\u0007\u0070\u0002\u0002\u011a\u0054\u0003\u0002\u0002\u0002\u011b\u011c\u0007\u002d\u0002\u0002\u011c\u0056\u0003\u0002\u0002\u0002\u011d\u011e\u0007\u002f\u0002\u0002\u011e\u0058\u0003\u0002\u0002\u0002\u011f\u0120\u0007\u002c\u0002\u0002\u0120\u005a\u0003\u0002\u0002\u0002\u0121\u0122\u0007\u0031\u0002\u0002\u0122\u005c\u0003\u0002\u0002\u0002\u0123\u0124\u0007\u003f\u0002\u0002\u0124\u005e\u0003\u0002\u0002\u0002\u0125\u0126\u0007\u003c\u0002\u0002\u0126\u0127\u0007\u003f\u0002\u0002\u0127\u0060\u0003\u0002\u0002\u0002\u0128\u0129\u0007\u0041\u0002\u0002\u0129\u0062\u0003\u0002\u0002\u0002\u012a\u012b\u0007\u0023\u0002\u0002\u012b\u0064\u0003\u0002\u0002\u0002\u012c\u012d\u0007\u003c\u0002\u0002\u012d\u0066\u0003\u0002\u0002\u0002\u012e\u012f\u0007\u0040\u0002\u0002\u012f\u0068\u0003\u0002\u0002\u0002\u0130\u0131\u0007\u003e\u0002\u0002\u0131\u006a\u0003\u0002\u0002\u0002\u0132\u0133\u0007\u0040\u0002\u0002\u0133\u0134\u0007\u003f\u0002\u0002\u0134\u006c\u0003\u0002\u0002\u0002\u0135\u0136\u0007\u003e\u0002\u0002\u0136\u0137\u0007\u003f\u0002\u0002\u0137\u006e\u0003\u0002\u0002\u0002\u0138\u0139\u0007\u003f\u0002\u0002\u0139\u013a\u0007\u003f\u0002\u0002\u013a\u0070\u0003\u0002\u0002\u0002\u013b\u013c\u0007\u0023\u0002\u0002\u013c\u013d\u0007\u003f\u0002\u0002\u013d\u0072\u0003\u0002\u0002\u0002\u013e\u013f\u0007\u0064\u0002\u0002\u013f\u0140\u0007\u0071\u0002\u0002\u0140\u0141\u0007\u0071\u0002\u0002\u0141\u015f\u0007\u006e\u0002\u0002\u0142\u0143\u0007\u0075\u0002\u0002\u0143\u0144\u0007\u0076\u0002\u0002\u0144\u015f\u0007\u0074\u0002\u0002\u0145\u0146\u0007\u0065\u0002\u0002\u0146\u0147\u0007\u006a\u0002\u0002\u0147\u0148\u0007\u0063\u0002\u0002\u0148\u015f\u0007\u0074\u0002\u0002\u0149\u014a\u0007\u006b\u0002\u0002\u014a\u015f\u0007\u003a\u0002\u0002\u014b\u014c\u0007\u006b\u0002\u0002\u014c\u014d\u0007\u0033\u0002\u0002\u014d\u015f\u0007\u0038\u0002\u0002\u014e\u014f\u0007\u006b\u0002\u0002\u014f\u0150\u0007\u0035\u0002\u0002\u0150\u015f\u0007\u0034\u0002\u0002\u0151\u0152\u0007\u006b\u0002\u0002\u0152\u0153\u0007\u0038\u0002\u0002\u0153\u015f\u0007\u0036\u0002\u0002\u0154\u0155\u0007\u0068\u0002\u0002\u0155\u0156\u0007\u0035\u0002\u0002\u0156\u015f\u0007\u0034\u0002\u0002\u0157\u0158\u0007\u0068\u0002\u0002\u0158\u0159\u0007\u0038\u0002\u0002\u0159\u015f\u0007\u0036\u0002\u0002\u015a\u015b\u0007\u0077\u0002\u0002\u015b\u015c\u0007\u0070\u0002\u0002\u015c\u015d\u0007\u006b\u0002\u0002\u015d\u015f\u0007\u0076\u0002\u0002\u015e\u013e\u0003\u0002\u0002\u0002\u015e\u0142\u0003\u0002\u0002\u0002\u015e\u0145\u0003\u0002\u0002\u0002\u015e\u0149\u0003\u0002\u0002\u0002\u015e\u014b\u0003\u0002\u0002\u0002\u015e\u014e\u0003\u0002\u0002\u0002\u015e\u0151\u0003\u0002\u0002\u0002\u015e\u0154\u0003\u0002\u0002\u0002\u015e\u0157\u0003\u0002\u0002\u0002\u015e\u015a\u0003\u0002\u0002\u0002\u015f\u0074\u0003\u0002\u0002\u0002\u0160\u0162\u0009\u0003\u0002\u0002\u0161\u0160\u0003\u0002\u0002\u0002\u0162\u0163\u0003\u0002\u0002\u0002\u0163\u0161\u0003\u0002\u0002\u0002\u0163\u0164\u0003\u0002\u0002\u0002\u0164\u0166\u0003\u0002\u0002\u0002\u0165\u0167\u0009\u0004\u0002\u0002\u0166\u0165\u0003\u0002\u0002\u0002\u0166\u0167\u0003\u0002\u0002\u0002\u0167\u0076\u0003\u0002\u0002\u0002\u0168\u016c\u0007\u0024\u0002\u0002\u0169\u016b\u000a\u0005\u0002\u0002\u016a\u0169\u0003\u0002\u0002\u0002\u016b\u016e\u0003\u0002\u0002\u0002\u016c\u016a\u0003\u0002\u0002\u0002\u016c\u016d\u0003\u0002\u0002\u0002\u016d\u016f\u0003\u0002\u0002\u0002\u016e\u016c\u0003\u0002\u0002\u0002\u016f\u0170\u0007\u0024\u0002\u0002\u0170\u0078\u0003\u0002\u0002\u0002\u0171\u0172\u0007\u0076\u0002\u0002\u0172\u0173\u0007\u0074\u0002\u0002\u0173\u0174\u0007\u0077\u0002\u0002\u0174\u017b\u0007\u0067\u0002\u0002\u0175\u0176\u0007\u0068\u0002\u0002\u0176\u0177\u0007\u0063\u0002\u0002\u0177\u0178\u0007\u006e\u0002\u0002\u0178\u0179\u0007\u0075\u0002\u0002\u0179\u017b\u0007\u0067\u0002\u0002\u017a\u0171\u0003\u0002\u0002\u0002\u017a\u0175\u0003\u0002\u0002\u0002\u017b\u007a\u0003\u0002\u0002\u0002\u017c\u017d\u0007\u0070\u0002\u0002\u017d\u017e\u0007\u0077\u0002\u0002\u017e\u017f\u0007\u006e\u0002\u0002\u017f\u0180\u0007\u006e\u0002\u0002\u0180\u007c\u0003\u0002\u0002\u0002\u0181\u0186\u0005\u0023\u0012\u0002\u0182\u0186\u0005\u0025\u0013\u0002\u0183\u0186\u0005\u0027\u0014\u0002\u0184\u0186\u0007\u0061\u0002\u0002\u0185\u0181\u0003\u0002\u0002\u0002\u0185\u0182\u0003\u0002\u0002\u0002\u0185\u0183\u0003\u0002\u0002\u0002\u0185\u0184\u0003\u0002\u0002\u0002\u0186\u0187\u0003\u0002\u0002\u0002\u0187\u0185\u0003\u0002\u0002\u0002\u0187\u0188\u0003\u0002\u0002\u0002\u0188\u007e\u0003\u0002\u0002\u0002\u0189\u018b\u0009\u0006\u0002\u0002\u018a\u0189\u0003\u0002\u0002\u0002\u018b\u018c\u0003\u0002\u0002\u0002\u018c\u018a\u0003\u0002\u0002\u0002\u018c\u018d\u0003\u0002\u0002\u0002\u018d\u018e\u0003\u0002\u0002\u0002\u018e\u018f\u0008\u0040\u0002\u0002\u018f\u0080\u0003\u0002\u0002\u0002\u000c\u0002\u00ac\u015e\u0163\u0166\u016c\u017a\u0185\u0187\u018c\u0003\u0008\u0002\u0002"

		val ATN = ATNDeserializer().deserialize(serializedATN.asCharArray())
		init {
			decisionToDFA = Array<DFA>(ATN.numberOfDecisions, {
				DFA(ATN.getDecisionState(it)!!, it)
			})


		}
	}

    enum class Tokens(val id: Int) {
	    T__0(1),
	    T__1(2),
	    T__2(3),
	    T__3(4),
	    T__4(5),
	    T__5(6),
	    T__6(7),
	    T__7(8),
	    T__8(9),
	    T__9(10),
	    T__10(11),
	    T__11(12),
	    T__12(13),
	    T__13(14),
	    T__14(15),
	    T__15(16),
	    MOD(17),
	    USE(18),
	    CLASS(19),
	    FN(20),
	    CTOR(21),
	    SELF(22),
	    COMP(23),
	    IF(24),
	    ELSE(25),
	    AS(26),
	    RETURN(27),
	    FOR(28),
	    DOWN(29),
	    TO(30),
	    UNTIL(31),
	    PUB(32),
	    PROT(33),
	    INTL(34),
	    PRIV(35),
	    MUT(36),
	    PRINT(37),
	    PRINTLN(38),
	    PLUS(39),
	    MINUS(40),
	    STAR(41),
	    SLASH(42),
	    EQUALS(43),
	    ASSIGN_EQ(44),
	    QUETION_MK(45),
	    EXCLAMATION_MK(46),
	    COLON(47),
	    GREATER(48),
	    LESS(49),
	    GREATER_EQ(50),
	    LESS_EQ(51),
	    EQ(52),
	    NOT_EQ(53),
	    TYPES(54),
	    NUMBER(55),
	    STRING(56),
	    BOOL(57),
	    NULL(58),
	    ID(59),
	    WS(60)
    }

	enum class Channels(val id: Int) {
		DEFAULT_TOKEN_CHANNEL(0),
		HIDDEN(1),
	}

	override val channelNames = Channels.values().map(Channels::name).toTypedArray()

	enum class Modes(val id: Int) {
		DEFAULT_MODE(0),
	}

	enum class Rules {
	    T__0,
	    T__1,
	    T__2,
	    T__3,
	    T__4,
	    T__5,
	    T__6,
	    T__7,
	    T__8,
	    T__9,
	    T__10,
	    T__11,
	    T__12,
	    T__13,
	    T__14,
	    T__15,
	    CHAR,
	    DIGIT,
	    UNICODE,
	    MOD,
	    USE,
	    CLASS,
	    FN,
	    CTOR,
	    SELF,
	    COMP,
	    IF,
	    ELSE,
	    AS,
	    RETURN,
	    FOR,
	    DOWN,
	    TO,
	    UNTIL,
	    PUB,
	    PROT,
	    INTL,
	    PRIV,
	    MUT,
	    PRINT,
	    PRINTLN,
	    PLUS,
	    MINUS,
	    STAR,
	    SLASH,
	    EQUALS,
	    ASSIGN_EQ,
	    QUETION_MK,
	    EXCLAMATION_MK,
	    COLON,
	    GREATER,
	    LESS,
	    GREATER_EQ,
	    LESS_EQ,
	    EQ,
	    NOT_EQ,
	    TYPES,
	    NUMBER,
	    STRING,
	    BOOL,
	    NULL,
	    ID,
	    WS
	}


	init {
		this.interpreter = LexerATNSimulator(this, ATN, decisionToDFA as Array<DFA?>, sharedContextCache)
	}

}