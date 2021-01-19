package hr.fer.oprpp1.gui.calc;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

public class BinaryOperatorButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private DoubleBinaryOperator op;
	private DoubleBinaryOperator inv;
	
	public BinaryOperatorButton(String oper, DoubleBinaryOperator op, DoubleBinaryOperator inv) {
		super(oper);
		this.op = op;
		this.inv = inv;
		setBackground(Color.GREEN);
	}
	
	public BinaryOperatorButton(String oper, DoubleBinaryOperator op) {
		this(oper, op, null);
	}

	public DoubleBinaryOperator getOp() {
		return op;
	}
	
	public DoubleBinaryOperator getInv() {
		return inv;
	}
	
}
