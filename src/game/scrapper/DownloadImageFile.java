package game.scrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DownloadImageFile {
	private String url;
	
	public DownloadImageFile(String fileUrl) {
		super();
		this.url = fileUrl;
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
			
			try(InputStream in = new URL(imageUrl).openStream()){
				Files.copy(in, Paths.get(imageFilePath));
			}catch(IOException e){
				e.printStackTrace();
			}
			
		} catch (Exception e1) {
			System.out.println("File already exists: " + e1.getMessage());
		}


	}
}
