package library;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

import modeles.ObjTest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.BaseTest;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.relevantcodes.extentreports.LogStatus;

import utils.ExtentTestManager;

/**
 * @author Marc LAVERROUX
 * 
 *         Classe d'appel aux fonctions Selenium en fonction d'un mot clé fourni
 *         dans un fichiser csv
 */

public class ScriptsTechniques {

	private static int timeWait = 15;
	private static WebElement From, To;

	/**
	 * Fonction appelé par le programme principal pour analyser les mots cles du
	 * fichier csv.
	 * 
	 * @param ObjTestIterator
	 * @throws IOException
	 */
	public static void AnalyseData(Iterator<ObjTest> ObjTestIterator, WebDriver driver) throws IOException {
		while (ObjTestIterator.hasNext()) {
			ObjTest objTest = ObjTestIterator.next();

			switch (objTest.getActionType()) {
			case "drag&drop":
				dragAndDrop(driver, objTest.getByType());
				break;
			case "click":
				clickElement(driver, objTest.getByType(), objTest.getTexteLog());
				break;
			case "input":
				sendText(driver, objTest.getByType(), objTest.getTexteLog(), objTest.getTexte());
				break;
			case "verif":
				verifTexte(driver, objTest.getByType(), objTest.getTexteLog(), objTest.getTexte());
				break;
			case "select":
				selectElementListByText(driver, objTest.getByType(), objTest.getTexteLog(), objTest.getTexte());
				break;
			case "clear":
				clearText(driver, objTest.getByType(), objTest.getTexteLog());
				break;
			case "exist":
				isElementExiste(driver, objTest.getByType(), objTest.getTexteLog());
				break;
			case "frame":
				switchToFrame(driver, objTest.getByType(), objTest.getTexteLog());
				break;
			default:
				ExtentTestManager.getTest().log(LogStatus.FATAL, "Mot clef inconnu dans le choix ActionType");
				break;
			}

		}
	}

	public static void dragAndDrop(WebDriver driver, String Element) throws IOException {

		String[] Parse = Element.split(",");
		boolean result = false;
		switch (Parse[0]) {
		case "id":
			From = driver.findElement(By.id(Parse[1]));
			break;

		case "name":
			From = driver.findElement(By.name(Parse[1]));
			break;

		case "xpath":
			From = driver.findElement(By.xpath(Parse[1]));
			break;

		case "css":
			From = driver.findElement(By.cssSelector(Parse[1]));
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour entre un texte");
			break;

		}
		switch (Parse[2]) {
		case "id":
			To = driver.findElement(By.id(Parse[3]));
			break;

		case "name":
			To = driver.findElement(By.name(Parse[3]));
			break;

		case "xpath":
			To = driver.findElement(By.xpath(Parse[3]));
			break;

		case "css":
			To = driver.findElement(By.cssSelector(Parse[3]));
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour entre un texte");
			break;

		}
		// Dragged and dropped.
		try {
			new Actions(driver).dragAndDrop(From, To).build().perform();
			result = true;
		} catch (Exception e) {
			result = false;
		}

		sendLogs(result, Parse[0], Parse[1], driver);
	}

	/**
	 * Renseigne un champ texte
	 * 
	 * @param driver
	 * @param Element sous la forme id,nom_de_id / xpath,chemin / etc ...
	 * @param Texte   descriptif de l'action réaliser
	 * @param Texte   a renseigner
	 * @throws IOException
	 */
	public static void sendText(WebDriver driver, String Element, String texteLog, String TextEntrer)
			throws IOException {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		Boolean result = false;

		switch (Parse[0]) {
		case "id":
			result = sendText(By.id(Parse[1]), TextEntrer, driver);
			break;

		case "name":
			result = sendText(By.name(Parse[1]), TextEntrer, driver);
			break;

		case "xpath":
			result = sendText(By.xpath(Parse[1]), TextEntrer, driver);
			break;

		case "css":
			result = sendText(By.cssSelector(Parse[1]), TextEntrer, driver);
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour entre un texte");
			break;

		}

		sendLogs(result, Parse[0], Parse[1], driver);

	}

