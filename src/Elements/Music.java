package Elements;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class responsible for creating music and sound effects
 * @author Pedro.
 */
public class Music {
    
    AudioInputStream music;
    Clip clip;
    Clip clip2;
    
    /**
     * Method that starts the background music in a loop.
     */
    public void startMusic(){
        
        try{
            //The music file is located in the root of the project
            music = AudioSystem.getAudioInputStream(new File("music.wav"));
            clip = AudioSystem.getClip();
            clip.open(music);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception error){
             System.out.println("Error!!! " + error.getMessage());
        }
    }
    
    /**
     * Method that plays the sound effect when the Player shoots.
     */
    public void shotMusic(){
        
        try{
            /** The sound effect file is located in the root of the project */
            music = AudioSystem.getAudioInputStream(new File("shot.wav"));
            clip2 = AudioSystem.getClip();
            clip2.open(music);
            clip2.start();
            
        }
        catch(Exception error){
             System.out.println("Error!!! " + error.getMessage());
        }
    }
}
