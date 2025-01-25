package InterfaceGraphic;

import Engine.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author pedro
 */

public class Screen {
    
    /** Image shown immediately after executing the code */
    private final Image startImage;
    
    /** Image displaying possible difficulty levels */
    private final Image difficultiesImage;
    
    /** Image showing a brief guide with instructions on how to play */
    private final Image guideImage;
    
    /** JavaFX GraphicsContext */
    private final GraphicsContext gc;
    
    /** Background image simulating space */
    private final Image backgroundImage;
    
    private final Engine engine;
 
    /** 
     * Constructor for the Screen class
     * @param gc the JavaFX GraphicsContext
     * @param engine the Engine class
     */
    public Screen(GraphicsContext gc, Engine engine){
        startImage = new Image("Images/start.png");
        difficultiesImage = new Image("Images/ difficulties.png");
        guideImage = new Image("Images/guide.png");
        backgroundImage = new Image("Images/background.png");
        this.gc = gc;
        this.engine = engine;
    }
    
    /**
     * This function is responsible for clearing the screen.
     */
    public void start(){
        gc.clearRect(0,0, 1400,900);
    }
    
    /**
     * This function is responsible for drawing the Menu screen.
     * @param g JavaFX GraphicsContext
     */
    public void drawStart(GraphicsContext g){
        g.drawImage(startImage,0,0);
    }
    
    /**
     * This function draws the difficulty options
     * @param g JavaFX GraphicsContext.
     */
    public void drawDifficulties(GraphicsContext g){
        g.drawImage(difficultiesImage,0,0);
    }
    
    /**
     * This function draws the help guide with the commands for the player.
     * @param g JavaFX GraphicsContext.
     */
    public void drawGuide(GraphicsContext g){
        g.drawImage(guideImage,0,0);
    }
    
    /**
     * This function is responsible for drawing the background image
     * that simulates space during the game.
     * It also draws, at the bottom of the screen, the selected mode
     * and the points obtained by the player at that moment.
     * @param g JavaFX GraphicsContext.
     * @param difficulty a String indicating the selected difficulty
     */
    public void drawBackground(GraphicsContext g, String difficulty){
        g.drawImage(backgroundImage,0,0);
        gc.fillText("MODE " + difficulty, 430, 860);
        gc.fillText("SCORE: " + Integer.toString(engine.getScore()), 1000, 860);
    }
    
    /**
     * This function shows a message when the cannon is hit by an alien's shot.
     */
    public void cannonHit(){
        gc.fillText("CANNON HIT", 200, 200 );
        gc.fillText("YOU STILL HAVE " + engine.ship.getLife() + " LIVES", 200, 300);
        gc.fillText("PRESS 'SPACE' TO CONTINUE", 200, 400);
    }
    
    /** 
     * This function shows a message when the player defeats all aliens and moves to the next level.
     */
    public void drawWonMessage(){
        gc.fillText("YOU WON.", 80, 100);
        gc.fillText("PRESS 'SPACE' TO PLAY THE NEXT LEVEL.", 80,200);
        gc.fillText("PRESS 'ESC' TO EXIT", 80, 300);
    }
    
    /**
     * Function that shows the remaining lives and the next level
     * when the cannon defeats all aliens.
     */
    public void msgNextLevel(){
        gc.fillText("REMAINING LIVES: " + engine.ship.getLife(), 80, 400);
        gc.fillText("NEXT LEVEL: " + (engine.getLevel() + 1), 80, 500);
    }

    /**
     * This function shows a message when the Aliens kill
     * the ship and pass the boundary.
     * If the user wants, they can press ESC and return to the menu.
     */
    public void drawLostMessage(){
        gc.fillText("DEFEAT!", 250, 300);
        gc.fillText("YOU COMPLETED " + (engine.getLevel() - 1) + " LEVELS", 250, 400);
        gc.fillText("AND SCORED " + engine.getScore() + " POINTS", 250, 500);
        gc.fillText("PRESS ESC TO RETURN TO THE MENU", 250, 600);
    }
}
