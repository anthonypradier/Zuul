import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Anthony Pradier
 * @version 1.4, 03/10/2024
 */
public class GameEngine {
    /**
     * Parser
     */
    private Parser aParser;
    
    /**
     * Interface utilisateur
     */
    private UserInterface aGui;

    
    /**
     * Tableau contenant les fichiers text des PNJ
     */
    private static final File[] PNJ_FILES = new File("assets/PNJ_Commands").listFiles();
    
    /**
     * Nombre d'action effectué
     */ 
    public int aNbActions;
    
    /**
     * Nombre max d'action faisable
     */ 
    public int aMaxActions;
    
    /**
     * HashMap qui contient les salles du jeu
     */ 
    public static final HashMap<String, Room> GAME_ROOM = new HashMap<String, Room>();
    
    /**
     * Set des salles
     */ 
    public static Set<String> ROOM_NAMES;
    
    /**
     * HashMap qui contient les Items du jeu
     */ 
    public static final HashMap<String, Item> GAME_ITEM = new HashMap<String, Item>();
    
    /**
     * Set d'Items
     */ 
    public static Set<String> ITEM_NAMES;
    
    /**
     * HashMap qui contient les Players du jeu
     */ 
    public static final HashMap<String, Player> GAME_PLAYER = new HashMap<String, Player>();
    
    /**
     * Set des Players
     */ 
    public static Set<String> PLAYER_NAMES;
    
    /**
     * HashMap qui contient les PNJ du jeu
     */ 
    public static final HashMap<String, PNJ> GAME_PNJ = new HashMap<String, PNJ>();
    
    /**
     * Set des PNJ
     */ 
    public static Set<String> PNJ_NAMES;
    
    /**
     * Joueur principal
     */
    private MC aMC;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine() {
        this.aParser = new Parser();
        this.createRooms();
        
        this.aNbActions = 0;
        this.aMaxActions = 50;
    }
    
    /**
     * Attribution de l'interface graphique à aGui
     * @param pUserInterface Interface graphique
     */
    public void setGUI(final UserInterface pUserInterface) {
        this.aGui = pUserInterface;
        this.printWelcome();
    }
    
    /**
     * Renvoie l'interface utilisateur
     * @return L'interface utilisateur
     */
    public UserInterface getUI() {
        return this.aGui;
    }
    
    /**
     * Renvoie l'instance du joueur
     * @return L'instance du joueur
     */
    public MC getMC() {
        return this.aMC;
    }
    
    /**
     * Augmente le nombre max d'actions possibles
     * @param pNb Nombre d'actions
     */
    public void addMaxActions(final int pNb) {
        this.aMaxActions += pNb;
    }
    
    /**
     * Reduit le nombre max d'actions possibles
     * @param pNb Nombre d'actions
     */
    public void subActions(final int pNb) {
        this.addMaxActions(-pNb);
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        Room vPCurrentRoom = this.aMC.getCurrentRoom();
        this.aGui.print("\n");
        this.aGui.println("Bienvenue dans l'univers Zuul !");
        this.aGui.println("Tape 'help' pour obtenir de l'aide.");
        this.aGui.print("\n");
        this.longDescription(vPCurrentRoom);
        if(vPCurrentRoom.getImageName() != null)
            this.aGui.showImage(vPCurrentRoom.getImageName());
    }

