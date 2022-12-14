package hr.fer.oprpp1.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

public class UnaryOperatorButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private DoubleUnaryOperator op;
	private DoubleUnaryOperator inv;
	private String oper;
	private String invoper;
	
	public UnaryOperatorButton(String oper, String invoper, DoubleUnaryOperator op, DoubleUnaryOperator inv) {
		super(oper);
		this.oper = oper;
		this.invoper = invoper;
		this.op = op;
		this.inv = inv;
		setBackground(Color.GREEN);
	}
	
	public UnaryOperatorButton(String oper, DoubleUnaryOperator op) {
		this(oper, oper, op, op);
	}

	public DoubleUnaryOperator getOp() {
		return op;
	}
	
	public DoubleUnaryOperator getInv() {
		return inv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.getText().equals(oper)) {
			this.setText(invoper);
		} else {
			this.setText(oper);
		}
	}
	
}
