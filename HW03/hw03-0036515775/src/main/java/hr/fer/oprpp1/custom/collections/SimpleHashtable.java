package hr.fer.oprpp1.custom.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <code>SimpleHashtable</code> class represents map with hash implementation.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	private TableEntry<K,V>[] table;
	private int size;
	private int modificationCount;
	
	/**
	 * <code>TableEntry</code> class models elements inside of slots of hash table.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Default constructor.
		 * 
		 * @param key
		 * @param value
		 * @param next reference to next entry in slot.
		 */
		public TableEntry(Object key, Object value, TableEntry next) {
			this.key = (K) key;
			this.value = (V) value;
			this.next = next;
		}
		
		/**
		 * Key parameter getter.
		 * 
		 * @return key parameter.
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Value parameter getter.
		 * 
		 * @return value parameter.
		 */
		public V getValue() {
			return this.value;
		}
		
		/**
		 * Value parameter setter.
		 * 
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Method returns string representation of an has entry element.
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append(key.toString());
			sb.append("=");
			sb.append(value.toString());
			
			return sb.toString();
		}
	}
	
	/**
	 * Default constructor.
	 */
	public SimpleHashtable() {
		this.table = (TableEntry<K, V>[])new TableEntry[16];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Constructor with given minimum capacity.
	 * 
	 * @param capacity minimum capacity.
	 * @throws IllegalArgumentException if capacity is less then 1.
	 */
	public SimpleHashtable(int capacity) {
		if (capacity < 1) throw new IllegalArgumentException();
		int tableSize = 0;
		
		for (int i = 0; i < 32; i++) {
			if (Math.pow(2, i) >= capacity) {
				tableSize = (int) Math.pow(2, i);
				break;
			}
		}
		
		this.table = (TableEntry<K, V>[])new TableEntry[tableSize];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Method puts new pair into collection. It overwrites old value if
	 * element with given key already exists in collection.
	 * 
	 * @param key
	 * @param value
	 * @return old value if element exists in collection, null otherwise.
	 * @throws NullPointerException if key is null.
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException();
		
		if (this.size()/this.table.length >= 0.75) {
			TableEntry<K, V>[] tmp = (TableEntry<K, V>[])new TableEntry[this.table.length*2];
			TableEntry<K, V>[] arr = this.toArray();
			this.table = tmp;
			this.size = 0;
			
			for (int i = 0; i < arr.length; i++) {
				this.put(arr[i].getKey(), arr[i].getValue());
			}
		}
		
		V tmp = null;
		
		if (this.containsKey(key)) {
			TableEntry<K, V> it = this.table[Math.abs(key.hashCode()) % this.table.length];
			
			while (it != null) {
				if (it.getKey().equals(key)) {
					tmp = it.getValue();
					it.setValue(value);
					break;
				}
				
				it = it.next;
			}
		} else {
			TableEntry<K, V> it = this.table[Math.abs(key.hashCode()) % this.table.length];
			if (it != null) {
				while (it.next != null) {
					it = it.next;
				}
				
				it.next = new TableEntry<>(key, value, null);
				
			} else {
				this.table[Math.abs(key.hashCode()) % this.table.length] = new TableEntry<>(key, value, null);
			}
			
			this.size++;
			this.modificationCount++;
		}
		
		return tmp;
	}
	
	/**
	 * Method gets value of an element with given key, returns null
	 * if it is not in collection.
	 * 
	 * @param key can't be null.
	 * @return value of element with given key, null otherwise.
	 */
	public V get(Object key) {
		if (key == null || !this.containsKey(key)) return null;
		else {
			TableEntry<K, V> it = this.table[Math.abs(key.hashCode()) % this.table.length];
			
			while (!it.getKey().equals(key)) {			
				it = it.next;
			}
			
			return it.getValue();
		}
	}
	
	/**
	 * Method gets number of objects currently stored in collection.
	 * 
	 * @return number of objects in collection.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Method checks if collection contains element with given key.
	 * 
	 * @param key
	 * @return <code>true</code> if key is in collection, <code>false</code> otherwise.
	 */
	public boolean containsKey(Object key) {		
		if (key == null) return false;
		
		TableEntry<K, V> it = this.table[Math.abs(key.hashCode()) % this.table.length];
		
		while (it != null) {
			if (it.getKey().equals(key)) {
				return true;
			}
			
			it = it.next;
		}
		
		return false;
	}
	
	/**
	 * Method checks if collection contains element with given value.
	 * 
	 * @param value
	 * @return <code>true</code> if value is in collection, <code>false</code> otherwise.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> it;
		for (int i = 0; i < this.table.length; i++) {
			 it = this.table[i];
			
			while (it != null) {
				if (it.getValue().equals(value)) {
					return true;
				}
				
				it = it.next;
			}
		}
		
		return false;
	}
	
	/**
	 * Method removes element with given key and returns its value.
	 * 
	 * @param key
	 * @return value of an removed element, null if nothing is removed.
	 */
	public V remove(Object key) {
		if (key == null) return null;
		
		V tmp = null;
		
		if (this.containsKey(key)) {
			TableEntry<K, V> it = this.table[Math.abs(key.hashCode()) % this.table.length];
			
			if (it.getKey().equals(key)) {
				tmp = it.getValue();
				this.table[Math.abs(key.hashCode()) % this.table.length] = it.next;
			}
			else {
				while (!it.next.getKey().equals(key)) {
					it = it.next;
				}
				
				tmp = it.next.getValue();
				it.next = it.next.next;
			}

			this.size--;
			this.modificationCount++;
		}
		
		return tmp;
	}
	
	/**
	 * Method checks if collection is empty.
	 * 
	 * @return <code>true</code> if collection is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Method returns string representation of its content.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TableEntry<K, V> it;
		boolean first = true;
		
		sb.append("[");
		for (int i = 0; i < this.table.length; i++) {
			it = this.table[i];
			if (it != null && !first) sb.append(", ");
			if (it != null) first = false;
			
			while (it != null) {
				if (it != null) first = false;
				sb.append(it.toString());			
				it = it.next;
				if (it != null) sb.append(", ");
			}
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Method returns array of its elements.
	 * 
	 * @return array of its elements.
	 */
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] arr = (TableEntry<K, V>[])new TableEntry[this.size()];
		TableEntry<K, V> it;
		int idx = 0;
		
		for (int i = 0; i < this.table.length; i++) {
			it = this.table[i];
			
			while (it != null) {
				arr[idx++] = it;
				it = it.next;
			}
		}
		
		return arr;
	}
	
	/**
	 * Method removes all elements from collection.
	 */
	public void clear() {
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}
		
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * Returns an iterator over elements of type TableEntry.
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * <code>IteratorImpl</code> represents iterator over TableEntry elements.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private SimpleHashtable.TableEntry<K, V> last = null;
		private int current = 0;
		private int i = 0;
		private int modif = 0;
		private boolean rem = false;
		
		/**
		 * Default constructor.
		 */
		public IteratorImpl() {
			this.modif = modificationCount;
			int j = 0;
			
			while (table[j] == null && j < table.length) {
				j++;
			}
			
			this.last = table[j];
		}
		/**
		 * @throws ConcurrentModificationException if collection is modified after
		 * iterator is created.
		 */
		@Override
		public boolean hasNext() {
			if (modif != modificationCount) throw new ConcurrentModificationException();
			return current < size;
		}
		
		/**
		 * @throws ConcurrentModificationException if collection is modified after
		 * iterator is created.
		 * @throws NoSuchElementException if there is no more elements to return.
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modif != modificationCount) throw new ConcurrentModificationException();
			if (current > size) throw new NoSuchElementException();
			
			if (last.next == null) {
				i += 1;
				while (i < table.length && table[i] == null) {
					i++;
				}
				
				if (i < table.length) {
					this.last = table[i];
				}
				
			} else {
				last = last.next;
			}
			
			current++;
			rem = false;
			
			return last;
		}
		
		/**
		 * @throws IllegalStateException if method is called twice for same element.
		 * @throws ConcurrentModificationException if collection is modified after
		 * iterator is created.
		 */
		@Override
		public void remove() {
			if (!rem) {
				rem = true;
			} else {
				throw new IllegalStateException();
			}
			if (this.modif != modificationCount) throw new ConcurrentModificationException();

			TableEntry<K, V> it = table[Math.abs(last.getKey().hashCode()) % table.length];

			if (it.getKey().equals(last.getKey())) {
				table[Math.abs(last.getKey().hashCode()) % table.length] = it.next;
			} else {
				while (!it.next.getKey().equals(last.getKey())) {
					it = it.next;
				}

				it.next = last.next;
			}

			size--;
			modificationCount++;
			this.modif++;
		}	
	}
}
