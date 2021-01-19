package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface {@code ShellCommand} provides methods for
 * implementation of myshell commands.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface ShellCommand {
	
	/**
	 * Method executes command in the given environment.
	 * 
	 * @param env execution environment
	 * @param arguments execution arguments
	 * @return shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method returns command name.
	 * 
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Method returns command description.
	 * 
	 * @return command description as list
	 */
	List<String> getCommandDescription();
	
}
