package Elements;

/*
Definition of the Cannon class, child class of the Element class
@author Pedro
*/
import javafx.scene.image.Image;

public class Cannon extends Element {
    
    /**
     * Constructor of the Cannon class
     * @param pos_x - integer representing the horizontal position of the cannon
     * @param pos_y - integer representing the vertical position of the cannon
     * @param width - integer used for collision handling
     * @param height - integer used for collision handling
     * @param life - represents the number of lives of the cannon
     * @param image - of type Image defining the cannon's image
     */
    public Cannon(int pos_x, int pos_y, int width, int height, int life, Image image){
        super(pos_x, pos_y, width, height, life, image);
    }
   
    public void reset(){
        this.life = 3;
        this.pos_x = 200;
    }
}
