/**
 * 
 */
package com.agt.client.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.net.ssl.HttpsURLConnection;

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
import com.agt.util.TimeUtil;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;

import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.application.HostServices;
import javafx.application.Application;
import javafx.stage.FileChooser;

/**
 * @author mlopez
 *
 */
public class ToolsAppPane extends Application implements  EventHandler<ActionEvent> {
	private Stage primaryStage;
	private BorderPane borderPane;
	private JFXButton exportDataToggleNode;
	private JFXButton importDataToggleNode;
	private JFXButton userGuideToggleNode;
	private JFXButton diccionaryToggleNode;
	static final int BUFFER = 2048;


	public ToolsAppPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public BorderPane buildPane() {

		// Finally create the BorderPane base container...
		this.borderPane = new BorderPane();

		// Header Panel
		HBox titleHbox = new HBox();
		titleHbox.setPadding(new Insets(10,10,10,10));
		titleHbox.setSpacing(5.0);
		titleHbox.setAlignment(Pos.CENTER_LEFT);
		titleHbox.setStyle("-fx-background-color: #2CCFFF;");
		titleHbox.setPrefHeight(75.0);

		// Header del Panel
		Icon icon = new Icon(AwesomeIcon.WRENCH,"2em",";","");
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setHeight(5);
		Label titleLabel = new Label("HERRAMIENTAS DEL SISTEMA");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20.0));
		titleLabel.setStyle("-fx-text-fill: #ffffff;");
		titleLabel.setEffect(ds);
		titleHbox.getChildren().add(icon);
		titleHbox.getChildren().add(titleLabel);

		this.borderPane.setTop(titleHbox);

		// Grid del toolbar...
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 55, 25));

		// Botón para exportar datos...
		VBox exportDataHBox = new VBox();
		exportDataHBox.setSpacing(3);
		Icon iconExport = new Icon(AwesomeIcon.DOWNLOAD,"12em",";","");
		exportDataToggleNode = new JFXButton();       
		iconExport.setPadding(new Insets(0,50,0,0));
		exportDataToggleNode.setGraphic(iconExport);
		exportDataToggleNode.setCursor(Cursor.HAND);
		exportDataToggleNode.getStyleClass().add("jfx-toggle-node");
		exportDataToggleNode.setOnAction(this);
		exportDataHBox.getChildren().add(exportDataToggleNode);
		exportDataHBox.getChildren().add(new Label("Exportar datos"));
		exportDataHBox.setAlignment(Pos.CENTER);

		// Botón para importar datos...
		VBox importDataHBox = new VBox();
		importDataHBox.setSpacing(3);
		Icon iconImport = new Icon(AwesomeIcon.UPLOAD,"12em",";","");
		importDataToggleNode = new JFXButton();       
		iconImport.setPadding(new Insets(0,50,0,0));
		importDataToggleNode.setGraphic(iconImport);
		importDataToggleNode.setCursor(Cursor.HAND);
		importDataToggleNode.setOnAction(this);
		importDataHBox.getChildren().add(importDataToggleNode);
		importDataHBox.getChildren().add(new Label("Exportar datos"));
		importDataHBox.setAlignment(Pos.CENTER);

		// Botón para ver el manual...
		VBox userGuideDataHBox = new VBox();
		userGuideDataHBox.setSpacing(3);
		Icon guideExport = new Icon(AwesomeIcon.BOOK,"12em",";","");
		userGuideToggleNode = new JFXButton();       
		guideExport.setPadding(new Insets(0,50,0,0));
		userGuideToggleNode.setGraphic(guideExport);
		userGuideToggleNode.setCursor(Cursor.HAND);
		userGuideToggleNode.setOnAction(this);
		userGuideDataHBox.getChildren().add(userGuideToggleNode);
		userGuideDataHBox.getChildren().add(new Label("Manual de usuario"));
		userGuideDataHBox.setAlignment(Pos.CENTER);


		// Botón para ver el diccionario de situaciones de desprotección...
		VBox diccionaryDataHBox = new VBox();
		diccionaryDataHBox.setSpacing(3);
		Icon diccionaryExport = new Icon(AwesomeIcon.BOOK,"12em",";","");
		diccionaryToggleNode = new JFXButton();       
		diccionaryExport.setPadding(new Insets(0,50,0,0));
		diccionaryToggleNode.setGraphic(diccionaryExport);
		diccionaryToggleNode.setCursor(Cursor.HAND);
		diccionaryToggleNode.setOnAction(this);
		diccionaryDataHBox.getChildren().add(diccionaryToggleNode);
		diccionaryDataHBox.getChildren().add(new Label("Situaciones de desprotección"));
		diccionaryDataHBox.setAlignment(Pos.CENTER);


		grid.add(userGuideDataHBox, 0, 0);
		grid.add(diccionaryDataHBox, 1, 0);
		grid.add(exportDataHBox, 0, 1);
		//grid.add(importDataHBox, 1, 1);


		this.borderPane.setCenter(grid);

		return borderPane;
	}

	public void generateBackup(String path){
		try{
			// Obtenemos los casos de desproteccion...
			CasoDesproteccionDbActions casoDesproteccionDbActions = new CasoDesproteccionDbActions();
			ArrayList<CasoDesproteccionInfo> casosDesproteccionList =  casoDesproteccionDbActions.getAllCases();
			// Obtenemos el seguimiento de los casos...
			SeguimientoCasoDbActions seguimientoCasoDbActions = new SeguimientoCasoDbActions();
			ArrayList<SeguimientoCasoInfo> seguimientoCasosList = seguimientoCasoDbActions.getAllEventos();
			// Obenemos los datos de configuracion...
			SettingsDbActions settingsDbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			// Obtenemos las actividades de prevencion...
			CasoPrevencionDbActions  casoPrevencionDbActions = new CasoPrevencionDbActions();
			ArrayList<CasoPrevencionInfo> casoPrevencionList = casoPrevencionDbActions.getAllCases();
			// Obtenemos los casos de desproteccion...
			CasoDesproteccionSocialDbActions casoDesproteccionSocialDbActions = new CasoDesproteccionSocialDbActions();
			ArrayList<CasoDesproteccionSocialInfo> casoDesproteccionSocialInfoList =  casoDesproteccionSocialDbActions.getAllCases();

			// Creamos la info del backup...
			BackUpInfo backUpInfo = new BackUpInfo();
			Date date = TimeUtil.now();
			backUpInfo.setCodigo(settingsInfo.getCode());
			backUpInfo.setFecha(date);
			backUpInfo.setTime(date.getTime());
			Gson gson = new Gson();

			String settingsJson = gson.toJson(settingsInfo);
			String backUpJson = gson.toJson(backUpInfo);
			String casosJson = gson.toJson(casosDesproteccionList);
			String seguimientoJson = gson.toJson(seguimientoCasosList);
			String prevencionesJson = gson.toJson(casoPrevencionList);
			String casosSocialJson = gson.toJson(casoDesproteccionSocialInfoList);
			
			// Creamos el archivo comprimido...
			File settingsTemp = File.createTempFile("settings", ".temp");
			File casosDesproteccionTemp = File.createTempFile("casos", ".temp");
			File seguimientoCasoTemp = File.createTempFile("seguimiento", ".temp");
			File backUpInfoTemp = File.createTempFile("backUp", ".temp");
			File prevencionTemp = File.createTempFile("prevenciones", ".temp");
			File casoSocialTemp = File.createTempFile("casossocial", ".temp");

			// Settings...
			this.escribirAlArchivo(settingsTemp,settingsJson);
			// Casos...
			this.escribirAlArchivo(casosDesproteccionTemp,casosJson);
			// Seguimiento...
			this.escribirAlArchivo(backUpInfoTemp,backUpJson);
			// BackUp...
			this.escribirAlArchivo(seguimientoCasoTemp,seguimientoJson);
			// Prevencion...
			this.escribirAlArchivo(prevencionTemp, prevencionesJson);
			// Casos Social...
			this.escribirAlArchivo(casoSocialTemp, casosSocialJson);

			FileOutputStream dest = new 
					FileOutputStream(path+".zip");
			ZipOutputStream out = new ZipOutputStream(new 
					BufferedOutputStream(dest));
			//out.setMethod(ZipOutputStream.DEFLATED);

			// get a list of files from current directory
			this.addFilesToZip(settingsTemp,dest,out,"settings.json");
			this.addFilesToZip(casosDesproteccionTemp,dest,out,"casos.json");
			this.addFilesToZip(backUpInfoTemp,dest,out,"backup.json");
			this.addFilesToZip(seguimientoCasoTemp,dest,out,"seguimiento.json");
			this.addFilesToZip(prevencionTemp,dest,out,"prevenciones.json");
			this.addFilesToZip(casoSocialTemp,dest,out,"casossocial.json");
			out.close();


		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void escribirAlArchivo(File fileTemp, String json ){
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			// Settings...
			fw = new FileWriter(fileTemp);
			bw = new BufferedWriter(fw);
			bw.write(json);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
	public void addFilesToZip(File fileTemp, FileOutputStream dest,ZipOutputStream out, String fileName){
		try{
			byte data[] = new byte[BUFFER];
			FileInputStream fi = new FileInputStream(fileTemp);
			BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
			ZipEntry entry = new ZipEntry(fileName);
			out.putNextEntry(entry);
			int count;
			while((count = origin.read(data, 0, 
					BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();

		if(eventSource == this.diccionaryToggleNode){
			File file = new File("diccionario.pdf");
			HostServices hostServices = getHostServices();
			hostServices.showDocument(file.getAbsolutePath());
			/*
	        DocumentsModalDialog waitModalDialog = new DocumentsModalDialog(this.primaryStage);
			Stage waitDialogStage = waitModalDialog.buildModalDialogStage();
			waitDialogStage.show();
			 */
		}if(eventSource == this.exportDataToggleNode){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Exportar backup");
			File file = fileChooser.showSaveDialog(this.primaryStage);
			if (file != null) {
				try {
					this.generateBackup(file.getAbsolutePath());
					System.out.println(file.getAbsolutePath());
					MessageModalDialog messageModalDialog = new MessageModalDialog(this.primaryStage);
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "BackUp generado con éxito");
    				messageDialogStage.show();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		if(eventSource == this.userGuideToggleNode){
			File file = new File("manual.pdf");
			HostServices hostServices = getHostServices();
			hostServices.showDocument(file.getAbsolutePath());
			/*
	        DocumentsModalDialog waitModalDialog = new DocumentsModalDialog(this.primaryStage);
			Stage waitDialogStage = waitModalDialog.buildModalDialogStage();
			waitDialogStage.show();
			 */
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}
