package nosi.webapps.igrp.pages.pesquisanif;

import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.Model;
import nosi.core.webapp.View;

public class PesquisaNIFView extends View {

	public Field sectionheader_1_text;
	public Field nif;
	public Field nome;
	public Field n_documento;
	public Field tipo_contribuinte;
	public Field nif_tabela;
	public Field nome_tabela;
	public Field desig_social;
	public Field data_nascimento;
	public Field nome_pai;
	public Field nome_mae;
	public Field documento;
	public IGRPForm sectionheader_1;
	public IGRPForm form_1;
	public IGRPTable table_1;

	public IGRPButton btn_pesquisar;

	public PesquisaNIFView(){

		this.setPageTitle("Pesquisar NIF");
			
		sectionheader_1 = new IGRPForm("sectionheader_1","");

		form_1 = new IGRPForm("form_1","");

		table_1 = new IGRPTable("table_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Pesquisa NIF [DIREÇÃO GERAL DAS CONTRIBUIÇÕES E IMPOSTOS]"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		nif = new NumberField(model,"nif");
		nif.setLabel(gt("NIF"));
		nif.propertie().add("name","p_nif").add("type","number").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("java-type","");
		
		nome = new TextField(model,"nome");
		nome.setLabel(gt("Nome"));
		nome.propertie().add("name","p_nome").add("type","text").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false");
		
		n_documento = new NumberField(model,"n_documento");
		n_documento.setLabel(gt("Nº Documento"));
		n_documento.propertie().add("name","p_n_documento").add("type","number").add("min","").add("max","").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("java-type","");
		
		tipo_contribuinte = new ListField(model,"tipo_contribuinte");
		tipo_contribuinte.setLabel(gt("Tipo Contribuinte"));
		tipo_contribuinte.propertie().add("name","p_tipo_contribuinte").add("type","select").add("multiple","false").add("domain","").add("maxlength","30").add("required","true").add("disabled","false").add("tags","false").add("java-type","");
		
		nif_tabela = new TextField(model,"nif_tabela");
		nif_tabela.setLabel(gt("NIF"));
		nif_tabela.propertie().add("name","p_nif_tabela").add("type","text").add("maxlength","30");
		
		nome_tabela = new TextField(model,"nome_tabela");
		nome_tabela.setLabel(gt("Nome"));
		nome_tabela.propertie().add("name","p_nome_tabela").add("type","text").add("maxlength","30");
		
		desig_social = new TextField(model,"desig_social");
		desig_social.setLabel(gt("Desig. Social"));
		desig_social.propertie().add("name","p_desig_social").add("type","text").add("maxlength","30");
		
		data_nascimento = new TextField(model,"data_nascimento");
		data_nascimento.setLabel(gt("Data Nascimento"));
		data_nascimento.propertie().add("name","p_data_nascimento").add("type","text").add("maxlength","30");
		
		nome_pai = new TextField(model,"nome_pai");
		nome_pai.setLabel(gt("Nome Pai"));
		nome_pai.propertie().add("name","p_nome_pai").add("type","text").add("maxlength","30");
		
		nome_mae = new TextField(model,"nome_mae");
		nome_mae.setLabel(gt("Nome Mãe"));
		nome_mae.propertie().add("name","p_nome_mae").add("type","text").add("maxlength","30");
		
		documento = new TextField(model,"documento");
		documento.setLabel(gt("Documento"));
		documento.propertie().add("name","p_documento").add("type","text").add("maxlength","30");
		


		btn_pesquisar = new IGRPButton("Pesquisar","igrp","PesquisaNIF","pesquisar","submit","primary|fa-search","","");
		btn_pesquisar.propertie.add("type","form").add("rel","pesquisar");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		form_1.addField(nif);
		form_1.addField(nome);
		form_1.addField(n_documento);
		form_1.addField(tipo_contribuinte);

		table_1.addField(nif_tabela);
		table_1.addField(nome_tabela);
		table_1.addField(desig_social);
		table_1.addField(data_nascimento);
		table_1.addField(nome_pai);
		table_1.addField(nome_mae);
		table_1.addField(documento);

		form_1.addButton(btn_pesquisar);
		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
		this.addToPage(table_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		nif.setValue(model);
		nome.setValue(model);
		n_documento.setValue(model);
		tipo_contribuinte.setValue(model);
		nif_tabela.setValue(model);
		nome_tabela.setValue(model);
		desig_social.setValue(model);
		data_nascimento.setValue(model);
		nome_pai.setValue(model);
		nome_mae.setValue(model);
		documento.setValue(model);	

		table_1.loadModel(((PesquisaNIF) model).getTable_1());
		}
}
