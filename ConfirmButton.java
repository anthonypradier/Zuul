import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Décrivez votre classe CancelButton ici.
 *
 * @author Anthony Pradier
 * @version 1.0, 23/05/2024
 */
public class ConfirmButton extends JButton implements MouseListener {
    /**
     * Interface graphique
     */
    private UserInterface aUI;
    /**
     * Fenetre de dialogue
     */
    private JDialog aDialog;
    /**
     * Joueur
     */
    private MC aMC;
    
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
     * Constructeur naturel de ConfirmButton
     * @param pText Texte du bouton
     * @param pDialog La fenetre de dialogue sud laquelle on ajoute le bouton
     * @param pUI Interface utilisateur
     */
    public ConfirmButton(final String pText, final JDialog pDialog, final UserInterface pUI) {
        super(pText);
        this.aUI = pUI;
        this.aMC = this.aUI.getGE().getMC();
        
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
        if(PopupButton.COMMAND_WORD.equals("look")) {
            if(PopupButton.SECOND_WORD.equals("")) {
                return;   
            } else if(PopupButton.COMMAND_WORD.equals("look") && PopupButton.SECOND_WORD.equals("salle")) {
                this.aUI.getGE().interpretCommand(PopupButton.COMMAND_WORD);
            }  else {
            this.aUI.getGE().interpretCommand(PopupButton.COMMAND_WORD + " " + PopupButton.SECOND_WORD);
            }
        } else if(PopupButton.COMMAND_WORD.equals("write")) {
            System.out.println("debut");
            Player vTarget = GameEngine.GAME_PNJ.get(WriteButton.TARGET.split(" ")[0].toLowerCase());
            System.out.println("fin");
            String vContent = WriteButton.CONTENT.getText();
            this.aMC.addItem(new Letter(this.aMC, vTarget, vContent));
            this.aMC.removeItem(GameEngine.GAME_ITEM.get("feuille"));
            this.aMC.removeItem(GameEngine.GAME_ITEM.get("encre"));
            System.out.println("lettre ecrite");
            WriteButton.TARGET = "";
            WriteButton.CONTENT.setText("");
        }
        PopupButton.COMMAND_WORD = "";
        PopupButton.SECOND_WORD = "";
        this.aUI.getLookButton().setTarget("");
        this.aDialog.dispose();
    }
}
