/**
 * 
 */
package artemis45;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Provides the methods that manage the flow of action once a player has landed
 * on a Square
 * 
 * @author rebekkahohare
 *
 */
public class SquareAction {

	/**
	 * Outputs details of the square a player has landed on and determines the
	 * appropriate course of action depending on the landed square.
	 * 
	 * @param boardPlay
	 * @param squares
	 * @param landedSquare
	 * @param players
	 * @param player
	 * @param scanner
	 */
	public static boolean action(Board board, BoardPlay boardPlay, ArrayList<Square> squares, Square landedSquare,
			ArrayList<Player> players, Player player, Scanner scanner) {

		boolean quit = false;
		
		if (landedSquare instanceof Element) {

			System.out.println("\nYou have landed on the Element '" + landedSquare.getName() + "' from the "
					+ ((Element) landedSquare).getSpaceSystemName() + " System.");

			// if element not being managed by a player
			if (((Element) landedSquare).getPlayer() == null) {

				quit = elementAction(boardPlay, squares, landedSquare, players, player, scanner);

				// if element is already being managed by the player
			} else if ((((Element) landedSquare).getPlayer() != null)
					&& (((Element) landedSquare).getPlayer().equals(player))) {

				System.out.println("\n" + landedSquare.getName() + " is under your management.");
				quit = developElement(board, squares, landedSquare, player, scanner);
				if (boardPlay.developmentStatus(squares) == true) {
					quit = GameMaster.winGame();
				} else {

				}

				// element is being managed by another player
			} else {

				quit = contributeResources(landedSquare, player, scanner);
			}

			// if square is start square
		} else if (landedSquare.getName().equalsIgnoreCase("Mission Control")) {

			System.out.println("\nYou have landed on 'Mission Control'.");
			player.passGo();
			// System.out.println("Your Government Funds are now at " + player.getResources());

			// square is rest square
		} else {

			System.out.println("\nYou have landed on '" + landedSquare.getName()
					+ "'. Check in with HQ and revisit your strategy!");
		}
		
		return quit;
	}

	/**
	 * Informs the player that an Element is not yet being managed by another
	 * player. Determines whether the player is managing any Elements. Determines
	 * whether the player is managing an Element from the same System as the landed
	 * Element. Determines whether another player is managing an Element from the
	 * same system as the landed Element and offers that player the option to assume
	 * management of the Element provided that they have enough resources to do so.
	 * Determines whether the player has reached the maximum number of systems any
	 * one player can manage in the game. Offers the player appropriate
	 * opportunities depending on the above. Updates Element and Player objects
	 * according to the decided action of the player.
	 * 
	 * @param boardPlay
	 * @param squares
	 * @param landedSquare
	 * @param players
	 * @param player
	 * @param scanner
	 */
	public static boolean elementAction(BoardPlay boardPlay, ArrayList<Square> squares, Square landedSquare,
			ArrayList<Player> players, Player player, Scanner scanner) {

		boolean quit = false;
		
		// give the player info
		System.out.println("\nElement '" + landedSquare.getName() + "' is not being managed by any player.");

		// first check if player yet managing any elements
		// if not...
		if (player.getOwnedElements().isEmpty()) {

			System.out.println("\nYou are not yet managing any Elements from any System.");

			// then check if another player is managing elements from this system
			// if true...
			if (otherPlayerSystemCheck(squares, landedSquare, player) == true) {

				System.out.println(
						"\nFor the sake of the mission, you can not assume management of an Element from a System"
								+ " that is already under the management of another player.");

				// get other player
				Player otherPlayer = otherPlayer(squares, landedSquare, player);

				// offer element to that player
				quit = offerToPlayer(landedSquare, player, otherPlayer, scanner);

				// if not...
				// check players resources to give appropriate info and option to take charge
			} else {

				quit = takeChargeOptions(boardPlay, squares, landedSquare, players, player, scanner);

			}

			// if player managing elements, check if managing elements from this System
			// if true...
		} else if (sameSystemCheck(landedSquare, player) == true) {

			quit = takeChargeOptions(boardPlay, squares, landedSquare, players, player, scanner);

			// if not...
			// check if another player is
		} else if (otherPlayerSystemCheck(squares, landedSquare, player) == true) {

			System.out
					.println("\n" + player.getAgency() + ", for the sake of the mission, you can not assume management of an Element from a System"
							+ " that is already under the management of another player.");

			// get other player
			Player otherPlayer = otherPlayer(squares, landedSquare, player);

			// offer element to that player
			quit = offerToPlayer(landedSquare, player, otherPlayer, scanner);

			// if player not managing elements from this System and neither is another
			// player,
			// check if player is able to assume management or whether they have reached the
			// system limit
			// if not...
		} else if (player.systemLimitCheck(boardPlay.getPlayerSystemLimit()) == false) {

			System.out.println(
					"\nYou have not reached the maximum number of Systems that can be managed by any one player.");

			quit = takeChargeOptions(boardPlay, squares, landedSquare, players, player, scanner);

			// if true...
		} else {

			System.out
					.println("\nYou have reached the maximum number of Systems that can be managed by any one player. "
							+ "That's good work! But for the sake of the mission you cannot assume management of another System."
							+ "\nLet's see if another player can and/or wants to assume management of this Element...");

			offerElement(boardPlay, landedSquare, players, player, scanner);

		}
		
		return quit;

	}

