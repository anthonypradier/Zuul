import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Décrivez votre classe EntryField ici.
 *
 * @author Anthony Pradier
 * @version 1.0, 23/05/2024
 */
public class EntryField extends JTextField implements ActionListener {
    /**
     * Interface utilisateur
     */
    private UserInterface aUI;
    
    /**
     * Constructeur naturel de EntryField
     * @param pColumns Nombre de colonnes
     * @param pUI Interface utilisateur
     */
    public EntryField(final int pColumns, final UserInterface pUI) {
        super(pColumns);
        this.aUI = pUI;
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        String vStr = this.getText();
        this.setText("");
        this.aUI.getGE().interpretCommand(vStr);
    }
    
    /**
     * Rendre EntryField inutilisable
     * @param pOnOff Rend utilisable si true
     */
    public void enable(final boolean pOnOff) {
        this.setEditable(pOnOff); // enable/disable
        if (pOnOff) { // enable
            this.getCaret().setBlinkRate( 500 ); // cursor blink
            this.addActionListener(this); // reacts to entry
        }
        else { // disable
            this.getCaret().setBlinkRate(0); // cursor won't blink
            this.removeActionListener(this); // won't react to entry
        }
    }
}
