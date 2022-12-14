package nosi.webapps.igrp_studio.pages.importarquivo;

import nosi.core.gui.components.IGRPLink;
import nosi.core.webapp.Report;
import nosi.core.gui.components.IGRPTable;
import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;
import nosi.core.webapp.databse.helpers.BaseQueryInterface;
import nosi.core.gui.components.IGRPSeparatorList.Pair;
import nosi.core.webapp.SeparatorList;
import java.util.ArrayList;
import java.util.List;
import nosi.core.webapp.uploadfile.UploadFile;
import javax.validation.Valid;
public class ImportArquivo extends Model{		

	@RParam(rParamName = "p_sectionheader_1_text")
	private String sectionheader_1_text;

	@RParam(rParamName = "p_help")
	private IGRPLink help;
	@RParam(rParamName = "p_help_desc")
	private String help_desc;

	@RParam(rParamName = "p_importar_aplicacao")
	private String importar_aplicacao;

	@RParam(rParamName = "p_importar_jar_file")
	private String importar_jar_file;

	@RParam(rParamName = "p_importar_sql_script")
	private String importar_sql_script;

	@RParam(rParamName = "p_importar_imagem")
	private String importar_imagem;

	@RParam(rParamName = "p_personalizar_login")
	private String personalizar_login;

	@RParam(rParamName = "p_sectionheader_2_text")
	private String sectionheader_2_text;

	@RParam(rParamName = "p_sectionheader_4_text")
	private String sectionheader_4_text;

	@RParam(rParamName = "p_sectionheader_5_text")
	private String sectionheader_5_text;

	@RParam(rParamName = "p_sectionheader_6_text")
	private String sectionheader_6_text;

	@RParam(rParamName = "p_arquivo_aplicacao")
	private UploadFile arquivo_aplicacao;

	@RParam(rParamName = "p_jar_file")
	private UploadFile jar_file;

	@RParam(rParamName = "p_aplicacao_script")
	private String aplicacao_script;

	@RParam(rParamName = "p_data_source")
	private String data_source;

	@RParam(rParamName = "p_sql_script")
	private UploadFile sql_script;

	@RParam(rParamName = "p_aplicacao_combo_img")
	private String aplicacao_combo_img;

	@RParam(rParamName = "p_tipo")
	private int tipo;

	@RParam(rParamName = "p_imagens")
	private UploadFile[] imagens;

	@RParam(rParamName = "p_form_5_link_1")
	private String form_5_link_1;

	@RParam(rParamName = "p_carousel_1_label")
	private String carousel_1_label;

	@RParam(rParamName = "p_carousel_1_img")
	private String carousel_1_img;

	@RParam(rParamName = "p_sectionheader_3_text")
	private String sectionheader_3_text;

	@RParam(rParamName = "p_imagem")
	private UploadFile imagem;

	@RParam(rParamName = "p_list_aplicacao")
	private String list_aplicacao;

	@RParam(rParamName = "p_arquivo_pagina")
	private UploadFile arquivo_pagina;
	
	private List<Carousel_1> carousel_1 = new ArrayList<>();	
	public void setCarousel_1(List<Carousel_1> carousel_1){
		this.carousel_1 = carousel_1;
	}
	public List<Carousel_1> getCarousel_1(){
		return this.carousel_1;
	}

	
	@SeparatorList(name = Formlist_1.class)
	@Valid
	private List<Formlist_1> formlist_1 = new ArrayList<>();	
	public void setFormlist_1(List<Formlist_1> formlist_1){
		this.formlist_1 = formlist_1;
	}
	public List<Formlist_1> getFormlist_1(){
		return this.formlist_1;
	}
	@RParam(rParamName = "p_formlist_1_id")
	private String[] p_formlist_1_id;
	@RParam(rParamName = "p_formlist_1_del")
	private String[] p_formlist_1_del;
	@RParam(rParamName = "p_formlist_1_edit")
	private String[] p_formlist_1_edit;
	
