/**
 * 
 */
package com.agt.client.gui;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import com.agt.client.db.CasoDesproteccionDbActions;
import com.agt.client.db.CasoDesproteccionSocialDbActions;
import com.agt.client.db.CasoPrevencionDbActions;
import com.agt.client.info.CasoDesproteccionInfo;
import com.agt.client.info.CasoDesproteccionSocialInfo;
import com.agt.client.info.CasoPrevencionInfo;
import com.agt.client.info.CatalogInfo;
import com.agt.client.info.ReportInfo;
import com.agt.util.ExcelUtil;
import com.agt.util.TimeUtil;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;


/**
 * @author mlopez
 *
 */
public class ReportsAppPane implements EventHandler<ActionEvent> {
	private Stage primaryStage;
	private BorderPane borderPane;
	ArrayList<CasoDesproteccionInfo> dataList;
	ArrayList<CasoDesproteccionSocialInfo> socialDataList;
	ArrayList<CasoPrevencionInfo> prevencionDataList;
	private String currentAno;
	private ArrayList<ReportInfo> situacionesInfo = null;
	private JFXButton downloadButton;
	private JFXButton downloadButtonSocial;
	private JFXButton downloadButtonPrevencion;

	public ReportsAppPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public BorderPane buildPane() {

		// Finally create the BorderPane base container...
		this.borderPane = new BorderPane();
		
		currentAno = String.valueOf(TimeUtil.getCurrentYear());
		//currentAno = String.valueOf(TimeUtil.getCurrentYear());

		downloadButton = new JFXButton("Descargar Excel".toUpperCase());
		downloadButton.getStyleClass().add("button-raised");
		downloadButton.setCursor(Cursor.HAND);
		downloadButton.setOnAction(this);
		downloadButton.setPrefWidth(400);

		downloadButtonSocial = new JFXButton("Descargar Excel".toUpperCase());
		downloadButtonSocial.getStyleClass().add("button-raised");
		downloadButtonSocial.setCursor(Cursor.HAND);
		downloadButtonSocial.setOnAction(this);
		downloadButtonSocial.setPrefWidth(400);
		
		downloadButtonPrevencion = new JFXButton("Descargar Excel".toUpperCase());
		downloadButtonPrevencion.getStyleClass().add("button-raised");
		downloadButtonPrevencion.setCursor(Cursor.HAND);
		downloadButtonPrevencion.setOnAction(this);
		downloadButtonPrevencion.setPrefWidth(400);
		
		ListView<String> list = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList (
				"CASOS DESPROTECCION", "CASOS DESPROTECCION ESPECIAL", "ACCIONES PREVENCION");
		list.setCursor(Cursor.HAND);
		list.setItems(items);
		list.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						if(new_val.equals("CASOS DESPROTECCION")){
							changeMenuCasosDesproteccion();
						}
						if(new_val.equals("CASOS DESPROTECCION ESPECIAL")){
							changeMenuCasosDesproteccionEspecial();
						}
						if(new_val.equals("ACCIONES PREVENCION")){
							changeMenuCasosPrevencion();
						}
					}
				});

		VBox vBox = new VBox();
		vBox.setSpacing(10);
		ScrollPane scrollPane = new ScrollPane(vBox);
		scrollPane.setFitToWidth(true);
		this.borderPane.setCenter(scrollPane);
		this.borderPane.setLeft(list);
		return borderPane;
	}
	
	private void changeMenuCasosDesproteccion(){
		CasoDesproteccionDbActions dbAction = new CasoDesproteccionDbActions();
		dataList = dbAction.getCurrentYearCases();
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().addAll(this.downloadButton,this.buildPieChart(),this.buildMounthChart(),this.buildSituacionesChart());

		//vBox.getChildren().addAll(this.buildPieChart(),this.buildMounthChart(),this.buildSituacionesChart());

		ScrollPane scrollPane = new ScrollPane(vBox);
		scrollPane.setFitToWidth(true);
		this.borderPane.setCenter(scrollPane);
	}
	
	private void changeMenuCasosDesproteccionEspecial(){
		CasoDesproteccionSocialDbActions dbAction = new CasoDesproteccionSocialDbActions();
		socialDataList = dbAction.getCurrentYearCases();
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().addAll(this.downloadButtonSocial, this.buildPieChartEspecial(),this.buildMounthChartEspecial(),this.buildSituacionesChartEspecial());

		//vBox.getChildren().addAll(this.buildPieChart(),this.buildMounthChart(),this.buildSituacionesChart());

		ScrollPane scrollPane = new ScrollPane(vBox);
		scrollPane.setFitToWidth(true);
		this.borderPane.setCenter(scrollPane);
	}
	
	private void changeMenuCasosPrevencion(){
		CasoPrevencionDbActions dbAction = new CasoPrevencionDbActions();
		String currentAno = String.valueOf(TimeUtil.getCurrentYear());
		String startDate = "01/01/"+currentAno;
		String endDate = "31/12/"+currentAno;
		Date fechaInicial = TimeUtil.getDateFormat(startDate);
		Date fechaFinal = TimeUtil.getDateFormat(endDate);
		prevencionDataList = dbAction.getCasosByDate(fechaInicial, fechaFinal);
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().addAll(this.downloadButtonPrevencion, this.buildMounthChartPrevencion());

		//vBox.getChildren().addAll(this.buildPieChart(),this.buildMounthChart(),this.buildSituacionesChart());

		ScrollPane scrollPane = new ScrollPane(vBox);
		scrollPane.setFitToWidth(true);
		this.borderPane.setCenter(scrollPane);
	}
	
	/*CASOS DESPROTECCION ESPECIAL*/

	public PieChart buildPieChartEspecial(){
		PieChart chart = new PieChart();
		Double hombre = Double.valueOf("0");
		Double mujer = Double.valueOf("0");
		if(this.dataList != null){
			for(CasoDesproteccionSocialInfo info : this.socialDataList){
				if(info.getSexo().equals("M")){
					hombre = hombre + 1;
				}
				if(info.getSexo().equals("F")){
					mujer = mujer + 1;
				}
			}
		}
		try{
			PieChart.Data hombres = new PieChart.Data("Hombres "+hombre.intValue(), hombre);
			ObservableList<PieChart.Data> pieChartData =
					FXCollections.observableArrayList(hombres,
							new PieChart.Data("Mujeres "+mujer.intValue(), mujer));
			chart = new PieChart(pieChartData);
			chart.setTitle("Casos desprotección social por sexo "+currentAno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return chart;
	}

	public LineChart buildMounthChartEspecial(){
		//defining the axes
		final CategoryAxis  xAxis = new CategoryAxis ();
		final NumberAxis yAxis = new NumberAxis();
		Double enero = 0.0;
		Double febrero = 0.0;
		Double marzo = 0.0;
		Double abril = 0.0;
		Double mayo = 0.0;
		Double junio = 0.0;
		Double julio = 0.0;
		Double agosto = 0.0;
		Double septiembre = 0.0;
		Double octubre = 0.0;
		Double noviembre = 0.0;
		Double diciembre = 0.0;

		if(this.dataList != null){
			for(CasoDesproteccionSocialInfo info : this.socialDataList){
				Date fecha = TimeUtil.getDateFormat(info.getFecha());

				int mount = fecha.getMonth();
				if(mount == 0){
					enero = enero + 1;
				}
				if(mount == 1){
					febrero = febrero + 1;
				}
				if(mount == 2){
					marzo = marzo + 1;
				}
				if(mount == 3){
					abril = abril + 1;
				}
				if(mount == 4){
					mayo = mayo + 1;
				}
				if(mount == 5){
					junio = junio + 1;
				}
				if(mount == 6){
					julio = julio + 1;
				}	
				if(mount == 7){
					agosto = agosto + 1;
				}
				if(mount == 8){
					septiembre = septiembre + 1;
				}
				if(mount == 9){
					octubre = octubre + 1;
				}
				if(mount == 10){
					noviembre = noviembre + 1;
				}
				if(mount == 11){
					diciembre = diciembre + 1;
				}

			}
		}
		LineChart <String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);
		try{
			lineChart.setTitle("Casos desprotección social por mes, año "+this.currentAno);
			//defining a series
			XYChart.Series series = new XYChart.Series();
			series.setName("Mes");
			//populating the series with data
			series.getData().add(new XYChart.Data("Enero "+enero.intValue(), enero));
			series.getData().add(new XYChart.Data("Febrero "+febrero.intValue(), febrero));
			series.getData().add(new XYChart.Data("Marzo "+marzo.intValue(), marzo));
			series.getData().add(new XYChart.Data("Abril "+abril.intValue(), abril));
			series.getData().add(new XYChart.Data("Mayo "+mayo.intValue(), mayo));
			series.getData().add(new XYChart.Data("Junio "+junio.intValue(), junio));
			series.getData().add(new XYChart.Data("Julio "+julio.intValue(), julio));
			series.getData().add(new XYChart.Data("Agosto "+agosto.intValue(), agosto));
			series.getData().add(new XYChart.Data("Septiembre "+septiembre.intValue(), septiembre));
			series.getData().add(new XYChart.Data("Octubre "+octubre.intValue(), octubre));
			series.getData().add(new XYChart.Data("Noviembre "+noviembre.intValue(), noviembre));
			series.getData().add(new XYChart.Data("Diciembre "+diciembre.intValue(), diciembre));

			lineChart.getData().add(series);
		}catch(Exception e){
			e.getMessage();
		}
		return lineChart;
	}

	public LineChart buildSituacionesChartEspecial(){
		//defining the axes
		final CategoryAxis  xAxis = new CategoryAxis ();
		final NumberAxis yAxis = new NumberAxis();

		this.situacionesInfo = new ArrayList<ReportInfo>();
		if(this.socialDataList != null){
			for(CasoDesproteccionSocialInfo info : this.socialDataList){
				ArrayList<String> situaciones = info.getSituacionDesproteccionList();
				for(String situacion : situaciones){
					ReportInfo reportInfo = this.findReportInfo(situacion);
					if(reportInfo == null){
						reportInfo = new ReportInfo();
						reportInfo.setId(situacion);
						reportInfo.setCount(1.0);
						this.situacionesInfo.add(reportInfo);
					}else{
						Double count = reportInfo.getCount();
						reportInfo.setCount(count + 1);
					}
				}
			}
		}
		LineChart <String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);
		try{
			lineChart.setTitle("Casos por Situación de Desprotección "+this.currentAno);
			//defining a series
			XYChart.Series series = new XYChart.Series();
			series.setName("Situación");
			//populating the series with data
			ArrayList<CatalogInfo> situacionesList = DashboardGlobalInfo.getSituacionDesproteccionInfoList();
			for(CatalogInfo catalogInfo : situacionesList){
				ReportInfo reportInfo = this.findReportInfo(catalogInfo.getId());
				if(reportInfo != null){
					String label = catalogInfo.getDescr()+ " "+reportInfo.getCount().intValue();
					Double value = reportInfo.getCount();
					series.getData().add(new XYChart.Data(label, value));
				}else{
					String label = catalogInfo.getDescr() +" 0";
					series.getData().add(new XYChart.Data(label, 0.0));
				}

			}

			lineChart.getData().add(series);
		}catch(Exception e){
			e.printStackTrace();
		}
		return lineChart;
	}
	
	/*CASOS DESPROTECCION*/

	public PieChart buildPieChart(){
		PieChart chart = new PieChart();
		Double hombre = Double.valueOf("0");
		Double mujer = Double.valueOf("0");
		if(this.dataList != null){
			for(CasoDesproteccionInfo info : this.dataList){
				if(info.getSexo().equals("M")){
					hombre = hombre + 1;
				}
				if(info.getSexo().equals("F")){
					mujer = mujer + 1;
				}
			}
		}
		try{
			PieChart.Data hombres = new PieChart.Data("Hombres "+hombre.intValue(), hombre);
			ObservableList<PieChart.Data> pieChartData =
					FXCollections.observableArrayList(hombres,
							new PieChart.Data("Mujeres "+mujer.intValue(), mujer));
			chart = new PieChart(pieChartData);
			chart.setTitle("Casos desprotección por sexo "+currentAno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return chart;
	}

	public LineChart buildMounthChart(){
		//defining the axes
		final CategoryAxis  xAxis = new CategoryAxis ();
		final NumberAxis yAxis = new NumberAxis();
		Double enero = 0.0;
		Double febrero = 0.0;
		Double marzo = 0.0;
		Double abril = 0.0;
		Double mayo = 0.0;
		Double junio = 0.0;
		Double julio = 0.0;
		Double agosto = 0.0;
		Double septiembre = 0.0;
		Double octubre = 0.0;
		Double noviembre = 0.0;
		Double diciembre = 0.0;

		if(this.dataList != null){
			for(CasoDesproteccionInfo info : this.dataList){
				Date fecha = TimeUtil.getDateFormat(info.getFecha());

				int mount = fecha.getMonth();
				if(mount == 0){
					enero = enero + 1;
				}
				if(mount == 1){
					febrero = febrero + 1;
				}
				if(mount == 2){
					marzo = marzo + 1;
				}
				if(mount == 3){
					abril = abril + 1;
				}
				if(mount == 4){
					mayo = mayo + 1;
				}
				if(mount == 5){
					junio = junio + 1;
				}
				if(mount == 6){
					julio = julio + 1;
				}	
				if(mount == 7){
					agosto = agosto + 1;
				}
				if(mount == 8){
					septiembre = septiembre + 1;
				}
				if(mount == 9){
					octubre = octubre + 1;
				}
				if(mount == 10){
					noviembre = noviembre + 1;
				}
				if(mount == 11){
					diciembre = diciembre + 1;
				}

			}
		}
		LineChart <String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);
		try{
			lineChart.setTitle("Casos por mes, año "+this.currentAno);
			//defining a series
			XYChart.Series series = new XYChart.Series();
			series.setName("Mes");
			//populating the series with data
			series.getData().add(new XYChart.Data("Enero "+enero.intValue(), enero));
			series.getData().add(new XYChart.Data("Febrero "+febrero.intValue(), febrero));
			series.getData().add(new XYChart.Data("Marzo "+marzo.intValue(), marzo));
			series.getData().add(new XYChart.Data("Abril "+abril.intValue(), abril));
			series.getData().add(new XYChart.Data("Mayo "+mayo.intValue(), mayo));
			series.getData().add(new XYChart.Data("Junio "+junio.intValue(), junio));
			series.getData().add(new XYChart.Data("Julio "+julio.intValue(), julio));
			series.getData().add(new XYChart.Data("Agosto "+agosto.intValue(), agosto));
			series.getData().add(new XYChart.Data("Septiembre "+septiembre.intValue(), septiembre));
			series.getData().add(new XYChart.Data("Octubre "+octubre.intValue(), octubre));
			series.getData().add(new XYChart.Data("Noviembre "+noviembre.intValue(), noviembre));
			series.getData().add(new XYChart.Data("Diciembre "+diciembre.intValue(), diciembre));

			lineChart.getData().add(series);
		}catch(Exception e){
			e.getMessage();
		}
		return lineChart;
	}

	public LineChart buildSituacionesChart(){
		//defining the axes
		final CategoryAxis  xAxis = new CategoryAxis ();
		final NumberAxis yAxis = new NumberAxis();

		this.situacionesInfo = new ArrayList<ReportInfo>();
		if(this.dataList != null){
			for(CasoDesproteccionInfo info : this.dataList){
				ArrayList<String> situaciones = info.getSituacionDesproteccionList();
				for(String situacion : situaciones){
					ReportInfo reportInfo = this.findReportInfo(situacion);
					if(reportInfo == null){
						reportInfo = new ReportInfo();
						reportInfo.setId(situacion);
						reportInfo.setCount(1.0);
						this.situacionesInfo.add(reportInfo);
					}else{
						Double count = reportInfo.getCount();
						reportInfo.setCount(count + 1);
					}
				}
			}
		}
		LineChart <String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);
		try{
			lineChart.setTitle("Casos por Situación de Desprotección "+this.currentAno);
			//defining a series
			XYChart.Series series = new XYChart.Series();
			series.setName("Situación");
			//populating the series with data
			ArrayList<CatalogInfo> situacionesList = DashboardGlobalInfo.getSituacionDesproteccionInfoList();
			for(CatalogInfo catalogInfo : situacionesList){
				ReportInfo reportInfo = this.findReportInfo(catalogInfo.getId());
				if(reportInfo != null){
					String label = catalogInfo.getDescr()+ " "+reportInfo.getCount().intValue();
					Double value = reportInfo.getCount();
					series.getData().add(new XYChart.Data(label, value));
				}else{
					String label = catalogInfo.getDescr() +" 0";
					series.getData().add(new XYChart.Data(label, 0.0));
				}

			}

			lineChart.getData().add(series);
		}catch(Exception e){
			e.printStackTrace();
		}
		return lineChart;
	}

	public ReportInfo findReportInfo(String id){
		ReportInfo reportInfo = null;
		for(ReportInfo info : this.situacionesInfo){
			if(info.getId().equals(id)){
				return info;
			}
		}
		return null;
	}
	
	/*SITUACIONES PREVENCION*/
	public LineChart buildMounthChartPrevencion(){
		//defining the axes
		final CategoryAxis  xAxis = new CategoryAxis ();
		final NumberAxis yAxis = new NumberAxis();
		Double enero = 0.0;
		Double febrero = 0.0;
		Double marzo = 0.0;
		Double abril = 0.0;
		Double mayo = 0.0;
		Double junio = 0.0;
		Double julio = 0.0;
		Double agosto = 0.0;
		Double septiembre = 0.0;
		Double octubre = 0.0;
		Double noviembre = 0.0;
		Double diciembre = 0.0;

		if(this.prevencionDataList != null){
			for(CasoPrevencionInfo info : this.prevencionDataList){
				Date fecha = TimeUtil.getDateFormat(info.getFecha());

				int mount = fecha.getMonth();
				if(mount == 0){
					enero = enero + 1;
				}
				if(mount == 1){
					febrero = febrero + 1;
				}
				if(mount == 2){
					marzo = marzo + 1;
				}
				if(mount == 3){
					abril = abril + 1;
				}
				if(mount == 4){
					mayo = mayo + 1;
				}
				if(mount == 5){
					junio = junio + 1;
				}
				if(mount == 6){
					julio = julio + 1;
				}	
				if(mount == 7){
					agosto = agosto + 1;
				}
				if(mount == 8){
					septiembre = septiembre + 1;
				}
				if(mount == 9){
					octubre = octubre + 1;
				}
				if(mount == 10){
					noviembre = noviembre + 1;
				}
				if(mount == 11){
					diciembre = diciembre + 1;
				}

			}
		}
		LineChart <String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);
		try{
			lineChart.setTitle("Acciones prevención por mes, año "+this.currentAno);
			//defining a series
			XYChart.Series series = new XYChart.Series();
			series.setName("Mes");
			//populating the series with data
			series.getData().add(new XYChart.Data("Enero "+enero.intValue(), enero));
			series.getData().add(new XYChart.Data("Febrero "+febrero.intValue(), febrero));
			series.getData().add(new XYChart.Data("Marzo "+marzo.intValue(), marzo));
			series.getData().add(new XYChart.Data("Abril "+abril.intValue(), abril));
			series.getData().add(new XYChart.Data("Mayo "+mayo.intValue(), mayo));
			series.getData().add(new XYChart.Data("Junio "+junio.intValue(), junio));
			series.getData().add(new XYChart.Data("Julio "+julio.intValue(), julio));
			series.getData().add(new XYChart.Data("Agosto "+agosto.intValue(), agosto));
			series.getData().add(new XYChart.Data("Septiembre "+septiembre.intValue(), septiembre));
			series.getData().add(new XYChart.Data("Octubre "+octubre.intValue(), octubre));
			series.getData().add(new XYChart.Data("Noviembre "+noviembre.intValue(), noviembre));
			series.getData().add(new XYChart.Data("Diciembre "+diciembre.intValue(), diciembre));

			lineChart.getData().add(series);
		}catch(Exception e){
			e.getMessage();
		}
		return lineChart;
	}
	

	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();
		 MessageModalDialog messageModalDialog = new MessageModalDialog(this.primaryStage);
		if(eventSource == this.downloadButton){
			if(this.dataList != null){
				FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Guardar Reporte");
	           
	            File file = fileChooser.showSaveDialog(this.primaryStage);
	            if (file != null) {
	                try {
	                	
	                	String path = file.getCanonicalPath() +".xlsx";
	                	ExcelUtil.generateDashboard(path);
	                	System.out.println(file.getAbsolutePath());
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Dashboard generado con éxito");
	    				messageDialogStage.show();
	                } catch (Exception ex) {
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Error al generar el Dashboard");
	    				messageDialogStage.show();
	                    System.out.println(ex.getMessage());
	                }
	            }
			
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No hay datos para descargar");
				messageDialogStage.show();
			}
		}
		
		if(eventSource == this.downloadButtonSocial){
			if(this.dataList != null){
				FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Guardar Reporte");
	           
	            File file = fileChooser.showSaveDialog(this.primaryStage);
	            if (file != null) {
	                try {
	                	
	                	String path = file.getCanonicalPath() +".xlsx";
	                	ExcelUtil.generateDashboardSocial(path);
	                	System.out.println(file.getAbsolutePath());
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Dashboard generado con éxito");
	    				messageDialogStage.show();
	                } catch (Exception ex) {
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Error al generar el Dashboard");
	    				messageDialogStage.show();
	                    System.out.println(ex.getMessage());
	                }
	            }
			
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No hay datos para descargar");
				messageDialogStage.show();
			}
		}
		
		if(eventSource == this.downloadButtonPrevencion){
			if(this.dataList != null){
				FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Guardar Reporte");
	           
	            File file = fileChooser.showSaveDialog(this.primaryStage);
	            if (file != null) {
	                try {
	                	
	                	String path = file.getCanonicalPath() +".xlsx";
	                	ExcelUtil.generateDashboardPrevencion(path);
	                	System.out.println(file.getAbsolutePath());
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Dashboard generado con éxito");
	    				messageDialogStage.show();
	                } catch (Exception ex) {
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Error al generar el Dashboard");
	    				messageDialogStage.show();
	                    System.out.println(ex.getMessage());
	                }
	            }
			
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No hay datos para descargar");
				messageDialogStage.show();
			}
		}

	}
}
