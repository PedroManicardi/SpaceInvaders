package Elementos;

/*
Definição da classe Canhão, filha da classe Elemento
@author Pedro
*/
import javafx.scene.image.Image;

public class Canhao extends Elemento {
    
    /**
     * Construtor da classe canhão
     * @param pos_x - inteiro que representa a posição horizontal do canhão
     * @param pos_y - inteiro que representa a posição vertical do canhão
     * @param largura - inteiro que auxilia no tratamento de colisões
     * @param altura - inteiro que auxilia no tratamento de colisões
     * @param vida - representa o número de vidas do canhão
     * @param imag- da classe Image que define a imagem do canhão
     */
    public Canhao(int pos_x, int pos_y, int largura, int altura, int vida, Image imag){
        super(pos_x, pos_y, largura, altura, vida, imag);
    }
   
    public void reseta(){
        this.vida = 3;
        this.pos_x = 200;
    }
}
