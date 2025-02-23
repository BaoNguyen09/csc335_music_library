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
	void testGetSongsAndAddSong() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		Song song3 = new Song("Tired", "Adele", "19");
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
	void testGetSongArray() {
		Song song1 = new Song("Daydreamer", "Adele", "19");
		Song song2 = new Song("Best for Last", "Adele", "19");
		Song song3 = new Song("Tired", "Adele", "19");
		
		Playlist playlist = new Playlist("My playlist");
		playlist.addSongToPlaylist(song1);
		playlist.addSongToPlaylist(song2);
		playlist.addSongToPlaylist(song3);
		
		ArrayList<Song> expectedSongList = new ArrayList<Song>();
		expectedSongList.add(song1);
		expectedSongList.add(song2);
		expectedSongList.add(song3);
		
		assertIterableEquals(expectedSongList, playlist.getSongArray(), "getSongArray should return a list of Song objects");		
	}
	
	@Test
	void testToString() {
		Playlist playlist = new Playlist("My playlist");
		assertEquals("My playlist", playlist.toString(), "Playlist info should be the playlist title");
	}

}
