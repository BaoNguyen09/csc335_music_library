package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.MusicStore;

public class LibraryModel {
	private Map<String, List<Song>> songByTitle;
	private Map<String, List<Song>> songByArtist;
	private Map<String, List<Album>> albumByTitle;
	private Map<String, List<Album>> albumByArtist;
	
	public LibraryModel() {
		songByTitle = new HashMap<String, List<Song>>();
		songByArtist = new HashMap<String, List<Song>>();
		albumByTitle = new HashMap<String, List<Album>>();
		albumByArtist = new HashMap<String, List<Album>>();
	}
	
	public boolean addSong(MusicStore store, String songTitle, String artist, String album) {
		// prevents same song from being added twice
		if (this.containsSong(songTitle, artist, album)) {
			return false;
		}
		List<Song> songsWithTitle = store.searchSongByTitle(songTitle);
		for (Song song : songsWithTitle) {
			if (song.getArtist().equalsIgnoreCase(artist) &&
					song.getAlbumTitle().equalsIgnoreCase(album)) {
					// add song to songs hashmap
					addToMapList(songByTitle, songTitle.toUpperCase(), new Song(song));
					addToMapList(songByArtist, artist.toUpperCase(), new Song(song));
					return true;
			}			
		}
		return false;
		
	}
	
	// Helper method to detect if the song already exist in the library
		private boolean containsSong(String songTitle, String artist, String album) {
			List<Song> songsWithTitleInLib = songByTitle.get(songTitle.toUpperCase());
			if (songsWithTitleInLib == null) {
				return false;
			}
			for (Song song: songsWithTitleInLib) {
				if (song.getArtist().equalsIgnoreCase(artist) && 
						song.getAlbumTitle().equalsIgnoreCase(album)) {
					return true;
				}
			}
			return false;
			
		}
	
	public boolean addAlbum(MusicStore store, String albumTitle, String artist) {
		// prevents same album from being added twice
		if (this.containsAlbum(albumTitle, artist)) {
			return false;
		}
		
		List<Album> albumWithTitle = store.searchAlbumByTitle(albumTitle);
		for (Album album : albumWithTitle) {
			if (album.getArtist().equalsIgnoreCase(artist)) {
			
					// add album to album hashmaps
					addToMapList(albumByTitle, albumTitle.toUpperCase(), new Album(album));
					addToMapList(albumByArtist, artist.toUpperCase(), new Album(album));
					
					// add all songs from album
					for (Song song: album.getSongArray()) {
						this.addSong(store, song.getSongTitle(), song.getArtist(), song.getAlbumTitle());
					}
					
					return true;
			}			
		}
		return false;
	}
	
	// Helper method to detect if the album already exist in the library
	private boolean containsAlbum(String albumTitle, String artist) {
		List<Album> albumWithTitleInLib = albumByTitle.get(albumTitle.toUpperCase());
		if (albumWithTitleInLib == null) {
			return false;
		}
		for (Album album: albumWithTitleInLib) {
			if (album.getArtist().equalsIgnoreCase(artist)) {
				return true;
			}
		}
		return false;
		
	}
	
	// Search functions
	
	public List<Song> searchSongByTitle(String songTitle) {
		List<Song> songsWithTitle = songByTitle.get(songTitle.toUpperCase());
		return copySongsList(songsWithTitle);
	}
	
	public List<Song> searchSongByArtist(String artist) {
		List<Song> songsWithArtist = songByArtist.get(artist.toUpperCase());
		return copySongsList(songsWithArtist);
	}
	
	private List<Song> copySongsList(List<Song> songsList){
		if (songsList == null) {
			return new ArrayList<Song>();
		}
		List<Song> listCopy = new ArrayList<Song>();
		
		for (int i= 0; i < songsList.size(); i++) {
			Song songCopy = new Song(songsList.get(i));
			listCopy.add(songCopy);
		}
		return listCopy;
	}
	
	
	public List<Album> searchAlbumByTitle(String albumTitle) {
		List<Album> albumsWithTitle = albumByTitle.get(albumTitle.toUpperCase());
		return copyAlbumsList(albumsWithTitle);
	}
	
	public List<Album> searchAlbumByArtist(String artist) {
		List<Album> albumsWithArtist = albumByArtist.get(artist.toUpperCase());
		return copyAlbumsList(albumsWithArtist);
	}
	
	
	private List<Album> copyAlbumsList(List<Album> albumsList){
		if (albumsList == null) {
			return new ArrayList<Album>();
		}
		
		List<Album> listCopy = new ArrayList<Album>();
		for (int i= 0; i < albumsList.size(); i++) {
			Album albumCopy = new Album(albumsList.get(i));
			listCopy.add(albumCopy);
		}
		return listCopy;
	}
	
	
	// Function adds to the respective map
	private <K, V> void addToMapList(Map<K, List<V>> map, K key, V value) {
		
		// if the key already exist then it just adds the value to the list
		// else it initializes a list for that key value with that value added
	    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
}
