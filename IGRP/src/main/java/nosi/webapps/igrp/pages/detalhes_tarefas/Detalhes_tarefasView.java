
package nosi.webapps.igrp.pages.detalhes_tarefas;
import static nosi.core.i18n.Translator.gt;

import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import nosi.core.webapp.View;

public class Detalhes_tarefasView extends View {
	
	
	public Field sectionheader_1_text;
	public Field tipo_da_tarefa;
	public Field data_criacao_da_tarefa;
	public Field data_inicio_da_tarefa;
	public Field data_fim_da_tarefa;
	public Field prioridade_da_tarefa;
	public Field tarefa_atribuida_por;
	public Field tarefa_atribuida_a;
	public Field tipo_de_processo;
	public Field numero_de_processo;
	public Field data_criacao_de_processo;
	public Field data_inicio_de_processo;
	public Field data_fim_processo;
	public Field descricao_da_tarefa;
	public IGRPSectionHeader sectionheader_1;
	public IGRPForm form_1;

	public Detalhes_tarefasView(Detalhes_tarefas model){
		this.setPageTitle("Detalhes Tarefas");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");
		form_1 = new IGRPForm("form_1","");
		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		
		sectionheader_1_text.setValue(gt("Detalhes da Tarefa"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("persist","true").add("maxlength","4000");
		tipo_da_tarefa = new TextField(model,"tipo_da_tarefa");
		tipo_da_tarefa.setLabel(gt("Tipo da Tarefa"));
		
		tipo_da_tarefa.propertie().add("name","p_tipo_da_tarefa").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		data_criacao_da_tarefa = new TextField(model,"data_criacao_da_tarefa");
		data_criacao_da_tarefa.setLabel(gt("Data Criação da Tarefa"));
		
		data_criacao_da_tarefa.propertie().add("name","p_data_criacao_da_tarefa").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		data_inicio_da_tarefa = new TextField(model,"data_inicio_da_tarefa");
		data_inicio_da_tarefa.setLabel(gt("Data Início da Tarefa"));
		
		data_inicio_da_tarefa.propertie().add("name","p_data_inicio_da_tarefa").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		data_fim_da_tarefa = new TextField(model,"data_fim_da_tarefa");
		data_fim_da_tarefa.setLabel(gt("Data Fim da Tarefa"));
		
		data_fim_da_tarefa.propertie().add("name","p_data_fim_da_tarefa").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		prioridade_da_tarefa = new TextField(model,"prioridade_da_tarefa");
		prioridade_da_tarefa.setLabel(gt("Prioridade da Tarefa"));
		
		prioridade_da_tarefa.propertie().add("name","p_prioridade_da_tarefa").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		tarefa_atribuida_por = new TextField(model,"tarefa_atribuida_por");
		tarefa_atribuida_por.setLabel(gt("Tarefa Atribuída Por"));
		
		tarefa_atribuida_por.propertie().add("name","p_tarefa_atribuida_por").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		tarefa_atribuida_a = new TextField(model,"tarefa_atribuida_a");
		tarefa_atribuida_a.setLabel(gt("Tarefa Atribuída A"));
		
		tarefa_atribuida_a.propertie().add("name","p_tarefa_atribuida_a").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		tipo_de_processo = new TextField(model,"tipo_de_processo");
		tipo_de_processo.setLabel(gt("Tipo de Processo"));
		
		tipo_de_processo.propertie().add("name","p_tipo_de_processo").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		numero_de_processo = new TextField(model,"numero_de_processo");
		numero_de_processo.setLabel(gt("Número De Processo"));
		
		numero_de_processo.propertie().add("name","p_numero_de_processo").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		data_criacao_de_processo = new TextField(model,"data_criacao_de_processo");
		data_criacao_de_processo.setLabel(gt("Data Criação de Processo"));
		
		data_criacao_de_processo.propertie().add("name","p_data_criacao_de_processo").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		data_inicio_de_processo = new TextField(model,"data_inicio_de_processo");
		data_inicio_de_processo.setLabel(gt("Data Início de Processo"));
		
		data_inicio_de_processo.propertie().add("name","p_data_inicio_de_processo").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		data_fim_processo = new TextField(model,"data_fim_processo");
		data_fim_processo.setLabel(gt("Data Fim Processo"));
		
		data_fim_processo.propertie().add("name","p_data_fim_processo").add("type","text").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");
		descricao_da_tarefa = new TextAreaField(model,"descricao_da_tarefa");
		descricao_da_tarefa.setLabel(gt("Descrição da Tarefa"));
		
		descricao_da_tarefa.propertie().add("name","p_descricao_da_tarefa").add("type","textarea").add("maxlength","30").add("required","false").add("change","false").add("readonly","true").add("disabled","true").add("placeholder","").add("right","false");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		form_1.addField(tipo_da_tarefa);
		form_1.addField(data_criacao_da_tarefa);
		form_1.addField(data_inicio_da_tarefa);
		form_1.addField(data_fim_da_tarefa);
		form_1.addField(prioridade_da_tarefa);
		form_1.addField(tarefa_atribuida_por);
		form_1.addField(tarefa_atribuida_a);
		form_1.addField(tipo_de_processo);
		form_1.addField(numero_de_processo);
		form_1.addField(data_criacao_de_processo);
		form_1.addField(data_inicio_de_processo);
		form_1.addField(data_fim_processo);
		form_1.addField(descricao_da_tarefa);

		this.addToPage(sectionheader_1);
		this.addToPage(form_1);
	}
}
