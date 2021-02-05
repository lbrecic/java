package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public class InfoAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel docModel;
	private JFrame parent;

	public InfoAction(JFrame parent, DefaultMultipleDocumentModel docModel, String key, ILocalizationProvider flp) {
		super(key, flp);
		this.docModel = docModel;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (docModel.getCurrentDocument() != null) {

			int x = 0, y = 0, z = 0; // x = chars, y = non_blank, z = lines

			for (String line : docModel.getCurrentDocument().getTextComponent().getText().split("\n")) {
				z++;
				x += line.length();
				line += '\n';
				for (int i = 0; i < line.length(); i++) {
					if (!Character.isWhitespace(line.charAt(i))) {
						y++;
					}
				}

			}
			x += z - 1;

			JOptionPane.showMessageDialog(parent,
					"Your document has " + x + " characters, " + y + " non-blank characters and " + z + " lines.",
					"Statistical info", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
}
