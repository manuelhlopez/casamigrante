/**
 * 
 */
package com.agt.client.gui;

import javafx.application.Platform;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class MessageModalDialog implements EventHandler<ActionEvent> {
	private Stage primaryStage;
	private Stage modalDialogStage;
	private Button okButton;
	
	public MessageModalDialog(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage buildModalDialogStage(MessageDialogType dialogType, String message) {
		// Based on the message type, get the style class for button and dialog border...
		String buttonStyleClass = "";
		String dialogBorderClass = "";
		if (dialogType == MessageDialogType.WARNING) {
			buttonStyleClass = "warning-button-ok";
			dialogBorderClass = "warning-dialog-border-pane";
		}
		else if (dialogType == MessageDialogType.INFO) {
			buttonStyleClass = "info-button-ok";
			dialogBorderClass = "info-dialog-border-pane";
		}
		else if (dialogType == MessageDialogType.ERROR) {
			buttonStyleClass = "error-button-ok";
			dialogBorderClass = "error-dialog-border-pane";
		}
		else if (dialogType == MessageDialogType.WAIT) {
			buttonStyleClass = "info-button-ok";
			dialogBorderClass = "info-dialog-border-pane";
		}
		
		
		// Build the images...
		Image okImage = new Image(MessageModalDialog.class.getResourceAsStream("/com/agt/client/images/ok.png"));
		ImageView okImageView = new ImageView(okImage);
		
		// Buttons...
		this.okButton = new Button();
		this.okButton.setGraphic(okImageView);
		this.okButton.getStyleClass().add(buttonStyleClass);
		this.okButton.setOnAction(this);
		this.okButton.setPrefHeight(30.0);
		this.okButton.setPrefWidth(30.0);
		this.okButton.setCursor(Cursor.HAND);
		

		// Build the top button bar...
		HBox topButtonBarHBox = new HBox();
		topButtonBarHBox.setAlignment(Pos.CENTER_RIGHT);
		topButtonBarHBox.setPadding(new Insets(0, 0, 0, 0));
		if (dialogType != MessageDialogType.WAIT) {
			topButtonBarHBox.getChildren().add(this.okButton);
		}
		
		// Build the body of the message...
		BorderPane bodyBorderPane = new BorderPane();
		bodyBorderPane.setPadding(new Insets(0, 30, 20, 30));
		Label messageLabel = new Label(message);
		messageLabel.setWrapText(true);
		bodyBorderPane.setCenter(messageLabel);
		
		// The BorderPane that is the container for the dialog...
		BorderPane modalDialogBorderPane = new BorderPane();
		modalDialogBorderPane.setTop(topButtonBarHBox);
		modalDialogBorderPane.setCenter(bodyBorderPane);
		modalDialogBorderPane.getStyleClass().add(dialogBorderClass);
		
		// Create the Scene...
		Scene modalDialogScene = new Scene(modalDialogBorderPane, 400, 150);
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
			this.primaryStage.getScene().getRoot().setEffect(null);
			this.modalDialogStage.close();
			this.primaryStage.isFocused();
		}
	}
}
