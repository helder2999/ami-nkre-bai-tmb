package nosi.webapps.igrp_studio.pages.file_editor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.apache.commons.text.StringEscapeUtils;
import com.google.gson.Gson;

import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
import nosi.core.webapp.bpmn.BPMNConstants;
import nosi.core.webapp.compiler.helpers.Compiler;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.import_export_v2.common.Path;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Config_env;
import nosi.webapps.igrp.dao.Historic;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/*----#end-code----*/
		
public class File_editorController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		File_editor model = new File_editor();
		model.load();
		model.setJson_data("undefined","undefined","undefined");
		model.setSave_url("undefined","undefined","undefined");
		model.setCreate_url("igrp_studio","File_editor","index");
		File_editorView view = new File_editorView();
		/*----#start-code(index)----*/
		model.setLink_doc(this.getConfig().getResolveUrl("tutorial","Listar_documentos","index&p_type=file_editor"));
		model.setJson_data("igrp_studio", "File_editor", "get-json-all-folder").addParam("task_id", Core.getParam("p_task_id")).addParam("env_fk", Core.getParam("p_env_fk"));
		model.setSave_url("igrp_studio", "File_editor", "save-and-compile-file").addParam("env_fk", Core.getParam("p_env_fk"));
		String type = Core.getParam("type");
		String path = Core.getParam("path");
		String name = Core.getParam("name");
		String file_type = Core.getParam("file_type");
		
		path = URLDecoder.decode(path, "UTF-8");
		if(Core.isNotNullMultiple(type,path,name)) {
			return this.saveFolderFile(type,path,name,file_type);
		}
		view.save_url.setLabel("Save");
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	

