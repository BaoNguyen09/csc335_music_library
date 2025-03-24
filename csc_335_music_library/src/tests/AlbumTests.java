package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Album;
import model.Playlist;
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
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song song3 = new Song("Tired", "Adele", "19", "Pop");
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
	void testGetSongArray() {
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song song3 = new Song("Tired", "Adele", "19", "Pop");
		Album album = new Album("19", "Adele", "Pop", "2008");
		album.addSong(song1);
		album.addSong(song2);
		album.addSong(song3);

		ArrayList<Song> expectedSongList = new ArrayList<Song>();
		expectedSongList.add(song1);
		expectedSongList.add(song2);
		expectedSongList.add(song3);
		
		assertEquals(expectedSongList.toString(), album.getSongArray().toString(), "getSongs should return a list of song in format: title, artist, album");		
	}
	
	@Test
	void testCopyConstructor() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		String[] expectedSongList1 = new String[0];
		assertArrayEquals(expectedSongList1, album.getSongs(), "Initially, getSongs return an empty array");
		
		Album copyAlbum = new Album(album);
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		album.addSong(song);
		String[] expectedSongList2 = new String[1];
		String expectedSong = "Daydreamer, Adele, 19";
		expectedSongList2[0] = expectedSong;
		assertArrayEquals(expectedSongList2, album.getSongs(), "Afterwards, original Album object will have 1 song");
		assertArrayEquals(expectedSongList1, copyAlbum.getSongs(), "Afterwards, copied Album object will have no song");
	}
	
	@Test
	void testToStringMethod() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		assertEquals("19, Adele, Pop, 2008", album.toString(), "Album info should be: 19, Adele, Pop, 2008");
	}
	
	@Test

	void testContainsSongTrue() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		album.addSong(song1);
		assertTrue(album.containsSong(song1));
  }
  
  @Test
	void testRemoveSongTrue() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song songToRemove = new Song("Daydreamer", "Adele", "19", "Pop");

		album.addSong(song1);
		album.addSong(song2);
		assertTrue(album.removeSong(songToRemove));
		ArrayList<Song> songsInAlbum = album.getSongArray();
		assertEquals("[Best for Last, Adele, 19]", songsInAlbum.toString());

	}
	
	@Test
	void testContainsSongFalse() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		assertFalse(album.containsSong(song1));
	}

  @Test
	void testRemoveSongFalse() {
		Album album = new Album("19", "Adele", "Pop", "2008");
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song songToRemove = new Song("NotInAlbum", "Adele", "19", "Pop");

		album.addSong(song1);
		album.addSong(song2);
		assertFalse(album.removeSong(songToRemove));
		ArrayList<Song> songsInAlbum = album.getSongArray();
		assertEquals("[Daydreamer, Adele, 19, Best for Last, Adele, 19]", songsInAlbum.toString());
  }	
}
