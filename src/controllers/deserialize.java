package controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import model.Users;

 /**
 * Utility class to deserialize User info from stored file
 */
public class deserialize {
	/**
	 * Reads and deserializes user data stored in "userAccounts.data"
	 * @return ArrayList containing deserialized User objects, null if error occurs
	 */

	public static ArrayList<Users> deserialize() {
		ArrayList<Users> storedUsers = null;
		try {
			FileInputStream fileIn = new FileInputStream("userAccounts.dat");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			storedUsers = (ArrayList<Users>) in.readObject();
			in.close();
			fileIn.close();
			return storedUsers;

		}	
	catch (ClassNotFoundException ex) {
		System.out.println("Cant find a certain class");
		}
	catch (IOException ex) {
		System.out.println("Cant read file");
		
		
		}
		return storedUsers;
	} 
}