/*----#start-code(custom_actions)----*/
	private Response saveFolderFile(String type, String path, String name,String file_type) throws IOException {
		Map<String, Object> dirs = new HashMap<>();
		if(type.compareTo("folder")==0) {
			FileHelper.createDiretory(path+File.separator+name);
			dirs.put("dir_name", name);
			dirs.put("dir", new Object[0]);
			dirs.put("dir_files", new Object[0]);
			dirs.put("dir_path", path+File.separator+name);
		}
		if(type.compareTo("file")==0) {
			FileHelper.save(path, name, FileJavaType.createFile(this.convertToPackageName(path),name.substring(0, name.indexOf(".")),file_type));
			dirs.put("name", name);
			dirs.put("path", this.getConfig().getResolveUrl("igrp_studio", "File_editor", "get-file&fileName="+ URLEncoder.encode(path+File.separator+name,"UTF-8")));
			dirs.put("fileName", path+File.separator+name);
		}

		this.format = Response.FORMAT_JSON;
		String json = new Gson().toJson(dirs);
		return this.renderView(json);
	}

	
	public Response actionGetJsonAllFolder() {
		Integer envId = Core.getParamInt("env_fk");
		Application app = new Application().findOne(envId);
		Map<String, Object> dirs = null;
		if(app!=null) {
			String path = Path.getPath(app);
			try {
				dirs = this.listDirectory(app,new File(path),true);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String task_id = Core.getParam("task_id");
			if(Core.isNotNull(task_id)) {
				this.addDefaultFile(task_id,dirs,envId);
			}
		}		
		this.format = Response.FORMAT_JSON;
		String json = new Gson().toJson(dirs);
		return this.renderView(json);
	}
	
	private void addDefaultFile(String task_id, Map<String, Object> dirs, Integer envId) {
		Action ac = new Action().find().andWhere("application", "=",envId).andWhere("page", "=",BPMNConstants.PREFIX_TASK+task_id).one();
		FileEditor file = new FileEditor();
		file.setName(ac.getPage()+".java");
		try {
			file.setFileName(URLEncoder.encode(Path.getPath(ac.getApplication())+"process"+File.separator+ac.getProcessKey()+ac.getPage()+".java", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		file.setPath(this.getConfig().getResolveUrl("igrp_studio", "File_editor", "get-file&fileName="+ file.getFileName()));
		file.setId(null);
		ArrayList<FileEditor> files = new ArrayList<>();
		files.add(file);
		dirs.put("default_file",files);
	}

	public Map<String, Object> listDirectory(Application app,File dir, boolean addHibernateConfigs) throws UnsupportedEncodingException {
		File[] content = dir.listFiles();
		List<FileEditor> files = new LinkedList<>();
		List<Map<String, Object>> folders = new LinkedList<>();
		if(content!=null) {
			for (File f : content) {
				if (f.isDirectory()) {
					Map<String, Object> subList = listDirectory(app,f,false);
					folders.add(subList);
				} else {
					if(f.getName().endsWith(".java")) {
						this.addFile(files,f);
					}
				}
			}
		}
		if(addHibernateConfigs) {
			String path = Path.getPathHibernateConfig();
			File[] hibernateFiles = new File(path).listFiles();
			for (File f : hibernateFiles) {
				int index = f.getName().indexOf("."+app.getDad().toLowerCase()+".cfg.xml");
				if(index!=-1) {
					Config_env config = new Config_env()
								.find()
								.andWhere("name","=", f.getName().substring(0, index))
								.andWhere("application", "=",app.getId())
								.one();
					if(config!=null) {
						this.addFile(files, f);
					}
				}
			}
		}
		Collections.sort(files,FileEditor.ORDER_BY_NAME);
		Map<String, Object> result = new HashMap<>();
		result.put("dir_name", dir.getName());
		result.put("dir_path", URLEncoder.encode(dir.getPath(),"UTF-8"));
		result.put("dir", folders);
		result.put("dir_files", files);
		return result;
	}

	
	private void addFile(List<FileEditor> files, File f) throws UnsupportedEncodingException {
		FileEditor file = new FileEditor();
		file.setName(URLEncoder.encode(f.getName(),"UTF-8"));
		file.setFileName(URLEncoder.encode(f.getAbsolutePath(), "UTF-8"));
		file.setPath(this.getConfig().getResolveUrl("igrp_studio", "File_editor", "get-file&fileName="+ file.getFileName()));
		file.setId(null);
		file.setDir_path(URLEncoder.encode(f.getParent(),"UTF-8"));
		files.add(file);
	}


	private String convertToPackageName(String dir) {
		int start = dir.indexOf("nosi"+File.separator+"webapps");
		if(Core.isNotNull(dir) && start!=-1) {
			dir = dir.substring(start, dir.length());
			dir = dir.replace(File.separator, ".");
		}
		return dir;
	}


	public Response actionSaveAndCompileFile() {
		try {
			Part javaCode = Core.getFile("p_package");
			String fileName = Core.getParam("fileName");
			if(Core.isNotNull(fileName) && javaCode!=null) {
				fileName = URLDecoder.decode(fileName, "UTF-8");	
				String content = FileHelper.convertToString(javaCode);
				FileHelper.save(fileName, null, content);
				if(fileName.endsWith(".java")) {
					Compiler compiler = new Compiler();
					compiler.addFileName(fileName);
					compiler.compile();
					String erros = compiler.getErrorToJson();
					if(Core.isNotNull(erros)) {
						return this.renderView("<messages><message type=\"error\">"+StringEscapeUtils.escapeXml10(erros)+"</message></messages>");
					}
					if (fileName.endsWith("Controller.java")) {
						String[] code_split = fileName.split("Controller");
						String code_page = code_split[0].substring(code_split[0].lastIndexOf(File.separator))
								.replace(File.separator, "");
						Action page = new Action().find().where("page", "=", code_page)
								.andWhere("application", "=", Core.getParamInt("env_fk")).one();
						Historic hitoric_page = new Historic();
						hitoric_page.setNome(Core.getCurrentUser().getName());
						hitoric_page.setIdUtilizador(Core.getCurrentUser().getId());
						hitoric_page.setPage(page);
						hitoric_page.setDescricao("Alterações no File Editor.");
						hitoric_page.insert();
					}
				}
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}	
		return this.renderView("<messages><message type=\"success\">{\"msg\":\""+StringEscapeUtils.escapeXml10(StringEscapeUtils.escapeHtml4("Compilação efetuada com sucesso"))+"\"}</message></messages>");
		
	}
	
	
	public Response actionGetFile() throws UnsupportedEncodingException {
		String content = "";
		String fileName = Core.getParam("fileName");
		fileName = URLDecoder.decode(fileName,"UTF-8");
		content = FileHelper.readFile(fileName,"");
		this.format = Response.FORMAT_TEXT;
		return this.renderView(content);
	}
	/*----#end-code----*/
}
