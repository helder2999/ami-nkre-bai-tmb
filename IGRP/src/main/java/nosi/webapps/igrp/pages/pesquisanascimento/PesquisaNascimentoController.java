package nosi.webapps.igrp.pages.pesquisanascimento;

import java.io.IOException;
/*----#start-code(packages_import)----*/
import java.util.ArrayList;
import java.util.List;

import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;

/*----#end-code----*/
		
public class PesquisaNascimentoController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisaNascimento model = new PesquisaNascimento();
		model.load();
		PesquisaNascimentoView view = new PesquisaNascimentoView();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		model.loadTable_1(Core.query(null,"SELECT 'Consectetur iste labore iste a' as n_registo_tabela,'Perspiciatis lorem unde elit s' as nome_tabela,'Rem deserunt elit anim ipsum' as data_facto,'Rem sit doloremque deserunt lo' as nome_mae,'Stract aliqua iste ipsum totam' as nome_pai "));
		  ----#gen-example */
		/*----#start-code(index)----*/

		if(Igrp.getInstance().getRequest().getMethod().equalsIgnoreCase("post")){
			model.load();
		}
		
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionPesquisar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisaNascimento model = new PesquisaNascimento();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp","ErrorPage","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(pesquisar)----*/

		if(Igrp.getInstance().getRequest().getMethod().equalsIgnoreCase("post")){
			model.load();
//			nosi.core.webapp.webservices.biztalk.dao.PesquisaNascimento p = Core.getBizTalkPesquisaNascimento(new nosi.core.webapp.webservices.biztalk.dao.PesquisaNascimento(model.getNome(), (model.getN_registo()!=null && !model.getN_registo().equals(""))?Integer.parseInt(model.getN_registo()):null, null));
//			List<PesquisaNascimento.Table_1> data = new ArrayList<>();
//			if(p!=null){
//				for(RowList row:p.getRowList()){
//					PesquisaNascimento.Table_1 pbi = new PesquisaNascimento.Table_1();
//					pbi.setData_facto(row.getData_nascimento());
//					pbi.setN_registo_tabela(""+row.getNumero());
//					pbi.setNome_mae(row.getNome_mae());
//					pbi.setNome_pai(row.getNome_pai());
//					pbi.setNome_tabela(row.getIdentificacao());
//					data.add(pbi);
//				}
//			}
//			PesquisaNascimentoView view = new PesquisaNascimentoView();
//			view.table_1.addData(data);
			
		}
		
		/*----#end-code----*/
		return this.redirect("igrp","ErrorPage","index", this.queryString());	
	}
	
/*----#start-code(custom_actions)----*/

	/*----#end-code----*/
}
