package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * <code>SmartScriptLexer</code> class represents lexer for smart script.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SmartScriptLexer {
	
	private char[] data;
	private SmartScriptToken token;
	private int currentIndex;
	private SmartScriptLexerState state;
	
	/**
	 * Default constructor.
	 * 
	 * @param text string that is processed by lexer
	 */
	public SmartScriptLexer(String text) {
		if (text == null) throw new NullPointerException();
		
		data = new char[text.length()];
		
		for (int i = 0; i < text.length(); i++) {
			data[i] = text.charAt(i);
		}
		
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.BASIC;
	}
	
	private boolean done = false;
	
	/**
	 * Method returns next lexer token.
	 * 
	 * @return next token.
	 */
	public SmartScriptToken nextToken() {
		if (done) throw new SmartScriptLexerException();
		
		if (currentIndex >= data.length) {
			done = true;
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			
		} else {	
			StringBuilder sb = new StringBuilder();
				
			if (this.state == SmartScriptLexerState.TAG) {		
				while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
					currentIndex += 1;
				}
				
				if (currentIndex >= data.length) {
					done = true;
					token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
					return token;
				}
				
				if (data[currentIndex] == '@') {
					currentIndex += 1;
					
					if (currentIndex >= data.length || !Character.isLetter(data[currentIndex])) throw new SmartScriptLexerException();
					
					while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
						sb.append(data[currentIndex]);
					}
					
					ElementFunction el = new ElementFunction(sb.toString());
					token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, el);
					
				} else if (isOperator(data[currentIndex])) {
					if (data[currentIndex] == '-') {
						if (currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
							sb.append(data[currentIndex]);
							currentIndex += 1;
							
							while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
								sb.append(data[currentIndex]);
							}
							
							try {
								ElementConstantInteger el = new ElementConstantInteger(Integer.parseInt(sb.toString()));
								token = new SmartScriptToken(SmartScriptTokenType.INTEGER, el);
								
							} catch (Exception e) {
								ElementConstantDouble el = new ElementConstantDouble(Double.parseDouble(sb.toString()));
								token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, el);
							}
							
						} else {
							sb.append(data[currentIndex++]);
							ElementOperator el = new ElementOperator(sb.toString());
							token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, el);
						}
						
					} else {
						sb.append(data[currentIndex++]);
						ElementOperator el = new ElementOperator(sb.toString());
						token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, el);
					}
					
				} else if (data[currentIndex] == '\"') {
					currentIndex += 1;
					
					if (currentIndex >= data.length) throw new SmartScriptLexerException();
					
					while (currentIndex < data.length && data[currentIndex] != '\"') {
						if (data[currentIndex] == '\\') {
							currentIndex += 1;
							
							if (currentIndex >= data.length) throw new SmartScriptLexerException();
							
							if (data[currentIndex] != '\"' && data[currentIndex] != '\\'
									&& data[currentIndex] != 'n'
									&& data[currentIndex] != 't'
									&& data[currentIndex] != 'r') throw new SmartScriptLexerException();
							
							switch (data[currentIndex]) {
							case 'n':
								sb.append('\n');
								break;
							case 't':
								sb.append('\t');
								break;
							case 'r':
								sb.append('\r');
								break;
							default:
								sb.append(data[currentIndex]);
							}
						} else {
							sb.append(data[currentIndex]);
						}
						
						currentIndex++;
					}
					
					currentIndex += 1;
					ElementString el = new ElementString(sb.toString());
					token = new SmartScriptToken(SmartScriptTokenType.STRING, el);
					
				} else if (currentIndex < data.length && (currentIndex+1) < data.length && data[currentIndex] == '$' && data[currentIndex+1] == '}') {
					sb.append(data[currentIndex]);
					sb.append(data[currentIndex+1]);
					currentIndex += 2;
					
					ElementString el = new ElementString(sb.toString());
					token = new SmartScriptToken(SmartScriptTokenType.TAGEND, el);
					
				} else if (data[currentIndex] == '=') {
					sb.append(data[currentIndex++]);
					
					ElementString el = new ElementString(sb.toString());
					token = new SmartScriptToken(SmartScriptTokenType.TAGNAME, el);
					
				} else if (Character.isLetter(data[currentIndex])) {
					while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
						sb.append(data[currentIndex++]);
					}
					
					String tmp = sb.toString();
					
					if (tmp.toUpperCase().equals("FOR") || tmp.toUpperCase().equals("END")) {
						ElementString el = new ElementString(tmp.toUpperCase());
						token = new SmartScriptToken(SmartScriptTokenType.TAGNAME, el);
						
					} else {
						if (!Character.isLetter(tmp.charAt(0))) throw new SmartScriptLexerException();
						
						ElementVariable el = new ElementVariable(tmp);
						token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, el);
					}
					
				} else {
					throw new SmartScriptLexerException();
				}
				
			} else if (this.state == SmartScriptLexerState.BASIC) {
				if (currentIndex < data.length && (currentIndex+1) < data.length && data[currentIndex] == '{' && data[currentIndex+1] == '$') {
					sb.append(data[currentIndex]);
					sb.append(data[currentIndex+1]);
					currentIndex += 2;
					
					ElementString el = new ElementString(sb.toString());
					token = new SmartScriptToken(SmartScriptTokenType.TAGOPEN, el);
					
				} else {
					while (currentIndex < data.length) {
						if (data[currentIndex] == '\\') {
							currentIndex += 1;
							if (currentIndex >= data.length || (data[currentIndex] != '\\' && data[currentIndex] != '{')) { 
								throw new SmartScriptLexerException();	
							} else {
								sb.append(data[currentIndex]);
								currentIndex += 1;
								continue;
							}
						}
						
						if (data[currentIndex] == '{' && data[currentIndex+1] == '$') {
							break;
						}
						
						sb.append(data[currentIndex++]);
					}
					
					String tmp = sb.toString();
					token = new SmartScriptToken(SmartScriptTokenType.TEXT, tmp);
				}
			}
		}

		return token;
	}
	
	/**
	 * Method return last returned token.
	 * 
	 * @return last returned token.
	 */
	public SmartScriptToken getToken() {
		return token;
	}
	
	/**
	 * Method sets new lexer state.
	 * 
	 * @param state new lexer state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) throw new NullPointerException();
		
		this.state = state;
	}
	
	/**
	 * Method checks if character is operator type.
	 * 
	 * @param c character that is checked
	 * @return {@code true} if character is operator, {@code false} otherwise.
	 */
	private static boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') 
			return true;
		
		return false;
	}
}
