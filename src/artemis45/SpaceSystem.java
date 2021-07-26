/**
 * 
 */
package artemis45;

import java.util.ArrayList;

/**
 * This class represents the Space Systems in the ArtemisLite game
 * 
 * @author rebekkahohare
 *
 */
public class SpaceSystem {

	// constants

	private static int LOWER_MAX_ELEMENTS = 2;
	private static int UPPER_MAX_ELEMENTS = 3;

	// instance vars

	/**
	 * name of system
	 */
	private SpaceSystemName spaceSystemName;

	/**
	 * max elements that space system can hold
	 */
	private int maxElements;

	/**
	 * elements that space system is made up of
	 */
	private ArrayList<Element> elements;

	// constructors

	/**
	 * Default Constructor
	 */
	public SpaceSystem() {

	}

	/**
	 * Constructor with parameters that take maxElement and spaceSystemName as args
	 * 
	 * @param maxElements
	 * @param spaceSystemName
	 */
	public SpaceSystem(int maxElements, SpaceSystemName spaceSystemName) {
		this.setMaxElements(maxElements);
		this.spaceSystemName = spaceSystemName;
		elements = new ArrayList<>(maxElements);
	}

	// methods

	/**
	 * @return the maxElements
	 */
	public int getMaxElements() {
		return maxElements;
	}

	/**
	 * Throws IllegalArgumentException
	 * 
	 * @param maxElements the maxElements to set - must be a value between
	 *                    {@value #LOWER_MAX_ELEMENTS} and
	 *                    {@value #UPPER_MAX_ELEMENTS} (inclusive)
	 */
	public void setMaxElements(int maxElements) throws IllegalArgumentException {

		if (maxElements < LOWER_MAX_ELEMENTS || maxElements > UPPER_MAX_ELEMENTS) {

			throw new IllegalArgumentException("INVALID INPUT - MAX ELEMENTS MUST BE A VALUE BETWEEN "
					+ LOWER_MAX_ELEMENTS + " AND " + UPPER_MAX_ELEMENTS + " INCLUSIVE");

		} else {

			this.maxElements = maxElements;
		}

	}

	/**
	 * @return the elements
	 */
	public ArrayList<Element> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}

	/**
	 * @return the spaceSystemName
	 */
	public SpaceSystemName getSpaceSystemName() {
		return spaceSystemName;
	}

	/**
	 * @param spaceSystemName the spaceSystemName to set
	 */
	public void setSpaceSystemName(SpaceSystemName spaceSystemName) {
		this.spaceSystemName = spaceSystemName;
	}

	/**
	 * Adds an Element to the ArrayList of Elements
	 * 
	 * @param element
	 */
	public void addElement(Element element) {

		if ((element.getSpaceSystemName() == this.spaceSystemName) && (this.maxElements > elements.size())) {
			elements.add(element);
		} else if (((element.getSpaceSystemName() != this.spaceSystemName)) || (this.maxElements == elements.size())) {
			System.out.print(
					"Element " + element.getName() + " not added to the Space System " + this.spaceSystemName + "...");
			if (this.maxElements == elements.size()) {
				System.out.println("The maximum number of Elements (" + this.maxElements
						+ ") had already been added to the System.");
			} else if (element.getSpaceSystemName() != this.spaceSystemName) {
				System.out.println("There was a Space System name mismatch.");
			}
		}
	}

}
