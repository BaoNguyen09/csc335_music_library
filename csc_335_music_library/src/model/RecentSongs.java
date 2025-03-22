package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RecentSongs{
	private Queue<Song> songs;
	
	public RecentSongs() {
		songs = new LinkedList<Song>();
	}
	
	
	public void addSongToPlaylist(Song song) {
		// Check if Song instances is already added (deep check)
		if (!songs.contains(song)) {
			// remove least recently played song before adding new one
			if (songs.size() >= 10) songs.remove();
		} 
		
		if (songs.contains(song)) {
			// remove the existing song and add them back to show that 
			// it's most recently played song
			songs.remove(song);
		}
		
		songs.add(new Song(song));
	}
	
	/* Return boolean to check if song is successfully removed */
	public boolean removeSong(Song song) {
		if (songs.contains(song)) {
			songs.remove(song);
			return true; // return a value to check if it's removed
		}
		return false; // if index isn't valid
	}
	
	public String[] getPlaylistSongs() {
		int songListLength = songs.size();
		String[] songList = new String[songListLength];
		int index = 0;
		for (Song song: songs) {
			songList[index] = song.toString();
			index ++;
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
	
}