    /**
     * Creer les salles et le reseau de salle, les items et les personnages
     */
    private void createRooms() {
        
        // Creation of rooms
        // this.aPreviousRooms = new Stack();
        
        Room vCompany = new Room("dans la Compagnie postière CH, à Leiden", "Company", "Company");
        Room vFirstFloor = new Room("au premier étage de la Compagnie", "FirstFloor", "FirstFloor");
        Room vClaudiaOffice = new Room("au bureau de Claudia", "ClaudiaOffice", "ClaudiaOffice");
        Room vCattleyaOffice = new Room("au bureau de Cattleya", "CattleyaOffice", "CattleyaOffice");
        Room vWorkRoom = new Room("dans la salle de travail de la Compagnie", "WorkRoom", "WorkRoom");
        
        Room vSchool = new Room("à l'école des poupées de souvenirs automatiques à Leiden", "School", "School");
        Room vOberberg = new Room("au hangar d'avions d'Oberberg", "Oberberg", "Oberberg");
        Room vIntense = new Room("dans la ville d'Intense", "Intense", "Intense");
        Room vBociaccia = new Room("dans la ville de Bociaccia", "Bociaccia", "Bociaccia");
        Room vMachtig = new Room("au marché de Machtig", "Machtig", "Machtig");
        Room vTownHall = new Room("à l'hotel de ville de Machtig", "TownHall", "TownHall");
        Room vLechernt = new Room("dans la maison d'Anne et Clara à Lechernt", "Lechernt", "Lechernt");
        Room vKazaly = new Room("dans la maison d'enfance d'Iris à Kazaly", "Kazaly", "Kazaly");
        Room vAstria = new Room("à Astria, la capitale de l'astronomie", "Astria", "Astria");
        TransporterRoom vObservatory = new TransporterRoom("à l'observatoire d'Astria", "Observatory", "Observatory");
        Room vEnciel = new Room("à l'hôpital d'Enciel", "Enciel", "Enciel");
        Room vOpera = new Room("à l'opéra d'Enciel", "Opera", "Opera");
        Room vDrossel = new Room("dans le Royaume de Drossel", "Drossel", "Drossel");
        Room vFlugel = new Room("dans le Royaume de Flugel", "Flugel", "Flugel");
        Room vRoswell = new Room("à coté du lac de Roswell", "Roswell", "Roswell");
        Room vAidan = new Room("dans la maison d'Aidan à Roswell", "Aidan", "Aidan");
        Room vCtrigall = new Room("dans la région froide du pays, Ctrigall", "Ctrigall", "Ctrigall");
        Room vEkarte = new Room("sur l'île d'Ekarte", "Ekarte", "Ekarte");
        
        Room vPortL = new Room("dans le port de Leiden", "PortL", "PortL");
        Room vPortA = new Room("dans le port d'Astria", "PortA", "PortA");
        Room vPortE = new Room("dans le port d'Enciel", "PortE", "PortE");
        
        // Creation of Rooms Set
        
        this.ROOM_NAMES = this.GAME_ROOM.keySet();
        
        // Creation of Items
        // Item : name, description, weight, price, pickable, stackable, eatable
        Item vTypeMachine = new Item("Dactylotype", "servant à écrire toute sorte de choses", 2, 10000, true, false, false);
        Item vPaper = new Item("Feuille", "sur laquel on peut écrire toute sorte de choses", 0.001, 5, true, true, false);
        Item vPen = new Item("Plume", "avec laquelle on peut écrire sur une feuille", 0.05, 200, true, false, false);
        Item vRifle = new Item("Fusil", "ayant servi pendant la guerre contre les Américains", 50, 7000, true, false, false);
        Item vIDTag = new Item("Plaque", "d'identification d'un soldat Japonais", 0, 100, true, false, false);
        Item vCookie = new Item("Cookie", "qui permet, une fois mangé, de porter plus de poids", 0.05, 20, true, false, true);
        Item vInk = new Item("Encre", "indispensable pour l'écriture", 0.1, 50, true, true, false);
        Beamer vBeamer = new Beamer("Beamer", "qui permet d'aller à l'endroit enregistré dans celui-ci", 2, 0, true, false, false);
        
        // Creation of Items Set
        
        this.ITEM_NAMES = this.GAME_ITEM.keySet();
        
        // Creation of Players
        PNJ vClaudia = new PNJ("Claudia", "Hodgins", vCompany, 50);
        PNJ vGilbert = new PNJ("Gilbert", "Bougainvillea", vEkarte, 50);
        PNJ vCattleya = new PNJ("Cattleya", "Baudelaire", vCattleyaOffice, 50);
        
        aMC = new MC("Violet", "Evergarden", vBociaccia, 50, this);
        
        // Creation of Players Set
        
        this.PNJ_NAMES = this.GAME_PNJ.keySet();
        this.PLAYER_NAMES = this.GAME_PLAYER.keySet();
        
        // Setting Items in PNJ
        vClaudia.getItemList().addItem(vPen);
        vClaudia.getItemList().addItem(vPaper);
        vClaudia.getItemList().addItem(vInk);
        
        // Setting Items in Rooms
        
        vCompany.getItemList().addItem(vTypeMachine);
        vCompany.getItemList().addItem(vPen);
        vCompany.getItemList().addItem(vBeamer);
        
        for(int i = 0; i < 5; i++) {
            // Item vPaper = new Item("Feuille", "sur laquel on peut écrire toute sorte de choses", 0.001, 5, true, true, false);
            vWorkRoom.getItemList().addItem(vPaper);
            vWorkRoom.getItemList().addItem(vInk);
            
            vCompany.getItemList().addItem(vPaper);
            vCompany.getItemList().addItem(vInk);
        }
        vWorkRoom.getItemList().addItem(vCookie);
        
        vBociaccia.getItemList().addItem(vRifle);
        vBociaccia.getItemList().addItem(vIDTag);
        
        // Creation of exits
        
        vCompany.setExit("nord", vIntense);
        vCompany.setExit("nordest", vMachtig);
        vCompany.setExit("est", vKazaly);
        vCompany.setExit("sud", vPortL);
        vCompany.setExit("sudouest", vSchool);
        vCompany.setExit("nordouest", vOberberg);
        vCompany.setExit("haut", vFirstFloor);
        
        vFirstFloor.setExit("nordest", vClaudiaOffice);
        vFirstFloor.setExit("sudest", vCattleyaOffice);
        vFirstFloor.setExit("sud", vWorkRoom);
        vFirstFloor.setExit("bas", vCompany);
        
        vClaudiaOffice.setExit("ouest", vFirstFloor);
        vCattleyaOffice.setExit("ouest", vFirstFloor);
        vWorkRoom.setExit("nord", vFirstFloor);
        
        vSchool.setExit("nord", vCompany);
        vSchool.setExit("nordouest", vCompany);
        vSchool.setExit("est", vCompany);
        
        vOberberg.setExit("nord", vCtrigall);
        vOberberg.setExit("nordest", vCtrigall);
        vOberberg.setExit("sud", vCompany);
        
        vIntense.setExit("sud", vCompany);
        
        vBociaccia.setExit("sudest", vLechernt);
        vBociaccia.setExit("sudouest", vMachtig);
        
        vMachtig.setExit("nord", vBociaccia);
        vMachtig.setExit("sudouest", vCompany);
        vMachtig.setExit("ouest", vIntense);
        vMachtig.setExit("est", vTownHall);
        vMachtig.setExit("sudest", vTownHall);
        
        vTownHall.setExit("nord", vMachtig);
        vTownHall.setExit("ouest", vMachtig);
        vTownHall.setExit("nordouest", vMachtig);
        
        vLechernt.setExit("nord", vBociaccia);
        vLechernt.setExit("sud", vKazaly);
        
        vKazaly.setExit("nordest", vLechernt);
        vKazaly.setExit("ouest", vCompany);
        
        vAstria.setExit("nord", vRoswell);
        vAstria.setExit("est", vCompany);
        vAstria.setExit("sud", vPortA);
        vAstria.setExit("ouest", vEnciel);
        vAstria.setExit("haut", vObservatory);
        
        vObservatory.setExit("bas", vAstria);
        Room vaef = vObservatory.getRandomExit();
        
        vEnciel.setExit("nord", vDrossel);
        vEnciel.setExit("nordest", vRoswell);
        vEnciel.setExit("sud", vPortE);
        vEnciel.setExit("ouest", vOpera);
        
        vOpera.setExit("est", vEnciel);
        
        vDrossel.setExit("nord", vFlugel);
        vDrossel.setExit("sud", vEnciel);
        
        vFlugel.setExit("sudest", vRoswell);
        vFlugel.setExit("sudouest", vDrossel);
        
        vRoswell.setExit("nord", vFlugel);
        vRoswell.setExit("nordest", vAidan);
        vRoswell.setExit("sudouest", vEnciel);
        
        vAidan.setExit("sud", vRoswell);
        vAidan.setExit("ouest", vRoswell);
        vAidan.setExit("sudouest", vRoswell);
        
        vCtrigall.setExit("sud", vOberberg);
        vCtrigall.setExit("sudouest", vOberberg);
        vCtrigall.setExit("ouest", vOberberg);
        
        vPortE.setExit("nord", vEnciel);
        vPortE.setExit("est", vPortA);
        vPortE.setExit("sudest", vPortL);
        
        vPortA.setExit("nord", vAstria);
        vPortA.setExit("est", vPortL);
        vPortA.setExit("ouest", vPortE);
        
        vPortL.setExit("nord", vCompany);
        vPortL.setExit("est", vEkarte);
        vPortL.setExit("sudouest", vPortE);
        vPortL.setExit("ouest", vPortA);
        
        // this.aCurrentRoom = vBociaccia;
    } // createRooms()

