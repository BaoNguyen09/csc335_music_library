package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.User;

class UserTests {

	@Test
	void testGetUsername() {
		User person = new User("Alice", "1234abc*@398");
		assertEquals("Alice", person.getUsername());
	}
	
	@Test
	void testCheckPasswordMatchTrue() {
		User person = new User("Alice", "1234abc*@398");
		assertTrue(person.checkPasswordMatch("1234abc*@398"));
	}
	
	@Test
	void testCheckPasswordMatchFalse() {
		User person = new User("Alice", "1234abc*@398");
		assertFalse(person.checkPasswordMatch("234abc*@398"));
	}

}