	/**
	 * Checks whether Elements from the same System are managed by the player and
	 * outputs appropriate message.
	 * 
	 * @param landedSquare
	 * @param player
	 * @return - boolean
	 */
	public static boolean sameSystemCheck(Square landedSquare, Player player) {

		ArrayList<Element> sameSystemElements = new ArrayList<Element>();

		// check if player managing other elements from same system and add to
		// sameSystemElements
		boolean flag = false;

		for (Element element : player.getOwnedElements()) {
			if (element.getSpaceSystemName() == ((Element) landedSquare).getSpaceSystemName()) {
				flag = true;
				sameSystemElements.add(element);
			}
		}

		// if owns no elements from same system
		if (sameSystemElements.isEmpty()) {

			System.out.println("\nNo Elements from the " + ((Element) landedSquare).getSpaceSystemName()
			+ " System are currently under your management."
					+ "\nElement(s) under " + "your management is/are: ");
			for (Element otherElement : player.getOwnedElements()) {
				System.out
						.println(otherElement.getName() + " from the " + otherElement.getSpaceSystemName() + " System");
			}

			// if owns elements from same system
		} else {

			System.out.println("\nThe following Element(s) from the " + ((Element) landedSquare).getSpaceSystemName()
					+ " System is/are already under your management:");

			for (Element sameSystemElement : player.getOwnedElements()) {
				System.out.println(sameSystemElement.getName());
			}

		}

		return flag;

	}

	/**
	 * Checks whether Elements from the same System are managed by another player
	 * and outputs appropriate message.
	 * 
	 * @param squares
	 * @param landedSquare
	 * @param player
	 * @return - boolean
	 */
	public static boolean otherPlayerSystemCheck(ArrayList<Square> squares, Square landedSquare, Player player) {

		boolean flag = false;

		// check if another player is managing Elements from the same system, if so,
		// add to otherPlayerSameSystemElements
		ArrayList<Element> otherPlayerSameSystemElements = new ArrayList<Element>();
		for (Square square : squares) {
			if (square instanceof Element) {
				if (((Element) square).getSpaceSystemName() == ((Element) landedSquare).getSpaceSystemName()) {
					if ((((Element) square).getPlayer() != null) && ((Element) square).getPlayer() != player) {
						otherPlayerSameSystemElements.add((Element) square);
					}
				}
			}
		}

		// output outcome
		if (otherPlayerSameSystemElements.isEmpty()) {
			System.out.println("\nElements from the " + ((Element) landedSquare).getSpaceSystemName()
					+ " System are not being managed by another player.");
		} else {
			flag = true;
			System.out.println("\nHowever..");
			for (Element otherPlayerSameSystemElement : otherPlayerSameSystemElements) {
				System.out.println("Element " + otherPlayerSameSystemElement.getName() + " from the "
						+ otherPlayerSameSystemElement.getSpaceSystemName() + " System is already under "
						+ otherPlayerSameSystemElement.getPlayer().getAgency() + "'s management.");
			}
		}

		return flag;

	}

