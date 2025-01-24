package Elementos;

/** Definição da classe Tiro, filha da classe Elemento. */

import javafx.scene.image.Image;

public class Tiro extends Elemento{
    
    /** O tiro pode estar subindo(tiro do canhão) ou descendo(tiro dos aliens). */
    private boolean subindo;
    private boolean descendo;
    /** Construtor da classe Tiro. 
     * @param pos_x - inteiro que representa a posição horizontal do tiro
     * @param pos_y - inteiro que representa a posição vertical do tiro
     * @param largura - inteiro que auxilia no tratamento de colisões
     * @param altura - inteiro que auxilia no tratamento de colisões
     * @param vida - representa o número de vidas do tiro
     * @param imag- da classe Image que define a imagem do tiro
     */
    public Tiro(int pos_x, int pos_y, int largura, int altura, int vida, Image imag){
        super(pos_x, pos_y, largura, altura, vida, imag);
        subindo = false;
        descendo = false;
    }
      public boolean getDescendo(){
        return descendo;
    }

    public void setDescendo(boolean d){
        descendo = d;
    }

    public boolean getSubindo(){
        return subindo;
    }

    public void setSubindo(boolean s){
        subindo = s;
    }
}
