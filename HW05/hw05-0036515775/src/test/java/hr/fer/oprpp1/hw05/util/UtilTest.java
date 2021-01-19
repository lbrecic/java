package hr.fer.oprpp1.hw05.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testExample() {
		byte[] data = {1, -82, 34};
		assertArrayEquals(data, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void testReverseExample() {
		byte[] data = {1, -82, 34};
		assertEquals("01ae22", Util.bytetohex(data));
	}
}
