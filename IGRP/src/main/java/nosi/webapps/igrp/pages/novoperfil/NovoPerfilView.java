package nosi.webapps.igrp.pages.novoperfil;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;
import nosi.core.webapp.Core;

public class NovoPerfilView extends View {

	public Field sectionheader_1_text;
	public Field nome;
	public Field codigo;
	public Field activo;
	public Field activo_check;
	public Field nada;
	public Field aplicacao;
	public Field organica;
	public Field perfil_pai;
	public Field extras;
	public Field primeira_pagina;
	public Field igrp_code;
	public Field id_perfil;
	public IGRPSectionHeader sectionheader_1;
	public IGRPForm form_1;

	public IGRPToolsBar toolsbar_1;
	public IGRPButton btn_gravar;

	public NovoPerfilView(){

		this.setPageTitle("Registar Perfil");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		form_1 = new IGRPForm("form_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Gestão de Perfil - Novo"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		nome = new TextField(model,"nome");
		nome.setLabel(gt("Nome"));
		nome.propertie().add("remote",Core.getIGRPLink("igrp","NovoPerfil","fillCodigo")).add("name","p_nome").add("type","text").add("maxlength","255").add("required","true").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false");
		
		codigo = new TextField(model,"codigo");
		codigo.setLabel(gt("Código"));
		codigo.propertie().add("name","p_codigo").add("type","text").add("maxlength","255").add("required","true").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false");
		
		activo = new CheckBoxField(model,"activo");
		activo.setLabel(gt("Ativo?"));
		activo.propertie().add("name","p_activo").add("type","checkbox").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("switch","true").add("java-type","int").add("check","true");
		
		nada = new SeparatorField(model,"nada");
		nada.setLabel(gt(" "));
		nada.propertie().add("name","p_nada").add("type","separator").add("maxlength","30").add("placeholder",gt("")).add("desclabel","false");
		
		aplicacao = new ListField(model,"aplicacao");
		aplicacao.setLabel(gt("Aplicação"));
		aplicacao.propertie().add("name","p_aplicacao").add("type","select").add("multiple","false").add("maxlength","30").add("required","true").add("disabled","false").add("domain","").add("java-type","int").add("tags","false");
		
		organica = new ListField(model,"organica");
		organica.setLabel(gt("Organização"));
		organica.propertie().add("name","p_organica").add("type","select").add("multiple","false").add("maxlength","30").add("required","true").add("disabled","false").add("domain","").add("java-type","int").add("tags","false");
		
		perfil_pai = new ListField(model,"perfil_pai");
		perfil_pai.setLabel(gt("Perfil pai"));
		perfil_pai.propertie().add("name","p_perfil_pai").add("type","select").add("multiple","false").add("maxlength","30").add("required","false").add("disabled","false").add("domain","").add("java-type","int").add("tags","false");
		
		extras = new SeparatorField(model,"extras");
		extras.setLabel(gt("Extras"));
		extras.propertie().add("name","p_extras").add("type","separator").add("maxlength","250").add("placeholder",gt("")).add("desclabel","false");
		
		primeira_pagina = new ListField(model,"primeira_pagina");
		primeira_pagina.setLabel(gt("Primeira página"));
		primeira_pagina.propertie().add("name","p_primeira_pagina").add("type","select").add("multiple","false").add("tags","false").add("domain","").add("maxlength","250").add("required","false").add("disabled","false").add("java-type","int");
		
		igrp_code = new TextField(model,"igrp_code");
		igrp_code.setLabel(gt("IGRP (code)"));
		igrp_code.propertie().add("name","p_igrp_code").add("type","text").add("maxlength","250").add("required","false").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false");
		
		id_perfil = new HiddenField(model,"id_perfil");
		id_perfil.setLabel(gt(""));
		id_perfil.propertie().add("name","p_id_perfil").add("type","hidden").add("maxlength","250").add("java-type","").add("tag","id_perfil");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");

		btn_gravar = new IGRPButton("Gravar","igrp","NovoPerfil","gravar","submit","primary|fa-save","","");
		btn_gravar.propertie.add("type","specific").add("rel","gravar").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);


		form_1.addField(nome);
		form_1.addField(codigo);
		form_1.addField(activo);
		form_1.addField(nada);
		form_1.addField(aplicacao);
		form_1.addField(organica);
		form_1.addField(perfil_pai);
		form_1.addField(extras);
		form_1.addField(primeira_pagina);
		form_1.addField(igrp_code);
		form_1.addField(id_perfil);

		toolsbar_1.addButton(btn_gravar);
		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
		this.addToPage(toolsbar_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		nome.setValue(model);
		codigo.setValue(model);
		activo.setValue(model);
		nada.setValue(model);
		aplicacao.setValue(model);
		organica.setValue(model);
		perfil_pai.setValue(model);
		extras.setValue(model);
		primeira_pagina.setValue(model);
		igrp_code.setValue(model);
		id_perfil.setValue(model);	

		}
}
