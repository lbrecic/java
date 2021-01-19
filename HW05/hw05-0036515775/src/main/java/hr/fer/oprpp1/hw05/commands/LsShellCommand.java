package hr.fer.oprpp1.hw05.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code LsShellCommand} provides work with ls command.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = MyShellCommandParser.splitArgs(arguments, 1);
		
		File f = new File(args[0]);
		
		String[] pathnames = f.list();
		
		for (String pathname : pathnames) {
			File file = new File(args[0] + "/" + pathname);
			
			long len = file.length();
			
			if (file.isDirectory()) {
				env.write("d");
			} else {
				env.write("-");
			}
			
			if (file.canRead()) {
				env.write("r");
			} else {
				env.write("-");
			}
			
			if (file.canWrite()) {
				env.write("w");
			} else {
				env.write("-");
			}
			
			if (file.canExecute()) {
				env.write("x ");
			} else {
				env.write("- ");
			}
			
			env.write(String.format("%10d ", len));
			
			env.write(getTime(args[0], pathname) + " ");
			
			env.write(pathname);
			
			env.writeln("");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command ls writes all child-directories and child-files of given directory.");
		return list;
	}
	
	/**
	 * Method returns creation time of file or directory in
	 * yyyy-MM-dd HH:mm:ss format.
	 * 
	 * @param f path
	 * @param c file
	 * @return
	 */
	public String getTime(String f, String c) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Path path = Paths.get(f + "/" + c);
		
		BasicFileAttributeView faView = Files.getFileAttributeView(
		path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
		return formattedDateTime;
	}

}
