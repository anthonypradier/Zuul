import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * Décrivez votre classe CopyOfCancelButton ici.
 *
 * @author Anthony Pradier
 * @version 1.0, 23/05/2024
 */
public class CancelButton extends JButton implements MouseListener {
    /**
     * Interface graphique
     */
    private UserInterface aUI;
    /**
     * Fenetre de dialogue
     */
    private JDialog aDialog;
    
    /**
     * Couleur de fond
     */
    private Color aBGColor;
    /**
     * Couleur de texte
     */
    private Color aTextColor;
    /**
     * Couleur de fond lors du survolage
     */
    private Color aHoverBGColor;
    /**
     * Couleur de texte lors du survolage
     */
    private Color aHoverTextColor;
    
    /**
     * Constructeur naturel de CancelButton
     * @param pText Texte du bouton
     * @param pDialog La fenetre de dialogue sud laquelle on ajoute le bouton
     * @param pUI Interface utilisateur
     */
    public CancelButton(final String pText, final JDialog pDialog, final UserInterface pUI) {
        super(pText);
        this.aUI = pUI;
        this.aBGColor = new Color(61, 51, 51);
        this.aTextColor = new Color(250, 240, 240);
        this.aHoverBGColor = new Color(50, 40, 40);
        this.aHoverTextColor = new Color(160, 150, 150);
        this.aDialog = pDialog;
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        Module.setFontSize(this, 14);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setBackground(this.aBGColor);
        this.setForeground(this.aTextColor);
        
        this.addMouseListener(this);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
        this.setBackground(this.aHoverBGColor);
        this.setForeground(this.aHoverTextColor);
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(this.aBGColor);
        this.setForeground(this.aTextColor);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(PopupButton.COMMAND_WORD.equals("write")) {
            WriteButton.CONTENT.setText("");
        }
        PopupButton.COMMAND_WORD = "";
        PopupButton.SECOND_WORD = "";
        PopupButton.setTarget("");
        this.aDialog.dispose();
    }
}
