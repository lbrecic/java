package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code RotateCommand} provides instructions for rotating 
 * turtle thus changing its orientation.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class RotateCommand implements Command {
	double angle;
	
	/**
	 * Default constructor.
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle =  angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getCurrentDirection().rotate(angle);
	}

}
