package hr.fer.oprpp1.hw08.jnotepadpp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadPPActions;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

public class LanguagesMenu extends LJMenu {

private static final long serialVersionUID = 1L;
	
	public LanguagesMenu(JNotepadPPActions actions, String key, ILocalizationProvider flp) {
		super(key, flp);
		initGUI(actions, flp);
	}
	

	private void initGUI(JNotepadPPActions actions, ILocalizationProvider flp) {
		JMenuItem hr = new JMenuItem("hr");
		hr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		});
		add(hr);
		
		JMenuItem en = new JMenuItem("en");
		en.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
		add(en);
		
		JMenuItem de = new JMenuItem("de");
		de.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		});
		add(de);
	}
	
}
