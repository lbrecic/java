package hr.fer.oprpp1.custom.collections;

/**
 * Interface <code>List</code> enables users to work with list-like collections of objects.
 * 
 * @author Luka Brečić
 * @version 2.0
 */
public interface List<T> extends Collection<T> {
	/**
	 * Method gets object from the given index of collection.
	 * 
	 * @param index number from which we take the object of collection. 
	 * @return an object from given index.
	 */
	public T get(int index);
	
	/**
	 * Method adds given object at given position in collection.
	 * All elements at position and greater positions must be shifted one place towards the end.
	 * 
	 * @param value object that is added into collection.
	 * @param position number that represents position at which object is added into collection.
	 */
	public void insert(T value, int position);
	
	/**
	 * Method searches the collection and returns the index of the first occurence
	 * of the given value or -1 if the value is not found or value is <code>null</code>.
	 * 
	 * @param value an object whose index is searched for.
	 * @return index of an object if object is found, -1 if object is not found or object is <code>null</code>. 
	 */
	public int indexOf(T value);
	
	/**
	 * Method removes an object from collection at given index.
	 * All elements at positions greater than given index are shifted one place left.
	 * 
	 * @param index number from which an object is removed.
	 */
	public void remove(int index);
}
