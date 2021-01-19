package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code QueryParser} is parser for given query
 * that operates with student records. 
 * 
 * @author Luka Brečić
 * @version 1.0
 */
public class QueryParser {
	List<ConditionalExpression> queries;
	private String literal;
	private IFieldValueGetter field;
	private IComparisonOperator oper;
	
	/**
	 * Default constructor inside which the parsing of given query is done.
	 * 
	 * @param query that is parsed
	 */
	public QueryParser(String query) {
		queries = new ArrayList<>();
		
		char[] data = query.toCharArray();
		int i = 0;
		
		while(i < data.length) {
			StringBuilder sb = new StringBuilder();
			while (i < data.length && Character.isWhitespace(data[i])) {
				i++;
			}
			
			if (isOperator(data[i])) {
				sb.append(data[i]);
				i += 1;
				if (i < data.length && isOperator(data[i])) {
					sb.append(data[i]);
					i += 1;
				}
				String op = sb.toString();
				
				switch (op) {
				case "<":
					oper = ComparisonOperators.LESS;
					break;
				case "<=":
					oper = ComparisonOperators.LESS_OR_EQUALS;
					break;
				case ">=":
					oper = ComparisonOperators.GREATER_OR_EQUALS;
					break;
				case ">":
					oper = ComparisonOperators.GREATER;
					break;
				case "=":
					oper = ComparisonOperators.EQUALS;
					break;
				case "!=":
					oper = ComparisonOperators.NOT_EQUALS;
					break;
				}
			} else if (data[i] == '\"') {
				i += 1;
				while (i < data.length && data[i] != '\"') {
					sb.append(data[i++]);
				}
				literal = sb.toString();
				i++;
			} else {
				while (i < data.length && !Character.isWhitespace(data[i])
						&& data[i] != '\"' && !isOperator(data[i])) {
					sb.append(data[i++]);
				}
				
				String tmp = sb.toString().toLowerCase();
				
				switch (tmp) {
				case "jmbag":
					field = FieldValueGetters.JMBAG;
					break;
				case "firstname":
					field = FieldValueGetters.FIRST_NAME;
					break;
				case "lastname":
					field = FieldValueGetters.LAST_NAME;
					break;
				case "like":
					oper = ComparisonOperators.LIKE;
					break;
				case "and":
					queries.add(new ConditionalExpression(field, literal, oper));
					field = null;
					literal = "";
					oper = null;
					break;
				}
			}
		}
		
		queries.add(new ConditionalExpression(field, literal, oper));
	}
	
	/**
	 * Method checks if given query is in direct query shape 
	 * (i. e. jmbag = ...).
	 * 
	 * @return {@code true} if query is direct, {@code false}
	 */
	public boolean isDirectQuery() {
		for (ConditionalExpression e : queries) {
			if (e.getComparisonOperator().equals(ComparisonOperators.EQUALS)
					&& e.getFieldGetter().equals(FieldValueGetters.JMBAG)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method returns jmbag from a direct query.
	 * 
	 * @return jmbag from direct query
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery())  throw new IllegalStateException();
		
		return queries.get(0).getStringLiteral();
	}
	
	/**
	 * Method returns list of queries written like
	 * {@code ConditionalExpression} instances.
	 * 
	 * @return list of {@code ConditionalExpression} instances.
	 */
	public List<ConditionalExpression> getQuery() {
		return this.queries;
	}
	
	/**
	 * Method checks if given character is operator.
	 * 
	 * @param c character that is checked
	 * @return {@code true} if character is operator, {@code false} otherwise.
	 */
	private static boolean isOperator(char c) {
		if (c == '<' || c == '>' || c == '=' || c == '!') 
			return true;
		
		return false;
	}
}
