package Engine;
import Elementos.*;
import InterfaceGraphic.*;

import java.util.Random;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Class responsible for the game's functionality. */
public class Engine{  
    
    /** Constructor for the Engine Class
     *@param gc - of type GraphicsContext from JavaFX
     */
    public Engine(GraphicsContext gc){
        this.gc = gc;
        phase = 1;
        score = 0;
    }
    
    /** GraphicsContext from JavaFX */
    GraphicsContext gc;
    
    /** Object of the Screen class responsible for creating the Display. */
    public Screen screen;
    
    /**
    * Player's score.
    */
    private int score;
    
    /**
    * The phase the player is in.
    */
    private int phase;
    
    /**
    * Sequence of Vectors to be used.
    */
    private final ArrayList<Shot> cannonShots = new ArrayList();
    private final ArrayList<Shot> alienShots = new ArrayList();
    private final ArrayList<Barrier> barriers = new ArrayList();
    public final ArrayList<Alien> aliens = new ArrayList();
    public final ArrayList<Alien> specialAlien = new ArrayList();
    
    /** Object of the Music class responsible for sound effects. */
    private Music music;
    
    /** Object of the Cannon class representing the Player. */
    public Cannon ship;
    
    /** Alien and Cannon shots will be fired at certain intervals. */
    private long cannonShotTimer;
    private long alienShotTimer;
    
    /** Creates the vector of Aliens and fills it. 
     @param phase - the game's phase is required, as the higher the phase,
     * the lower the Aliens start.
     */
    public void createAliens(int phase){
        
        // Defining the image of the 3 types of Aliens
        Image alien1 = new Image("Images/alien1.png", 40, 40, false, false);
        Image alien2 = new Image("Images/alien2.png", 40, 40, false, false);
        Image alien3 = new Image("Images/alien3.png", 40, 40, false, false);
        Image img = alien1;
        int i, j;
        
        // Variable to check which type of Alien should be inserted
        int size = 0;
        for(j = 50; j < 300; j+=50){
            for(i = 50; i < 600; i+=50){
            
                if(size == 11){
                    img = alien2;
                }
                else if(size == 33){
                    img = alien3;
                }
                
                Alien a = new Alien(i , j + (phase-1)* 100, 0, 40, 10, 3, img);
                aliens.add(a);
                
                size ++;
            }
        }
    }
    
    /**
    * Creates the vector of Barriers and fills it with 4 barriers.
    */    

    public void createBarrier(){
        
        // Initially uses the image of the undamaged barrier
        Image barrierImage = new Image("Images/barrier.png", 150, 150, false, false);
        Barrier b1 = new Barrier(100, 550, 110, 90, 15, barrierImage);
        Barrier b2 = new Barrier(450, 550, 110, 90, 15, barrierImage);
        Barrier b3 = new Barrier(800, 550, 110, 90, 15, barrierImage);
        Barrier b4 = new Barrier(1150, 550, 110, 90, 15, barrierImage);
        barriers.add(b1);
        barriers.add(b2);
        barriers.add(b3);
        barriers.add(b4);
    }

    /**
    * Creates the Cannon object.
    */
    public void createCannon(){
        Image cannonImage = new Image("Images/cannon.png", 40, 40, false, false);
        ship = new Cannon(200,700, 40, 35, 3, cannonImage);
    }
    
    /**
    * Creates the Cannon's Shots as requested by the Player.
    */
    public void createCannonShot(){
        
        // To make it harder, the Player can shoot every 1.5 seconds
        if(System.currentTimeMillis() - cannonShotTimer > 1500){
            Image shotImage = new Image("Images/cannon_shot.png", 40, 40, false, false);
            Shot shot = new Shot(ship.pos_x, ship.pos_y - 1 , 5, 15, 1, shotImage);
            cannonShots.add(shot);
            music = new Music();
            music.playShotSound();
            
            cannonShotTimer = System.currentTimeMillis();
        }
    }
    
    /**
    * Creates the game's screen.
    */
    public void createScreen(){
        
        screen = new Screen(gc, this);
        screen.initialize();  
    }
    
