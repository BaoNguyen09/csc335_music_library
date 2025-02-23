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
	private boolean isFavorite;
	
	public Song(String songTitle, String artist, String albumTitle) {
		this.songTitle = songTitle;
		this.artist = artist;
		this.albumTitle = albumTitle;
		rating = Rating.UNRATED;
		isFavorite = false;
	}
	
	public String getSongTitle() {
		return songTitle;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbumTitle() {
		return albumTitle;
	}
	
	public Rating getRating() {
		return rating;
	}
	
	public boolean isFavorite() {
		return isFavorite;
	}
	
	public void markAsFavorite() {
		isFavorite = true;
	}
}
