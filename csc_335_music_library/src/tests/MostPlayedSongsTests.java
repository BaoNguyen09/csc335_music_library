package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.Song;
import model.MostPlayedSongs;
import model.RecentSongs;

public class MostPlayedSongsTests {

    MostPlayedSongs mostPlayedSongs;
    
    // Create some test songs
    Song song1, song2, song3, song4, song5, song6, song7, song8, song9, song10, song11;
    
    @BeforeEach
    public void setUp() {
        mostPlayedSongs = new MostPlayedSongs();
        
        // Create songs with stream counts for testing
        song1 = new Song("Song 1", "Artist 1", "Album 1", "Pop");
        song1.updateStreamCount(); // stream count = 1
        song2 = new Song("Song 2", "Artist 2", "Album 2", "Pop");
        song2.updateStreamCount(); // stream count = 1
        song3 = new Song("Song 3", "Artist 3", "Album 3", "Pop");
        song3.updateStreamCount(); // stream count = 1
        song4 = new Song("Song 4", "Artist 4", "Album 4", "Pop");
        song4.updateStreamCount(); // stream count = 1
        song5 = new Song("Song 5", "Artist 5", "Album 5", "Pop");
        song5.updateStreamCount(); // stream count = 1
        song6 = new Song("Song 6", "Artist 6", "Album 6", "Pop");
        song6.updateStreamCount(); // stream count = 1
        song7 = new Song("Song 7", "Artist 7", "Album 7", "Pop");
        song7.updateStreamCount(); // stream count = 1
        song8 = new Song("Song 8", "Artist 8", "Album 8", "Pop");
        song8.updateStreamCount(); // stream count = 1
        song9 = new Song("Song 9", "Artist 9", "Album 9", "Pop");
        song9.updateStreamCount(); // stream count = 1
        song10 = new Song("Song 10", "Artist 10", "Album 10", "Pop");
        song10.updateStreamCount(); // stream count = 1
        song11 = new Song("Song 11", "Artist 11", "Album 11", "Pop");
        song11.updateStreamCount(); // stream count = 1
    }
    
    @Test
	void testGetPlaylistTitle() {
		MostPlayedSongs playlist = new MostPlayedSongs();
		assertEquals("Most Played Songs Playlist", playlist.getPlaylistTitle(), "Playlist Title should be: Most Played Songs Playlist");
	}
    
    @Test
    public void testAddSongToPlaylist() {
        // Add song to playlist and check if it's added correctly
        mostPlayedSongs.addSongToPlaylist(song1);
        assertEquals(1, mostPlayedSongs.getSongArray().size());
        assertTrue(mostPlayedSongs.getSongArray().contains(song1));
        
        // Add another song
        mostPlayedSongs.addSongToPlaylist(song2);
        assertEquals(2, mostPlayedSongs.getSongArray().size());
        assertTrue(mostPlayedSongs.getSongArray().contains(song2));
        
        // Add a song with lower stream count than the current lowest, should be added since the list size still <= 10
        mostPlayedSongs.addSongToPlaylist(song3);
        assertEquals(3, mostPlayedSongs.getSongArray().size()); // should be added
        
        // Add a song with higher stream count
        mostPlayedSongs.addSongToPlaylist(song4);
        assertEquals(4, mostPlayedSongs.getSongArray().size());
        assertTrue(mostPlayedSongs.getSongArray().contains(song4));
    }

    @Test
    public void testPlaylistSizeLimit() {
        // Add 11 songs, playlist should only have 10 after this
        mostPlayedSongs.addSongToPlaylist(song1);
        mostPlayedSongs.addSongToPlaylist(song2);
        mostPlayedSongs.addSongToPlaylist(song3);
        mostPlayedSongs.addSongToPlaylist(song4);
        mostPlayedSongs.addSongToPlaylist(song5);
        mostPlayedSongs.addSongToPlaylist(song6);
        mostPlayedSongs.addSongToPlaylist(song7);
        mostPlayedSongs.addSongToPlaylist(song8);
        mostPlayedSongs.addSongToPlaylist(song9);
        mostPlayedSongs.addSongToPlaylist(song10);
        
        // Before adding song 11, the playlist should have 10 songs
        assertEquals(10, mostPlayedSongs.getSongArray().size());
        
        // Now add song 11
        mostPlayedSongs.addSongToPlaylist(song11);
        
        // The playlist size should still be 10, and song 1 (lowest stream count) should be removed
        assertEquals(10, mostPlayedSongs.getSongArray().size());
        assertFalse(mostPlayedSongs.getSongArray().contains(song1)); // song 1 should be removed
    }

