package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Locale;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class LowerAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;

	public LowerAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection selected = new StringSelection(
				docModel.getCurrentDocument().getTextComponent().getSelectedText());

		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());

		String lower = "";
		try {
			lower = (String) selected.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e1) {
			e1.printStackTrace();
		}
		lower = lower.toLowerCase(locale);

		docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
				.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), lower));
	}
	
}
