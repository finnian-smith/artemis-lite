/**
 * 
 */
package artemis45;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * This class represents the player in the ArtemisLite game
 * @author finnian
 *
 */
public class Player {

	// constants
	private static final int LOWER_PLAYER_ID = 1;
	private static final int UPPER_PLAYER_ID = 4;
	private static final int LOWER_RESOURCE_LIMIT = 0;
	private static final int LOWER_BOARD_POSITION_LIMIT = 0;
	private static final int UPPER_BOARD_POSITION_LIMIT = 11;
	private static final int PASS_GO_RESOURCES = 200;

	// instance vars
	private int playerId;
	private Agency agency;
	private int resources;
	private int boardPosition; // default to 0 to represent start position
	private ArrayList<Element> ownedElements;

	// constructors
	/**
	 * Default constructor
	 */
	public Player() {
		
	}

	/**
	 * Constructor with PlayerID and Agency as args
	 * 
	 * @param playerId      - id of the player (must be between
	 *                      {@value #LOWER_PLAYER_ID} - {@value #UPPER_PLAYER_ID}
	 *                      inclusive)
	 * @param agency        - name of the player  
	 */
	public Player(int playerId, Agency agency) {
		this.setPlayerId(playerId);
		this.setAgency(agency);
		this.ownedElements = new ArrayList<Element>();
	}

	// getters and setters methods
	/**
	 * 
	 * @return the player's Id
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Sets the player's Id, must be between {@value #LOWER_PLAYER_ID} - {@value #UPPER_PLAYER_ID}
	 * 
	 * @param playerId
	 * 
	 * @throws IllegalArgumentException
	 */
	public void setPlayerId(int playerId) throws IllegalArgumentException {
		if (playerId >= LOWER_PLAYER_ID && playerId <= UPPER_PLAYER_ID) {
			this.playerId = playerId;
		} else {
			throw new IllegalArgumentException("Player ID must be between " + LOWER_PLAYER_ID + " - " + UPPER_PLAYER_ID
					+ " (inclusive). You have entered: " + playerId + ".");
		}
	}

	/**
	 * 
	 * @return the player's name
	 */
	public Agency getAgency() {
		return agency;
	}

	/**
	 * Sets the player's agency (using enum)
	 * 
	 * @param Agency
	 */

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	/**
	 * 
	 * @return the amount of resources the player has
	 */
	public int getResources() {
		return resources;
	}

	/**
	 * Sets the amount of resources the player has, must be greater than/equal to {@value #LOWER_RESOURCE_LIMIT}
	 * 
	 * @param resources
	 * @throws IllegalArgumentException
	 */

	public void setResources(int resources) throws IllegalArgumentException {
		if (resources >= LOWER_RESOURCE_LIMIT) {
			this.resources = resources;
		} else {
			throw new IllegalArgumentException(
					"Error. You cannot enter a resource value below " + LOWER_RESOURCE_LIMIT + ".");
		}

	}

	/**
	 * 
	 * @return the position of the player on the board
	 */
	public int getBoardPosition() {
		return boardPosition;
	}

	/**
	 * Sets the board position of the player - {@value #LOWER_BOARD_POSITION_LIMIT} represents start, 
	 * {@value #UPPER_BOARD_POSITION_LIMIT} represents the final square
	 * 
	 * @param boardPosition - int representing the player's position on board
	 * 
	 * @throws IllegalArgumentException
	 */
	public void setBoardPosition(int boardPosition) throws IllegalArgumentException {
		if (boardPosition >= LOWER_BOARD_POSITION_LIMIT && boardPosition <= UPPER_BOARD_POSITION_LIMIT) {
			this.boardPosition = boardPosition;
		} else {
			throw new IllegalArgumentException("Board position must be between " + LOWER_BOARD_POSITION_LIMIT + " - "
					+ UPPER_BOARD_POSITION_LIMIT + " (inclusive). You have entered " + boardPosition + ".");
		}
	}

	
	/**
	 * 
	 * @param ownedElements
	 */
	public void setOwnedElements(ArrayList<Element> ownedElements){
		
		this.ownedElements = ownedElements;
		
	}
	
