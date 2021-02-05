package hr.fer.oprpp1.hw08.jnotepadpp.gui;

import javax.swing.JToolBar;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadPPActions;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJButton;

public class DockableToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	ILocalizationProvider flp;

	public DockableToolBar(JNotepadPPActions actions, ILocalizationProvider flp) {
		super();
		addToolBarItems(actions, flp);
	}
	
	public void addToolBarItems(JNotepadPPActions actions, ILocalizationProvider flp) {
		add(new LJButton(actions.getOpenDocumentAction(), "open", flp));
		add(new LJButton(actions.getSaveDocumentAction(), "save", flp));
		addSeparator();
		add(new LJButton(actions.getCutAction(), "cut", flp));
		add(new LJButton(actions.getCopyAction(), "copy", flp));
		add(new LJButton(actions.getPasteAction(), "paste", flp));
	}
	
}
