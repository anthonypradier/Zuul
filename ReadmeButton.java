import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Décrivez votre classe ReadmeButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class ReadmeButton extends JButton implements ActionListener {
    public ReadmeButton() {
        super("README");
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        System.out.println("Read me text");
    }
}
