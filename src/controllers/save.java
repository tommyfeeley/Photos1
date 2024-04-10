package controllers;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import model.Users;

 /**
  * Uses OutputStreams to let us store user information
  */


public class save {
	/**
	 * File path where user information is store in a "dat" file
	 */
	public static final String path = "userAccounts.dat";
	
	/**
	 * @param users Accesses the users ArrayList, saves user info in ArrayList
	 */
	public static void save(ArrayList<Users> users) {
	
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("userAccounts.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(users);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
