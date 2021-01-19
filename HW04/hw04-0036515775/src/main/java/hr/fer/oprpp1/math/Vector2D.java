package hr.fer.oprpp1.math;

/**
 * <code>Vector2D</code> class provides simple operations and work with
 * two-dimensional vectors.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Vector2D {
	private double x, y;
	
	/**
	 * Default constructor.
	 * 
	 * @param x x-axis parameter.
	 * @param y y-axis parameter.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * X-axis parameter getter.
	 * 
	 * @return x-axis parameter.
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Y-axis parameter getter.
	 * 
	 * @return y-axis parameter.
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Method adds given vector to current vector.
	 * 
	 * @param offset vector that is added to current vector.
	 */
	public void add(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}
	
	/**
	 * Method creates new vector whose parameters are
	 * added parameters of given and current vector.
	 * 
	 * @param offset vector that is added to current vector.
	 * @return new addition created vector.
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.getX() + offset.getX(),
				this.getY() + offset.getY());
	}
	
	/**
	 * Method rotates current vector for given angle.
	 * 
	 * @param angle rotation angle.
	 */
	public void rotate(double angle) {
		double tmpX = this.getX();
		this.x = Math.cos(angle)*this.getX() - Math.sin(angle)*this.getY();
		this.y = Math.sin(angle)*tmpX + Math.cos(angle)*this.getY();
	}
	
	/**
	 * Method creates and returns new vector which is created by rotation
	 * of current vector.
	 * 
	 * @param angle rotation angle.
	 * @return new rotation created vector.
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle)*this.getX() - Math.sin(angle)*this.getY(),
				Math.sin(angle)*this.getX() + Math.cos(angle)*this.getY());
	}
	
	/**
	 * Method scales current vector for given scaler.
	 * 
	 * @param scaler constant by which vector is scaled.
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Method creates and returns new vector which is created by scaling
	 * of current vector.
	 * 
	 * @param scaler constant by which vector is scaled.
	 * @return new scaling created vector.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.getX()*scaler, this.getY()*scaler);
	}
	
	/**
	 * Method copies current vector to a new one.
	 * 
	 * @return new instance of vector that is copy of current vector.
	 */
	public Vector2D copy() {
		return new Vector2D(this.getX(), this.getY());
	}
}
