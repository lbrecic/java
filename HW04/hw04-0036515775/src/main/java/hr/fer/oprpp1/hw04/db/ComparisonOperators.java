package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code ComparisonOperators} contains all operators provided for query.
 * 
 * @author Luka Brečić
 * @version 1.0 
 */
public class ComparisonOperators {
	public static final IComparisonOperator LESS;
	public static final IComparisonOperator LESS_OR_EQUALS;
	public static final IComparisonOperator GREATER;
	public static final IComparisonOperator GREATER_OR_EQUALS;
	public static final IComparisonOperator EQUALS;
	public static final IComparisonOperator NOT_EQUALS;
	public static final IComparisonOperator LIKE;
	
	static
	{
		LESS = new LessOperator();
		LESS_OR_EQUALS = new LessOrEqualsOperator();
		GREATER = new GreaterOperator();
		GREATER_OR_EQUALS = new GreaterOrEqualsOperator();
		EQUALS = new EqualsOperator();
		NOT_EQUALS = new NotEqualsOperator();
		LIKE = new LikeOperator();
	}
	
	/**
	 * Class {@code LessOperator} represents less operator.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class LessOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) < 0;
		}
		
	}
	
	/**
	 * Class {@code LessOrEqualsOperator} represents less or equals operator.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class LessOrEqualsOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) <= 0;
		}
		
	}
	
	/**
	 * Class {@code GreaterOperator} represents greater operator.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class GreaterOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) > 0;
		}
		
	}
	
	/**
	 * Class {@code GreaterOrEqualsOperator} represents greater or equals operator.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class GreaterOrEqualsOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) >= 0;
		}
		
	}
	
	/**
	 * Class {@code EqualsOperator} represents equals operator.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class EqualsOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.equals(value2);
		}
		
	}
	
	/**
	 * Class {@code NotEqualsOperator} represents not equals operator.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	private static class NotEqualsOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			return !value1.equals(value2);
		}
		
	}
	
	/**
	 * Class {@code LikeOperator} represents like operator which compares similarity
	 * between given string values.
	 * 
	 * @author Luka Brečić
	 * @version 1.0
	 */
	public static class LikeOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			
			if (!value2.contains("*")) {
				return value1.equals(value2);
			}
			
			if (value2.indexOf("*") != value2.lastIndexOf("*")) {
				throw new IllegalArgumentException("Regex not allowed!");
			}
			
			if (value2.startsWith("*")) {
				return value1.endsWith(value2.substring(value2.indexOf("*") + 1));
			} else if (value2.endsWith("*")) {
				return value1.startsWith(value2.substring(0, value2.indexOf("*")));
			} else {
				return value1.startsWith(value2.substring(0, value2.indexOf("*"))) && value1.endsWith(value2.substring(value2.indexOf("*") + 1));
			}
		}
		
	}
}
