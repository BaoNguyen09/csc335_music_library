package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import database.MusicStore;
import model.Song.Rating;

public class LibraryModel {
  
	private List<Playlist> playlists;
	private List<Song> favoriteSongs;
	private List<Song> topRatedSongs;
	private List<Song> songs;
	private Map<String, List<Song>> songByTitle;
	private Map<String, List<Song>> songByArtist;
	private Map<String, List<Song>> songByGenre;
	private Map<String, List<Album>> albumByTitle;
	private Map<String, List<Album>> albumByArtist;
	private Map<String, Playlist> playlistByTitle;
	private MostPlayedSongs mostPlayedSongs;
	private RecentSongs recentSongs;
	
	public LibraryModel() {
		playlists = new ArrayList<Playlist>();
		favoriteSongs = new ArrayList<Song>();
		topRatedSongs = new ArrayList<Song>();
		songByTitle = new HashMap<String, List<Song>>();
		songByArtist = new HashMap<String, List<Song>>();
		songByGenre = new HashMap<String, List<Song>>();
		albumByTitle = new HashMap<String, List<Album>>();
		albumByArtist = new HashMap<String, List<Album>>();
		playlistByTitle = new HashMap<String, Playlist>();
		songs = new ArrayList<Song>();
		mostPlayedSongs = new MostPlayedSongs();
		recentSongs = new RecentSongs();
	}
	
