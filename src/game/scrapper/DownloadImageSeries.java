package game.scrapper;

import java.io.File;

import org.openqa.selenium.WebDriver;

public class DownloadImageSeries {
	private String series;
	private WebDriver driver;
	
	public DownloadImageSeries(String series, WebDriver driver) {
		super();
		this.series = series;
		this.driver = driver;
		run();
	}
	
	private void run(){
		File seriesFolder = new File( "CardData/" + series);
		if(!seriesFolder.exists())
			return;
		
		for (String	set: seriesFolder.list()) {
			File setFolder = new File(seriesFolder + "/" + set);
			for (String cardPath : setFolder.list()) {
//				if(cardPath.equals("AB-W11-TEST.xml")){
					System.out.println(setFolder + "/" + cardPath);
					new DownloadImageFile(setFolder + "/" + cardPath, driver);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//				}
			}
			
		}
		
	}
}
