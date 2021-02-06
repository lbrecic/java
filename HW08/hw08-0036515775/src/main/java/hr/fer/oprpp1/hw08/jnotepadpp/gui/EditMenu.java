package hr.fer.oprpp1.hw08.jnotepadpp.gui;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadPPActions;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenuItem;

public class EditMenu extends LJMenu {

	private static final long serialVersionUID = 1L;
	
	public EditMenu(JNotepadPPActions actions, String key, ILocalizationProvider flp) {
		super(key, flp);
		initGUI(actions, flp);
	}
	
	public void initGUI(JNotepadPPActions actions, ILocalizationProvider flp) {
		add(new LJMenuItem(actions.getCutAction(), "cut", flp));
		add(new LJMenuItem(actions.getCopyAction(), "copy", flp));
		add(new LJMenuItem(actions.getPasteAction(), "paste", flp));
	}
	
}
