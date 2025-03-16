package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Playlist;
import model.RecentSongs;
import model.Song;

class RecentSongsTests {

	@Test
	void testGetPlaylistTitle() {
		RecentSongs playlist = new RecentSongs();
		assertEquals("Most Recently Played Songs", playlist.getPlaylistTitle(), "Playlist Title should be: Most Recently Played Songs");
	}
	
	@Test
	void testGetSongsAndAddSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		Song song3 = new Song("Tired", "Adele", "19");
		RecentSongs playlist = new RecentSongs();
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);
		playlist.addSongToPlaylist(song3);
				
		String expectedSong1 = "Daydreamer, Adele, 19";
		String expectedSong2 = "Best for Last, Adele, 19";
		String expectedSong3 = "Tired, Adele, 19";
		String[] expectedSongLists = new String[3];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;
		expectedSongLists[2] = expectedSong3;
		
		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of song in format: title, artist, album");		
	}
	
	@Test
	void testAddDuplicatedSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Daydreamer", "Adele", "19");
		Song song3 = new Song("Daydreamer", "Bruno Mars", "19");
		
		RecentSongs playlist = new RecentSongs();
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song3);
		playlist.addSongToPlaylist(song2); // duplicated song values
		playlist.addSongToPlaylist(song1); // duplicated Song object
		
		String expectedSong1 = "Daydreamer, Bruno Mars, 19";
		String expectedSong2 = "Daydreamer, Adele, 19";
		String[] expectedSongLists = new String[2];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;
		
		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "This should return a list of songs in the ordered they are recently added");		
	}
	
	@Test
	void testAddMoreThan10Songs() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		Song song3 = new Song("Tired", "Adele", "19");
		Song song4 = new Song("Hold On", "Alabama Shakes", "Boys & Girls");
		Song song5 = new Song("I Found You", "Alabama Shakes", "Boys & Girls");
		Song song6 = new Song("Hang Loose", "Alabama Shakes", "Boys & Girls");
		Song song7 = new Song("Rise to the Sun", "Alabama Shakes", "Boys & Girls");
		Song song8 = new Song("You Ain't Alone", "Alabama Shakes", "Boys & Girls");
		Song song9 = new Song("Goin' to the Party", "Alabama Shakes", "Boys & Girls");
		Song song10 = new Song("Heartbreaker", "Alabama Shakes", "Boys & Girls");
		Song song11 = new Song("Boys & Girls", "Alabama Shakes", "Boys & Girls");

		RecentSongs playlist = new RecentSongs();
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);
		playlist.addSongToPlaylist(song3);
		playlist.addSongToPlaylist(song4);
		playlist.addSongToPlaylist(song5);
		playlist.addSongToPlaylist(song6);
		playlist.addSongToPlaylist(song7);
		playlist.addSongToPlaylist(song8);
		playlist.addSongToPlaylist(song9);
		playlist.addSongToPlaylist(song10);
				
		String expectedSong1 = "Daydreamer, Adele, 19";
		String expectedSong2 = "Best for Last, Adele, 19";
		String expectedSong3 = "Tired, Adele, 19";
		String expectedSong4 = "Hold On, Alabama Shakes, Boys & Girls";
		String expectedSong5 = "I Found You, Alabama Shakes, Boys & Girls";
		String expectedSong6 = "Hang Loose, Alabama Shakes, Boys & Girls";
		String expectedSong7 = "Rise to the Sun, Alabama Shakes, Boys & Girls";
		String expectedSong8 = "You Ain't Alone, Alabama Shakes, Boys & Girls";
		String expectedSong9 = "Goin' to the Party, Alabama Shakes, Boys & Girls";
		String expectedSong10 = "Heartbreaker, Alabama Shakes, Boys & Girls";
		String expectedSong11 = "Boys & Girls, Alabama Shakes, Boys & Girls";

		String[] expectedSongLists = new String[10];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;
		expectedSongLists[2] = expectedSong3;
		expectedSongLists[3] = expectedSong4;
		expectedSongLists[4] = expectedSong5;
		expectedSongLists[5] = expectedSong6;
		expectedSongLists[6] = expectedSong7;
		expectedSongLists[7] = expectedSong8;
		expectedSongLists[8] = expectedSong9;
		expectedSongLists[9] = expectedSong10;
		
		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of songs in the order they are added");		
		
		playlist.addSongToPlaylist(song9); // now this song will be added to the end, showing it's the most recently added song
		expectedSongLists[8] = expectedSong10;
		expectedSongLists[9] = expectedSong9;
		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of songs in the order they are added");		
		
		playlist.addSongToPlaylist(song11); // when this song is added, the least recently added song will be removed to maintain size of 10
		expectedSongLists[0] = expectedSong2;
		expectedSongLists[1] = expectedSong3;
		expectedSongLists[2] = expectedSong4;
		expectedSongLists[3] = expectedSong5;
		expectedSongLists[4] = expectedSong6;
		expectedSongLists[5] = expectedSong7;
		expectedSongLists[6] = expectedSong8;
		expectedSongLists[7] = expectedSong10;
		expectedSongLists[8] = expectedSong9;
		expectedSongLists[9] = expectedSong11;
		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of songs in the order they are added and maintain length of <= 10");		
		
	}
	
	@Test
	void testGetSongArray() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		Song song3 = new Song("Tired", "Adele", "19");
		
		RecentSongs playlist = new RecentSongs();
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);
		playlist.addSongToPlaylist(song3);
		
		ArrayList<Song> expectedSongList = new ArrayList<Song>();
		expectedSongList.add(song1);
		expectedSongList.add(song2);
		expectedSongList.add(song3);
		
		assertEquals(expectedSongList.toString(), playlist.getSongArray().toString(), "getSongArray should return a list of Song objects");		
	}
	
	@Test
	void testToString() {
		RecentSongs playlist = new RecentSongs();
		assertEquals("Most Recently Played Songs", playlist.toString(), "Playlist info should be the playlist title");
	}

}
