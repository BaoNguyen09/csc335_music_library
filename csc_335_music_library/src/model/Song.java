package model;

import java.util.Comparator;
import java.util.Objects;

public class Song {
	private String songTitle;
	private String artist;
	private String albumTitle;
	private String genre;
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
	private Integer streamCount;
	
	/* @pre songTitle != null & artist != null && albumTitle != null */
	public Song(String songTitle, String artist, String albumTitle, String genre) {
		this.songTitle = songTitle;
		this.artist = artist;
		this.albumTitle = albumTitle;
		this.genre = genre;
		rating = Rating.UNRATED;
		isFavorite = false;
		streamCount = 0;
	}
	
	/* Copy constructor */
	public Song(Song anotherSong) {
		this.songTitle = anotherSong.getSongTitle();
		this.artist = anotherSong.getArtist();
		this.albumTitle = anotherSong.getAlbumTitle();
		this.genre = anotherSong.getGenre();
		rating = anotherSong.getRating();
		isFavorite = anotherSong.isFavorite();
		streamCount = anotherSong.getStreamCount();
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
	
	public String getGenre() {
		return genre;
	}
	
	public Rating getRating() {
		return rating;
	}
	
	public boolean isFavorite() {
		return isFavorite;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
		if (rating == Rating.FIVE_STAR) {
			isFavorite = true;
		}
	}

	public void markAsFavorite() {
		isFavorite = true;
	}
	
	public Integer getStreamCount() {
		return streamCount;
	}
	
	public void updateStreamCount() {
		streamCount ++;
	}
	
	/* This function is for function that need to have Comparator to sort by title */
	public static Comparator<Song> titleFirstComparator() {
		return new Comparator<Song>(){
    		public int compare(Song song1, Song song2)
    		{
    			int comp = song1.getSongTitle().compareTo(song2.getSongTitle());
    			if (comp == 0) 
    				comp = song1.getArtist().compareTo(song2.getArtist());
    			return comp;
    		}
    	};
	}
	
	/* This function is for function that need to have Comparator to sort by artist */
	public static Comparator<Song> artistFirstComparator() {
		return new Comparator<Song>(){
    		public int compare(Song song1, Song song2)
    		{
    			int comp = song1.getArtist().compareTo(song2.getArtist());
    			if (comp == 0)
    				comp = song1.getSongTitle().compareTo(song2.getSongTitle());
    			return comp;
    		}
    	};
	}
	
	/* This function is for function that need to have Comparator to sort by rating */
	public static Comparator<Song> ratingFirstComparator() {
		return new Comparator<Song>(){
    		public int compare(Song song1, Song song2)
    		{
    			int comp = song1.getRating().compareTo(song2.getRating());
    			if (comp == 0) 
    				comp = song1.getSongTitle().compareTo(song2.getSongTitle());
    			return comp;
    		}
    	};
	}
	
	@Override
	public boolean equals(Object song) {
		if (song == null) return false;
		if (this == song) return true;
		if (this.getClass() != song.getClass()) return false;
		Song other = (Song) song;
		return this.getSongTitle().equals(other.getSongTitle())
				&& this.getAlbumTitle().equals(other.getAlbumTitle())
				&& this.getArtist().equals(other.getArtist());
	
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getSongTitle(), this.getAlbumTitle(), this.getArtist());
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s", songTitle, artist, albumTitle);
	}
    
}
