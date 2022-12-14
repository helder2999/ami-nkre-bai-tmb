package nosi.core.webapp.databse.helpers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.persistence.Tuple;

import nosi.core.webapp.Core;

/**
 * Emanuel
 * 17 Apr 2018
 */
public class ResultSet {

	private String sql;
	private String error;
	private Object keyValue;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	@SuppressWarnings("unchecked")
	public Object getKeyValue() {
		if(keyValue instanceof Map) {
			return ((Map<String, Object>) keyValue).values().stream().findFirst().get();
		}
		return keyValue;
	}
	
	public void setKeyValue(Object keyValue) {
		this.keyValue = keyValue;
	}
	public boolean hasError() {
		return Core.isNotNull(this.getError());
	}	
	
	
	public Integer getInt(String name) {
		Object obj = this.getObject(name);
		return obj!=null?Core.toInt(obj.toString()): new Integer(-1);
	}

	public String getString(String name) {
		Object obj = this.getObject(name);
		return obj!=null?obj.toString(): "";
	}

	public Long getLong(String name) {
		Object obj = this.getObject(name);
		return obj!=null?Core.toLong(obj.toString()): new Long(-1);
	}

	public Short getShort(String name) {
		Object obj = this.getObject(name);
		return obj!=null?Core.toShort(obj.toString()): new Short((short)-1);
	}
	
	public Double getDouble(String name) {
		Object obj = this.getObject(name);
		return obj!=null?Core.toDouble(obj.toString()): new Double(-1);
	}

	public Float getFloat(String name) {
		Object obj = this.getObject(name);
		return obj!=null?Core.toFloat(obj.toString()): new Float(-1);
	}
	
	@SuppressWarnings("unchecked")
	public Object getObject(String name) {
		if(keyValue instanceof Map) {
			return ((Map<String, Object>) keyValue).get(name);
		}
		return "";
	}
	
	
	public static class Record{

		public Tuple Row;	
		public List<Record> RowList;
		private String sql;
		
		public Object getObject(String name) {
			if(this.Row!=null) {
				try {
					return this.Row.get(name);
				}catch(IllegalArgumentException e) {
					return null;
				}
			}
			return null;
		}
		
		public String getString(String name) {
			Object v = this.getObject(name);
			return v!=null?v.toString():null;
		}
		
		public Boolean getBoolean(String name) {
			Object v = this.getObject(name);
			return v!=null?new Boolean(""+v):new Boolean(false);
		}
		
		public Integer getInt(String name) {
			Object v = this.getObject(name);
			return v!=null? Core.toInt(v.toString()):null;
		}
		
		public Float getFloat(String name) {
			Object v = this.getObject(name);
			return v!=null? Core.toFloat(v.toString()):null;
		}
		
		public Double getDouble(String name) {
			Object v = this.getObject(name);
			return v!=null? Core.toDouble(v.toString()):null;
		}
		
		public Short getShort(String name) {
			Object v = this.getObject(name);
			return v!=null? Core.toShort(v.toString()):null;
		}
		
		public Long getLong(String name) {
			Object v = this.getObject(name);
			return v!=null? Core.toLong(v.toString()):null;
		}	

		public BigDecimal getBigDecimal(String name) {
			Object v = this.getObject(name);
			return v!=null? Core.toBigDecimal(v.toString()):null;
		}

		public Object getObject(int index) {
			if(this.Row!=null) {
				try {
					return this.Row.get(index);
				}catch(IllegalArgumentException e) {
					return null;
				}
			}
			return null;
		}
		
		public String getString(int index) {
			Object v = this.getObject(index);
			return v!=null?v.toString():null;
		}
		
		public Boolean getBoolean(int index) {
			Object v = this.getObject(index);
			return v!=null?new Boolean(""+v):new Boolean(false);
		}
		
		public Integer getInt(int index) {
			Object v = this.getObject(index);
			return v!=null? Core.toInt(v.toString()):null;
		}
		
		public Float getFloat(int index) {
			Object v = this.getObject(index);
			return v!=null? Core.toFloat(v.toString()):null;
		}
		
		public Double getDouble(int index) {
			Object v = this.getObject(index);
			return v!=null? Core.toDouble(v.toString()):null;
		}
		
		public Short getShort(int index) {
			Object v = this.getObject(index);
			return v!=null? Core.toShort(v.toString()):null;
		}
		
		public Long getLong(int index) {
			Object v = this.getObject(index);
			return v!=null? Core.toLong(v.toString()):null;
		}	

		public BigDecimal getBigDecimal(int index) {
			Object v = this.getObject(index);
			return v!=null? Core.toBigDecimal(v.toString()):null;
		}
		public void setSql(String sql) {
			this.sql = sql;
		}
		
		public String getSql() {
			return this.sql;
		}
	}

}
