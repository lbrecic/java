package hr.fer.oprpp1.hw05.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code MkdirShellCommand} provides work with mkdir command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 1);
		
		Path file = Paths.get(args[0]);
		
		if (!Files.exists(file)){
		    try {
				Files.createDirectories(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command mkdir creates new directory with given argument as a directory name.");
		return list;
	}

}
