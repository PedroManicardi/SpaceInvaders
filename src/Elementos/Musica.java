
package Elementos;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classe responsável por criar músicas e efeitos sonoros
 * @author Pedro.
 */
public class Musica {
    
    AudioInputStream musica;
    Clip clip;
    Clip clip2;
    
    
    /**
     * Método que inicia a música de fundo em loop.
     */
    public void iniciaMusica(){
        
        try{
            //O arquivo da música ficou na raiz do projeto
            musica = AudioSystem.getAudioInputStream(new File("musica.wav"));
            clip = AudioSystem.getClip();
            clip.open(musica);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception erro){
             System.out.println("Erro!!! " + erro.getMessage());
        }
    }
    
    /**
     * Método que toca efeito sonoro quando o Player atira.
     */
    public void musicaTiro(){
        
        try{
            /** O arquivo da música ficou na raíz do projeto */
            musica =  AudioSystem.getAudioInputStream(new File("tiro.wav"));
            clip2 = AudioSystem.getClip();
            clip2.open(musica);
            clip2.start();
            
        }
        catch(Exception erro){
             System.out.println("Erro!!! " + erro.getMessage());
        }
    }
}
