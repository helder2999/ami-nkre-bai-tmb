package nosi.webapps.igrp.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nosi.core.webapp.Core;

/**
 * Emanuel
 * 27 Mar 2018
 */
@Entity
@Table(name="tbl_domain")
public class Domain extends IGRPBaseActiveRecord<Domain> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false, length=100)
	private String dominio;
	@Column(nullable=false, length=150)
	private String valor;
	@Column(nullable=false, length=250)
	private String description;
	@Column(nullable=false, length=8)
	private String status = "ATIVE";
	@Column(nullable=false, length=2)
	private int ordem = 0;
	@ManyToOne
	@JoinColumn(name = "env_fk", foreignKey = @ForeignKey(name = "DOMAIN_ENV_FK"), nullable = true)
	private Application application;	
	@Enumerated(EnumType.STRING)
	@Column(name="domain_type", length=8,nullable=true)
	private DomainType domainType;
	
	public Domain(String dominio, String valor, String description, String status, int ordem,DomainType domainType,Application application) {
		super();
		this.dominio = dominio;
		this.valor = valor;
		this.description = description;
		this.status = Core.isNotNull(status)?status:"ATIVE";
		this.ordem = ordem;
		this.domainType = domainType;
		this.application = application;
	}

	public Domain() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getordem() {
		return ordem;
	}
	public void setordem(int ordem) {
		this.ordem = ordem;
	}
	
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * Default is Public
	 * @return
	 */
	public DomainType getDomainType() {
		if(Core.isNotNull(this.domainType))
			return domainType;
		return DomainType.PUBLIC;
	}

	public void setDomainType(DomainType domainType) {
		this.domainType = domainType;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + ", dominio=" + dominio + ", valor=" + valor + ", description=" + description
				+ ", status=" + status + ", ordem=" + ordem + "]";
	}
		
}
