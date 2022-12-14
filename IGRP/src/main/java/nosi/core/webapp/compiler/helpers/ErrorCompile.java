package nosi.core.webapp.compiler.helpers;

import java.io.Serializable;

/**
 * @author: Emanuel Pereira
 * 12 Nov 2017
 */
public class ErrorCompile  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error;
	private String warning;
	private long line;
	private String fileName;
	
	public ErrorCompile() {
	}
	
	public ErrorCompile(String error,String warning, long line, String fileName) {
		super();
		this.error = error;
		this.warning = warning;
		this.line = line;
		this.fileName = fileName;
	}
	
	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public long getLine() {
		return line;
	}
	public void setLine(long line) {
		this.line = line;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	@Override
	public String toString() {
		return "ErrorCompile [error=" + error + ", line=" + line + ", fileName=" + fileName + "]";
	}	
	
}
