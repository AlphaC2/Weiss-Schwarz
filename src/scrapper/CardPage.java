package scrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.FileUtilities;
import model.card.CardType;
import model.card.Colour;
import model.card.Trigger;
import model.exceptions.ParseJPException;
import util.Util;

public class CardPage {

	private WebDriver driver;
	private String name;

	public final String getName() {
		return name;
	}

	private String rarity;
	private String ID;
	private CardType cardType;
	private int level;
	private int cost;
	private int power;
	private int soul;
	private Trigger[] triggers;
	private List<String> traits;
	private Colour color;
	private Document doc;
	private String folder;

	private final static Logger log = Logger.getLogger(CardPage.class);

	public final String getFlavourText() {
		return flavourText;
	}

	public final String getAbilityJP() {
		return abilityJP;
	}

	private String flavourText;
	private String abilityJP;
	private File f;

	public final Colour getColor() {
		return color;
	}

	public final String[] getTraits() {
		return (String[]) traits.toArray();
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
		return cardType;
	}

	public CardPage(String url, WebDriver driver) {
		this.driver = driver;
		driver.get(url);
		try {
			saveToFile();
		} catch (ParseJPException e) {
			log.error(e.getMessage() + url);
			log.error(Util.formatStackTrace(e));
		} catch (Exception e) {
			log.error("Error parsing: " + url);
			log.error(Util.formatStackTrace(e));
		}

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
		String setID = ID.split("-")[0];
		folder = "CardData/" + setID;
		ID = ID.replace("/", "-");
		String filename = folder + "/" +ID + ".xml";
		f = new File(filename);
		if (f.exists()){
			log.debug("Skipped card "+ ID);
			return;
		} else {
			log.debug("Parsing card " + ID);
		}
		
		
		String s = driver
				.findElement(By.cssSelector(
						"#main > div.card_detail_box> div.information_box > table.heading > tbody > tr:nth-child(2) > td"))
				.getText().split("\n")[0];
		name = s.split(" ")[1];
		rarity = s.split(" ")[0];
		
		createFolder(folder);
		cardImageURL = driver
				.findElement(By.cssSelector("#main > div.card_detail_box > div.operation_box > div.image_box > p img"))
				.getAttribute("src");

		WebElement table = driver.findElement(
				By.cssSelector("#main > div.card_detail_box > div.information_box > table.middle > tbody"));
		WebElement row1 = table.findElement(By.cssSelector("tr:nth-child(2)"));
		WebElement row2 = table.findElement(By.cssSelector("tr:nth-child(4)"));
		String typeJP = row1.findElement(By.cssSelector("td:nth-child(1)")).getText();
		String powerText = row1.findElement(By.cssSelector("td:nth-child(4)")).getText();
		String soulText = row1.findElement(By.cssSelector("td:nth-child(5)")).getText();
		String levelText = row1.findElement(By.cssSelector("td:nth-child(2)")).getText();
		String costText = row1.findElement(By.cssSelector("td:nth-child(3)")).getText();
		
		// Attempt to determine the card type based on other fields
		if (typeJP.isEmpty()){
			if (levelText.equals("-") || costText.equals("-")){
				cardType = CardType.CLIMAX;
			}else if (powerText.equals("-") || soulText.equals("-")){
				cardType = CardType.EVENT;
			} else {
				cardType = CardType.CHARACTER;
			}
		} else {
			cardType = CardType.parseJP(typeJP);
		}

		if (cardType != CardType.CLIMAX) {
			level = Integer.parseInt(levelText);
			cost = Integer.parseInt(costText);
		}
		if (cardType == CardType.CHARACTER) {
			power = Integer.parseInt(powerText);
			soul = Integer.parseInt(soulText);
			String traitText = row2.findElement(By.cssSelector("td:nth-child(1)")).getText();
			traits = FileUtilities.getTraits(traitText);
		}

		String triggerURL = row1.findElement(By.cssSelector("td:nth-child(6) > img")).getAttribute("src");
		triggers = Trigger.parseImage(triggerURL);

		String colorText = row2.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
		color = Colour.parseString(colorText);
		flavourText = driver
				.findElement(By.cssSelector("div.information_box > table:nth-child(3) > tbody > tr:nth-child(2) > td"))
				.getText();
		abilityJP = driver
				.findElement(By.cssSelector("div.information_box > table:nth-child(4) > tbody > tr:nth-child(2) > td"))
				.getText();
		writeToXML();
	}

	private void addNode(Element root, String key, String value) {
		Element newNode = doc.createElement(key);
		root.appendChild(newNode);
		newNode.appendChild(doc.createTextNode(value));
	}

	public void writeToXML() {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Card");
			doc.appendChild(rootElement);

			addNode(rootElement, "Type", cardType.toString());
			addNode(rootElement, "ID", ID);
			addNode(rootElement, "Name", name);
			addNode(rootElement, "Rarity", rarity);
			addNode(rootElement, "ImageURL", cardImageURL);
			addNode(rootElement, "Colour", color.toString());
			addNode(rootElement, "Level", "" + level);
			addNode(rootElement, "Cost", "" + cost);
			for (Trigger trigger : triggers) {
				addNode(rootElement, "Trigger", trigger.toString());
			}
			addNode(rootElement, "FlavourText", flavourText);
			addNode(rootElement, "Ability", abilityJP);
			if (cardType == CardType.CHARACTER) {
				Element characterNode = doc.createElement("Character");
				rootElement.appendChild(characterNode);

				addNode(characterNode, "Power", "" + power);
				for (String trait : traits) {
					addNode(characterNode, "Trait", trait);
				}
				addNode(characterNode, "Soul", "" + soul);

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			f.createNewFile();
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
