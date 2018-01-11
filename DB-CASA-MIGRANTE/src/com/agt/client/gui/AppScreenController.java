/**
 * 
 */
package com.agt.client.gui;

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.input.MouseEvent;

import java.awt.JobAttributes.DialogType;
import java.util.concurrent.ExecutionException;

import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.SettingsInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;


/**
 * @author Manuel López
 *
 */
public class AppScreenController implements EventHandler<ActionEvent> {


	private DashboardMain dashboardMain;
	private Stage primaryStage;
	private ImageView newCaseImageView;
	private ImageView newCaseHoverImageView;
	private ImageView newCaseMoseOverImageView;
	private ImageView newCaseSocialImageView;
	private ImageView newCaseSocialHoverImageView;
	private ImageView newCaseSocialMoseOverImageView;
	private ImageView adminCaseImageView;
	private ImageView adminCaseHoverImageView;
	private ImageView adminCaseMoseOverImageView;
	private ImageView reportsImageView;
	private ImageView reportsHoverImageView;
	private ImageView reportsMoseOverImageView;
	private ImageView settingsImageView;
	private ImageView settingsHoverImageView;
	private ImageView settingsMoseOverImageView;
	private ImageView toolsImageView;
	private ImageView toolsHoverImageView;
	private ImageView toolsMoseOverImageView;

	private static Button newCaseButton;
	private static Button newCaseSocialButton;
	private static Button adminCaseButton;
	private static Button reportsButton;
	private static Button settingsButton;
	private static Button toolsButton;
	private  JFXButton loginButton;

	public static BorderPane appBodyBorderPane;
	private BorderPane mainBorderPane;
	private SettingsInfo settingsInfo;
	private HBox headerHBox;
	private VBox leftVBox;
	private JFXPasswordField codigoTextField;

	private String selectedPanel = "";


	public AppScreenController(DashboardMain dashboardMain, Stage primaryStage, SettingsInfo settingsInfo) {
		this.dashboardMain = dashboardMain;
		this.primaryStage = primaryStage;
		this.settingsInfo = settingsInfo;
	}

