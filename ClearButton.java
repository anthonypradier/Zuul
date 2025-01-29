import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Décrivez votre classe CopyOfClearButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class ClearButton extends JButton implements ActionListener{
    /**
     * Zone de texte de la fenetre
     */
    private JTextArea aLog;
    
    /**
     * Constructeur naturel de ClearButton
     * @param pText Texte du bouton
     * @param pLog Zone de texte de la fenetre
     */
    public ClearButton(final String pText, final JTextArea pLog) {
        super(pText);
        this.aLog = pLog;
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        this.aLog.setText("");
    }
}