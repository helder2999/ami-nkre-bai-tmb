package nosi.core.webapp.activit.rest.entities;

/**
 * Emanuel
 * 18 Jan 2018
 */
public class Rows  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Object[] value;
	
	public Rows(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object[] getValue() {
		return value;
	}
	public void setValue(Object[] value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Rows [name=" + name + ", value=" + value + "]";
	}
	
}