    /**
    * Creates the Alien's shots randomly.
    * The shots happen more frequently as the player advances to higher phases.
    * In addition to random shots, a shot is created from an alien
    * that is in the column closest to the cannon.
    */
    public void createAlienShot(){
        
        Alien auxAlien;
        Random generator = new Random();
        
        int cannonColumn, alienColumn;
        int alienIndex = -1;
        
        int i;
        
        /** Generate a random value. */
        int value = (int) Math.floor(Math.random() * 300) + 1500; 
        
        Image shotImg = new Image("Images/alienShot.png", 35, 45, false, false);
        
        /** Shots happen at certain intervals. */
        if(System.currentTimeMillis() - alienShotTimer > value)
        {
            cannonColumn = ship.getPos_x();
            
            /** Looks for an alien in the column closest to the Cannon. */
            for(i = 0; i < aliens.size(); i++){
                alienColumn = aliens.get(i).getPos_x();
                if(alienColumn >= cannonColumn && alienColumn <= cannonColumn + 4){
                    alienIndex = i;
                    break;
                }
            }
            
            /** If an alien is found, create a shot. */
            if(alienIndex != -1){
                alienShots.add(new Shot(aliens.get(alienIndex).getPos_x(), 
                            aliens.get(alienIndex).getPos_y() + 2 , 30, 30, 1, shotImg));
            }
 
            
            /** Creates random shots. */
            for(i = 0; i < phase ; i++){
                
                int j = generator.nextInt(aliens.size());
                auxAlien = aliens.get(j);
                alienShots.add(new Shot(auxAlien.getPos_x(), 
                            auxAlien.getPos_y() + 2 , 30, 30, 1, shotImg));
            }
            
            /** Reset the alien shot timer. */
            alienShotTimer = System.currentTimeMillis();
        }
    }

        
    /**
     * Creates the vector of Red Aliens, which
     * appear periodically at the top of the screen.
     */
    public void createSpecialAlien(){
        
        Image image = new Image("Images/special_alien.png", 60, 60, false, false);    
        
        int value = (int) Math.floor(Math.random() * 305) + 1500; 
        if(System.currentTimeMillis() - alienShotTimer > value && specialAlien.isEmpty())
        {
            
            Alien a = new Alien(1300, 10, 1, 50, 50, 1, image); 
            specialAlien.add(a);
            
            alienShotTimer = System.currentTimeMillis();
        }
    }

    /**
     * Method that moves the Aliens
     * @param difficulty - it's important to know the game difficulty
     *     in Hard mode, aliens move faster than in Normal mode
     * @return end - indicates that the aliens reached the bottom border and the game should end.
     */
    public boolean moveAliens(String difficulty){
        
        boolean reverseDirection = false;
        boolean end = false;
        
        int i;
        for(i = 0; i < aliens.size(); i++){
            
            /**
            * If an invader is about to exit
            * the border, the direction of movement should be reversed.
            */
            if(aliens.get(i).getPos_x() <= 5 && aliens.get(i).getDirection() == 1){
                reverseDirection = true;
            }
            if(aliens.get(i).getPos_x() >= 1340 && aliens.get(i).getDirection() == 0){
                reverseDirection = true;
            }
            
            /**
            * If the aliens pass the cannon level,
            *   the game ends.
            */
            if(aliens.get(i).getPos_y() >= 650){
                end = true;
            }
        }
        
        /** Actually moving the aliens using the move method. */
        for (i = 0; i < aliens.size(); i++){
            
            /** If it's the last alien, it should move faster. */
            if(aliens.size() == 1){
                aliens.get(i).move(reverseDirection, true, difficulty);
            }
            else{
                aliens.get(i).move(reverseDirection, false, difficulty);
            }
        }
        
        return end;
        
    }

    /**
    * Movement of the special Alien.
    */
    public void moveSpecialAlien(){
        int i;
        int pos_x;
        ArrayList<Alien> removeAlien = new ArrayList<>();
        Alien tempAlien;
        for(i = 0; i < specialAlien.size(); i++){
            tempAlien =  specialAlien.get(i);
            
            /** If the alien reaches the screen border, it should be removed. */
            if(tempAlien.getPos_x() <= 2){
                removeAlien.add(tempAlien);
            }
            
            /** Actually move the alien, -4 was obtained experimentally. */
            else{
                pos_x = tempAlien.getPos_x();
                tempAlien.setPosition(pos_x - 4, 10);
            }
        }
        
        /** Remove the aliens that reached the border. */
        if(!removeAlien.isEmpty()){
            specialAlien.removeAll(removeAlien);
            removeAlien.clear();
        } 
    }

    /**
    * This method checks, first, if it's possible
    * to move the cannon, i.e., if it has
    * not yet reached the border.
    * @param direction - it is necessary to know if the cannon is moving to the right
    *   or to the left.
    */
    public void moveCannon(int direction){
        
        //Move to the right
        if(cannon.getPos_x() >= 2 && direction == 0){
            cannon.addPosition(-9, 0);
        }
        
        //Move to the left
        if(cannon.getPos_x() <= 1340  && direction == 1){
            cannon.addPosition(9, 0);
        }
    }


