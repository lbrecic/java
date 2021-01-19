package hr.fer.oprpp1.hw04.db;

/**
 * Interface {@code IFilter} provides method declarations for 
 * given filtering of student records.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface IFilter {
	
	/**
	 * Method checks if given record is valid.
	 * 
	 * @param record instance of student record 
	 * @return {@code true} if record is valid, {@code false} otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
