/**
 * 
 */
package com.agt.client.info;

import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * @author MLOPEZ
 *
 */
@Entity
public class CasoPrevencionInfo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
	private String id;
	private String codigoMunicipal;
	private String fecha;
	private String hora;
	private String actividad;
	private String institucion;
	private String lugar;
	private String objetivo;
	private String presupuesto;
	private String observaciones;
	private String resultados;
	private String niniosM;
	private String niniosF;
	private String adolescentesM;
	private String adolescentesF;
	private String encargadosM;
	private String encargadosF;
	private String tecnicosM;
	private String tecnicosF;
	private String funcionariosM;
	private String funcionariosF;
	private String servidoresM;
	private String servidoresF;
	private String otrosM;
	private String otrosF;
	private String otros;
	private Date recordDate;
	private Date lastUpdate;
	
	
	public CasoPrevencionInfo() {
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getResultados() {
		return resultados;
	}

	public void setResultados(String resultados) {
		this.resultados = resultados;
	}

	public String getNiniosM() {
		return niniosM;
	}

	public void setNiniosM(String niniosM) {
		this.niniosM = niniosM;
	}

	public String getNiniosF() {
		return niniosF;
	}

	public void setNiniosF(String niniosF) {
		this.niniosF = niniosF;
	}

	public String getAdolescentesM() {
		return adolescentesM;
	}

	public void setAdolescentesM(String adolescentesM) {
		this.adolescentesM = adolescentesM;
	}

	public String getAdolescentesF() {
		return adolescentesF;
	}

	public void setAdolescentesF(String adolescentesF) {
		this.adolescentesF = adolescentesF;
	}

	public String getEncargadosM() {
		return encargadosM;
	}

	public void setEncargadosM(String encargadosM) {
		this.encargadosM = encargadosM;
	}

	public String getEncargadosF() {
		return encargadosF;
	}

	public void setEncargadosF(String encargadosF) {
		this.encargadosF = encargadosF;
	}

	public String getTecnicosM() {
		return tecnicosM;
	}

	public void setTecnicosM(String tecnicosM) {
		this.tecnicosM = tecnicosM;
	}

	public String getTecnicosF() {
		return tecnicosF;
	}

	public void setTecnicosF(String tecnicosF) {
		this.tecnicosF = tecnicosF;
	}

	public String getFuncionariosM() {
		return funcionariosM;
	}

	public void setFuncionariosM(String funcionariosM) {
		this.funcionariosM = funcionariosM;
	}

	

	public String getOtrosM() {
		return otrosM;
	}

	public void setOtrosM(String otrosM) {
		this.otrosM = otrosM;
	}

	public String getOtrosF() {
		return otrosF;
	}

	public void setOtrosF(String otrosF) {
		this.otrosF = otrosF;
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

	public String getCodigoMunicipal() {
		return codigoMunicipal;
	}

	public void setCodigoMunicipal(String codigoMunicipal) {
		this.codigoMunicipal = codigoMunicipal;
	}

	public String getServidoresM() {
		return servidoresM;
	}

	public void setServidoresM(String servidoresM) {
		this.servidoresM = servidoresM;
	}

	public String getServidoresF() {
		return servidoresF;
	}

	public void setServidoresF(String servidoresF) {
		this.servidoresF = servidoresF;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getFuncionariosF() {
		return funcionariosF;
	}

	public void setFuncionariosF(String funcionariosF) {
		this.funcionariosF = funcionariosF;
	}

}
