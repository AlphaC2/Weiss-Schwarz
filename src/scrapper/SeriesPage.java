package scrapper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.DriverUtilities;

public class SeriesPage {
	
	private static String url = "https://yuyu-tei.jp/game_ws/sell/sell_price.php";
	
	public static void run() {
		WebDriver driver = DriverUtilities.createDriver(false);
		driver.get(url);
		driver.findElement(By.className("item_single_card")).click();
		WebElement navbar = driver.findElement(By.className("nav_list_second"));
		List<WebElement> series = navbar.findElements(By.cssSelector("a[href]"));
		driver.quit();
		for (WebElement serie : series) {
			String deckURL = serie.getAttribute("href");
			System.out.println(deckURL);
			if (!deckURL.endsWith("=smp")){
				new DeckPage(deckURL);
			}
		}
		
	}
}
