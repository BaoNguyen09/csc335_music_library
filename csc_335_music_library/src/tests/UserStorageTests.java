package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.MusicStore;
import database.UserStorage;
import model.LibraryModel;
import model.Song;
import model.User;


class UserStorageTests {
	 UserStorage storage = new UserStorage();
	 MusicStore store = new MusicStore();
	
	 @BeforeEach
	 
     // Clean the database by removing all user JSON files before each test.
	 void cleanDatabaseBeforeTest() {
	        File directory = new File("./src/database/users/");
	        File[] jsonFiles = directory.listFiles((dir, name) -> name.endsWith(".json"));
	        for (File file : jsonFiles) {
	            file.delete();
	        }

	    }
	 
	@Test
	void testCreateUserTrue() {
		assertTrue(storage.createUser("Alice", "hdfhdsif92"));
	}
	
	@Test
	void testCreateUserDuplicateUsers() {
		assertTrue(storage.createUser("Alice", "hdfhdsif92"));
		assertFalse(storage.createUser("Alice", "hdfhdsif92"));
	}
	
	@Test
	void testLoadUserTrue() {
		assertTrue(storage.createUser("Alice", "hdfhdsif92"));
		User alice = storage.loadUser("Alice", "hdfhdsif92");
		assertEquals("Alice", alice.getUsername());
		assertTrue(alice.checkPasswordMatch("hdfhdsif92"));
	}
	
	@Test
	void testLoadUserWrongPassword() {
		assertTrue(storage.createUser("Alice", "hdfhdsif92"));
		User alice = storage.loadUser("Alice", "hdfhdsif9");
		assertEquals(null, alice);
	}
	
	@Test
	void testLoadUserWrongUsername() {
		assertTrue(storage.createUser("Alice", "hdfhdsif92"));
		User alice = storage.loadUser("alice", "hdfhdsif92");
		assertEquals(null, alice);
	}
	
	@Test
	void testSaveUserTrue() {
		assertTrue(storage.createUser("Alice", "hdfhdsif92"));
		User alice = storage.loadUser("Alice", "hdfhdsif92");
		assertEquals(0, alice.getLibrary().getAlbumTitles().length);
		
		LibraryModel aliceLibrary = alice.getLibrary();
		aliceLibrary.addAlbum(store, "19", "adele");
		
		// Updates the user's library and then saves user data
		alice.updateLibrary(aliceLibrary);
		assertTrue(storage.saveUser(alice));
		alice = storage.loadUser("Alice", "hdfhdsif92");
		
		assertEquals(1, alice.getLibrary().getAlbumTitles().length);		
	}
	
	@Test
	void testSaveUserFalse() {
		// User was not created using the createUser method in storage
		User user = new User("Alice", "jfdifjd");
		assertFalse(storage.saveUser(user));
	}
	
	@Test
	void testMultipleUsers() {
		storage.createUser("Alice12", "1234359483049@*(#*$(#");
		storage.createUser("Leo", "9uijd0fhf90343");
		User leo = storage.loadUser("Leo" , "9uijd0fhf90343");
		assertEquals("Leo", leo.getUsername());
		LibraryModel leoLibrary = leo.getLibrary();
		leoLibrary.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors");
		
		// Saving changes
		leo.updateLibrary(leoLibrary);
		storage.saveUser(leo);
		
		leo = storage.loadUser("Leo" , "9uijd0fhf90343");
		leoLibrary = leo.getLibrary();
		String[] listOfSongs = leoLibrary.getSongTitles();
		assertEquals(1, listOfSongs.length);
		assertEquals("If I Lose My Mind", listOfSongs[0]);     
	}
	
}