	/**
	 * Supprime un texte présent dans un champ texte
	 * 
	 * @param driver
	 * @param xpath
	 * @throws IOException
	 */
	public static void clearText(WebDriver driver, String Element, String texteLog) throws IOException {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		Boolean result = false;

		switch (Parse[0]) {
		case "id":
			result = clearText(By.id(Parse[1]), driver);
			break;

		case "name":
			result = clearText(By.name(Parse[1]), driver);
			break;

		case "xpath":
			result = clearText(By.xpath(Parse[1]), driver);
			break;

		case "css":
			result = clearText(By.cssSelector(Parse[1]), driver);
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour effacer un texte");
			break;

		}

		sendLogs(result, Parse[0], Parse[1], driver);

	}

	/**
	 * Click sur un element
	 * 
	 * @param driver
	 * @param Element sous la forme id,nom_de_id / xpath,chemin / etc ... (accepte
	 *                id,name,css,xpath,linkText)
	 * @param Texte   descriptif de l'action réaliser
	 * @throws IOException
	 */
	public static void clickElement(WebDriver driver, String Element, String texteLog) throws IOException {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		Boolean result = false;

		switch (Parse[0]) {
		case "id":
			result = clickOnElement(By.id(Parse[1]), driver);
			break;

		case "name":
			result = clickOnElement(By.name(Parse[1]), driver);
			break;

		case "xpath":
			result = clickOnElement(By.xpath(Parse[1]), driver);
			break;

		case "css":
			result = clickOnElement(By.cssSelector(Parse[1]), driver);
			break;

		case "linkText":
			result = clickOnElement(By.linkText(Parse[1]), driver);
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour le clique sur un Element");
			break;

		}

		sendLogs(result, Parse[0], Parse[1], driver);

	}

	/**
	 * Vérifie la présence d'un texte
	 * 
	 * @param driver
	 * @param Element sous la forme id,nom_de_id / xpath,chemin / etc ...
	 * @param Texte   descriptif de l'action réaliser
	 * @param Texte   a vérifier
	 * @throws IOException
	 */
	public static void verifTexte(WebDriver driver, String Element, String texteLog, String TextEntrer)
			throws IOException {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		Boolean result = false;

		switch (Parse[0]) {
		case "id":
			result = isTextePresent(By.id(Parse[1]), driver, TextEntrer);
			break;

		case "name":
			result = isTextePresent(By.name(Parse[1]), driver, TextEntrer);

			break;

		case "css":
			result = isTextePresent(By.cssSelector(Parse[1]), driver, TextEntrer);
			break;

		case "xpath":
			result = isTextePresent(By.xpath(Parse[1]), driver, TextEntrer);
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour la verification de texte");
			break;

		}

		sendLogs(result, Parse[0], Parse[1], driver);

	}

	/**
	 * Verifie la presence d'un element graphique
	 * 
	 * @param driver
	 * @param xpath
	 * @throws IOException
	 */
	public static void isElementExiste(WebDriver driver, String Element, String texteLog) throws IOException {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		Boolean result = false;

		switch (Parse[0]) {

		case "id":
			result = isElementPresent(By.id(Parse[1]), driver);
			break;

		case "name":
			result = isElementPresent(By.name(Parse[1]), driver);
			break;

		case "css":
			result = isElementPresent(By.cssSelector(Parse[1]), driver);
			break;

		case "xpath":
			result = isElementPresent(By.xpath(Parse[1]), driver);
			break;

		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour la verification de la présence d'un élément");
			break;
		}

		sendLogs(result, Parse[0], Parse[1], driver);

	}

