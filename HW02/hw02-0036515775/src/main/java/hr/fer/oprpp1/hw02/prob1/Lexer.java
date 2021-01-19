package hr.fer.oprpp1.hw02.prob1;

/**
 * Class {@code Lexer} represents lexer for this homework language.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class Lexer {
	
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	
	/**
	 * Default constructor.
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		if (text == null) throw new NullPointerException();
		
		data = new char[text.length()];
		for (int i = 0; i < text.length(); i++) {
			data[i] = text.charAt(i);
		}
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	private boolean done = false;
	
	/**
	 * Method returns next lexer token.
	 * 
	 * @return next token.
	 */
	public Token nextToken() {
		if (done) throw new LexerException();
		
		if (currentIndex >= data.length) {
			done = true;
			token = new Token(TokenType.EOF, null);
		} else {
			
			StringBuilder sb = new StringBuilder();
			
			
			while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
				currentIndex += 1;
			}
			
			if (currentIndex >= data.length ) {
				done = true;
				token = new Token(TokenType.EOF, null);
				return token;
			}
				
			if (this.state == LexerState.BASIC) {		
				if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
					while (currentIndex < data.length && 
							(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
						if (data[currentIndex] == '\\') {
							currentIndex += 1;
							if (currentIndex >= data.length || Character.isLetter(data[currentIndex])) throw new LexerException();
							
							sb.append(data[currentIndex++]);
						} else {
							sb.append(data[currentIndex++]);
						}
					}
					
					String tmp = sb.toString();
					token = new Token(TokenType.WORD, tmp);
				} else if (Character.isDigit(data[currentIndex])) {
					while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
						sb.append(data[currentIndex++]);
					}
					
					Long tmp;
					try {
						tmp = Long.parseLong(sb.toString());
					} catch(NumberFormatException ex) {
						throw new LexerException();
					}
					token = new Token(TokenType.NUMBER, tmp);
				} else if (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
					if (data[currentIndex] == '#') {
					}
					sb.append(data[currentIndex++]);
					Character tmp = sb.toString().charAt(0);
					token = new Token(TokenType.SYMBOL, tmp);
				}
			} else if (this.state == LexerState.EXTENDED) {
				if (data[currentIndex] == '#') {
					sb.append(data[currentIndex++]);
					Character tmp = sb.toString().charAt(0);
					token = new Token(TokenType.SYMBOL, tmp);
				} else {
					while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '#') {
						sb.append(data[currentIndex++]);
					}
					
					String tmp = sb.toString();
					token = new Token(TokenType.WORD, tmp);
				}
			}
		}

		return token;
	}
	
	/**
	 * Method returns last returned token.
	 * 
	 * @return last returned token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method sets new lexer state.
	 * 
	 * @param state new lexer state.
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException();
		this.state = state;
	}
}
