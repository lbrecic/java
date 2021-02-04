package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	
	private ResourceBundle bundle;
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.properties.prijevodi", locale);
		fire();
	}
	
	public String getLanguage() {
		return language;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
}
