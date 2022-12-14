package nosi.core.webapp.import_export_v2.imports;

import java.util.ArrayList;
import java.util.List;

import nosi.core.config.Config;
import nosi.core.webapp.Core;
import nosi.core.webapp.compiler.helpers.Compiler;
import nosi.core.webapp.compiler.helpers.ErrorCompile;
import nosi.webapps.igrp.dao.Application;

/**
 * Emanuel
 * 2 Nov 2018
 */
public class Import{

	private List<IImport> imports;
	private List<String> errors;
	private List<String> warnings;
	
	public Import() {
		this.imports = new ArrayList<>();
		this.errors = new ArrayList<>();
		this.warnings = new ArrayList<>();
	}

	public void add(IImport i) {
		this.imports.add(i);
	}
	
	public void addError(String error) {
		if(Core.isNotNull(error))
			this.errors.add(error);
	}
	
	public void addWarning(String warning) {
		if(Core.isNotNull(warning))
			this.warnings.add(warning);
	}
	
	public void execute() {
		this.imports.stream().forEach(i->{
			i.execute();
			if(Core.isNotNull(i.getError()))
				this.addError(i.getFileName()+" "+i.getError());
			if(Core.isNotNull(i.getWarning()))
				this.addWarning(i.getFileName()+" "+i.getWarning());
		});
	}

	public void compile(Application app) {
		Compiler compiler = new Compiler();
		this.imports.stream().forEach(i->{
			i.getFileName().forEach(f->{
				compiler.addFileName(f);
			});
		});
		compiler.compile();
		this.addError(compiler.getErrors());
		this.addWarning(compiler.getWarnings());
		this.removeJavaClass();
	}
	
	private void removeJavaClass() {
		String env = new Config().getEnvironment();
		if(env.equalsIgnoreCase("prod") || env.equalsIgnoreCase("prd") ) {
			this.imports.stream().forEach(i->{
				i.getFileName().forEach(f->{
					Core.forceDelete(f);
				});
			});
		}
	}

	public void addError(List<ErrorCompile> errors) {
		if(errors!=null) {
			errors.stream().forEach(e->{
				if(Core.isNotNull(e.getError()))
					this.addError(e.getFileName()+" "+e.getError());
			 });
		}
	}

	public void addWarning(List<ErrorCompile> warnings) {
		if(warnings!=null) {
			 warnings.stream().forEach(e->{
				 if(Core.isNotNull(e.getWarning()))
					 this.addWarning(e.getFileName()+" "+e.getWarning());
			 });
		}
	}
	
	public List<String> getErrors() {
		return this.errors;
	}

	public List<String> getWarnings() {
		return warnings;
	}
	
}
