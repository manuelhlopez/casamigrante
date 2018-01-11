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
import javafx.scene.control.TextArea;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

import java.io.File;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.cell.PropertyValueFactory;

import com.agt.client.db.CasoDesproteccionDbActions;
import com.agt.client.db.PersonaDbActions;
import com.agt.client.db.SeguimientoCasoDbActions;
import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.CasoDesproteccionInfo;
import com.agt.client.info.CatalogInfo;
import com.agt.client.info.PersonaInfo;
import com.agt.client.info.SeguimientoCasoInfo;
import com.agt.client.info.SettingsInfo;
import com.agt.util.CatalogUtil;
import com.agt.util.MaskFieldUtil;
import com.agt.util.PdfUtil;
import com.agt.util.TimeUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;



/**
 * @author mlopez
 *
 */
public class PersonasAppPane implements EventHandler<ActionEvent>,ChangeListener<PersonaInfo>  {
	private Stage primaryStage;
	private BorderPane borderPane;
	
	// Form migrante
	private JFXTextField nombreTextField;
	private JFXTextField addressTextField;
	private JFXTextField fechaNacimientoTextField;
	private JFXTextField numeroTelefonicoTextField;
	private JFXTextField emailTextField;
	private JFXRadioButton maleRadioButton;
	private JFXRadioButton femaleRadioButton;
	private JFXComboBox<CatalogInfo> nacionalidadComboBox;
	private JFXComboBox<String> estadoCivilComboBox;
	private JFXTextField edadTextField;
	private JFXTextField hijosTextFiled;
	private JFXComboBox<String> escolaridadComboBox;
	private JFXCheckBox lgbtiChecBox;
	private JFXRadioButton transRadioButton;
	private JFXRadioButton gayRadioButton;
	private JFXRadioButton lesvianaRadioButton;
	private JFXRadioButton bisexualRadioButton;
	private JFXButton saveButton;
	private JFXButton clearButton;
	
	private BorderPane newCasesBorderPane;
	private BorderPane personaTablaBorderPane;
	private BorderPane seguimientoBorderPane;
	
	// Toolbar general
	private JFXButton newButton;
	private JFXButton editButton;
	private JFXButton tableButton;
	private JFXButton printButton;
	private JFXButton serviciosButton;
	private JFXButton perfilPsicoSocialButton;
	private JFXButton perfilJuridicoButton;
	private JFXButton relacionFamiliarButton;
	
	// Persona seleccionada....
	private TableView<PersonaInfo> personaTableView;
	private ArrayList<PersonaInfo> personaInfoList;
	private PersonaInfo currentPersonaInfo = null;
	private VBox infoCasoVBox;
	
	// Campos seguimiento...
	private JFXTextField nombreBusquedaTextField;
	private JFXButton searchButton;
	private JFXComboBox<CatalogInfo> estadosComboBox;
	private JFXTextField fechaSeguimientoTextField;
	private JFXTextField horaSeguimientoTextField;
	private TextArea observacionesTextArea;
	private JFXButton saveSeguimientoButton;
	private SeguimientoCasoInfo currentSeguimientoInfo = null;
	private VBox seguimientoInfoPanelVBox = null;
	private ArrayList<CatalogInfo> necesidadProteccionInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> motivoMigracionInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> violenciaInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> delitosInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> actitudInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> areaVulneravildiadInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> sintomaInfoList = new ArrayList<CatalogInfo>();
	private ArrayList<CatalogInfo> intervencionPsicologicaInfoList = new ArrayList<CatalogInfo>();
	
	// Campos evaluacion juridica
	private JFXTextField actoresTextField;
	private JFXTextField oficinaDerivacionTextField;
	private JFXTextField asuntoTextField;
	private JFXTextField compromisosTextField;
	private JFXTextField instanciaTextField;
	private JFXTextField resultadosTextField;
	private TextArea observacionesJuridicoTextArea;
	
	// Campos evaluación psicosocial
	private JFXComboBox<String> categoricaPoblacionComboBox;
	private JFXComboBox<CatalogInfo> destinoFinalComboBox;
	private JFXComboBox<String> victimaComboBox;
	private ListView<String> necesidadesProteccionListView;
	private ListView<String> motivoMigracionListView;
	private ListView<String> violenciaListView;
	private ListView<String> delitosListView;
	private JFXComboBox<String> situacionMigratoriaComboBox;
	private JFXTextField especificacionMigracionTextField;
	private ListView<String> motivoDeportacionComboBox;
	
	//Psicologico
	private ListView<String> intervencionComboBox;
	private TextArea observacionesPsicosocialTextArea;
	private ListView<String> actitudListView;
	private ListView<String> areaVulneravildiadListView;
	private ListView<String> sintomaListView;
	private ListView<String> intervencionPsicologicaListView;
	
	
	//Relación familiar
	private JFXComboBox<String> parentescoComboBox;
	
