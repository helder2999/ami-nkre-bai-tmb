package nosi.webapps.igrp_studio.pages.pesquisa_bi_cni_passport;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;
import nosi.core.webapp.Core;

public class Pesquisa_bi_cni_passportView extends View {

	public Field sectionheader_1_text;
	public Field numero_do_documento;
	public Field tipo_documento;
	public Field data_nascimento;
	public Field nome;
	public Field tipo_documento_tab;
	public Field n_doc_tab;
	public Field nome_tab;
	public Field sexo_tab;
	public Field data_nascimento_tab;
	public Field nome_pai_tab;
	public Field nome_mae_tab;
	public Field data_emissao_tab;
	public Field emissor_tab;
	public Field nic_cni_tab;
	public Field estado_civil;
	public Field nat_conselho;
	public Field residencia;
	public Field dt_validade;
	public Field bi_tab;
	public Field passaporte_tab;
	public IGRPSectionHeader sectionheader_1;
	public IGRPForm form_1;
	public IGRPTable table_1;

	public IGRPButton btn_pesquisar;

	public Pesquisa_bi_cni_passportView(){

		this.setPageTitle("Pesquisa BI-CNI-passport");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		form_1 = new IGRPForm("form_1","");

		table_1 = new IGRPTable("table_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("<p>Pesquisa BI / NIC (CNI) / Passaporte novo</p>"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		numero_do_documento = new TextField(model,"numero_do_documento");
		numero_do_documento.setLabel(gt("Número do documento"));
		numero_do_documento.propertie().add("remote",Core.getIGRPLink("igrp_studio","Pesquisa_bi_cni_passport","index")).add("name","p_numero_do_documento").add("type","text").add("maxlength","250").add("required","false").add("readonly","false").add("disabled","false").add("placeholder",gt("BI/NIC/Passaporte. Ex: 1234567 / 19801002M001Q / PA123456")).add("desclabel","true");
		
		tipo_documento = new ListField(model,"tipo_documento");
		tipo_documento.setLabel(gt("Tipo documento"));
		tipo_documento.propertie().add("name","p_tipo_documento").add("type","select").add("multiple","false").add("tags","false").add("domain","").add("maxlength","250").add("required","true").add("disabled","false").add("java-type","");
		
		data_nascimento = new DateField(model,"data_nascimento");
		data_nascimento.setLabel(gt("Data nascimento"));
		data_nascimento.propertie().add("name","p_data_nascimento").add("type","date").add("range","false").add("maxlength","250").add("required","false").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("class","primary").add("desclabel","false");
		
		nome = new TextField(model,"nome");
		nome.setLabel(gt("Nome"));
		nome.propertie().add("name","p_nome").add("type","text").add("maxlength","250").add("required","false").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false");
		
		tipo_documento_tab = new TextField(model,"tipo_documento_tab");
		tipo_documento_tab.setLabel(gt("Doc."));
		tipo_documento_tab.propertie().add("name","p_tipo_documento_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		n_doc_tab = new TextField(model,"n_doc_tab");
		n_doc_tab.setLabel(gt("N# do documento"));
		n_doc_tab.propertie().add("name","p_n_doc_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		nome_tab = new TextField(model,"nome_tab");
		nome_tab.setLabel(gt("Nome"));
		nome_tab.propertie().add("name","p_nome_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		sexo_tab = new TextField(model,"sexo_tab");
		sexo_tab.setLabel(gt("Sexo"));
		sexo_tab.propertie().add("name","p_sexo_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		data_nascimento_tab = new DateField(model,"data_nascimento_tab");
		data_nascimento_tab.setLabel(gt("Data nascimento"));
		data_nascimento_tab.propertie().add("name","p_data_nascimento_tab").add("type","date").add("range","false").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		nome_pai_tab = new TextField(model,"nome_pai_tab");
		nome_pai_tab.setLabel(gt("Nome pai"));
		nome_pai_tab.propertie().add("name","p_nome_pai_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		nome_mae_tab = new TextField(model,"nome_mae_tab");
		nome_mae_tab.setLabel(gt("Nome mãe"));
		nome_mae_tab.propertie().add("name","p_nome_mae_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		data_emissao_tab = new TextField(model,"data_emissao_tab");
		data_emissao_tab.setLabel(gt("Data Emissão"));
		data_emissao_tab.propertie().add("name","p_data_emissao_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		emissor_tab = new TextField(model,"emissor_tab");
		emissor_tab.setLabel(gt("Emissor"));
		emissor_tab.propertie().add("name","p_emissor_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		nic_cni_tab = new TextField(model,"nic_cni_tab");
		nic_cni_tab.setLabel(gt("NIC"));
		nic_cni_tab.propertie().add("name","p_nic_cni_tab").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		estado_civil = new HiddenField(model,"estado_civil");
		estado_civil.setLabel(gt(""));
		estado_civil.propertie().add("name","p_estado_civil").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","estado_civil");
		
		nat_conselho = new HiddenField(model,"nat_conselho");
		nat_conselho.setLabel(gt(""));
		nat_conselho.propertie().add("name","p_nat_conselho").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","nat_conselho");
		
		residencia = new HiddenField(model,"residencia");
		residencia.setLabel(gt(""));
		residencia.propertie().add("name","p_residencia").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","residencia");
		
		dt_validade = new HiddenField(model,"dt_validade");
		dt_validade.setLabel(gt(""));
		dt_validade.propertie().add("name","p_dt_validade").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","dt_validade");
		
		bi_tab = new HiddenField(model,"bi_tab");
		bi_tab.setLabel(gt(""));
		bi_tab.propertie().add("name","p_bi_tab").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","bi_tab");
		
		passaporte_tab = new HiddenField(model,"passaporte_tab");
		passaporte_tab.setLabel(gt(""));
		passaporte_tab.propertie().add("name","p_passaporte_tab").add("type","hidden").add("maxlength","30").add("java-type","").add("showLabel","true").add("group_in","").add("tag","passaporte_tab");
		


		btn_pesquisar = new IGRPButton("Pesquisar","igrp_studio","Pesquisa_bi_cni_passport","pesquisar","submit_ajax","primary|fa-search","","");
		btn_pesquisar.propertie.add("id","button_57fe_d801").add("type","form").add("class","primary").add("rel","pesquisar").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		form_1.addField(numero_do_documento);
		form_1.addField(tipo_documento);
		form_1.addField(data_nascimento);
		form_1.addField(nome);

		table_1.addField(tipo_documento_tab);
		table_1.addField(n_doc_tab);
		table_1.addField(nome_tab);
		table_1.addField(sexo_tab);
		table_1.addField(data_nascimento_tab);
		table_1.addField(nome_pai_tab);
		table_1.addField(nome_mae_tab);
		table_1.addField(data_emissao_tab);
		table_1.addField(emissor_tab);
		table_1.addField(nic_cni_tab);
		table_1.addField(estado_civil);
		table_1.addField(nat_conselho);
		table_1.addField(residencia);
		table_1.addField(dt_validade);
		table_1.addField(bi_tab);
		table_1.addField(passaporte_tab);

		form_1.addButton(btn_pesquisar);
		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
		this.addToPage(table_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		numero_do_documento.setValue(model);
		tipo_documento.setValue(model);
		data_nascimento.setValue(model);
		nome.setValue(model);
		tipo_documento_tab.setValue(model);
		n_doc_tab.setValue(model);
		nome_tab.setValue(model);
		sexo_tab.setValue(model);
		data_nascimento_tab.setValue(model);
		nome_pai_tab.setValue(model);
		nome_mae_tab.setValue(model);
		data_emissao_tab.setValue(model);
		emissor_tab.setValue(model);
		nic_cni_tab.setValue(model);
		estado_civil.setValue(model);
		nat_conselho.setValue(model);
		residencia.setValue(model);
		dt_validade.setValue(model);
		bi_tab.setValue(model);
		passaporte_tab.setValue(model);	

		table_1.loadModel(((Pesquisa_bi_cni_passport) model).getTable_1());
		}
}
