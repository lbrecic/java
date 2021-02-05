package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public class PasteAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private Clipboard clpbrd;

	public PasteAction(DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp, Clipboard clpbrd) {
		super(key, flp);
		this.docModel = docModel;
		this.clpbrd = clpbrd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Transferable t = clpbrd.getContents(this);
		if (t != null) {
			try {
				docModel.getCurrentDocument().getTextComponent().insert(
						(String) t.getTransferData(DataFlavor.stringFlavor),
						docModel.getCurrentDocument().getTextComponent().getCaretPosition());
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
