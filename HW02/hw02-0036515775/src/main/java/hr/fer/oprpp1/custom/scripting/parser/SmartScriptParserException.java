package hr.fer.oprpp1.custom.scripting.parser;

/**
 * <code>SmartScriptParserException</code> class extends <code>RuntimeException</code>.
 * This exception is thrown whitin <code>SmartScriptParser</code> environment.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * Default empty constructor.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Default constructor with reference to another exception.
	 * 
	 * @param e reference to other exception
	 */
	public SmartScriptParserException(Exception e) {
		super(e);
	}
}
