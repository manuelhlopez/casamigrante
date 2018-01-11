/**
 * 
 */
package com.agt.client.info;

import java.util.ArrayList;
import java.util.Date;

import com.agt.util.TimeUtil;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;
import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;
/**
 * @author MLOPEZ
 *
 */
@Entity
public class CasoDesproteccionSocialInfo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@PrimaryKey
	private String id = null;
	private String codigoMunicipal;
	@SecondaryKey(relate = MANY_TO_ONE)
	private String ano;
	private String correlativo;
	private String nombre;
	private String edadId;
	private String edadDescr;
	private String lugarResidencia;
	private String sexo;
	private String grupoEtnicoId;
	private String grupoEtnicoDescr;
	private String grupoIndigenaId;
	private String grupoIndigenaDescr;
	private String areaGeograficaId;
	private String areaGeograficaDescr;
	private String otraAreaGeograficaId;
	private String otraAreaGeograficaDescr;
	private String agresor;
	private String fecha;
	private String hora;
	private ArrayList<String> situacionDesproteccionList;
	private String otraSituacionDesproteccion;
	private String parentescoId;
	private String parentescoDescr;
	private String otroParentesco;
	private String institucionId;
	private String institucionDescr;
	private String otroInstitucion;
	private String narracion;
	private Date recordDate;
	private Date lastUpdate;
	private Boolean anonimo;
	private Boolean elMismo;
	private Boolean otro;
	private String nombrePersona;
	private String direccionPersona;
	private String telefonoPersona;
	private String emailPersona;
	private String parentescoPersonaId;
	private String parentescoPersonaDescr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodigoMunicipal() {
		return codigoMunicipal;
	}
	public void setCodigoMunicipal(String codigoMunicipal) {
		this.codigoMunicipal = codigoMunicipal;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(String correlativo) {
		this.correlativo = correlativo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLugarResidencia() {
		return lugarResidencia;
	}
	public void setLugarResidencia(String lugarResidencia) {
		this.lugarResidencia = lugarResidencia;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getGrupoEtnicoId() {
		return grupoEtnicoId;
	}
	public void setGrupoEtnicoId(String grupoEtnicoId) {
		this.grupoEtnicoId = grupoEtnicoId;
	}
	public String getGrupoEtnicoDescr() {
		return grupoEtnicoDescr;
	}
	public void setGrupoEtnicoDescr(String grupoEtnicoDescr) {
		this.grupoEtnicoDescr = grupoEtnicoDescr;
	}
	public String getAreaGeograficaId() {
		return areaGeograficaId;
	}
	public void setAreaGeograficaId(String areaGeograficaId) {
		this.areaGeograficaId = areaGeograficaId;
	}
	public String getAreaGeograficaDescr() {
		return areaGeograficaDescr;
	}
	public void setAreaGeograficaDescr(String areaGeograficaDescr) {
		this.areaGeograficaDescr = areaGeograficaDescr;
	}
	public String getAgresor() {
		return agresor;
	}
	public void setAgresor(String agresor) {
		this.agresor = agresor;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public ArrayList<String> getSituacionDesproteccionList() {
		return situacionDesproteccionList;
	}
	public void setSituacionDesproteccionList(ArrayList<String> situacionDesproteccionList) {
		this.situacionDesproteccionList = situacionDesproteccionList;
	}
	public String getOtraSituacionDesproteccion() {
		return otraSituacionDesproteccion;
	}
	public void setOtraSituacionDesproteccion(String otraSituacionDesproteccion) {
		this.otraSituacionDesproteccion = otraSituacionDesproteccion;
	}
	public String getParentescoId() {
		return parentescoId;
	}
	public void setParentescoId(String parentescoId) {
		this.parentescoId = parentescoId;
	}
	public String getParentescoDescr() {
		return parentescoDescr;
	}
	public void setParentescoDescr(String parentescoDescr) {
		this.parentescoDescr = parentescoDescr;
	}
	public String getOtroParentesco() {
		return otroParentesco;
	}
	public void setOtroParentesco(String otroParentesco) {
		this.otroParentesco = otroParentesco;
	}
	public String getInstitucionId() {
		return institucionId;
	}
	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}
	public String getInstitucionDescr() {
		return institucionDescr;
	}
	public void setInstitucionDescr(String institucionDescr) {
		this.institucionDescr = institucionDescr;
	}
	public String getOtroInstitucion() {
		return otroInstitucion;
	}
	public void setOtroInstitucion(String otroInstitucion) {
		this.otroInstitucion = otroInstitucion;
	}
	public String getNarracion() {
		return narracion;
	}
	public void setNarracion(String narracion) {
		this.narracion = narracion;
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
	
	public String getEdadId() {
		return edadId;
	}
	public void setEdadId(String edadId) {
		this.edadId = edadId;
	}
	public String getEdadDescr() {
		return edadDescr;
	}
	public void setEdadDescr(String edadDescr) {
		this.edadDescr = edadDescr;
	}
	public String getFechaRegistroFormato(){
		return this.fecha +" "+this.hora;
	}
	public String getNumeroCaso(){
		return this.codigoMunicipal +"-"+this.ano+"-"+this.correlativo;
	}
	public String getOtraAreaGeograficaDescr() {
		return otraAreaGeograficaDescr;
	}
	public void setOtraAreaGeograficaDescr(String otraAreaGeograficaDescr) {
		this.otraAreaGeograficaDescr = otraAreaGeograficaDescr;
	}
	public String getOtraAreaGeograficaId() {
		return otraAreaGeograficaId;
	}
	public void setOtraAreaGeograficaId(String otraAreaGeograficaId) {
		this.otraAreaGeograficaId = otraAreaGeograficaId;
	}
	public String getGrupoIndigenaId() {
		return grupoIndigenaId;
	}
	public void setGrupoIndigenaId(String grupoIndigenaId) {
		this.grupoIndigenaId = grupoIndigenaId;
	}
	public String getGrupoIndigenaDescr() {
		return grupoIndigenaDescr;
	}
	public void setGrupoIndigenaDescr(String grupoIndigenaDescr) {
		this.grupoIndigenaDescr = grupoIndigenaDescr;
	}
	public Boolean getAnonimo() {
		return anonimo;
	}
	public void setAnonimo(Boolean anonimo) {
		this.anonimo = anonimo;
	}
	public Boolean getElMismo() {
		return elMismo;
	}
	public void setElMismo(Boolean elMismo) {
		this.elMismo = elMismo;
	}
	public Boolean getOtro() {
		return otro;
	}
	public void setOtro(Boolean otro) {
		this.otro = otro;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	public String getDireccionPersona() {
		return direccionPersona;
	}
	public void setDireccionPersona(String direccionPersona) {
		this.direccionPersona = direccionPersona;
	}
	public String getTelefonoPersona() {
		return telefonoPersona;
	}
	public void setTelefonoPersona(String telefonoPersona) {
		this.telefonoPersona = telefonoPersona;
	}
	public String getEmailPersona() {
		return emailPersona;
	}
	public void setEmailPersona(String emailPersona) {
		this.emailPersona = emailPersona;
	}
	public String getParentescoPersonaId() {
		return parentescoPersonaId;
	}
	public void setParentescoPersonaId(String parentescoPersonaId) {
		this.parentescoPersonaId = parentescoPersonaId;
	}
	public String getParentescoPersonaDescr() {
		return parentescoPersonaDescr;
	}
	public void setParentescoPersonaDescr(String parentescoPersonaDescr) {
		this.parentescoPersonaDescr = parentescoPersonaDescr;
	}
	
}
