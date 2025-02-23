package model;

import java.util.ArrayList;

public class Album {
	private String title;
	private String artist;
	private ArrayList<Song> songs;
	private String genre;
	private String year;
	
	public Album(String title, String artist, String genre, String year) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		songs = new ArrayList<Song>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getYear() {
		return year;
	}
	
	public String[] getSongs() {
//		ArrayList<String> songList = new ArrayList<String>();
		int songListLength = songs.size();
		String[] songList = new String[songListLength];
		for (int i = 0; i < songListLength; i++) {
			Song song = songs.get(i);
			String songTitle = song.getSongTitle();
			String songInfo = String.format("%s, %s, %s", songTitle, artist, title);
			songList[i] = songInfo;
		}
		return songList;
	}
}
