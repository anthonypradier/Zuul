 

/**
 * Classe Command qui permet de récupérer des informations sur la commande tapée
 *
 * @author Anthony Pradier
 * @version 1.0, 01/22/2024
 */
public class Command {
    /**
     * Premier mot de la commande
     */
    private String aCommandWord;
    
    /**
     * Deuxième mot de la commande
     */
    private String aSecondWord;
    
    /**
     * Constructeur naturel d'une commande
     * @param pCWord Le premier mot de la commande
     * @param pSWord Le second mot de la commande
     */
    public Command(final String pCWord, final String pSWord) {
        this.aCommandWord = pCWord;
        this.aSecondWord = pSWord;
    } // Command()
    
    /**
     * Renvoie le premier mot de la commande
     * @return Le premier mot de la commande
     */
    public String getCommandWord() {
        return this.aCommandWord;
    } // getCommandWord()
    
    /**
     * Renvoie le second mot de la commande
     * @return Le second mot de la commande
     */
    public String getSecondWord() {
        return this.aSecondWord;
    } // getSecondWord()
    
    /**
     * Renvoie si il y a un second mot
     * @return Si il y a un second mot
     */
    public boolean hasSecondWord() {
        if(this.aSecondWord == null) {
            return false;
        }
        return true;
    } // hasSecondWord()
    
    /**
     * Retourne vrai si le premier mot de la commande est null
     * @return Si le premier mot de la commande est null
     */
    public boolean isUnknown() {
        if(this.aCommandWord == null) {
            return true;
        }
        return false;
    } // isUnknow()
    
} // Command




