	public PersonasAppPane(Stage primaryStage) {
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
		Label titleLabel = new Label("GESTIÓN DE INFORMACION DE EXPEDIENTES DE PERSONAS");
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
		this.newButton.setTooltip(new Tooltip("Ingresar nuevo expediente"));
		this.newButton.setOnAction(this);

		// Tabla de datos...
		Icon iconEdit = new Icon(AwesomeIcon.EDIT,"4em",";","");
		Label editButtonLabel = new Label();
		editButtonLabel.setGraphic(iconEdit);
		this.editButton = new JFXButton();
		this.editButton.setGraphic(editButtonLabel);
		this.editButton.setCursor(Cursor.HAND);
		this.editButton.setTooltip(new Tooltip("Editar información del expediente"));
		this.editButton.setOnAction(this);

		// Tabla de datos...
		Icon iconTable = new Icon(AwesomeIcon.TABLE,"4em",";","");
		Label tableButtonLabel = new Label();
		tableButtonLabel.setGraphic(iconTable);
		this.tableButton = new JFXButton();
		this.tableButton.setGraphic(tableButtonLabel);
		this.tableButton.setCursor(Cursor.HAND);
		this.tableButton.setTooltip(new Tooltip("Ver tabla de personas"));
		this.tableButton.setOnAction(this);

		// Servicios...
		Icon iconSeguimiento = new Icon(AwesomeIcon.CHECK_SQUARE,"4em",";","");
		Label segumientoButtonLabel = new Label();
		segumientoButtonLabel.setGraphic(iconSeguimiento);
		this.serviciosButton = new JFXButton();
		this.serviciosButton.setGraphic(segumientoButtonLabel);
		this.serviciosButton.setCursor(Cursor.HAND);
		this.serviciosButton.setTooltip(new Tooltip("Servicios"));
		this.serviciosButton.setOnAction(this);

		// Perfil Psicosocial...
		Icon iconPerfilSocial = new Icon(AwesomeIcon.LIST_ALT,"4em",";","");
		Label perfilSocialLabel = new Label();
		perfilSocialLabel.setGraphic(iconPerfilSocial);
		this.perfilPsicoSocialButton = new JFXButton();
		this.perfilPsicoSocialButton.setGraphic(perfilSocialLabel);
		this.perfilPsicoSocialButton.setCursor(Cursor.HAND);
		this.perfilPsicoSocialButton.setTooltip(new Tooltip("Perfil Psicosocial"));
		this.perfilPsicoSocialButton.setOnAction(this);

		// Perfil Psicosocial...
		Icon iconPerfilJuridico = new Icon(AwesomeIcon.LEGAL,"4em",";","");
		Label perfilJuridicoLabel = new Label();
		perfilJuridicoLabel.setGraphic(iconPerfilJuridico);
		this.perfilJuridicoButton = new JFXButton();
		this.perfilJuridicoButton.setGraphic(perfilJuridicoLabel);
		this.perfilJuridicoButton.setCursor(Cursor.HAND);
		this.perfilJuridicoButton.setTooltip(new Tooltip("Perfil Juridico"));
		this.perfilJuridicoButton.setOnAction(this);

		// Relacion familiar...
		Icon iconRelacionFamiliar = new Icon(AwesomeIcon.GROUP,"4em",";","");
		Label labelRelacionFamiliar = new Label();
		labelRelacionFamiliar.setGraphic(iconRelacionFamiliar);
		this.relacionFamiliarButton = new JFXButton();
		this.relacionFamiliarButton.setGraphic(labelRelacionFamiliar);
		this.relacionFamiliarButton.setCursor(Cursor.HAND);
		this.relacionFamiliarButton.setTooltip(new Tooltip("Relaciones familiares"));
		this.relacionFamiliarButton.setOnAction(this);

		// Botón para impirmir...
		Icon iconPrint = new Icon(AwesomeIcon.PRINT,"4em",";","");
		Label printButtonLabel = new Label();
		printButtonLabel.setGraphic(iconPrint);
		this.printButton = new JFXButton();
		this.printButton.setGraphic(printButtonLabel);
		this.printButton.setCursor(Cursor.HAND);
		this.printButton.setTooltip(new Tooltip("Imprimir información del expediente"));
		this.printButton.setOnAction(this);

		toolbarHBox.getChildren().addAll(this.newButton, this.tableButton, this.editButton, this.perfilPsicoSocialButton, this.perfilJuridicoButton, this.serviciosButton, this.relacionFamiliarButton, this.printButton);

		titleHbox.getChildren().add(toolbarHBox);
		titleHbox.getChildren().add(titleLabelHBox);

		// Main del Panel
		this.borderPane.setTop(titleHbox);

		// Constuimos los paneles....
		this.newCasesBorderPane = this.buildNewCasePane();
		this.personaTablaBorderPane = this.buildCasosDesproteccionTablaBorderPane();
	
		return borderPane;
	}

	/*
	 * Este método construlle la pantalla principal para la creación de un nuevo caso.
	 */
	public BorderPane buildNewCasePane(){
		BorderPane mainBorderPane = new BorderPane();

		
		// Componente para las instituciones de referencia...
		ObservableList<CatalogInfo> nacionalidadOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getNacionalidadInfoList());
		nacionalidadComboBox = new  JFXComboBox<CatalogInfo>(nacionalidadOptions);
		nacionalidadComboBox.setPromptText("Seleccione la nacionalidad");
		nacionalidadComboBox.setPrefWidth(400);


		// Componente para seleccionar el sexo...
		ToggleGroup group = new ToggleGroup();
		femaleRadioButton = new JFXRadioButton("Femenino");
		femaleRadioButton.setPadding(new Insets(5));
		femaleRadioButton.setToggleGroup(group);
		maleRadioButton = new JFXRadioButton("Masculio");
		maleRadioButton.setPadding(new Insets(5));
		maleRadioButton.setToggleGroup(group);
		HBox sexoHBox = new HBox();
		sexoHBox.setSpacing(5);
		sexoHBox.getChildren().addAll(femaleRadioButton,maleRadioButton);

		// Componente para el nombre...
		nombreTextField = new JFXTextField();
		nombreTextField.setPromptText("Ingrese el nombre completo");
		nombreTextField.setPrefWidth(100);

		// Componente para el nombre...
		fechaNacimientoTextField = new JFXTextField();
		fechaNacimientoTextField.setPromptText("Ingrese el nombre completo");
		fechaNacimientoTextField.setPrefWidth(100);
		

		numeroTelefonicoTextField = new JFXTextField();
		numeroTelefonicoTextField.setPromptText("Ingrese el número telefónico");
		numeroTelefonicoTextField.setPrefWidth(100);

		emailTextField = new JFXTextField();
		emailTextField.setPromptText("Ingrese el correo electrónico");
		emailTextField.setPrefWidth(100);
		
		// Componente para la dirección...
		addressTextField = new JFXTextField();
		addressTextField.setPromptText("Ingrese la dirección del migrante");
		addressTextField.setPrefWidth(100);

		ObservableList<String> estadoCivilOptions = 
				FXCollections.observableArrayList("SOLTERO(A)","CASADO(A)","UNION LIBRE","SEPARADO");
		estadoCivilComboBox = new  JFXComboBox<String>(estadoCivilOptions);
		estadoCivilComboBox.setPromptText("Seleccione el estado civil");
		estadoCivilComboBox.setPrefWidth(400);
		

		edadTextField = new JFXTextField();
		edadTextField.setPromptText("Ingrese la edad en números");
		edadTextField.setPrefWidth(100);
		
		hijosTextFiled = new JFXTextField();
		hijosTextFiled.setPromptText("Ingrese la cantidad de hijos en números");
		hijosTextFiled.setPrefWidth(100);
		
		
		ObservableList<String> escolaridadlOptions = 
				FXCollections.observableArrayList("NINGUNA","PRIMARIA","BASICOS","BACHILLER","UNIVERSIDAD");
		escolaridadComboBox = new  JFXComboBox<String>(escolaridadlOptions);
		escolaridadComboBox.setPromptText("Seleccione la escolaridad");
		escolaridadComboBox.setPrefWidth(400);
		
		lgbtiChecBox = new JFXCheckBox();
		
		// Componente para seleccionar el lgbti..
		ToggleGroup lgbtigroup = new ToggleGroup();
		transRadioButton = new JFXRadioButton("Transexual");
		transRadioButton.setPadding(new Insets(5));
		transRadioButton.setToggleGroup(lgbtigroup);
		gayRadioButton = new JFXRadioButton("Gay");
		gayRadioButton.setPadding(new Insets(5));
		gayRadioButton.setToggleGroup(lgbtigroup);
		lesvianaRadioButton = new JFXRadioButton("Lesviana");
		lesvianaRadioButton.setPadding(new Insets(5));
		lesvianaRadioButton.setToggleGroup(lgbtigroup);
		bisexualRadioButton = new JFXRadioButton("Bisexual");
		bisexualRadioButton.setPadding(new Insets(5));
		bisexualRadioButton.setToggleGroup(lgbtigroup);
		VBox lgbtiVBox = new VBox();
		lgbtiVBox.setSpacing(5);
		lgbtiVBox.getChildren().addAll(transRadioButton,gayRadioButton,lesvianaRadioButton,bisexualRadioButton);
		
	

