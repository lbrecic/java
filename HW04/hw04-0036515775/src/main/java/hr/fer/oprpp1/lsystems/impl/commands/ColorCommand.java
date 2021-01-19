package hr.fer.oprpp1.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code ColorComand} provides instructions for changing the color of the line.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ColorCommand implements Command{
	Color color;
	
	/**
	 * Default constructor.
	 * 
	 * @param color 
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setCurrentColor(color);
	}

}
