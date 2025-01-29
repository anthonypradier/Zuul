import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Décrivez votre classe TradeItemButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class TradeItemButton extends JButton implements ActionListener {
    private Item aItem;
    private TradeWindow aTW;
    private Player aP;
    
    private boolean aSide;
    /**
     * Constructeur d'objets de classe TradeItemButton
     */
    public TradeItemButton(final String pText, final TradeWindow pTW, final Player pP) {
        super(pText);
        
        this.aItem = GameEngine.GAME_ITEM.get(pText.toLowerCase());
        this.aTW = pTW;
        this.aP = pP;
        
        if(this.aP instanceof MC) {
            this.aSide = true;
        } else {
            this.aSide = false;
        }
        
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        if(this.aSide) {
            this.setVisible(false);
            this.aTW.getPNJCenterPanel().add(this);
            this.aTW.getMCCenterPanel().remove(this);
            this.aTW.getMCCenterPanel().revalidate();
            this.aTW.getMCCenterPanel().repaint();
            this.aTW.getMainPanel().add(this.aTW.getMCCenterPanel());
            this.aTW.getMainPanel().add(this.aTW.getPNJCenterPanel());
            
            this.aTW.getDialog().revalidate();
            this.aTW.getDialog().repaint();
        } else {
            this.setVisible(false);
            this.aTW.getMCCenterPanel().add(this);
            this.aTW.getPNJCenterPanel().remove(this);
            this.aTW.getPNJCenterPanel().revalidate();
            this.aTW.getPNJCenterPanel().repaint();
            
            this.aTW.getDialog().revalidate();
            this.aTW.getDialog().repaint();
        }
    }
}