	/**
	 * Searches for and returns the other player who is managing the system that the
	 * landed square belongs to
	 * 
	 * @param squares
	 * @param landedSquare
	 * @param player
	 * @return - Player
	 */
	public static Player otherPlayer(ArrayList<Square> squares, Square landedSquare, Player player) {

		Player otherPlayer = null;

		for (Square square : squares) {
			if (square instanceof Element) {
				if (((Element) square).getSpaceSystemName() == ((Element) landedSquare).getSpaceSystemName()) {
					if ((((Element) square).getPlayer() != null) && ((Element) square).getPlayer() != player) {
						otherPlayer = ((Element) square).getPlayer();
						break;
					}
				}
			}
		}

		return otherPlayer;
	}

	/**
	 * Asks the player whether they would like to take charge of an Element and
	 * actions input.
	 * 
	 * @param boardPlay
	 * @param squares
	 * @param landedSquare
	 * @param players
	 * @param player
	 * @param scanner
	 */
	public static boolean takeChargeOptions(BoardPlay boardPlay, ArrayList<Square> squares, Square landedSquare,
			ArrayList<Player> players, Player player, Scanner scanner) {

		boolean quit = false;
		
		String userAnswer;

		// if player has more than enough resources

		if (player.getResources() > ((Element) landedSquare).getTakeChargeCost()) {

			costDetails(landedSquare, player);
			System.out.println("\nYou have sufficient Government Funds to assume management of this Element.");

			System.out.println(
					"\nAssuming management of this Element will take your Government Funds from " + player.getResources()
							+ " to " + (player.getResources() - ((Element) landedSquare).getTakeChargeCost()) + ".");

			// ask the player whether or not they want to take charge of the element
			do {
				System.out.println("\nWould you like to assume management of '" + landedSquare.getName() + "'?"
						+ "\n(Enter Y for Yes / N for No)");
				userAnswer = scanner.next();
			} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

			// take appropriate action if player wants to take charge of the square
			if (userAnswer.equalsIgnoreCase("Y")) {
				((Element) landedSquare).setPlayer(player);

				player.addElement((Element) landedSquare);
				player.subtractResources(((Element) landedSquare).getTakeChargeCost());
				System.out.println("\nElement '" + landedSquare.getName() + "' from the "
						+ ((Element) landedSquare).getSpaceSystemName() + " System "
						+ "is now under your management. \nYour Government Funds are now at " + player.getResources()
						+ ".");

				// if player doesn't want to take charge of the element pass the option on to
				// other players
			} else {

				ArrayList<Element> sameSystemElements = new ArrayList<Element>();

				// check if player managing other Elements from same system before offering to
				// another player
				for (Element element : player.getOwnedElements()) {
					if (element.getSpaceSystemName() == ((Element) landedSquare).getSpaceSystemName()) {
						sameSystemElements.add(element);
					}
				}

				if (sameSystemElements.isEmpty()) {

					System.out.println("\nOk, let's see if another player can and/or wants to assume "
							+ "management of this Element...");
					offerElement(boardPlay, landedSquare, players, player, scanner);

				} else {

				}

			}

			// if player has just enough resources to take charge of
			// element but that it will result in game over if they decide to proceed
		} else if (player.getResources() == ((Element) landedSquare).getTakeChargeCost()) {

			costDetails(landedSquare, player);
			System.out.println(
					"\nYou have just enough Government Funds to assume management of this Element. Assuming management of this "
							+ "Element will deplete your funds.");

			do {

				System.out.println(

						"Do you still want to assume management of this Element? (Enter Y for Yes / N for No)");

				userAnswer = scanner.next();

			} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

			// if player answers yes, ask to confirm
			if (userAnswer.equalsIgnoreCase("y")) {

				do {

					System.out.println(

							"Are you sure you want to end the game for everyone? (Enter Y for Yes / N for No)");

					userAnswer = scanner.next();

				} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

				// if player chooses to proceed, update the objects as appropriate and set off
				// loseGame() method
				if (userAnswer.equalsIgnoreCase("Y")) {
					((Element) landedSquare).setPlayer(player);
					player.addElement((Element) landedSquare);
					player.subtractResources(((Element) landedSquare).getTakeChargeCost());
					quit = GameMaster.loseGame();

				} else {

					System.out.println(
							"\nYour comradery is appreciated in the joint effort to complete the Artemis mission. No action has been taken on this Element or your Government Funds.");

				}

			} else {

				System.out.println(
						"\nYour comradery is appreciated in the joint effort to complete the Artemis mission. No action has been taken on this Element or your Government Funds.");

				// don't pass offer to other players as player has done noble thing
			}

			// player has insufficient resources to take charge
		} else {

			costDetails(landedSquare, player);
			System.out.println("\nYou do not have sufficient Government Funds to assume management of this Element.");

		}
		
		return quit;

	}

