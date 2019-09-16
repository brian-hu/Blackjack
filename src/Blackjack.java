import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Blackjack {
	
	private static final int STARTING_MONEY = 500;
	private static final int MAX_PLAYERS = 10;
	private static List<Player> players = new ArrayList<Player>();
	private static Deck deck = new Deck();
	private static Scanner sc = new Scanner(System.in);
	/**
	 * Runs the main application in the console
	 * @param args
	 */
	public static void main(String[] args) {
		clearScreen();
		
		
		boolean playAgain = true;
		boolean firstTime = true;
		
		players.add(new Dealer());
		//introduction
		System.out.println("Hi, welcome to Blackjack!");
		System.out.println("How many people will be playing?");
		addPlayers(MAX_PLAYERS);
		
		//main game
		while(playAgain) {
			setupGame();
			//only run this section to add more players if it is after the first round
			if(!firstTime) {
				if(askYesNo("Do you want to add more players?(y/n)")) {
					System.out.println("How many more?");
					addPlayers(MAX_PLAYERS - players.size());
				}
			}
			System.out.println();
			
			//deal cards and display them on the screen
			deal(true);
			printPlayers(false);
			while(!askYesNo("Is everyone ready? (y/n)"));
			//allow each player to privately view their cards and make a bet
			System.out.println("Now, we will start betting.\n");
			for(int i = 1; i < players.size(); i++) {
				Player p = players.get(i); //assign Player a temp variable for ease of reading
				if(players.size() != 2) { //only make sure that the right player is looking at the screen if it is not single player
					System.out.println("Make sure " + p.getName() + " is the only one seeing the screen.");
					while(!askYesNo("Is " + p.getName() + " the only one viewing the screen? (y/n)"));
				}
				clearScreen();
				printHeader();
				System.out.println(p.cardsVisibleToString());
				System.out.println("How much would you like to bet?");
				int bet = 0;
				while(bet < 1 || bet > p.getMoney()) {
					try { //try-catch assures that the user enters a valid integer for the bet
						bet = sc.nextInt();
						if(bet < 1 || bet > p.getMoney()) System.out.println("Please enter a valid bet");
					} catch(InputMismatchException e) {
						System.out.println("Please enter a valid bet as an integer");
					}
					sc.nextLine();
				}
				p.setBet(bet);
				p.setMoney(p.getMoney() - bet);
				clearScreen();
			}
			//start the actual game of blackjack one player at a time
			System.out.println("Let's play Blackjack!\n");
			for(int i = 1; i < players.size(); i++) {
				Player p = players.get(i); //assign player to temp variable for ease of reading
				if(players.size() != 2) { //only make sure that the right player is looking at the screen if it is not single player
					System.out.println("Make sure " + p.getName() + " is the only one seeing the screen.");
					while(!askYesNo("Is " + p.getName() + " the only one viewing the screen? (y/n)"));
				}
				boolean hit = true; //boolean holding the status of the current player (hit = true, stand = false), default is true
				while(hit && !p.isBust() && !p.blackjack()) {
					clearScreen();
					printHeader();
					System.out.println(p.cardsVisibleToString()); //prints out current player's cards and sum
					if(askBinaryQuestion("Hit or stand? (h/s)", "h", "s")) {
						p.addCard(deck.deal());
					}
					else {
						hit = false;
					}
				}
				printHeader();
				System.out.println(p.cardsVisibleToString());
				if(p.isBust()) System.out.println("Unfortunately, you busted."); //tells player that he/she busted
				System.out.println(); //spacer
				while(!askYesNo("Ready to continue? (y/n)")); //buffer screen to allow player to process info
				clearScreen();
			}
			//dealer goes last, so his turn is after all the other players
			dealersTurn();
			//settle the bets and print the final results
			settlement();
			printPlayers(true);
			//determine if the players want to continue w/ the same players, reset players or quit entirely
			if(askYesNo("Good game! Want to keep playing? (y/n)")) {
				if(askYesNo("Do you want to reset the players? (y/n)")) {
					players.clear();
					players.add(new Dealer());
				}
				else firstTime = false;
				playAgain = true;
			}
			else playAgain = false;
		}
		
	}
	/**
	 * Sets up the game so that all the players have empty hands
	 * and the deck is full and shuffled
	 * @param deck is the deck of cards
	 */
	private static void setupGame() {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).emptyHand();
			players.get(i).setBet(0);
		}
		deck.reset();
	}
	/**
	 * Asks users how many players are to be added to the game, up to a maximum of 10 total,
	 * including the dealer, then asks user to provide names to identify each player
	 * @param upBound is the maximum number of players that can be added
	 */
	private static void addPlayers(int upBound) {
		int numOfNewPlayers = 0;
		while(numOfNewPlayers < 1 || numOfNewPlayers > upBound) {
			try {
				numOfNewPlayers = sc.nextInt();
				if(numOfNewPlayers < 1 || numOfNewPlayers > upBound) System.out.println("Please enter a valid number of players");
			} catch(InputMismatchException e) {
				System.out.println("Please enter a valid number of players");
			}
			sc.nextLine();
		}
		int curSize = players.size();
		for(int i = players.size(); i < curSize + numOfNewPlayers; i++) {
			String name = "";
			System.out.println("What should player " + i + " be called?");
			while(name.length() > 10 || name.length() < 1) {
				name = sc.nextLine();
				if(name.length() > 10 || name.length() < 1) System.out.println("Please enter a name with at least 1 character and up to 10 characters");
			}
			players.add(new Player(name, STARTING_MONEY));
		}
	}
	/**
	 * Deals one card to each player in the list of players
	 * @param firstHand determines whether or not to recursively call this function 
	 * to deal another hand, as the first hand dealt in blackjack is two cards
	 */
	private static void deal(boolean firstHand) {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).addCard(deck.deal());
		}
		if(firstHand) deal(!firstHand);
	}
	/**
	 * Prints all the players, their current amount of money, and their cards
	 * @param showSums determines whether or not to print the sums of each player's cards
	 */
	private static void printPlayers(boolean showSums) {
		printHeader();
		for(int i = 0; i < players.size(); i++) {
			if(!showSums) System.out.println(players.get(i).cardsHiddenToString());
			else System.out.println(players.get(i).cardsVisibleToString());
		}
		System.out.println();
	}
	/**
	 * Asks a yes/no question to the user and returns the response as a boolean
	 * @param question is the string containing the question
	 * @return true if yes, false if no
	 */
	private static boolean askYesNo(String question) {
		return askBinaryQuestion(question, "y", "n");
	}
	/**
	 * Asks generic binary question to the user and returns the response as a boolean
	 * @param question is the string containing the question
	 * @param posResponse is the affirmative response to the question
	 * @param negResponse is the negative response to the question
	 * @return true if affirmative, false if negative
	 */
	private static boolean askBinaryQuestion(String question, String posResponse, String negResponse) {
		System.out.println(question);
		String response = "";
		while(!(response.equalsIgnoreCase(posResponse) || response.equalsIgnoreCase(negResponse))) {
			response = sc.nextLine();
			if(!(response.equalsIgnoreCase(posResponse) || response.equalsIgnoreCase(negResponse))) System.out.println("Please enter a valid response character");
		}
		return response.equalsIgnoreCase(posResponse);
	}
	/**
	 * Automates the dealer's turn based on the following rule:
	 * 	If the maximum total (A = 11) of the dealer's hand is under
	 * 	16, then the dealer must hit, otherwise, the dealer must stand
	 */
	private static void dealersTurn() {
		Dealer dealer = (Dealer) players.get(0);
		while(!dealer.isBust() && dealer.sumOfCards() < 17) {
			dealer.addCard(deck.deal());
		}
	}
	/**
	 * Pays the proper amount to each player based on whether or not the player
	 * beat, tied or lost to the dealer
	 */
	private static void settlement() {
		Player dealer = players.get(0);
		for(int i = 1; i < players.size(); i++) {
			Player p = players.get(i);
			int moneyAdded = 0;
			if(!p.isBust()) {
				if(dealer.isBust()) {
					moneyAdded = p.blackjack() ? 3 * p.getBet() : 2 * p.getBet();
				}
				else if(dealer.sumOfCards() == p.sumOfCards()) {
					moneyAdded = p.getBet();
				}
				else {
					moneyAdded = (dealer.sumOfCards() < p.sumOfCards()) ? 2 * p.getBet() : 0;
				}
			}
			p.setMoney(p.getMoney() + moneyAdded);
		}
	}
	/**
	 * Prints a standard header which comes in this format:
	 * Name:      Money: Bets:  Cards:
	 */
	public static void printHeader() {
		System.out.println(String.format("%-10s %-6s %-6s Cards:", "Name:", "Money:", "Bets:"));
	}
	/**
	 * "Clears" the screen in the terminal (DOES NOT WORK ON JAVA CONSOLE)
	 */
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	} 
}