    /**
     * Interprete la commande tapee et effectue la commande adequate 
     * si elle existe
     * @param pCommandLine La commande tapée
     */
    public void interpretCommand(final String pCommandLine) {
        this.aGui.println(">> " + pCommandLine);
        Command vCommand = this.aParser.getCommand(pCommandLine);

        if (vCommand.isUnknown()) {
            this.aGui.println("Je ne comprends pas...\n");
            return;
        }

        String vCommandWord = vCommand.getCommandWord().toLowerCase();
        boolean vAction = false;
        if (vCommandWord.equals("help")) {
            vAction = this.aMC.printHelp();
        } else if (vCommandWord.equals("go")) {
            vAction = this.aMC.goRoom(vCommand);
        } else if(vCommandWord.equals("look")) {
            vAction = this.aMC.look(vCommand);
        } else if(vCommandWord.equals("eat")) {
            vAction = this.aMC.eat(vCommand);
        } else if(vCommandWord.equals("back")) {
            vAction = this.aMC.back(vCommand);
        } else if(vCommandWord.equals("take")) {
            vAction = this.aMC.take(vCommand);
        } else if(vCommandWord.equals("drop")) {
            vAction = this.aMC.drop(vCommand);
        } else if(vCommandWord.equals("charge")) {
            vAction = this.aMC.charge(vCommand);
        } else if(vCommandWord.equals("use")) {
            vAction = this.aMC.use(vCommand);
        } else if(vCommandWord.equals("test")) {
            vAction = this.aMC.test(vCommand);
        } else if(vCommandWord.equals("info")) {
            vAction = this.aMC.info(vCommand);
        } else if(vCommandWord.equals("talk")) {
            vAction = this.aMC.talk(vCommand);
        } else if(vCommandWord.equals("write")) {
            vAction = this.aMC.write(vCommand);
        } else if(vCommandWord.equals("trade")) {
            vAction = this.aMC.trade(vCommand);
        } else if(vCommandWord.equals("quit")) {
            if (vCommand.hasSecondWord())
                this.aGui.println("Quitter quoi ?");
            else
                this.endGame();
        }
        this.aGui.println("");
        this.getUI().getLog().setCaretPosition(this.getUI().getLog().getDocument().getLength());
        if(vAction) {
            this.pnjActions();
        }
        // if(this.aNbActions > this.aMaxActions) {
            // this.aGui.println("Je n'ai plus d'espoir, j'arrête mes recherches...");
            // this.endGame();
        // }
    } // interpretCommand()
    
