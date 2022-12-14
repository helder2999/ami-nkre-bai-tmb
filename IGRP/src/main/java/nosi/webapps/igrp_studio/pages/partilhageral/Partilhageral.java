package nosi.webapps.igrp_studio.pages.partilhageral;

import java.util.ArrayList;
import java.util.List;

import nosi.core.gui.components.IGRPLink;
import nosi.core.gui.components.IGRPTable;
import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;
import nosi.core.webapp.Report;
import nosi.core.webapp.databse.helpers.BaseQueryInterface;

public class Partilhageral extends Model{		

	@RParam(rParamName = "p_sectionheader_1_text")
	private String sectionheader_1_text;

	@RParam(rParamName = "p_aplicacao_origem")
	private String aplicacao_origem;

	@RParam(rParamName = "p_elemento")
	private String elemento;

	@RParam(rParamName = "p_app_or")
	private String app_or;

	@RParam(rParamName = "p_aplicacao_destino")
	private String aplicacao_destino;

	@RParam(rParamName = "p_documento")
	private IGRPLink documento;
	@RParam(rParamName = "p_documento_desc")
	private String documento_desc;
	
	private List<Table_1> table_1 = new ArrayList<>();	
	public void setTable_1(List<Table_1> table_1){
		this.table_1 = table_1;
	}
	public List<Table_1> getTable_1(){
		return this.table_1;
	}

	
	public void setSectionheader_1_text(String sectionheader_1_text){
		this.sectionheader_1_text = sectionheader_1_text;
	}
	public String getSectionheader_1_text(){
		return this.sectionheader_1_text;
	}
	
	public void setAplicacao_origem(String aplicacao_origem){
		this.aplicacao_origem = aplicacao_origem;
	}
	public String getAplicacao_origem(){
		return this.aplicacao_origem;
	}
	
	public void setElemento(String elemento){
		this.elemento = elemento;
	}
	public String getElemento(){
		return this.elemento;
	}
	
	public void setApp_or(String app_or){
		this.app_or = app_or;
	}
	public String getApp_or(){
		return this.app_or;
	}
	
	public void setAplicacao_destino(String aplicacao_destino){
		this.aplicacao_destino = aplicacao_destino;
	}
	public String getAplicacao_destino(){
		return this.aplicacao_destino;
	}
	
	public IGRPLink setDocumento(String app,String page,String action){
		this.documento = new IGRPLink(app,page,action);
		return this.documento;
	}
	public IGRPLink getDocumento(){
		return this.documento;
	}
	public void setDocumento_desc(String documento_desc){
		this.documento_desc = documento_desc;
	}
	public String getDocumento_desc(){
		return this.documento_desc;
	}
	public IGRPLink setDocumento(String link){
		this.documento = new IGRPLink(link);
		return this.documento;
	}
	public IGRPLink setDocumento(Report link){
		this.documento = new IGRPLink(link);
		return this.documento;
	}


	public static class Table_1 extends IGRPTable.Table{
		private int estado;
		private int estado_check;
		private String nome;
		public void setEstado(int estado){
			this.estado = estado;
		}
		public int getEstado(){
			return this.estado;
		}
		public void setEstado_check(int estado_check){
			this.estado_check = estado_check;
		}
		public int getEstado_check(){
			return this.estado_check;
		}

		public void setNome(String nome){
			this.nome = nome;
		}
		public String getNome(){
			return this.nome;
		}

	}

	public void loadTable_1(BaseQueryInterface query) {
		this.setTable_1(this.loadTable(query,Table_1.class));
	}

}
