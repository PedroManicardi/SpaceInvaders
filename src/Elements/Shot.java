package Elements;

/** Definition of the class Shot, subclass of the class Element. */

import javafx.scene.image.Image;

public class Shot extends Element {
    
    /** The shot can either be going up (cannon shot) or going down (alien shot). */
    private boolean goingUp;
    private boolean goingDown;
    /** Constructor of the Shot class. 
     * @param posX - integer representing the horizontal position of the shot
     * @param posY - integer representing the vertical position of the shot
     * @param width - integer that helps with collision detection
     * @param height - integer that helps with collision detection
     * @param health - represents the number of health points of the shot
     * @param image - Image class that defines the shot's image
     */
    public Shot(int posX, int posY, int width, int height, int health, Image image){
        super(posX, posY, width, height, health, image);
        goingUp = false;
        goingDown = false;
    }
    
    public boolean isGoingDown(){
        return goingDown;
    }

    public void setGoingDown(boolean down){
        goingDown = down;
    }

    public boolean isGoingUp(){
        return goingUp;
    }

    public void setGoingUp(boolean up){
        goingUp = up;
    }
}
