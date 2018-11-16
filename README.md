# Automate de lancement de tests Selenium

##Détail
Il s'agit d'un projet JAVA sous Maven.
Celui ci prend en paramètre un fichier au format csv, placé dans src/test/resources
Ce dernier est indiqué dans les scenaios situés dans src/test/java/tests

Le lancement se fait via le fichier Launcher.xml (type TestNG)
Ce fichier XML contient un descriptif des scenarii devant être lancé.

##Utilisation

1/Mettre le fichier au format csv dans src/test/resources
2/Indiquez son chemin dans le scenario appelant ce fichier (le scenario se trouve dans src/test/java/tests). Un template est disponible dans ce chemin
3/Renseignez le scenario dans le fichier Launcher.xml
4/Lancez le Launcher.xml
5/En fin d'exécution un fichier de log au format HTML est généré dans le répertoire ExtendReports (à ouvrir avec chrome)

##Notes 
Le fichier XML contient le nécessaire pour lancer chaque scenario :
*la platform d'excéution : WINDOWS, Linux (utiliser dans le cas d'un selenium grid couplé à des noeuds Selenium)
*le browser ou sera réalisé le test (chrome, chromium, firefox, ie, edge)
*l'URL du site à tester
*la version du browser (utiliser dans le cas d'un selenium grid couplé à des noeuds Selenium)
*le nom du scenario à lancer

Le répertoire driver situé sous src/main/resources, doit contenir les WebDrivers nécessaires au lancement

Le fichier au format csv contient les donnees en entrée : le texte de log / l'action à réaliser / le type de référence pour selenium / si nécessaire le texte à vérifier ou bien le texte à passer en paramètre
exemple :
Click sur "My Account";click;xpath,//*[@id="li_myaccount"]/a;""
Verification du texte de la page;verif;id,textelabel;"Ceci est un exemple"

Les mots clés sont définis dans la classe SCcriptsTechniques. Pour en ajouter de nouveau il suffit d'ajouter une nouvelle fonction au sein de cette classe.
Les mots clefs existant sont :
drag&drop / click / input (pour entrer du texte) / verif (présence d'un texte) / select (selection dans une liste) / clear (mise a zero d'un champ) / exist (présence d'une image ) / frame (sélection d'une frame)

Nota : le champ "type de référence", se décompose en 2 entrées séparées par une virgule (","). La première entrée indique s'il s'agit d'un :
*id
*name
*css
*xpath

La seconde entrée indique le nom de l'id ou du name ou du css, ou bien le chemin du xpath

Changez le temps d'attente de la présence d'un élément se fait dans la classe ScriptsTechniques.java : 
	private static int timeWait = 15;
	







