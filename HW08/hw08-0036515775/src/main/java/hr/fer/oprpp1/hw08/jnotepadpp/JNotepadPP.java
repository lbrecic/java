package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Timer;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadPPActions;
import hr.fer.oprpp1.hw08.jnotepadpp.gui.DockableToolBar;
import hr.fer.oprpp1.hw08.jnotepadpp.gui.EditMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.gui.FileMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.gui.LanguagesMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.gui.StatusPanel;
import hr.fer.oprpp1.hw08.jnotepadpp.gui.ToolsMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private Clipboard clpbrd;
	private Timer t;
	private FormLocalizationProvider flp;
	private JNotepadPPActions actions;

	public JNotepadPP() throws IOException {

		setTitle("JNotepad++");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		docModel = new DefaultMultipleDocumentModel();
		clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		t = new Timer();
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		actions = new JNotepadPPActions(this, docModel, flp, clpbrd, t);

		initGUI();
	}

	public void initGUI() throws IOException {
		Container cp = getContentPane();

		listeners();

		// menus
		JMenuBar bar = new JMenuBar();
		FileMenu fileMenu = new FileMenu(actions, "file", flp);
		EditMenu editMenu = new EditMenu(actions, "edit", flp);
		LanguagesMenu languagesMenu = new LanguagesMenu(actions, "lan", flp);
		ToolsMenu toolsMenu = new ToolsMenu(actions, docModel, "tools", flp);
		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(languagesMenu);
		bar.add(toolsMenu);
		this.setJMenuBar(bar);

		// dockable toolbar
		DockableToolBar tool = new DockableToolBar(actions, flp);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(tool, BorderLayout.PAGE_START);
		panel1.add((JTabbedPane) docModel, BorderLayout.CENTER);

		StatusPanel panel2 = new StatusPanel(docModel, flp, t);

		cp.add(panel1, BorderLayout.CENTER);
		cp.add(panel2, BorderLayout.PAGE_END);

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

	private void tooltips() {
		for (int index = 0; index < docModel.getDocuments().size(); index++) {
			if (docModel.getDocument(index).getFilePath() == null) {
				docModel.setToolTipTextAt(index, "unnamed");
			} else {
				docModel.setToolTipTextAt(index, docModel.getDocument(index).getFilePath().toString());
			}
		}
	}

	private void listeners() {

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				actions.getExitDocumentAction().exitNotepad();
			}

		});

		docModel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (docModel.getSelectedIndex() >= 0) {
					docModel.setCurrent(docModel.getDocument(docModel.getSelectedIndex()));

					if (docModel.getCurrent().getFilePath() == null) {
						setTitle("unnamed");
					} else {
						setTitle(docModel.getCurrent().getFilePath().getFileName().toString());
					}
				} else {
					setTitle("JNotepad++");
				}
			}
		});

		docModel.addMultipleDocumentListener(new MultipleDocumentListener() {

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

		});

	}

}
