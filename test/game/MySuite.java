package game;

import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

@RunWith(WildcardPatternSuite.class)
@SuiteClasses(
		{"**/Test*.class",
		"!**/scrapper/Test*.class"
		})
public class MySuite {
	
}
