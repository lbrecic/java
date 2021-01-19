package hr.fer.oprpp1.gui.calc;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.oprpp1.gui.calc.model.CalcModel;
import hr.fer.oprpp1.gui.calc.model.CalcValueListener;

public class Display extends JLabel implements CalcValueListener {
	
	private static final long serialVersionUID = 1L;

	public Display(String text) {
		super(text);
		setOpaque(true);
		setBackground(Color.YELLOW);
		setHorizontalAlignment(RIGHT);
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		repaint();
	}

}
