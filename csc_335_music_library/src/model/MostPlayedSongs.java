package model;

import java.util.Collections;
import java.util.Comparator;

public class MostPlayedSongs extends Playlist{
	
	public MostPlayedSongs() {
		super("Most Played Songs Playlist");
	}
	
	@Override
	public void addSongToPlaylist(Song song) {
		int lowestStreamCount = 0;
		if (songs.size() > 0) lowestStreamCount = songs.get(0).getStreamCount();
		
		// Check if Song instances is already added (deep check)
		if (!songs.contains(song)) {
			// if stream count of this new song >= lowestStreamCount, add it to the list
			if (song.getStreamCount() >= lowestStreamCount || songs.size() <= 10) songs.add(song);
			else return;
		} 
		
		// Sort this song list again to update the order with updated stream count
		Collections.sort(songs, new Comparator<Song>(){

			  public int compare(Song song_1, Song song_2)
			  {
			     return song_1.getStreamCount().compareTo(song_2.getStreamCount());
			  }
		});
		
		if (songs.size() > 10) songs.remove(0); // ensure the length of the list is always <= 10
	}
	
	/* Disable this method because this playlist is maintained automatically */
	@Override
	public boolean removeSong(int index) {
		return false;
	}
}
