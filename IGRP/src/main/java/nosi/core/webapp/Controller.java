package nosi.core.webapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import nosi.core.config.Config;
import nosi.core.config.ConfigApp;
import nosi.core.exception.ServerErrorHttpException;
import nosi.core.gui.components.IGRPForm;
import nosi.core.gui.components.IGRPMessage;
import nosi.core.gui.components.IGRPView;
import nosi.core.gui.fields.HiddenField;
import nosi.core.gui.page.Page;
import nosi.core.webapp.activit.rest.entities.TaskService;
import nosi.core.webapp.activit.rest.entities.TaskServiceQuery;
import nosi.core.webapp.activit.rest.entities.TaskVariableDetails;
import nosi.core.webapp.activit.rest.services.TaskServiceRest;
import nosi.core.webapp.bpmn.BPMNButton;
import nosi.core.webapp.bpmn.BPMNConstants;
import nosi.core.webapp.bpmn.BPMNHelper;
import nosi.core.webapp.bpmn.BPMNTimeLine;
import nosi.core.webapp.bpmn.DisplayDocmentType;
import nosi.core.webapp.bpmn.InterfaceBPMNTask;
import nosi.core.webapp.bpmn.RuntimeTask;
import nosi.core.webapp.bpmn.ViewTaskDetails;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.Route;
import nosi.core.webapp.helpers.StringHelper;
import nosi.core.webapp.security.SecurtyCallPage;
import nosi.core.webapp.webservices.helpers.FileRest;
import nosi.core.xml.XMLWritter;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.ProfileType;
import nosi.webapps.igrp.dao.TipoDocumentoEtapa;

/**
 *
 * @author Marcel Iekiny Apr 15, 2017
 */
public class Controller {
	
	protected ConfigApp configApp = ConfigApp.getInstance();
	
	private QueryString<String, Object> queryString = new QueryString<>();
	private View view;

	protected String format = Response.FORMAT_XML;
	protected String encoding = Response.CHARSET_UTF_8;
	protected boolean isNoCached = false;

	private Response responseWrapper;
	private String qs = "";

	public Response getResponseWrapper() {
		return responseWrapper;
	}

	public void setResponseWrapper(Response responseWrapper) {
		this.responseWrapper = responseWrapper;
	}

	protected void restartQueryString() {
		queryString = new QueryString<>();
	}

	/**
	 * Get all the r Params, and in a forEach does this.addQueryString of them all
	 *
	 * @return
	 */
	protected QueryString<String, Object> loadQueryString() {
		Core.getParameters().entrySet().stream().filter(p -> !p.getKey().equals("r"))
				.forEach(p -> this.addQueryString(p.getKey(), p.getValue() != null ? p.getValue()[0] : ""));
		return this.queryString;
	}

	public QueryString<String, Object> queryString() {
		return this.queryString;
	}

	public QueryString<String, Object> addQueryString(String name, Object value) {
		if (value instanceof Object[]) {
			for (Object v : (Object[]) value) {
				this.queryString().addQueryString(name, v);
			}
			return this.queryString();
		}
		return this.queryString().addQueryString(name, value);
	}

	public QueryString<String, Object> removeQueryString(String key) {
		return this.queryString.removeQueryString(key);
	}

	protected String[] getQueryArray(String name) {
		return Core.getParamArray(name);
	}

	protected String getQueryString(String name) {
		return Core.getParam(name);
	}

	protected Integer getQueryStringInteger(String name) {
		return Core.getParamInt(name);
	}

	protected Long getQueryStringLong(String name) {
		return Core.getParamLong(name);
	}

	protected Short getQueryStringShort(String name) {
		return Core.getParamShort(name);
	}

	protected Float getQueryStringFloat(String name) {
		return Core.getParamFloat(name);
	}

	protected Double getQueryStringDouble(String name) {
		return Core.getParamDouble(name);
	}

	public Controller() {}

