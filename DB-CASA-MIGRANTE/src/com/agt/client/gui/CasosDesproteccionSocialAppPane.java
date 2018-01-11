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

import com.agt.client.db.CasoDesproteccionSocialDbActions;
import com.agt.client.db.SeguimientoCasoDbActions;
import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.CasoDesproteccionSocialInfo;
import com.agt.client.info.CatalogInfo;
import com.agt.client.info.SeguimientoCasoInfo;
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
import javafx.scene.control.ScrollPane;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;



/**
 * @author mlopez
 *
 */
public class CasosDesproteccionSocialAppPane implements EventHandler<ActionEvent>,ChangeListener<CasoDesproteccionSocialInfo>  {
	private Stage primaryStage;
	private BorderPane borderPane;
	@SuppressWarnings("restriction")
	private ListView<CatalogInfo> situacionDesproteccionComboBox;
	private JFXTextField nombreVictimaTextField;
	private JFXComboBox<CatalogInfo> edadComboBox;
	private JFXTextField addressTextField;
	private JFXTextField otrosSituacionDesproteccionTextField;
	private JFXTextField otrosParentescoTextField;
	private JFXTextField otrosInstitucionTextField;
	private  JFXComboBox<CatalogInfo> otroAreaGeograficaComboBox;
	private JFXTextField fechaTextField;
	private JFXTextField hourTextField;
	private TextArea hechoTextArea;
	private JFXRadioButton maleRadioButton;
	private JFXRadioButton femaleRadioButton;
	private JFXComboBox<CatalogInfo> grupoEtnicoComboBox;
	private JFXComboBox<CatalogInfo> grupoIndigenaComboBox;
	private JFXComboBox<CatalogInfo> parentescoComboBox;
	private JFXComboBox<CatalogInfo> areaGeograficaComboBox;
	private JFXComboBox<CatalogInfo> institucionesComboBox;
	private JFXButton saveButton;
	private JFXButton clearButton;
	private JFXButton newButton;
	private JFXButton editButton;
	private JFXButton addSituacionButton;
	private JFXButton tableButton;
	private JFXButton printButton;
	private JFXButton seguimientoButton;
	private BorderPane newCasesBorderPane;
	private BorderPane casoDesproteccionTablaBorderPane;
	private BorderPane seguimientoBorderPane;
	private Boolean otraSituacionDesproteccionSelected = false;
	private TableView<CasoDesproteccionSocialInfo> casosDesproteccionTableView;
	private ArrayList<CasoDesproteccionSocialInfo> casoDesproteccionInfoList;
	private CasoDesproteccionSocialInfo currentCasoDesproteccionSocialInfo = null;
	private JFXRadioButton anonimoRadioButton;
	private JFXRadioButton otroRadioButton;
	private JFXRadioButton idemRadioButton;
	private JFXTextField nombrePersonaTextField;
	private JFXTextField direccionPersonaTextField;
	private JFXTextField emailPersonaTextField;
	private JFXTextField telefonoPersonaTextField;
	private JFXComboBox<CatalogInfo> parentescoPersonaComboBox;
	private VBox personaVBox;
	private VBox infoCasoVBox;
	private JFXTextField fechaInicioJFXTextField;
	private JFXTextField fechaFinJFXTextField;
	private JFXButton searchButton;
	// Campos seguimiento...
	private JFXComboBox<CatalogInfo> estadosComboBox;
	private JFXTextField fechaSeguimientoTextField;
	private JFXTextField horaSeguimientoTextField;
	private TextArea observacionesTextArea;
	private JFXButton saveSeguimientoButton;
	private SeguimientoCasoInfo currentSeguimientoInfo = null;
	private VBox seguimientoInfoPanelVBox = null;
	private ArrayList<CatalogInfo> situacionDesproteccionInfoList = new ArrayList<CatalogInfo>();

	public CasosDesproteccionSocialAppPane(Stage primaryStage) {
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
		Label titleLabel = new Label("GESTIÓN DE CASOS DE DESPROTECCIÓN SOCIAL");
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
		this.newButton.setTooltip(new Tooltip("Ingresar nuevo caso"));
		this.newButton.setOnAction(this);

		// Tabla de datos...
		Icon iconEdit = new Icon(AwesomeIcon.EDIT,"4em",";","");
		Label editButtonLabel = new Label();
		editButtonLabel.setGraphic(iconEdit);
		this.editButton = new JFXButton();
		this.editButton.setGraphic(editButtonLabel);
		this.editButton.setCursor(Cursor.HAND);
		this.editButton.setTooltip(new Tooltip("Editar información del caso"));
		this.editButton.setOnAction(this);

		// Tabla de datos...
		Icon iconTable = new Icon(AwesomeIcon.TABLE,"4em",";","");
		Label tableButtonLabel = new Label();
		tableButtonLabel.setGraphic(iconTable);
		this.tableButton = new JFXButton();
		this.tableButton.setGraphic(tableButtonLabel);
		this.tableButton.setCursor(Cursor.HAND);
		this.tableButton.setTooltip(new Tooltip("Ver tabla de casos"));
		this.tableButton.setOnAction(this);

		// Segumiento...
		Icon iconSeguimiento = new Icon(AwesomeIcon.HAND_ALT_RIGHT,"4em",";","");
		Label segumientoButtonLabel = new Label();
		segumientoButtonLabel.setGraphic(iconSeguimiento);
		this.seguimientoButton = new JFXButton();
		this.seguimientoButton.setGraphic(segumientoButtonLabel);
		this.seguimientoButton.setCursor(Cursor.HAND);
		this.seguimientoButton.setTooltip(new Tooltip("Seguimiento al caso"));
		this.seguimientoButton.setOnAction(this);
		
		// Botón para impirmir...
		Icon iconPrint = new Icon(AwesomeIcon.PRINT,"4em",";","");
		Label printButtonLabel = new Label();
		printButtonLabel.setGraphic(iconPrint);
		this.printButton = new JFXButton();
		this.printButton.setGraphic(printButtonLabel);
		this.printButton.setCursor(Cursor.HAND);
		this.printButton.setTooltip(new Tooltip("Imprimir información del caso"));
		this.printButton.setOnAction(this);

		toolbarHBox.getChildren().addAll(this.newButton, this.editButton, this.seguimientoButton, this.tableButton, this.printButton);

		titleHbox.getChildren().add(toolbarHBox);
		titleHbox.getChildren().add(titleLabelHBox);

		// Main del Panel
		this.borderPane.setTop(titleHbox);

		// Constuimos los paneles....
		this.newCasesBorderPane = this.buildNewCasePane();
		this.casoDesproteccionTablaBorderPane = this.buildCasosDesproteccionTablaBorderPane();
		//this.borderPane.setCenter(this.buildNewCasePane());
		
		DashboardGlobalInfo.getSituacionDesproteccionSocialInfoList();
		return borderPane;
	}

