package com.agt.client.info;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.*;

@Entity
public class CatalogInfo {
	
	@PrimaryKey
	@KeyField(1) private String id;
	@KeyField(2) private String descr;
	@KeyField(3) private String fk;

	public CatalogInfo(){

	}

	public CatalogInfo(String id, String descr){
		this.id = id;
		this.descr = descr;
	}

	public CatalogInfo(String id, String descr, String fk){
		this.id = id;
		this.descr = descr;
		this.fk = fk;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String toString(){
		return this.descr;
	}

	public String getFk() {
		return fk;
	}

	public void setFk(String fk) {
		this.fk = fk;
	}

}
