package hr.fer.oprpp1.hw02.prob1;

/**
 * Class {@code Token} represents lexer tokens.
 * 
 * @author Luka BRečić
 * @version 1.0
 */
public class Token {
	
	TokenType type;
	Object value;
	
	/**
	 * Default constructor.
	 * 
	 * @param type token type
	 * @param value token value
	 */
	public Token(TokenType type, Object value) {
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
	public TokenType getType() {
		return this.type;
	}
}
