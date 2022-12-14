package nosi.webapps.igrp.pages.resetbyemail;

import java.io.IOException;
import java.util.Properties;
import nosi.core.config.ConfigCommonMainConstants;
import nosi.core.integration.autentika.RemoteUserStoreManagerServiceSoapClient;
import nosi.core.integration.autentika.dto.UserClaimValuesRequestDTO;
import nosi.core.integration.autentika.dto.UserClaimValuesResponseDTO;
import nosi.core.mail.EmailMessage;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.webapps.igrp.dao.User;

/*----#end-code----*/
		
public class ResetbyemailController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		Resetbyemail model = new Resetbyemail();
		model.load();
		model.setSign_in("igrp","Dominio","index");
		ResetbyemailView view = new ResetbyemailView();
		/*----#start-code(index)----*/
		model.setSign_in("igrp","login","login&isPublic=0&target=_self");		
		
/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionEnviar() throws IOException, IllegalArgumentException, IllegalAccessException{
		Resetbyemail model = new Resetbyemail();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp","Resetbyemail","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(enviar)----*/
		String token = nosi.core.webapp.User.generatePasswordResetToken();
		String link = Igrp.getInstance().getRequest().getRequestURL() + "?r=" + "igrp" + "/Resetpassword/index" + "&target=_blank&isPublic=1" + "&t=" + token; 
		String email = model.getForm_1_email_1();
		String username = "";
		String authenticationType = this.getConfig().getAutenticationType(); 
		if(authenticationType.equals(ConfigCommonMainConstants.IGRP_AUTHENTICATION_TYPE_DATABASE.value())) {
			if(!db(email, token)) { 
				Core.setMessageError("Ooops ! O email inserido não foi encontrado."); 
				return forward("igrp","Resetbyemail","index", this.queryString()); 
			}
			username= Core.findUserByEmail(email)!=null?Core.findUserByEmail(email).getName():""; 
		}else 
			if(authenticationType.equals(ConfigCommonMainConstants.IGRP_AUTHENTICATION_TYPE_LDAP.value())) {
				if(!ldap(email, token)) {
					Core.setMessageError("Ooops ! O email inserido não foi encontrado.");
					return forward("igrp","Resetbyemail","index", this.queryString());
				}
			}
		String msg = "" + "<p>Caro(a) "+username+", foi solicitado o reset de password da sua conta de acesso ... </p>"; 
		String aux = EmailMessage.PdexTemplate.getCorpoFormatado("Recuperação da palavra-passe", "Excelentíssimo,", new String[] {msg}, new String[] {"Recuperar Password"}, new String[] {link},  "http://igrp.cv");
		boolean r = Core.mail("igrpweb@nosi.cv",email, "Reset de Password", aux,"");
		if(r)
			Core.setMessageSuccess("Consulte a sua conta de email para confirmar a ação e assim ter acesso a sua nova password.");
		else
			Core.setMessageError("Ocorreu um erro no envio do email. Email não foi enviado ...");	
		
		/*----#end-code----*/
		return this.redirect("igrp","Resetbyemail","index", this.queryString());	
	}
	
/*----#start-code(custom_actions)----*/
	
	private boolean db(String email, String token) {
		User u = Core.findUserByEmail(email);          
		if(u != null) {
			u.setPassword_reset_token(token);
			u = u.update();
		}
		return u != null;
	}
	
	private boolean ldap(String email, String token) {
		boolean flag = false;
		try {
			Properties settings = this.configApp.getMainSettings(); 
			String wsdlUrl = settings.getProperty(ConfigCommonMainConstants.IDS_AUTENTIKA_REMOTE_USER_STORE_MANAGER_SERVICE_WSDL_URL.value());
			String uid = settings.getProperty(ConfigCommonMainConstants.IDS_AUTENTIKA_ADMIN_USN.value()); 
			String pwd = settings.getProperty(ConfigCommonMainConstants.IDS_AUTENTIKA_ADMIN_PWD.value());
			RemoteUserStoreManagerServiceSoapClient client = new RemoteUserStoreManagerServiceSoapClient(wsdlUrl, uid, pwd);
			UserClaimValuesRequestDTO request = new UserClaimValuesRequestDTO();
			request.setUserName(email);
			UserClaimValuesResponseDTO result = client.getUserClaimValues(request);
	        flag = result != null && !result.getClaimDTOs().isEmpty() && db(email, token);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	

	
/*----#end-code----*/
}
