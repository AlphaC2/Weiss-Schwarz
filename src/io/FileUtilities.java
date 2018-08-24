package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtilities {

	public static void appendToFile(String filename, String s) {
		try {
			new File(filename).createNewFile();
			String text = System.lineSeparator() + s;
			Files.write(Paths.get(filename), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

}
