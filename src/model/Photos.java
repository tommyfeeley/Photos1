package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;

import java.util.Date;

/**
 * Class representing a photo in our application
 */

public class Photos implements Serializable{
	   
    private static final long serialVersionUID = 1L;
    private String name;
    private String caption;
    private File file;
	private Date date;
    private ArrayList<Tags> tags;
    
    /**
	 * Constructor letting us create Photo object from a picture file
	 * @param file The file that represents photo
	 *
	 */
    public Photos(File file){
        
        this.file = file;
        this.caption = "";
		this.date = new Date(file.lastModified());
        this.tags = new ArrayList<Tags>();

    }
    
    /**
   	 * Constructor letting us create Photo object from a picture file with a caption
   	 * @param file The file that represents photo
   	 * @param caption A String with caption for new Photo object
   	 * 
   	 */    
 public Photos(File file, String caption){
        
        this.file = file;
        this.caption = caption;
		this.date = new Date(file.lastModified());
        this.tags = new ArrayList<Tags>();

    }
 
 	/**
 	 * Method to get the name of a photo
	 * @return String Containing name of photo
	 */
    public String getName() {
    
        return name;
    }
    
    /**
     * Method to return the caption of a photo
   	 * @return String Containing caption of photo
   	 * 
   	 */
    public String getCaption() {
        
        return this.caption;
    }
    /**
     * Method to create a new caption for photo, or overwrite old caption
   	 * @param newCaption The new caption being added to a photo
   	 * 
   	 */
    public void setCaption (String newCaption) {
        
        this.caption = newCaption;
    }
    /**
     * Method to remove the caption from the photo
   	 * 
   	 */
    public void removeCaption () {
        
        this.caption = "";
    }
    /**
     * Method to return all the tags of a photo
   	 * @return ArrayList All tags for that photo
   	 * 
   	 */
    public ArrayList<Tags> getTags(){
        
        return this.tags;
    }
    /**
     * Method to return the number of tags a photo has
   	 * @return int Representing that photo's number of tags
   	 * 
   	 */
    public int getTagsQuantity(){

        return this.tags.size();
    }
    
    /**
     * Method to return the date of a photo
   	 * @return String Telling the photo's date in typical format
   	 * 
   	 */
	public String getDateString(){
		return new SimpleDateFormat("MM/dd/yy").format(this.getDate());
	}
	
	/**
   	 * Method that returns date in String but not "typical" format
   	 * @return String Telling date	in very specific detail
   	 * 
   	 */
    public String getDate(){

        return this.date+"";
    }
    
    /**
   	 * Method that returns the file used for that picture
   	 * @return String Representing the file the application added as a photo
   	 * 
   	 */
	public File getFile() {
		return this.file;
	}
	/**
   	 * Method comparing current photo to another photo
   	 * @param other The photo we want to compare to our current photo
   	 * @return boolean True or false if the photos are equal
   	 * 
   	 */
    public boolean equals(Photos other) {
        
        return this.name.equals(other.name);
    } 
    /**
   	 * Method to return the path of a photo in memory
   	 * @return String Containing the path where photo is located
   	 * 
   	 */
    public String getPath() {
        
        return this.file.getPath();
	}
    
}