package util;

import java.util.ArrayList;
import java.util.List;

public class Util {

	public static final String formatStackTrace(Exception e){
		String s = "";
		for ( StackTraceElement trace : e.getStackTrace()) {
			if (trace.toString().startsWith("scrapper")){
				s += trace.toString() + System.lineSeparator();
			}
		}
		return s;
	}
	
	public static <T> List<List<T>> permute(T[] conditions) {
		List<List<T>> result = new ArrayList<>();
	 
		//start from an empty list
		result.add(new ArrayList<T>());
	 
		for (int i = 0; i < conditions.length; i++) {
			//list of list in current iteration of the array num
			List<List<T>> current = new ArrayList<>();
			
			for (List<T> l : result) {
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					// + add num[i] to different locations
					l.add(j, conditions[i]);
	 
					List<T> temp = new ArrayList<>(l);
					current.add(temp);
	 
					//System.out.println(temp);
	 
					// - remove num[i] add
					l.remove(j);
				}
			}
			
			result = new ArrayList<List<T>>(current);
		}
	 
		return result;
	}
	
	public static <T> List<List<T>> permute2(T[] conditions) {
		return null;
	}
}