	/*
	 * Este método construlle la pantalla principal para la creación de un nuevo caso.
	 */
	public BorderPane buildNewCasePane(){
		BorderPane mainBorderPane = new BorderPane();

		// Componente para la situación de desprotección...
		
		ObservableList<CatalogInfo> situacionDesproteccionOptions = 
				FXCollections.observableArrayList(situacionDesproteccionInfoList);
		situacionDesproteccionComboBox = new  ListView<CatalogInfo>(situacionDesproteccionOptions);
		//situacionDesproteccionComboBox.setPromptText("Seleccione la situación de desprotección:");
		situacionDesproteccionComboBox.setPrefWidth(200);
		situacionDesproteccionComboBox.setPrefHeight(100);
		situacionDesproteccionComboBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		situacionDesproteccionComboBox.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ObservableList<CatalogInfo> selectedItems =  situacionDesproteccionComboBox.getSelectionModel().getSelectedItems();

				for(CatalogInfo s : selectedItems){
					if(s.getDescr().toLowerCase().equals("otros")){
						otrosSituacionDesproteccionTextField.setVisible(true);
						otraSituacionDesproteccionSelected = true;
					}else{
						otrosSituacionDesproteccionTextField.setVisible(false);
						otraSituacionDesproteccionSelected = false;
					}
				}

			}
		});
		 
		/*
		situacionDesproteccionComboBox.valueProperty().addListener(new ChangeListener<CatalogInfo>() {
			@Override public void changed(ObservableValue ov, CatalogInfo t, CatalogInfo t1) {	             
				if(t1.getDescr().toLowerCase().equals("otros")){
					otrosSituacionDesproteccionTextField.setVisible(true);
				}else{
					otrosSituacionDesproteccionTextField.setVisible(false);
				}
			}    
		});
		 */
		
		Icon addButton = new Icon(AwesomeIcon.PLUS,"2em",";","");
		Label addButtonLabel = new Label();
		addButtonLabel.setGraphic(addButton);
		this.addSituacionButton = new JFXButton();
		this.addSituacionButton.setGraphic(addButtonLabel);
		this.addSituacionButton.setCursor(Cursor.HAND);
		this.addSituacionButton.setTooltip(new Tooltip("Agregar situaciones de desprotección"));
		this.addSituacionButton.setOnAction(this);

		// Componente para los parentescos...
		ObservableList<CatalogInfo> parentescoOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getParentescoInfoList());
		parentescoComboBox = new  JFXComboBox<CatalogInfo>(parentescoOptions);
		parentescoComboBox.setPromptText("Seleccione el parentesco:");
		parentescoComboBox.setPrefWidth(400);
		parentescoComboBox.valueProperty().addListener(new ChangeListener<CatalogInfo>() {
			@Override public void changed(ObservableValue ov, CatalogInfo t, CatalogInfo t1) {	             
				if(t1.getDescr().toLowerCase().equals("otros")){
					otrosParentescoTextField.setVisible(true);
				}else{
					otrosParentescoTextField.setVisible(false);
				}
			}    
		});

		// Componente para las instituciones de referencia...
		ObservableList<CatalogInfo> institucionOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getInstitucionesInfoList());
		institucionesComboBox = new  JFXComboBox<CatalogInfo>(institucionOptions);
		institucionesComboBox.setPromptText("Seleccione la institución");
		institucionesComboBox.setPrefWidth(400);
		institucionesComboBox.valueProperty().addListener(new ChangeListener<CatalogInfo>() {
			@Override public void changed(ObservableValue ov, CatalogInfo t, CatalogInfo t1) {	             
				if(t1.getDescr().toLowerCase().equals("otros")){
					otrosInstitucionTextField.setVisible(true);
				}else{
					otrosInstitucionTextField.setVisible(false);
				}
			}    
		});

		// Componente para el grupo etnico...
		ObservableList<CatalogInfo> grupoEtnicoOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getGrupoEtnicoInfoList());
		grupoEtnicoComboBox = new  JFXComboBox<CatalogInfo>(grupoEtnicoOptions);
		grupoEtnicoComboBox.setPromptText("Seleccione el grupo étnico:");
		grupoEtnicoComboBox.setPrefWidth(400);
		grupoEtnicoComboBox.valueProperty().addListener(new ChangeListener<CatalogInfo>() {
			@Override public void changed(ObservableValue ov, CatalogInfo t, CatalogInfo t1) {	             
				if(t1.getDescr().toLowerCase().equals("indigena")){
					grupoIndigenaComboBox.setVisible(true);
				}else{
					grupoIndigenaComboBox.setVisible(false);
				}
			}    
		});

		// Componente para el grupo etnico...
		ObservableList<CatalogInfo> grupoIndigenaOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getGruposIndigenasInfoList());
		grupoIndigenaComboBox = new  JFXComboBox<CatalogInfo>(grupoIndigenaOptions);
		grupoIndigenaComboBox.setPromptText("Seleccione el grupo indígena:");
		grupoIndigenaComboBox.setPrefWidth(400);
		grupoIndigenaComboBox.setVisible(false);


		// Componente para el area geográfica...
		ObservableList<CatalogInfo> areaGeograficaOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getAreaGeograficaInfoList());
		areaGeograficaComboBox = new  JFXComboBox<CatalogInfo>(areaGeograficaOptions);
		areaGeograficaComboBox.setPromptText("Seleccione el área geográfica:");
		areaGeograficaComboBox.setPrefWidth(400);
		areaGeograficaComboBox.valueProperty().addListener(new ChangeListener<CatalogInfo>() {
			@Override public void changed(ObservableValue ov, CatalogInfo t, CatalogInfo t1) {	             
				if(t1.getDescr().toLowerCase().equals("extranjero")){
					otroAreaGeograficaComboBox.setVisible(true);
				}else{
					otroAreaGeograficaComboBox.setVisible(false);
				}
			}    
		});

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

		// Componente para ingresar el nombre...
		nombreVictimaTextField = new JFXTextField();
		nombreVictimaTextField.setPromptText("Ingrese nombres y apellidos del niño, niña o adolescente");
		nombreVictimaTextField.setPrefWidth(100);

		// Componente para la edad...
		ObservableList<CatalogInfo> edadOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getEdadInfoList());
		edadComboBox = new  JFXComboBox<CatalogInfo>(edadOptions);
		edadComboBox.setPromptText("Seleccione la edad:");
		edadComboBox.setPrefWidth(400);

		// Componente para la dirección...
		addressTextField = new JFXTextField();
		addressTextField.setPromptText("Ingrese el nombre de la aldea, caserio, canton o municipio cercano");
		addressTextField.setPrefWidth(100);

		// Componente para especificar otro tipo de parentesco...
		otrosParentescoTextField = new JFXTextField();
		otrosParentescoTextField.setPromptText("Describa el parentesco");
		otrosParentescoTextField.setPrefWidth(100);
		otrosParentescoTextField.setVisible(false);

		// Componente para otra situación de desprotección...
		otrosSituacionDesproteccionTextField = new JFXTextField();
		otrosSituacionDesproteccionTextField.setPromptText("Describa la situación de desprotección");
		otrosSituacionDesproteccionTextField.setPrefWidth(100);
		otrosSituacionDesproteccionTextField.setVisible(false);

		// Componente para describir otra institución..
		otrosInstitucionTextField = new JFXTextField();
		otrosInstitucionTextField.setPromptText("Describa el nombre de la institución");
		otrosInstitucionTextField.setPrefWidth(100);
		otrosInstitucionTextField.setVisible(false);

		// Componente para especificar otro tipo de parentesco...
		ObservableList<CatalogInfo> paisesOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getPaisesInfoList());
		otroAreaGeograficaComboBox = new  JFXComboBox<CatalogInfo>(paisesOptions);
		otroAreaGeograficaComboBox.setPromptText("Seleccione el país");
		otroAreaGeograficaComboBox.setPrefWidth(400);
		otroAreaGeograficaComboBox.setVisible(false);


		// Componente para la narración del hecho...
		hechoTextArea = new TextArea();
		hechoTextArea.setPrefRowCount(10);
		hechoTextArea.setPrefColumnCount(50);
		hechoTextArea.setWrapText(true);
		hechoTextArea.setPrefWidth(150);

		// Componente para la fecha del hecho...
		fechaTextField = new JFXTextField();
		fechaTextField.setPromptText("Fecha dd/mm/yyyy");
		//fechaTextField.setPrefWidth(100);
		MaskFieldUtil.dateField(fechaTextField);

		hourTextField = new JFXTextField();
		hourTextField.setPromptText("Hora 00:00");
		MaskFieldUtil.hourField(hourTextField);

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

		// Componente para datos del denunciante...
		ToggleGroup group2 = new ToggleGroup();	
		anonimoRadioButton = new JFXRadioButton("Anónimo");
		anonimoRadioButton.setPadding(new Insets(5));
		anonimoRadioButton.setToggleGroup(group2);
		idemRadioButton = new JFXRadioButton("Él mismo");
		idemRadioButton.setPadding(new Insets(5));
		idemRadioButton.setToggleGroup(group2);
		otroRadioButton = new JFXRadioButton("Otro");
		otroRadioButton.setPadding(new Insets(5));
		otroRadioButton.setToggleGroup(group2);
		group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle old_toggle, Toggle new_toggle) {
				if (group2.getSelectedToggle() != null) {
					if(group2.getSelectedToggle().toString().toLowerCase().contains("otro")){
						personaVBox.setVisible(true);
					}else{
						personaVBox.setVisible(false);
					}

				}                
			}
		});

		HBox usuarioGestionHBox = new HBox();
		usuarioGestionHBox.setSpacing(5);
		usuarioGestionHBox.getChildren().addAll(anonimoRadioButton,idemRadioButton,otroRadioButton);

		// Componente para ingresar el nombre de la persona...
		nombrePersonaTextField = new JFXTextField();
		nombrePersonaTextField.setPromptText("Ingrese los nombres de la persona que presenta el caso");
		nombrePersonaTextField.setPrefWidth(100);

		// Componente para ingresar la direccion de la persona...
		direccionPersonaTextField = new JFXTextField();
		direccionPersonaTextField.setPromptText("Ingrese la dirección de la persona que presenta el caso");
		direccionPersonaTextField.setPrefWidth(100);

		// Componente para ingresar la direccion de la persona...
		telefonoPersonaTextField = new JFXTextField();
		telefonoPersonaTextField.setPromptText("Ingrese el teléfono de la persona que presenta el caso");
		telefonoPersonaTextField.setPrefWidth(100);

		// Componente para ingresar el email de la persona...
		emailPersonaTextField = new JFXTextField();
		emailPersonaTextField.setPromptText("Ingrese la dirección de la persona que presenta el caso");
		emailPersonaTextField.setPrefWidth(100);

		// Componente para especificar otro tipo de parentesco...
		ObservableList<CatalogInfo> relacionPersonaOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getParentescoInfoList());
		parentescoPersonaComboBox = new  JFXComboBox<CatalogInfo>(relacionPersonaOptions);
		parentescoPersonaComboBox.setPromptText("Seleccione la relación con el NNA");
		parentescoPersonaComboBox.setPrefWidth(400);

		personaVBox = new VBox();
		personaVBox.setSpacing(10);
		personaVBox.getChildren().addAll(
				new Label("Nombre:"),nombrePersonaTextField, 
				new Label("Dirección:"),direccionPersonaTextField,
				new Label("Teléfono:"),telefonoPersonaTextField,
				new Label("Email:"),emailPersonaTextField,
				new Label("Relación con el NNA:"),parentescoPersonaComboBox
				);
		personaVBox.setVisible(false);

		// Toolbar...
		HBox toolbarHBox = new HBox();
		toolbarHBox.setSpacing(5);
		toolbarHBox.getChildren().addAll(clearButton,saveButton);

		// Título del forumario
		Label datosVictimaLabel = new Label("Datos del niño, niña o adolescente");
		datosVictimaLabel.setFont(Font.font("Arial", 18));
		datosVictimaLabel.setStyle("-fx-text-fill: #848484");

		// Definimos la caja principal para el formulario...
		HBox mainFormHBox = new HBox();
		mainFormHBox.setSpacing(60);
		mainFormHBox.setPadding(new Insets(10,20,20,40));
		mainFormHBox.setPrefWidth(500);

		Label personaGestionLabel = new Label("Persona que presenta el caso:");
		personaGestionLabel.setFont(Font.font("Arial", 18));
		personaGestionLabel.setStyle("-fx-text-fill: #848484");

		// Caja para los componentes de los datos de la víctima...
		VBox datosVictimaVBox = new VBox();
		datosVictimaVBox.setSpacing(10);
		datosVictimaVBox.getChildren().addAll(
				datosVictimaLabel,
				new Label("Nombre:"),nombreVictimaTextField, 
				new Label("Edad:"),edadComboBox,
				new Label("Lugar de residencia:"),addressTextField,
				new Label("Sexo:"),sexoHBox,
				new Label("Grupo étnico:"),grupoEtnicoComboBox,grupoIndigenaComboBox,
				new Label("Área geográfica:"),areaGeograficaComboBox,otroAreaGeograficaComboBox,
				personaGestionLabel,usuarioGestionHBox,personaVBox
				);

		// Título del forumario
		Label datosHechoLabel = new Label("Datos del hecho");
		datosHechoLabel.setFont(Font.font("Arial", 18));
		datosHechoLabel.setStyle("-fx-text-fill: #848484");

		// Caja para los componentes de los datos de la víctima...
		VBox datosHechoVBox = new VBox();
		datosHechoVBox.setSpacing(5);
		datosHechoVBox.getChildren().addAll(
				datosHechoLabel,				 
				new Label("Fecha y hora del hecho:"),dateHBox,
				new Label("Situación de desprotección:"),addSituacionButton,situacionDesproteccionComboBox,
				otrosSituacionDesproteccionTextField,
				new Label("Parentesco:"),parentescoComboBox,
				otrosParentescoTextField,
				new Label("Referido a:"), institucionesComboBox,
				otrosInstitucionTextField,
				new Label("Narración del hecho:"),hechoTextArea,
				toolbarHBox
				);


		mainFormHBox.getChildren().addAll(datosVictimaVBox, datosHechoVBox);
		ScrollPane scrollPane = new ScrollPane(mainFormHBox);
		scrollPane.setFitToWidth(true);
		mainBorderPane.setCenter(scrollPane);
		return mainBorderPane;
	}
	
	public void updateSelectedSituaciones(){
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<CatalogInfo> situacionDesproteccionOptions = 
						FXCollections.observableArrayList(situacionDesproteccionInfoList);
				situacionDesproteccionComboBox.setItems(situacionDesproteccionOptions);
			}
		}); 
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


		if(this.currentCasoDesproteccionSocialInfo != null){
			if(this.currentSeguimientoInfo == null){
				this.currentSeguimientoInfo = new SeguimientoCasoInfo();
				String id = UUID.randomUUID().toString();
				this.currentSeguimientoInfo.setId(id);
				this.currentSeguimientoInfo.setCasoId(this.currentCasoDesproteccionSocialInfo.getId());
				this.currentSeguimientoInfo.setCodigoMunicipal(this.currentCasoDesproteccionSocialInfo.getCodigoMunicipal());
				this.currentSeguimientoInfo.setAno(this.currentCasoDesproteccionSocialInfo.getAno());
				this.currentSeguimientoInfo.setCorrelativo(this.currentCasoDesproteccionSocialInfo.getCorrelativo());
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
		String nombre = this.nombreVictimaTextField.getText();
		if(nombre.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					nombreVictimaTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el nombre del niño, niña o adolescente";
		}
		CatalogInfo edad = this.edadComboBox.getSelectionModel().getSelectedItem();
		if(edad == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					edadComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar la edad del niño, niña o adolescente";
		}
		String residencia = this.addressTextField.getText();
		if(residencia.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					addressTextField.requestFocus();
				}
			}); 
			return "Debe ingresar el lugar de residencia del niño, niña o adolescente";
		}
		Boolean female = this.femaleRadioButton.isSelected();
		Boolean male = this.maleRadioButton.isSelected();
		if(!female && !male){
			return "Debe seleccionar el sexo del niño, niña o adolescente";
		}
		CatalogInfo grupoEtnico = this.grupoEtnicoComboBox.getSelectionModel().getSelectedItem();
		if(grupoEtnico == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					grupoEtnicoComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar el grupo étnico del niño, niña o adolescente";
		}
		CatalogInfo grupoIndigena = null;
		if(grupoEtnico != null){
			if(grupoEtnico.getDescr().toLowerCase().equals("indigena")){
				grupoIndigena = this.grupoIndigenaComboBox.getValue();
				if(grupoIndigena == null){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							grupoIndigenaComboBox.requestFocus();
						}
					}); 
					return "Debe seleccionar el grupo indígena del niño, niña o adolescente";
				}
			}
		}

		CatalogInfo areaGeografica = this.areaGeograficaComboBox.getSelectionModel().getSelectedItem();
		if(areaGeografica == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					areaGeograficaComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar el área geográfica del niño, niña o adolescente";
		}
		CatalogInfo otraArea = null;
		if(areaGeografica != null){
			if(areaGeografica.getDescr().toLowerCase().equals("extranjero")){
				otraArea = this.otroAreaGeograficaComboBox.getValue();
				if(otraArea == null){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							otroAreaGeograficaComboBox.requestFocus();
						}
					}); 
					return "Debe seleccionar el país del niño, niña o adolescente";
				}
			}
		}

		Boolean anonimo = this.anonimoRadioButton.isSelected();
		Boolean elMismo = this.idemRadioButton.isSelected();
		Boolean otro = this.otroRadioButton.isSelected();
		String nombrePersona = this.nombrePersonaTextField.getText();
		String direccionPersona = this.direccionPersonaTextField.getText();
		String telefonoPersona = this.telefonoPersonaTextField.getText();
		String emailPersona = this.emailPersonaTextField.getText();
		CatalogInfo parentescoPersona = this.parentescoPersonaComboBox.getSelectionModel().getSelectedItem();

		if(!anonimo && !elMismo && !otro){
			return "Debe seleccionar el tipo de persona que presenta el caso";
		}
		if(otro){
			if(nombrePersona.trim().length() == 0){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						nombrePersonaTextField.requestFocus();
					}
				}); 
				return "Debe ingresar el nombre de la persona que presenta el caso";
			}
			if(direccionPersona.trim().length() == 0){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						direccionPersonaTextField.requestFocus();
					}
				}); 
				return "Debe ingresar la dirección de la persona que presenta el caso";
			}
			if(telefonoPersona.trim().length() == 0){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						telefonoPersonaTextField.requestFocus();
					}
				}); 
				return "Debe ingresar el teléfono de la persona que presenta el caso";
			}
			if(emailPersona.trim().length() == 0){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						emailPersonaTextField.requestFocus();
					}
				}); 
				return "Debe ingresar el email de la persona que presenta el caso";
			}
			if(parentescoPersona == null){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						parentescoPersonaComboBox.requestFocus();
					}
				}); 
				return "Debe seleccionar la relación con el NNA";
			}
		}

		String fecha = this.fechaTextField.getText();
		if(fecha.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la fecha del hecho";
		}
		String hour = this.hourTextField.getText();
		if(hour.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					hourTextField.requestFocus();
				}
			}); 
			return "Debe ingresar la hora del hecho";
		}
		String date = fecha +" "+hour;
		if(TimeUtil.getDateTimeFormat(date) == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					fechaTextField.requestFocus();
				}
			}); 
			return "Fecha y hora del hecho no válida";
		}
		String otraSituacionDesproteccion = "";
		ObservableList<CatalogInfo> situaciones = this.situacionDesproteccionComboBox.getItems();
		if(situaciones != null){
			if(situaciones.isEmpty()){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						situacionDesproteccionComboBox.requestFocus();
					}
				}); 
				return "Debe seleccionar una o varias situaciones de desprotección";
			}else{
				if(this.otraSituacionDesproteccionSelected){
					otraSituacionDesproteccion = otrosSituacionDesproteccionTextField.getText();
					if(otraSituacionDesproteccion.trim().length() == 0){
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								otrosSituacionDesproteccionTextField.requestFocus();
							}
						}); 
						return "Describa la situación de desprotección identificada";
					}
				}
			}
		}
		CatalogInfo parentezco = this.parentescoComboBox.getSelectionModel().getSelectedItem();
		if(parentezco == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					parentescoComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar el parentesco del agresro con el niño, niña o adolescente";
		}
		String otroParentezco = "";
		if(parentezco != null){
			if(parentezco.getDescr().toLowerCase().equals("otros")){
				otroParentezco = this.otrosParentescoTextField.getText();
				if(otroParentezco.trim().length() == 0){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							otrosParentescoTextField.requestFocus();
						}
					}); 
					return "Debe describir el parentesco del agresor con el niño, niña o adolescente";
				}
			}
		}
		CatalogInfo instituciones = this.institucionesComboBox.getSelectionModel().getSelectedItem();
		if(instituciones == null){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					institucionesComboBox.requestFocus();
				}
			}); 
			return "Debe seleccionar la institución a la cual se refirió el caso";
		}
		String otraInstitucion = "";
		if(instituciones != null){
			if(instituciones.getDescr().toLowerCase().equals("otros")){
				otraInstitucion = this.otrosInstitucionTextField.getText();
				if(otraInstitucion.trim().length() == 0){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							otrosInstitucionTextField.requestFocus();
						}
					}); 
					return "Debe describir el nombre de la institución a la cual se refirió el caso";
				}
			}
		}
		String narracion = this.hechoTextArea.getText();
		if(narracion.trim().length() == 0){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					hechoTextArea.requestFocus();
				}
			}); 
			return "Debe ingresar una breve narración del hecho";
		}



		if(this.currentCasoDesproteccionSocialInfo == null){
			this.currentCasoDesproteccionSocialInfo = new CasoDesproteccionSocialInfo();
			String id = UUID.randomUUID().toString();
			this.currentCasoDesproteccionSocialInfo.setId(id);
			SettingsDbActions dbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = dbActions.getSettingsInfo();
			this.currentCasoDesproteccionSocialInfo.setCodigoMunicipal(settingsInfo.getCode());
			String ano = String.valueOf(TimeUtil.getCurrentYear());
			this.currentCasoDesproteccionSocialInfo.setAno(ano);
			this.currentCasoDesproteccionSocialInfo.setRecordDate(TimeUtil.now());
			CasoDesproteccionSocialDbActions casoDesprotecccionDbActions = new CasoDesproteccionSocialDbActions();
			String correlativo = String.valueOf(casoDesprotecccionDbActions.getCorrelativo());
			this.currentCasoDesproteccionSocialInfo.setCorrelativo(correlativo);
		}
		this.currentCasoDesproteccionSocialInfo.setNombre(nombre.trim());
		this.currentCasoDesproteccionSocialInfo.setEdadId(edad.getId());
		this.currentCasoDesproteccionSocialInfo.setEdadDescr(edad.getDescr());
		this.currentCasoDesproteccionSocialInfo.setLugarResidencia(residencia.trim());
		if(female){
			this.currentCasoDesproteccionSocialInfo.setSexo("F");
		}
		if(male){
			this.currentCasoDesproteccionSocialInfo.setSexo("M");
		}
		this.currentCasoDesproteccionSocialInfo.setGrupoEtnicoId(grupoEtnico.getId());
		this.currentCasoDesproteccionSocialInfo.setGrupoEtnicoDescr(grupoEtnico.getDescr());
		this.currentCasoDesproteccionSocialInfo.setAreaGeograficaId(areaGeografica.getId());
		this.currentCasoDesproteccionSocialInfo.setAreaGeograficaDescr(areaGeografica.getDescr());
		if(grupoIndigena != null){
			this.currentCasoDesproteccionSocialInfo.setGrupoIndigenaId(grupoIndigena.getId());
			this.currentCasoDesproteccionSocialInfo.setGrupoIndigenaDescr(grupoIndigena.getDescr());
		}
		if(otraArea != null){
			this.currentCasoDesproteccionSocialInfo.setOtraAreaGeograficaId(otraArea.getId());
			this.currentCasoDesproteccionSocialInfo.setOtraAreaGeograficaDescr(otraArea.getDescr());
		}
		this.currentCasoDesproteccionSocialInfo.setFecha(fecha);
		this.currentCasoDesproteccionSocialInfo.setHora(hour);
		ArrayList<String> situacionesDesproteccionInfoList = new ArrayList<String>();
		for(CatalogInfo situacionInfo : situaciones){
			situacionesDesproteccionInfoList.add(situacionInfo.getId());
		}
		this.currentCasoDesproteccionSocialInfo.setSituacionDesproteccionList(situacionesDesproteccionInfoList);
		this.currentCasoDesproteccionSocialInfo.setOtraSituacionDesproteccion(otraSituacionDesproteccion.trim());
		this.currentCasoDesproteccionSocialInfo.setParentescoId(parentezco.getId());
		this.currentCasoDesproteccionSocialInfo.setParentescoDescr(parentezco.getDescr());
		this.currentCasoDesproteccionSocialInfo.setOtroParentesco(otroParentezco);
		this.currentCasoDesproteccionSocialInfo.setInstitucionId(instituciones.getId());
		this.currentCasoDesproteccionSocialInfo.setInstitucionDescr(instituciones.getDescr());
		this.currentCasoDesproteccionSocialInfo.setOtroInstitucion(otraInstitucion);
		this.currentCasoDesproteccionSocialInfo.setNarracion(narracion);
		this.currentCasoDesproteccionSocialInfo.setLastUpdate(TimeUtil.now());
		this.currentCasoDesproteccionSocialInfo.setOtro(otro);
		this.currentCasoDesproteccionSocialInfo.setAnonimo(anonimo);
		this.currentCasoDesproteccionSocialInfo.setElMismo(elMismo);
		this.currentCasoDesproteccionSocialInfo.setNombrePersona(nombrePersona);
		this.currentCasoDesproteccionSocialInfo.setDireccionPersona(direccionPersona);
		this.currentCasoDesproteccionSocialInfo.setTelefonoPersona(telefonoPersona);
		this.currentCasoDesproteccionSocialInfo.setEmailPersona(emailPersona);
		if(parentescoPersona != null){
			this.currentCasoDesproteccionSocialInfo.setParentescoPersonaId(parentescoPersona.getId());
			this.currentCasoDesproteccionSocialInfo.setParentescoPersonaDescr(parentescoPersona.getDescr());
		}
		return null;

	}

	public void clearNewCase(){
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentCasoDesproteccionSocialInfo = null;
				situacionDesproteccionComboBox.getItems().clear();
				situacionDesproteccionInfoList.clear();
				nombreVictimaTextField.setText("");
				edadComboBox.getSelectionModel().select(null);
				addressTextField.setText("");
				hourTextField.setText("");
				otrosSituacionDesproteccionTextField.setText("");
				otrosParentescoTextField.setText("");
				otrosInstitucionTextField.setText("");
				otroAreaGeograficaComboBox.getSelectionModel().select(null);
				fechaTextField.setText("");
				hechoTextArea.setText("");
				maleRadioButton.setSelected(false);
				femaleRadioButton.setSelected(false);
				grupoEtnicoComboBox.getSelectionModel().select(null);
				parentescoComboBox.getSelectionModel().select(null);
				areaGeograficaComboBox.getSelectionModel().select(null);
				institucionesComboBox.getSelectionModel().select(null);
				grupoIndigenaComboBox.getSelectionModel().select(null);
				anonimoRadioButton.setSelected(false);
				otroRadioButton.setSelected(false);
				idemRadioButton.setSelected(false);
				otrosSituacionDesproteccionTextField.setVisible(false);
				otroAreaGeograficaComboBox.setVisible(false);
				otrosInstitucionTextField.setVisible(false);
				otrosParentescoTextField.setVisible(false);
				grupoIndigenaComboBox.setVisible(false);
				nombrePersonaTextField.setText("");;
				direccionPersonaTextField.setText("");
				emailPersonaTextField.setText("");
				telefonoPersonaTextField.setText("");
				parentescoPersonaComboBox.getSelectionModel().select(null);
				personaVBox.setVisible(false);
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

	/************************************************************/
	/*          PANEL DE SEGUIMIENTO                    */
	/************************************************************/

	public BorderPane buildSeguimientoBorderPane(){
		BorderPane mainBorderPane = new BorderPane();

		// Información del caso...
		Label titleLabel = new Label("Información del Caso");
		ScrollPane scrollPane = new ScrollPane(this.infoCasoVBox);
		scrollPane.setPrefWidth(400);
		scrollPane.setFitToWidth(true);

		// Información del caso...
		Label tseguimientoLabel = new Label("Seguimiento del Caso");
		seguimientoInfoPanelVBox = new VBox();
		seguimientoInfoPanelVBox.setPadding(new Insets(10,10,10,10));
		seguimientoInfoPanelVBox.setSpacing(10);

		ScrollPane seguimientoScrollPane = new ScrollPane(this.seguimientoInfoPanelVBox);
		seguimientoScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		seguimientoScrollPane.setPrefWidth(400);
		seguimientoScrollPane.setFitToWidth(true);

		// Formulario de seguimiento...

		// Componente para el estado del caso...
		ObservableList<CatalogInfo> estadosOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getEstadoCasoInfoList());
		estadosComboBox = new  JFXComboBox<CatalogInfo>(estadosOptions);
		estadosComboBox.setPromptText("Estado del caso:");
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
				new Label("Nuevo estado del caso:"),estadosComboBox,
				new Label("Descripción de la acción:"),observacionesTextArea,
				saveSeguimientoButton
				);

		BorderPane formBorderPane = new BorderPane();
		formBorderPane.setTop(formVBox);

		mainBorderPane.setLeft(scrollPane);
		mainBorderPane.setCenter(formBorderPane);
		mainBorderPane.setRight(seguimientoScrollPane);
		return mainBorderPane;
	}

	public TableView<CasoDesproteccionSocialInfo> createTable() {
		if(this.casosDesproteccionTableView != null){
			this.casosDesproteccionTableView.getItems().clear();
		}
		this.casosDesproteccionTableView = new TableView<CasoDesproteccionSocialInfo>();


		TableColumn<CasoDesproteccionSocialInfo, String> numeroCasoColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("# Caso");
		numeroCasoColumn.setMinWidth(120);
		numeroCasoColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("numeroCaso"));
		TableColumn<CasoDesproteccionSocialInfo, String> fechaColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("Fecha");
		fechaColumn.setMinWidth(120);
		fechaColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("fechaRegistroFormato"));
		TableColumn<CasoDesproteccionSocialInfo, String> nombreColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("Niño, Niña, Adolescente");
		nombreColumn.setMinWidth(280);
		nombreColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("nombre"));
		TableColumn<CasoDesproteccionSocialInfo, String> agresorColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("Agresor");
		agresorColumn.setMinWidth(280);
		agresorColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("parentescoDescr"));

		this.casosDesproteccionTableView.getColumns().add(numeroCasoColumn);
		this.casosDesproteccionTableView.getColumns().add(fechaColumn);
		this.casosDesproteccionTableView.getColumns().add(nombreColumn);
		this.casosDesproteccionTableView.getColumns().add(agresorColumn);

		CasoDesproteccionSocialDbActions casoDesproteccionDbActions = new CasoDesproteccionSocialDbActions();
		this.casoDesproteccionInfoList = casoDesproteccionDbActions.getCurrentYearCases();

		if(this.casoDesproteccionInfoList != null){
			this.casosDesproteccionTableView.setItems(FXCollections.observableArrayList(this.casoDesproteccionInfoList));
		}
		this.casosDesproteccionTableView.getSelectionModel().selectedItemProperty().addListener(this);

		return this.casosDesproteccionTableView;

	}

	//Tabla de búsqueda....
	public BorderPane buildCasosDesproteccionTablaBorderPane(Date fechaInicial, Date fechaFinal){
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

	public TableView<CasoDesproteccionSocialInfo> createTable(Date fechaInicial, Date fechaFinal) {
		if(this.casosDesproteccionTableView != null){
			this.casosDesproteccionTableView.getItems().clear();
		}
		this.casosDesproteccionTableView = new TableView<CasoDesproteccionSocialInfo>();


		TableColumn<CasoDesproteccionSocialInfo, String> numeroCasoColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("# Caso");
		numeroCasoColumn.setMinWidth(120);
		numeroCasoColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("numeroCaso"));
		TableColumn<CasoDesproteccionSocialInfo, String> fechaColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("Fecha");
		fechaColumn.setMinWidth(120);
		fechaColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("fechaRegistroFormato"));
		TableColumn<CasoDesproteccionSocialInfo, String> nombreColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("Niño, Niña, Adolescente");
		nombreColumn.setMinWidth(280);
		nombreColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("nombre"));
		TableColumn<CasoDesproteccionSocialInfo, String> agresorColumn = new TableColumn<CasoDesproteccionSocialInfo, String>("Agresor");
		agresorColumn.setMinWidth(280);
		agresorColumn.setCellValueFactory(new PropertyValueFactory<CasoDesproteccionSocialInfo, String>("parentescoDescr"));

		this.casosDesproteccionTableView.getColumns().add(numeroCasoColumn);
		this.casosDesproteccionTableView.getColumns().add(fechaColumn);
		this.casosDesproteccionTableView.getColumns().add(nombreColumn);
		this.casosDesproteccionTableView.getColumns().add(agresorColumn);

		CasoDesproteccionSocialDbActions casoDesproteccionDbActions = new CasoDesproteccionSocialDbActions();
		this.casoDesproteccionInfoList = casoDesproteccionDbActions.getCasosByDate(fechaInicial, fechaFinal);
		this.fechaInicioJFXTextField.setText(TimeUtil.getSimpleTimeFormat(fechaInicial));
		this.fechaFinJFXTextField.setText(TimeUtil.getSimpleTimeFormat(fechaFinal));

		if(this.casoDesproteccionInfoList != null){
			this.casosDesproteccionTableView.setItems(FXCollections.observableArrayList(this.casoDesproteccionInfoList));
		}
		this.casosDesproteccionTableView.getSelectionModel().selectedItemProperty().addListener(this);

		return this.casosDesproteccionTableView;

	}

	public void loadInfo(){
		if(this.currentCasoDesproteccionSocialInfo != null){
			// Obtenemos la información del POJO...
			String nombre = this.currentCasoDesproteccionSocialInfo.getNombre();
			String edadId = this.currentCasoDesproteccionSocialInfo.getEdadId();
			String edadDescr = this.currentCasoDesproteccionSocialInfo.getEdadDescr();
			CatalogInfo edadInfo = new CatalogInfo(edadId, edadDescr);
			String residencia = this.currentCasoDesproteccionSocialInfo.getLugarResidencia();
			String sexo = this.currentCasoDesproteccionSocialInfo.getSexo();
			String grupoEtnico = this.currentCasoDesproteccionSocialInfo.getGrupoEtnicoId();
			String grupoEtnicoDescr = this.currentCasoDesproteccionSocialInfo.getGrupoEtnicoDescr();
			CatalogInfo grupoInfo = new CatalogInfo(grupoEtnico, grupoEtnicoDescr);
			String grupoIndigenaId = this.currentCasoDesproteccionSocialInfo.getGrupoIndigenaId();
			String grupoIndigenaDescr = this.currentCasoDesproteccionSocialInfo.getGrupoIndigenaDescr();
			String areaGeograficaId = this.currentCasoDesproteccionSocialInfo.getAreaGeograficaId();
			String areaGeograficaDescr = this.currentCasoDesproteccionSocialInfo.getAreaGeograficaDescr();
			CatalogInfo areaInfo = new CatalogInfo(areaGeograficaId, areaGeograficaDescr);
			String paisId = this.currentCasoDesproteccionSocialInfo.getOtraAreaGeograficaId();
			String paisDescr = this.currentCasoDesproteccionSocialInfo.getOtraAreaGeograficaDescr();
			Boolean anonimo = this.currentCasoDesproteccionSocialInfo.getAnonimo();
			Boolean idem = this.currentCasoDesproteccionSocialInfo.getElMismo();
			Boolean otro = this.currentCasoDesproteccionSocialInfo.getOtro();
			String nombrePersona = this.currentCasoDesproteccionSocialInfo.getNombrePersona();
			String direccion = this.currentCasoDesproteccionSocialInfo.getDireccionPersona();
			String telefono = this.currentCasoDesproteccionSocialInfo.getTelefonoPersona();
			String email = this.currentCasoDesproteccionSocialInfo.getEmailPersona();
			String parentescoPersonaId = this.currentCasoDesproteccionSocialInfo.getParentescoPersonaId();
			String parentescoPersonaDescr = this.currentCasoDesproteccionSocialInfo.getParentescoPersonaDescr();
			String fecha = this.currentCasoDesproteccionSocialInfo.getFecha();
			String hora = this.currentCasoDesproteccionSocialInfo.getHora();
			ArrayList<String> situacionesList = this.currentCasoDesproteccionSocialInfo.getSituacionDesproteccionList();
			String parentescoId = this.currentCasoDesproteccionSocialInfo.getParentescoId();
			String parentescoDescr = this.currentCasoDesproteccionSocialInfo.getParentescoDescr();
			CatalogInfo parentescoInfo = new CatalogInfo(parentescoId, parentescoDescr);
			String otroParentesco = this.currentCasoDesproteccionSocialInfo.getOtroParentesco();
			String institucionId = this.currentCasoDesproteccionSocialInfo.getInstitucionId();
			String institucionDescr = this.currentCasoDesproteccionSocialInfo.getInstitucionDescr();
			CatalogInfo institucionInfo = new CatalogInfo(institucionId, institucionDescr);
			String otraInstitucion = this.currentCasoDesproteccionSocialInfo.getOtroInstitucion();
			String narracion = this.currentCasoDesproteccionSocialInfo.getNarracion();
			String otraSituacion = this.currentCasoDesproteccionSocialInfo.getOtraSituacionDesproteccion();
			String caso = this.currentCasoDesproteccionSocialInfo.getNumeroCaso();
			// Seteamos los valores a los campos para la edición...
			this.nombreVictimaTextField.setText(nombre);
			this.addressTextField.setText(residencia);
			this.edadComboBox.getSelectionModel().select(edadInfo);
			if(sexo.equals("F")){
				this.femaleRadioButton.setSelected(true);
			}
			if(sexo.equals("M")){
				this.maleRadioButton.setSelected(true);
			}
			this.grupoEtnicoComboBox.getSelectionModel().select(grupoInfo);
			if((grupoIndigenaId != null) && (grupoIndigenaDescr != null)){
				CatalogInfo grupoIndigenaInfo = new CatalogInfo(grupoIndigenaId,grupoIndigenaDescr);
				this.grupoIndigenaComboBox.getSelectionModel().select(grupoIndigenaInfo);
			}
			this.areaGeograficaComboBox.getSelectionModel().select(areaInfo);
			if(paisId != null && paisDescr != null){
				CatalogInfo paisInfo = new CatalogInfo(paisId, paisDescr);
				this.otroAreaGeograficaComboBox.getSelectionModel().select(paisInfo);
			}
			this.anonimoRadioButton.setSelected(anonimo);
			this.idemRadioButton.setSelected(idem);
			this.otroRadioButton.setSelected(otro);
			if(nombrePersona != null){
				this.nombrePersonaTextField.setText(nombrePersona);
			}
			if(direccion != null){
				this.direccionPersonaTextField.setText(direccion);
			}
			if(telefono != null){
				this.telefonoPersonaTextField.setText(telefono);
			}
			if(email != null){
				this.emailPersonaTextField.setText(email);
			}
			if(parentescoPersonaId != null && parentescoPersonaDescr != null){
				CatalogInfo parentescoPersonaInfo = new CatalogInfo(parentescoPersonaId, parentescoPersonaDescr);
				this.parentescoPersonaComboBox.getSelectionModel().select(parentescoPersonaInfo);
			}
			this.fechaTextField.setText(fecha);
			this.hourTextField.setText(hora);
			this.parentescoComboBox.getSelectionModel().select(parentescoInfo);
			if(otroParentesco != null){
				this.otrosParentescoTextField.setText(otroParentesco);
			}
			this.institucionesComboBox.getSelectionModel().select(institucionInfo);
			this.otrosInstitucionTextField.setText(otraInstitucion);
			this.hechoTextArea.setText(narracion);
			this.situacionDesproteccionInfoList.clear();
			for(String situacionId : situacionesList){
				System.out.println(situacionId);
				CatalogInfo situacionInfo = DashboardGlobalInfo.getSituacionDesproteccionSocialInfo(situacionId);
				this.situacionDesproteccionInfoList.add(situacionInfo);
				//this.situacionDesproteccionComboBox.getSelectionModel().select(situacionInfo);
				this.updateSelectedSituaciones();
				if(situacionInfo.getDescr().toLowerCase().equals("otros")){
					this.otrosSituacionDesproteccionTextField.setVisible(true);
					this.otrosSituacionDesproteccionTextField.setText(otraSituacion);
				}
			}

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Creamos el panel para el display de la información del caso...
					infoCasoVBox.getChildren().clear();
					Label titleLabel = new Label("Información del caso");
					titleLabel.setFont(Font.font("Arial", 18));
					titleLabel.setStyle("-fx-text-fill: #848484");

					Label title1Label = new Label("Información del niño, niña o adolescente");
					title1Label.setFont(Font.font("Arial", 14));
					title1Label.setStyle("-fx-text-fill: #848484");

					Label nombreLabel = new Label("Nombre:");
					nombreLabel.setFont(Font.font("Arial", 12));
					nombreLabel.setStyle("-fx-text-fill: #848484");

					Label edadLabel = new Label("Edad:");
					edadLabel.setFont(Font.font("Arial", 12));
					edadLabel.setStyle("-fx-text-fill: #848484");

					Label residenciaLabel = new Label("Lugar de residencia:");
					residenciaLabel.setFont(Font.font("Arial", 12));
					residenciaLabel.setStyle("-fx-text-fill: #848484");

					Label sexoLabel = new Label("Sexo:");
					sexoLabel.setFont(Font.font("Arial", 12));
					sexoLabel.setStyle("-fx-text-fill: #848484");

					Label grupoEtnicoLabel = new Label("Grupo étnico:");
					grupoEtnicoLabel.setFont(Font.font("Arial", 12));
					grupoEtnicoLabel.setStyle("-fx-text-fill: #848484");

					Label areaGeograficaLabel = new Label("Área geográfica:");
					areaGeograficaLabel.setFont(Font.font("Arial", 12));
					areaGeograficaLabel.setStyle("-fx-text-fill: #848484");

					Label title2Label = new Label("Persona que presenta el caso");
					title2Label.setFont(Font.font("Arial", 14));
					title2Label.setStyle("-fx-text-fill: #848484");

					Label title3Label = new Label("Datos del hecho");
					title3Label.setFont(Font.font("Arial", 14));
					title3Label.setStyle("-fx-text-fill: #848484");

					Label fechaLabel = new Label("Fecha del hecho:");
					fechaLabel.setFont(Font.font("Arial", 12));
					fechaLabel.setStyle("-fx-text-fill: #848484");

					Label situacionDesproteccionLabel = new Label("Situaciones de desprotección:");
					situacionDesproteccionLabel.setFont(Font.font("Arial", 12));
					situacionDesproteccionLabel.setStyle("-fx-text-fill: #848484");

					Label parentescoLabel = new Label("Parentesco con el agresor:");
					parentescoLabel.setFont(Font.font("Arial", 12));
					parentescoLabel.setStyle("-fx-text-fill: #848484");

					Label referidoLabel = new Label("Referido a:");
					referidoLabel.setFont(Font.font("Arial", 12));
					referidoLabel.setStyle("-fx-text-fill: #848484");

					Label narracionLabel = new Label("Narración del hecho:");
					narracionLabel.setFont(Font.font("Arial", 12));
					narracionLabel.setStyle("-fx-text-fill: #848484");

					infoCasoVBox.getChildren().add(titleLabel);
					infoCasoVBox.getChildren().add(new Label(caso));
					infoCasoVBox.getChildren().add(title1Label);
					infoCasoVBox.getChildren().addAll(nombreLabel,new Label(nombre));
					infoCasoVBox.getChildren().addAll(edadLabel,new Label(edadDescr));
					infoCasoVBox.getChildren().addAll(residenciaLabel,new Label(residencia));
					if(sexo.equals("F")){
						infoCasoVBox.getChildren().addAll(sexoLabel,new Label("Mujer"));
					}
					if(sexo.equals("M")){
						infoCasoVBox.getChildren().addAll(sexoLabel,new Label("Hombre"));
					}
					infoCasoVBox.getChildren().addAll(grupoEtnicoLabel,new Label(grupoEtnicoDescr));
					if((grupoIndigenaId != null) && (grupoIndigenaDescr != null)){
						infoCasoVBox.getChildren().addAll(new Label(grupoIndigenaDescr));
					}
					infoCasoVBox.getChildren().addAll(areaGeograficaLabel,new Label(areaGeograficaDescr));
					if(paisId != null && paisDescr != null){
						infoCasoVBox.getChildren().addAll(new Label(paisDescr));
					}
					infoCasoVBox.getChildren().add(title2Label);
					if(anonimo){
						infoCasoVBox.getChildren().addAll(new Label("Anónimo"));
					}
					if(idem){
						infoCasoVBox.getChildren().addAll(new Label("Él mismo"));
					}
					if(otro){
						if(nombrePersona != null){
							Label nombrePersonaLabel = new Label("Nombre:");
							nombrePersonaLabel.setFont(Font.font("Arial", 12));
							nombrePersonaLabel.setStyle("-fx-text-fill: #848484");
							infoCasoVBox.getChildren().addAll(nombrePersonaLabel,new Label(nombrePersona));
						}
						if(direccion != null){
							Label direccionPersonaLabel = new Label("Dirección:");
							direccionPersonaLabel.setFont(Font.font("Arial", 12));
							direccionPersonaLabel.setStyle("-fx-text-fill: #848484");
							infoCasoVBox.getChildren().addAll(direccionPersonaLabel,new Label(direccion));
						}
						if(telefono != null){
							Label telefonoPersonaLabel = new Label("Teléfono:");
							telefonoPersonaLabel.setFont(Font.font("Arial", 12));
							telefonoPersonaLabel.setStyle("-fx-text-fill: #848484");
							infoCasoVBox.getChildren().addAll(telefonoPersonaLabel,new Label(telefono));
						}
						if(email != null){
							Label emailPersonaLabel = new Label("Email:");
							emailPersonaLabel.setFont(Font.font("Arial", 12));
							emailPersonaLabel.setStyle("-fx-text-fill: #848484");
							infoCasoVBox.getChildren().addAll(emailPersonaLabel,new Label(email));
						}
					}
					infoCasoVBox.getChildren().add(title3Label);
					infoCasoVBox.getChildren().addAll(fechaLabel, new Label(fecha+" "+hora));
					infoCasoVBox.getChildren().addAll(situacionDesproteccionLabel);
					for(String situacionId : situacionesList){
						CatalogInfo situacionInfo = DashboardGlobalInfo.getSituacionDesproteccionInfo(situacionId);
						if(situacionInfo.getDescr().toLowerCase().equals("otros")){
							infoCasoVBox.getChildren().add(new Label("Otros, "+otraSituacion));
						}else{
							infoCasoVBox.getChildren().add(new Label(situacionInfo.getDescr()));
						}
					}
					infoCasoVBox.getChildren().addAll(parentescoLabel, new Label(parentescoDescr));
					if(otroParentesco != null){
						infoCasoVBox.getChildren().add(new Label(otroParentesco));
					}
					infoCasoVBox.getChildren().addAll(referidoLabel, new Label(institucionInfo.getDescr()));
					if(otraInstitucion != null){
						infoCasoVBox.getChildren().addAll(new Label(otraInstitucion));
					}
					infoCasoVBox.getChildren().addAll(narracionLabel, new Label(narracion));
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
					ArrayList<SeguimientoCasoInfo> seguimientoCasoInfoList = seguimientoCasoDbActions.getEventosSeguimiento(currentCasoDesproteccionSocialInfo.getId());
					if(seguimientoCasoInfoList != null){
						int count = 0;
						for(SeguimientoCasoInfo info : seguimientoCasoInfoList){
							count ++;
							Label fechaLabel = new Label(count+". Fecha:");
							fechaLabel.setFont(Font.font("Arial", 12));
							fechaLabel.setStyle("-fx-text-fill: #848484");
							Label estadoLabel = new Label("Estado:");
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
			this.casoDesproteccionTablaBorderPane = this.buildCasosDesproteccionTablaBorderPane();
			//createTable();
			this.borderPane.setCenter(this.casoDesproteccionTablaBorderPane);
		}
		if(eventSource == this.seguimientoButton){
			if(this.currentCasoDesproteccionSocialInfo != null){
				this.seguimientoBorderPane = this.buildSeguimientoBorderPane();
				this.borderPane.setCenter(this.seguimientoBorderPane);
				buildSeguimientoInfoPanel();
			}else{
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar un caso para usar la opción de seguimiento");
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
					this.casoDesproteccionTablaBorderPane = this.buildCasosDesproteccionTablaBorderPane(fechaIni, fechaFin);
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
						CasoDesproteccionSocialDbActions casoDesproteccionDbActions = new CasoDesproteccionSocialDbActions();
						Boolean save = 	casoDesproteccionDbActions.saveCasoDesproteccion(currentCasoDesproteccionSocialInfo);
						return save;
					}
				};
				task.setOnRunning((e) -> waitDialogStage.show());
				task.setOnSucceeded((e) -> {
					waitDialogStage.hide();
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.INFO, "Caso guardado con éxito");
					messageDialogStage.show();
					Boolean returnValue = true;
					// process return value again in JavaFX thread
				});
				task.setOnFailed((e) -> {
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "No pudo guardarse el caso");
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
		if (eventSource == this.addSituacionButton){
			SituacionesSocialesModalDialog situacionesModalDialog = new SituacionesSocialesModalDialog(this.primaryStage, this);
			Stage situacionesModalDialogStage = situacionesModalDialog.buildModalDialogStage();
			situacionesModalDialogStage.show();
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
			if(this.currentCasoDesproteccionSocialInfo != null){
				FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Guardar Reporte");
	            
	            File file = fileChooser.showSaveDialog(this.primaryStage);
	            if (file != null) {
	                try {
	                	
	                	PdfUtil.generateReportSocial(this.currentCasoDesproteccionSocialInfo, file.getAbsolutePath());
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
				Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Debe seleccionar un caso para usar la opción de imprimir");
				messageDialogStage.show();
			}
		}
			

	}

	@Override
	public void changed(ObservableValue<? extends CasoDesproteccionSocialInfo> arg0, CasoDesproteccionSocialInfo arg1,
			CasoDesproteccionSocialInfo arg2) {
		this.currentCasoDesproteccionSocialInfo = arg2;
		this.loadInfo();
	}

	public CasoDesproteccionSocialInfo getCurrentCasoDesproteccionSocialInfo() {
		return currentCasoDesproteccionSocialInfo;
	}

	public void setCurrentCasoDesproteccionSocialInfo(CasoDesproteccionSocialInfo currentCasoDesproteccionSocialInfo) {
		this.currentCasoDesproteccionSocialInfo = currentCasoDesproteccionSocialInfo;
	}

	public ArrayList<CatalogInfo> getSituacionDesproteccionInfoList() {
		return situacionDesproteccionInfoList;
	}

	public void setSituacionDesproteccionInfoList(ArrayList<CatalogInfo> situacionDesproteccionInfoList) {
		this.situacionDesproteccionInfoList = situacionDesproteccionInfoList;
	}
	
}
