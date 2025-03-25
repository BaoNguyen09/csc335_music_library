package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenrePlaylists {
	private List<Song> songs;
	private Map<String, List<Song>> genrePlaylists;
	private Set<String> genreList;
	
	public GenrePlaylists() {
		songs = new ArrayList<Song>();
		genrePlaylists = new HashMap<String, List<Song>>();
		genreList = new HashSet<String>();
	}
	
	public void addSong(Song song) {
		String genre = song.getGenre().toLowerCase();
		songs.add(song); // add song to songs to track all songs
		
		// create new genre playlist if song's genre is new
		if (!genreList.contains(genre)) {
			genrePlaylists.put(genre, new ArrayList<Song>());
			genreList.add(genre);
		} 
		
		// add song to corresponding genre playlist
		List<Song> genrePlaylist = genrePlaylists.get(genre);
		if (!genrePlaylist.contains(song)) // add song only if it's not present
			genrePlaylist.add(song);	
	}
	
	public boolean removeSong(Song song) {
		if (songs.contains(song)) {
			// remove song from universal song list
			songs.remove(song);
			
			// remove song from genre-based playlists
			String genre = song.getGenre().toLowerCase();
			if (genrePlaylists.keySet().contains(genre)) {
				List<Song> genrePlaylist = genrePlaylists.get(genre);
				if (genrePlaylist.contains(song)) {
					genrePlaylist.remove(song);	
				}
			}
			return true;
		}
		return false;
	}
	
	public Map<String, List<Song>> getGenrePlaylist() {
		Map<String, List<Song>> resultGenrePlaylists = new HashMap<String, List<Song>>();
		for (String key: genrePlaylists.keySet()) {
			List<Song> genrePlaylist = genrePlaylists.get(key);
			if (genrePlaylist.size() >= 10) { // only return playlists with at least 10 songs
				resultGenrePlaylists.put(key, genrePlaylist);
			}
		}
		return resultGenrePlaylists;
	}
}
