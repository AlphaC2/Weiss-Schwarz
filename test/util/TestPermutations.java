package util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestPermutations {
	
	
	@Test
	public void testingPermutations(){
		int listSize = 3;
		int expectedSize = 9;
		
		Integer[] numbers = new Integer[listSize];
		for (int i = 0; i < listSize; i++) {
			numbers[i] = i;
		}
		
		List<List<Integer>> permutations = Util.permute(numbers);
		permutations.forEach(item -> System.out.println(Arrays.toString(item.toArray())));
		
		assertEquals(expectedSize, permutations.size());
	}
}
