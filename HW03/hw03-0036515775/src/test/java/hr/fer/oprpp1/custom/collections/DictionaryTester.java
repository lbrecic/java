package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTester {
	@Test
	public void testDictionaryIsEmptyTrue() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		assertTrue(dict.isEmpty());
	}
	
	@Test
	public void testDictionaryIsEmptyFalse() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertFalse(dict.isEmpty());
	}
	
	@Test
	public void testDictionarySizeEmpty() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		assertEquals(0, dict.size());
	}
	
	@Test
	public void testDictionarySize() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertEquals(1, dict.size());
	}
	
	@Test
	public void testDictionaryPut() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		dict.put(1, 2);	
		assertEquals(2, dict.size());
	}
	
	@Test
	public void testDictionaryPutOverwrite() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertEquals(0, dict.put(0, 1));
	}
	
	@Test
	public void testDictionaryGet() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertEquals(null, dict.get(1));
	}
	
	@Test
	public void testDictionaryGetContains() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertEquals(0, dict.get(0));
	}
	
	@Test
	public void testDictionaryRemoveContains() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertEquals(0, dict.remove(0));
	}
	
	@Test
	public void testDictionaryRemove() {
		Dictionary<Integer, Integer> dict = new Dictionary();
		dict.put(0, 0);
		assertEquals(null, dict.remove(1));
	}
}
