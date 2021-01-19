package hr.fer.oprpp1.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.lsystems.impl.commands.ColorCommand;
import hr.fer.oprpp1.lsystems.impl.commands.DrawCommand;
import hr.fer.oprpp1.lsystems.impl.commands.PopCommand;
import hr.fer.oprpp1.lsystems.impl.commands.PushCommand;
import hr.fer.oprpp1.lsystems.impl.commands.RotateCommand;
import hr.fer.oprpp1.lsystems.impl.commands.ScaleCommand;
import hr.fer.oprpp1.lsystems.impl.commands.SkipCommand;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class {@code LSystemBuilderImpl} is implementation of {@code LSystemBuilder} class.
 * Provides work with drawing L - systems.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	 
	private Dictionary<Character, Command> commands;
	private Dictionary<Character, String> productions;
	private String axiom;
	private double angle, unitLength, unitLengthDegreeScaler;
	private Vector2D origin;
	
	/**
	 * Class {@code LSystemImpl} is implementation of {@code LSystem} class.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private class LSystemImpl implements LSystem {
		String gen;
		Context ctx;
		TurtleState turtle;

		@Override
		public void draw(int arg0, Painter arg1) {
			ctx = new Context();
			Vector2D dir = new Vector2D(1, 0);
			dir.rotate(angle);
			turtle = new TurtleState(origin, dir,
										Color.black, unitLength*Math.pow(unitLengthDegreeScaler, arg0));
			ctx.pushState(turtle);
			gen = generate(arg0);
			
			for (int i = 0; i < gen.length(); i++) {
				if (commands.containsKey(gen.charAt(i))) {
					commands.get(gen.charAt(i)).execute(ctx, arg1);
				}
			}
			
		}

		@Override
		public String generate(int arg0) {
			String s = "";
			
			for (int i = 0; i <= arg0; i++) {
				if (i == 0) {
					s = axiom;
				} else {
					StringBuilder sb = new StringBuilder();
					
					for (int j = 0; j < s.length(); j++) {
						if (productions.containsKey(s.charAt(j))) {
							sb.append(productions.get(s.charAt(j)));
						} else {
							sb.append(s.charAt(j));
						}
					}
					
					s = sb.toString();
				}
			}
			
			return s;
		}
		
	}
	
	/**
	 * Default empty constructor.
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary<Character, Command>();
		productions = new Dictionary<Character, String>();
		setAngle(0);
		setAxiom("");
		setOrigin(0, 0);
		setUnitLength(0.1);
		setUnitLengthDegreeScaler(1);
	}
	

	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (int i = 0; i < arg0.length; i++) {
			if (!arg0[i].equals("")) {
				String[] s = arg0[i].split("\\s+");
				
				
				switch (s[0]) {
				case "origin":
					setOrigin(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
					break;
				case "angle":
					setAngle(Double.parseDouble(s[1]));
					break;
				case "unitLength":
					setUnitLength(Double.parseDouble(s[1]));
					break;
				case "unitLengthDegreeScaler":
					if (2 >= s.length || !s[2].equals("/")) {
						setUnitLengthDegreeScaler(Double.parseDouble(s[1]));
					} else {
						setUnitLengthDegreeScaler(Double.parseDouble(s[1]) / Double.parseDouble(s[3]));
					}
					break;
				case "command":
					if (s.length == 4) registerCommand(s[1].charAt(0), s[2] + " " + s[3]);
					if (s.length == 3) registerCommand(s[1].charAt(0), s[2]);
					break;
				case "axiom":
					setAxiom(s[1]);
					break;
				case "production":
					registerProduction(s[1].charAt(0), s[2]);
					break;
				}
			}
		}
		
		
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] s = arg1.split(" ");
		switch (s[0]) {
		case "draw":
			commands.put(arg0, new DrawCommand(Double.parseDouble(s[1])));
			break;
		case "rotate":
			commands.put(arg0, new RotateCommand((Double.parseDouble(s[1])*Math.PI)/180));
			break;
		case "skip":
			commands.put(arg0, new SkipCommand(Double.parseDouble(s[1])));
			break;
		case "scale":
			commands.put(arg0, new ScaleCommand(Double.parseDouble(s[1])));
			break;
		case "push":
			commands.put(arg0, new PushCommand());
			break;
		case "pop":
			commands.put(arg0, new PopCommand());
			break;
		case "color":
			commands.put(arg0, new ColorCommand(Color.decode("#" + s[1])));
			break;
		}
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0*Math.PI/180;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}

}
