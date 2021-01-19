package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTester {
	@Test
	public void testXGetter() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		assertEquals(1.0, vector.getX());
	}
	
	@Test
	public void testYGetter() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		assertEquals(1.0, vector.getY());
	}
	
	@Test
	public void testAdd() {
		Vector2D v1 = new Vector2D(1.0, 1.0);
		Vector2D v2 = new Vector2D(1.0, 1.0);
		v1.add(v2);
		assertEquals(2.0, v1.getX());
		assertEquals(2.0, v1.getY());
	}
	
	@Test
	public void testAdded() {
		Vector2D v1 = new Vector2D(1.0, 1.0);
		Vector2D v2 = new Vector2D(1.0, 1.0);
		Vector2D v3 = v1.added(v2);
		assertEquals(2.0, v3.getX());
		assertEquals(2.0, v3.getY());
	}
	
	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		vector.rotate(Math.PI/2);
		assertEquals(-1.0, Math.round(vector.getX()));
	}
	
	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		Vector2D rot = vector.rotated(Math.PI/2);
		assertEquals(-1.0, Math.round(rot.getX()));
	}
	
	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(1.0, 1.0);
		vector.scale(2.0);
		assertEquals(2.0, vector.getX());
	}
	
	@Test
	public void testScaled() {
		Vector2D v1 = new Vector2D(1.0, 1.0);
		Vector2D v2 = v1.scaled(2.0);
		assertEquals(2.0, v2.getX());
	}
	
	@Test
	public void testCopy() {
		Vector2D v1 = new Vector2D(1.0, 1.0);
		Vector2D v2 = v1.copy();
		assertEquals(1.0, v2.getX());
	}
}
