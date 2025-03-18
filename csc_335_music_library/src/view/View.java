package view;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import database.MusicStore;
import database.UserStorage;
import model.Album;
import model.LibraryModel;
import model.Playlist;
import model.Song;
import model.Song.Rating;
import model.User;

public class View {
	public static void main(String[] args) {
	    UserStorage storage = new UserStorage();
	    MusicStore musicStore = new MusicStore();
	    // Create a single Scanner for the entire program.
	    Scanner scanner = new Scanner(System.in);
	    
	    while (true) {
	        // CREATING ACCOUNT/LOGGING IN
	        User currentUser = authenticateUser(scanner, storage);
	        while (currentUser == null) {
	            System.out.println("Authentication failed. Please Retry.");
	            currentUser = authenticateUser(scanner, storage);
	        }
	    
	        LibraryModel library = currentUser.getLibrary();
	        showCommandMenu();
	        int command = 0;
	    
	        // MENU OPTIONS FOR USER
	        // Use the same scanner instance for commands.
	        while (command != 9) {
	            try {
	                System.out.print("Enter your command (in int): ");
	                command = Integer.valueOf(scanner.nextLine());
	                if (command == 0) {
	                    showCommandMenu();
	                }
	                if (command == 1) {
	                    searchMusicStore(scanner, musicStore);
	                    showCommandMenu();
	                }
	                if (command == 2) {
	                    searchLibrary(scanner, library);
	                    showCommandMenu();
	                }
	                if (command == 3) {
	                    addSongToLibrary(scanner, library, musicStore);
	                    showCommandMenu();
	                }
	                if (command == 4) {
	                    showItemsInLibrary(scanner, library);
	                    showCommandMenu();
	                }
	                if (command == 5) {
	                    createPlayList(scanner, library);
	                    showCommandMenu();
	                }
	                if (command == 6) {
	                    addOrRemoveSongFromPlaylist(scanner, library);
	                    showCommandMenu();
	                }
	                if (command == 7) {
	                    markSongAsFavorite(scanner, library);
	                    showCommandMenu();
	                }
	                if (command == 8) {
	                    rateSong(scanner, library);
	                    showCommandMenu();
	                }
	                if (command > 9 || command < 0) {
	                    System.out.println("Please enter valid command");
	                }
	                if (command == 9) {
	                    // Updating the user music library and saving the updated user data to database
	                    currentUser.updateLibrary(library);
	                    storage.saveUser(currentUser);
	                    System.out.println("Saving and logging out...");
	                    break;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        // The outer loop continues and prompts for authentication again.
	    }
	    // scanner.close(); // You could close it here when exiting the program.
	}

	
    /**
     * Handles user authentication by prompting for login or account creation.
     * @param scanner the Scanner used for user input
     * @param storage the UserStorage instance for loading/creating users
     * @return the authenticated User, or null if authentication fails
     */
    private static User authenticateUser(Scanner scanner, UserStorage storage) {
        System.out.println("\nWelcome! Do you want to:");
        System.out.println("1. Log in");
        System.out.println("2. Create a new account");
        System.out.println("3. Exit Program");
        
        int choice = 0;
        while (true) {
            System.out.print("Enter your command (int): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter 1 or 2.");
                continue;
            }
            if (choice == 3) {
            	System.out.println("Program closed.");
            	System.exit(1);
            }
            if (choice == 1 || choice == 2) {
                break;
            } else {
                System.out.println("Invalid choice. Please choose 1 for login or 2 for account creation.");
            }
        }
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        if (choice == 1) {
            User user = storage.loadUser(username, password);
            if (user != null) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid credentials.");
            }
            return user;
        } else { // choice == 2
            boolean created = storage.createUser(username, password);
            if (created) {
                System.out.println("Account created successfully!");
            } else {
                System.out.println("User with that username already exists. Please try logging in.");
                return null;
            }
            return storage.loadUser(username, password);
        }
    }

	
	public static void showCommandMenu() {
		System.out.println( """
				
							Welcome to Music Library App!
							Below are available commands. To call any, please enter the correct index:
							0. Print the command menu
							1. Search in Music Store
								a. for a song by title
								b. for a song by artist
								c. for an album by title
								d. for an album by artist
							2. Search in Library
								a. for a song by title
								b. for a song by artist
								c. for an album by title
								d. for an album by artist
								e. for a playlist by title
							3. Add to library
								a. song
								b. album (with all the songs)
							4. Get a list of items from library
								a. song titles (any order) 
								b. artist (any order)
								c. albums (any order)
								d. playlist (any order)
								e. favorite songs (any order)
								f. all song ratings (any order)
							5. Create a playlist
							6. Add/remove a song from playlist
							7. Mark a song as "favorite"
							8. Rate a song
							9. Save and Log Out
							
							""");
	}
	
	// COMMAND ONE menu options - The following code was generated by ChatGPT.
	// I did make some changes, such as reducing the code duplication by grouping code
	// into their own functions. I also formatted the output to be what was requested
	// in the specifications.
	private static void searchMusicStore(Scanner console, MusicStore musicStore) {
	    int searchChoice = 0;
	    
	    // Keep showing the sub-menu until the user chooses to exit
	    while (searchChoice != 5) {
	        System.out.println("""

	            Search in Music Store:
	            1. Search Song By Title
	            2. Search Song By Artist
	            3. Search Album By Title
	            4. Search Album By Artist
	            5. Return to Main Menu
	            """);

	        System.out.print("Enter your search choice: ");
	        try {
	            searchChoice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-5.");
	            continue;  // re-display the sub-menu
	        }

	        switch (searchChoice) {
	            case 1 -> {
	                System.out.print("Enter the song title: ");
	                String title = console.nextLine();
	                List<Song> foundSongs = musicStore.searchSongByTitle(title);
	                printSong(foundSongs, title);
	                
	            }
	            case 2 -> {
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                List<Song> foundSongs = musicStore.searchSongByArtist(artist);
	                printSong(foundSongs, artist);

	            }
	            case 3 -> {
	                System.out.print("Enter the album title: ");
	                String albumTitle = console.nextLine();
	                List<Album> foundAlbums = musicStore.searchAlbumByTitle(albumTitle);
	                printAlbum(foundAlbums, albumTitle);
	            }
	            case 4 -> {
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                List<Album> foundAlbums = musicStore.searchAlbumByArtist(artist);
	                printAlbum(foundAlbums, artist);
	            }
	            case 5 -> {
	                System.out.println("Returning to Main Menu...");
	                // The while loop will end because searchChoice == 5
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	// Helper method to print song details:
	// 	print the song title, the artist, and the album it’s on
	public static void printSong(List<Song> foundSongs, String searchTerm) {
		if (foundSongs.isEmpty()) {
            System.out.println("No songs found for title: " + searchTerm);
        } else {
            System.out.println("Found songs:");
            for (Song s : foundSongs) {
                System.out.println("- " + s.getSongTitle() + " by " + s.getArtist());
                System.out.println("	Album: " + s.getAlbumTitle() + "\n");
            }
        }
	}
		
	// Helper method to print album details:
	// 	print the album information and a list of the songs in the appropriate order
	public static void printAlbum(List<Album> foundAlbums, String searchTerm) {
		if (foundAlbums.isEmpty()) {
            System.out.println("No albums found for search query: " + searchTerm);
        } else {
            System.out.println("Found albums:\n");
            for (Album a : foundAlbums) {
            	System.out.println("Album Title: " + a.getAlbumTitle());
                System.out.println("Artist:      " + a.getArtist());
                System.out.println("Genre:       " + a.getGenre());
                System.out.println("Year:        " + a.getYear());
                System.out.println("Songs:");
                for (Song s : a.getSongArray()) {
                    System.out.println("  - " + s.getSongTitle());
                }
                System.out.println();
                
            }
        }
	}
	
	// COMMAND TWO menu options - Used the same code structure as above but
	// changed to reflect searching the Library instead of the MusicStore.
	private static void searchLibrary(Scanner console, LibraryModel library) {
	    int searchChoice = 0;
	    
	    // Keep showing the sub-menu until the user chooses to exit
	    while (searchChoice != 6) {
	        System.out.println("""
	        		
	            Search in Music Library:
	            1. Search Song By Title
	            2. Search Song By Artist
	            3. Search Album By Title
	            4. Search Album By Artist
	            5. Search Playlist by Title
	            6. Return to Main Menu
	            """);

	        System.out.print("Enter your search choice: ");
	        try {
	            searchChoice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-6.");
	            continue;  // re-display the sub-menu
	        }

	        switch (searchChoice) {
	            case 1 -> {
	                System.out.print("Enter the song title: ");
	                String title = console.nextLine();
	                List<Song> foundSongs = library.searchSongByTitle(title);
	                printSong(foundSongs, title);
	                
	            }
	            case 2 -> {
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                List<Song> foundSongs = library.searchSongByArtist(artist);
	                printSong(foundSongs, artist);

	            }
	            case 3 -> {
	                System.out.print("Enter the album title: ");
	                String albumTitle = console.nextLine();
	                List<Album> foundAlbums = library.searchAlbumByTitle(albumTitle);
	                printAlbum(foundAlbums, albumTitle);
	            }
	            case 4 -> {
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                List<Album> foundAlbums = library.searchAlbumByArtist(artist);
	                printAlbum(foundAlbums, artist);
	            }
	            case 5 -> {
	                System.out.print("Enter the playlist title: ");
	                String playlistTitle = console.nextLine();
	                Optional<Playlist> maybePlaylist = library.searchPlaylistByTitle(playlistTitle);
	                if (maybePlaylist.isEmpty()) {
	                    System.out.println("No playlist with that title. Returning to main menu...");
	                    break; // or return to the sub-menu
	                }

	                // Get the actual playlist
	                Playlist playlist = maybePlaylist.get();
	                List<Song> songs = playlist.getSongArray();
	                if (songs.isEmpty()) {
	                    System.out.println("This playlist has no songs.");
	                    break; // or return to the sub-menu
	                }

	                // Show the user all songs with their indexes
	                System.out.println("Songs in \"" + playlistTitle + "\":");
	                for (int i = 0; i < songs.size(); i++) {
	                    System.out.println(i + ": " + songs.get(i).getSongTitle());
	                }
	            }
	            case 6 -> {
	                System.out.println("Returning to Main Menu...");
	                // The while loop will end because searchChoice == 5
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	// COMMAND THREE menu options - Used the same code structure as above but
	// changed to add song to library
	private static void addSongToLibrary(Scanner console, LibraryModel library, MusicStore store) {
		int searchChoice = 0;
	    
	    // Keep showing the sub-menu until the user chooses to exit
	    while (searchChoice != 3) {
	        System.out.println("""
	        		
		        Add To Library:
	            1. A song
	            2. A whole album
	            3. Return to Main Menu
		            """);

	        System.out.print("Enter your search choice: ");
	        try {
	            searchChoice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-3.");
	            continue;  // re-display the sub-menu
	        }

	        switch (searchChoice) {
	            case 1 -> {
	                System.out.print("Enter the song title: ");
	                String title = console.nextLine();
	                System.out.print("Enter the album title: ");
	                String album = console.nextLine();
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                
	                // 'addSong' returns true if added successfully, false if song isn't in the store
	                boolean status = library.addSong(store, title, artist, album);
	                if (status) {
	                    System.out.println(String.format("\"%s\" was added to library successfully!", title));
	                } else {
	                    System.out.println("Fail: Song isn't in the store");
	                }
	                
	            }
	            case 2 -> {
	                System.out.print("Enter the album title: ");
	                String album = console.nextLine();
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                
	                // 'addAlbum' returns true if added successfully, false if album isn't in the store
	                boolean status = library.addAlbum(store, album, artist);
	                if (status) {
	                    System.out.println(String.format("Album \"%s\" was added to library successfully!", album));
	                } else {
	                    System.out.println("Fail: Album isn't in the store");
	                }
	            }
	            case 3 -> {
	                System.out.println("Returning to Main Menu...");
	                // The while loop will end because searchChoice == 5
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	// COMMAND FOUR menu options - Used the same code structure as above but
	// changed to show items in library
	private static void showItemsInLibrary(Scanner console, LibraryModel library) {
		int searchChoice = 0;
	    
	    // Keep showing the sub-menu until the user chooses to exit
	    while (searchChoice != 7) {
	        System.out.println("""
	        		
				        		Add To Library:
				        		    1. Get all song titles
				        		    2. Get all artist names
				        		    3. Get all album titles
				        		    4. Get all playlist titles
				        		    5. Get all favorite song titles
				        		    6. Get all song ratings
				        		    7. Return to Main Menu
	        		    		""");
	       

	        System.out.print("Enter your search choice: ");
	        try {
	            searchChoice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-7.");
	            continue;  // re-display the sub-menu
	        }

	        switch (searchChoice) {
	            case 1 -> {
	                String[] songTitles = library.getSongTitles();
	                printItems(songTitles, "song titles");
	            }
	            case 2 -> {
	            	String[] artists = library.getArtists();
	                printItems(artists, "artists");

	            }
	            case 3 -> {
	            	String[] albumTitles = library.getAlbumTitles();
	                printItems(albumTitles, "album titles");

	            }
	            case 4 -> {
	            	String[] playlistTitles = library.getPlaylistTitles();
	                printItems(playlistTitles, "playlist titles");

	            }
	            case 5 -> {
	            	String[] favoriteSongs= library.getFavoriteSongs();
	                printItems(favoriteSongs, "favorite songs");

	            }
	            case 6 -> {
	            	String[] songRatings= library.getSongRatings();
	                printItems(songRatings, "song ratings");

	            }
	            case 7 -> {
	                System.out.println("Returning to Main Menu...");
	                // The while loop will end because searchChoice == 5
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
		// Helper method to print item out as line by line
		public static void printItems(String[] foundItems, String searchTerm) {
			if (foundItems.length == 0) {
	            System.out.println(String.format("No  %s found", searchTerm));
	        } else {
	            System.out.println(String.format("Found %s:", searchTerm));
	            for (int i=0; i<foundItems.length; i++) {
	                System.out.println("- " + foundItems[i]);
	            }
	        }
		}
	

	
	// COMMAND FIVE menu options- create a song in library. The following again
	// uses the previous format generated by ChatGPT, but changes the options to
	// call the respective functions in library and print the correct sub menu
	// corresponding to the main menu.
	private static void createPlayList(Scanner console, LibraryModel library) {
	    int choice = 0;

	    while (choice != 2) {
	        System.out.println("""
	            Create a Playlist:
	            1. Create new playlist
	            2. Return to Main Menu
	            """);

	        System.out.print("Enter your choice: ");
	        try {
	            choice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-2.");
	            continue; // re-display the sub-menu
	        }

	        switch (choice) {
	            case 1 -> {
	                System.out.print("Enter the new playlist name: ");
	                String playlistName = console.nextLine().trim();

	                // 'addPlaylist' returns true if created successfully, false if it already exists
	                boolean created = library.addPlaylist(playlistName);
	                if (created) {
	                    System.out.println("Playlist \"" + playlistName + "\" created!\n");
	                } else {
	                    System.out.println("A playlist with that name already exists!");
	                }
	            }
	            case 2 -> {
	                System.out.println("Returning to Main Menu...");
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	// COMMAND SIX menu options- add or remove a song from playlist. The following
	// uses the previous format generated by ChatGPT, but changes the options to
	// call the respective functions in library and print the correct sub menu
	// corresponding to the main menu.
	private static void addOrRemoveSongFromPlaylist(Scanner console, LibraryModel library) {
	    int choice = 0;

	    while (choice != 3) {
	        System.out.println("""
	        		
	            Operations for Playlist:
	            1. Add a song to a playlist
	            2. Remove a song from a playlist
	            3. Return to Main Menu
	            """);

	        System.out.print("Enter your choice: ");
	        try {
	            choice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-3.");
	            continue; // re-display the sub-menu
	        }

	        switch (choice) {
	            case 1 -> {
	                System.out.print("Enter the wanted playlist name: ");
	                String playlistName = console.nextLine().trim();
	                System.out.print("Enter the song title: ");
	                String songTitle = console.nextLine().trim();
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine().trim();
	                System.out.print("Enter the album title: ");
	                String albumTitle = console.nextLine().trim();

	                // 'addSongToPlaylist' returns true if added successfully, false if song isn't in the store or playlist doesn't exist
	                boolean added = library.addSongToPlaylist(playlistName, songTitle, artist, albumTitle);
	                if (added) {
	                    System.out.println(String.format("\"%s\" was added successfully to playlist: %s", songTitle, playlistName));
	                } else {
	                    System.out.println("Fail: Song isn't in the store or playlist doesn't exist");
	                }
	            }
	            case 2 -> {
	            	// This part of the code to print out all songs in the playlist was
	            	// generated using ChatGPT.
	            	
	                System.out.print("Enter the playlist name: ");
	                String playlistName = console.nextLine().trim();

	                Optional<Playlist> maybePlaylist = library.searchPlaylistByTitle(playlistName);
	                if (maybePlaylist.isEmpty()) {
	                    System.out.println("No playlist with that title. Returning to main menu...");
	                    break; // or return to the sub-menu
	                }

	                // Get the actual playlist
	                Playlist playlist = maybePlaylist.get();
	                List<Song> songs = playlist.getSongArray();
	                if (songs.isEmpty()) {
	                    System.out.println("This playlist has no songs.");
	                    break; // or return to the sub-menu
	                }

	                // Show the user all songs with their indexes
	                System.out.println("Songs in \"" + playlistName + "\":");
	                for (int i = 0; i < songs.size(); i++) {
	                    System.out.println(i + ": " + songs.get(i).getSongTitle());
	                }

	                // prompt for the index to remove
	                System.out.print("Enter the index of the song to remove: ");
	                int index = Integer.parseInt(console.nextLine().trim());

	                // removeSongFromPlaylist returns true if successful, false if invalid
	                boolean removed = library.removeSongFromPlaylist(playlistName, index);
	                if (removed) {
	                    System.out.printf("Song was removed successfully from playlist: %s%n", playlistName);
	                } else {
	                    System.out.println("Fail: index isn't valid or playlist doesn't exist.");
	                }
	            }

	            case 3 -> {
	                System.out.println("Returning to Main Menu...");
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	// COMMAND SEVEN menu options - mark a song as favorite. The following code
	// uses the previous format generated by ChatGPT, but changes the options to
	// call the respective functions in library and print the correct sub menu
	// corresponding to the main menu.
	private static void markSongAsFavorite(Scanner console, LibraryModel library) {
	    int choice = 0;

	    while (choice != 2) {
	        System.out.println("""
	        		
	            Available options:
		            1. Mark a song as favorite
		            2. Return to Main Menu
	            """);

	        System.out.print("Enter your choice: ");
	        try {
	            choice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-2.");
	            continue; // re-display the sub-menu
	        }

	        switch (choice) {
	            case 1 -> {
	            	System.out.print("Enter the song title: ");
	                String title = console.nextLine();
	                System.out.print("Enter the album title: ");
	                String album = console.nextLine();
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                
	                // 'markAsFavorite' returns true if marked successfully, false if song isn't in the store or library
	                boolean status = library.markAsFavorite(title, artist, album);
	                if (status) {
	                    System.out.println(String.format("\"%s\" was marked as favorite successfully!", title));
	                } else {
	                    System.out.println("Fail: Song isn't in the store or library");
	                }
	            }
	            case 2 -> {
	                System.out.println("Returning to Main Menu...");
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	// COMMAND EIGHT menu options - rate a song and show all song raings. The following
	// uses the previous format generated by ChatGPT, but changes the options to
	// call the respective functions in library and print the correct sub menu
	// corresponding to the main menu.
	private static void rateSong(Scanner console, LibraryModel library) {
	    int choice = 0;

	    while (choice != 2) {
	        System.out.println("""
	        		
	            Available options:
		            1. Rate a song
		            2. Return to Main Menu
	            """);

	        System.out.print("Enter your choice: ");
	        try {
	            choice = Integer.parseInt(console.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a number 1-2.");
	            continue; // re-display the sub-menu
	        }

	        switch (choice) {
	            case 1 -> {
	            	System.out.print("Enter the song title: ");
	                String title = console.nextLine();
	                System.out.print("Enter the album title: ");
	                String album = console.nextLine();
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                showRatingMenu();
	                int ratingChoice = 0;
	                Rating rating = Rating.UNRATED;
	                while (ratingChoice == 0) {
	                	try {
		    	            ratingChoice = Integer.parseInt(console.nextLine().trim());
			                switch (ratingChoice) {
				                case 1 -> {
				                	rating = Rating.ONE_STAR;
				                }
				                case 2 -> {
				                	rating = Rating.TWO_STAR;
				                }
				                case 3 -> {
				                	rating = Rating.THREE_STAR;
				                }
				                case 4 -> {
				                	rating = Rating.FOUR_STAR;
				                }
				                case 5 -> {
				                	rating = Rating.FIVE_STAR;
				                }
				                default -> {
					                System.out.println("You chose a number other than 1-5 so rating is set to UNRATED");
					            }
			                }
		    	        } catch (NumberFormatException e) {
		    	            System.out.println("Invalid input. Please enter a number not a string.");
		    	            showRatingMenu();
		    	            continue;
		    	        }
	                }
	                
	                // 'rateSong' returns true if rated successfully, false if song isn't in the store or library
	                boolean status = library.rateSong(title, artist, album, rating);
	                if (status) {
	                    System.out.println(String.format("\"%s\" was rated %s successfully!", title, rating.toString()));
	                } else {
	                    System.out.println("Fail: Song isn't in the store or library");
	                }
	            }
	            case 2 -> {
	                System.out.println("Returning to Main Menu...");
	            }
	            default -> {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	}
	
	private static void showRatingMenu() {
		System.out.println("Choose a rating from 1-5: ");
        System.out.println("1. ONE_STAR");
        System.out.println("2. TWO_STAR");
        System.out.println("3. THREE_STAR");
        System.out.println("4. FOUR_STAR");
        System.out.println("5. FIVE_STAR");
        System.out.println("Or enter any other number to set it as UNRATED");
	}
	
}
