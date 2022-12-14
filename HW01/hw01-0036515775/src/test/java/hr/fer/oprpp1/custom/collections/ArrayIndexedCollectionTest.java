package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	@Test
	public void testEmptyConstructor() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertEquals(16, arr.length());
	}
	
	@Test
	public void testInitialCapacityConstructor() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection(100);
		assertEquals(100, arr.length());
	}
	
	@Test
	public void testInvalidInitialCapacityConstructorThrow() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-10));
	}
	
	@Test
	public void testCollectionCopyingConstructor() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add("Zagreb");
		col1.add("Split");
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1);
		assertEquals(col1.size(), col2.size());
	}
	
	@Test
	public void testCollectionAndCapacityConstructor() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add("Zagreb");
		col1.add("Split");
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 6);
		assertEquals(6, col2.length());
	}
	
	@Test
	public void testEmptyCollectionCopyingConstructorThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testAddNullElementThrow() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.add(null));
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		assertEquals(2, arr.size());
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		arr.add("Osijek");
		assertEquals("Osijek", arr.get(2));
	}
	
	@Test
	public void testInvalidIndexGetThrow() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.get(2));
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		arr.clear();
		assertEquals(0, arr.size());
	}
	
	@Test
	public void testInvalidPositionInsertThrow() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.insert("Zagreb", 2));
	}
	
	@Test
	public void testInvalidValueArgumentInsertThrow() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		assertThrows(NullPointerException.class, () -> arr.insert(null, 1));
	}
	
	@Test
	public void testInsertValid() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		arr.insert("Osijek", 1);
		assertEquals(1, arr.indexOf("Osijek"));
	}
	
	@Test
	public void testNullValueIndexOf() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertEquals(-1, arr.indexOf(null));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		arr.add("Osijek");
		assertEquals(2, arr.indexOf("Osijek"));
	}
	
	@Test
	public void testInvalidIndexRemoveThrow() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(5));
	}
	
	@Test
	public void testRemoveByIndex() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("Zagreb");
		arr.add("Split");
		arr.add("Osijek");
		arr.remove(2);
		assertEquals(2, arr.size());
	}
}