	// copy constructor for the libraryModel
	public LibraryModel(LibraryModel otherLibrary) {
		playlists = otherLibrary.playlists;
		favoriteSongs = otherLibrary.favoriteSongs;
		topRatedSongs = otherLibrary.topRatedSongs;
		songByTitle = otherLibrary.songByTitle;
		songByArtist =  otherLibrary.songByArtist;
		songByGenre = otherLibrary.songByGenre;
		albumByTitle = otherLibrary.albumByTitle;
		albumByArtist = otherLibrary.albumByArtist;
		playlistByTitle = otherLibrary.playlistByTitle;
		songs = otherLibrary.songs;
		mostPlayedSongs = otherLibrary.mostPlayedSongs;
		recentSongs = otherLibrary.recentSongs;
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
	
	// Get all details of each song in songs list
	public List<Song> getSongs() {
	    return new ArrayList<>(songs);
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
	
	public String[] getSongRatings() {
		int songListLength = songs.size();
		String[] songList = new String[songListLength];
		int i = 0;
		for (Song song: songs) {
			String title = song.getSongTitle();
			String albumTitle = song.getAlbumTitle();
			String artist = song.getArtist();
			String rating = song.getRating().toString();
			songList[i] = String.format("%s by %s in album %s - %s", title, artist, albumTitle, rating);
			i++;
		}
		return songList;
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
			songList[i] = song.getSongTitle();
		}
		return songList;
	}
	
	/* Get all top rated songs and return as a list of songs for View */
	public List<Song> getTopRatedSongs() {
		return copySongsList(topRatedSongs);
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
				addToMapList(songByGenre, song.getGenre().toUpperCase(), new Song(song));
				songs.add(song); // add to all song list to keep track
				
				// Adding the associated album
				addAssociatedAlbum(store, song);
								
				return true;
			}			
		}
		return false;
		
	}
	/* Adding the associated album with the song to the library:
	 * 	- If album not in library (check using helper method searchAlbum), then
	 * 		create the album object, add this song into it, and add
	 * 		it to the library using addAlbum.
	 * 
	 * 	- If the album already in library, then check if it contains
	 * 		the song, if not, then add the song to the album
	 */
	private void addAssociatedAlbum (MusicStore store, Song song) {		
		// CASE 1: Album not already in library
		String albumTitle = song.getAlbumTitle();	
		String artist = song.getArtist();
		Album albumInLibrary = searchAlbum(albumTitle, artist);
	
		if (albumInLibrary == null) {
			Album albumDetails = store.searchAlbum(albumTitle, artist);
			// Making the album with only one song added to it
			Album newAlbum = new Album(albumDetails.getAlbumTitle(),
					albumDetails.getArtist(), albumDetails.getGenre(), 
					albumDetails.getYear());
			
			newAlbum.addSong(song);
			// add album to album hashmaps
			addToMapList(albumByTitle, albumTitle.toUpperCase(), newAlbum);
			addToMapList(albumByArtist, artist.toUpperCase(), newAlbum);
		}
		
		// CASE 2: Album already in library
		else {
			if (!albumInLibrary.containsSong(song)) {
				albumInLibrary.addSong(song);				
			}
			
		}
		
	}
	
	
	/* Helper method to search for a specific album in the library. This is used
	 * in the addSong function to determine if an album is in the library or not.
	 * Note: this access directly the internal data of Library model and returns
	 * internal data too, so must be private.
	 * 
	 * @pre albumTitle != null, artist != null
	 */
	
	private Album searchAlbum(String albumTitle, String artist){
		List<Album> potentialAlbums = albumByTitle.get(albumTitle.toUpperCase());

		// If no songs with that title
		if (potentialAlbums == null) {
			return null;
		}

		for (Album album: potentialAlbums) {
			if (album.getArtist().toUpperCase().equals(artist.toUpperCase())){ 
						return album;
				}

		}
		return null;

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
	/*
	 * When removing a song from the library we must remove from:
	 * 	- songs list
	 * 	- all created playlists with the song
	 *  - favorite songs list
	 *  - all songs hashmaps
	 *  - the album list inside album hashmaps
	 * 
	 */
	public boolean removeSong(int index) {
		if (index >= 0 && index < songs.size()) {
			// Remove from songs list
			Song song = songs.remove(index);
			
			// Remove from all playlist with the song
			for (Playlist playlist: playlists) {
				playlist.removeSong(song);
				
			}
			
			mostPlayedSongs.removeSong(song);
			recentSongs.removeSong(song);
			
			// Remove from automatic song lists
	        favoriteSongs.removeIf(favSong -> favSong.equals(song));
	        topRatedSongs.removeIf(topRatedSong-> topRatedSong.equals(song));
	        
			
			// Remove from all songs hashmaps
			removeSongFromHashmap(songByTitle, song.getSongTitle(), song);
			removeSongFromHashmap(songByArtist, song.getArtist(), song);
			removeSongFromHashmap(songByGenre, song.getGenre(), song);
			
			// Remove from the albums hashmap songs list
			List<Album> listOfAlbums = albumByTitle.get(song.getAlbumTitle().toUpperCase());
			if (listOfAlbums != null) {
				for (Album album: listOfAlbums) {
					album.removeSong(song);
				}
			}
			if (listOfAlbums != null) {
				listOfAlbums = albumByArtist.get(song.getArtist().toUpperCase());
					for (Album album: listOfAlbums) {
						album.removeSong(song);
					}
			}
			
			return true;
			
		}
		return false;
	}
	
	/*
	 * Helper method to removeSong to remove songs from all song hashmaps
	 */
	public void removeSongFromHashmap (Map<String, List<Song>> map, String key, Song song) {

		List<Song> listOfSongs = map.get(key.toUpperCase());
		if (listOfSongs != null) {
	        listOfSongs.removeIf(s -> s.equals(song));

				// Removes the entire (key, value) pair if there are no longer any
				// song with the title, artist, genre, etc
				if (listOfSongs.size() == 0) {
					map.remove(key.toUpperCase());
					
				}
			}
		}
	
	/*
	 * When removing an album from the library we must remove:
	 *  - the album inside album hashmaps
	 *  - Call the removeSong function to remove all songs inside the album if it
	 *  	exists
	 */
	public boolean removeAlbum(String albumTitle, String artist) {
		
		boolean foundAlbum = false;
		
		List<Album> listOfAlbums = albumByTitle.get(albumTitle.toUpperCase());
		
		if (listOfAlbums == null) {
			return false;
		}
		
		for (Album album: listOfAlbums) {
			// If the artist of the album matches the artist stated, then
			// that is the album and we must remove it

			if (album.getArtist().toLowerCase().equals(artist.toLowerCase())) {
				foundAlbum = true;
				
				// Remove songs in album
				removeAllSongsInAlbum(album);
				
				// Remove album
				listOfAlbums.remove(album);
				
			
				// If no album with albumTitle exist after removal, remove the key from map
				if (listOfAlbums.size() == 0) {
					albumByTitle.remove(albumTitle.toUpperCase());
					
				}
				break;
			}
		}
		
		listOfAlbums = albumByArtist.get(artist.toUpperCase());
		for (Album album: listOfAlbums) {
			if (album.getAlbumTitle().toLowerCase().equals(albumTitle.toLowerCase())) {
				foundAlbum = true;
				// Remove songs in album
				removeAllSongsInAlbum(album);
		
				// Remove album
				listOfAlbums.remove(album);
				
				if (listOfAlbums.size() == 0) {
					albumByArtist.remove(artist.toUpperCase());
					
				}
				break;
			}
		}
		
		return foundAlbum;
				
	}
	
	private void removeAllSongsInAlbum(Album album) {
		// Removing all songs in the album from the library
		for (Song s: album.getSongArray()) {
			// Finding index of the song in songs list
			int i = songs.indexOf(s);
			
			// Calling the removeSong function at that index
			this.removeSong(i);
		}
	}
	
	
	/* Adds an album and all its songs to the library.
	 * 
	 * @pre songTitle != null, artist != null, album != null
	 */
	public boolean addAlbum(MusicStore store, String albumTitle, String artist) {
		// If album already in the store, then only add the missing songs
		if (this.containsAlbum(albumTitle, artist)) {
			Album albumInLibrary = searchAlbum(albumTitle, artist);
			
			Album albumInMusicStore = store.searchAlbum(albumTitle, artist);
			for (Song songInStore: albumInMusicStore.getSongArray()) {
				// IF the library does not have the song yet, then add it
				if (!songs.contains(songInStore)) {
					addSong(store, songInStore.getSongTitle(), songInStore.getArtist(), songInStore.getAlbumTitle());
				}
				
			}
			return true;
			
		}
		
		List<Album> albumWithTitle = store.searchAlbumByTitle(albumTitle.toUpperCase());
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
	
	public void shuffleLibrarySongs() {
		Collections.shuffle(songs);
	}
	
	/* Adds a playlist to the library. Title must match exactly and is case sensitive.
	 * 
	 * @pre playlistTitle != null
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
	
	/* Shuffle the song list in playlist
	 * 
	 * @pre playlistName != null
	 */
	public Playlist shufflePlaylist(String playlistTitle) {
		Playlist playlist = playlistByTitle.get(playlistTitle);
		if (playlistByTitle.get(playlistTitle) == null) {
			return null;
		}
		playlist.shuffleSongs();
		return new Playlist(playlist);
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
				
					// add song to playlist object
					playlist.addSongToPlaylist(song);
					return true;
			}			
		}
		return false;
		
	}
	
	/* Removes a specific song from a specified playlist using a given
	 * index location representing the song index of the playlist song array.
	 * 
	 * @pre playlistName != null, location != null
	 */
	public boolean removeSongFromPlaylist(String playlistName, int location) {
		// Search playlist to make sure it exists
		Playlist playlist = playlistByTitle.get(playlistName);
		if (playlist == null) {
			return false;
		} 
		
		// Checks that song location actually one of the song index
		if (location < 0 || location >= playlist.getSongArray().size()) {
			return false;
		}
		
		playlist.removeSong(location);
		return true;
		
	}
	
	// SEARCH FUNCTIONS
	
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
	
	/* 
	 * @pre genre != null
	 */
	public List<Song> searchSongByGenre(String genre) {
		List<Song> songsWithGenre = songByGenre.get(genre.toUpperCase());
		return copySongsList(songsWithGenre);
	}
	
	/*
	 * Function to search for a specific song inside the library list and return it if found.
	 * @pre songTitle != null, artist != null, albumTitle != null
	 *
	 */
	public Song searchSong(String songTitle, String artist, String albumTitle){
		List<Song> potentialSongs = this.searchSongByTitle(songTitle.toUpperCase());
		
		// If no songs with that title
		if (potentialSongs == null) {
			return null;
		}
		
		for (Song song: potentialSongs) {
			if (song.getArtist().toUpperCase().equals(artist.toUpperCase()) 
					&& song.getAlbumTitle().toUpperCase().equals(albumTitle.toUpperCase())){ 
						return new Song(song);
				}
			
		}
		return null;
		
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
	
	public boolean rateSong(String songTitle, String artist, String album, Rating rating) {
		// Make sure the song is already to the library
		if (!this.containsSong(songTitle, artist, album)) {
			return false;
		}
		for (Song song : songs) {
			if (song.getArtist().equalsIgnoreCase(artist) 
					&& song.getAlbumTitle().equalsIgnoreCase(album)
					&& song.getSongTitle().equalsIgnoreCase(songTitle)) {
				// set rating for song
				song.setRating(rating);
				if (rating == Rating.FIVE_STAR) { 
					favoriteSongs.add(song);
					topRatedSongs.add(song);
				}
				if (rating == Rating.FOUR_STAR) 
					topRatedSongs.add(song);
				return true;
			}			
		}
		return false;
	}
	
	public boolean markAsFavorite(String songTitle, String artist, String album) {
		// Make sure the song is already to the library
		if (!this.containsSong(songTitle, artist, album)) {
			return false;
		}
		for (Song song : songs) {
			if (song.getArtist().equalsIgnoreCase(artist)
					&& song.getAlbumTitle().equalsIgnoreCase(album)
					&& song.getSongTitle().equalsIgnoreCase(songTitle)) {
					// favorite a song
					song.markAsFavorite();
					favoriteSongs.add(song);
					return true;
			}			
		}
		return false;
	}
	
	// This function will find an existing song object in library and update the stream count
	public void updateStreamCount(Song song) {
		for (Song internalSong: songs) {
			if (internalSong.equals(song)) {
				internalSong.updateStreamCount();
				return;
			}
		}
		
	}
	
	// This function is to update these two playlists with a recently played song in library and new stream count
	public void updatePlaylists(Song song) {
		for (Song internalSong: songs) {
			if (internalSong.equals(song)) {
				recentSongs.addSongToPlaylist(new Song(internalSong));
				mostPlayedSongs.addSongToPlaylist(new Song(internalSong));
				return;
			}
		}
	}
	
	public List<Song> getRecentSongs() {
		return copySongsList(recentSongs.getSongArray());
	}
	
	public List<Song> getMostPlayedSongs() {
		return copySongsList(mostPlayedSongs.getSongArray());
	}
  
}
