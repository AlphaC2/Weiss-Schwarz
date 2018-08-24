package scrapper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.DriverUtilities;

public class SeriesPage {
	
	private static String url = "https://yuyu-tei.jp/game_ws/sell/sell_price.php";
	
	public static void main(String[] args) {
		WebDriver driver = DriverUtilities.createDriver(true);
		driver.get(url);
		driver.findElement(By.className("item_single_card")).click();
		WebElement navbar = driver.findElement(By.className("nav_list_second"));
		List<WebElement> series = navbar.findElements(By.cssSelector("a[href]"));
		for (WebElement serie : series) {
			System.out.println(serie.getAttribute("href"));
		}
		driver.quit();
	}
}
