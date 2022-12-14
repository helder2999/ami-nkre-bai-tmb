package nosi.core.gui.components;

/**
 * @author: Emanuel Pereira
 * 
 * Apr 17, 2017
 *
 * Description: class to generate xml of Table
 */
/*
 * /*Generate XML Table
 *<table_1 type="table" structure="fields">
    <fields>
        <number_1 name="p_number_1" type="number" align="left" lookup_parser="false">
            <label>Number</label>
        </number_1>
        ...
    </fields>
    <table>
        <value>
            <row>
                <context-menu>
                    <param>p1=linha1</param>
                    <param>p2=linha1</param>
                </context-menu>
                <number_1>527</number_1>
                <number_1_desc>527</number_1_desc>
                <text_1>Magna dolor labore ipsum totam</text_1>
                <text_1_desc>Magna dolor labore ipsum totam</text_1_desc>
            </row>
            ...
        </value>
        <context-menu>
            <item type="specific" code="" rel="button_1" class="default">
                <title>Button</title>
                <app/>
                <page/>
                <link/>
                <target>_blank</target>
                <img>fa-dot-circle-o</img>
            </item>
            ...
        </context-menu>
    </table>
</table_1>
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import nosi.core.gui.fields.CheckBoxField;
import nosi.core.gui.fields.CheckBoxListField;
import nosi.core.gui.fields.ColorField;
import nosi.core.gui.fields.Field;
import nosi.core.gui.fields.FieldProperties;
import nosi.core.gui.fields.GenXMLField;
import nosi.core.gui.fields.HiddenField;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.helpers.IgrpHelper;
import nosi.core.xml.XMLWritter;

import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
 
public class IGRPTable extends IGRPComponent{

	public static final String TABLE_LOOKUP_ROW = "p_fwl_form_idx";
	protected ArrayList<Field> fields;
	private IGRPContextMenu contextmenu;
	protected float version = (float) 2.3;
	protected ArrayList<IGRPButton> buttons;
	protected List<?> data;
	protected String rows = "";
	protected List<?> modelList;
	private List<Properties> legend_color = new ArrayList<>();
	private Map<Object,Map <String, String> > legend_colors = new HashMap<>();
	
	private final List<String> scapeParam = new ArrayList<>(
				Arrays.asList(new String[] {"p_prm_app","p_prm_page","p_target","p_dad","p_env_frm_url"})
				);
	
	private IGRPTable.Struct [][]rowStruct; 
	private IGRPTable.Struct []columnStruct; 
	
	public IGRPTable(String tag_name,String title) {
		super(tag_name,title);
		this.fields = new ArrayList<>();
		this.buttons = new ArrayList<>();
		this.properties.put("type", "table");
		this.properties.put("xml-type", "table");
		this.properties.put("structure", "fields");
		this.contextmenu = new IGRPContextMenu();
		this.contextmenu.setClassName(this);
		this.createParamsLookup();
	}	
	
	private void createParamsLookup() {
		String forLookup = Igrp.getInstance().getRequest().getParameter("forLookup");
		if(Core.isNotNull(forLookup)){
			Enumeration<String> params = Igrp.getInstance().getRequest().getParameterNames();
			while(params.hasMoreElements()){
				String param=params.nextElement();
				if(!this.scapeParam.contains(param) && !param.equalsIgnoreCase("r") && !param.equalsIgnoreCase("forLookup")){
					this.addLookupField(param,Igrp.getInstance().getRequest().getParameter(param));
				}
			}
		}else {
			String jsonLookup = Core.getParam("jsonLookup");
			if(Core.isNotNull(jsonLookup)) {
				jsonLookup = this.decodeJson(jsonLookup);
				Properties params = (Properties) Core.fromJson(jsonLookup,Properties.class);
				params.entrySet().forEach(p1->{
					this.addLookupField(p1.getKey().toString(),p1.getValue().toString());
				});
			}
		}
	}

	private String decodeJson(String jsonLookup) {
		String jsonLookup_ = jsonLookup;
		try {
			jsonLookup_ = URLDecoder.decode(jsonLookup_, "UTF-8");
			if(jsonLookup_.contains("%"))
				return this.decodeJson(jsonLookup_);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return jsonLookup_;
	}

	private void addLookupField(String param,String value) {
		if(Core.isNotNullMultiple(param,value)){
			Field f = new HiddenField(this,param);
			f.setName(param);
			f.setValue(value);
			f.setParam(true);
			f.propertie().add("isLookup", "true");
			f.setVisible(false);
			this.fields.add(f);
		}
	}
	
	public IGRPTable(String tag_name){
		this(tag_name,"");
	}
	
	public IGRPTable(String tag_name,float version){
		this(tag_name);
		this.version = version;
		this.properties = new FieldProperties();
		this.properties.put("operation", "");
	}
	public void addField(Field field){
		this.fields.add(field);
	}
	
	public void addData(List<?> data){
		this.data = data;
	}
	
	public List<?> getData(){
		return this.data;
	}
	
	public List<Field> getFields(){
		return this.fields;
	}
	
	public List<IGRPButton> getButtons(){
		return this.buttons;
	}
	
	public void addButton(IGRPButton button){
		button.propertie.put("type", "form");
		this.buttons.add(button);
	}
	
	protected void includeRowTotal() { 
		Map<String, Float> m = new HashMap<String, Float>(); 
		boolean hasOneFieldTotal = false; 
		for(Field field : this.fields) {
			String total_footer_or_col =  field.propertie().getProperty(this instanceof IGRPFormList ? "total_col" : "total_footer"); 
			if(total_footer_or_col != null && total_footer_or_col.equalsIgnoreCase("true")) {
				List<?> all = this.modelList != null ? this.modelList : this.data != null ? this.data : new ArrayList<>();
				Float total = (float) 0; 
				for(Object obj : all) { 
					String val = IgrpHelper.getValue(obj, field.getName()); 
					total += Core.toFloat(val); 
				}
				m.put(field.getName(), total); 
				hasOneFieldTotal = true; 
			}else 
				m.put(field.getName(), null);
		}
		if(!m.isEmpty() && hasOneFieldTotal) {
			this.xml.startElement("row");
			this.xml.writeAttribute("total", "yes"); 
			for(Entry<String, Float> e : m.entrySet()) {
				this.xml.startElement(e.getKey());
				this.xml.writeAttribute("name", "p_" + e.getKey()); 
				if(e.getValue() != null)
					this.xml.text(e.getValue() + ""); 
				this.xml.endElement();
			}
			this.xml.endElement();
		}
	}
			
	public String toString(){
		this.xml.startElement(this.tag_name);
			GenXMLField.writteAttributes(this.xml, properties);
			if(this.version > (float) 2.1){ 
				if(columnStruct != null && columnStruct.length > 0) 
					genRawColumns(); 
				else 
					GenXMLField.toXml(this.xml,this.fields);
				this.xml.startElement("table");
				this.xml.startElement("value");
				if(rowStruct != null && rowStruct.length > 0)
					genRawRows(); 
				else {
					if(this.modelList != null)
						this.genRowsWithSql(); 
					else 
						this.genRows();
				}
				this.includeRowTotal();		
				this.xml.endElement();//end tag value 
				this.genLegendColor();
				this.contextmenu.setButtons(this.buttons);
				this.xml.addXml(this.contextmenu.toXmlTools());
				this.xml.endElement();//end tag table
			}else if(this.version == (float) 2.1){
				if(columnStruct != null && columnStruct.length > 0) 
					genRawColumns(); 
				else 
					GenXMLField.toXmlV21(this.xml,this.fields); 
				this.xml.startElement("value");
				if(columnStruct != null && rowStruct != null && rowStruct.length > 0 && rowStruct[0] != null && rowStruct[0].length == columnStruct.length)
					genRawRows(); 
				else {
					if(this.modelList != null)
						this.genRowsWithSql();
					else
						this.genRows();
				}
				this.xml.endElement();//end tag value
				this.genLegendColor();
				this.contextmenu.setButtons(this.buttons);
				this.xml.addXml(this.contextmenu.toXmlTools());
			}
		this.xml.endElement(); 
		String aux = this.xml.toString(); 
		return aux; 
	}

	public void setLegendColors(Map<Object, Map<String, String>> colors) {
		this.legend_colors = colors;
	}
	
	private void genLegendColor() {
		if(this.fields.stream().filter(f->f instanceof ColorField).count() > 0 && this.legend_colors.size() > 0){
			this.xml.startElement("legend_color");
			this.legend_colors.entrySet().stream().forEach(l->{
				for(Entry<String, String> p : l.getValue().entrySet()) {
		          this.xml.startElement("item");
		          	this.xml.setElement("label", p.getValue().toString());
		          	this.xml.setElement("value", l.getKey().toString());
		          	this.xml.setElement("color", p.getKey().toString());
		          this.xml.endElement();
		        }
			});
			this.xml.endElement();
		}
	}

	private void genRowsWithSql() {
		for(Object l:this.modelList) {
			this.xml.startElement("row");
			this.xml.startElement("context-menu");
			for(Field field:this.fields){
				if(field.isParam()){
					String value= IgrpHelper.getValue(l,  field.propertie().getProperty("isLookup")=="true"  ? field.getValue().toString() : field.getName().toLowerCase());
					if(Core.isNull(value))
						value= IgrpHelper.getValue(l, "p_"+field.getName().toLowerCase());
					this.xml.setElement("param", field.propertie().getProperty("name")+"="+ value);
					if(Core.isNotNull(Core.getParam(TABLE_LOOKUP_ROW,false))) {
						this.xml.setElement("param", TABLE_LOOKUP_ROW+"="+Core.getParam(TABLE_LOOKUP_ROW));
					}
				}
			}
			int isPublic = Core.getParamInt("isPublic").intValue();
			if(isPublic==1) {
				this.xml.setElement("param", "isPublic="+isPublic);
			}
			if(l instanceof IGRPTable.Table && ((IGRPTable.Table)l).getHiddenButtons()!=null) {
				this.xml.startElement("param");
				String text= "ctx_hidden=";
				for(IGRPButton button:((IGRPTable.Table)l).getHiddenButtons()) {
					text+=button.getProperties().getProperty("rel")+",";
				}		
				this.xml.text(text);				
				this.xml.endElement();		
			}
			this.xml.endElement();			
			for(Field field:this.fields){
				if(field.isVisible()) {
					String value= IgrpHelper.getValue(l, field.getName().toLowerCase());
					if(Core.isNull(value))
						value= IgrpHelper.getValue(l, "p_"+field.getName().toLowerCase());	
					this.xml.startElement(field.getTagName().startsWith("p_")?field.getTagName().substring(2):field.getTagName());
					this.xml.writeAttribute("name", field.getName().startsWith("p_")?field.getName():"p_"+field.getName());
					this.xml.text(value);
					this.xml.endElement();
				}
			}
			this.xml.endElement();
		}
	}

	
	protected void genRows() { 
		if(this.data != null && this.data.size() > 0 && this.fields.size() > 0){ 
			for(Object obj:this.data){
				this.xml.startElement("row");
				this.xml.startElement("context-menu");
				for(Field field : this.fields){
					if(field.isParam()){
						String value = IgrpHelper.getValue(obj, field.getName());
						value = (value==null||value.equals(""))?IgrpHelper.getValue(obj, field.getValue().toString()).toString():value;
						value = (value==null||value.equals(""))?field.getValue().toString():value;
						if(value!=null && !value.equals(""))
							this.xml.setElement("param", field.propertie().getProperty("name")+"="+value);
						if(Core.isNotNull(Core.getParam(TABLE_LOOKUP_ROW,false))) {
							this.xml.setElement("param", TABLE_LOOKUP_ROW+"="+Core.getParam(TABLE_LOOKUP_ROW));
						}
					}
				}
				int isPublic = Core.getParamInt("isPublic").intValue();
				if(isPublic==1) {
					this.xml.setElement("param", "isPublic="+isPublic);
				}
				if(obj instanceof IGRPTable.Table && ((IGRPTable.Table)obj).getHiddenButtons()!=null) {
					this.xml.startElement("param");
					String text= "ctx_hidden=";
					for(IGRPButton button:((IGRPTable.Table)obj).getHiddenButtons()) {
						if(button!=null)
							text+=button.getProperties().getProperty("rel")+",";
					}		
					this.xml.text(text);
					this.xml.endElement();		
				}
				this.xml.endElement();
				for(Field field:this.fields){ 
					if(field.isVisible()) { 
						this.xml.startElement(field.getTagName());
						this.xml.writeAttribute("name", field.propertie().getProperty("name"));
						String val = IgrpHelper.getValue(obj, field.getName());
						if(val==null || val.equals("")){
							val = field.getValue()!=null?field.getValue().toString():"";
						}
						this.xml.text(val);
						this.xml.endElement();
						String sufix = "_desc";
						if(field instanceof CheckBoxField || field instanceof CheckBoxListField){
							sufix = "_check";
						}
						this.xml.startElement(field.getTagName()+sufix);
						this.xml.writeAttribute("name", field.propertie().getProperty("name")+sufix);
						String val1 = IgrpHelper.getValue(obj, field.getName()+sufix);
						if(val1==null || val1.equals("")){						
							val1 = field.getValue() != null ? field.getValue().toString() : "";						
						}
						this.xml.text(val1);
						this.xml.endElement();
					}
				}
				this.xml.endElement();
			}
		}
		if(!this.rows.equals("")){
			this.xml.addXml(this.rows);
		}
	}

	public void addRowsXMl(String rows){
		this.rows = rows;
	}
	
	//Para formato de xml 2.1
	public String getDoc_list(){
		this.xml.startElement("table");
			this.xml.startElement("prm_doc_list");
				this.xml.writeAttribute("type", "separatordialog");
				this.xml.writeAttribute("container", "true");
				GenXMLField.toXmlV21(this.xml,this.fields);
				if(!this.rows.equals("")){
					this.xml.addXml(this.rows);
				}
			this.xml.endElement();
		this.xml.endElement();
		return this.xml.toString();
	}

	public IGRPTable addLegendColor(String label,String value) {
		Properties p = new Properties();
		p.put(label, value); 
		this.legend_color.add(p);
		return this;
	}

	public void loadModel(List<?> modelList) {
		this.modelList = modelList;
	}
	
	
	public static class Table{
		private List<IGRPButton> buttons;
		
		public Table() {
			this.buttons = new ArrayList<>();
		}
		public void hiddenButton(IGRPButton...buttons) {
			if(buttons != null)
				this.buttons.addAll(Arrays.asList(buttons));
		}
		
		public IGRPButton[] getHiddenButtons() {
			if(this.buttons != null)
				return this.buttons.stream().toArray(IGRPButton[]::new);
			return null;
		}
	}
	
	public static String generateXmlForCalendar(String tagName, List<?> data) { 
		XMLWritter xmlWritter = new XMLWritter(); 
		xmlWritter.startElement(tagName + "_events"); 
		xmlWritter.startElement("table"); 
		xmlWritter.startElement("value");
		
		if(data != null) {
			for(Object obj : data) {
				xmlWritter.startElement("row"); 
				xmlWritter.setElement("context-menu", ""); 
				java.lang.reflect.Field []fields = obj.getClass().getDeclaredFields(); 
				if(fields != null) {
					for(java.lang.reflect.Field field : fields) {
						xmlWritter.startElement(field.getName()); 
						xmlWritter.writeAttribute("name", "p_" + field.getName());
						xmlWritter.text(IgrpHelper.getValue(obj, field.getName()));
						xmlWritter.endElement(); 
					}
				}
				xmlWritter.endElement(); 
			}
		}
		
		xmlWritter.endElement(); 
		xmlWritter.endElement(); 
		xmlWritter.endElement(); 
		
		return xmlWritter.toString();
	}
	
	public void setColumnStruct(IGRPTable.Struct columns[]) {
		columnStruct = columns; 
	}
	
	public void setRowStruct(IGRPTable.Struct [][]data) {
		rowStruct = data; 
	}
	
	private void genRawColumns() {
		xml.startElement("fields");
		for(IGRPTable.Struct column : columnStruct) 
			genXmlDomStruct(column, 0); 
		xml.endElement();
	}
	
	private void genRawRows() { 
		for(IGRPTable.Struct rows[] : rowStruct) {
			xml.startElement("row"); 
			for(IGRPTable.Struct data : rows)
				genXmlDomStruct(data, 0); 
			xml.endElement(); 
		}
	}
	
	private void genXmlDomStruct(IGRPTable.Struct data, int i) { 
		xml.startElement(data.getTagName()); 
		Properties attrs = data.getTagAttrs(); 
		Set<String> keys = attrs.stringPropertyNames(); 
		if(keys != null) {
			for(String key : keys)
				xml.writeAttribute(key, attrs.getProperty(key));
		}
		if(data.childs != null && !data.childs.isEmpty()) {
			genXmlDomStruct(data.childs.get(i), i);  
			i++; 
		}else
			xml.text(data.getTagValue()); 
		xml.endElement();
	}
	
	public static class Struct{
		
		private String tagName; 
		private String tagValue; 
		private Properties tagAttrs; 
		List<Struct> childs; 
		
		public Struct() {
			tagAttrs = new Properties(); 
		}
		
		public String getTagName() {
			return tagName;
		}
		public void setTagName(String tagName) {
			this.tagName = tagName;
			if(this.tagName != null && !this.tagName.isEmpty()) 
				tagAttrs.setProperty("name", "p_" + tagName); 
		}
		public String getTagValue() {
			return tagValue;
		}
		public void setTagValue(String tagValue) {
			this.tagValue = tagValue;
		}
		public Properties getTagAttrs() {
			return tagAttrs;
		}

		public List<Struct> getChilds() {
			return childs;
		}

		public void setChilds(List<Struct> childs) {
			this.childs = childs;
		}
	}
}
