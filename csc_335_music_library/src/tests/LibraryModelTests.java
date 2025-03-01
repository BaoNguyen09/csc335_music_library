package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.Song;
import model.Song.Rating;

class LibraryModelTests {

	MusicStore store = new MusicStore();
	LibraryModel library = new LibraryModel();

	// TESTING GETTER METHODS
	@Test 
	void testGetSongTitles_CaseInsensitive() {
		library.addSong(store, "If I lose My mind" , "Dolly Parton", "Coat of Many Colors");
		library.addSong(store, "here i Am" , "Dolly Parton", "Coat of Many Colors");

		assertEquals(2, library.getSongTitles().length, "Two song titles should be returned");
		assertEquals(true, containsItem(library.getSongTitles(), "Here I Am"), "The title should be original capitalization");
		assertEquals(true, containsItem(library.getSongTitles(), "If I Lose My Mind"), "The title should be original capitalization");
		assertEquals(false, containsItem(library.getSongTitles(), "Early Morning Breeze"), "This song wasn't added to the library");
	}
	
	@Test 
	void testGetArtist_CaseInsensitive() {
		library.addSong(store, "If I lose My mind" , "Dolly parton", "Coat of Many Colors");
		library.addSong(store, "DayDreamer" , "adele", "19");

		assertEquals(2, library.getArtists().length, "Two artists should be returned");
		assertEquals(true, containsItem(library.getArtists(), "Adele"), "The artist name should be original capitalization");
		assertEquals(true, containsItem(library.getArtists(), "Dolly Parton"), "The artist name should be original capitalization");
		assertEquals(false, containsItem(library.getArtists(), "Amos Lee"), "This artist wasn't added to the library");
	}
	
	@Test 
	void testGetAlbumTitles_CaseInsensitive() {
		library.addAlbum(store, "coat of many Colors", "Dolly parton");
		library.addAlbum(store, "19", "adele");

		assertEquals(2, library.getAlbumTitles().length, "Two album titles should be returned");
		assertEquals(true, containsItem(library.getAlbumTitles(), "Coat of Many Colors"), "The album title should be original capitalization");
		assertEquals(true, containsItem(library.getAlbumTitles(), "19"), "The album title should be original capitalization");
		assertEquals(false, containsItem(library.getAlbumTitles(), "Mission Bell"), "This album wasn't added to the library");
	}
	
	boolean containsItem(String[] items, String item) {
		for (int i=0; i<items.length; i++) {
			if (item.equals(items[i])) {
				return true;
			}
		}
		return false;
	}
	
	// TESTING ADD SONG
	@Test
	void testAddSongSuccess() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors"));
		List<Song> songListByTitle = library.searchSongByTitle("if i lose my mind");
		List<Song> songListByArtist = library.searchSongByArtist("dolly parton");
		assertEquals(1, songListByTitle.size());
		assertEquals(1, songListByTitle.size());
		
