package nosi.core.xml;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nosi.core.config.Config;
import nosi.core.config.IHeaderConfig;
import nosi.core.gui.components.IGRPButton;
import nosi.core.gui.components.IGRPComponent;
import nosi.core.gui.components.IGRPForm;
import nosi.core.gui.components.IGRPTable;
import nosi.core.gui.components.IGRPToolsBar;
import nosi.core.gui.fields.CheckBoxField;
import nosi.core.gui.fields.CheckBoxListField;
import nosi.core.gui.fields.DateField;
import nosi.core.gui.fields.EmailField;
import nosi.core.gui.fields.Field;
import nosi.core.gui.fields.FileField;
import nosi.core.gui.fields.HiddenField;
import nosi.core.gui.fields.ListField;
import nosi.core.gui.fields.NumberField;
import nosi.core.gui.fields.RadioField;
import nosi.core.gui.fields.RadioListField;
import nosi.core.gui.fields.TextAreaField;
import nosi.core.gui.fields.TextField;
import nosi.core.webapp.Core;
import nosi.core.webapp.databse.helpers.DatabaseMetadaHelper;
import nosi.core.webapp.databse.helpers.SqlJavaType;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Config_env;

/**
 * Emanuel
 * 11 Dec 2017
 */
public class XMLTransform {

	//Generate completed XML for Page
	public String genXML(String xmlContent,Action page,String typeContent,String conectionName,String schemaName,String tableName) {
		XMLWritter xml = new XMLWritter("rows", page.getPage()+".xsl", "utf-8");
		IHeaderConfig config = new IHeaderConfig() {
			@Override
			public String getPackageInstance(){
				return conectionName;
			}
			@Override
			public String getPackageCopyHtml(){
				return schemaName;
			}
			@Override
			public String getPackageCopyDb(){
				return tableName;
			}
		};
		xml.addXml(new Config().getHeader(config,page));
		xml.startElement("content");
		xml.writeAttribute("type", typeContent);
		xml.setElement("title", "");
			xml.addXml(xmlContent);
		xml.endElement();//close tag content
		xml.endElement();//close tag rows
		return xml.toString();
	}

	//Generate Form XML
	public String generateXMLForm(Config_env config,Action page, List<DatabaseMetadaHelper.Column> columns,Action pageList) {
		XMLWritter xml = new XMLWritter();	

		IGRPForm form = new IGRPForm("form_1",page.getPage_descr().replace("tbl_", "").replace("TBL_", ""));
		IGRPToolsBar tools = new IGRPToolsBar("toolsbar_1");
		
		IGRPButton btn_list = new IGRPButton("List", config.getApplication().getDad().toLowerCase(), pageList.getPage(), "list", "_self", "default|fa-list","","",true);
		btn_list.propertie.add("type","specific").add("code","list").add("rel","list").add("crud_op", "list").add("action-id", pageList.getId());
		IGRPButton btn_gravar = new IGRPButton("Gravar", config.getApplication().getDad().toLowerCase(), page.getPage(), "gravar", "submit", "primary|fa-save","","",true);
		btn_gravar.propertie.add("type","specific").add("code","").add("rel","save").add("crud_op", "save").add("action-id", page.getId());		
		tools.addButton(btn_list);
		tools.addButton(btn_gravar);	
		addField(form, columns);
		xml.addXml(tools.toString());
		xml.addXml(form.toString());
		return xml.toString();
	}
	