	protected final Response renderView(View view, boolean isRenderPartial) throws IOException { 
		Response resp = new Response();
		this.view = view;
		view.setContext(this); // associa controller ao view
		this.view.render();
		if (!isRenderPartial)
			this.view.addToPage(this.view.addFieldToFormHidden());
		String result = this.view.getPage().renderContent(!isRenderPartial);
		resp.setType(1);
		resp.setCharacterEncoding(Response.CHARSET_UTF_8);
		resp.setContentType(Response.FORMAT_XML);
		resp.setHttpStatus(HttpStatus.STATUS_200);
		if (this.isNoCached) {
			resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			resp.setDateHeader("Expires", 0); // Proxies.
		}
		resp.setContent(result);
		return resp;
	}

	public Response renderView(String app, String page, View v) throws IOException {
		return this.renderView(app, page, v, null, null);
	}

	public Response renderView(String app, String page, View v, InterfaceBPMNTask bpmn, RuntimeTask runtimeTask)
			throws IOException {
		String m = new IGRPMessage().toString();
		this.view = v;
		Action ac = new Action().find().andWhere("application.dad", "=", app).andWhere("page", "=", Page.resolvePageName(page)).one();
		Response resp = new Response();
		this.view.setContext(this);
		this.view.render();
		resp.setType(1);
		resp.setCharacterEncoding(Response.CHARSET_UTF_8);
		resp.setContentType(Response.FORMAT_XML);
		resp.setHttpStatus(HttpStatus.STATUS_200);
		if (this.isNoCached) {
			resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			resp.setDateHeader("Expires", 0); // Proxies.
		}
		this.view.addToPage(this.view.addFieldToFormHidden());
		String content = this.view.getPage().renderContent(false);
		content = BPMNButton.removeXMLButton(content);
		XMLWritter xml = new XMLWritter("rows", this.getConfig().getLinkPageXsl(ac), "utf-8");
		xml.addXml(this.getConfig().getHeader(null));
		xml.startElement("content");
		xml.writeAttribute("type", ""); 
		if (Core.isNotNull(runtimeTask)) {
			TaskServiceRest taskService = new TaskServiceRest();
			String taskId = runtimeTask.getTask().getId();
			if (runtimeTask.isSaveButton()) {
				xml.addXml(BPMNButton.generateButtonBack().toString());
				xml.addXml(BPMNButton.generateButtonTask("igrp", ac.getApplication().getId(), "ExecucaoTarefas",
						"process-task", taskId, this.queryString).toString());
			}
			ViewTaskDetails details = this.getTaskDetails(taskService, taskId);
			BPMNTimeLine bpmnTimeLine = new BPMNTimeLine();
			xml.addXml(bpmnTimeLine.get().toString());
			xml.addXml(this.getTaskViewDetails(details));
			xml.addXml(content);
			xml.addXml(this.getDocument(runtimeTask, bpmn, ac, details.getUserName())); 
			if (m != null) 
				xml.addXml(m);
			IGRPForm formHidden = new IGRPForm("hidden_form_igrp");
			HiddenField field = new HiddenField("env_frm_url");
			String pageTask = "ExecucaoTarefas";
			String value = Route.getResolveUrl("igrp",pageTask, "executar_button_minha_tarefas&prm_app=igrp&prm_page="+pageTask+"&p_id="+runtimeTask.getTask().getId());
			field.propertie().add("value", value).add("name","p_env_frm_url").add("type","hidden").add("maxlength","250").add("java-type","").add("tag","env_frm_url");
			field.setValue(value);
			formHidden.addField(field);
			xml.addXml(formHidden.toString());
		}
		xml.endElement();
		resp.setContent(xml.toString());
		return resp;
	}

	private String getTaskViewDetails(ViewTaskDetails details) {
		IGRPView viewTD = ViewTaskDetails.get(details);
		return viewTD.toString();
	}

