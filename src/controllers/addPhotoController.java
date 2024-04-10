package controllers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

/** 
 * Controls the addPhotoView stage for the User
 */
public class addPhotoController {

    @FXML TextField cap;
	@FXML TextField path;
	@FXML Button close;
	@FXML Button add;
	@FXML Button getUrl;
    private Users user;
    private Album album;
    private File file;

    /**
	 * Initializes controller's private fields and sets up controller for the stage
	 * @param user The current User adding a photo to an album
	 * @param album The current User's album the photo is being added to
	 */

    public void start(Users user, Album album) {

    this.user=user;
	this.album = album;
    }
	
    /**
     * Adds the photo to the album the user selected
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     */

    public void add (ActionEvent event) throws IOException {    

        ArrayList<Users> saved = deserialize.deserialize();
        
        if (file == null) return;
        
        if (file.exists()) {
            
            String caption = cap.getText();
            album.addPhoto(new Photos(file, caption));}
            
        else album.addPhoto(new Photos(file));
        
        ArrayList<Album> temp = user.getAlbums();
        // Finds the currentUser in storedUsers and adds album to the currUser
        
        for(Album t: temp) {
        
            if(t.getName().equals(album.getName())) {
        
                temp.set(temp.indexOf(t),album);
                user.setAlbums(temp);
            }
        }
        
        for (Users u : saved) {
        
            if (u.equals(user)) saved.set(saved.indexOf(u), user);
        
        }
        
        save.save(saved);
        
        try {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/specificAlbumView.fxml"));
            Parent parent = (Parent) loader.load();
            specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(stage,user, album);
            stage.setScene(scene);
            stage.show();
        
        } 
        
        catch (IOException exception) {
            System.out.println(exception);
        }
    }
            
    /**
     * Asks user to choose file, finds its path
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     */
    public void getUrl (ActionEvent event) throws IOException {
        FileChooser browser = new FileChooser();
        file = browser.showOpenDialog(null);
        System.out.println(file);
        if (file != null) {
            path.setText(file.getAbsolutePath());
        }
    }

    /**
     * Closes addPhoto window opens specificAlbumView
     * @param event The ActionEvent we have after the button is interacted with
     */
    public void close(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/specificAlbumView.fxml"));
            Parent parent = (Parent) loader.load();
            specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(stage,user, album);
            stage.setScene(scene);
            stage.show();
        } 
        
        catch (IOException exception) {
        
            System.out.println(exception);
        }
    }

}