	//Generate Table XML
	public String generateXMLTable(Config_env config,Action page, List<DatabaseMetadaHelper.Column> columns,Action pageForm) {
		XMLWritter xml = new XMLWritter();		

		IGRPTable table = new IGRPTable("table_1",page.getPage_descr().replace("tbl_", "").replace("TBL_", ""));
		IGRPToolsBar tools = new IGRPToolsBar("toolsbar_1");
		IGRPButton btn_novo = new IGRPButton("Novo", config.getApplication().getDad().toLowerCase(), pageForm.getPage(), "novo", "modal|refresh", "success|fa-plus","","",true);
		btn_novo.propertie.add("type","specific").add("code","novo").add("rel","new").add("crud_op", "addNew").add("action-id", pageForm.getId());
		tools.addButton(btn_novo);
		
		IGRPButton btn_editar = new IGRPButton("Editar", config.getApplication().getDad().toLowerCase(), pageForm.getPage(), "editar", "mpsubmit|refresh", "warning|fa-pencil","","",true);
		IGRPButton btn_eliminar = new IGRPButton("Eliminar", config.getApplication().getDad().toLowerCase(), page.getPage(), "eliminar", "confirm", "danger|fa-trash","","",true);
		btn_editar.propertie.add("type","specific").add("code","editar").add("rel","update").add("crud_op", "edit").add("action-id", pageForm.getId());
		btn_eliminar.propertie.add("type","specific").add("code","eliminar").add("rel","delete").add("crud_op", "delete").add("action-id", page.getId());
		addField(table, columns);
		table.addButton(btn_editar);
		table.addButton(btn_eliminar);
		xml.addXml(tools.toString());
		xml.addXml(table.toString());
		return xml.toString();
	}
	
	//Add field into Form or Table Component
	public void addField(IGRPComponent component,List<DatabaseMetadaHelper.Column> columns) {
		if(component!=null) {
			columns.stream().forEach(column->{
				Field f = XMLTransform.getGenFiled(component,column);
				f.setLabel(f.getLabel().replace("_", " "));
				if(component instanceof IGRPForm) {					
					((IGRPForm) component).addField(f);
				}else if(component instanceof IGRPTable) {
					((IGRPTable) component).addField(f);
				}
			});
		}
	}
	
	//Get XML Type for Field
	public static Field getGenFiled(IGRPComponent component,DatabaseMetadaHelper.Column column) {
		Field f = null;
		if(component instanceof IGRPTable) {
			switch (SqlJavaType.sqlToXML(column)) {
			case TypesXML.TEXT:
			case TypesXML.COMBOBOX:
				f = new TextField(null, column.getName());
				break;	
			case TypesXML.NUMBER:
				f = new NumberField(null, column.getName());
				break;	
			case TypesXML.CHECK:
				f = new CheckBoxField(null, column.getName());
				break;	
			case TypesXML.DATE:
				f = new DateField(null, column.getName());
				f.propertie().put("format", "date");
				f.propertie().add("data-format", column.getFormat());
				break;
			case TypesXML.DATE_TIME:
				f = new DateField(null, column.getName());
				f.propertie().put("format", "datetime");
				f.propertie().add("data-format", column.getFormat());
				break;
			case TypesXML.EMAIL:
				f = new EmailField(null, column.getName());
				break;	
			case TypesXML.TEXT_AREA:
				f = new TextAreaField(null, column.getName());
				break;	
			case TypesXML.HIDDEN:
				f = new HiddenField(null, column.getName());
				break;
			default:
				f = new TextField(null, column.getName());
				break;
		}
		}else {
			switch (SqlJavaType.sqlToXML(column)) {
				case TypesXML.TEXT:
					f = new TextField(null, column.getName());
					break;	
				case TypesXML.NUMBER:
					f = new NumberField(null, column.getName());
					break;	
				case TypesXML.CHECK:
					f = new CheckBoxField(null, column.getName());
					break;	
				case TypesXML.CHECK_LIST:
					f = new CheckBoxListField(null, column.getName());
					break;	
				case TypesXML.RADIO:
					f = new RadioField(null, column.getName());
					break;	
				case TypesXML.RADIO_LIST:
					f = new RadioListField(null, column.getName());
					break;	
				case TypesXML.COMBOBOX:
					f = new ListField(null, column.getName());
					if(column.isForeignKey() && Core.isNotNull(column.getColumnMap())) {
						f.propertie().add("schemaName", column.getSchemaName()).add("tableName", column.getTableRelation()).add("keyMap", column.getColumnMap()).add("conn", column.getConnectionName());
					}
					break;	
				case TypesXML.DATE:
					f = new DateField(null, column.getName());
					f.propertie().put("format", "date");
					f.propertie().add("data-format", column.getFormat());
					break;
				case TypesXML.DATE_TIME:
					f = new DateField(null, column.getName());
					f.propertie().put("format", "datetime");
					f.propertie().add("data-format", column.getFormat());
					break;
				case TypesXML.EMAIL:
					f = new EmailField(null, column.getName());
					break;	
				case TypesXML.TEXT_AREA:
					f = new TextAreaField(null, column.getName());
					break;	
				case TypesXML.FILE:
					f = new FileField(null, column.getName());
					break;	
				case TypesXML.HIDDEN:
					f = new HiddenField(null, column.getName());
					break;
				default:
					f = new TextField(null, column.getName());
					break;
			}
		}
		f.setLabel(column.getName());
		configureProperties(f,column);
		return f;
	}
	
