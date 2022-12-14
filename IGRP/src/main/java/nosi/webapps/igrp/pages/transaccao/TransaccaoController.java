package nosi.webapps.igrp.pages.transaccao;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Transaction;

import java.util.List;
/*----#end-code----*/
		
public class TransaccaoController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		Transaccao model = new Transaccao();
		model.load();
		TransaccaoView view = new TransaccaoView();
		view.codigo.setParam(true);
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		model.loadTable_1(Core.query(null,"SELECT '1' as status,'Sed omnis sit ut dolor' as descricao,'Aliqua labore amet aliqua accu' as codigo "));
		view.aplicacao.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		  ----#gen-example */
		/*----#start-code(index)----*/
		
		String dad = Core.getCurrentDad();		
		 if (!"igrp".equalsIgnoreCase(dad) && !"igrp_studio".equalsIgnoreCase(dad)) {			
			model.setAplicacao(""+Core.findApplicationByDad(dad).getId());
	        view.aplicacao.propertie().add("disabled","true");			
			}
		
		ArrayList<Transaccao.Table_1> table_1 = new ArrayList<>();
		Transaction trans = new Transaction();				
		if(Core.isNotNull(model.getAplicacao())) {
		List<Transaction> list =trans.find()
				.andWhere("application", "=", Core.toInt(model.getAplicacao()))
				.andWhere("code", "=", Core.isNotNull(model.getFiltro_codigo())?model.getFiltro_codigo():null)
				.all();
		for(Transaction t:list){
			Transaccao.Table_1 table = new Transaccao.Table_1();
			table.setCodigo(t.getCode());           
			table.setDescricao(t.getDescr());
			int check = t.getStatus() == 1 ? t.getStatus() : -1;
            table.setStatus(t.getStatus());
          	table.setStatus_check(check);
			table_1.add(table);
		}		
		}
		//view.btn_eliminar.setVisible(false);
		view.aplicacao.setValue(new Application().getListApps());
//		view.organica.setValue(new Organization().getListMyOrganizations());
		view.table_1.addData(table_1);
		
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionEditar() throws IOException, IllegalArgumentException, IllegalAccessException{
		Transaccao model = new Transaccao();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_codigo",Core.getParam("p_codigo"));
		 return this.forward("igrp","EditarTransacao","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(editar)----*/
    String codigo = Core.getParam("p_codigo");
      if(Core.isNotNull(codigo))
			this.addQueryString("codigo",codigo);
		else
			Core.setMessageError();
		/*----#end-code----*/
		return this.redirect("igrp","EditarTransacao","index", this.queryString());	
	}
	
	public Response actionEliminar() throws IOException, IllegalArgumentException, IllegalAccessException{
		Transaccao model = new Transaccao();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_codigo",Core.getParam("p_codigo"));
		 return this.forward("igrp","Transaccao","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(eliminar)----*/
		 this.addQueryString("p_codigo",Core.getParam("p_codigo"));
	
      
      String code = Core.getParam("p_codigo");
		Transaction t = new Transaction();
		t = t.find().andWhere("code", "=", code).one();
		if(t.delete(t.getId()))
			Core.setMessageSuccess();
		else
			Core.setMessageError();
		/*----#end-code----*/
		return this.redirect("igrp","Transaccao","index", this.queryString());	
	}
	
/*----#start-code(custom_actions)----*/
	public Response actionChangeStatus() throws IOException, IllegalArgumentException, IllegalAccessException, JSONException {

		this.format = Response.FORMAT_JSON;		
		String code = Core.getParam("p_code");
		String status = Core.getParam("p_status");
		
		boolean response = false;

		if (Core.isNotNull(code)) {
			Transaction t = new Transaction();
			t.setCode(code);
			t = t.find().andWhere("code", "=", code).one();
			if(t!=null){
				t.setStatus(Integer.parseInt(status));
				if (t.update() != null)
					response = true;
			}
		}

		JSONObject json = new JSONObject();
		json.put("status", response);
		Gson res = new Gson();
		res.toJson(json);

		return this.renderView(json.toString());
	}
	/*----#end-code----*/
}
