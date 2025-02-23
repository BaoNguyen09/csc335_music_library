package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;

class AlbumTests {

	@Test
	void testGetAlbumTitle() {
		Album song = new Album("19", "Adele", "Pop", "2008");
		assertEquals("19", song.getAlbumTitle(), "Album Title should be: 19");
	}
	
	@Test
	void testGetArtist() {
		Album song = new Album("19", "Adele", "Pop", "2008");
		assertEquals("Adele", song.getArtist(), "Artist should be: Adele");
	}
	
	@Test
	void testGetGenre() {
		Album song = new Album("19", "Adele", "Pop", "2008");
		assertEquals("Pop", song.getGenre(), "Genre should be: Pop");
	}
	
	@Test
	void testGetYear() {
		Album song = new Album("19", "Adele", "Pop", "2008");
		assertEquals("2008", song.getYear(), "Year should be: 2008");
	}
}
