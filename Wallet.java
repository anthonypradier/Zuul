
/**
 * Classe représentant le porte monnaie des Player
 * Elle gère les flux d'argent de Player en Player
 *
 * @author Anthony Pradier
 * @version 1.0, 04/08/2024
 */
public class Wallet implements Cloneable {
    /**
     * Le montant actuel du Wallet
     */
    private int aMoney;
    
    /**
     * Constructeur par défaut du Wallet
     */
    public Wallet() {
        this.aMoney = 0;
    }
    
    /**
     * Renvoie le montant actuel du Wallet
     * @return le montant actuel du Wallet
     */
    public int getMoney() {
        return this.aMoney;
    }
    
    /**
     * Ajoute un montant au Wallet
     * @return si l'action a été effectuée
     */
    public boolean addMoney(final int pAmount) {
        this.aMoney += pAmount;
        return true;
    }
    
    /**
     * Retire un montant au Wallet
     * @return si l'action a été effectuée
     */
    public boolean removeMoney(final int pAmount) {
        if(this.aMoney < pAmount) {
            return false;
        }
        return this.addMoney(-pAmount);
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
