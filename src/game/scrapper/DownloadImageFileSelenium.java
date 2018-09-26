package game.scrapper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DownloadImageFileSelenium {
	private String url;
	private WebDriver driver;
	
	public DownloadImageFileSelenium(String fileUrl, WebDriver driver) {
		super();
		this.url = fileUrl;
		this.driver = driver;
		run();
	}
	
	private void run(){
		File fXmlFile = new File(url);
		
		String imageUrl = "";
		String imageFilePath = "";
		try(BufferedReader br = new BufferedReader(new FileReader(fXmlFile))){
			//Read the image URL from xml file
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			imageUrl = doc.getElementsByTagName("ImageURL").item(0).getTextContent();
			
			//Get the output data
			imageFilePath = fXmlFile.getAbsolutePath().replaceFirst("CardData", "Images").replaceFirst(".xml", ".jpg");
			File imageFile = new File(imageFilePath);
			imageFile.getParentFile().mkdirs();
			
			//Open image in browser
			driver.get(imageUrl);
			
			//Open rightclick menu
			WebElement Image = driver.findElement(By.tagName("img"));
			Actions action= new Actions(driver);
			action.contextClick(Image).build().perform();

			//Click SaveAs
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			
			//Put the full path into the clipboard
			StringSelection stringSelection = new StringSelection(imageFilePath);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, stringSelection);
			
			//Paste in name
			robot.delay(200);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.delay(200);
			robot.keyPress(KeyEvent.VK_V);
			robot.delay(200);
			robot.keyRelease(KeyEvent.VK_V);
			robot.delay(200);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(200);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(200);
			robot.keyRelease(KeyEvent.VK_ENTER);

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}

//		System.out.println("PassedPath:" + url);
//		System.out.println("FilePath:" + fXmlFile.getAbsolutePath());
//		System.out.println("ImageFilePath:" + imageFilePath);
//		System.out.println("ImageURL:" + imageUrl);
	}
}
