package controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Users;
import model.*;

/**
 * Controls the "albumView" stage
 */
public class albumController {
	@FXML ListView<Album> albumsListView;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML Button search;
	@FXML Button logout;
	@FXML Button quit;
	@FXML TextField albumname;
	@FXML TextField num;
	@FXML TextField date;
    Users current;
	Album select;
	Stage stage;

	private ObservableList<Album> obsList;
	

    /**
     * Initializes the controller's private fields, sets up controller for the stage
     * @param stage  The Stage that this controller controls (albumView)
     * @param user The User currently accessing this stage
     */
	public void start(Stage stage, Users user) {

		this.stage = stage;
		this.current= user;
		displayAlbums();
		select = albumsListView.getSelectionModel().getSelectedItem();
	}
	
	/**
     * Opens the addAlbumView stage for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void add(ActionEvent event) throws IOException, ClassNotFoundException {
	    try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addAlbumView.fxml"));
			Parent parent = (Parent) loader.load();
			addAlbumController controller = loader.<addAlbumController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(current);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
    
    /**
     * Deletes the selected album for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void delete (ActionEvent event) throws IOException, ClassNotFoundException {
		System.out.println(select.getName());
		select = albumsListView.getSelectionModel().getSelectedItem();
		if (select == null) {
			Dialog("No selected album.");
			return;
		}
				
        ArrayList<Users> saved = deserialize.deserialize();
        
		for (Users u : saved) {
        
            if (current.equals(u)) {
        
                current.deleteAlbum(select);
				saved.set(saved.indexOf(u), current);
			}
			save.save(saved);
		}
		displayAlbums();
		
	}
	
	/**
     * Opens the editView stage for the User
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void edit (ActionEvent event) throws IOException, ClassNotFoundException {
	try {
			select = albumsListView.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editView.fxml"));
			Parent parent = (Parent) loader.load();
			editController controller = loader.<editController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage, current, select);
			stage.setScene(scene);
			stage.show();

        } catch (Exception exception) {
			exception.printStackTrace();
		}
	}

    /**
     * Opens searchView stage for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
    public void search(ActionEvent event) throws IOException, ClassNotFoundException {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/searchView.fxml"));
			Parent parent = (Parent) loader.load();
			searchController controller = loader.<searchController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(stage,current);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	
	
	/**
     * Quits and closes the application
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had a I/O exception happened
     * @throws ClassNotFoundException Tells us we are missing a class needed for deserializing
     */
	public void quit (ActionEvent event) throws IOException, ClassNotFoundException {
		stage.close();
		
	}
	
	
	
	
	/**
     * Opens the loginView stage for the user 
     * @param event The ActionEvent we have after the button is interacted with
     */
	public void logout(ActionEvent event) {

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
     * Uses ListView to display available albums to the user
     */
	private void displayAlbums () {
		try {
            obsList = FXCollections.observableArrayList();
            

            for (Album album : current.getAlbums()) {
                obsList.add(album);
            }

            
            albumsListView.setItems(obsList);
        
            albumsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {

                @Override
                public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
                    if(newValue!=null) {
                        num.setText("Number of Photos: \t"+newValue.getPhotoQuantity());
                        albumname.setText("Name of Album: \t"+"\""+newValue.getName()+"\"");
                    }
                    else {
                        albumname.setText("");
                        num.setText("");
                    }
                }
            });         		
            
        // TO OPEN specificAlbumView: Double Click on the desired album 
            albumsListView.setOnMouseClicked (new EventHandler<MouseEvent>() {
                @Override
                public void handle (MouseEvent click) {
                    Album selectedAlbum = albumsListView.getSelectionModel().getSelectedItem();
                    if (click.getClickCount() == 2) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/specificAlbumView.fxml"));
                            Parent parent = (Parent) loader.load();
                            specificAlbumViewController controller = loader.<specificAlbumViewController>getController();
                            Scene scene = new Scene(parent);
                    
                            Stage stage = (Stage) ((Node) click.getSource()).getScene().getWindow();
                            controller.start(stage,current, selectedAlbum);
                            stage.setScene(scene);
                            stage.show();
                        } 
                        catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                    else {
                        select=selectedAlbum;
                    }
                }
            });
            albumsListView.getSelectionModel().select(0);
        } catch(NullPointerException e) {
		}
	
}

    /**
     * Display a success notification to the user
     * @param emessage The success message displayed to user
     */
	public void Dialog(String emessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Album Changed ");
        alert.setHeaderText("Success");
        alert.setContentText(emessage);
        alert.showAndWait();
}
	 
}

	

