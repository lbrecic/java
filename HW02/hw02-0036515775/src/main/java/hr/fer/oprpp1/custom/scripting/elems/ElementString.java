package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class {@code ElementString} represents string element.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * Default constructor.
	 * 
	 * @param value
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Default string value getter.
	 * 
	 * @return string value.
	 */
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return "\"" + this.value + "\"";
	}
}
