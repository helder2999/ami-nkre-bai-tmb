package nosi.core.gui.components;
/**
 * Emanuel
 * 3 Apr 2019
 */
public class IGRPChart3D{


	private String eixoX;
	private Object eixoZ;
	private String eixoY;
	
	public IGRPChart3D(String eixoX, String eixoY, Object eixoZ) {
		this.eixoX = eixoX;
		this.eixoY = eixoY;
		this.eixoZ = eixoZ;
	}

	public IGRPChart3D() {
		
	}
	
	
	public String getEixoX() {
		return eixoX;
	}

	public void setEixoX(String eixoX) {
		this.eixoX = eixoX;
	}

	public Object getEixoZ() {
		return eixoZ;
	}

	public void setEixoZ(Object eixoZ) {
		this.eixoZ = eixoZ;
	}

	public String getEixoY() {
		return eixoY;
	}

	public void setEixoY(String eixoY) {
		this.eixoY = eixoY;
	}

	@Override
	public String toString() {
		return "IGRPChart3D [eixoX=" + eixoX + ", eixoZ=" + eixoZ + ", eixoY=" + eixoY + "]";
	}
	
	
}
