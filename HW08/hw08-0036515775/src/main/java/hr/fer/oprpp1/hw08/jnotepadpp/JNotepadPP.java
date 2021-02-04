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
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
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

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJButton;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenuItem;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private Clipboard clpbrd;
	private ChangeListener listener;
	private DocumentListener l;
	private Timer t;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	public JNotepadPP() throws IOException {
		clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exitNotepad();
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

		createActions();

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout());
		panel2.setBorder(BorderFactory.createLineBorder(Color.black));

		addStatusPanel(panel2);

		cp.add(panel2, BorderLayout.PAGE_END);

		docModel = new DefaultMultipleDocumentModel();
		docModel.addChangeListener(listener);
		docModel.addMultipleDocumentListener(mdl);

		JMenuBar bar = new JMenuBar();

		LJMenu fileMenu = new LJMenu("file", flp);
		addFileMenuItems(fileMenu);

		LJMenu editMenu = new LJMenu("edit", flp);
		addEditMenuItems(editMenu);

		LJMenu languagesMenu = new LJMenu("lan", flp);
		addLanguagesMenuItems(languagesMenu);
		
		LJMenu toolsMenu = new LJMenu("tools", flp);
		addToolsMenuItems(toolsMenu);

		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(languagesMenu);
		bar.add(toolsMenu);

		this.setJMenuBar(bar);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());

		JToolBar tool = new JToolBar();
		addToolBarItems(tool);

		panel1.add(tool, BorderLayout.PAGE_START);
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
	
	private void addCaretPanel(JPanel panel) {
		
		LJLabel line = new LJLabel("line", flp);
		LJLabel column = new LJLabel("col", flp);
		LJLabel select = new LJLabel("sel", flp);
		
		panel.add(line, BorderLayout.WEST);
		panel.add(column, BorderLayout.CENTER);
		panel.add(select, BorderLayout.EAST);

		listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (docModel.getSelectedIndex() >= 0) {
					docModel.setCurrent(docModel.getDocument(docModel.getSelectedIndex()));

					if (docModel.getCurrent().getFilePath() == null) {
						setTitle("unnamed");
					} else {
						setTitle(docModel.getCurrent().getFilePath().getFileName().toString());
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

						line.setText(flp.getString("line") + ": " + ln); 
						column.setText(flp.getString("col") + ": " + col);
						select.setText(flp.getString("sel") + ": " + sel);
					}

				});
				
				} else {
					setTitle("JNotepad++");
					line.setText(flp.getString("line") + ": "); 
					column.setText(flp.getString("col") + ": ");
					select.setText(flp.getString("sel") + ": ");
				}
			}
		};
	}

	private void addStatusPanel(JPanel panel2) {
		LJLabel label1 = new LJLabel("length", flp);
		label1.setHorizontalTextPosition(SwingConstants.LEFT);
		panel2.add(label1, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.setAlignmentX(LEFT_ALIGNMENT);
		addCaretPanel(panel);
		panel2.add(panel, BorderLayout.CENTER);

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

		l = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				label1.setText(flp.getString("length" )+ " " + docModel.getCurrentDocument().getTextComponent().getText().length());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				label1.setText(flp.getString("length" )+ " " + docModel.getCurrentDocument().getTextComponent().getText().length());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				label1.setText(flp.getString("length" )+ " " + docModel.getCurrentDocument().getTextComponent().getText().length());
			}
		};

	}

	private MultipleDocumentListener mdl = new MultipleDocumentListener() {

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

	public void tooltips() {
		for (int index = 0; index < docModel.getDocuments().size(); index++) {
			if (docModel.getDocument(index).getFilePath() == null) {
				docModel.setToolTipTextAt(index, "unnamed");
			} else {
				docModel.setToolTipTextAt(index, docModel.getDocument(index).getFilePath().toString());
			}
		}
	}

	private Action newDocumentAction = new LocalizableAction("new", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			docModel.createNewDocument();
		}

	};

	private Action openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();

			int returnVal = chooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				docModel.loadDocument(Paths.get(chooser.getSelectedFile().getPath()));
			}
		}

	};

	private Action saveDocumentAction = new LocalizableAction("save", flp) {

		private static final long serialVersionUID = 1L;

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
					docModel.saveDocument(docModel.getCurrentDocument(), docModel.getCurrentDocument().getFilePath());
				}
			}
		}

	};

	private Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {

		private static final long serialVersionUID = 1L;

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

	private Action closeDocumentAction = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (docModel.getCurrentDocument() != null) {
				docModel.closeDocument(docModel.getCurrentDocument());
			}
		}

	};

	private Action infoDocumentAction = new LocalizableAction("info", flp) {

		private static final long serialVersionUID = 1L;

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

				JOptionPane.showMessageDialog(getParent(),
						"Your document has " + x + " characters, " + y + " non-blank characters and " + z + " lines.",
						"Statistical info", JOptionPane.INFORMATION_MESSAGE);

			}
		}

	};

	private Action exitDocumentAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exitNotepad();
		}

	};

	private Action cutAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			clpbrd.setContents(selected, null);
			docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
					.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), ""));
		}

	};

	private Action copyAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			clpbrd.setContents(selected, null);
		}

	};

	private Action pasteAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

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
	
	private Action toUppercaseAction = new LocalizableAction("up", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			
			Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
			
			String upper = "";
			try {
				upper = (String)selected.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}
			upper = upper.toUpperCase(locale);
			
			
			docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
					.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), upper));
		}

	};
	
	private Action toLowercaseAction = new LocalizableAction("low", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			
			Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
			
			String lower = "";
			try {
				lower = (String)selected.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}
			lower = lower.toLowerCase(locale);
			
			docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
					.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), lower));
		}

	};
	
	private Action invertCaseAction = new LocalizableAction("inv", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StringSelection selected = new StringSelection(
					docModel.getCurrentDocument().getTextComponent().getSelectedText());
			
			Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
			
			String invert = "";
			try {
				invert = (String)selected.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			StringBuilder sb = new StringBuilder();
			for (Character c : invert.toCharArray()) {
				if (Character.isLowerCase(c)) {
					sb.append(Character.toUpperCase(c));
				} else {
					sb.append(Character.toLowerCase(c));
				}
			}
			
			docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
					.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), sb.toString()));
		}

	};
	
	private Action ascendingAction = new LocalizableAction("asc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	};
	
	private Action descendingAction = new LocalizableAction("desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	};
	
	private Action uniqueAction = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	};

	private void createActions() {
		
		newDocumentAction.putValue(Action.NAME, flp.getString("new"));
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Creates new document.");

		openDocumentAction.putValue(Action.NAME, flp.getString("open"));
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Opens existing document.");

		saveDocumentAction.putValue(Action.NAME, flp.getString("save"));
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves document.");

		saveAsDocumentAction.putValue(Action.NAME, flp.getString("saveas"));
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves as another document.");

		closeDocumentAction.putValue(Action.NAME, flp.getString("close"));
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Closes current document.");

		infoDocumentAction.putValue(Action.NAME, flp.getString("info"));
		infoDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		infoDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Statistical information of current document.");

		exitDocumentAction.putValue(Action.NAME, flp.getString("exit"));
		exitDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Exit program.");

		cutAction.putValue(Action.NAME, flp.getString("cut"));
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Cut operation.");

		copyAction.putValue(Action.NAME, flp.getString("copy"));
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copy operation.");

		pasteAction.putValue(Action.NAME, flp.getString("paste"));
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Paste operation.");
		
		toUppercaseAction.putValue(Action.NAME, flp.getString("up"));
		toLowercaseAction.putValue(Action.NAME, flp.getString("low"));
		invertCaseAction.putValue(Action.NAME, flp.getString("inv"));
		ascendingAction.putValue(Action.NAME, flp.getString("asc"));
		descendingAction.putValue(Action.NAME, flp.getString("desc"));
		uniqueAction.putValue(Action.NAME, flp.getString("unique"));

	}

	private void addFileMenuItems(JMenu menu) {
		menu.add(new LJMenuItem(newDocumentAction, "new", flp));
		menu.add(new LJMenuItem(openDocumentAction, "open", flp));
		menu.add(new LJMenuItem(saveDocumentAction, "save", flp));
		menu.add(new LJMenuItem(saveAsDocumentAction, "saveas", flp));
		menu.add(new LJMenuItem(closeDocumentAction, "close", flp));
		menu.addSeparator();
		menu.add(new LJMenuItem(exitDocumentAction, "exit", flp));
	}

	private void addEditMenuItems(JMenu menu) {
		menu.add(new LJMenuItem(cutAction, "cut", flp));
		menu.add(new LJMenuItem(copyAction, "copy", flp));
		menu.add(new LJMenuItem(pasteAction, "paste", flp));
	}

	private void addLanguagesMenuItems(JMenu menu) {
		JMenuItem hr = new JMenuItem("hr");
		hr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		});
		menu.add(hr);
		
		JMenuItem en = new JMenuItem("en");
		en.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
		menu.add(en);
		
		JMenuItem de = new JMenuItem("de");
		de.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		});
		menu.add(de);
	}
	
	private void addToolsMenuItems(JMenu menu) {
		LJMenu change = new LJMenu("case", flp);
		change.add(new LJMenuItem(toUppercaseAction, "up", flp));
		change.add(new LJMenuItem(toLowercaseAction, "low", flp));
		change.add(new LJMenuItem(invertCaseAction, "inv", flp));
		
		menu.add(change);
		
		LJMenu sort = new LJMenu("sort", flp);
		sort.add(new LJMenuItem(ascendingAction, "asc", flp));
		sort.add(new LJMenuItem(descendingAction, "desc", flp));
		
		menu.add(sort);
		
		LJMenuItem unique = new LJMenuItem(uniqueAction, "unique", flp);
		menu.add(unique);
		
		change.setEnabled(false);
		sort.setEnabled(false);
		unique.setEnabled(false);
		
		docModel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				docModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent e) {
						if (docModel.getCurrentDocument().getTextComponent().getSelectedText() != null) {
							change.setEnabled(true);
							sort.setEnabled(true);
							unique.setEnabled(true);
						} else {
							change.setEnabled(false);
							sort.setEnabled(false);
							unique.setEnabled(false);
						}
					}
					
				});
			}
			
		});
	}

	private void addToolBarItems(JToolBar tool) {
		tool.add(new LJButton(openDocumentAction, "open", flp));
		tool.add(new LJButton(saveDocumentAction, "save", flp));
		tool.addSeparator();
		tool.add(new LJButton(cutAction, "cut", flp));
		tool.add(new LJButton(copyAction, "copy", flp));
		tool.add(new LJButton(pasteAction, "paste", flp));
	}

	private void exitNotepad() {
		int result = JOptionPane.DEFAULT_OPTION;
		if (docModel.getNumberOfDocuments() != 0) {
			for (SingleDocumentModel sdm : docModel.getDocuments()) {
				if (sdm.isModified()) {
					result = JOptionPane.showOptionDialog(getParent(),
							"There are unsaved documents! Are you sure you want to exit the program?", "Warning",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null,
							JOptionPane.NO_OPTION);
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

}
