package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code FiledValueGetters} represents all possible columns
 * of a table that is being queried.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class FieldValueGetters {
	public static IFieldValueGetter FIRST_NAME;
	public static IFieldValueGetter LAST_NAME;
	public static IFieldValueGetter JMBAG;
	
	static
	{
		FIRST_NAME = new FirstNameGetter();
		LAST_NAME = new LastNameGetter();
		JMBAG = new JMBAGGetter();
	}
	
	/**
	 * Class {@code FirstNameGetter} provides getter for first name column.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class FirstNameGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getFirstName();
		}
		
	}
	
	/**
	 * Class {@code LastNameGetter} provides getter for last name column.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class LastNameGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getLastName();
		}
		
	}
	
	/**
	 * Class {@code JMBAGGetter} provides getter for jmbag column.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class JMBAGGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getJmbag().toString();
		}
		
	}
}
