import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemEvent;
import javax.swing.JTextArea;
import java.awt.Dimension;

/**
 * Décrivez votre classe TradeButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class WriteButton extends PopupButton implements ActionListener {
    private UserInterface aUI;
    private GameEngine aGE;
    
    private MC aMC;
    
    private JDialog aDialog;
    private JPanel aMainPanel;
    private JPanel aButtonPanel;
    
    private Color aBGColor;
    private Color aTextColor;
    private Color aHoverBGColor;
    private Color aHoverTextColor;
    
    private ConfirmButton aConfirmButton;
    private CancelButton aCancelButton;
    
    public static String TARGET;
    public static JTextArea CONTENT;
    
    public WriteButton(final String pText, final UserInterface pUI) {
        super(pText);
        this.aUI = pUI;
        this.aGE = this.aUI.getGE();
        this.aMC = this.aGE.getMC();
        
        this.aBGColor = new Color(56, 51, 51);
        this.aTextColor = new Color(245, 240, 240);
        this.aHoverBGColor = new Color(45, 40, 40);
        this.aHoverTextColor = new Color(155, 150, 150);
        
        this.CONTENT = new JTextArea();
        
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        PopupButton.COMMAND_WORD = this.getText();
        this.createDialog(this.aMC, this.aUI, this.aGE);
    }
    
    public static void createDialog(final MC pMC, final UserInterface pUI, final GameEngine pGE) {
        HashMap<String, Item> vItems = pMC.getItemList().getItems();
        if(checkWriteCondition(pMC)) {
            // String vTargetName;
            PNJ vTarget;
            
            Color vBGColor = new Color(56, 51, 51);
            Color vTextColor = new Color(240, 240, 240);
            JPanel vMainPanel = new JPanel();
            JPanel vButtonPanel = new JPanel();
            JPanel vTargetPanel = new JPanel();
            JPanel vContentPanel = new JPanel();
            
            CONTENT.setPreferredSize(new Dimension(500, 300));
            
            vContentPanel.add(CONTENT);
            
            JDialog vDialog = new JDialog(pUI.getFrame(), JDialog.ModalityType.APPLICATION_MODAL);
            ConfirmButton vConfirmButton = new ConfirmButton("Confirmer", vDialog, pUI);
            CancelButton vCancelButton = new CancelButton("Annuler", vDialog, pUI);
            
            
            JComboBox vCombobox = new JComboBox();
            for(String vPNJName : GameEngine.GAME_PNJ.keySet()) {
                vCombobox.addItem(GameEngine.GAME_PNJ.get(vPNJName).getFullName());
            }
            
            TARGET = (String)vCombobox.getItemAt(0);
            
            vDialog.setLayout(new BorderLayout());
            
            vMainPanel.setLayout(new BorderLayout());
            vMainPanel.setBackground(vBGColor);
            
            vButtonPanel.setBackground(vBGColor);
            
            vTargetPanel.setBackground(vBGColor);
            
            // vTargetPanel.add(vTargetLabel);
            vTargetPanel.add(vCombobox);
            
            vButtonPanel.add(vConfirmButton);
            vButtonPanel.add(vCancelButton);
            
            vMainPanel.add(vButtonPanel, BorderLayout.SOUTH);
            vMainPanel.add(vContentPanel, BorderLayout.CENTER);
            vMainPanel.add(vTargetPanel, BorderLayout.NORTH);
            
            vDialog.add(vMainPanel, BorderLayout.CENTER);
            vDialog.setSize(600, 400);
            vDialog.setUndecorated(true);
            vDialog.setLocationRelativeTo(null);
            
            vCombobox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(final ItemEvent pE) {
                    TARGET = (String)vCombobox.getSelectedItem();
                    System.out.println(TARGET);
                }
            });
            
            vDialog.setVisible(true);
        } else {
            if(!(vItems.containsValue(pGE.GAME_ITEM.get("dactylotype")) || vItems.containsValue(pGE.GAME_ITEM.get("plume")))) {
                System.out.println("Je n'ai pas de quoi écrire...");
            } else if(!vItems.containsValue(pGE.GAME_ITEM.get("feuille")) && !vItems.containsValue(pGE.GAME_ITEM.get("encre"))) {
                System.out.println("Il me manque une feuille et de l'encre...");
            } else if(!vItems.containsValue(pGE.GAME_ITEM.get("feuille"))) {
                System.out.println("Il me manque une feuille...");
            } else if(!vItems.containsValue(pGE.GAME_ITEM.get("encre"))) {
                System.out.println("Il me manque de l'encre...");
            }
        }
    }
    
    private static boolean checkWriteCondition(final MC pMC) {
        HashMap<String, Item> vItems = pMC.getItemList().getItems();
        return vItems.containsValue(GameEngine.GAME_ITEM.get("feuille")) && vItems.containsValue(GameEngine.GAME_ITEM.get("encre")) && (vItems.containsValue(GameEngine.GAME_ITEM.get("plume")) || vItems.containsValue(GameEngine.GAME_ITEM.get("dactylotype")));
    }
}