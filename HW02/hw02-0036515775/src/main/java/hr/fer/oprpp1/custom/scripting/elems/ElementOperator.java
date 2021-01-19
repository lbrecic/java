package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class {@code ElementOperator} represents operator element.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ElementOperator extends Element {
	
	private String symbol;
	
	/**
	 * Default constructor.
	 * 
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Default operator symbol getter.
	 * 
	 * @return operator symbol.
	 */
	public String getValue() {
		return this.symbol;
	}
	
	@Override
	public String asText() {
		return this.symbol;
	}
}
