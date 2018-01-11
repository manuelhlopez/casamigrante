package com.agt.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.agt.client.db.CasoDesproteccionDbActions;
import com.agt.client.db.CasoDesproteccionSocialDbActions;
import com.agt.client.db.CasoPrevencionDbActions;
import com.agt.client.db.SeguimientoCasoDbActions;
import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.BackUpInfo;
import com.agt.client.info.CasoDesproteccionInfo;
import com.agt.client.info.CasoDesproteccionSocialInfo;
import com.agt.client.info.CasoPrevencionInfo;
import com.agt.client.info.SeguimientoCasoInfo;
import com.agt.client.info.SettingsInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ExcelUtil {

	//private String FILE_NAME = "C:/Users/MLOPEZ/Desktop/dashboard.xlsx";



	public static void main(String[] args) throws IOException, SQLException {
		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.generateDashboard("C:/Users/MLOPEZ/Desktop/dashboard.xlsx");

	}

	public static void generateDashboard(String path) throws SQLException, IOException{
		//XSSFWorkbook workbook = new XSSFWorkbook();
		// XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		InputStream inputStream = ExcelUtil.class.getResourceAsStream("/com/agt/util/Dashboard.xlsx");

		//FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		// XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		Sheet sheet = workbook.getSheetAt(0);

		Object[][] data = {};
		SettingsDbActions settingsDbActions = new SettingsDbActions();
		SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
		// Con base al codigo municipal obtengo el ultimo backup...
		int rowNum = 1;

		String codigoMunicipal = settingsInfo.getCode();
		CasoDesproteccionDbActions casoDesproteccionDbActions = new CasoDesproteccionDbActions();
		ArrayList<CasoDesproteccionInfo> casoDesproteccionInfoList = casoDesproteccionDbActions.getAllCases();
		//Agregamos la informacion de los casos al arreglo de datos...
		int colNum = 0;
		if(casoDesproteccionInfoList != null){
			for(CasoDesproteccionInfo casoInfo : casoDesproteccionInfoList){
				//Correlativo...
				Row row = sheet.createRow(rowNum++);
				String correlativo = casoInfo.getNumeroCaso();
				Cell correlativoCell = row.createCell(0);
				correlativoCell.setCellValue((String) correlativo);
				//Mes
				String fecha = casoInfo.getFecha();
				Date fechaDate = TimeUtil.getDateFormat(fecha);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaDate);
				int month = cal.get(Calendar.MONTH) + 1;


				Cell cellMonth = row.createCell(1);
				cellMonth.setCellValue((Integer) month);
				//Mes
				Cell cellMonth2 = row.createCell(2);
				cellMonth2.setCellValue((Integer) month);
				//Dia
				int day = cal.get(Calendar.DAY_OF_MONTH);
				Cell cellDay = row.createCell(3);
				cellDay.setCellValue((Integer) day);
				//Anio
				int year = cal.get(Calendar.YEAR);
				Cell cellYear = row.createCell(4);
				cellYear.setCellValue((Integer) year);
				//Departamento
				String departamento = settingsInfo.getDepartamento();
				Cell cellDepto = row.createCell(5);
				cellDepto.setCellValue((String) departamento);
				//Departamento
				Cell cellDepto2 = row.createCell(6);
				cellDepto2.setCellValue((String) departamento);
				//Municipio
				String municipio = settingsInfo.getMunicipio();
				Cell cellMupio = row.createCell(7);
				cellMupio.setCellValue((String) municipio);
				//Municipio
				Cell cellMupio2 = row.createCell(8);
				cellMupio2.setCellValue((String) municipio);
				//Institucion
				String institucion = casoInfo.getInstitucionDescr();
				Cell cellInst = row.createCell(9);
				cellInst.setCellValue((String) institucion);
				//Institucion
				Cell cellInst2 = row.createCell(10);
				cellInst2.setCellValue((String) institucion);
				//Parentesco
				String parentesco = casoInfo.getParentescoDescr();
				Cell cellParentesco= row.createCell(11);
				cellParentesco.setCellValue((String) parentesco);
				//Parentesco
				Cell cellParentesco2 = row.createCell(12);
				cellParentesco2.setCellValue((String) parentesco);
				//Area geografica
				String areaGeografica = casoInfo.getAreaGeograficaDescr();
				Cell cellAreaGeografica = row.createCell(13);
				cellAreaGeografica.setCellValue((String) areaGeografica);
				//Orden edad
				String idEdad = casoInfo.getEdadId();
				Cell cellOrdenEdad = row.createCell(14);
				cellOrdenEdad.setCellValue((Integer) Integer.valueOf(idEdad));
				//Edad
				Cell cellIdEdad = row.createCell(15);
				cellIdEdad.setCellValue((Integer) Integer.valueOf(idEdad));
				//Edad
				String edad = casoInfo.getEdadDescr();
				Cell cellEdad = row.createCell(16);
				cellEdad.setCellValue((String) edad);
				//Rango edad
				Cell cellRangoEdad = row.createCell(17);
				String[] rangoEdad1 = {"1","2","3","4","5"};//Menor a 6 meses
				String[] rangoEdad2 = {"6","7","8","9","10","11"};//De 6 meses a 1 anio
				String[] rangoEdad3 = {"12","13","14"};//De 1 a 3 anios
				String[] rangoEdad4 = {"15","16","17"};//De 4 a 6 anios
				String[] rangoEdad5 = {"18","19","20"};//De 7 a 9 anios
				String[] rangoEdad6 = {"21","22","23"};//De 10 a 12 anios
				String[] rangoEdad7 = {"24","25","26"};//De 13 a 15 anios
				String[] rangoEdad8 = {"27","28","29"};//De 16 a 18 anios

				if(Arrays.asList(rangoEdad1).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "Menor a 6 meses");
				}
				if(Arrays.asList(rangoEdad2).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 6 meses a 1 año");
				}
				if(Arrays.asList(rangoEdad3).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 1 a 3 años");
				}
				if(Arrays.asList(rangoEdad4).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 4 a 6 años");
				}
				if(Arrays.asList(rangoEdad5).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 7 a 9 años");
				}
				if(Arrays.asList(rangoEdad6).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 10 a 12 años");
				}
				if(Arrays.asList(rangoEdad7).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 13 a 15 años");
				}
				if(Arrays.asList(rangoEdad8).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 16 a 18 años");
				}

				//Estado caso
				Cell cellEstado = row.createCell(18);
				cellEstado.setCellValue((String) "Denuncia");
				//Grupo etnico
				String grupoEtnico = casoInfo.getGrupoEtnicoDescr();
				Cell cellGrupoEtnico = row.createCell(19);
				cellGrupoEtnico.setCellValue((String) grupoEtnico);
				//Etnia
				String etnia = casoInfo.getGrupoIndigenaDescr();
				Cell cellEtnia = row.createCell(20);
				cellEtnia.setCellValue((String) etnia);
				//Pais
				String pais = casoInfo.getOtraAreaGeograficaDescr();
				Cell cellPais = row.createCell(21);
				if(pais != null){
					cellPais.setCellValue((String) pais);
				}else{
					cellPais.setCellValue((String) "Guatemala");
				}
				//Sexo
				String sexo = casoInfo.getSexo();
				Cell cellSexo = row.createCell(22);
				if(sexo.equals("M")){
					cellSexo.setCellValue((String) "Masculino");
				}

				if(sexo.equals("F")){
					cellSexo.setCellValue((String) "Femenino");
				}
				//Estado
				SeguimientoCasoDbActions seguimientoCasoDbActions = new SeguimientoCasoDbActions();
				ArrayList<SeguimientoCasoInfo> estadosList = seguimientoCasoDbActions.getEventosSeguimiento(casoInfo.getId());
				Cell cellEstadoCaso = row.createCell(23);
				if(estadosList == null){
					cellEstadoCaso.setCellValue((String) "Denuncia");
				}else{
					for(SeguimientoCasoInfo info : estadosList){
						cellEstadoCaso.setCellValue((String) info.getEstado());
					}

				}

				ArrayList<String> situacionList = casoInfo.getSituacionDesproteccionList();
				//2,En conflicto con la ley penal
				Cell celli2 = row.createCell(24);
				celli2.setCellValue((Integer) isSelectedSituacion(situacionList, "2"));
				//3,Agresión sexual
				Cell celli3 = row.createCell(25);
				celli3.setCellValue((Integer) isSelectedSituacion(situacionList, "3"));
				//4,Alcoholísmo en NA
				Cell celli4 = row.createCell(26);
				celli4.setCellValue((Integer) isSelectedSituacion(situacionList, "4"));
				//5,Discapacidad en NA
				Cell celli5 = row.createCell(27);
				celli5.setCellValue((Integer) isSelectedSituacion(situacionList, "5"));
				//6,Embarazos en NA
				Cell celli6 = row.createCell(28);
				celli6.setCellValue((Integer) isSelectedSituacion(situacionList, "6"));
				//7,Matrimonio infantil y uniones de hecho
				Cell celli7 = row.createCell(29);
				celli7.setCellValue((Integer) isSelectedSituacion(situacionList, "7"));
				//8,NA en situación de emergencia
				Cell celli8 = row.createCell(30);
				celli8.setCellValue((Integer) isSelectedSituacion(situacionList, "8"));
				//9,NA sin cuidado de sus progenitores
				Cell celli9 = row.createCell(31);
				celli9.setCellValue((Integer) isSelectedSituacion(situacionList, "9"));
				//10,NA víctima de trata de personas
				Cell celli10 = row.createCell(32);
				celli10.setCellValue((Integer) isSelectedSituacion(situacionList, "10"));
				//11,NA víctima de violencia armada
				Cell celli11 = row.createCell(33);
				celli11.setCellValue((Integer) isSelectedSituacion(situacionList, "11"));
				//12,NA con VIH/SIDA
				Cell celli12 = row.createCell(34);
				celli12.setCellValue((Integer) isSelectedSituacion(situacionList, "12"));
				//13,NA desaparecida
				Cell celli13 = row.createCell(35);
				celli13.setCellValue((Integer) isSelectedSituacion(situacionList, "13"));
				//14,NA migrantes
				Cell celli14 = row.createCell(36);
				celli14.setCellValue((Integer) isSelectedSituacion(situacionList, "14"));
				//15,Sub registro de nacimientos
				Cell celli15 = row.createCell(37);
				celli15.setCellValue((Integer) isSelectedSituacion(situacionList, "15"));
				//16,Suicidio en NA
				Cell celli16 = row.createCell(38);
				celli16.setCellValue((Integer) isSelectedSituacion(situacionList, "16"));
				//17,Trabajo infantil
				Cell celli17 = row.createCell(39);
				celli17.setCellValue((Integer) isSelectedSituacion(situacionList, "17"));
				//18,Violencia física
				Cell celli18 = row.createCell(40);
				celli18.setCellValue((Integer) isSelectedSituacion(situacionList, "18"));
				//19,Violencia psicológica
				Cell celli19 = row.createCell(41);
				celli19.setCellValue((Integer) isSelectedSituacion(situacionList, "19"));
				//20,Violación sexual
				Cell celli20 = row.createCell(42);
				celli20.setCellValue((Integer) isSelectedSituacion(situacionList, "20"));
				//21,Otros
				Cell celli21 = row.createCell(43);
				celli21.setCellValue((Integer) isSelectedSituacion(situacionList, "21"));
				//1,Acoso escolar
				Cell celli1 = row.createCell(44);
				celli1.setCellValue((Integer) isSelectedSituacion(situacionList, "1"));
			}

		}

		System.out.println("Creating excel");

		try {
			FileOutputStream outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	public static void generateDashboardSocial(String path) throws SQLException, IOException{
		//XSSFWorkbook workbook = new XSSFWorkbook();
		// XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		InputStream inputStream = ExcelUtil.class.getResourceAsStream("/com/agt/util/Dashboard2.xlsx");

		//FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		// XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		Sheet sheet = workbook.getSheetAt(0);

		Object[][] data = {};
		SettingsDbActions settingsDbActions = new SettingsDbActions();
		SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
		// Con base al codigo municipal obtengo el ultimo backup...
		int rowNum = 1;

		String codigoMunicipal = settingsInfo.getCode();
		CasoDesproteccionSocialDbActions casoDesproteccionDbActions = new CasoDesproteccionSocialDbActions();
		ArrayList<CasoDesproteccionSocialInfo> casoDesproteccionInfoList = casoDesproteccionDbActions.getAllCases();
		//Agregamos la informacion de los casos al arreglo de datos...
		int colNum = 0;
		if(casoDesproteccionInfoList != null){
			for(CasoDesproteccionSocialInfo casoInfo : casoDesproteccionInfoList){
				//Correlativo...
				Row row = sheet.createRow(rowNum++);
				String correlativo = casoInfo.getNumeroCaso();
				Cell correlativoCell = row.createCell(0);
				correlativoCell.setCellValue((String) correlativo);
				//Mes
				String fecha = casoInfo.getFecha();
				Date fechaDate = TimeUtil.getDateFormat(fecha);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaDate);
				int month = cal.get(Calendar.MONTH) + 1;


				Cell cellMonth = row.createCell(1);
				cellMonth.setCellValue((Integer) month);
				//Mes
				Cell cellMonth2 = row.createCell(2);
				cellMonth2.setCellValue((Integer) month);
				//Dia
				int day = cal.get(Calendar.DAY_OF_MONTH);
				Cell cellDay = row.createCell(3);
				cellDay.setCellValue((Integer) day);
				//Anio
				int year = cal.get(Calendar.YEAR);
				Cell cellYear = row.createCell(4);
				cellYear.setCellValue((Integer) year);
				//Departamento
				String departamento = settingsInfo.getDepartamento();
				Cell cellDepto = row.createCell(5);
				cellDepto.setCellValue((String) departamento);
				//Departamento
				Cell cellDepto2 = row.createCell(6);
				cellDepto2.setCellValue((String) departamento);
				//Municipio
				String municipio = settingsInfo.getMunicipio();
				Cell cellMupio = row.createCell(7);
				cellMupio.setCellValue((String) municipio);
				//Municipio
				Cell cellMupio2 = row.createCell(8);
				cellMupio2.setCellValue((String) municipio);
				//Institucion
				String institucion = casoInfo.getInstitucionDescr();
				Cell cellInst = row.createCell(9);
				cellInst.setCellValue((String) institucion);
				//Institucion
				Cell cellInst2 = row.createCell(10);
				cellInst2.setCellValue((String) institucion);
				//Parentesco
				String parentesco = casoInfo.getParentescoDescr();
				Cell cellParentesco= row.createCell(11);
				cellParentesco.setCellValue((String) parentesco);
				//Parentesco
				Cell cellParentesco2 = row.createCell(12);
				cellParentesco2.setCellValue((String) parentesco);
				//Area geografica
				String areaGeografica = casoInfo.getAreaGeograficaDescr();
				Cell cellAreaGeografica = row.createCell(13);
				cellAreaGeografica.setCellValue((String) areaGeografica);
				//Orden edad
				String idEdad = casoInfo.getEdadId();
				Cell cellOrdenEdad = row.createCell(14);
				cellOrdenEdad.setCellValue((Integer) Integer.valueOf(idEdad));
				//Edad
				Cell cellIdEdad = row.createCell(15);
				cellIdEdad.setCellValue((Integer) Integer.valueOf(idEdad));
				//Edad
				String edad = casoInfo.getEdadDescr();
				Cell cellEdad = row.createCell(16);
				cellEdad.setCellValue((String) edad);
				//Rango edad
				Cell cellRangoEdad = row.createCell(17);
				String[] rangoEdad1 = {"1","2","3","4","5"};//Menor a 6 meses
				String[] rangoEdad2 = {"6","7","8","9","10","11"};//De 6 meses a 1 anio
				String[] rangoEdad3 = {"12","13","14"};//De 1 a 3 anios
				String[] rangoEdad4 = {"15","16","17"};//De 4 a 6 anios
				String[] rangoEdad5 = {"18","19","20"};//De 7 a 9 anios
				String[] rangoEdad6 = {"21","22","23"};//De 10 a 12 anios
				String[] rangoEdad7 = {"24","25","26"};//De 13 a 15 anios
				String[] rangoEdad8 = {"27","28","29"};//De 16 a 18 anios

				if(Arrays.asList(rangoEdad1).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "Menor a 6 meses");
				}
				if(Arrays.asList(rangoEdad2).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 6 meses a 1 año");
				}
				if(Arrays.asList(rangoEdad3).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 1 a 3 años");
				}
				if(Arrays.asList(rangoEdad4).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 4 a 6 años");
				}
				if(Arrays.asList(rangoEdad5).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 7 a 9 años");
				}
				if(Arrays.asList(rangoEdad6).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 10 a 12 años");
				}
				if(Arrays.asList(rangoEdad7).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 13 a 15 años");
				}
				if(Arrays.asList(rangoEdad8).contains(idEdad)){
					cellRangoEdad.setCellValue((String) "De 16 a 18 años");
				}

				//Estado caso
				Cell cellEstado = row.createCell(18);
				cellEstado.setCellValue((String) "Denuncia");
				//Grupo etnico
				String grupoEtnico = casoInfo.getGrupoEtnicoDescr();
				Cell cellGrupoEtnico = row.createCell(19);
				cellGrupoEtnico.setCellValue((String) grupoEtnico);
				//Etnia
				String etnia = casoInfo.getGrupoIndigenaDescr();
				Cell cellEtnia = row.createCell(20);
				cellEtnia.setCellValue((String) etnia);
				//Pais
				String pais = casoInfo.getOtraAreaGeograficaDescr();
				Cell cellPais = row.createCell(21);
				if(pais != null){
					cellPais.setCellValue((String) pais);
				}else{
					cellPais.setCellValue((String) "Guatemala");
				}
				//Sexo
				String sexo = casoInfo.getSexo();
				Cell cellSexo = row.createCell(22);
				if(sexo.equals("M")){
					cellSexo.setCellValue((String) "Masculino");
				}

				if(sexo.equals("F")){
					cellSexo.setCellValue((String) "Femenino");
				}
				//Estado
				SeguimientoCasoDbActions seguimientoCasoDbActions = new SeguimientoCasoDbActions();
				ArrayList<SeguimientoCasoInfo> estadosList = seguimientoCasoDbActions.getEventosSeguimiento(casoInfo.getId());
				Cell cellEstadoCaso = row.createCell(23);
				if(estadosList == null){
					cellEstadoCaso.setCellValue((String) "Denuncia");
				}else{
					for(SeguimientoCasoInfo info : estadosList){
						cellEstadoCaso.setCellValue((String) info.getEstado());
					}

				}

				ArrayList<String> situacionList = casoInfo.getSituacionDesproteccionList();
				//2,En conflicto con la ley penal
				Cell celli2 = row.createCell(24);
				celli2.setCellValue((Integer) isSelectedSituacion(situacionList, "2"));
				//3,Agresión sexual
				Cell celli3 = row.createCell(25);
				celli3.setCellValue((Integer) isSelectedSituacion(situacionList, "3"));
				//4,Alcoholísmo en NA
				Cell celli4 = row.createCell(26);
				celli4.setCellValue((Integer) isSelectedSituacion(situacionList, "4"));
				//5,Discapacidad en NA
				Cell celli5 = row.createCell(27);
				celli5.setCellValue((Integer) isSelectedSituacion(situacionList, "5"));
				//6,Embarazos en NA
				Cell celli6 = row.createCell(28);
				celli6.setCellValue((Integer) isSelectedSituacion(situacionList, "6"));
				//7,Matrimonio infantil y uniones de hecho
				Cell celli7 = row.createCell(29);
				celli7.setCellValue((Integer) isSelectedSituacion(situacionList, "7"));
				//8,NA en situación de emergencia
				Cell celli8 = row.createCell(30);
				celli8.setCellValue((Integer) isSelectedSituacion(situacionList, "8"));
				//9,NA sin cuidado de sus progenitores
				Cell celli9 = row.createCell(31);
				celli9.setCellValue((Integer) isSelectedSituacion(situacionList, "9"));
				//10,NA víctima de trata de personas
				Cell celli10 = row.createCell(32);
				celli10.setCellValue((Integer) isSelectedSituacion(situacionList, "10"));
				//11,NA víctima de violencia armada
				Cell celli11 = row.createCell(33);
				celli11.setCellValue((Integer) isSelectedSituacion(situacionList, "11"));
				//12,NA con VIH/SIDA
				Cell celli12 = row.createCell(34);
				celli12.setCellValue((Integer) isSelectedSituacion(situacionList, "12"));
				//13,NA desaparecida
				Cell celli13 = row.createCell(35);
				celli13.setCellValue((Integer) isSelectedSituacion(situacionList, "13"));
				//14,NA migrantes
				Cell celli14 = row.createCell(36);
				celli14.setCellValue((Integer) isSelectedSituacion(situacionList, "14"));
				//15,Sub registro de nacimientos
				Cell celli15 = row.createCell(37);
				celli15.setCellValue((Integer) isSelectedSituacion(situacionList, "15"));
				//16,Suicidio en NA
				Cell celli16 = row.createCell(38);
				celli16.setCellValue((Integer) isSelectedSituacion(situacionList, "16"));
				//17,Trabajo infantil
				Cell celli17 = row.createCell(39);
				celli17.setCellValue((Integer) isSelectedSituacion(situacionList, "17"));
				//18,Violencia física
				Cell celli18 = row.createCell(40);
				celli18.setCellValue((Integer) isSelectedSituacion(situacionList, "18"));
				//19,Violencia psicológica
				Cell celli19 = row.createCell(41);
				celli19.setCellValue((Integer) isSelectedSituacion(situacionList, "19"));
				//20,Violación sexual
				Cell celli20 = row.createCell(42);
				celli20.setCellValue((Integer) isSelectedSituacion(situacionList, "20"));
				//21,Otros
				Cell celli21 = row.createCell(43);
				celli21.setCellValue((Integer) isSelectedSituacion(situacionList, "21"));
				//1,Acoso escolar
				Cell celli1 = row.createCell(44);
				celli1.setCellValue((Integer) isSelectedSituacion(situacionList, "1"));
			}

		}

		System.out.println("Creating excel");

		try {
			FileOutputStream outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}
	
	public static void generateDashboardPrevencion(String path) throws SQLException, IOException{
		//XSSFWorkbook workbook = new XSSFWorkbook();
		// XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		InputStream inputStream = ExcelUtil.class.getResourceAsStream("/com/agt/util/Dashboard3.xlsx");

		//FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		// XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		Sheet sheet = workbook.getSheetAt(0);

		Object[][] data = {};
		SettingsDbActions settingsDbActions = new SettingsDbActions();
		SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
		// Con base al codigo municipal obtengo el ultimo backup...
		int rowNum = 1;

		String codigoMunicipal = settingsInfo.getCode();
		CasoPrevencionDbActions casoPrevencionDbActions = new CasoPrevencionDbActions();
		ArrayList<CasoPrevencionInfo> casoPrevencionInfoList = casoPrevencionDbActions.getAllCases();
		//Agregamos la informacion de los casos al arreglo de datos...
		int colNum = 0;
		if(casoPrevencionInfoList != null){
			for(CasoPrevencionInfo casoInfo : casoPrevencionInfoList){
				//Correlativo...
				Row row = sheet.createRow(rowNum++);
				String correlativo = casoInfo.getId();
				Cell correlativoCell = row.createCell(0);
				correlativoCell.setCellValue((String) correlativo);
				//Mes
				String fecha = casoInfo.getFecha();
				Date fechaDate = TimeUtil.getDateFormat(fecha);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaDate);
				int month = cal.get(Calendar.MONTH) + 1;


				Cell cellMonth = row.createCell(1);
				cellMonth.setCellValue((Integer) month);
				//Mes
				Cell cellMonth2 = row.createCell(2);
				cellMonth2.setCellValue((Integer) month);
				//Dia
				int day = cal.get(Calendar.DAY_OF_MONTH);
				Cell cellDay = row.createCell(3);
				cellDay.setCellValue((Integer) day);
				//Anio
				int year = cal.get(Calendar.YEAR);
				Cell cellYear = row.createCell(4);
				cellYear.setCellValue((Integer) year);
				//Departamento
				String departamento = settingsInfo.getDepartamentoDescr();
				Cell cellDepto = row.createCell(5);
				cellDepto.setCellValue((String) departamento);
				//Departamento
				Cell cellDepto2 = row.createCell(6);
				cellDepto2.setCellValue((String) departamento);
				//Municipio
				String municipio = settingsInfo.getMunicipioDescr();
				Cell cellMupio = row.createCell(7);
				cellMupio.setCellValue((String) municipio);
				//Municipio
				Cell cellMupio2 = row.createCell(8);
				cellMupio2.setCellValue((String) municipio);
				//Institucion
				String institucion = casoInfo.getInstitucion();
				Cell cellInst = row.createCell(9);
				cellInst.setCellValue((String) institucion);
				//Institucion
				Cell cellInst2 = row.createCell(10);
				cellInst2.setCellValue((String) institucion);
				//Actividad
				String actividad = casoInfo.getActividad();
				Cell cellActividad = row.createCell(11);
				cellActividad.setCellValue((String) actividad);
				Cell cellActividad2 = row.createCell(12);
				cellActividad2.setCellValue((String) actividad);
				//Aldea
				String aldea = casoInfo.getLugar();
				Cell cell13 = row.createCell(13);
				cell13.setCellValue((String) aldea);
				//Objetivo
				String objetivo = casoInfo.getObjetivo();
				Cell cell14 = row.createCell(14);
				cell14.setCellValue((String) objetivo);
				//Resultados
				String resultados = casoInfo.getResultados();
				Cell cell15 = row.createCell(15);
				cell15.setCellValue((String) resultados);
				//Presupuesto
				String presupuesto = casoInfo.getPresupuesto();
				Cell cell16 = row.createCell(16);
				cell16.setCellValue((String) presupuesto);
				//Observaciones
				String observaciones = casoInfo.getObservaciones();
				Cell cell17 = row.createCell(17);
				cell17.setCellValue((String) observaciones);
				//NNA Hombres	
				String nnaHombres = casoInfo.getNiniosM();
				Cell cell18 = row.createCell(18);
				cell18.setCellValue((String) nnaHombres);
				//NNA Mujeres	
				String nnaMujeres = casoInfo.getNiniosF();
				Cell cell19 = row.createCell(19);
				cell19.setCellValue((String) nnaMujeres);
				//Adolescentes Hombres	
				String adolH = casoInfo.getAdolescentesM();
				Cell cell20 = row.createCell(20);
				cell20.setCellValue((String) adolH);
				//Adolescentes Mujeres	
				String adolM = casoInfo.getAdolescentesF();
				Cell cell21 = row.createCell(21);
				cell21.setCellValue((String) adolM);
				//Encargados NNA Hombres	
				String encargadosH = casoInfo.getEncargadosM();
				Cell cell22 = row.createCell(22);
				cell22.setCellValue((String) encargadosH);
				//Encargados NNA Mujeres	
				String encargadosM = casoInfo.getEncargadosF();
				Cell cell23 = row.createCell(23);
				cell23.setCellValue((String) encargadosM);
				//Tecnicos Hombres	
				String tecnicosH = casoInfo.getTecnicosM();
				Cell cell24 = row.createCell(24);
				cell24.setCellValue((String) tecnicosH);
				//Tecnicos Mujeres	
				String tecnicosF = casoInfo.getTecnicosF();
				Cell cell25 = row.createCell(25);
				cell25.setCellValue((String) tecnicosF);
				//Funcionarios Hombres	
				String funcionariosH = casoInfo.getFuncionariosM();
				Cell cell26 = row.createCell(26);
				cell26.setCellValue((String) funcionariosH);
				//Funcionarios Mujeres	
				String funcionariosM = casoInfo.getFuncionariosF();
				Cell cell27 = row.createCell(27);
				cell27.setCellValue((String) funcionariosM);
				//Servidores Publicos Hombres	
				String servidoresH = casoInfo.getServidoresM();
				Cell cell28 = row.createCell(28);
				cell28.setCellValue((String) servidoresH);
				//Servidores Publicos Mujeres	
				String servidoresM = casoInfo.getServidoresF();
				Cell cell29 = row.createCell(29);
				cell29.setCellValue((String) servidoresM);
				//Otros Hombres	
				String otrosH = casoInfo.getOtrosM();
				Cell cell30 = row.createCell(30);
				cell30.setCellValue((String) otrosH);
				//Otros Mujeres	
				String otrosM = casoInfo.getOtrosF();
				Cell cell31 = row.createCell(31);
				cell31.setCellValue((String) otrosM);
				//Otros
				String otros = casoInfo.getOtros();
				Cell cell32 = row.createCell(32);
				cell32.setCellValue((String) otros);
				
			}

		}

		System.out.println("Creating excel");

		try {
			FileOutputStream outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}
	
	public static Integer isSelectedSituacion(ArrayList<String> situacionList, String situacionId){
		if(situacionList != null){
			for(String situacion : situacionList){
				if(situacion.equals(situacionId)){
					return 1;
				}
			}
		}
		return 0;
	}
}
