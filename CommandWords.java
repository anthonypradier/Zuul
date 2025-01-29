 
/**
 * Classe CommandWords qui traite les commandes
 *
 * @author Anthony Pradier
 * @version 1.3, 02/12/2024
 */
public class CommandWords {
    /**
     * Commandes utilisables
     */
    private static final String[] VALID_COMMANDS = {"go", "help", "quit", "look", "eat", "back", "test", "take", "drop", "info", "charge", "use", "wait", "createquest", "talk", "write"};
    
    /**
     * Constructeur par défaut de CommandWords
     */
    public CommandWords() {
        
    }
    
    /**
     * Renvoie si a chaine de caractère en parametre est une commande existante
     * @param pString Une String
     * @return Si la chaine dest une commande existante
     */
    public static boolean isCommand(final String pString) {
        if(pString == null) { return false; }
        for(int vI = 0; vI < VALID_COMMANDS.length; vI++) {
            if(pString.toLowerCase().equals(VALID_COMMANDS[vI])) {
                return true;
            }
        }
        return false;
    } // is Command()
    
    /**
     * Verifie si la String en parametre fait parti des objets
     * @param pString Une String
     * @return si la String en parametre fait parti des objets
     */
    public static boolean isItem(final String pString) {
        for(String vItemName : GameEngine.ITEM_NAMES) {
            if(vItemName.equals(pString)) {
                return true;
            }
        }
        return false;
    } // isItem()
    
    /**
     * Retourne les Commandes utilisables sous forme d'une String
     * @return Les commandes utilisables dans une String
     */
    public static String getCommandList() {
        String vCommands = "";
        for(String vCommand : VALID_COMMANDS) {
            vCommands += " " + vCommand;
        }
        return vCommands;
    }
}
