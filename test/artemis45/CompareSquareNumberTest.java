package artemis45;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompareSquareNumberTest {

	// test data
	Square s1, s2, s3, s4;
	int n1, n2, n3, n4;
	CompareSquareNumber compare;
	
	@BeforeEach
	void setUp() throws Exception {
		
		s1 = new Square();
		s2 = new Square();
		s3 = new Square();
		s4 = new Square();
		
		n1 = 9;
		n2 = 5;
		n3 = 5;
		n4 = 2;
		
		s1.setNumber(n1);
		s2.setNumber(n2);
		s3.setNumber(n3);
		s4.setNumber(n4);
		
		compare = new CompareSquareNumber();
		
	}

	@Test
	void testCompareGreater() {
		
		assertEquals(4, compare.compare(s1, s2));
	}
	
	@Test
	void testCompareLessThan() {
		
		assertEquals(-7, compare.compare(s4, s1));
		
	}

	@Test
	void testCompareEqualTo() {
		
		assertEquals(0, compare.compare(s2, s3));
	}


}
