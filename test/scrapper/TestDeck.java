package scrapper;


import org.junit.*;
import org.openqa.selenium.WebDriver;

import io.DriverUtilities;

public class TestDeck {

	@Test
	public void TestDeckScrapping(){
		WebDriver driver = DriverUtilities.createDriver(false);
		new DeckPage("https://yuyu-tei.jp/game_ws/sell/sell_price.php?ver=sao", driver);
		driver.quit();
	}
}