		//dateHBox.getChildren().addAll(fechaTextField,hourTextField);
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
		Label datosMigranteLabel = new Label("Datos del Migrante");
		datosMigranteLabel.setFont(Font.font("Arial", 18));
		datosMigranteLabel.setStyle("-fx-text-fill: #848484");

		// Definimos la caja principal para el formulario...
		HBox mainFormHBox = new HBox();
		mainFormHBox.setSpacing(60);
		mainFormHBox.setPadding(new Insets(10,20,20,40));
		mainFormHBox.setPrefWidth(500);

		Label personaGestionLabel = new Label("Persona que presenta el caso:");
		personaGestionLabel.setFont(Font.font("Arial", 18));
		personaGestionLabel.setStyle("-fx-text-fill: #848484");

		// Caja para los componentes de los datos de la víctima...
		VBox datosPersonaVBox = new VBox();
		datosPersonaVBox.setSpacing(10);
		datosPersonaVBox.getChildren().addAll(
				datosMigranteLabel,
				new Label("Nombre:"),nombreTextField, 
				new Label("Sexo:"),sexoHBox,
				new Label("Nacionalidad:"),nacionalidadComboBox, 
				new Label("Fecha de nacimiento:"),fechaNacimientoTextField,
				new Label("Edad:"),edadTextField,
				new Label("Estado civil:"),estadoCivilComboBox,
				new Label("Cuántos hijos tiene:"),hijosTextFiled,
				new Label("Escolaridad:"),escolaridadComboBox,
				new Label("Lugar de residencia:"),addressTextField,
				new Label("Número telefónico:"),numeroTelefonicoTextField,
				new Label("Email:"),emailTextField,
				new Label("LGBTI:"),lgbtiChecBox,lgbtiVBox,
				toolbarHBox
				);


