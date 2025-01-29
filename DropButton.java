import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.BorderLayout;

/**
 * Décrivez votre classe TradeButton ici.
 *
 * @author Anthony Pradier
 * @version 1.0, 23/05/2024
 */
public class DropButton extends PopupButton implements ActionListener {
    /**
     * Interface utilisateur
     */
    private UserInterface aUI;
    /**
     * Joueur
     */
    private MC aMC;
    
    /**
     * Fenetre de dialogue
     */
    private JDialog aDialog;
    /**
     * Panel principal
     */
    private JPanel aMainPanel;
    /**
     * Panel des boutons
     */
    private JPanel aButtonPanel;
    /**
     * Panel des Items
     */
    private JPanel aItemPanel;
    /**
     * Panel du label des Items
     */
    private JPanel aItemLabelPanel;
    /**
     * Panel central des Items
     */
    private JPanel aItemCenterPanel;
    
    /**
     * Label des Items
     */
    private JLabel aItemLabel;
    
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
     * Couleur du texte lors du survolage
     */
    private Color aHoverTextColor;
    
    /**
     * Liste des Items possibles
     */
    private ArrayList<String> aItems;
    
    /**
     * Bouton Annuler
     */
    private CancelButton aCancelButton;
    
    /**
     * Constructeur naturel de DropButton
     * @param pText Texte du bouton
     * @param pUI Interface utilisateur
     */
    public DropButton(final String pText, final UserInterface pUI) {
        super(pText);
        
        this.aUI = pUI;
        this.aMC = this.aUI.getGE().getMC();
        
        this.aBGColor = new Color(56, 51, 51);
        this.aTextColor = new Color(245, 240, 240);
        this.aHoverBGColor = new Color(45, 40, 40);
        this.aHoverTextColor = new Color(155, 150, 150);
        
        this.aItemLabel = new JLabel("Objets :");
        this.aItemLabel.setForeground(Color.white);
        
        this.aItems = new ArrayList<String>();
        
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        PopupButton.COMMAND_WORD = this.getText();
        this.createDialog();
    }
    
    /**
     * Creation de la fenetre de dialogue
     */
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
        this.aItemPanel = new JPanel();
        this.aItemPanel.setLayout(new BorderLayout());
        this.aItemPanel.setBackground(new Color(4, 51, 86));
        
        // panel pour le label Item
        this.aItemLabelPanel = new JPanel();
        this.aItemLabelPanel.add(this.aItemLabel);
        this.aItemLabelPanel.setBackground(new Color(4, 51, 86));
        
        this.aItemPanel.add(this.aItemLabelPanel, BorderLayout.NORTH);

        this.aCancelButton = new CancelButton("Annuler", this.aDialog, this.aUI);
        
        this.aButtonPanel.add(this.aCancelButton);
        
        this.aMainPanel.add(this.aItemPanel, BorderLayout.CENTER);
        this.aMainPanel.add(this.aButtonPanel, BorderLayout.SOUTH);
        
        this.aItemCenterPanel = new JPanel();
        
        this.addButtons();
        
        this.aDialog.add(aMainPanel, BorderLayout.CENTER);
        this.aDialog.setSize(500, 300);
        this.aDialog.setUndecorated(true);
        this.aDialog.setLocationRelativeTo(null);
        
        this.aDialog.setVisible(true);
    }
    
    /**
     * Ajouter les boutons dans le panel de boutons
     */
    private void addButtons() {
        for(String vItemName : this.aItems) {
            this.aItemCenterPanel.add(new TargetButton(vItemName, this.aUI, this.aDialog));
        }
        this.aItemPanel.add(this.aItemCenterPanel, BorderLayout.CENTER);
    }
    
    /**
     * Modifier les items possibles 
     */
    private void setPossibleItems() {
        this.aItems.clear();
        if(!this.aMC.getItemList().empty()) {
            for(String vItemName : this.aMC.getItemList().getItems().keySet()) {
                if(!this.aItems.contains(vItemName))
                    this.aItems.add(vItemName);
            }
        }
    }
}