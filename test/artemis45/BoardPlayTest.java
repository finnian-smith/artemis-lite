package artemis45;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardPlayTest {

	
	// test data...

	// squares
	
	Square e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, s1, s2;
	
	ArrayList<Square> squares;
	
	// players
	
	Player player1, player2, player3;
	
	ArrayList<Player> players; 
	
	// boardplay
	
	BoardPlay boardPlay; 
	
	@BeforeEach
	void setUp() throws Exception {
	
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
	
	squares.add(s1);
	squares.add(s2);
	squares.add((Element) e1);
	squares.add((Element) e2);
	squares.add((Element) e3);
	squares.add((Element) e4);
	squares.add((Element) e5);
	squares.add((Element) e6);
	squares.add((Element) e1);
	squares.add((Element) e8);
	squares.add((Element) e9);
	squares.add((Element) e10);
	
	// players 
	
	player1 = new Player(1, Agency.CSA);
	player2 = new Player(2, Agency.ESA);
	player3 = new Player(3, Agency.NASA);
	
	players = new ArrayList<Player>();
	players.add(player1);
	players.add(player2);
	players.add(player3);
	
	// boardPlay 
	
	boardPlay = new BoardPlay(players, squares);
	
	}
	
	
	@Test
	void testDefaultConstructor() {
		
		BoardPlay boardPlay = new BoardPlay(); 
		
		assertEquals(null, boardPlay.getPlayers());
		assertEquals(null, boardPlay.getSquares());
		assertEquals(0, boardPlay.getPlayerSystemLimit());
		
	}

	@Test
	void testConstructorWithArgs() {
		
		BoardPlay boardPlay = new BoardPlay(players, squares);

		assertEquals(players, boardPlay.getPlayers());
		assertEquals(squares, boardPlay.getSquares());
		assertEquals(2, boardPlay.getPlayerSystemLimit());
	}

	@Test
	void testSetAndGetPlayers() {
		
		boardPlay.setPlayers(players);
		assertEquals(players, boardPlay.getPlayers());
		
	}

	
	@Test
	void testSetAndGetSquares() {
		
		boardPlay.setSquares(squares);
		assertEquals(squares, boardPlay.getSquares());
	}

	@Test
	void testSetAndGetPlayerSystemLimit() {
		
		BoardPlay boardPlay = new BoardPlay(players, squares);
		assertEquals(2, boardPlay.getPlayerSystemLimit());
		
	}

	@Test
	void testStartGame() {
		
		boardPlay.startGame();
		
		assertEquals(4000, player1.getResources());
		assertEquals(4000, player2.getResources());
		assertEquals(4000, player3.getResources());
		
		assertEquals(0, player1.getBoardPosition());
		assertEquals(0, player2.getBoardPosition());
		assertEquals(0, player3.getBoardPosition());
		
	}
	
	
	@Test
	void testDevelopmentStatusFalse() {
		
		assertEquals(false, boardPlay.developmentStatus(squares));
		
	}
	
	@Test
	void testDevelopmentStatusTrue() {
		
		for(Square s : squares) {
			
			if(s instanceof Element) {
				
			  ((Element) s).setDevStage(4);
			}
		}
		
		assertEquals(true, boardPlay.developmentStatus(squares));
		
	}

	@Test
	void testSquareSearch() {
				
		assertEquals(e6, boardPlay.squareSearch(7));
		
	}

}
