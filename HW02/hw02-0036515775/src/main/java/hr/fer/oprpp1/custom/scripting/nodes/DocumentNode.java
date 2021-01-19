package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class {@code DocumentNode} represents a document node.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class DocumentNode extends Node {
	
	/**
	 * Default constructor.
	 */
	public DocumentNode() {}
	
	/**
	 * Method returns this node as text.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (this.getInit()) {
			for (int i = 0; i < this.numberOfChildren(); i++) {
				sb.append(this.getChild(i).toString());
			}
		}
		
		return sb.toString();
	}
}
