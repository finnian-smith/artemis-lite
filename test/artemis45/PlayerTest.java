/**
 * 
 */
package artemis45;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author finnian
 *
 */
class PlayerTest {

	// test data
	int playerIdValidLower, playerIdValidMid, playerIdValidUpper, playerIdInvalidLower, playerIdInvalidUpper,
			resourcesValidLower, resourcesValidGeneral, resourcesInvalidLower, boardPositionValidLower,
			boardPositionValidMid, boardPositionValidUpper, boardPositionInvalidLower, boardPositionInvalidUpper,
		    resourcesToAdd, resourcesAdded, resourcesToSubtract, resourcesSubtracted, systems, systemLimit, numberToMove, finalPosition, passGoBonusAdded;
	Element e1, e2;
	ArrayList<Element> ownedElements;
	

	Player player;

	/**
	 * Test data initialisation
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		playerIdValidLower = 1;
		playerIdValidMid = 2;
		playerIdValidUpper = 4;
		playerIdInvalidLower = 0;
		playerIdInvalidUpper = 5;
		
		resourcesValidLower = 0;
		resourcesValidGeneral = 3000;
		resourcesInvalidLower = -1;
		
		boardPositionValidLower = 0;
		boardPositionValidMid = 6;
		boardPositionValidUpper = 11;
		boardPositionInvalidLower = -1;
		boardPositionInvalidUpper = 12;
		
		resourcesToAdd = 200;
		resourcesAdded = 3200;
		resourcesToSubtract = 200;
		resourcesSubtracted = 2800;
		
		systems = 1;
		systemLimit = 1;
		
		numberToMove = 3;
		finalPosition = 9;
		passGoBonusAdded = 3200;
		
		e1 = new Element("Element", 1, SpaceSystemName.SLS, 100, 200, 300, 400, 500);
		e2 = new Element("Element2", 2, SpaceSystemName.HLS, 200, 300, 400, 500, 600);
		ownedElements = new ArrayList<Element>();
				
		player = new Player();
		
	}

	/**
	 * Test method for default player constructor
	 */
	@Test
	void testPlayerDefaultConstructor() {
		assertNotNull(player);
	}

	/**
	 * Test method for player valid constructor with args
	 */
	@Test
	void testPlayerValidConstructorWithArgs() {
		player = new Player(playerIdValidMid, Agency.NASA);
		
		assertEquals(playerIdValidMid, player.getPlayerId());
	}
	
	/**
	 * Test method for player invalid constructor with args
	 */
	@Test
	void testPlayerInvalidConstructorWithArgs() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			player = new Player(playerIdInvalidLower, Agency.NASA);
		});
	}

	/**
	 * Test method for player id valid get and set
	 */
	@Test
	void testGetSetPlayerIdValid() {
		player.setPlayerId(playerIdValidMid);
		
		assertEquals(playerIdValidMid, player.getPlayerId());
	}
	
	/**
	 * Test method for player id invalid get and set
	 */
	@Test
	void testGetSetPlayerIdInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setPlayerId(playerIdInvalidLower);
		});
	}

	/**
	 * Test method for resources valid get and set
	 */
	@Test
	void testGetSetResourcesValid() {
		player.setResources(resourcesValidGeneral);
		
		assertEquals(resourcesValidGeneral, player.getResources());
	}
	
	/**
	 * Test method for resources invalid get and set
	 */
	@Test
	void testGetSetResourcesInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setResources(resourcesInvalidLower);
		});
	}

	/**
	 * Test method for board position valid get and set
	 */
	@Test
	void testGetSetBoardPositionValid() {
		player.setBoardPosition(boardPositionValidMid);
		
		assertEquals(boardPositionValidMid, player.getBoardPosition());
	}
	
	/**
	 * Test method for board position invalid get and set
	 */
	@Test
	void testGetSetBoardPositionInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setBoardPosition(boardPositionInvalidLower);
		});
	}

	/**
	 * Test method for owned elements get and set
	 */
	@Test
	void testSetAndGetOwnedElements() {
		
		player.setOwnedElements(ownedElements);
		
		assertTrue(player.getOwnedElements().size() == 0);
		
	}

	/**
	 * Test method for adding resources
	 */
	@Test
	void testAddResources() {
		player.setResources(resourcesValidGeneral);
		player.addResources(resourcesToAdd);
		
		assertEquals(resourcesAdded, player.getResources());
	}

	/**
	 * Test method for subtracting resources
	 */
	@Test
	void testSubtractResources() {
		player.setResources(resourcesValidGeneral);
		player.subtractResources(resourcesToSubtract);
		
		assertEquals(resourcesSubtracted, player.getResources());
	}

	/**
	 * Test method for adding element 
	 */
	@Test
	void testAddElement() {

		player.setOwnedElements(ownedElements);
		player.addElement(e1);
		player.addElement(e2);
		
		assertTrue(player.getOwnedElements().size() == 2 && player.getOwnedElements().contains(e1)
				&& player.getOwnedElements().contains(e2));
		
	}

	/**
	 * Test method for system limit check 
	 */
	@Test
	void testSystemLimitCheck() {

		player.setOwnedElements(ownedElements);
		player.addElement(e1);
		assertEquals(true, player.systemLimitCheck(systemLimit));
		
	}

	/**
	 * Test method for move position
	 */
	@Test
	void testMovePosition() {
		player.setBoardPosition(boardPositionValidMid);
		player.movePosition(numberToMove);
		
		assertEquals(finalPosition, player.getBoardPosition());
	}

	/**
	 * Test method for pass go
	 */
	@Test
	void testPassGo() {
		player.setResources(resourcesValidGeneral);
		player.passGo();
		
		assertEquals(passGoBonusAdded, player.getResources());
	}

}