	/**
	 * Outputs details of the cost of assuming management of an Element and the
	 * player's current resources
	 * 
	 * @param landedSquare
	 * @param player
	 */
	public static void costDetails(Square landedSquare, Player player) {

		System.out.println("\nIt costs " + ((Element) landedSquare).getTakeChargeCost()
				+ " Government Funds to assume management of '" + landedSquare.getName() + "'.\nYou have "
				+ player.getResources() + " Government Funds.");

	}

	/**
	 * 
	 * Informs the player of current stage of development of Element. Informs player
	 * of cost of developing the Element. Updates Element and Player objects
	 * according to decided action of the player.
	 * 
	 * @param landedSquare
	 * @param player
	 * @param scanner
	 */
	public static boolean developElement(Board board, ArrayList<Square> squares, Square landedSquare, Player player, Scanner scanner) {

		boolean quit = false;
		
		// first check if Player is managing whole system

		if (managingWholeSystem(board, squares, landedSquare, player) == true) {

			System.out.println(
					"\nAll Elements from this System are under your management. That means you have the opportunity to "
							+ " develop its Elements!");

			final int LOWER_DEV_STAGE = 0;
			final int FINAL_DEV_STAGE = 3;
			final int UPPER_DEV_STAGE = 4;
			int currentDevStage = ((Element) landedSquare).getDevStage();
			int nextDevStage = (((Element) landedSquare).getDevStage() + 1);

			// if element has not yet been developed
			if (((Element) landedSquare).getDevStage() == LOWER_DEV_STAGE) {

				System.out.println(
						"\nDevelopment of this Element has not yet begun. The first stage of development (Stage "
								+ nextDevStage + ") will cost " + ((Element) landedSquare).getDevCost(nextDevStage)
								+ " Government Funds.\nYou have " + player.getResources()
								+ " Government Funds available.");

				quit = developOptions(squares, landedSquare, player, scanner);

				// if element has started development but not at final stage
			} else if ((currentDevStage > LOWER_DEV_STAGE) && (currentDevStage < UPPER_DEV_STAGE)) {

				if (currentDevStage == FINAL_DEV_STAGE) {

					System.out.println("\nDevelopment of this Element is currently at stage " + currentDevStage
							+ ". The last stage of development (stage " + nextDevStage + ")"
							+ " will complete the development of this Element."
							+ "\nThis major and final stage involves testing that everything is working as expected."
							+ "\nAlmost like a mini mission this will require a lot of resources and will cost "
							+ ((Element) landedSquare).getDevCost(nextDevStage) + " Government Funds.");

				} else {

					System.out.println("\nDevelopment of this Element is currently at stage " + currentDevStage
							+ ". The next stage of development (stage " + nextDevStage + ") will cost "
							+ ((Element) landedSquare).getDevCost(nextDevStage) + " Government Funds.");
				}

				quit = developOptions(squares, landedSquare, player, scanner);

				// Element's development has been completed
			} else {

				System.out.println(
						"\nThis Element has been fully developed - nice work! No further action can be take on this Element.");

			}

			// player is not managing all Elements from the System
		} else {

			System.out.println(
					"\nAll Elements from this System are not yet under your management. You must manage the whole System of Elements before "
							+ "an Element can be developed.");
		}
		
		return quit;

	}

	/**
	 * Determines whether a Player is managing all the Elements in the System that
	 * has landed on
	 * 
	 * @param landedSquare
	 * @param squares
	 * @param player
	 * @param scanner
	 * @return - boolean
	 */
	public static boolean managingWholeSystem(Board board, ArrayList<Square> squares, Square landedSquare, Player player) {

		boolean wholeSystem = false;

		// get space systems on board
		for (SpaceSystem ss : board.getSpaceSystems()) {

			// find space system that landed on
			if (ss.getSpaceSystemName() == ((Element) landedSquare).getSpaceSystemName()) {

				// check if player has all of the elements in that system
				if (player.getOwnedElements().containsAll(ss.getElements())) {
					wholeSystem = true;
				}
			}
		}
		return wholeSystem;

	}