	/**
	 * Selectionner un element dans une liste
	 * 
	 * @param driver
	 * @param name   : nom de la liste
	 * @param text   : texte e selectionner
	 * @throws IOException
	 */
	public static void selectElementListByText(WebDriver driver, String Element, String texteLog, String TextEntrer)
			throws IOException {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		Boolean result = false;

		try {
			new Select(driver.findElement(By.name(Parse[1]))).selectByVisibleText(TextEntrer);
			ExtentTestManager.getTest().log(LogStatus.INFO,
					"Element trouve by Name et Texte =" + Parse[1] + "/" + TextEntrer);

		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.WARNING,
					"Element NON trouve by Name et Texte =" + Parse[1] + "/" + TextEntrer);
		}
	}

	/**********************************************
	 * 
	 * Fonctions Génériques
	 * 
	 **********************************************/

	/**
	 * Fonction d'Appel générique Présence d'un élément
	 * 
	 * @param by
	 * @param driver
	 * @return
	 */
	private static boolean isElementPresent(By by, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, timeWait);

		try {

			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			assertTrue(driver.findElement(by).isDisplayed());
			return true;
		} catch (Exception e) {
			highLight(driver, driver.findElement(by));
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, e.getMessage(),
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		}
	}

	/**
	 * Fonction d'Appel générique Présence d'un texte
	 * 
	 * @param by
	 * @param driver
	 * @param TextEntrer
	 * @return
	 * @throws IOException
	 */
	private static boolean isTextePresent(By by, WebDriver driver, String TextEntrer) throws IOException {

		WebDriverWait wait = new WebDriverWait(driver, timeWait);

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			assertEquals(driver.findElement(by).getText(), StringEscapeUtils.unescapeJava(TextEntrer));
			return true;
		} catch (AssertionError e) {
			highLight(driver, driver.findElement(by));
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, e.getMessage(),
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		}

	}

	/**
	 * Fonction Générique Click Element
	 * 
	 * @param by
	 * @param driver
	 * @return
	 */
	private static boolean clickOnElement(By by, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, timeWait);

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			WebElement element = driver.findElement(by);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().perform();
			return true;
		} catch (Exception e) {
			highLight(driver, driver.findElement(by));
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, e.getMessage(),
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		}
	}

	/**
	 * Fonction Générique Envoi de Texte
	 * 
	 * @param by
	 * @param TextEntrer
	 * @param driver
	 * @return
	 */
	private static boolean sendText(By by, String TextEntrer, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, timeWait);

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			driver.findElement(by).sendKeys(TextEntrer);
			return true;
		} catch (Exception e) {
			highLight(driver, driver.findElement(by));
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, e.getMessage(),
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		}
	}

	/**
	 * Fonction d'ecriture des logs
	 * 
	 * @param result
	 * @param by
	 * @param byIdent
	 * @param driver
	 * @throws IOException
	 */
	private static void sendLogs(Boolean result, String by, String byIdent, WebDriver driver) throws IOException {
		// if (result) {
		// ExtentTestManager.getTest().log(LogStatus.INFO,
		// "Element trouve by " + by + " =" + byIdent);
		// } else {
		// ExtentTestManager.getTest().log(LogStatus.WARNING,
		// "Element NON trouve by " + by + " =" + byIdent);
		// }
		if (!result) {
			ExtentTestManager.getTest().log(LogStatus.WARNING, "Element NON trouve by " + by + " =" + byIdent);
		}
	}

	/**
	 * Fonction générique de nettoyage d'un champ
	 * 
	 * @param id
	 * @param textEntrer
	 * @param driver
	 * @return
	 */
	private static boolean clearText(By by, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, timeWait);

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			driver.findElement(by).clear();
			return true;
		} catch (Exception e) {
			highLight(driver, driver.findElement(by));
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, e.getMessage(),
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		}
	}

	/**
	 * Fonction pour switcher dasn une frame
	 * 
	 * @param driver
	 * @param frame
	 * @param texteLog
	 * @return
	 */

	public static boolean switchToFrame(WebDriver driver, String Element, String texteLog) {

		ExtentTestManager.getTest().log(LogStatus.INFO, texteLog);

		String[] Parse = Element.split(",");
		By result = null;

		WebDriverWait wait = new WebDriverWait(driver, timeWait);

		switch (Parse[0]) {

		case "id":
			result = By.id(Parse[1]);
			break;

		case "name":
			result = By.name(Parse[1]);
			break;
		default:
			ExtentTestManager.getTest().log(LogStatus.FATAL,
					"Mot clef inconnu dans le choix ByType pour le choix de la frame : montextedelog;frame;id ou name,nom de la frame;''");
			break;
		}

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(result));
			driver.switchTo().frame(Parse[1]);
			return true;
		} catch (NoSuchFrameException e) {
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, "Frame non trouvé",
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		} catch (Exception e) {
			String imagePath = "ExtentReports/screenshot/" + LogStatus.WARNING + Calendar.getInstance() + ".png";
			Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).save(imagePath);
			ExtentTestManager.getTest().log(LogStatus.WARNING, "Impossible de naviguer dans la frame",
					ExtentTestManager.getTest().addScreenCapture(imagePath));
			return false;
		}
	}

	/**
	 * Met en surbrillance un element A utiliser dans le cas d'un debug visuel Est
	 * utiliser dans le cas des logs en erreurs
	 * 
	 * @param driver
	 * @param elem
	 */
	public static void highLight(WebDriver driver, WebElement elem) {
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", elem);
		}
	}
}
