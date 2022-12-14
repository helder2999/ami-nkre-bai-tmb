/*-------------------------*/

/*Create View*/

package nosi.webapps.igrp.pages.mapaprocesso;
import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.webapp.View;

public class MapaProcessoView extends View {
	
	
	public IGRPFilter filter;
	public IGRPMenu menu;
	
	public MapaProcessoView(MapaProcesso model){
		this.setPageTitle(gt("Mapa de Processo"));
		menu = new IGRPMenu("menus");
		filter = new IGRPFilter("filter",(float)2.1);
	}
		
	@Override
	public void render(){
		
		this.addToPage(menu);
		this.addToPage(filter);
	}
}
/*-------------------------*/