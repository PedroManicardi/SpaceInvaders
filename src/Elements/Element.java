package Elements;

import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;


/** Definition of the Element class,
  parent class of the Alien, Barrier, Cannon, and Shot classes.
*/
public class Element {
   
    /** Each Element will have a position. */
    public int pos_x;
    public int pos_y;
    
    /** Every Element will have height and width for collision handling. */
    public int height, width;
    
    /** Every Element will have an associated life, which may decrease during the game. */
    protected int life;
    
    /** Each Element will be represented by an Image. */
    public Image image;
    
    /** Constructor for the Element class. 
     * @param pos_x - integer representing the horizontal position of the element
     * @param pos_y - integer representing the vertical position of the element
     * @param width - integer used for collision handling
     * @param height - integer used for collision handling
     * @param life - represents the number of lives of the element
     * @param image - Image class instance defining the element's image
     */
    public Element(int pos_x, int pos_y, int width, int height, int life, Image image){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.width = width;
        this.height = height;
        this.life = life;
        this.image = image;
    }

    /** Method that returns the "horizontal" position of the Element. */
    public int getPos_x() {
        return pos_x;
    }
    
    /** Method that returns the "vertical" position of the Element.
     *@return - vertical position of the element
     */
    public int getPos_y() {
        return pos_y;
    }
    
    /** Method that returns the current life of the Element. 
     *@return - life of the element
     */
    public int getLife(){
        return this.life;
    }
        
    /** Method that decreases the life of the Element by one unit. */
    public void setLife(){
       this.life--;
    }
    
    /** 
     * Method that changes the image of the Element. 
     *@param image - of type Image defining the element's image
     */
    public void setImage(Image image){
        this.image = image;
    }
    
    /** Method that updates the position of the Element. */
     public void setPosition(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
    }
     
    /** Method that assists in moving the Elements. */
    public void addPosition(int x, int y){
        this.pos_x += x;
        this.pos_y += y;
    }
    
    
    /**
    The following methods are responsible for detecting 
    collisions between elements.
    * @return Rectangle2D - the rectangle occupied by the element
    */
    public Rectangle2D getBoundary(){
        return new Rectangle2D(pos_x , pos_y, width, height);
    }
    
    public boolean intersects(Element x){

        return x.getBoundary().intersects(this.getBoundary());
    }  
}
