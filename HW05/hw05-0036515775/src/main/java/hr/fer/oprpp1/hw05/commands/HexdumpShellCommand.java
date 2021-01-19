package hr.fer.oprpp1.hw05.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.util.Util;

/**
 * Class {@code HexdumpShellCommand} provides work with hexdump command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 1);

		Path file = Paths.get(args[0]);

		if (Files.isDirectory(file)) {
			env.writeln("Arguments must be file not directory!");
		} else {
			BufferedInputStream bis;
			
			try {
				bis = new BufferedInputStream(new FileInputStream(args[0]), 32);

				byte[] buff = new byte[32];
				int k = bis.read(buff);
				int n = 0;
				while (k != -1) {
					String[] hex = new String[16];
					String s = Util.bytetohex(buff);
					Character s1, s2;

					env.write(String.format("%08X:", n));
					n += 16;
					
					for (int i = 0; i < buff.length; i++) {
						if (buff[i] < 32 || buff[i] > 127) {
							buff[i] = '.';
						}
					}

					for (int i = 0; i < buff.length; i += 2) {
						s1 = s.charAt(i);
						s2 = s.charAt(i + 1);

						hex[i / 2] = s1.toString() + s2.toString();
					}

					for (int i = 0; i < hex.length; i++) {
						if (i <= 7) {
							env.write(" " + hex[i]);
						} else {
							env.write(hex[i] + " ");
						}
						if (i == 7 || i == 15) {
							env.write("|");
						}
					}
					
					env.write(" ");
					
					for (int i = 0; i < buff.length; i ++) {
						env.write(String.format("%c", buff[i]));
					}

					env.write("\n");

					k = bis.read(buff);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command hexdump writes contents of file in 16 byte hex-decimal type.");
		return list;
	}

}
