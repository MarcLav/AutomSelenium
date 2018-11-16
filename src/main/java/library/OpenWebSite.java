package library;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@SuppressWarnings("unused")
public class OpenWebSite {

	public static WebDriver driver;
	public static DesiredCapabilities capabilities;
	public static ChromeOptions chromeOptions;
	public static Runtime rt = Runtime.getRuntime();

	/**
	 * Fonction d'initialisation des tests en Local
	 * Nota : avoir chromium portable /firefox portable. La fonction considère qu'ils sont dans le chemin suivant :
	 * D:\AppPortable\Chromium et D:\AppPortable\Firefox
	 * Dézipper le fichier AppPortable présent dans le fichier src à la racine du lecteur D: 
	 * @param platform --> indiquez dans le fichier XML
	 * @param browser --> indiquez dans le fichier XML
	 * @param version --> indiquez dans le fichier XML
	 * @param baseUrl --> indiquez dans le fichier XML
	 * @return
	 * @throws MalformedURLException
	 */

	@SuppressWarnings("deprecation")
	public static WebDriver openLocal(String platform, String browser, String version, String baseUrl) throws MalformedURLException {

				DesiredCapabilities cap = new DesiredCapabilities();
				if (browser.equals(Browser.chrome.toString())) {
					System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
					
					capabilities = DesiredCapabilities.chrome();

					chromeOptions = new ChromeOptions();

					chromeOptions.addArguments("disable-infobars");
					chromeOptions.addArguments("--disable-extensions");
					chromeOptions.addArguments("--disable-notifications");
					chromeOptions.addArguments("--start-maximized");
					chromeOptions.addArguments("--disable-web-security");
					chromeOptions.addArguments("--no-proxy-server");
					chromeOptions.addArguments("--enable-automation");
					chromeOptions.addArguments("--disable-save-password-bubble");
					chromeOptions.addArguments("--incognito");
					chromeOptions.setExperimentalOption("useAutomationExtension", false);
					
					capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					
					
					driver = new ChromeDriver(capabilities);
				}
		
		if (browser.equals(Browser.firefox.toString())) {

			
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability("marionette", false);

			driver = new FirefoxDriver(dc);	
		}
		
		
		if (browser.equals(Browser.edge.toString())) {
			System.setProperty("webdriver.edge.driver", "src/main/resources/driver/MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}
		if (browser.equals(Browser.ie.toString())) {
			System.setProperty("webdriver.ie.driver", "src/main/resources/driver/IEDriver.exe");
			driver = new InternetExplorerDriver();

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		driver.get(baseUrl);

		return driver;
	}
	
}


		
