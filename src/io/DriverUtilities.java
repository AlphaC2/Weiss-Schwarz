package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverUtilities {

	public static WebDriver createDriver(boolean headless) {
		String driverName = getDriverName();
		switch (driverName) {
		case "Google Chrome":
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			if (headless)
			options.addArguments("--headless");
			return new ChromeDriver(options);
			
		case "FireFox":
			System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			return new FirefoxDriver();
		}
		return null;
	}

	private static String getDriverName() {
		Properties config = new Properties();
		String driverName = "";
		try {
			config.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String key : config.stringPropertyNames()) {
			if (key.equalsIgnoreCase("Browser")) {
				driverName = config.getProperty(key);
			}
		}
		return driverName;
	}

}
