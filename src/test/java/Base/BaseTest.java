package Base;
 
import static org.testng.Assert.fail;

import java.net.MalformedURLException;

import library.OpenWebSite;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
 
/**
 * @author Marc LAVERROUX
 * 
 * Classe contenant les appels TestNg exécuter avant et après un scenario de test
 */

public class BaseTest {

	public WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}


	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	private StringBuffer verificationErrors = new StringBuffer();
	public String FileData = "";
 

	@Parameters({ "platform", "browser", "version", "baseUrl" })
	@BeforeTest(alwaysRun = true)
	public void setUp(String platform, String browser, String version,
			String baseUrl) throws Exception {
		try {
			driver = OpenWebSite.openLocal(platform, browser, version, baseUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	@AfterTest(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
    

}