	/**
	 * 
	 * @return ownedElements
	 */
	public ArrayList<Element> getOwnedElements() {
		return ownedElements;
	}


	// methods
	/**
	 * Adds resources
	 * 
	 * @param resources - int
	 */
	public void addResources(int resources) {
		this.resources += resources;
	}

	/**
	 * Subtracts resources
	 * 
	 * @param cost
	 */
	public void subtractResources(int cost) {
		if (cost > resources) {
			resources = LOWER_RESOURCE_LIMIT;
		} else {
			resources -= cost;
		}
	}
	
	/**
	 * Adds an Element to ownedElements
	 * 
	 * @param element
	 */
	public void addElement(Element element) {
		this.ownedElements.add(element);
	}
	
	/**
	 * Checks if a player is managing the max number of Systems 
	 * 
	 * @return 
	 */
	public boolean systemLimitCheck(int systemLimit) {
		
		HashSet<SpaceSystemName> systems = new HashSet<SpaceSystemName>();
		boolean systemLimitReached = false;
		
		for(Element e: ownedElements) {
			systems.add(e.getSpaceSystemName());
		}
		
		if(systems.size()==systemLimit) {
			systemLimitReached = true;
		}
		return systemLimitReached;
		
	}

	/**
	 * 
	 * Moves the player's position on the board.
	 * 
	 * If the sum of the player's current position and numberToMove is greater than
	 * {@value #UPPER_BOARD_POSITION_LIMIT}, {@value #UPPER_BOARD_POSITION_LIMIT}
	 * plus 1 is subtracted from the player's position and the player receives
	 * {@value #PASS_GO_RESOURCES} resources
	 * 
	 * @param numberToMove
	 */
	public void movePosition(int numberToMove) {

		if ((this.boardPosition + numberToMove) > UPPER_BOARD_POSITION_LIMIT) {
			this.boardPosition = (this.boardPosition + numberToMove) - (UPPER_BOARD_POSITION_LIMIT + 1);

			if (this.boardPosition != LOWER_BOARD_POSITION_LIMIT) {
				System.out.println("\nYou have passed 'Mission Control'.");
				passGo();
			}

		} else {
			
			this.boardPosition += numberToMove;
			
		}
	}
	
	/**
	 * Adds {@value #PASS_GO_RESOURCES} to player's resources and outputs message that this has happened
	 */
	public void passGo() {
		
		addResources(PASS_GO_RESOURCES);
		System.out.println("You receive a boost of " + PASS_GO_RESOURCES + " Government Funds.");
		System.out.println("Your Government funds are now at " +this.resources+ ".");
		
	}
	
	/**
	 * Prints all owned elements and their current dev stage to screen
	 * 
	 * @param ownedElements
	 */
	public void printElements(ArrayList<Element> ownedElements) {
		
		System.out.printf("Elements Owned    : ");
		
		Collections.sort(ownedElements, new CompareSquareNumber());
		
		for (int loop = 0; loop < ownedElements.size(); loop++) {
			
			if (loop != ownedElements.size() - 1) {
				System.out.printf("[" + ownedElements.get(loop).getName() + " (" + ownedElements.get(loop).getSpaceSystemName() + ") / Dev Stage - " + ownedElements.get(loop).getDevStage() + "], ");
			} else {
				System.out.printf("[" + ownedElements.get(loop).getName() + " (" + ownedElements.get(loop).getSpaceSystemName() + ") / Dev Stage - " + ownedElements.get(loop).getDevStage() + "]");
			}
		}
	}
	
	/**
	 * Displays all player details to screen
	 */
	public void displayAll() {
		
		System.out.println("Agency - " + this.agency + "__________");
		System.out.println("Resources         : " + this.resources);
		printElements(ownedElements);
		System.out.printf("\nBoard Position    : " + (this.boardPosition + 1) + " - ");
		
	}
	

}
