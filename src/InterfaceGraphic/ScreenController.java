package InterfaceGraphic;

import Engine.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @author pedro
 */
public class ScreenController extends Application {
    
    /** Helper attribute to track if the game has started (i.e., left the menu). */
    public boolean started = false;
    
    /** Helper attribute to track if the user wants to see the guide. */
    public boolean guide = false;
    
    /** Helper attribute to track if the user wants to see the difficulties. */
    public boolean difficulties = false;
    
    /** Helper attribute to track if the user paused the game. */
    public boolean paused = false;
    
    /** Helper attribute to track if the user has defeated all aliens. */
    public boolean won = false;
    
    /** Helper attribute for tracking player defeat. */
    public boolean lost = false;
    
    /** Helper attribute to track if the cannon has been hit by a shot. */
    public boolean hit = false;
    
    /** Helper attribute for resetting the next level. */
    public boolean reset = false;
    
    /** Attribute to define the difficulty level: Normal or Hard. */
    private String difficulty;
    
    /** Screen width (experimentally determined). */
    public final int width = 1400;
    
    /** Screen height (experimentally determined). */
    public final int height = 900;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        /**
         * Create the game.
         */
        Engine engine = new Engine(gc);
        engine.startGame();
        
        /**
         * Define the font to be used by the gc.fillText() function.
         */
        Font theFont = Font.font("SansSerif", FontWeight.BOLD, 50);
        gc.setFill(Color.PINK);
        gc.setFont(theFont);
        gc.setStroke(Color.BLACK);
        
   
        new AnimationTimer(){
            @Override
            public void handle(long time){

                readKeys(scene, engine);
                gameLoop(gc, engine);
  
            }            
        }.start();
                
        stage.setTitle("SPACE INVADERS");
        stage.setScene(scene);
        stage.getIcons().add(new Image("Images/cannon.png"));
        stage.setResizable(false);
        stage.show();
        
    }
    
    /**
     * Function to toggle the paused boolean.
     */
    public void pause(){
        paused = !paused;
    }
    
    /**
     * Function to recognize the keys pressed by the player.
     * @param scene
     * @param engine 
     */
    public void readKeys(Scene scene, Engine engine){
        
        scene.setOnKeyPressed((KeyEvent e) -> {
            switch(e.getCode()){
                
                /**
                 * Actions related to the Menu
                 */
                case ENTER:     
                    difficulties = true;
                    break;
                case G:
                    if(!started){
                        guide = true;
                        difficulties = false;
                    }
                    break;
                case ESCAPE:                 
                    if(!started){
                        guide = false;
                        difficulties = false;
                    }
                    
                    /** If the user wants to go back to the menu after losing. */
                    else if(started && lost){
                        started = false;
                        difficulties = false;
                        lost = false;
                        hit = false;
                        engine.deleteElements();
                        engine.ship.reset();
                        engine.createAliens(1);
                        engine.createBarrier();
                    }
                    break;
                case DIGIT1:
                    if(difficulties && !started){
                        started = true;
                        difficulty = "NORMAL";
                    }
                    break;
                case DIGIT2:
                    if(difficulties && !started){
                        started = true;
                        difficulty = "HARD";
                    }
                    break;
                    
                /**
                 * Actions during the game loop
                 */
                case LEFT:
                    engine.moveCannon(0);
                    break;
                case RIGHT:
                    engine.moveCannon(1);
                    break;
                case SPACE:
                    if(started && !paused){
                        engine.createCannonShot();
                        hit = false;
                        if(won){
                            reset = true;
                        }
                    }
                    break;
                case P:
                    if(started){
                        pause();
                        break;
                    }
            }
        });
    }
     
    /**
     * Main method that defines the game loop.
     * It controls what should appear on the screen.
     * @param gc GraphicsContext of JavaFx
     * @param engine Engine object
     */
    public void gameLoop(GraphicsContext gc, Engine engine){
        
        /**
         * If the game hasn't started, draw the menu screen.
         */
        if(!started){
            engine.Screen.drawStart(gc);
            
            /** If the user requested the guide, call the DrawGuide() function. */
            if(guide){
                engine.Screen.drawGuide(gc);
            }
            
            /** If the user pressed the play button, show the available difficulties. */
            else if(difficulties){
                engine.Screen.drawDifficulties(gc);
            }
        } 
        
        /**
         * If the game is actually running.
         */
        else{
            
            /** Check if the game is paused. */
            if(paused){
                gc.fillText("CONTINUE (P)", 500, 40);
            }
            
            
            /** Check if the cannon has been hit and if it still has lives. */
            else if(hit && engine.ship.getLife() > 0){
                engine.Screen.cannonHit();
            }
            
            /** Check if the user defeated all the aliens. */
            else if(engine.aliens.size() <= 0){
                engine.Screen.msgNextLevel();
                won = true;
                
                /** If the user wants to play the next level, reset the game. */
                if(reset){
                    engine.setLevel();
                    engine.createAliens(engine.getLevel());
                    
                    won = false;
                    reset = false;
                }    
            }
            
            /**
             * This is the main game loop
             * The game will continue as long as the player has lives and there are aliens remaining.
             */
            else if(engine.ship.getLife() > 0 && engine.aliens.size() >  0 && difficulties && !lost){

                engine.Screen.drawBackground(gc, difficulty);
                engine.drawElements(gc);
                
                engine.createAlienShot();
                engine.createSpecialAlien();
                
                lost = engine.moveElements(difficulty);
                hit = engine.cannonShotCollision();
                
            }
            
            /** If the user defeats all aliens, the win message appears on the screen. */
            else if(won){
                engine.Screen.drawWonMessage();
            }
            
            /** Otherwise, the user lost. */
            else{
                engine.Screen.drawLostMessage();
                lost = true;
            }       
        }
    }
}
