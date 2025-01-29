import java.util.Stack;
import java.util.HashMap;

/**
 * Classe Player qui contient les méthodes et attributs valables pour n'importe quelle antité vivante du jeu
 *
 * @author Anthony Pradier
 * @version 1.2, 03/10/2024
 */
public class Player {
    /**
     * Nom du Player
     */
    private String aName;
    private String aLastName;
    /**
     * Room actuelle du Player
     */
    private Room aCurrentRoom;
    /**
     * Liste d'Item du Player
     */
    private ItemList aItemList;
    /**
     * Liste des Quest du Player
     */
    private QuestList aQuestList;
    /**
     * Poids max du Player
     */
    private double aMaxWeight;
    /**
     * Poids actuel du Player
     */
    private double aCurrentWeight;
    /**
     * Argent du Player
     */
    private Wallet aWallet;
    /**
     * Rooms précédentes du Player
     */
    private Stack aPreviousRooms;
    
    /**
     * Constructeur naturel de Player
     * @param pName Le nom du Player
     * @param pCurrentRoom La Room actuelle (la Room de départ lors de la création d'un Player)
     * @param pMaxWeight Poids maximal que le Player peut porter
     */
    public Player(final String pName, final String pLastName, final Room pCurrentRoom, final double pMaxWeight) {
        this.aName = pName;
        this.aLastName = pLastName;
        this.aCurrentRoom = pCurrentRoom;
        this.aMaxWeight = pMaxWeight;
        this.aCurrentWeight = 0;
        this.aWallet = new Wallet();
        this.aPreviousRooms = new Stack();
        this.aItemList = new ItemList();
        this.aQuestList = new QuestList();
    } // Player()
    
    // GETTERS
    
    /**
     * Renvoie la Room actuelle du Player
     * @return la Room actuelle du Player
     */
    public Room getCurrentRoom() {
        return this.aCurrentRoom;
    } // getCurrentRoom()
    
    /**
     * Renvoie la Stack des Rooms précédentes du Player
     * @return La Stack des Rooms précédentes du Player
     */
    public Stack getPreviousRooms() {
        return this.aPreviousRooms;
    } // getPreviousRooms()
    
    /**
     * Renvoie le nom du Player
     * @return le nom du Player
     */
    public String getName() {
        return this.aName;
    } // getName()
    
    public String getFullName() {
        return this.aName + " " + this.aLastName;
    }
    
    /**
     * Renvoie le poids du Player
     * @return Le poids du Player
     */
    public double getCurrentWeight() {
        return this.aCurrentWeight;
    } // getCurrentWeight()
    
    /**
     * Renvoie le poids max portable par le Player
     * @return Le poids max portable par le Player
     */
    public double getMaxWeight() {
        return this.aMaxWeight;
    } // getMaxWeight()
    
    /**
     * Renvoie le Wallet du Player
     * @return le Wallet du Player
     */
    public Wallet getWallet() {
        return this.aWallet;
    }
    
    /**
     * Renvoie la liste d'Item / l'inventaire du Player
     * @return La liste d'Item / l'inventaire du Player
     */
    public ItemList getItemList() {
        return this.aItemList;
    } // getItemList()
    
    /**
     * Renvoie la liste des Quest du Player
     * @return la liste des Quest du Player
     */
    public QuestList getQuestList() {
        return this.aQuestList;
    } // getQuestList()
    
    // SETTERS
    
    /**
     * Change la Room
     * @param pRoom La nouvelle Room
     */
    public void setCurrentRoom(final Room pRoom) {
        this.aCurrentRoom = pRoom;
    } // setCurrentRoom()
    
    /**
     * Change le poids max portable
     * @param pWeight Le nouveau poids
     */
    public void setMaxWeight(final double pWeight) {
        this.aMaxWeight = pWeight;
        this.aMaxWeight = Math.round(this.aMaxWeight * 1000)/1000.0;
    } // setMaxWeight()
    
    /**
     * Ajoute du poids
     * @param pWeight Le poids à ajouter
     */
    public void addWeight(final double pWeight) {
        this.aCurrentWeight += pWeight;
        this.aCurrentWeight = Module.round(this.aCurrentWeight);
        // à compléter
    } // addWeight()
    
    /**
     * Retire du poids
     * @param pWeight Le poids à retirer
     */
    public void removeWeight(final double pWeight) {
        this.aCurrentWeight -= pWeight;
        this.aCurrentWeight = Module.round(this.aCurrentWeight);
        // a compléter
    } // removeWeight()
    
    public void addItem(final Item pItem) {
        this.getItemList().addItem(pItem);
        this.addWeight(pItem.getWeight());
    }
    
    public void removeItem(final Item pItem) {
        this.getItemList().removeItem(pItem);
        this.removeWeight(pItem.getWeight());
    }
    
    /**
     * Renvoie une String qui énumère la liste d'Item que porte le Player
     * qui contient les Items, leur nombre, et leur poids
     * S'il n'y a rien dans la liste d'Item, renvoie "le sac est vide"
     * @return La liste des Items portés par le Player
     */
    public String items() {
        // String vInfo = "";
        String vItemsInfo = "";
        String vGlobalInfo = "Inventaire : ";
        int vMoney = this.getWallet().getMoney();
        if(!this.aItemList.empty()) {
            HashMap<String, Item> vItems = this.aItemList.getItems();
            int vNbItems = 0;
            for(String vIName : vItems.keySet()) {
                if(!vItems.get(vIName).isStackable()) {
                    vItemsInfo += "\n   - " + vItems.get(vIName).getName() + " ~ " + vItems.get(vIName).getWeight() + " kg ~ " + vItems.get(vIName).getPrice() + " ¥";
                    vNbItems += 1;
                } else {
                    int vNbOcc = this.aItemList.getStackedItems().get(vItems.get(vIName));
                    vItemsInfo += "\n   - " + vNbOcc + "x " + vItems.get(vIName).getName() + " ~ " + vItems.get(vIName).getWeight() * vNbOcc + " kg ~ " + vItems.get(vIName).getPrice() * vNbOcc + " ¥";
                    vNbItems += vNbOcc;
                }
            }
            // à modifier quand les objets pourront être stackés
            vGlobalInfo += vNbItems + " objets ~ " + vMoney + " ¥ ~ (" + this.aCurrentWeight + "/" + this.aMaxWeight + ")";
        } else {
            vItemsInfo += "sac vide ~ " + vMoney + " ¥ ~";
        }
        return vGlobalInfo + vItemsInfo;
    } // items()
} // Player
