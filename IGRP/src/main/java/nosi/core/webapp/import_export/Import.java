package nosi.core.webapp.import_export;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXB;

import nosi.core.config.Config;
import nosi.core.gui.page.Page;
import nosi.core.webapp.Core;
import nosi.core.webapp.compiler.helpers.Compiler;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.StringHelper;
import nosi.core.xml.XMLApplicationReader;
import nosi.core.xml.XMLPageReader;
import nosi.core.xml.XMLWritter;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
/**
 * @author: Emanuel Pereira
 * 5 Nov 2017
 */
public class Import {

	private Config config = new Config();
	protected String encode = FileHelper.ENCODE_UTF8;
	protected Application app;
	protected Compiler compiler = new Compiler();
	
	public boolean importApp(IFImportExport ie) {
		return ie.importApp();
	}

	public boolean importPage(IFImportExport ie,Application app) {
		return ie.importPage(app);
	}	

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	protected boolean compileFiles(List<FileImportAppOrPage> filesToCompile, Application app) {
		for(FileImportAppOrPage file:filesToCompile){
			if(file.getNome()!=null){
				String[] partPage = file.getNome().split("/");
				if(partPage.length > 2 && partPage[2].equalsIgnoreCase("DefaultPage")){
					this.compiler.addFileName(this.addFileDesfaultPage(file,app));				
				}
				if(file.getNome().startsWith("dao")){
					this.compiler.addFileName(this.addFileDao(file,app));	
				}
				if(!file.getNome().startsWith("dao") && !partPage[2].equalsIgnoreCase("DefaultPage")){
					this.compiler.addFileName(this.addFileMVC(file,app));	
				}
			}
		}
		compiler.compile();
		return compiler.hasError();
	}


