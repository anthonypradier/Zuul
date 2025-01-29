import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.net.URL;
import java.awt.image.*;
import javax.swing.JButton;
import java.util.ArrayList;

/**
 * Moteur du jeu qui implémente une interface graphique
 * 
 * @author Michael Kolling
 * @version 2.0, 02/08/2024
 */
public class UserInterface {
    /**
     * Référence à GameEngine
     */
    private GameEngine aEngine;

    private JLabel aImage;
    
    private JFrame aFrame;
    private JTextArea aLog;
    private EntryField aEntryField;
    private JPanel aImagePanel;
    
    private LookButton aLookButton;
    private UseButton aUseButton;
    private TakeButton aTakeButton;
    private DropButton aDropButton;
    private InfoButton aInfoButton;
    private TradeButton aTradeButton;
    private WriteButton aWriteButton;
    private TalkButton aTalkButton;
    
    private ArrayList<JButton> aButtonArray;
    private ArrayList<JButton> aCommandsButtonArray;
    private ArrayList<JButton> aDirectionButtonArray;
    
    private ClearButton aClearButton;
    
    private double aWidthUnite;
    private double aHeightUnite;
    
    private Color aBGColor;
    private Color aTextColor;
    private Color aHoverBGColor;
    private Color aHoverTextColor;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Show an image file in the interface.
     * @param pImageName Le nom de l'image
     */
    public void showImage(final String pImageName) {
        String vImagePath = "assets/images/" + pImageName + ".jpeg"; // to change the directory
        // URL vImageURL = this.getClass().getClassLoader().getResource(vImagePath);
        // if ( vImageURL == null )
            // System.out.println("Image not found : " + vImagePath);
        // else {
            ImageIcon vIcon = new ImageIcon( vImagePath );
            this.aImage.setIcon( vIcon );
            this.aImagePanel.add(this.aImage);
            this.aFrame.pack();
        // }
        
        // ImageIcon imageIcon = new ImageIcon("assets/images/" + pImageName + ".jpg"); // load the image to a imageIcon
        // System.out.println(imageIcon);
        // Image image = imageIcon.getImage(); // transform it 
        // Image newimg = image.getScaledInstance((int)(4 * this.aWidthUnite), (int)(5 * this.aHeightUnite),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        // imageIcon = new ImageIcon(newimg);
        // this.aImage.setIcon(imageIcon);
        // this.aImagePanel.add(this.aImage);
    } // showImage(.)

