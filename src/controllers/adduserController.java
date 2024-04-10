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
 * Controls the addUser stage accessed by the admin (administrator)
 */
public class adduserController {
    
    @FXML private Button addUserButton;
	@FXML private Button close;
	@FXML private TextField userNameTextField;
    boolean check = false;
	FXMLLoader loader;
	Parent parent;
	Stage stage;
    
    /**
	 * Initializes controller's private fields and sets up controller for stage
	 * @param stage The Stage that this controller controls
	 */
    public void start (Stage stage) {
    
        this.stage=stage;
		
	}
	
    /**
     * Closes add user window and returns to adminView stage
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void close (ActionEvent event) throws IOException, ClassNotFoundException {
	
		loader = new FXMLLoader(getClass().getResource("/view/adminView.fxml"));

		parent = (Parent) loader.load();
	
		adminController controller = loader.<adminController>getController();
		
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage);
		stage.setScene(scene);
		stage.show();
		
	}
	/**
	 * Adds created User objects for users in ArrayList
	 * @param event The ActionEvent we have after the button is interacted with
	 * @throws IOException Tells us we had a I/O exception happened
	 * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
	 */
	public void addUser (ActionEvent event) throws IOException, ClassNotFoundException {
		
		Users newUser = new Users(userNameTextField.getText());
		
		if (!newUser.getUserName().isEmpty()) {

			
				ArrayList<Users> storedUsers = deserialize.deserialize();
			
				for (Users currentUser : storedUsers) {
					if (currentUser.getUserName().equals(newUser.getUserName())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Admin");
						alert.setHeaderText("User error");
						alert.setContentText("user exists");

						alert.showAndWait();
						return;
					}
					
				}

				storedUsers.add(newUser);
				
				save.save(storedUsers);
			
				loader = new FXMLLoader(getClass().getResource("/view/adminView.fxml"));

				parent = (Parent) loader.load();
		
			adminController controller = loader.<adminController>getController();
			
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
}
