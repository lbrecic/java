package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class CloseAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;

	public CloseAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (docModel.getCurrentDocument() != null) {
			docModel.closeDocument(docModel.getCurrentDocument());
		}
	}
	
}
