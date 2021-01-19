package hr.fer.oprpp1.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

public class BinaryOperatorButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private DoubleBinaryOperator op;
	private DoubleBinaryOperator inv;
	private String oper;
	private String invoper;
	
	public BinaryOperatorButton(String oper, String invoper, DoubleBinaryOperator op, DoubleBinaryOperator inv) {
		super(oper);
		this.oper = oper;
		this.invoper = invoper;
		this.op = op;
		this.inv = inv;
		setBackground(Color.GREEN);
	}
	
	public BinaryOperatorButton(String oper, DoubleBinaryOperator op) {
		this(oper, "", op, null);
	}

	public DoubleBinaryOperator getOp() {
		return op;
	}
	
	public DoubleBinaryOperator getInv() {
		if (inv == null) return op;
		return inv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!invoper.equals("")) {
			if (this.getText().equals(oper)) {
				this.setText(invoper);
			} else {
				this.setText(oper);
			}
		}
	}
	
}
