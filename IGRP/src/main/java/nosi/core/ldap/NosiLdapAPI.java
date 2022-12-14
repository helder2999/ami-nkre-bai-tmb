package nosi.core.ldap;

/**
 * @author Adilson Rodrigues
 * May 29, 2017
 */

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import java.util.Hashtable;
import java.util.List;

public class NosiLdapAPI {

	private String l_ldap_url;
	private String l_ldap_username;
	private String l_ldap_password;
	private String l_ldap_base;
	private String l_ldap_authenticationFilter;
	private String l_ldap_entryDN;

	private String error;

	private Hashtable<String, String> props;

	public NosiLdapAPI() {
		super();
	}

	public NosiLdapAPI(String l_ldap_url, String l_ldap_username, String l_ldap_password, String l_ldap_base, String l_ldap_authenticationFilter, String l_ldap_entryDN) {
		super();
		this.l_ldap_url = l_ldap_url;
		this.l_ldap_username = l_ldap_username;
		this.l_ldap_password = l_ldap_password;
		this.l_ldap_base = l_ldap_base;
		this.l_ldap_authenticationFilter  = l_ldap_authenticationFilter;
		this.l_ldap_entryDN = l_ldap_entryDN;
	}

	public String getL_ldap_url() {
		return l_ldap_url;
	}

	public void setL_ldap_url(String l_ldap_url) {
		this.l_ldap_url = l_ldap_url;
	}

	public String getL_ldap_username() {
		return l_ldap_username;
	}

	public void setL_ldap_username(String l_ldap_username) {
		this.l_ldap_username = l_ldap_username;
	}

	public String getL_ldap_password() {
		return l_ldap_password;
	}

	public void setL_ldap_password(String l_ldap_password) {
		this.l_ldap_password = l_ldap_password;
	}

	public String getL_ldap_base() {
		return l_ldap_base;
	}

	public void setL_ldap_base(String l_ldap_base) {
		this.l_ldap_base = l_ldap_base;
	}
	
	public String getL_ldap_authenticationFilter() {
		return l_ldap_authenticationFilter;
	}

	public void setL_ldap_authenticationFilter(String l_ldap_authenticationFilter) {
		this.l_ldap_authenticationFilter = l_ldap_authenticationFilter;
	}

	public String getL_ldap_entryDN() {
		return l_ldap_entryDN;
	}

	public void setL_ldap_entryDN(String l_ldap_entryDN) {
		this.l_ldap_entryDN = l_ldap_entryDN;
	}

