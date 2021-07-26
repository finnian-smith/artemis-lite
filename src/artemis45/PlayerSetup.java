/**
 * 
 */
package artemis45;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class provides the methods that allow the number of players to be set
 * and registered in the ArtemisLite game
 * 
 * @author finnian
 *
 */
public class PlayerSetup {

	// constants
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;

	/**
	 * Global scanner used as input in each method where scanner is required
	 */
	public static Scanner scanner = new Scanner(System.in);

	/**
	 * Prompts user for number of players to play the game ({@value #MIN_PLAYERS} -
	 * {@value #MAX_PLAYERS} inclusive)
	 * 
	 * @param scanner - user input
	 * @return number of players
	 */
	public int numberOfPlayers() {

		int playerCount = 0;

		do {
			System.out.println("How many players are playing? (Min - " + MIN_PLAYERS + " / Max - " + MAX_PLAYERS + ")");

			try {
				playerCount = scanner.nextInt();

				if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS) {
					System.err.println("Invalid number of players entered - Please enter a valid number");
				}

			} catch (InputMismatchException e) {
				System.err.println("Invalid input - please enter a number value");
				scanner.next();
			}
		} while (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS);

		return playerCount;
	}

	/**
	 * Registers players by assigning them their player ID, agency, starting
	 * resources and position
	 * 
	 * @param scanner     - user input
	 * @param playerCount - number of players
	 * @return ArrayList of registered players
	 */
	public ArrayList<Player> registerPlayers(int playerCount) {

		ArrayList<Player> players = new ArrayList<Player>();

		// array of enums
		ArrayList<Agency> agencies = new ArrayList<Agency>();

		agencies.add(Agency.NASA);
		agencies.add(Agency.CSA);
		agencies.add(Agency.ESA);
		agencies.add(Agency.JAXA);

		int count;
		int userChoice = 0;

		for (int playerLoop = 0; playerLoop < playerCount; playerLoop++) {

			do {

				count = 1;

				System.out.println("Player " + (playerLoop + 1) + ", please choose your agency:");

				for (int agencyLoop = 0; agencyLoop < agencies.size(); agencyLoop++) {
					System.out.println("Select " + count + " for " + agencies.get(agencyLoop));
					count++;
				}

				try {
					userChoice = scanner.nextInt();

					if (userChoice <= 0 || userChoice > agencies.size()) {
						System.err.println("Invalid agency selection - Please enter a valid number");
						count = 1;
					}

				} catch (InputMismatchException e) {
					System.err.println("Invalid input - please enter a number value");
					scanner.next(); // doesn't always work for some reason??? <- play around with different inputs
									// at different times
				}

			} while (userChoice <= 0 || userChoice > agencies.size());

			Player player = new Player((playerLoop + 1), (agencies.get(userChoice - 1)));

			players.add(player);

			agencies.remove(userChoice - 1);
		}

		return players;
	}

}
