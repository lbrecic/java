package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Class {@code SmartScriptToken} represents tokens for smart script lexer.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SmartScriptToken {
	
	private SmartScriptTokenType type;
	private Object value;
	
	/**
	 * Default constructor.
	 * 
	 * @param type token type
	 * @param value token value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Default value getter.
	 * 
	 * @return token value.
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Default type getter.
	 * 
	 * @return token type.
	 */
	public SmartScriptTokenType getType() {
		return this.type;
	}
}
