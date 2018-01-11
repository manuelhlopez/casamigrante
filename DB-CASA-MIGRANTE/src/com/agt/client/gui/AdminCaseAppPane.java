/**
 * 
 */
package com.agt.client.gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import com.agt.client.info.CatalogInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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


/**
 * @author mlopez
 *
 */
public class AdminCaseAppPane implements EventHandler<ActionEvent> {
	private Stage primaryStage;
	private BorderPane borderPane;
	private JFXComboBox<CatalogInfo> estadosComboBox;
	private JFXTextField fechaTextField;
	private TextArea observacionesTextArea;
	private JFXButton saveButton;


	public AdminCaseAppPane(Stage primaryStage) {
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
		Icon icon = new Icon(AwesomeIcon.HAND_ALT_RIGHT,"2em",";","");
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setHeight(5);
		Label titleLabel = new Label("SEGUIMIENTO");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20.0));
		titleLabel.setStyle("-fx-text-fill: #ffffff;");
		titleLabel.setEffect(ds);
		titleHbox.getChildren().add(icon);
		titleHbox.getChildren().add(titleLabel);

		this.borderPane.setTop(titleHbox);

		this.borderPane.setCenter(this.buildAdminCasePanel());

		return borderPane;
	}

	public BorderPane buildAdminCasePanel(){
		BorderPane mainBorderPane = new BorderPane();
		BorderPane infoBorderPanel = new BorderPane();
		infoBorderPanel.setPrefWidth(300);
		infoBorderPanel.setStyle("-fx-background-color: #ffffff;");
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setHeight(5);
		infoBorderPanel.setEffect(ds);
		Label titleLabel = new Label("Información del caso");
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(5,5,5,5));
		vBox.getChildren().add(titleLabel);
		infoBorderPanel.setTop(vBox);
		mainBorderPane.setLeft(infoBorderPanel);

		VBox mainVBox = new VBox();
		mainVBox.setSpacing(10);
		mainVBox.setPadding(new Insets(10,10,10,10));

		VBox formVBox = new VBox();
		formVBox.setSpacing(10);
		formVBox.setPadding(new Insets(10,10,10,10));
		
		// Componente para el estado del caso...
		ObservableList<CatalogInfo> estadosOptions = 
				FXCollections.observableArrayList(
						DashboardGlobalInfo.getEstadoCasoInfoList());
		estadosComboBox = new  JFXComboBox<CatalogInfo>(estadosOptions);
		estadosComboBox.setPromptText("Estado del caso:");
		estadosComboBox.setPrefWidth(160);


		// Componente para la fecha del hecho...
		fechaTextField = new JFXTextField();
		fechaTextField.setPromptText("Fecha del hecho. Formato 01/01/2016 00:00");
		fechaTextField.setPrefWidth(100);

		// Componente para la narración del hecho...
		observacionesTextArea = new TextArea();
		observacionesTextArea.setPrefRowCount(10);
		observacionesTextArea.setPrefColumnCount(20);
		observacionesTextArea.setWrapText(true);
		observacionesTextArea.setPrefWidth(150);

		// Boton guardar...
		saveButton = new JFXButton("Guardar".toUpperCase());
		saveButton.getStyleClass().add("button-raised");
		saveButton.setCursor(Cursor.HAND);
		saveButton.setOnAction(this);
		
		formVBox.getChildren().addAll(estadosComboBox, fechaTextField, observacionesTextArea, saveButton);

		BorderPane formBorderPane = new BorderPane();
		formBorderPane.setTop(formVBox);
		
		mainBorderPane.setCenter(formBorderPane);
		
		return mainBorderPane;

	}
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
