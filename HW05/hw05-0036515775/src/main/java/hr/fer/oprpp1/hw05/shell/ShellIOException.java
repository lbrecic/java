package hr.fer.oprpp1.hw05.shell;

/**
 * Class {@code ShellOPException} represents MyShell input/output exception.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ShellIOException extends RuntimeException {
	
	/**
	 * Constructor with message.
	 * 
	 * @param msg exception message
	 */
	public ShellIOException(String msg) {
		super(msg);
	}
	
	/**
	 * Default empty constructor.
	 */
	public ShellIOException() {
		this("");
	}
	
}
