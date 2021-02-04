package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.Action;
import javax.swing.JButton;

public class LJButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private ILocalizationProvider lp;
	
	private ILocalizationListener l = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			updateItem();
		}
		
	};
	
	public LJButton(Action a, String key, ILocalizationProvider lp) {
		super(a);
		this.lp = lp;
		this.key = key;
		
		updateItem();
		
		lp.addLocalizationListener(l);
	}
	
	private void updateItem() {
		setText(lp.getString(key));
	}
	
}