    /**
    *   Alters the positions of the cannon's shots 
    *   Verifies if the shot is exceeding the screen limit to
    *   remove it
    *   Also checks for collisions between shots and aliens
    *   If a collision occurs, both the shot and the alien are removed.
    */
    public void moveCannonShots(){

        CannonShot tempShot;
        Alien tempAlien;
        ArrayList<CannonShot> removeShot = new ArrayList<>();
        ArrayList<Alien> removeAlien = new ArrayList<>();

        int i,j;
        
        /**
        * Iterates over the shots and checks if they can move.
        */
        for(i = 0; i < cannonShots.size(); i++){
            tempShot = cannonShots.get(i);
            if(tempShot.getPos_y() >= 0){
            tempShot.addPosition(0, -3);
                for(j = 0; j < aliens.size(); j++){
                tempAlien = aliens.get(j);
                if(tempAlien.intersects(tempShot)){
                    removeShot.add(tempShot);
                    removeAlien.add(tempAlien);  
                    
                    /** If the shot hits a regular alien, 10 points are awarded. */
                    score += 10;
                }
                }
                
                
                for(j = 0; j < specialAlien.size(); j++){
                tempAlien = specialAlien.get(j);
                if(tempAlien.intersects(tempShot)){
                    
                    removeShot.add(tempShot);
                    removeAlien.add(tempAlien);  
                    
                    /** If the shot hits the special alien, 100 points are awarded. */
                    score += 100;
                }
                }
            }
            
            /** If the shot is leaving the screen from the top. */
            else{
                removeShot.add(tempShot); 
            }
        }
        
        /**
        * Removes the aliens and the shots involved
        * in a collision.
        */
        if(!removeShot.isEmpty()){
            cannonShots.removeAll(removeShot);
            removeShot.clear();
        }
        if(!removeAlien.isEmpty()){
            aliens.removeAll(removeAlien);
            specialAlien.removeAll(removeAlien);
            removeAlien.clear();
        }        
    }

    /**
     * Method responsible for moving the Alien's shots
     * @param difficulty - indicates the game difficulty.
     * If the difficulty is Hard, the movement should be
     *    faster than in Normal mode.
     */
    public void moveAlienShots(String difficulty){

        AlienShot tempShot;
        ArrayList<AlienShot> removeShot = new ArrayList<>();
        
        int i,j;
        for(i = 0; i < alienShots.size(); i++){
            tempShot = alienShots.get(i);
            if(tempShot.getPos_y() <= 740){
            if("NORMAL".equals(difficulty)){
                    tempShot.addPosition(0, 2);
            }
            else{
                    tempShot.addPosition(0, 3);
            }
            }
            else{
                removeShot.add(tempShot);
            }
        }
        if(!removeShot.isEmpty()){
            alienShots.removeAll(removeShot);
            removeShot.clear();
        }
    }

    /** 
     * Method that detects collisions between the shots of the Aliens and the Cannon
     * In this case, both shots are removed.
     */
    public void collisionShotShot(){
        CannonShot cannonShot;
        AlienShot alienShot;
        ArrayList<CannonShot> cannonShotsToRemove = new ArrayList<>();
        ArrayList<AlienShot> alienShotsToRemove = new ArrayList<>();
        int i, j;
        
        for(i = 0; i < cannonShots.size(); i++){
            cannonShot = cannonShots.get(i);
            for(j = 0; j < alienShots.size(); j++){
                alienShot = alienShots.get(j);
                if(cannonShot.intersects(alienShot)){
                    cannonShotsToRemove.add(cannonShot);
                    alienShotsToRemove.add(alienShot);
                }
            }
        }
        if(!cannonShotsToRemove.isEmpty()){
            cannonShots.removeAll(cannonShotsToRemove);
            cannonShotsToRemove.clear();
        }
        if(!alienShotsToRemove.isEmpty()){
            alienShots.removeAll(alienShotsToRemove);
            alienShotsToRemove.clear();
        }
    }

