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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;


/**
 * The addAlbumController class allows user to add and store album to their user/profile
 */
public class addAlbumController {
	
	@FXML TextField albumName;
	private Users user;
	
	/**
	 * ArrayList of Users to store user information
	 */
	public ArrayList<Users> users;
    
    /**
     * Initializes controller's private fields and sets up controller for stage
     * @param user The current user adding an album
     */
	public void start(Users user) {
		this.user=user;
	}
    
    /**
     * Adds the album to the user who created the album
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void add(ActionEvent event)throws IOException, ClassNotFoundException {

        String name = albumName.getText();
		System.out.print(name);
        if(!name.isEmpty()) {

            if(user.getAlbums()!=null) {

                for (Album album : user.getAlbums()) {

                    if (album.getName().equals(name)) {

                        error("Albums cannot have the same name.");
                        return;
                    }
				}
		} 
            
		try {
		
		Album sample = new Album(name);
		user.addAlbums(sample);
		Success("Success");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albumView.fxml"));
        Parent parent = (Parent) loader.load();
        albumController controller = loader.<albumController>getController();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.start(stage,user);
        stage.setScene(scene);
        stage.show();
        saves(user);
        return;
    }
		catch(java.lang.NullPointerException exception){
        }
      
		//Avoids null pointer and makes sure an ArrayList of albums exists 
			
			Album sample = new Album(name);
			ArrayList<Album> t = new ArrayList<>();
			t.add(sample);
			user.setAlbums(t);
			Success("Success");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albumView.fxml"));
			Parent parent = (Parent) loader.load();
			albumController controller = loader.<albumController>getController();
			Scene scene = new Scene(parent);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,user);
			stage.setScene(scene);
			stage.show();
		    saves(user);
		    return;
				
        }
	}
			  
    /**
     * Saves the album to user who created it
     * @param user The current user adding the new album
     */
	public void saves(Users user){
	ArrayList<Users> savedUsers = deserialize.deserialize();
	// Finds the currentUser in storedUsers and add album to the currentUser
	for (Users u : savedUsers) {
		if (u.equals(user)) {
			savedUsers.set(savedUsers.indexOf(u), user);
		}
	}
	save.save(savedUsers);
			
}
      
    /**
     * Display success notification to the User
     * @param emessage The success notification displayed
     */
	public void Success(String emessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Album Changed");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }

    /**
     * Display an error notification when error occurs
     * @param emessage The error notification that is displayed
     */
	public void error(String emessage) {
    
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ALERT ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(emessage);
        alert.showAndWait();
	}
    /**
     * Open the albumView stage by closing the addAlbum stage
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void close(ActionEvent event) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albumView.fxml"));
		Parent parent = (Parent) loader.load();
		albumController controller = loader.<albumController>getController();
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage,user);
		stage.setScene(scene);
		stage.show();
	}
}
