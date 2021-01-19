package hr.fer.oprpp1.hw05.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code SymbolShellCommand} provides work with symbol command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		
		if (args.length == 1) {
			if (args[0].equals(env.getMorelinesSymbol().toString())) {
				env.write(env.getMultilineSymbol() + " ");
				String next = env.readLine();
				
				StringBuilder sb = new StringBuilder();
				String[] arr = next.split(" ");
				
				while (arr.length == 2) {
					sb.append(arr[0]);
					sb.append(" ");
					env.write(env.getMultilineSymbol() + " ");
					next = env.readLine();
					arr = next.split(" ");
				}
				
				sb.append(arr[0]);
				
				executeCommand(env, sb.toString());
				
			} else {
				env.write("Symbol for " + args[0] + " is '");
				switch(args[0]) {
				case "PROMPT":
					env.write(env.getPromptSymbol().toString());
					break;
				case "MULTILINE":
					env.write(env.getMultilineSymbol().toString());
					break;
				case "MORELINES":
					env.write(env.getMorelinesSymbol().toString());
					break;
				}
				env.write("'");
			}
			
			env.writeln("");
			
		} else if (args.length == 2) {
			env.write("Symbol for " + args[0] + " changed from '");
			switch(args[0]) {
			case "PROMPT":
				env.write(env.getPromptSymbol().toString());
				env.write("' to '");
				env.setPromptSymbol(args[1].charAt(0));
				env.write(args[1] + "'");
				break;
			case "MULTILINE":
				env.write(env.getMultilineSymbol().toString());
				env.write("' to '");
				env.setMultilineSymbol(args[1].charAt(0));
				env.write(args[1] + "'");	
				break;
			case "MORELINES":
				env.write(env.getMorelinesSymbol().toString());
				env.write("' to '");
				env.setMorelinesSymbol(args[1].charAt(0));
				env.write(args[1] + "'");
				break;
			}
			
			env.writeln("");

		} else {
			throw new ShellIOException("Neispravni argumetni!");
		} 
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("If one argument is given, command symbol returns symbol for the given argument.");
		list.add("If there are more arguments, command changes symbol according to given arguments.");
		return list;
	}

}
