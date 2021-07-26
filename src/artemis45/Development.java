/**
 * 
 */
package artemis45;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class provides the methods that manage the development process in the
 * ArtemisLite game
 * 
 * @author finnian
 *
 */
public class Development {

	/**
	 * Represents the final stage of development of an element
	 */
	private static final int UPPER_DEV_STAGE = 4;

	/**
	 * Asks players if they would like to develop an element. If yes, they are given
	 * a list of their owned elements and prompted to choose which they would like
	 * to develop. If they don't own any elements or choose no, they will exit and
	 * play will continue with the next player
	 * 
	 * @param player
	 * @param scanner
	 */
	public static boolean developElement(Board board, BoardPlay boardPlay, ArrayList<Square> squares, Player player,
			Scanner scanner) {

		boolean quit = false;
		
		ArrayList<Element> playerElements = new ArrayList<Element>(player.getOwnedElements());
		
		
		// no elements owned or no system completely owned
		if (playerElements.isEmpty() || systemOwnershipCheck(board, player) == false) {
			System.out.println("\nYou do not have the option to develop at this time.\n");
			System.out.println("\nProceeding to the next player's turn...\n");
			
        } else if(fullyDeveloped(player)==true) {
			
			System.out.println("\nAll Elements under your management have been fully developed - nice work!");
			System.out.println("\nProceeding to the next player's turn...\n");
			
			// if player can develop -> ask them if they would like to
		} else {

			String toDevelop;
			do {
				System.out.println(
						"\nYou have the opportunity to develop an Element - Would you like to develop an Element? (Enter Y for Yes / N for No)\n");
				toDevelop = scanner.next();
			} while (!toDevelop.equalsIgnoreCase("Y") && !toDevelop.equalsIgnoreCase("N"));

			// do want to develop
			if (toDevelop.equalsIgnoreCase("Y")) {

				int choice = 0;
				
				ArrayList<Element> elementsToDevelop;

				do {

					// offers options to users
					System.out.println("Which Element would you like to develop?\n");

					elementsToDevelop = devMenu(board, player);

					try {

						choice = scanner.nextInt();

						if (choice < 0 || choice > playerElements.size()) {
							System.err.println("Invalid number entered - please enter a valid number");
						}

					} catch (InputMismatchException inputMismatchException) {
						System.err.println("Invalid input - please enter a valid number");
						scanner.next();
						quit = developElement(board, boardPlay, squares, player, scanner);
					}

				} while (choice < 0 || choice > elementsToDevelop.size());

				// choice determines the flow - if 0 continue to next player
				if (choice != 0) {
					
					quit = devProcess(board, boardPlay, player, squares, elementsToDevelop, choice, scanner);
					
				} else {
					
					if(quit==true) {
						
					} else {
						
					 System.out.println("\nProceeding to the next player's turn...\n");
				
				    }
				}

				// doesn't want to develop
			} else {
				
				if(quit==true) {
					
				} else {
					
				System.out.println("Proceeding to the next player's turn...\n");
				
				}
				
			}
		}
		
		return quit;
	}

	/**
	 * Checks if a user has any elements to develop, and if so, prints options for
	 * them to choose which they would like to develop
	 * 
	 * @param spaceSystems
	 * @param player
	 */
	public static boolean systemOwnershipCheck(Board board, Player player) {

		boolean flag = false;

		for (SpaceSystem ss : board.getSpaceSystems()) {

			// if player has all of the elements in that system
			if (player.getOwnedElements().containsAll(ss.getElements())) {
				flag = true;
			}
		}

		return flag;
	}
	
	/**
	 * Checks if player's Elements are fully developed
	 * 
	 * @param player
	 * 
	 * @return boolean
	 */
	public static boolean fullyDeveloped(Player player) {
		
		boolean complete = true;
		
		for(Element e: player.getOwnedElements()) {
			if(e.isFullyDeveloped()==false) {
				complete=false;
			}
		}
		
		return complete;
	}

	/**
	 * Checks if a user has any elements to develop, and if so, prints options for
	 * them to choose which they would like to develop
	 * 
	 * @param board
	 * @param player
	 * 
	 * @return ArrayList<Element>
	 */
	public static ArrayList<Element> devMenu(Board board, Player player) {

		ArrayList<Element> toDevelop = new ArrayList<>();
		ArrayList<Element> complete = new ArrayList<>();

		int count = 0;
		for (SpaceSystem ss : board.getSpaceSystems()) {

			Collections.sort(player.getOwnedElements(), new CompareSquareNumber());

			// if player has all of the elements in that system
			if (player.getOwnedElements().containsAll(ss.getElements())) {

				// check against elements
				for (Element e : ss.getElements()) {

					// if it contains an element that not fully developed then add to toDevelop
					if (player.getOwnedElements().contains(e) && e.isFullyDeveloped() == false) {

						toDevelop.add(e);

					} else if (player.getOwnedElements().contains(e) && e.isFullyDeveloped() == true) {

						complete.add(e);
					}

				}
			}
		}

		for (Element e1 : complete) {

			System.out.println(e1.getName() + " (" + e1.getSpaceSystemName() + ") - FULLY DEVELOPED");

		}

		for (Element e2 : toDevelop) {

			System.out.println(
					"Enter " + (count + 1) + " to develop " + e2.getName() + " (" + e2.getSpaceSystemName() + ")");
			count++;
		}

		System.out.println("Enter 0 to continue the game");

		return toDevelop;

	}

