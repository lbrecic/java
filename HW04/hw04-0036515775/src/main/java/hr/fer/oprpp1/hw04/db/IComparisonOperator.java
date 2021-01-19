package hr.fer.oprpp1.hw04.db;

/**
 * Interface {@code IComparisonOperator} represents query operators.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public interface IComparisonOperator {
	
	/**
	 * Method checks if given values satisfy current operator.
	 * 
	 * @param value1 string value that is compared.
	 * @param value2 string value that method compares to.
	 * @return {@code true} if satisfy, {@code false} otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
