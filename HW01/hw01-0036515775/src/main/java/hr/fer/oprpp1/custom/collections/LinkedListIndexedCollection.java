package hr.fer.oprpp1.custom.collections;

/**
 * Class <code>LinkedListIndexedCollection</code> 
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection{
	
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Default empty constructor.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		this.size = 0;
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
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if given argument is <code>null</code>.
	 */
	@Override
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
	}
	
	/**
	 * Method gets object from the given index of collection.
	 * 
	 * @param index number from which we take the object of collection. 
	 * @return an object from given index.
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
	@Override
	public void clear() {
		this.first = this.last = null;
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
	}
	
	/**
	 * Method searches the collection and returns the index of the first occurence
	 * of the given value or -1 if the value is not found or value is <code>null</code>.
	 * 
	 * @param value an object whose index is searched for.
	 * @return index of an object if object is found, -1 if object is not found or object is <code>null</code>. 
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
	 * Method removes an object from collection at given index.
	 * All elements at positions greater than given index are shifted one place left.
	 * 
	 * @param index number from which an object is removed.
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
	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			ListNode iterator = first;
			for (int i = 0; i < this.size(); i++) {
				if (iterator.value.equals(value)) {
					iterator.previous.next = iterator.next;
					iterator.next.previous  = iterator.previous;
					this.size--;
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
	@Override
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
	@Override
	public void forEach(Processor processor) {
		ListNode iterator = first;
		while (iterator != null) {
			processor.process(iterator.value);
			iterator = iterator.next;
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
}
