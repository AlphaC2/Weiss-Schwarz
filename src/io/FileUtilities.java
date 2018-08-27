package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import model.exceptions.ParseJPException;

public class FileUtilities {
	

	public static void appendToFile(String filename, String s) {
		try {
			new File(filename).createNewFile();
			String text = System.lineSeparator() + s;
			for (String line : Files.readAllLines(Paths.get(filename))) {
				if (line.equals(s)){
					return;
				}
			}
			
			Files.write(Paths.get(filename), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public static List<String> getTraits(String traitText){
		List<String> traits = new ArrayList<>();;
		String splitChar="･";
		if (traitText.contains("・")){
			splitChar = "・";
		}
		for (String trait : traitText.split(splitChar)) {
			String translatedTrait = translateJP(trait);
			traits.add(translatedTrait);
		}
		if (traits.contains(null)){
			throw new ParseJPException(traitText);
		}
		return traits;
	}
	
	public static String translateJP(String trait){
	
		String filename = "traits.txt";
		try {
			new File(filename).createNewFile();
			List<String> lines = Files.readAllLines(Paths.get(filename));
			for (String line : lines) {
				if (line.indexOf(',') != line.length()-1){
					String JP = line.split(",")[1];
					if (JP.equals(trait)){
						return line.split(",")[0];
					}
				}
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return null;
	}

}
