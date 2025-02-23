package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Song;
import model.Song.Rating;

class SongTests {

	@Test
	void testGetSongTitle() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals("Daydreamer", song.getSongTitle(), "Song Title should be: Daydreamer");
	}
	
	@Test
	void testGetArtist() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals("Adele", song.getArtist(), "Song Artist should be: Adele");
	}
	
	@Test
	void testGetAlbumTitle() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals("19", song.getAlbumTitle(), "Song Album Title should be: 19");
	}
	
	@Test
	void testGetDefaultRating() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals(Rating.UNRATED, song.getRating(), "Initially, rating is set to UNRATED");
	}
	
	@Test
	void testGetDefaultIsFavorite() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals(false, song.isFavorite(), "Initially, favorite is set to false");
	}
	
	@Test
	void testSetRating() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals(Rating.UNRATED, song.getRating(), "Initially, rating is set to UNRATED");
		song.setRating(Rating.FIVE_STAR);
		assertEquals(Rating.FIVE_STAR, song.getRating(), "Afterwards, rating is set to FIVE_STAR");
		assertEquals(true, song.isFavorite(), "When rating is FIVE_STAR, it's marked as favorite");

  @Test
	void testMarkingASongAsFavorite() {
		Song song = new Song("Daydreamer", "Adele", "19");
		assertEquals(false, song.isFavorite(), "Initially, favorite is set to false");
		song.markAsFavorite();
		assertEquals(true, song.isFavorite(), "Afterwards, favorite is set to true");
	}

}
