import java.util.HashMap;
import java.util.Set;
import java.util.Collection;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul contenant un objet
 *
 * @author Anthony Pradier
 * @version 2.3, 02/12/2024
 */
public class Room {
    /**
     * Nom de la Room
     */
    private String aName;
    /**
     * Description de la Room
     */
    private String aDescription;
    /**
     * Nom de l'image de la Room
     */
    private String aImageName;
    /**
     * Liste d'Item de la Room
     */
    private ItemList aItemList;
    /**
     * Sorties de la Room
     */
    private HashMap<String, Room> aExits;
    
    /**
     * PNJ dans la Room
     */
    private PNJList aPNJList;
    
    /**
     * Constructeur naturel
     * @param pDescription Décrit la salle
     * @param pName Nom de la salle
     * @param pImageName Nom de l'image associee
     */
    public Room(final String pDescription, final String pName, final String pImageName) {
        this.aDescription = pDescription;
        this.aName = pName;
        this.aImageName = pImageName;
        this.aItemList = new ItemList();
        this.aPNJList = new PNJList();
        this.aExits = new HashMap<String, Room>();
        GameEngine.GAME_ROOM.put(this.aName.toLowerCase(), this);
    } // Room()
    
    /**
     * Renvoie le nom de la Room
     * @return Le nom de la Room
     */
    public String getName() {
        return this.aName;
    } // getName()
    
    /**
     * Renvoie la dercription de la salle
     * @return La description de la salle
     */
    public String getDescription() {
        return this.aDescription;
    } //getDescription()
    
    /**
     * Renvoie une description longue de la salle, comprenant
     * la description, les sorties, les items
     * @return La description complète de la salle
     */
    public String getLongDescription() {
        String vLongDesc = "Je suis " + this.aDescription + ".\n";
        vLongDesc += this.getExitString() + ".\n";
        vLongDesc += this.getPNJString() + "\n";
        vLongDesc += this.getItemString();
        return vLongDesc;
    } // getLongDescription()
    
    /**
     * Renvoie la sortie correspondante à la direction
     * @param pDirection La direction en minuscule
     * @return Salle correspondante à la direction, null si il n'y a pas de salle
     */
    public Room getExit(final String pDirection) {
        if(this.aExits.get(pDirection) != null) {
            return this.aExits.get(pDirection.toLowerCase());
        }
        return null;
    } // setExit()
    
    /**
     * Renvoie les salles accessibles dans un String à l'aide d'un StringBuilder
     * @return Les salles accessibles dans une String
     */
    public String getExitString() {
        StringBuilder vExits = new StringBuilder("Sorties :");
        Set<String> vExitSet = this.aExits.keySet();
        for(String vE : vExitSet) {
            vExits.append(" " + vE);
        }
        return vExits.toString();
    } // getExitString()
    
    /**
     * Renvoie la liste d'Item / l'inventaire de la Room
     * @return La liste d'Item / l'Inventaire de la Room
     */
    public ItemList getItemList() {
        return this.aItemList;
    } // getItemList()
    
    /**
     * Renvoie les objets presents dans la Room
     * @return les objets presents dans la Room
     */
    public String getItemString() {
        if(this.aItemList.empty()) {
            return "Pas d'objets ici";
        }
        String vItems = "Objets : ";
        for(String vItemName : this.aItemList.getItems().keySet()) {
            Item vItem = this.aItemList.getItems().get(vItemName);
            if(vItem.isStackable()) {
                int vNbItem = this.aItemList.getStackedItems().get(vItem);
                vItems += "\n   - " + vNbItem + "x " + vItem.getName() + " ~ " + Module.round(vNbItem * vItem.getWeight()) + " ~ " + vNbItem * vItem.getPrice() + " ¥";
            } else {
                vItems += "\n   - " + vItem.getName() + " ~ " + Module.round(vItem.getWeight()) + " ~ " + vItem.getPrice() + " ¥";
            }
        }
        return vItems;
    } // getItemString()
    
    /**
     * Renvoie la liste des PNJ dans la Room
     * @return la liste des PNJ dans la Room
     */
    public PNJList getPNJList() {
        return this.aPNJList;
    }
    
    /**
     * Renvoie les PNJ presents dans la Room
     * @return les PNJ presents dans la Room
     */
    public String getPNJString() {
        if(this.aPNJList.empty()) {
            return "Il n'y a personne ici";
        }
        
        String vPNJs = "Personnages : ";
        HashMap<String, PNJ> vPNJList = this.aPNJList.getPNJs();
        for(String vPNJName : vPNJList.keySet()) {
            vPNJs += vPNJList.get(vPNJName).getName() + ", ";
        }
        return vPNJs.substring(0, vPNJs.length() - 2);
    }
    
    /**
     * aAjoute une sortie dans le HashMap des sortie de la salle
     * @param pDirection Une direction
     * @param pNeighbour La salle voisine
     */
    public void setExit(final String pDirection, final Room pNeighbour) {
        this.aExits.put(pDirection, pNeighbour);
    } // setExits()
    
    /**
     * Redefinition de la methode toString()
     * @return le nom de la piece
     */
    @Override
    public String toString() {
        return this.aName;
    } // toString()
    
    /**
     * Renvoie le nom de l'image de la salle
     * @return Le nom de l'image de la salle
     */
    public String getImageName() {
        return this.aImageName;
    } // getImageName()
    
    /**
     * Renvoie si la Room en paramètre est une sortie
     * @param pRoom Une Room
     * @return si la Room en paramètre est une sortie
     */
    public boolean isExit(final Room pRoom) {
        Collection<Room> vExit = this.aExits.values();
        if(vExit.contains(pRoom))
            return true;
        else
            return false;
    } // isExit()
    
    /**
     * Renvoie si la Room actuelle est une TransporterRoom
     * @return si la Room actuelle est une TransporterRoom
     */
    public boolean isTransporterRoom() {
        return this instanceof TransporterRoom;
    }
    
    // public TransporterRoom convertToTransporter() {
        // return (TransporterRoom)this;
    // }
    
    /**
     * Redefinition de la methode equals
     * @param pObj Un objet
     * @return Un booleen
     */
    @Override
    public boolean equals(final Object pObj) {
        if(pObj == null)
            return false;
        if(this.getClass() != pObj.getClass())
            return false;
        Room vRoom = (Room)pObj;
        return (this.aName.equals(vRoom.getName()) && this.aDescription.equals(vRoom.getDescription()));
    }
} // Room
