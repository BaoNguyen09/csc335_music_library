package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;
import model.Song;

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
		String[] expectedSongLists = new String[3];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;
		expectedSongLists[2] = expectedSong3;
		
		assertArrayEquals(expectedSongLists, album.getSongs(), "getSongs should return a list of song in format: title, artist, album");		
	}
}
