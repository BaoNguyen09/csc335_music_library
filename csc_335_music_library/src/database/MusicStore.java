package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        songByTitle = new HashMap<>();
        songByArtist = new HashMap<>();
        albumByTitle = new HashMap<>();
        albumByArtist = new HashMap<>();
        
        try {
            processAlbums();
        } catch (IOException e) {
            throw new RuntimeException("Failed to process albums.txt", e);
        }
    }
    
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
            return new ArrayList<>();
        }
        List<Song> listCopy = new ArrayList<>();
        for (Song s : songsList) {
            listCopy.add(new Song(s));
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
            return new ArrayList<>();
        }
        
        List<Album> listCopy = new ArrayList<>();
        for (Album a : albumsList) {
            listCopy.add(new Album(a));
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
    private void processAlbums() throws IOException {
        // "albums.txt" is assumed to be in the same package as this class
        InputStream in = getClass().getResourceAsStream("albums.txt");
        if (in == null) {
            throw new FileNotFoundException("Could not find albums.txt");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Each line: <Album Title>,<Artist>
                String[] temp = line.strip().split(",");
                String albumTitle = temp[0];
                String artist = temp[1];

                String albumResource = albumTitle + "_" + artist + ".txt";
                // Reading each album individually
                processFile(albumResource);
            }
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
    private void processFile(String resourcePath) throws IOException {
        InputStream fileIn = getClass().getResourceAsStream(resourcePath);
        if (fileIn == null) {
            throw new FileNotFoundException("Could not find " + resourcePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn))) {
            // First line: Album Title, Artist, Genre, Year
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Empty album file: " + resourcePath);
            }
            
            String[] albumInfo = line.strip().split(",");
            String albumTitle = albumInfo[0];
            String artist = albumInfo[1];
            Album currAlbum = new Album(albumTitle, artist, albumInfo[2], albumInfo[3]);
            
            // For each song line, add it to the album
            while ((line = reader.readLine()) != null) {
                String songTitle = line.strip();
                Song currSong = new Song(songTitle, artist, albumTitle);
                currAlbum.addSong(currSong);

                // Add to the songs map
                addToMapList(songByArtist, artist.toUpperCase(), currSong);
                addToMapList(songByTitle, songTitle.toUpperCase(), currSong);
            }
            
            // Add to the albums map
            addToMapList(albumByTitle, albumTitle.toUpperCase(), currAlbum);
            addToMapList(albumByArtist, artist.toUpperCase(), currAlbum);
        }
    }
    
	// Function adds to the respective map
    private <K, V> void addToMapList(Map<K, List<V>> map, K key, V value) {
    	
    	// if the key already exist then it just adds the value to the list
    	// else it initializes a list for that key value with that value added
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }
}

