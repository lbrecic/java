package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Class {@code EchoNode} represents body of parent node.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class EchoNode extends Node {
	
	private Element[] elements;
	
	/**
	 * Default constructor.
	 * 
	 * @param elements
	 */
	public EchoNode(Element... elements) {
		this.elements = elements;
	}
	
	/**
	 * Default elements getter.
	 * 
	 * @return elements of echo node.
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	/**
	 * Method returns elements as string.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Element[] el = this.getElements();
		
		sb.append("{$= ");
		for (int i = 0; i < el.length; i++) {
			sb.append(el[i].asText());
			if (i != el.length - 1) {
				sb.append(" ");
			}
		}
		sb.append("$}");
		
		return sb.toString();
	}
}
