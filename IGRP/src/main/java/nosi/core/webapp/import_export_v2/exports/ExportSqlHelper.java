package nosi.core.webapp.import_export_v2.exports;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nosi.core.config.ConfigDBIGRP;
import nosi.core.webapp.Core;
import nosi.core.webapp.activit.rest.business.ProcessDefinitionIGRP;
import nosi.core.webapp.activit.rest.entities.ProcessDefinitionService;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.import_export_v2.common.OptionsImportExport;
import nosi.core.webapp.import_export_v2.common.Path;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp_studio.pages.wizard_export_step_2.Wizard_export_step_2;
import nosi.webapps.igrp_studio.pages.wizard_export_step_2.Wizard_export_step_2View;
import nosi.webapps.igrp_studio.pages.wizard_export_step_2.Wizard_export_step_2.Table_bpmn;

/**
 * Emanuel
 * 31 Oct 2018
 */
public class ExportSqlHelper {

	private Application application;
	private List<File> files;

	public void loadDataExport(Wizard_export_step_2View view, Wizard_export_step_2 model, String[] opcoes) {
		this.application = Core.findApplicationById(model.getApplication_id());
		this.showAllTable(view,false);
		for(String type:opcoes) {
			int t = Core.toInt(type).intValue();
			if(t==OptionsImportExport.BPMN.getValor()) {
				view.table_bpmn.setVisible(true);
				this.loadBPMNData(model);
			}
			else if(t==OptionsImportExport.DOMAIN.getValor()) {
				view.table_domain.setVisible(true);
				this.loadDomainData(model);
			}
			else if(t==OptionsImportExport.PAGE.getValor()) {
				view.box_paginas.setVisible(true);
				view.table_pagina.setVisible(true);
				this.loadPageData(model);
			}
			else if(t==OptionsImportExport.TRANSATION.getValor()) {
				view.tbl_transation.setVisible(true);
				this.loadTransationData(model);
			}
			else if(t==OptionsImportExport.REPORT.getValor()) {
				view.table_report.setVisible(true);
				this.loadReportData(model);
			}
			else if(t==OptionsImportExport.CONNECTION.getValor()) {
				view.table_connections.setVisible(true);
				this.loadConnectionData(model);
			}
			else if(t==OptionsImportExport.DAO.getValor()) {
				view.table_dao.setVisible(true);
				this.loadDaoData(model);
			}
			else if(t==OptionsImportExport.MENU.getValor()) {
				view.table_menu.setVisible(true);
				this.loadMenuData(model);
			}
			else if(t==OptionsImportExport.OTHERS_CLASS.getValor()) {
				view.table_others_class.setVisible(true);
				this.loadOthersClassData(model);
			}
			else if(t==OptionsImportExport.DOCUMENT_TYPE.getValor()) {
				view.table_doc_type.setVisible(true);
				this.loadDocTypeData(model);
			}
			else if(t==OptionsImportExport.SERVICE.getValor()) {
				view.table_service.setVisible(true);
				this.loadServiceData(model);
			}
		}
	}


