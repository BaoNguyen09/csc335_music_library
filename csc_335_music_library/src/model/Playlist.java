package model;

import java.util.ArrayList;

public class Playlist {
	private String playlistTitle;
	private ArrayList<Song> songs;
	
	public Playlist(String title) {
		playlistTitle = title;
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
	
	@Override
	public String toString() {
		return playlistTitle;
	}
}
