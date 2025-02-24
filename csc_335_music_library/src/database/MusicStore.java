package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import model.Album;
import model.Song;

public class MusicStore {
	private Map<String, List<Song>> songByTitle;
	private Map<String, List<Song>> songByArtist;
	private Map<String, List<Album>> albumByTitle;
	private Map<String, List<Album>> albumByArtist;

	public MusicStore() {
		songByTitle = new HashMap<String, List<Song>>();
		songByArtist = new HashMap<String, List<Song>>();
		albumByTitle = new HashMap<String, List<Album>>();
		albumByArtist = new HashMap<String, List<Album>>();
		
		try {
			processAlbums();
		} catch (IOException e) {
			fail("Error processing file: albums.txt");
		}
	}
	
	public List<Song> searchSongByTitle(String songTitle) {
		List<Song> songsWithTitle = songByTitle.get(songTitle);
		return copySongsList(songsWithTitle);
	}
	
	public List<Song> searchSongByArtist(String artist) {
		List<Song> songsWithArtist = songByArtist.get(artist);
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
		List<Album> albumsWithTitle = albumByTitle.get(albumTitle);
		return copyAlbumsList(albumsWithTitle);
	}
	
	public List<Album> searchAlbumByArtist(String artist) {
		List<Album> albumsWithArtist = albumByArtist.get(artist);
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

	
	/*
	 * Description: Reads and process all albums files listed in the 
	 *'src/database/albums/fileName' text file. (e.g. processing all 
	 * <album title>_<artist>.txt files listed in the albums.txt file.
	 * 
	 * @pre each line in fileName is formatted as follows:
	 * 	<album title>,<artist>
	 */
	private void processAlbums() throws IOException  {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("src/database/albums/albums.txt"));
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				// Each line is formatted <Album Title>,<Artist>
				String[] temp = line.strip().split(",");
				String songFileName = temp[0] + "_" + temp[1] + ".txt";
				String filePath = "src/database/albums/" + songFileName;
				processFile(filePath);
				
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw e;
		}
	
	}
	
	/*
	 * @pre 
	 * The first line albums.txt is formatted as follows:
	 * 	<album title>,<artist>,<genre>,<year>
	 * 
	 * Each Subsequent line is formatted as follows:
	 * 	<songTitle>
	 */
	private void processFile(String filePath) throws IOException{
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(filePath));
		// First line is formatted: Album Title, Artist, Genre, Year
		String line = reader.readLine();
		String[] albumInfo = line.strip().split(",");
		
		String albumTitle = albumInfo[0];
		String artist = albumInfo[1];
		Album currAlbum = new Album(albumTitle, artist, albumInfo[2], albumInfo[3]);
		
		// For each song in the album, add it to album
		while ((line = reader.readLine()) != null) {
			String songTitle = line.strip();
			Song currSong = new Song(songTitle, artist, albumTitle);
			currAlbum.addSong(currSong);
			
			// Adding to the songs map
			addToMapList(songByArtist, artist, currSong);
			addToMapList(songByTitle, songTitle, currSong);
			
		}
		
		// Adding to the albums map
		addToMapList(albumByTitle, albumTitle, currAlbum);
		addToMapList(albumByArtist, artist, currAlbum);
		
		reader.close();
		
	}
	
	// Function adds to the respective map
	private <K, V> void addToMapList(Map<K, List<V>> map, K key, V value) {
		
		// if the key already exist then it just adds the value to the list
		// else it initializes a list for that key value with that value added
	    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
	
	

}
