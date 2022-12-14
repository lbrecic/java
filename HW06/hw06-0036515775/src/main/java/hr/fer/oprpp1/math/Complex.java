package hr.fer.oprpp1.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code Complex} provides user work with complex numbers.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Complex {

	private double re;
	private double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default empty constructor.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor with real and imaginary part od complex number.
	 * 
	 * @param re real part of complex number
	 * @param im imaginary part of complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Method returns module of complex number.
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return Math.abs(Math.sqrt(this.re*this.re + this.im*this.im));
	}

	/**
	 * Method multiplies this complex number with given complex number.
	 * 
	 * @param c second complex number factor of multiplication
	 * @return multiplied complex number
	 */
	public Complex multiply(Complex c) {
		Complex num = new Complex(0.0, 0.0);
		num.re = this.re*c.re - this.im*c.im;
		num.im = this.re*c.im + this.im*c.re;
		return num;
	}

	/**
	 * Method divides this complex number with given complex number.
	 * 
	 * @param c second complex number factor of division
	 * @return divided complex number
	 */
	public Complex divide(Complex c) {
		Complex num = new Complex(0.0, 0.0);
		num.re = (this.re*c.re + this.im*c.im)/(c.re*c.re + c.im*c.im);
		num.im = (this.im*c.re - this.re*c.im)/(c.re*c.re + c.im*c.im);
		return num;
	}

	/**
	 * Method adds given complex number to this complex number.
	 * 
	 * @param c complex number that is added
	 * @return complex number as a result of addition
	 */
	public Complex add(Complex c) {
		Complex num = new Complex(0.0, 0.0);
		num.re = this.re + c.re;
		num.im = this.im + c.im;
		return num;
	}

	/**
	 * Method subtracts given complex number from this complex number.
	 * 
	 * @param c complex number that is subtracted
	 * @return complex number as a result of subtraction
	 */
	public Complex sub(Complex c) {
		Complex num = new Complex(0.0, 0.0);
		num.re = this.re - c.re;
		num.im = this.im - c.im;
		return num;
	}

	/**
	 * Method negates this complex number.
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(this.re * -1, this.im * -1);
	}

	/**
	 * Method returns this complex number power of n.
	 * n is non-negative number.
	 * 
	 * @param n non-negative number
	 * @return power of n complex number
	 */
	public Complex power(int n) {
		double angle;
		double tmp = Math.atan(this.im/this.re);
		
		if (this.getRe() > 0 && this.getIm() > 0) angle = tmp;
		else if (this.getRe() < 0 && this.getIm() > 0) angle = Math.PI + tmp;
		else if (this.getRe() < 0 && this.getIm() < 0) angle = Math.PI + tmp;
		else  angle = 2*Math.PI + tmp;
		
		double mag = Math.pow(this.module(), n);
		
		Complex num = new Complex(0.0, 0.0);
		
		num.re = mag*Math.cos(n*angle);
		num.im = mag*Math.sin(n*angle);
		
		return num;
	}

	/**
	 * Method returns n-th root of this complex number.
	 * 
	 * @param n non-negative number
	 * @return n-th root of this complex number
	 */
	public List<Complex> root(int n) {
		List<Complex> roots = new ArrayList<>();
		double angle;
		double tmp = Math.atan(this.im/this.re);
		
		if (this.getRe() > 0 && this.getIm() > 0) angle = tmp;
		else if (this.getRe() < 0 && this.getIm() > 0) angle = Math.PI + tmp;
		else if (this.getRe() < 0 && this.getIm() < 0) angle = Math.PI + tmp;
		else  angle = 2*Math.PI + tmp;

		double mag = Math.pow(this.module(), 1 / n);

		for (int i = 0; i < n; i++) {
			Complex num = new Complex(0.0, 0.0);

			num.re = mag * Math.cos((angle + 2 * i * Math.PI) / n);
			num.im = mag * Math.sin((angle + 2 * i * Math.PI) / n);

			roots.add(num);
		}

		return roots;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(" + Double.toString(this.re));
		
		if (this.im >= 0.0) {
			sb.append("+i");
		} else {
			sb.append("-i");
		}
		
		sb.append(Double.toString(Math.abs(this.im)) + ")");
		
		return sb.toString();
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

}
