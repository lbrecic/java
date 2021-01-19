package hr.fer.oprpp1.custom.collections;

/**
 * Interface <code>Tester</code>.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface Tester {
	/**
	 * Method checks if given object is appropriate for given conditions.
	 * 
	 * @param obj an object that is tested.
	 * @return <code>true</code> if object is appropriate, <code>false</code> otherwise.
	 */
	public boolean test(Object obj);
}
