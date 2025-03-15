package model;
import org.mindrot.jbcrypt.BCrypt;

public class User {
	private String username;
	private String password;
	private LibraryModel userLibrary;
	
	public User (String username, String password) {
		this.username = username;
		this.password = hashPassword(password);
		userLibrary = new LibraryModel();		
	}
	
	public String getUsername() {
		return username;
	}
	
	// Password Encryption using bcrypt hasing and salting
	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	// First find the user instance with the same username in the
	// database, the check that the user instance has the right password
	// using this function.
	public boolean checkPasswordMatch(String userInputPassword) {
		return BCrypt.checkpw(userInputPassword, this.password);
	}
	
}
