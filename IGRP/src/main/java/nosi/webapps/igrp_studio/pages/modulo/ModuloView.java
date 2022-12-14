package nosi.webapps.igrp_studio.pages.modulo;

import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.Model;
import nosi.core.webapp.View;

public class ModuloView extends View {

	public Field sectionheader_1_text;
	public Field aplicacao;
	public Field modulo;
	public Field descricao;
	public IGRPForm sectionheader_1;
	public IGRPForm form_1;

	public IGRPButton btn_gravar;

	public ModuloView(){

		this.setPageTitle("Módulo");
			
		sectionheader_1 = new IGRPForm("sectionheader_1","");

		form_1 = new IGRPForm("form_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Novo Módulo"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		aplicacao = new ListField(model,"aplicacao");
		aplicacao.setLabel(gt("Aplicação"));
		aplicacao.propertie().add("name","p_aplicacao").add("type","select").add("multiple","false").add("domain","").add("maxlength","50").add("required","true").add("disabled","false").add("java-type","").add("tags","false");
		
		modulo = new TextField(model,"modulo");
		modulo.setLabel(gt("Módulo"));
		modulo.propertie().add("name","p_modulo").add("type","text").add("maxlength","50").add("required","true").add("readonly","false").add("disabled","false");
		
		descricao = new TextField(model,"descricao");
		descricao.setLabel(gt("Descrição"));
		descricao.propertie().add("name","p_descricao").add("type","text").add("maxlength","250").add("required","true").add("readonly","false").add("disabled","false");
		


		btn_gravar = new IGRPButton("Adicionar","igrp_studio","Modulo","gravar","submit_form","success|fa-plus","","");
		btn_gravar.propertie.add("type","form").add("rel","gravar");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		form_1.addField(aplicacao);
		form_1.addField(modulo);
		form_1.addField(descricao);

		form_1.addButton(btn_gravar);
		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		aplicacao.setValue(model);
		modulo.setValue(model);
		descricao.setValue(model);	

		}
}