	private void loadDocTypeData(Wizard_export_step_2 model) {
		String sql = "SELECT id as tipo_doc_ids,0 as tipo_doc_ids_check, concat(codigo,' - ',descricao) as descricao_tipo_doc "
				   + "FROM tbl_tipo_documento "
				   + "WHERE status=1 AND env_fk=:application_id";
		model.loadTable_doc_type(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql).addInt("application_id", model.getApplication_id()));
	
	}


	private void showAllTable(Wizard_export_step_2View view, boolean isVisible) {
		view.table_bpmn.setVisible(isVisible);
		view.table_connections.setVisible(isVisible);
		view.table_dao.setVisible(isVisible);
		view.table_domain.setVisible(isVisible);
		view.table_menu.setVisible(isVisible);
		view.box_paginas.setVisible(isVisible);
		view.table_pagina.setVisible(isVisible);
		view.table_report.setVisible(isVisible);
		view.table_modulo.setVisible(isVisible);
		view.tbl_transation.setVisible(isVisible);
		view.table_others_class.setVisible(isVisible);
		view.table_doc_type.setVisible(isVisible);
		view.table_service.setVisible(isVisible);
	}


	private void loadTransationData(Wizard_export_step_2 model) {
		String sql = "SELECT id as transation_ids,0 as transation_ids_check, concat(descr,' (',code,')') as descricao_transation "
				   + "FROM tbl_transaction "
				   + "WHERE status=1 AND env_fk=:application_id";
		model.loadTbl_transation(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql).addInt("application_id", model.getApplication_id()));
	}

	private String loadDataFromFile(Wizard_export_step_2 model, String dir,String columnName) {
		String sql = "";
		String basePath = Path.getPath(this.application)+dir;		
		this.files = new ArrayList<>();
		this.listFilesDirectory(basePath);
		if(this.files!=null) {
			int size = this.files.size();
			int count = 0;
			for(File f:this.files) {
				sql += "SELECT '" + f.getAbsolutePath() + "' as " + columnName + "_ids,0 as " + columnName
						+ "_ids_check, '" +f.getParentFile().getName()+" / "+f.getName()+"' as descricao_" + columnName;
				++count;
				if(count!=size) {
					sql+=" UNION ";
				}
			}
		}
		return sql;
	}
	
	public void listFilesDirectory(String path) {
		if(FileHelper.fileExists(path)){
			File folder = new File(path);
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		        	//dao, process,services and pages folder export in another option
		        	if(!fileEntry.getName().equals(OptionsImportExport.DAO.getFileName()) && !fileEntry.getName().equals(OptionsImportExport.BPMN.getFileName())
		        	&& !fileEntry.getName().equals(OptionsImportExport.PAGE.getFileName()) && !fileEntry.getName().equals(OptionsImportExport.SERVICE.getFileName()))
		        		listFilesDirectory(fileEntry.toString());
		        } else {
		        	if(fileEntry.getName().endsWith(".java"))
		        		this.files.add(fileEntry);
		        }
		    }
		}
	}

	private void loadOthersClassData(Wizard_export_step_2 model) {
		String sql = this.loadDataFromFile(model,"", OptionsImportExport.OTHERS_CLASS.getFileName());
		if(Core.isNotNull(sql))
			model.loadTable_others_class(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql));
	}

	private void loadServiceData(Wizard_export_step_2 model) {
		String sql = this.loadDataFromFile(model,OptionsImportExport.SERVICE.getFileName(),OptionsImportExport.SERVICE.getFileName());
		if(Core.isNotNull(sql))
			model.loadTable_service(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql));
	}

	private void loadDaoData(Wizard_export_step_2 model) {
		String sql = this.loadDataFromFile(model,OptionsImportExport.DAO.getFileName(),OptionsImportExport.DAO.getFileName());
		if(Core.isNotNull(sql))
			model.loadTable_dao(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql));
	}
	private void loadMenuData(Wizard_export_step_2 model) {
		String sql = "SELECT id as menu_ids,0 as menu_ids_check, descr as descricao_menu "
				   + "FROM tbl_menu "
				   + "WHERE env_fk=:application_id";
		model.loadTable_menu(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql).addInt("application_id", model.getApplication_id()));
	}


	
	private void loadConnectionData(Wizard_export_step_2 model) {
		String sql = "SELECT id as conexao_ids,0 as conexao_ids_check, name as descricao_conexao "
				   + "FROM tbl_config_env "
				   + "WHERE env_fk=:application_id";
		model.loadTable_connections(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql).addInt("application_id", model.getApplication_id()));
	}


	private void loadReportData(Wizard_export_step_2 model) {
		String sql = "SELECT id as report_ids,0 as report_ids_check, concat(name,' (',code,')') as descricao_report "
				   + "FROM tbl_rep_template "
				   + "WHERE env_fk=:application_id AND status=1";
		model.loadTable_report(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql).addInt("application_id", model.getApplication_id()));
	}

	private void loadPageData(Wizard_export_step_2 model) {
		String sql = "SELECT id as pagina_ids,0 as pagina_ids_check,concat(page_descr,' (',page,')') as descricao_pagina "
				   + "FROM tbl_action "
				   + "WHERE env_fk=:application_id AND status=1 AND processkey is null";
		if(Core.isNotNull(model.getModulo())) {
			sql+= " AND (";
			int count=0;
			int size = model.getModulo().length;			
			for(String modulo:model.getModulo()) {
				sql+=" nomeModulo='"+modulo+"'";
				++count;
				if(count!=size)
					sql+=" OR ";
			}
			sql+= ")";
		}
		model.loadTable_pagina(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql).addInt("application_id", model.getApplication_id()));
	}

	private void loadDomainData(Wizard_export_step_2 model) {
		String sql = "SELECT DISTINCT dominio as domain_ids,0 as domain_ids_check, dominio as descricao_domain "
				   + "FROM tbl_domain WHERE status='ATIVE' AND env_fk="+model.getApplication_id();
//		sql += " UNION ";
//		sql += " SELECT dominio as domain_ids,'-1' as domain_ids_check, dominio as descricao_domain "
//				   + "FROM tbl_domain WHERE status='ATIVE'  AND (domain_type='"+DomainType.PUBLIC+"' OR domain_type is null)) X ORDER BY domain_ids_check ";
		model.loadTable_domain(Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG,sql));
	}

	private void loadBPMNData(Wizard_export_step_2 model) {
		List<Table_bpmn> table_1 = new ArrayList<>();
		for(ProcessDefinitionService process: new ProcessDefinitionIGRP().getProcessDefinitionsForCreated(this.application.getDad())){
			Table_bpmn t = new Table_bpmn();
			t.setBpmn_ids(process.getId());
			//t.setBpmn_ids_check(process.getId());
			t.setDescricao_bpmn(process.getName());
			table_1.add(t );
		}
		model.setTable_bpmn(table_1);
	}

}
