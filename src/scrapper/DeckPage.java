package scrapper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.DriverUtilities;

public class DeckPage {

	private String url;
	private WebDriver driver;

	public DeckPage(String url) {
		this.url = url;
		driver = DriverUtilities.createDriver(true);
		run();
		driver.quit();
	}

	private void processGroup(WebElement group) {
		for (WebElement webElement : group.findElements(By.className("card_unit"))) {
			String headline = webElement.findElement(By.className("headline")).getText();
			char lastChar = headline.charAt(headline.length() - 1);
			if (lastChar >= '0' && lastChar <= '9') {
				String url = webElement.findElement(By.cssSelector("div.image_box > a")).getAttribute("href");
				new CardPage(url,this.driver);
			}

		}
	}

	private void run() {
		WebDriver driver = DriverUtilities.createDriver(true);
		driver.get(url);
		WebElement root = driver.findElement(By.className("card_list_box"));
		List<WebElement> groups = root.findElements(By.cssSelector("div.group_box > ul.card_list"));
		for (WebElement group : groups) {
			processGroup(group);
		}
		driver.quit();
	}

}
