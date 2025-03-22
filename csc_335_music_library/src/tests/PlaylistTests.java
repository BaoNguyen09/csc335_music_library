package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Playlist;
import model.Song;

class PlaylistTests {

	@Test
	void testGetPlaylistTitle() {
		Playlist playlist = new Playlist("My playlist");
		assertEquals("My playlist", playlist.getPlaylistTitle(), "Playlist Title should be: My playlist");
	}
	
	@Test
	void testRemoveSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		
		Playlist playlist = new Playlist("My playlist");
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);

		String expectedSong1 = "Daydreamer, Adele, 19";
		String expectedSong2 = "Best for Last, Adele, 19";
		
		String[] expectedSongLists = new String[2];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;

		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of 2 songs in format: title, artist, album");
		
		playlist.removeSong(0); // remove "Daydreamer"
		boolean removeStatus = playlist.removeSong(4); // remove song not in playlist
		String[] expectedSongLists2 = new String[1];
		expectedSongLists2[0] = expectedSong2;
		assertEquals(false, removeStatus, "If song isn't in the playlist, return null");
		assertArrayEquals(expectedSongLists2, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of 1 song in format: title, artist, album");
	}

	void testGetSongsAndAddSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song song3 = new Song("Tired", "Adele", "19", "Pop");
		Playlist playlist = new Playlist("My playlist");
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
	void testAddSongDuplicatedSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song3 = new Song("Daydreamer", "Bruno Mars", "19", "Pop");
		
		Playlist playlist = new Playlist("My playlist");
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song1); // duplicated Song object
		playlist.addSongToPlaylist(song2); // duplicated song values
		playlist.addSongToPlaylist(song3);
		
		String expectedSong1 = "Daydreamer, Adele, 19";
		String expectedSong2 = "Daydreamer, Bruno Mars, 19";
		String[] expectedSongLists = new String[2];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;
		
		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of 2 songs in format: title, artist, album");		
	}
	
	@Test
	void testGetSongArray() {
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song song3 = new Song("Tired", "Adele", "19", "Pop");
		
		Playlist playlist = new Playlist("My playlist");
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
		Playlist playlist = new Playlist("My playlist");
		assertEquals("My playlist", playlist.toString(), "Playlist info should be the playlist title");
	}
	
	@Test
	void testRemoveSong2MethodTrue() {
		Playlist playlist = new Playlist("My playlist");
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song songToRemove = new Song("Daydreamer", "Adele", "19", "Pop");

		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);
		assertTrue(playlist.removeSong(songToRemove));
		ArrayList<Song> songsInPlaylist = playlist.getSongArray();
		assertEquals("[Best for Last, Adele, 19]", songsInPlaylist.toString());
		

	}
	
	@Test
	void testRemoveSong2MethodFalse() {
		Playlist playlist = new Playlist("My playlist");
		Song song1 = new Song("Daydreamer", "Adele", "19", "Pop");
		Song song2 = new Song("Best for Last", "Adele", "19", "Pop");
		Song songToRemove = new Song("NotInPlaylist", "Adele", "19", "Pop");

		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);
		assertFalse(playlist.removeSong(songToRemove));
		ArrayList<Song> songsInPlaylist = playlist.getSongArray();
		assertEquals("[Daydreamer, Adele, 19, Best for Last, Adele, 19]", songsInPlaylist.toString());
		

	}

}
