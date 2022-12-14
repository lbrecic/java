package hr.fer.oprpp1.hw01;

/**
 * Class <code>ComplexNumber</code> provides work with complex numbers.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ComplexNumber {
	private double real, imaginary;
	
	/**
	 * Default constructor.
	 * 
	 * @param real represents real part value of the complex number.
	 * @param imaginary represents imaginary part value of the complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Method creates complex number instance only with real part as a given argument.
	 * 
	 * @param real represents real part value of the complex number.
	 * @return new complex number. 
	 */
	public static ComplexNumber fromReal(double real) {
		ComplexNumber num = new ComplexNumber(real, 0.0);
		return num;
	}
	
	/**
	 * Method creates complex number instance only with imaginary part as a given argument.
	 * 
	 * @param imaginary represents imaginary part value of the complex number.
	 * @return new complex number. 
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		ComplexNumber num = new ComplexNumber(0.0, imaginary);
		return num;
	}
	
	/**
	 * Method creates complex number instance from magnitude and angle as given arguments.
	 * Method uses known trigonometric functions and formulas.
	 * 
	 * @param magnitude complex number's distance from the origin.
	 * @param angle an angle between the complex number and the Real axis.
	 * @return new complex number.
	 * @throws IndexOutOfBoundsException is angle argument is less than 0 or greater than 2Pi.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if (angle < 0 || angle > 2*Math.PI) throw new IndexOutOfBoundsException();
		ComplexNumber num = new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
		return num;
	}
	
	/**
	 * Method creates new complex number by parsing the given string.
	 * 
	 * @param s string written complex number that is parsed.
	 * @return new parsed complex number.
	 * @throws IllegalArgumentException if argument is not given in valid format.
	 */
	public static ComplexNumber parse(String s) {
		char[] c = s.toCharArray();
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		String n = "";
		boolean b = false;
		
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '+' || c[i] == '-') { 
				if (b) throw new IllegalArgumentException();
				else {
					if (!n.equals("")) {
						num.real = Double.parseDouble(n);
					}
					
					n = Character.toString(c[i]);
				}
			} else if (c[i] == 'i') { 
				 if (n.equals("")) {
					 num.imaginary = 1.0;
				 } else if (n.equals("-")) {
					 num.imaginary = -1.0;
				 } else {
					 num.imaginary = Double.parseDouble(n);
				 }
				 
				 if (i == c.length -1) {
					 break;
				 } else {
					 throw new IllegalArgumentException();
				 }
			} else {
				n = n + Character.toString(c[i]);
				if (b) b = false;
			}
		}
		
		return num;
	}
	
	/**
	 * Method returns real part of the complex number.
	 * 
	 * @return real part of complex number.
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * Method returns imaginary part of the complex number.
	 * 
	 * @return imaginary part of complex number.
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	
	/**
	 * Method returns magnitude of the complex number using the known mathematical formula.
	 * 
	 * @return magnitude of the complex number.
	 */
	public double getMagnitude() {
		return Math.abs(Math.sqrt(real*real + imaginary*imaginary));
	}
	
	/**
	 * Method returns an angle of the complex number using <code>Math.tan()</code> method.
	 * Angle depends on the quadrant of the complex number.
	 * 
	 * @return angle of the complex number.
	 */
	public double getAngle() {
		double tmp = Math.atan(imaginary/real);
		
		if (this.getReal() > 0 && this.getImaginary() > 0) return tmp;
		else if (this.getReal() < 0 && this.getImaginary() > 0) return Math.PI + tmp;
		else if (this.getReal() < 0 && this.getImaginary() < 0) return Math.PI + tmp;
		else  return 2*Math.PI + tmp;
	}
	
	/**
	 * Method adds two complex numbers.
	 * 
	 * @param c complex number that is added to this complex number.
	 * @return result of addition as a new instance of complex number.
	 */
	public ComplexNumber add(ComplexNumber c) {
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		num.real = this.real + c.real;
		num.imaginary = this.imaginary + c.imaginary;
		return num;
	}
	
	/**
	 * Method subtracts two complex numbers.
	 * 
	 * @param c complex number that is subtracted from this complex number.
	 * @return result of subtraction as a new instance of complex number.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		num.real = this.real - c.real;
		num.imaginary = this.imaginary - c.imaginary;
		return num;
	}
	
	/**
	 * Method multiplies two complex numbers.
	 * 
	 * @param c complex number that is multiplied to this complex number.
	 * @return result of multiplication as a new instance of complex number.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		num.real = this.real*c.real - this.imaginary*c.imaginary;
		num.imaginary = this.real*c.imaginary + this.imaginary*c.real;
		return num;
	}
	
	/**
	 * Method divides two complex numbers.
	 * 
	 * @param c complex number that is divided from this complex number.
	 * @return result of division as a new instance of complex number.
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		num.real = (this.real*c.real + this.imaginary*c.imaginary)/(c.real*c.real + c.imaginary*c.imaginary);
		num.imaginary = (this.imaginary*c.real - this.real*c.imaginary)/(c.real*c.real + c.imaginary*c.imaginary);
		return num;
	}
	
	/**
	 * Method raises this complex number to the power given in argument.
	 * 
	 * @param n exponent.
	 * @return this complex number raised to the power of n as a new complex number.
	 */
	public ComplexNumber power(int n) {
		double th = this.getAngle();
		double mag = Math.pow(this.getMagnitude(), n);
		
		ComplexNumber num = new ComplexNumber(0.0, 0.0);
		
		num.real = mag*Math.cos(n*th);
		num.imaginary = mag*Math.sin(n*th);
		
		return num;
	}
	
	/**
	 * Method calculates all the n-roots of the given complex number.
	 * 
	 * @param n number of roots.
	 * @return array of n roots of the complex number.
	 */
	public ComplexNumber[] root(int n) {
		ComplexNumber[] roots = new ComplexNumber[n];
		
		double mag = Math.pow(this.getMagnitude(), 1/n);
		double ang = this.getAngle();
		
		for (int i = 0; i < n; i++) {
			ComplexNumber num = new ComplexNumber(0.0, 0.0);
			
			num.real = mag*Math.cos((ang + 2*i*Math.PI)/n);
			num.imaginary = mag*Math.sin((ang + 2*i*Math.PI)/n);
			
			roots[i] = num;
		}
		
		return roots;
	}
	
	/**
	 * Method writes complex number in <code>String</code> format.
	 * 
	 * @return complex number as <code>String</code>.
	 */
	public String toString() {
		String s = new String();
		if (this.getReal() != 0.0) {
			s = Double.toString(this.getReal());
		}
		
		if (this.getImaginary() != 0.0) {
			if (this.getImaginary() > 0) {
				s = s + "+";
			}
			if (this.getImaginary() != 1.0 && this.getImaginary() != -1.0) {
				s = s + Double.toString(this.getImaginary());
			} else if (this.getImaginary() == -1.0) {
				s = s + "-";
			}
			s = s + "i";
		}
		
		return s;
	}
}
