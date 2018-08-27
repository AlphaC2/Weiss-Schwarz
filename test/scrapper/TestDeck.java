package scrapper;


import org.junit.*;

public class TestDeck {

	@Test
	public void TestDeckScrapping(){
		new DeckPage("https://yuyu-tei.jp/game_ws/sell/sell_price.php?ver=njext");
	}
}
