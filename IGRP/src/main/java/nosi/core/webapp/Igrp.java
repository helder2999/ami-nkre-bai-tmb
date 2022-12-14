package nosi.core.webapp;
/**
 * @author Marcel Iekiny
 * Apr 14, 2017
 */
import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import nosi.core.i18n.I18nManager;
import nosi.core.servlet.IgrpServlet;
import nosi.core.webapp.security.EncrypDecrypt;


public final class Igrp{
	
	private static IgrpThreadLocal appInstance = new IgrpThreadLocal();

	private HttpServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private Controller controller;
	private String currentAppName;
	private String currentPageName;
	private String currentActionName;
	
	private String homeUrl;
	private String basePath;
	private String baseRoute;
	
	private boolean die;
	
	//Flash Message
	private FlashMessage flashMessage;
	
	// User component (Identity)
	private User user;
	
	// For i18n
	private I18nManager i18nManager;
	
	// For Igrp log toolbar 
	private IgrpLog log; 
	
	public static final String HOME_ROUTE = "igrp/home/index"; 
	
	private Igrp(){} // Private and empty default constructor ... allow Singleton class 
	
	public static Igrp getInstance() {
	    return appInstance.getCurrentInstance();
	 }
	
	 public static void set() {
		 appInstance.set(new Igrp());
	 }
	 
    public static void remove() {
    	appInstance.cleanUp();
    }
	 
	// Inicialize the web app components
	public Igrp init(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response){
		this.servlet = servlet;
		this.request = request;
		this.response = response;
		this.basePath = this.request.getContextPath();
		this.baseRoute = this.request.getServletPath();
		this.homeUrl = EncrypDecrypt.encrypt(HOME_ROUTE);
		// init. of others configuration 
		this.flashMessage = new FlashMessage(); // Flash Message instance 
		// For internacionalization purpose 
		this.i18nManager = new I18nManager();
		this.i18nManager.init(request);
		// For Igrp log toolbar 
		this.log = new IgrpLog();
		this.log.init(request);
		// User component (Identity)
		this.user = new User();
		this.user.init(request);
		
		return this;
	}
	
	public void run() throws IOException{ // run the web app 
		this.log.run();
		if(!this.die)
			this.runController();
		this.exit();
	}
	
	// Exit and Send the response. 
	private void exit(){
		new Controller().sendResponse();
		this.die = false;
	}
	
	public IgrpThreadLocal currentThreadLocal() {
		return appInstance;
	}
	
	private void runController() throws IOException{
		new Controller().initControllerNRunAction();
	}
	
	public HttpServlet getServlet() {
		return servlet;
	}

	public void setServlet(IgrpServlet servlet) {
		this.servlet = servlet;
	}
	
	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Controller getCurrentController(){
		return this.controller;
	}
	
	public void setCurrentController(Controller controller){
		this.controller = controller;
	}
	
	public HttpServletResponse getResponse(){
		return this.response;
	}
	
	public void setResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public HttpServletRequest getRequest(){
		return this.request;
	}
	
	public String getBaseRoute(){
		return this.baseRoute;
	}
	
	public String getBasePath(){
		return this.basePath;
	}
	
	public String getHomeUrl(){
		return this.homeUrl;
	}
	
	public FlashMessage getFlashMessage(){
		return this.flashMessage;
	}

	
	public String getCurrentAppName() {
		return Core.isNotNull(currentAppName)?currentAppName:"igrp";
	}

	public void setCurrentAppName(String currentAppName) {
		this.currentAppName = currentAppName;
	}

	public String getCurrentPageName() {
		return Core.isNotNull(currentPageName)?currentPageName:"home";
	}

	public void setCurrentPageName(String currentPageName) {
		this.currentPageName = currentPageName;
	}

	public String getCurrentActionName() {
		return Core.isNotNull(currentActionName)?currentActionName:"index";
	}

	public void setCurrentActionName(String currentActionName) {
		this.currentActionName = currentActionName;
	}
	
	public User getUser(){
		return this.user;
	}

	public void die(){
		this.die = true;
	}
	
	public I18nManager getI18nManager() {	
		return i18nManager;
	}
	
	public IgrpLog getLog() {
		return log;
	}
	
	public String generateCsrfToken() {
		byte[] buffer = new byte[50];
		new java.security.SecureRandom().nextBytes(buffer);
	    String result = DatatypeConverter.printHexBinary(buffer);
	    String cookieName = this.request.getParameter("r").replace("/", "-");
	    Cookie cookie = new Cookie(cookieName, result);
	    this.response.addCookie(cookie);
		return result;
	}
	
}
