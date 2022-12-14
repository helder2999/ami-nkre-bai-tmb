package nosi.webapps.igrp.pages.icons_list;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;

public class Icons_listView extends View {

	public Field fwl_search;
	public Field choose_icon;
	public IGRPForm icon_form;


	public Icons_listView(){

		this.setPageTitle("Icons List");
			
		icon_form = new IGRPForm("icon_form","");

		fwl_search = new HiddenField(model,"fwl_search");
		fwl_search.setLabel(gt(""));
		fwl_search.propertie().add("name","p_fwl_search").add("type","hidden").add("maxlength","250").add("java-type","").add("tag","fwl_search");
		
		choose_icon = new HiddenField(model,"choose_icon");
		choose_icon.setLabel(gt(""));
		choose_icon.propertie().add("name","p_choose_icon").add("type","hidden").add("maxlength","250").add("java-type","").add("tag","choose_icon");
		


		
	}
		
	@Override
	public void render(){
		
		icon_form.addField(fwl_search);
		icon_form.addField(choose_icon);

		this.addToPage(icon_form);
	}
		
	@Override
	public void setModel(Model model) {
		
		fwl_search.setValue(model);
		choose_icon.setValue(model);	

		}
}