	public Scene buildScene(double stageWidth, double stageHeight) {

		// Button images...
		Image newCaseImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/new_case.png"));
		this.newCaseImageView = new ImageView(newCaseImage);
		Image newCaseSocialImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/new_case_social.png"));
		this.newCaseSocialImageView = new ImageView(newCaseSocialImage);
		Image adminCaseImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/admin_case.png"));
		this.adminCaseImageView = new ImageView(adminCaseImage);
		Image reportsImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/reports.png"));
		this.reportsImageView = new ImageView(reportsImage);
		Image settingsImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/settings.png"));
		this.settingsImageView = new ImageView(settingsImage);
		Image toolsImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/tools.png"));
		this.toolsImageView = new ImageView(toolsImage);

		// Hover Button images...
		Image newCaseHoverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/new_case_hover.png"));
		this.newCaseHoverImageView = new ImageView(newCaseHoverImage);
		Image newCaseSocialHoverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/new_case_hover_social.png"));
		this.newCaseSocialHoverImageView = new ImageView(newCaseSocialHoverImage);
		Image adminCaseHoverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/admin_case_hover.png"));
		this.adminCaseHoverImageView = new ImageView(adminCaseHoverImage);
		Image reportsHoverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/reports_hover.png"));
		this.reportsHoverImageView = new ImageView(reportsHoverImage);
		Image settingsHoverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/settings_hover.png"));
		this.settingsHoverImageView = new ImageView(settingsHoverImage);
		Image toolsHoverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/tools_hover.png"));
		this.toolsHoverImageView = new ImageView(toolsHoverImage);

		// On mose over images...
		Image newCaseMoseOverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/new_case_mose_over.png"));
		this.newCaseMoseOverImageView = new ImageView(newCaseMoseOverImage);
		Image newCaseSocialMoseOverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/new_case_mose_over_social.png"));
		this.newCaseSocialMoseOverImageView = new ImageView(newCaseSocialMoseOverImage);
		Image adminCaseMoseOverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/admin_case_mose_over.png"));
		this.adminCaseMoseOverImageView = new ImageView(adminCaseMoseOverImage);
		Image reportsMoseOverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/reports_mose_over.png"));
		this.reportsMoseOverImageView = new ImageView(reportsMoseOverImage);
		Image settingsMoseOverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/settings_mose_over.png"));
		this.settingsMoseOverImageView = new ImageView(settingsMoseOverImage);
		Image toolsMoseOverImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/tools_mose_over.png"));
		this.toolsMoseOverImageView = new ImageView(toolsMoseOverImage);

		// The logo image...
		Image logoImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/logo5.png"));
		ImageView logoImageView = new ImageView(logoImage);
		logoImageView.setFitHeight(125);
		logoImageView.setFitWidth(150);
		
		Image logoLoginImage = new Image(AppScreenController.class.getResourceAsStream("/com/agt/client/images/logo4.png"));
		ImageView logoLoginImageView = new ImageView(logoLoginImage);
		logoLoginImageView.setFitHeight(200);
		logoLoginImageView.setFitWidth(250);
		
		// Spacer panes...
		Pane spacerPane = new Pane();
		spacerPane.setPrefHeight(15.0);
		Pane spacerPane1 = new Pane();
		spacerPane1.setPrefHeight(25.0);

		// Buttons...
		newCaseButton = new Button();
		newCaseButton.setGraphic(this.newCaseImageView);
		newCaseButton.getStyleClass().add("button-app");
		newCaseButton.setCursor(Cursor.HAND);
		newCaseButton.setOnAction(this);
		newCaseButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("newCase")){
							newCaseButton.setGraphic(newCaseMoseOverImageView);
						}
					}
				});

			}
		});

		newCaseButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("newCase")){
							newCaseButton.setGraphic(newCaseImageView);
						}
					}
				});
			}
		});
		
		
		newCaseSocialButton = new Button();
		newCaseSocialButton.setGraphic(this.newCaseSocialImageView);
		newCaseSocialButton.getStyleClass().add("button-app");
		newCaseSocialButton.setCursor(Cursor.HAND);
		newCaseSocialButton.setOnAction(this);
		newCaseSocialButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("newCaseSocial")){
							newCaseSocialButton.setGraphic(newCaseSocialMoseOverImageView);
						}
					}
				});

			}
		});

		newCaseSocialButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("newCaseSocial")){
							newCaseSocialButton.setGraphic(newCaseSocialImageView);
						}
					}
				});
			}
		});


		adminCaseButton = new Button();
		adminCaseButton.setGraphic(this.adminCaseImageView);
		adminCaseButton.getStyleClass().add("button-app");
		adminCaseButton.setCursor(Cursor.HAND);
		adminCaseButton.setOnAction(this);
		adminCaseButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("adminCase")){
							adminCaseButton.setGraphic(adminCaseMoseOverImageView);
						}
					}
				});

			}
		});

		adminCaseButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("adminCase")){
							adminCaseButton.setGraphic(adminCaseImageView);
						}
					}
				});
			}
		});


		reportsButton = new Button();
		reportsButton.setGraphic(this.reportsImageView);
		reportsButton.getStyleClass().add("button-app");
		reportsButton.setCursor(Cursor.HAND);
		reportsButton.setOnAction(this);
		reportsButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("reports")){
							reportsButton.setGraphic(reportsMoseOverImageView);
						}
					}
				});

			}
		});

		reportsButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("reports")){
							reportsButton.setGraphic(reportsImageView);
						}
					}
				});
			}
		});

		settingsButton = new Button();
		settingsButton.setGraphic(this.settingsImageView);
		settingsButton.getStyleClass().add("button-app");
		settingsButton.setCursor(Cursor.HAND);
		settingsButton.setOnAction(this);
		settingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("settings")){
							settingsButton.setGraphic(settingsMoseOverImageView);
						}
					}
				});

			}
		});

		settingsButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("settings")){
							settingsButton.setGraphic(settingsImageView);
						}
					}
				});
			}
		});

		toolsButton = new Button();
		toolsButton.setGraphic(this.toolsImageView);
		toolsButton.getStyleClass().add("button-app");
		toolsButton.setCursor(Cursor.HAND);
		toolsButton.setOnAction(this);
		toolsButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("tools")){
							toolsButton.setGraphic(toolsMoseOverImageView);
						}
					}
				});

			}
		});

		toolsButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(!selectedPanel.equals("tools")){
							toolsButton.setGraphic(toolsImageView);
						}
					}
				});
			}
		});

		// Create the left bar...
		leftVBox = new VBox();
		leftVBox.setAlignment(Pos.TOP_CENTER);
		leftVBox.getStyleClass().add("app-left-pane");
		leftVBox.setPrefWidth(177.0);
		leftVBox.setSpacing(0);
		leftVBox.getChildren().add(spacerPane);
		leftVBox.getChildren().add(logoImageView);
		leftVBox.getChildren().add(spacerPane1);
		leftVBox.getChildren().add(newCaseButton);
		//leftVBox.getChildren().add(newCaseSocialButton);
		//leftVBox.getChildren().add(adminCaseButton);
		//leftVBox.getChildren().add(reportsButton);
		//leftVBox.getChildren().add(settingsButton);
		//leftVBox.getChildren().add(toolsButton);

		// The header controls...
		Label titleLabel = new Label("BASE DE DATOS CASA DEL MIGRANTE");
		titleLabel.setFont(Font.font("Arial", 15));
		titleLabel.setStyle("-fx-text-fill: #848484");

		Pane fillerPane = new Pane();
		fillerPane.setPrefWidth(20.0);
		fillerPane.setPrefHeight(55.0);

		// Create the header bar...
		headerHBox = new HBox();
		headerHBox.getStyleClass().add("app-header-bar");
		headerHBox.setAlignment(Pos.CENTER_RIGHT);
		headerHBox.setPrefHeight(55.0);
		headerHBox.setSpacing(10.0);
		headerHBox.getChildren().add(titleLabel);
		headerHBox.getChildren().add(fillerPane);
		
		loginButton = new JFXButton();
		loginButton.setCursor(Cursor.HAND);
		loginButton.getStyleClass().add("button-raised");
		loginButton.setText("Aceptar");
		loginButton.setOnAction(this);

		// Create the right app container...
		appBodyBorderPane = new BorderPane();
		appBodyBorderPane.getStyleClass().add("base-background");
		appBodyBorderPane.setTop(headerHBox);
		mainBorderPane = new BorderPane();
		mainBorderPane.getStyleClass().add("base-background");
		
		/*
		if(this.settingsInfo == null){
			// The app scene border pane...
			
			mainBorderPane.setLeft(leftVBox);
			mainBorderPane.setCenter(appBodyBorderPane);

		}else{
				// Componente para ingresar el codigo municipal...
				codigoTextField = new JFXPasswordField();
				codigoTextField.setPromptText("Ingrese el código de acceso");
				codigoTextField.setPrefWidth(100);
				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
				
				VBox vBox = new VBox();
				vBox.setSpacing(10);
				vBox.setAlignment(Pos.CENTER);
				vBox.getChildren().addAll(logoLoginImageView, new Label("Usuario"), new JFXTextField(), new Label("Contraseña"), codigoTextField, loginButton);
				hbox.getChildren().add(vBox);
				mainBorderPane.setCenter(hbox);
		}
		*/
		// Componente para ingresar el codigo municipal...
		codigoTextField = new JFXPasswordField();
		codigoTextField.setPromptText("Ingrese el código de acceso");
		codigoTextField.setPrefWidth(100);
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(logoLoginImageView, new Label("Usuario"), new JFXTextField(), new Label("Contraseña"), codigoTextField, loginButton);
		hbox.getChildren().add(vBox);
		mainBorderPane.setCenter(hbox);
		
		// Finally create the scene...
		Scene appScene = new Scene(mainBorderPane, stageWidth, stageHeight);
		appScene.getStylesheets().add("/com/agt/client/css/dashboard-app.css");
		return appScene;
	}

	
	
	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();
		if (eventSource == newCaseButton) {
			this.selectedPanel = "newCase";
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Select the user button...
					newCaseButton.setGraphic(newCaseHoverImageView);
					// Deselect all the other buttons...
					adminCaseButton.setGraphic(adminCaseImageView);
					reportsButton.setGraphic(reportsImageView);
					settingsButton.setGraphic(settingsImageView);
					toolsButton.setGraphic(toolsImageView);
					newCaseSocialButton.setGraphic(newCaseSocialImageView);
				}
			}); 
			// Present the new case panel...
			PersonasAppPane newCaseAppPane = new PersonasAppPane(primaryStage);
			BorderPane borderPane = newCaseAppPane.buildPane();
			appBodyBorderPane.setCenter(borderPane);
		}	
		if (eventSource == newCaseSocialButton) {
			this.selectedPanel = "newCaseSocial";
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Select the user button...
					newCaseSocialButton.setGraphic(newCaseSocialHoverImageView);
					// Deselect all the other buttons...
					newCaseButton.setGraphic(newCaseImageView);	
					adminCaseButton.setGraphic(adminCaseImageView);
					reportsButton.setGraphic(reportsImageView);
					settingsButton.setGraphic(settingsImageView);
					toolsButton.setGraphic(toolsImageView);
				}
			}); 
			// Present the new case panel...
			CasosDesproteccionSocialAppPane newCaseAppPane = new CasosDesproteccionSocialAppPane(primaryStage);
			BorderPane borderPane = newCaseAppPane.buildPane();
			appBodyBorderPane.setCenter(borderPane);
		}	
		if (eventSource == adminCaseButton) {
			this.selectedPanel = "adminCase";
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Select the user button...
					adminCaseButton.setGraphic(adminCaseHoverImageView);
					// Deselect all the other buttons...
					newCaseButton.setGraphic(newCaseImageView);					
					reportsButton.setGraphic(reportsImageView);
					settingsButton.setGraphic(settingsImageView);
					toolsButton.setGraphic(toolsImageView);
					newCaseSocialButton.setGraphic(newCaseSocialImageView);
				}
			}); 
			// Present the admin case panel...
			CasosPrevencionAppPane adminCaseAppPane = new CasosPrevencionAppPane(this.primaryStage);
			BorderPane borderPane = adminCaseAppPane.buildPane();
			appBodyBorderPane.setCenter(borderPane);
		}
		if (eventSource == reportsButton) {
			this.selectedPanel = "reports";
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Select the user button...
					reportsButton.setGraphic(reportsHoverImageView);
					// Deselect all the other buttons...
					adminCaseButton.setGraphic(adminCaseImageView);
					newCaseButton.setGraphic(newCaseImageView);										
					settingsButton.setGraphic(settingsImageView);
					toolsButton.setGraphic(toolsImageView);
					newCaseSocialButton.setGraphic(newCaseSocialImageView);
				}
			}); 
			ReportsAppPane reportsAppPane = new ReportsAppPane(this.primaryStage);
			BorderPane borderPane = reportsAppPane.buildPane();
			appBodyBorderPane.setCenter(borderPane);
		}		
		if (eventSource == settingsButton) {
			this.selectedPanel = "settings";
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Select the user button...
					settingsButton.setGraphic(settingsHoverImageView);				
					// Deselect all the other buttons...
					adminCaseButton.setGraphic(adminCaseImageView);
					newCaseButton.setGraphic(newCaseImageView);	
					reportsButton.setGraphic(reportsImageView);	
					toolsButton.setGraphic(toolsImageView);
					newCaseSocialButton.setGraphic(newCaseSocialImageView);
				}
			}); 
			SettingsAppPane settingsAppPane = new SettingsAppPane(this.primaryStage);
			BorderPane borderPane = settingsAppPane.buildPane();
			appBodyBorderPane.setCenter(borderPane);
		}	
		if (eventSource == toolsButton) {
			this.selectedPanel = "tools";
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// Select the user button...
					toolsButton.setGraphic(toolsHoverImageView);				
					// Deselect all the other buttons...
					adminCaseButton.setGraphic(adminCaseImageView);
					newCaseButton.setGraphic(newCaseImageView);	
					reportsButton.setGraphic(reportsImageView);	
					settingsButton.setGraphic(settingsImageView);
					newCaseSocialButton.setGraphic(newCaseSocialImageView);
				}
			}); 
			ToolsAppPane toolsAppPane = new ToolsAppPane(this.primaryStage);
			BorderPane borderPane = toolsAppPane.buildPane();
			appBodyBorderPane.setCenter(borderPane);
		}
		
		if (eventSource == loginButton) {
			//SettingsDbActions settingsDbActions = new SettingsDbActions();
			//SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			//String codigo = settingsInfo.getCode();
			String codigoIngresado = this.codigoTextField.getText();
				if(codigoIngresado.equals("a")){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							mainBorderPane.setLeft(leftVBox);
							mainBorderPane.setCenter(appBodyBorderPane);

						}
					}); 
				}else{
					MessageModalDialog messageModalDialog = new MessageModalDialog(this.primaryStage);
					Stage messageDialogStage = messageModalDialog.buildModalDialogStage(MessageDialogType.ERROR, "Código ingresado incorrecto");
					messageDialogStage.show();
				}
			
		}	
	}
}
