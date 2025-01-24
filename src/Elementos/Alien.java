package Elementos;

import javafx.scene.image.Image;

/**
* Definição da classe Alien, filha da classe Elemento
* @author Pedro
*/

public class Alien extends Elemento {

    /** O Alien pode se deslocar para a direita ou esquerda
        0 - direita
        1 - esquerda.
    */
    private int direcao;
    
    /** Construtor da classe alien. 
     @param pos_x - inteiro que representa a posição horizontal do alien
     *@param pos_y - inteiro que representa a posição vertical do alien
     * @param direcao - inteiro que indica se o alien está se movendo para a direita ou esquerda
     * @param largura - inteiro que auxilia no tratamento de colisões
     * @param altura - inteiro que auxilia no tratamento de colisões
     * @param vida - representa o número de vidas do alien
     * @param imag- da classe Image que define a imagem do alien
     */
    public Alien(int pos_x, int pos_y, int direcao, int largura, int altura, int vida, Image imag){
        super(pos_x, pos_y, largura, altura, vida, imag);
        this.direcao = direcao;
 
    }

    /** Método que retorna a direção dos aliens. 
     @return - retorna a direção do Alien.
     *               0 - move para a direita
     *               1 - move para a esquerda
    */
    public int getDirecao() {
        return this.direcao;
    }
    
    /** Método responsável por mudar a direção dos aliens quando chegarem na borda da tela. */
    public void mudarDirecao(){

        if(this.direcao == 0){
            this.direcao = 1;
        }
        else{
            this.direcao = 0;
        }
    } 

    /** Método responsável pela movimentação dos aliens.
     * @param inverte - quando o aliens chega na borda lateral, deve-se inverter o sentido
     * @param ultimo - se for o último alien, é necessário aumentar a velocidade dele.
     * @param dificuldade - String que define a dificuldade do jogo (Normal ou Dificil)
     */
    public void movimenta(boolean inverte, boolean ultimo, String dificuldade){
        
        if(inverte == false){
            if(direcao == 0){  
                if(ultimo){
                    this.pos_x += 3;
                }
                else{
                    if("NORMAL".equals(dificuldade)){
                        this.pos_x ++;
                    }
                    else{
                        this.pos_x += 2;
                    }     
                }
            }
            else{
                if(ultimo){
                    this.pos_x -= 2;
                }
                else{
                    if("NORMAL".equals(dificuldade)){
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
            mudarDirecao();
            }
        
    }
}
