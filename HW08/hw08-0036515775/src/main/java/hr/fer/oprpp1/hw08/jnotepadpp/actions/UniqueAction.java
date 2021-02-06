package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class UniqueAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;

	public UniqueAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		StringSelection selected = new StringSelection(
				docModel.getCurrentDocument().getTextComponent().getSelectedText());

		String s = "";
		try {
			s = (String) selected.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e1) {
			e1.printStackTrace();
		}

		String[] lines = s.split("\n");

		StringBuilder sb = new StringBuilder();
		Set<String> set = new TreeSet<>();

//		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
//		Collator collator = Collator.getInstance(locale);

		for (int i = 0; i < lines.length; i++) {
			lines[i].trim();
		}

		for (int i = 0; i < lines.length; i++) {
			set.add(lines[i]);
		}

		for (String str : set) {
			sb.append(str);
			sb.append("\n");
		}
		
		sb.deleteCharAt(sb.length() - 1);

		docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
				.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), sb.toString()));

	}

}
