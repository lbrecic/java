package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code SkipCommand} provides instructions for skipping next command
 * but updates context.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SkipCommand implements Command {
	double step;
	
	/**
	 * Default constructor.
	 * 
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		Vector2D next = tmp.getCurrentPosition().added(tmp.getCurrentDirection().scaled(tmp.getCurrentShift() * step));
		ctx.getCurrentState().setCurrentPosition(next);
	}

}
