/**
 * 
 */
package com.agt.util;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import com.agt.client.db.SettingsDbActions;
import com.agt.client.gui.DashboardGlobalInfo;
import com.agt.client.info.CasoDesproteccionInfo;
import com.agt.client.info.CasoDesproteccionSocialInfo;
import com.agt.client.info.CasoPrevencionInfo;
import com.agt.client.info.CatalogInfo;
import com.agt.client.info.SettingsInfo;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.view.JasperViewer;


/**
 * @author Manuel López
 *
 */
public class PdfUtil {


	public static void generateReport1(CasoDesproteccionInfo casoInfo, String filePath){

		try {
			String reportName = filePath;

			Map<String, Object> parameters = new HashMap<String, Object>();
			SettingsDbActions settingsDbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			//Información de la municipalidad....
			String municipalidad = settingsInfo.getNombre();
			String departamento = settingsInfo.getDepartamentoDescr();
			String municipio = settingsInfo.getMunicipioDescr();
			String tecnico = settingsInfo.getTecnico();
			//Información del caso...
			String caso = casoInfo.getNumeroCaso();
			String fecha = casoInfo.getFechaRegistroFormato();
			String nombreNNA = casoInfo.getNombre();
			String edad = casoInfo.getEdadDescr();
			String residencia = casoInfo.getLugarResidencia();
			String sexo = casoInfo.getSexo();
			String grupoEtnico = casoInfo.getGrupoEtnicoDescr();
			String areaGeografica = casoInfo.getAreaGeograficaDescr();
			String tipoPersona = "";
			if(casoInfo.getAnonimo()) tipoPersona ="Anónimo";
			if(casoInfo.getElMismo()) tipoPersona = "Él mismo";
			if(casoInfo.getOtro()) tipoPersona = "Otro";
			String nombrePersona = casoInfo.getNombrePersona();
			String direccionPersona = casoInfo.getDireccionPersona();
			String telefonoPerona = casoInfo.getTelefonoPersona();
			String emailPersona = casoInfo.getEmailPersona();
			String renalcionPersona = casoInfo.getParentescoPersonaDescr();
			String situacion = "";
			ArrayList<String> situaciones = casoInfo.getSituacionDesproteccionList();
			int count = 0;
			for(String situ : situaciones){
				CatalogInfo info = DashboardGlobalInfo.getSituacionDesproteccionInfo(situ);
				if(count == 0){
					situacion = info.getDescr();
				}else{
					situacion = situacion + ", "+info.getDescr();
				}
				count ++;
			}
			String parentescoAgresor = casoInfo.getParentescoDescr();
			String institucion = casoInfo.getInstitucionDescr();
			String narracion = casoInfo.getNarracion();


			parameters.put("municipalidad", municipalidad);
			parameters.put("departamento", departamento);
			parameters.put("municipio", municipio);
			parameters.put("tecnico", tecnico);
			parameters.put("caso", caso);
			parameters.put("fecha", fecha);
			parameters.put("nombreNNA", nombreNNA);
			parameters.put("edad", edad);
			parameters.put("residencia", residencia);
			parameters.put("sexo", sexo);
			parameters.put("grupoEtnico", grupoEtnico);
			parameters.put("areaGeografica", areaGeografica);
			parameters.put("tipoDenunciante", tipoPersona);
			parameters.put("nombrePersona", nombrePersona);
			parameters.put("direccionPersona", direccionPersona);
			parameters.put("telefonoPerona", telefonoPerona);
			parameters.put("emailPersona", emailPersona);
			parameters.put("renalcionPersona", renalcionPersona);
			parameters.put("situacion", situacion);
			parameters.put("parentescoAgresor", parentescoAgresor);
			parameters.put("institucion", institucion);
			parameters.put("narracion", narracion);
			//parameters.put("logo", "logo2.png");

			//parameters.put("idMedida", id);

			InputStream inputStream = PdfUtil.class.getResourceAsStream("/com/agt/util/cna.jasper");

			JasperPrint print = JasperFillManager.fillReport(inputStream, parameters);
			// exports report to pdf
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			FileOutputStream  fop = new FileOutputStream(reportName + ".pdf");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fop); // your output goes here
			exporter.exportReport();
			inputStream.close();
			fop.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("It's not possible to generate the pdf report.", e);

		} 

	}


	public static void generateReport(CasoDesproteccionInfo casoInfo, String filePath){

		try {
			String reportName = filePath;

			Map<String, Object> parameters = new HashMap<String, Object>();
			SettingsDbActions settingsDbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			//Información de la municipalidad....
			String municipalidad = settingsInfo.getNombre();
			String departamento = settingsInfo.getDepartamentoDescr();
			String municipio = settingsInfo.getMunicipioDescr();
			String tecnico = settingsInfo.getTecnico();
			//Información del caso...
			String caso = casoInfo.getNumeroCaso();
			String fecha = casoInfo.getFechaRegistroFormato();
			String nombreNNA = casoInfo.getNombre();
			String edad = casoInfo.getEdadDescr();
			String residencia = casoInfo.getLugarResidencia();
			String sexo = casoInfo.getSexo();
			String grupoEtnico = casoInfo.getGrupoEtnicoDescr();
			String areaGeografica = casoInfo.getAreaGeograficaDescr();
			String tipoPersona = "";
			if(casoInfo.getAnonimo()) tipoPersona ="Anónimo";
			if(casoInfo.getElMismo()) tipoPersona = "Él mismo";
			if(casoInfo.getOtro()) tipoPersona = "Otro";
			String nombrePersona = casoInfo.getNombrePersona();
			String direccionPersona = casoInfo.getDireccionPersona();
			String telefonoPerona = casoInfo.getTelefonoPersona();
			String emailPersona = casoInfo.getEmailPersona();
			String renalcionPersona = casoInfo.getParentescoPersonaDescr();
			String situacion = "";
			ArrayList<String> situaciones = casoInfo.getSituacionDesproteccionList();
			int count = 0;
			for(String situ : situaciones){
				CatalogInfo info = DashboardGlobalInfo.getSituacionDesproteccionInfo(situ);
				if(count == 0){
					situacion = info.getDescr();
				}else{
					situacion = situacion + ", "+info.getDescr();
				}
				count ++;
			}
			String parentescoAgresor = casoInfo.getParentescoDescr();
			String institucion = casoInfo.getInstitucionDescr();
			String narracion = casoInfo.getNarracion();


			parameters.put("municipalidad", municipalidad);
			parameters.put("departamento", departamento);
			parameters.put("municipio", municipio);
			parameters.put("tecnico", tecnico);
			parameters.put("caso", caso);
			parameters.put("fecha", fecha);
			parameters.put("nombreNNA", nombreNNA);
			parameters.put("edad", edad);
			parameters.put("residencia", residencia);
			parameters.put("sexo", sexo);
			parameters.put("grupoEtnico", grupoEtnico);
			parameters.put("areaGeografica", areaGeografica);
			parameters.put("tipoDenunciante", tipoPersona);
			parameters.put("nombrePersona", nombrePersona);
			parameters.put("direccionPersona", direccionPersona);
			parameters.put("telefonoPerona", telefonoPerona);
			parameters.put("emailPersona", emailPersona);
			parameters.put("renalcionPersona", renalcionPersona);
			parameters.put("situacion", situacion);
			parameters.put("parentescoAgresor", parentescoAgresor);
			parameters.put("institucion", institucion);
			parameters.put("narracion", narracion);
			//parameters.put("logo", "logo2.png");

			//parameters.put("idMedida", id);
			//parameters.put("logo", "logo2.png");

			//parameters.put("idMedida", id);
			String[] columnNames = {"nombreNNA"};
			String[][] data = {
					{nombreNNA}
			};
			DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
			InputStream inputStream = PdfUtil.class.getResourceAsStream("/com/agt/util/cna.jasper");

			//JasperCompileManager.compileReportToFile("reports/report1.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
					new JRTableModelDataSource(tableModel));
			//JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			//jasperViewer.setVisible(true);
			JasperExportManager.exportReportToPdfFile(jasperPrint,reportName + ".pdf");


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("It's not possible to generate the pdf report.", e);

		} 

	}
	
	public static void generateReportSocial(CasoDesproteccionSocialInfo casoInfo, String filePath){

		try {
			String reportName = filePath;

			Map<String, Object> parameters = new HashMap<String, Object>();
			SettingsDbActions settingsDbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			//Información de la municipalidad....
			String municipalidad = settingsInfo.getNombre();
			String departamento = settingsInfo.getDepartamentoDescr();
			String municipio = settingsInfo.getMunicipioDescr();
			String tecnico = settingsInfo.getTecnico();
			//Información del caso...
			String caso = casoInfo.getNumeroCaso();
			String fecha = casoInfo.getFechaRegistroFormato();
			String nombreNNA = casoInfo.getNombre();
			String edad = casoInfo.getEdadDescr();
			String residencia = casoInfo.getLugarResidencia();
			String sexo = casoInfo.getSexo();
			String grupoEtnico = casoInfo.getGrupoEtnicoDescr();
			String areaGeografica = casoInfo.getAreaGeograficaDescr();
			String tipoPersona = "";
			if(casoInfo.getAnonimo()) tipoPersona ="Anónimo";
			if(casoInfo.getElMismo()) tipoPersona = "Él mismo";
			if(casoInfo.getOtro()) tipoPersona = "Otro";
			String nombrePersona = casoInfo.getNombrePersona();
			String direccionPersona = casoInfo.getDireccionPersona();
			String telefonoPerona = casoInfo.getTelefonoPersona();
			String emailPersona = casoInfo.getEmailPersona();
			String renalcionPersona = casoInfo.getParentescoPersonaDescr();
			String situacion = "";
			ArrayList<String> situaciones = casoInfo.getSituacionDesproteccionList();
			int count = 0;
			for(String situ : situaciones){
				CatalogInfo info = DashboardGlobalInfo.getSituacionDesproteccionInfo(situ);
				if(count == 0){
					situacion = info.getDescr();
				}else{
					situacion = situacion + ", "+info.getDescr();
				}
				count ++;
			}
			String parentescoAgresor = casoInfo.getParentescoDescr();
			String institucion = casoInfo.getInstitucionDescr();
			String narracion = casoInfo.getNarracion();


			parameters.put("municipalidad", municipalidad);
			parameters.put("departamento", departamento);
			parameters.put("municipio", municipio);
			parameters.put("tecnico", tecnico);
			parameters.put("caso", caso);
			parameters.put("fecha", fecha);
			parameters.put("nombreNNA", nombreNNA);
			parameters.put("edad", edad);
			parameters.put("residencia", residencia);
			parameters.put("sexo", sexo);
			parameters.put("grupoEtnico", grupoEtnico);
			parameters.put("areaGeografica", areaGeografica);
			parameters.put("tipoDenunciante", tipoPersona);
			parameters.put("nombrePersona", nombrePersona);
			parameters.put("direccionPersona", direccionPersona);
			parameters.put("telefonoPerona", telefonoPerona);
			parameters.put("emailPersona", emailPersona);
			parameters.put("renalcionPersona", renalcionPersona);
			parameters.put("situacion", situacion);
			parameters.put("parentescoAgresor", parentescoAgresor);
			parameters.put("institucion", institucion);
			parameters.put("narracion", narracion);
			//parameters.put("logo", "logo2.png");

			//parameters.put("idMedida", id);
			//parameters.put("logo", "logo2.png");

			//parameters.put("idMedida", id);
			String[] columnNames = {"nombreNNA"};
			String[][] data = {
					{nombreNNA}
			};
			DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
			InputStream inputStream = PdfUtil.class.getResourceAsStream("/com/agt/util/cna.jasper");

			//JasperCompileManager.compileReportToFile("reports/report1.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
					new JRTableModelDataSource(tableModel));
			//JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			//jasperViewer.setVisible(true);
			JasperExportManager.exportReportToPdfFile(jasperPrint,reportName + ".pdf");


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("It's not possible to generate the pdf report.", e);

		} 

	}



	public static void generateReportPrevencion(CasoPrevencionInfo actividadInfo, String filePath){

		try {
			String reportName = filePath;

			Map<String, Object> parameters = new HashMap<String, Object>();
			SettingsDbActions settingsDbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			//Información de la municipalidad....
			String municipalidad = settingsInfo.getNombre();
			String departamento = settingsInfo.getDepartamentoDescr();
			String municipio = settingsInfo.getMunicipioDescr();
			String tecnico = settingsInfo.getTecnico();
			//Información de la actividad...
			
			String fecha = actividadInfo.getFecha();
			String hora = actividadInfo.getHora();
			String fechaFormato = fecha +" "+hora;
			String actividad = actividadInfo.getActividad();
			String institucion = actividadInfo.getInstitucion();
			String lugar = actividadInfo.getLugar();
			String objetivo = actividadInfo.getObjetivo();
			String presupuesto = actividadInfo.getPresupuesto();
			String resultados = actividadInfo.getResultados();
			String observaciones = actividadInfo.getObservaciones();
			String id = actividadInfo.getId();
			String ninios = "Hombres: "+actividadInfo.getNiniosM()+" Mujeres: "+actividadInfo.getNiniosF(); 
			String adolescentes = "Hombres: "+actividadInfo.getAdolescentesM()+" Mujeres: "+actividadInfo.getAdolescentesF(); 
			String encargados = "Hombres: "+actividadInfo.getEncargadosM()+" Mujeres: "+actividadInfo.getEncargadosF(); 
			String tecnicos = "Hombres: "+actividadInfo.getTecnicosM()+" Mujeres: "+actividadInfo.getTecnicosF(); 
			String funcionarios = "Hombres: "+actividadInfo.getFuncionariosM()+" Mujeres: "+actividadInfo.getFuncionariosF(); 
			String servidores = "Hombres: "+actividadInfo.getServidoresM()+" Mujeres: "+actividadInfo.getServidoresF();
			String otros = "Hombres: "+actividadInfo.getOtrosM()+" Mujeres: "+actividadInfo.getOtrosF();
			String otro = actividadInfo.getOtros();
			
			parameters.put("municipalidad", municipalidad);
			parameters.put("departamento", departamento);
			parameters.put("municipio", municipio);
			parameters.put("tecnico", tecnico);
			parameters.put("institucion", institucion);
			parameters.put("lugar", lugar);
			parameters.put("objetivo", objetivo);
			parameters.put("presupuesto", presupuesto);
			parameters.put("resultados", resultados);
			parameters.put("observaciones", observaciones);
			parameters.put("ninios", ninios);
			parameters.put("adolescentes", adolescentes);
			parameters.put("encargados", encargados);
			parameters.put("tecnicos", tecnicos);
			parameters.put("funcionarios", funcionarios);
			parameters.put("servidores", servidores);
			parameters.put("otros", otros);
			parameters.put("otro", otro);
			parameters.put("id", id);
			parameters.put("fecha", fechaFormato);
			
			String[] columnNames = {"actividad"};
			String[][] data = {
					{actividad}
			};
			
			DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
			InputStream inputStream = PdfUtil.class.getResourceAsStream("/com/agt/util/prevencion.jasper");

			//JasperCompileManager.compileReportToFile("reports/report1.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
					new JRTableModelDataSource(tableModel));
			//JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			//jasperViewer.setVisible(true);
			JasperExportManager.exportReportToPdfFile(jasperPrint,reportName + ".pdf");


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("It's not possible to generate the pdf report.", e);

		} 

	}

}
