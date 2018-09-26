package game.scrapper;

import java.io.File;

public class DownloadImageSeries {
	private String series;
	
	public DownloadImageSeries(String series) {
		super();
		this.series = series;
		run();
	}
	
	private void run(){
		File seriesFolder = new File( "CardData/" + series);
		if(!seriesFolder.exists())
			return;
		
		for (String	set: seriesFolder.list()) {
			File setFolder = new File(seriesFolder + "/" + set);
			for (String cardPath : setFolder.list()) {
					System.out.println(setFolder + "/" + cardPath);
					new DownloadImageFile(setFolder + "/" + cardPath);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			
		}
		
	}
}
