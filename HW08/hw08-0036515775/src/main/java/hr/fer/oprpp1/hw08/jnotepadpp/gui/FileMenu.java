package hr.fer.oprpp1.hw08.jnotepadpp.gui;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadPPActions;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenuItem;

public class FileMenu extends LJMenu {
	
	private static final long serialVersionUID = 1L;
	
	public FileMenu(JNotepadPPActions actions, String key, ILocalizationProvider flp) {
		super(key, flp);
		addFileMenuItems(actions, flp);
	}
	
	public void addFileMenuItems(JNotepadPPActions actions, ILocalizationProvider flp) {
		add(new LJMenuItem(actions.getNewDocumentAction(), "new", flp));
		add(new LJMenuItem(actions.getOpenDocumentAction(), "open", flp));
		add(new LJMenuItem(actions.getSaveDocumentAction(), "save", flp));
		add(new LJMenuItem(actions.getSaveAsDocumentAction(), "saveas", flp));
		add(new LJMenuItem(actions.getCloseDocumentAction(), "close", flp));
		addSeparator();
		add(new LJMenuItem(actions.getExitDocumentAction(), "exit", flp));
	}
	
}
