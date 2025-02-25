package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private String playlistTitle;
<<<<<<< HEAD
	private List<Song> songs;
=======
	private ArrayList<Song> songs;
	private static int defaultPlaylistCount = 1; // static counter for PlayList instances with no parameter
>>>>>>> branch '19-playlist---write-addsong-function' of https://github.com/BaoNguyen09/csc335_music_library.git
	
	/* @pre title != null */
	public Playlist(String title) {
		playlistTitle = title;
		songs = new ArrayList<Song>();
<<<<<<< HEAD
=======
	}
	
	/* Constructor to generate default title if no title is provided */
	public Playlist() {
		this.playlistTitle = "My playlist " + defaultPlaylistCount; // Default title with count
		this.songs = new ArrayList<>();
		defaultPlaylistCount++;
>>>>>>> branch '19-playlist---write-addsong-function' of https://github.com/BaoNguyen09/csc335_music_library.git
	}
	
	/* Copy constructor */
	public Playlist(Playlist anotherPlaylist) {
		this.playlistTitle = anotherPlaylist.getPlaylistTitle();
		this.songs = anotherPlaylist.getSongArray();
	}
	
	public String getPlaylistTitle() {
		return this.playlistTitle;
	}
	
	public String[] getPlaylistSongs() {
		int songListLength = songs.size();
		String[] songList = new String[songListLength];
		for (int i = 0; i < songListLength; i++) {
			Song song = songs.get(i);
			songList[i] = song.toString();
		}
		return songList;
	}
	
	public ArrayList<Song> getSongArray() {
		ArrayList<Song> songArray = new ArrayList<Song>();
		for (Song song: songs) {
			songArray.add(new Song(song));
		}
		return songArray;
	}
	
	public void addSongToPlaylist(Song song) {
		// Check if Song instances is duplicated (shallow check)
		if (!songs.contains(song)) {
			// Compare song's values with each other (deep check)
			for (Song songItem: songs) {
				if (songItem.getSongTitle().equals(song.getSongTitle())
						&& songItem.getAlbumTitle().equals(song.getAlbumTitle())
						&& songItem.getArtist().equals(song.getArtist())) {
					return; // stop if spot duplicated song
				}
			}
			songs.add(new Song(song));
		}
	}
	
	@Override
	public String toString() {
		return playlistTitle;
	}
}