	private ViewTaskDetails getTaskDetails(TaskServiceRest taskService, String taskId) {
		ViewTaskDetails details = new ViewTaskDetails();
		Object obj = Core.getAttributeObject(BPMNConstants.PRM_TASK_OBJ, true);
		if (Core.isNotNull(obj)) {
			TaskServiceQuery task = (TaskServiceQuery) obj;
			List<TaskVariableDetails> v = taskService.queryHistoryTaskVariables(task.getId());
			String prof_id = v.stream().filter(var -> var.getPropertyId().equals("profile")).findFirst().get()
					.getPropertyValue();
			ProfileType prof = new ProfileType().findOne(Core.toInt(prof_id));
			String userName = v.stream().filter(var -> var.getPropertyId().equals("userName")).findFirst().get()
					.getPropertyValue();
			details.setnProcess(task.getProcessInstanceId());
			details.setnTask(taskId);
			details.setProcessName(task.getProcessName());
			details.setTaskName(task.getName());
			details.setEndTime(task.getEndTime());
			details.setOrg(prof.getOrganization().getName());
			details.setProfile(prof.getDescr());
			details.setStartTime(task.getStartTime());
			details.setUserName(userName);
			return details;
		}
		TaskService task = new TaskServiceRest().getTask(taskId);
		if (task != null) {
			details.setnProcess(task.getProcessInstanceId());
			details.setnTask(taskId);
			details.setProcessName(task.getProcessName());
			details.setTaskName(task.getName());
		}
		return details;
	}
	
