package hr.fer.oprpp1.math;

/**
 * Class {@code ComplexRootedPolynomial} provides user work with complex rooted polynomials.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	public Complex[] roots;
	
	/**
	 * Default constructor.
	 * 
	 * @param constant of this polynomial
	 * @param roots of this polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = new Complex[roots.length];
		
		for (int i = 0; i < roots.length; i++) {
			this.roots[i] = roots[i];
		}
	}
	
	/**
	 * Method computes polynomial value at given point z.
	 * 
	 * @param z complex number for which polynomial value is computed
	 * @return polynomial value at given point
	 */
	public Complex apply(Complex z) {
		Complex app = this.constant;
		
		for (int i = 0; i < this.roots.length; i++) {
			app = app.multiply((z.sub(this.roots[i])));
		}
		
		return app;
	}
	
	/**
	 * Method converts this representation to ComplexPolynomial type.
	 * 
	 * @return this rooted polynomial as ordinary complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] c = new Complex[1];
		c[0] = this.constant;
		ComplexPolynomial cp = new ComplexPolynomial(c);
		
		Complex[] cmp = new Complex[2];
		cmp[1] = Complex.ONE;
		for (int i = 0; i < this.roots.length; i++) {
			cmp[0] = this.roots[i].negate();
			
			cp = cp.multiply(new ComplexPolynomial(cmp));
		}
		
		return cp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.constant.toString());
		
		for (int i = 0; i < this.roots.length; i++) {
			sb.append("*(z-" + this.roots[i].toString() + ")");
		}
		
		return sb.toString();
	}
	
	/**
	 * Method finds index of closest root for given complex number z that is whitin 
	 * treshold. if there is no such root, returns -1 first root has index 0, second index 1, etc.
	 * 
	 * @param z complex number for which closest index is computed
	 * @param treshold 
	 * @return {@code index} of closest root or {@code -1} if there is no such root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		for (int i = 0; i < this.roots.length; i++) {
			if ((z.sub(this.roots[i])).module() < treshold) {
				return i;
			}
		}
		
		return -1;
	}
	
}
