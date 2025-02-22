package model;

public class Song {
	private String songTitle;
	private String artist;
	private String albumTitle;
	public enum Rating {
		UNRATED,
		ONE_STAR,
		TWO_STAR,
		THREE_STAR,
		FOUR_STAR,
		FIVE_STAR
	};
	private Rating rating;
	
	public Song(String songTitle, String artist, String albumTitle) {
		this.songTitle = songTitle;
		this.artist = artist;
		this.albumTitle = albumTitle;
	}
}
