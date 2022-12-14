package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	@Test
	public void testEmptyConstructor() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
	}
	
	@Test
	public void testCollectionCopyingConstructor() {
		LinkedListIndexedCollection list1 = new LinkedListIndexedCollection();
		list1.add("Zagreb");
		list1.add("Split");
		LinkedListIndexedCollection list2 = new LinkedListIndexedCollection(list1);
		assertEquals(2, list2.size());
	}
	
	@Test
	public void testNullCollectionConstructorThrow() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testAddNullValueThrow() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> list.add(null));
	}
	
	@Test
	public void testAddValidElement() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		assertEquals(2, list.size());
	}
	
	@Test
	public void testGetInvalidIndexThrow() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
	}
	
	@Test
	public void testGet() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		list.add("OSijek");
		list.add("Dubrovnik");
		assertEquals("Dubrovnik", list.get(3));
	}
	
	@Test
	public void testClear()  {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		list.add("OSijek");
		list.clear();
		assertEquals(0,  list.size());
	}
	
	@Test
	public void testInsertOnInvalidPositionArgumentThrow() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert("Zagreb", 2));
	}
	
	@Test
	public void testInsertInvalidNullValueArgumentThrow() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		assertThrows(NullPointerException.class, () -> list.insert(null, 1));
	}
	
	@Test
	public void testValidInsert() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		list.add("Osijek");
		list.insert("Dubrovnik", 1);
		assertEquals(1, list.indexOf("Dubrovnik"));
	}
	
	@Test
	public void testIndexOfNullArgument() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		list.add("Osijek");
		list.add("Dubrovnik");
		assertEquals(3, list.indexOf("Dubrovnik"));
	}
	
	@Test
	public void testRemoveAtInvalidIndexArgumentThrow() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
	}
	
	@Test
	public void testRemoveAtValidIndexArgument() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Zagreb");
		list.add("Split");
		list.add("Osijek");
		list.add("Dubrovnik");
		list.remove(2);
		assertEquals(3, list.size());
	}
}
