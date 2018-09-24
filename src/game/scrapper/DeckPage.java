package game.scrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeckPage {

	private String url;
	private WebDriver driver;
	List<String> cardURLs = new ArrayList<>();
	
	private final static Logger log = Logger.getLogger(DeckPage.class);

	public DeckPage(String url,WebDriver driver) {
		this.url = url;
		this.driver = driver;
		run();
	}
	
	private boolean fileExist(String ID){
		String setID = ID.split("-")[0];
		String folder = "CardData/" + setID;
		ID = ID.replace("/", "-");
		String filename = folder + "/" +ID + ".xml";
		File f = new File(filename);
		if (f.exists()){
			log.debug(ID + " skipped");
			return true;
		}
		return false;
	}

	private void processGroup(WebElement group) {
		
		for (WebElement webElement : group.findElements(By.className("card_unit"))) {
			String headline = webElement.findElement(By.className("headline")).getText();
			char lastChar = headline.charAt(headline.length() - 1);
			if (lastChar >= '0' && lastChar <= '9' && !fileExist(headline)) {
				String url = webElement.findElement(By.cssSelector("div.image_box > a")).getAttribute("href");
				cardURLs.add(url);
			}
		}
		
	}

	private void run() {
		driver.get(url);
		WebElement root = driver.findElement(By.className("card_list_box"));
		List<WebElement> groups = root.findElements(By.cssSelector("div.group_box > ul.card_list"));
		for (WebElement group : groups) {
			processGroup(group);
		}
		for (String cardURL : cardURLs) {
			new CardPage(cardURL,driver);
		}
	}

}
