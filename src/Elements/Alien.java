package Elements;

import javafx.scene.image.Image;

/**
* Definition of the Alien class, child class of the Element class
* @author Pedro
*/

public class Alien extends Element {

    /** The Alien can move to the right or left
        0 - right
        1 - left.
    */
    private int direction;
    
    /** Constructor of the Alien class.
     * @param pos_x - integer representing the horizontal position of the alien
     * @param pos_y - integer representing the vertical position of the alien
     * @param direction - integer indicating if the alien is moving to the right or left
     * @param width - integer used for collision handling
     * @param height - integer used for collision handling
     * @param life - represents the number of lives of the alien
     * @param image - of type Image defining the alien's image
     */
    public Alien(int pos_x, int pos_y, int direction, int width, int height, int life, Image image){
        super(pos_x, pos_y, width, height, life, image);
        this.direction = direction;
    }

    /** Method that returns the direction of the aliens. 
     * @return - returns the direction of the Alien.
     *               0 - moves to the right
     *               1 - moves to the left
     */
    public int getDirection() {
        return this.direction;
    }
    
    /** Method responsible for changing the direction of the aliens when they reach the screen's edge. */
    public void changeDirection(){
        if(this.direction == 0){
            this.direction = 1;
        }
        else{
            this.direction = 0;
        }
    } 

    /** Method responsible for the alien's movement.
     * @param reverse - when the aliens reach the side edge, the direction should be reversed
     * @param last - if it's the last alien, its speed should be increased.
     * @param difficulty - String defining the game's difficulty (Normal or Hard)
     */
    public void move(boolean reverse, boolean last, String difficulty){
        
        if(reverse == false){
            if(direction == 0){  
                if(last){
                    this.pos_x += 3;
                }
                else{
                    if("NORMAL".equals(difficulty)){
                        this.pos_x ++;
                    }
                    else{
                        this.pos_x += 2;
                    }     
                }
            }
            else{
                if(last){
                    this.pos_x -= 2;
                }
                else{
                    if("NORMAL".equals(difficulty)){
                        this.pos_x --;
                    }
                    else{
                        this.pos_x -= 2;
                    }
                }
            }
        }
        else{
            this.pos_y += 50;
            changeDirection();
        }
    }
}