    /** Method that processes the collision between Cannon and Alien Shots with Barriers
     In this case, the Shot is removed, and the barrier loses a life.
    */
    public void collisionShotBarrier(){

        CannonShot cannonShot, alienShot;
        Barrier tempBarrier;
        ArrayList<CannonShot> removeShot = new ArrayList<>();
        ArrayList<Barrier> removeBarrier = new ArrayList<>();
        
    /**
        * Every 5 shots, the barrier image will change.
        */
        Image damagedBarrier1 = new Image("Images/damagedBarrier1.png", 150, 150, false, false);
        Image damagedBarrier2 = new Image("Images/damagedBarrier2.png", 130, 130, false, false);

        int i, j;
        
        for(i = 0; i < cannonShots.size(); i++){
            cannonShot = cannonShots.get(i);
            for(j = 0; j < barriers.size(); j++){
                tempBarrier = barriers.get(j);
                if(cannonShot.intersects(tempBarrier)){
                    removeShot.add(cannonShot);
                    tempBarrier.decreaseLife();
                    
                    /** If it receives 5 shots, the barrier image changes */
                    if(tempBarrier.getLife() <= 10 && tempBarrier.getLife() > 5 ){                            
                        tempBarrier.setImage(damagedBarrier1);
                    }
                    
                    /** If it receives 10 shots, the barrier image changes again */
                    else if(tempBarrier.getLife() <= 5 && tempBarrier.getLife() > 0){
    
                        tempBarrier.setImage(damagedBarrier2);
                        tempBarrier.setPosition(tempBarrier.getPos_x(),570);
                    }
                    
                    /** Receiving 15 shots, the barrier disappears */
                    else if(tempBarrier.getLife() <= 0){
                        removeBarrier.add(tempBarrier);
                    }
                }
            }
        } 
        /**
        * Checks for collisions between the alien shots and the barriers,
        * similar to the Cannon Shot.
        */
        for(i = 0; i < alienShots.size(); i++){
            alien_shot = alienShots.get(i);
            for(j = 0; j < barriers.size(); j++){
                barrier_aux = barriers.get(j);
                if(alien_shot.intersects(barrier_aux)){
                    remove_shot.add(alien_shot);
                    
                    barrier_aux.setHealth();
                    if(barrier_aux.getHealth() <= 10 && barrier_aux.getHealth() > 5 ){                            
                        barrier_aux.setImage(damagedBarrier1);
                    }
                    else if(barrier_aux.getHealth() <= 5 && barrier_aux.getHealth() > 0){
            
                        barrier_aux.setImage(damagedBarrier2);
                        barrier_aux.setPosition(barrier_aux.getPos_x(),570);
                    }
                    else if(barrier_aux.getHealth() <= 0){
                        remove_barrier.add(barrier_aux);
                    }
                }
            }
        }

        /**
        * Removes the shots and the barriers marked for removal.
        */
        if(remove_shot.isEmpty() == false){
            cannonShots.removeAll(remove_shot);
            alienShots.removeAll(remove_shot);
            remove_shot.clear();
        }
        if(remove_barrier.isEmpty() == false){
            barriers.removeAll(remove_barrier);
            remove_barrier.clear();
        }

    /**
    * Method that checks for collisions between the alien shots
    * and the Cannon.
    * @return hit - a boolean to pause the game if the collision occurs.
    */
    public boolean cannonShotCollision(){
        int i;
        boolean hit = false;
        Shot shot_aux;
        ArrayList<Shot> remove_shot = new ArrayList();
        for(i = 0; i < alienShots.size(); i++){
            shot_aux = alienShots.get(i);
            if(shot_aux.intersects(ship)){
                remove_shot.add(shot_aux);
                alienShots.removeAll(remove_shot);
                remove_shot.clear();
                ship.setHealth();
                
                /** The cannon goes back to the start. */
                ship.setPosition(200,700);
                hit = true;
            }
        }
        return hit;
    }

    /**
    * This method creates the objects necessary to start the game.
    * It also creates the music object, which plays in the background during the game
    * and has a sound effect when the Player shoots.
    */
    public void startGame() {
        
        createScreen();
        createCannon();
        createBarrier();
        createAliens(level);
        music = new Music();
        music.startMusic();
    }

    /**
    * Methods that draw the elements: Cannon, Aliens, Barriers, and Shots.
    * @param gc - from JavaFX's GraphicsContext
    */
    public void drawCannon(GraphicsContext gc){

    gc.drawImage( ship.image, ship.pos_x, ship.pos_y );
    } 

    public void drawAliens(GraphicsContext gc){
        int i;
        for(i = 0; i < aliens.size(); i++){
            gc.drawImage( aliens.get(i).image, aliens.get(i).pos_x, aliens.get(i).pos_y );
        }
        
        for(i = 0; i < specialAliens.size(); i++){
            gc.drawImage( specialAliens.get(i).image, specialAliens.get(i).pos_x, specialAliens.get(i).pos_y );
        }
    }

    public void drawCannonShot(GraphicsContext gc){
        int i;
        for(i = 0; i < cannonShots.size(); i++){
            gc.drawImage( cannonShots.get(i).image, cannonShots.get(i).pos_x, cannonShots.get(i).pos_y );
        }
    }

    public void drawAlienShot(GraphicsContext gc){
        int i;
        for(i = 0; i < alienShots.size(); i++){
            gc.drawImage( alienShots.get(i).image, alienShots.get(i).pos_x, alienShots.get(i).pos_y );
        }
    }

    public void drawBarriers(GraphicsContext gc){
        
        int i;
    
        for(i = 0; i < barriers.size(); i++){
            
            gc.drawImage(barriers.get(i).image, barriers.get(i).pos_x, barriers.get(i).pos_y);
        
        }
    }

    /**
    * This method draws the current lives of the cannon at the bottom edge of the screen.
    * If the player is hit, a life is lost, and the game pauses.
    */
    public void drawLives(GraphicsContext gc){
        
        Image life_image = new Image("Images/life.png", 50, 50, false, false);
        switch (ship.getHealth()) {
            case 3:
                gc.drawImage(life_image, 30, 810);
                gc.drawImage(life_image, 85, 810);
                gc.drawImage(life_image, 140, 810);
                break;
            case 2:
                gc.drawImage(life_image, 30, 810);
                gc.drawImage(life_image, 85, 810);
                break;
            case 1:
                gc.drawImage(life_image, 30, 810);
                break;
            default:
                break;
        }  
    }

    /**
    * Combines the drawing methods for easier understanding.
    */
    public void drawElements(GraphicsContext gc){

        drawCannon(gc);
        drawAliens(gc);
        drawCannonShot(gc);
        drawBarriers(gc);
        drawAlienShot(gc);
        drawLives(gc);
    }

    /**
    * Combines the moving methods for easier reading. 
    * @return end - indicates that the aliens reached the edge of the screen and the game ends.
    */
    public boolean moveElements(String difficulty){
        boolean end;
        moveCannonShots();
        end = moveAliens(difficulty);
        moveAlienShots(difficulty);
        moveSpecialAlien();
        return end;
    }

    /**
    * Combines the collision methods.
    * @return hit - if the cannon is hit, the game should pause.
    */
    public boolean collisionElements(){
        boolean hit;
        collisionShotBarrier(); 
        hit =  cannonShotCollision();
        collisionShotShot();
        return hit;
    }

    /**
    * Method that deletes the Shot, Barrier, and Alien elements.
    * This method will be useful to reset the game when the player dies
    * and wants to restart the game from the beginning.
    */
    public void deleteElements(){
        
        ArrayList<Shot> remove_shot = new ArrayList<>();
        ArrayList<Barrier> remove_barrier = new ArrayList<>();
        ArrayList<Alien> remove_aliens = new ArrayList<>();
        Shot shot_aux;
        Barrier barrier_aux;
        Alien  alien_aux;

        int i;
        
        for(i = 0; i < cannonShots.size(); i++){
            shot_aux = cannonShots.get(i);
            remove_shot.add(shot_aux);
        }
        for(i = 0; i < alienShots.size(); i++){
            shot_aux = alienShots.get(i);
            remove_shot.add(shot_aux);
        }
        for(i = 0; i < barriers.size(); i++){
            barrier_aux =  barriers.get(i);
            remove_barrier.add(barrier_aux);
        }
        for(i = 0; i < aliens.size(); i++){
            alien_aux =  aliens.get(i);
            remove_aliens.add(alien_aux);
        }

        if(remove_shot.isEmpty() == false){
            cannonShots.removeAll(remove_shot);
            alienShots.removeAll(remove_shot);
            remove_shot.clear();
        }
        if(remove_barrier.isEmpty() == false){
            barriers.removeAll(remove_barrier);
            remove_barrier.clear();
        }
        if(remove_aliens.isEmpty() == false){
            aliens.removeAll(remove_aliens);
            remove_aliens.clear();
        }
    }


    /** Returns the level the player is currently on. */
    public int getLevel(){
        return level;
    }

    /** Increases the player's level. */
    public void setLevel(){
        this.level++;
    }

    /** Returns the player's current score. */
    public int getScore(){
        return score;
    }
}