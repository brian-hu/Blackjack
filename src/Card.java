
public class Card {
	/** Integer representation of the card suit "Spades" */
	public static final int SPADES = 0; 
	/** Integer representation of the card suit "Clubs" */
	public static final int CLUBS = 1;
	/** Integer representation of the card suit "Hearts" */
	public static final int HEARTS = 2;
	/** Integer representation of the card suit "Diamonds" */
	public static final int DIAMONDS = 3;
	
	private int value;
	private int suit;
	
	/**
	 * Constructs a Card with value (1 to 13) and a suit integer representing
	 * spades, clubs, hearts or diamonds based on the static integers defined
	 * on the top of the class
	 * @param value of the card from 1 to 13, inclusive, with 11, 12, 13, and 1 representing
	 * Jack, Queen, King, and Ace respectively
	 * @param suit of the card as an integer, corresponding to the predefined static
	 * values in the class
	 * @throws InvalidCardValueException if value is not between 1 and 13, invlusive
	 * @throws InvalidCardSuitException  if suit value is not between 0 and 3, inclusive
	 */
	public Card(int value, int suit) throws InvalidCardValueException, InvalidCardSuitException {
		this.value = value;
		if(value < 1 || value > 13) throw new InvalidCardValueException("Card value must be between 1 and 13, inclusive");
		this.suit = suit;
		if(suit < 0 || suit > 3) throw new InvalidCardSuitException("Card suit value must be between 0 and 3, inclusive");
		
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the suit
	 */
	public int getSuit() {
		return suit;
	}

	/**
	 * @param suit the suit to set
	 */
	public void setSuit(int suit) {
		this.suit = suit;
	}
	
	public String toString() {
		String cardVal = getValueString();
		String cardSuit = getSuitString();
		return cardVal + " of " + cardSuit;
	}
	
	public String getValueString() {
		switch(value) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return String.valueOf(value);
		}
	}
	
	public String getSuitString() {
		switch(suit) {
		case 0:
			return "spades";
		case 1:
			return "clubs";
		case 2:
			return "hearts";
		case 3:
			return "diamonds";
		default:
			return "";
	}
	}
}
