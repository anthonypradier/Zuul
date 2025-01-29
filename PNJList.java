import java.util.HashMap;

/**
 * Permet de gérer les flux de PNJ dans les Room
 *
 * @author Anthony Pradier
 * @version 1.0, 04/03/2024
 */
public class PNJList {
    /**
     * HashMap des PNJ
     */
    private HashMap<String, PNJ> aPNJs;
    
    /**
     * Constructeur par défaut de la classe
     */
    public PNJList() {
        this.aPNJs = new HashMap<String, PNJ>();
    }
    
    /**
     * Renvoie la HashMap des PNJ
     * @return la HashMap des PNJ
     */
    public HashMap<String, PNJ> getPNJs() {
        return this.aPNJs;
    }
    
    /**
     * Ajoute un PNJ dans la HashMap
     * @param pPNJ un PNJ
     */
    public void addPNJ(final PNJ pPNJ) {
        this.aPNJs.putIfAbsent(pPNJ.getName().toLowerCase(), pPNJ);
    }
    
    /**
     * Retire un PNJ de la HashMap
     * @param pPNJ PNJ
     */
    public void removePNJ(final PNJ pPNJ) {
        this.aPNJs.remove(pPNJ.getName().toLowerCase());
    }
    
    /**
     * Renvoie si la liste des PNJ est vide
     * @return si la liste des PNJ est vide
     */
    public boolean empty() {
        return this.aPNJs.size() == 0;
    }
}
