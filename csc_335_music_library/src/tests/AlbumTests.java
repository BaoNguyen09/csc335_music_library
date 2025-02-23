package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;
import model.Song;

class AlbumTests {

	@Test
	void testGetAlbumTitle() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		assertEquals("19", album.getAlbumTitle(), "Album Title should be: 19");
	}
	
	@Test
	void testGetArtist() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		assertEquals("Adele", album.getArtist(), "Artist should be: Adele");
	}
	
	@Test
	void testGetGenre() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		assertEquals("Pop", album.getGenre(), "Genre should be: Pop");
	}
	
	@Test
	void testGetYear() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		assertEquals("2008", album.getYear(), "Year should be: 2008");
	}
	
	@Test
	void testGetSongsAndAddSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		Song song3 = new Song("Tired", "Adele", "19");
		Album album = new Album("19", "Adele", "Pop", "2008");
		album.addSong(song1);
		album.addSong(song2);
		album.addSong(song3);
		
		String expectedSong1 = "Daydreamer, Adele, 19";
		String expectedSong2 = "Best for Last, Adele, 19";
		String expectedSong3 = "Tired, Adele, 19";
		String[] expectedSongList = new String[3];
		expectedSongList[0] = expectedSong1;
		expectedSongList[1] = expectedSong2;
		expectedSongList[2] = expectedSong3;
		
		assertArrayEquals(expectedSongList, album.getSongs(), "getSongs should return a list of song in format: title, artist, album");		
	}
	
	@Test
	void testToStringMethod() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		assertEquals("19, Adele, Pop, 2008", album.toString(), "Album info should be: 19, Adele, Pop, 2008");
	}
}
