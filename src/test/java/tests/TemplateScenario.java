package tests;

import org.testng.annotations.Test;

import utils.ExtentTestManager;
import Base.BaseTest;
import library.ScriptsCSV;
import library.ScriptsTechniques;



public class TemplateScenario extends BaseTest{


	@Test(priority = 0, description="")
	public void Test_Template() throws Exception {

		//Entrez la fonction du scenario par un texte descriptif
		ExtentTestManager.getTest().setDescription("Descriptif du scenario");

		//Entrez le nom du fichier csv a analyser
		FileData = "src/test/resources/data/xxxxxxxxx";
		//Ne pas toucher
		ScriptsTechniques.AnalyseData(ScriptsCSV.ReadDataCSV(FileData), driver);

	}
	

}