	private String getDocument(RuntimeTask runtimeTak, InterfaceBPMNTask bpmn, Action action, String userName) {
		if (bpmn == null)
			return BPMNHelper.addFileSeparator(action.getApplication().getDad(),
					runtimeTak.getTask().getProcessDefinitionKey(), runtimeTak.getTask().getTaskDefinitionKey(), null);
		
		DisplayDocmentType display = new DisplayDocmentType();
		display.setUserName(userName);
		display.setListDocmentType(bpmn.getInputDocumentType());
		
		if (runtimeTak.isDetails())
			display.setShowInputFile(false);
		try {
			Core.setAttribute(BPMNConstants.PRM_TASK_DEFINITION, runtimeTak.getTask().getTaskDefinitionKey());
			String packageName = "nosi.webapps." + action.getApplication().getDad().toLowerCase() + ".process." + runtimeTak.getTask().getProcessDefinitionKey().toLowerCase();
			Class<?> c = Class.forName(packageName + "." + BPMNConstants.PREFIX_TASK + runtimeTak.getTask().getTaskDefinitionKey() + "Controller");
			Method method = c.getMethod("getOutputDocumentType");
			@SuppressWarnings("unchecked")
			List<TipoDocumentoEtapa> listDocOutput = (List<TipoDocumentoEtapa>) method.invoke(c.newInstance());
			display.addListDocumentType(listDocOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return display.displayInputNOutputDocsInDistinctFormList();
	}
	
	protected final Response renderView(View view) throws IOException {
		return this.renderView(view, false);
	}

	/**
	 * Calls this.xSend
	 *
	 * @param file
	 * @param name
	 * @param contentType
	 * @param download
	 * @return
	 */
	public final Response render(byte[] file, String name, String contentType, boolean download) {
		return this.xSend(file, name, contentType, download);
	}

	/**
	 * Calls this.xSend
	 * 
	 * @param file
	 * @param name
	 * @param contentType
	 * @param download
	 * @param url
	 * @return
	 */
	Response render(byte[] file, String name, String contentType, boolean download, String url) {
		return this.xSend(file, name, contentType, download, url);

	}

	protected final Response renderView(String content) {
		Response resp = new Response();
		resp.setType(1);
		resp.setCharacterEncoding(this.encoding);
		resp.setContentType(this.format);
		resp.setContent(content);
		return resp;
	}

	private final Response redirect_(String url) {
		Response resp = new Response();
		resp.setType(2);
		resp.setCharacterEncoding(Response.CHARSET_UTF_8);
		resp.setContentType(this.format);
		resp.setUrl(url);
		resp.setHttpStatus(HttpStatus.STATUS_200);
		return resp;
	}

	protected final Response redirect(String app, String page, String action, Model model,
			QueryString<String, Object> queryString) throws IOException {
		Igrp.getInstance().getRequest().getSession().setAttribute(Model.ATTRIBUTE_NAME_REQUEST, model);
		return this.redirect(app, page, action, queryString);
	}

	protected final Response redirect(String app, String page, String action, QueryString<String, Object> queryString)
			throws IOException {
		redirectAddQueryStrFuncion(action, queryString);
		return this.redirect_(Route.toUrl(app, page, action, qs));
	}


	protected final Response redirect(String app, String page, String action, QueryString<String, Object> queryString, int isPublic)
			throws IOException {
		redirectAddQueryStrFuncion(action, queryString);
		return this.redirect_(Route.toUrl(app, page, action, qs,isPublic));
	}

	private void redirectAddQueryStrFuncion(String action, QueryString<String, Object> queryString) {
		if (queryString.getValues("dad") == null && !action.contains("dad"))
			queryString.addQueryString("dad", Core.getParam("dad"));
		String jsonLookup = Core.getParam("jsonLookup");
		if (Core.isNotNull(jsonLookup)) {
			queryString.addQueryString("jsonLookup", jsonLookup);
		}
		this.setQueryString(queryString);
		Map<String, String[]> paramsName = Core.getParameters();
		paramsName.entrySet().stream().filter(param -> param.getKey().startsWith("p_fwl_"))
				.filter(param -> !param.getKey().equalsIgnoreCase("p_fwl_search"))
				.forEach(param -> {				
						qs += "&" + param.getKey() + "=" + param.getValue()[0];					
				});
	}
	
	private void setQueryString(QueryString<String, Object> queryString) {
		qs = "";
		if (queryString != null && !queryString.getQueryString().isEmpty()) {
			queryString.getQueryString().entrySet().stream().forEach(q -> q.getValue().stream().filter(q1 -> q1 != null)
					.forEach(q1 -> {
						try {
							qs += "&" + q.getKey() + "=" +  (Core.isNotNull(q1) && q1 instanceof String? URLEncoder.encode((String) q1, StandardCharsets.UTF_8.toString()):q1);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}));
		}
	}

	private void setQueryStringToAttributes(QueryString<String, Object> queryString) {
		if (queryString != null && !queryString.getQueryString().isEmpty()) {
			queryString.getQueryString().entrySet().stream()
					.forEach(qs -> Core.setAttribute(qs.getKey(), qs.getValue().toArray()));
		}
	}

	protected final Response redirect(String app, String page, String action, String qs) throws IOException {
		return this.redirect_(Route.toUrl(app, page, action, qs));
	}

	protected final Response redirect(String r, String qs) throws IOException {
		return this.redirect_(Route.toUrl(r, qs));
	}

	protected final Response redirect(String r) {
		return this.redirect_(Route.toUrl(r));
	}

	protected final Response redirectError() throws IOException {
		return this.redirect_(Route.toUrl("igrp", "error-page", "exception"));
	}

	protected final Response redirect(String app, String page, String action) throws IOException { 
		String ssoUrl = ssoUrl(app, page, action, null, null);
		if(ssoUrl != null) 
			return this.redirectToUrl(ssoUrl); 
		return this.redirect_(Route.toUrl(app, page, this.addParamDad(action))); 
	}

	protected final Response redirect(String app, String page, String action, Model model) throws IOException {
		return this.redirect(app, page, action, model, new QueryString<>());
	}

	protected final Response redirect(String app, String page, String action, String[] paramNames, String[] paramValues)
			throws IOException {
		String ssoUrl = ssoUrl(app, page, action, paramNames, paramValues);
		if(ssoUrl != null) 
			return this.redirectToUrl(ssoUrl); 
		return this.redirect_(Route.toUrl(app, page, action, paramNames, paramValues));
	}
	
	private String ssoUrl(String app, String page, String action, String[] paramNames, String[] paramValues) {
		String ssoUrl = null;
		Map<String, String> params = new LinkedHashMap<>(); 
		if(paramNames != null && paramValues != null && paramValues.length == paramNames.length)
			for(int i = 0; i < paramNames.length; i++) 
				params.put(paramNames[i], paramValues[i]); 
		String currentDad = Core.getCurrentDad();
		if(currentDad != null && !currentDad.equals(app) && Core.isSharedPage(currentDad, app, page)) { 
			String stateValue = Core.buildStateValueForSsoAutentikaWhenPage(page, app, null, null, !params.isEmpty() ? params : null); 
			ssoUrl = Core.buildAppUrlUsingAutentikaForSSO(app, stateValue); 
		}
		return ssoUrl; 
	}

	protected final Response redirectToUrl(String url) {
		Response resp = new Response();
		resp.setType(2);
		resp.setUrl(url);
		resp.setHttpStatus(HttpStatus.STATUS_200);
		return resp;
	}

	protected final Response forward(String app, String page, String action) {
		Response resp = new Response();
		resp.setType(3);
		resp.setUrl(Route.toUrl(app, page, this.addParamDad(action)));
		return resp;
	}

	private String addParamDad(String action) {
		if (!action.contains("dad")) 
			action += "&dad=" + Core.getParam("dad");
		return action;
	}

	protected final Response sendFile(File file, String name, String contentType, boolean download) {
		byte[] content = null;
		try (FileInputStream in = new FileInputStream(file)) {		
			return this.xSend(FileHelper.convertInputStreamToByte(in), name, contentType, download);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.xSend(content, name, contentType, download); // send it as stream ... binary file
	}

	// send it as stream ... binary file
	public final Response xSend(byte[] file, String name, String contentType, boolean download) {
		return xSend(file, name, contentType, download, null);
	}

	public final Response xSend(byte[] file, String name, String contentType, boolean download, String url) {
		Igrp igrpApp = Igrp.getInstance();
		if (file == null)
			throw new ServerErrorHttpException();
		Response response = new Response();
		if (contentType == null || contentType.isEmpty()) 
			contentType = "application/octet-stream";
		else {
			try {
				String extension = "." + contentType.split("/")[1];
				if(name == null || name.trim().isEmpty())
					name = String.format("igrp-file%s", extension);
				if (!name.contains("."))
					name = String.format("%s%s", name, extension);
			} catch (Exception e) {
				contentType = "application/octet-stream";
				e.printStackTrace();
			}
		}
		igrpApp.getResponse().addHeader("Content-Description", "File Transfer");
		if (download)
			igrpApp.getResponse().addHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
		else
			igrpApp.getResponse().addHeader("Content-Disposition", "inline; filename=\"" + name + "\"");
		response.setType(1);
		response.setContentLength(file.length);
		response.setContentType(contentType);
		response.setStream(file);
		if(url != null && !url.trim().isEmpty())
			response.setUrl(url);
		return response;
	}

	public View getView() {
		return this.view;
	}

	public void initControllerNRunAction() throws IOException {
		this.resolveRoute();
		this.prepareResponse();
	}

	private void prepareResponse() throws IOException {
		Object obj = this.run();
		if (obj instanceof Response)
			Igrp.getInstance().getCurrentController().setResponseWrapper((Response) obj);
	}

	private void resolveRoute() throws IOException {
		Igrp app = Igrp.getInstance(); 
		String r = Core.isNotNull(app.getRequest().getParameter("r")) ? app.getRequest().getParameter("r") : "igrp/login/login";
		r = SecurtyCallPage.resolvePage(r); 
		if (r != null) {			
			String[] aux = r.split("/");
			if(aux.length==3) {
				app.setCurrentAppName(aux[0]);
				app.setCurrentPageName(aux[1]);
				app.setCurrentActionName(aux[2]);
			} else
				throw new ServerErrorHttpException("The route format is invalid");
		}
		String application = "Application: " + app.getCurrentAppName();
		String page = "Page: " + app.getCurrentPageName();
		String action = "Action: " + app.getCurrentActionName();
		String controllerName = "Controller: " + "nosi.webapps." + app.getCurrentAppName().trim().toLowerCase()
				+ ".pages." + app.getCurrentPageName() + "Controller.java";
		String viewName = "View: " + "nosi.webapps." + app.getCurrentAppName().trim().toLowerCase() + ".pages."
				+ app.getCurrentPageName() + "View.java";
		String modelName = "Model: " + "nosi.webapps." + app.getCurrentAppName().trim().toLowerCase() + ".pages."
				+ app.getCurrentPageName() + ".java";
		String xsl = "xsl: " + Igrp.getInstance().getServlet().getServletContext().getContextPath()
				+ "/images/IGRP/IGRP2.3/app/" + app.getCurrentAppName().trim().toLowerCase() + "/"
				+ app.getCurrentPageName().toLowerCase() + "/" + app.getCurrentPageName() + ".xsl";
		app.getLog().addMessage(application);
		app.getLog().addMessage(page);
		app.getLog().addMessage(action);
		app.getLog().addMessage(controllerName);
		app.getLog().addMessage(viewName);
		app.getLog().addMessage(modelName);
		app.getLog().addMessage(xsl); 
		
	} 

	protected Object run() {
		Igrp app = Igrp.getInstance();
		String auxAppName = "";
		String auxPageName = "";
		String auxcontrollerPath = "";
		String auxActionName = "";
		if (app != null && app.getCurrentAppName() != null && app.getCurrentActionName() != null
				&& app.getCurrentPageName() != null) {
			for (String aux : app.getCurrentAppName().split("-"))
				auxAppName += aux.substring(0, 1).toUpperCase() + aux.substring(1);
			for (String aux : app.getCurrentPageName().split("-")) 
				auxPageName += aux.substring(0, 1).toUpperCase() + aux.substring(1);
			for (String aux : app.getCurrentActionName().split("-"))
				auxActionName += aux.substring(0, 1).toUpperCase() + aux.substring(1);
			auxActionName = "action" + auxActionName;
			auxcontrollerPath = this.getConfig().getPackage(auxAppName, auxPageName, auxActionName);
		} else {
			auxActionName = "actionIndex";
			auxcontrollerPath = this.getConfig().getPackage("igrp", "Home", auxActionName);
		}
		return Page.loadPage(auxcontrollerPath, auxActionName); 
	}

	protected Response call(String app, String page, String action) {
		return this.call(app, page, action, null);
	}

	public Response call(String app, String page, String action, QueryString<String, Object> queryString) {
		IGRPMessage msg = new IGRPMessage();
		Igrp igrpApp = Igrp.getInstance();
		String m = msg.toString();
		this.setQueryStringToAttributes(queryString);
		String auxcontrollerPath = this.getConfig().getPackage(app, page, action);
		igrpApp.setCurrentAppName(app);
		igrpApp.setCurrentPageName(page);
		igrpApp.setCurrentActionName(action);
		Object obj = Page.loadPage(auxcontrollerPath, "action" + StringHelper.camelCaseFirst(action));
		Response resp = (Response) obj;
		if (resp != null) {
			String content = resp.getContent();
			if (m != null) 
				content = Core.isNotNull(content) ? content.replace("<messages></messages>", m) : content;
			resp.setContent(content);
		}
		return resp;
	}
	
	@Deprecated
	/**
	 * @deprecated
	 * @param app
	 * @param page
	 * @param action
	 * @param model
	 * @return
	 */
	protected Response forward(String app, String page, String action, Model model) {
		return this.forward(app, page, action, model, new QueryString<>());
	}
	
	/**@deprecated
	 * 
	 * 	
	 */
	@Deprecated
	protected Response forward(String app, String page, String action, Model model,
			QueryString<String, Object> queryString) {
		return this.forward(app, page, action, queryString);
	}

	protected Response forward(String app, String page, String action, QueryString<String, Object> queryString) {
		this.setQueryString(queryString);
		Response resp = new Response();
		resp.setType(3);
		resp.setUrl(Route.toUrl(app, page, action, qs));
		return resp;
	}

	protected final Response xSend(FileRest file, String name, String contentType, boolean download) {
		Response response = new Response();
		Igrp.getInstance().getResponse().addHeader("Content-Description", "File Transfer");
		if (download)
			Igrp.getInstance().getResponse().addHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
		else
			Igrp.getInstance().getResponse().addHeader("Content-Disposition", "inline; filename=\"" + name + "\"");
		response.setType(1);
		response.setFile(file);
		return response;
	}

	public void sendResponse() {
		Response responseWrapper2 = Igrp.getInstance().getCurrentController().getResponseWrapper();
		if (responseWrapper2 != null) {
			try {
				switch (responseWrapper2.getType()) {
				case 1: // render it
					try {
						if (responseWrapper2.getStream() != null && responseWrapper2.getStream().length > 0) {
							Igrp.getInstance().getResponse().getOutputStream().write(responseWrapper2.getStream());
							Igrp.getInstance().getResponse().getOutputStream().flush();
							Igrp.getInstance().getResponse().getOutputStream().close();
							Igrp.getInstance().getResponse().flushBuffer();
						} else if (responseWrapper2.getFile() != null) {
							HttpServletResponse response = Igrp.getInstance().getResponse();
							String name = responseWrapper2.getFile().getFileName();
							response.setContentType(responseWrapper2.getFile().getContentType());
							response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\";");
							response.setHeader("Cache-Control", "no-cache");
							response.setContentLength(responseWrapper2.getFile().getSize());
							try (ServletOutputStream sos = response.getOutputStream();
									BufferedInputStream bis = new BufferedInputStream(
											responseWrapper2.getFile().getContent())) {
								int data;
								while ((data = bis.read()) != -1) {
									sos.write(data);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							responseWrapper2.getFile().getContent().close();
						} else {
							Igrp.getInstance().getResponse().getWriter().append(responseWrapper2.getContent());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 2: // redirect
					boolean isAbsolute = false;
					try {
						String url = responseWrapper2.getUrl();
						try {
							java.net.URI uri = java.net.URI.create(url);
							isAbsolute = uri.isAbsolute() && uri.toURL().getProtocol().matches("(?i)(http|https)");
						} catch (MalformedURLException e) { // Ensure the url format is perfect ...
							isAbsolute = false;
						}
						if (!Igrp.getInstance().getResponse().isCommitted()) {
							Igrp.getInstance().getResponse().sendRedirect(
									isAbsolute? url : (url.startsWith("webapps") ? url : ("webapps" + url)));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 3: // forward
					String url = responseWrapper2.getUrl().replace("&&", "&");
					url = url.startsWith("webapps") ? ("app/" + url) : ("app/webapps" + url);
					try {
						Igrp.getInstance().getRequest().getRequestDispatcher(url)
								.forward(Igrp.getInstance().getRequest(), Igrp.getInstance().getResponse());
					} catch (ServletException | IOException e) {
						e.printStackTrace();
					}
					break;
				case 4:
					try {
						Igrp.getInstance().getResponse().sendRedirect("pg_studio.jsp");
//							Igrp.getInstance().getRequest().getRequestDispatcher("pg_studio.jsp").forward(Igrp.getInstance().getRequest(), Igrp.getInstance().getResponse());
						Igrp.getInstance().getResponse().getOutputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				default:
				}
			} catch (java.lang.NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	public Config getConfig() {
		return this.configApp.getConfig();
	} 
	
}
