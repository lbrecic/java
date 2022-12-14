package hr.fer.oprpp1.custom.collections;

/**
 * Class <code>ArrayIndexedCollection</code> enables users to work with an array-like collection
 * that provides methods that work with index.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	private int size;
	private Object[] elements;
	
	/**
	 * Constructor with given capacity that collection can contain.
	 * 
	 * @param initialCapacity number which represents capacity of collection.
	 * @throws IllegalArgumentException if argument is less than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException();
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Default empty constructor. Sets initial capacity to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Constructor with given capacity that collection can contain and other collection
	 * whose elements are copied using the <code>addAll</code> method.
	 * 
	 * @param collection collection that is copied into new collection. 
	 * @param initialCapacity number which represents capacity of collection.
	 * @throws NullPointerException if given collection is <code>null</code>.
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (collection == null) throw new NullPointerException();
		int n;
		
		if (collection.size() > initialCapacity ) n = collection.size();
		else n = initialCapacity;
		
		this.elements = new Object[n];
		this.addAll(collection);
	}
	
	/**
	 * Constructor with given other collection whose elements are copied using the <code>addAll</code> method.
	 * 
	 * @param collection collection that is copied into new collection. 
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 0);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if given argument is <code>null</code>.
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		if (this.size() == this.elements.length) {
			ArrayIndexedCollection tmp = new ArrayIndexedCollection(this.size()*2);
			for (int i = 0; i < this.size(); i++) {
				tmp.elements[i] = this.elements[i];
			}
			this.elements = tmp.elements;			
		}
		this.elements[this.size++] = value;
	}
	
	/**
	 * Method gets object from the given index of collection.
	 * 
	 * @param index number from which we take the object of collection. 
	 * @return an object from given index.
	 * @throws IndexOutOfBoundsException if argument is less than 0 or higher than or equal to collection size.
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size() - 1) 
			throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size(); i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Method adds given object at given position in collection.
	 * All elements at position and greater positions must be shifted one place towards the end.
	 * 
	 * @param value object that is added into collection.
	 * @param position number that represents position at which object is added into collection.
	 * @throws NullPointerException if an object value is <code>null</code>.
	 * @throws indexOutOfboundesException if position argument is less than 0 or higher than collection size.
	 */
	public void insert(Object value, int position) {
		if (value == null) throw new NullPointerException();
		if (position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		if (position != this.size()) {
			for (int i = this.size(); i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
		}
		this.elements[position] = value;
		this.size++;
	}
	
	/**
	 * Method searches the collection and returns the index of the first occurence
	 * of the given value or -1 if the value is not found or value is <code>null</code>.
	 * 
	 * @param value an object whose index is searched for.
	 * @return index of an object if object is found, -1 if object is not found or object is <code>null</code>. 
	 */
	public int indexOf(Object value) {
		if (value != null) {
			for (int i = 0; i < this.size(); i++) {
				if (value.equals(this.elements[i])) return i;
			}
		}
		return -1;
	}
	
	/**
	 * Method removes an object from collection at given index.
	 * All elements at positions greater than given index are shifted one place left.
	 * 
	 * @param index number from which an object is removed.
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to collection size.
	 */
	public void remove(int index) {
		if (index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
		if (index != this.size() - 1) {
			for (int i = index; i < this.size() - 1; i++) {
				this.elements[i] = this.elements[i + 1];
			}
		}
		this.elements[this.size() - 1] = null;
		this.size--;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		if (value != null) {
			for (int i = 0; i < this.size(); i++) {
				if (value.equals(this.elements[i])) return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[this.size()];
		for (int i = 0; i < this.size(); i++) {
			arr[i] = this.elements[i];
		}
		return arr;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size(); i++) {
			processor.process(this.elements[i]);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAll(Collection other) {
		class AddAllProcessor extends Processor {
			public void process(Object value) {
				add(value);
			}
		};
		
		other.forEach(new AddAllProcessor());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			this.remove(indexOf(value));
			return true;
		}
		return false;	
	}
	
	/**
	 * Method returns length of the array in array-backed collection.
	 * 
	 * @return length of the backing array from collection.
	 */
	public int length() {
		return this.elements.length;
	}
}
