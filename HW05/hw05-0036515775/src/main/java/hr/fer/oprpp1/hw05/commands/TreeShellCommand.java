package hr.fer.oprpp1.hw05.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code TreeShellCommand} provides work with tree command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {
	private static int n = 0;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 1);
		
		Path p = Paths.get(args[0]);
		try {
			Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					for (int i = 0; i < n; i++) {
						env.write(" ");
					}
					
					env.write(dir.toFile().getName());
					env.writeln("");
					n += 2;
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) {
					n -= 2;
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) {
					for (int i = 0; i < n; i++) {
						env.write(" ");
					}
					
					env.write(dir.toFile().getName());
					env.writeln("");
					
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command tree returns tree representation of given directory.");
		list.add("Subdirectories and subfiles are indented with two spaces.");
		return list;
	}

}
