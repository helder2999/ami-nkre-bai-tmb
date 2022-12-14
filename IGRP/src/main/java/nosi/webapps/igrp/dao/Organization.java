package nosi.webapps.igrp.dao;
/**
 * @author: Emanuel Pereira
 * 29 Jun 2017
 */

import static nosi.core.i18n.Translator.gt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nosi.core.webapp.Core;
import nosi.core.webapp.databse.helpers.ResultSet;

@Entity
@Table(name = "tbl_organization")
public class Organization extends IGRPBaseActiveRecord<Organization> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3697544500624399720L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String code;
	@Column(nullable = false)
	private String name;
	private int status;
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "env_fk", foreignKey = @ForeignKey(name = "ORGANIZATION_ENV_FK"), nullable = false)
	private Application application;
	@ManyToOne
	@JoinColumn(name = "user_created_fk", foreignKey = @ForeignKey(name = "ORGANIZATION_USER_FK"))
	private User user;
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "self_fk", foreignKey = @ForeignKey(name = "ORGANIZATION_SELF_FK"), nullable = true)
	private Organization organization;
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization",fetch=FetchType.EAGER)
	private List<ProfileType> profilesType;
	@OneToMany(mappedBy = "organization",fetch=FetchType.EAGER)
	private Set<Profile> profiles;
	
	private String plsql_code; 
	
	public Organization() {
		super();
	}

	public Organization(String code, String name, int status, Application application, User user,
			Organization organization) {
		super();
		this.code = code;
		this.name = name;
		this.status = status;
		this.application = application;
		this.user = user;
		this.organization = organization;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<ProfileType> getProfilesType() {
		return profilesType;
	}

	public void setProfilesType(List<ProfileType> profilesType) {
		this.profilesType = profilesType;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}
	
	public String getPlsql_code() {
		return plsql_code;
	}

	public void setPlsql_code(String plsql_code) {
		this.plsql_code = plsql_code;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", code=" + code + ", name=" + name + ", status=" + status + "]";
	}

	public Map<String, String> getListMyOrganizations() {
		Map<String, String> lista = new HashMap<>();
		lista.put("", "-- Selecionar --");
		for (Profile p : new Profile().getMyPerfile()) {
			if(p.getOrganization().getStatus()==1)
				lista.put(p.getOrganization().getId() + "", p.getOrganization().getName());
		}
		return lista;
	}

	public Map<String, String> getListOrganizations() {
		Map<String, String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for (Map<String, Object> o : this.find()
				.where("status","=",1)				
				.allColumns("id","name")) {
			lista.put(o.get("id")+"",""+ o.get("name"));
		}
		return lista;
	}

	public  Map<String, String> getListOrganizations(Integer app) {
		 Map<String, String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for (Map<String, Object> o : this.find()
				.where("status","=",1)
				.andWhere("application.id", "=", app)				
				.allColumns("id","name")) {
			lista.put(o.get("id")+"",""+ o.get("name"));
		}
		return lista;
	}

	public List<Menu> getOrgMenu(Integer appId,Integer orgId) {		
			List<Menu> myMenu = new ArrayList<>();			
			//First shows all the app pages than all the public pages in the menu
			String sqlMenuByApp = "SELECT m.id,m.descr,m.flg_base,(CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='MEN' AND org_fk=:org_fk AND type_fk=m.id) then 1 else 0 END) as isInserted  FROM tbl_menu m WHERE (m.action_fk is not null or link is not null) AND m.status=1 AND m.env_fk=:env_fk";
			String sqlPublicMenu = "SELECT m.id,m.descr,m.flg_base,(CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='MEN' AND org_fk=:org_fk AND type_fk=m.id) then 1 else 0 END) as isInserted FROM tbl_menu m WHERE (m.action_fk is not null or link is not null) AND m.status=1 AND m.env_fk<>:env_fk AND m.flg_base=1";
			ResultSet.Record recorde = Core.query(this.getConnectionName(),sqlMenuByApp)
										  .union()
										  .select(sqlPublicMenu)
										  .addInt("org_fk", orgId)
										  .addInt("org_fk", orgId)
										  .addInt("env_fk", appId)
										  .addInt("env_fk", appId)
										  .getRecordList();
			recorde.RowList.forEach(row->{
				Menu m = new Menu();					
				m.setDescr(row.getString("descr"));
				m.setId(row.getInt("id"));
				m.setFlg_base(row.getInt("flg_base"));
				m.setInserted(row.getInt("isInserted")==1);
				myMenu.add(m);
			});
			return myMenu;
	}


	public List<Menu> getPerfilMenu(Integer orgId,Integer profId) {
		List<Menu> myMenu = new ArrayList<>();			
		//First shows all the app pages than all the public pages in the menu
		String sqlMenuByOrg = " SELECT m.id,m.descr,m.flg_base,(CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='MEN' AND org_fk=:org_fk AND prof_type_fk=:prof_fk AND type_fk=m.id) then 1 else 0 END) as isInserted  "
							+ " FROM tbl_menu m INNER JOIN tbl_profile p ON p.type_fk=m.id AND p.type='MEN' AND p.org_fk=:org_fk"
							+ " RIGHT JOIN tbl_profile_type pt ON pt.id=p.prof_type_fk AND pt.code='ALL' AND pt.descr='ALL PROFILE' "
							+ " WHERE (m.action_fk is not null or link is not null) AND m.status=1";
		ResultSet.Record record = Core.query(this.getConnectionName(),sqlMenuByOrg)
									  .addInt("org_fk", orgId)
									  .addInt("prof_fk", profId)
									  .addInt("org_fk", orgId)
									  .getRecordList();
		record.RowList.forEach(row->{
			Menu m = new Menu();					
			m.setDescr(row.getString("descr"));
			m.setId(row.getInt("id"));
			m.setFlg_base(row.getInt("flg_base"));
			m.setInserted(row.getInt("isInserted")==1);
			myMenu.add(m);
		});
		return myMenu;
	}

	public List<Transaction> getOrgTransaction(Integer appId,Integer orgId) {
		List<Transaction> transactions = new ArrayList<>();
		String sqlTransationByApp = " SELECT t.id,t.code,t.descr, t.env_fk, (CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='TRANS' AND org_fk=:org_fk AND type_fk=t.id) then 1 else 0 END) as isInserted  "
				+ " FROM tbl_transaction t"
				+ " WHERE t.env_fk=:env_fk AND t.status=1";
		ResultSet.Record recorde = Core.query(this.getConnectionName(),sqlTransationByApp)
								  .addInt("org_fk", orgId)
								  .addInt("env_fk", appId)
								  .getRecordList();
		recorde.RowList.forEach(row->{
			Transaction t = new Transaction();
			t.setId(row.getInt("id"));
			t.setCode(row.getString("code"));
			t.setDescr(row.getString("descr"));
			t.setInserted(row.getInt("isInserted")==1);
			Application app = new Application();
			app.setId(row.getInt("env_fk"));
			t.setApplication(app);
			transactions.add(t );
		});
		return transactions ;
	}

	public List<Transaction> getPerfilTransaction(Integer orgId,Integer profId) {
		String sqlTransationByOrg = " SELECT t.id,t.code,t.descr, t.env_fk, (CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='TRANS' AND org_fk=:org_fk AND prof_type_fk=:prof_fk AND type_fk=t.id) then 1 else 0 END) as isInserted  "
								+ " FROM tbl_transaction t INNER JOIN tbl_profile p ON p.type_fk=t.id AND p.type='TRANS' AND p.org_fk=:org_fk"
								+ " RIGHT JOIN tbl_profile_type pt ON pt.id=p.prof_type_fk AND pt.code='ALL' AND pt.descr='ALL PROFILE' "
								+ " WHERE t.status=1";
		ResultSet.Record recorde = Core.query(this.getConnectionName(),sqlTransationByOrg)
				  .addInt("org_fk", orgId)
				  .addInt("prof_fk", profId)
				  .addInt("org_fk", orgId)
				  .getRecordList();
		List<Transaction> transactions = new ArrayList<>();
		recorde.RowList.forEach(row->{
			Transaction t = new Transaction();
			t.setId(row.getInt("id"));
			t.setCode(row.getString("code"));
			t.setDescr(row.getString("descr"));
			t.setInserted(row.getInt("isInserted")==1);
			Application app = new Application();
			app.setId(row.getInt("env_fk"));
			t.setApplication(app);
			
			transactions.add(t );
		});
		return transactions;
	}

	public Organization findByCode(String code) {
		return this.find().andWhere("code", "=",code).one();
	}

	public List<Menu> getOrgMenuByUser(Integer orgId, Integer userId) {
		List<Menu> myMenu = new ArrayList<>();			
		//First shows all the app pages than all the public pages in the menu
		String sqlMenuByOrg = " SELECT m.id,m.descr,m.flg_base,(CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='MEN_USER' AND org_fk=:org_fk AND user_fk=:user_fk AND type_fk=m.id) then 1 else 0 END) as isInserted  "
							+ " FROM tbl_menu m INNER JOIN tbl_profile p ON p.type_fk=m.id AND p.type='MEN' AND p.org_fk=:org_fk"
							+ " RIGHT JOIN tbl_profile_type pt ON pt.id=p.prof_type_fk AND pt.code='ALL' AND pt.descr='ALL PROFILE' "
							+ " WHERE (m.action_fk is not null or link is not null) AND m.status=1";
		ResultSet.Record recorde = Core.query(this.getConnectionName(),sqlMenuByOrg)
									  .addInt("org_fk", orgId)
									  .addInt("user_fk", userId)
									  .addInt("org_fk", orgId)
									  .getRecordList();
		recorde.RowList.forEach(row->{
			Menu m = new Menu();					
			m.setDescr(row.getString("descr"));
			m.setId(row.getInt("id"));
			m.setFlg_base(row.getInt("flg_base"));
			m.setInserted(row.getInt("isInserted")==1);
			myMenu.add(m);
		});
		return myMenu;
	}

	public List<Transaction> getOrgTransactionByUser(Integer orgId, Integer userId) {
		List<Transaction> transactions = new ArrayList<>();
		String sqlTransationByOrg = " SELECT t.id,t.code,t.descr, t.env_fk, (CASE WHEN EXISTS (SELECT type_fk from tbl_profile where type='TRANS_USER' AND org_fk=:org_fk AND user_fk=:user_fk AND type_fk=t.id) then 1 else 0 END) as isInserted  "
				+ " FROM tbl_transaction t INNER JOIN tbl_profile p ON p.type_fk=t.id AND p.type='TRANS' AND p.org_fk=:org_fk"
				+ " RIGHT JOIN tbl_profile_type pt ON pt.id=p.prof_type_fk AND pt.code='ALL' AND pt.descr='ALL PROFILE' "
				+ " WHERE t.status=1";
		ResultSet.Record recorde = Core.query(this.getConnectionName(),sqlTransationByOrg)
								  .addInt("org_fk", orgId)
								  .addInt("user_fk", userId)
								  .addInt("org_fk", orgId)
								  .getRecordList();
		recorde.RowList.forEach(row->{
			Transaction t = new Transaction();
			t.setId(row.getInt("id"));
			t.setCode(row.getString("code"));
			t.setDescr(row.getString("descr"));
			t.setInserted(row.getInt("isInserted")==1); 
			Application app = new Application();
			app.setId(row.getInt("env_fk"));
			t.setApplication(app);
			
			transactions.add(t );
		});
		return transactions ;
	}
}