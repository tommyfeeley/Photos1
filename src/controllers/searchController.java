package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;

/**
 * Opens the searchView stage for the user
 */
public class searchController {
	@FXML ImageView image_view;
	@FXML Button quit;
	@FXML Button search;
	@FXML ListView<Photos> photoListView;
	@FXML ListView<Tags> tags;
	@FXML ListView<Tags> para_ListView;
	@FXML TextField caption;
	@FXML TextField date;
	@FXML private ChoiceBox<String> tagChoice, tagValue;
	@FXML private DatePicker fromDate, toDate;
	@FXML Button createAlbum;
	@FXML Button back;
	@FXML Button logout;
	@FXML Button removeTag;
	@FXML Button addTag;
    
    private ObservableList<Photos> obsList=FXCollections.observableArrayList();;
	private ObservableList<Tags> tagsObsList;
	private Users user;
	private Stage stage;

    /**
	 * Initialize the private fields of the controller and sets up controller
	 * @param stage The current stage being controlled by the search controller
     * @param user The current user doing the search in the application
	 */
    public void start(Stage stage, Users user) {
        
        this.user=user;
        this.stage=stage;
        
        ArrayList<String> tagtype = new ArrayList<String>();
        ArrayList<String> tagvalue = new ArrayList<String>();
        tagtype.add(0, "Tag Type");
        tagvalue.add(0, "Tag Value");
        ArrayList<Album> allalbums = user.getAlbums();
        
        for (Album curralbum : allalbums) {
        
            ArrayList<Photos> allphoto = curralbum.getAlbumPhotos();
            for (Photos photo : allphoto) {
        
                ArrayList<Tags> tag = photo.getTags();
                for (Tags t : tag) {
        
                    if (!tagtype.contains(t.getTagType())) tagtype.add(t.getTagType());
                    if (!tagvalue.contains(t.getTagValue())) tagvalue.add(t.getTagValue());
                
                }

            }

        }
        
        tagChoice.setItems(FXCollections.observableArrayList(tagtype));
        tagChoice.setValue("Tag Type");

        tagValue.setItems(FXCollections.observableArrayList(tagvalue));
        tagValue.setValue("Tag Value");
        
    }
    
    /**
	 * Populates the ListView to meet the user's search parameters	 
	 * @param event The ActionEvent we have after the button is interacted with
	 * @throws IOException Tells us we had an I/O exception happen
	 */
    public void search(ActionEvent event) throws IOException{
        
        photoListView.getItems().clear();

        ArrayList<Album> albumList = user.getAlbums();
        for (Album album : albumList) {
        
            ArrayList<Photos> photolist = album.getAlbumPhotos();
        
            for (Photos photo : photolist) {
        
                boolean added = false;
                ArrayList<Tags> phototag = photo.getTags();
                String[] photodate = photo.getDate().toString().split(" ");
                Alert alert = new Alert(AlertType.ERROR);

                DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
                TemporalAccessor accessor = parser.parse(photodate[1]);
                int month = accessor.get(ChronoField.MONTH_OF_YEAR);

                LocalDate photoDate = LocalDate.of(Integer.parseInt(photodate[5]), month,
                        Integer.parseInt(photodate[2]));
                
                if (fromDate.getValue() != null) {

                    // start of our range: FromDate
                    String frdate = fromDate.getValue().toString();
                    String[] fromdate = frdate.split("-");
                    LocalDate f_date = LocalDate.of(Integer.parseInt(fromdate[0]), Integer.parseInt(fromdate[1]),
                            Integer.parseInt(fromdate[2]));

                    // End of our range: ToDate
                    String todate = toDate.getValue().toString();
                    String[] tdate = todate.split("-");
                    LocalDate t_date = LocalDate.of(Integer.parseInt(tdate[0]), Integer.parseInt(tdate[1]),
                            Integer.parseInt(tdate[2]));

                    if (photoDate.isAfter(f_date) && photoDate.isBefore(t_date)) {

                        if (photoListView.getItems().contains(photo)) continue; 
                        else {
                            
                            added = true;
                            obsList.add(photo);
                            render();
                        }

                    }
                }

                if (tagChoice.getSelectionModel().getSelectedItem().toString() != null && tagValue.getSelectionModel().getSelectedItem().toString() != null && added == false) {
                    
                    for (Tags currTag : tags.getItems()) {
                    
                        for (Tags pTag : phototag) {
                    
                            if (currTag.getTagType().matches(pTag.getTagType()) && currTag.getTagValue().matches(pTag.getTagValue())) {
                    
                                if (photoListView.getItems().contains(photo)) {
                    
                                    continue;
                    
                                } else {
                    
                                    obsList.add(photo);
                                    render();
                                }

                            }

                        }
                    }

                }
            }
        }
    }

