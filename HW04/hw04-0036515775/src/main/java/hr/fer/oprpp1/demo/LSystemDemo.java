package hr.fer.oprpp1.demo;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;

public class LSystemDemo {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(new LSystem() {
			@Override
			public String generate(int level) {
				return""; // totalno ignoriramo u ovom primjeru...
			}

			@Override
			public void draw(int level, Painter painter) {
				painter.drawLine(0.1, 0.1, 0.9, 0.1, Color.RED, 1f);
				painter.drawLine(0.9, 0.1, 0.9, 0.9, Color.GREEN, 1f);
				painter.drawLine(0.9, 0.9, 0.1, 0.1, Color.BLUE, 1f);
			}
		});
	}
}