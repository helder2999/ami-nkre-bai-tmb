package nosi.webapps.igrp.pages.pesquisarperfil;

import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.Core;
import nosi.core.webapp.Model;
import nosi.core.webapp.View;

public class PesquisarPerfilView extends View {

	public Field sectionheader_1_text;
	public Field help;
	public Field organica;
	public Field estado;
	public Field estado_check;
	public Field descricao;
	public Field codigo;
	public Field id;
	public Field id_app;
	public Field id_org;
	public IGRPSectionHeader sectionheader_1;
	public IGRPView view_1;
	public IGRPTable table_1;
	public IGRPForm form_1;

	public IGRPToolsBar toolsbar_1;
	public IGRPButton btn_novo;
	public IGRPButton btn_editar;
	public IGRPButton btn_menu;
	public IGRPButton btn_transacao;
	public IGRPButton btn_associar_etapa;
	public IGRPButton btn_convidar;
	public IGRPButton btn_eliminar;

	public PesquisarPerfilView(){

		this.setPageTitle("Gestão de Perfil");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		view_1 = new IGRPView("view_1","");

		table_1 = new IGRPTable("table_1","");

		form_1 = new IGRPForm("form_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Gestão de Perfil"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		help = new LinkField(model,"help");
		help.setLabel(gt("Help"));
		help.setValue(Core.getIGRPLink("igrp","Dominio","index"));

									help.propertie().add("name","p_help").add("type","link").add("target","_newtab").add("request_fields","").add("refresh_components","").add("refresh_submit","false").add("class","[object Object]").add("img","[object Object]").add("maxlength","250").add("showlabel","true").add("adbcli","");
		
		organica = new TextField(model,"organica");
		organica.setLabel(gt("Organização"));
		organica.propertie().add("name","p_organica").add("type","text").add("maxlength","255").add("showLabel","true").add("group_in","");
		
		estado = new CheckBoxField(model,"estado");
		estado.setLabel(gt("Estado"));
		estado.propertie().add("name","p_estado").add("type","checkbox").add("maxlength","30").add("switch","false").add("java-type","int").add("showLabel","true").add("group_in","").add("check","true").add("desc","true");
		
		estado_check = new CheckBoxField(model,"estado_check");
		estado_check.propertie().add("name","p_estado").add("type","checkbox").add("maxlength","30").add("switch","false").add("java-type","int").add("showLabel","true").add("group_in","").add("check","true").add("desc","true");
		
		descricao = new TextField(model,"descricao");
		descricao.setLabel(gt("Nome"));
		descricao.propertie().add("name","p_descricao").add("type","text").add("maxlength","255").add("showLabel","true").add("group_in","");
		
		codigo = new TextField(model,"codigo");
		codigo.setLabel(gt("Código"));
		codigo.propertie().add("name","p_codigo").add("type","text").add("maxlength","255").add("showLabel","true").add("group_in","");
		
		id = new HiddenField(model,"id");
		id.setLabel(gt(""));
		id.propertie().add("name","p_id").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","id");
		
		id_app = new HiddenField(model,"id_app");
		id_app.setLabel(gt(""));
		id_app.propertie().add("name","p_id_app").add("type","hidden").add("maxlength","250").add("java-type","int").add("tag","id_app");
		
		id_org = new HiddenField(model,"id_org");
		id_org.setLabel(gt(""));
		id_org.propertie().add("name","p_id_org").add("type","hidden").add("maxlength","250").add("java-type","int").add("tag","id_org");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");

		btn_novo = new IGRPButton("Novo","igrp","PesquisarPerfil","novo","right_panel|refresh","success|fa-plus-square","","");
		btn_novo.propertie.add("type","specific").add("rel","novo").add("refresh_components","");

		btn_editar = new IGRPButton("Editar","igrp","PesquisarPerfil","editar","right_panel|refresh","warning|fa-pencil","","");
		btn_editar.propertie.add("id","button_40d9_9edf").add("type","specific").add("class","warning").add("rel","editar").add("refresh_components","");

		btn_menu = new IGRPButton("Menu","igrp","PesquisarPerfil","menu","right_panel","info|fa-bars","","");
		btn_menu.propertie.add("id","button_2d77_a624").add("type","specific").add("class","info").add("rel","menu").add("refresh_components","");

		btn_transacao = new IGRPButton("Transacao","igrp","PesquisarPerfil","transacao","right_panel","black|fa-exchange","","");
		btn_transacao.propertie.add("id","button_6e1b_72ce").add("type","specific").add("class","black").add("rel","transacao").add("refresh_components","");

		btn_associar_etapa = new IGRPButton("Associar Etapa","igrp","PesquisarPerfil","associar_etapa","right_panel","primary|fa-sitemap","","");
		btn_associar_etapa.propertie.add("id","button_0636_8fae").add("type","specific").add("class","primary").add("rel","associar_etapa").add("refresh_components","");

		btn_convidar = new IGRPButton("Convidar","igrp","PesquisarPerfil","convidar","right_panel","warning|fa-send","","");
		btn_convidar.propertie.add("id","button_a1c1_3c29").add("type","specific").add("class","warning").add("rel","convidar").add("refresh_components","");

		btn_eliminar = new IGRPButton("Eliminar","igrp","PesquisarPerfil","eliminar","alert_submit","danger|fa-trash","","");
		btn_eliminar.propertie.add("id","button_aa26_9fae").add("type","specific").add("class","danger").add("rel","eliminar").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		view_1.addField(help);


		table_1.addField(organica);
		table_1.addField(estado);
		table_1.addField(estado_check);
		table_1.addField(descricao);
		table_1.addField(codigo);
		table_1.addField(id);

		form_1.addField(id_app);
		form_1.addField(id_org);

		toolsbar_1.addButton(btn_novo);
		table_1.addButton(btn_editar);
		table_1.addButton(btn_menu);
		table_1.addButton(btn_transacao);
		table_1.addButton(btn_associar_etapa);
		table_1.addButton(btn_convidar);
		table_1.addButton(btn_eliminar);
		this.addToPage(sectionheader_1);
		this.addToPage(view_1);
		this.addToPage(table_1);
		this.addToPage(form_1);
		this.addToPage(toolsbar_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		help.setValue(model);
		organica.setValue(model);
		estado.setValue(model);
		descricao.setValue(model);
		codigo.setValue(model);
		id.setValue(model);
		id_app.setValue(model);
		id_org.setValue(model);	

		table_1.loadModel(((PesquisarPerfil) model).getTable_1());
		}
}