    /**
	 * Populates the ListView matching tags specified by user
	 * @param event The ActionEvent we have after the button is interacted with
	 */
    public void addTag(ActionEvent event) {

        ObservableList<Tags> tagList = tags.getItems();

        Tags newTag = new Tags(tagChoice.getSelectionModel().getSelectedItem().toString(),
                tagValue.getSelectionModel().getSelectedItem().toString());
        
        for (Tags currentTag : tagList) {

            if (currentTag.equals(newTag)) {
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Search View Error");
                alert.setHeaderText("Tag Add Error.");
                alert.setContentText("A tag with this name and value already exists.");
                alert.showAndWait();
                return;
            }
        }

        tags.getItems().add(newTag);
        tags.refresh();
        tags.getSelectionModel().select(0);
        tagChoice.getSelectionModel().select(0);
        tagValue.getSelectionModel().select(0);

    }

    /**
	 * Removes the matching tags from the ListView
	 * @param event The ActionEvent we have after the button is interacted with
	 */
    public void removeTag(ActionEvent event) {
        
        tags.getItems().remove(tags.getSelectionModel().getSelectedItem());
        tags.refresh();
        tags.getSelectionModel().select(0);

    }
    
    /**
	 * Creates an album with the search results
	 * @param event The ActionEvent we have after the button is interacted with
	 */
    public void createAlbum(ActionEvent event) {
       
        if (photoListView.getItems().isEmpty()) {
       
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Search View Error");
            alert.setHeaderText("No Photos Error.");
            alert.setContentText("Please Search Photos to Create Album.");
            alert.showAndWait();

        } else {
       
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Album newAlbum = new Album("Search Result " + dateFormat.format(cal.getTime()).toString());
            user.getAlbums().add(newAlbum);
            for (Photos currphoto : photoListView.getItems()) {
       
                newAlbum.getAlbumPhotos().add(currphoto);
            
            }
            ArrayList<Users> savedUsers = deserialize.deserialize();
            // Find currentUser in storedUsers and add album to currentUser
            for (Users u : savedUsers) {
       
                if (u.equals(user)) {
       
                    savedUsers.set(savedUsers.indexOf(u), user);
                }
            }
            save.save(savedUsers);
            
        }
        
    }

    /**
     * Display pictures in ListView after retrieving from search results
     */
    private void render() {
    	photoListView.setItems(obsList);
    
    	photoListView.setCellFactory(param -> new ListCell<Photos>() {
    		private ImageView imageView = new ImageView();
    		@Override
    		public void updateItem (Photos photo, boolean empty) {
    			super.updateItem(photo, empty);
            
    			if (empty) {
    				setText (null);
    				setGraphic(null);
            } else {
            
                String path = "file:///" + photo.getPath();
                Image image = new Image(path, 50, 50, true, true);
                imageView.setImage(image);
                setGraphic(imageView);
            }
            }
        });

    }
    
    /**
     * Returns to the loginView stage for the user
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
     * Returns to the albumView stage for the user
     * @param event The ActionEvent we have after the button is interacted with
     * @throws IOException Tells us we had an I/O exception happen
     */
    public void back(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albumView.fxml"));
        Parent parent = (Parent) loader.load();
        albumController controller = loader.<albumController>getController();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.start(stage,user);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Exits and quits the application
     * @param event The ActionEvent we have after the button is interacted with
     */
    public void quit(ActionEvent event) {
        
        stage.close();
    }



}
