import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * Classe constituant le/les joueurs
 *
 * @author Anthony Pradier
 * @version 1.3, 03/10/2024
 */
public class MC extends Player {
    /**
     * Moteur de jeu
     */
    private GameEngine GE;
    
    private int aExp;
    
    /**
     * Constructeur naturel de MC, sous classe de Player
     * @param pName Le nom du MC
     * @param pCurrentRoom La Room actuelle du MC (Room de départ lors de la création)
     * @param pMaxWeight Le poids max portable par le MC
     * @param pGE Référence à la classe GameEngine pour pouvoir afficher du texte
     */
    public MC(final String pName, final String pLastName, final Room pCurrentRoom, final double pMaxWeight, final GameEngine pGE) {
        super(pName, pLastName, pCurrentRoom, pMaxWeight);
        this.GE = pGE;
        this.aExp = 0;
        this.GE.GAME_PLAYER.put(this.getName().toLowerCase(), this);
    }
    
    /**
     * Renvoie l'experience du MC
     * @return l'experience du MC
     */
    public int getExp() {
        return this.aExp;
    }
    
    /**
     * Affiche les commandes utilisables
     * @return si l'action est effectuée
     */
    public boolean printHelp() {
        this.GE.help();
        return false;
    } // printHelp()
    
    /**
     * Affiche les infos du joueur, Nom, Room actuelle, Inventaire, poids actuel et poids max 
     * du joueur s'il n'y a pas de second mot, ou d'un pnj si le second mot est le nom d'un pnj
     * @param pCommand Une commande
     * @return si l'action est effectuée
     */
    public boolean info(final Command pCommand) {
        // Ajouter les infos d'un pnj si second mot
        if(pCommand.hasSecondWord()) {
            String pName = pCommand.getSecondWord();
            Player pPlayer = this.GE.GAME_PLAYER.get(pName.toLowerCase());
            if(pPlayer == null) {
                this.GE.pnjNotExists();
            } else {
                this.GE.playerInfo(pPlayer);
            }
        } else {
            this.GE.playerInfo(this);
        }
        return false;
    } // info()
    
