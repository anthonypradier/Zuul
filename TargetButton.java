import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;

/**
 * Décrivez votre classe TargetButton ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class TargetButton extends JButton implements ActionListener {
    private UserInterface aUI;
    private JDialog aDialog;
    
    public TargetButton(final String pText, final UserInterface pUI, final JDialog pDialog) {
        super(Module.camelCase(pText));
        this.aUI = pUI;
        this.aDialog = pDialog;
        this.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent pE) {
        String vSecondWord = this.getText().split(" ")[0].toLowerCase();
        PopupButton.SECOND_WORD = vSecondWord;
        PopupButton.setTarget(this.getText()); // Changer les target
        
        //
        if(PopupButton.SECOND_WORD.equals("")) {
            return;
        } else if(PopupButton.SECOND_WORD.equals("salle")) {
            this.aUI.getGE().interpretCommand(PopupButton.COMMAND_WORD);
        } else if(PopupButton.SECOND_WORD.equals("moi")) {
            this.aUI.getGE().interpretCommand(PopupButton.COMMAND_WORD);
        } else if(PopupButton.COMMAND_WORD.equals("talk")) {
            PNJ vTarget = GameEngine.GAME_PNJ.get(vSecondWord);
            TalkButton.talkTo(this.aUI.getGE().getMC(), vTarget, this.aUI, this.aUI.getGE());
        } else if(PopupButton.COMMAND_WORD.equals("trade")) {
            PNJ vTarget = GameEngine.GAME_PNJ.get(vSecondWord);
            TradeWindow vTW = new TradeWindow(this.aUI.getGE().getMC(), vTarget, this.aUI, this.aUI.getGE());
        } else {
            this.aUI.getGE().interpretCommand(PopupButton.COMMAND_WORD + " " + vSecondWord);
        }
        PopupButton.COMMAND_WORD = "";
        PopupButton.SECOND_WORD = "";
        this.aUI.getLog().setCaretPosition(this.aUI.getLog().getDocument().getLength());
        
        this.aDialog.dispose();
    }
}
