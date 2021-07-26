/**
 * 
 */
package artemis45;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * This class is where the ArtemisLite game is played
 * @author finnian
 *
 */
public class GameMaster {

	/**
	 * Global scanner used as input in each method where scanner is required
	 */
	public static Scanner scanner = new Scanner(System.in);

	/**
	 * Player ArrayList to store players for game
	 */
	public static ArrayList<Player> players;

	/**
	 * Square ArrayList to store squares on board
	 */
	public static ArrayList<Square> squares;

	/**
	 * BoardPlay declaration
	 */
	public static BoardPlay boardPlay;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to ArtemisLite!");

		int playerCount;

		if (startGame() == true) {

			PlayerSetup playerSetup = new PlayerSetup();

			playerCount = playerSetup.numberOfPlayers();

			players = new ArrayList<Player>(playerSetup.registerPlayers(playerCount));

			// set up board -> squares
			Board board = new Board(12, 4);
			buildBoard(board);
			squares = new ArrayList<Square>(board.getSquares());

			// board play begins -> players and squares
			boardPlay = new BoardPlay(players, squares);
			boardPlay.startGame();

			// turn taking begins
			beginPlay(board);

		} else {

			endGame();

		}

		
	    scanner.close();

	}

	/**
	 * Asks users if they would like to start the game. True indicates users wish to
	 * start the game / False indicates they do not
	 * 
	 * @return boolean - true/false
	 */
	public static boolean startGame() {

		String start;
		boolean flag = false;

		do {

			System.out.println("Would you like to start the game? (Enter Y for Yes / N for No)");
			start = scanner.next();

			if (start.equalsIgnoreCase("Y")) {
				flag = true;
			} else if (start.equalsIgnoreCase("N")) {
				flag = false;
			}

		} while (!start.equalsIgnoreCase("Y") && !start.equalsIgnoreCase("N"));

		return flag;

	}

	/**
	 * Begins the process of play and iterates through players until a player wins,
	 * loses or quits
	 */
	public static void beginPlay(Board board) {

		// continue to run whilst quit == false
		boolean quit = false;

		// tracks number of rounds player
		int roundCount = 1;

		System.out.println("\nNow you're all kitted out, it's time to get to work...\n");

		while (quit == false) {

			System.out.println("Round " + roundCount + " - ");

			for (Player player : players) {

				if (quit == false) {

					System.out.println("\n--------------------------------------------");
					System.out.println("\nIt's now " + player.getAgency() + "'s turn");
					System.out.println("You currently have " + player.getResources() + " Government Funds\n");
					
					// get player's decision on what happens during their turn
					quit = takeTurn(board, player);
					
				} else {
					
					break;
	
				}
				
			}
			
			roundCount++;
		}
		
		endGame();
	}

	/**
	 * Presents a menu of options to the player for them to decide what to do during
	 * their turn and executes their chosen action. If player does not choose a
	 * valid option then they will be repeatedly prompted to do so whilst their
	 * choice is invalid. Similarly, if players choose to view the state of play,
	 * they will have the ability to choose another option after this has been
	 * executed.
	 * 
	 * @return quit game status
	 */
	public static boolean takeTurn(Board board, Player player) {

		// menu prompt to user
		System.out.println("Enter X to roll the dice...");
		System.out.println("Enter S to view the state of play...");
		System.out.println("Enter P to pass your turn...");
		System.out.println("Enter Q to quit...");

		// get option
		String option = scanner.next();

		// set to false so game continues
		boolean quit = false;

		switch (option.toUpperCase()) {
		case "X":
			System.out.println("\nRolling dice...\n");
			player.movePosition(boardPlay.rollDice());
			Square landedSquare = boardPlay.squareSearch(player.getBoardPosition());
			quit = SquareAction.action(board, boardPlay, squares, landedSquare, players, player, scanner);
			if(quit == true) {
				break;
			}
			quit = Development.developElement(board, boardPlay, squares, player, scanner);	
			if(quit == true) {
				break;
			}
			break;
		case "S":
			System.out.println("Viewing state of play...\n");
			stateOfPlay();
			System.out.println("What else would you like to do?");
			quit = takeTurn(board, player);
			break;
		case "P":
			System.out.println("Passing turn...\n");
			break;
		case "Q":
			String quitCheck;
			do {
				System.out.println(
						"This will end the game for everyone\nAre you sure you want to quit? (Enter Y for Yes / N for No)\n");
				quitCheck = scanner.next();
				quit = (quitCheck.equalsIgnoreCase("Y") ? true : false);
			} while (!quitCheck.equalsIgnoreCase("Y") && !quitCheck.equalsIgnoreCase("N"));
			if (quit != true) {
				quit = takeTurn(board, player);
			} else {
				quitGame();
			}
			break;
		default:
			System.err.println("Invalid selection. Please choose one of the following options...\n");
			quit = takeTurn(board, player);
		}
		return quit;

	}

	/**
	 * Prints all players agency, position, resources and elements owned to screen
	 * 
	 */
	public static void stateOfPlay() {

		System.out.println("ArtemisLite - State of Play");

		for (Player player : players) {

			player.displayAll();

			// iterates through squares
			for (Square square : squares) {

				// gets board name of player's position and prints to screen
				if (square.getNumber() == player.getBoardPosition() + 1) {
					System.out.printf(square.getName());
					System.out.println();
				}
			}
			System.out.println();
		}
	}

	/**
	 * Ends the game
	 */
	public static void endGame() {
		
		System.out.println("Ending game...Thanks for playing!");
	}
	
	/**
	 * Quits game and outputs an exit message to screen
	 */
	public static void quitGame() {

		Queue<String> quitOutro = new LinkedList<String>();

		quitOutro.add("\nThis feat has proven to be too great of a challenge for one of our agencies");
		quitOutro.add("The domino effect begins - one by one, agency by agency");
		quitOutro.add("Failure at the precipice of glory");
		quitOutro.add("We applaud you for your effort, but alas 2024 has come too soon");
		quitOutro.add("The moon will always be there - but we my friends, might not...");

		for (String line : quitOutro) {
			System.out.println(line);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nEnd of Game Scenario - ");
		stateOfPlay();

	}

	/**
	 * Ends game and outputs message to screen that the game has been won
	 */
	public static boolean winGame() {

		boolean quit = true;

		Queue<String> winOutro = new LinkedList<String>();

		winOutro.add("\n****MISSION COMPLETE****");
		winOutro.add("All Elements from all Space Systems have been fully developed and Artemis is ready for launch!");
		winOutro.add("In 2021...NASA recruits and trains highly skilled astronauts for the Artemis Moon landing...");
		winOutro.add("In 2022...NASA trials flight tests of SLS and Orion as an integrated system...");
		winOutro.add("In 2023...NASA trials flights of crew to the Moon aboard SLS and Orion...");
		winOutro.add("In 2024...NASA prepares for the landing of the first woman and next man on the Moon...");
		winOutro.add("As the world watches with bated breath, the culmination of years of effort and dedication "
				+ "\nacross the globe once again brings humanity out into the cosmos and onto another planetary body.");
		winOutro.add("One small step for man, one giant leap for mankind!");

		for (String line : winOutro) {
			System.out.println(line);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nEnd of Game Scenario - ");
		stateOfPlay();

		return quit;
		
	}

	/**
	 * Ends game and outputs message to screen that game has been lost
	 */
	public static boolean loseGame() {
		
		boolean quit = true;
		
		Queue<String> lossOutro = new LinkedList<String>();

		lossOutro.add("\nWith the loss of a player's resources, the mission has stumbled and failed.");
		lossOutro.add("This means game over for now, but why not try again?");

		for (String line : lossOutro) {
			System.out.println(line);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nEnd of Game Scenario - ");
		stateOfPlay();

		return quit;
		
	}

	/**
	 * Builds board for game
	 * 
	 * @param board
	 */
	private static void buildBoard(Board board) {

		System.out.println("Generating board...\n");

		// create the Space Systems with limits on Elements
		SpaceSystem sys1 = new SpaceSystem(2, SpaceSystemName.SLS);
		SpaceSystem sys2 = new SpaceSystem(3, SpaceSystemName.HLS);
		SpaceSystem sys3 = new SpaceSystem(2, SpaceSystemName.OSS);
		SpaceSystem sys4 = new SpaceSystem(3, SpaceSystemName.EGS);
		
		// add the Space Systems to the board
		board.addSpaceSystem(sys1);
		board.addSpaceSystem(sys2);
		board.addSpaceSystem(sys3);
		board.addSpaceSystem(sys4);
		

		// create Elements
		Square e1 = new Element("Rocket Engine", 2, SpaceSystemName.SLS, 100, 200, 300, 400, 600);
		Square e2 = new Element("Cryogenic Propulsion", 3, SpaceSystemName.SLS, 150, 200, 300, 400, 600);
		Square e3 = new Element("Power Generation", 4, SpaceSystemName.HLS, 200, 250, 350, 450, 650);
		Square e4 = new Element("Energy Storage", 5, SpaceSystemName.HLS, 250, 250, 350, 450, 650);
		Square e5 = new Element("Communications", 6, SpaceSystemName.HLS, 300, 250, 350, 450, 650);
		Square e6 = new Element("Independent Communications", 8, SpaceSystemName.OSS, 600, 400, 500, 600, 800);
		Square e7 = new Element("Automated Docking System", 9, SpaceSystemName.OSS, 600, 400, 500, 600, 800);
		Square e8 = new Element("Crawler Transporter", 10, SpaceSystemName.EGS, 400, 300, 400, 500, 700);
		Square e9 = new Element("Mobile Launcher", 11, SpaceSystemName.EGS, 450, 300, 400, 500, 700);
		Square e10 = new Element("SLS Launch Pad", 12, SpaceSystemName.EGS, 500, 300, 400, 500, 700);

		// add Elements to the Space Systems
		sys1.addElement((Element) e1);
		sys1.addElement((Element) e2);

		sys2.addElement((Element) e3);
		sys2.addElement((Element) e4);
		sys2.addElement((Element) e5);
		
		sys3.addElement((Element) e6);
		sys3.addElement((Element) e7);
		
		sys4.addElement((Element) e8);
		sys4.addElement((Element) e9);
		sys4.addElement((Element) e10);

		// create non Element squares
		Square s1 = new Square("Mission Control", 1);
		Square s2 = new Square("NASA HQ", 7);

		// add Squares to arraylist squares in Board class
		for (SpaceSystem system : board.getSpaceSystems()) {
			for (Element e : system.getElements()) {
				board.addSquare(e);
			}
		}

		// add non Element squares
		board.addSquare(s1);
		board.addSquare(s2);
		
		// sort squares by square number
		Collections.sort(board.getSquares(), new CompareSquareNumber());

	}

}