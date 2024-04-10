package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Photos class allows us to start our JavaFX photo management application
 * The class has a start method to setup our stages, and get UI elements from FXML
 * The main method has our launch method to launch the JavaFX application
 * @author Tommy Feeley, Rishabh Prajapati
 */

public class Photos extends Application {
	
	/**
	* Initializes main method and login view stage when application first opened
	* @param primaryStage The Stage that this controller controls
	*/  
	 	@Override
		public void start(Stage primaryStage) {
			try {
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
				Scene scene = new Scene(root,350,300);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param args The arguments input from the command line
		 * @throws IOException Tells us we had a I/O exception happened
		 */
		public static void main(String[] args) throws IOException {
				launch(args);					

		}						
}
	