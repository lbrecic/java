package hr.fer.oprpp1.math;

/**
 * Class {@code ComplexPolynomial} provides user work with ordinary complex polynomials.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ComplexPolynomial {
	Complex[] factors;
	
	/**
	 * Default constructor.
	 * 
	 * @param complex constants of polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = new Complex[factors.length];
		
		for (int i = 0; i < factors.length; i++) {
			this.factors[i] = factors[i];
		}
	}
	
	/**
	 * Method returns order of this polynom.
	 * 
	 * @return order of this polynom
	 */
	public short order() {
		return ((short)(this.factors.length - 1));
	}
	
	/**
	 * Method computes a new polynomial by multiplying with another.
	 * 
	 * @param p complex polynomial by which this complex plynomial is multplied.
	 * @return new polynomial that is computed
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int n = this.order() + p.order();
		Complex[] c = new Complex[n + 1];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = Complex.ZERO;
		}
		
		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				Complex tmp = this.factors[i].multiply(p.factors[j]);
				c[i + j] = c[i + j].add(tmp);
			}
		}
		
		return new ComplexPolynomial(c);
	}
	
	/**
	 * Method computes first derivative of this polynomial.
	 * 
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] factors = new Complex[this.factors.length - 1];
		
		for (int i = 1; i < this.factors.length; i++) {
			factors[i - 1] = this.factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(factors);
	}
	
	/**
	 * Method computes polynomial value at given point z.
	 * 
	 * @param z point for which polynomial value is computed
	 * @return polynomial value for point z
	 */
	public Complex apply(Complex z) {
		Complex c = new Complex();
		
		for (int i = 0; i < this.factors.length; i++) {
			c = c.add(this.factors[i].multiply(z.power(i)));
		}
		
		return c;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = this.factors.length - 1; i > 0; i--) {
			sb.append(this.factors[i].toString() + "*z^" + i + "+");
		}
		
		sb.append(this.factors[0].toString());
		
		return sb.toString();
	}
	
}