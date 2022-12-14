package nosi.webapps.igrp.pages.gestaodeacesso;

import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.Core;
import nosi.core.webapp.Model;
import nosi.core.webapp.View;

public class GestaodeacessoView extends View {

	public Field sectionheader_1_text;
	public Field documento_link;
	public Field forum;
	public Field aplicacao;
	public Field adicionar_organica;
	public Field gestao_de_utilizadores;
	public Field gestao_de_menu;
	public Field id_app;
	public Field estado;
	public Field estado_check;
	public Field org_nome;
	public Field mostrar_perfis;
	public Field mostrar_perfis_desc;
	public Field id;
	public IGRPSectionHeader sectionheader_1;
	public IGRPView view_1;
	public IGRPForm form_1;
	public IGRPTable org_table;

	public IGRPButton btn_editar;
	public IGRPButton btn_menu;
	public IGRPButton btn_transacti_org;
	public IGRPButton btn_eliminar;
	public IGRPButton btn_associar_etapa;

	public GestaodeacessoView(){

		this.setPageTitle("Gestao de Acesso");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		view_1 = new IGRPView("view_1","");

		form_1 = new IGRPForm("form_1","");

		org_table = new IGRPTable("org_table","Organizações");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Gestão de Acesso"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		documento_link = new LinkField(model,"documento_link");
		documento_link.setLabel(gt("Help"));
		documento_link.setValue(gt("https://docs.igrp.cv/IGRP/app/webapps?r=tutorial/Listar_documentos/index&dad=tutorial&target=_blank&isPublic=1&lang=pt_PT;&p_type=acesso"));
		documento_link.propertie().add("name","p_documento_link").add("type","link").add("target","_newtab").add("request_fields","").add("refresh_components","").add("refresh_submit","false").add("class","[object Object]").add("img","fa-question-circle").add("maxlength","250").add("showlabel","true");
		
		forum = new LinkField(model,"forum");
		forum.setLabel(gt("Forum"));
		forum.setValue(gt("https://gitter.im/igrpweb/gestao_de_acessos?utm_source=share-link&utm_medium=link&utm_campaign=share-link"));
		forum.propertie().add("name","p_forum").add("type","link").add("target","_newtab").add("request_fields","").add("refresh_components","").add("refresh_submit","false").add("class","[object Object]").add("img","fa-comments").add("maxlength","250").add("showlabel","true");
		
		aplicacao = new ListField(model,"aplicacao");
		aplicacao.setLabel(gt("Aplicação"));
		aplicacao.propertie().add("remote",Core.getIGRPLink("igrp","Gestaodeacesso","index")).add("name","p_aplicacao").add("type","select").add("multiple","false").add("domain","").add("maxlength","30").add("required","false").add("disabled","false").add("java-type","").add("tags","false");
		
		adicionar_organica = new LinkField(model,"adicionar_organica");
		adicionar_organica.setLabel(gt("Adicionar Organização"));
		adicionar_organica.setValue(Core.getIGRPLink("igrp","NovaOrganica","index"));

									adicionar_organica.propertie().add("name","p_adicionar_organica").add("type","link").add("target","right_panel_submit").add("class","primary").add("img","fa-plus-square").add("maxlength","30").add("placeholder",gt("")).add("request_fields","").add("refresh_submit","true").add("desclabel","false").add("refresh_components","");
		
		gestao_de_utilizadores = new LinkField(model,"gestao_de_utilizadores");
		gestao_de_utilizadores.setLabel(gt("Gestão de utilizadores"));
		gestao_de_utilizadores.setValue(Core.getIGRPLink("igrp","PesquisarUtilizador","index"));

									gestao_de_utilizadores.propertie().add("name","p_gestao_de_utilizadores").add("type","link").add("target","mpsubmit").add("class","success").add("img","fa-users").add("maxlength","30").add("placeholder",gt("")).add("request_fields","").add("refresh_submit","false").add("desclabel","false").add("refresh_components","");
		
		gestao_de_menu = new LinkField(model,"gestao_de_menu");
		gestao_de_menu.setLabel(gt("Gestão de menu"));
		gestao_de_menu.setValue(Core.getIGRPLink("igrp","PesquisarMenu","index"));

									gestao_de_menu.propertie().add("name","p_gestao_de_menu").add("type","link").add("target","mpsubmit").add("class","info").add("img","fa-bars").add("maxlength","30").add("placeholder",gt("")).add("request_fields","").add("refresh_submit","true").add("desclabel","false").add("refresh_components","");
		
		id_app = new HiddenField(model,"id_app");
		id_app.setLabel(gt(""));
		id_app.propertie().add("name","p_id_app").add("type","hidden").add("maxlength","250").add("java-type","int").add("tag","id_app");
		
		estado = new CheckBoxField(model,"estado");
		estado.setLabel(gt("Estado"));
		estado.propertie().add("name","p_estado").add("type","checkbox").add("maxlength","10").add("switch","false").add("java-type","int").add("showLabel","true").add("group_in","").add("check","true").add("desc","true");
		
		estado_check = new CheckBoxField(model,"estado_check");
		estado_check.propertie().add("name","p_estado").add("type","checkbox").add("maxlength","10").add("switch","false").add("java-type","int").add("showLabel","true").add("group_in","").add("check","true").add("desc","true");
		
		org_nome = new TextField(model,"org_nome");
		org_nome.setLabel(gt("Nome"));
		org_nome.propertie().add("name","p_org_nome").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		mostrar_perfis = new LinkField(model,"mostrar_perfis");
		mostrar_perfis.setLabel(gt("Mostrar perfis"));
		mostrar_perfis.setValue(Core.getIGRPLink("igrp","Dominio","index"));

									mostrar_perfis_desc = new LinkField(model,"mostrar_perfis_desc");
		mostrar_perfis_desc.setLabel(gt("Mostrar perfis"));
		mostrar_perfis.propertie().add("name","p_mostrar_perfis").add("type","link").add("target","mpsubmit").add("class","primary").add("img","fa-address-card").add("maxlength","30").add("request_fields","").add("show_header","true").add("refresh_submit","false").add("list_source","").add("showLabel","true").add("refresh_components","").add("group_in","").add("desc","true");
		
		id = new HiddenField(model,"id");
		id.setLabel(gt(""));
		id.propertie().add("name","p_id").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","id");
		


		btn_editar = new IGRPButton("Editar","igrp","Gestaodeacesso","editar","right_panel|refresh_submit","warning|fa-pencil","","");
		btn_editar.propertie.add("id","button_ef0b_e3d3").add("type","specific").add("class","warning").add("rel","editar").add("refresh_components","");

		btn_menu = new IGRPButton("Menu","igrp","Gestaodeacesso","menu","right_panel|refresh_submit","info|fa-bars","","");
		btn_menu.propertie.add("id","button_ffb1_fe36").add("type","specific").add("class","info").add("rel","menu").add("refresh_components","");

		btn_transacti_org = new IGRPButton("Transaction","igrp","Gestaodeacesso","transacti_org","right_panel","black|fa-exchange","","");
		btn_transacti_org.propertie.add("id","button_a597_1f01").add("type","specific").add("class","black").add("rel","transacti_org").add("refresh_components","");

		btn_eliminar = new IGRPButton("Eliminar","igrp","Gestaodeacesso","eliminar","confirm","danger|fa-trash","","");
		btn_eliminar.propertie.add("id","button_1699_b3b1").add("type","specific").add("class","danger").add("rel","eliminar").add("refresh_components","");

		btn_associar_etapa = new IGRPButton("Associar Etapa","igrp","Gestaodeacesso","associar_etapa","right_panel","primary|fa-sitemap","","");
		btn_associar_etapa.propertie.add("id","button_5045_3859").add("type","specific").add("class","primary").add("rel","associar_etapa").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		view_1.addField(documento_link);
		view_1.addField(forum);

		form_1.addField(aplicacao);
		form_1.addField(adicionar_organica);
		form_1.addField(gestao_de_utilizadores);
		form_1.addField(gestao_de_menu);
		form_1.addField(id_app);

		org_table.addField(estado);
		org_table.addField(estado_check);
		org_table.addField(org_nome);
		org_table.addField(mostrar_perfis);
		org_table.addField(mostrar_perfis_desc);
		org_table.addField(id);

		org_table.addButton(btn_editar);
		org_table.addButton(btn_menu);
		org_table.addButton(btn_transacti_org);
		org_table.addButton(btn_eliminar);
		org_table.addButton(btn_associar_etapa);
		this.addToPage(sectionheader_1);
		this.addToPage(view_1);
		this.addToPage(form_1);
		this.addToPage(org_table);
	}
		
	@Override
	public void setModel(Model model) {
		
		aplicacao.setValue(model);
		adicionar_organica.setValue(model);
		gestao_de_utilizadores.setValue(model);
		gestao_de_menu.setValue(model);
		id_app.setValue(model);
		estado.setValue(model);
		org_nome.setValue(model);
		mostrar_perfis.setValue(model);
		mostrar_perfis_desc.setValue(model);
		id.setValue(model);	

		org_table.loadModel(((Gestaodeacesso) model).getOrg_table());
		}
}
