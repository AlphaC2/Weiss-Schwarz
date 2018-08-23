package test.board;

import org.junit.After;
import org.junit.Before;

public class LevelZone {
	LevelZone level;
	
	@Before
	public void init(){
		level = new LevelZone();
	}
	
	@After
	public void tearDown(){
		level = null;
	}
	
	
	
}
