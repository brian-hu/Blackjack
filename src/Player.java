import java.util.LinkedList;
import java.util.List;

public class Player {
	protected List<Card> cards;
	private String name;
	private int money;
	private int bet;
	
	public Player(String name, int money) {
		cards = new LinkedList<Card>();
		bet = 0;
		this.name = name;
		this.money = money;
	}
	/**
	 * Adds provided card to the player's hand
	 * @param card to be added
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	/**
	 * Resets the player's hand to empty
	 */
	public void emptyHand() {
		cards = new LinkedList<Card>();
	}
	/**
	 * Creates a string with the player's name, money and cards, except cards
	 * are all invisible (" - ") except for the first one
	 * @return the string
	 */
	public String cardsHiddenToString() {
		String res = String.format("%-10s %-6d %-6d", name, money, bet);
		for(int i = 0; i < cards.size(); i++) {
			if(i == 0) res += String.format(" %-2s", cards.get(i).getValueString());
			else res += " -";
		}
		return res;
	}
	/**
	 * Creates a string with the player's name, money, cards and total sum of cards
	 * @return the string
	 */
	public String cardsVisibleToString() {
		String res = String.format("%-10s %-6d %-6d", name, money, bet);
		for(int i = 0; i < cards.size(); i++) {
			res += String.format(" %-2s", cards.get(i).getValueString());
		}
		res += "   Sum: " + sumOfCards() + (isBust() ? " BUST" : "");
		return res;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	/**
	 * Sums up the cards in the player's hand, automatically selecting values for the aces
	 * if the player is bust
	 * @return
	 */
	public int sumOfCards() {
		int sum = 0;
		int aceCounter = 0;
		for(int i = 0; i < cards.size(); i++) {
			int value = cards.get(i).getValue();
			if(value > 10) value = 10;
			if(value == 1) {
				value = 11;
				aceCounter++;
			}
			sum += value;
		}
		while(aceCounter > 0 && sum > 21) {
			sum -= 10;
			aceCounter--;
		}
		return sum;
	}

	/**
	 * @return whether the player has busted or not
	 */
	public boolean isBust() {
		return sumOfCards() > 21;
	}
	/**
	 * Determines if the player has blackjack or not
	 * @return true if player has blackjack, false if not
	 */
	public boolean blackjack() {
		return sumOfCards() == 21 && cards.size() == 2;
	}
	/**
	 * @return the bet
	 */
	public int getBet() {
		return bet;
	}
	/**
	 * @param bet the bet to set
	 */
	public void setBet(int bet) {
		int netDiff = this.bet - bet;
		this.bet = bet;
	}

}
