package hr.fer.oprpp1.hw05.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code HelpShellCommand} provides work with help command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 1);
		
		if (args[0].equals("")) {
			for (String name : env.commands().keySet()) {
				env.writeln(name);
			}
		} else {
			env.writeln(env.commands().get(args[0]).getCommandName());
			
			for (String text : env.commands().get(args[0]).getCommandDescription()) {
				env.writeln(text);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command help without arguments writes list of available shell commands.");
		list.add("Command help with one argument writes description of given command.");
		return list;
	}

}