    // PNJ actions
    
    /**
     * Méthode permettant aux PNJ de faire leurs actions
     */
    private void pnjActions() {
        Scanner vSc;
        for(File file : PNJ_FILES) {
            try {
                vSc = new Scanner(file);
            
                String vAuthorStr = file.getName().substring(0, file.getName().length()-4);
                PNJ vAuthorPNJ = this.GAME_PNJ.get(vAuthorStr.toLowerCase());
                
                // PAS OPTI => DEMANDER DES CONSEILS
                
                for(int vI = 0; vI < aMaxActions; vI++) {
                    if(vI == aNbActions) {
                        String vCommandStr = vSc.nextLine();
                        vAuthorPNJ.interpretPNJCommand(vCommandStr);
                        break;
                    } else {
                        vSc.nextLine();
                    }
                }
            } catch (Exception pE) {
                // System.out.println(pE);
            }
        }
        this.checkGameOver();
        this.aMC.checkQuestAchieved(); // regarde toutes les quêtes de aMC, vérifie les conditions d'achèvement
        this.aNbActions += 1;
    } // pnjActions()
    
    private void checkGameOver() {
        if(this.aNbActions > this.aMaxActions) {
            this.aGui.println("Je n'ai plus d'espoir, j'arrête mes recherches...");
            this.endGame();
            this.aGui.enable(false);
            return;
        }
        if(this.aMC.getCurrentRoom().equals(this.GAME_ROOM.get("ekarte"))) { //this.aMC.getExp() >= 75 && 
            this.aGui.println("Vous avez gagné la partie ! Vous avez trouvé Gilbert à temps.");
            this.aGui.enable(false);
            return;
        }
    }
    
