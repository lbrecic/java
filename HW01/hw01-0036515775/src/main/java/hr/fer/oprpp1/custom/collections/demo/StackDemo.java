package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class <code>StackDemo</code> checks functionality of class <code>ObjectStack</code>.
 */
public class StackDemo {
	/**
	 * Main method calculates postfix expression using ObjectStack instance of stack.
	 * 
	 * @param args postfix expression written in <code>String</code> format.
	 * @throws IllegalArgumentException if expression is written in incorrect format.
	 */
	public static void main(String[] args) {
		String[] s = args[0].split(" ");
		ObjectStack stack = new ObjectStack();
		
		int a, b;
		
		for (int i = 0; i < s.length; i++) {
			if (!s[i].equals("+") && !s[i].equals("-") && !s[i].equals("*") && !s[i].equals("/") && !s[i].equals("%")) {
				Integer n = Integer.parseInt(s[i]);
				stack.push(n);
			} else {
				b = (Integer) stack.pop();
				a = (Integer) stack.pop();

				if (b == 0) throw new IllegalArgumentException();
				
				switch(s[i]) {
					case "+":
						stack.push(a + b);
						break;
					case "-":
						stack.push(a - b);
						break;
					case "*":
						stack.push(a * b);
						break;
					case "/":
						stack.push(a / b);
						break;
					case "%":
						stack.push(a % b);
						break;
					default:
						throw new IllegalArgumentException();
				}
			}
		}
		
		if (stack.size() != 1) { System.err.println("error"); }
		else { System.out.println("Expression evaluates to " + stack.pop() + "."); }
	}
}
