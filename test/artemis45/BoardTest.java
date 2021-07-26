package artemis45;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

	// constants...
	private static int LOWER_MAX_SQUARES = 8;
	private static int UPPER_MAX_SQUARES = 18;
	private static int LOWER_MAX_SYSTEMS = 2;
	private static int UPPER_MAX_SYSTEMS = 5;
	
	// test data...
	Board board;

	int maxSquaresLowerValid, maxSquaresMidValid, maxSquaresUpperValid, maxSquaresLowerInvalid, maxSquaresUpperInvalid;

	int maxSpaceSystemsLowerValid, maxSpaceSystemsMidValid, maxSpaceSystemsUpperValid, maxSpaceSystemsLowerInvalid,
			maxSpaceSystemsUpperInvalid;

	Square e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, s1, s2;

	ArrayList<Square> squares;

	SpaceSystem sys1, sys2, sys3, sys4;

	ArrayList<SpaceSystem> spaceSystems;

	@BeforeEach
	void setUp() throws Exception {

		board = new Board();
		
		maxSquaresLowerValid = 8;
		maxSquaresMidValid = 12;
		maxSquaresUpperValid = 16;
		maxSquaresLowerInvalid = 7;
		maxSquaresUpperInvalid = 19;
		
		maxSpaceSystemsLowerValid = 2;
		maxSpaceSystemsMidValid = 3;
		maxSpaceSystemsUpperValid = 5; 
		maxSpaceSystemsLowerInvalid = 1;
		maxSpaceSystemsUpperInvalid = 6;
		
		
		sys1 = new SpaceSystem(2, SpaceSystemName.SLS);
		sys2 = new SpaceSystem(3, SpaceSystemName.HLS);
		sys3 = new SpaceSystem(2, SpaceSystemName.OSS);
		sys4 = new SpaceSystem(3, SpaceSystemName.EGS);
		
		spaceSystems = new ArrayList<SpaceSystem>();

		// squares
		e1 = new Element("Rocket Engine", 2, SpaceSystemName.SLS, 100, 200, 300, 400, 600);
		e2 = new Element("Cryogenic Propulsion", 3, SpaceSystemName.SLS, 150, 200, 300, 400, 600);
		e3 = new Element("Power Generation", 4, SpaceSystemName.HLS, 200, 250, 350, 450, 650);
		e4 = new Element("Energy Storage", 5, SpaceSystemName.HLS, 250, 250, 350, 450, 650);
		e5 = new Element("Communications", 6, SpaceSystemName.HLS, 300, 250, 350, 450, 650);
		e6 = new Element("Independent Communications", 8, SpaceSystemName.OSS, 600, 400, 500, 600, 800);
		e7 = new Element("Automated Docking System", 9, SpaceSystemName.OSS, 600, 400, 500, 600, 800);
		e8 = new Element("Crawler Transporter", 10, SpaceSystemName.EGS, 400, 300, 400, 500, 700);
		e9 = new Element("Mobile Launcher", 11, SpaceSystemName.EGS, 450, 300, 400, 500, 700);
		e10 = new Element("SLS Launch Pad", 12, SpaceSystemName.EGS, 500, 300, 400, 500, 700);

		s1 = new Square("Mission Control", 1);
		s2 = new Square("NASA HQ", 7);
		
		squares = new ArrayList<Square>();

	}

	@Test
	void testDefaultConstructor() {
		
		Board board = new Board();
		
		assertEquals(0, board.getMaxSquares());
		assertEquals(0, board.getMaxSpaceSystems());
		assertEquals(null, board.getSpaceSystems());
		assertEquals(null, board.getSquares());
		
	}
	
	@Test
	void testConstructorWithArgsValid() {
		
		Board board = new Board(maxSquaresMidValid, maxSpaceSystemsMidValid);
		
		assertEquals(maxSquaresMidValid, board.getMaxSquares());
		assertEquals(maxSpaceSystemsMidValid, board.getMaxSpaceSystems());
		assertEquals(squares, board.getSquares());
		assertEquals(spaceSystems, board.getSpaceSystems());
		
	}
	
	@Test
	void testConstructorWithArgsInvalidMaxSquares() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Board spaceSystem = new Board(maxSquaresLowerInvalid, maxSpaceSystemsMidValid);
		});

		assertEquals("INVALID INPUT - MAX SQUARES MUST BE A VALUE BETWEEN "
				+ LOWER_MAX_SQUARES + " AND " + UPPER_MAX_SQUARES + " INCLUSIVE", exception.getMessage());
	}
	
	@Test
	void testConstructorWithArgsInvalidMaxSystems() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Board spaceSystem = new Board(maxSquaresMidValid, maxSpaceSystemsLowerInvalid);
		});

		assertEquals("INVALID INPUT - MAX SYSTEMS MUST BE A VALUE BETWEEN "
				+ LOWER_MAX_SYSTEMS + " AND " + UPPER_MAX_SYSTEMS + " INCLUSIVE", exception.getMessage());
		
	}
	
	
	@Test
	void testSetAndGetMaxSquaresValidLower() {
		
		board.setMaxSquares(maxSquaresLowerValid);
		assertEquals(maxSquaresLowerValid, board.getMaxSquares());
	}
	
	@Test
	void testSetAndGetMaxSquaresValidMid() {
	
		board.setMaxSquares(maxSquaresMidValid);
		assertEquals(maxSquaresMidValid, board.getMaxSquares());
		
	}
	
	@Test
	void testSetAndGetMaxSquaresValidUpper() {
	
		board.setMaxSquares(maxSquaresUpperValid);
		assertEquals(maxSquaresUpperValid, board.getMaxSquares());
	}
	
	@Test
	void testSetAndGetMaxSquaresInvalidLower() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			board.setMaxSquares(maxSquaresLowerInvalid);
		});

		assertEquals("INVALID INPUT - MAX SQUARES MUST BE A VALUE BETWEEN "
				+ LOWER_MAX_SQUARES + " AND " + UPPER_MAX_SQUARES + " INCLUSIVE", exception.getMessage());
		
	}
	
	@Test
	void testSetAndGetMaxSquaresInvalidUpper() {
	
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			board.setMaxSquares(maxSquaresUpperInvalid);
		});

		assertEquals("INVALID INPUT - MAX SQUARES MUST BE A VALUE BETWEEN "
				+ LOWER_MAX_SQUARES + " AND " + UPPER_MAX_SQUARES + " INCLUSIVE", exception.getMessage());
	}


	@Test
	void testSetandGetMaxSpaceSystemsValidLower() {
		
		board.setMaxSpaceSystems(maxSpaceSystemsLowerValid);
		assertEquals(maxSpaceSystemsLowerValid, board.getMaxSpaceSystems());
	}
	
	@Test
	void testSetandGetMaxSpaceSystemsValidMid() {
		
		board.setMaxSpaceSystems(maxSpaceSystemsMidValid);
		assertEquals(maxSpaceSystemsMidValid, board.getMaxSpaceSystems());
		
	}

	@Test
	void testSetandGetMaxSpaceSystemsValidUpper() {
		
		board.setMaxSpaceSystems(maxSpaceSystemsUpperValid);
		assertEquals(maxSpaceSystemsUpperValid, board.getMaxSpaceSystems());
		
	}

	@Test
	void testSetandGetMaxSpaceSystemsInValidLower() {
	
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			board.setMaxSpaceSystems(maxSpaceSystemsLowerInvalid);
		});

		assertEquals("INVALID INPUT - MAX SYSTEMS MUST BE A VALUE BETWEEN "
				+ LOWER_MAX_SYSTEMS + " AND " + UPPER_MAX_SYSTEMS + " INCLUSIVE", exception.getMessage());
		
	}

	@Test
	void testSetandGetMaxSpaceSystemsInValidUpper() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			board.setMaxSpaceSystems(maxSpaceSystemsUpperInvalid);
		});

		assertEquals("INVALID INPUT - MAX SYSTEMS MUST BE A VALUE BETWEEN "
				+ LOWER_MAX_SYSTEMS + " AND " + UPPER_MAX_SYSTEMS + " INCLUSIVE", exception.getMessage());
		
		
	}
	
	
	
}
