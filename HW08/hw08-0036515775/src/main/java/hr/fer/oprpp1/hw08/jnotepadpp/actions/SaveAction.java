package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;

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
					if (Files.exists(Paths.get(chooser.getSelectedFile().getPath()), LinkOption.NOFOLLOW_LINKS)) {
						int ret = JOptionPane.showOptionDialog(parent, "File already exists! Are you sure you want to overwrite it?",
								"Overwrite?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, JOptionPane.NO_OPTION);
						
						switch(ret) {
						case JOptionPane.YES_OPTION:
							docModel.saveDocument(docModel.getCurrentDocument(),
									Paths.get(chooser.getSelectedFile().getPath()));
							break;
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							break;
						}
					} else {
						docModel.saveDocument(docModel.getCurrentDocument(),
								Paths.get(chooser.getSelectedFile().getPath()));
					}
				}
			} else {
				docModel.saveDocument(docModel.getCurrentDocument(), docModel.getCurrentDocument().getFilePath());
			}
		}
	}

}
