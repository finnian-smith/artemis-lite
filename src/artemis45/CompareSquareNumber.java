/**
 * 
 */
/**
 * 
 */
package artemis45;

import java.util.Comparator;

/**
 * @author rebekkahohare 40129272
 *
 */
public class CompareSquareNumber implements Comparator<Square> {

	public int compare(Square s1, Square s2) {
		
		return s1.getNumber() - s2.getNumber();
		
	}

}