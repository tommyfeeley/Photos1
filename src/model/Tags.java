package model;

import java.io.Serializable;
/**
 * Represents the type and value of a certain tag
 */
public class Tags implements Serializable{

	private static final long serialVersionUID = 1L;
    private String type;
    private String value;


    /**
	 * Constructor that creates a Tag object with a certain type and value
	 * @param type The name of the tag
	 * @param value The value associated with the tag
	 */
    public Tags(String type, String value){
        
        this.type = type;
        this.value = value;
    }

    /**
	 * Method that gets tag's type
	 * @return The name of the tag
	 */
    public String getTagType(){

        return this.type;
    }

    /**
	 * Method to get the tag's value
	 * @return The value associated with the tag
	 */
    public String getTagValue(){

        return this.value;
    }
    
    /**
	 * Method to compare if tags are equal
	 * @param other The other tag being compared 
	 * @return True if the tags are equal, false otherwise
	 */
    public boolean equals(Tags other) {
        
        return type.equals(other.type) && value.equals(other.value);
    }
    
    /**
	 * Method to get string representation of the tag's type and value
	 * @return A string representation of the tag in type:value format
	 */
    public String toString() {
        
        return type + " : " + value;
	}


}