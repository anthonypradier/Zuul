
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author Anthony Pradier
 * @version 2.0, 02/08/2024
 */
public class Game {
    /**
     * Interface utilisateur
     */
    private UserInterface aGui;
    
    /**
     * Moteur de jeu
     */
    private GameEngine aEngine;

    /**
     * Creer le jeu, initialise l'interface et la map
     */
    public Game() {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface(this.aEngine);
        this.aEngine.setGUI(this.aGui);
    }
} // Game















