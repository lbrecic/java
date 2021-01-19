package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class {@code ElementConstantInteger} represents integer constant element.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	private int value;
	
	/**
	 * Default constructor.
	 * 
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Default constant value getter.
	 * 
	 * @return constant value.
	 */
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
}
