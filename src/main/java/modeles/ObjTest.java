package modeles;


import com.opencsv.bean.CsvBindByName;
/**
 * @author Marc LAVERROUX
 * 
 * Classe Objet chargé depuis fichier csv
 */

public class ObjTest {
	@CsvBindByName
	private String texteLog;
	
    public String getTexteLog() {
		return texteLog;
	}

	public void setTexteLog(String texteLog) {
		this.texteLog = texteLog;
	}

	@CsvBindByName(column = "ActionType", required = true)
    private String actionType;

    @CsvBindByName(column = "ByType", required = true)
    private String byType;

    @CsvBindByName(column = "texte")
    private String texte;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getByType() {
		return byType;
	}

	public void setByType(String byType) {
		this.byType = byType;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}




}
