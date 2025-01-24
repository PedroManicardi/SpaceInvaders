package Elementos;

import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;


/** Definição da classe Elemento, 
  classe mãe das classes Alien, Barreira, Canhão e Tiro.
*/
public class Elemento {
   
    /** Cada Elemento terá uma posição. */
    public int pos_x;
    public int pos_y;
    
    /** Todo Elemento terá altura e largura para o tratamento de colisões. */
    public int altura, largura;
    
    /** Todo Elemento terá uma vida associada, a qual poderá perder ao longo do jogo. */
    protected int vida;
    
    /** Cada Elemento será representado por uma Imagem. */
    public Image imagem;
    
    /**Construtor da classe Elemento. 
     * @param pos_x - inteiro que representa a posição horizontal do elemento
     * @param pos_y - inteiro que representa a posição vertical do elemento
     * @param largura - inteiro que auxilia no tratamento de colisões
     * @param altura - inteiro que auxilia no tratamento de colisões
     * @param vida - representa o número de vidas do elemento
     * @param imag- da classe Image que define a imagem do elemento
     */
    public Elemento(int pos_x, int pos_y, int largura, int altura, int vida, Image imag){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.largura = largura;
        this.altura = altura;
        this.vida = vida;
        this.imagem = imag;
    }

    /** Método que retorna a posição "vertical" do Elemento. */
    public int getPos_x() {
        return pos_x;
    }
    
    /** Método que retorna a posição "horizontal" do Elemento.
     *@return - posicao vertical do elemento
     */
    public int getPos_y() {
        return pos_y;
    }
    
    /** Método que retorna a vida atual do Elemento. 
     *@return - vida do elemento
     */
    public int getVida(){
        return this.vida;
    }
        
    /** Método que diminui a vida do Elemento em uma unidade. */
    public void setVida(){
       this.vida--;
    }
    
    /** 
     * Método que altera a imagem do Elemento. 
     *@param imagem - do Tipo Image que define a imagem do elemento
     */
    public void setImag(Image imagem){
        this.imagem = imagem;
    }
    
    /** Método que atualiza a posição do Elemento. */
     public void setPosicao(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
    }
     
    /** Método que auxilia na movimentação dos Elementos. */
    public void acrescentaPosicao(int x, int y){
        this.pos_x += x;
        this.pos_y += y;
    }
    
    
    /**
    Os métodos a seguir são responsáveis por detectar 
    colisoes entre os elementos.
    * @reeturn Rectangle2D - o retângulo ocupado pelo elemento
    */
    public Rectangle2D getBoundary(){
        return new Rectangle2D(pos_x , pos_y, largura, altura);
    }
    
    public boolean intersects(Elemento x){

        return x.getBoundary().intersects(this.getBoundary());
    }  
}

