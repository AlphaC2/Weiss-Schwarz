package util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

public class TestPermutations {
	
	@Test
	public void testingPermutations(){
		int maxListSize = 5;
		int expectedSize = (int) Math.pow(2, maxListSize);
		
		Integer[] numbers = new Integer[maxListSize];
		for (int i = 0; i < maxListSize; i++) {
			numbers[i] = i;
		}
		
		List<List<Integer>> permutations = Util.powerSet(numbers);
		//permutations.forEach(item -> System.out.println(Arrays.toString(item.toArray())));
		
		assertEquals(expectedSize, permutations.size());
	}
}
