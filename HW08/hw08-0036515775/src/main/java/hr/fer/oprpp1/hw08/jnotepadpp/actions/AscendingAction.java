package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class AscendingAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
//	private ILocalizationProvider flp;

	public AscendingAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
//		this.flp = flp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection selected = new StringSelection(
				docModel.getCurrentDocument().getTextComponent().getSelectedText());
		
		String s = "";
		try {
			s = (String)selected.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e1) {
			e1.printStackTrace();
		}
		
		String[] lines = s.split("\n");
		
		StringBuilder sb = new StringBuilder();
		
//		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
//		Collator collator = Collator.getInstance(locale);
		
		for (int i = 0; i < lines.length; i++) {
			String[] str = lines[i].split(" ");
			
			for (int j = 0; j < str.length; j++) {
				str[j].trim();
			}
			
			for (int j = 0; j < str.length; j++) {
				String tmp = str[j];
				int n = Math.abs(Arrays.binarySearch(str, 0, j, tmp) + 1);
				System.arraycopy(str, n, str, n + 1, j - n);
				str[n] = tmp;
			}
			
			for (int j = 0; j < str.length; j++) {
				sb.append(str[j]);
				if (j != str.length - 1) {
					sb.append(" ");
				}
			}
			
			if (i != lines.length - 1) {
				sb.append("\n");
			}
		}
		
		docModel.getCurrentDocument().getTextComponent().setText(docModel.getCurrentDocument().getTextComponent()
				.getText().replace(docModel.getCurrentDocument().getTextComponent().getSelectedText(), sb.toString()));
		
	}
	
}