	public void setP_formlist_1_id(String[] p_formlist_1_id){
		this.p_formlist_1_id = p_formlist_1_id;
	}
	public String[] getP_formlist_1_id(){
		return this.p_formlist_1_id;
	}
	
	public void setP_formlist_1_del(String[] p_formlist_1_del){
		this.p_formlist_1_del = p_formlist_1_del;
	}
	public String[] getP_formlist_1_del(){
		return this.p_formlist_1_del;
	}
	
	public void setP_formlist_1_edit(String[] p_formlist_1_edit){
		this.p_formlist_1_edit = p_formlist_1_edit;
	}
	public String[] getP_formlist_1_edit(){
		return this.p_formlist_1_edit;
	}
	
	public void setSectionheader_1_text(String sectionheader_1_text){
		this.sectionheader_1_text = sectionheader_1_text;
	}
	public String getSectionheader_1_text(){
		return this.sectionheader_1_text;
	}
	
	public IGRPLink setHelp(String app,String page,String action){
		this.help = new IGRPLink(app,page,action);
		return this.help;
	}
	public IGRPLink getHelp(){
		return this.help;
	}
	public void setHelp_desc(String help_desc){
		this.help_desc = help_desc;
	}
	public String getHelp_desc(){
		return this.help_desc;
	}
	public IGRPLink setHelp(String link){
		this.help = new IGRPLink(link);
		return this.help;
	}
	public IGRPLink setHelp(Report link){
		this.help = new IGRPLink(link);
		return this.help;
	}
	
	public void setImportar_aplicacao(String importar_aplicacao){
		this.importar_aplicacao = importar_aplicacao;
	}
	public String getImportar_aplicacao(){
		return this.importar_aplicacao;
	}
	
	public void setImportar_jar_file(String importar_jar_file){
		this.importar_jar_file = importar_jar_file;
	}
	public String getImportar_jar_file(){
		return this.importar_jar_file;
	}
	
	public void setImportar_sql_script(String importar_sql_script){
		this.importar_sql_script = importar_sql_script;
	}
	public String getImportar_sql_script(){
		return this.importar_sql_script;
	}
	
	public void setImportar_imagem(String importar_imagem){
		this.importar_imagem = importar_imagem;
	}
	public String getImportar_imagem(){
		return this.importar_imagem;
	}
	
	public void setPersonalizar_login(String personalizar_login){
		this.personalizar_login = personalizar_login;
	}
	public String getPersonalizar_login(){
		return this.personalizar_login;
	}
	
	public void setSectionheader_2_text(String sectionheader_2_text){
		this.sectionheader_2_text = sectionheader_2_text;
	}
	public String getSectionheader_2_text(){
		return this.sectionheader_2_text;
	}
	
	public void setSectionheader_4_text(String sectionheader_4_text){
		this.sectionheader_4_text = sectionheader_4_text;
	}
	public String getSectionheader_4_text(){
		return this.sectionheader_4_text;
	}
	
	public void setSectionheader_5_text(String sectionheader_5_text){
		this.sectionheader_5_text = sectionheader_5_text;
	}
	public String getSectionheader_5_text(){
		return this.sectionheader_5_text;
	}
	
	public void setSectionheader_6_text(String sectionheader_6_text){
		this.sectionheader_6_text = sectionheader_6_text;
	}
	public String getSectionheader_6_text(){
		return this.sectionheader_6_text;
	}
	
	public void setArquivo_aplicacao(UploadFile arquivo_aplicacao){
		this.arquivo_aplicacao = arquivo_aplicacao;
	}
	public UploadFile getArquivo_aplicacao(){
		return this.arquivo_aplicacao;
	}
	
	public void setJar_file(UploadFile jar_file){
		this.jar_file = jar_file;
	}
	public UploadFile getJar_file(){
		return this.jar_file;
	}
	
