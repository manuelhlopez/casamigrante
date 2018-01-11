/**
 * 
 */
package com.agt.client.gui;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.locks.*;

import com.agt.client.info.CatalogInfo;
import com.agt.util.CatalogUtil;

import au.com.bytecode.opencsv.CSVReader;




public class DashboardGlobalInfo {

	private static int appHeight = 600;
	private static int appWidth = 950;
	private static String currentVersion = "V1";
	/* Configuración para Windows */
	private static String dbPaht = "C:/localdb";//Windows
	private static int fontZize = 9;//Windows;
	private static String system = "windows";
	private static Double fontSpace = 0.2;
	private static ArrayList<CatalogInfo> situacionDesproteccionInfoList = null;
	private static ArrayList<CatalogInfo> situacionDesproteccionSocialInfoList = null;
	private static ArrayList<CatalogInfo> grupoEtnicoInfoList = null;
	private static ArrayList<CatalogInfo> parentescoInfoList = null;
	private static ArrayList<CatalogInfo> areaGeograficaInfoList = null;
	private static ArrayList<CatalogInfo> edadInfoList = null;
	private static ArrayList<CatalogInfo> institucionesInfoList = null;
	private static ArrayList<CatalogInfo> municipiosInfoList = null;
	private static ArrayList<CatalogInfo> departamentosInfoList = null;
	private static ArrayList<CatalogInfo> estadoCasoInfoList = null;
	private static ArrayList<CatalogInfo> paisesInfoList = null;
	private static ArrayList<CatalogInfo> gruposIndigenasInfoList = null;
	private static ArrayList<CatalogInfo> nacionalidadInfoList = null;
	private static ArrayList<String> participantesList = null;
	private static ArrayList<String> tipoActividadList = null;
	
