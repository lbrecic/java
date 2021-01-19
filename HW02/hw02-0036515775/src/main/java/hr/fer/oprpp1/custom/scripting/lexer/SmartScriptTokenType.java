package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enum {@code SmartScriptTokenType} represents all smart script token types.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public enum SmartScriptTokenType {
	/**
	 * Text token.
	 */
	TEXT,
	
	/**
	 * Integer constant token.
	 */
	INTEGER,

	/**
	 * Double constant token.
	 */
	DOUBLE,
	
	/**
	 * Open tag token.
	 */
	TAGOPEN,
	
	/**
	 * End tag token.
	 */
	TAGEND,
	
	/**
	 * Variable token.
	 */
	VARIABLE,
	
	/**
	 * Function token.
	 */
	FUNCTION,
	
	/**
	 * String token.
	 */
	STRING,
	
	/**
	 * Operator token.
	 */
	OPERATOR,
	
	/**
	 * Tag name token.
	 */
	TAGNAME,
	
	/**
	 * End of file token.
	 */
	EOF
}
