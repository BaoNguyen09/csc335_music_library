package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.GenrePlaylists;
import model.Song;

import java.util.*;

public class GenrePlaylistsTests {
    private GenrePlaylists genrePlaylists;
    private Song song1, song2, song3, song4;
    
    @Before
    public void setUp() {
        genrePlaylists = new GenrePlaylists();
        song1 = new Song("Song One", "Artist A", "Album X", "Rock");
        song2 = new Song("Song Two", "Artist B", "Album Y", "Rock");
        song3 = new Song("Song Three", "Artist C", "Album Z", "Pop");
        song4 = new Song("Song Four", "Artist D", "Album W", "Jazz");
    }
    
    @Test
    public void testAddSong_NewGenre() {
        genrePlaylists.addSong(song1);
        assertFalse(genrePlaylists.getGenrePlaylist().containsKey("rock"));
    }
    
    @Test
    public void testRemoveSong_ExistingSong() {
        genrePlaylists.addSong(song1);
        for (int i = 0; i < 9; i++) {
            genrePlaylists.addSong(new Song("Song " + i, "Artist", "Album", "Rock"));
        }
        assertTrue(genrePlaylists.getGenrePlaylist().containsKey("rock"));
        assertTrue(genrePlaylists.removeSong(song1));
        assertFalse(genrePlaylists.getGenrePlaylist().containsKey("rock"));
    }
    
    @Test
    public void testRemoveSong_NonExistentSong() {
        assertFalse(genrePlaylists.removeSong(song1));
    }
    
    @Test
    public void testGetGenrePlaylist_WithEnoughSongs() {
        for (int i = 0; i < 10; i++) {
            genrePlaylists.addSong(new Song("Song " + i, "Artist", "Album", "Rock"));
        }
        assertTrue(genrePlaylists.getGenrePlaylist().containsKey("rock"));
    }
    
    @Test
    public void testGetGenrePlaylist_NotEnoughSongs() {
        genrePlaylists.addSong(song1);
        assertFalse(genrePlaylists.getGenrePlaylist().containsKey("rock"));
    }
}

