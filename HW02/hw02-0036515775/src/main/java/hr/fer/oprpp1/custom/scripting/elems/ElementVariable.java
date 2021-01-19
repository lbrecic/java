package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class {@code ElementVariable} represents variable element.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ElementVariable extends Element{
	
	private String name;
	
	/**
	 * Default constructor.
	 * 
	 * @param name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Default variable name getter.
	 * 
	 * @return variable name.
	 */
	public String getValue() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
}
