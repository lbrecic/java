package hr.fer.oprpp1.hw08.jnotepadpp.gui;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadPPActions;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenuItem;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class ToolsMenu extends LJMenu {

	private static final long serialVersionUID = 1L;
	
	public ToolsMenu(JNotepadPPActions actions, DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		addToolsMenuItems(actions, docModel, flp);
	}
	
	public void addToolsMenuItems(JNotepadPPActions actions, DefaultMultipleDocumentModel docModel, ILocalizationProvider flp) {
		LJMenu change = new LJMenu("case", flp);
		change.add(new LJMenuItem(actions.getToUppercaseAction(), "up", flp));
		change.add(new LJMenuItem(actions.getToLowercaseAction(), "low", flp));
		change.add(new LJMenuItem(actions.getInvertCaseAction(), "inv", flp));
		
		add(change);
		
		LJMenu sort = new LJMenu("sort", flp);
		sort.add(new LJMenuItem(actions.getAscendingAction(), "asc", flp));
		sort.add(new LJMenuItem(actions.getDescendingAction(), "desc", flp));
		
		add(sort);
		
		LJMenuItem unique = new LJMenuItem(actions.getUniqueAction(), "unique", flp);
		add(unique);
		
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
	
}
