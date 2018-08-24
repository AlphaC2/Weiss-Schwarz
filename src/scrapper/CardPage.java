package scrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import model.card.CardType;
import model.card.Colour;
import model.card.Trait;
import model.card.Trigger;
import model.exceptions.ParseJPException;

public class CardPage {

	private WebDriver driver;
	private String ID;
	private CardType type;
	private int level;
	private int cost;
	private int power;
	private int soul;
	private Trigger[] triggers;
	private List<Trait> traits = new ArrayList<>();
	private Colour color;

	public final String getFlavourText() {
		return flavourText;
	}

	public final String getAbilityJP() {
		return abilityJP;
	}

	private String flavourText;
	private String abilityJP;

	public final Colour getColor() {
		return color;
	}

	public final Trait[] getTraits() {
		return (Trait[]) traits.toArray();
	}

	private String cardImageURL;

	public final String getCardImageURL() {
		return cardImageURL;
	}

	public final int getLevel() {
		return level;
	}

	public final int getCost() {
		return cost;
	}

	public final int getPower() {
		return power;
	}

	public final int getSoul() {
		return soul;
	}

	public final Trigger[] getTriggers() {
		return triggers;
	}

	public final CardType getType() {
		return type;
	}

	public CardPage(String url, WebDriver driver) {
		this.driver = driver;
		driver.get(url);
		try {
			saveToFile();
		} catch (ParseJPException e) {
			try {
				String filename = "traits.txt";
				new File(filename).createNewFile();
				String text = System.lineSeparator() + e.getMessage() ;
			    Files.write(Paths.get(filename), text.getBytes(), StandardOpenOption.APPEND);
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (Exception e) {
			System.exit(0);
		}
		writeToXML();

	}
	
	

	public String getID() {
		return ID;
	}

	private void createFolder(String folderName) {
		File folder = new File(folderName);
		folder.mkdirs();
	}

	private void saveToFile() {
		ID = driver.findElement(By.cssSelector("div.information_box > table.heading > tbody > tr.headline.gr_bg > th"))
				.getText();
		System.out.println(ID);
		String setID = ID.split("-")[0];
		createFolder("CardData/" + setID);
		cardImageURL = driver
				.findElement(
						By.cssSelector("#main > div.card_detail_box > div.operation_box > div.image_box > p img"))
				.getAttribute("src");

		WebElement table = driver.findElement(
				By.cssSelector("#main > div.card_detail_box > div.information_box > table.middle > tbody"));
		WebElement row1 = table.findElement(By.cssSelector("tr:nth-child(2)"));
		WebElement row2 = table.findElement(By.cssSelector("tr:nth-child(4)"));
		String typeJP = row1.findElement(By.cssSelector("td:nth-child(1)")).getText();
		type = CardType.parseJP(typeJP);

		if (type != CardType.CLIMAX) {
			level = Integer.parseInt(row1.findElement(By.cssSelector("td:nth-child(2)")).getText());
			cost = Integer.parseInt(row1.findElement(By.cssSelector("td:nth-child(3)")).getText());
		}
		if (type == CardType.CHARACTER) {
			power = Integer.parseInt(row1.findElement(By.cssSelector("td:nth-child(4)")).getText());
			soul = Integer.parseInt(row1.findElement(By.cssSelector("td:nth-child(5)")).getText());
			String traitText = row2.findElement(By.cssSelector("td:nth-child(1)")).getText();
			for (String trait : traitText.split("・")) {
				traits.add(Trait.parseString(trait));
			}
		}

		String triggerURL = row1.findElement(By.cssSelector("td:nth-child(6) > img")).getAttribute("src");
		triggers = Trigger.parseImage(triggerURL);

		String colorText = row2.findElement(By.cssSelector("td:nth-child(2)")).getText();
		color = Colour.parseString(colorText);
		flavourText = driver
				.findElement(By.cssSelector("div.information_box > table:nth-child(3) > tbody > tr:nth-child(2) > td"))
				.getText();
		abilityJP = driver
				.findElement(By.cssSelector("div.information_box > table:nth-child(4) > tbody > tr:nth-child(2) > td"))
				.getText();
	}

	public void writeToXML() {
		String filename = ID + ".xml";
	}

}
