package hr.fer.oprpp1.hw04.db;

/**
 * Class {@code ConditionalExpression} represents conditional expression in query.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class ConditionalExpression {
	private IFieldValueGetter getStrategy;
	private String literal;
	private IComparisonOperator compStrategy;
	
	/**
	 * Default constructor.
	 * 
	 * @param getStrategy represents which column is being queried
	 * @param literal represents referent value for query
	 * @param compStrategy represents comparison operator or method
	 */
	public ConditionalExpression(IFieldValueGetter getStrategy, String literal, IComparisonOperator compStrategy) {
		this.getStrategy = getStrategy;
		this.literal = literal;
		this.compStrategy = compStrategy;
	}
	
	/**
	 * Default conditional expression field getter.
	 * 
	 * @return filed value getter strategy.
	 */
	public IFieldValueGetter getFieldGetter() {
		return this.getStrategy;
	}
	
	/**
	 * Default conditional expression literal getter.
	 * 
	 * @return conditional literal.
	 */
	public String getStringLiteral() {
		return this.literal;
	}
	
	/**
	 * Default conditional expression comparison operator.
	 * 
	 * @return conditional comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return this.compStrategy;
	}
}