    /** 
     * Essaie de changer de salle pour celle dans la commande
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean goRoom(final Command pCommand) {
        if (!pCommand.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            this.GE.goWhere();
            return false;
        }
        
        String vDirection = pCommand.getSecondWord();

        // Try to leave current room.
        Room vNextRoom = this.getCurrentRoom().getExit(vDirection);

        if (vNextRoom == null)
            this.GE.cannotGoHere();
        else {
            if(this.getCurrentRoom().isTransporterRoom()) {
                TransporterRoom vTR = (TransporterRoom)this.getCurrentRoom();
                vNextRoom = vTR.getRandomExit();
                // System.out.println("Room aléatoire : " + vNextRoom.getName());
            }
            this.getPreviousRooms().push(this.getCurrentRoom());
            this.setCurrentRoom(vNextRoom);
            this.GE.longDescription(this.getCurrentRoom());
            // System.out.println(this.getCurrentRoom().isTransporterRoom());
            if (this.getCurrentRoom().getImageName() != null)
                this.GE.roomImage(getCurrentRoom());
            // else
                // this.aGui.println("Pas d'image pour l'instant");
        }
        return true;
    } // goRoom()
    
    /**
     * Permet au joueur d'afficher les informations completes de la salle actuelle
     * Et en cas de second mot, d'afficher les informations sur l'Item correspondant si il existe
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean look(final Command pCommand) {
        if(pCommand.hasSecondWord()) {
            String vSWord = pCommand.getSecondWord().toLowerCase();
            boolean vIsItem = CommandWords.isItem(vSWord);
            if(vIsItem) {
                Item vItem = GE.GAME_ITEM.get(vSWord);
                boolean vIsInCurrentRoom = vItem.isInRoom(this.getCurrentRoom());
                if(vIsInCurrentRoom) {
                    this.GE.itemDescription(vItem);
                } else {
                    this.GE.itemNotFound();
                }
            } else {
                this.GE.itemNotExist();
            }
        } else {
            this.GE.longDescription(this.getCurrentRoom());    
        }
        return false;
    } // look()
    
    /**
     * Permet au joueur de consommer de la nourriture présente dans l'inventaire
     * et lui octroyer les effets associés
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean eat(final Command pCommand) {
        if(!pCommand.hasSecondWord()) {
            this.GE.cannotEat();
            return false;
        }
        String vIName = pCommand.getSecondWord().toLowerCase();
        ItemList vItemList = this.getItemList();
        Item vItem = vItemList.getItems().get(vIName);
        if(!CommandWords.isItem(vIName)) {
            this.GE.itemNotExist();
            return false;
        } else if(vItem == null) {
            this.GE.itemNotInInventory();
        } else if(!vItem.isEatable()) {
            this.GE.itemNotEatable();
        } else {
            if(vIName.equals("cookie")) {
                this.setMaxWeight(this.getMaxWeight() * 2);
                this.GE.addMaxActions(20);
                this.GE.itemEaten(vItem.getName());
                vItemList.removeItem(vItem);
            } // else if...
            this.removeWeight(vItem.getWeight());
        } // else if
        return true;
    } // eat()
    
    /**
     * Commande pour revenir en arrière, faisable jusqu'à arriver à la salle de départ
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean back(final Command pCommand) {
        if(pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        if(!this.getPreviousRooms().empty()) {
            Room vPreviousRoom = (Room)this.getPreviousRooms().pop();
            if(this.getCurrentRoom().isExit(vPreviousRoom)) {
                this.GE.goBack();
                this.setCurrentRoom(vPreviousRoom);
                this.GE.longDescription(this.getCurrentRoom());
            } else {
                this.GE.cannotGoBack();
            }
            return true;
        }
        this.GE.cannotGoBack();
        return false;
    } // back()
    
    /**
     * Permet de récupérer un Item et de le mettre dans son inventaire, si celui-ci existe, si
     * il se trouve dans la même Room que le joueur et si la charge maximale n'est pas dépassée
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean take(final Command pCommand) {
        if(!pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        String vItemName = pCommand.getSecondWord().toLowerCase();
        if(CommandWords.isItem(vItemName)) {
            Item vItem = this.getCurrentRoom().getItemList().getItems().get(vItemName);
            if(vItem == null) {
                this.GE.itemNotInRoom();
            } else if(vItem.isInRoom(this.getCurrentRoom())) {
                if(this.getCurrentWeight()  + vItem.getWeight() > this.getMaxWeight()) {
                    this.GE.fullInventory();
                } else {
                    this.GE.itemCollected(vItem.getName());
                    this.getItemList().addItem(vItem);
                    this.addWeight(vItem.getWeight());
                    this.getCurrentRoom().getItemList().removeItem(vItem);
                }
            }
            return true;
        }
        this.GE.itemNotExist();
        return false;
    } // take()
    
    /**
     * Enlève l'Item de l'inventaire pour le placer dans la HashMap
     * de la Room actuelle qui stock les Items
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean drop(final Command pCommand) {
        if(!pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        if(this.getItemList().empty()) {
            this.GE.emptyInventory();
            return true;
        }
        String vItemName = pCommand.getSecondWord().toLowerCase();
        if(CommandWords.isItem(vItemName)) {
            Item vItem = this.getItemList().getItems().get(vItemName);
            if(vItem == null) {
                this.GE.itemNotInInventory();
            } else {
                this.getCurrentRoom().getItemList().addItem(vItem);
                this.removeWeight(vItem.getWeight());
                // Condition si l'objet est stackable, retirer une occurence
                this.getItemList().getItems().remove(vItemName);
                this.GE.itemDroped(vItem.getName());
            }
            return true;
        }
        this.GE.itemNotExist();
        return false;
    } // drop()
    
    /**
     * Commande permettant de charger le Beamer
     * @param pCommand Une commande
     * @return si l'action est effectuée
     */
    public boolean charge(final Command pCommand) {
        if(pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        Beamer vBeamer = (Beamer)this.getItemList().getItems().get("beamer");
        if(vBeamer != null) {
            vBeamer.chargeBeamer(this.getCurrentRoom());
            this.GE.beamerCharged();
            return true;
        }
        this.GE.beamerNotInInventory();
        return true;
    }
    
    /**
     * Commande permettant d'utiliser les Items
     * @param pCommand Une commande
     * @return si l'action est effectuée
     */
    public boolean use(final Command pCommand) {
        if(!pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        String vIName = pCommand.getSecondWord().toLowerCase();
        Item vItem = this.getItemList().getItems().get(vIName);
        if(CommandWords.isItem(vIName)) {
            if(this.getItemList().getItems().get(vIName) != null) {
                if(vIName.equals("beamer")) {
                    Beamer vBeamer = (Beamer)vItem;
                    if(vBeamer.isCharged()) {
                        this.getPreviousRooms().push(this.getCurrentRoom());
                        this.setCurrentRoom(vBeamer.getChargedRoom());
                    } else {
                        this.GE.beamerNotCharged();
                    }
                } else if(vIName.equals("dactylotype")) {
                } else if(vIName.startsWith("lettre")) {
                    UseButton.readLetter(this.GE, (Letter)vItem);
                }
                this.GE.usedItem(vItem);
                return true;
            }
            this.GE.itemNotInInventory();
            return true;
        }
        this.GE.itemNotExist();
        return false;
    }
    
    public boolean talk(final Command pCommand) {
        if(!pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        String vPNJName = pCommand.getSecondWord();
        PNJ vPNJ = this.GE.GAME_PNJ.get(vPNJName.toLowerCase());
        if(vPNJ == null) {
            this.GE.pnjNotExists();
        } else if(!vPNJ.getCurrentRoom().equals(this.getCurrentRoom())) {
            this.GE.pnjNotHere();
        } else {
            TalkButton.talkTo(this, vPNJ, this.GE.getUI(), this.GE);
            return true;
        }
        return false;
    }
    
    public boolean write(final Command pCommand) {
        if(pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        
        WriteButton.createDialog(this, this.GE.getUI(), this.GE);
        return true;
    }
    
    public boolean trade(final Command pCommand) {
        if(pCommand.hasSecondWord()) {
            this.GE.noSens();
            return false;
        }
        
        if(GameEngine.GAME_PNJ.get(pCommand.getSecondWord().toLowerCase()) == null) {
            return false;
        }
        PNJ vPNJ = GameEngine.GAME_PNJ.get(pCommand.getSecondWord().toLowerCase());
        TradeWindow vTW = new TradeWindow(this, vPNJ, this.GE.getUI(), this.GE);
        return true;
    }
    
    // private boolean checkWriteCondition() {
        // HashMap<String, Item> vItems = this.getItemList().getItems();
        // return vItems.containsValue(this.GE.GAME_ITEM.get("feuille")) && vItems.containsValue(this.GE.GAME_ITEM.get("encre")) && (vItems.containsValue(this.GE.GAME_ITEM.get("plume")) || vItems.containsValue(this.GE.GAME_ITEM.get("dactylotype")));
    // }
    
    // TEST COMMANDS
    /**
     * Commande de test qui lit un fichier texte
     * @param pCommand La commande
     * @return si l'action est effectuée
     */
    public boolean test(final Command pCommand) {
        if(!pCommand.hasSecondWord())
            this.GE.cannotTest();
        else {
            String vFileName = pCommand.getSecondWord();
            Scanner vSc;
            try {
                if(vFileName.endsWith(".txt")) {
                    vSc = new Scanner(new File(vFileName));
                } else {
                    vSc = new Scanner(new File(vFileName + ".txt"));
                }
                this.GE.fileFound();
                while(vSc.hasNextLine()) {
                    String vLine = vSc.nextLine();
                    String[] vLineTab = vLine.split(" ");
                    if(vLineTab[0].equals("roomIs")) {
                        this.roomIs(vLineTab[1]);
                    } else if(vLineTab[0].equals("roomHas")) {
                        this.roomHas(vLineTab[1]);
                    } else if(vLineTab[0].equals("playerHas")) {
                        this.playerHas(vLineTab[1]);
                    } else if(vLineTab[0].equals("alea")) {
                        this.alea(vLineTab[1]);
                    } else {
                        this.GE.interpretCommand(vLine);
                    }
                }
                this.GE.noMoreCommands();
            } catch(Exception pE) {
                this.GE.fileNotFound();
            } // try catch
        } // if else
        return false;
    } // test()
    
    /**
     * Regarde si la Room en parametre est la meme que la Room actuelle
     * @param pSecondWord La commande
     */
    private void roomIs(final String pSecondWord) {
        Room vRoom = this.GE.GAME_ROOM.get(pSecondWord.toLowerCase());
        if(this.getCurrentRoom().equals(vRoom))
            this.GE.printTrue();
        else
            this.GE.printFalse();
    } // roomIs()
    
    /**
     * Regarde si la Room actuelle possède l'Item dont le nom est en paramètre
     * @param pSecondWord La commande
     */
    private void roomHas(final String pSecondWord) {
        // Item vItem = this.GE.GAME_ITEM.get(pSecondWord.toLowerCase());
        // if(vItem.isInRoom(this.getCurrentRoom()))
        if(this.getCurrentRoom().getItemList().getItems().get(pSecondWord.toLowerCase()) != null)
            this.GE.printTrue();
        else
            this.GE.printFalse();
    } // roomHas()
    
    /**
     * Regarde si le MC possède l'Item dont le nom est passé en paramètre
     * @param pSecondWord La commande
     */
    private void playerHas(final String pSecondWord) {
        if(this.getItemList().getItems().get(pSecondWord.toLowerCase()) != null)
            this.GE.printTrue();
        else
            this.GE.printFalse();
    } // playerHas()
    
    /**
     * Force la Room aléatoire à la room en paramètre
     * @param pSecondWord le second mot de la commande
     */
    public void alea(final String pSecondWord) {
        Room vRoom = (Room)GameEngine.GAME_ROOM.get(pSecondWord);
        for(String vRN : this.GE.GAME_ROOM.keySet()) {
            if(this.GE.GAME_ROOM.get(vRN) instanceof TransporterRoom) {
                ((TransporterRoom)this.GE.GAME_ROOM.get(vRN)).setAlea(vRoom);
            }
        }
    }
    
    public void checkQuestAchieved() {
        if(this.getQuestList().getQuests().isEmpty()) {
            return;
        }
        QuestList vQL = this.getQuestList();
        Quest vQuest;
        String vGoal;
        
        Item vItemQuest;
        Letter vLetterQuest;
        PNJ vPNJQuest;
        Room vRoomQuest;
        for(String vQName : vQL.getQuests().keySet()) {
            vQuest = vQL.getQuests().get(vQName);
            vGoal = vQuest.getGoal();
            
            vItemQuest = vQuest.getItemQuest();
            vLetterQuest = vQuest.getLetterQuest();
            vPNJQuest = vQuest.getPNJQuest();
            vRoomQuest = vQuest.getRoomQuest();
            
            if(vGoal.equals("finditem")) {
                if(this.getItemList().getItems().containsValue(vItemQuest)) {
                    System.out.println("Quête " + vQuest.getName() + " terminée\nRécompenses : " + vQuest.getRewardsString());
                    this.getWallet().addMoney(vQuest.getReward());
                    this.getQuestList().removeQuest(vQuest);
                }
            } else if(vGoal.equals("gosomewhere")) {
                if(this.getCurrentRoom().equals(vRoomQuest)) {
                    System.out.println("Quête " + vQuest.getName() + " terminée\nRécompenses : " + vQuest.getRewardsString());
                    this.getWallet().addMoney(vQuest.getReward());
                    this.getQuestList().removeQuest(vQuest);
                }
            } else if(vGoal.equals("giveitem")) {
                if(vPNJQuest.getItemList().getItems().containsValue(vItemQuest)) {
                    System.out.println("Quête " + vQuest.getName() + " terminée\nRécompenses : " + vQuest.getRewardsString());
                    this.getWallet().addMoney(vQuest.getReward());
                    this.getQuestList().removeQuest(vQuest);
                }
            }
        }
    }
}
