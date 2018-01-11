/**
 * 
 */
package com.agt.client.gui;

import java.io.File;
import java.util.*;
import java.util.logging.*;

import com.agt.client.db.SettingsDbActions;
import com.agt.client.info.SettingsInfo;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.*;


/**
 * @author Manuel López
 *
 */
public class DashboardMain extends Application {
	private static final Logger log = Logger.getLogger(DashboardMain.class.getName());
	private Stage primaryStage;
	 
	
	public void buildAppScene() {
		try {
			SettingsDbActions settingsDbActions = new SettingsDbActions();
			SettingsInfo settingsInfo = settingsDbActions.getSettingsInfo();
			AppScreenController appScreenController = new AppScreenController(this, this.primaryStage, settingsInfo);
			double stageWidth = 950;
			double stageHeight  = 600;
			
			
			Scene appScene = appScreenController.buildScene(stageWidth, stageHeight);
			this.primaryStage.setScene(appScene);
			this.primaryStage.sizeToScene();
			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			      public void handle(WindowEvent we) {
			    	  Alert alert = new Alert(AlertType.CONFIRMATION);
			    	  alert.setTitle("DB-CASA-MIGRANTE");
			    	  alert.setHeaderText("Saliendo del sistema...");
			    	  alert.setContentText("¿Desea salir del sistema?");
			    	  Optional<ButtonType> result = alert.showAndWait();
			    	  if (result.get() == ButtonType.OK){
			    	      primaryStage.close();
			    	  } else {
			    	    we.consume(); 
			    	  }
			      }
			  }); 
		}
		catch (Exception e) {	
			log.severe("There was an error loading the App Screen");		
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Get the parameters...
			// Get some global settings...
			int appWidth = DashboardGlobalInfo.getAppWidth();
			int appHeight = DashboardGlobalInfo.getAppHeight();
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("DB");
			this.primaryStage.setMinWidth(appWidth);
			this.primaryStage.setMinHeight(appHeight);
			File file = new File("C:\\localdb");
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			this.buildAppScene();
			this.primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.severe("There was a problem loading the application");
		}
	}

	public static void main(String[] args) {
		// Start the JavaFX Application...
		Application.launch(DashboardMain.class, args);		
	}
}
