package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing an album within our application
 */
public class Album implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Photos> photos;
    /**
   	 * Constructor letting user create an Album object with desired name
   	 * Initialize album with given name, empty ArrayList of photos
   	 * @param name the name of the album to be created
   	 */
    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photos>();
        
    }
    /**
   	 * Method getting photos from a specific album's ArrayList of pictures
   	 * @return arrayList of photos from the user's album
   	 */
    public ArrayList<Photos> getAlbumPhotos() {
        return this.photos;
    }
    
    /**
     * Method that gets the number of photos in user's specific album
   	 * @return int sums all the photos in the album and returns the sum
   	 */
    
    public int getPhotoQuantity() {
        return this.photos.size();
    }
    /**
   	 * Method that gets the name of user's specific album
   	 * @return String containing name of album
   	 */
    
    public String getName(){
        return this.name;
    }
    /**
   	 * Method to set the name of user's specific album
   	 * @param name A String containing the album name just set
   	 */
    
    public void setName(String name){
        this.name = name;
    }
    /**
   	 * Method that adds a specific photo from the album selected by user
   	 * @param pic The selected picture user is trying to add to album
   	 */
    
    public void addPhoto(Photos pic){
        photos.add(pic);

    }
    /**
   	 * Method that removes a specific photo from the album selected by user
   	 * @param pic The picture user is removing from album
   	 */
    
    public void removePhoto(Photos pic){
        if(photos.contains(pic)) photos.remove(pic);

    }
    /**
   	 * Method to get our album as a String
   	 * @return String containing the name of the album
   	 */
    public String toString() {
		return this.name;
	}
}