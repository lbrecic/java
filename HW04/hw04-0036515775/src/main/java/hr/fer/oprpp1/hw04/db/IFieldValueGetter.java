package hr.fer.oprpp1.hw04.db;

/**
 * Interface {@code IFieldValueGEtter} represents possible columns of table
 * that are queried.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface IFieldValueGetter {
	
	/**
	 * Method gets value of a current column.
	 * 
	 * @param record represents a record of student
	 * @return value of current column
	 */
	public String get(StudentRecord record);
}
