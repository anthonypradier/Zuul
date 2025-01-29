import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.BorderLayout;

/**
 * Décrivez votre classe TradeWindow ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class TradeWindow {
    private JDialog aDialog;
    private JPanel aMCCenterPanel;
    private JPanel aPNJCenterPanel;
    private JPanel aMainPanel;
    
    private JLabel aMCWalletLabel;
    private JLabel aPNJWalletLabel;
    
    /**
     * Constructeur d'objets de classe TradeWindow
     */
    public TradeWindow(final MC pMC, final PNJ pPNJ, final UserInterface pUI, final GameEngine pGE) {
        this.aDialog = new JDialog(pUI.getFrame(), JDialog.ModalityType.APPLICATION_MODAL);
        this.aMCCenterPanel = new JPanel();
        this.aPNJCenterPanel = new JPanel();
        this.aMCWalletLabel = new JLabel("" + pMC.getWallet().getMoney());
        this.aPNJWalletLabel = new JLabel("" + pPNJ.getWallet().getMoney());
        
        this.aDialog.setLayout(new BorderLayout());
        
        this.aMainPanel = new JPanel();
        this.aMainPanel.setLayout(new BorderLayout());
        this.aMainPanel.setBackground(new Color(20, 0, 0));
        
        JPanel vButtonPanel = new JPanel();
        vButtonPanel.setBackground(new Color(56, 51, 51));
        
        ConfirmButton vConfirmButton = new ConfirmButton("Confirmer", this.aDialog, pUI);
        CancelButton vCancelButton = new CancelButton("Annuler", this.aDialog, pUI);
        
        vButtonPanel.add(vConfirmButton);
        vButtonPanel.add(vCancelButton);
        
        this.aMainPanel.add(vButtonPanel, BorderLayout.SOUTH);
        
        // Création des copies d'ItemList et de Wallet
        ItemList vMCIL = (ItemList)pMC.getItemList().clone();
        ItemList vPNJIL = (ItemList)pPNJ.getItemList().clone();
        
        Wallet vMCWallet = (Wallet)pMC.getWallet().clone();
        Wallet vPNJWallet = (Wallet)pPNJ.getWallet().clone();
        
        // Wallet
        
        // Création des panels d'inventaire
        JPanel vCenterPanel = new JPanel();
        vCenterPanel.setLayout(new BoxLayout(vCenterPanel, BoxLayout.X_AXIS));
        
        JPanel vMCMainPanel = new JPanel();
        JPanel vPNJMainPanel = new JPanel();
        vMCMainPanel.setLayout(new BorderLayout());
        vPNJMainPanel.setLayout(new BorderLayout());
        
        JPanel vMCLabelPanel = new JPanel();
        JPanel vPNJLabelPanel = new JPanel();
        
        JPanel vMCWalletPanel = new JPanel();
        JPanel vPNJWalletPanel = new JPanel();
        
        JPanel vMCCenterPanel = new JPanel();
        JPanel vPNJCenterPanel = new JPanel();
        
        // Setup des panels
        
        JLabel vMCLabel = new JLabel("Mon inventaire");
        JLabel vPNJLabel = new JLabel(pPNJ.getFullName());
        
        JLabel vMCWalletLabel = new JLabel("" + vMCWallet.getMoney());
        JLabel vPNJWalletLabel = new JLabel("" + vPNJWallet.getMoney());
        
        vMCMainPanel.add(vMCLabel, BorderLayout.NORTH);
        vPNJMainPanel.add(vPNJLabel, BorderLayout.NORTH);
        
        vMCWalletPanel.add(vMCWalletLabel);
        vPNJWalletPanel.add(vMCWalletLabel);
        
        this.addTradeButtons(pMC, vMCCenterPanel, vMCMainPanel, vMCIL);
        this.addTradeButtons(pPNJ, vPNJCenterPanel, vPNJMainPanel, vPNJIL);
        
        vCenterPanel.add(vMCMainPanel);
        vCenterPanel.add(vPNJMainPanel);
        
        this.aMainPanel.add(vCenterPanel, BorderLayout.CENTER);
        
        this.aDialog.add(this.aMainPanel, BorderLayout.CENTER);
        this.aDialog.setSize(500, 300);
        this.aDialog.setUndecorated(true);
        this.aDialog.setLocationRelativeTo(null);
        
        this.aDialog.setVisible(true);
    }
    
    private void addTradeButtons(final Player pP, final JPanel pItemPanel, final JPanel vMainPanel, final ItemList pIL) {
        System.out.println("ajout");
        for(String vItemName : pIL.getItems().keySet()) {
            pItemPanel.add(new TradeItemButton(vItemName, this, pP));
        }
        vMainPanel.add(pItemPanel, BorderLayout.CENTER);
    }
    
    public JDialog getDialog() {
        return this.aDialog;
    }
    
    public JPanel getMCCenterPanel() {
        return this.aMCCenterPanel;
    }
    
    public JPanel getPNJCenterPanel() {
        return this.aPNJCenterPanel;
    }
    
    public JPanel getMainPanel() {
        return this.aMainPanel;
    }
    
    public JLabel getMCWalletLabel() {
        return this.aMCWalletLabel;
    }
    
    public JLabel getPNJWalletLabel() {
        return this.aPNJWalletLabel;
    }
}
