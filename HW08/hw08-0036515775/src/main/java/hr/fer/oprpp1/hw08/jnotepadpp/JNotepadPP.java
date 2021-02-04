package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private MultipleDocumentListener mdl;
	private Clipboard clpbrd;
	private ChangeListener listener;
	private Timer t;
	private DocumentListener l;

	public JNotepadPP() throws IOException {
		clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				int result = JOptionPane.DEFAULT_OPTION;
				if (docModel.getNumberOfDocuments() != 0) {
					for (SingleDocumentModel sdm : docModel.getDocuments()) {
						if (sdm.isModified()) {
							result = JOptionPane.showOptionDialog(getParent(),
									"There are unsaved documents! Are you sure you want to exit the program?",
									"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
									null, JOptionPane.NO_OPTION);
							break;
						}
					}
				}

				switch (result) {
				case JOptionPane.DEFAULT_OPTION:
					t.cancel();
					dispose();
					break;
				case JOptionPane.YES_OPTION:
					t.cancel();
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
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		initGUI();
	}

	public void initGUI() throws IOException {
		Container cp = getContentPane();

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout());
		panel2.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label1 = new JLabel();
		label1.setText(" length: ");
		label1.setHorizontalTextPosition(SwingConstants.LEFT);
		panel2.add(label1, BorderLayout.WEST);

		JLabel label2 = new JLabel();
		label2.setText(" Ln: " + " Col: " + " Sel: ");
		label2.setHorizontalTextPosition(SwingConstants.LEFT);
		panel2.add(label2, BorderLayout.CENTER);

		JLabel label3 = new JLabel();
		label3.setAlignmentX(SwingConstants.RIGHT);
		label3.setHorizontalTextPosition(SwingConstants.RIGHT);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				label3.setText(LocalDateTime.now().format(formatter));

			}
		}, 0, 1000);

		panel2.add(label3, BorderLayout.EAST);

		cp.add(panel2, BorderLayout.PAGE_END);
		
		createActions();

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu();
		menu.setText("File");
		addFileMenuItems(menu);
		menu.setMnemonic(KeyEvent.VK_F);
		bar.add(menu);
		this.setJMenuBar(bar);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		
		JToolBar tool = new JToolBar();
		addToolBarItems(tool);
		
		panel1.add(tool, BorderLayout.PAGE_START);

		docModel = new DefaultMultipleDocumentModel();

		l = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				label1.setText(" length: " + 
						docModel.getCurrentDocument().getTextComponent().getText().length()
				);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				label1.setText(" length: " + 
						docModel.getCurrentDocument().getTextComponent().getText().length()
				);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				label1.setText(" length: " + 
						docModel.getCurrentDocument().getTextComponent().getText().length()
				);
			}
		};

		listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (docModel.getDocuments().size() > 0) {
					docModel.setCurrent(docModel.getDocument(docModel.getSelectedIndex()));

					if (docModel.getCurrent().getFilePath() == null) {
						setTitle("unnamed");
					} else {
						setTitle(docModel.getCurrent().getFilePath().getFileName().toString());
					}
				} else {
					setTitle("JNotepad++");
				}

				docModel.getCurrentDocument().getTextComponent().getDocument().addDocumentListener(l);
				docModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						int sel = 0;
						if (docModel.getCurrentDocument().getTextComponent().getSelectedText() != null) {
							sel = docModel.getCurrentDocument().getTextComponent().getSelectedText().length();
						}

						int ln = 1, col = 1;
						try {
							int caretpos = docModel.getCurrentDocument().getTextComponent().getCaretPosition();
							ln = docModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretpos);
							col = caretpos - docModel.getCurrentDocument().getTextComponent().getLineStartOffset(ln);
							ln += 1;
							col += 1;

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						label2.setText(" Ln: " + ln + " Col: " + col + " Sel: " + sel);
					}

				});
			}

		};

		docModel.addChangeListener(listener);

		mdl = new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel.getFilePath() == null) {
					setTitle("unnamed");
				} else {
					setTitle(currentModel.getFilePath().getFileName().toString());
				}
				tooltips();
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				int index = 0;
				for (SingleDocumentModel m : docModel.getDocuments()) {
					if (m != model) {
						index++;
					} else {
						break;
					}
				}
				docModel.setSelectedIndex(index);

				if (model.getFilePath() == null) {
					setTitle("unnamed");
				} else {
					setTitle(model.getFilePath().getFileName().toString());
				}
				tooltips();
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				int index = 0;
				for (SingleDocumentModel m : docModel.getDocuments()) {
					if (m != model) {
						index++;
					} else {
						break;
					}
				}

				int next = index - 1;
				if (docModel.getDocuments().size() - 1 > 0) {
					if (index == 0)
						next = index + 1;
					docModel.setSelectedIndex(next);
				} else {
					docModel.setCurrent(null);
				}

				docModel.removeTabAt(index);
				docModel.getDocuments().remove(index);
				tooltips();

			}

		};

		docModel.addMultipleDocumentListener(mdl);

		panel1.add((JTabbedPane) docModel, BorderLayout.CENTER);

		cp.add(panel1, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new JNotepadPP().setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void tooltips() {
		for (int index = 0; index < docModel.getDocuments().size(); index++) {
			if (docModel.getDocument(index).getFilePath() == null) {
				docModel.setToolTipTextAt(index, "unnamed");
			} else {
				docModel.setToolTipTextAt(index, docModel.getDocument(index).getFilePath().toString());
			}
		}
	}
	
	private Action newDocumentAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			docModel.createNewDocument();
		}
		
	};
	
	private Action openDocumentAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();

			int returnVal = chooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				docModel.loadDocument(Paths.get(chooser.getSelectedFile().getPath()));
			}
		}
		
	};
	
	private Action saveDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (docModel.getCurrentDocument() != null) {
				if (docModel.getCurrentDocument().getFilePath() == null) {
					JFileChooser chooser = new JFileChooser();

					int returnVal = chooser.showSaveDialog(getParent());
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						docModel.saveDocument(docModel.getCurrentDocument(),
								Paths.get(chooser.getSelectedFile().getPath()));
					}
				} else {
					docModel.saveDocument(docModel.getCurrentDocument(),
							docModel.getCurrentDocument().getFilePath());
				}
			}
		}
		
	};
	
	private Action saveAsDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (docModel.getCurrentDocument() != null) {
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					docModel.saveDocument(docModel.getCurrentDocument(),
							Paths.get(chooser.getSelectedFile().getPath()));
				}
			}
		}
		
	};
	
	private Action closeDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (docModel.getCurrentDocument() != null) {
				docModel.closeDocument(docModel.getCurrentDocument());
			}
		}
		
	};
	
	private Action infoDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (docModel.getCurrentDocument() != null) {

				int x = 0, y = 0, z = 0; // x = chars, y = non_blank, z = lines

				for (String line : docModel.getCurrentDocument().getTextComponent().getText().split("\n")) {
					z++;
					x += line.length();
					line += '\n';
					for (int i = 0; i < line.length(); i++) {
						if (!Character.isWhitespace(line.charAt(i))) {
							y++;
						}
					}

				}
				x += z - 1;

				JOptionPane
						.showMessageDialog(
								getParent(), "Your document has " + x + " characters, " + y
										+ " non-blank characters and " + z + " lines.",
								"Statistical info", JOptionPane.INFORMATION_MESSAGE);

			}
		}
		
	};
	
	private Action exitDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.DEFAULT_OPTION;
			if (docModel.getNumberOfDocuments() != 0) {
				for (SingleDocumentModel sdm : docModel.getDocuments()) {
					if (sdm.isModified()) {
						result = JOptionPane.showOptionDialog(getParent(),
								"There are unsaved documents! Are you sure you want to exit the program?",
								"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
								null, JOptionPane.NO_OPTION);
						break;
					}
				}
			}

			switch (result) {
			case JOptionPane.DEFAULT_OPTION:
				t.cancel();
				dispose();
				break;
			case JOptionPane.YES_OPTION:
				t.cancel();
				dispose();
				break;
			case JOptionPane.NO_OPTION:
				break;
			case JOptionPane.CANCEL_OPTION:
				break;
			}
		}
		
	};
	
	private Action cutAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			clpbrd.setContents(selected, null);
			docModel.getCurrentDocument().getTextComponent()
					.setText(docModel.getCurrentDocument().getTextComponent().getText()
							.replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), ""));
		}
		
	};
	
	private Action copyAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			clpbrd.setContents(selected, null);
		}
		
	};
	
	private Action pasteAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Transferable t = clpbrd.getContents(this);
			if (t != null) {
				try {
					docModel.getCurrentDocument().getTextComponent().insert(
							(String) t.getTransferData(DataFlavor.stringFlavor),
							docModel.getCurrentDocument().getTextComponent().getCaretPosition());
				} catch (UnsupportedFlavorException | IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	};
	
	
	private void createActions() {
		newDocumentAction.putValue(Action.NAME, "New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Creates new document.");
		
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Opens existing document.");
		
		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves document.");
		
		saveAsDocumentAction.putValue(Action.NAME, "Save As");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves as another document.");
		
		closeDocumentAction.putValue(Action.NAME, "Close");
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Closes current document.");
		
		infoDocumentAction.putValue(Action.NAME, "Info");
		infoDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		infoDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Statistical information of current document.");
		
		exitDocumentAction.putValue(Action.NAME, "Exit");
		exitDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Exit program.");
		
		cutAction.putValue(Action.NAME, "Cut");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Cut operation.");
		
		copyAction.putValue(Action.NAME, "Copy");
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copy operation.");
		
		pasteAction.putValue(Action.NAME, "Paste");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Paste operation.");
	}
	
	private void addFileMenuItems(JMenu menu) {
		menu.add(new JMenuItem(newDocumentAction));
		menu.add(new JMenuItem(openDocumentAction));
		menu.add(new JMenuItem(saveDocumentAction));
		menu.add(new JMenuItem(saveAsDocumentAction));
		menu.add(new JMenuItem(closeDocumentAction));
		menu.addSeparator();
		menu.add(new JMenuItem(exitDocumentAction));
	}
	
	private void addToolBarItems(JToolBar tool) {
		tool.add(new JButton(cutAction));
		tool.add(new JButton(copyAction));
		tool.add(new JButton(pasteAction));
	}

}
