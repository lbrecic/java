package hr.fer.oprpp1.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class {@code Context} represents stack of turtle states.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Context {
	
	ObjectStack<TurtleState> stack;
	
	/**
	 * Default constructor.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}
	
	/**
	 * Method gets current turtle state from top of stack
	 * without removing it.
	 * 
	 * @return current turtle state
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Method puts new turtle state on top of stack.
	 * 
	 * @param state turtle state that is put on top of stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Method removes one turtle state from top of stack.
	 */
	public void popState() {
		stack.pop();
	}
}