	public static int getAppHeight() {
		return appHeight;
	}
	public static void setAppHeight(int appHeight) {
		DashboardGlobalInfo.appHeight = appHeight;
	}
	public static int getAppWidth() {
		return appWidth;
	}
	public static void setAppWidth(int appWidth) {
		DashboardGlobalInfo.appWidth = appWidth;
	}
	public static String getCurrentVersion() {
		return currentVersion;
	}
	public static void setCurrentVersion(String currentVersion) {
		DashboardGlobalInfo.currentVersion = currentVersion;
	}
	public static String getDbPaht() {
		return dbPaht;
	}
	public static void setDbPaht(String dbPaht) {
		DashboardGlobalInfo.dbPaht = dbPaht;
	}
	public static int getFontZize() {
		return fontZize;
	}
	public static void setFontZize(int fontZize) {
		DashboardGlobalInfo.fontZize = fontZize;
	}
	public static String getSystem() {
		return system;
	}
	public static void setSystem(String system) {
		DashboardGlobalInfo.system = system;
	}
	public static Double getFontSpace() {
		return fontSpace;
	}
	public static void setFontSpace(Double fontSpace) {
		DashboardGlobalInfo.fontSpace = fontSpace;
	}
	public static ArrayList<CatalogInfo> getSituacionDesproteccionInfoList() {
		if(situacionDesproteccionInfoList == null){
			situacionDesproteccionInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("situacion-desproteccion.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				situacionDesproteccionInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return situacionDesproteccionInfoList;
	}
	public static void setSituacionDesproteccionInfoList(ArrayList<CatalogInfo> situacionDesproteccionInfoList) {
		DashboardGlobalInfo.situacionDesproteccionInfoList = situacionDesproteccionInfoList;
	}
	public static ArrayList<CatalogInfo> getGrupoEtnicoInfoList() {
		if(grupoEtnicoInfoList == null){
			grupoEtnicoInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("grupo-etnico.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				grupoEtnicoInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return grupoEtnicoInfoList;
	}
	public static void setGrupoEtnicoInfoList(ArrayList<CatalogInfo> grupoEtnicoInfoList) {
		DashboardGlobalInfo.grupoEtnicoInfoList = grupoEtnicoInfoList;
	}
	public static ArrayList<CatalogInfo> getParentescoInfoList() {
		if(parentescoInfoList == null){
			parentescoInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("parentescos.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}

				parentescoInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return parentescoInfoList;
	}
	public static void setParentescoInfoList(ArrayList<CatalogInfo> parentescoInfoList) {
		DashboardGlobalInfo.parentescoInfoList = parentescoInfoList;
	}
	public static ArrayList<CatalogInfo> getAreaGeograficaInfoList() {
		if(areaGeograficaInfoList == null){
			areaGeograficaInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("area-geografica.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				areaGeograficaInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return areaGeograficaInfoList;
	}
	public static void setAreaGeograficaInfoList(ArrayList<CatalogInfo> areaGeograficaInfoList) {
		DashboardGlobalInfo.areaGeograficaInfoList = areaGeograficaInfoList;
	}
	public static ArrayList<CatalogInfo> getEdadInfoList() {
		if(edadInfoList == null){
			edadInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("edad.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				edadInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return edadInfoList;
	}
	public static void setEdadInfoList(ArrayList<CatalogInfo> edadInfoList) {
		DashboardGlobalInfo.edadInfoList = edadInfoList;
	}
	public static ArrayList<CatalogInfo> getInstitucionesInfoList() {
		if(institucionesInfoList == null){
			institucionesInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("instituciones.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				institucionesInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return institucionesInfoList;
	}
	public static void setInstitucionesInfoList(ArrayList<CatalogInfo> institucionesInfoList) {
		DashboardGlobalInfo.institucionesInfoList = institucionesInfoList;
	}
	public static ArrayList<CatalogInfo> getMunicipiosInfoList() {
		if(municipiosInfoList == null){
			municipiosInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("municipios.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String fk = nextLine[1];
						String value = nextLine[2];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value,fk));
					}
				}
				municipiosInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return municipiosInfoList;
	}

	public static void setMunicipiosInfoList(ArrayList<CatalogInfo> municipiosInfoList) {
		DashboardGlobalInfo.municipiosInfoList = municipiosInfoList;
	}
	public static ArrayList<CatalogInfo> getDepartamentosInfoList() {
		if(departamentosInfoList == null){
			departamentosInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("departamentos.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				departamentosInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return departamentosInfoList;
	}

	// Método útil para filtrar los municipios por departamento...
	public static ArrayList<CatalogInfo> getMunicipioPorDepartamento(String idDepartamento){
		ArrayList<CatalogInfo> municipiosFiltroInfoList = new ArrayList<CatalogInfo>();

		if(municipiosInfoList == null){
			municipiosInfoList = getMunicipiosInfoList();
		}
		for(CatalogInfo catalogInfo : municipiosInfoList){
			if(catalogInfo.getFk().equals(idDepartamento)){
				municipiosFiltroInfoList.add(catalogInfo);
			}	
		}

		return municipiosFiltroInfoList;
	}
	
	// Método útil para encontrar una la sitiación de desprotección por id...
	public static CatalogInfo getSituacionDesproteccionInfo(String id){
		if(situacionDesproteccionInfoList != null){
			for(CatalogInfo info : situacionDesproteccionInfoList){
				if(info.getId().equals(id)){
					return info;
				}	
			}
		}
		return null;
	}
	
	// Método útil para encontrar una la sitiación de desprotección social por id...
	public static CatalogInfo getSituacionDesproteccionSocialInfo(String id){
		if(situacionDesproteccionSocialInfoList != null){
			for(CatalogInfo info : situacionDesproteccionSocialInfoList){
				if(info.getId().equals(id)){
					return info;
				}	
			}
		}
		return null;
	}
		
	

	public static void setDepartamentosInfoList(ArrayList<CatalogInfo> departamentosInfoList) {
		DashboardGlobalInfo.departamentosInfoList = departamentosInfoList;
	}
	
	public static ArrayList<CatalogInfo> getEstadoCasoInfoList() {
		if(estadoCasoInfoList == null){
			estadoCasoInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("estado-caso.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				estadoCasoInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return estadoCasoInfoList;
	}
	
	public static void setEstadoCasoInfoList(ArrayList<CatalogInfo> estadoCasoInfoList) {
		DashboardGlobalInfo.estadoCasoInfoList = estadoCasoInfoList;
	}
	public static ArrayList<CatalogInfo> getPaisesInfoList() {
		if(paisesInfoList == null){
			paisesInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("paises.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				paisesInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return paisesInfoList;
	}
	public static void setPaisesInfoList(ArrayList<CatalogInfo> paisesInfoList) {
		DashboardGlobalInfo.paisesInfoList = paisesInfoList;
	}
	public static ArrayList<CatalogInfo> getGruposIndigenasInfoList() {
		if(gruposIndigenasInfoList == null){
			gruposIndigenasInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("grupos-indigenas.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				gruposIndigenasInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return gruposIndigenasInfoList;
	}
	public static void setGruposIndigenasInfoList(ArrayList<CatalogInfo> gruposIndigenasInfoList) {
		DashboardGlobalInfo.gruposIndigenasInfoList = gruposIndigenasInfoList;
	}
	public static ArrayList<CatalogInfo> getSituacionDesproteccionSocialInfoList() {
		if(situacionDesproteccionSocialInfoList == null){
			situacionDesproteccionSocialInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("situacion-desproteccion-social.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				situacionDesproteccionSocialInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return situacionDesproteccionSocialInfoList;
	}
	public static void setSituacionDesproteccionSocialInfoList(ArrayList<CatalogInfo> situacionDesproteccionSocialInfoList) {
		DashboardGlobalInfo.situacionDesproteccionSocialInfoList = situacionDesproteccionSocialInfoList;
	}
	public static ArrayList<String> getParticipantesList() {
		if(participantesList == null){
			participantesList = new ArrayList<String>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("participantes.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						participantesList.add(id);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return participantesList;
	}
	public static void setParticipantesList(ArrayList<String> participantesList) {
		DashboardGlobalInfo.participantesList = participantesList;
	}
	public static ArrayList<String> getTipoActividadList() {
		if(tipoActividadList == null){
			tipoActividadList = new ArrayList<String>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("tipo-actividad.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						tipoActividadList.add(id);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return tipoActividadList;
	}
	public static void setTipoActividadList(ArrayList<String> tipoActividadList) {
		DashboardGlobalInfo.tipoActividadList = tipoActividadList;
	}
	public static ArrayList<CatalogInfo> getNacionalidadInfoList() {
		if(nacionalidadInfoList == null){
			nacionalidadInfoList = new ArrayList<CatalogInfo>();
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader("nacionalidad.csv"));		       
				//Read CSV line by line and use the string array as you want
				String[] nextLine;
				CatalogUtil catalogUtil = new CatalogUtil();
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						String id = nextLine[0];
						String value = nextLine[1];
						catalogUtil.addCatalogInfo(new CatalogInfo(id,value));
					}
				}
				nacionalidadInfoList.addAll(catalogUtil.getCatalogInfoList());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return nacionalidadInfoList;
	}
	public static void setNacionalidadInfoList(ArrayList<CatalogInfo> nacionalidadInfoList) {
		DashboardGlobalInfo.nacionalidadInfoList = nacionalidadInfoList;
	}

}
