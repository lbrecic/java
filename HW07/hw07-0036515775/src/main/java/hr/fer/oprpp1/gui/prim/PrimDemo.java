package hr.fer.oprpp1.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private PrimListModel model;
	
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(300, 300);
		
		initGUI();
	}
	
	public void initGUI() {
		model = new PrimListModel();
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		list1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JScrollPane(list1));
		p.add(new JScrollPane(list2));
		
		cp.add(p, BorderLayout.CENTER);
		
		JButton sljedeci = new JButton("sljedeci");
		cp.add(sljedeci, BorderLayout.PAGE_END);
		sljedeci.addActionListener(l -> {
			model.next();
		});
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
