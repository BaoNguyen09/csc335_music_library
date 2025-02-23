package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;

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
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		
		Playlist playlist = new Playlist("My playlist");
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);

		String expectedSong1 = "Daydreamer, Adele, 19";
		String expectedSong2 = "Best for Last, Adele, 19";
		
		String[] expectedSongLists = new String[2];
		expectedSongLists[0] = expectedSong1;
		expectedSongLists[1] = expectedSong2;

		assertArrayEquals(expectedSongLists, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of 2 songs in format: title, artist, album");
		
		playlist.removeSong("Daydreamer");
		String[] expectedSongLists2 = new String[1];
		expectedSongLists2[0] = expectedSong2;
		assertArrayEquals(expectedSongLists2, playlist.getPlaylistSongs(), "getPlaylistSongs should return a list of 1 song in format: title, artist, album");
	}
	
	@Test
	void testToString() {
		Playlist playlist = new Playlist("My playlist");
		assertEquals("My playlist", playlist.toString(), "Playlist info should be the playlist title");
	}

}
