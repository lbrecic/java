package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class {@code QueryFilter} represent filter for given
 * queries that work with student records.
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class QueryFilter  implements IFilter{
	List<ConditionalExpression> list;
	
	/**
	 * Default constructor.
	 * 
	 * @param list of queries written in {@code ConditionalExpression} type
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression ex : list) {
			if (!ex.getComparisonOperator().satisfied(ex.getFieldGetter().get(record), ex.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}

}
