package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import model.Song;
import model.Song.Rating;

class SongTests {

	@Test
	void testGetSongTitle() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals("Daydreamer", song.getSongTitle(), "Song Title should be: Daydreamer");
	}
	
	@Test
	void testGetArtist() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals("Adele", song.getArtist(), "Song Artist should be: Adele");
	}
	
	@Test
	void testGetAlbumTitle() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals("19", song.getAlbumTitle(), "Song Album Title should be: 19");
	}
	
	@Test
	void testGetDefaultRating() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals(Rating.UNRATED, song.getRating(), "Initially, rating is set to UNRATED");
	}
	
	@Test
	void testGetDefaultIsFavorite() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals(false, song.isFavorite(), "Initially, favorite is set to false");
	}
	
	@Test
	void testSetRating() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals(Rating.UNRATED, song.getRating(), "Initially, rating is set to UNRATED");
		song.setRating(Rating.FIVE_STAR);
		assertEquals(Rating.FIVE_STAR, song.getRating(), "Afterwards, rating is set to FIVE_STAR");
		assertEquals(true, song.isFavorite(), "When rating is FIVE_STAR, it's marked as favorite");
	}

  @Test
	void testMarkingASongAsFavorite() {
		Song song = new Song("Daydreamer", "Adele", "19", "Pop");
		assertEquals(false, song.isFavorite(), "Initially, favorite is set to false");
		song.markAsFavorite();
		assertEquals(true, song.isFavorite(), "Afterwards, favorite is set to true");
	}
   

  @Test
  public void testTitleFirstComparatorDifferetTitles() {
	  Song song1 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", "Singer/SongWriter");
      Song song2 = new Song("Daydreamer", "Adele", "19", "Pop");

      Comparator<Song> comparator = Song.artistFirstComparator(); 

      // Artist comparison
      assertTrue(comparator.compare(song1, song2) > 0);
      assertTrue(comparator.compare(song2, song1) < 0);
  }

  @Test
  public void testArtistFirstComparatorDifferentArtists() {
	  Song song1 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", "Singer/SongWriter");
      Song song2 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");

      Comparator<Song> comparator = Song.artistFirstComparator(); 

      // Artist comparison
      assertTrue(comparator.compare(song1, song2) < 0);
      assertTrue(comparator.compare(song2, song1) > 0);
  }

  @Test
  public void testArtistFirstComparatorSameArtistDifferentTitles() {
	  Song song1 = new Song("Secrets", "Adele", "19", "Pop");
      Song song2 = new Song("Daydreamer", "Adele", "19", "Pop");

      Comparator<Song> comparator = Song.artistFirstComparator();

      // Same artist, compare by title
      assertTrue(comparator.compare(song1, song2) > 0);
      assertTrue(comparator.compare(song2, song1) < 0);
  }


  @Test
  public void testRatingFirstComparatorDifferentRatings() {
	  Song song1 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", "Singer/SongWriter");
      Song song2 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");
      song1.setRating(Rating.FIVE_STAR);
      song2.setRating(Rating.FOUR_STAR);

      Comparator<Song> comparator = Song.ratingFirstComparator();

      // Rating comparison
      assertTrue(comparator.compare(song1, song2) > 0);   // 5 < 4
      assertTrue(comparator.compare(song2, song1) < 0);
  }
  
@Test
public void testTitleFirstComparatorSameTitleDifferentArtists() {
	Song song1 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", "Singer/SongWriter");
    Song song2 = new Song("Daydreamer", "Adele", "19", "Pop");


    Comparator<Song> comparator = Song.titleFirstComparator();
    
    // Same title, compare by artist
    assertTrue(comparator.compare(song1, song2) > 0); 
    assertTrue(comparator.compare(song2, song1) < 0);
}

@Test
public void testTitleFirstComparatorSameTitleSameArtist() {
    Song song1 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");
    Song song2 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");

    Comparator<Song> comparator = Song.titleFirstComparator();
    
    // Same title and artist
    assertEquals(0, comparator.compare(song1, song2));
}

@Test
public void testEqualsTrue() {
    Song song1 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");
    Song song2 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");

    assertTrue(song1.equals(song2));
}

@Test
public void testEqualsFalse() {
    Song song1 = new Song("Lullaby", "OneRepublic", "Waking Up", "Rock");
    Song song2 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", "Singer/Songwriter");

    assertFalse(song1.equals(song2));
}
  
}
