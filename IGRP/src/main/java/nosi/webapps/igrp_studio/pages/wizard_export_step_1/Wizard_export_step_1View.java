package nosi.webapps.igrp_studio.pages.wizard_export_step_1;

import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.Core;
import nosi.core.webapp.Model;
import nosi.core.webapp.View;

public class Wizard_export_step_1View extends View {

	public Field sectionheader_1_text;
	public Field file_name;
	public Field selecionar_opcao;
	public Field application_id;
	public Field help;
	public IGRPSectionHeader sectionheader_1;
	public IGRPForm form_1;
	public IGRPView view_1;

	public IGRPToolsBar toolsbar_1;
	public IGRPButton btn_seguinte;

	public Wizard_export_step_1View(){

		this.setPageTitle("Export Wizard Step 1");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		form_1 = new IGRPForm("form_1","");

		view_1 = new IGRPView("view_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Exportação Aplicação - Passo 1"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		file_name = new TextField(model,"file_name");
		file_name.setLabel(gt("File Name"));
		file_name.propertie().add("name","p_file_name").add("type","text").add("maxlength","250").add("required","true").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false");
		
		selecionar_opcao = new CheckBoxListField(model,"selecionar_opcao");
		selecionar_opcao.setLabel(gt("Selecionar opções"));
		selecionar_opcao.propertie().add("name","p_selecionar_opcao").add("type","checkboxlist").add("domain","").add("maxlength","250").add("required","true").add("readonly","false").add("disabled","false").add("child_size","6").add("java-type","");
		
		application_id = new HiddenField(model,"application_id");
		application_id.setLabel(gt(""));
		application_id.propertie().add("name","p_application_id").add("type","hidden").add("maxlength","250").add("java-type","int").add("tag","application_id");
		
		help = new LinkField(model,"help");
		help.setLabel(gt("Help"));
		help.setValue(Core.getIGRPLink("igrp_studio","ListaPage","index"));

									help.propertie().add("name","p_help").add("type","link").add("target","_newtab").add("request_fields","").add("refresh_components","").add("refresh_submit","false").add("class","default").add("img","fa-question-circle").add("maxlength","250").add("showlabel","true");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");

		btn_seguinte = new IGRPButton("Seguinte","igrp_studio","Wizard_export_step_1","seguinte","submit","primary|fa-forward","","");
		btn_seguinte.propertie.add("type","specific").add("rel","seguinte").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);


		form_1.addField(file_name);
		form_1.addField(selecionar_opcao);
		form_1.addField(application_id);

		view_1.addField(help);

		toolsbar_1.addButton(btn_seguinte);
		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
		this.addToPage(view_1);
		this.addToPage(toolsbar_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		file_name.setValue(model);
		selecionar_opcao.setValue(model);
		application_id.setValue(model);
		help.setValue(model);	

		}
}