	/**
	 * Commences the development process - allowing players to develop elements
	 * under their ownership
	 * 
	 * @param player
	 * @param playerElements
	 * @param choice
	 * @param scanner
	 */
	public static boolean devProcess(Board board, BoardPlay boardPlay, Player player, ArrayList<Square> squares,
			ArrayList<Element> elementsToDevelop, int choice, Scanner scanner) {

		boolean quit = false;
		
		String elementName = elementsToDevelop.get(choice - 1).getName();

		int currentDevelopmentStage = elementsToDevelop.get(choice - 1).getDevStage();

		if (currentDevelopmentStage != UPPER_DEV_STAGE) {

			int nextDevelopmentStage = currentDevelopmentStage + 1;
			int developmentCost = elementsToDevelop.get(choice - 1).getDevCost(nextDevelopmentStage);

			System.out.println("You have chosen to develop - " + elementName + "\n");
			System.out.println(elementName + " is currently at stage " + currentDevelopmentStage);
			System.out.println("This development will take it to stage " + nextDevelopmentStage);

			// let player know this is the final development
			if (nextDevelopmentStage == UPPER_DEV_STAGE) {
				System.out.println("This is the final major development!\n"
						+ "This major and final stage involves testing that everything is working as expected."
						+ "\nAlmost like a mini mission this will require a lot of resources.");
			}

			System.out.println("The cost of this process will be " + developmentCost + " Government Funds.\n");

			// player has enough resources
			if (player.getResources() > developmentCost) {
				System.out.println("You currently have " + player.getResources() + " Government Funds.");
				System.out.println("If you wish to develop this Element, your Government Funds will decrease by "
						+ developmentCost + " to " + (player.getResources() - developmentCost) + ".\n");
				System.out.println("Develop this Element? (Enter Y for Yes / N for No)\n");

				String develop = scanner.next();

				// wants to develop -> do the maths
				if (develop.equalsIgnoreCase("Y")) {
					player.subtractResources(developmentCost);
					elementsToDevelop.get(choice - 1).setDevStage(nextDevelopmentStage);
					currentDevelopmentStage++;
					elementsToDevelop.get(choice - 1).setComCost(); // double check this is working as expected

					System.out.println("The Element has now been developed and is currently at development stage "
							+ currentDevelopmentStage);
					System.out.println("Your Government Funds are now at " + player.getResources() + ".\n");

					// check to see if all systems developed
					if (boardPlay.developmentStatus(squares) == true) {
						
						quit = GameMaster.winGame();
		
					// no longer can be developed
					} else if (currentDevelopmentStage != UPPER_DEV_STAGE) {

						System.out.println("Would you like to develop this Element further? (Enter Y for Yes / N for no)");

						String developAgain = scanner.next();

						if (developAgain.equalsIgnoreCase("Y")) {
							quit = devProcess(board, boardPlay, player, squares, elementsToDevelop, choice, scanner);
						} else {
							quit = developElement(board, boardPlay, squares, player, scanner);
						}

						// fully developed
					} else {
						
						System.out.println("Development of this Element is complete - nice work!");
						quit = developElement(board, boardPlay, squares, player, scanner);
					}

					// doesn't wish to develop chosen element
				} else {
					
					quit = developElement(board, boardPlay, squares, player, scanner);
					
				}

				// exact resources -> but will go bankrupt
			} else if (player.getResources() == developmentCost) {

				String answer;

				System.out.println(
						"\nYou have just enough Government Funds to develop this Element but it will deplete your funds and end the game for everyone.");
				do {

					System.out.println("Do you still want to develop this Element? (Enter Y for Yes / N for No)");

					answer = scanner.next();

				} while (!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("N"));

				// ends game
				if (answer.equalsIgnoreCase("Y")) {
					player.subtractResources(developmentCost);
					elementsToDevelop.get(choice - 1).setDevStage(nextDevelopmentStage);
					currentDevelopmentStage++;
					quit = GameMaster.loseGame();
					
					// continues game
				} else {

					System.out.println(
							"\nYour comradery is appreciated in the joint effort to complete the Artemis mission. No action has been taken on this Element or your Government Funds.");
					quit = developElement(board, boardPlay, squares, player, scanner);
				}

				// doesn't have enough resources
			} else {
				System.out.println("Sorry, you don't currently have enough Government Funds to develop this Element");
				quit = developElement(board, boardPlay, squares, player, scanner);
			}

			// chosen element to develop has already been fully developed
		} else {
			System.out.println(elementName + " has already been fully developed!");
			quit = developElement(board, boardPlay, squares, player, scanner);
		}
		
		return quit;
	}
	
}