    @Test
    public void testSongOrderAfterAdd() {
        // Add songs with varying stream counts
        song1.updateStreamCount(); // stream count = 2
        song2.updateStreamCount();
        song2.updateStreamCount(); // stream count = 3
        song3.updateStreamCount();
        song3.updateStreamCount();
        song3.updateStreamCount(); // stream count = 4
        mostPlayedSongs.addSongToPlaylist(song1);
        mostPlayedSongs.addSongToPlaylist(song2);
        mostPlayedSongs.addSongToPlaylist(song3);
        
        // After adding, the songs should be sorted in order of stream count, ascending
        assertEquals(song3, mostPlayedSongs.getSongArray().get(2));
        assertEquals(song2, mostPlayedSongs.getSongArray().get(1));
        assertEquals(song1, mostPlayedSongs.getSongArray().get(0));
    }

    @Test
    public void testSortOrderWithTiedStreamCounts() {
        // Add multiple songs with the same stream count
        mostPlayedSongs.addSongToPlaylist(song1);
        mostPlayedSongs.addSongToPlaylist(song2);
        mostPlayedSongs.addSongToPlaylist(song3);
        
        // Check the order after sorting
        // Since they all have the same stream count, the order should depend on insertion order
        assertEquals(song1, mostPlayedSongs.getSongArray().get(0));
        assertEquals(song2, mostPlayedSongs.getSongArray().get(1));
        assertEquals(song3, mostPlayedSongs.getSongArray().get(2));
    }

    @Test
    public void testRemoveSongIfPlaylistExceedsLimit() {
        // Add 11 songs
    	song1.updateStreamCount(); // stream count = 2
        mostPlayedSongs.addSongToPlaylist(song1);
        mostPlayedSongs.addSongToPlaylist(song2);
        mostPlayedSongs.addSongToPlaylist(song3);
        mostPlayedSongs.addSongToPlaylist(song4);
        mostPlayedSongs.addSongToPlaylist(song5);
        mostPlayedSongs.addSongToPlaylist(song6);
        mostPlayedSongs.addSongToPlaylist(song7);
        mostPlayedSongs.addSongToPlaylist(song8);
        mostPlayedSongs.addSongToPlaylist(song9);
        mostPlayedSongs.addSongToPlaylist(song10);
        mostPlayedSongs.addSongToPlaylist(song11);
        
        // Playlist should have 10 songs, and the song with the lowest stream count (song2) should be removed
        assertEquals(10, mostPlayedSongs.getSongArray().size());
        assertFalse(mostPlayedSongs.getSongArray().contains(song2)); // song 1 should be removed
    }

    @Test
    public void testSongEquality() {
        // Create a copy of song1 and add it to playlist
        Song song1Copy = new Song(song1);
        mostPlayedSongs.addSongToPlaylist(song1Copy);
        
        // Check that the playlist size remains 1 (no duplicates allowed)
        assertEquals(1, mostPlayedSongs.getSongArray().size());
        assertTrue(mostPlayedSongs.getSongArray().contains(song1)); // original song should still be there
        assertTrue(mostPlayedSongs.getSongArray().contains(song1Copy)); // the copy should also be added
    }

    @Test
    public void testAddDuplicateSong() {
        // Add the same song twice (same title, artist, album)
        mostPlayedSongs.addSongToPlaylist(song1);
        mostPlayedSongs.addSongToPlaylist(song1);
        
        // The playlist size should remain 1 (no duplicates allowed)
        assertEquals(1, mostPlayedSongs.getSongArray().size());
    }
}
