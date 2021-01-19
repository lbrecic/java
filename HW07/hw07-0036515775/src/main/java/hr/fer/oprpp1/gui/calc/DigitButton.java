package hr.fer.oprpp1.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

public class DigitButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private int op;

	public DigitButton(Integer op) {
		super(op.toString());
		this.op = op;
		setBackground(Color.GREEN);
	}

	public int getOp() {
		return op;
	}
	
}
