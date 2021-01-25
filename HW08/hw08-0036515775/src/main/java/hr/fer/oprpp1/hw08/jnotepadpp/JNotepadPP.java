package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class JNotepadPP extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MultipleDocumentModel model;

	public JNotepadPP() {
		//setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		setTitle("JNotepad++");
		setSize(300, 300);
		setLocation(300, 300);
		setLayout(new BorderLayout());
		
		initGUI();
	}
	
	public void initGUI() {
		Container cp = getContentPane();
		JToolBar tool = new JToolBar();
		model = new DefaultMultipleDocumentModel();
		cp.add((JTabbedPane) model, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);;
		});
	}
	
}
