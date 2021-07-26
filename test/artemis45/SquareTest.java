package artemis45;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SquareTest {
	
	// test data
	
	String validName;
	int validNumber;
	
	Square s1;

	@BeforeEach
	void setUp() throws Exception {
		
		validName = "validName";
		validNumber = 1;
		s1 = new Square();
	
	}
	

	@Test
	void testGetSetName() {
		s1.setName(validName);
		assertEquals(validName, s1.getName());
	}

	@Test
	void testGetSetNumber() {
		s1.setNumber(validNumber);
		assertEquals(validNumber, s1.getNumber());
	}
	
	@Test
	void testSquareConstructor() {
		Square s1 = new Square(validName, validNumber);
		
		assertEquals(validName, s1.getName());
		assertEquals(validNumber, s1.getNumber());
	}
	
}