		mainFormHBox.getChildren().addAll(datosPersonaVBox);
		ScrollPane scrollPane = new ScrollPane(mainFormHBox);
		scrollPane.setFitToWidth(true);
		mainBorderPane.setCenter(scrollPane);
		return mainBorderPane;
	}



	public String validateSeguimiento(){

		String fecha = this.fechaSeguimientoTextField.getText();
		if(fecha.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaSeguimientoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la fecha del evento";
		}
		String hour = this.horaSeguimientoTextField.getText();
		if(hour.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					horaSeguimientoTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la hora del evento";
		}
		String date = fecha +" "+hour;
		if(TimeUtil.getDateTimeFormat(date) == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaSeguimientoTextField.requestFocus();
				}
			}); 
			return "Fecha y hora del evento no válida";
		}

		CatalogInfo estado = this.estadosComboBox.getSelectionModel().getSelectedItem();
		if(estado == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					estadosComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar un estado para el caso";
		}

		String descripcion = this.observacionesTextArea.getText();
		if(descripcion.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					observacionesTextArea.requestFocus();
				}
			}); 
			return "Debe ingresar la descripción del evento";
		}


		if(this.currentPersonaInfo != null){
			if(this.currentSeguimientoInfo == null){
				this.currentSeguimientoInfo = new SeguimientoCasoInfo();
				String id = UUID.randomUUID().toString();
				this.currentPersonaInfo.setId(id);
				this.currentSeguimientoInfo.setId(id);
				this.currentSeguimientoInfo.setCasoId(this.currentPersonaInfo.getId());
				this.currentSeguimientoInfo.setFecha(fecha);
				this.currentSeguimientoInfo.setHora(hour);
				this.currentSeguimientoInfo.setEstadoId(estado.getId());
				this.currentSeguimientoInfo.setEstado(estado.getDescr());
				this.currentSeguimientoInfo.setDescripcion(descripcion);
				this.currentSeguimientoInfo.setFechaRegistro(TimeUtil.now());
				this.currentSeguimientoInfo.setFechaEdicion(TimeUtil.now());
				this.currentSeguimientoInfo.setStatus("1");
			}
		}

		return null;
	}

	public String validate(){
		String nombre = this.nombreTextField.getText();
		if(nombre.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					nombreTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el nombre completo del migrante";
		}

		String fecha = this.fechaNacimientoTextField.getText();
		if(fecha.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaNacimientoTextField.requestFocus();
				}
			}); 
			return "Debe de ingresar la Fecha de nacimiento";
		}

		Boolean female = this.femaleRadioButton.isSelected();
		Boolean male = this.maleRadioButton.isSelected();
		if(!female && !male){
			return "Debe seleccionar el sexo";
		}

		if(this.currentPersonaInfo == null){
			this.currentPersonaInfo = new PersonaInfo();
			String id = UUID.randomUUID().toString();
			this.currentPersonaInfo.setId(id);
			
		}
		this.currentPersonaInfo.setNombre(nombre.trim());
		
		if(female){
			this.currentPersonaInfo.setSexo("F");
		}
		if(male){
			this.currentPersonaInfo.setSexo("M");
		}


		return null;

	}

	public void clearNewCase(){

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentPersonaInfo = null;
				nombreTextField.setText("");
				addressTextField.setText("");
				maleRadioButton.setSelected(false);
				femaleRadioButton.setSelected(false);
			}
		}); 
	}
	
	
	public void updateSelectedSituaciones(){
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			//	ObservableList<CatalogInfo> situacionDesproteccionOptions = 
					//	FXCollections.observableArrayList(situacionDesproteccionInfoList);
				//situacionDesproteccionComboBox.setItems(situacionDesproteccionOptions);
			}
		}); 
	}

	// Mpetodo para limpiar el formulario de seguimiento...
	public void clearSeguimiento(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				estadosComboBox.getSelectionModel().select(null);
				fechaSeguimientoTextField.setText("");
				horaSeguimientoTextField.setText("");
				observacionesTextArea.setText("");
				currentSeguimientoInfo = null;
			}
		}); 
	}

	/************************************************************/
	/*          EVENTOS DE LA TABLA DE CASOS                    */
	/************************************************************/

	public BorderPane buildCasosDesproteccionTablaBorderPane(){
		BorderPane mainBorderPane = new BorderPane();
		HBox searchHBox = new HBox();
		searchHBox.setPadding(new Insets(5,5,5,5));
		searchHBox.setSpacing(5);
		this.nombreBusquedaTextField = new JFXTextField();
		this.nombreBusquedaTextField.setPromptText("Ingrese el nombre a buscar");
		this.nombreBusquedaTextField.setPrefWidth(300);
		Icon iconSearch = new Icon(AwesomeIcon.SEARCH,"1em",";","");
		Label searchButtonLabel = new Label();
		searchButtonLabel.setGraphic(iconSearch);
		this.searchButton = new JFXButton();
		this.searchButton.setGraphic(searchButtonLabel);
		this.searchButton.setCursor(Cursor.HAND);
		this.searchButton.setTooltip(new Tooltip("Buscar"));
		this.searchButton.setOnAction(this);
		searchHBox.getChildren().addAll(this.nombreBusquedaTextField, this.searchButton);

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

	/************************************************************/
	/*          PANEL DE SEGUIMIENTO                    */
	/************************************************************/

	public BorderPane buildSeguimientoBorderPane(){
		BorderPane mainBorderPane = new BorderPane();

		// Información del caso...
		Label titleLabel = new Label("Información del expediente");
		ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
		scrollPane.setPrefWidth(400);
		scrollPane.setFitToWidth(true);

		// Información del caso...
		Label tseguimientoLabel = new Label("Seguimiento del migrante");
		seguimientoInfoPanelVBox = new VBox();
		seguimientoInfoPanelVBox.setPadding(new Insets(10,10,10,10));
		seguimientoInfoPanelVBox.setSpacing(10);

		ScrollPane seguimientoScrollPane = new ScrollPane(this.seguimientoInfoPanelVBox);
		seguimientoScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		seguimientoScrollPane.setPrefWidth(600);
		seguimientoScrollPane.setFitToWidth(true);

		// Formulario de seguimiento...

		// Componente para el estado del caso...
		ObservableList<CatalogInfo> estadosOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getEstadoCasoInfoList());
		estadosComboBox = new  JFXComboBox<CatalogInfo>(estadosOptions);
		estadosComboBox.setPromptText("Servicio:");
		estadosComboBox.setPrefWidth(160);


		// Componente para la fecha del hecho...
		fechaSeguimientoTextField = new JFXTextField();
		fechaSeguimientoTextField.setPromptText("Fecha: dd/mm/yyyy");
		MaskFieldUtil.dateField(fechaSeguimientoTextField);
		fechaSeguimientoTextField.setPrefWidth(150);

		horaSeguimientoTextField = new JFXTextField();
		horaSeguimientoTextField.setPromptText("Hora: hh:mm");
		MaskFieldUtil.hourField(horaSeguimientoTextField);
		horaSeguimientoTextField.setPrefWidth(100);

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(5);
		dateHBox.getChildren().addAll(fechaSeguimientoTextField,horaSeguimientoTextField);
		// Boton guardar...

		// Componente para la narración del hecho...d
		observacionesTextArea = new TextArea();
		observacionesTextArea.setPrefRowCount(10);
		observacionesTextArea.setPrefColumnCount(20);
		observacionesTextArea.setWrapText(true);
		observacionesTextArea.setPrefWidth(150);

		// Boton guardar...
		saveSeguimientoButton = new JFXButton("Guardar".toUpperCase());
		saveSeguimientoButton.getStyleClass().add("button-raised");
		saveSeguimientoButton.setCursor(Cursor.HAND);
		saveSeguimientoButton.setOnAction(this);

		VBox formVBox = new VBox();
		formVBox.setSpacing(10);
		formVBox.setPadding(new Insets(10,10,10,10));
		formVBox.getChildren().addAll(
				new Label("Fecha y hora de la acción:"),dateHBox, 
				new Label("Servicio:"),estadosComboBox,
				new Label("Actuaciones:"),observacionesTextArea,
				saveSeguimientoButton
				);

		BorderPane formBorderPane = new BorderPane();
		formBorderPane.setTop(formVBox);

		mainBorderPane.setLeft(scrollPane);
		mainBorderPane.setCenter(formBorderPane);
		mainBorderPane.setRight(seguimientoScrollPane);
		return mainBorderPane;
	}
	
	/************************************************************/
	/*          PANEL EVALUACIÓN PSICOSOCIAL                    */
	/************************************************************/

	public BorderPane buildPsicoSocialBorderPane(){
		BorderPane mainBorderPane = new BorderPane();

		// Información del caso...
		Label titleLabel = new Label("Información del migrante");
		ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
		scrollPane.setPrefWidth(400);
		scrollPane.setFitToWidth(true);

		// Información del caso...
		seguimientoInfoPanelVBox = new VBox();
		seguimientoInfoPanelVBox.setPadding(new Insets(10,10,10,10));
		seguimientoInfoPanelVBox.setSpacing(10);

		ScrollPane seguimientoScrollPane = new ScrollPane(this.seguimientoInfoPanelVBox);
		seguimientoScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		seguimientoScrollPane.setPrefWidth(600);
		seguimientoScrollPane.setFitToWidth(true);

		// Formulario de seguimiento...
		ObservableList<String> categoriaOptions = 
				FXCollections.observableArrayList("EN TRÁNSITO","DEPORTADA","GUATEMALTECO EN ALTO REISCO","SOLICITUD ASILO EN GUATEMALA");
		categoricaPoblacionComboBox = new  JFXComboBox<String>(categoriaOptions);
		categoricaPoblacionComboBox.setPromptText("Seleccione la categoría de Población");
		categoricaPoblacionComboBox.setPrefWidth(400);
		
		ObservableList<CatalogInfo> destinoOptions = 
				FXCollections.observableArrayList(DashboardGlobalInfo.getPaisesInfoList());
		destinoFinalComboBox = new  JFXComboBox<CatalogInfo>(destinoOptions);
		destinoFinalComboBox.setPromptText("Seleccione el destino");
		destinoFinalComboBox.setPrefWidth(400);
	
		ObservableList<String> victimaOptions = 
				FXCollections.observableArrayList("SI","NO");
		victimaComboBox = new  JFXComboBox<String>(victimaOptions);
		victimaComboBox.setPromptText("Seleccione la opción");
		victimaComboBox.setPrefWidth(400);
		
		ObservableList<String> situacionOptions = 
				FXCollections.observableArrayList("NA","REFUGIO","EN TRANSITO","RETORNADO","DEPORTADO");
		situacionMigratoriaComboBox = new  JFXComboBox<String>(situacionOptions);
		situacionMigratoriaComboBox.setPromptText("Seleccione la opción");
		situacionMigratoriaComboBox.setPrefWidth(400);
		
		especificacionMigracionTextField = new JFXTextField();
		especificacionMigracionTextField.setPromptText("Especifique la situación de migración");
		especificacionMigracionTextField.setPrefWidth(100);

		// Componente para la fecha del hecho...
		fechaSeguimientoTextField = new JFXTextField();
		fechaSeguimientoTextField.setPromptText("Fecha: dd/mm/yyyy");
		MaskFieldUtil.dateField(fechaSeguimientoTextField);
		fechaSeguimientoTextField.setPrefWidth(150);

		horaSeguimientoTextField = new JFXTextField();
		horaSeguimientoTextField.setPromptText("Hora: hh:mm");
		MaskFieldUtil.hourField(horaSeguimientoTextField);
		horaSeguimientoTextField.setPrefWidth(100);
		
		ObservableList<String> necedadesOptions = 
				FXCollections.observableArrayList("Casos NNA no acompañado","Casos de NNA separado"," Casos de Sobrevivientes de SGBV","Casos de LGBTI","Casos de VIH","Mujeres en Riesgo","Personas de la tercera edad","Personas con discapacidad fisica y mental");
		necesidadesProteccionListView = new  ListView<String>(necedadesOptions);
		necesidadesProteccionListView.setPrefWidth(200);
		necesidadesProteccionListView.setPrefHeight(100);
		necesidadesProteccionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<String> motivosOptions = 
				FXCollections.observableArrayList("Económicos/ Pobreza","Reunificación familiar","Persecucion (Política, religiosa, étnica, nacionalidad, grupo social)","Violencia");
		motivoMigracionListView = new  ListView<String>(motivosOptions);
		motivoMigracionListView.setPrefWidth(200);
		motivoMigracionListView.setPrefHeight(100);
		motivoMigracionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		

		ObservableList<String> violenciaOptions = 
				FXCollections.observableArrayList("Violencia por razón de género y/u orientación sexual","Violencia organizada o delincuencia (Maras, Pandillas)","Violencia intra familiar","Extorsión","Homicidios o atentados","Amenazas/Intimidaciones");
		violenciaListView = new  ListView<String>(violenciaOptions);
		violenciaListView.setPrefWidth(200);
		violenciaListView.setPrefHeight(100);
		violenciaListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<String> delitosOptions = 
				FXCollections.observableArrayList("Asalto","Extorsión","Violencia o abuso sexualr","Extorsión","Homicidios o atentados","Abuso de autoridad");
		delitosListView = new  ListView<String>(delitosOptions);
		delitosListView.setPrefWidth(200);
		delitosListView.setPrefHeight(100);
		delitosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<String> motivoOptions = 
				FXCollections.observableArrayList("Migración lo detiene en EEUU","DETENIDO POR MANEJAR EBRIO","MIGRACIÓN IRREGULAR","NA");
		motivoDeportacionComboBox = new  ListView<String>(motivoOptions);
		motivoDeportacionComboBox.setPrefWidth(200);
		motivoDeportacionComboBox.setPrefHeight(100);
		motivoDeportacionComboBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<String> actitudOptions = 
				FXCollections.observableArrayList("Timida","Cooperadora","Molesto","Nervioso");
		actitudListView = new  ListView<String>(actitudOptions);
		actitudListView.setPrefWidth(200);
		actitudListView.setPrefHeight(100);
		actitudListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<String> areaVulnerabilidadOptions = 
				FXCollections.observableArrayList("NA","Emocional","Fisiologica","Cognitivo");
		areaVulneravildiadListView = new  ListView<String>(areaVulnerabilidadOptions);
		areaVulneravildiadListView.setPrefWidth(200);
		areaVulneravildiadListView.setPrefHeight(100);
		areaVulneravildiadListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<String> signoOptions = 
				FXCollections.observableArrayList("Tranquilidad","Miedo","Preocupado","Serio");
		sintomaListView = new  ListView<String>(signoOptions);
		sintomaListView.setPrefWidth(200);
		sintomaListView.setPrefHeight(100);
		sintomaListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<String> intervencionOptions = 
				FXCollections.observableArrayList("Escucha activa","Escucha responsable","Viviendo el refugio","Entrevista terapéutica");
		intervencionPsicologicaListView = new  ListView<String>(intervencionOptions);
		intervencionPsicologicaListView.setPrefWidth(200);
		intervencionPsicologicaListView.setPrefHeight(100);
		intervencionPsicologicaListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(5);
		dateHBox.getChildren().addAll(fechaSeguimientoTextField,horaSeguimientoTextField);
		// Boton guardar...

		// Componente para la narración del hecho...d
		observacionesPsicosocialTextArea = new TextArea();
		observacionesPsicosocialTextArea.setPrefRowCount(10);
		observacionesPsicosocialTextArea.setPrefColumnCount(20);
		observacionesPsicosocialTextArea.setWrapText(true);
		observacionesPsicosocialTextArea.setPrefWidth(150);

		// Boton guardar...
		saveSeguimientoButton = new JFXButton("Guardar".toUpperCase());
		saveSeguimientoButton.getStyleClass().add("button-raised");
		saveSeguimientoButton.setCursor(Cursor.HAND);
		saveSeguimientoButton.setOnAction(this);

		VBox formVBox = new VBox();
		formVBox.setSpacing(10);
		formVBox.setPadding(new Insets(10,10,10,10));
		formVBox.getChildren().addAll(
				new Label("Fecha y hora de la evaluación:"),dateHBox, 
				new Label("Categoría de Población:"),categoricaPoblacionComboBox,
				new Label("Destino Final:"),destinoFinalComboBox,
				new Label("Victima de Abuso en Guatemala:"),victimaComboBox,
				new Label("Situación Migratoria:"),situacionMigratoriaComboBox,
				new Label("Especificación Situación Migratoria:"),especificacionMigracionTextField,
				new Label("Necesidad específica de protección:"),necesidadesProteccionListView,
				new Label("Motivos por los que salio de su país"),motivoMigracionListView,
				new Label("Si salio por violencia, ¿qué tipo de violencia sufrió?"),violenciaListView,
				new Label("De qué delitos o violencia fue victima en su tránsito?"),delitosListView,
				new Label("Motivo de deportación"),motivoDeportacionComboBox,
				new Label("Actitud en la intervención"),actitudListView,
				new Label("Área de vulnerabilidad"),areaVulneravildiadListView,
				new Label("Signo / Síntoma predominante"),sintomaListView,
				new Label("Intervención Psicológica"),intervencionPsicologicaListView,
				new Label("Observaciones:"),observacionesPsicosocialTextArea,
				saveSeguimientoButton
				);

		BorderPane formBorderPane = new BorderPane();
		formBorderPane.setTop(formVBox);
		ScrollPane scrollPaneForm = new ScrollPane(formBorderPane);
		//scrollPaneForm.setPrefWidth(400);
		scrollPaneForm.setFitToWidth(true);
		
		mainBorderPane.setLeft(scrollPane);
		mainBorderPane.setCenter(scrollPaneForm);
		mainBorderPane.setRight(seguimientoScrollPane);
		return mainBorderPane;
	}
	
	/************************************************************/
	/*          PANEL EVALUACIÓN LEGAL                    */
	/************************************************************/

	public BorderPane buildLegalBorderPane(){
		BorderPane mainBorderPane = new BorderPane();

		// Información del caso...
		Label titleLabel = new Label("Información del migrante");
		ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
		scrollPane.setPrefWidth(400);
		scrollPane.setFitToWidth(true);

		// Información del caso...
		seguimientoInfoPanelVBox = new VBox();
		seguimientoInfoPanelVBox.setPadding(new Insets(10,10,10,10));
		seguimientoInfoPanelVBox.setSpacing(10);

		ScrollPane seguimientoScrollPane = new ScrollPane(this.seguimientoInfoPanelVBox);
		seguimientoScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		seguimientoScrollPane.setPrefWidth(600);
		seguimientoScrollPane.setFitToWidth(true);

		// Formulario de seguimiento...

		// Componente para la dirección...
		actoresTextField = new JFXTextField();
		actoresTextField.setPromptText("");
		actoresTextField.setPrefWidth(100);
		
		oficinaDerivacionTextField = new JFXTextField();
		oficinaDerivacionTextField.setPromptText("");
		oficinaDerivacionTextField.setPrefWidth(100);
		
		asuntoTextField = new JFXTextField();
		asuntoTextField.setPromptText("");
		asuntoTextField.setPrefWidth(100);
		
		compromisosTextField = new JFXTextField();
		compromisosTextField.setPromptText("");
		compromisosTextField.setPrefWidth(100);
		
		instanciaTextField = new JFXTextField();
		instanciaTextField.setPromptText("");
		instanciaTextField.setPrefWidth(100);
		
		resultadosTextField = new JFXTextField();
		resultadosTextField.setPromptText("");
		resultadosTextField.setPrefWidth(100);


		// Componente para la fecha del hecho...
		fechaSeguimientoTextField = new JFXTextField();
		fechaSeguimientoTextField.setPromptText("Fecha: dd/mm/yyyy");
		MaskFieldUtil.dateField(fechaSeguimientoTextField);
		fechaSeguimientoTextField.setPrefWidth(150);

		horaSeguimientoTextField = new JFXTextField();
		horaSeguimientoTextField.setPromptText("Hora: hh:mm");
		MaskFieldUtil.hourField(horaSeguimientoTextField);
		horaSeguimientoTextField.setPrefWidth(100);

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(5);
		dateHBox.getChildren().addAll(fechaSeguimientoTextField,horaSeguimientoTextField);
		// Boton guardar...

		// Componente para la narración del hecho...d
		observacionesJuridicoTextArea = new TextArea();
		observacionesJuridicoTextArea.setPrefRowCount(10);
		observacionesJuridicoTextArea.setPrefColumnCount(20);
		observacionesJuridicoTextArea.setWrapText(true);
		observacionesJuridicoTextArea.setPrefWidth(150);

		// Boton guardar...
		saveSeguimientoButton = new JFXButton("Guardar".toUpperCase());
		saveSeguimientoButton.getStyleClass().add("button-raised");
		saveSeguimientoButton.setCursor(Cursor.HAND);
		saveSeguimientoButton.setOnAction(this);

		VBox formVBox = new VBox();
		formVBox.setSpacing(10);
		formVBox.setPadding(new Insets(10,10,10,10));
		formVBox.getChildren().addAll(
				new Label("Fecha y hora de la evaluación:"),dateHBox, 
				new Label("Actores involucrados:"),actoresTextField,
				new Label("Oficina que derivó el caso:"),oficinaDerivacionTextField,
				new Label("Asunto:"),asuntoTextField,
				new Label("Compromisos o acuerdos:"),compromisosTextField,
				new Label("Instancia a la que se derivó:"),instanciaTextField,
				new Label("Resultados:"),resultadosTextField,
				new Label("Observaciones:"),observacionesJuridicoTextArea,
				saveSeguimientoButton
				);

		BorderPane formBorderPane = new BorderPane();
		formBorderPane.setTop(formVBox);

		mainBorderPane.setLeft(scrollPane);
		mainBorderPane.setCenter(formBorderPane);
		mainBorderPane.setRight(seguimientoScrollPane);
		return mainBorderPane;
	}


	/************************************************************/
	/*          PANEL DE RELACIÓN FAMILIAR                   */
	/************************************************************/

	public BorderPane buildRelacionFamiliarBorderPane(){
		BorderPane mainBorderPane = new BorderPane();

		// Información del caso...
		Label titleLabel = new Label("Información del expediente");
		ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
		scrollPane.setPrefWidth(400);
		scrollPane.setFitToWidth(true);

		// Información del caso...
		Label tseguimientoLabel = new Label("Seguimiento del migrante");
		seguimientoInfoPanelVBox = new VBox();
		seguimientoInfoPanelVBox.setPadding(new Insets(10,10,10,10));
		seguimientoInfoPanelVBox.setSpacing(10);

		ScrollPane seguimientoScrollPane = new ScrollPane(this.seguimientoInfoPanelVBox);
		seguimientoScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		seguimientoScrollPane.setPrefWidth(600);
		seguimientoScrollPane.setFitToWidth(true);

		// Formulario de seguimiento...

		// Componente para el estado del caso...
		ObservableList<String> estadosOptions = 
				FXCollections.observableArrayList("Padre","Madre","Hermano(a)","Hijo","Primo(a)","Tio(a)","Sobrino(a)","Abuelo(a)");
		parentescoComboBox = new  JFXComboBox<String>(estadosOptions);
		parentescoComboBox.setPromptText("Seleccione");
		parentescoComboBox.setPrefWidth(160);


		// Componente para la fecha del hecho...
		fechaSeguimientoTextField = new JFXTextField();
		fechaSeguimientoTextField.setPromptText("Fecha: dd/mm/yyyy");
		MaskFieldUtil.dateField(fechaSeguimientoTextField);
		fechaSeguimientoTextField.setPrefWidth(150);

		horaSeguimientoTextField = new JFXTextField();
		horaSeguimientoTextField.setPromptText("Hora: hh:mm");
		MaskFieldUtil.hourField(horaSeguimientoTextField);
		horaSeguimientoTextField.setPrefWidth(100);

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(5);
		dateHBox.getChildren().addAll(fechaSeguimientoTextField,horaSeguimientoTextField);
		// Boton guardar...

		// Componente para la narración del hecho...d
		observacionesTextArea = new TextArea();
		observacionesTextArea.setPrefRowCount(10);
		observacionesTextArea.setPrefColumnCount(20);
		observacionesTextArea.setWrapText(true);
		observacionesTextArea.setPrefWidth(150);

		// Boton guardar...
		saveSeguimientoButton = new JFXButton("Guardar".toUpperCase());
		saveSeguimientoButton.getStyleClass().add("button-raised");
		saveSeguimientoButton.setCursor(Cursor.HAND);
		saveSeguimientoButton.setOnAction(this);

		VBox formVBox = new VBox();
		formVBox.setSpacing(10);
		formVBox.setPadding(new Insets(10,10,10,10));
		formVBox.getChildren().addAll(
				new Label("Fecha y hora de la acción:"),dateHBox, 
				new Label("Parentesco:"),parentescoComboBox,
				new Label("Seleccione:"),this.createTable(),
				new Label("Observaciones:"),observacionesTextArea,
				saveSeguimientoButton
				);

		BorderPane formBorderPane = new BorderPane();
		formBorderPane.setTop(formVBox);

		mainBorderPane.setLeft(scrollPane);
		mainBorderPane.setCenter(formBorderPane);
		mainBorderPane.setRight(seguimientoScrollPane);
		return mainBorderPane;
	}
	
	
	public TableView<PersonaInfo> createTable() {
		if(this.personaTableView != null){
			this.personaTableView.getItems().clear();
		}
		this.personaTableView = new TableView<PersonaInfo>();



		TableColumn<PersonaInfo, String> numeroCasoColumn = new TableColumn<PersonaInfo, String>("# ID");
		numeroCasoColumn.setMinWidth(120);
		numeroCasoColumn.setCellValueFactory(new PropertyValueFactory<PersonaInfo, String>("id"));
		TableColumn<PersonaInfo, String> nombreColumn = new TableColumn<PersonaInfo, String>("Nombre del Migrante");
		nombreColumn.setMinWidth(280);
		nombreColumn.setCellValueFactory(new PropertyValueFactory<PersonaInfo, String>("nombre"));
		

		this.personaTableView.getColumns().add(numeroCasoColumn);
		this.personaTableView.getColumns().add(nombreColumn);
	
		PersonaDbActions personaDbActions = new PersonaDbActions();
		this.personaInfoList = personaDbActions.getAll();

		if(this.personaInfoList != null){
			this.personaTableView.setItems(FXCollections.observableArrayList(this.personaInfoList));
		}
		this.personaTableView.getSelectionModel().selectedItemProperty().addListener(this);

		return this.personaTableView;

	}

	



	public void loadInfo(){
		if(this.currentPersonaInfo != null){
			// Obtenemos la información del POJO...
			String nombre = this.currentPersonaInfo.getNombre();
			
			String sexo = this.currentPersonaInfo.getSexo();

			// Seteamos los valores a los campos para la edición...
			this.nombreTextField.setText(nombre);

			if(sexo.equals("F")){
				this.femaleRadioButton.setSelected(true);
			}
			if(sexo.equals("M")){
				this.maleRadioButton.setSelected(true);
			}


			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Creamos el panel para el display de la información del caso...
					infoCasoVBox.getChildren().clear();
					
					Label title1Label = new Label("Información del migrante");
					title1Label.setFont(Font.font("Arial", 18));
					title1Label.setStyle("-fx-text-fill: #848484");

					Label nombreLabel = new Label("Nombre:");
					nombreLabel.setFont(Font.font("Arial", 12));
					nombreLabel.setStyle("-fx-text-fill: #848484");

					Label edadLabel = new Label("Edad:");
					edadLabel.setFont(Font.font("Arial", 12));
					edadLabel.setStyle("-fx-text-fill: #848484");

					Label sexoLabel = new Label("Sexo:");
					sexoLabel.setFont(Font.font("Arial", 12));
					sexoLabel.setStyle("-fx-text-fill: #848484");

					infoCasoVBox.getChildren().add(title1Label);
					infoCasoVBox.getChildren().addAll(nombreLabel,new Label(nombre));
				

					if(sexo.equals("F")){
						infoCasoVBox.getChildren().addAll(sexoLabel,new Label("Mujer"));
					}
					if(sexo.equals("M")){
						infoCasoVBox.getChildren().addAll(sexoLabel,new Label("Hombre"));
					}

				}

			});
		}
	}

	// Método para construir los paneles de seguimiento;
	public void buildSeguimientoInfoPanel(){
		try{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					seguimientoInfoPanelVBox.getChildren().clear();
					Label titleLabel = new Label("Seguimiento");
					titleLabel.setFont(Font.font("Arial", 14));
					titleLabel.setStyle("-fx-text-fill: #848484");
					seguimientoInfoPanelVBox.getChildren().add(titleLabel);
					SeguimientoCasoDbActions seguimientoCasoDbActions = new SeguimientoCasoDbActions();
					ArrayList<SeguimientoCasoInfo> seguimientoCasoInfoList = seguimientoCasoDbActions.getEventosSeguimiento(currentPersonaInfo.getId());
					if(seguimientoCasoInfoList != null){
						int count = 0;
						for(SeguimientoCasoInfo info : seguimientoCasoInfoList){
							count ++;
							Label fechaLabel = new Label(count+". Fecha:");
							fechaLabel.setFont(Font.font("Arial", 12));
							fechaLabel.setStyle("-fx-text-fill: #848484");
							Label estadoLabel = new Label("Servicio:");
							estadoLabel.setFont(Font.font("Arial", 12));
							estadoLabel.setStyle("-fx-text-fill: #848484");
							Label descripcionLabel = new Label("Descripción:");
							descripcionLabel.setFont(Font.font("Arial", 12));
							descripcionLabel.setStyle("-fx-text-fill: #848484");

							Icon iconEdit = new Icon(AwesomeIcon.ERASER,"1em",";","");
							Label editButtonLabel = new Label();
							editButtonLabel.setGraphic(iconEdit);
							JFXButton elimnarButton = new JFXButton();
							elimnarButton.setGraphic(editButtonLabel);
							elimnarButton.setCursor(Cursor.HAND);
							elimnarButton.setTooltip(new Tooltip("Eliminar"));
							elimnarButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override public void handle(ActionEvent e) {
									info.setStatus("0");
									SeguimientoCasoDbActions seguimientoCasoDbActions = new SeguimientoCasoDbActions();
									seguimientoCasoDbActions.saveSeguimiento(info);
									buildSeguimientoInfoPanel();
								}
							});

							// Componente para la narración del hecho...d
							TextArea observacionesTextArea = new TextArea();
							observacionesTextArea.setPrefRowCount(7);
							observacionesTextArea.setPrefColumnCount(20);
							observacionesTextArea.setWrapText(true);
							observacionesTextArea.setPrefWidth(150);
							observacionesTextArea.setText(info.getDescripcion());

							VBox vBox = new VBox();
							vBox.setSpacing(5);
							String fecha = info.getFecha()+" "+info.getHora();
							HBox fechaHbox = new HBox();
							fechaHbox.setSpacing(10);
							fechaHbox.getChildren().addAll(fechaLabel,new Label(fecha));
							vBox.getChildren().addAll(fechaHbox);
							HBox estadoHbox = new HBox();
							estadoHbox.setSpacing(10);
							estadoHbox.getChildren().addAll(estadoLabel, new Label(info.getEstado()));
							vBox.getChildren().addAll(estadoHbox);
							vBox.getChildren().addAll(descripcionLabel, observacionesTextArea);
							vBox.getChildren().addAll(elimnarButton);
							seguimientoInfoPanelVBox.getChildren().add(vBox);
						}



					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
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
			this.personaTablaBorderPane = this.buildCasosDesproteccionTablaBorderPane();
			//createTable();
			this.borderPane.setCenter(this.personaTablaBorderPane);
		}
		if(eventSource == this.serviciosButton){
			if(this.currentPersonaInfo != null){
				this.seguimientoBorderPane = this.buildSeguimientoBorderPane();
				this.borderPane.setCenter(this.seguimientoBorderPane);
				buildSeguimientoInfoPanel();
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar una persona para usar la opción de seguimiento");
				messageDialogStage.show();
			}
		}
		
		if(eventSource == this.perfilPsicoSocialButton){
			if(this.currentPersonaInfo != null){
				this.seguimientoBorderPane = this.buildPsicoSocialBorderPane();
				this.borderPane.setCenter(this.seguimientoBorderPane);
				buildSeguimientoInfoPanel();
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar una persona para usar la opción de seguimiento");
				messageDialogStage.show();
			}
		}
		if(eventSource == this.perfilJuridicoButton){
			if(this.currentPersonaInfo != null){
				this.seguimientoBorderPane = this.buildLegalBorderPane();
				this.borderPane.setCenter(this.seguimientoBorderPane);
				buildSeguimientoInfoPanel();
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar una persona para usar la opción de seguimiento");
				messageDialogStage.show();
			}
		}
		
		if(eventSource == this.relacionFamiliarButton){
			if(this.currentPersonaInfo != null){
				this.seguimientoBorderPane = this.buildRelacionFamiliarBorderPane();
				this.borderPane.setCenter(this.seguimientoBorderPane);
				buildSeguimientoInfoPanel();
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar una persona para usar la opción de seguimiento");
				messageDialogStage.show();
			}
		}
		
		if(eventSource == this.searchButton){


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
						PersonaDbActions personaDbActions = new PersonaDbActions();
						Boolean save = 	personaDbActions.save(currentPersonaInfo);
						//return save;
						return true;
					}
				};
				task.setOnRunning((e) -> waitDialogStage.show());
				task.setOnSucceeded((e) -> {
					waitDialogStage.hide();
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Información guardada con éxito");
					messageDialogStage.show();
					Boolean returnValue = true;
					// process return value again in JavaFX thread
				});
				task.setOnFailed((e) -> {
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No pudo guardarse la información");
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
		if (eventSource == this.editButton) {
			this.borderPane.setCenter(this.newCasesBorderPane);
		}
		
		if (eventSource == this.saveSeguimientoButton) {
			String errorMsg = this.validateSeguimiento();
			if(errorMsg == null){
				try{
					WaitModalDialog waitModalDialog = new WaitModalDialog(this.primaryStage);
					final Stage waitDialogStage = waitModalDialog.buildModalDialogStage(MessageDialogType.LOAD);
					Task<Boolean> task = new Task<Boolean>() {
						@Override public Boolean call() {
							SeguimientoCasoDbActions seguimientoCasoDbActions = new SeguimientoCasoDbActions();
							Boolean save = 	seguimientoCasoDbActions.saveSeguimiento(currentSeguimientoInfo);
							clearSeguimiento();
							buildSeguimientoInfoPanel();
							return save;
						}
					};
					task.setOnRunning((e) -> waitDialogStage.show());
					task.setOnSucceeded((e) -> {
						waitDialogStage.hide();
						Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Evento guardado con éxito");
						messageDialogStage.show();
						Boolean returnValue = true;
						// process return value again in JavaFX thread
					});
					task.setOnFailed((e) -> {
						Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No pudo guardarse el evento");
						waitDialogStage.hide();
						messageDialogStage.show();
						// eventual error handling by catching exceptions from task.get()  
					});
					new Thread(task).start();
				}catch(Exception e){
					e.printStackTrace();
				}
				buildSeguimientoInfoPanel();
			}else{

				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, errorMsg);
				messageDialogStage.show();
			}

		}

		if(eventSource == this.printButton){
			
		}


	}

	public ArrayList<CatalogInfo> getNecesidadProteccionInfoList() {
		return necesidadProteccionInfoList;
	}

	public void setNecesidadProteccionInfoList(ArrayList<CatalogInfo> necesidadProteccionInfoList) {
		this.necesidadProteccionInfoList = necesidadProteccionInfoList;
	}

	@Override
	public void changed(ObservableValue<? extends PersonaInfo> arg0, PersonaInfo arg1, PersonaInfo arg2) {
		this.currentPersonaInfo = arg2;
		this.loadInfo();
		
	}

	public ArrayList<CatalogInfo> getMotivoMigracionInfoList() {
		return motivoMigracionInfoList;
	}

	public void setMotivoMigracionInfoList(ArrayList<CatalogInfo> motivoMigracionInfoList) {
		this.motivoMigracionInfoList = motivoMigracionInfoList;
	}

	public ArrayList<CatalogInfo> getViolenciaInfoList() {
		return violenciaInfoList;
	}

	public void setViolenciaInfoList(ArrayList<CatalogInfo> violenciaInfoList) {
		this.violenciaInfoList = violenciaInfoList;
	}

	public ArrayList<CatalogInfo> getActitudInfoList() {
		return actitudInfoList;
	}

	public void setActitudInfoList(ArrayList<CatalogInfo> actitudInfoList) {
		this.actitudInfoList = actitudInfoList;
	}

	public ArrayList<CatalogInfo> getDelitosInfoList() {
		return delitosInfoList;
	}

	public void setDelitosInfoList(ArrayList<CatalogInfo> delitosInfoList) {
		this.delitosInfoList = delitosInfoList;
	}

	public ArrayList<CatalogInfo> getAreaVulneravildiadInfoList() {
		return areaVulneravildiadInfoList;
	}

	public void setAreaVulneravildiadInfoList(ArrayList<CatalogInfo> areaVulneravildiadInfoList) {
		this.areaVulneravildiadInfoList = areaVulneravildiadInfoList;
	}

	public ArrayList<CatalogInfo> getSintomaInfoList() {
		return sintomaInfoList;
	}

	public void setSintomaInfoList(ArrayList<CatalogInfo> sintomaInfoList) {
		this.sintomaInfoList = sintomaInfoList;
	}

	public ArrayList<CatalogInfo> getIntervencionPsicologicaInfoList() {
		return intervencionPsicologicaInfoList;
	}

	public void setIntervencionPsicologicaInfoList(ArrayList<CatalogInfo> intervencionPsicologicaInfoList) {
		this.intervencionPsicologicaInfoList = intervencionPsicologicaInfoList;
	}

}