	private void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return this.error;
	}

	private InitialDirContext ldapContext(String user, String password) throws NamingException {
		props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.PROVIDER_URL, this.getL_ldap_url());
		props.put(Context.SECURITY_AUTHENTICATION, "simple");
		props.put(Context.SECURITY_PRINCIPAL, user);
		props.put(Context.SECURITY_CREDENTIALS, password);
		props.put(Context.REFERRAL, "follow"); 
		
		InitialDirContext context = new InitialDirContext(props);
		return context;
	}

	public String getDistinguishedName(String pUsername, ArrayList<LdapPerson> ldapPersons) {
		InitialDirContext context;
		String distinguishedName = "";
		try {
			context = this.ldapContext(this.getL_ldap_username(), this.getL_ldap_password());
			SearchControls ctrls = new SearchControls();
			ctrls.setReturningAttributes(new String[] { "givenName", "sn", "memberOf", "mail", "name" });
			ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			String filter = this.l_ldap_authenticationFilter.replaceAll(":_placeholder", pUsername);
			
			NamingEnumeration<javax.naming.directory.SearchResult> answers = context.search(this.getL_ldap_base(), filter, ctrls);
			
			if (answers.hasMore()) {
				javax.naming.directory.SearchResult result = answers.nextElement();
				distinguishedName = result.getNameInNamespace();
			}
			
			// same code below ... 
			answers = context.search(this.getL_ldap_base(), filter, ctrls);
			SerializeAttribs(answers, ldapPersons); 
			//System.out.println("Internal: " + ldapPersons.size());
			//
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			this.setError(e.getMessage());
			e.printStackTrace();

		}
		//System.out.println("DistinguishedName: " + distinguishedName);
		
		return distinguishedName;
	}
	
	public String getDistinguishedName(String pUsername) {
		return getDistinguishedName(pUsername, new ArrayList<LdapPerson>());
	}
	
	
	private void SerializeAttribs(NamingEnumeration<?> results, ArrayList<LdapPerson> personArray) {
		LdapPerson p = null;
		try {
			while (results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				Attributes attrs = sr.getAttributes();
				p = new LdapPerson();

				for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();
					String name = attr.getID();
					String value = "";
					for (NamingEnumeration e = attr.getAll(); e.hasMore(); value += e.next())
						;

					switch (name.toLowerCase()) {
					case "uid":
						p.setUid(value);
						break;
					case "sn":
						p.setSn(value);
						break;
					case "mail":
						p.setMail(value);
						break;

					case "proxyaddresses":
						p.setProxyAddresses(value);
						break;
					case "name":
						p.setName(value);
						break;
					case "accountexpires":
						p.setAccountExpires(value);
						break;
					case "lastlogon":
						p.setLastLogon(value);
						break;
					case "lastlogoff":
						p.setLastLogoff(value);
						break;
					case "cn":
						p.setCn(value);
						break;
					case "samaccounttype":
						p.setsAMAccountType(value);
						break;
					case "givenname":
						p.setGivenName(value);
						break;
					case "displayname":
						p.setDisplayName(value);
						break;

					case "userprincipalname":
						p.setUserPrincipalName(value);
						break;

					case "pwdlastset":
						p.setPwdLastSet(value);
						break;

					case "lastlogontimestamp":
						p.setLastLogonTimestamp(value);
						break;

					case "mailnickname":
						p.setMailNickname(value);
						break;

					case "distinguishedname":
						p.setDistinguishedName(value);
						break;

					case "samaccountname":
						p.setsAMAccountName(value);
						break;
					}
				}
				p.setName(p.getName() != null ? p.getName() : (p.getGivenName() != null ? p.getGivenName() : p.getSn()));
				personArray.add(p);
			}
		} catch (NamingException e) {
			this.setError(e.getMessage());
		}
	}
	
	
	
	
	
	private ArrayList<LdapPerson> SerializeAttribs(NamingEnumeration<?> results) {
		ArrayList<LdapPerson> personArray = new ArrayList<LdapPerson>();
		LdapPerson p = null;

		try {
			while (results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				Attributes attrs = sr.getAttributes();
				p = new LdapPerson();

				for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();
					String name = attr.getID();
					String value = "";
					for (NamingEnumeration e = attr.getAll(); e.hasMore(); value += e.next())
						;

					switch (name.toLowerCase()) {
					case "uid":
						p.setUid(value);
						break;
					case "sn":
						p.setSn(value);
						break;
					case "mail":
						p.setMail(value);
						break;

					case "proxyaddresses":
						p.setProxyAddresses(value);
						break;
					case "name":
						p.setName(value);
						break;
					case "accountexpires":
						p.setAccountExpires(value);
						break;
					case "lastlogon":
						p.setLastLogon(value);
						break;
					case "lastlogoff":
						p.setLastLogoff(value);
						break;
					case "cn":
						p.setCn(value);
						break;
					case "samaccounttype":
						p.setsAMAccountType(value);
						break;
					case "givenname":
						p.setGivenName(value);
						break;
					case "displayname":
						p.setDisplayName(value);
						break;

					case "userprincipalname":
						p.setUserPrincipalName(value);
						break;

					case "pwdlastset":
						p.setPwdLastSet(value);
						break;

					case "lastlogontimestamp":
						p.setLastLogonTimestamp(value);
						break;

					case "mailnickname":
						p.setMailNickname(value);
						break;

					case "distinguishedname":
						p.setDistinguishedName(value);
						break;

					case "samaccountname":
						p.setsAMAccountName(value);
						break;
					}
				}
				p.setName(p.getName() != null ? p.getName() : (p.getGivenName() != null ? p.getGivenName() : p.getSn()));
				personArray.add(p);
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			this.setError(e.getMessage());
			//e.printStackTrace();
		}

		return personArray;
	}

	public boolean validateLogin(String pUsername, String pPassword, ArrayList<LdapPerson> ldapPersons) {
		String user = this.getDistinguishedName(pUsername, ldapPersons);
		InitialDirContext context;
		boolean validate = false;
		if (!user.equals("")) {
			try {
				context = this.ldapContext(user, pPassword);
				validate = true;
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				this.setError(e.getMessage());
				e.printStackTrace();
			}
		}
		return validate;
	}

	public ArrayList<LdapPerson> getUser(String pEmail) {
		ArrayList<LdapPerson> p = null;
		InitialDirContext context;

		try {
			context = this.ldapContext(this.getL_ldap_username(), this.getL_ldap_password());

			SearchControls ctrls = new SearchControls();
			ctrls.setReturningAttributes(new String[] { "*" });
			ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String filter = "(&(mail=" + pEmail + "))";
			NamingEnumeration<?> results = context.search(this.getL_ldap_base(), filter, ctrls);
			
			if (!results.hasMore()) {
				
				filter = "(&(proxyAddresses=smtp:" + pEmail + "))";
				results = context.search(this.getL_ldap_base(), filter, ctrls);
			}
			if (!results.hasMore()) {
				
				filter = "(&(proxyAddresses=sip:" + pEmail + "))";
				results = context.search(this.getL_ldap_base(), filter, ctrls);
			}

			if (results.hasMore()) {
				p = this.SerializeAttribs(results);
			} else
				this.setError("error searching for " + pEmail);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			this.setError(e.getMessage());
			e.printStackTrace();
		}
		return p;
	}
	
	 public void createUser(LdapPerson person) {
		 // get a handle to an Initial DirContext
         InitialDirContext ctx = null;
         try {
              ctx = ldapContext(l_ldap_username, l_ldap_password);
              } 
         catch (NamingException e1) {
	          e1.printStackTrace();
	          this.setError(e1.getMessage());
	          return;
         }
         
        String entryDN = this.l_ldap_entryDN.replaceAll(":_placeholder", person.getUid()) + "," + this.l_ldap_base;
         
         // entry's attributes
         Attribute cn = new BasicAttribute("cn", person.getCn());
         Attribute sn = new BasicAttribute("sn", person.getSn());
         Attribute uid = new BasicAttribute("uid", person.getUid());
         Attribute mail = new BasicAttribute("mail", person.getMail());
         Attribute pass =  new BasicAttribute("userPassword","Pa$$w0rd");
         Attribute oc = new BasicAttribute("objectClass");
         Attribute dn = new BasicAttribute("displayName", person.getDisplayName());
         Attribute gn = new BasicAttribute("givenName", person.getGivenName());
         oc.add("organizationalPerson");
         oc.add("inetOrgPerson");
         oc.add("person");
         oc.add("top");
         try {
        	 // build the entry
        	 BasicAttributes entry = new BasicAttributes();
        	 entry.put(cn);
        	 entry.put(sn);
        	 entry.put(mail);
        	 entry.put(pass);
        	 entry.put(uid);
        	 entry.put(oc);
        	 entry.put(dn);
        	 entry.put(gn);
        	 // Add the entry
        	 ctx.createSubcontext(entryDN, entry);
        	 } catch (NamingException e) {
        		 this.setError(e.getMessage());
        	}
         }
	 
	 public void updateUser(LdapPerson person, String id) {
		 // get a handle to an Initial DirContext
         InitialDirContext ctx = null;
         try {
              ctx = ldapContext(l_ldap_username, l_ldap_password);
              } 
         catch (NamingException e1) {
	          e1.printStackTrace();
	          this.setError(e1.getMessage());
	          return;
         }
         // entry's DN
         String entryDN = this.getDistinguishedName(id); 
         
         // entry's attributes
         Attribute cn = new BasicAttribute("cn", person.getCn());
         Attribute sn = new BasicAttribute("sn", person.getSn());
         //Attribute uid = new BasicAttribute("uid", person.getUid());
         Attribute mail = new BasicAttribute("mail", person.getMail());
        // Attribute pass =  new BasicAttribute("userPassword",person.getPwdLastSet());
         Attribute oc = new BasicAttribute("objectClass");
         Attribute dn = new BasicAttribute("displayName", person.getDisplayName());
         Attribute gn = new BasicAttribute("givenName", person.getGivenName());
         oc.add("organizationalPerson");
         oc.add("inetOrgPerson");
         oc.add("person");
         oc.add("top");
         try {
        	 // build the entry
        	 BasicAttributes entry = new BasicAttributes();
        	 entry.put(cn);
        	 entry.put(sn);
        	 entry.put(mail);
        	 //entry.put(uid);
        	 entry.put(oc);
        	 entry.put(dn);
        	 entry.put(gn);
        	// entry.put(pass);
        	 // Add the entry 
        	 ctx.modifyAttributes(entryDN, DirContext.REPLACE_ATTRIBUTE, entry);
        	 } catch (NamingException e) {
        		 this.setError(e.getMessage());
        	}
         }
	 
	 public void renameEntry(String oldName, String newName) {
		 // get a handle to an Initial DirContext
         InitialDirContext ctx = null;
         try {
              ctx = ldapContext(l_ldap_username, l_ldap_password);
              } 
         catch (NamingException e1) {
	          e1.printStackTrace();
	          this.setError(e1.getMessage());
	          return;
         }
         try {
			ctx.rename(oldName, newName);
		} catch (NamingException e) {
			this.setError(e.getMessage());
			e.printStackTrace();
		}
	 }
	 
	 public void changePassword(LdapPerson person) {
		 // get a handle to an Initial DirContext 
         InitialDirContext ctx = null;
         try {
              ctx = ldapContext(l_ldap_username, l_ldap_password);
              } 
         catch (NamingException e1) {
	          e1.printStackTrace();
	          this.setError(e1.getMessage());
	          return;
         }
         // entry's DN
         String entryDN = this.getDistinguishedName(person.getUid()); 
         // entry's attributes
         Attribute pass =  new BasicAttribute("userPassword",person.getPwdLastSet());
         try {
        	 // build the entry
        	 BasicAttributes entry = new BasicAttributes();
        	 entry.put(pass);
        	 // Add the entry 
        	 ctx.modifyAttributes(entryDN, DirContext.REPLACE_ATTRIBUTE, entry);
        	 } catch (NamingException e) {
        		 this.setError(e.getMessage());
        	}
         }
	 
	 public void deleteUser(String id) {
	     InitialDirContext ctx = null;
	     try {
	    	 ctx = ldapContext(l_ldap_username, l_ldap_password);
	    	 String dn = this.getDistinguishedName(id);
	    	 ctx.destroySubcontext(dn);
	     } 
	     catch (NamingException e1) {
	          e1.printStackTrace();
	          this.setError(e1.getMessage());
	     }finally {
	    	 try {
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
	     }
	 }
	 
	 public LdapPerson getUserLastInfo(String pEmail) {
		List<LdapPerson> l =  this.getUser(pEmail);
		try {
			return l.get(l.size() - 1);
		}catch(Exception e) { // NullPointerException 
			// do nothing yet 
		}
		return null;
	 }
	 
}
