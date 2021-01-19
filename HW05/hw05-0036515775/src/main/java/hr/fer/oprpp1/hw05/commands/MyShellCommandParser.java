package hr.fer.oprpp1.hw05.commands;

/**
 * Class {@code MyShellCommandParser} provides parsing of command arguments.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class MyShellCommandParser {
	
	/**
	 * Method splits given string input of arguments according to myshell parsing
	 * rules. Returns n splitted arguments.
	 * 
	 * @param arguments string input of arguments
	 * @param n number of arguments
	 * @return string array of arguments
	 */
	public static String[] splitArgs(String arguments, int n) {
		char[] data = arguments.toCharArray();
		
		String[] args = new String[n];
		for (int i = 0; i < n; i++) {
			args[i] = "";
		}
		
		int i = 0, j = 0;
		while (i < data.length && j < n) {
			StringBuilder sb = new StringBuilder();
			if (data[i] == '\"') {
				i += 1;
				while (i < data.length && data[i] != '\"') {
					sb.append(data[i++]);
				}
				i += 1;
				if (!Character.isWhitespace(data[i]) && i < data.length) {
					throw new IllegalArgumentException("Invalid path argument.");
				}
				args[j++] = sb.toString();
			} else {
				while (i < data.length && !Character.isWhitespace(data[i])) {
					sb.append(data[i++]);
				}
				i += 1;
				args[j++] = sb.toString();
			}
		}
		
		if (i < data.length) {
			throw new IllegalArgumentException("Invalid arguments.");
		}
		
		return args;
	}
	
	/**
	 * Method splits command name from command arguments.
	 * 
	 * @param arg user input
	 * @return split command name and arguments as an array string
	 */
	public static String[] shellSplitter(String arg) {
		String[] s = new String[2];
		
		char[] data = arg.toCharArray();
		
		int n = 0;
		for (int i = 0; i < data.length; i++) {
			if (Character.isWhitespace(data[i])) {
				n = i;
				break;
			}
		}
		
		s[0] = arg.substring(0, n);
		s[1] = arg.substring(n + 1);
		
		return s;
	}
	
}
