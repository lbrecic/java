package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel model;
	private MultipleDocumentListener mdl;

	public JNotepadPP() throws IOException {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int result = JOptionPane.DEFAULT_OPTION;
				if (model.getNumberOfDocuments() != 0) {
					for (SingleDocumentModel sdm : model.getDocuments()) {
						if (sdm.isModified()) {
							result = JOptionPane.showOptionDialog(getParent(),
									"There are unsaved documents!" + "Are you sure you want to exit the program?",
									"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
									null, JOptionPane.NO_OPTION);
						}
					}
				}

				switch (result) {
				case JOptionPane.DEFAULT_OPTION:
					dispose();
					break;
				case JOptionPane.YES_OPTION:
					dispose();
					break;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					break;
				}
			}

		});

		setTitle("JNotepad++");
		setSize(700, 700);
		setLocation(300, 300);
		setLayout(new BorderLayout());

		initGUI();
	}

	public void initGUI() throws IOException {
		Container cp = getContentPane();

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu();
		menu.setText("Menu");
		addMenuButtons(menu);
		bar.add(menu);
		this.setJMenuBar(bar);

		JToolBar tool = new JToolBar();
		addToolButtons(tool);
		cp.add(tool, BorderLayout.PAGE_START);

		model = new DefaultMultipleDocumentModel();
		model.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				model.setCurrent(model.getDocuments().get(model.getSelectedIndex()));

				if (model.getCurrent().getFilePath() == null) {
					setTitle("unnamed");
				} else {
					setTitle(model.getCurrent().getFilePath().getFileName().toString());
				}
			}

		});
		mdl = new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel.getFilePath() == null) {
					setTitle("unnamed");
				} else {
					setTitle(currentModel.getFilePath().getFileName().toString());
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {}

			@Override
			public void documentRemoved(SingleDocumentModel model) {}
			
		};
		model.addMultipleDocumentListener(mdl);
		cp.add((JTabbedPane) model, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new JNotepadPP().setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			;
		});
	}

	public void addMenuButtons(JMenu menu) {
		JButton btn = new JButton();
		btn.setText("New");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		});

		menu.add(btn);

		btn = new JButton();
		btn.setText("Open");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					model.loadDocument(Paths.get(chooser.getSelectedFile().getPath()));
				}

			}
		});

		menu.add(btn);

		btn = new JButton();
		btn.setText("Save");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().getFilePath() == null) {
					JFileChooser chooser = new JFileChooser();

					int returnVal = chooser.showOpenDialog(getParent());
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						model.saveDocument(model.getCurrentDocument(), Paths.get(chooser.getSelectedFile().getPath()));
					}
				} else {
					model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
				}

			}
		}); 

		menu.add(btn);

		btn = new JButton();
		btn.setText("Save as");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					model.saveDocument(model.getCurrentDocument(), Paths.get(chooser.getSelectedFile().getPath()));
				}
			}
		});

		menu.add(btn);

	}

	public void addToolButtons(JToolBar tool) {

	}

}
