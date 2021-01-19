package hr.fer.oprpp1.gui.calc;

import java.awt.Color;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

public class UnaryOperatorButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private DoubleUnaryOperator op;
	private DoubleUnaryOperator inv;
	
	public UnaryOperatorButton(String oper, DoubleUnaryOperator op, DoubleUnaryOperator inv) {
		super(oper);
		this.op = op;
		this.inv = inv;
		setBackground(Color.GREEN);
	}

	public DoubleUnaryOperator getOp() {
		return op;
	}
	
	public DoubleUnaryOperator getInv() {
		return inv;
	}
	
}
