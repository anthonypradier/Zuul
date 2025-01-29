
/**
 * Décrivez votre classe Letter ici.
 *
 * @author Anthony Pradier
 * @version 04/15/2024
 */
public class Letter extends Item {
    private static int COUNT = 1;
    
    private Player aAuthor;
    private Player aTarget;
    private String aContent;
    
    public Letter(final Player pAuthor, final Player pTarget, final String pContent) {
        super("Lettre" + COUNT, "Lettre de " + pAuthor.getName() + " adressée à " + pTarget.getName(), 0.002, 400, true, false, false);
        this.aAuthor = pAuthor;
        this.aTarget = pTarget;
        this.aContent = "Lettre adressée à " + this.aTarget + ", de la part de " + this.aAuthor + "\n\n" + pContent;
        
        COUNT += 1;
    }
    
    public String getContent() {
        return this.aContent;
    }
}
