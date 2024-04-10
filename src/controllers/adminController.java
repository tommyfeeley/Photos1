package controllers;


import java.io.IOException;

import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Users;



/** 
 * Controls the adminView stage for the User
 */
public class adminController {

	@FXML ListView<Users> ListViewer;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button logout;
	@FXML Button quit;
	private ObservableList<Users> users;
	Stage stage;
    
    /**
	 * Initializes controller's private fields and sets up controller for stage
	 * @param stage The Stage that this controller controls (adminView)
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     * @throws IOException Tells us we had a I/O exception happened
	 */
    public void start(Stage stage) throws ClassNotFoundException, IOException {
		this.stage=stage;
		displayUsers();
	}
	
	/**
	 * Opens the addUserView window for the admin
	 * @param event The ActionEvent we have after the button is interacted with
	 * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
	 */
	public void addUser (ActionEvent event) throws IOException, ClassNotFoundException {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adduserView.fxml"));
				Parent parent = (Parent) loader.load();
				adduserController controller = loader.<adduserController>getController();
				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				controller.start(stage);
				stage.setScene(scene);
				stage.show();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
			displayUsers();

		}
	
	
	/**
	 * Allows admin to delete selected user
	 * @param event The ActionEvent we have after the button is interacted with
	 * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     * @throws IOException Tells us we had a I/O exception happened
	 */
	public void deleteUser (ActionEvent event) throws ClassNotFoundException, IOException{
		// Get the selected user's name and check if anything was selected
		Users selectedUser = ListViewer.getSelectionModel().getSelectedItem();
		if (selectedUser == null) {
            
            System.out.println("nothing was selected.");
			return;
		}
        ArrayList<Users> storedUsers = deserialize.deserialize();

        for (Users user : storedUsers) {
        
            if (user.getUserName().equals(selectedUser.getUserName())){
        
                storedUsers.remove(user);
                break;
            }
        }
        

        save.save(storedUsers);

		
		// Display updated list of users after removing the selected one
		displayUsers();
	}
    
    /**
	 * Closes current window for admin
     * @param event The ActionEvent we have after the button is interacted with
     */
	public void closeWindow(ActionEvent event) {
        
        stage.close();
		
    }
    
	/**
	 * Returns the admin to the loginView scene
	 * @param event The ActionEvent we have after the button is interacted with
	 */
	public void logOut(ActionEvent event) {

		try {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginView.fxml"));
			Parent parent = (Parent) loader.load();
			loginController controller = loader.<loginController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start();
			stage.setScene(scene);
			stage.show();
        
        } catch (Exception exception) {
        
            exception.printStackTrace();
		}
	}
		
	/**
     * Helper method that displays all users to current user
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     * @throws IOException Tells us we had a I/O exception happened
     */
	public void displayUsers () throws ClassNotFoundException, IOException {
		// Create a new observable ArrayList to put users in
        users = FXCollections.observableArrayList();
        
		ArrayList<Users> savedUsers = deserialize.deserialize();
        // Users are stored in savedUser and we put these users in observable ArrayList
        for (Users user : savedUsers) users.add(user);
    
		// Display the observable ArrayList with the list of users
    
        ListViewer.setItems(users);
		ListViewer.setCellFactory(new Callback<ListView<Users>, ListCell<Users>>(){  
            @Override
            public ListCell<Users> call(ListView<Users> p) {                 
                ListCell<Users> cell = new ListCell<Users>(){ 
                    @Override
                    protected void updateItem(Users user, boolean bln) {
                        super.updateItem(user, bln);
                        if (user != null) setText(user.getUserName());
                        else setText(null);
                    }
 
                };
                 
                return cell;
            }
        });
	}
}
