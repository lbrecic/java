package hr.fer.oprpp1.custom.collections;

/**
 * Interface <code>Processor</code>.
 * 
 * @author Luka Brečić
 * @version 3.0
 */
public interface Processor<T> {
	/**
	 * Method does work over given object. 
	 * @param value an object that is processed.
	 */
	public void process(T value);
}
