package hr.fer.oprpp1.hw05.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
 * Class {@code CopyShellCommand} provides work with copy command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 2);

		Path file1 = Paths.get(args[0]);
		Path file2 = Paths.get(args[1]);
		Path dest;

		if (Files.isDirectory(file1)) {
			env.writeln("Can not copy directories!");
		} else {
			String perm = "";
			if (Files.isDirectory(file2)) {
				dest = Paths.get(args[1] + "/" + file1.getFileName());
			} else {
				dest = file2;
				if (Files.exists(file2)) {
					env.writeln("Datoteka vec postoji! Overwrite? (yes/no):");
					perm = env.readLine();

					if (perm.equals("no")) {
						return ShellStatus.CONTINUE;
					}
				}
			}

			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(args[0]), 4096);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest.toString()), 4096);

				byte[] buff = new byte[4096];
				int k = bis.read(buff);
				while (k != -1) {
					bos.write(buff, 0, k);
					k = bis.read(buff);
				}

				bis.close();
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command copy copies first given argument file to second given argument file.");
		list.add("If destination file exists, user is asked permission to overwrite existing content.");
		list.add("If second given argument is directory, file is copied to that directory with same file name.");
		return list;
	}

}
