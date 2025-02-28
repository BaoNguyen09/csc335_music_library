package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.Song;

class MusicStoreTests {
	
	MusicStore store = new MusicStore();
	
	// Helper method that creates a string of all songs in the specified file
	private ArrayList<String> readSongFile(String fileName){
		ArrayList<String> songList = new ArrayList<String>();
		try(BufferedReader reader = new BufferedReader(new FileReader("src/database/albums/" + fileName))){
			String line = reader.readLine();
			
			while ((line = reader.readLine()) != null) {
				songList.add(line.strip());
			}
			reader.close();	

		} catch (IOException e) {
	        fail("Error reading file: " + fileName);
	    }
		return songList;
	}
	
	@Test
	void testSearchSongByTitleEmpty() {
		List<Song> songsList = store.searchSongByTitle("Not a Song");
		assertTrue(songsList.isEmpty());
		
	}
	@Test
	void testSearchSongByTitleSingleSong() {
		List<Song> songsList = store.searchSongByTitle("in my place");
		ArrayList<String> result = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			result.add(songsList.get(i).toString());
		}
		// Song.toString returns string "songTitle, artist, albumTitle"
		assertEquals("[In My Place, Coldplay, A Rush of Blood to the Head]", result.toString());
		
	}
	
	@Test
	void testSearchSongByTitleDupSong() {
		List<Song> songsList = store.searchSongByTitle("LULLABY");
		ArrayList<String> result = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			result.add(songsList.get(i).toString());
		}
		assertEquals(result.size(), 2);
		// Song.toString returns string "songTitle, artist, albumTitle
		assertEquals("Lullaby, Leonard Cohen, Old Ideas", result.get(0).toString());	
		assertEquals("Lullaby, OneRepublic, Waking Up", result.get(1).toString());	
	}
	
	@Test
	void testSearchSongByArtist1() {
		List<Song> songsList = store.searchSongByArtist("AdElE");
		
		ArrayList<String> observed = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			observed.add(songsList.get(i).getSongTitle());
		}
		
		ArrayList<String> expected = readSongFile("19_Adele.txt");
		expected.addAll(readSongFile("21_Adele.txt"));
		
		assertEquals(expected.toString(), observed.toString());

	}
	
	@Test
	void testSearchSongByArtist2() {
		List<Song> songsList = store.searchSongByArtist("mumford & Sons");
		ArrayList<String> observed = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			observed.add(songsList.get(i).getSongTitle());
		}
		
		ArrayList<String> expected = readSongFile("Sigh No More_Mumford & Sons.txt");
		assertEquals(expected.toString(), observed.toString());

	}
	@Test
	void testSearchAlbumByTitleEmpty() {
		List<Album> albumList = store.searchAlbumByTitle("Not a Album");
		assertTrue(albumList.isEmpty());
	}
	
	@Test
	void testSearchAlbumByTitle() {
		List<Album> albumList = store.searchAlbumByTitle("Boys & girls");
		ArrayList<String> result = new ArrayList<String>();
		for (int i= 0; i < albumList.size(); i++) {
			result.add(albumList.get(i).toString());
		}
		// Album.toString returns string "albumTitle, artist, genre, year"
		assertEquals("[Boys & Girls, Alabama Shakes, Alternative, 2012]", result.toString());
		
		// Check if the songs list is correct and in the right order
		ArrayList<String> expected = readSongFile("Boys & Girls_Alabama Shakes.txt");
		ArrayList<Song> songArray = albumList.get(0).getSongArray();
		
		ArrayList<String> observed = new ArrayList<String>();
		for (Song song: songArray) {
			observed.add(song.getSongTitle());
		}
		
		assertEquals(expected, observed);
	}
	
	@Test
	void testSearchAlbumByArtistEmpty() {
		List<Album> albumList = store.searchAlbumByArtist("Not an Artist");
		assertTrue(albumList.isEmpty());
	}
	
	@Test
	void testSearchAlbumByArtist() {
		List<Album> albumList = store.searchAlbumByArtist("Amos lee");
		ArrayList<String> result = new ArrayList<String>();
		
		// Converts each album in albumList to string
		for (int i= 0; i < albumList.size(); i++) {
			result.add(albumList.get(i).toString());
		}
		// Album.toString returns string "albumTitle, artist, genre, year"
		assertEquals("[Mission Bell, Amos Lee, Singer/Songwriter, 2010]", result.toString(),
				"album found from function is correct album");
		
		// Check if the songs list is correct and in the right order
		ArrayList<String> expected = readSongFile("Mission Bell_Amos Lee.txt");
		ArrayList<Song> songArray = albumList.get(0).getSongArray();
		
		// Converts each song in songArray into a string of songTitles
		ArrayList<String> observed = new ArrayList<String>();
		for (Song song: songArray) {
			observed.add(song.getSongTitle());
		}
		
		assertEquals(expected, observed, "the album's songsList is correct and in the right order");
	}
	
	@Test
	void testSearchAlbumByArtist2() {
		
		// Testing when there are multiple albums from same artist
		List<Album> albumList = store.searchAlbumByArtist("Adele");
		assertEquals(2, albumList.size());
		ArrayList<String> result = new ArrayList<String>();
		
		// Converts each album in albumList to string
		for (int i= 0; i < albumList.size(); i++) {
			result.add(albumList.get(i).getAlbumTitle());
		}
		assertEquals("[19, 21]", result.toString());
	}
	

}
