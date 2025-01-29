import java.util.HashMap;

/**
 * Classe ItemList qui permet de gérer les transferts d'Items entre les Players et les Rooms
 *
 * @author Anthony Pradier
 * @version 1.0, 03/07/2024
 */
public class ItemList implements Cloneable {
    /**
     * HashMap qui stock les Items
     */
    private HashMap<String, Item> aItems;
    /**
     * HashMap qui stock les occurences d'Items
     */
    private HashMap<Item, Integer> aStacked;
    
    /**
     * Constructeur par défaut de ItemList
     */
    public ItemList() {
        this.aItems = new HashMap<String, Item>();
        this.aStacked = new HashMap<Item, Integer>();
    }
    
    /**
     * Renvoie la HashMap de l'inventaire
     * @return la HashMap de l'inventaire
     */
    public HashMap<String, Item> getItems() {
        return this.aItems;
    } // getItems()
    
    /**
     * Renvoie la HashMap de l'inventaire
     * @return la HashMap de l'inventaire
     */
    public HashMap<Item, Integer> getStackedItems() {
        return this.aStacked;
    } // getItems()
    
    /**
     * Renvoie si l'inventaire est vide ou pas
     * @return si l'inventaire est vide ou pas
     */
    public boolean empty() {
        return this.aItems.isEmpty();
    } // empty()
    
     /**
      * Ajoute un Item dans l'inventaire. Si celui-ci est stackable
      * on crée un élément (Item, Integer) dans la HashMap aStacked si il n'existe pas encore
      * ou on lui ajoute une occurence si il existe déjà. Si le nombre d'occurence est à 0
      * l'Item n'est plus dans l'Inventaire.
      * @param pItem Item que l'on veut mettre dans l'inventaire
      */
    public void addItem(final Item pItem) {
        if(!pItem.isStackable()) { // .putIfAbsent()
            this.aItems.put(pItem.getName().toLowerCase(), pItem);
        } else {
            if(this.aItems.putIfAbsent(pItem.getName().toLowerCase(), pItem) == null) {
                this.aStacked.put(pItem, 1);
            } else {
                Item vItem = this.aItems.get(pItem.getName().toLowerCase());
                Integer vI = this.aStacked.get(vItem);
                vI += 1;
                this.aStacked.put(vItem, vI);
            }
        }
    } // addItem()
    
    /**
      * Retire un Item dans l'inventaire. Si celui-ci est stackable
      * on soustrait 1 à (Item, Integer) dans la HashMap aStacked
      * Si le nombre d'occurence tombe à 0 l'Item n'est plus dans l'Inventaire
      * et on doit le supprimer de aItems et/ou de aStacked
      * @param pItem Item que l'on veut mettre dans l'inventaire
     */
    public void removeItem(final Item pItem) {
        if(!pItem.isStackable()) {
            this.aItems.remove(pItem.getName().toLowerCase());
        } else {
            Integer vI = this.aStacked.get(pItem);
            boolean vZero = (vI - 1 == 0);
            if(vZero) {
                this.aStacked.remove(pItem);
                this.aItems.remove(pItem.getName().toLowerCase());
            } else {
                this.aStacked.put(pItem, vI - 1);
            }
        }
    } // removeItem()
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
            return null;
        }
    }
}
