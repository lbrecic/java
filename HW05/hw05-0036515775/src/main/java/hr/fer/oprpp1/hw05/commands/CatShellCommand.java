package hr.fer.oprpp1.hw05.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code CatShellCommand} provides work with cat command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 2);

		Charset chset;
		if (args[1].equals("")) {
			chset = Charset.defaultCharset();
		} else {
			chset = Charset.forName(args[1]);
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), chset));

			String buff = br.readLine();
			while (buff != null) {
				env.writeln(buff);
				buff = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command cat returns file content to user.");
		list.add("Content is written on standard output.");
		return list;
	}

}
