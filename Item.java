
/**
 * Classe qui s'occupe de produire les informations sur les Items
 *
 * @author Anthony Pradier
 * @version 1.3, 03/10/2024
 */
public class Item {
    /**
     * Nom de l'Item
     */
    private String aName;
    /**
     * Description de l'Item
     */
    private String aDescription;
    /**
     * Poids de l'Item
     */
    private double aWeight;
    /**
     * Prix de l'Item
     */
    private int aPrice;
    /**
     * Définit si l'Item est prenable
     */
    private boolean aPickable;
    /**
     * Définit si l'Item est stackable
     */
    private boolean aStackable;
    /**
     * Définit si l'Item est mangeable
     */
    private boolean aEatable;
    // aUsable ??
    
    /**
     * Constructeur naturel
     * @param pName Le nom de l'item
     * @param pDescription La description de l'objet
     * @param pWeight Le poids de l'objet
     * @param pPrice Le prix de l'objet
     * @param pPickable Si l'Item est récupérable
     * @param pStackable Si l'Item est stackable
     * @param pEatable Si l'Item est mangeable
     */
    public Item(final String pName, final String pDescription, final double pWeight, final int pPrice, final boolean pPickable, final boolean pStackable, final boolean pEatable) {
        this.aName = pName;
        this.aDescription = pDescription;
        this.aWeight = pWeight;
        this.aPrice = pPrice;
        this.aPickable = pPickable;
        this.aStackable = pStackable;
        this.aEatable = pEatable;
        GameEngine.GAME_ITEM.putIfAbsent(this.aName.toLowerCase(), this);
    } // Item()
    
    /**
     * Renvoie si l'Item en parametre est dans la Room
     * @param pRoom Une Room
     * @return Si l'Item en parametre est dans la Room
     */
    public boolean isInRoom(final Room pRoom) {
        Item vItem = pRoom.getItemList().getItems().get(this.aName.toLowerCase());
        return vItem != null;
    } // isInRoom()
    
    /**
     * Renvoie si l'Item est prenable ou pas
     * @return si l'Item est prenable ou pas
     */
    public boolean isPickable() {
        return this.aPickable;
    } // isPickable()
    
    /**
     * Renvoie si l'Item est stackable ou pas
     * @return si l'Item est stackable ou pas
     */
    public boolean isStackable() {
        return this.aStackable;
    } // isStackable()
    
    /**
     * Renvoie si l'Item est mangeable ou pas
     * @return si l'Item est mangeable ou pas
     */
    public boolean isEatable() {
        return this.aEatable;
    } // isEatable()
    
    /**
     * Produit une description complete de l'item
     * @return La description complete de l'objet
     */
    public String getLongDescription() {
        return "Nom : " + this.aName + " " + this.aDescription + ", " + this.aWeight + " kg, " + this.aPrice + " Yens";
    } // getLongDescription()
    
    /**
     * Renvoie le nom de l'objet
     * @return le nom de l'objet
     */
    public String getName() {
        return this.aName;
    } // getDescription()
    
    /**
     * Renvoie la description de l'item
     * @return la description de l'item
     */
    public String getDescription() {
        return this.aDescription;
    } // getDescription()
    
    /**
     * Renvoie le poids de l'objet
     * @return le poide de l'objet
     */
    public double getWeight() {
        return this.aWeight;
    } // getWeight()
    
    /**
     * Renvoie de prix de l'objet
     * @return le prix de l'objet
     */
    public int getPrice() {
        return this.aPrice;
    } // getPrice()
    
    @Override
    public boolean equals(Object vObj) {
        if(vObj == null) {
            return false;
        }
        if(vObj.getClass() != this.getClass()) {
            return false;
        }
        Item vItem = (Item)vObj;
        if(vItem.getName().equals(this.aName) && vItem.getDescription().equals(this.aDescription) && (vItem.getPrice() == this.aPrice)) {
            return true;
        }
        return false;
    }
}
