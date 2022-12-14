package nosi.core.webapp.databse.helpers;
/**
 * Emanuel
 * 19 Apr 2018
 */
public interface QueryInterface extends BaseQueryInterface{

	public QueryInterface where(String name,String operator,java.sql.Date value); 

	public QueryInterface where(String name,String operator,String value,String format);
	
	public QueryInterface andWhereNotNull(String name);
	
	public QueryInterface andWhereIsNull(String name);
	
	public QueryInterface andWhere(String name,String operator,String value);
	
	public QueryInterface andWhere(String name,String operator,String[] values);
	
	public QueryInterface andWhere(String name,String operator,Integer[] values);
	
	public QueryInterface andWhere(String name,String operator,Double[] values);
	
	public QueryInterface andWhere(String name,String operator,Float[] values);
	
	public QueryInterface andWhere(String name,String operator,Integer value);
	
	public QueryInterface andWhere(String name,String operator,Float value);
	
	public QueryInterface andWhere(String name,String operator,Double value);

	public QueryInterface andWhere(String name,String operator,java.sql.Date value); 

	public QueryInterface andWhere(String name,String operator,String value,String format);
	
	public QueryInterface orWhereNotNull(String name);
	
	public QueryInterface orWhereIsNull(String name);
	
	public QueryInterface orWhere(String name,String operator,String value);
	
	public QueryInterface orWhere(String name,String operator,String[] values);
	
	public QueryInterface orWhere(String name,String operator,Integer[] values);
	
	public QueryInterface orWhere(String name,String operator,Double[] values);
	
	public QueryInterface orWhere(String name,String operator,Float[] values);
	
	public QueryInterface orWhere(String name,String operator,Integer value);
	
	public QueryInterface orWhere(String name,String operator,Float value);
	
	public QueryInterface orWhere(String name,String operator,Double value);

	public QueryInterface orWhere(String name,String operator,java.sql.Date value); 

	public QueryInterface orWhere(String name,String operator,String value,String format);

	public QueryInterface having(String name,String operator,String value);
	
	public QueryInterface having(String name,String operator,Integer value);
	
	public QueryInterface having(String name,String operator,Double value);
	
	public QueryInterface having(String name,String operator,Float value);

	public QueryInterface having(String name,String operator,java.sql.Date value); 

	public QueryInterface having(String name,String operator,String value,String format);
	
	public QueryInterface orderBy(String[] ...orderByNames);
	
	public QueryInterface orderByAsc(String...columns);
	
	public QueryInterface orderByDesc(String...columns);
	
	public QueryInterface groupBy(String ...groupByNames);
	
	public QueryInterface between(String name,Object value1,Object value2);
	
	public QueryInterface notBetween(String name,Object value1,Object value2);
	
	public QueryInterface exists(String value);	
	
	public QueryInterface notExists(String value);	

	public QueryInterface innerJoin(String table2,String key1,String key2);
	
	public QueryInterface leftJoin(String table2,String key1,String key2);
	
	public QueryInterface rightJoin(String table2,String key1,String key2);
	
	public QueryInterface outerJoin(String table2,String key1,String key2);
	
	public QueryInterface selfJoin(String table2,String key1,String key2);

	public QueryInterface innerJoin(String table1,String table2,String key1,String key2);
	
	public QueryInterface leftJoin(String table1,String table2,String key1,String key2);
	
	public QueryInterface rightJoin(String table1,String table2,String key1,String key2);
	
	public QueryInterface outerJoin(String table1,String table2,String key1,String key2);
	
	public QueryInterface selfJoin(String table1,String table2,String key1,String key2);
	
	public QueryInterface union();
	
	public QueryInterface unionAll();
//	@Deprecated
	public QueryInterface returning(String...retuerningKeys);
	
	public QueryInterface and();
	
	public QueryInterface or();
	
	public QueryInterface limit(int limit);
	
	public QueryInterface offset(int offset);
	
	public QueryInterface any(String subQuery);
	
	public void setShowError(boolean showError);

	public void setShowTracing(boolean showTracing);
}
