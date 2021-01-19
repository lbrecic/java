package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * <code>SmartScriptParser</code> class represents parser for smart script.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SmartScriptParser {
	
	private SmartScriptLexer lexer;
	private ObjectStack stack;
	private SmartScriptToken token;
	private DocumentNode documentNode;
	
	/**
	 * Default constructor.
	 * 
	 * @param text text for parsing
	 */
	public SmartScriptParser(String text) {
		lexer = new SmartScriptLexer(text);
		stack = new ObjectStack();
		try {
			this.parse();
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException(e);
		}
	}
	
	/**
	 * Default document node getter.
	 * 
	 * @return this document node.
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
	/**
	 * Method parses this parser's text.
	 */
	public void parse() {
		this.documentNode = new DocumentNode();
		stack.push(this.documentNode);
		
		token = lexer.nextToken();
		
		while (token.getType() != SmartScriptTokenType.EOF) {

			if (token.getType() == SmartScriptTokenType.TAGOPEN) {
				lexer.setState(SmartScriptLexerState.TAG);

			} else if (token.getType() == SmartScriptTokenType.TAGEND) {
				lexer.setState(SmartScriptLexerState.BASIC);

			} else if (token.getType() == SmartScriptTokenType.TAGNAME) {	
				if (((ElementString)token.getValue()).getValue().equals("FOR")) {
					token = lexer.nextToken();
					if (token.getType() != SmartScriptTokenType.VARIABLE) throw new SmartScriptLexerException();
					ElementVariable i = (ElementVariable)token.getValue();

					token = lexer.nextToken();
					Element s;
					switch (token.getType()) {
					case INTEGER:
						s = (ElementConstantInteger)token.getValue();
						break;
					case DOUBLE:
						s = (ElementConstantDouble)token.getValue();
						break;
					default:
						throw new SmartScriptLexerException();
					}

					token = lexer.nextToken();
					Element e;
					switch (token.getType()) {
					case INTEGER:
						e = (ElementConstantInteger)token.getValue();
						break;
					case DOUBLE:
						e = (ElementConstantDouble)token.getValue();
						break;
					case VARIABLE:
						e = (ElementVariable)token.getValue();
						break;
					default:
						throw new SmartScriptLexerException();
					}

					token = lexer.nextToken();
					Element step;

					ForLoopNode node;

					switch (token.getType()) {
					case INTEGER:
						step = (ElementConstantInteger)token.getValue();
						node = new ForLoopNode(i, s, e, step);
						break;
					case DOUBLE:
						step = (ElementConstantDouble)token.getValue();
						node = new ForLoopNode(i, s, e, step);
						break;
					case TAGEND:
						node = new ForLoopNode(i, s, e);
						lexer.setState(SmartScriptLexerState.BASIC);
						break;
					default:
						throw new SmartScriptLexerException();
					}

					Node tmp = (Node)stack.peek();
					tmp.addChildNode(node);

					stack.push(node);

				} else if (((ElementString)token.getValue()).getValue().equals("END")) {
					stack.pop();

				} else if (((ElementString)token.getValue()).getValue().equals("=")) {
					EchoNode node;
					Element[] arr;
					int cnt = 0;

					token = lexer.nextToken();

					while (token.getType() != SmartScriptTokenType.TAGEND) {
						cnt++;
						stack.push(token);
						token = lexer.nextToken();
					}

					lexer.setState(SmartScriptLexerState.BASIC);

					arr = new Element[cnt];

					for (int i = cnt - 1; i >= 0; i--) {
						SmartScriptToken tmpToken = (SmartScriptToken)stack.pop();

						switch (tmpToken.getType()) {
						case INTEGER:
							arr[i] = (ElementConstantInteger)tmpToken.getValue();
							break;
						case DOUBLE:
							arr[i] = (ElementConstantDouble)tmpToken.getValue();
							break;
						case VARIABLE:
							arr[i] = (ElementVariable)tmpToken.getValue();
							break;
						case STRING:
							arr[i] = (ElementString)tmpToken.getValue();
							break;
						case FUNCTION:
							arr[i] = (ElementFunction)tmpToken.getValue();
							break;
						case OPERATOR:
							arr[i] = (ElementOperator)tmpToken.getValue();
							break;
						default:
							throw new SmartScriptLexerException();
						}	
					}

					node = new EchoNode(arr);

					Node tmp = (Node)stack.peek();
					tmp.addChildNode(node);

				}

			} else if (token.getType() == SmartScriptTokenType.TEXT) {
				TextNode node = new TextNode((String)token.getValue());
				Node tmp = (Node)stack.peek();
				tmp.addChildNode(node);
			} 

			
			
			token = lexer.nextToken();
		}
		
		Node lastNode = (Node)stack.pop();
		
		if (stack.size() != 0) {
			throw new SmartScriptParserException();
		}
	} 
}
