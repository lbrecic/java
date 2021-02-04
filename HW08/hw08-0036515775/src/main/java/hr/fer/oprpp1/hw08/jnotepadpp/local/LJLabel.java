package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private ILocalizationProvider lp;
	
	private ILocalizationListener l = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			updateItem();
		}
		
	};
	
	public LJLabel(String key, ILocalizationProvider lp) {
		this.lp = lp;
		this.key = key;
		
		updateItem();
		
		lp.addLocalizationListener(l);
	}
	
	private void updateItem() {
		setText(lp.getString(key));
	}

}
