package hr.fer.oprpp1.lsystems.impl;

import java.awt.Color;
import hr.fer.oprpp1.math.Vector2D;

/**
 * Class {@code TurtleState} 
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class TurtleState {
	
	private Vector2D currentPosition;
	private Vector2D currentDirection;
	private Color currentColor;
	private double currentShift;
	
	/**
	 * Default current position getter.
	 * 
	 * @return current position.
	 */
	public Vector2D getCurrentPosition() {
		return this.currentPosition;
	}
	
	/**
	 * Default current direction getter.
	 * 
	 * @return current direction.
	 */
	public Vector2D getCurrentDirection() {
		return this.currentDirection;
	}
	
	/**
	 * Default color getter.
	 * 
	 * @return current color.
	 */
	public Color getCurrentColor() {
		return this.currentColor;
	}
	
	/**
	 * Default shift getter.
	 * 
	 * @return current shift.
	 */
	public double getCurrentShift() {
		return this.currentShift;
	}
	
	/**
	 * Default current direction setter.
	 * 
	 * @param next
	 */
	public void setCurrentDirection(Vector2D next) {
		this.currentDirection = next;
	}
	
	/**
	 * Default current position setter.
	 * 
	 * @param next
	 */
	public void setCurrentPosition(Vector2D next) {
		this.currentPosition = next;
	}
	/**
	 * Default color setter.
	 * 
	 * @param color
	 */
	public void setCurrentColor(Color color) {
		this.currentColor = color;
	}
	
	/**
	 * Default shift setter.
	 * 
	 * @param shift
	 */
	public void setCurrentShift(double shift) {
		this.currentShift = shift;
	}
	
	/**
	 * Default constructor.
	 * 
	 * @param currentPosition current position of turtle
	 * @param currentDirection current direction of turtle
	 * @param currentColor current color of turtle's line
	 * @param currentShift current shift in drawing
	 */
	public TurtleState(Vector2D currentPosition, Vector2D currentDirection,
							Color currentColor, double currentShift) {
		this.currentColor = currentColor;
		this.currentDirection = currentDirection;
		this.currentPosition = currentPosition;
		this.currentShift = currentShift;
	}
	
	/**
	 * Default empty constructor.
	 */
	public TurtleState() {}

	/**
	 * Method returns copy of current turtle state.
	 * 
	 * @return copy of turtle state.
	 */
	public TurtleState copy() {
		TurtleState copy = new TurtleState();
		copy.currentColor = this.currentColor;
		copy.currentDirection = this.currentDirection.copy();
		copy.currentPosition = this.currentPosition.copy();
		copy.currentShift = this.currentShift;
		
		return copy;
	}
}
