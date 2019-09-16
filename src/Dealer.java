
public class Dealer extends Player {
	/**
	 * Constructs a Dealer
	 */
	public Dealer() {
		super("Dealer", 0);
	}
	/**
	 * Overrides the Player cardsHiddenToString method, customizing it so that
	 * the Dealer's "Money" and "Bet" sections are blank
	 */
	public String cardsHiddenToString() {
		String res = String.format("%-10s %-6s %-6s", getName(), "", "");
		for(int i = 0; i < cards.size(); i++) {
			if(i == 0) res += String.format(" %-2s", cards.get(i).getValueString());
			else res += " -";
		}
		return res;
	}
	/**
	 * Overrides the Player cardsVisibleToString method, customizing it so that
	 * the Dealer's "Money" and "Bet" sections are blank
	 */
	public String cardsVisibleToString() {
		String res = String.format("%-10s %-6s %-6s", getName(), "", "");
		for(int i = 0; i < cards.size(); i++) {
			res += String.format(" %-2s", cards.get(i).getValueString());
		}
		res += "   Sum: " + sumOfCards() + (isBust() ? " BUST" : "");
		return res;
	}
}
