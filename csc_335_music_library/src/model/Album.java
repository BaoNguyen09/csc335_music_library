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
	
	/* Copy constructor */
	public Album(Album anotherAlbum) {
		this.albumTitle = anotherAlbum.getAlbumTitle();
		this.artist = anotherAlbum.getArtist();
		this.genre = anotherAlbum.getGenre();
		this.year = anotherAlbum.getYear();
		this.songs = anotherAlbum.getSongArray();
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
	
	public ArrayList<Song> getSongArray() {
		ArrayList<Song> songArray = new ArrayList<Song>();
		for (Song song: songs) {
			songArray.add(new Song(song));
		}
		return songArray;
	}
	
	public void addSong(Song song) {
		songs.add(new Song(song));
	}
	
	// Helper method used in addAssociatedAlbum to determine if album contains a certain song
	public boolean containsSong(Song song) {
		for (Song songInAlbum: songs) {
			if (songInAlbum.equals(song)) {
				return true;
			}
		}
		return false;
		
	}

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
		return String.format("%s, %s, %s, %s", albumTitle, artist, genre, year);
	}
}
