package game.scrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import game.io.DriverUtilities;

public class SeriesPage {
	
	private static String url = "https://yuyu-tei.jp/game_ws/sell/sell_price.php";
	private final static Logger log = Logger.getLogger(DeckPage.class);

	public static void run() {
		WebDriver driver = DriverUtilities.createDriver(false);
		driver.get(url);
		driver.findElement(By.className("item_single_card")).click();
		WebElement navbar = driver.findElement(By.className("nav_list_second"));
		List<WebElement> series = navbar.findElements(By.cssSelector("a[href]"));
		Set<String> deckURLs = new HashSet<>();
		for (WebElement serie : series) {
			String deckURL = serie.getAttribute("href");
			if (!deckURL.endsWith("=smp")){
				deckURLs.add(deckURL);
			}
		}
		
		for (String deckURL : deckURLs) {
			log.info("Parsing deck "+deckURL);
			new DeckPage(deckURL,driver);
		}
		driver.quit();
	}
}
