package model;

import java.util.ArrayList;

public class Playlist {
	private String playlistTitle;
	private ArrayList<Song> songs;
	
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
	
	public void addSongToPlaylist(Song song) {
		songs.add(new Song(song));
	}
	
	@Override
	public String toString() {
		return playlistTitle;
	}
}