    /**
     * Interface utilisateur
     */
    private void createGUI() {
        this.aFrame = new JFrame("Violet Evergarden");
        SwingUtilities.invokeLater(() -> {
           this.aFrame.setVisible(true); 
        });
        
        this.aFrame.setTitle("Violet Evergarden");
        this.aFrame.setSize(1280, 720);
        this.aFrame.setMinimumSize(new Dimension(1280, 720));
        this.aFrame.setMaximumSize(new Dimension(1280, 720));
        this.aFrame.setPreferredSize(new Dimension(1280, 720));
        this.aFrame.setState(JFrame.NORMAL);
        this.aFrame.setLocationRelativeTo(null);
        this.aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.aFrame.setResizable(false);
        
        ImageIcon imageIcon = new ImageIcon("assets/images/logo2.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        this.aFrame.setIconImage(newimg);
        
        this.aImage = new JLabel();

        this.aBGColor = new Color(51, 51, 51);
        this.aTextColor = new Color(240, 240, 240);
        this.aHoverBGColor = new Color(40, 40, 40);
        this.aHoverTextColor = new Color(150, 150, 150);
        
        // Ajout des composants et des evenements
        this.createForm();
        
        // to end program when window is closed
        this.aFrame.addWindowListener(
            new WindowAdapter() { // anonymous class
                @Override public void windowClosing(final WindowEvent pE)
                {
                    System.exit(0);
                }
        } );

        // this.aFrame.pack();
        this.aFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    private void createForm() {
        // Panel Creation
        JPanel vPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        vPanel.setBackground(new Color(0, 0, 0));
        vPanel.setLayout(new GridBagLayout());
        
        // JFrame Dimension
        Dimension vFrameDim = this.aFrame.getSize();
        double vFrameWidth = vFrameDim.getWidth();
        double vFrameHeight = vFrameDim.getHeight();
        
        this.aWidthUnite = vFrameWidth / 15;
        this.aHeightUnite = vFrameHeight / 15;
        
        this.aImagePanel = new JPanel();
        JPanel vButtonPanel = new JPanel();
        JPanel vMapPanel = new JPanel();
        JPanel vTextAreaPanel = new JPanel();
        JPanel vOtherPanel = new JPanel();
        JPanel vClearPanel = new JPanel();
        JPanel vTextFieldPanel = new JPanel();
        
        this.aDirectionButtonArray = new ArrayList();
        this.aCommandsButtonArray = new ArrayList();
        this.aButtonArray = new ArrayList();

        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 0;
        c.ipady = 0;
        
        // Set up the panels
        this.setupImagePanel(vPanel, this.aImagePanel, c);
        
        this.setupTextAreaPanel(vPanel, vTextAreaPanel, c);
        
        this.setupButtonPanel(vPanel, vButtonPanel, c);
        
        this.setupReadmePanel(vPanel, vButtonPanel, c);
        
        this.setupMapPanel(vPanel, vMapPanel, c);
        
        this.setupOtherPanel(vPanel, vOtherPanel, c);
        
        this.setupClearPanel(vPanel, vClearPanel, c);
        
        this.setupTextFieldPanel(vPanel, vTextFieldPanel, c);

        
        this.addAllButtonsEventListeners();
        // this.addTextFieldListener();
        
        
        this.aFrame.getContentPane().add(vPanel);
    }
    
    private void setupImagePanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        this.aImage = new JLabel();
        
        pPanel.setMinimumSize(new Dimension((int)(10 * this.aWidthUnite), (int)(10 * this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(10 * this.aWidthUnite), (int)(10 * this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(10 * this.aWidthUnite), (int)(10 * this.aHeightUnite)));
        pPanel.setBackground(new Color(230, 230, 230));
        pPanel.setLayout(new BorderLayout());
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 0;
        pC.gridwidth = 10;
        pC.gridheight = 10;
        
        pPanel.add(this.aImage, BorderLayout.NORTH);
        
        pMainPanel.add(pPanel, pC);
    }
    
    private void setupButtonPanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        pPanel.setMinimumSize(new Dimension((int)(4 * this.aWidthUnite) - 1, (int)(10 * this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(4 * this.aWidthUnite) - 1, (int)(10 * this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(4 * this.aWidthUnite) - 1, (int)(10 * this.aHeightUnite)));
        pPanel.setBackground(this.aBGColor);
        pPanel.setLayout(new BorderLayout());
        
        this.setupCommandsPanel(pMainPanel, pPanel, pC);
        this.setupDirectionPanel(pMainPanel, pPanel, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 10;
        pC.gridy = 0;
        pC.gridwidth = 4;
        pC.gridheight = 10;
        
        pMainPanel.add(pPanel, pC);
    }
    
    private void setupCommandsPanel(final JPanel pMainPanel, final JPanel pButtonPanel, final GridBagConstraints pC) {
        JPanel vCommandsPanel = new JPanel();
        
        vCommandsPanel.setMinimumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(3 * this.aHeightUnite)));
        vCommandsPanel.setMaximumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(3 * this.aHeightUnite)));
        vCommandsPanel.setPreferredSize(new Dimension((int)(3 * this.aWidthUnite), (int)(3 * this.aHeightUnite)));
        vCommandsPanel.setBackground(this.aBGColor);
        vCommandsPanel.setLayout(new GridBagLayout());
        
        JButton vBackButton = new JButton("back");
        JButton vChargeButton = new JButton("charge");
        
        this.aLookButton = new LookButton("look", this);
        this.aUseButton = new UseButton("use", this);
        this.aTakeButton = new TakeButton("take", this);
        this.aDropButton = new DropButton("drop", this);
        this.aInfoButton = new InfoButton("info", this);
        this.aTradeButton = new TradeButton("trade", this);
        this.aWriteButton = new WriteButton("write", this);
        this.aTalkButton = new TalkButton("talk", this);
        
        this.aButtonArray.add(vBackButton);
        this.aButtonArray.add(vChargeButton);
        this.aButtonArray.add(this.aLookButton);
        this.aButtonArray.add(this.aUseButton);
        this.aButtonArray.add(this.aTakeButton);
        this.aButtonArray.add(this.aDropButton);
        this.aButtonArray.add(this.aInfoButton);
        this.aButtonArray.add(this.aTradeButton);
        this.aButtonArray.add(this.aWriteButton);
        this.aButtonArray.add(this.aTalkButton);
        
        this.aCommandsButtonArray.add(vBackButton);
        this.aCommandsButtonArray.add(vChargeButton);
        this.aCommandsButtonArray.add(this.aLookButton);
        this.aCommandsButtonArray.add(this.aUseButton);
        this.aCommandsButtonArray.add(this.aTakeButton);
        this.aCommandsButtonArray.add(this.aDropButton);
        this.aCommandsButtonArray.add(this.aInfoButton);
        this.aCommandsButtonArray.add(this.aTradeButton);
        this.aCommandsButtonArray.add(this.aWriteButton);
        this.aCommandsButtonArray.add(this.aTalkButton);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 0;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(vBackButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 1;
        pC.gridy = 0;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aTakeButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 2;
        pC.gridy = 0;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aUseButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 3;
        pC.gridy = 0;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aLookButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 4;
        pC.gridy = 0;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aTalkButton, pC);
        
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 1;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(vChargeButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 1;
        pC.gridy = 1;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aDropButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 2;
        pC.gridy = 1;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aTradeButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 3;
        pC.gridy = 1;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aInfoButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 4;
        pC.gridy = 1;
        pC.gridwidth = 1;
        pC.gridheight = 1;
        vCommandsPanel.add(this.aWriteButton, pC);
        
        pButtonPanel.add(vCommandsPanel, BorderLayout.NORTH);
    }
    
    private void setupReadmePanel(final JPanel pMainPanel, final JPanel pButtonPanel, final GridBagConstraints pC) {
        ReadmeButton vReadmeButton = new ReadmeButton();
        this.aButtonArray.add(vReadmeButton);
        
        pButtonPanel.add(vReadmeButton, BorderLayout.CENTER);
    }
    
    private void setupDirectionPanel(final JPanel pMainPanel, final JPanel pButtonPanel, final GridBagConstraints pC) {
        JPanel vDirectionPanel = new JPanel();
        
        vDirectionPanel.setMinimumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        vDirectionPanel.setMaximumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        vDirectionPanel.setPreferredSize(new Dimension((int)(3 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        vDirectionPanel.setBackground(this.aBGColor);
        vDirectionPanel.setLayout(new GridBagLayout());
        
        JButton vNButton = new DirectionButton("nord", this);
        JButton vSButton = new DirectionButton("sud", this);
        JButton vEButton = new DirectionButton("est", this);
        JButton vWButton = new DirectionButton("ouest", this);
        JButton vNEButton = new DirectionButton("nordest", this);
        JButton vNWButton = new DirectionButton("nordouest", this);
        JButton vSEButton = new DirectionButton("sudest", this);
        JButton vSWButton = new DirectionButton("sudouest", this);
        JButton vUButton = new DirectionButton("haut", this);
        JButton vDButton = new DirectionButton("bas", this);
        
        this.aButtonArray.add(vNButton);
        this.aButtonArray.add(vSButton);
        this.aButtonArray.add(vEButton);
        this.aButtonArray.add(vWButton);
        this.aButtonArray.add(vNEButton);
        this.aButtonArray.add(vNWButton);
        this.aButtonArray.add(vSEButton);
        this.aButtonArray.add(vSWButton);
        this.aButtonArray.add(vUButton);
        this.aButtonArray.add(vDButton);
        
        this.aDirectionButtonArray.add(vNButton);
        this.aDirectionButtonArray.add(vSButton);
        this.aDirectionButtonArray.add(vEButton);
        this.aDirectionButtonArray.add(vWButton);
        this.aDirectionButtonArray.add(vNEButton);
        this.aDirectionButtonArray.add(vNWButton);
        this.aDirectionButtonArray.add(vSEButton);
        this.aDirectionButtonArray.add(vSWButton);
        this.aDirectionButtonArray.add(vUButton);
        this.aDirectionButtonArray.add(vDButton);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 2;
        pC.gridy = 0;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vNButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 0;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vNWButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 4;
        pC.gridy = 0;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vNEButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 4;
        pC.gridy = 2;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vEButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 2;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vWButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 4;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vSWButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 2;
        pC.gridy = 4;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vSButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 4;
        pC.gridy = 4;
        pC.gridwidth = 2;
        pC.gridheight = 2;
        vDirectionPanel.add(vSEButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 2;
        pC.gridy = 2;
        pC.gridwidth = 2;
        pC.gridheight = 1;
        vDirectionPanel.add(vUButton, pC);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 2;
        pC.gridy = 3;
        pC.gridwidth = 2;
        pC.gridheight = 1;
        vDirectionPanel.add(vDButton, pC);
        
        pButtonPanel.add(vDirectionPanel, BorderLayout.SOUTH);
    }
    
    private void setupMapPanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        pPanel.setMinimumSize(new Dimension((int)(4 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(4 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(4 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        pPanel.setBackground(new Color(144, 128, 224));
        pPanel.setLayout(new BorderLayout());
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 10;
        pC.gridy = 10;
        pC.gridwidth = 4;
        pC.gridheight = 5;
        
        pPanel.setLayout(new BorderLayout());
        
        ImageIcon imageIcon = new ImageIcon("assets/images/WorldMap_WithoutNames.jpg"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(366, 215,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);
        
        JLabel vImg = new JLabel("");
        vImg.setIcon(imageIcon);
        
        pPanel.add(vImg, BorderLayout.CENTER);
        
        pMainPanel.add(pPanel, pC);
    }
    
    private void setupTextAreaPanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        pPanel.setMinimumSize(new Dimension((int)(7 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(7 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(7 * this.aWidthUnite), (int)(5 * this.aHeightUnite)));
        pPanel.setBackground(new Color(68, 182, 43));
        pPanel.setLayout(new BorderLayout());
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 0;
        pC.gridy = 10;
        pC.gridwidth = 7;
        pC.gridheight = 5;
        
        this.aLog = new JTextArea();
        this.aLog.setEditable(false);
        this.aLog.setBackground(this.aTextColor);
        this.aLog.setForeground(this.aBGColor);
        Module.setFontSize(this.aLog, 15);
        JScrollPane vScrollPane = new JScrollPane(this.aLog);
        pPanel.add(vScrollPane, BorderLayout.CENTER);
        
        pMainPanel.add(pPanel, pC);
    }
    
    private void setupOtherPanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        pPanel.setMinimumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(3 * this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(3 * this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(3 * this.aWidthUnite), (int)(3 * this.aHeightUnite)));
        pPanel.setBackground(new Color(245, 164, 19));
        pPanel.setLayout(new BorderLayout());
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 7;
        pC.gridy = 10;
        pC.gridwidth = 3;
        pC.gridheight = 3;
        
        ImageIcon imageIcon = new ImageIcon("assets/images/game_image.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(281, 144,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);
        
        JLabel vImg = new JLabel("");
        vImg.setIcon(imageIcon);
        
        pPanel.add(vImg, BorderLayout.CENTER);
        
        pMainPanel.add(pPanel, pC);
    }
    
    private void setupClearPanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        pPanel.setMinimumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(3 * this.aWidthUnite), (int)(this.aHeightUnite)));
        pPanel.setBackground(new Color(144, 116, 68));
        pPanel.setLayout(new BorderLayout());
        
        this.aClearButton = new ClearButton("effacer", this.aLog);
        this.aButtonArray.add(this.aClearButton);
        
        pPanel.add(this.aClearButton, BorderLayout.CENTER);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 7;
        pC.gridy = 13;
        pC.gridwidth = 3;
        pC.gridheight = 1;
        pMainPanel.add(pPanel, pC);
    }
    
    private void setupTextFieldPanel(final JPanel pMainPanel, final JPanel pPanel, final GridBagConstraints pC) {
        pPanel.setMinimumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(this.aHeightUnite)));
        pPanel.setMaximumSize(new Dimension((int)(3 * this.aWidthUnite), (int)(this.aHeightUnite)));
        pPanel.setPreferredSize(new Dimension((int)(3 * this.aWidthUnite), (int)(this.aHeightUnite)));
        pPanel.setBackground(new Color(255, 39, 15));
        pPanel.setLayout(new BorderLayout());
        
        this.aEntryField = new EntryField(20, this);
        
        this.aEntryField.setBackground(this.aTextColor);
        this.aEntryField.setForeground(this.aBGColor);
        
        pC.fill = GridBagConstraints.BOTH;
        pC.gridx = 7;
        pC.gridy = 14;
        pC.gridwidth = 3;
        pC.gridheight = 1;
        
        Module.setFontSize(this.aEntryField, 15);
        pPanel.add(this.aEntryField, BorderLayout.CENTER);
        
        pMainPanel.add(pPanel, pC);
    }
    
    
    private void addAllButtonsEventListeners() {
        UserInterface t = this;
        for(JButton vButton : this.aButtonArray) {
            
            vButton.setBorderPainted(false);
            vButton.setFocusPainted(false);
            Module.setFontSize(vButton, 14);
            vButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            if(vButton instanceof PopupButton) {
                continue;
            }
            vButton.setBackground(this.aBGColor);
            vButton.setForeground(this.aTextColor);
            vButton.addMouseListener(new MouseListener() {
                @Override
                public void mousePressed(MouseEvent e) {
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    
                    vButton.setBackground(t.aHoverBGColor);
                    vButton.setForeground(t.aHoverTextColor);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    vButton.setBackground(t.aBGColor);
                    vButton.setForeground(t.aTextColor);
                }
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    t.aLog.setCaretPosition(t.aLog.getDocument().getLength());
                    if(vButton.getText().equals("back") || vButton.getText().equals("charge")) {
                        t.aEngine.interpretCommand(vButton.getText());
                    }
                }
            });
        }
    }
    
    public void enable(final boolean pOnOff) {
        this.aEntryField.setEnabled(pOnOff);
        for(JButton vButton : this.aButtonArray) {
            vButton.setEnabled(pOnOff);
        }
    }
    
    public void print(final String pStr) {
        this.aLog.append(pStr);
    }
    
    public void println(final String pStr) {
        this.print(pStr + "\n");
    }
    
    public JFrame getFrame() {
        return this.aFrame;
    }
    
    public JTextArea getLog() {
        return this.aLog;
    }
    
    public EntryField getEntryField() {
        return this.aEntryField;
    }
    
    // Buttons getters
    public LookButton getLookButton() {
        return this.aLookButton;
    }
    
    public UseButton getUseButton() {
        return this.aUseButton;
    }
    
    public TakeButton getTakeButton() {
        return this.aTakeButton;
    }
    
    public DropButton getDropButton() {
        return this.aDropButton;
    }
    
    public InfoButton getInfoButton() {
        return this.aInfoButton;
    }
    
    public TradeButton getTradeButton() {
        return this.aTradeButton;
    }
    
    public WriteButton getWriteButton() {
        return this.aWriteButton;
    }
    
    public TalkButton getTalkButton() {
        return this.aTalkButton;
    }
        
    public GameEngine getGE() {
        return this.aEngine;
    }
} // UserInterface 