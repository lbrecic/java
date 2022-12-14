package hr.fer.oprpp1.fractals;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class {@code Newton} provides work with Newton-Raphson fractals.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Newton {
	
	private static ComplexRootedPolynomial rooted;
	private static ComplexPolynomial polynomial;
	private static ComplexPolynomial derived;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int i = 1;
		
		Complex[] roots = new Complex[1];
		Complex[] tmp;
		
		String next;
		System.out.print("Root " + i + "> ");
		next = sc.nextLine();
		while (!next.equals("done")){
			if (i != 1) {
				tmp = roots;
				roots = new Complex[i];
				
				for (int j = 0; j < tmp.length; j++) {
					roots[j] = tmp[j];
				}
			}
			
			Complex c = parse(next);
			roots[roots.length - 1] = c;
			
			i += 1;
			System.out.print("Root " + i + "> ");
			next = sc.nextLine();
		}
		
		sc.close();
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		rooted = new ComplexRootedPolynomial(Complex.ONE, roots);
		polynomial = rooted.toComplexPolynom();
		derived = polynomial.derive();
		
		FractalViewer.show(new MojProducer());	
	}

	public static class MojProducer implements IFractalProducer {
		
		@Override
		/**
		 * Method iterates with Newton-Rhapson iteration.
		 */
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex c = new Complex(cre, cim);
					Complex zn = c;
					Complex znold;
					double module = 0;
					int iters = 0;
					do {
						Complex numerator = polynomial.apply(zn); 
						Complex denominator = derived.apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(iters < m && module > 1E-3);
					int index = rooted.indexOfClosestRootFor(zn, 2E-3);
					data[offset] = (short) (index + 1);
					offset++;
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
	
	/**
	 * Method parses strings of complex number provided by user
	 * 
	 * @param s string representation of complex number
	 * @return complex number
	 * @throws IllegalArgumentException
	 */
	public static Complex parse(String s) {
		String[] str = s.split(" ");
		StringBuilder sb = new StringBuilder();
		sb.append("");
		boolean img = false;
		
		switch (str.length) {
		case 1:
			for (int i = 0; i < str[0].length(); i++) {
				if (str[0].charAt(i) == 'i') {
					img = true;
				} else {
					sb.append(str[0].charAt(i));
				}
			}
			
			if (img) {
				if (sb.toString().equals("") || sb.toString().equals("-")) {
					sb.append("1");
				}
				return new Complex(0, Double.parseDouble(sb.toString()));
			} else {
				return new Complex(Double.parseDouble(sb.toString()), 0);
			}

		case 3:
			double re = Double.parseDouble(str[0]);
			double im;
			
			if (str[1] == "-") sb.append(str[1]);
			
			if (str[2].equals("i")) {
				sb.append("1");
			} else {
				sb.append(str[2].substring(1));
			}
			
			im = Double.parseDouble(sb.toString());
			return new Complex(re, im);

		default:
			throw new IllegalArgumentException();
		}
	}
}