	/**
	 * Outputs appropriate options to player depending on their level of Government
	 * Funds
	 * 
	 * @param squares
	 * @param landedSquare
	 * @param player
	 * @param scanner
	 */
	public static boolean developOptions(ArrayList<Square> squares, Square landedSquare, Player player, Scanner scanner) {

		boolean quit = false;
		
		final int FINAL_DEV_STAGE = 3;
		int currentDevStage = ((Element) landedSquare).getDevStage();
		int nextDevStage = (((Element) landedSquare).getDevStage() + 1);
		String userAnswer;

		if (player.getResources() > ((Element) landedSquare).getDevCost(nextDevStage)) {

			if ((currentDevStage == FINAL_DEV_STAGE)) {

				do {
					System.out.println("\nYou have sufficient Government Funds to complete the major and final "
							+ "development of this Element!\nWould you like to complete development and move "
							+ "one step closer towards completion of the Artemis mission?\n(Enter Y for Yes / N for No)");

					userAnswer = scanner.next();

				} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

				if (userAnswer.equalsIgnoreCase("Y")) {

					player.subtractResources(((Element) landedSquare).getDevCost(nextDevStage));
					((Element) landedSquare).setDevStage(nextDevStage);
					((Element) landedSquare).setComCost();

					System.out.println("\nThe final stage of development has been completed on "
							+ landedSquare.getName() + ".\nYour Government Funds have decreased by "
							+ ((Element) landedSquare).getDevCost(nextDevStage) + ".\nYour Government Funds are now at "
							+ player.getResources() + ".");

					System.out
							.println("Development of Element '" + landedSquare.getName() + "' complete - nice going!");

				}

			} else {

				do {
					System.out.println("\nYou have sufficient Government Funds to develop this Element."
							+ "\nWould you like to develop this Element? (Enter Y for Yes / N for No)");
					userAnswer = scanner.next();

				} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

				if (userAnswer.equalsIgnoreCase("Y")) {

					player.subtractResources(((Element) landedSquare).getDevCost(nextDevStage));
					((Element) landedSquare).setDevStage(nextDevStage);
					((Element) landedSquare).setComCost();

					System.out.println("\nStage " + nextDevStage + " of development has been completed on '"
							+ landedSquare.getName() + "'.\nYour Government Funds have decreased by "
							+ ((Element) landedSquare).getDevCost(nextDevStage) + ".\nYour Government Funds are now at "
							+ player.getResources() + ".");

				} else {

					System.out.println("\nNo action has been taken on this Element or on your Government Funds.");
				}

			}

		} else if (player.getResources() == ((Element) landedSquare).getDevCost(nextDevStage)) {

			do {

				System.out.println(
						"\nDeveloping this Element will deplete your Government Funds and end the game for everyone.\nDo you still want to"
								+ " develop this element? (Enter Y for Yes / N for No)");
				userAnswer = scanner.next();

			} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

			// if player answers yes, ask to confirm
			if (userAnswer.equalsIgnoreCase("y")) {

				do {

					System.out.println(

							"Are you sure you want to end the game for everyone? (Enter Y for Yes / N for No)");

					userAnswer = scanner.next();

				} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

				// if player chooses to proceed, update the objects as appropriate and set off
				// loseGame() method
				if (userAnswer.equalsIgnoreCase("Y")) {

					player.subtractResources(((Element) landedSquare).getDevCost(nextDevStage));
					((Element) landedSquare).setDevStage(nextDevStage);
					((Element) landedSquare).setComCost();

					quit = GameMaster.loseGame();

				} else {

					System.out.println(
							"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!\nNo action has been taken on this Element or your Government Funds.");

				}

			} else {

				System.out.println(
						"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!\nNo action has been taken on this Element or on your Government Funds.");
			}

		} else {

			System.out.println("\nYou do not have sufficient Government Funds to develop this Element.");

		}
		
		return quit;

	}

