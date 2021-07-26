package artemis45;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElementTest {
	
	// test data 
	
	String validName;
	SpaceSystemName validSpaceSystem;
	int validNumber,validTakeCharge,validIncreased,validD1,validD2,validD3,validMajorDev,validDevStage,validComCost;
	boolean validFullyDev;
	Player validPlayer;
	Element element;
	
	Player p1 = new Player();
	

	@BeforeEach
	void setUp() throws Exception {
		
		validName = "validName";
		validSpaceSystem = SpaceSystemName.EGS;
		validNumber =1;
		validTakeCharge =2;
		validIncreased = 8;
		validD1 =4;
		validD2 =5;
		validD3 =6;
		validMajorDev = 7;
		validDevStage = 3;
		validComCost = 9;
		validFullyDev = false;
		validPlayer = p1;
		element = new Element();
		
	}
	

	@Test
	void testElementConstructor() {
		element = new Element(validName, validNumber, validSpaceSystem, validTakeCharge, validD1, validD2, validD3, validMajorDev);
		assertEquals(validName, element.getName());
		assertEquals(validNumber, element.getNumber());
		assertEquals(validSpaceSystem, element.getSpaceSystemName());
		assertEquals(validTakeCharge, element.getTakeChargeCost());
		assertEquals(validD1, element.getD1Cost());
		assertEquals(validD2, element.getD2Cost());
		assertEquals(validD3, element.getD3Cost());
		assertEquals(validMajorDev, element.getMajorDevelopmentCost());
		
	}

	@Test
	void testGetSetSpaceSystemName() {
		element.setSpaceSystemName(validSpaceSystem);
		assertEquals(validSpaceSystem, element.getSpaceSystemName());
		
	}

	@Test
	void testGetSetTakeChargeCost() {
		element.setTakeChargeCost(validTakeCharge);
		assertEquals(validTakeCharge, element.getTakeChargeCost());
	}

	

	@Test
	void testGetSetD1Cost() {
		element.setD1Cost(validD1);
		assertEquals(validD1, element.getD1Cost());
		
	}

	@Test
	void testGetSetD2Cost() {
		element.setD2Cost(validD2);
		assertEquals(validD2, element.getD2Cost());
	}

	@Test
	void testGetSetD3Cost() {
		element.setD3Cost(validD3);
		assertEquals(validD3, element.getD3Cost());
		
	}

	@Test
	void testGetSetMajorDevelopmentCost() {
		element.setMajorDevelopmentCost(validMajorDev);
		assertEquals(validMajorDev, element.getMajorDevelopmentCost());
	}

	@Test
	void testGetSetDevStage() {
		element.setDevStage(validDevStage);
		assertEquals(validDevStage, element.getDevStage());
	}


	@Test
	void testGetSetPlayer() {
		element.setPlayer(validPlayer);
		assertEquals(validPlayer, element.getPlayer());
	}

	

}
