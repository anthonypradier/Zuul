
/**
 * Décrivez votre classe Quest ici.
 *
 * @author Anthony Pradier
 * @version 1.0, 23/05/2024
 */
public class Quest {
    private String aName; // words are separated with "-"
    private String aDescription; // words are separated with "-"
    private String aGoal; // finditem, gosomewhere, giveitem, writeletter, talkto
    private String aObject;
    private String aTarget;
    private int aReward;
    private int aExp;
    
    // createquest nom_description_finditem_intemname_null_10_10
    // createquest nom_description_gosomewhere_roomname_null_10_10
    // createquest nom_description_giveitem_intemname_pnjname_10_10
    // createquest nom_description_writeletter_letter_pnjname_10_10
    // createquest nom_description_talkto_pnjname_null_10_10
    
    private Item aItemQuest;
    private Letter aLetterQuest;
    private PNJ aPNJQuest;
    private Room aRoomQuest;
    
    public Quest(final String pName, final String pDescription, final String pGoal, final String pObject, final String pTarget, final int pReward, final int pExp) {
        this.aName = pName;
        this.aDescription = pDescription;
        this.aGoal = pGoal;
        this.aObject = pObject;
        this.aTarget = pTarget;
        this.aReward = pReward;
        this.aExp = pExp;
        
        if(this.aGoal.equals("finditem")) {
            this.aItemQuest = GameEngine.GAME_ITEM.get(this.aObject.toLowerCase());
        } else if(this.aGoal.equals("giveitem")) {
            this.aItemQuest = GameEngine.GAME_ITEM.get(this.aObject.toLowerCase());
            this.aPNJQuest = GameEngine.GAME_PNJ.get(this.aTarget.toLowerCase());
            
        } else if(this.aGoal.equals("writeletter")) {
            this.aLetterQuest = null;
            this.aPNJQuest = GameEngine.GAME_PNJ.get(this.aObject.toLowerCase());
            
        } else if(this.aGoal.equals("talkto")) {
            this.aPNJQuest = GameEngine.GAME_PNJ.get(this.aObject.toLowerCase());
        } else if(this.aGoal.equals("gosomewhere")) {
            this.aRoomQuest = GameEngine.GAME_ROOM.get(this.aObject.toLowerCase());
        }
    }
    
    public String getName() {
        return this.aName;
    }
    
    public String getDescription() {
        return this.aDescription;
    }
    
    public String getGoal() {
        return this.aGoal;
    }
    
    public int getReward() {
        return this.aReward;
    }
    
    public int getExp() {
        return this.aExp;
    }
    
    public String getRewardsString() {
        return this.aReward + " ¥ - " + this.aExp + " xp";
    }
    
    
    public Item getItemQuest() {
        return this.aItemQuest;
    }
    
    public Letter getLetterQuest() {
        return this.aLetterQuest;
    }
    
    public PNJ getPNJQuest() {
        return this.aPNJQuest;
    }
    
    public Room getRoomQuest() {
        return this.aRoomQuest;
    }
}
