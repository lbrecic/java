package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public class InvertAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;

	public InvertAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection selected = new StringSelection(
				docModel.getCurrentDocument().getTextComponent().getSelectedText());

		String invert = "";
		try {
			invert = (String) selected.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (Character c : invert.toCharArray()) {
			if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}

		docModel.getCurrentDocument().getTextComponent()
				.setText(docModel.getCurrentDocument().getTextComponent().getText().replace(
						docModel.getCurrentDocument().getTextComponent().getSelectedText(), sb.toString()));
	}
	
}
