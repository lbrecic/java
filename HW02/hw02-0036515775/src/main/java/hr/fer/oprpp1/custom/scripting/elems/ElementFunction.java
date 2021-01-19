package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class {@code ElementFunction} represents function element.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ElementFunction extends Element { 
	
	private String name;
	
	/**
	 * Default constructor.
	 * 
	 * @param name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Default function name getter.
	 * 
	 * @return function name.
	 */
	public String getValue() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return "@" + this.name;
	}
}