	//Configure Properties for field
	private static void configureProperties(Field f,DatabaseMetadaHelper.Column column) {
		if(f!=null && column!=null){
			if(!column.isNullable() || column.isPrimaryKey()){
				f.propertie().add("required", "true");
				f.propertie().add("tag", column.getName());
			}
			if(column.isAutoIncrement()) {
				f.propertie().add("tag",column.getName());
				f.propertie().add("isAutoincrement", "true");
			}
			if(column.isPrimaryKey() || column.isAutoIncrement()) {
				f.propertie().add("iskey", "true");
			}
			if(!(f instanceof DateField)) {
				f.propertie().add("maxlength", column.getSize());
			}
			f.propertie().add("name", "p_"+column.getName());
			f.propertie().add("java-type",getTypePrimitive(column.getType().toString()));
		}
	}

	private static String getTypePrimitive(String javaType_) {
		String javaType = javaType_.replace("class ", "");
		switch (javaType) {
			case "java.lang.Integer":
				javaType = "Integer";
				break;
			case "java.lang.Double":
				javaType = "Double";
				break;
			case "java.lang.Float":
				javaType = "Float";
				break;
			case "java.lang.Long":
				javaType = "Long";
				break;
			case "java.lang.Short":
				javaType = "Short";
				break;
			case "java.sql.Date":
				javaType = "Date";
				break;
	
			default:
				javaType = "String";
		}
		return javaType;
	}

	//Transform XML to Output content using an XSL File Transformation
	public static String xmlTransformWithXSL(String xmlFileName,String xslFileName) throws TransformerConfigurationException{
		StreamSource xlsStreamSource = new StreamSource(Paths.get(xslFileName).toAbsolutePath().toFile());
	    StreamSource xmlStreamSource = new StreamSource(Paths.get(xmlFileName).toAbsolutePath().toFile());
		try {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    StreamResult result = new StreamResult(new ByteArrayOutputStream());
	    Transformer transformer;
	
			transformer = transformerFactory.newTransformer(xlsStreamSource);
			if(transformer!=null) {
			    transformer.transform(xmlStreamSource, result);
			    return result.getOutputStream().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String xmlTransformWithXSL(InputStream inputStream,String xslFileName) throws TransformerConfigurationException{
		StreamSource xlsStreamSource = new StreamSource(Paths.get(xslFileName).toAbsolutePath().toFile());
	    StreamSource xmlStreamSource = new StreamSource(inputStream);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	//	transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant - mas deu erro conflito ao fazer cRUD
		//transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); // Compliant
	    StreamResult result = new StreamResult(new ByteArrayOutputStream());
	    Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer(xlsStreamSource);
			if(transformer!=null) {
			    transformer.transform(xmlStreamSource, result);
			    return result.getOutputStream().toString();
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	public static Field getField(String name,String type){
		switch (type) {
			case "date":
				return new DateField(null, name);
			case "long":
				return new NumberField(null, name);
			case "boolean":
				return new CheckBoxField(null, name);
			case "enum":
				return new ListField(null, name);
			case "hidden":
				return new HiddenField(null,name);
			default :
					return new TextField(null, name);
		}
	}*/
}
