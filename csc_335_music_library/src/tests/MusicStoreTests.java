package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Song;

class MusicStoreTests {
	
	MusicStore store = new MusicStore();
	
	// Helper method that creates a string of all songs in the specified file
	private ArrayList<String> readSongFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("src/database/albums/" + fileName));
		String line = reader.readLine();
		ArrayList<String> songList = new ArrayList<String>();
		
		while ((line = reader.readLine()) != null) {
			songList.add(line.strip());
		}
		reader.close();
		return songList;
	}
	
	@Test
	void testSearchSongByTitle1() {
		List<Song> songsList = store.searchSongByTitle("In My Place");
		ArrayList<String> result = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			result.add(songsList.get(i).toString());
		}
		// Song.toString returns string "songTitle, artist, albumTitle"
		assertEquals("[In My Place, Coldplay, A Rush of Blood to the Head]", result.toString());
		
	}
	
	@Test
	void testSearchSongByTitle2() {
		List<Song> songsList = store.searchSongByTitle("Missing Persons 1 & 2");
		ArrayList<String> result = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			result.add(songsList.get(i).toString());
		}
		// Song.toString returns string "songTitle, artist, albumTitle"
		assertEquals("[Missing Persons 1 & 2, OneRepublic, Waking Up]", result.toString());
		
	}

	@Test
	void testSearchSongByTitleEmpty() {
		List<Song> songsList = store.searchSongByTitle("Not a Song");
		ArrayList<String> result = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			result.add(songsList.get(i).toString());
		}
		// Song.toString returns string "songTitle, artist, albumTitle"
		assertEquals("[]", result.toString());
		
	}
	
	@Test
	void testSearchSongByArtistEmpty() {
		List<Song> songsList = store.searchSongByArtist("Not an Artist");
		ArrayList<String> observed = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			observed.add(songsList.get(i).toString());
		}
		assertEquals("[]", observed.toString());
	}
	
	@Test
	void testSearchSongByArtist1() throws IOException {
		List<Song> songsList = store.searchSongByArtist("Adele");
		ArrayList<String> observed = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			observed.add(songsList.get(i).getSongTitle());
		}
		
		ArrayList<String> expected = readSongFile("19_Adele.txt");
		expected.addAll(readSongFile("21_Adele.txt"));
		assertEquals(expected.toString(), observed.toString());

	}
	
	@Test
	void testSearchSongByArtist2() throws IOException {
		List<Song> songsList = store.searchSongByArtist("Mumford & Sons");
		ArrayList<String> observed = new ArrayList<String>();
		for (int i= 0; i < songsList.size(); i++) {
			observed.add(songsList.get(i).getSongTitle());
		}
		
		ArrayList<String> expected = readSongFile("Sigh No More_Mumford & Sons.txt");
		assertEquals(expected.toString(), observed.toString());

	}

}
