package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RecentSongs extends Playlist {
	private Queue<Song> songs;
	
	public RecentSongs(String title) {
		super(title);
		songs = new LinkedList<Song>();
	}
	
	@Override
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
	
	/* Disable this method because this playlist is maintained automatically */
	@Override
	public boolean removeSong(int index) {
		return false;
	}
	
	/* Override this method to use songs variable created in this class */
	@Override
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
	
	/* Override this method to use songs variable created in this class */
	@Override
	public ArrayList<Song> getSongArray() {
		ArrayList<Song> songArray = new ArrayList<Song>();
		for (Song song: songs) {
			songArray.add(new Song(song));
		}
		return songArray;
	}
	
}
