package nosi.webapps.igrp.dao;
/**
 * @author: Emanuel Pereira
 * 29 Jun 2017
 */

import static nosi.core.i18n.Translator.gt;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nosi.core.config.ConfigDBIGRP;
import nosi.core.webapp.Core;

@Entity
@Table(name="tbl_profile_type")
public class ProfileType extends IGRPBaseActiveRecord<ProfileType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 820520902648272514L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false)
	private String descr;
	@Column(nullable=false,unique=true)
	private String code;
	private int status;
	private String plsql_code; 
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="org_fk",foreignKey=@ForeignKey(name="PROFILE_TYPE_ORG_FK"))
	private Organization organization;	

	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="env_fk",foreignKey=@ForeignKey(name="PROFILE_TYPE_ENV_FK"),nullable=false)
	private Application application;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="self_fk",foreignKey=@ForeignKey(name="PROFILE_TYPE_SELF_FK"))
	private ProfileType profiletype;
	@OneToMany(mappedBy="profileType")
	private List<Profile> profiles;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="firstPage",foreignKey=@ForeignKey(name="PROFILE_TYPE_ACTION_FK"))
	private Action firstPage;
	
	public ProfileType(){}
	
	public ProfileType(String descr, String code, int status, Organization organization,
			Application application, ProfileType profiletype) {
		super();
		this.descr = descr;
		this.code = code;
		this.status = status;
		this.organization = organization;
		this.application = application;
		this.profiletype = profiletype;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public ProfileType getProfiletype() {
		return profiletype;
	}

	public void setProfiletype(ProfileType profiletype) {
		this.profiletype = profiletype;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	public String getPlsql_code() {
		return plsql_code;
	}

	public void setPlsql_code(String plsql_code) {
		this.plsql_code = plsql_code;
	}

	public HashMap<String, String> getListMyProfiles() {
		HashMap<String,String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for(Profile p: new Profile().getMyPerfile()){
			if(p.getProfileType().getStatus()==1)
				lista.put(p.getProfileType().getId()+"",p.getOrganization().getName() + " / "+ p.getProfileType().getDescr());
		}
		return lista;
	}

	public HashMap<String, String> getListProfiles() {
		HashMap<String,String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for(ProfileType p: this.find().where("status","=",1).all()){
			lista.put(p.getId()+"", p.getDescr());
		}
		return lista;
	}
	
	public HashMap<String, String> getListProfiles(int app, int organic) {
		HashMap<String,String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for(ProfileType p: this.find().where("status","=",1).andWhere("application.id", "=",app).andWhere("organization.id", "=",organic).all()){
			lista.put(p.getId()+"", p.getDescr());
		}
		return lista;
	}
	
	public HashMap<String, String> getListProfiles4Pai(int app, int organic) {
		HashMap<String,String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for(ProfileType p: this.find().where("status","=",1).andWhere("application.id", "=",app).andWhere("organization.id", "=",organic).andWhere("profiletype", "isnull").all()){
			lista.put(p.getId()+"", p.getDescr());
		}
		return lista;
	}

	//Verifica se é perfil pai
	public static boolean isPerfilPai(){
		String sql = "SELECT count(self_fk) as total FROM tbl_profile_type";
		nosi.core.webapp.databse.helpers.ResultSet.Record r = Core.query(ConfigDBIGRP.FILE_NAME_HIBERNATE_IGRP_CONFIG, sql).where("self_fk", "=",Core.getCurrentProfile()).getSingleRecord();
		return r.getInt("total") > 0;
	}

	@Override
	public String toString() {
		return "ProfileType [id=" + id + ", descr=" + descr + ", code=" + code + ", status=" + status + "]";
	}

	public HashMap<String,String> getListProfiles(Integer app) {
		HashMap<String,String> lista = new HashMap<>();
		lista.put(null, gt("-- Selecionar --"));
		for(ProfileType p: this.find().where("status","=",1).andWhere("application.id", "=",app).all()){
			lista.put(p.getId()+"", p.getDescr());
		}
		return lista;
	}

	public ProfileType findByCode(String code) {
		return this.find().andWhere("code", "=",code).one();
	}

	public ProfileType getProfileAdmin() {
		return this.find().andWhere("code", "=","ALL").andWhere("descr", "=","ALL PROFILE").one();
	}

	public Action getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Action firstPage) {
		this.firstPage = firstPage;
	}
	
}