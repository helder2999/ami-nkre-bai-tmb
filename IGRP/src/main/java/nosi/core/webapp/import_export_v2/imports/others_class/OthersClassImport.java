package nosi.core.webapp.import_export_v2.imports.others_class;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;

import nosi.core.webapp.Core;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.import_export_v2.common.Path;
import nosi.core.webapp.import_export_v2.common.serializable.dao.DAOSerializable;
import nosi.core.webapp.import_export_v2.imports.AbstractImport;
import nosi.core.webapp.import_export_v2.imports.IImport;
import nosi.webapps.igrp.dao.Application;

/**
 * Emanuel
 * 22 Nov 2018
 */
public class OthersClassImport extends AbstractImport implements IImport{

	private List<DAOSerializable> others_class;

	public OthersClassImport(Application application) {
		this.others_class = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deserialization(String jsonContent) {
		if(Core.isNotNull(jsonContent)) {
			this.others_class = (List<DAOSerializable>) Core.fromJsonWithJsonBuilder(jsonContent, new TypeToken<List<DAOSerializable>>() {}.getType());
		}
	}

	@Override
	public void execute() {
		if(this.others_class!=null) {
			this.others_class.stream().forEach(fileClass->{
				this.saveFile(fileClass);
			});
		}
	}


	private void saveFile(DAOSerializable fileClass) {
		if(fileClass.getFileName().endsWith(".java")) {
			String basePath = Path.getRootPath();
			if(Core.isNotNull(fileClass.getPath())) {
				basePath += fileClass.getPath().replace("\\", File.separator).replace("//", File.separator);
				try {
					if(!FileHelper.save(basePath, fileClass.getFileName(), fileClass.getContent()))					
						this.addError( fileClass.getFileName()+" has error saving");
					else
						this.fileName.add(basePath + File.separator+fileClass.getFileName());
				} catch (IOException e) {
					this.addError(fileClass.getFileName()+" - "+e.getMessage());
				}
			}
		}
	}
}
