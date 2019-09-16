import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck {
	public List<Card> deck;
	
	/**
	 * Initializes a standard 52 card deck, meaning 52 unique cards
	 * each with one value (from 1 to 13) and one suit (spades, clubs
	 * hearts or diamonds)
	 */
	public Deck() {
		deck = new LinkedList<Card>();
		reset();
	}
	
	/**
	 * Shuffles the deck into a random permutation, regardless of current size
	 */
	public void shuffle() {
		Random rand = new Random();
		for(int i = 0; i < deck.size(); i++) {
			Collections.swap(deck, i, i + rand.nextInt(deck.size() - i));
		}
	}
	
	/**
	 * Resets the deck to its standard 52 cards and shuffles it
	 */
	public void reset() {
		for(int i = 1; i <= 13; i++) {
			for(int j = 0; j < 4; j++) {
				deck.add(new Card(i, j));
			}
		}
		shuffle();
	}
	
	/**
	 * Deals top card of the deck
	 */
	public Card deal() {
		if(deck.size() < 1) return null;
		return deck.remove(0);
	}
}
