package nosi.webapps.igrp.pages.settings;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;

public class SettingsView extends View {

	public Field sectionheader_1_text;
	public Field nome;
	public Field email;
	public Field username;
	public Field cni;
	public Field telefone;
	public Field telemovel;
	public Field ultimo_acesso_igrp;
	public Field ultimo_acesso_rede_estado;
	public Field password_expira_em;
	public Field view_1_img;
	public Field idioma;
	public Field separator_1;
	public Field perfil;
	public Field s_as;
	public Field assinatura;
	public Field organica;
	public IGRPSectionHeader sectionheader_1;
	public IGRPView view_1;
	public IGRPForm form_1;

	public IGRPToolsBar toolsbar_1;
	public IGRPButton btn_alterar_senha;
	public IGRPButton btn_editar_perfil;

	public SettingsView(){

		this.setPageTitle("Área pessoal");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		view_1 = new IGRPView("view_1","");

		form_1 = new IGRPForm("form_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Área pessoal"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		nome = new TextField(model,"nome");
		nome.setLabel(gt("Nome"));
		nome.propertie().add("name","p_nome").add("type","text").add("maxlength","100").add("class","default").add("img","").add("showlabel","true");
		
		email = new TextField(model,"email");
		email.setLabel(gt("Email"));
		email.propertie().add("name","p_email").add("type","text").add("maxlength","100").add("class","default").add("img","").add("showlabel","true");
		
		username = new TextField(model,"username");
		username.setLabel(gt("Username"));
		username.propertie().add("name","p_username").add("type","text").add("maxlength","30").add("class","default").add("img","").add("showlabel","true");
		
		cni = new TextField(model,"cni");
		cni.setLabel(gt("CNI"));
		cni.propertie().add("name","p_cni").add("type","text").add("maxlength","250").add("class","default").add("img","").add("showlabel","true");
		
		telefone = new TextField(model,"telefone");
		telefone.setLabel(gt("Telefone"));
		telefone.propertie().add("name","p_telefone").add("type","text").add("maxlength","30").add("class","default").add("img","").add("showlabel","true");
		
		telemovel = new TextField(model,"telemovel");
		telemovel.setLabel(gt("Telemóvel"));
		telemovel.propertie().add("name","p_telemovel").add("type","text").add("maxlength","30").add("class","default").add("img","").add("showlabel","true");
		
		ultimo_acesso_igrp = new TextField(model,"ultimo_acesso_igrp");
		ultimo_acesso_igrp.setLabel(gt("Ultimo Acesso IGRP"));
		ultimo_acesso_igrp.propertie().add("name","p_ultimo_acesso_igrp").add("type","text").add("maxlength","30").add("class","default").add("img","").add("showlabel","true");
		
		ultimo_acesso_rede_estado = new TextField(model,"ultimo_acesso_rede_estado");
		ultimo_acesso_rede_estado.setLabel(gt("Ultimo Acesso Rede Estado"));
		ultimo_acesso_rede_estado.propertie().add("name","p_ultimo_acesso_rede_estado").add("type","text").add("maxlength","30").add("class","default").add("img","").add("showlabel","true");
		
		password_expira_em = new TextField(model,"password_expira_em");
		password_expira_em.setLabel(gt("Password Expira em"));
		password_expira_em.propertie().add("name","p_password_expira_em").add("type","text").add("maxlength","30").add("class","default").add("img","").add("showlabel","true");
		
		view_1_img = new TextField(model,"view_1_img");
		view_1_img.setLabel(gt(""));
		view_1_img.propertie().add("type","text").add("name","p_view_1_img").add("maxlength","300");
		
		idioma = new ListField(model,"idioma");
		idioma.setLabel(gt(" Idioma"));
		idioma.propertie().add("name","p_idioma").add("type","select").add("multiple","false").add("maxlength","30").add("required","false").add("disabled","false").add("domain","").add("java-type","").add("tags","false").add("load_service_data","false").add("tooltip","false").add("disable_copy_paste","false");
		
		separator_1 = new SeparatorField(model,"separator_1");
		separator_1.setLabel(gt("Acesso"));
		separator_1.propertie().add("name","p_separator_1").add("type","separator").add("maxlength","30").add("placeholder",gt("")).add("desclabel","false").add("tooltip","false").add("disable_copy_paste","false");
		
		perfil = new ListField(model,"perfil");
		perfil.setLabel(gt("Perfil"));
		perfil.propertie().add("name","p_perfil").add("type","select").add("multiple","false").add("maxlength","30").add("required","true").add("disabled","false").add("domain","").add("java-type","").add("tags","false").add("load_service_data","false").add("tooltip","false").add("disable_copy_paste","false");
		
		s_as = new SeparatorField(model,"s_as");
		s_as.setLabel(gt("Assinatura"));
		s_as.propertie().add("name","p_s_as").add("type","separator").add("maxlength","250").add("placeholder",gt("")).add("desclabel","false").add("tooltip","false").add("disable_copy_paste","false");
		
		assinatura = new TextField(model,"assinatura");
		assinatura.setLabel(gt("Img"));
		assinatura.setValue(gt("../images/IGRP/IGRP2.3/assets/img/v1/assinatura.png"));
		assinatura.propertie().add("name","p_assinatura").add("type","img").add("img","../images/IGRP/IGRP2.3/assets/img/v1/assinatura.png").add("width","").add("height","").add("croppie","false").add("rounded","false").add("autoupload","false").add("maxlength","250").add("placeholder",gt("")).add("desclabel","false").add("tooltip","false").add("disable_copy_paste","false");
		
		organica = new HiddenField(model,"organica");
		organica.setLabel(gt(""));
		organica.propertie().add("name","p_organica").add("type","hidden").add("maxlength","30").add("java-type","").add("tooltip","false").add("disable_copy_paste","false").add("tag","organica");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");

		btn_alterar_senha = new IGRPButton("Alterar senha","igrp","Settings","alterar_senha","right_panel","info|fa-lock","","");
		btn_alterar_senha.propertie.add("type","specific").add("rel","alterar_senha").add("refresh_components","");

		btn_editar_perfil = new IGRPButton("Editar Perfil","igrp","Settings","editar_perfil","right_panel_submit|refresh","warning|fa-pencil-square-o","","");
		btn_editar_perfil.propertie.add("type","specific").add("rel","editar_perfil").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);


		view_1.addField(nome);
		view_1.addField(email);
		view_1.addField(username);
		view_1.addField(cni);
		view_1.addField(telefone);
		view_1.addField(telemovel);
		view_1.addField(ultimo_acesso_igrp);
		view_1.addField(ultimo_acesso_rede_estado);
		view_1.addField(password_expira_em);
		view_1.addField(view_1_img);

		form_1.addField(idioma);
		form_1.addField(separator_1);
		form_1.addField(perfil);
		form_1.addField(s_as);
		form_1.addField(assinatura);
		form_1.addField(organica);

		toolsbar_1.addButton(btn_alterar_senha);
		toolsbar_1.addButton(btn_editar_perfil);
		this.addToPage(sectionheader_1);
		this.addToPage(view_1);
		this.addToPage(form_1);
		this.addToPage(toolsbar_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		nome.setValue(model);
		email.setValue(model);
		username.setValue(model);
		cni.setValue(model);
		telefone.setValue(model);
		telemovel.setValue(model);
		ultimo_acesso_igrp.setValue(model);
		ultimo_acesso_rede_estado.setValue(model);
		password_expira_em.setValue(model);
		view_1_img.setValue(model);
		idioma.setValue(model);
		separator_1.setValue(model);
		perfil.setValue(model);
		s_as.setValue(model);
		organica.setValue(model);	

		}
}
