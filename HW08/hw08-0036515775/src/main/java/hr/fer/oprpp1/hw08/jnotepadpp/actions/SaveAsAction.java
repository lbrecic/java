package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

public class SaveAsAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private JFrame parent;

	public SaveAsAction(JFrame parent, DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (docModel.getCurrentDocument() != null) {
			JFileChooser chooser = new JFileChooser();

			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				docModel.saveDocument(docModel.getCurrentDocument(),
						Paths.get(chooser.getSelectedFile().getPath()));
			}
		}
	}
	
}
