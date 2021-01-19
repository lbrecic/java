package hr.fer.oprpp1.custom.collections;

/**
 * Interface <code>ElementsGetter</code> provides easy work with getters that operate over collections.
 * 
 * @author Luka Brečić
 * @version 2.0
 */
public interface ElementsGetter<T> {
	/**
	 * Method checks if collection has next element for given instance of <code>ElementsGetter</code> class.
	 * 
	 * @returns <code>true</code> if collection has next element, <code>false</code> otherwise.
	 */
	public boolean hasNextElement();
	
	/**
	 * Method gets next element of the given collection.
	 * 
	 * @return next element from the collection.
	 */
	public T getNextElement();
	
	/**
	 * Method process remaining elements of collection for this instance of getter using instance of <code>Processor</code> class.
	 * 
	 * @param p processor which process objects from this getter instance.
	 */
	public default void processRemaining(Processor<T> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
