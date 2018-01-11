/**
 * 
 */
package com.agt.client.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import  javafx.scene.control.TextArea;

import java.io.File;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javafx.scene.control.cell.PropertyValueFactory;

import com.agt.client.db.CasoDesproteccionDbActions;
import com.agt.client.db.CasoPrevencionDbActions;
import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.CasoDesproteccionInfo;
import com.agt.client.info.CasoPrevencionInfo;
import com.agt.client.info.CatalogInfo;
import com.agt.client.info.SettingsInfo;
import com.agt.util.CatalogUtil;
import com.agt.util.MaskFieldUtil;
import com.agt.util.PdfUtil;
import com.agt.util.TimeUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.event.Event;
import javafx.event.EventHandler;



/**
 * @author mlopez
 *
 */
public class CasosPrevencionAppPane implements EventHandler<ActionEvent>,ChangeListener<CasoPrevencionInfo>  {
	private Stage primaryStage;
	private BorderPane borderPane;
	@SuppressWarnings("restriction")

	private JFXTextField fechaTextField;
	private JFXTextField hourTextField;
	private JFXTextField tipoActividadTextField;
	private JFXTextField lugarActividadTextField;
	private JFXTextField presupuestoTextField;
	private JFXTextField institucionesTextField;
	private TextArea resultadoTextField;
	private JFXTextField objetivoTextField;
	private TextArea comentariosTextArea;
	private JFXTextField ninezFTextField;
	private JFXTextField ninezMTextField;
	private JFXTextField adolescentesFTextField;
	private JFXTextField adolescentesMTextField;
	private JFXTextField padreFTextField;
	private JFXTextField padreMTextField;
	private JFXTextField funcionariosFTextField;
	private JFXTextField funcionariosMTextField;
	private JFXTextField alcaldesFTextField;
	private JFXTextField alcaldesMTextField;
	private JFXTextField tecnicosMTextField;
	private JFXTextField tecnicosFTextField;
	private JFXTextField otrosFTextField;
	private JFXTextField otrosMTextField;
	private JFXTextField otrosTextField;
	private JFXButton saveButton;
	private JFXButton clearButton;
	private JFXButton newButton;
	private JFXButton editButton;
	private JFXButton tableButton;
	private BorderPane newCasesBorderPane;
	private BorderPane casoDesproteccionTablaBorderPane;
	private Boolean otraSituacionDesproteccionSelected = false;
	private TableView<CasoPrevencionInfo> casosPrevencionTableView;
	private ArrayList<CasoPrevencionInfo> casoPrevencionInfoList;
	private CasoPrevencionInfo currentCasoPrevencionInfo;
	private VBox infoCasoVBox;
	private JFXTextField fechaInicioJFXTextField;
	private JFXTextField fechaFinJFXTextField;
	private JFXButton searchButton;
	private JFXButton printButton;
	private JFXButton addTipoActividadButton;
	private JFXButton addParticipantesButton;
	
