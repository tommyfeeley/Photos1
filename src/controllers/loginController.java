package controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Users;

/**
 * Controls the loginView stage for the user
 */
public class loginController {
	@FXML
	private Button enter;
	@FXML
	private TextField username;
	private Users user;
	/**
     * Initializes controller's private fields and sets up controller for stage
     */
	public void start() {
		
	}

    /**
     * Checking if user regular user, or admin logging in
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had an I/O exception happen
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void enter(ActionEvent event) throws IOException, ClassNotFoundException {
	    ArrayList<Users> users = deserialize.deserialize();
	    String name = username.getText();
	    user = null;
	    
	    if (users != null) {
	        for (Users currentUser : users) {
	            if (currentUser.getUserName().equals(name)) {
	                user = currentUser;
	            }
	        }
	    } else {
	        // Return error is the number of users is NULL
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Login Error");
	        alert.setHeaderText("User data not loaded.");
	        alert.setContentText("Please ensure user data is available." + name);

	        alert.showAndWait();
	        return; // Exit the method if we cannot find user data
	    }

	    System.out.println(name);

		if (name.equals("admin") || user != null) {
		
			if (name.equals("admin")) {
			
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/adminView.fxml"));
				Parent root = (Parent)loader.load();
				adminController controller = loader.getController();
				
				Stage secondaryStage = new Stage();
				controller.start(secondaryStage);
				
				Scene scene = new Scene(root);
				secondaryStage.setScene(scene);
				secondaryStage.setTitle("Photo App");
				secondaryStage.show();
				
				
			} else {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albumView.fxml"));
				Parent parent = (Parent) loader.load();
				albumController controller = loader.<albumController>getController();
				Scene scene = new Scene(parent);
		
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				controller.start(stage,user);
				stage.setScene(scene);
				stage.show();
			
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setHeaderText("User not found.");
			alert.setContentText("This user does not exist.");

			alert.showAndWait();
		}
	
	}
}
