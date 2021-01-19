package hr.fer.oprpp1.hw05.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.commands.*;

/**
 * Class {@code MyShell} provides user work with MyShell shell.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class MyShell {
	
	private SortedMap<String, ShellCommand> commands;
	private Environment env;
	private Scanner sc;
	private Character promptSymbol;
	private Character multilineSymbol;
	private Character morelinesSymbol;
	private ShellStatus status;
	
	/**
	 * Default empty constructor.
	 */
	public MyShell() {
		status = ShellStatus.CONTINUE;
		promptSymbol = '>';
		multilineSymbol = '|';
		morelinesSymbol = '\\';
		
		commands = new TreeMap<String, ShellCommand>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		
		sc = new Scanner(System.in);
		
		env = new Environment() {

			@Override
			public String readLine() throws ShellIOException {
				return sc.nextLine();
			}

			@Override
			public void write(String text) throws ShellIOException {
				System.out.print(text);
			}

			@Override
			public void writeln(String text) throws ShellIOException {
				System.out.println(text);
			}

			@Override
			public SortedMap<String, ShellCommand> commands() {
				return commands;
			}

			@Override
			public Character getMultilineSymbol() {
				return multilineSymbol;
			}

			@Override
			public void setMultilineSymbol(Character symbol) {
				multilineSymbol = symbol;
			}

			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}

			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;
			}

			@Override
			public Character getMorelinesSymbol() {
				return morelinesSymbol;
			}

			@Override
			public void setMorelinesSymbol(Character symbol) {
				morelinesSymbol = symbol;
			}
			
		};
	}
	
	/**
	 * Method builds environment and starts MyShell.
	 */
	public void runMyShell() {
		env.writeln("Welcome to MyShell v 1.0");
		String input, arg, commandName;
		ShellCommand command;
		
		do {
			env.write(promptSymbol + " ");
			input = env.readLine();
			
			if (input.split(" ").length > 1) {
				String[] args = MyShellCommandParser.shellSplitter(input);
				commandName = args[0];
				if (args.length > 1) {
					arg = args[1];
				} else {
					arg = "";
				}
			} else {
				commandName = input;
				arg = "";
			}
			
			command = commands.get(commandName);
			status = command.executeCommand(env, arg);
			
		} while (status.equals(ShellStatus.CONTINUE));
	}
	
	public static void main(String[] args) {
		MyShell myShell = new MyShell();
		myShell.runMyShell();
	}
	
}
