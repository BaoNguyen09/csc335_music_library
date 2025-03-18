package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.User;

public class UserStorage {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    /*
     * Creates and saves the user into the user database.
     * @pre username != null, password != null
     */
    public boolean createUser(String username, String password) {
    	if (!userExist(username)) {
    		User user = new User(username, password);
    		writeToJson(user);
    		return true;
    	} else {
    		return false;
    	}
    	 
    }
    
    /*
     * Loads a user with "username" and "password" from the user database.
     * @pre username != null, password != null
     */
    public User loadUser(String username, String password) {
        String filePath = "./src/database/users/" + username + ".json";
        File file = new File(filePath);
        if (!file.exists()) {
        	// file of the user does not exist
            return null;
        }
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Converting the whole file into a single line for gson
            while ((line = reader.readLine()) != null) {
            	fileContents.append(line).append("\n");
            }
            User user = gson.fromJson(fileContents.toString(), User.class);
            // If the user has the right username and password
            if (user.getUsername().equals(username) && user.checkPasswordMatch(password)) {
            	return user;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    	
    
    // Saves the the updated user data to database
    public boolean saveUser(User user) {
    	if (userExist(user.getUsername())) {
    		writeToJson(user);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // Either creates a new user json file or updates the existing json file
    private void writeToJson(User user) {
    	String filePath = "./src/database/users/" + user.getUsername() + ".json";
    	
    	String userData = gson.toJson(user);
		try (FileWriter writer = new FileWriter(filePath)) {
		    writer.write(userData);
		} catch (IOException e) {
		    e.printStackTrace();
		}
    	
    }
    
    // Checks if the user with "username" already exist (checking if the file exist)
    private boolean userExist(String username) {
        File file = new File("./src/database/users/" + username + ".json");
        return file.exists();
    }
}

