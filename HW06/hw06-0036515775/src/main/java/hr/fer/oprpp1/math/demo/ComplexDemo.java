package hr.fer.oprpp1.math.demo;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;

public class ComplexDemo {
	
	public static void main(String[] args) {
		
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
		);
		
		ComplexPolynomial cp = crp.toComplexPolynom();
		
		System.out.println(crp.toString());
		System.out.println(cp.toString());
		System.out.println(cp.derive().toString());
	}
}
