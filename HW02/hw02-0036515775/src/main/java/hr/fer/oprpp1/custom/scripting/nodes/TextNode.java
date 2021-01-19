package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class {@code TextNode} represents text node.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * Default constructor.
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Default text getter.
	 * 
	 * @return text.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Method return text node as string.
	 */
	public String toString() {
		return this.getText();
	}
}
