package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private boolean connected;
	
	private ILocalizationProvider provider;
	
	private ILocalizationListener l = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			fire();
		}
	};
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		connected = false;
	}
	
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(l);
			connected = false;
		}
	}
	
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(l);
			connected = true;
		}
	}
	
	public String getString(String key) {
		return provider.getString(key);
	}
}
