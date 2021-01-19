package hr.fer.oprpp1.custom.collections;

/**
 * Class <code>ObjecStack</code> provides work with stack-like structure.
 * Class uses <i>Adapter pattern</i> with instance of <code>ArrayIndexedCollection</code> as adaptee.
 * Adaptee represents stack.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ObjectStack {
	private ArrayIndexedCollection adaptee;
	
	/**
	 * Default empty constructor.
	 */
	public ObjectStack() {
		adaptee = new ArrayIndexedCollection();
	}
	
	/**
	 * Method checks if stack is empty.
	 * 
	 * @return <code>true</code> if stack is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}
	
	/**
	 * Method returns number of objects currently on stack.
	 * 
	 * @return current number of objects on stack.
	 */
	public int size() {
		return adaptee.size();
	}
	
	/**
	 * Method puts an object on top of the stack.
	 * 
	 * @param value an object that is placed on top of the stack.
	 */
	public void push(Object value) {
		adaptee.add(value);
	}
	
	/**
	 * Method returns and removes an object from top of the stack.
	 * 
	 * @return an object from top of the stack.
	 */
	public Object pop() {
		if (this.isEmpty()) throw new EmptyStackException();
		Object item = adaptee.get(adaptee.size() - 1);
		adaptee.remove(adaptee.size() - 1);
		return item;
	}
	
	/**
	 * Method returns an object that is currently on top of the stack.
	 * 
	 * @return what object is currently on top of the stack.
	 */
	public Object peek() {
		if (this.isEmpty()) throw new EmptyStackException();
		return adaptee.get(adaptee.size() - 1);
	}
	
	/**
	 * Method removes all objects from the stack.
	 */
	public void clear() {
		adaptee.clear();
	}
}
