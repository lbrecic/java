package hr.fer.oprpp1.custom.collections;

/**
 * <code>ConcurrentModificationException</code> instance is thrown if collection is changed during iteration.
 * 
 * @author Luka Brečić
 * @version 2.0
 */
public class ConcurrentModificationException extends RuntimeException {
	/**
	 * Default empty constructor.
	 */
	public ConcurrentModificationException() {
		super();
	}
}
