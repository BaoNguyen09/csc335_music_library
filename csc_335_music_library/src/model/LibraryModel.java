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
	
	public LibraryModel() {
		playlists = new ArrayList<Playlist>();
		favoriteSongs = new ArrayList<Song>();
		songByTitle = new HashMap<String, List<Song>>();
		songByArtist = new HashMap<String, List<Song>>();
		albumByTitle = new HashMap<String, List<Album>>();
		albumByArtist = new HashMap<String, List<Album>>();
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
}
