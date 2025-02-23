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
	void testToString() {
		Playlist playlist = new Playlist("My playlist");
		assertEquals("My playlist", playlist.toString(), "Playlist info should be the playlist title");
	}

}
