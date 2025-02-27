package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private String playlistTitle;
	private List<Song> songs;

	/* @pre title != null */
	public Playlist(String title) {
		playlistTitle = title;
		songs = new ArrayList<Song>();
	}
	
	/* Copy constructor */
	public Playlist(Playlist anotherPlaylist) {
		this.playlistTitle = anotherPlaylist.getPlaylistTitle();
		this.songs = anotherPlaylist.getSongArray();
	}
	
	public String getPlaylistTitle() {
		return this.playlistTitle;
	}
	
	public String[] getPlaylistSongs() {
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
	
	public void addSongToPlaylist(Song song) {
		// Check if Song instances is duplicated (shallow check)
		if (!songs.contains(song)) {
			// Compare song's values with each other (deep check)
			for (Song songItem: songs) {
				if (songItem.getSongTitle().equals(song.getSongTitle())
						&& songItem.getAlbumTitle().equals(song.getAlbumTitle())
						&& songItem.getArtist().equals(song.getArtist())) {
					return; // stop if spot duplicated song
				}
			}
			songs.add(new Song(song));
		}
	}
    
	/* Return boolean for View to check if song is successfully removed */
	public Song removeSong(int index) {
		if (index >= 0 && index < songs.size()) {
			return songs.remove(index);
		}
		return null; // if index isn't valid
	}
	
	@Override
	public String toString() {
		return playlistTitle;
	}
}
