package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import database.MusicStore;

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
			songList[i] = songByTitle.get(songTitle).get(0).getSongTitle();
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
			albumList[i] = albumByTitle.get(albumTitle).get(0).getAlbumTitle();
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
	
	public String[] getFavoriteSongs() {
		int songListLength = favoriteSongs.size();
		String[] songList = new String[songListLength];
		for (int i = 0; i < songListLength; i++) {
			Song song = favoriteSongs.get(i);
			songList[i] = song.toString();
		}
		return songList;
	}
	
	/* Adds a specific song to the library.
	 * 
	 * @pre store != null, songTitle != null, artist != null, album != null
	 */
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
	
	
		/* Helper method to detect if the song already exist in the library.
		 * 
		 * @pre songTitle != null, artist != null, album != null
		 */
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
	
	/* Adds an album and all its songs to the library.
	 * 
	 * @pre songTitle != null, artist != null, album != null
	 */
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
	
	/* Helper method to detect if the album already exist in the library
	 * 
	 * @pre albumTitle != null, artist != null
	 */
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
	
	/* Adds a playlist to the library. Title must match exactly and is case sensitive.
	 * 
	 * @pre playlistTitle
	 */
	public boolean addPlaylist(String playlistTitle) {
		// Search playlist to make sure it doesn't exist
		if (playlistByTitle.get(playlistTitle) != null) {
			return false;
		}
		// Create playlist and add it to playlists and hashmap
		Playlist newPlaylist = new Playlist(playlistTitle);
		playlists.add(newPlaylist);
		playlistByTitle.put(playlistTitle, newPlaylist);
		return true;
		
	}
	
	
	/* Adds a specific song to a specified playlist.
	 * 
	 * @pre playlistName != null, songTitle != null, artist != null, album != null
	 */
	public boolean addSongToPlaylist(String playlistName, String songTitle, String artist, String album) {
		// Search playlist to make sure it exists
		Playlist playlist = playlistByTitle.get(playlistName);
		if (playlist == null) {
			return false;
		} 
		
		// Only add songs that are in the library
		if (!this.containsSong(songTitle, artist, album)) {
			return false;
		}
		
		
		List<Song> songsWithTitle = songByTitle.get(songTitle.toUpperCase());
		for (Song song : songsWithTitle) {
			if (song.getArtist().equalsIgnoreCase(artist) &&
					song.getAlbumTitle().equalsIgnoreCase(album)) {
				
					// add song to song to playlist list
					playlists.add(playlist);
					// add song to playlist object
					playlist.addSongToPlaylist(song);
					return true;
			}			
		}
		return false;
		
	}
	
	// Search functions
	
	/* 
	 * @pre songTitle != null
	 */
	public List<Song> searchSongByTitle(String songTitle) {
		List<Song> songsWithTitle = songByTitle.get(songTitle.toUpperCase());
		return copySongsList(songsWithTitle);
	}
	
	/* 
	 * @pre artist != null
	 */
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
	
	
	/* 
	 * @pre albumTitle != null
	 */
	public List<Album> searchAlbumByTitle(String albumTitle) {
		List<Album> albumsWithTitle = albumByTitle.get(albumTitle.toUpperCase());
		return copyAlbumsList(albumsWithTitle);
	}
	
	/* 
	 * @pre artist != null
	 */
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
	
	/* This method searches for the playlist object based on the playlist title.
	 * If such a playlist does not exist it would return isempty optional
	 * variable, else it returns the actual playlist object.
	 * 
	 * @pre playlistTitle != null
	 */
	public Optional<Playlist> searchPlaylistByTitle(String playlistTitle) {
		Playlist playlist = playlistByTitle.get(playlistTitle);
		if (playlist == null){
			return Optional.empty();
		}		
		return Optional.of(new Playlist(playlist));
	}
	
	
	// Function adds to the respective map
	private <K, V> void addToMapList(Map<K, List<V>> map, K key, V value) {
		
		// if the key already exist then it just adds the value to the list
		// else it initializes a list for that key value with that value added
	    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
}
