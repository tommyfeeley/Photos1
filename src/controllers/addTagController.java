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
import model.*;


/**
 * Controls the addTag stage for the User
 */
public class addTagController {

	@FXML Button close;
	@FXML Button edit;
	@FXML TextField value;
	@FXML TextField type;
	
	Photos select;
	Users user;
	Stage stage;
    Album album;
    
    /**
	 * Initializes controller's private fields and sets up the controller for the stage
	 * @param stage The current stage which is the addTag stage for any user
     * @param user The current User adding a tag to a photo
     * @param album The Album that the user's photo is within
	 * @param select The specific Photos having tags added to them
	 */
	public void start(Stage stage, Users user, Album album, Photos select) {
        this.user = user;
		this.select = select;
		this.stage = stage;
		this.album = album;
	}
	
    /**
     * 
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void addTag(ActionEvent event)throws IOException, ClassNotFoundException{
        
        String Type = type.getText();
		String Value = value.getText();
		ArrayList<Users> allUsers = deserialize.deserialize();
		
		if(Type.equals("") || Value.equals("")) {
        
            error("Cannot add a blank tag!");
			return;
		}
        
        Tags tag = new Tags(Type, Value);
		select.getTags().add(tag);
		
		for(Users u: allUsers) {
            
            if(u.getUserName().equals(user.getUserName())) allUsers.set(allUsers.indexOf(u), user);
		}
		
		
		save.save(allUsers);
		Dialog("Successfully added Tag");
		
		// Want to return back to specificAlbumView after we successfully added the desired tag
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/specificAlbumView.fxml"));
		Parent parent = (Parent) loader.load();
		specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage,user,album);
		stage.setScene(scene);
		stage.show();
		
	}

	/**
     * Displays an error notification to the User
     * @param emessage The error message displayed
     */
	public void error(String emessage) {
    
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ALERT ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
    
    /**
     * Displays a success notification to the User
     * @param emessage the success message to be displayed
     */
	public void Dialog(String emessage) {
    
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("tag Added");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
    
    /**
     * Closes current window and returns to specificAlbumView
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void close(ActionEvent event) throws IOException, ClassNotFoundException {
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/specificAlbumView.fxml"));
		Parent parent = (Parent) loader.load();
		specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		controller.start(stage,user,album);
		stage.setScene(scene);
		stage.show();
	}

}