    // Print methods
    // goRoom(), look(), back()
    
    /**
     * Affiche "Aller où ?"
     */
    public void goWhere() {
        this.aGui.println("Aller où ?");
    } // goWhere()
    
    /**
     * Affiche "Je ne peux pas aller là..."
     */
    public void cannotGoHere() {
        this.aGui.println("Je ne peux pas aller là...");
    } // cannotGoHere()
    
    /**
     * Affiche la longue description de la pRoom
     * @param pRoom Une Room
     */
    public void longDescription(final Room pRoom) {
        this.aGui.println(pRoom.getLongDescription());
    } // longDescription()
    
    /**
     * Affiche l'image de la Room
     * @param pRoom Une Room
     */
    public void roomImage(final Room pRoom) {
        this.aGui.showImage(pRoom.getImageName());
    } // roomImage()
    
    // printHelp()
    
    /**
     * Affiche les commandes utilisables
     */
    public void help() {
        this.aGui.println( "Les commandes utilisables sont : " + this.aParser.getCommandString() );
    } // help()
    
    // look(), take(), 
    
    /**
     * Affiche la longue description d'un Item
     * @param pItem Un Item
     */
    public void itemDescription(final Item pItem) {
        this.aGui.println(pItem.getLongDescription());
    } // itemDescription()
    
    /**
     * Affiche "L'objet que je cherche n'est pas ici..."
     */
    public void itemNotFound() {
        this.aGui.println("L'objet que je cherche n'est pas ici...");
    } // itemNotFound()
    
    /**
     * Affiche "Cet objet n'existe pas..."
     */
    public void itemNotExist() {
        this.aGui.println("Cet objet n'existe pas...");
    } // itemNotExist()
    
    // eat()
    
    /**
     * Affiche "Il me faut quelque chose à manger..."
     */
    public void cannotEat() {
        this.aGui.println("Il me faut quelque chose à manger...");
    } // cannotEat()
    
    /**
     * Affiche "Je n'ai plus faim, je vais enfin pouvoir reprendre mon travail."
     */
    public void haveEat() {
        this.aGui.println("Je n'ai plus faim, je vais enfin pouvoir reprendre mon travail.");
    } // haveEat()
    
    /**
     * Affiche "(nom d'un Item) mangé !"
     * @param pItemName Nom de l'Item
     */
    public void itemEaten(final String pItemName) {
        this.aGui.println(pItemName + " mangé !");
    } // itemEaten()
    
    /**
     * Affiche "Je... ne peux pas manger ça..."
     */
    public void itemNotEatable() {
        this.aGui.println("Je... ne peux pas manger ça...");
    } // itemNotEatable()
    
    //back(), take(), drop(), talk
    
    /**
     * Affiche "Ça n'a aucun sens..."
     */
    public void noSens() {
        this.aGui.println("Ça n'a aucun sens...");
    } // noSens()
    
    /**
     * Affiche "Je retourne en arrière"
     */
    public void goBack() {
        this.aGui.println("Je retourne en arrière");
    } // goBack()
    
    /**
     * Affiche "Je ne peux pas retourner en arrière..."
     */
    public void cannotGoBack() {
        this.aGui.println("Je ne peux pas retourner en arrière...");
    } // cannotGoBack()
    
    // take(), drop()
    
    /**
     * Affiche "(nom d'un Item) récupéré !"
     * @param pItemName Nom de l'Item
     */
    public void itemCollected(final String pItemName) {
        this.aGui.println(pItemName + " récupéré !");
    } // itemCollected()
    
    /**
     * Affiche "(nom d'un Item) déposé !"
     * @param pItemName Nom de l'Item
     */
    public void itemDroped(final String pItemName) {
        this.aGui.println(pItemName + " déposé !");
    } // itemDroped()
    
