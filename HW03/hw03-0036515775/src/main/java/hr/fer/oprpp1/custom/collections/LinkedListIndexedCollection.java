package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class <code>LinkedListIndexedCollection</code> provides work with list-like collection of objects.
 * 
 * @author Luka Brečić
 * @version 3.0
 */
public class LinkedListIndexedCollection<T> implements List<T> {
	
	private static class ListNode<T> {
		private ListNode<T> previous;
		private ListNode<T> next;
		private T value;
	}
	
	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	
	private long modificationCount;
	
	/**
	 * Default empty constructor.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Constructor with given other collection whose elements are copied using the <code>addAll</code> method.
	 * 
	 * @param collection collection that is copied into new collection.
	 * @throws NullPointerException if argument is <code>null</code>.
	 */
	public LinkedListIndexedCollection(Collection<T> collection) {
		if (collection == null) throw new NullPointerException();
		this.addAll(collection);
		this.modificationCount = 0;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if given argument is <code>null</code>.
	 */
	public void add(T value) {
		if (value == null) throw new NullPointerException();
		ListNode<T>  item = new ListNode();
		item.value = value;
		if (this.first == null) {
			this.first = item;
			item.previous = null;
		} else {
			this.last.next = item;
			item.previous = this.last;
		}
		item.next = null;
		this.last = item;
		this.size++;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IndexOutOfBoundsException if argument is less than 0 or higher than or equal to collection size.
	 */
	public T get(int index) {
		if (index < 0 || index > this.size() - 1) throw new IndexOutOfBoundsException();
		ListNode<T> iterator;
		if (index <= this.size() / 2) {
			iterator = this.first;
			while (index > 0) {
				iterator = iterator.next;
				index--;
			}
		} else {
			index = this.size() - index - 1;
			iterator = this.last;
			while (index > 0) {
				iterator = iterator.previous;
				index--;
			}
		}
		return iterator.value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}.
	 * @throws NullPointerException if an object value is <code>null</code>.
	 * @throws indexOutOfboundesException if position argument is less than 0 or higher than collection size.
	 */
	public void insert(T value, int position) {
		if (position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		if (value == null) throw new NullPointerException();
		ListNode<T> iterator = this.first;
		while (position > 0) {
			iterator = iterator.next;
			position--;
		}
		ListNode<T> item = new ListNode();
		item.value = value;
		item.next = iterator;
		item.previous = iterator.previous;
		iterator.previous.next = item;
		iterator.previous = item;
		this.size++;
		this.modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int indexOf(T value) {
		if(value != null) {		
			ListNode<T> iterator = first;
			for (int i = 0; i < this.size(); i++) {
				if (iterator.value.equals(value)) return i;
				iterator = iterator.next;
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
		ListNode<T> iterator = first;
		for (int i = 0; i < index; i++) {
			iterator = iterator.next;
		}
		iterator.previous.next = iterator.next;
		iterator.next.previous  = iterator.previous;
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
			ListNode<T> iterator = first;
			for (int i = 0; i < this.size(); i++) {
				if (iterator.value.equals(value)) return true;
				iterator = iterator.next;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean remove(T value) {
		if (this.contains(value)) {
			ListNode<T> iterator = first;
			for (int i = 0; i < this.size(); i++) {
				if (iterator.value.equals(value)) {
					iterator.previous.next = iterator.next;
					iterator.next.previous  = iterator.previous;
					this.size--;
					this.modificationCount++;
					return true;
				}
				iterator = iterator.next;
			}
		}	
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		T[] arr = (T[])new Object[this.size()];
		int i = 0;
		ListNode<T> iterator = this.first;
		while (iterator != null) {
			arr[i] = iterator.value;
			iterator = iterator.next;
			i++;
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
	 * Method gives reference to this Collection.
	 * 
	 * @returns new instance of <code>ElementsGetter</code> class.
	 */
	public ElementsGetter<T> createElementsGetter() {
		return new LinkedElementsGetter(this);
	}
	
	private static class LinkedElementsGetter<T> implements ElementsGetter<T> {
		public ListNode<T> currElement;
		private LinkedListIndexedCollection<T> list;
		private long savedModificiationCount;
		
		/**
		 * Default constructor of <code>LinkedElementsGetter</code> class.
		 * 
		 * @param list reference to collection over which this class iterates.
		 */
		public LinkedElementsGetter(LinkedListIndexedCollection<T> list) {
			currElement = list.first;
			this.list = list;
			this.savedModificiationCount = list.modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if collection was update after creating instance of this getter.
		 */
		public boolean hasNextElement() {
			if (this.savedModificiationCount != list.modificationCount) throw new ConcurrentModificationException();
			if (currElement != null) return true;
			return false;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if collection was update after creating instance of this getter.
		 * @throws NoSuchElementException if collection doesn't have any elements for instance of this getter.
		 */
		public T getNextElement() {
			if (this.savedModificiationCount != list.modificationCount) throw new ConcurrentModificationException();
			if (this.hasNextElement()) {
				ListNode<T> tmp = currElement;
				currElement = currElement.next;
				return tmp.value;
			} else {
				throw new NoSuchElementException();
			}
		}
	}
}
