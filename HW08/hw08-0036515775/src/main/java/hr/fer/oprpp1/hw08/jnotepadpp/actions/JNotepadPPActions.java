package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;
import java.util.Timer;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class JNotepadPPActions {

	private ILocalizationProvider flp;
	
	private NewAction newDocumentAction;
	private OpenAction openDocumentAction;
	private SaveAction saveDocumentAction;
	private SaveAsAction saveAsDocumentAction;
	private CloseAction closeDocumentAction;
	private ExitAction exitDocumentAction;
	private InfoAction infoDocumentAction;
	private CutAction cutAction;
	private CopyAction copyAction;
	private PasteAction pasteAction;
	private UpperAction toUppercaseAction;
	private LowerAction toLowercaseAction;
	private InvertAction invertCaseAction;
	private AscendingAction ascendingAction;
	private DescendingAction descendingAction;
	private UniqueAction uniqueAction;

	public JNotepadPPActions(JFrame parent, DefaultMultipleDocumentModel docModel, ILocalizationProvider flp,
			Clipboard clpbrd, Timer t) {
		
		this.flp = flp;
		
		newDocumentAction = new NewAction(docModel, "new", flp);
		openDocumentAction = new OpenAction(parent, docModel, "open", flp);
		saveDocumentAction = new SaveAction(parent, docModel, "save", flp);
		saveAsDocumentAction = new SaveAsAction(parent, docModel, "saveas", flp);
		closeDocumentAction = new CloseAction(docModel, "close", flp);
		infoDocumentAction = new InfoAction(parent, docModel, "info", flp);
		exitDocumentAction = new ExitAction(parent, docModel, "exit", flp, t) ;
		cutAction = new CutAction(docModel, "cut", flp, clpbrd);
		copyAction = new CopyAction(docModel, "copy", flp, clpbrd);
		pasteAction = new PasteAction(docModel, "paste", flp, clpbrd);
		toUppercaseAction = new UpperAction(docModel, "up", flp);
		toLowercaseAction = new LowerAction(docModel, "low", flp);
		invertCaseAction = new InvertAction(docModel, "inv", flp);
		ascendingAction = new AscendingAction(docModel, "asc", flp);
		descendingAction = new DescendingAction(docModel, "desc", flp);
		uniqueAction = new UniqueAction(docModel, "unique", flp);
		
		createActions();
	}

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

	public NewAction getNewDocumentAction() {
		return newDocumentAction;
	}

	public OpenAction getOpenDocumentAction() {
		return openDocumentAction;
	}

	public SaveAction getSaveDocumentAction() {
		return saveDocumentAction;
	}

	public SaveAsAction getSaveAsDocumentAction() {
		return saveAsDocumentAction;
	}

	public CloseAction getCloseDocumentAction() {
		return closeDocumentAction;
	}

	public ExitAction getExitDocumentAction() {
		return exitDocumentAction;
	}

	public InfoAction getInfoDocumentAction() {
		return infoDocumentAction;
	}

	public CutAction getCutAction() {
		return cutAction;
	}

	public CopyAction getCopyAction() {
		return copyAction;
	}

	public PasteAction getPasteAction() {
		return pasteAction;
	}

	public UpperAction getToUppercaseAction() {
		return toUppercaseAction;
	}

	public LowerAction getToLowercaseAction() {
		return toLowercaseAction;
	}

	public InvertAction getInvertCaseAction() {
		return invertCaseAction;
	}

	public AscendingAction getAscendingAction() {
		return ascendingAction;
	}

	public DescendingAction getDescendingAction() {
		return descendingAction;
	}

	public UniqueAction getUniqueAction() {
		return uniqueAction;
	}
	
}