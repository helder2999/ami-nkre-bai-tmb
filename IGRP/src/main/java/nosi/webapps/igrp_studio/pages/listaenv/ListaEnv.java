package nosi.webapps.igrp_studio.pages.listaenv;

import java.util.ArrayList;
import java.util.List;

import nosi.core.gui.components.IGRPLink;
import nosi.core.gui.components.IGRPTable;
import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;
import nosi.core.webapp.Report;
import nosi.core.webapp.databse.helpers.BaseQueryInterface;

public class ListaEnv extends Model{		

	@RParam(rParamName = "p_sectionheader_1_text")
	private String sectionheader_1_text;

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
		private int status;
		private int status_check;
		private IGRPLink name;
		private String name_desc= "Name";
		private String dad;
		private String id;
		public void setStatus(int status){
			this.status = status;
		}
		public int getStatus(){
			return this.status;
		}
		public void setStatus_check(int status_check){
			this.status_check = status_check;
		}
		public int getStatus_check(){
			return this.status_check;
		}

		public IGRPLink setName(String app,String page,String action){
			this.name = new IGRPLink(app,page,action);
			return this.name;
		}
		public IGRPLink getName(){
			return this.name;
		}
		public void setName_desc(String name_desc){
			this.name_desc = name_desc;
		}
		public String getName_desc(){
			return this.name_desc;
		}
	public IGRPLink setName(String link){
		this.name = new IGRPLink(link);
		return this.name;
	}
	public IGRPLink setName(Report link){
		this.name = new IGRPLink(link);
		return this.name;
	}

		public void setDad(String dad){
			this.dad = dad;
		}
		public String getDad(){
			return this.dad;
		}

		public void setId(String id){
			this.id = id;
		}
		public String getId(){
			return this.id;
		}

	}

	public void loadTable_1(BaseQueryInterface query) {
		this.setTable_1(this.loadTable(query,Table_1.class));
	}

}
