import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.BorderLayout;

/**
 * Décrivez votre classe TradeButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class InfoButton extends PopupButton implements ActionListener {
    private UserInterface aUI;
    private MC aMC;
    
    private JDialog aDialog;
    private JPanel aMainPanel;
    private JPanel aButtonPanel;
    private JPanel aPNJPanel;
    private JPanel aPNJLabelPanel;
    private JPanel aPNJCenterPanel;
    private JPanel aSelfPanel;
    private JPanel aSelfCenterPanel;
    
    private JLabel aPNJLabel;
    private JLabel aTarget;
    
    private Color aBGColor;
    private Color aTextColor;
    private Color aHoverBGColor;
    private Color aHoverTextColor;
    
    private ArrayList<String> aItems;

    private CancelButton aCancelButton;
    
    public InfoButton(final String pText, final UserInterface pUI) {
        super(pText);
        this.aUI = pUI;
        this.aMC = this.aUI.getGE().getMC();
        
        this.aBGColor = new Color(56, 51, 51);
        this.aTextColor = new Color(245, 240, 240);
        this.aHoverBGColor = new Color(45, 40, 40);
        this.aHoverTextColor = new Color(155, 150, 150);
        
        this.aPNJLabel = new JLabel("Personnages :");
        this.aPNJLabel.setForeground(Color.white);
        
        this.aItems = new ArrayList<String>();
        
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        PopupButton.COMMAND_WORD = this.getText();
        this.createDialog();
    }
    
    private void createDialog() {
        this.setPossibleItems();
        
        this.aDialog = new JDialog(this.aUI.getFrame(), JDialog.ModalityType.APPLICATION_MODAL);
        this.aDialog.setLayout(new BorderLayout());
        
        this.aMainPanel = new JPanel();
        this.aMainPanel.setLayout(new BorderLayout());
        this.aMainPanel.setBackground(new Color(20, 0, 0));
        
        // Panel des boutons confirmer et annuler
        this.aButtonPanel = new JPanel();
        this.aButtonPanel.setBackground(this.aBGColor);
        
        // panel avec les boutons des items
        this.aPNJPanel = new JPanel();
        this.aPNJPanel.setLayout(new BorderLayout());
        this.aPNJPanel.setBackground(new Color(4, 51, 86));
        
        // panel pour le label Item
        this.aPNJLabelPanel = new JPanel();
        this.aPNJLabelPanel.add(this.aPNJLabel);
        this.aPNJLabelPanel.setBackground(new Color(4, 51, 86));
        
        this.aPNJPanel.add(this.aPNJLabelPanel, BorderLayout.NORTH);
        
        //
        this.aSelfPanel = new JPanel();
        this.aSelfPanel.setBackground(new Color(23, 26, 29));
        
        this.aSelfPanel.add(new TargetButton("Moi", this.aUI, this.aDialog));
    
        this.aCancelButton = new CancelButton("Annuler", this.aDialog, this.aUI);
        
        this.aButtonPanel.add(this.aCancelButton);
        
        this.aMainPanel.add(this.aSelfPanel, BorderLayout.NORTH);
        this.aMainPanel.add(this.aPNJPanel, BorderLayout.CENTER);
        this.aMainPanel.add(this.aButtonPanel, BorderLayout.SOUTH);
        
        this.aPNJCenterPanel = new JPanel();
        this.aSelfCenterPanel = new JPanel();
        
        this.addButtons();
        
        this.aDialog.add(aMainPanel, BorderLayout.CENTER);
        this.aDialog.setSize(500, 300);
        this.aDialog.setUndecorated(true);
        this.aDialog.setLocationRelativeTo(null);
        
        this.aDialog.setVisible(true);
    }
    
    private void addButtons() {
        for(String vItemName : this.aItems) {
            this.aPNJCenterPanel.add(new TargetButton(vItemName, this.aUI, this.aDialog));
        }
        this.aPNJPanel.add(this.aPNJCenterPanel, BorderLayout.CENTER);
    }
    
    private void setPossibleItems() {
        this.aItems.clear();
        for(String vItemName : GameEngine.GAME_PNJ.keySet()) {
            if(!this.aItems.contains(vItemName))
                this.aItems.add(vItemName);
        }
    }
}