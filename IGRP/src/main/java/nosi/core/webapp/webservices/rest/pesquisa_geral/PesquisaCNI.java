package nosi.core.webapp.webservices.rest.pesquisa_geral;

import java.io.Serializable;

import com.google.gson.reflect.TypeToken;

import nosi.core.webapp.Core;

/**
 * Isaias.Nunes
 * Jan 8, 2019
 */

public class PesquisaCNI implements Serializable{

	private static final long serialVersionUID = 1L;
	private String NOME_COMPLETO;
	private String SEXO;
	private String DATA_NASC;
	private String NUM_DOCUMENTO;
	private String Error;
	
	public String getNOME_COMPLETO() {
		return NOME_COMPLETO;
	}
	public void setNOME_COMPLETO(String nOME_COMPLETO) {
		NOME_COMPLETO = nOME_COMPLETO;
	}
	public String getSEXO() {
		return SEXO;
	}
	public void setSEXO(String sEXO) {
		SEXO = sEXO;
	}
	public String getDATA_NASC() {
		return DATA_NASC;
	}
	public void setDATA_NASC(String dATA_NASC) {
		DATA_NASC = dATA_NASC;
	}
	public String getNUM_DOCUMENTO() {
		return NUM_DOCUMENTO;
	}
	public void setNUM_DOCUMENTO(String nUM_DOCUMENTO) {
		NUM_DOCUMENTO = nUM_DOCUMENTO;
	}
	
	public String getError() {
		return Error;
	}
	public void setError(String error) {
		Error = error;
	}
	@Override
	public String toString() {
		return "PesquisaCNI [NOME_COMPLETO=" + NOME_COMPLETO + ", SEXO=" + SEXO + ", DATA_NASC=" + DATA_NASC
				+ ", NUM_DOCUMENTO=" + NUM_DOCUMENTO + "]";
	}
	
	public PesquisaCNI getListCNI(String json) {
		return (PesquisaCNI) Core.fromJsonWithJsonBuilder(json, new TypeToken<PesquisaCNI>() {
			private static final long serialVersionUID = 1L;}.getType());
	}
	
}
