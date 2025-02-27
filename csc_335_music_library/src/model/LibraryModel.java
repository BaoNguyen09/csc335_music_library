package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryModel {
	
	List<Playlist> playlists;
	List<Song> favoriteSongs;
	private Map<String, List<Song>> songByTitle;
	private Map<String, List<Song>> songByArtist;
	private Map<String, List<Album>> albumByTitle;
	private Map<String, List<Album>> albumByArtist;
	private Map<String, Playlist> playlistByTitle;
	
	public LibraryModel() {
		playlists = new ArrayList<Playlist>();
		favoriteSongs = new ArrayList<Song>();
		songByTitle = new HashMap<String, List<Song>>();
		songByArtist = new HashMap<String, List<Song>>();
		albumByTitle = new HashMap<String, List<Album>>();
		albumByArtist = new HashMap<String, List<Album>>();
		playlistByTitle = new HashMap<String, Playlist>();
	}
	
	public String[] getSongTitles() {
		int songListLength = songByTitle.size();
		String[] songList = new String[songListLength];
		int i = 0;
		for (String songTitle: songByTitle.keySet()) {
			songList[i] = songTitle;
			i++;
		}
		return songList;
	}
	
	public String[] getArtists() {
		int artistListLength = songByArtist.size();
		String[] artistList = new String[artistListLength];
		int i = 0;
		for (String artist: songByArtist.keySet()) {
			artistList[i] = capitalizeFirstLetter(artist.toLowerCase());
			i++;
		}
		return artistList;
	}
	
	/* This helper method capitalize first letter of each word used for all-lowercase artist name */
	private String capitalizeFirstLetter(String word) {
		if (word == null || word.isEmpty()) {
            return word;
        }
		String result = "";
		String[] words = word.split(" ");
		
		for (String w : words) {
            if (!w.isEmpty()) {
                result += Character.toUpperCase(w.charAt(0));
                result += w.substring(1);
                result += " ";
            }
        }
        return result.trim(); // Remove trailing space
	}
	
	public String[] getAlbumTitles() {
		int albumListLength = albumByTitle.size();
		String[] albumList = new String[albumListLength];
		int i = 0;
		for (String albumTitle: albumByTitle.keySet()) {
			albumList[i] = albumTitle;
			i++;
		}
		return albumList;
	}
	
	public String[] getPlaylistTitles() {
		int playlistListLength = playlistByTitle.size();
		String[] playlistList = new String[playlistListLength];
		int i = 0;
		for (String playlistTitle: playlistByTitle.keySet()) {
			// get playlist title from Playlist object to preserve capitalization
			playlistList[i] = playlistByTitle.get(playlistTitle).toString();
			i++;
		}
		return playlistList;
	}
}
