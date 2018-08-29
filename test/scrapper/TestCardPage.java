package scrapper;

import org.apache.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import io.DriverUtilities;

public class TestCardPage {
	
	private final static Logger log = Logger.getLogger(TestCardPage.class);

	
	@Test
	public void TestCard(){
		String url = "https://yuyu-tei.jp/game_ws/carddetail/cardpreview.php?VER=symphogeargx&CID=10145&MODE=sell";
		String url2 = "https://yuyu-tei.jp/game_ws/carddetail/cardpreview.php?VER=fapo&CID=10006&MODE=sell";
		WebDriver driver = DriverUtilities.createDriver(false);
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
