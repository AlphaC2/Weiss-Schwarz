package game.scrapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import game.io.DriverUtilities;
import game.scrapper.CardPage;

public class TestCardPage {
	
	private final static Logger log = Logger.getLogger(TestCardPage.class);

	@Test
	public void testList(){
		List<String> list = new ArrayList<>(); 
		list.add(null);
		list.add(null);
		assertEquals(2,list.size());
	}
	
	@Ignore
	@Test
	public void TestCard(){
		String url = "https://yuyu-tei.jp/game_ws/carddetail/cardpreview.php?VER=symphogeargx&CID=10145&MODE=sell";
		String url2 = "https://yuyu-tei.jp/game_ws/carddetail/cardpreview.php?VER=fapo&CID=10006&MODE=sell";
		WebDriver driver = DriverUtilities.createDriver(false, false);
		new CardPage(url,driver); 
		new CardPage(url2,driver); 
		driver.quit();
	}
	
	@Ignore
	@Test
	public void testLog4j(){
		log.error("Error msg");
	}
	
}
