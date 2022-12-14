package nosi.webapps.igrp.pages.configdatabase;

import static nosi.core.i18n.Translator.gt;

import nosi.core.config.Config;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.Model;
import nosi.core.webapp.View;

public class ConfigDatabaseView extends View {

	public Field sectionheader_1_text;
	public Field aplicacao;
	public Field tipo_base_dados;
	public Field nome_de_conexao;
	public Field config;
	public Field url_connection;
	public Field driver_connection;
	public Field paragraph_1;
	public Field credenciais;
	public Field username;
	public Field password;
	public Field default_;
	public Field link_doc;
	public Field default__check;
	public Field nome_de_conexao_tabela;
	public Field user_name_tabela;
	public Field tipo_de_base_de_dados_tabela;
	public Field t_url_connection;
	public Field t_driver_connection;
	public Field id;
	public IGRPForm sectionheader_1;
	public IGRPForm form_1;
	public IGRPTable table_1;
	

	public IGRPToolsBar toolsbar_1;
	public IGRPButton btn_gravar;
	public IGRPButton btn_edit;
	public IGRPButton btn_delete;

	public ConfigDatabaseView(){

		this.setPageTitle("Configurar Base de Dados de uma aplicação");
			
		sectionheader_1 = new IGRPForm("sectionheader_1","");

		form_1 = new IGRPForm("form_1","");

		table_1 = new IGRPTable("table_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Configuração da base de dados"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		aplicacao = new ListField(model,"aplicacao");
		aplicacao.setLabel(gt("Aplicação"));
		aplicacao.propertie().add("name","p_aplicacao").add("type","select").add("multiple","false").add("domain","").add("maxlength","30").add("required","true").add("disabled","false").add("java-type","").add("tags","false");
		
		tipo_base_dados = new ListField(model,"tipo_base_dados");
		tipo_base_dados.setLabel(gt("Tipo de base de dados"));
		tipo_base_dados.propertie().add("name","p_tipo_base_dados").add("type","select").add("multiple","false").add("maxlength","20").add("required","true").add("disabled","false").add("domain","").add("java-type","").add("tags","false");
		
		nome_de_conexao = new TextField(model,"nome_de_conexao");
		nome_de_conexao.setLabel(gt("Nome da conexão"));
		nome_de_conexao.propertie().add("name","p_nome_de_conexao").add("type","text").add("maxlength","255").add("required","true").add("readonly","false").add("disabled","false").add("desclabel","false");
		
		config = new SeparatorField(model,"config");
		config.setLabel(gt("Config"));
		config.propertie().add("name","p_config").add("type","separator").add("maxlength","30").add("desclabel","false");
		
		url_connection = new TextField(model,"url_connection");
		url_connection.setLabel(gt("Url Connection"));
		url_connection.propertie().add("name","p_url_connection").add("type","text").add("maxlength","250").add("required","true").add("readonly","false").add("disabled","false").add("desclabel","false");
		
		driver_connection = new TextField(model,"driver_connection");
		driver_connection.setLabel(gt("Driver Connection"));
		driver_connection.propertie().add("name","p_driver_connection").add("type","text").add("maxlength","250").add("required","true").add("readonly","false").add("disabled","false").add("desclabel","false");
		
		paragraph_1 = new PlainTextField(model,"paragraph_1");
		paragraph_1.setLabel(gt("Example"));
		paragraph_1.propertie().add("name","p_paragraph_1").add("type","plaintext").add("clear","false").add("maxlength","250");
		
		credenciais = new SeparatorField(model,"credenciais");
		credenciais.setLabel(gt("Credenciais"));
		credenciais.propertie().add("name","p_credenciais").add("type","separator").add("maxlength","30").add("desclabel","false");
		
		username = new TextField(model,"username");
		username.setLabel(gt("Username"));
		username.propertie().add("name","p_username").add("type","text").add("maxlength","80").add("required","true").add("readonly","false").add("disabled","false").add("desclabel","false");
		
		password = new PasswordField(model,"password");
		password.setLabel(gt("Password"));
		password.propertie().add("name","p_password").add("type","password").add("maxlength","80").add("required","false").add("readonly","false").add("disabled","false").add("desclabel","false");
		
		default_ = new RadioField(model,"default_");
		default_.setLabel(gt("Default"));
		default_.propertie().add("remote",new Config().getResolveUrl("igrp","ConfigDatabase","changeStatus")).add("name","p_default_").add("type","radio").add("maxlength","30").add("java-type","").add("showLabel","true").add("check","true").add("desc","true");
		
		default__check = new RadioField(model,"default__check");
		default__check.propertie().add("name","p_default_").add("type","radio").add("maxlength","30").add("java-type","").add("showLabel","true").add("check","true").add("desc","true");
		
		nome_de_conexao_tabela = new TextField(model,"nome_de_conexao_tabela");
		nome_de_conexao_tabela.setLabel(gt("Nome da conexão"));
		nome_de_conexao_tabela.propertie().add("name","p_nome_de_conexao_tabela").add("type","text").add("maxlength","30").add("showLabel","true");
		
		link_doc = new LinkField(model,"link_doc");
		link_doc.setLabel(gt("link_doc"));
		link_doc.setValue(new Config().getResolveUrl("tutorial","Listar_documentos","index&p_type=base_dados"));
		link_doc.propertie().add("name","p_link_doc").add("type","link").add("target","modal").add("maxlength","30");
		
		user_name_tabela = new TextField(model,"user_name_tabela");
		user_name_tabela.setLabel(gt("Username"));
		user_name_tabela.propertie().add("name","p_user_name_tabela").add("type","text").add("maxlength","30").add("showLabel","true");
		
		tipo_de_base_de_dados_tabela = new TextField(model,"tipo_de_base_de_dados_tabela");
		tipo_de_base_de_dados_tabela.setLabel(gt("Tipo de base de dados"));
		tipo_de_base_de_dados_tabela.propertie().add("name","p_tipo_de_base_de_dados_tabela").add("type","text").add("maxlength","30").add("showLabel","true");
		
		t_url_connection = new TextField(model,"t_url_connection");
		t_url_connection.setLabel(gt("Url Connection"));
		t_url_connection.propertie().add("name","p_t_url_connection").add("type","text").add("maxlength","30").add("showLabel","true");
		
		t_driver_connection = new TextField(model,"t_driver_connection");
		t_driver_connection.setLabel(gt("Driver Connection"));
		t_driver_connection.propertie().add("name","p_t_driver_connection").add("type","text").add("maxlength","30").add("showLabel","true");
		
		id = new HiddenField(model,"id");
		id.setLabel(gt(""));
		id.propertie().add("name","p_id").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("tag","id");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");

		btn_gravar = new IGRPButton("Testar conexão e gravar","igrp","ConfigDatabase","gravar","submit","primary|fa-save","","");
		btn_gravar.propertie.add("type","specific").add("rel","gravar");

		btn_edit = new IGRPButton("Edit","igrp","ConfigDatabase","edit","submit","warning|fa-pencil","","");
		btn_edit.propertie.add("type","specific").add("rel","edit");

		btn_delete = new IGRPButton("Delete","igrp","ConfigDatabase","delete","submit","danger|fa-trash","","");
		btn_delete.propertie.add("type","specific").add("rel","delete");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);


		form_1.addField(aplicacao);
		form_1.addField(tipo_base_dados);
		form_1.addField(nome_de_conexao);
		form_1.addField(config);
		form_1.addField(url_connection);
		form_1.addField(driver_connection);
		form_1.addField(paragraph_1);
		form_1.addField(credenciais);
		form_1.addField(username);
		form_1.addField(password);
		form_1.addField(link_doc);

		table_1.addField(default_);
		table_1.addField(default__check);
		table_1.addField(nome_de_conexao_tabela);
		table_1.addField(user_name_tabela);
		table_1.addField(tipo_de_base_de_dados_tabela);
		table_1.addField(t_url_connection);
		table_1.addField(t_driver_connection);
		table_1.addField(id);

		toolsbar_1.addButton(btn_gravar);
		table_1.addButton(btn_edit);
		table_1.addButton(btn_delete);
		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
		this.addToPage(table_1);
		this.addToPage(toolsbar_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		aplicacao.setValue(model);
		tipo_base_dados.setValue(model);
		nome_de_conexao.setValue(model);
		config.setValue(model);
		url_connection.setValue(model);
		driver_connection.setValue(model);
		paragraph_1.setValue(model);
		credenciais.setValue(model);
		username.setValue(model);
		password.setValue(model);
		default_.setValue(model);
		nome_de_conexao_tabela.setValue(model);
		user_name_tabela.setValue(model);
		tipo_de_base_de_dados_tabela.setValue(model);
		t_url_connection.setValue(model);
		t_driver_connection.setValue(model);
		id.setValue(model);	

		table_1.loadModel(((ConfigDatabase) model).getTable_1());
		}
}
