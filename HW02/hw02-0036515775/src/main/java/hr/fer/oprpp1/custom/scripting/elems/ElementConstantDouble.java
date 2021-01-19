package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class {@code ElementConstantDouble} represents double constant element.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Default constructor.
	 * 
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Default constant value getter.
	 * 
	 * @return constant value
	 */
	public double getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return Double.toString(this.value);
	}
}
