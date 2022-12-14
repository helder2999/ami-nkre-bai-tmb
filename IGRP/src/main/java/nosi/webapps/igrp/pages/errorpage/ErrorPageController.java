package nosi.webapps.igrp.pages.errorpage;

import java.io.IOException;

/*----#start-code(packages_import)----*/
import javax.servlet.RequestDispatcher;


import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.webapps.igrp.dao.Config;
		
public class ErrorPageController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		ErrorPage model = new ErrorPage();
		model.load();
		ErrorPageView view = new ErrorPageView();
		/*----#start-code(index)----*/
	
	
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionVoltar() throws IOException, IllegalArgumentException, IllegalAccessException{
		ErrorPage model = new ErrorPage();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp","Dominio","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(voltar)----*/
		
		
		/*----#end-code----*/
		return this.redirect("igrp","Dominio","index", this.queryString());	
	}
	
/*----#start-code(custom_actions)----*/
	//private Logger logger = LogManager.getLogger(ErrorPageController.class);
	public Response actionException() throws IOException, IllegalArgumentException, IllegalAccessException{
      ErrorPage model = new ErrorPage();
      model.load();
		ErrorPageView view = new ErrorPageView();
	//	if(Igrp.getInstance().getUser().isAuthenticated()){					
			try {
				Exception e = (Exception)Igrp.getInstance().getRequest().getAttribute(RequestDispatcher.ERROR_EXCEPTION);
				if(e!=null) {
					Core.log("ExpTion: "+e.toString());
					e.printStackTrace();
				}				
				
				String errorMsg = Core.getAttribute("javax.servlet.error.message",true); 
				
				//logger.error(errorMsg);
				if(errorMsg!=null)
					Core.setMessageError(errorMsg);
			
				// dbug
				if(Igrp.getInstance()!=null && Igrp.getInstance().getRequest() !=null && Igrp.getInstance().getRequest().getSession()!=null  )
					Core.log(Igrp.getInstance().getRequest().getSession().getAttribute("igrp.error")+"");
			} catch (Exception e1) {
				// TODO Auto-generated catch block				
				Core.log("TryCatch: "+e1.toString());
				e1.printStackTrace();
			}
			
			Config tblconfigfilter = new Config().find().where("name","=","ERROR_MSG_"+Core.getCurrentDad()).one();
			
			if(Core.isNotNull(tblconfigfilter))
				Core.setMessageInfo(Core.gt(tblconfigfilter.getValue()));
			else			
				Core.setMessageInfo(Core.gt("Por favor contactar o serviço de HELPDESK para mais informações.(helpdesk@nosi.cv - Tel:2607973)"));
						
			view.setModel(model);
			return this.renderView(view);
//		}
//		else
//			return this.redirect("igrp", "login", "login");
	}
	
	
	/*----#end-code----*/
}
