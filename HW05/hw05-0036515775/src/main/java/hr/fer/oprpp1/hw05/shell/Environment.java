package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface {@code Environment} represents environment in which shell is built.
 * User communicates with shell through this interface.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface Environment {
	
	/**
	 * Method reads user input line and passes it to shell.
	 * 
	 * @return string with user input
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method writes shell output to user through terminal.
	 * 
	 * @param text string output
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Method writes shell output with new line character at the end of output
	 * to user through terminal.
	 * 
	 * @param text string with user input
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method returns unmodifiable sorted map of MyShell commands.
	 * 
	 * @return unmodifiable sorted map of MyShell commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Method returns MyShell current multiline symbol.
	 * 
	 * @return current MyShell multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Method sets new MyShell multiline symbol.
	 * 
	 * @param symbol new MyShell multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Method returns MyShell current prompt symbol.
	 * 
	 * @return current MyShell prompt symbol
	 */
	Character getPromptSymbol();
	/**
	 * Method sets new MyShell prompt symbol.
	 * 
	 * @param symbol symbol new MyShell prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Method returns MyShell current morelines symbol.
	 * 
	 * @return current MyShell morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Method sets new MyShell morelines symbol.
	 * 
	 * @param symbol symbol new MyShell morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
