
package nosi.webapps.igrp.pages.startprocess;

import java.io.IOException;

import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
import nosi.core.webapp.activit.rest.entities.ProcessDefinitionService;
import nosi.core.webapp.activit.rest.services.ProcessDefinitionServiceRest;
import nosi.core.webapp.activit.rest.services.ProcessInstanceServiceRest;

/*----#end-code----*/


public class StartprocessController extends Controller {		

	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		
		Startprocess model = new Startprocess();
		model.load();
		StartprocessView view = new StartprocessView();
		/*----#start-code(index)----*/
		ProcessDefinitionService process = null;
		Integer totalProcAtivos = 0,totalProcFinished = 0;
		String p_processId = Core.getParam("process_id");
		if(p_processId !=null){
			process = new ProcessDefinitionServiceRest().getProcessDefinition(p_processId);
			view.header_text.setValue(process.getName());
			ProcessInstanceServiceRest p = new ProcessInstanceServiceRest();
			totalProcAtivos = p.totalProccesAtivos(process.getKey());
			totalProcFinished = p.totalProccesTerminados(process.getKey());
		}
		view.btn_iniciar_processo.setLink("Iniciar_processo&p_process_id="+p_processId);	
		model.setTotal_proc_em_execucao_val(""+totalProcAtivos);
		model.setTotal_proc_finished_val(""+totalProcFinished);
		model.setTotal_proc_em_execucao_url("#");
		model.setTotal_proc_finished_url("#");
		model.setTotal_proc_em_execucao_tit("Total de processos em execução");
		model.setTotal_proc_finished_tit("Total de processos executados");
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionIniciar_processo() throws IOException, IllegalArgumentException, IllegalAccessException{
		
		Startprocess model = new Startprocess();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name added in application builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp","Startprocess","index", this.queryString()); //if submit, loads the values
		  ----#gen-example */
		/*----#start-code(iniciar_processo)----*/
		String p_process_id = Core.getParam("p_process_id");
		if(Core.isNotNull(p_process_id)) {
			this.addQueryString("p_prm_definitionid", p_process_id);
			return this.redirect("igrp","ExecucaoTarefas","process-task",this.queryString());
		}
		/*----#end-code----*/
		return this.redirect("igrp","Startprocess","index", this.queryString());	
	}
	
	/*----#start-code(custom_actions)----*/
	
	
		/*----#end-code----*/
	}
