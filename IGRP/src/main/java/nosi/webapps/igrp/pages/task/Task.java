package nosi.webapps.igrp.pages.task;
import java.util.ArrayList;
import java.util.List;

import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;

public class Task extends Model{		
	@RParam(rParamName = "p_aplicacao")
	private String aplicacao;
	@RParam(rParamName = "p_processo")
	private String processo;
	
	private List<Table_1> table_1 = new ArrayList<>();	
	public void setTable_1(List<Table_1> table_1){
		this.table_1 = table_1;
	}
	public List<Table_1> getTable_1(){
		return this.table_1;
	}
	
	public void setAplicacao(String aplicacao){
		this.aplicacao = aplicacao;
	}
	public String getAplicacao(){
		return this.aplicacao;
	}
	
	public void setProcesso(String processo){
		this.processo = processo;
	}
	public String getProcesso(){
		return this.processo;
	}


	public static class Table_1{
		private String selecionar;
		private String selecionar_check;
		private String descricao;
		public void setSelecionar(String selecionar){
			this.selecionar = selecionar;
		}
		public String getSelecionar(){
			return this.selecionar;
		}
		public void setSelecionar_check(String selecionar_check){
			this.selecionar_check = selecionar_check;
		}
		public String getSelecionar_check(){
			return this.selecionar_check;
		}

		public void setDescricao(String descricao){
			this.descricao = descricao;
		}
		public String getDescricao(){
			return this.descricao;
		}

	}
}
