package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code ScaleCommand} provides instructions for scaling
 * current state of Turtle.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ScaleCommand implements Command {
	double factor;
	
	/**
	 * Default constructor.
	 * 
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setCurrentShift(ctx.getCurrentState().getCurrentShift() * factor);
	}

}
