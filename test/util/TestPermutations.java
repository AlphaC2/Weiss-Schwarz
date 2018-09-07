package util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestPermutations {
	
	
	@Test
	public void testingPermutations(){
		int listSize = 25;
		int expectedSize = (int) Math.pow(2, listSize);
		
		Integer[] numbers = new Integer[listSize];
		for (int i = 0; i < listSize; i++) {
			numbers[i] = i;
		}
		
		List<List<Integer>> permutations = Util.powerSet(numbers);
		//permutations.forEach(item -> System.out.println(Arrays.toString(item.toArray())));
		
		assertEquals(expectedSize, permutations.size());
	}
}
