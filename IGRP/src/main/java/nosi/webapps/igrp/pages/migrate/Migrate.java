package nosi.webapps.igrp.pages.migrate;


import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;

public class Migrate extends Model{		
	@RParam(rParamName = "p_sectionheader_1_text")
	private String sectionheader_1_text;
	@RParam(rParamName = "p_aplicacao")
	private String aplicacao;
	@RParam(rParamName = "p_tipo_base_dados")
	private String tipo_base_dados;
	@RParam(rParamName = "p_nome_de_conexao")
	private String nome_de_conexao;
	@RParam(rParamName = "p_config")
	private String config;
	@RParam(rParamName = "p_url_connection")
	private String url_connection;
	@RParam(rParamName = "p_driver_connection")
	private String driver_connection;
	@RParam(rParamName = "p_paragraph_1")
	private String paragraph_1;
	@RParam(rParamName = "p_credenciais")
	private String credenciais;
	@RParam(rParamName = "p_username")
	private String username;
	@RParam(rParamName = "p_password")
	private String password;
	
	public void setSectionheader_1_text(String sectionheader_1_text){
		this.sectionheader_1_text = sectionheader_1_text;
	}
	public String getSectionheader_1_text(){
		return this.sectionheader_1_text;
	}
	
	public void setAplicacao(String aplicacao){
		this.aplicacao = aplicacao;
	}
	public String getAplicacao(){
		return this.aplicacao;
	}
	
	public void setTipo_base_dados(String tipo_base_dados){
		this.tipo_base_dados = tipo_base_dados;
	}
	public String getTipo_base_dados(){
		return this.tipo_base_dados;
	}
	
	public void setNome_de_conexao(String nome_de_conexao){
		this.nome_de_conexao = nome_de_conexao;
	}
	public String getNome_de_conexao(){
		return this.nome_de_conexao;
	}
	
	public void setConfig(String config){
		this.config = config;
	}
	public String getConfig(){
		return this.config;
	}
	
	public void setUrl_connection(String url_connection){
		this.url_connection = url_connection;
	}
	public String getUrl_connection(){
		return this.url_connection;
	}
	
	public void setDriver_connection(String driver_connection){
		this.driver_connection = driver_connection;
	}
	public String getDriver_connection(){
		return this.driver_connection;
	}
	
	public void setParagraph_1(String paragraph_1){
		this.paragraph_1 = paragraph_1;
	}
	public String getParagraph_1(){
		return this.paragraph_1;
	}
	
	public void setCredenciais(String credenciais){
		this.credenciais = credenciais;
	}
	public String getCredenciais(){
		return this.credenciais;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	@Override
	public String toString() {
		return "Migrate [aplicacao=" + aplicacao + ", tipo_base_dados=" + tipo_base_dados + ", nome_de_conexao="
				+ nome_de_conexao + ", url_connection=" + url_connection + ", driver_connection=" + driver_connection
				+ ", username=" + username + ", password=" + password + "]";
	}

}
