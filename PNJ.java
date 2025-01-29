
/**
 * Sous classe de Player qui définit les PNJ, leurs actions n'affichent rien dans la fenêtre de jeu
 *
 * @author Anthony Pradier
 * @version 1.1, 03/05/2024
 */
public class PNJ extends Player {
    /**
     * Référence au moteur de jeu
     */
    private GameEngine GE;
    /**
     * Référence à Parser
     */
    private Parser aParser;
    
    /**
     * Constructeur naturel de PNJ
     * @param pName Le nom du PNJ
     * @param pCurrentRoom La Room actuelle (la Room de départ lors de la création d'un PNJ)
     * @param pMaxWeight Poids maximal que le PNJ peut porter
     */
    public PNJ(final String pName, final String pLastName, final Room pCurrentRoom, final double pMaxWeight) {
        super(pName, pLastName, pCurrentRoom, pMaxWeight);
        this.getCurrentRoom().getPNJList().addPNJ(this);
        GE.GAME_PLAYER.put(this.getName().toLowerCase(), this);
        GE.GAME_PNJ.put(this.getName().toLowerCase(), this);
        this.aParser = new Parser();
        this.getWallet().addMoney(1000);
    }
    
    /** 
     * Essaie de changer de salle pour celle dans la commande pour les PNJ
     * @param pCommand La commande
     */
    public void goRoom(final Command pCommand) {
        if (!pCommand.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            // System.out.println(this.getName() + " aller où ?");
            return;
        }
        
        String vDirection = pCommand.getSecondWord();

        // Try to leave current room.
        Room vNextRoom = this.getCurrentRoom().getExit(vDirection);

        if (vNextRoom == null){
            // System.out.println(this.getName() + " ne peut pas aller là");
        } else {
            this.getPreviousRooms().push(this.getCurrentRoom());
            this.getCurrentRoom().getPNJList().removePNJ(this);
            this.setCurrentRoom(vNextRoom);
            this.getCurrentRoom().getPNJList().addPNJ(this);
        }
    } // goRoom()
    
    /**
     * Permet au PNJ de récupérer de l'énergie, ou de consommer un consommable spécial
     * @param pCommand La commande
     */
    public void eat(final Command pCommand) {
        // Doit faire quelque chose de spécial pour manger
    } // eat()
    
    /**
     * Commande back pour PNJ
     * @param pCommand La commande
     */
    public void back(final Command pCommand) {
        if(!this.getPreviousRooms().empty()) {
            Room vPreviousRoom = (Room)this.getPreviousRooms().pop();
            this.setCurrentRoom(vPreviousRoom);
        } else
            return;
    } // back()
    
    public void waiting() {
        System.out.println(this.getName() + " s'occupe");
    }
    
    /**
     * Créer une Quest et la stock dans la QuestList
     */
    public void createQuest(final Command pCommand) {
        String vSWord = pCommand.getSecondWord();
        String[] vQuestArray = vSWord.split("_");
        String[] vQuestNameArray = vQuestArray[0].split("-");
        String[] vQuestDescArray = vQuestArray[1].split("-");
        
        String vName = "";
        for(String vStr : vQuestNameArray) {
            vName += vStr + " ";
        }
        
        String vDesc = "";
        for(String vStr : vQuestDescArray) {
            vDesc += vStr + " ";
        }
        String vGoal = vQuestArray[2];
        String vObject = vQuestArray[3];
        String vTarget = vQuestArray[4];
        int vReward = Integer.parseInt(vQuestArray[5]);
        int vExp = Integer.parseInt(vQuestArray[6]);
        
        Quest vQuest = new Quest(vName, vDesc, vGoal, vObject, vTarget, vReward, vExp);
        this.getQuestList().addQuest(vQuest);
    } // createQuest()
    
    /**
     * Interprétation des commandes pour les PNJ, différente de celle pour les MC
     * @param pCommandLine Commande exécutée par le PNJ
     */
    public void interpretPNJCommand(final String pCommandLine) {
        Command vCommand = this.aParser.getCommand(pCommandLine);

        if (vCommand.isUnknown()) {
            System.out.println("Il y a une commande anormale");
            return;
        }

        String vCommandWord = vCommand.getCommandWord().toLowerCase();
        if (vCommandWord.equals("go")) {
            this.goRoom(vCommand);
        } else if(vCommandWord.equals("eat")) {
            // System.out.println("eat");
            this.eat(vCommand);
        } else if(vCommandWord.equals("back")) {
            // System.out.println("back");
            this.back(vCommand);
        } else if(vCommandWord.equals("wait")) {
            // System.out.println("wait");
            this.waiting();
        } else if(vCommandWord.equals("createquest")) {
            // System.out.println("createquest");
            this.createQuest(vCommand);
        }
    }// interpretCommand()
}
