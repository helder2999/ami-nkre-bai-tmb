package nosi.webapps.igrp.pages.novomenu;

import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;

public class NovoMenu extends Model{		

	@RParam(rParamName = "p_sectionheader_1_text")
	private String sectionheader_1_text;

	@RParam(rParamName = "p_env_fk")
	private int env_fk;

	@RParam(rParamName = "p_action_fk")
	private int action_fk;

	@RParam(rParamName = "p_detalhes")
	private String detalhes;

	@RParam(rParamName = "p_titulo")
	private String titulo;

	@RParam(rParamName = "p_status")
	private int status;
	@RParam(rParamName = "p_status_check")
	private int status_check;

	@RParam(rParamName = "p_extra")
	private String extra;

	@RParam(rParamName = "p_self_id")
	private int self_id;

	@RParam(rParamName = "p_orderby")
	private int orderby;

	@RParam(rParamName = "p_target")
	private String target;

	@RParam(rParamName = "p_link")
	private String link;

	@RParam(rParamName = "p_icone")
	private String icone;

	@RParam(rParamName = "p_flg_base")
	private int flg_base;
	@RParam(rParamName = "p_flg_base_check")
	private int flg_base_check;

	@RParam(rParamName = "p_global_acl")
	private int global_acl;
	@RParam(rParamName = "p_global_acl_check")
	private int global_acl_check;

	@RParam(rParamName = "p_area")
	private String area;

	@RParam(rParamName = "p_img_src")
	private String img_src;

	@RParam(rParamName = "p_id")
	private int id;

	@RParam(rParamName = "p_app")
	private int app;
	
	public void setSectionheader_1_text(String sectionheader_1_text){
		this.sectionheader_1_text = sectionheader_1_text;
	}
	public String getSectionheader_1_text(){
		return this.sectionheader_1_text;
	}
	
	public void setEnv_fk(int env_fk){
		this.env_fk = env_fk;
	}
	public int getEnv_fk(){
		return this.env_fk;
	}
	
	public void setAction_fk(int action_fk){
		this.action_fk = action_fk;
	}
	public int getAction_fk(){
		return this.action_fk;
	}
	
	public void setDetalhes(String detalhes){
		this.detalhes = detalhes;
	}
	public String getDetalhes(){
		return this.detalhes;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	public String getTitulo(){
		return this.titulo;
	}
	
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
	
	public void setExtra(String extra){
		this.extra = extra;
	}
	public String getExtra(){
		return this.extra;
	}
	
	public void setSelf_id(int self_id){
		this.self_id = self_id;
	}
	public int getSelf_id(){
		return this.self_id;
	}
	
	public void setOrderby(int orderby){
		this.orderby = orderby;
	}
	public int getOrderby(){
		return this.orderby;
	}
	
	public void setTarget(String target){
		this.target = target;
	}
	public String getTarget(){
		return this.target;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return this.link;
	}
	
	public void setIcone(String icone){
		this.icone = icone;
	}
	public String getIcone(){
		return this.icone;
	}
	
	public void setFlg_base(int flg_base){
		this.flg_base = flg_base;
	}
	public int getFlg_base(){
		return this.flg_base;
	}
	public void setFlg_base_check(int flg_base_check){
		this.flg_base_check = flg_base_check;
	}
	public int getFlg_base_check(){
		return this.flg_base_check;
	}
	
	public void setGlobal_acl(int global_acl){
		this.global_acl = global_acl;
	}
	public int getGlobal_acl(){
		return this.global_acl;
	}
	public void setGlobal_acl_check(int global_acl_check){
		this.global_acl_check = global_acl_check;
	}
	public int getGlobal_acl_check(){
		return this.global_acl_check;
	}
	
	public void setArea(String area){
		this.area = area;
	}
	public String getArea(){
		return this.area;
	}
	
	public void setImg_src(String img_src){
		this.img_src = img_src;
	}
	public String getImg_src(){
		return this.img_src;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	
	public void setApp(int app){
		this.app = app;
	}
	public int getApp(){
		return this.app;
	}



}
