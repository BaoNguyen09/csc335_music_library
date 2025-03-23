package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.Playlist;
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
	
	@Test
	void testGetSongs() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors"));
		assertTrue(library.addSong(store, "My Blue Tears" , "Dolly Parton", "Coat of Many Colors"));
		assertTrue(library.addSong(store, "Traveling Man" , "Dolly Parton", "Coat of Many Colors"));
		
		Song expectedSong1 = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors", "Pop");
		Song expectedSong2 = new Song("My Blue Tears" , "Dolly Parton", "Coat of Many Colors", "Pop");
		Song expectedSong3 = new Song("Traveling Man" , "Dolly Parton", "Coat of Many Colors", "Pop");
		ArrayList<Song> expectedSongs = new ArrayList<Song>();
		expectedSongs.add(expectedSong1);
		expectedSongs.add(expectedSong2);
		expectedSongs.add(expectedSong3);
		
		assertTrue(library.getSongs().equals(expectedSongs), "the playlist should have new order");
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
		
		Song expectedSong = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors", "Pop");
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
		Song expectedSong = new Song("Lullaby" , "Leonard Cohen", "Old Ideas", "Pop");
		assertEquals("["+ expectedSong.toString() + "]", observedSongList.toString());
		
		// Testing when adding the dup lullaby song
		assertTrue(library.addSong(store, "lullaby" , "onerepublic", "waking up"));
		observedSongList = library.searchSongByTitle("lullaby");
		assertEquals(2, observedSongList.size());
		Song secondSong = new Song("Lullaby", "OneRepublic", "Waking Up", "Pop");
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
		
		Song expectedSong = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors", "Pop");
		assertEquals(expectedSong.toString(), songListByTitle.get(0).toString());
		assertEquals(expectedSong.toString(), songListByArtist.get(0).toString());
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

	// TESTING PLAYLIST METHODS
	
	// helper method to ensure that expected string list is what
	// the observed string list is
	private boolean testStringListEquals (String[] expected, String[] observed) {
		for (int i=0; i <expected.length; i++) {
			if (expected[i] != observed[i]) {
				return false;
			}
		}
		return true;
	}
	
	@Test
	void testAddPlaylist() {
		assertTrue(library.addPlaylist("hello"));
		String[] expected = new String[1];
		expected[0] = "hello";
		
		// Test that the two string lists are the same
		assertTrue(testStringListEquals(expected, library.getPlaylistTitles()));
		
	}
	
	@Test
	void testAddPlaylistSameName() {
		assertTrue(library.addPlaylist("hello"));
		assertFalse(library.addPlaylist("hello"));
		String[] expected = new String[1];
		expected[0] = "hello";
		
		// Test that the two string lists are the same
		assertTrue(testStringListEquals(expected, library.getPlaylistTitles()));
		
	}
	
	@Test
	void testAddSongToPlaylistFailNoSongInLibrary() {
		assertTrue(library.addPlaylist("My Playlist 1"));
		// playlist name, songTitle, artist, album
		assertFalse(library.addSongToPlaylist("My Playlist 1", "Daylight",
				"ColdPlay", "A Rush of Blood to the Head"));
	}
	
	@Test
	void testAddSongToPlaylistSuccess() {
		assertTrue(library.addPlaylist("My Playlist 1"));
		library.addSong(store, "Daylight", "Coldplay", "A Rush of Blood to the Head");
		
		// playlist name, songTitle, artist, album
		assertTrue(library.addSongToPlaylist("My Playlist 1", "Daylight",
				"ColdPlay", "A Rush of Blood to the Head"));
		
		// Checks that playlist does exist and it's contents are as expected
		Optional<Playlist> playlist = library.searchPlaylistByTitle("My Playlist 1");
		assertTrue(playlist.isPresent());
		String[] observed = playlist.get().getPlaylistSongs();
		assertEquals(1, observed.length);
		
		String[] expected = new String[1];
		expected[0] = "Daylight, Coldplay, A Rush of Blood to the Head";
		assertEquals(expected[0], observed[0]);
		
		// Testing adding second song
		library.addSong(store, "one road to freedom", "ben harper", "fight for Your Mind");
		
		// playlist name, songTitle, artist, album
		assertTrue(library.addSongToPlaylist("My Playlist 1", "one road to Freedom",
				"ben harper", "fight for your mind"));
		
		playlist = library.searchPlaylistByTitle("My Playlist 1");
		assertTrue(playlist.isPresent());
		observed = playlist.get().getPlaylistSongs();
		assertEquals(2, observed.length);
		
		expected = new String[2];
		expected[0] = "Daylight, Coldplay, A Rush of Blood to the Head";
		expected[1] = "One Road to Freedom, Ben Harper, Fight for Your Mind";
		assertEquals(expected[0], observed[0]);
		assertEquals(expected[1], observed[1]);
	
	}
	
	@Test
	void testAddSongPlaylistDoesNotExist() {
		assertFalse(library.addSongToPlaylist("hello", "Daylight",
				"ColdPlay", "A Rush of Blood to the Head"));
	};
	
	@Test
	void testSearchPlaylistDoesNotExist() {
		Optional<Playlist> playlist = library.searchPlaylistByTitle("not a playlist");
		assertTrue(playlist.isEmpty());
	};
	
	@Test
	void testRemoveSongFromPlaylistEmpty() {
		library.addPlaylist("Empty playlist");
		assertFalse(library.removeSongFromPlaylist("Empty playlist", 0));
	}
	
	@Test
	void testRemoveSongFromPlaylistDoesNotExist() {
		assertFalse(library.removeSongFromPlaylist("Not a playlist", 0));
	}
	
	@Test
	void testRemoveSongFromPlaylistSuccess() {
		assertTrue(library.addPlaylist("My Playlist 1"));
		library.addSong(store, "Daylight", "Coldplay", "A Rush of Blood to the Head");
		
		// playlist name, songTitle, artist, album
		assertTrue(library.addSongToPlaylist("My Playlist 1", "Daylight",
				"ColdPlay", "A Rush of Blood to the Head"));
		
		Optional<Playlist> playlist = library.searchPlaylistByTitle("My Playlist 1");
		String[] observed = playlist.get().getPlaylistSongs();
		assertEquals(1, observed.length);
		
		String[] expected = new String[1];
		expected[0] = "Daylight, Coldplay, A Rush of Blood to the Head";
		assertEquals(expected[0], observed[0]);
		
		// REMOVE SONG
		assertTrue(library.removeSongFromPlaylist("My Playlist 1", 0));
		
		playlist = library.searchPlaylistByTitle("My Playlist 1");
		observed = playlist.get().getPlaylistSongs();
		assertEquals(0, observed.length);
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
  
	@Test
	void testSearchSongByGenre() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors")); // genre: Traditional Country
		assertTrue(library.addSong(store, "daydreamer" , "Adele", "19")); // genre: Pop
		
		Song expectedSong = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors", "Traditional Country");
		Song expectedSong2 = new Song("Daydreamer" , "Adele", "19", "Pop");
		assertEquals(expectedSong.toString(), library.searchSongByGenre("traditional country").get(0).toString());
		assertEquals(expectedSong2.toString(), library.searchSongByGenre("pop").get(0).toString());
	}
	
	@Test
	void testSearchSong() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors"));
		assertTrue(library.addSong(store, "daydreamer" , "Adele", "19")); 
		
		Song expectedSong = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors", "Traditional Country");
		assertEquals(expectedSong, library.searchSong("if I lose my mind", "dolly parton", "coat of Many Colors"));

	}
	@Test
	void testSearchSongDuplicate() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "lullaby" , "Leonard Cohen", "old ideas"));
		assertTrue(library.addSong(store, "lullaby" , "OneRepublic", "waking up"));
		
		Song expectedSong = new Song("Lullaby" , "OneRepublic", "Waking Up", "Rock");
		assertEquals(expectedSong, library.searchSong("Lullaby" , "OneRepublic", "Waking Up"));

	}

	@Test
	void testShuffleLibrarySongs() {
		// store, songTitle, artist, and album
		assertTrue(library.addSong(store, "If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors"));
		assertTrue(library.addSong(store, "My Blue Tears" , "Dolly Parton", "Coat of Many Colors"));
		assertTrue(library.addSong(store, "Traveling Man" , "Dolly Parton", "Coat of Many Colors"));
		
		Song expectedSong1 = new Song("If I Lose My Mind" , "Dolly Parton", "Coat of Many Colors", "Pop");
		Song expectedSong2 = new Song("My Blue Tears" , "Dolly Parton", "Coat of Many Colors", "Pop");
		Song expectedSong3 = new Song("Traveling Man" , "Dolly Parton", "Coat of Many Colors", "Pop");
		ArrayList<Song> expectedSongs = new ArrayList<Song>();
		expectedSongs.add(expectedSong1);
		expectedSongs.add(expectedSong2);
		expectedSongs.add(expectedSong3);
		
		library.shuffleLibrarySongs();
		
		assertTrue(library.getSongs().contains(expectedSong1), "the library should still have the same songs");
		assertTrue(library.getSongs().contains(expectedSong2), "the library should still have the same songs");
		assertTrue(library.getSongs().contains(expectedSong3), "the library should still have the same songs");
		assertFalse(library.getSongs().equals(expectedSongs), "the playlist should have new order");
    
	}
}
