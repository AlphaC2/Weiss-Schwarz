package game.scrapper;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import game.io.DriverUtilities;

public class DownloadImages {

	@Test
	public void downloadDirectory(){
		WebDriver driver = DriverUtilities.createDriver(false, true);
		new DownloadImageFile("CardData\\AB\\W11\\AB-W11-TEST.xml", driver);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.quit();
	}
}
