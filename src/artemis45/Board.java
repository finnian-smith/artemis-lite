/**
 * 
 */
package artemis45;

import java.util.ArrayList;

/**
 * This class represents the board in the ArtemisLite game
 * 
 * @author rebekkahohare
 *
 */
public class Board {

	// constants
	private static int LOWER_MAX_SQUARES = 8;
	private static int UPPER_MAX_SQUARES = 18;

	private static int LOWER_MAX_SYSTEMS = 2;
	private static int UPPER_MAX_SYSTEMS = 5;

	// instance vars
	/**
	 * max number of squares that can exist on a board
	 */
	private int maxSquares; // to factor in start and rest square

	/**
	 * max number of systems that can exist on board
	 */
	private int maxSpaceSystems;

	/**
	 * ArrayList of squares
	 */
	private ArrayList<Square> squares;

	/**
	 * ArrayList of systems
	 */
	private ArrayList<SpaceSystem> spaceSystems;

	// constructors
	/**
	 * Default constructor
	 */
	public Board() {

	}

	/**
	 * Constructor that takes the max number of space systems allowed on the board
	 * as an arg
	 * 
	 * @param maxSpaceSystems
	 */
	public Board(int maxSquares, int maxSpaceSystems) {
		this.setMaxSquares(maxSquares);
		this.setMaxSpaceSystems(maxSpaceSystems);
		spaceSystems = new ArrayList<SpaceSystem>();
		squares = new ArrayList<Square>();
	}

	// methods
	/**
	 * @return the maxSquares
	 */
	public int getMaxSquares() {
		return maxSquares;
	}

	/**
	 * Throws IllegalArgumentException
	 * 
	 * @param maxSquares the maxSquares to set - must be a value between
	 *                   {@value #LOWER_MAX_SQUARES} and {@value #UPPER_MAX_SQUARES}
	 *                   (inclusive)
	 */
	public void setMaxSquares(int maxSquares) throws IllegalArgumentException {

		if (maxSquares < LOWER_MAX_SQUARES || maxSquares > UPPER_MAX_SQUARES) {
			throw new IllegalArgumentException("INVALID INPUT - MAX SQUARES MUST BE A VALUE BETWEEN "
					+ LOWER_MAX_SQUARES + " AND " + UPPER_MAX_SQUARES + " INCLUSIVE");
		} else {
			this.maxSquares = maxSquares;
		}

	}

	/**
	 * @return the maxSpaceSystems
	 */
	public int getMaxSpaceSystems() {
		return maxSpaceSystems;
	}

	/**
	 * Throws IllegalArgumentException
	 * 
	 * @param maxSystems the maxSystems to set - must be a value between
	 *                   {@value #LOWER_MAX_SYSTEMS} and {@value #UPPER_MAX_SYSTEMS}
	 *                   (inclusive)
	 */
	public void setMaxSpaceSystems(int maxSpaceSystems) {

		if (maxSpaceSystems < LOWER_MAX_SYSTEMS || maxSpaceSystems > UPPER_MAX_SYSTEMS) {
			throw new IllegalArgumentException("INVALID INPUT - MAX SYSTEMS MUST BE A VALUE BETWEEN "
					+ LOWER_MAX_SYSTEMS + " AND " + UPPER_MAX_SYSTEMS + " INCLUSIVE");
		} else {
			this.maxSpaceSystems = maxSpaceSystems;
		}

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}

	/**
	 * 
	 * @param squares
	 */
	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	/**
	 * @return the spaceSystems
	 */
	public ArrayList<SpaceSystem> getSpaceSystems() {
		return spaceSystems;
	}

	/**
	 * @param spaceSystems the spaceSystems to set
	 */
	public void setSpaceSystems(ArrayList<SpaceSystem> spaceSystems) {
		this.spaceSystems = spaceSystems;
	}

	/**
	 * Adds a space system to the board (if max number of space systems has not been
	 * reached)
	 * 
	 * @param spaceSystem
	 */
	public void addSpaceSystem(SpaceSystem spaceSystem) {

		if (this.maxSpaceSystems > spaceSystems.size()) {
			spaceSystems.add(spaceSystem);
		} else {
			System.out.println(
					"Max number of Space Systems (" + this.maxSpaceSystems + ") have been added to the board...");
		}
	}

	/**
	 * Adds a square to the board (if max number of squares has not been reached)
	 * 
	 * @param square
	 */
	public void addSquare(Square square) {

		if (this.maxSquares > squares.size()) {
			squares.add(square);
		} else {
			System.out.print(square.getName() + " square not added to the board...");
			System.out
					.println("Max number of squares (" + this.maxSquares + ") have already been added to the board...");
		}
	}

}