	/**
	 * 
	 * Informs the player that an Element is being managed by another player.
	 * Informs the player of the contribution cost to be made to the player that is
	 * managing the Element and offers the receiving player the opportunity to
	 * decline the contribution. Updates Player objects according to decided action
	 * of the Player who is managing the element.
	 * 
	 * @param landedSquare
	 * @param player
	 * @param scanner
	 */
	public static boolean contributeResources(Square landedSquare, Player player, Scanner scanner) {

		boolean quit = false;
		
		String userAnswer;

		// let the player know who the element is being managed by
		System.out.println("\nThis Element is being managed by " + ((Element) landedSquare).getPlayer().getAgency()
				+ ".\nA contribution of " + ((Element) landedSquare).getComCost()
				+ " is required in order for you to stay on this Element. \n\n" 
				+ player.getAgency() + " has " + player.getResources()
				+ " Government Funds.\n" + ((Element) landedSquare).getPlayer().getAgency() + " has "
				+ ((Element) landedSquare).getPlayer().getResources() + " Government Funds.");

		// let player who is managing element know that enforcing contribution will
		// result in
		// game over and ask the player if they want to enforce contribution
		if (((Element) landedSquare).getComCost() >= player.getResources()) {

			do {

				System.out.println("\n" + ((Element) landedSquare).getPlayer().getAgency() +
						", enforcing a contribution from " + player.getAgency()
						+ " will deplete their Government Funds and end the game for everyone, preventing the"
						+ " Artemis mission from being completed.\nDo you want to enforce the contribution from " 
						+ player.getAgency() + "? (Enter Y for Yes / N for No)");

				userAnswer = scanner.next();

			} while (!userAnswer.equalsIgnoreCase("Y") && (!userAnswer.equalsIgnoreCase("N")));

			// if player answers yes, ask to confirm
			if (userAnswer.equalsIgnoreCase("y")) {

				do {

					System.out.println(

							"Are you sure you want to end the game for everyone? (Enter Y for Yes / N for No)");

					userAnswer = scanner.next();

				} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

				// if player chooses to proceed, update the objects as appropriate and set off
				// loseGame() method
				if (userAnswer.equalsIgnoreCase("Y")) {

					((Element) landedSquare).getPlayer().addResources(((Element) landedSquare).getComCost());
					player.subtractResources(((Element) landedSquare).getComCost());
					quit = GameMaster.loseGame();

				} else {

					System.out.println(
							"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!");
					
					System.out.println("\nOK, back to you " + player.getAgency() + ".");

				}

				// if player answers no
			} else {

				System.out.println(
						"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!");

				System.out.println("\nOK, back to you " + player.getAgency() + ".");
			}

		}

		// the player has enough resources to contribute -
		// ask the player who manages the Element if they want to enforce contribution
		else {

			do {

				System.out.println("\n" + ((Element) landedSquare).getPlayer().getAgency()
						+ ", do you want to enforce the contribution from " + player.getAgency()
						+ "? (Enter Y for Yes / N for No)");

				userAnswer = scanner.next();

			} while (!userAnswer.equalsIgnoreCase("Y") && (!userAnswer.equalsIgnoreCase("N")));

			// if player answers yes, update objects accordingly
			if (userAnswer.equalsIgnoreCase("Y")) {

				((Element) landedSquare).getPlayer().addResources(((Element) landedSquare).getComCost());
				player.subtractResources(((Element) landedSquare).getComCost());

				System.out.println(("\n" + ((Element) landedSquare).getPlayer().getAgency())
						+ ", your Government Funds have increased by " + ((Element) landedSquare).getComCost()
						+ " Government Funds. Your Government Funds are now at "
						+ ((Element) landedSquare).getPlayer().getResources() + ".");
				System.out.println(player.getAgency() + ", your Government Funds have decreased by "
						+ ((Element) landedSquare).getComCost() + " Government Funds. Your Government Funds are now at "
						+ player.getResources() + ".");
				
				System.out.println("\nOK, back to you " + player.getAgency() + ".");

				// if player answers no
			} else {

				System.out.println(
						"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!");
				
				System.out.println("\nOK, back to you " + player.getAgency() + ".");

			}

		}
		
		return quit;

	}

