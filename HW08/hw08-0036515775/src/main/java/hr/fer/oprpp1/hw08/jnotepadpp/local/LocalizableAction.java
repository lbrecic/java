package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private ILocalizationProvider lp;
	
	private ILocalizationListener l = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			String value = lp.getString(key);
			putValue(Action.NAME, value);
		}
		
	};
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		
		String value = lp.getString(key);
		putValue(Action.NAME, value);
		
		lp.addLocalizationListener(l);
	}

}