		Song expectedSong = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors");
		assertEquals(expectedSong.toString(), songListByTitle.get(0).toString());
		assertEquals(expectedSong.toString(), songListByArtist.get(0).toString());
	}
	
	
	@Test
	void testAddSongSameTitle() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "lullaby" , "leonard cohen", "old ideas"));
		List<Song> observedSongList = library.searchSongByTitle("lullaby");
		List<Song> songListByArtist = library.searchSongByArtist("leonard cohen");
		List<Song> songListByArtist2 = library.searchSongByArtist("onerepublic");
		assertEquals(1, observedSongList.size());
		assertEquals(1, songListByArtist.size());
		assertEquals(0, songListByArtist2.size());
		Song expectedSong = new Song("Lullaby" , "Leonard Cohen", "Old Ideas");
		assertEquals("["+ expectedSong.toString() + "]", observedSongList.toString());
		
		// Testing when adding the dup lullaby song
		assertTrue(library.addSong(store, "lullaby" , "onerepublic", "waking up"));
		observedSongList = library.searchSongByTitle("lullaby");
		assertEquals(2, observedSongList.size());
		Song secondSong = new Song("Lullaby", "OneRepublic", "Waking Up");
		assertEquals("["+ expectedSong.toString() + ", " + secondSong.toString() + "]", 
				observedSongList.toString());

		
	}
	@Test
	void testAddSongNoDuplication() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors"));
		assertFalse(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors"));
		List<Song> songListByTitle = library.searchSongByTitle("if i lose my mind");
		List<Song> songListByArtist = library.searchSongByArtist("dolly parton");
		assertEquals(1, songListByTitle.size());
		assertEquals(1, songListByTitle.size());
		
		Song expectedSong = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors");
		assertEquals(expectedSong.toString(), songListByTitle.get(0).toString());
		assertEquals(expectedSong.toString(), songListByArtist.get(0).toString());
	}
	
	
	
	@Test
	void testAddSongFailToFindSongTitle() {
		// store, songTitle, artist, and album
		assertFalse(library.addSong(store, "Not a song" , "Dolly Parton", "Coat of Many Colors"));
		List<Song> observedSongList = library.searchSongByTitle("Not a song");
		assertEquals(0, observedSongList.size());	
	}
	
	@Test
	void testAddSongFailToFindArtist() {
		// store, songTitle, artist, and album
		assertFalse(library.addSong(store, "If I Lose My Mind" , "Not artist", "Coat of Many Colors"));
		List<Song> observedSongList = library.searchSongByTitle("Not a song");
		assertEquals(0, observedSongList.size());	
	}

	@Test
	void testAddSongFailToFindAlbum() {
		// store, songTitle, artist, and album
		assertFalse(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "not an album"));
		List<Song> observedSongList = library.searchSongByTitle("Not a song");
		assertEquals(0, observedSongList.size());	
	}
	
	
	// TESTING ADD ALBUM
	
	@Test
	void testAddAlbumSuccess() {
		// store, album, artist
		assertTrue(library.addAlbum(store, "Don't Mess with the dragon" , "ozomatli"));
		List<Album> albumListByTitle = library.searchAlbumByTitle("don't mess with the dragon");
		List<Album> albumListByArtist = library.searchAlbumByArtist("ozomatli");
		assertEquals(1, albumListByTitle.size());
		assertEquals(1, albumListByArtist.size());
				
		// Check that all songs in album is added
		ArrayList<String> expectedSongList = readSongFile("Don't Mess With the Dragon_Ozomatli.txt");
		
		List<Song> songListByArtist = library.searchSongByArtist("OZOMATLI");
		ArrayList<String> observed = new ArrayList<String>();
		for (int i= 0; i < songListByArtist.size(); i++) {
			observed.add(songListByArtist.get(i).getSongTitle());
		}
		assertEquals(expectedSongList.toString(), observed.toString());
	} 
	
	
	@Test
	void testAddAlbumDupFail() {
		// store, album, artist
		assertTrue(library.addAlbum(store, "Don't Mess with the dragon" , "ozomatli"));
		assertFalse(library.addAlbum(store, "Don't Mess with the dragon" , "ozomatli"));
		assertEquals(1, library.searchAlbumByArtist("ozomatli").size());
		
	}
	
	@Test
	void testAddAlbumFailToFindAlbumTitle() {
		// store, album, artist
		assertFalse(library.addAlbum(store, "Not an album" , "Dolly Parton"));
		List<Album> observedSongList = library.searchAlbumByArtist("Dolly Parton");
		assertEquals(0, observedSongList.size());	
	}
	
	@Test
	void testAddAlbumFailToFindArtist() {
		// store, songTitle, artist, and album
		assertFalse(library.addAlbum(store, "If I Lose My Mind" , "Not artist"));
		List<Album> observedSongList = library.searchAlbumByTitle("If I Lose My Mind");
		assertEquals(0, observedSongList.size());	
	}

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
	void testRateSong() {
		library.addSong(store, "If I lose My mind" , "Dolly parton", "Coat of Many Colors");
		library.rateSong("If I lose My mind" , "Dolly parton", "Coat of Many Colors", Rating.FOUR_STAR);
		library.addSong(store, "DayDreamer" , "adele", "19");
		library.rateSong("DayDreamer" , "adele", "19", Rating.FIVE_STAR);
		library.addSong(store, "Behind me now" , "Amos Lee", "Mission Bell"); // this song rating is set as default
		String[] songRatings = library.getSongRatings();
		
		assertEquals(3, library.getSongRatings().length, "All songs should be returned");
		assertEquals(true, containsItem(songRatings, "If I Lose My Mind by Dolly Parton in album Coat of Many Colors - FOUR_STAR"), "This song is rated 4 stars");
		assertEquals(true, containsItem(songRatings, "Daydreamer by Adele in album 19 - FIVE_STAR"), "This song is rated 5 stars");
		assertEquals(true, containsItem(songRatings, "Behind Me Now by Amos Lee in album Mission Bell - UNRATED"), "This song is not rated yet");
	}
	
	@Test 
	void testMarkAsFavorite() {
		library.addSong(store, "If I lose My mind" , "Dolly parton", "Coat of Many Colors");
		library.markAsFavorite("If I lose My mind" , "Dolly parton", "Coat of Many Colors");
		library.addSong(store, "DayDreamer" , "adele", "19");
		library.rateSong("DayDreamer" , "adele", "19", Rating.FIVE_STAR); // five star will automatically set the song as favorite
		library.addSong(store, "Behind me now" , "Amos Lee", "Mission Bell");

		assertEquals(2, library.getFavoriteSongs().length, "Two favorite song titles should be returned");
		assertEquals(true, containsItem(library.getFavoriteSongs(), "Daydreamer"), "The title should be original capitalization");
		assertEquals(true, containsItem(library.getFavoriteSongs(), "If I Lose My Mind"), "The title should be original capitalization");
		assertEquals(false, containsItem(library.getFavoriteSongs(), "Behind Me Now"), "This song isn't favorite");
	}
  
}
