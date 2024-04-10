package model;

import java.util.ArrayList;
import java.io.*;

/**
 * Represents the username and albums of a User of the application
 */

public class Users implements Serializable {
    
    
	private static final long serialVersionUID = 1L;
    private String username;
    private ArrayList<Album> albums; 

    /**
	 * Constructor to save instance of a unique user
	 * @param username The username of user being added
	 */
   
    public Users(String username){

        this.username = username;
 
    }

    /**
	 * Method to get the username of user
	 * @return String The username of this user
	 */
    public String getUserName(){
        return this.username;
    }

    /**
	 * Method to get Albums associated with user
	 * @return albums The user's albums
	 */
    public ArrayList<Album> getAlbums() {
        return albums;
	}
    
    
	/**
	 * Method to get the string representation of user
	 * @return String representation of user (username)
	 */
	public String toString() {
        return this.username;
	}
    
    
	/**
	 * Method to check if existing user already has selected name
	 * @param other The user to be compared with
	 * @return Boolean true if users are equal, false otherwise
	 */
	public boolean equals(Users other) {
        
        return this.username.equals(other.username);
    } 
    
    
    /**
	 * Method to add an album for a user
	 * @param a The album being added
	 */
    public void addAlbums(Album a) {
		System.out.println("adding: "+a.getName()+" to: "+albums.toString());
		this.albums.add(a);
	}

    
    /**
     * Method to set an album for a user
     * @param t ArrayList of albums the user wants set
     */
	public void setAlbums(ArrayList<Album> t){
	this.albums = t;
		
		
	}
    /**
	 * Method to remove an album for a user
	 * @param a Album to remove
	 */
    public void deleteAlbum(Album a) {
		this.albums.remove(a);
    }
    

    
}