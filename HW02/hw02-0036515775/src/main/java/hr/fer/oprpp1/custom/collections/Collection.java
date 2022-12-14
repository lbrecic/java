package hr.fer.oprpp1.custom.collections;

/**
 * Interface <code>Collection</code> enables users to work with collections.
 * 
 * @author Luka Brečić
 * @version 2.0
 */
public interface Collection {
	
	/**
	 * Method checks if collection is empty.
	 * 
	 * @return <code>true</code> if collection is empty, <code>false</code> otherwise.
	 */
	public default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Method gets number of objects currently stored in collection.
	 * 
	 * @return number of objects in collection.
	 */
	public int size();
	/**
	 * Method adds object into collection.
	 * 
	 * @param value an object that is added into collection.
	 */
	public void add(Object value);
	
	/**
	 * Method checks if collections contains given object using the method <code>equals</code>.
	 * 
	 * @param value an object whose existence we are checking within the collection. Object can be <code>null</code>
	 * @return <code>true</code> if collection contains given object, <code>false</code> otherwise.
	 */
	public boolean contains(Object value);
	
	/**
	 * The method checks if the collection contains a given object using the <code>equals</code> method 
	 * and removes a single occurrence of the object (this class does not specify which occurrence).
	 * 
	 * @param value an object that is removed if it is in a collection.
	 * @return <code>true</code> if object is successfully removed, <code>false</code> otherwise.
	 */
	public boolean remove(Object value);
	
	/**
	 * The method allocates an array same size as collection size, fills it with the contents of the collection,
	 * and returns the newly created array.
	 * 
	 * @return collection as array. Can't return <code>null</code>.
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray();
	
	/**
	 * Method calls <code>Processor.process(.)</code> for each element of the collection.
	 * The order in which the elements will be sent is not defined in this class.
	 * 
	 * @param processor the type of processor that performs the task on the elements.
	 */
	public default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * The method adds elements of the given collection to the current collection. The given collection remains unchanged.
	 * A local class defines a processor that adds elements to the current collection using the <code>add</code> method.
	 * Addition was done using the <code>forEach</code> method over the given collection with the local class instance as an argument.
	 * 
	 * @param other collection whose elements are added to the current collection.
	 */
	public default void addAll(Collection other) {
		/**
		 * Class <code>AddAllProcessor</code> extends clas <code>Processor</code>.
		 * This implementation procces objects of other collection and adds them to current collection.
		 * 
		 * @author Luka Brečić
		 * @version 1.0
		 */
		class AddAllProcessor implements Processor {
			/**
			 * Method adds given object to collection using <code>add</code> method.
			 * 
			 * @param value an object that is added to collection.
			 */
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new AddAllProcessor());
	}
	
	/**
	 * Method removes all elements from collection.
	 */
	public void clear();
	
	/**
	 * Method creates new instance of <code>ElementsGetter</code> class.
	 */
	public ElementsGetter createElementsGetter();
	
	/**
	 * Default method adds all elements from given collection that satisfy given tester's conditions 
	 * using instance of <code>ElementsGetter</code>.
	 * 
	 * @param col collection whose elements are tested and potentially added to this collection.
	 * @param tester tester that checks if elements are appropriate to be added to this collection.
	 */
	public default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		while (getter.hasNextElement()) {
			Object tmp = getter.getNextElement();
			if (tester.test(tmp)) {
				this.add(tmp);
			}
		}
	}
}
