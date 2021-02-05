package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public class NewAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel docModel;

	public NewAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key,flp);
		this.docModel = docModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		docModel.createNewDocument();
	}
	
}
