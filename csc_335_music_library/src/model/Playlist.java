package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
	private String playlistTitle;
	protected List<Song> songs;

	/* @pre title != null */
	public Playlist(String title) {
		playlistTitle = title;
		songs = new ArrayList<Song>();
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
	
	public void shuffleSongs() {
		Collections.shuffle(songs);
	}
	
	public void addSongToPlaylist(Song song) {
		// Check if Song instances is duplicated (deep check)
		if (!songs.contains(song)) {
			songs.add(new Song(song));
		}
	}
    
	/* Return boolean for View to check if song is successfully removed */
	public boolean removeSong(int index) {
		if (index >= 0 && index < songs.size()) {
			songs.remove(index);
			return true; // return a value for View to check if it's removed
		}
		return false; // if index isn't valid
	}
	
	/* Another removeSong method for playlist to remove a song object */
	public boolean removeSong(Song songToRemove) {
		for (Song song: songs) {
			if (song.equals(songToRemove)) {
				songs.remove(song);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return playlistTitle;
	}
}
