package scrapper;


import org.junit.*;

import io.DriverUtilities;

public class TestDeck {

	@Test
	public void TestDeckScrapping(){
		new DeckPage("https://yuyu-tei.jp/game_ws/sell/sell_price.php?ver=gc", DriverUtilities.createDriver(false));
	}
}
