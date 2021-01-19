package hr.fer.oprpp1.custom.collections;

/**
 * <code>Dictionary</code> class is a map-like structure and it provides
 * simple operations for its instances.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Dictionary<K, V> {
	private ArrayIndexedCollection<Content<K, V>> adaptee;
	
	/**
	 * <code>Content</code> class models structures of elements inside of dictionary.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private class Content<K, V> {
		private K key;
		private V value;
		
		/**
		 * Default constructor.
		 * 
		 * @param key
		 * @param value
		 */
		public Content(K key, V value) {
			if (key == null) throw new NullPointerException();
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Constructor that accepts only key element.
		 * 
		 * @param key
		 */
		public Content(K key) {
			this(key, null);
		}
		
		/**
		 * Key parameter setter.
		 * 
		 * @param key
		 */
		public void setKey(K key) {
			this.key = key;
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
	}
	
	/**
	 * Default constructor.
	 */
	public Dictionary() {
		adaptee = new ArrayIndexedCollection();
	}
	
	/**
	 * Method checks if collection is empty.
	 * 
	 * @return <code>true</code> if collection is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return this.adaptee.isEmpty();
	}
	
	/**
	 * Method gets number of objects currently stored in collection.
	 * 
	 * @return number of objects in collection.
	 */
	public int size() {
		return this.adaptee.size();
	}
	
	/**
	 * Method removes all elements from collection.
	 */
	public void clear() {
		this.adaptee.clear();
	}
	
	/**
	 * Method puts new pair into collection. It overwrites old value if
	 * element with key already exists in collection.
	 * 
	 * @param key
	 * @param value
	 * @return old value if element exists in collection, null otherwise.
	 * @throws IllegalArgumentException if an error occurs.
	 */
	public V put(K key, V value) {
		Content<K, V> tmp = new Content(key, value);
		V tmpValue;
		
		if (this.containsKey(key)) {
			int i = indexKey(key);
			
			if (i == -1) throw new IllegalArgumentException();
			
			tmpValue = this.adaptee.get(i).getValue();
			
			this.adaptee.remove(i);
			this.adaptee.insert(tmp, i);
			
		} else {
			this.adaptee.add(tmp);
			tmpValue = null;
		}
		
		return tmpValue;
	}
	
	/**
	 * Method gets value of an element with given key, returns null
	 * if it is not in collection.
	 * 
	 * @param key
	 * @return value of element with given key, null otherwise.
	 */
	public V get(K key) {
		if (this.containsKey(key)) {
			return this.adaptee.get(this.indexKey(key)).getValue();
		} else {
			return null;
		}
	}
	
	/**
	 * Method removes element with given key and returns its value.
	 * 
	 * @param key
	 * @return value of an removed element, null if nothing is removed.
	 */
	public V remove(K key) {
		V tmp;
		
		if (this.containsKey(key)) {
			tmp = this.adaptee.get(this.indexKey(key)).getValue();
			this.adaptee.remove(indexKey(key));
		} else {
			tmp = null;
		}
		
		return tmp;
	}
	
	/**
	 * Method checks if collection contains element with given key.
	 * 
	 * @param key
	 * @return <code>true</code> if key is in collection, <code>false</code> otherwise.
	 */
	public boolean containsKey(K key) {
		ElementsGetter<Content<K, V>> getter = adaptee.createElementsGetter();
		Content<K, V> tmp;
		
		while (getter.hasNextElement()) {
			tmp = getter.getNextElement();
			if (tmp.getKey().equals(key)) return true;
		}
		
		return false;
	}
	
	/**
	 * Method returns index of element with given key inside of an array-backed structure.
	 * 
	 * @param key
	 * @return index of element with given key, -1 if it doesn't exists.
	 */
	public int indexKey(K key) {
		ElementsGetter<Content<K, V>> getter = adaptee.createElementsGetter();
		Content<K, V> tmp;
		
		while (getter.hasNextElement()) {
			tmp = getter.getNextElement();
			if (tmp.getKey().equals(key)) return adaptee.indexOf(tmp);
		}
		
		return -1;
	}
}
