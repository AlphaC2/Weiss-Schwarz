package scrapper;


import org.junit.*;

public class TestDeck {

	@Test
	public void TestDeck(){
		new DeckPage("https://yuyu-tei.jp/game_ws/sell/sell_price.php?ver=saekano");
		new DeckPage("https://yuyu-tei.jp/game_ws/sell/sell_price.php?ver=miku");
	}
}
