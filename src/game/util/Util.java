package game.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

	public static final String formatStackTrace(Exception e) {
		String s = "";
		for (StackTraceElement trace : e.getStackTrace()) {
			if (trace.toString().startsWith("scrapper")) {
				s += trace.toString() + System.lineSeparator();
			}
		}
		return s;
	}

	public static <T> List<List<T>> powerSet(T[] set) {
		int set_size = set.length;
		long pow_set_size = (long) Math.pow(2, set_size);
		int counter, j;
		List<List<T>> result = new ArrayList<List<T>>();
		
		for (counter = 0; counter < pow_set_size; counter++) {
			List<T> inner = new ArrayList<>();
			for (j = 0; j < set_size; j++) {
				if ((counter & (1 << j)) > 0)
					inner.add(set[j]);
			}
			result.add(inner);
		}
		
		return result;
	}
}