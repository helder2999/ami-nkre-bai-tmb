package nosi.core.webapp.security;
/**
 * @author Emanuel Pereira
 * May 29, 2017
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;

import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.helpers.ApplicationPermition;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Menu;
import nosi.webapps.igrp.dao.Organization;
import nosi.webapps.igrp.dao.Profile;
import nosi.webapps.igrp.dao.ProfileType;
import nosi.webapps.igrp.dao.Share;
import nosi.webapps.igrp.dao.Transaction;
import nosi.webapps.igrp.dao.User;

public class Permission {
	
	public static final String ENCODE = "UTF-8"; 
	public static final int MAX_AGE = 60*60*24;//24h 
	
	private ApplicationPermition applicationPermition; 
	
	public boolean isPermition(String app, String appP, String page, String action){ // check permission on app 
		if(Igrp.getInstance().getUser() != null && Igrp.getInstance().getUser().isAuthenticated()){ 
			if(PagesScapePermission.PAGES_SHAREDS.contains((appP + "/" + page + "/" + action).toLowerCase())) 
				return true; 
			if(app.equals(appP) || appP.equals("igrp") || appP.equals("igrp_studio")) 
				return (new Application().getPermissionApp(app) && new Menu().getPermissionMen(appP, page)); 
			else { 
				if(appP.equals("tutorial")) // default page purpose 
					return true; 
				return new Share().getPermissionPage(app,appP,new Action().findByPage(page, appP).getId()); 
			}
		}
		return PagesScapePermission.PAGES_WIDTHOUT_LOGIN.contains((appP+"/"+page+"/"+action).toLowerCase());
	}
	
	public  boolean isPermission(String transaction){
		return new Transaction().getPermission(transaction);
	}

	public  void changeOrgAndProfile(String dad){
		Application app = Core.findApplicationByDad(dad);
		ProfileType profType = new ProfileType();
		Organization org = new Organization();
		Profile prof = new Profile();
		if(app!=null){			
			int id_user = 0;
			
			try {// eliminar 
				id_user = Core.getCurrentUser().getIdentityId();
			}catch(Exception e) {
				
			}
			
			if(app.getPermissionApp(dad)){
				prof = (Profile) prof.getByUserPerfil(id_user,app.getId());
				if(prof!=null){
					 org.setId(prof.getOrganization().getId());
					 profType.setId(prof.getProfileType().getId());
					 ApplicationPermition appP = this.getApplicationPermition(dad);
					 if(appP==null) {
						 appP = new ApplicationPermition(app.getId(),dad, org!=null?org.getId():null, profType!=null ? profType.getId():null,prof!=null && prof.getOrganization()!=null? prof.getOrganization().getCode():null,prof!=null && prof.getProfileType()!=null?prof.getProfileType().getCode():null);
					}
					 this.applicationPermition = appP;
					 this.setCookie(appP);
				}
			}
		}
		
		((User)Igrp.getInstance().getUser().getIdentity()).setAplicacao(app);
		((User)Igrp.getInstance().getUser().getIdentity()).setProfile(profType);
		((User)Igrp.getInstance().getUser().getIdentity()).setOrganica(org);
		
		if(Igrp.getInstance().getRequest().getSession()!=null && app!=null) {
			ApplicationPermition appP = this.getApplicationPermition(dad);
			if(appP==null) {
				 appP = new ApplicationPermition(app.getId(),dad, org!=null?org.getId():null, profType!=null ? profType.getId():null,prof!=null && prof.getOrganization()!=null? prof.getOrganization().getCode():null,prof!=null && prof.getProfileType()!=null?prof.getProfileType().getCode():null);
			}
			this.applicationPermition = appP; 
			this.setCookie(appP); 
		}
	}
	
	public void setCookie(ApplicationPermition appP) {
		try {
			String json = Core.toJson(appP);
			Cookie cookie = new Cookie(appP.getDad(), URLEncoder.encode( json,ENCODE));
			cookie.setMaxAge(MAX_AGE);
			Igrp.getInstance().getResponse().addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public  String getCurrentEnv() {
		ApplicationPermition appP = this.getApplicationPermition();
		return appP!=null && !appP.getDad().equals("")?appP.getDad():Core.getCurrentDadParam();
	}
	
	public  Integer getCurrentPerfilId() {
		ApplicationPermition appP = this.getApplicationPermition();
		if(appP!=null && appP.getProfId()!=null)
			return appP.getProfId();
		return -1;
	}

	public  Integer getCurrentOrganization() {
		ApplicationPermition appP = this.getApplicationPermition();
		if(appP!=null && appP.getOgrId()!=null)
			return appP.getOgrId();
		return -1;
	}
	
	public  Integer getCurrentEnvId() {
		ApplicationPermition appP = this.getApplicationPermition();
		return appP!=null?appP.getAppId():-1;
	}
	
	public  String getCurrentPerfilCode() {
		ApplicationPermition appP = this.getApplicationPermition();
		if(appP!=null)
			return appP.getCode_profile();
		return "";
	}

	public  String getCurrentOrganizationCode() {
		ApplicationPermition appP = this.getApplicationPermition();
		if(appP!=null)
			return appP.getCode_organization();
		return "";
	}
	
	public ApplicationPermition getApplicationPermition() {
		String dad = Core.getParam("dad");
		return this.getApplicationPermition(dad);
	}
	
	public ApplicationPermition getApplicationPermition(String dad) {
		Optional<Cookie> cookies = Igrp.getInstance().getRequest().getCookies()!=null?Arrays.asList(Igrp.getInstance().getRequest().getCookies()).stream().filter(c -> c.getName().equalsIgnoreCase(dad)).findFirst():null;
		String json = (cookies!=null && cookies.isPresent())?cookies.get().getValue():null;
		if(json!=null) {
			try {
				json = URLDecoder.decode(json,ENCODE);
				if(Core.isNotNull(json)) {
					ApplicationPermition appP = (ApplicationPermition) Core.fromJson(json, ApplicationPermition.class);
					return appP;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public ApplicationPermition getApplicationPermitionBeforeCookie() {
		return applicationPermition;
	}
	
}