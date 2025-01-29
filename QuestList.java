import java.util.HashMap;

/**
 * Décrivez votre classe QuestList ici.
 *
 * @author Anthony Pradier
 * @version 1.0, 23/05/2024
 */
public class QuestList {
    private HashMap<String, Quest> aQuests;
    
    public QuestList() {
        this.aQuests = new HashMap<String, Quest>();
    }
    
    public HashMap<String, Quest> getQuests() {
        return this.aQuests;
    }
    
    public Quest getSingleQuest() {
        if(this.aQuests.size() == 1) {
            return (Quest)this.aQuests.values().toArray()[0];
        }
        return null;
    }
    
    public void addQuest(final Quest pQuest) {
        this.aQuests.put(pQuest.getName().toLowerCase(), pQuest);
    }
    
    public void removeQuest(final Quest pQuest) {
        this.aQuests.remove(pQuest.getName().toLowerCase());
    }
    
    public String getQuestListString() {
        if(this.aQuests.isEmpty()) {
            return "Aucune quête disponible";
        }
        String vQuests = "Quêtes disponibles :";
        for(String vQName : this.aQuests.keySet()) {
            vQuests += "\n   - " + this.aQuests.get(vQName).getName();
        }
        return vQuests;
    }
}
