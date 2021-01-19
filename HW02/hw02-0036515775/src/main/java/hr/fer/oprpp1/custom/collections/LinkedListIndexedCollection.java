package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class <code>LinkedListIndexedCollection</code> provides work with list-like collection of objects.
 * 
 * @author Luka Brečić
 * @version 2.0
 */
public class LinkedListIndexedCollection implements List {
	
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
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
	public LinkedListIndexedCollection(Collection collection) {
		if (collection == null) throw new NullPointerException();
		this.addAll(collection);
		this.modificationCount = 0;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if given argument is <code>null</code>.
	 */
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		ListNode  item = new ListNode();
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
	public Object get(int index) {
		if (index < 0 || index > this.size() - 1) throw new IndexOutOfBoundsException();
		ListNode iterator;
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
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		if (value == null) throw new NullPointerException();
		ListNode iterator = this.first;
		while (position > 0) {
			iterator = iterator.next;
			position--;
		}
		ListNode item = new ListNode();
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
	public int indexOf(Object value) {
		if(value != null) {		
			ListNode iterator = first;
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
		ListNode iterator = first;
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
	public boolean contains(Object value) {
		if (value != null) {
			ListNode iterator = first;
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
	public boolean remove(Object value) {
		if (this.contains(value)) {
			ListNode iterator = first;
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
		Object[] arr = new Object[this.size()];
		int i = 0;
		ListNode iterator = this.first;
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
	public void addAll(Collection other) {
		class AddAllProcessor implements Processor {
			public void process(Object value) {
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
	public ElementsGetter createElementsGetter() {
		return new LinkedElementsGetter(this);
	}
	
	private static class LinkedElementsGetter implements ElementsGetter{
		public ListNode currElement;
		private LinkedListIndexedCollection list;
		private long savedModificiationCount;
		
		/**
		 * Default constructor of <code>LinkedElementsGetter</code> class.
		 * 
		 * @param list reference to collection over which this class iterates.
		 */
		public LinkedElementsGetter(LinkedListIndexedCollection list) {
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
		public Object getNextElement() {
			if (this.savedModificiationCount != list.modificationCount) throw new ConcurrentModificationException();
			if (this.hasNextElement()) {
				ListNode tmp = currElement;
				currElement = currElement.next;
				return tmp.value;
			} else {
				throw new NoSuchElementException();
			}
		}
	}
}