	/**
	 * Offers the Player who is managing Elements from the System of the Element
	 * that another Player has landed on to assume management of that Element
	 * 
	 * @param landedSquare
	 * @param player
	 * @param otherPlayer
	 * @param scanner
	 */
	public static boolean offerToPlayer(Square landedSquare, Player player, Player otherPlayer, Scanner scanner) {

		boolean quit = false;
		
		String userAnswer;

		if (otherPlayer.getResources() > ((Element) landedSquare).getIncreasedTakeChargeCost()) {

			System.out.println("\n" + otherPlayer.getAgency() + ", you are managing Elements from this System.\n"
					+ "For the benefit of the mission, you can assume management of this Element at an increased cost.\n"
					+ "The additional cost will be added to " + player.getAgency() + "'s resources "
					+ "given that they were forced to offer up this Element.\n\nIt will cost "
					+ ((Element) landedSquare).getIncreasedTakeChargeCost()
					+ " Government Funds to assume management of this Element.\n"
					+ (((Element) landedSquare).getIncreasedTakeChargeCost()
							- ((Element) landedSquare).getTakeChargeCost())
					+ " of this will be added to " + player.getAgency()
					+ "'s Government Funds.\nYour Government Funds are at " + otherPlayer.getResources()
					+ ". You have sufficient Government Funds to assume management of this Element."
					+ "\nAssuming management of this Element will take your Government Funds from "
					+ otherPlayer.getResources() + " to "
					+ (otherPlayer.getResources() - ((Element) landedSquare).getIncreasedTakeChargeCost()) + ".");

			do {

				System.out.println("\n" + otherPlayer.getAgency()
						+ ", do you want to assume management of this Element? (Enter Y for Yes / N for No)");

				userAnswer = scanner.next();

			} while (!userAnswer.equalsIgnoreCase("Y") && (!userAnswer.equalsIgnoreCase("N")));

			if (userAnswer.equalsIgnoreCase("Y")) {
				((Element) landedSquare).setPlayer(otherPlayer);
				otherPlayer.addElement((Element) landedSquare);
				otherPlayer.subtractResources(((Element) landedSquare).getIncreasedTakeChargeCost());
				player.addResources(((((Element) landedSquare).getIncreasedTakeChargeCost()
						- ((Element) landedSquare).getTakeChargeCost())));

				System.out.println("\n" + otherPlayer.getAgency() + ", " + landedSquare.getName()
						+ " is now under your management. Your Government Funds are now at " + otherPlayer.getResources()
						+ ".");

				System.out.println("\n" + player.getAgency() + ", your Government Funds have increased by "
						+ ((((Element) landedSquare).getIncreasedTakeChargeCost()
								- ((Element) landedSquare).getTakeChargeCost()))
						+ ". Your Government Funds are now at " + player.getResources() + ".");

				System.out.println("\nOk, back to you " + player.getAgency() + ".");

				// if player answers no - just pass control back to player in takeTurn method
			} else {

				System.out.println("\nOk, back to you " + player.getAgency() + ".");

			}

		} else if (otherPlayer.getResources() == ((Element) landedSquare).getIncreasedTakeChargeCost()) {

			System.out.println("\n" + otherPlayer.getAgency() + ", you are managing Elements from this system.\n"
					+ "You therefore have the opportunity to assume management of this Element at increased cost.\n"
					+ "The additional cost will be added to " + player.getAgency() + " 's Government Funds "
					+ "given that they landed on the Element during their turn.\n It will cost "
					+ ((Element) landedSquare).getIncreasedTakeChargeCost()
					+ " Government Funds to assume management of this Element.\n"
					+ (((Element) landedSquare).getIncreasedTakeChargeCost()
							- ((Element) landedSquare).getTakeChargeCost())
					+ " of this will be added to " + player.getAgency()
					+ " 's Government Funds. \n\nYour Government Funds are at " + otherPlayer.getResources()
					+ ". You have just enough Government Funds to assume management of this Element"
					+ " but it will deplete your Government Funds and end the game for everyone.");

			do {

				System.out.println(
						"\nAssuming management of this Element will end the game for everyone. Do you still want to"
								+ " assume management of this element? (Enter Y for Yes / N for No)");

				userAnswer = scanner.next();

			} while (!userAnswer.equalsIgnoreCase("Y") && (!userAnswer.equalsIgnoreCase("N")));

			// if player answers yes, ask to confirm
			if (userAnswer.equalsIgnoreCase("Y")) {

				do {

					System.out.println(

							"Are you sure you want to end the game for everyone? (Enter Y for Yes / N for No)");

					userAnswer = scanner.next();

				} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));

				// if player chooses to proceed, update the objects as appropriate and set off
				// loseGame() method
				if (userAnswer.equalsIgnoreCase("Y")) {
					((Element) landedSquare).setPlayer(otherPlayer);
					otherPlayer.addElement((Element) landedSquare);
					otherPlayer.subtractResources(((Element) landedSquare).getIncreasedTakeChargeCost());
					player.addResources((((Element) landedSquare).getIncreasedTakeChargeCost()
							- ((Element) landedSquare).getTakeChargeCost()));
					quit = GameMaster.loseGame();

				} else {

					System.out.println(
							"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!\nNo action has been taken on this Element or your Government Funds.");

					System.out.println("\nOk, back to you " + player.getAgency() + ".");

				}

				// if player answers no - just pass control back to player in takeTurn method
			} else {

				System.out.println(
						"\nYour comradery is appreciated in the joint effort to complete the Artemis mission!\nNo action has been taken on this Element or your Government Funds.");

				System.out.println("\nOk, back to you " + player.getAgency() + ".");

			}

		} else {

			System.out.println("\n" + otherPlayer.getAgency()
					+ ", you would normally be offered the opportunity to assume "
					+ " management of a landed Element from a System you are managing at an increased cost. However, you do not have "
					+ " sufficient Government Funds to do so.");

			System.out.println("\nOK, back to you " + player.getAgency() + ".");
			
		}
		
		return quit;

	}

	/**
	 * 
	 * Offers another player the opportunity to assume management of the landed
	 * Element subject to specific requirements
	 * 
	 * @param landedSquare
	 * @param players
	 * @param player
	 * @param scanner
	 */
	public static void offerElement(BoardPlay boardPlay, Square landedSquare, ArrayList<Player> players, Player player,
			Scanner scanner) {

		String userAnswer;

		for (Player otherPlayer : players) {

			// make sure not asking player again
			if (otherPlayer.getPlayerId() == player.getPlayerId()) {

				continue;

			} else {

				// make sure other player has not reached system limit
				if (otherPlayer.systemLimitCheck(boardPlay.getPlayerSystemLimit()) == false) {

					// make sure other player has more than enough resources
					if (otherPlayer.getResources() > ((Element) landedSquare).getTakeChargeCost()) {

						System.out.println("\n" + otherPlayer.getAgency()
								+ ", you have not reached the maximum number of Systems any one player can manage "
								+ "\nand you have sufficient Government Funds to assume management of this Element.\nIt costs "
								+ ((Element) landedSquare).getTakeChargeCost()
								+ " Government Funds to assume management "
								+ "of this Element.\nAssuming management of this Element will take your Government Funds from "
								+ otherPlayer.getResources() + " to "
								+ (otherPlayer.getResources() - ((Element) landedSquare).getTakeChargeCost()) + ".");

						do {

							System.out.println("\nWould you like to assume management of this Element? "
									+ "(Enter Y for Yes / N for No)");

							userAnswer = scanner.next();

						} while (!userAnswer.equalsIgnoreCase("Y") && (!userAnswer.equalsIgnoreCase("N")));

						if (userAnswer.equalsIgnoreCase("y")) {

							((Element) landedSquare).setPlayer(otherPlayer);
							otherPlayer.addElement((Element) landedSquare);
							otherPlayer.subtractResources(((Element) landedSquare).getTakeChargeCost());
							System.out.println("\nElement '" + landedSquare.getName() + "' from the "
									+ ((Element) landedSquare).getSpaceSystemName() + " is now under your management. "
									+ "\nYour Government Funds are now at " + otherPlayer.getResources() + ".");
							break;

						} else {

							continue;

						}

					} else {

						System.out.println("\n" + otherPlayer.getAgency()
								+ " does not have sufficient Government Funds to assume management of this Element.");
						continue;

					}

				} else {

					System.out.println(otherPlayer.getAgency()
							+ " is managing the maximum number of Systems any one player can manage.");
					continue;

				}

			}

		}

		if (((Element) landedSquare).getPlayer() == null) {

			System.out.println(
					"\nNo player has assumed management of the Element '" + ((Element) landedSquare).getName() + "'.");

		}

		System.out.println("\nOK, back to you " + player.getAgency() + ".");

	}

}