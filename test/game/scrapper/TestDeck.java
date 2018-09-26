package game.scrapper;


import org.junit.*;
import org.openqa.selenium.WebDriver;

import game.io.DriverUtilities;
import game.scrapper.DeckPage;

public class TestDeck {

	@Test
	public void TestDeckScrapping(){
		WebDriver driver = DriverUtilities.createDriver(false, false);
		new DeckPage("https://yuyu-tei.jp/game_ws/sell/sell_price.php?ver=abre", driver);
		driver.quit();
	}
}
