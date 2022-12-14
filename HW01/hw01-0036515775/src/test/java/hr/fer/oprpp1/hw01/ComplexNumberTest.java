package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	@Test
	public void testDefaultConstructorReal() {
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		assertEquals(0.0, num.getReal());
	}
	
	@Test
	public void testDefaultConstructorImaginary() {
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		assertEquals(0.0, num.getImaginary());
	}
	
	@Test
	public void testGetNumFromReal() {
		ComplexNumber num = ComplexNumber.fromReal(2.0);
		assertEquals(2.0, num.getReal());
	}
	
	@Test
	public void testGetNumFromImaginary() {
		ComplexNumber num = ComplexNumber.fromImaginary(2.0);
		assertEquals(2.0, num.getImaginary());
	}
	
	@Test
	public void testGetNumFromMagnitudeAndInvalidAngleThrow() {
		assertThrows(IndexOutOfBoundsException.class, () -> ComplexNumber.fromMagnitudeAndAngle(2.0, 7.0));
	}
	
	@Test
	public void testGetNumFromMagnitudeAndAngle() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(1.0, Math.PI);
		assertEquals(-1.0, num.getReal());
	}
	
	@Test
	public void testParseInvalidArgumentThrow() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("++8-3i"));
	}
	
	@Test
	public void testParseValid() {
		assertEquals(2.0, ComplexNumber.parse("+2+3i").getReal());
	}
	
	@Test
	public void testGetReal() {
		ComplexNumber num = new ComplexNumber(3.0, -1.0);
		assertEquals(3.0, num.getReal());
	}
	
	@Test
	public void testGetImaginary() {
		ComplexNumber num = new ComplexNumber(3.0, -1.0);
		assertEquals(-1.0, num.getImaginary());
	}
	
	@Test
	public void testGetMagnitude() {
		ComplexNumber num = new ComplexNumber(1.0, -1.0);
		assertEquals(Math.sqrt(2), num.getMagnitude());
	}
	
	@Test
	public void testGetAngle() {
		ComplexNumber num = new ComplexNumber(1.0, -1.0);
		assertEquals(7*Math.PI/4, num.getAngle());
	}
	
	@Test
	public void testAddComplex() {
		ComplexNumber c1 = new ComplexNumber(1.0, 1.0);
		ComplexNumber c2 = new ComplexNumber(3.0, -4.0);
		c1 = c1.add(c2);
		assertEquals(-3.0, c1.getImaginary());
	}
	
	@Test
	public void testSubComplex() {
		ComplexNumber c1 = new ComplexNumber(1.0, 1.0);
		ComplexNumber c2 = new ComplexNumber(3.0, -4.0);
		c1 = c1.sub(c2);
		assertEquals(5.0, c1.getImaginary());
	}
	
	@Test
	public void testMulComplex() {
		ComplexNumber c1 = new ComplexNumber(1.0, 1.0);
		ComplexNumber c2 = new ComplexNumber(3.0, -4.0);
		c1 = c1.mul(c2);
		assertEquals(-1.0, c1.getImaginary());
	}
	
	@Test
	public void testDivComplex() {
		ComplexNumber c1 = new ComplexNumber(1.0, 1.0);
		ComplexNumber c2 = new ComplexNumber(3.0, -4.0);
		c1 = c1.div(c2);
		assertEquals(0.28, c1.getImaginary());
	}
	
	@Test
	public void testPowComplex() {
		ComplexNumber c1 = new ComplexNumber(1.0, 1.0);
		c1 = c1.power(3);
		assertEquals(Math.pow(Math.sqrt(2), 3)*Math.cos(3*Math.PI/4), c1.getReal());
	}
	
	@Test
	public void testRootComplex() {
		ComplexNumber c1 = new ComplexNumber(1.0, 1.0);
		int n = 3;
		ComplexNumber[] c = new ComplexNumber[n];
		c = c1.root(n);
		assertEquals(Math.pow(2, 1/6)*Math.cos(3*Math.PI/4), c[1].getReal());
	}
	
	@Test
	public void testToString() {
		ComplexNumber c1 = new ComplexNumber(1.0, -1.0);
		assertEquals("1.0-i", c1.toString());
	}
}
