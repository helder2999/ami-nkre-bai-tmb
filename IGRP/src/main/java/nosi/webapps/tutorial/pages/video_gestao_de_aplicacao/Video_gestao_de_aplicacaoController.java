
package nosi.webapps.tutorial.pages.video_gestao_de_aplicacao;

import java.io.IOException;

import nosi.core.webapp.Controller;
import nosi.core.webapp.Response;

/*----#end-code----*/


public class Video_gestao_de_aplicacaoController extends Controller {		

	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		
		Video_gestao_de_aplicacao model = new Video_gestao_de_aplicacao();
		model.load();
		Video_gestao_de_aplicacaoView view = new Video_gestao_de_aplicacaoView();
		/*----#start-code(index)----*/
		
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	/*----#start-code(custom_actions)----*/
	
	/*----#end-code----*/
	}
