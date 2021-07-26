package artemis45;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpaceSystemTest {

	// constants...
	private static int LOWER_MAX_ELEMENTS = 2;
	private static int UPPER_MAX_ELEMENTS = 3;

	// test data...
	SpaceSystem spaceSystem;

	SpaceSystemName spaceSystemName;

	int maxElementsLowerInvalid, maxElementsUpperInvalid, maxElementsLowerValid, maxElementsUpperValid;

	Element e1, e2, e3, e4, e5;

	ArrayList<Element> elements;

	@BeforeEach
	void setUp() throws Exception {

		spaceSystem = new SpaceSystem();

		spaceSystemName = SpaceSystemName.SLS;

		maxElementsLowerInvalid = 0;
		maxElementsUpperInvalid = 4;
		maxElementsLowerValid = 2;
		maxElementsUpperValid = 3;

		e1 = new Element("Rocket Engine", 2, SpaceSystemName.SLS, 100, 200, 300, 400, 600);
		e2 = new Element("Cryogenic Propulsion", 3, SpaceSystemName.SLS, 150, 200, 300, 400, 600);
		e3 = new Element("Cryogenic Propulsion", 3, SpaceSystemName.SLS, 150, 200, 300, 400, 600);
		e4 = new Element("Power Generation", 4, SpaceSystemName.HLS, 200, 250, 350, 450, 650);
		e5 = new Element("Independent Communications", 8, SpaceSystemName.OSS, 600, 400, 500, 600, 800);

		elements = new ArrayList<Element>();

	}

	@Test
	void testDefaultConstructor() {

		SpaceSystem spaceSystem = new SpaceSystem();

		assertEquals(null, spaceSystem.getSpaceSystemName());
		assertEquals(0, spaceSystem.getMaxElements());
		assertEquals(null, spaceSystem.getElements());

	}

	@Test
	void testConstructorWithArgsValid() {

		SpaceSystem spaceSystem = new SpaceSystem(maxElementsUpperValid, spaceSystemName);

		assertEquals(maxElementsUpperValid, spaceSystem.getMaxElements());
		assertEquals(spaceSystemName, spaceSystem.getSpaceSystemName());
		assertEquals(elements, spaceSystem.getElements());

	}

	@Test
	void testConstructorWithArgsLowerInvalid() {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			SpaceSystem spaceSystem = new SpaceSystem(maxElementsLowerInvalid, spaceSystemName);
		});

		assertEquals("INVALID INPUT - MAX ELEMENTS MUST BE A VALUE BETWEEN " 
				+ LOWER_MAX_ELEMENTS + " AND " + UPPER_MAX_ELEMENTS + " INCLUSIVE", exception.getMessage());

	}

	@Test
	void testConstructorWithArgsUpperInvalid() {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			SpaceSystem spaceSystem = new SpaceSystem(maxElementsUpperInvalid, spaceSystemName);
		});

		assertEquals("INVALID INPUT - MAX ELEMENTS MUST BE A VALUE BETWEEN " 
				+ LOWER_MAX_ELEMENTS + " AND " + UPPER_MAX_ELEMENTS + " INCLUSIVE", exception.getMessage());

	}

	@Test
	void testSetAndGetSpaceSystemName() {

		spaceSystem.setSpaceSystemName(spaceSystemName);
		assertEquals(spaceSystemName, spaceSystem.getSpaceSystemName());
	}

	@Test
	void testSetAndGetMaxElementsLowerValid() {

		spaceSystem.setMaxElements(maxElementsLowerValid);
		assertEquals(maxElementsLowerValid, spaceSystem.getMaxElements());

	}

	
	@Test
	void testSetAndGetMaxElementsUpperValid() {

		spaceSystem.setMaxElements(maxElementsUpperValid);
		assertEquals(maxElementsUpperValid, spaceSystem.getMaxElements());

	}

	@Test
	void testSetMaxElementLowerInvalid() {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			spaceSystem.setMaxElements(maxElementsLowerInvalid);
		});

		assertEquals("INVALID INPUT - MAX ELEMENTS MUST BE A VALUE BETWEEN " 
				+ LOWER_MAX_ELEMENTS + " AND " + UPPER_MAX_ELEMENTS + " INCLUSIVE", exception.getMessage());

	}

	@Test
	void testSetMaxElementUpperInvalid() {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			spaceSystem.setMaxElements(maxElementsUpperInvalid);
		});

		assertEquals("INVALID INPUT - MAX ELEMENTS MUST BE A VALUE BETWEEN " 
				+ LOWER_MAX_ELEMENTS + " AND " + UPPER_MAX_ELEMENTS + " INCLUSIVE", exception.getMessage());
	}

	@Test
	void testSetAndGetElements() {

		spaceSystem.setElements(elements);
		assertEquals(elements, spaceSystem.getElements());

	}

	@Test
	void testAddElement() {

		spaceSystem.setSpaceSystemName(spaceSystemName);
		spaceSystem.setMaxElements(maxElementsLowerValid);
		spaceSystem.setElements(elements);

		spaceSystem.addElement(e1);
		spaceSystem.addElement(e4);
		spaceSystem.addElement(e3);
		spaceSystem.addElement(e2);
		spaceSystem.addElement(e5);

		assertTrue(spaceSystem.getElements().size() == 2 && elements.contains(e1) && elements.contains(e3));

	}

}
