package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public class SaveAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private JFrame parent;

	public SaveAction(JFrame parent, DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (docModel.getCurrentDocument() != null) {
			if (docModel.getCurrentDocument().getFilePath() == null) {
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showSaveDialog(parent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					docModel.saveDocument(docModel.getCurrentDocument(),
							Paths.get(chooser.getSelectedFile().getPath()));
				}
			} else {
				docModel.saveDocument(docModel.getCurrentDocument(), docModel.getCurrentDocument().getFilePath());
			}
		}
	}
	
}
