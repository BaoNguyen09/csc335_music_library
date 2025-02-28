package view;

import java.util.Scanner;

import database.MusicStore;
import model.LibraryModel;

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
						System.out.println("Search in Music Store");
					}
					if (command == 2) {
						System.out.println("Search in Library");
					}
					if (command == 3) {
						System.out.println("Add to library");
					}
					if (command == 4) {
						System.out.println("Get a list of items from library");
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
}
