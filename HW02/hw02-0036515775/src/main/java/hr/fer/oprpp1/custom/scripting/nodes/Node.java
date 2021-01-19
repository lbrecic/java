package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Class {@code Node} represent parser node with elements.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Node {
	
	private ArrayIndexedCollection list;
	private boolean init = false;
	
	/**
	 * Method adds node child to this node.
	 * 
	 * @param child inner node
	 */
	public void addChildNode(Node child) {
		if (!this.init) {
			this.list = new ArrayIndexedCollection();
			this.init = true;
		}
		this.list.add(child);
	}
	
	/**
	 * Method returns number of current node children.
	 * 
	 * @return number of node children.
	 */
	public int numberOfChildren() {
		return this.list.size();
	}
	
	/**
	 * Method returns child node at specified index.
	 * 
	 * @param index at which node is found
	 * @return child node at given index.
	 */
	public Node getChild(int index) {
		return (Node)this.list.get(index);
	}
	
	/**
	 * Method checks if node already has child nodes.
	 * 
	 * @return {@code true} if already has child nodes {@code false} otherwise.
	 */
	public boolean getInit() {
		return this.init;
	}
}
