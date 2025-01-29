
/**
 * Beamer est un objet permettant de stocker une certaine pièce dans un attribut
 * et de téléporter le joueur dans la pièce enregistrée lors de l'utilisation
 *
 * @author Anthony Pradier
 * @version 03/13/2024
 */
public class Beamer extends Item {
    /**
     * Attribut qui stocke la Room dans laquelle le Player se téléportera à 
     * l'utilisation du Beamer
     */
    private Room aChargedRoom;
    
    /**
     * Constructeur naturel de Beamer
     * @param pName Le nom de l'item
     * @param pDescription La description de l'objet
     * @param pWeight Le poids de l'objet
     * @param pPrice Le prix de l'objet
     * @param pPickable Si l'Item est récupérable
     * @param pStackable Si l'Item est stackable
     * @param pEatable Si l'Item est mangeable
     */
    public Beamer(final String pName, final String pDescription, final double pWeight, final int pPrice, final boolean pPickable, final boolean pStackable, final boolean pEatable) {
        super(pName, pDescription, pWeight, pPrice, pPickable, pStackable, pEatable);
    } // Beamer()
    
    /**
     * Charge le Beamer avec la Room en paramètre
     * @param pRoom La Room à charger
     */
    public void chargeBeamer(final Room pRoom) {
        this.aChargedRoom = pRoom;
    } // chargeBeamer()
    
    /**
     * Renvoie la Room chargée
     * @return La Room chargée
     */
    public Room getChargedRoom() {
        return this.aChargedRoom;
    } // getChargedItem()
    
    /**
     * Renvoie si le Beamer est chargé
     * @return Si le Beamer est chargé
     */
    public boolean isCharged() {
        return this.aChargedRoom != null;
    } // isCharged()
} // Beamer
