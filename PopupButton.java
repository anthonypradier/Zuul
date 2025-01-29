import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Décrivez votre classe CopyOfPopupButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class PopupButton extends JButton implements MouseListener {
    public static String COMMAND_WORD = "";
    public static String SECOND_WORD = "";
    
    public static JLabel TARGET = new JLabel("");
    
    private Color aBGColor;
    private Color aTextColor;
    private Color aHoverBGColor;
    private Color aHoverTextColor;
    
    public PopupButton(final String pText) {
        super(pText);
        
        this.aBGColor = new Color(61, 51, 51);
        this.aTextColor = new Color(250, 240, 240);
        this.aHoverBGColor = new Color(50, 40, 40);
        this.aHoverTextColor = new Color(160, 150, 150);
        
        this.setBackground(this.aBGColor);
        this.setForeground(this.aTextColor);
        this.TARGET.setForeground(Color.white);
        
        this.addMouseListener(this);
    }
    
    public static void setTarget(final String pTarget) {
        TARGET.setText(pTarget);
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
        
    }
    
}
