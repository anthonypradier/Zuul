import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * Décrivez votre classe QuestButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class QuestButton extends JButton implements ActionListener{
    private String aQuestName;
    private Player aOwner;
    private Player aGetter;
    private Quest aQuest;
    
    /**
     * Constructeur d'objets de classe QuestButton
     */
    public QuestButton(final String pQuestName, final Player pOwner, final Player pGetter) {
        super("Accepter");
        this.aQuestName = pQuestName;
        this.aOwner = pOwner;
        this.aGetter = pGetter;
        this.aQuest = this.aOwner.getQuestList().getQuests().get(this.aQuestName);
        
        this.addActionListener(this);
    }
    
    public String getQuestName() {
        return this.aQuestName;
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        this.aGetter.getQuestList().addQuest(this.aQuest);
        this.aOwner.getQuestList().removeQuest(this.aQuest);
        
        this.setBackground(new Color(180, 180, 180));
        this.setText("Acceptée");
        this.setEnabled(false);
    }
}
