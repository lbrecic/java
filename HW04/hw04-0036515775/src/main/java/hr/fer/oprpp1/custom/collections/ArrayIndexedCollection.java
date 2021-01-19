package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class <code>ArrayIndexedCollection</code> enables users to work with an array-like collection
 * that provides methods that work with index.
 * 
 * @author Luka Brečić
 * @version 3.0
 */
public class ArrayIndexedCollection<T> implements List<T> {
	private int size;
	private T[] elements;
	
	private long modificationCount;
	
	/**
	 * Constructor with given capacity that collection can contain.
	 * 
	 * @param initialCapacity number which represents capacity of collection.
	 * @throws IllegalArgumentException if argument is less than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException();
		this.size = 0;
		this.elements = (T[])new Object[initialCapacity];
		this.modificationCount = 0;
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
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		if (collection == null) throw new NullPointerException();
		int n;
		
		if (collection.size() > initialCapacity ) n = collection.size();
		else n = initialCapacity;
		
		this.elements = (T[])new Object[n];
		this.addAll(collection);
		this.modificationCount = 0;
	}
	
	/**
	 * Constructor with given other collection whose elements are copied using the <code>addAll</code> method.
	 * 
	 * @param collection collection that is copied into new collection. 
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection, 0);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if given argument is <code>null</code>.
	 */
	public void add(T value) {
		if (value == null) throw new NullPointerException();
		if (this.size() == this.elements.length) {
			ArrayIndexedCollection<T> tmp = new ArrayIndexedCollection(this.size()*2);
			for (int i = 0; i < this.size(); i++) {
				tmp.elements[i] = this.elements[i];
			}
			this.elements = tmp.elements;			
		}
		this.elements[this.size++] = value;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IndexOutOfBoundsException if argument is less than 0 or higher than or equal to collection size.
	 */
	public T get(int index) {
		if (index < 0 || index > this.size() - 1) 
			throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		for (int i = 0; i < this.size(); i++) {
			this.elements[i] = null;
		}
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if an object value is <code>null</code>.
	 * @throws indexOutOfboundesException if position argument is less than 0 or higher than collection size.
	 */
	public void insert(T value, int position) {
		if (value == null) throw new NullPointerException();
		if (position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		if (position != this.size()) {
			for (int i = this.size(); i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
		}
		this.elements[position] = value;
		this.size++;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int indexOf(T value) {
		if (value != null) {
			for (int i = 0; i < this.size(); i++) {
				if (value.equals(this.elements[i])) return i;
			}
		}
		return -1;
	}
	
	/**
	 * {@inheritDoc}
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
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return this.size;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(T value) {
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
	public Object[] toArray() {
		T[] arr = (T[])new Object[this.size()];
		for (int i = 0; i < this.size(); i++) {
			arr[i] = this.elements[i];
		}
		return arr;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addAll(Collection<? extends T> other) {
		class AddAllProcessor implements Processor<T> {
			public void process(T value) {
				add(value);
			}
		};
		
		other.forEach(new AddAllProcessor());
		
		this.modificationCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(T value) {
		if (this.contains(value)) {
			this.remove(indexOf(value));
			this.modificationCount++;
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
	
	/**
	 * {@inheritDoc}
	 * Method gives reference to this Collection.
	 * 
	 * @returns new instance of <code>ElementsGetter</code> class.
	 */
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayElementsGetter(this);
	}
	
	/**
	 * Class <code>ArrayElementsGetter</code> is appropriate instance of <code>ElementsGetter</code> class
	 * that provides easy work wtih elements of this collection.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class ArrayElementsGetter<T> implements ElementsGetter<T> {
		public int currElement;
		private ArrayIndexedCollection<T> arr;
		private long savedModificationCount;
		
		/**
		 * Default constructor of <code>ArrayElementsGetter</code> class.
		 * 
		 * @param arr reference to collection over which this class iterates.
		 */
		public ArrayElementsGetter(ArrayIndexedCollection<T> arr) {
			currElement = 0;
			this.arr = arr;
			this.savedModificationCount = arr.modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if collection was update after creating instance of this getter.
		 */
		public boolean hasNextElement() {
			if (this.savedModificationCount != arr.modificationCount) throw new ConcurrentModificationException();
			if (arr.elements[this.currElement] != null) return true;
			return false;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if collection was update after creating instance of this getter.
		 * @throws NoSuchElementException if collection doesn't have any elements for instance of this getter.
		 */
		public T getNextElement() {
			if (this.savedModificationCount != arr.modificationCount) throw new ConcurrentModificationException();
			if (this.hasNextElement()) {
				return arr.get(currElement++);
			} else {
				throw new NoSuchElementException();
			}
		}
	}
}
