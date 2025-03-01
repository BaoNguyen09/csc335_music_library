package view;

import java.util.List;
import java.util.Scanner;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.Song;

public class View {
	public static void main(String[] args) {
		LibraryModel library = new LibraryModel();
		MusicStore musicStore = new MusicStore();
		showCommandMenu();
		int command = 0;
		
		try (Scanner console = new Scanner(System.in)) {
			// program loop
			while (command != 9) {
				try {
					System.out.print("Enter your command (in int): ");
					command = Integer.valueOf(console.nextLine());
					if (command == 0) {
						showCommandMenu();
					}
					if (command == 1) {
					    searchMusicStore(console, musicStore);
						showCommandMenu();
					}
					if (command == 2) {
						searchLibrary(console, library);
						showCommandMenu();

					}
					if (command == 3) {
						addSongToLibrary(console, library, musicStore);
						showCommandMenu();
					}
					if (command == 4) {
						showItemsInLibrary(console, library);
						showCommandMenu();
						
					}
					if (command == 5) {
						System.out.println("Create a playlist");
					}
					if (command == 6) {
						System.out.println("Add or remove a song from playlist");
					}
					if (command == 7) {
						System.out.println("Mark a song as \"favorite\"");
					}
					if (command == 8) {
						System.out.println("Rate a song");
					}
					if (command > 9 || command < 0) {
						System.out.println("Please enter valid command");
					}
					if (command == 9) {
						System.out.println("Exiting the program...");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			console.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Program closed. Thank you!");
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
							5. Create a playlist
							6. Add/remove a song from playlist
							7. Mark a song as "favorite"
							8. Rate a song
							9. Exit
							
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
	    while (searchChoice != 5) {
	        System.out.println("""
	        		
	            Search in Music Library:
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
	                boolean status = library.addSong(store, title, artist, album);
	                if (status) System.out.println("Added song successfully!");
	                else System.out.println("Song wasn't added successfully, make sure song is present in the store");
	                
	            }
	            case 2 -> {
	                System.out.print("Enter the album title: ");
	                String album = console.nextLine();
	                System.out.print("Enter the artist name: ");
	                String artist = console.nextLine();
	                boolean status = library.addAlbum(store, album, artist);
	                if (status) System.out.println("The album was successfully added");
	                else System.out.println("Album wasn't added successfully, make sure album is present in the store");

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
	    while (searchChoice != 6) {
	        System.out.println("""
	        		
				        		Add To Library:
				        		    1. Get all song titles
				        		    2. Get all artist names
				        		    3. Get all album titles
				        		    4. Get all playlist titles
				        		    5. Get all favorite song titles
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
	

}
