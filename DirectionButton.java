
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ButtonModel;

/**
 * Décrivez votre classe CopyOfDirectionButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class DirectionButton extends JButton implements ActionListener {
    /**
     * Interface utilisateur
     */
    private UserInterface aUI;
    
    /**
     * Constructeur naturel de DirectionButton
     * @param pText Texte du bouton
     * @param pUI Interface utilisateur
     */
    public DirectionButton(final String pText, final UserInterface pUI) {
        super(pText);
        this.aUI = pUI;
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        this.aUI.getGE().interpretCommand("go " + this.getText());
        // this...aMC.goRoom();
    }
}
