package nosi.core.webapp;
/**
 * @author Marcel Iekiny
 * Apr 18, 2017
 */
public interface Identity {
	
	public Object findIdentityById(int identityId);
	public int getIdentityId();
	public Object findIdentityByUsername(String username);
	public boolean validate(String inputPassword);
	public String getAuthenticationKey();
}
