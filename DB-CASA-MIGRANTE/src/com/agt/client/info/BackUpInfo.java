package com.agt.client.info;

import java.util.Date;

public class BackUpInfo implements java.io.Serializable{
	
	public BackUpInfo() {
	
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Date fecha;
	private Long time;
	private String codigo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
