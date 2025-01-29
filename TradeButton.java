import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.BoxLayout;

/**
 * Décrivez votre classe TradeButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class TradeButton extends PopupButton implements ActionListener {
    private UserInterface aUI;
    private MC aMC;
    
    private JDialog aDialog;
    private JPanel aMainPanel;
    private JPanel aButtonPanel;
    private JPanel aPNJPanel;
    private JPanel aPNJLabelPanel;
    private JPanel aPNJCenterPanel;
    
    private JLabel aPNJLabel;
    private JLabel aTarget;
    
    private Color aBGColor;
    private Color aTextColor;
    private Color aHoverBGColor;
    private Color aHoverTextColor;
    
    private ArrayList<String> aItems;

    private CancelButton aCancelButton;
    
    public TradeButton(final String pText, final UserInterface pUI) {
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
    
    public void createDialog() {
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
    
        this.aCancelButton = new CancelButton("Annuler", this.aDialog, this.aUI);
        
        this.aButtonPanel.add(this.aCancelButton);
        
        this.aMainPanel.add(this.aPNJPanel, BorderLayout.CENTER);
        this.aMainPanel.add(this.aButtonPanel, BorderLayout.SOUTH);
        
        this.aPNJCenterPanel = new JPanel();
        
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
            if(GameEngine.GAME_PNJ.get(vItemName).getCurrentRoom().equals(this.aMC.getCurrentRoom()))
                this.aItems.add(vItemName);
        }
    }
    
    public static void tradeWith() {
        // JDialog vDialog = new JDialog(pGE.getUI().getFrame(), JDialog.ModalityType.APPLICATION_MODAL);
        
        // vDialog = new JDialog(pUI.getFrame(), JDialog.ModalityType.APPLICATION_MODAL);
        // vDialog.setLayout(new BorderLayout());
        
        // JPanel vMainPanel = new JPanel();
        // vMainPanel.setLayout(new BorderLayout());
        // vMainPanel.setBackground(new Color(20, 0, 0));
        
        // JPanel vButtonPanel = new JPanel();
        // vButtonPanel.setBackground(new Color(56, 51, 51));
        
        // ConfirmButton vConfirmButton = new ConfirmButton("Confirmer", vDialog, pUI);
        // CancelButton vCancelButton = new CancelButton("Annuler", vDialog, pUI);
        
        // vButtonPanel.add(vConfirmButton);
        // vButtonPanel.add(vCancelButton);
        
        // vMainPanel.add(vButtonPanel, BorderLayout.SOUTH);
        
        // // Création des copies d'ItemList et de Wallet
        // ItemList vMCIL = (ItemList)pMC.getItemList().clone();
        // ItemList vPNJIL = (ItemList)pPNJ.getItemList().clone();
        
        // Wallet vMCWallet = (Wallet)pMC.getWallet().clone();
        // Wallet vPNJWallet = (Wallet)pPNJ.getWallet().clone();
        
        // // Wallet
        
        // // Création des panels d'inventaire
        // JPanel vCenterPanel = new JPanel();
        // vCenterPanel.setLayout(new BoxLayout(vCenterPanel, BoxLayout.X_AXIS));
        
        // JPanel vMCMainPanel = new JPanel();
        // JPanel vPNJMainPanel = new JPanel();
        // vMCMainPanel.setLayout(new BorderLayout());
        // vPNJMainPanel.setLayout(new BorderLayout());
        
        // JPanel vMCLabelPanel = new JPanel();
        // JPanel vPNJLabelPanel = new JPanel();
        
        // JPanel vMCWalletPanel = new JPanel();
        // JPanel vPNJWalletPanel = new JPanel();
        
        // JPanel vMCCenterPanel = new JPanel();
        // JPanel vPNJCenterPanel = new JPanel();
        
        // // Setup des panels
        
        // JLabel vMCLabel = new JLabel("Mon inventaire");
        // JLabel vPNJLabel = new JLabel(pPNJ.getFullName());
        
        // JLabel vMCWalletLabel = new JLabel("" + vMCWallet.getMoney());
        // JLabel vPNJWalletLabel = new JLabel("" + vPNJWallet.getMoney());
        
        // vMCMainPanel.add(vMCLabel, BorderLayout.NORTH);
        // vPNJMainPanel.add(vPNJLabel, BorderLayout.NORTH);
        
        // addTradeButtons(vMCCenterPanel, vMCMainPanel, vMCIL);
        // addTradeButtons(vPNJCenterPanel, vPNJMainPanel, vPNJIL);
        
        // vCenterPanel.add(vMCMainPanel);
        // vCenterPanel.add(vPNJMainPanel);
        
        // vMainPanel.add(vCenterPanel, BorderLayout.CENTER);
        
        // vDialog.add(vMainPanel, BorderLayout.CENTER);
        // vDialog.setSize(300, 200);
        // vDialog.setUndecorated(true);
        // vDialog.setLocationRelativeTo(null);
        
        // vDialog.setVisible(true);
        
        // Créer deux ItemLists supplémentaire pour copier l'ItemList de chacun des deux Players
        // Copier le Wallet des deux Players et créer un panel au sud des main panel pour y mettre le solde
        // La fenêtre de trade sera séparée en 2 moitiée pour y mettre les 2 ItemLists, avec des boutons spéciaux
        // Lors de l'appui de l'un de ses boutons, on le transfert à l'autre ItemList (en modifiant l'interface graphique)
        // Si on appuie sur le bouton "annuler", on ne fait rien avec les deux ItemLists créees
        // Si on appuie sur "Confirmer", on change les ItemLists des deux players avec celles créees
    }
    
    // private static void addTradeButtons(final JPanel pItemPanel, final JPanel vMainPanel, final ItemList pIL) {
        // for(String vItemName : pIL.getItems().keySet()) {
            // pItemPanel.add(new TradeItemButton(vItemName));
        // }
        // vMainPanel.add(pItemPanel, BorderLayout.CENTER);
    // }
}
