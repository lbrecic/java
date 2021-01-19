package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class {@code ForLoopNode} represents for-loop node.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Default constructor with step expression.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
							Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Default constructor without step expression.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}
	
	/**
	 * Default variable getter.
	 * 
	 * @return variable.
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * Default start expression getter.
	 * 
	 * @return start expression.
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}
	
	/**
	 * Default end expression getter.
	 * 
	 * @return end expression.
	 */
	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * Default step expression getter.
	 * 
	 * @return step expression.
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	/**
	 * Method returns for loop node as string.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$ FOR ");
		sb.append(this.getVariable().asText());
		sb.append(" ");
		sb.append(this.getStartExpression().asText());
		sb.append(" ");
		sb.append(this.getEndExpression().asText());
		sb.append(" ");
		
		if (this.getStepExpression() != null) {
			sb.append(this.getStepExpression().asText());
			sb.append(" ");
		}
		sb.append("$}");
		
		if (this.getInit()) {
			for (int i = 0; i < this.numberOfChildren(); i++) {
				sb.append(this.getChild(i).toString());
			}
		}
		
		sb.append("{$ END $}");
		
		return sb.toString();		
	}
}
