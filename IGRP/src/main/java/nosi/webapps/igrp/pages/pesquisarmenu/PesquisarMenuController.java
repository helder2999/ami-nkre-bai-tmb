package nosi.webapps.igrp.pages.pesquisarmenu;

import nosi.core.config.ConfigApp;
import nosi.core.gui.components.IGRPSeparatorList.Pair;
import nosi.core.gui.components.IGRPTopMenu;
import nosi.core.integration.pdex.service.AppConfig;
import nosi.core.integration.pdex.service.AppConfig.ExternalMenu;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.core.webapp.activit.rest.business.ProcessInstanceIGRP;
import nosi.core.webapp.activit.rest.business.TaskServiceIGRP;
import nosi.core.xml.XMLWritter;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Menu;
import nosi.webapps.igrp.dao.Menu.MenuProfile;
import nosi.webapps.igrp.dao.Organization;
import nosi.webapps.igrp.dao.ProfileType;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static nosi.core.i18n.Translator.gt;

/*----#end-code----*/
		
public class PesquisarMenuController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarMenu model = new PesquisarMenu();
		model.load();
		PesquisarMenuView view = new PesquisarMenuView();
		view.id.setParam(true);
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		model.loadTable_1(Core.query(null,"SELECT 'Totam officia iste ut ipsum accusantium iste sit omnis rem laudantium deserunt accusantium natus des' as t1_menu_principal,'1' as ativo,'25' as ordem,'Aliqua anim totam natus volupt' as icon,'Rem adipiscing sed deserunt adipiscing omnis labore lorem sed totam voluptatem totam ipsum aliqua la' as table_titulo,'Lorem unde anim perspiciatis aperiam consectetur rem laudantium officia laudantium adipiscing labore' as pagina,'1' as checkbox,'hidden-a3f7_7e27' as id "));
		model.loadFormlist_1(Core.query(null,"SELECT 'Consectetur stract laudantium ipsum mollit' as pagina_order,'hidden-c12f_2540' as id_page_ord,'hidden-7901_8043' as id_pai,'hidden-b089_7b47' as id_do_pai "));
		view.aplicacao.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		  ----#gen-example */
		/* Start-Code-Block (index) *//* End-Code-Block (index) */
		/*----#start-code(index)----*/

		int idApp;

		// If in an app, choose automatically the app in the combo-box
		String dad = Core.getCurrentDad();

		if (!"igrp".equalsIgnoreCase(dad) && !"igrp_studio".equalsIgnoreCase(dad)) {
			idApp = Core.findApplicationByDad(dad).getId();
			view.aplicacao.propertie().add("disabled", "true");
		} else {
			idApp = Core.toInt(model.getAplicacao());
		}

		if (idApp != 0) {
			model.setAplicacao("" + idApp);
			Core.setAttribute("p_aplicacao", "" + idApp);
			view.btn_btn_novo.addParameter("p_aplicacao", idApp);
		}

		model.setId_app(idApp);

		Menu menu = new Menu();
		menu.setApplication(idApp != 0 ? new Application().findOne(idApp) : null);
		List<Menu> menus;

		if (idApp != 0) {
			if (Core.getCurrentUser().getEmail().compareTo("igrpweb@nosi.cv") == 0) {// User master
				menus = menu.find().andWhere("application.id", "=", idApp).orderByAsc("orderby").all();
			} else {
				menus = menu.find().andWhere("application.id", "=", idApp)
						.andWhere("application", "<>", 1) // Oculta IGRP Core
						.andWhere("application", "<>", 2) // Oculta IGRP Tutorial
						.andWhere("application", "<>", 3) // Oculta IGRP Studio
						.orderByAsc("orderby").all();
			}

			menus.sort((a, b) -> b.getStatus() - a.getStatus());

			final ArrayList<PesquisarMenu.Table_1> lista = new ArrayList<>();

			final List<PesquisarMenu.Formlist_1> separatorlistDocs = new ArrayList<>();

			// Preenchendo a tabela
			for (Menu menu_db1 : menus) {

				final PesquisarMenu.Table_1 table1 = new PesquisarMenu.Table_1();
				final PesquisarMenu.Formlist_1 row = new PesquisarMenu.Formlist_1();

				table1.setT1_menu_principal("-");
				if (Core.isNull(menu_db1.getMenu())) {
					table1.setT1_menu_principal(menu_db1.getDescr());
				} else if (!Objects.equals(menu_db1.getMenu().getId(), menu_db1.getId())) {
					table1.setT1_menu_principal(menu_db1.getMenu().getDescr());
				}
				table1.setTable_titulo(gt(menu_db1.getDescr()));
				row.setPagina_order(new Pair(menu_db1.getDescr(), menu_db1.getDescr()));

				if (menu_db1.getAction() != null) {
					String mdad = "";
					if (menu_db1.getAction().getApplication().getId() != idApp)
						mdad = menu_db1.getAction().getApplication().getDad() + " / ";
					table1.setPagina(mdad + gt(menu_db1.getAction().getPage_descr()) + " ("
							+ menu_db1.getAction().getPage() + ")");
				} else if (menu_db1.getLink() != null && !menu_db1.getLink().isEmpty()) {
					table1.setPagina("Página Externa (GlobalAcl)");
					table1.setCheckbox(1);
					table1.setCheckbox_check(1);
				}
				row.setId_page_ord(new Pair(menu_db1.getId() + "", menu_db1.getId() + ""));
				row.setFormlist_1_id(new Pair(menu_db1.getId() + "", menu_db1.getId() + ""));

				if (Core.isNotNullOrZero(menu_db1.getMenu()) && !menu_db1.getMenu().getId().equals(menu_db1.getId()) )
					row.setId_do_pai(new Pair(menu_db1.getMenu().getId() + "", menu_db1.getMenu().getId() + ""));
				else
					row.setId_pai(new Pair(menu_db1.getId() + "", menu_db1.getId() + ""));

				table1.setOrdem(menu_db1.getOrderby());
				table1.setAtivo(menu_db1.getStatus());
				table1.setAtivo_check(menu_db1.getStatus() == 1 ? menu_db1.getStatus() : -1);
				table1.setCheckbox(menu_db1.getId());
				table1.setId("" + menu_db1.getId());
				table1.setIcon("<i class=\"fa " + menu_db1.getMenu_icon() + "\"/>");
				if (menu_db1.getFlg_base() == 1) {
					table1.setCheckbox_check(menu_db1.getId());
				}
				lista.add(table1);
				separatorlistDocs.add(row);
			}
			model.setFormlist_1(separatorlistDocs);

			if (!lista.isEmpty())
				lista.sort(Comparator.comparing(PesquisarMenu.Table_1::getT1_menu_principal));

			view.table_1.addData(lista);
		}

		view.aplicacao.setValue(new Application().getListApps());

		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionBtn_novo() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarMenu model = new PesquisarMenu();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		  this.addQueryString("p_id","12"); //to send a query string in the URL
		  this.addQueryString("p_id",Core.getParam("p_id"));
		  return this.forward("igrp","Dominio","index",this.queryString()); //if submit, loads the values
		  Use model.validate() to validate your model
		  ----#gen-example */
		/* Start-Code-Block (btn_novo)  *//* End-Code-Block  */
		/*----#start-code(btn_novo)----*/
		return this.redirect("igrp", "NovoMenu", "index&p_app="+Core.getParam("p_aplicacao"), this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionGravar_ordenacao() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarMenu model = new PesquisarMenu();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		  this.addQueryString("p_id","12"); //to send a query string in the URL
		  this.addQueryString("p_id",Core.getParam("p_id"));
		  return this.forward("igrp","PesquisarMenu","index",this.queryString()); //if submit, loads the values
		  Use model.validate() to validate your model
		  ----#gen-example */
		/* Start-Code-Block (gravar_ordenacao)  *//* End-Code-Block  */
		/*----#start-code(gravar_ordenacao)----*/
		try {
			int i = 100;
			for (PesquisarMenu.Formlist_1 row : model.getFormlist_1()) {
				if (Core.isNotNull(row.getId_pai().getKey()) || Core.isNull(row.getId_do_pai().getKey())) {
					Menu tblimagelogin = new Menu().findOne(Core.toInt(row.getId_page_ord().getKey()));
					if (tblimagelogin != null) {
						tblimagelogin.setOrderby(i);
						tblimagelogin.update();
						i++;
					}
				}
			}
			int j = 1;
			for (PesquisarMenu.Formlist_1 row : model.getFormlist_1()) {
				if (Core.isNotNull(row.getId_do_pai().getKey())) {
					Menu tblimagelogin = new Menu().findOne(Core.toInt(row.getId_page_ord().getKey()));
					if (tblimagelogin != null) {
						String ordemPai = tblimagelogin.getMenu().getOrderby() + "";
						String ordem = ordemPai.concat(j + "");
						tblimagelogin.setOrderby(Core.toInt(ordem));
						tblimagelogin.update();
						j++;
					}
				}
			}
			Core.setMessageSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			Core.setMessageError("Error: " + e.getMessage());
		}
		return this.forward("igrp", "PesquisarMenu", "index", this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionEditar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarMenu model = new PesquisarMenu();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		  this.addQueryString("p_id","12"); //to send a query string in the URL
		  this.addQueryString("p_id",Core.getParam("p_id"));
		  return this.forward("igrp","NovoMenu","index",this.queryString()); //if submit, loads the values
		  Use model.validate() to validate your model
		  ----#gen-example */
		/* Start-Code-Block (editar)  *//* End-Code-Block  */
		/*----#start-code(editar)----*/
		return this.redirect("igrp","NovoMenu","index&p_id="+Core.getParam("p_id"), this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionEliminar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarMenu model = new PesquisarMenu();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		  this.addQueryString("p_id","12"); //to send a query string in the URL
		  this.addQueryString("p_id",Core.getParam("p_id"));
		  return this.forward("igrp","PesquisarMenu","index",this.queryString()); //if submit, loads the values
		  Use model.validate() to validate your model
		  ----#gen-example */
		/* Start-Code-Block (eliminar)  *//* End-Code-Block  */
		/*----#start-code(eliminar)----*/

		final Menu menu = new Menu().findOne(Core.getParamInt("p_id"));

		if (menu != null) {

			final Long numberOfChildMenus = new Menu().find()
					.andWhere("menu.id", "=", menu.getId())
					.andWhere("id", "!=", menu.getId())
					.getCount();

			if (numberOfChildMenus > 0)
				Core.setMessageError("Este menu possui dependências. Não pode ser eliminado.");
			else if (menu.delete())
				Core.setMessageSuccess();
			else
				Core.setMessageError();
		}

		return this.redirect("igrp", "PesquisarMenu", "index", model, this.queryString());
		/*----#end-code----*/
			
	}
	/* Start-Code-Block (custom-actions)  *//* End-Code-Block  */
/*----#start-code(custom_actions)----*/

	// Menu list I have access to
	public Response actionMyMenu() {

		final XMLWritter xmlWritter = new XMLWritter();
		xmlWritter.startElement("menus");

		try {
			if (Igrp.getInstance().getUser().isAuthenticated()) {

				final LinkedHashMap<String, List<MenuProfile>> menu = new Menu().getMyMenu();

				if (menu != null) {

					for (Entry<String, List<MenuProfile>> m : menu.entrySet()) {
						xmlWritter.startElement("menu");
						xmlWritter.setElement("title", gt(m.getKey()));

						for (MenuProfile menuProfile : m.getValue()) {

							if (menuProfile.isSubMenuAndSuperMenu()) {
								xmlWritter.setElement("link", menuProfile.getType() == 2 ? menuProfile.getLink() : "webapps?r=" + menuProfile.getLink());
								xmlWritter.setElement("target", menuProfile.getTarget());
							}

							xmlWritter.setElement("order", "" + menuProfile.getOrder());
							xmlWritter.startElement("submenu");
							xmlWritter.writeAttribute("title", gt(menuProfile.getTitle()));
							xmlWritter.writeAttribute("id", "" + menuProfile.getId());

							xmlWritter.setElement("link", menuProfile.getType() == 2 ? menuProfile.getLink() : "webapps?r=" + menuProfile.getLink());

							xmlWritter.setElement("title", gt(menuProfile.getTitle()));
							xmlWritter.setElement("target", menuProfile.getTarget());
							xmlWritter.setElement("id", "" + menuProfile.getId());
							xmlWritter.setElement("status", "" + menuProfile.getStatus());
							xmlWritter.setElement("order", "" + menuProfile.getOrder());
							xmlWritter.setElement("menu_icon", menuProfile.getMenu_icon());
							xmlWritter.endElement();
						}
						xmlWritter.endElement();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		displayMenusPlSql(xmlWritter);

		xmlWritter.endElement();
		this.format = Response.FORMAT_XML;
		return this.renderView(xmlWritter.toString());
	}

	private void displayMenusPlSql(XMLWritter xmlWritter) {

		final List<ExternalMenu> menus = getAllMyMenusFromPlSql();

		if (menus == null || menus.isEmpty())
			return;

		xmlWritter.startElement("menu");
		xmlWritter.setElement("title", "IGRP-PLSQL SSO");
		xmlWritter.setElement("order", "99");
		xmlWritter.setElement("id", "2020");
		xmlWritter.setElement("status", "1");

		for (ExternalMenu m : menus) {

			if (m.getSelf_id() == null || m.getSelf_id().isEmpty()) {
				boolean hasChild = false;
				xmlWritter.startElement("submenu");
				xmlWritter.setElement("title", gt(m.getTitle()));
				xmlWritter.setElement("target", m.getArea());
				xmlWritter.setElement("id", "" + m.getId());
				xmlWritter.setElement("status", "" + m.getEstado());
				xmlWritter.setElement("order", "99");
				for (ExternalMenu submenu : menus) {
					if (submenu.getSelf_id() != null && m.getId() != null
							&& submenu.getSelf_id().equalsIgnoreCase(m.getId())) {
						xmlWritter.startElement("submenu");
						xmlWritter.writeAttribute("title", gt(submenu.getTitle()));
						xmlWritter.writeAttribute("id", "" + submenu.getId());
						xmlWritter.setElement("title", gt(submenu.getTitle()));
						xmlWritter.setElement("target", submenu.getArea());
						xmlWritter.setElement("id", "" + submenu.getId());
						xmlWritter.setElement("status", "" + submenu.getEstado());
						xmlWritter.setElement("order", "99");
						xmlWritter.setElement("link", "" + submenu.getUrl());
						xmlWritter.endElement();
						hasChild = true;
					}
				}
				if (!hasChild)
					xmlWritter.setElement("link", "" + m.getUrl());

				xmlWritter.endElement();
			}
		}
		xmlWritter.endElement();
	}

	// Get Top Menu
	public Response actionTopMenu() {
		boolean isStartProc = ProcessInstanceIGRP.isStartPermission();
		boolean isTask = TaskServiceIGRP.isTaskPermission();
		IGRPTopMenu topMenu = new IGRPTopMenu("top_menu");
		String dad = Core.getParam("dad");

		if (!"igrp".equals(dad)) {
			Application app = Core.getCurrentApp();
			String page = "tutorial/DefaultPage/index&title=" + app.getName();
			if (app.getAction() != null) {
				page = app.getAction().getApplication().getDad().toLowerCase() + "/" + app.getAction().getPage() + "/"
						+ app.getAction().getAction();
			}
			topMenu.addItem("Home", "igrp_studio", "env", "openApp" + "&app=" + dad + "&page=" + page, "_self",
					"home.png", "webapps?r=");
		} else {
			topMenu.addItem("Home", "igrp", "DefaultPage", "index", "_self", "home.png", "webapps?r=");
		}

		String flag = "english_flag.png";
		for (Cookie cookie : Igrp.getInstance().getRequest().getCookies()) {
			if (cookie.getName().equals("igrp_lang")) {
				switch (cookie.getValue()) {
				case "pt_PT":
					flag = "portuguese_flag.png";
					break;
				case "es_ES":
					flag = "spanish_flag.png";
					break;
				case "fr_FR":
					flag = "french_flag.png";
					break;
				}
			}
		}

		topMenu.addItem("Settings", "igrp", "Settings", "index", "modal", flag, "webapps?r=");
		if (isStartProc) {
			topMenu.addItem("Mapa Processos", "igrp", "Dash_board_processo", "index", "_self", "process.png",
					"webapps?r=");
		}
		if (isTask) {
			topMenu.addItem("Tarefas", "igrp", "ExecucaoTarefas", "index", "_self", "tasks.png", "webapps?r=");
		}
		this.format = Response.FORMAT_XML;
		return this.renderView(topMenu.toString());
	}

	public Response actionChangeStatus() throws IllegalArgumentException, JSONException {

		this.format = Response.FORMAT_JSON;
		Integer id = Core.getParamInt("p_id");
		String status = Core.getParam("p_ativo_check");
		boolean response = false;

		if (Core.isNotNullOrZero(id)) {
			Menu menu = new Menu().findOne(id);
			if (menu != null) {
				menu.setStatus(status.equals("true") ? 1 : 0);
				menu = menu.update();
				if (!menu.hasError())
					response = true;
			}
		}
		JSONObject json = new JSONObject();
		json.put("status", response);
		return this.renderView(json.toString());
	}

	/**
	 * Integration with IGRP-PLSQL Apps **
	 */
	private List<ExternalMenu> getAllMyMenusFromPlSql() {
		String appCode = "";
		String profCode = "";
		String orgCode = "";
		try {
			appCode = Core.getCurrentApp().getPlsql_code();
			profCode = new ProfileType().findOne(Core.getCurrentProfile()).getPlsql_code();
			orgCode = new Organization().findOne(Core.getCurrentOrganization()).getPlsql_code();
		} catch (Exception ignored) {
			// Ignored
		}
		final Properties properties = ConfigApp.getInstance().loadConfig("common", "main.xml");

		final String IGRP_PDEX_APPCONFIG_URL = "igrp.pdex.appconfig.url";
		final String baseUrl = properties.getProperty(IGRP_PDEX_APPCONFIG_URL);
		final String IGRP_PDEX_APPCONFIG_TOKEN = "igrp.pdex.appconfig.token";
		final String token = properties.getProperty(IGRP_PDEX_APPCONFIG_TOKEN);

		AppConfig appConfig = new AppConfig();
		appConfig.setUrl(baseUrl);
		appConfig.setToken(token);

		return appConfig.profAppMenus(appCode, orgCode, profCode);
	}

	/*----#end-code----*/
}
