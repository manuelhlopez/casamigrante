/**
 * 
 */
package com.agt.client.gui;

import com.jfoenix.controls.JFXSpinner;

import javafx.application.Platform;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class WaitModalDialog implements EventHandler<ActionEvent> {
	private Stage primaryStage;
	private Stage modalDialogStage;
		
	public WaitModalDialog(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage buildModalDialogStage(MessageDialogType dialogType) {
		// Based on the message type, get the style class for button and dialog border...
		String dialogBorderClass = "info-dialog-border-pane";
		String message = "";
		
		if(dialogType == MessageDialogType.LOAD){
			message = "Cargando...";
		}
		if(dialogType == MessageDialogType.SAVE){
			message = "Guardando...";
		}
		if(dialogType == MessageDialogType.UPDATE){
			message = "Actualizando";
		}
		if(dialogType == MessageDialogType.DELETE){
			message = "Borrando...";
		}
		// Build the top button bar...
		HBox topButtonBarHBox = new HBox();
		topButtonBarHBox.setAlignment(Pos.CENTER_RIGHT);
		topButtonBarHBox.setPadding(new Insets(0, 0, 0, 0));
		
		// Build the body of the message...
		BorderPane bodyBorderPane = new BorderPane();
		bodyBorderPane.setPadding(new Insets(0, 30, 20, 30));
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		Label messageLabel = new Label(message);
		JFXSpinner spinner = new JFXSpinner();
		messageLabel.setWrapText(true);
		hbox.getChildren().addAll(messageLabel, spinner);
		bodyBorderPane.setCenter(hbox);
		
		// The BorderPane that is the container for the dialog...
		BorderPane modalDialogBorderPane = new BorderPane();
		modalDialogBorderPane.setTop(topButtonBarHBox);
		modalDialogBorderPane.setCenter(bodyBorderPane);
		modalDialogBorderPane.getStyleClass().add(dialogBorderClass);
		
		// Create the Scene...
		Scene modalDialogScene = new Scene(modalDialogBorderPane, 200, 100);
		modalDialogScene.getStylesheets().add("/com/agt/client/css/messages-dialog.css");
		
		// Create the result Stage...
		this.modalDialogStage = new Stage(StageStyle.TRANSPARENT);
		this.modalDialogStage.initModality(Modality.WINDOW_MODAL);
		
		this.modalDialogStage.initOwner(this.primaryStage);
		this.modalDialogStage.setScene(modalDialogScene);
		this.modalDialogStage.sizeToScene();
		
		// Return the created dialog stage...
		return this.modalDialogStage;
	}

	@Override
	public void handle(ActionEvent event) {
		Object eventSource = event.getSource();
		
	}
}
