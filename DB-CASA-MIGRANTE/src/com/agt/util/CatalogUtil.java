package com.agt.util;

import java.util.ArrayList;

import com.agt.client.info.CatalogInfo;

public class CatalogUtil {
	
	private ArrayList<CatalogInfo> catalogInfoList;

	public ArrayList<CatalogInfo> getCatalogInfoList() {
		return catalogInfoList;
	}

	public void setCatalogInfoList(ArrayList<CatalogInfo> catalogInfoList) {
		this.catalogInfoList = catalogInfoList;
	}
	
	public void addCatalogInfo(CatalogInfo catalogInfo){
		if(this.catalogInfoList == null){
			this.catalogInfoList = new ArrayList<CatalogInfo>();
			this.catalogInfoList.add(catalogInfo);
		}else{
			this.catalogInfoList.add(catalogInfo);
		}
	}
}
