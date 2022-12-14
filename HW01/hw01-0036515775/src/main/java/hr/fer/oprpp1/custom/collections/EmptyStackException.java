package hr.fer.oprpp1.custom.collections;

/**
 * Class <code>EmptyStackException</code> extends java.lang.RuntimeException.
 * This exception is <i>unchecked exception</i> and it is thrown when user is trying
 * to get elements from an empty stack.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EmptyStackException extends RuntimeException {
	/**
	 * Default empty constructor.
	 */
	public EmptyStackException() {
		super();
	}
}
