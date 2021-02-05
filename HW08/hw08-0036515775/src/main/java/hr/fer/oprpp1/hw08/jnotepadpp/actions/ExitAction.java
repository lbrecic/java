package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public class ExitAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel docModel;
	private JFrame parent;
	private Timer t;

	public ExitAction(JFrame parent, DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider lp, Timer t) {
		super(key, lp);
		this.docModel = docModel;
		this.parent = parent;
		this.t = t;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		exitNotepad();
	}

	public void exitNotepad() {
		int result = JOptionPane.DEFAULT_OPTION;
		if (docModel.getNumberOfDocuments() != 0) {
			for (SingleDocumentModel sdm : docModel.getDocuments()) {
				if (sdm.isModified()) {
					result = JOptionPane.showOptionDialog(parent,
							"There are unsaved documents! Are you sure you want to exit the program?", "Warning",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null,
							JOptionPane.NO_OPTION);
					break;
				}
			}
		}

		switch (result) {
		case JOptionPane.DEFAULT_OPTION:
			t.cancel();
			parent.dispose();
			break;
		case JOptionPane.YES_OPTION:
			t.cancel();
			parent.dispose();
			break;
		case JOptionPane.NO_OPTION:
			break;
		case JOptionPane.CANCEL_OPTION:
			break;
		}
	}
	
}
