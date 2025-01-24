package Elementos;
import javafx.scene.image.Image;

/*
Definição da classe barreira, filha da classe Elemento
@author Pedro
*/

public class Barreira extends Elemento {

    public Barreira(int pos_x, int pos_y, int largura, int altura, int vida, Image imag){
        super(pos_x, pos_y, largura, altura, vida,imag);
    }
}
