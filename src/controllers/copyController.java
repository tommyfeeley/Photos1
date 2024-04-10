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
 * Controls the "copyView" stage
 */
public class copyController {

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
     * @param stage The Stage that this controller controls (copyController)
     * @param user The current user accessing this Stage
     * @param album The album that contains the photo to be copied
     * @param photo The photo that the user wants to copy
     */
    public void start(Stage stage, Users user,Album album, Photos photo) {
       
        this.stage=stage;
        this.user=user;
        this.photo=photo;
        this.album=album;
        displayAlbums();
    }

    /**
     * Opens the specificAlbumView stage for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
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
     * Copies the specified photo for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     */
    public void copy(ActionEvent event) throws IOException {
        Album select = albumsList.getSelectionModel().getSelectedItem();

        if (select == null) {

            errDialog("No selected album.");
            return;
        }

        ArrayList<Users> allUsers = deserialize.deserialize();
        select.addPhoto(photo);
        

        for(Users u: allUsers) {

            if(u.getUserName().equals(user.getUserName())){

                allUsers.set(allUsers.indexOf(u), user);
            }
        }
        
        
        save.save(allUsers);
        Dialog("Successfully copied photo");

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
     * Uses ListView to display available albums to user
     */    
    private void displayAlbums() {
        obsList = FXCollections.observableArrayList();


        for (Album album : user.getAlbums()) {
            obsList.add(album);
        }


        albumsList.setItems(obsList);


    }

    /**
     * Displays a error notification to the user
     * @param emessage The error message displayed to the user
     */
    public void errDialog(String emessage) {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText("error");
        alert.setContentText(emessage);
        alert.showAndWait();
    }

    /**
     * Displays a success notification to the user
     * @param emessage The success message displayed to the user
     */
    public void Dialog(String emessage) {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("success");
        alert.setHeaderText("success");
        alert.setContentText(emessage);
        alert.showAndWait();
    }
}

