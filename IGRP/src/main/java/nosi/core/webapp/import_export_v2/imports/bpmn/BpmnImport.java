package nosi.core.webapp.import_export_v2.imports.bpmn;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.google.gson.reflect.TypeToken;

import nosi.core.webapp.Core;
import nosi.core.webapp.activit.rest.services.DeploymentServiceRest;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.mime_type.MimeType;
import nosi.core.webapp.import_export_v2.common.Path;
import nosi.core.webapp.import_export_v2.common.serializable.bpmn.BPMNSerializable;
import nosi.core.webapp.import_export_v2.imports.AbstractImport;
import nosi.core.webapp.import_export_v2.imports.IImport;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;

/**
 * Emanuel
 * 2 Nov 2018
 */
public class BpmnImport extends AbstractImport implements IImport {
	
	private List<BPMNSerializable> bpmns;
	private Application application;	
	
	public BpmnImport(Application application) {
		super();
		this.application = application;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deserialization(String jsonContent) {
		if(Core.isNotNull(jsonContent)) {
			this.bpmns = (List<BPMNSerializable>) Core.fromJson(jsonContent, new TypeToken<List<BPMNSerializable>>() {}.getType());
		}
	}

	@Override
	public void execute() {
		if(this.bpmns!=null) {
			this.bpmns.stream().forEach(bpmn->{
				if(this.application==null) {
					 this.application = Core.findApplicationByDad(bpmn.getDad());
				}
				this.saveBPMN(bpmn);
				this.savePagesBPMN(bpmn);
				this.savePagesFile(bpmn);
			});
		}
	}

	private void savePagesFile(BPMNSerializable bpmn) {
		if(bpmn.getPageFiles()!=null) {
			bpmn.getPageFiles().stream().forEach(page->{
				if(page.getFileName().endsWith(".java")) {
					String basePath = Path.getPath(this.application);
					basePath += "process" + File.separator + bpmn.getKey().toLowerCase() + File.separator;
					try {
						if(!FileHelper.save(basePath, page.getFileName(), page.getFileContent()))
							this.addError( page.getFileName()+" has error saving");
						else
							this.fileName.add(basePath+page.getFileName());
					} catch (IOException e) {
						this.addError(e.getMessage());
					}
				}
			});
		}
	}

	private void savePagesBPMN(BPMNSerializable bpmn) {
		if(bpmn.getPages()!=null) {
			bpmn.getPages().stream().forEach(page->{
				Action ac = new Action().find().where("application.dad","=",page.getDad())
											   .andWhere("page","=",page.getPage())
											   .andWhere("processKey","=",page.getProcessKey())
											   .one();
				if(ac==null) {
					ac = new Action();
					Core.mapper(page, ac);
					ac.setApplication(this.application);
					ac = ac.insert();
					this.addError(ac.hasError()?ac.getError().get(0):null);
				}
			});
		}
	}

	private void saveBPMN(BPMNSerializable bpmn) {
		DeploymentServiceRest deploy = new DeploymentServiceRest();
		try {
			deploy.create(FileHelper.convertStringToInputStream(bpmn.getXml()),this.application.getDad(), bpmn.getFileName(),MimeType.APPLICATION_BIN);
			if(Core.isNotNull(deploy.getError()))
				this.addError(deploy.getError().getMessage());
		} catch (IOException e) {
			this.addError(e.getMessage());
		}
	}

}
