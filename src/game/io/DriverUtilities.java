package game.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
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
			if (headless) {
				 options.addArguments("--headless");
			}
			HashMap<String,Object> prefs = new HashMap<>();
			prefs.put("profile.managed_default_content_settings.images", 2);
			options.setExperimentalOption("prefs", prefs);
			return new ChromeDriver(options);

		case "FireFox":
			/*System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			FirefoxOptions options2 = new FirefoxOptions();
			if (headless) {
				 options2.addArguments("--headless");
			}
			options2.addPreference("permissions.default.image", 2);*/
			FirefoxDriver driver = new FirefoxDriver();
			return driver;
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
