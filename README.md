# csc335_music_library
This is a class large assignment for CSC 335 Spring 2025

## Items to submit
- An executable jar file for grading purpose
- All source code & unit tests & other required sources, organized into folders and packages
- UML diagrams of all classes of Model package (in a single PDF)
- A video
- Collaboration report

### Source code
- Database package
  - MusicStore.java: read each item from the albums file, construct each album’s file name, and then read in the album information. 
  - 15 files: <album title>_<artist>.txt (show list of songs in an album)
  - albums.txt: contains a list of all the album titles and artists
  - Recommend APIs: 
    -  java.io.BufferedReader – can help with reading in from a file
    -  java.lang.String – has some very useful methods for splitting Strings 
- Model package: a collection of classes
  - LibraryModel.java(require): keeps track of the user’s library and interacts with other classes including the View and the MusicStore
  - Other potential classes: Song, Album, Playlist, Artist
- View package: It has two main jobs in this application: 
  - Prompt the user for commands, get those commands, and communicate those commands/requests to the model 
  - Receive the requested data from the model and display it to the user–in this case through a text-based user interface
### Functionalities

- Search
- Add/remove songs/albums
- Get a list of items
- Create playlists
- Mark a song/album as “favorite”
- Rate a song/album

### Unit tests
Test with at least 90% coverage: model and store

### UML diagrams

### Video

### Sprints
1. Feb 16 - Feb 22
  - [ ] Unit tests (for store and model)
  - [ ] MusicStore.java (complete): process + store all provided text files efficiently
  - [ ] Model packages (75%) + UML diagrams: Song + Album + Playlist class
  - [ ] Functionalities: search, add/remove, get
2. Feb 23 - Feb 28
  - [ ] Unit tests (for store and model)
  - [ ] Model packages (remaining) + UML diagrams: LibraryModel
  - [ ] Remaining functionalities
  - [ ] View
  - [ ] Video
  - [ ] A jar file
