package hr.fer.oprpp1.hw02.prob1;

/**
 * Enum <code>TokenType</code> lists possible types of token.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public enum TokenType {
	/**
	 * End of file token.
	 */
	EOF, 
	
	/**
	 * Token represents specific type of string.
	 */
	WORD, 
	
	/**
	 * Number token.
	 */
	NUMBER, 
	
	/**
	 * Token represents symbols.
	 */
	SYMBOL
}