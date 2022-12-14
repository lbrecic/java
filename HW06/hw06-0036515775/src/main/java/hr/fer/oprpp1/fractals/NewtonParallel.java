package hr.fer.oprpp1.fractals;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.oprpp1.math.Complex;
import hr.fer.oprpp1.math.ComplexPolynomial;
import hr.fer.oprpp1.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class {@code NewtonParallel} provides user multi-threaded work with Newton-Rhapson fractals.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class NewtonParallel {
	
	private static int N = Runtime.getRuntime().availableProcessors();
	private static int K = 4 * Runtime.getRuntime().availableProcessors();
	
	private static ComplexRootedPolynomial rooted;
	private static ComplexPolynomial polynomial;
	private static ComplexPolynomial derived;
	
	public static void main(String[] args) {
		if (args.length > 0) {
			NewtonParallel.parseArgs(args);
		}
		
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

	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
			NewtonParallel.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
			
		}
	}
	
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka = K > reMax ? 4 * Runtime.getRuntime().availableProcessors() : K;
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[N];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
	
	public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int m, int yMin, int yMax, short[] data, AtomicBoolean cancel) {
		int offset = 0;
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
	
	/**
	 * Method parses strings of arguments provided by user
	 * 
	 * @param s string array of user arguments
	 * @throws IllegalArgumentException
	 */
	public static void parseArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--workers=")) {
				String s = args[i].substring(10);
				N = Integer.parseInt(s);
			} else if (args[i].startsWith("--tracks=")) {
				String s = args[i].substring(9);
				K = Integer.parseInt(s);
			} else if (args[i].startsWith("-w")) {
				N = Integer.parseInt(args[i + 1]);
				i += 1;
			} else if (args[i].startsWith("-t")) {
				K = Integer.parseInt(args[i + 1]);
				i += 1;
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
}
