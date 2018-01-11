/**
 * 
 */
package com.agt.client.gui;

import java.util.ArrayList;

import com.agt.client.info.CatalogInfo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.CheckBox;

public class ParticipantesModalDialog implements EventHandler<ActionEvent> {
	private Stage primaryStage;
	private Stage modalDialogStage;
	private Button okButton;
	private Button cancelButton;
	private CasosPrevencionAppPane appPane;
	private ArrayList<CheckBox> checkBoxList;
	private ListView<String> list;
	public ParticipantesModalDialog(Stage primaryStage, CasosPrevencionAppPane appPane) {
		this.primaryStage = primaryStage;
		this.appPane = appPane;
	}

	public Stage buildModalDialogStage() {
		// Build the images...
		Image okImage = new Image(ParticipantesModalDialog.class.getResourceAsStream("/com/agt/client/images/ok.png"));
		ImageView okImageView = new ImageView(okImage);
		
		Image closeImage = new Image(ParticipantesModalDialog.class.getResourceAsStream("/com/agt/client/images/close.png"));
		ImageView closeImageView = new ImageView(closeImage);
		
		// Buttons...
		this.okButton = new Button();
		this.okButton.setGraphic(okImageView);
		this.okButton.getStyleClass().add("info-button-ok");
		this.okButton.setOnAction(this);
		this.okButton.setPrefHeight(30.0);
		this.okButton.setPrefWidth(30.0);
		this.okButton.setCursor(Cursor.HAND);
		
		this.cancelButton = new Button();
		this.cancelButton.setGraphic(closeImageView);
		this.cancelButton.getStyleClass().add("error-button-ok");
		this.cancelButton.setOnAction(this);
		this.cancelButton.setPrefHeight(30.0);
		this.cancelButton.setPrefWidth(30.0);
		this.cancelButton.setCursor(Cursor.HAND);
		

		// Build the top button bar...
		HBox downButtonBarHBox = new HBox();
		downButtonBarHBox.setAlignment(Pos.CENTER_RIGHT);
		downButtonBarHBox.setPadding(new Insets(0, 0, 0, 0));
		downButtonBarHBox.getChildren().add(this.okButton);
		
		HBox topButtonBarHBox = new HBox();
		topButtonBarHBox.setAlignment(Pos.CENTER_RIGHT);
		topButtonBarHBox.setPadding(new Insets(0, 0, 0, 0));
		topButtonBarHBox.getChildren().add(this.cancelButton);
		
		
		
		// Build the body of the message...
		BorderPane bodyBorderPane = new BorderPane();
		bodyBorderPane.setPadding(new Insets(0, 30, 20, 30));
		VBox vboxLeft = new VBox();
		vboxLeft.setSpacing(5);
		vboxLeft.setPadding(new Insets(5,5,5,5));
		
		ArrayList<String> tipoActividadList = DashboardGlobalInfo.getParticipantesList();
		
		list = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList (
				tipoActividadList);
		list.setItems(items);
		list.setPrefWidth(500);
		vboxLeft.getChildren().add(list);
		HBox situacionesHbox = new HBox();
		situacionesHbox.setSpacing(10);
		situacionesHbox.getChildren().addAll(vboxLeft);
		bodyBorderPane.setCenter(situacionesHbox);
		
		// The BorderPane that is the container for the dialog...
		BorderPane modalDialogBorderPane = new BorderPane();
		modalDialogBorderPane.setTop(topButtonBarHBox);		
		modalDialogBorderPane.setBottom(downButtonBarHBox);
		modalDialogBorderPane.setCenter(bodyBorderPane);
		modalDialogBorderPane.getStyleClass().add("info-dialog-border-pane");
		
		// Create the Scene...
		Scene modalDialogScene = new Scene(modalDialogBorderPane, 600, 400);
		modalDialogScene.getStylesheets().add("/com/agt/client/css/messages-dialog.css");
		
		// Create the result Stage...
		this.modalDialogStage = new Stage(StageStyle.TRANSPARENT);
		this.modalDialogStage.initModality(Modality.WINDOW_MODAL);
		this.modalDialogStage.initOwner(this.primaryStage);
		this.modalDialogStage.setScene(modalDialogScene);
		this.modalDialogStage.sizeToScene();
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				okButton.requestFocus();
			}
		});
		
		// Return the created dialog stage...
		return this.modalDialogStage;
	}
	
	

	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();
		if (eventSource == this.okButton) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					String selected = list.getSelectionModel().getSelectedItem();
					if(selected.toLowerCase().startsWith("otro")){
						appPane.getInstitucionesTextField().setEditable(true);
						appPane.getInstitucionesTextField().setText("");
					}else{
						appPane.getInstitucionesTextField().setEditable(false);
						appPane.getInstitucionesTextField().setText(selected);
					}
					
				}
			});
			this.primaryStage.getScene().getRoot().setEffect(null);
			this.modalDialogStage.close();
			this.primaryStage.isFocused();
		}
		if (eventSource == this.cancelButton) {
			this.primaryStage.getScene().getRoot().setEffect(null);
			this.modalDialogStage.close();
			this.primaryStage.isFocused();
		}
	}
}
