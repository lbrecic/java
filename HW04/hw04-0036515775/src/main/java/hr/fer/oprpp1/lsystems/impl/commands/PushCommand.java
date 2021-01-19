package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code PushCommand} provides instructions for pushing 
 * copy of current state on top of context stack.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState().copy();
		ctx.pushState(tmp);
	}

}
