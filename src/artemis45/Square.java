/**
 * 
 */
package artemis45;

/**
 * This class represents the object square in the board game
 * 
 * @author Curtis Martin
 * 
 */
public class Square {

	// instance vars
	private String name;
	private int number;

	// constructors
	/**
	 * Default square constructor
	 */
	public Square() {

	}

	/**
	 * Overloaded constructor with args
	 * 
	 * @param name
	 * @param number
	 */
	public Square(String name, int number) {
		this.name = name;
		this.number = number;
	}

	// getters and setters
	/**
	 * @return the name to get
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
	 * @return the number to get
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
}