	public CasosPrevencionAppPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public BorderPane buildPane() {

		// Finally create the BorderPane base container...
		this.borderPane = new BorderPane();

		// Header Panel
		HBox titleHbox = new HBox();
		titleHbox.setPadding(new Insets(10,10,10,10));
		titleHbox.setSpacing(15.0);
		//titleHbox.setAlignment(Pos.CENTER_RIGHT);
		titleHbox.setStyle("-fx-background-color: #2CCFFF;");
		titleHbox.setPrefHeight(75.0);

		// Header del Panel
		Icon icon = new Icon(AwesomeIcon.EDIT,"2em",";","");
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setHeight(5);
		Label titleLabel = new Label("GESTIÓN DE ACCIONES DE PREVENCIÓN");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20.0));
		titleLabel.setStyle("-fx-text-fill: #ffffff;");
		titleLabel.setEffect(ds);
		titleLabel.setAlignment(Pos.CENTER_RIGHT);
		HBox titleLabelHBox = new HBox();
		titleLabelHBox.setAlignment(Pos.CENTER_RIGHT);
		titleLabelHBox.setPadding(new Insets(10,10,10,10));
		titleLabelHBox.getChildren().add(titleLabel);

		//Toolbar
		HBox toolbarHBox = new HBox();
		toolbarHBox.setPadding(new Insets(5,5,5,5));
		toolbarHBox.setSpacing(5);
		toolbarHBox.setAlignment(Pos.CENTER_LEFT);

		// Caso nuevo...
		Icon iconNew = new Icon(AwesomeIcon.PLUS,"4em",";","");
		Label newButtonLabel = new Label();
		newButtonLabel.setGraphic(iconNew);
		this.newButton = new JFXButton();
		this.newButton.setGraphic(newButtonLabel);
		this.newButton.setCursor(Cursor.HAND);
		this.newButton.setTooltip(new Tooltip("Ingresar nueva acción"));
		this.newButton.setOnAction(this);

		// Tabla de datos...
		Icon iconEdit = new Icon(AwesomeIcon.EDIT,"4em",";","");
		Label editButtonLabel = new Label();
		editButtonLabel.setGraphic(iconEdit);
		this.editButton = new JFXButton();
		this.editButton.setGraphic(editButtonLabel);
		this.editButton.setCursor(Cursor.HAND);
		this.editButton.setTooltip(new Tooltip("Editar información de la acción"));
		this.editButton.setOnAction(this);

		// Tabla de datos...
		Icon iconTable = new Icon(AwesomeIcon.TABLE,"4em",";","");
		Label tableButtonLabel = new Label();
		tableButtonLabel.setGraphic(iconTable);
		this.tableButton = new JFXButton();
		this.tableButton.setGraphic(tableButtonLabel);
		this.tableButton.setCursor(Cursor.HAND);
		this.tableButton.setTooltip(new Tooltip("Ver tabla de acciones"));
		this.tableButton.setOnAction(this);
		
		// Botón para impirmir...
		Icon iconPrint = new Icon(AwesomeIcon.PRINT,"4em",";","");
		Label printButtonLabel = new Label();
		printButtonLabel.setGraphic(iconPrint);
		this.printButton = new JFXButton();
		this.printButton.setGraphic(printButtonLabel);
		this.printButton.setCursor(Cursor.HAND);
		this.printButton.setTooltip(new Tooltip("Imprimir información del caso"));
		this.printButton.setOnAction(this);
		

		toolbarHBox.getChildren().addAll(this.newButton, this.editButton, this.tableButton, this.printButton);

		titleHbox.getChildren().add(toolbarHBox);
		titleHbox.getChildren().add(titleLabelHBox);

		// Main del Panel
		this.borderPane.setTop(titleHbox);

		// Constuimos los paneles....
		this.newCasesBorderPane = this.buildNewCasePane();
		this.casoDesproteccionTablaBorderPane = this.buildCasosPrevencionTablaBorderPane();
		//this.borderPane.setCenter(this.buildNewCasePane());

		return borderPane;
	}

	/*
	 * Este método construlle la pantalla principal para la creación de un nuevo caso.
	 */
	public BorderPane buildNewCasePane(){
		BorderPane mainBorderPane = new BorderPane();



		// Componente para la fecha del hecho...
		fechaTextField = new JFXTextField();
		fechaTextField.setPromptText("Fecha 01/01/2016");
		//fechaTextField.setPrefWidth(100);
		MaskFieldUtil.dateField(fechaTextField);

		hourTextField = new JFXTextField();
		hourTextField.setPromptText("Hora 00:00");
		MaskFieldUtil.hourField(hourTextField);
		
		// Botón para seleccionar tipo de actividad...
		Icon iconAddTipoActividad = new Icon(AwesomeIcon.PLUS,"2em",";","");
		Label addTipoActividadButtonLabel = new Label();
		addTipoActividadButtonLabel.setGraphic(iconAddTipoActividad);
		this.addTipoActividadButton = new JFXButton();
		this.addTipoActividadButton.setGraphic(addTipoActividadButtonLabel);
		this.addTipoActividadButton.setCursor(Cursor.HAND);
		this.addTipoActividadButton.setTooltip(new Tooltip("Seleccionar un tipo de actividad"));
		this.addTipoActividadButton.setOnAction(this);
		// Componente para ingresar el tipo de actividad...
		tipoActividadTextField = new JFXTextField();
		tipoActividadTextField.setPromptText("Ingrese el nombre de la actividad");
		tipoActividadTextField.setPrefWidth(275);
		tipoActividadTextField.setEditable(false);
		HBox tipoActividadHbox = new HBox();
		tipoActividadHbox.setSpacing(5);
		tipoActividadHbox.getChildren().addAll(this.tipoActividadTextField, this.addTipoActividadButton);

		// Componente para ingresar el tipo de actividad...
		lugarActividadTextField = new JFXTextField();
		lugarActividadTextField.setPromptText("Ingrese el lugar de la actividad");
		lugarActividadTextField.setPrefWidth(100);

		// Componente para ingresar el presupuesto...
		presupuestoTextField = new JFXTextField();
		presupuestoTextField.setPromptText("Ingrese la ejecución presupuestaria o gestión");
		presupuestoTextField.setPrefWidth(100);
		MaskFieldUtil.monetaryField(presupuestoTextField);

		// Componente para ingresar el resultado...
		resultadoTextField = new TextArea();
		resultadoTextField.setPrefRowCount(10);
		resultadoTextField.setPrefColumnCount(50);
		resultadoTextField.setWrapText(true);
		resultadoTextField.setPrefWidth(150);	
		
		// Componente para ingresar el objetivo...
		objetivoTextField = new JFXTextField();
		objetivoTextField.setPromptText("Ingrese el objetivo de la actividad");
		objetivoTextField.setPrefWidth(100);	
		
		

		// Botón para seleccionar participantes...
		Icon iconAddParticipantes = new Icon(AwesomeIcon.PLUS,"2em",";","");
		Label addParticipantesButtonLabel = new Label();
		addParticipantesButtonLabel.setGraphic(iconAddParticipantes);
		this.addParticipantesButton = new JFXButton();
		this.addParticipantesButton.setGraphic(addParticipantesButtonLabel);
		this.addParticipantesButton.setCursor(Cursor.HAND);
		this.addParticipantesButton.setTooltip(new Tooltip("Seleccionar un tipo de participantes"));
		this.addParticipantesButton.setOnAction(this);
		// Componente para ingresar el objetivo...
		institucionesTextField = new JFXTextField();
		institucionesTextField.setPromptText("Ingrese el nombre de las instituciones y organizaciones");
		institucionesTextField.setPrefWidth(275);
		institucionesTextField.setEditable(false);
		HBox participantesHbox = new HBox();
		participantesHbox.setSpacing(5);
		participantesHbox.getChildren().addAll(this.institucionesTextField, this.addParticipantesButton);

		// Componente para los comentarios...
		comentariosTextArea = new TextArea();
		comentariosTextArea.setPrefRowCount(10);
		comentariosTextArea.setPrefColumnCount(50);
		comentariosTextArea.setWrapText(true);
		comentariosTextArea.setPrefWidth(150);

		// Componentes para beneficiarios...
		ninezFTextField = new JFXTextField();
		ninezFTextField.setPromptText("F");
		ninezMTextField = new JFXTextField();
		ninezMTextField.setPromptText("M");
		MaskFieldUtil.numericField(ninezFTextField);
		MaskFieldUtil.numericField(ninezMTextField);
		HBox ninezHBox = new HBox();
		ninezHBox.setSpacing(5);
		ninezHBox.getChildren().addAll(new Label("Niños 0-12 años:"),ninezFTextField,ninezMTextField);

		adolescentesFTextField = new JFXTextField();
		adolescentesFTextField.setPromptText("F");
		adolescentesMTextField = new JFXTextField();
		adolescentesMTextField.setPromptText("M");
		MaskFieldUtil.numericField(adolescentesFTextField);
		MaskFieldUtil.numericField(adolescentesMTextField);
		HBox adolescentesHBox = new HBox();
		adolescentesHBox.setSpacing(5);
		adolescentesHBox.getChildren().addAll(new Label("Adolescentes 13-17 años:"),adolescentesFTextField,adolescentesMTextField);

		padreFTextField = new JFXTextField();
		padreFTextField.setPromptText("F");
		padreMTextField = new JFXTextField();
		padreMTextField.setPromptText("M");
		MaskFieldUtil.numericField(padreFTextField);
		MaskFieldUtil.numericField(padreMTextField);
		HBox padreHBox = new HBox();
		padreHBox.setSpacing(5);
		padreHBox.getChildren().addAll(new Label("Encargaos NNA:"),padreFTextField,padreMTextField);
		
		tecnicosFTextField = new JFXTextField();
		tecnicosFTextField.setPromptText("F");
		tecnicosMTextField = new JFXTextField();
		tecnicosMTextField.setPromptText("M");
		MaskFieldUtil.numericField(tecnicosFTextField);
		MaskFieldUtil.numericField(tecnicosMTextField);
		HBox tecnicosHBox = new HBox();
		tecnicosHBox.setSpacing(5);
		tecnicosHBox.getChildren().addAll(new Label("Técnicos:"),tecnicosFTextField,tecnicosMTextField);

		
		funcionariosFTextField = new JFXTextField();
		funcionariosFTextField.setPromptText("F");
		funcionariosMTextField = new JFXTextField();
		funcionariosMTextField.setPromptText("M");
		MaskFieldUtil.numericField(funcionariosFTextField);
		MaskFieldUtil.numericField(funcionariosMTextField);
		HBox funcionariosHBox = new HBox();
		funcionariosHBox.setSpacing(5);
		funcionariosHBox.getChildren().addAll(new Label("Funcionarios:"),funcionariosFTextField,funcionariosMTextField);

		alcaldesFTextField = new JFXTextField();
		alcaldesFTextField.setPromptText("F");
		alcaldesMTextField = new JFXTextField();
		alcaldesMTextField.setPromptText("M");
		MaskFieldUtil.numericField(alcaldesFTextField);
		MaskFieldUtil.numericField(alcaldesMTextField);
		HBox alcaldesHBox = new HBox();
		alcaldesHBox.setSpacing(5);
		alcaldesHBox.getChildren().addAll(new Label("Servidores Públicos y privados:"),alcaldesFTextField,alcaldesMTextField);

		otrosFTextField = new JFXTextField();
		otrosFTextField.setPromptText("F");
		otrosMTextField = new JFXTextField();
		otrosMTextField.setPromptText("M");
		MaskFieldUtil.numericField(otrosFTextField);
		MaskFieldUtil.numericField(otrosMTextField);
		HBox otrosHBox = new HBox();
		otrosHBox.setSpacing(5);
		otrosHBox.getChildren().addAll(new Label("Otros:"),otrosFTextField,otrosMTextField);

		// Componente para ingresar el resultado...
		otrosTextField = new JFXTextField();
		otrosTextField.setPromptText("Describa otros beneficiarios");
		otrosTextField.setPrefWidth(100);	

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(2);
		grid.setVgap(10);

		grid.add(new Label("Niñez 0-12 años:"), 0, 0);
		grid.add(ninezFTextField, 1, 0);
		grid.add(ninezMTextField, 2, 0);
		grid.add(new Label("Adolescentes 13-17 años:"), 0, 1);
		grid.add(adolescentesFTextField, 1, 1);
		grid.add(adolescentesMTextField, 2, 1);
		grid.add(new Label("Encargado NNA:"), 0, 2);
		grid.add(padreFTextField, 1, 2);
		grid.add(padreMTextField, 2, 2);
		grid.add(new Label("Tecnicos:"), 0, 3);
		grid.add(tecnicosFTextField, 1, 3);
		grid.add(tecnicosMTextField, 2, 3);
		grid.add(new Label("Funcionarios:"), 0, 4);
		grid.add(funcionariosFTextField, 1, 4);
		grid.add(funcionariosMTextField, 2, 4);
		grid.add(new Label("Servidores Públicos y Privados:"), 0, 5);
		grid.add(alcaldesFTextField, 1, 5);
		grid.add(alcaldesMTextField, 2, 5);
		grid.add(new Label("Otros:"), 0, 6);
		grid.add(otrosFTextField, 1, 6);
		grid.add(otrosMTextField, 2, 6);

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(5);
		dateHBox.getChildren().addAll(fechaTextField,hourTextField);
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
		toolbarHBox.getChildren().addAll(clearButton,saveButton);

		// Título del forumario
		Label datosVictimaLabel = new Label("Datos de la actividad");
		datosVictimaLabel.setFont(Font.font("Arial", 18));
		datosVictimaLabel.setStyle("-fx-text-fill: #848484");

		// Definimos la caja principal para el formulario...
		HBox mainFormHBox = new HBox();
		mainFormHBox.setSpacing(60);
		mainFormHBox.setPadding(new Insets(10,20,20,40));
		mainFormHBox.setPrefWidth(500);

		// Caja para los componentes de los datos de la víctima...
		VBox datosVictimaVBox = new VBox();
		datosVictimaVBox.setSpacing(10);
		datosVictimaVBox.getChildren().addAll(
				datosVictimaLabel,
				new Label("Fecha:"),dateHBox, 
				new Label("Actividad:"),tipoActividadHbox,
				new Label("Instituciones y Organizaciones:"),participantesHbox,
				new Label("Aldea/Comunidad:"),lugarActividadTextField,
				new Label("Objetivo:"),objetivoTextField,
				new Label("Presupuesto (Q):"),presupuestoTextField,
				//new Label("Principales Resultados:"),resultadoTextField,
				new Label("Observaciones:"),comentariosTextArea
				);

		// Título del forumario
		Label datosHechoLabel = new Label("Beneficiarios");
		datosHechoLabel.setFont(Font.font("Arial", 18));
		datosHechoLabel.setStyle("-fx-text-fill: #848484");

		// Caja para los componentes de los datos de la víctima...
		VBox datosHechoVBox = new VBox();
		datosHechoVBox.setSpacing(5);
		datosHechoVBox.getChildren().addAll(
				datosHechoLabel,				 
				grid,
				new Label("Describa otros"),otrosTextField,
				new Label("Principales Resultados:"),resultadoTextField,
				toolbarHBox
				);

		mainFormHBox.getChildren().addAll(datosVictimaVBox, datosHechoVBox);
		ScrollPane scrollPane = new ScrollPane(mainFormHBox);
		scrollPane.setFitToWidth(true);
		mainBorderPane.setCenter(scrollPane);
		return mainBorderPane;
	}

	public String validate(){

		String fecha = this.fechaTextField.getText();
		if(fecha.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la fecha de la actividad";
		}
		String hour = this.hourTextField.getText();
		if(hour.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					hourTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la hora de la actividad";
		}
		String date = fecha +" "+hour;
		if(TimeUtil.getDateTimeFormat(date) == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaTextField.requestFocus();
				}
			}); 
			return "Fecha y hora de la actividad no válida";
		}
		String tipoActividad = this.tipoActividadTextField.getText();
		if(tipoActividad.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					tipoActividadTextField.requestFocus();
				}
			}); 
			return "Debe definir el tipo de actividad";
		}
		
		String institucion = this.institucionesTextField.getText();
		if(institucion.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					institucionesTextField.requestFocus();
				}
			}); 
			return "Debe describir la Intitución y/o Organización";
		}
		
		String lugarActividad = this.lugarActividadTextField.getText();
		if(lugarActividad.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					lugarActividadTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el lugar donde se realizó la actividad";
		}
		
		String objetivo = this.objetivoTextField.getText();
		if(objetivo.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					objetivoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el objetivo de la actividad";
		}
		
		String presupuesto = this.presupuestoTextField.getText();
		if(presupuesto.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					presupuestoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el presupuesto ejecutado o gestionado";
		}
		String resultados = this.resultadoTextField.getText();
		if(resultados.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					resultadoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el resultado de la actividad";
		}
		String comentarios = this.comentariosTextArea.getText();
		if(comentarios.length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					comentariosTextArea.requestFocus();
				}
			}); 
			return "Debe ingresar comentarios y/o observaciones de la actividad";
		}
		String niniosF = this.ninezFTextField.getText();
		if(this.ninezFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ninezFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de niñas que fueron beneficiadas";
		}
		String niniosM = this.ninezMTextField.getText();
		if(this.ninezMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ninezMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de niños que fueron beneficiados";
		}
		String adolescentesF = this.adolescentesFTextField.getText();
		if(this.adolescentesFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					adolescentesFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de adolescentes mujeres que fueron beneficiadas";
		}
		String adolescentesM = this.adolescentesMTextField.getText();
		if(this.adolescentesMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					adolescentesMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de adolescentes hombres que fueron beneficiados";
		}
		String encargadosF = this.padreFTextField.getText();
		if(this.padreFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					padreFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de madres que fueron beneficiadas";
		}
		String encargadosM = this.padreMTextField.getText();
		if(this.padreMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					padreMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de padres que fueron beneficiados";
		}
		String funcionariosF = this.funcionariosFTextField.getText();
		if(this.funcionariosFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					funcionariosFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de funcionarios mujeres que fueron beneficiadas";
		}
		String funcionariosM = this.funcionariosMTextField.getText();
		if(this.funcionariosMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					funcionariosMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de funcionarios hombres que fueron beneficiados";
		}
		String servidoresF = this.alcaldesFTextField.getText();
		if(this.alcaldesFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					alcaldesFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de alcaldes mujeres que fueron beneficiadas";
		}
		String servidoresM = this.alcaldesMTextField.getText();
		if(this.alcaldesMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					alcaldesMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de alcaldes hombres que fueron beneficiados";
		}
		
		String otrosF = this.otrosFTextField.getText();
		if(this.otrosFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					otrosFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de otras mujeres que fueron beneficiadas";
		}
		String otrosM = this.otrosMTextField.getText();
		if(this.otrosMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					otrosMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de otros hombres que fueron beneficiados";
		}
		String tecnicosM = this.tecnicosMTextField.getText();
		if(this.tecnicosMTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					tecnicosMTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de Técnicos hombres que asistieron";
		}
		String tecnicosF = this.tecnicosFTextField.getText();
		if(this.tecnicosFTextField.getText().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					tecnicosFTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la cantidad de Técnicos mujeres que asistieron";
		}
		
		String otros = this.otrosTextField.getText();
		if(otrosF.length() > 0 && otrosM.length() > 0){
			Integer otrosFint = Integer.valueOf(otrosF);
			Integer otrosMint = Integer.valueOf(otrosM);
			Integer result = otrosFint + otrosMint;
			if(result > 0){
				if(this.otrosTextField.getText().length() == 0){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							otrosTextField.requestFocus();
						}
					}); 
					return "Debe definir el tipo de otras personas que fueron beneficiadas";
				}
			}
		}
		
		// LLenamos los campos
		if(this.currentCasoPrevencionInfo == null){
			this.currentCasoPrevencionInfo = new CasoPrevencionInfo();
			String id = UUID.randomUUID().toString();
			this.currentCasoPrevencionInfo.setId(id);
			SettingsDbActions dbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = dbActions.getSettingsInfo();
			this.currentCasoPrevencionInfo.setCodigoMunicipal(settingsInfo.getCode());
			this.currentCasoPrevencionInfo.setRecordDate(TimeUtil.now());
		}
		this.currentCasoPrevencionInfo.setFecha(fecha);
		this.currentCasoPrevencionInfo.setHora(hour);
		this.currentCasoPrevencionInfo.setActividad(tipoActividad);
		this.currentCasoPrevencionInfo.setInstitucion(institucion);
		this.currentCasoPrevencionInfo.setLugar(lugarActividad);
		this.currentCasoPrevencionInfo.setPresupuesto(presupuesto);
		this.currentCasoPrevencionInfo.setObjetivo(objetivo);
		this.currentCasoPrevencionInfo.setNiniosF(niniosF);
		this.currentCasoPrevencionInfo.setNiniosM(niniosM);
		this.currentCasoPrevencionInfo.setAdolescentesF(adolescentesF);
		this.currentCasoPrevencionInfo.setAdolescentesM(adolescentesM);
		this.currentCasoPrevencionInfo.setEncargadosF(encargadosF);
		this.currentCasoPrevencionInfo.setEncargadosM(encargadosM);
		this.currentCasoPrevencionInfo.setTecnicosF(tecnicosF);
		this.currentCasoPrevencionInfo.setTecnicosM(tecnicosM);
		this.currentCasoPrevencionInfo.setFuncionariosF(funcionariosF);
		this.currentCasoPrevencionInfo.setFuncionariosM(funcionariosM);
		this.currentCasoPrevencionInfo.setServidoresF(servidoresF);
		this.currentCasoPrevencionInfo.setServidoresM(servidoresM);
		this.currentCasoPrevencionInfo.setOtrosF(otrosF);
		this.currentCasoPrevencionInfo.setOtrosM(otrosM);
		this.currentCasoPrevencionInfo.setResultados(resultados);
		this.currentCasoPrevencionInfo.setObservaciones(comentarios);
		this.currentCasoPrevencionInfo.setOtros(otros);
		this.currentCasoPrevencionInfo.setLastUpdate(TimeUtil.now());
		return null;
	}

	public void clearNewCase(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentCasoPrevencionInfo = null;
				hourTextField.setText("");
				fechaTextField.setText("");
				tipoActividadTextField.setText("");
				lugarActividadTextField.setText("");
				presupuestoTextField.setText("");
				resultadoTextField.setText("");
				comentariosTextArea.setText("");
				ninezFTextField.setText("");
				ninezMTextField.setText("");
				adolescentesFTextField.setText("");
				adolescentesMTextField.setText("");
				padreFTextField.setText("");
				padreMTextField.setText("");
				funcionariosFTextField.setText("");
				funcionariosMTextField.setText("");
				alcaldesFTextField.setText("");
				alcaldesMTextField.setText("");
				objetivoTextField.setText("");
				institucionesTextField.setText("");
				otrosFTextField.setText("");
				otrosMTextField.setText("");
				otrosTextField.setText("");
				tecnicosMTextField.setText("");
				tecnicosFTextField.setText("");
			}
		}); 
	}


	/************************************************************/
	/*          EVENTOS DE LA TABLA DE CASOS                    */
	/************************************************************/

	public BorderPane buildCasosPrevencionTablaBorderPane(){
		BorderPane mainBorderPane = new BorderPane();
		HBox searchHBox = new HBox();
		searchHBox.setPadding(new Insets(5,5,5,5));
		searchHBox.setSpacing(5);
		this.fechaInicioJFXTextField = new JFXTextField();
		this.fechaInicioJFXTextField.setPromptText("dd/mm/yyyy");
		MaskFieldUtil.dateField(this.fechaInicioJFXTextField);
		this.fechaFinJFXTextField = new JFXTextField();
		this.fechaFinJFXTextField.setPromptText("dd/mm/yyyy");
		MaskFieldUtil.dateField(this.fechaFinJFXTextField);
		Icon iconSearch = new Icon(AwesomeIcon.SEARCH,"1em",";","");
		Label searchButtonLabel = new Label();
		searchButtonLabel.setGraphic(iconSearch);
		this.searchButton = new JFXButton();
		this.searchButton.setGraphic(searchButtonLabel);
		this.searchButton.setCursor(Cursor.HAND);
		this.searchButton.setTooltip(new Tooltip("Buscar"));
		this.searchButton.setOnAction(this);
		searchHBox.getChildren().addAll(this.fechaInicioJFXTextField,this.fechaFinJFXTextField,this.searchButton);

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(1,1,1,1));
		vBox.setSpacing(1);

		vBox.getChildren().addAll(searchHBox,this.createTable());
		mainBorderPane.setLeft(vBox);
		Label titleLabel = new Label("Información del Caso");
		this.infoCasoVBox = new VBox();
		this.infoCasoVBox.setSpacing(3);
		this.infoCasoVBox.setPadding(new Insets(10,10,10,10));
		ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
		scrollPane.setFitToWidth(true);
		mainBorderPane.setCenter(scrollPane);
		return mainBorderPane;
	}
	
	//Tabla de búsqueda....
		public BorderPane buildCasosPrevencionTablaBorderPane(Date fechaInicial, Date fechaFinal){
			BorderPane mainBorderPane = new BorderPane();
			HBox searchHBox = new HBox();
			searchHBox.setPadding(new Insets(5,5,5,5));
			searchHBox.setSpacing(5);
			this.fechaInicioJFXTextField = new JFXTextField();
			this.fechaInicioJFXTextField.setPromptText("dd/mm/yyyy");
			MaskFieldUtil.dateField(this.fechaInicioJFXTextField);
			this.fechaFinJFXTextField = new JFXTextField();
			this.fechaFinJFXTextField.setPromptText("dd/mm/yyyy");
			MaskFieldUtil.dateField(this.fechaFinJFXTextField);
			Icon iconSearch = new Icon(AwesomeIcon.SEARCH,"1em",";","");
			Label searchButtonLabel = new Label();
			searchButtonLabel.setGraphic(iconSearch);
			this.searchButton = new JFXButton();
			this.searchButton.setGraphic(searchButtonLabel);
			this.searchButton.setCursor(Cursor.HAND);
			this.searchButton.setTooltip(new Tooltip("Buscar"));
			this.searchButton.setOnAction(this);
			searchHBox.getChildren().addAll(this.fechaInicioJFXTextField,this.fechaFinJFXTextField,this.searchButton);

			VBox vBox = new VBox();
			vBox.setPadding(new Insets(1,1,1,1));
			vBox.setSpacing(1);

			vBox.getChildren().addAll(searchHBox,this.createTable(fechaInicial, fechaFinal));
			mainBorderPane.setLeft(vBox);
			Label titleLabel = new Label("Información del Caso");
			this.infoCasoVBox = new VBox();
			this.infoCasoVBox.setSpacing(3);
			this.infoCasoVBox.setPadding(new Insets(10,10,10,10));
			ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
			scrollPane.setFitToWidth(true);
			mainBorderPane.setCenter(scrollPane);
			return mainBorderPane;
		}

	public TableView<CasoPrevencionInfo> createTable() {
		if(this.casosPrevencionTableView != null){
			this.casosPrevencionTableView.getItems().clear();
		}
		this.casosPrevencionTableView = new TableView<CasoPrevencionInfo>();

		TableColumn<CasoPrevencionInfo, String> fechaColumn = new TableColumn<CasoPrevencionInfo, String>("Fecha");
		fechaColumn.setMinWidth(120);
		fechaColumn.setCellValueFactory(new PropertyValueFactory<CasoPrevencionInfo, String>("fecha"));
		TableColumn<CasoPrevencionInfo, String> nombreColumn = new TableColumn<CasoPrevencionInfo, String>("Actividad");
		nombreColumn.setMinWidth(280);
		nombreColumn.setCellValueFactory(new PropertyValueFactory<CasoPrevencionInfo, String>("actividad"));
		TableColumn<CasoPrevencionInfo, String> objetivoColumn = new TableColumn<CasoPrevencionInfo, String>("Objetivo");
		objetivoColumn.setMinWidth(280);
		objetivoColumn.setCellValueFactory(new PropertyValueFactory<CasoPrevencionInfo, String>("objetivo"));

		
		this.casosPrevencionTableView.getColumns().add(fechaColumn);
		this.casosPrevencionTableView.getColumns().add(nombreColumn);
		this.casosPrevencionTableView.getColumns().add(objetivoColumn);
		
		CasoPrevencionDbActions casoPrevencionDbActions = new CasoPrevencionDbActions();
		this.casoPrevencionInfoList = casoPrevencionDbActions.getAllCases();

		if(this.casosPrevencionTableView != null){
			if(this.casoPrevencionInfoList != null){
			this.casosPrevencionTableView.setItems(FXCollections.observableArrayList(this.casoPrevencionInfoList));
			}	
		}
		this.casosPrevencionTableView.getSelectionModel().selectedItemProperty().addListener(this);

		return this.casosPrevencionTableView;

	}
	
	public TableView<CasoPrevencionInfo> createTable(Date fechaInicial, Date fechaFinal) {
		if(this.casosPrevencionTableView != null){
			this.casosPrevencionTableView.getItems().clear();
		}
		this.casosPrevencionTableView = new TableView<CasoPrevencionInfo>();

		TableColumn<CasoPrevencionInfo, String> fechaColumn = new TableColumn<CasoPrevencionInfo, String>("Fecha");
		fechaColumn.setMinWidth(120);
		fechaColumn.setCellValueFactory(new PropertyValueFactory<CasoPrevencionInfo, String>("fecha"));
		TableColumn<CasoPrevencionInfo, String> nombreColumn = new TableColumn<CasoPrevencionInfo, String>("Actividad");
		nombreColumn.setMinWidth(280);
		nombreColumn.setCellValueFactory(new PropertyValueFactory<CasoPrevencionInfo, String>("actividad"));
		TableColumn<CasoPrevencionInfo, String> objetivoColumn = new TableColumn<CasoPrevencionInfo, String>("Objetivo");
		objetivoColumn.setMinWidth(280);
		objetivoColumn.setCellValueFactory(new PropertyValueFactory<CasoPrevencionInfo, String>("objetivo"));

		
		this.casosPrevencionTableView.getColumns().add(fechaColumn);
		this.casosPrevencionTableView.getColumns().add(nombreColumn);
		this.casosPrevencionTableView.getColumns().add(objetivoColumn);
		
		CasoPrevencionDbActions casoPrevencionDbActions = new CasoPrevencionDbActions();
		this.casoPrevencionInfoList = casoPrevencionDbActions.getCasosByDate(fechaInicial, fechaFinal);
		this.fechaInicioJFXTextField.setText(TimeUtil.getSimpleTimeFormat(fechaInicial));
		this.fechaFinJFXTextField.setText(TimeUtil.getSimpleTimeFormat(fechaFinal));

		if(this.casosPrevencionTableView != null){
			if(this.casoPrevencionInfoList != null){
			this.casosPrevencionTableView.setItems(FXCollections.observableArrayList(this.casoPrevencionInfoList));
			}	
		}
		this.casosPrevencionTableView.getSelectionModel().selectedItemProperty().addListener(this);

		return this.casosPrevencionTableView;

	}



	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();
		MessageModalDialog messageModalDialog = new MessageModalDialog(this.primaryStage);
		if(eventSource == this.newButton){
			this.clearNewCase();
			this.borderPane.setCenter(this.newCasesBorderPane);
		}
		if(eventSource == this.tableButton){
			this.casoDesproteccionTablaBorderPane = this.buildCasosPrevencionTablaBorderPane();
			this.borderPane.setCenter(this.casoDesproteccionTablaBorderPane);
		}
		if(eventSource == this.clearButton){
			this.clearNewCase();
		}
		if (eventSource == this.saveButton) {
			String errorMsg = this.validate();

			if(errorMsg == null){
				WaitModalDialog waitModalDialog = new WaitModalDialog(this.primaryStage);
				final Stage waitDialogStage = waitModalDialog.buildModalDialogStage(MessageDialogType.LOAD);
				Task<Boolean> task = new Task<Boolean>() {
					@Override public Boolean call() {
						CasoPrevencionDbActions casoPrevencionDbActions = new CasoPrevencionDbActions();
						Boolean save = 	casoPrevencionDbActions.saveCasoPrevencion(currentCasoPrevencionInfo);
						return save;
					}
				};
				task.setOnRunning((e) -> waitDialogStage.show());
				task.setOnSucceeded((e) -> {
					waitDialogStage.hide();
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Actividad guardada con éxito");
					messageDialogStage.show();
					Boolean returnValue = true;
					// process return value again in JavaFX thread
				});
				task.setOnFailed((e) -> {
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No pudo guardarse la actividad");
					waitDialogStage.hide();
					messageDialogStage.show();
					// eventual error handling by catching exceptions from task.get()  
				});
				new Thread(task).start();
			}else{

				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, errorMsg);
				messageDialogStage.show();
			}
		}
		
		if(eventSource == this.searchButton){

			String fechaInicial = this.fechaInicioJFXTextField.getText();
			String fechaFinal = this.fechaFinJFXTextField.getText();
			if(!fechaInicial.trim().equals("") || !fechaFinal.trim().equals("")){
				Date fechaIni = TimeUtil.getDateFormat(fechaInicial);
				Date fechaFin = TimeUtil.getDateFormat(fechaFinal);
				if(fechaIni != null && fechaFin != null){
					this.casoDesproteccionTablaBorderPane = this.buildCasosPrevencionTablaBorderPane(fechaIni, fechaFin);
				}else{
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe ingresar la fecha final y fecha inicial");
					messageDialogStage.show();
				}
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe ingresar la fecha final y fecha inicial");
				messageDialogStage.show();
			}
			this.borderPane.setCenter(this.casoDesproteccionTablaBorderPane);
		}
		if (eventSource == this.editButton) {
			this.borderPane.setCenter(this.newCasesBorderPane);
		}
		
		if(eventSource == this.printButton){
			if(this.currentCasoPrevencionInfo != null){
				FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Guardar Reporte");
	            
	            File file = fileChooser.showSaveDialog(this.primaryStage);
	            if (file != null) {
	                try {
	                	
	                	PdfUtil.generateReportPrevencion(this.currentCasoPrevencionInfo, file.getAbsolutePath());
	                	System.out.println(file.getAbsolutePath());
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Reporte generado con éxito");
	    				messageDialogStage.show();
	                } catch (Exception ex) {
	                	Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Error al generar el reporte");
	    				messageDialogStage.show();
	                    System.out.println(ex.getMessage());
	                }
	            }
			
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar una actividad para imprimir");
				messageDialogStage.show();
			}
		}
		if(eventSource == this.addTipoActividadButton){
			TipoActividadModalDialog tipoActividadModalDialog = new TipoActividadModalDialog(this.primaryStage, this);
			Stage tipoActividadModalDialogStage = tipoActividadModalDialog.buildModalDialogStage();
			tipoActividadModalDialogStage.show();
		}
		if(eventSource == this.addParticipantesButton){
			ParticipantesModalDialog participantesModalDialog = new ParticipantesModalDialog(this.primaryStage, this);
			Stage participantesModalDialogStage = participantesModalDialog.buildModalDialogStage();
			participantesModalDialogStage.show();
		}
	}
	
	public void loadInfo(){
		if(this.currentCasoPrevencionInfo != null){
			// Obtenemos la información del POJO...
			String id = this.currentCasoPrevencionInfo.getId();
			String codigoMunicipal = this.currentCasoPrevencionInfo.getCodigoMunicipal();
			String fecha = this.currentCasoPrevencionInfo.getFecha();
			String hora = this.currentCasoPrevencionInfo.getHora();
			String actividad = this.currentCasoPrevencionInfo.getActividad();
			String institucion = this.currentCasoPrevencionInfo.getInstitucion();
			String lugar = this.currentCasoPrevencionInfo.getLugar();
			String objetivo = this.currentCasoPrevencionInfo.getObjetivo();
			String presupuesto = this.currentCasoPrevencionInfo.getPresupuesto();
			String observaciones = this.currentCasoPrevencionInfo.getObservaciones();
			String resultados = this.currentCasoPrevencionInfo.getResultados();
			String niniosM = this.currentCasoPrevencionInfo.getNiniosM();
			String niniosF = this.currentCasoPrevencionInfo.getNiniosF();
			String adolescentesM = this.currentCasoPrevencionInfo.getAdolescentesM();
			String adolescentesF = this.currentCasoPrevencionInfo.getAdolescentesF();
			String encargadosM = this.currentCasoPrevencionInfo.getEncargadosM();
			String encargadosF = this.currentCasoPrevencionInfo.getEncargadosF();
			String tecnicosM = this.currentCasoPrevencionInfo.getTecnicosM();
			String tecnicosF = this.currentCasoPrevencionInfo.getTecnicosF();
			String funcionariosM = this.currentCasoPrevencionInfo.getFuncionariosM();
			String funcionariosF = this.currentCasoPrevencionInfo.getFuncionariosF();
			String servidoresM = this.currentCasoPrevencionInfo.getServidoresM();
			String servidoresF = this.currentCasoPrevencionInfo.getServidoresF();
			String otrosM = this.currentCasoPrevencionInfo.getOtrosM();
			String otrosF = this.currentCasoPrevencionInfo.getOtrosF();
			String otros = this.currentCasoPrevencionInfo.getOtros();
			
			// Seteamos los valores a los campos para la edición...
			this.fechaTextField.setText(fecha);
			this.hourTextField.setText(hora);
			this.tipoActividadTextField.setText(actividad);
			this.lugarActividadTextField.setText(lugar);
			this.presupuestoTextField.setText(presupuesto);
			this.institucionesTextField.setText(institucion);
			this.resultadoTextField.setText(resultados);
			this.objetivoTextField.setText(objetivo);
			this.comentariosTextArea.setText(observaciones);
			this.ninezFTextField.setText(niniosF);
			this.ninezMTextField.setText(niniosM);
			this.adolescentesFTextField.setText(adolescentesF);
			this.adolescentesMTextField.setText(adolescentesM);
			this.padreFTextField.setText(encargadosF);
			this.padreMTextField.setText(encargadosM);
			this.funcionariosFTextField.setText(funcionariosF);
			this.funcionariosMTextField.setText(funcionariosM);
			this.alcaldesFTextField.setText(servidoresF);
			this.alcaldesMTextField.setText(servidoresM);
			this.tecnicosMTextField.setText(tecnicosM);
			this.tecnicosFTextField.setText(tecnicosF);
			this.otrosFTextField.setText(otrosF);
			this.otrosMTextField.setText(otrosM);
			this.otrosTextField.setText(otros);
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Creamos el panel para el display de la información del caso...
					infoCasoVBox.getChildren().clear();
					Label titleLabel = new Label("Información de la actividad");
					titleLabel.setFont(Font.font("Arial", 18));
					titleLabel.setStyle("-fx-text-fill: #848484");
					
					Label fechaLabel = new Label("Fecha:");
					fechaLabel.setFont(Font.font("Arial", 12));
					fechaLabel.setStyle("-fx-text-fill: #848484");

					Label actividadLabel = new Label("Actividad:");
					actividadLabel.setFont(Font.font("Arial", 12));
					actividadLabel.setStyle("-fx-text-fill: #848484");
					
					Label institucionesLabel = new Label("Instituciones y Organizaciones:");
					institucionesLabel.setFont(Font.font("Arial", 12));
					institucionesLabel.setStyle("-fx-text-fill: #848484");
					
					Label lugarLabel = new Label("Municipio/Aldea/Comunidad:");
					lugarLabel.setFont(Font.font("Arial", 12));
					lugarLabel.setStyle("-fx-text-fill: #848484");
					
					Label objetivoLabel = new Label("Objetivo:");
					objetivoLabel.setFont(Font.font("Arial", 12));
					objetivoLabel.setStyle("-fx-text-fill: #848484");

					Label resultadosLabel = new Label("Resultados:");
					resultadosLabel.setFont(Font.font("Arial", 12));
					resultadosLabel.setStyle("-fx-text-fill: #848484");
					
					Label presupuestoLabel = new Label("Presupuesto:");
					presupuestoLabel.setFont(Font.font("Arial", 12));
					presupuestoLabel.setStyle("-fx-text-fill: #848484");
					
					Label comentariosLabel = new Label("Observaciones:");
					comentariosLabel.setFont(Font.font("Arial", 12));
					comentariosLabel.setStyle("-fx-text-fill: #848484");
					
					Label title2Label = new Label("Participantes");
					title2Label.setFont(Font.font("Arial", 18));
					title2Label.setStyle("-fx-text-fill: #848484");
					
					Label ninezLabel = new Label("Niñez:");
					ninezLabel.setFont(Font.font("Arial", 12));
					ninezLabel.setStyle("-fx-text-fill: #848484");
					
					Label adolescentesLabel = new Label("Adolescentes:");
					adolescentesLabel.setFont(Font.font("Arial", 12));
					adolescentesLabel.setStyle("-fx-text-fill: #848484");
					
					Label encargadosLabel = new Label("Encargados NNA:");
					encargadosLabel.setFont(Font.font("Arial", 12));
					encargadosLabel.setStyle("-fx-text-fill: #848484");
					
					Label tecnicosLabel = new Label("Tecnicos:");
					tecnicosLabel.setFont(Font.font("Arial", 12));
					tecnicosLabel.setStyle("-fx-text-fill: #848484");
					
					Label funcionariosLabel = new Label("Funcionarios:");
					funcionariosLabel.setFont(Font.font("Arial", 12));
					funcionariosLabel.setStyle("-fx-text-fill: #848484");
					
					Label servidoresLabel = new Label("Servidores Públicos y Privados:");
					servidoresLabel.setFont(Font.font("Arial", 12));
					servidoresLabel.setStyle("-fx-text-fill: #848484");
					
					Label otrosLabel = new Label("Otros:");
					otrosLabel.setFont(Font.font("Arial", 12));
					otrosLabel.setStyle("-fx-text-fill: #848484");
					
					Label otroLabel = new Label("Otro:");
					otroLabel.setFont(Font.font("Arial", 12));
					otroLabel.setStyle("-fx-text-fill: #848484");

					infoCasoVBox.getChildren().add(titleLabel);
					infoCasoVBox.getChildren().add(fechaLabel);
					String fechaValue = fecha +" "+hora;
					infoCasoVBox.getChildren().add(new Label(fechaValue));
					infoCasoVBox.getChildren().add(actividadLabel);
					infoCasoVBox.getChildren().add(new Label(actividad));
					infoCasoVBox.getChildren().add(institucionesLabel);
					infoCasoVBox.getChildren().add(new Label(institucion));
					infoCasoVBox.getChildren().add(lugarLabel);
					infoCasoVBox.getChildren().add(new Label(lugar));
					infoCasoVBox.getChildren().add(objetivoLabel);
					infoCasoVBox.getChildren().add(new Label(objetivo));
					infoCasoVBox.getChildren().add(resultadosLabel);
					infoCasoVBox.getChildren().add(new Label(resultados));
					infoCasoVBox.getChildren().add(presupuestoLabel);
					infoCasoVBox.getChildren().add(new Label(presupuesto));
					infoCasoVBox.getChildren().add(comentariosLabel);
					infoCasoVBox.getChildren().add(new Label(observaciones));
					infoCasoVBox.getChildren().add(title2Label);
					infoCasoVBox.getChildren().add(ninezLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+niniosM +" Mujeres: "+niniosF));
					infoCasoVBox.getChildren().add(adolescentesLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+adolescentesM +" Mujeres: "+adolescentesF));
					infoCasoVBox.getChildren().add(encargadosLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+encargadosM +" Mujeres: "+encargadosF));
					infoCasoVBox.getChildren().add(tecnicosLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+tecnicosM +" Mujeres: "+tecnicosF));
					infoCasoVBox.getChildren().add(funcionariosLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+funcionariosM +" Mujeres: "+funcionariosF));
					infoCasoVBox.getChildren().add(servidoresLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+servidoresM +" Mujeres: "+servidoresF));
					infoCasoVBox.getChildren().add(otrosLabel);
					infoCasoVBox.getChildren().add(new Label("Hombres: "+otrosM +" Mujeres: "+otrosF));
					infoCasoVBox.getChildren().add(otroLabel);
					infoCasoVBox.getChildren().add(new Label(otros));
				}
			});
		}
	}
	
	@Override
	public void changed(ObservableValue<? extends CasoPrevencionInfo> arg0, CasoPrevencionInfo arg1,
			CasoPrevencionInfo arg2) {
		this.currentCasoPrevencionInfo = arg2;
		this.loadInfo();

	}

	public JFXTextField getTipoActividadTextField() {
		return tipoActividadTextField;
	}

	public void setTipoActividadTextField(JFXTextField tipoActividadTextField) {
		this.tipoActividadTextField = tipoActividadTextField;
	}

	public JFXTextField getInstitucionesTextField() {
		return institucionesTextField;
	}

	public void setInstitucionesTextField(JFXTextField institucionesTextField) {
		this.institucionesTextField = institucionesTextField;
	}
	
	
}
