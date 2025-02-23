package model;

import java.util.ArrayList;

public class Album {
	private String albumTitle;
	private String artist;
	private ArrayList<Song> songs;
	private String genre;
	private String year;
	
	/* @pre albumTitle != null & artist != null && genre != null && year != null*/
	public Album(String title, String artist, String genre, String year) {
		this.albumTitle = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		songs = new ArrayList<Song>();
	}
	
	public String getAlbumTitle() {
		return albumTitle;
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
		int songListLength = songs.size();
		String[] songList = new String[songListLength];
		for (int i = 0; i < songListLength; i++) {
			Song song = songs.get(i);
			songList[i] = song.toString();
		}
		return songList;
	}
	
	public void addSong(Song song) {
		songs.add(new Song(song));
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s", albumTitle, artist, genre, year);
	}
}