    /**
     * Affiche "L'objet que je veux prendre n'est pas ici..."
     */
    public void itemNotInRoom() {
        this.aGui.println("L'objet que je veux prendre n'est pas ici...");
    } // itemNotInRoom()
    
    /**
     * Affiche "Cet objet n'est pas dans mon sac..."
     */
    public void itemNotInInventory() {
        this.aGui.println("Cet objet n'est pas dans mon sac...");
    } // itemNotInInventory()
    
    /**
     * Affiche "Je ne peux plus prendre d'objets... Mon sac est plein..."
     */
    public void fullInventory() {
        this.aGui.println("Je ne peux plus prendre d'objets... Mon sac est plein...");
    } // fullInventory()
    
    /**
     * Affiche "Beamer chargé"
     */
    public void beamerCharged() {
        this.aGui.println("Beamer chargé !");
    } // chargedBeamer()
    
    /**
     * Affiche "Beamer non chargé"
     */
    public void beamerNotCharged() {
        this.aGui.println("Beamer pas encore chargé...");
    } // beamerNotCharged()
    
    /**
     * Affiche "Je n'ai pas le beamer dans mon sac..."
     */
    public void beamerNotInInventory() {
        this.aGui.println("Je n'ai pas le beamer dans mon sac...");
    } // beamerNotInInventory()
    
    /**
     * Affiche l'utilisation d'un Item
     * @param pItem Un Item
     */
    public void usedItem(final Item pItem) {
        this.aGui.println(pItem.getName() + " utilisé !");
    } // usedItem()
    
    // drop()
    
    /**
     * Affiche "Mon sac est vide..."
     */
    public void emptyInventory() {
        this.aGui.println("Mon sac est vide...");
    } // emptyInventory()
    
    // info()
    
    /**
     * Affiche les informations d'un Player
     * @param pPlayer Player dont on veut les informations
     */
    public void playerInfo(final Player pPlayer) {
        String vInfo = "Nom : " + pPlayer.getName() + "\n";
        vInfo += "Lieu actuel : " + pPlayer.getCurrentRoom().getDescription() + "\n";
        vInfo += pPlayer.items() + "\n";
        vInfo += pPlayer.getQuestList().getQuestListString() + "\n";
        this.aGui.println(vInfo);
    } // playerInfo()
    
    /**
     * Affiche "Cette personne n'existe pas..."
     */
    public void pnjNotExists() {
        this.aGui.println("Cette personne n'existe pas...");
    } // pnjNotExists()
    
    /**
     * Affiche "Cette personne n'est pas ici..."
     */
    public void pnjNotHere() {
        this.aGui.println("Cette personne n'est pas ici...");
    }
    
    public void noQuestAvailable() {
        this.aGui.println("Acune quêtes disponibles");
    }
    
    public void questAccepted(final Quest pQuest) {
        this.aGui.println("Quête - " + pQuest.getName() + " - acceptée");
    }
    
    // test()
    
    /**
     * Affiche "Il me faut quelque chose à tester..."
     */
    public void cannotTest() {
        this.aGui.println("Il me faut quelque chose à tester...");
    } // cannotTest()
    
    /**
     * Affiche "Fichier test trouvé !"
     */
    public void fileFound() {
        this.aGui.println("Fichier test trouvé !");
    } // fileFound()
    
    /**
     * Affiche "Fichier test introuvable..."
     */
    public void fileNotFound() {
        this.aGui.println("Fichier test introuvable...");
    } // fileNotFound()
    
    /**
     * Affiche "Il n'y a pas/plus de commandes"
     */
    public void noMoreCommands() {
        this.aGui.println("Il n'y a pas/plus de commandes");
    } // noMoreCommands()
    
    // roomIs(), roomHas(), playerHas()
    
    /**
     * Affiche "true"
     */
    public void printTrue() {
        this.aGui.println("true");
    } // printTrue()
    
    /**
     * Affiche "false"
     */
    public void printFalse() {
        this.aGui.println("false");
    } // printFalse()
    
    /**
     * Fin du jeu
     */
    private void endGame() {
        this.aGui.println( "Merci d'avoir joué, à bientôt !" );
        this.aGui.getEntryField().enable( false );
    }
}