	public void setAplicacao_script(String aplicacao_script){
		this.aplicacao_script = aplicacao_script;
	}
	public String getAplicacao_script(){
		return this.aplicacao_script;
	}
	
	public void setData_source(String data_source){
		this.data_source = data_source;
	}
	public String getData_source(){
		return this.data_source;
	}
	
	public void setSql_script(UploadFile sql_script){
		this.sql_script = sql_script;
	}
	public UploadFile getSql_script(){
		return this.sql_script;
	}
	
	public void setAplicacao_combo_img(String aplicacao_combo_img){
		this.aplicacao_combo_img = aplicacao_combo_img;
	}
	public String getAplicacao_combo_img(){
		return this.aplicacao_combo_img;
	}
	
	public void setTipo(int tipo){
		this.tipo = tipo;
	}
	public int getTipo(){
		return this.tipo;
	}
	
	public void setImagens(UploadFile[] imagens){
		this.imagens = imagens;
	}
	public UploadFile[] getImagens(){
		return this.imagens;
	}
	
	public void setForm_5_link_1(String form_5_link_1){
		this.form_5_link_1 = form_5_link_1;
	}
	public String getForm_5_link_1(){
		return this.form_5_link_1;
	}
	
	public void setCarousel_1_label(String carousel_1_label){
		this.carousel_1_label = carousel_1_label;
	}
	public String getCarousel_1_label(){
		return this.carousel_1_label;
	}
	
	public void setCarousel_1_img(String carousel_1_img){
		this.carousel_1_img = carousel_1_img;
	}
	public String getCarousel_1_img(){
		return this.carousel_1_img;
	}
	
	public void setSectionheader_3_text(String sectionheader_3_text){
		this.sectionheader_3_text = sectionheader_3_text;
	}
	public String getSectionheader_3_text(){
		return this.sectionheader_3_text;
	}
	
	public void setImagem(UploadFile imagem){
		this.imagem = imagem;
	}
	public UploadFile getImagem(){
		return this.imagem;
	}
	
	public void setList_aplicacao(String list_aplicacao){
		this.list_aplicacao = list_aplicacao;
	}
	public String getList_aplicacao(){
		return this.list_aplicacao;
	}
	
	public void setArquivo_pagina(UploadFile arquivo_pagina){
		this.arquivo_pagina = arquivo_pagina;
	}
	public UploadFile getArquivo_pagina(){
		return this.arquivo_pagina;
	}


	public static class Carousel_1 extends IGRPTable.Table{
		private String carousel_1_label;
		private String carousel_1_img;
		public void setCarousel_1_label(String carousel_1_label){
			this.carousel_1_label = carousel_1_label;
		}
		public String getCarousel_1_label(){
			return this.carousel_1_label;
		}

		public void setCarousel_1_img(String carousel_1_img){
			this.carousel_1_img = carousel_1_img;
		}
		public String getCarousel_1_img(){
			return this.carousel_1_img;
		}

	}
	public static class Formlist_1{
		private Pair formlist_1_id;
private Pair imagem_tbl;
private Pair id_img;
		public void setFormlist_1_id(Pair formlist_1_id){
			this.formlist_1_id = formlist_1_id;
		}
		public Pair getFormlist_1_id(){
			return this.formlist_1_id;
		}
		public void setImagem_tbl(Pair imagem_tbl){
			this.imagem_tbl = imagem_tbl;
		}
		public Pair getImagem_tbl(){
			return this.imagem_tbl;
		}

		public void setId_img(Pair id_img){
			this.id_img = id_img;
		}
		public Pair getId_img(){
			return this.id_img;
		}

	}

	public void loadCarousel_1(BaseQueryInterface query) {
		this.setCarousel_1(this.loadTable(query,Carousel_1.class));
	}

	public void loadFormlist_1(BaseQueryInterface query) {
		this.setFormlist_1(this.loadFormList(query,Formlist_1.class));
	}

}
