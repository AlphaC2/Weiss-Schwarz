package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import model.exceptions.ParseJPException;

public class FileUtilities {
	
	public static List<String> getTraits(String traitText){

		
		List<String> traits = new ArrayList<>();;
		String splitChar="･";
		if (traitText.contains("・")){
			splitChar = "・";
		}
		
		if (StringUtils.countMatches(traitText, splitChar)>1){
			int index = traitText.lastIndexOf(splitChar);
			String trait1 = traitText.substring(0,index);
			String trait2 = traitText.substring(index+1,traitText.length());
			traits.add(trait1);
			traits.add(trait2);
		} else {
			for (String trait : traitText.split(splitChar)) {
				if (!trait.equals("-")){
					String translatedTrait = translateJP(trait);
					traits.add(translatedTrait);
				}
			}
		}
		
		if (traits.contains(null) || traits.contains("")){
			throw new ParseJPException("Missing Trait " + traitText);
		}
		return traits;
	}
	
	public static String translateJP(String trait){
		trait = trait.trim();
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
