package com.agt.client.info;

import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class SettingsInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private Integer idSettings = 1;
	private String code;
	private String nombre;
	private String direccion;
	private String departamento;
	private String departamentoDescr;
	private String municipio;
	private String municipioDescr;
	private String municipioFk;
	private String tecnico;
	private String telefono;
	private String email;
	private String cargo;
	private Date recordDate;
	private Date lastUpdate;
	private String recordUser;
	private String telefonoMunicipalidad;
	private String emailMunicipalidad;
	private String alcalde;
	private String slogan;
	private String periodo;
	
	public SettingsInfo() {

	}

	public Integer getIdSettings() {
		return idSettings;
	}
	public void setIdSettings(Integer idSettings) {
		this.idSettings = idSettings;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getDepartamentoDescr() {
		return departamentoDescr;
	}
	public void setDepartamentoDescr(String departamentoDescr) {
		this.departamentoDescr = departamentoDescr;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipioDescr() {
		return municipioDescr;
	}
	public void setMunicipioDescr(String municipioDescr) {
		this.municipioDescr = municipioDescr;
	}
	public String getTecnico() {
		return tecnico;
	}
	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public String getMunicipioFk() {
		return municipioFk;
	}

	public void setMunicipioFk(String municipioFk) {
		this.municipioFk = municipioFk;
	}

	public String getTelefonoMunicipalidad() {
		return telefonoMunicipalidad;
	}

	public void setTelefonoMunicipalidad(String telefonoMunicipalidad) {
		this.telefonoMunicipalidad = telefonoMunicipalidad;
	}

	public String getEmailMunicipalidad() {
		return emailMunicipalidad;
	}

	public void setEmailMunicipalidad(String emailMunicipalidad) {
		this.emailMunicipalidad = emailMunicipalidad;
	}

	public String getAlcalde() {
		return alcalde;
	}

	public void setAlcalde(String alcalde) {
		this.alcalde = alcalde;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
}