	private String addFileMVC(FileImportAppOrPage file,Application app) {
		String[] partPage = file.getNome().split("/");
		Action page = new Action().find()
				  .andWhere("application.dad", "=", app.getDad())
				  .andWhere("page", "=", Page.resolvePageName(partPage[2]))
				  .one();
		String path_class = this.getConfig().
				getBasePathClass() + 
				page.getPackage_name()
				.replace(".",File.separator);
		String content = file.getConteudo();
		if(file.getNome().endsWith(".java")){
			
			content = content.trim();
			
			if(content != null && !content.isEmpty()) { // Sometimes the content of file is empty ... and throw StringIndexOutOfBoundsException 
			content = content.substring(0, 
					content.indexOf("package") + 
					"package".length()) +
					" " +page.getPackage_name() 
					+ content.substring(
							content.indexOf(";", 
									content.indexOf("package")
									));
			}
		}
		try {
			FileHelper.save(path_class,partPage[3],content);
			if(Core.isNotNull(this.getConfig().getWorkspace()) && FileHelper.fileExists(this.getConfig().getWorkspace())){
				String path_class_work_space = this.getConfig().getBasePahtClassWorkspace(page.getApplication().getDad())+File.separator+"pages"+File.separator+page.getPage().toLowerCase();
				FileHelper.save(path_class_work_space,partPage[3],content);
			}
			return path_class+"/"+partPage[3];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private String addFileDao(FileImportAppOrPage file,Application app) {
		String[] partPage = file.getNome().split("/");
		String dir = this.getConfig().getBasePathClass()+"nosi"+"/"+"webapps"+"/"+app.getDad().toLowerCase()+"/dao";
		if(!FileHelper.fileExists(dir))
			FileHelper.createDiretory(dir);
		try {
			FileHelper.save(dir, partPage[1],file.getConteudo());
			if(Core.isNotNull(this.getConfig().getWorkspace()) && FileHelper.fileExists(this.getConfig().getWorkspace()) && FileHelper.createDiretory(this.getConfig().getRawBasePathClassWorkspace()+"/nosi/webapps/"+app.getDad().toLowerCase()+"/dao")){
				FileHelper.save(this.getConfig().getRawBasePathClassWorkspace()+"/nosi/webapps"+"/"+app.getDad().toLowerCase()+"/"+"dao",partPage[1], file.getConteudo());
			}	
			return dir+"/"+ partPage[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private String addFileDesfaultPage(FileImportAppOrPage file,Application app) {
		String dir = this.getConfig().getBasePathClass()+"nosi"+"/"+"webapps"+"/"+app.getDad().toLowerCase()+"/"+"pages"+"/"+"defaultpage";
		if(!FileHelper.fileExists(dir))
			FileHelper.createDiretory(dir);
		
		try {
			FileHelper.save(dir, "DefaultPageController.java",this.getConfig().getDefaultPageController(app.getDad().toLowerCase(), app.getName()));
			if(Core.isNotNull(this.getConfig().getWorkspace()) && FileHelper.fileExists(this.getConfig().getWorkspace()) && FileHelper.createDiretory(this.getConfig().getRawBasePathClassWorkspace()+"/nosi/webapps/"+app.getDad().toLowerCase()+"/pages/defaultpage")){
				FileHelper.save(this.getConfig().getRawBasePathClassWorkspace()+"/nosi/webapps"+"/"+app.getDad().toLowerCase()+"/"+"pages/defaultpage", "DefaultPageController.java",this.getConfig().getDefaultPageController(app.getDad().toLowerCase(), app.getName()));
			}	
			return dir+"/"+ "DefaultPageController.java";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	protected boolean saveFiles(FileImportAppOrPage file, Application app){
		String[] partPage = file.getNome().split("/");
		Action page = new Action().find()
								  .andWhere("application.dad", "=", app.getDad())
								  .andWhere("page", "=", Page.resolvePageName(partPage[2]))
								  .one();
		String path = this.getConfig().getCurrentBaseServerPahtXsl(page);
		FileHelper.createDiretory(path);
		String content = file.getConteudo();
		
		//Substitui nome de pacotes tanto no arquivo xml como tambem no json
		if(file.getNome().endsWith(".xml")){
			content = content.substring(0, content.indexOf("<package_db>")+"<package_db>".length())+page.getPackage_name() +content.substring(content.indexOf("</package_db>"));
		}else if(file.getNome().endsWith(".json") && content.indexOf("\"package\":") >=0 ){		
			content = this.addClassAndPackage(content,page,"json");
		}
		try {
			boolean result = false;
			//Guarda ficheiros no workspace caso existe
			if(Core.isNotNull(this.getConfig().getWorkspace()) && FileHelper.fileExists(this.getConfig().getWorkspace())){
				String path_xsl_work_space = this.getConfig().getBasePahtXslWorkspace(page);		
				result = FileHelper.save(path_xsl_work_space,partPage[4], content);
			}
			result = FileHelper.save(path, partPage[4], content);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	protected Application saveApp(FileImportAppOrPage file){
		return saveApp(file,false);		
	}
	protected Application saveApp(FileImportAppOrPage file,Boolean isPlsql){
		InputStream is;
		try {
			if(isPlsql)
				encode = FileHelper.ENCODE_ISO;
			is = new ByteArrayInputStream(file.getConteudo().getBytes(this.encode));
			XMLApplicationReader listApp = JAXB.unmarshal(is, XMLApplicationReader.class);
			for(Application app:listApp.getRow()){
				if(new Application().find().andWhere("dad", "=", app.getDad()).one()==null){
					app.setAction(null);
					app.setId(null);
					app = app.insert();
					return app;
				}else{
					return new Application().find().andWhere("dad", "=", app.getDad()).one();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	protected List<Action> savePageJava(FileImportAppOrPage file,Application app){
		return this.savePage(file, app, "java");
	}
	
	protected List<Action> savePagePlsql(FileImportAppOrPage file,Application app){
		return this.savePage(file, app, "plsql");
	}
	
	private List<Action> savePage(FileImportAppOrPage file,Application app,String type){
		
		ImportAppJar aux = (ImportAppJar) this;
		
		InputStream is;
		try {
			if(type.equals("plsql"))
				encode = FileHelper.ENCODE_ISO;			
			is = new ByteArrayInputStream(file.getConteudo().getBytes(this.encode));		
			XMLPageReader listPage = JAXB.unmarshal(is, XMLPageReader.class);
		
			List<Action> pages = new ArrayList<>();
			
			for(Action page : listPage.getRow()){
				
				//Depois validar nome de classe
				if(type.equals("plsql")){ //Se for de psql, assume Page como Action 
					
					boolean nextIteration = true;
					
					for(FileImportAppOrPage f : aux.un_jar_files) { 
						//System.out.println("XSL: " + page.getXsl_src());
						//System.out.println("Nome: " + f.getNome());
						if(f.getNome() != null && page.getXsl_src() != null && f.getOrder()==2) { // Marcos: tirei porque nao fazia sentido e nao funcionava. Coloquei order=2 f.getNome().contains(page.getXsl_src())
							nextIteration = false;
							break;
						}
					}
					
					if(nextIteration) continue; 
					
					page.setPage(Page.resolvePageName(this.resolveClassName(page.getPage())+"_"+page.getAction()));
					page.setPage_descr(page.getAction_descr());
				}
				Action action = new Action();
				action.setId(null);
				action.setAction("index");
				action.setPage(page.getPage());
				action.setVersion(page.getVersion());
				action.setAction_descr(page.getAction_descr());
				action.setPage_descr(page.getPage_descr());
				action.setStatus(page.getStatus());
				action.setXsl_src(page.getXsl_src());
				action.setApplication(app);		
				
				Action pageCheck = new Action().find().andWhere("page", "=", page.getPage()).andWhere("application.dad", "=", app.getDad()).one();
	
				if(type.equals("java")){
					if(nosi.core.gui.page.Page.validatePage(page.getPage()) && pageCheck==null){
						action.setPackage_name("nosi.webapps."+app.getDad().toLowerCase()+".pages."+page.getPage().toLowerCase());
						action = action.insert();
						pages.add(action);
					}else if(pageCheck!=null){
						pages.add(pageCheck);
					}
				}else if(type.equals("plsql")){// Caso for de psql, valida nome de classe e versao da pagina 
					if(Core.isNotNull(page.getPage())&& pageCheck ==null && nosi.core.gui.page.Page.validatePage(page.getPage())&& page.getImg_src()==null){
							
						action.setPackage_name("nosi.webapps."+
						app.getDad()
						.toLowerCase()+
						".pages."+page.getPage()
						.toLowerCase()
						);
							
						action.setXsl_src(app.getDad().toLowerCase()+"/"+page.getPage().toLowerCase()+"/"+page.getPage()+".xsl");
							action.setVersion("2.3");	
							action = action.insert();
							action.setSrc_xsl_plsql(page.getXsl_src());
							action.setId_plsql(page.getId());
							action.setVersion_src(page.getVersion_src());
							pages.add(action);
					}else if(pageCheck!=null){
						pageCheck.setId_plsql(page.getId());
						pageCheck.setSrc_xsl_plsql(page.getXsl_src());
						pageCheck.setVersion_src(page.getVersion_src());
						pages.add(pageCheck);
					}
				}	
			}
			return pages;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	


	private String resolveClassName(String page) {
		if(Core.isNotNull(page)) {
			if(!StringHelper.validateClassName(page)) {
				return this.resolveClassName(page.substring(page.length()-(page.length()-1)));
			}
			return page;
		}
		return "";
	}

	//Adiciona Class e Pacote no XML
	protected String addClassAndPackage(String content,Action page,String type) {
		if(type.equalsIgnoreCase("xml")) {
			int index =  content.indexOf("<package_html>");
			if(index >= 0){
				content = content.substring(0, content.indexOf("<package_html>")+"<package_html>".length())+ page.getPage()+content.substring(content.indexOf("</package_html>"));
				String package_name = this.getConfig().getBasePackage(app.getDad()).contains(".pages")?this.getConfig().getBasePackage(app.getDad()):this.getConfig().getBasePackage(app.getDad())+".pages";
				content = content.substring(0, content.indexOf("<package_db>")+"<package_db>".length())+package_name +content.substring(content.indexOf("</package_db>"));
			}else{
				content = this.configurePackageAndClass(content,page);
			}
		}else if(type.equalsIgnoreCase("json")) {
			int index =  content.indexOf("\"html\":");
			if(index >= 0){
				String package_name = page.getPackage_name();
				package_name = package_name.substring(0, package_name.indexOf(".",package_name.indexOf("pages")));
				content = content.substring(0, content.indexOf("\"html\":")+"\"html\":\"".length())+page.getPage()+"\""+content.substring(content.indexOf(",\"replace\""));
				content = content.substring(0, content.indexOf("\"package\":")+"\"package\":\"".length())+package_name+"\"" +content.substring(content.indexOf(",\"html\""));
			}
		}
		return content;
	}
	


	private String configurePackageAndClass(String content,Action page) {
		String aux = content.substring(0,content.indexOf("</site>")+"</site>".length());
		XMLWritter xml = new XMLWritter();
		xml.startElement("plsql");
			xml.setElement("action", page.getPage());
			xml.setElement("package_db", page.getPackage_name());//PackageName
			xml.setElement("package_html", page.getPage());//ClassName
		xml.endElement();
		aux += xml.toString() + content.substring(content.indexOf("</site>")+"</site>".length());
		return aux;
	}
}
