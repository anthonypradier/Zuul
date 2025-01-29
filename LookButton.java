import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.Set;
import java.util.ArrayList;
import java.awt.Dimension;

/**
 * Décrivez votre classe TradeButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class LookButton extends PopupButton implements ActionListener {
    private UserInterface aUI;
    private MC aMC;
    
    private JDialog aDialog;
    private JPanel aMainPanel;
    private JPanel aButtonPanel;
    private JPanel aItemPanel;
    private JPanel aItemLabelPanel;
    private JPanel aItemCenterPanel;
    private JPanel aRoomPanel;
    private JPanel aSelectedPanel;
    private JPanel aRoomCenterPanel;
    
    private JLabel aItemLabel;
    private JLabel aSelected;
    private JLabel aTarget;
    
    private Color aBGColor;
    private Color aTextColor;
    private Color aHoverBGColor;
    private Color aHoverTextColor;
    
    private ArrayList<String> aItems;

    private CancelButton aCancelButton;
    public LookButton(final String pText, final UserInterface pUI) {
        super(pText);
        this.aUI = pUI;
        this.aMC = this.aUI.getGE().getMC();
        
        this.aBGColor = new Color(56, 51, 51);
        this.aTextColor = new Color(245, 240, 240);
        this.aHoverBGColor = new Color(45, 40, 40);
        this.aHoverTextColor = new Color(155, 150, 150);
        
        this.aItemLabel = new JLabel("Objets :");
        this.aItemLabel.setForeground(Color.white);
        this.aSelected = new JLabel("Selectionné : ");
        this.aSelected.setForeground(Color.white);
        
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
        this.aItemPanel = new JPanel();
        this.aItemPanel.setLayout(new BorderLayout());
        this.aItemPanel.setBackground(new Color(4, 51, 86));
        
        // panel pour le label Item
        this.aItemLabelPanel = new JPanel();
        this.aItemLabelPanel.add(this.aItemLabel);
        this.aItemLabelPanel.setBackground(new Color(4, 51, 86));
        
        this.aItemPanel.add(this.aItemLabelPanel, BorderLayout.NORTH);
        
        //
        this.aRoomPanel = new JPanel();
        // this.aRoomPanel.setLayout(new BorderLayout());
        this.aRoomPanel.setBackground(new Color(23, 26, 29));
        
        this.aRoomPanel.add(new TargetButton("Salle", this.aUI, this.aDialog));
    
        //
        // this.aSelectedPanel = new JPanel();
        // this.aSelectedPanel.setBackground(new Color(23, 26, 29));
        // this.aSelectedPanel.add(this.aSelected);
        // this.aSelectedPanel.add(PopupButton.TARGET);
        
        // this.aItemPanel.add(this.aSelectedPanel, BorderLayout.SOUTH);
        
        // this.aConfirmButton = new ConfirmButton("Confirmer", this.aDialog, this.aUI);
        this.aCancelButton = new CancelButton("Annuler", this.aDialog, this.aUI);
        
        // this.aButtonPanel.add(this.aConfirmButton);
        this.aButtonPanel.add(this.aCancelButton);
        
        this.aMainPanel.add(this.aRoomPanel, BorderLayout.NORTH);
        this.aMainPanel.add(this.aItemPanel, BorderLayout.CENTER);
        this.aMainPanel.add(this.aButtonPanel, BorderLayout.SOUTH);
        
        this.aItemCenterPanel = new JPanel();
        this.aRoomCenterPanel = new JPanel();
        
        this.addButtons();
        
        this.aDialog.add(aMainPanel, BorderLayout.CENTER);
        this.aDialog.setSize(500, 300);
        this.aDialog.setUndecorated(true);
        this.aDialog.setLocationRelativeTo(null);
        
        this.aDialog.setVisible(true);
    }
    
    private void addButtons() {
        for(String vItemName : this.aItems) {
            this.aItemCenterPanel.add(new TargetButton(vItemName, this.aUI, this.aDialog));
        }
        this.aItemPanel.add(this.aItemCenterPanel, BorderLayout.CENTER);
    }
    
    private void setPossibleItems() {
        this.aItems.clear();
        if(!this.aMC.getItemList().empty()) {
            for(String vItemName : this.aMC.getItemList().getItems().keySet()) {
                if(!this.aItems.contains(vItemName))
                    this.aItems.add(vItemName);
            }
        }
        if(!this.aMC.getCurrentRoom().getItemList().empty()) {
            for(String vItemName : this.aMC.getCurrentRoom().getItemList().getItems().keySet()) {
                if(!this.aItems.contains(vItemName))
                    this.aItems.add(vItemName);
            }
        }
    }
    
    public JPanel getSelectedPanel() {
        return this.aSelectedPanel;
    }
}