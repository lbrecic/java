package hr.fer.oprpp1.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.gui.CalcLayout;
import hr.fer.oprpp1.gui.RCPosition;

public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private CalcModelImpl model;
	
	public Calculator() {
		model = new CalcModelImpl();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 350);
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setFont(new Font(getName(), Font.BOLD, 24));
		cp.setLayout(new CalcLayout(5));
		
		Display display = new Display(model.toString());
		cp.add(display, new RCPosition(1, 1));
		model.addCalcValueListener(display);
		
		JCheckBox inv = new JCheckBox("Invert");
		inv.setBackground(Color.GREEN);
		cp.add(inv, new RCPosition(5, 7));
		
		addButtons(cp);
		
		for (Component comp : cp.getComponents()) {
			if (comp instanceof BinaryOperatorButton) {
				((BinaryOperatorButton) comp).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (model.isActiveOperandSet()) {
							model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));//?
						}
						
						model.setActiveOperand(model.getValue());
						if (inv.isSelected() && ((BinaryOperatorButton) comp).getInv() != null) {
							model.setPendingBinaryOperation(((BinaryOperatorButton) comp).getInv());
						} else {
							model.setPendingBinaryOperation(((BinaryOperatorButton) comp).getOp());
						}
						model.clear();
					}
					
				});
				
				inv.addActionListener((ActionListener)comp);
			} else if (comp instanceof UnaryOperatorButton) {
				((UnaryOperatorButton) comp).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						model.setActiveOperand(model.getValue());
						if (!inv.isSelected()) {
							model.setValue(((UnaryOperatorButton) comp).getOp().applyAsDouble(model.getActiveOperand()));
						} else {
							model.setValue(((UnaryOperatorButton) comp).getInv().applyAsDouble(model.getActiveOperand()));
						}
						model.clear();
					}
					
				});
				
				inv.addActionListener((ActionListener)comp);
			} else if (comp instanceof DigitButton) {
				((DigitButton) comp).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						model.insertDigit(((DigitButton) comp).getOp());
					}
					
				});
			}
		}
		
		JButton clr = new JButton("clr");
		clr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.clear();
			}
			
		});
		clr.setBackground(Color.GREEN);
		cp.add(clr, new RCPosition(1, 7));
		
		JButton res = new JButton("reset");
		res.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearAll();
			}
		});
		res.setBackground(Color.GREEN);
		cp.add(res, new RCPosition(2, 7));
		
		JButton push = new JButton("push");
		push.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.getStack().push(model.getValue());
			}
		});
		push.setBackground(Color.GREEN);
		cp.add(push, new RCPosition(3, 7));
		
		JButton pop = new JButton("pop");
		pop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setValue(model.getStack().pop());
			}
		});
		pop.setBackground(Color.GREEN);
		cp.add(pop, new RCPosition(4, 7));
		
		JButton point = new JButton(".");
		point.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.insertDecimalPoint();
			}
		});
		point.setBackground(Color.GREEN);
		cp.add(point, new RCPosition(5, 5));
		
		JButton swap = new JButton("+/-"); 
		swap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.swapSign();
			}
		});
		swap.setBackground(Color.GREEN);
		cp.add(swap, new RCPosition(5, 4));
		
		JButton equ = new JButton("=");
		equ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
			}
		});
		equ.setBackground(Color.GREEN);
		cp.add(equ, new RCPosition(1, 6));
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	public static void addButtons(Container cp) {
		int n = 1;
		for (int i = 4; i > 1; i--) {
			for (int j = 3; j <= 5; j++) {
				DigitButton db = new DigitButton(n++);
				db.setFont(new Font(db.getFont().getName(), Font.BOLD, 20));
				cp.add(db, new RCPosition(i, j));
			}
		}
		
		cp.add(new DigitButton(0), new RCPosition(5, 3));
		cp.add(new BinaryOperatorButton("+", (a, b) -> {return a + b;}), new RCPosition(5, 6));
		cp.add(new BinaryOperatorButton("-", (a, b) -> {return a - b;}), new RCPosition(4, 6));
		cp.add(new BinaryOperatorButton("*", (a, b) -> {return a * b;}), new RCPosition(3, 6));
		cp.add(new BinaryOperatorButton("/", (a, b) -> {return a / b;}), new RCPosition(2, 6));
		
		cp.add(new BinaryOperatorButton("x^n", "x^(1/n)",(a, b) -> {return Math.pow(a, b);}, (a, b) -> {return Math.pow(a, 1 / b);}), new RCPosition(5, 1));
		
		cp.add(new UnaryOperatorButton("sin", "arcsin",(a) -> {return Math.sin(a);}, (a) -> {return Math.asin(a);}), new RCPosition(2, 2));
		cp.add(new UnaryOperatorButton("cos", "arccos",(a) -> {return Math.cos(a);}, (a) -> {return Math.acos(a);}), new RCPosition(3, 2));
		cp.add(new UnaryOperatorButton("tan", "arctg",(a) -> {return Math.tan(a);}, (a) -> {return Math.atan(a);}), new RCPosition(4, 2));
		cp.add(new UnaryOperatorButton("ctg", "arcctg",(a) -> {return 1 / Math.tan(a);}, (a) -> {return 1 / Math.atan(a);}), new RCPosition(5, 2));
		cp.add(new UnaryOperatorButton("1/x",(a) -> {return 1 / a;}), new RCPosition(2, 1));
		cp.add(new UnaryOperatorButton("log", "10^x",(a) -> {return Math.log10(a);}, (a) -> {return Math.pow(10, a);}), new RCPosition(3, 1));
		cp.add(new UnaryOperatorButton("ln", "e^x",(a) -> {return Math.log(a);}, (a) -> {return Math.pow(Math.E, a);}), new RCPosition(4, 1));
		
	}

}
