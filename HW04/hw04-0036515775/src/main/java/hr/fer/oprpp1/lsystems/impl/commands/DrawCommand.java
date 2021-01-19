package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code DrawCommand} provides instructions for drawing on screen
 * aware of current context.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class DrawCommand implements Command {
	double step;
	
	/**
	 * Default constructor.
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		Vector2D next = tmp.getCurrentPosition().added(tmp.getCurrentDirection().scaled(tmp.getCurrentShift() * step));
		painter.drawLine(tmp.getCurrentPosition().getX(), tmp.getCurrentPosition().getY(),
							next.getX(), next.getY(), tmp.getCurrentColor(), 1f);
		ctx.getCurrentState().setCurrentPosition(next);
	}

}
