/**
 * 
 */
package artemis45;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * This class represents the board play in the ArtemisLite game
 * 
 * @author rebekkahohare
 *
 */
public class BoardPlay {
	
	// constants 
	
	/**
	 * Player's starting resources 
	 */
	private static final int INITIAL_RESOURCES = 4000;
	
	/**
	 * Player's starting position on the board
	 */
	private static final int START_SQUARE = 0;
	
	/**
	 * Number of dice
	 */
	private static final int NUMBER_OF_DICE = 2;
	
	
	// instance vars 
	
	private ArrayList<Player> players;
	private ArrayList<Square> squares;
	private int playerSystemLimit;
	
	// constructors 
	
	/**
	 * Default constructor
	 */
	public BoardPlay() {
		
	}
	
	/**
	 * Constructor with args 
	 * @param players - ArrayList
	 * @param squares - ArrayList
	 */
	public BoardPlay(ArrayList<Player> players, ArrayList<Square> squares) {
		this.players = players;
		this.squares = squares;
		setPlayerSystemLimit();
	}
	
	// methods
	
	/**
	 * 
	 * @return the ArrayList of players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * 
	 * @param players the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	/**
	 * 
	 * @return the ArrayList of squares
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}
	
	/**
	 * 
	 * @param squares the squares to set
	 */
	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}
	
	/**
	 * 
	 * @return playerSystemLimit
	 */
	public int getPlayerSystemLimit() {
		return playerSystemLimit;
	}

	/**
	 * sets the maximum number of systems any one player can 
	 * manage during the game 
	 * 
	 */
	public void setPlayerSystemLimit() { 
	    
		HashSet<SpaceSystemName> systems = new HashSet<SpaceSystemName>();
		
		for(Square s : squares) {
			if(s instanceof Element) {
				systems.add(((Element)s).getSpaceSystemName());			
			}
		}
		
		this.playerSystemLimit = (int) Math.ceil((double)(systems.size()/(double)this.players.size()));
		
	}
		
		
	/**
	 * Starts the game by setting each players board position to the start 
	 * square ({@value #START_SQUARE}) and allocating each player 
	 * initial resources ({@value #INITIAL_RESOURCES})
	 */
	public void startGame() {
		System.out.println("Preparing players for commencement....\n\nAllocating initial Government Funds to each player...");
		for(Player player : this.players) {
			player.setBoardPosition(START_SQUARE);
			player.setResources(INITIAL_RESOURCES);	
		}	
	}
	
	/**
	 * Rolls the dice and returns the sum of {@value #NUMBER_OF_DICE} dice values
	 * @return total of dice rolls
	 */	
	public int rollDice() {

		int totalRoll = 0;

		Random diceRoll = new Random();

		for (int loop = 1; loop <= NUMBER_OF_DICE; loop++) {

			int roll = diceRoll.nextInt(6);
			roll++;

			System.out.println("Roll for dice " + (loop) + ": " + roll);

			totalRoll += roll;
		}
		System.out.println("You move " + totalRoll + " spaces..");
		
		return totalRoll;
	}
	
	/**
	 * Checks the development status of board
	 * @param squares - ArrayList
	 * @return boolean - returns true if all Elements are 
	 * fully developed 
	 */
	public boolean developmentStatus(ArrayList<Square> squares) {
		
		boolean fullyDevelopedBoard = true;
		
		for(Square s : squares) {
			if(s instanceof Element) {
				if(((Element) s).isFullyDeveloped()!= true) {
					fullyDevelopedBoard = false;
					break;
				}
			}
		}
		
		return fullyDevelopedBoard;
		
	}
	
	/**
	 * Returns the Square at the specified index
	 * 
	 * @param playerPosition - int
	 * @return Square
	 */
	
	public Square squareSearch(int playerPosition) {
		
		return squares.get(playerPosition);
	}
		
}
