import java.util.HashMap;
import java.util.Random;

/**
 * Décrivez votre classe TransporterRoom ici.
 *
 * @author Anthony Pradier
 * @version 1.1, 04/03/2024
 */
public class TransporterRoom extends Room {
    /**
     * Pour la commande de test, permet de forcer le choix de la Room aléatoire
     * par la Room aAlea
     */
    private Room aAlea;
    
    /**
     * Constructeur naturel
     * @param pDescription Décrit la salle
     * @param pName Nom de la salle
     * @param pImageName Nom de l'image associee
     */
    public TransporterRoom(final String pDescription, final String pName, final String pImageName) {
        super(pDescription, pName, pImageName);
    }
    
    /**
     * Change la valeur de aAlea
     * @param pRoom une Room
     */
    public void setAlea(final Room pRoom) {
        this.aAlea = pRoom;
    }
    
    /**
     * Renvoie une Room aléatoire parmis celles possibles
     * @return Une Room aléatoire
     */
    public Room getRandomExit() {
        return this.findRandomRoom();
    }
    
    /**
     * Renvoie une Room aléatoire parmis celles possibles
     * @return Une Room aléatoire
     */
    public Room findRandomRoom() {
        Room vRoom;
        if(this.aAlea != null) {
            vRoom = this.aAlea;
            for(String vRN : GameEngine.GAME_ROOM.keySet()) {
                if(GameEngine.GAME_ROOM.get(vRN) instanceof TransporterRoom) {
                    ((TransporterRoom)GameEngine.GAME_ROOM.get(vRN)).setAlea(null);
                }
            }
        } else {
            Object[] vArrayRooms = GameEngine.GAME_ROOM.values().toArray();
            Random vRdm = new Random();
            do {
                vRoom = (Room)vArrayRooms[vRdm.nextInt(vArrayRooms.length)];
            }
            while(vRoom.getName().equals("Ekarte") || vRoom.getName().equals("Observatory") || vRoom.getName().equals("Ctrigall"));
        }
        return vRoom;
    }
}
