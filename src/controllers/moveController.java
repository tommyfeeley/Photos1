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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

/**
 * Controls the moveView stage for the user
 */
public class moveController {


    private ObservableList<Album> obsList;
	@FXML TextField caption;
	@FXML ListView<Album> albumsList;
	@FXML Button close;
	@FXML Button copy;
	private Stage stage;
	private Users user;
	private Photos photo;
	private Album album;

    /**
     * Initializes controller's private fields and sets up controller for stage
     * @param stage The Stage being controlled (moveView)
     * @param user The User accessing this stage currently
     * @param album The album we are moving our photo to
     * @param photo The photo we are moving
     */
    public void start(Stage stage, Users user, Album album, Photos photo) {
        this.stage=stage;
        this.user=user;
        this.photo=photo;
        this.album=album;
        displayAlbums();
    }

    /**
     * Returns to the specificAlbumView stage for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had an I/O exception happen
     */
    public void close(ActionEvent event) throws IOException {

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
     * Moves selectedPhoto from the current album to desired album
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had an I/O exception happen
     */
    public void move(ActionEvent event) throws IOException {
        Album select = albumsList.getSelectionModel().getSelectedItem();

        if (select == null) {

            errDialog("No selected album.");
            return;
        }
        
        ArrayList<Users> allUsers = deserialize.deserialize();
        album.removePhoto(photo);
        select.addPhoto(photo);
        
        for(Users u: allUsers) {

            if(u.getUserName().equals(user.getUserName())){

                allUsers.set(allUsers.indexOf(u), user);
            }
        }
        
        save.save(allUsers);
        Dialog("Successfully moved photo");
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
	 * Helper method to populate ListView with the current user's albums
	 */
    private void displayAlbums() {
        obsList = FXCollections.observableArrayList();

        for (Album album : user.getAlbums()) {
            obsList.add(album);
        }

        albumsList.setItems(obsList);

    }
    
    /**
     * Returns notification stating error has occured
     * @param emessage The error message we are displaying
     */
    public void errDialog(String emessage) {
       
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText("error");
        alert.setContentText(emessage);
        alert.showAndWait();
    
    }
    
    /**
     * Returns notification stating success in operation
     * @param emessage The success message we are displaying
     */
    public void Dialog(String emessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("success");
        alert.setHeaderText("success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
}
