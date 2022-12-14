package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class CopyAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private Clipboard clpbrd;

	public CopyAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp, Clipboard clpbrd) {
		super(key, flp);
		this.docModel = docModel;
		this.clpbrd = clpbrd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection selected = new StringSelection(
				docModel.getCurrentDocument().getTextComponent().getSelectedText());
		clpbrd.setContents(selected, null);
	}
	
}
