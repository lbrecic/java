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
import javax.swing.text.Caret;

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

		// ---

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

		// ---

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu();
		menu.setText("File");
		addMenuButtons(menu);
		menu.setMnemonic(KeyEvent.VK_F);
		bar.add(menu);
		this.setJMenuBar(bar);

		JToolBar tool = new JToolBar();
		addToolButtons(tool);
		panel1.add(tool, BorderLayout.PAGE_START);

		docModel = new DefaultMultipleDocumentModel();

//		l = new DocumentListener() {
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//
//			}
//
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//
//			}
//		};
		
		// ------------------------------ sto dalje? kako dobiti poziciju careta? 

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
				
				// --------- novo, s caretom

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
							col = docModel.getCurrentDocument().getTextComponent().getLineStartOffset(ln);
							ln += 1;

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						label2.setText(" Ln: " + ln + " Col: " + col + " Sel: " + sel);
					}

				});
				
				// ----------
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

		// nekada ovdje islo panel2 i to ----------

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

	public void addMenuButtons(JMenu menu) {

		JMenuItem btn = new JMenuItem();
		btn.setText("New");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				docModel.createNewDocument();
			}
		});
		btn.setMnemonic(KeyEvent.VK_N);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

		btn = new JMenuItem();
		btn.setText("Open");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					docModel.loadDocument(Paths.get(chooser.getSelectedFile().getPath()));
				}

			}
		});
		btn.setMnemonic(KeyEvent.VK_O);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

		btn = new JMenuItem();
		btn.setText("Save");
		btn.addActionListener(new ActionListener() {

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
		});
		btn.setMnemonic(KeyEvent.VK_S);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

		btn = new JMenuItem();
		btn.setText("Save As");
		btn.addActionListener(new ActionListener() {

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
		});
		btn.setMnemonic(KeyEvent.VK_A);
		btn.setDisplayedMnemonicIndex(5);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

		btn = new JMenuItem();
		btn.setText("Close");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (docModel.getCurrentDocument() != null) {
					docModel.closeDocument(docModel.getCurrentDocument());
				}
			}
		});
		btn.setMnemonic(KeyEvent.VK_L);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

		btn = new JMenuItem();
		btn.setText("Info");
		btn.addActionListener(new ActionListener() {

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
		});
		btn.setMnemonic(KeyEvent.VK_I);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

		btn = new JMenuItem();
		btn.setText("Exit");
		btn.addActionListener(new ActionListener() {

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
		});
		btn.setMnemonic(KeyEvent.VK_E);
		btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		menu.add(btn);

	}

	public void addToolButtons(JToolBar tool) {

		JButton btn = new JButton();
		btn.setText("Cut");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection selected = new StringSelection(
						docModel.getCurrentDocument().getTextComponent().getSelectedText());
				clpbrd.setContents(selected, null);
				docModel.getCurrentDocument().getTextComponent()
						.setText(docModel.getCurrentDocument().getTextComponent().getText()
								.replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), ""));
			}

		});

		tool.add(btn);

		btn = new JButton();
		btn.setText("Copy");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection selected = new StringSelection(
						docModel.getCurrentDocument().getTextComponent().getSelectedText());
				clpbrd.setContents(selected, null);
			}

		});

		tool.add(btn);

		btn = new JButton();
		btn.setText("Paste");
		btn.addActionListener(new ActionListener() {

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

		});

		tool.add(btn);
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

}
