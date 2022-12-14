package nosi.webapps.igrp.pages.configdatabase;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static nosi.core.i18n.Translator.gt;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import nosi.core.config.Config;
import nosi.core.config.ConfigCommonMainConstants;
import nosi.core.config.ConfigDBIGRP;
import nosi.core.igrp.mingrations.MigrationIGRP;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.core.webapp.databse.helpers.DatabaseConfigHelper;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.dao_helper.SaveMapeamentoDAO;
import nosi.core.webapp.security.EncrypDecrypt;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Config_env;
import nosi.webapps.igrp.pages.migrate.Migrate;
		
public class ConfigDatabaseController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		ConfigDatabase model = new ConfigDatabase();
		model.load();
		ConfigDatabaseView view = new ConfigDatabaseView();
		view.nome_de_conexao_tabela.setParam(true);
		view.id.setParam(true);
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		model.loadTable_1(Core.query(null,"SELECT '1' as default_,'Perspiciatis officia labore om' as nome_de_conexao_tabela,'Rem aliqua amet ut labore' as user_name_tabela,'Amet consectetur iste sed ipsu' as tipo_de_base_de_dados_tabela,'Magna stract accusantium persp' as t_url_connection,'Sit doloremque consectetur ani' as t_driver_connection,'hidden-523d_2ca7' as id "));
		view.aplicacao.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		view.tipo_base_dados.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		  ----#gen-example */
		/*----#start-code(index)----*/
		//model.setLink_doc(this.getConfig().getResolveUrl("tutorial","Listar_documentos","index&p_type=base_dados"));
		String id_app = model.getAplicacao();
		java.util.List<Config_env> list_app = new Config_env().find().andWhere("application", "=",Core.toInt(id_app)).all();
		ArrayList<ConfigDatabase.Table_1> lista_tabela = new ArrayList<>();
		list_app.sort(Comparator.comparing(Config_env::getName));
		for(Config_env lista : list_app) {
			ConfigDatabase.Table_1 tabela = new ConfigDatabase.Table_1();
          	tabela.setId(""+lista.getId());
          	tabela.setTipo_de_base_de_dados_tabela(Core.decrypt(lista.getType_db(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			tabela.setNome_de_conexao_tabela(lista.getName());
			tabela.setT_url_connection(
				Core.isNotNull(lista.getUrl_connection())
				?
					Core.decrypt(lista.getUrl_connection(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)
				:
					DatabaseConfigHelper.getUrl(
						tabela.getTipo_de_base_de_dados_tabela(), 
						Core.decrypt(lista.getHost(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB), 
						Core.decrypt(lista.getPort(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB), 
						Core.decrypt(lista.getName_db(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)
					)
			);
			tabela.setT_driver_connection(Core.isNotNull(lista.getDriver_connection())?Core.decrypt(lista.getDriver_connection(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB):DatabaseConfigHelper.getDatabaseDriversExamples(tabela.getTipo_de_base_de_dados_tabela()));
			tabela.setUser_name_tabela(Core.decrypt(lista.getUsername(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			
			if(lista.getIsDefault() == 1) {
				tabela.setDefault_(1);
				tabela.setDefault__check(1);
			}else {
				tabela.setDefault_(0);
				tabela.setDefault__check(1);
			}
			
			lista_tabela.add(tabela);
		}
		if (Core.isInt(model.getAplicacao()) ) {
			view.aplicacao.setQuery(Core.query(this.configApp.getMainSettings().getProperty(ConfigCommonMainConstants.IGRP_DATASOURCE_CONNECTION_NAME.value()),"SELECT id as ID, name as NAME FROM tbl_env WHERE id=" + Core.toInt(model.getAplicacao())));		 	
			view.tipo_base_dados.setValue(DatabaseConfigHelper.getDatabaseTypes());
			view.table_1.addData(lista_tabela);
			//if EDIT
			if(Core.isNotNull(Core.getParam("p_id_connection"))) {
				Integer id = Core.getParamInt("p_id_connection");
				Config_env config = new Config_env().findOne(id);
				// if it didnt came from failed connection
				if(Core.isNull(Core.getParam("failed_conn"))) {
					model.setAplicacao(config.getApplication().getId().toString());
					model.setTipo_base_dados(Core.decrypt(config.getType_db(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
					model.setNome_de_conexao(config.getName());
					model.setUrl_connection(Core.decrypt(config.getUrl_connection(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
					model.setDriver_connection(Core.decrypt(config.getDriver_connection(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
					model.setUsername(Core.decrypt(config.getUsername(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
					model.setPassword(null);
					view.btn_gravar.addParameter("p_id", id)
								   .addParameter("conn_name", config.getName())
								   .addParameter("app_name", config.getApplication().getDad())
								   .setLink("edit-gravar");
				}
				// if it is an edit and came from failed connection, just edit the save btn
				else {
					view.btn_gravar.addParameter("p_id", id)
					   .addParameter("conn_name", config.getName())
					   .addParameter("app_name", config.getApplication().getDad())
					   .setLink("edit-gravar");
				}
				
			} else
				if(Core.isNotNull(model.getTipo_base_dados())) {
					if(Core.isNotNull(model.getUrl_connection().trim())){
						if(!model.getUrl_connection().trim().contains(model.getTipo_base_dados()))
							model.setUrl_connection(DatabaseConfigHelper.getUrlConnections(model.getTipo_base_dados()));
					}else
						model.setUrl_connection(DatabaseConfigHelper.getUrlConnections(model.getTipo_base_dados()));	
				model.setDriver_connection(DatabaseConfigHelper.getDatabaseDriversExamples(model.getTipo_base_dados()));
				Integer idApp = Core.toInt(model.getAplicacao());
				Application app = new Application().findOne(idApp);
				List<Config_env> list = new Config_env()
											.find()
											.andWhere("application", "=",idApp)
											.andWhere("type_db", "=",Core.encrypt(model.getTipo_base_dados(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB) )
											.all();
				int size = list!=null?(list.size()+1):1;
				model.setNome_de_conexao(app.getDad().toLowerCase()+"_"+model.getTipo_base_dados()+"_"+size);
//				model.setParagraph_1("  Ex: "+ DatabaseConfigHelper.getUrlConnectionsExamples(model.getTipo_base_dados(),new Application().findOne(model.getAplicacao()).getDad()));
				model.setParagraph_1("  Ex: "+ DatabaseConfigHelper.getUrlConnectionsExamples(model.getTipo_base_dados(),"devnosi.gov.cv"));
			}else
              Core.setMessageInfo(gt("-- Selecionar --")+" "+gt("Tipo de base de dados")+"!");
		}
		
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionGravar() throws IOException, IllegalArgumentException, IllegalAccessException{
		ConfigDatabase model = new ConfigDatabase();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome_de_conexao_tabela",Core.getParam("p_nome_de_conexao_tabela"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","ConfigDatabase","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(gravar)----*/
		
      if (Igrp.getInstance().getRequest().getMethod().equalsIgnoreCase("post")) {		
			Config_env config = new Config_env();
			config.setApplication(Core.findApplicationById(Core.toInt(model.getAplicacao())));
			config.setCharset("utf-8");
			config.setUsername(Core.encrypt(model.getUsername().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setPassword(Core.encrypt(model.getPassword().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setType_db(Core.encrypt(model.getTipo_base_dados(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setUrl_connection(Core.encrypt(model.getUrl_connection().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setDriver_connection(Core.encrypt(model.getDriver_connection(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setName(model.getNome_de_conexao().trim());
			Migrate m = new Migrate();
			m.load();
			if (!(new MigrationIGRP().validate(m)) || config.getName().equalsIgnoreCase(this.configApp.getBaseConnection())) {
				Core.setMessageError(gt("Falha na conexão com a base de dados"));
				return this.forward("igrp", "ConfigDatabase", "index&id=" + model.getAplicacao());
			}
			boolean check = new Config_env().find()
											.andWhere("name", "=", config.getName())
											.andWhere("application", "=",Integer.parseInt(model.getAplicacao()))
											.one() == null;
			if (check && !config.getName().equalsIgnoreCase(this.configApp.getBaseConnection())) {
				java.util.List<Config_env> all = new Config_env().find().andWhere("application", "=", Integer.parseInt(model.getAplicacao())).all();
				if(all == null || all.isEmpty())
					config.setIsDefault((short)1); 
				
				config = config.insert();
				
				if (config != null) {
					this.saveConfigHibernateFile(config);
					Core.setMessageSuccess();
                    Core.setMessageInfo(gt(new ConfigDatabaseView().nome_de_conexao.getLabel())+": " + config.getName());
                    this.addQueryString("p_aplicacao",model.getAplicacao()); 
					return this.redirect("igrp", "ConfigDatabase", "index", this.queryString());
				}
			} else {
              Core.setMessageWarning(gt(new ConfigDatabaseView().nome_de_conexao.getLabel())+" "+gt("INV"));
				return this.forward("igrp", "ConfigDatabase", "index&id=" + model.getAplicacao());
			}
		}
		
		/*----#end-code----*/
		return this.redirect("igrp","ConfigDatabase","index", this.queryString());	
	}
	
	public Response actionEdit() throws IOException, IllegalArgumentException, IllegalAccessException{
		ConfigDatabase model = new ConfigDatabase();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome_de_conexao_tabela",Core.getParam("p_nome_de_conexao_tabela"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","ConfigDatabase","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(edit)----*/
		this.addQueryString("isEdit", true);
        this.addQueryString("p_id_connection", Core.getParam("p_id"));
		return this.forward("igrp","ConfigDatabase","index", this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionDelete() throws IOException, IllegalArgumentException, IllegalAccessException{
		ConfigDatabase model = new ConfigDatabase();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome_de_conexao_tabela",Core.getParam("p_nome_de_conexao_tabela"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","ConfigDatabase","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(delete)----*/
		Config_env obj = new Config_env().findOne(Core.getParamInt("p_id"));
		
		if(obj != null && obj.getApplication() != null) {
			if(obj.getIsDefault() == 1) {
				java.util.List<Config_env> all = new Config_env().find().
						andWhere("name", "<>", obj.getName())
						.andWhere("application", "=", Integer.parseInt(model.getAplicacao()))
						.all();
				if(all != null && all.size() > 0) {
					Config_env aux = all.get(0);
					aux.setIsDefault((short)1);
					aux = aux.update();
				}
			}
			if (obj.delete(obj.getId())) {
				Core.setMessageSuccess();
			}else
				Core.setMessageError();
			
		}
      this.addQueryString("p_aplicacao",Core.getParam("p_aplicacao")); 
      return this.forward("igrp", "ConfigDatabase", "index", this.queryString());
		/*----#end-code----*/
			
	}
	
/*----#start-code(custom_actions)----*/
	private void saveConfigHibernateFile(Config_env config) throws IOException {
		String package_ = "nosi.webapps." + config.getApplication().getDad().toLowerCase() + ".dao";
		String content = this.getHibernateConfig(config,package_);
		String pathWS = this.getConfig().getPathWorkspaceResources();
		String pathServer = this.getConfig().getBasePathClass();
		FileHelper.save(pathServer, config.getName()+"."+config.getApplication().getDad().toLowerCase() + ".cfg.xml", content);
		if (FileHelper.fileExists(pathWS)) {
			FileHelper.save(pathWS, config.getName()+"."+config.getApplication().getDad().toLowerCase() + ".cfg.xml", content);
		}
	}


	public Response actionChangeStatus() {
		this.format = Response.FORMAT_JSON;
        Integer id = Core.getParamInt("p_id");
        boolean response = false;
        
        if(id != null) {
        	Config_env config_env = new Config_env().find().andWhere("id", "=", id).one();
        	if(config_env != null) {
        		java.util.List<Config_env> all = new Config_env().find().andWhere("application.id", "=", config_env.getApplication().getId()).all();
        		if(all != null) {
        			all.forEach(obj->{
        				obj.setIsDefault((short)0);
        				obj = obj.update();
        			});
        		}
        		config_env.setIsDefault((short)1);
        		config_env = config_env.update();
        		if(config_env != null)
        			response = true;
        	}
        }
        JSONObject json = new JSONObject();
        json.put("status", response);     
        return this.renderView(json.toString());
	}
	
	public Response actionEditGravar() throws IOException, IllegalArgumentException, IllegalAccessException{
		ConfigDatabase model = new ConfigDatabase();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","ConfigDatabase","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(gravar)----*/
		if (Igrp.getInstance().getRequest().getMethod().equalsIgnoreCase("post")) {		
			Integer id_conn = Core.getParamInt("p_id");
			Config_env config = new Config_env().findOne(id_conn);
			config.setApplication(Core.findApplicationById(Integer.parseInt(model.getAplicacao())));
			config.setCharset("utf-8");
			config.setUsername(Core.encrypt(model.getUsername().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setPassword(Core.encrypt(model.getPassword().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setType_db(Core.encrypt(model.getTipo_base_dados(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setUrl_connection(Core.encrypt(model.getUrl_connection().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setDriver_connection(Core.encrypt(model.getDriver_connection(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setName(model.getNome_de_conexao().trim());
			Migrate m = new Migrate();
			m.load();

			if (!(new MigrationIGRP().validate(m)) || config.getName().equalsIgnoreCase(this.configApp.getBaseConnection())) {
				Core.setMessageError(gt("Falha na conexão com a base de dados"));
				return this.forward("igrp", "ConfigDatabase", "index&failed_conn=" + true + "&p_id_connection=" + id_conn);
			}
			boolean check = false;
			if(config.getName().equals(Core.getParam("conn_name"))) {
				check = true;
			}
			else check = new Config_env().find()
											.andWhere("name", "=", config.getName())
											.andWhere("application", "=",Integer.parseInt(model.getAplicacao()))
											.one() == null;
			if (check) {
				config = config.update();
				
				if (config != null) { // Editar aqui ... 
					boolean success = updateHibernateConfigFileOfApp(config, model); 
					System.out.println("Hibernate File .cfg updat status success = " + success);
					Core.setMessageSuccess();
                    Core.setMessageInfo(gt(new ConfigDatabaseView().nome_de_conexao.getLabel())+": " + config.getName());
                    this.addQueryString("p_aplicacao",model.getAplicacao()); 
					return this.redirect("igrp", "ConfigDatabase", "index", this.queryString());
				}
			} else {
              Core.setMessageWarning(gt(new ConfigDatabaseView().nome_de_conexao.getLabel())+" "+gt("INV"));
				return this.forward("igrp", "ConfigDatabase", "index&id=" + model.getAplicacao());
			}
		}
		return this.redirect("igrp","ConfigDatabase","index", this.queryString());	
	}

	/*
	private void editConfigHibernateFile(Config_env config) throws IOException {
		String oldConnName = Core.getParam("conn_name");
		String oldAppName = Core.getParam("app_name");
		
		String pathWS = this.getConfig().getPathWorkspaceResources();
		String pathServer = this.getConfig().getBasePathClass();
		FileHelper.renameFile(pathServer, oldConnName+"."+oldAppName+".cfg.xml", config.getName()+"."+config.getApplication().getDad().toLowerCase() + ".cfg.xml");
		if (Core.isNotNull(this.getConfig().getWorkspace()) && FileHelper.fileExists(this.getConfig().getWorkspace())) {
			FileHelper.renameFile(pathWS + File.separator, oldConnName+"."+oldAppName+".cfg.xml", config.getName()+"."+config.getApplication().getDad().toLowerCase() + ".cfg.xml");
		}
	}
	*/
	private String getHibernateConfig(Config_env config,String package_) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE hibernate-configuration PUBLIC\r\n" + 
				"\"-//Hibernate/Hibernate Configuration DTD 3.0//EN\"\r\n" + 
				"\"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n"+ 
				"<hibernate-configuration>\n" + 
				"\t<session-factory>\n" +
				"\t\t<property name=\"hibernate.connection.driver_class\">"+Core.decrypt(config.getDriver_connection(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)+"</property>\r\n" + 
				"\t\t<property name=\"hibernate.connection.url\">"+Core.decrypt(config.getUrl_connection().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)+"</property>\r\n" + 
				"\t\t<property name=\"hibernate.connection.username\">"+Core.decrypt(config.getUsername().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)+"</property>\r\n" + 
				"\t\t<property name=\"hibernate.connection.password\">"+Core.decrypt(config.getPassword().trim(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)+"</property>\r\n" + 
				"\t\t<property name=\"hibernate.hbm2ddl.auto\">update</property>\r\n" + 
				"\t\t<property name=\"hibernate.connection.isolation\">2</property>\r\n" + 
				"\t\t<property name=\"hibernate.connection.autocommit\">false</property>\r\n" + 
				"\t\t<property name=\"hibernate.hbm2ddl.jdbc_metadata_extraction_strategy\">individually</property>\r\n" + 
				"\t\t<property name=\"hibernate.current_session_context_class\">org.hibernate.context.internal.ThreadLocalSessionContext</property>\r\n" + 
				"\t\t<property name=\"hibernate.transaction.auto_close_session\">DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION</property>\r\n" + 
				"\t\t<property name=\"hibernate.dialect\">"+DatabaseConfigHelper.getHibernateDialect(Core.decrypt(config.getType_db(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB))+"</property>\n"+
				"\t\t<!-- Hikaricp configuration -->\r\n" + 
				"\t\t<property name=\"hibernate.connection.provider_class\">org.hibernate.hikaricp.internal.HikariCPConnectionProvider</property>\r\n" + 
				"\t\t<property name=\"hibernate.hikari.connectionTimeout\">120000</property>\r\n" + 
				"\t\t<property name=\"hibernate.hikari.idleTimeout\">600000</property>\r\n" + 
				"\t\t<property name=\"hibernate.hikari.minimumIdle\">0</property>\r\n" + 
				"\t\t<property name=\"hibernate.hikari.maximumPoolSize\">10</property>\r\n" + 
				"\t\t<property name=\"hibernate.hikari.maxLifetime\">1800000</property>\r\n" + 
				"\t\t<property name=\"hibernate.hikari.leakDetectionThreshold\">0</property>\r\n" +
				"\t\t<!-- Mapping your class here... \n" + 
				"\t\tEx: <mapping class=\""+ package_ + ".Employee\"/>" + 
				" \t\t-->\n" + 
				"\t</session-factory>\n"+ 
				"</hibernate-configuration>";
		
	}
	
	
	
	private boolean updateHibernateConfigFileOfApp(Config_env config, ConfigDatabase dados) {
		boolean success = false;
		try {
			String fileName = config.getName()+"."+config.getApplication().getDad().toLowerCase() + ".cfg.xml"; 
			String path = new Config().getPathConexao() ;
			String cfgFileContent = SaveMapeamentoDAO.getHibernateConfig(path + File.separator + fileName); 
			cfgFileContent = processHibernateConfigFileXml(cfgFileContent, config); 
			if(cfgFileContent != null && !cfgFileContent.isEmpty()) 
				success = this.saveFiles(fileName, cfgFileContent, path); 
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return success;
	}
	
	private String processHibernateConfigFileXml(String xmlInput, Config_env config) {
		String xmlOutput = null; 
		try {
		//   Document originalDoc = new SAXReader().read(new StringReader("<root><given></given></root>")); 
			Document doc = new SAXReader().read(new StringReader(xmlInput)); 
			Element root = doc.getRootElement();

			    // iterate through child elements of root
			    Iterator<Element> i = root.elementIterator("session-factory"); 
			    if(i.hasNext()) { 
			    	Element element = (Element) i.next(); 
			    	   Iterator<Element> j = element.elementIterator(); 
			    	   while(j.hasNext()) { 
			    		   Element property = (Element) j.next(); 
			    		   String attr_name = property.attributeValue("name");
			    		   if(attr_name != null) {
			    			   if(attr_name.equals("hibernate.connection.url")) 
			    				   property.setText(Core.decrypt(config.getUrl_connection(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			    			   if(attr_name.equals("hibernate.connection.username")) 
			    				   property.setText(Core.decrypt(config.getUsername(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			    			   if(attr_name.equals("hibernate.connection.password")) 
			    				   property.setText(Core.decrypt(config.getPassword(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			    			   if(attr_name.equals("hibernate.connection.driver_class")) 
			    				   property.setText(Core.decrypt(config.getDriver_connection(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			    			   if(attr_name.equals("hibernate.dialect")) 
			    				   property.setText(DatabaseConfigHelper.getHibernateDialect(Core.decrypt(config.getType_db(),EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)));
			    		   }
			    	   }
			    }
			    xmlOutput = doc.asXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return xmlOutput;
	}
	
	private boolean saveFiles(String fileName,String content,String path) throws IOException {
		boolean flag = false;
		if(Core.isNotNull(content)) {
			flag = FileHelper.save(path, fileName, content);
		}
		return flag;
	}
	
	/*----#end-code----*/
}
