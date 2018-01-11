/**
 * 
 */
package com.agt.client.gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.CatalogInfo;
import com.agt.client.info.SettingsInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import javafx.concurrent.Task;

/**
 * @author mlopez
 *
 */
public class SettingsAppPane implements EventHandler<ActionEvent> {
	private Stage primaryStage;
	private BorderPane borderPane;

	private JFXTextField codigoTextField;
	private JFXTextField nombreTextField;
	private JFXTextField alcaldeTextField;
	private JFXTextField periodoTextField;
	private JFXTextField sloganTextField;
	private JFXTextField direccionTextField;
	private JFXTextField nombreTecnicoTextField;
	private JFXTextField telefonoTecnicoTextField;
	private JFXTextField emailTecnicoTextField;
	private JFXTextField cargoTecnicoTextField;
	private JFXTextField telefonoMunicipalidadTextField;
	private JFXTextField emailMunicipalidadTextField;
	private JFXComboBox<CatalogInfo> departamentosComboBox;
	private JFXComboBox<CatalogInfo> municipiosComboBox;
	private JFXComboBox<CatalogInfo> institucionesComboBox;
	private JFXButton saveButton;
	private JFXButton clearButton;
	private SettingsInfo currentSettingsInfo = null;

	public SettingsAppPane(Stage primaryStage) {
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
		Icon icon = new Icon(AwesomeIcon.COGS,"2em",";","");
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setHeight(5);
		Label titleLabel = new Label("CONFIGURACIÓN DE INFORMACIÓN DE LA MUNICIPALIDAD");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20.0));
		titleLabel.setStyle("-fx-text-fill: #ffffff;");
		titleLabel.setEffect(ds);
		titleHbox.getChildren().add(icon);
		titleHbox.getChildren().add(titleLabel);

		// Main del Panel
		this.borderPane.setTop(titleHbox);
		this.borderPane.setCenter(this.buildNewCasePane());

		// Cargamos los datos al panel...
		this.loadSettingsInfo();

		return borderPane;
	}

	/*
	 *  Método para gargar la infomación de la configuración...
	 */
	public void loadSettingsInfo(){
		SettingsDbActions settingsDbActions = new SettingsDbActions();
		SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();

		if(settingsInfo != null){
			String code = settingsInfo.getCode();
			System.out.println(code);
			String nombre = settingsInfo.getNombre();
			String direccion = settingsInfo.getDireccion();
			String departamentoId = settingsInfo.getDepartamento();
			String departamentoDescr = settingsInfo.getDepartamentoDescr();
			CatalogInfo departamentoInfo = new CatalogInfo(departamentoId,departamentoDescr);
			String municipioId = settingsInfo.getMunicipio();
			String municipioDescr = settingsInfo.getMunicipioDescr();
			String municipioFk = settingsInfo.getMunicipioFk();
			CatalogInfo municipioInfo = new CatalogInfo(municipioId, municipioDescr, municipioFk);
			String nombreTecnico = settingsInfo.getTecnico();
			String telefono = settingsInfo.getTelefono();
			String email = settingsInfo.getEmail();
			String cargo = settingsInfo.getCargo();
			String emailMuni = settingsInfo.getEmailMunicipalidad();
			String telefonoMuni = settingsInfo.getTelefonoMunicipalidad();
			String alcalde = settingsInfo.getAlcalde();
			String slogan = settingsInfo.getSlogan();
			String periodo = settingsInfo.getPeriodo();
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					codigoTextField.setText(code);
					nombreTextField.setText(nombre);
					direccionTextField.setText(direccion);
					departamentosComboBox.getSelectionModel().select(departamentoInfo);
					municipiosComboBox.getSelectionModel().select(municipioInfo);
					nombreTecnicoTextField.setText(nombreTecnico);
					telefonoTecnicoTextField.setText(telefono);
					emailTecnicoTextField.setText(email);
					cargoTecnicoTextField.setText(cargo);
					emailMunicipalidadTextField.setText(emailMuni);
					telefonoMunicipalidadTextField.setText(telefonoMuni);
					alcaldeTextField.setText(alcalde);
					periodoTextField.setText(periodo);
					sloganTextField.setText(slogan);
				}
			});
		}
	}

	/*
	 * Este método construlle la pantalla principal para el ingreso de la configuración.
	 */
	public BorderPane buildNewCasePane(){
		BorderPane mainBorderPane = new BorderPane();
		
		// Componente para ingresar el alcalde municipal...
		alcaldeTextField = new JFXTextField();
		alcaldeTextField.setPromptText("Ingrese el nombre del alcalde actual de la Municipalidad");
		alcaldeTextField.setPrefWidth(100);

		// Componente para ingresar el alcalde municipal...
		periodoTextField = new JFXTextField();
		periodoTextField.setPromptText("Ingrese el peropdo del alcalde actual ej. 2016-2020");
		periodoTextField.setPrefWidth(100);
		
		// Componente para ingresar el codigo municipal...
		codigoTextField = new JFXTextField();
		codigoTextField.setPromptText("Ingrese el código asignado a la Municipalidad");
		codigoTextField.setPrefWidth(100);
		
		// Componente para ingresar el eslogan municipal...
		sloganTextField = new JFXTextField();
		sloganTextField.setPromptText("Ingrese el eslogan de la Municipalidad");
		sloganTextField.setPrefWidth(100);

		// Componente para ingresar el nombre...
		nombreTextField = new JFXTextField();
		nombreTextField.setPromptText("Ingrese el nombre/descripción de la Municipalidad");
		nombreTextField.setPrefWidth(100);

		// Componente para ingresar la dirección...
		direccionTextField = new JFXTextField();
		direccionTextField.setPromptText("Ingrese la dirección de la Municipalidad");
		direccionTextField.setPrefWidth(100);

		// Componente para ingresar el teléfono de la municipalidad...
		telefonoMunicipalidadTextField = new JFXTextField();
		telefonoMunicipalidadTextField.setPromptText("Ingrese el teléfono de la Municipalidad");
		telefonoMunicipalidadTextField.setPrefWidth(100);

		// Componente para ingresar el email de la municipalidad...
		emailMunicipalidadTextField = new JFXTextField();
		emailMunicipalidadTextField.setPromptText("Ingrese el email de la Municipalidad");
		emailMunicipalidadTextField.setPrefWidth(100);

		// Componente para los municipios..
		municipiosComboBox = new  JFXComboBox<CatalogInfo>();
		municipiosComboBox.setPromptText("Seleccione el municipio");
		municipiosComboBox.setPrefWidth(400);

		// Componente para los departamentos...
		ObservableList<CatalogInfo> departamentosOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getDepartamentosInfoList());
		departamentosComboBox = new  JFXComboBox<CatalogInfo>(departamentosOptions);
		departamentosComboBox.setPromptText("Seleccione el departamento");
		departamentosComboBox.setPrefWidth(400);
		departamentosComboBox.valueProperty().addListener(new ChangeListener<CatalogInfo>() {
			@Override public void changed(ObservableValue ov, CatalogInfo t, CatalogInfo t1) {	             
				final String idDepartamento = t1.getId();
				System.out.println(idDepartamento);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ObservableList<CatalogInfo> municipiosOptions = 
								FXCollections.observableArrayList(
										DashboardGlobalInfo.getMunicipioPorDepartamento(idDepartamento));
						municipiosComboBox.setItems(municipiosOptions);
					}
				});
			}    
		});


		// Componente para el nombre del técnico..
		nombreTecnicoTextField = new JFXTextField();
		nombreTecnicoTextField.setPromptText("Ingrese el nombre del Técnico Municipal");
		nombreTecnicoTextField.setPrefWidth(100);

		// Componente para el teléfono del técnico..
		telefonoTecnicoTextField = new JFXTextField();
		telefonoTecnicoTextField.setPromptText("Ingrese el número de teléfono del Técnico Municipal");
		telefonoTecnicoTextField.setPrefWidth(100);

		// Componente para el email del técnico...
		emailTecnicoTextField = new JFXTextField();
		emailTecnicoTextField.setPromptText("Ingrese el correo electrónico del Técnico Municipal");
		emailTecnicoTextField.setPrefWidth(100);

		// Componente para cargo del técnico...
		cargoTecnicoTextField = new JFXTextField();
		cargoTecnicoTextField.setPromptText("Ingrese el nombre del cargo del Técnico Municipal");
		cargoTecnicoTextField.setPrefWidth(100);

		// Componente para las instituciones de referencia...
		ObservableList<CatalogInfo> institucionOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getInstitucionesInfoList());
		institucionesComboBox = new  JFXComboBox<CatalogInfo>(institucionOptions);
		institucionesComboBox.setPromptText("Seleccione la institución");
		institucionesComboBox.setPrefWidth(400);

		// Boton guardar...
		saveButton = new JFXButton("Guardar".toUpperCase());
		saveButton.getStyleClass().add("button-raised");
		saveButton.setCursor(Cursor.HAND);
		saveButton.setOnAction(this);
		// Boton limpiar...
		clearButton = new JFXButton("Limpiar campos".toUpperCase());
		clearButton.getStyleClass().add("button-raised-clear");
		clearButton.setCursor(Cursor.HAND);
		clearButton.setOnAction(this);

		// Toolbar...
		HBox toolbarHBox = new HBox();
		toolbarHBox.setSpacing(5);
		toolbarHBox.getChildren().addAll(saveButton);

		// Título del forumario
		Label datosVictimaLabel = new Label("Datos de la Municipalidad");
		datosVictimaLabel.setFont(Font.font("Arial", 18));
		datosVictimaLabel.setStyle("-fx-text-fill: #848484");

		// Definimos la caja principal para el formulario...
		HBox mainFormHBox = new HBox();
		mainFormHBox.setSpacing(60);
		mainFormHBox.setPadding(new Insets(30,20,20,40));
		mainFormHBox.setPrefWidth(500);

		// Caja para los componentes de los datos de la víctima...
		VBox datosVictimaVBox = new VBox();
		datosVictimaVBox.setSpacing(10);
		datosVictimaVBox.getChildren().addAll(
				datosVictimaLabel,
				new Label("Código:"),codigoTextField, 
				new Label("Nombre:"),nombreTextField,
				new Label("Dirección:"),direccionTextField,
				new Label("Teléfono:"),telefonoMunicipalidadTextField,
				new Label("Email:"),emailMunicipalidadTextField,
				new Label("Departamento:"),departamentosComboBox,
				new Label("Municipio:"),municipiosComboBox,
				new Label("Alcalde:"),alcaldeTextField,
				new Label("Período:"),periodoTextField,
				new Label("Eslogan:"),sloganTextField

				);

		// Título del forumario
		Label datosHechoLabel = new Label("Datos del Técnico Municipal");
		datosHechoLabel.setFont(Font.font("Arial", 18));
		datosHechoLabel.setStyle("-fx-text-fill: #848484");

		// Caja para los componentes de los datos de la víctima...
		VBox datosHechoVBox = new VBox();
		datosHechoVBox.setSpacing(10);
		datosHechoVBox.getChildren().addAll(
				datosHechoLabel,				 
				new Label("Técnico Municipal:"),nombreTecnicoTextField,
				new Label("Teléfono:"),telefonoTecnicoTextField,
				new Label("Email:"),emailTecnicoTextField,
				new Label("Cargo:"),cargoTecnicoTextField,
				toolbarHBox
				);

		mainFormHBox.getChildren().addAll(datosVictimaVBox, datosHechoVBox);
		ScrollPane scrollPane = new ScrollPane(mainFormHBox);
		scrollPane.setFitToWidth(true);
		mainBorderPane.setCenter(scrollPane);
		
		return mainBorderPane;
	}

	public String validate(){
		String codigo = this.codigoTextField.getText();
		CatalogInfo departamentoInfo = this.departamentosComboBox.getValue();
		CatalogInfo municipioInfo = this.municipiosComboBox.getValue();
		String nombre = this.nombreTextField.getText();
		String direccion = this.direccionTextField.getText();
		String nombreTecnico = this.nombreTecnicoTextField.getText();
		String telefono = this.telefonoTecnicoTextField.getText();
		String email = this.emailTecnicoTextField.getText();
		String cargo = this.cargoTecnicoTextField.getText();
		String emailMuni = this.emailMunicipalidadTextField.getText();
		String telefonoMuni = this.telefonoMunicipalidadTextField.getText();
	
		if(codigo.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					codigoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el código asignado a la Municipalidad";
		}
		if(nombre.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					nombreTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el nombre de la Municipalidad";
		}
		if(direccion.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					direccionTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la dirección de la Municipalidad";
		}
		if(telefonoMuni.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					telefonoMunicipalidadTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el teléfono de la Municipalidad";
		}
		if(emailMuni.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					emailMunicipalidadTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el email de la Municipalidad";
		}
		if(departamentoInfo == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					departamentosComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar el departamento donde se encuentra Municipalidad";
		}
		if(municipioInfo == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					municipiosComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar el municipio donde se encuentra Municipalidad";
		}
		if(nombreTecnico.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					nombreTecnicoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el nombre del Técnico Municipal";
		}
		if(telefono.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					telefonoTecnicoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el número telefónico del Técnico Municipal";
		}
		if(email.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					emailTecnicoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el correo electrónico del Técnico Municipal";
		}
		if(cargo.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					cargoTecnicoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el cargo del Técnico Municipal";
		}
		String alcalde = this.alcaldeTextField.getText();
		String periodo = this.periodoTextField.getText();
		String slogan = this.sloganTextField.getText();
		
		this.currentSettingsInfo = new SettingsInfo();
		this.currentSettingsInfo.setCode(codigo.trim());
		this.currentSettingsInfo.setDepartamento(departamentoInfo.getId());
		this.currentSettingsInfo.setDepartamentoDescr(departamentoInfo.getDescr());
		this.currentSettingsInfo.setMunicipio(municipioInfo.getId());
		this.currentSettingsInfo.setMunicipioDescr(municipioInfo.getDescr());
		this.currentSettingsInfo.setMunicipioFk(municipioInfo.getFk());
		this.currentSettingsInfo.setNombre(nombre.trim());
		this.currentSettingsInfo.setDireccion(direccion.trim());
		this.currentSettingsInfo.setTecnico(nombreTecnico.trim());
		this.currentSettingsInfo.setTelefono(telefono.trim());
		this.currentSettingsInfo.setEmail(email.trim());
		this.currentSettingsInfo.setCargo(cargo.trim());
		this.currentSettingsInfo.setEmailMunicipalidad(emailMuni.trim());
		this.currentSettingsInfo.setTelefonoMunicipalidad(telefonoMuni.trim());
		this.currentSettingsInfo.setAlcalde(alcalde);
		this.currentSettingsInfo.setPeriodo(periodo);
		this.currentSettingsInfo.setSlogan(slogan);
		
		return null;
	}

	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();
		if (eventSource == this.saveButton) {
			String errorMsg = this.validate();
			MessageModalDialog messageModalDialog = new MessageModalDialog(this.primaryStage);
			if(errorMsg == null){
				WaitModalDialog waitModalDialog = new WaitModalDialog(this.primaryStage);
				final Stage waitDialogStage = waitModalDialog.buildModalDialogStage(MessageDialogType.LOAD);
				Task<Boolean> task = new Task<Boolean>() {
					@Override public Boolean call() {
						SettingsDbActions settingsDbActions = new SettingsDbActions();
						Boolean save = 	settingsDbActions.saveSettings(currentSettingsInfo);
						return save;
					}
				};
				task.setOnRunning((e) -> waitDialogStage.show());
				task.setOnSucceeded((e) -> {
					waitDialogStage.hide();
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Configuración guardada con éxito");
					messageDialogStage.show();
					Boolean returnValue = true;
					// process return value again in JavaFX thread
				});
				task.setOnFailed((e) -> {
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No pudo guardarse la configuración");
					messageDialogStage.show();
					// eventual error handling by catching exceptions from task.get()  
				});
				new Thread(task).start();
			}else{

				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, errorMsg);
				messageDialogStage.show();
			}
		}
	}

}
