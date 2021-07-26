/**
 * 
 */
package artemis45;

/**
 * This class represents the elements in the ArtemisLite game
 * 
 * @author Curtis Martin
 *
 */
public class Element extends Square {

	// constants
	private static int LOWER_DEV_STAGE = 0; // just taken charge, then stage 1,2,3,4
	private static int UPPER_DEV_STAGE = 4; // 4 devs complete -> finished
	private static double COMMISSION_RATE = 0.5;
	private static double INCREASED_COST_RATE = 1.25;

	// instance vars
	private SpaceSystemName spaceSystemName;
	private int takeChargeCost;
	private int increasedTakeChargeCost;
	private int d1Cost;
	private int d2Cost;
	private int d3Cost;
	private int majorDevelopmentCost;
	private int devStage;
	private int comCost;
	private boolean fullyDeveloped;
	private Player player;

	// methods
	/**
	 * Default constructor
	 */
	public Element() {

	}

	/**
	 * Constructor with args
	 * 
	 * @param name
	 * @param number
	 * @param system
	 * @param takeChargeCost
	 * @param d1Cost
	 * @param d2Cost
	 * @param d3Cost
	 * @param majorDevelopmentCost
	 */
	public Element(String name, int number, SpaceSystemName spaceSystemName, int takeChargeCost, int d1Cost, int d2Cost,
			int d3Cost, int majorDevelopmentCost) {
		super(name, number);
		this.spaceSystemName = spaceSystemName;
		this.takeChargeCost = takeChargeCost;
		this.d1Cost = d1Cost;
		this.d2Cost = d2Cost;
		this.d3Cost = d3Cost;
		this.majorDevelopmentCost = majorDevelopmentCost;
		this.setComCost();
		this.setIncreasedTakeChargeCost();
	}

	/**
	 * @return the spaceSystem
	 */
	public SpaceSystemName getSpaceSystemName() {
		return spaceSystemName;
	}

	/**
	 * @param system the spaceSystem to set
	 */
	public void setSpaceSystemName(SpaceSystemName spaceSystemName) {
		this.spaceSystemName = spaceSystemName;
	}

	/**
	 * @return the takeChargeCost
	 */
	public int getTakeChargeCost() {
		return takeChargeCost;
	}

	/**
	 * @param takeChargeCost the takeChargeCost to set
	 */
	public void setTakeChargeCost(int takeChargeCost) {
		this.takeChargeCost = takeChargeCost;
	}

	/**
	 * @return the increasedTakeChargeCost
	 */
	public int getIncreasedTakeChargeCost() {
		return increasedTakeChargeCost;
	}

	/**
	 * @param increasedTakeChargeCost the increasedTakeChargeCost to set
	 */
	public void setIncreasedTakeChargeCost() {

		this.increasedTakeChargeCost = (int) (this.takeChargeCost * INCREASED_COST_RATE);

	}

	/**
	 * @return the d1Cost
	 */
	public int getD1Cost() {
		return d1Cost;
	}

	/**
	 * @param d1Cost the d1Cost to set
	 */
	public void setD1Cost(int d1Cost) {
		this.d1Cost = d1Cost;
	}

	/**
	 * @return the d2Cost
	 */
	public int getD2Cost() {
		return d2Cost;
	}

	/**
	 * @param d2Cost the d2Cost to set
	 */
	public void setD2Cost(int d2Cost) {
		this.d2Cost = d2Cost;
	}

	/**
	 * @return the d3Cost
	 */
	public int getD3Cost() {
		return d3Cost;
	}

	/**
	 * @param d3Cost the d3Cost to set
	 */
	public void setD3Cost(int d3Cost) {
		this.d3Cost = d3Cost;
	}

	public int getMajorDevelopmentCost() {
		return majorDevelopmentCost;
	}

	public void setMajorDevelopmentCost(int majorDevelopmentCost) {
		this.majorDevelopmentCost = majorDevelopmentCost;
	}

	/**
	 * @return the devStage
	 */
	public int getDevStage() {
		return devStage;
	}

	/**
	 * @param devStage the devStage to set
	 */
	public void setDevStage(int devStage) {

		if (devStage == UPPER_DEV_STAGE) {
			this.devStage = devStage;
			setFullyDeveloped(true);
		} else {
			this.devStage = devStage;
		}
	}

	/**
	 * @return the comCost
	 */
	public int getComCost() {
		return comCost;
	}

	/**
	 * @param comCost the comCost to set
	 */
	public void setComCost() {

		if (devStage == LOWER_DEV_STAGE) {
			comCost = (int) (getTakeChargeCost() * COMMISSION_RATE);
		} else if (devStage > LOWER_DEV_STAGE && devStage <= UPPER_DEV_STAGE) {
			this.comCost = (int) (getDevCost(this.devStage) * COMMISSION_RATE);
		}
	}

	/**
	 * @return the fullyDeveloped
	 */
	public boolean isFullyDeveloped() {
		return fullyDeveloped;
	}

	/**
	 * @param fullyDeveloped the fullyDeveloped to set
	 */
	public void setFullyDeveloped(boolean fullyDeveloped) {

		this.fullyDeveloped = fullyDeveloped;

	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Returns the cost to develop the specified development stage
	 * 
	 * @param devStage
	 * @return development cost
	 */
	public int getDevCost(int devStage) throws IllegalArgumentException {

		int devCost = 0;

		switch (devStage) {

		case 1:
			devCost = getD1Cost();
			break;
		case 2:
			devCost = getD2Cost();
			break;
		case 3:
			devCost = getD3Cost();
			break;
		case 4:
			devCost = getMajorDevelopmentCost();
			break;
		default:
			throw new IllegalArgumentException("Invalid development stage entered");
		}

		return devCost;
	}

}
