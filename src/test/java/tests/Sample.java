package tests;

import org.testng.annotations.Test;

import utils.ExtentTestManager;
import Base.BaseTest;
import library.ScriptsCSV;
import library.ScriptsTechniques;



public class Sample extends BaseTest{


	@Test(priority = 0, description="")
	public void Sample() throws Exception {

		//Entrez la fonction du scenario par un texte descriptif
		ExtentTestManager.getTest().setDescription("Scenario de demo");

		//Entrez le nom du fichier csv a analyser
		FileData = "src/test/resources/data/Sample";
		//Ne pas toucher
		ScriptsTechniques.AnalyseData(ScriptsCSV.ReadDataCSV(FileData), driver);

	}
	

}
