package hr.fer.oprpp1.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface {@code Command} represents command that can be executed.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface Command {
	
	/**
	 * Method provides instructions for given command.
	 * 
	 * @param ctx current context (environment) of command
	 * @param painter instance that executes command
	 */
	void execute(Context ctx, Painter painter);
}
