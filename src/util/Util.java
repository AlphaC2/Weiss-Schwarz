package util;

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
}
