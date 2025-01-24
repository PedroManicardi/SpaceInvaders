package InterfaceGrafica;

import Engine.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author pedro
 */

public class Tela{
    
    /** Imagem da tela logo apos executar o código  */
    private final Image comeco;
    
    /** Imagem que exibe as dificuldades possíveis */
    private final Image dificuldades;
    
    /** Imagem que mostra um breve guia com instruções de como jogar */
    private final Image guia;
    
    /** GraphicsContext do JavaFX */
    private final GraphicsContext gc;
    
    /** Imagem de fundo que simula o espaço */
    private final Image fundo;
    
    private final Engine Engine;
 
    
    /** 
     * Construtor da classe Tela
     * @param gc do Java FX
     * @param engine da classe Engine
     */
    public Tela(GraphicsContext gc, Engine engine){
        comeco = new Image("Imagens/comeco.png");
        dificuldades = new Image("Imagens/dificuldades.png");
        guia = new Image("Imagens/guia.png");
        fundo = new Image("Imagens/fundo.png");
        this.gc = gc;
        this.Engine = engine;
        
    }
    
    
    /**
     * Essa função é responsável por limpar a tela.
     */
    public void inicia(){
        gc.clearRect(0,0, 1400,900);
    }
    
    /**
     * Essa função é responsável por desenhar a tela do Menu.
     * @param g de GraphicsContext do JavaFX
     */
    public void desenhaComeco(GraphicsContext g){
        g.drawImage(comeco,0,0);
    }
    
    /**
     * Aqui é desenhado as opções de dificuldade
     * @param g de GraphicsContext do JavaFX.
     */
    public void desenhaDificuldades(GraphicsContext g){
        g.drawImage(dificuldades,0,0);
    }
    
    /**
     * Aqui é desenhado o guia de auxílio, com os comandos, ao jogador.
     * @param g de GraphicsContext do JavaFX.
     */
    public void desenhaGuia(GraphicsContext g){
        g.drawImage(guia,0,0);
    }
    
    /**
     * Essa função é responsável por desenhar a imagem de fundo
     *  que simula um espaço durante o jogo
     * Também é desenhado, na parte inferior da tela, o modo selecionado
     *      e os pontos obtidos pelo jogador naquele momento
     * @param g de GraphicsContext do JavaFX.
     * @param dificuldade do tipo String responsável por informar a dificuldade selecionada
     */
    public void desenhaFundo(GraphicsContext g, String dificuldade){
        g.drawImage(fundo,0,0);
        gc.fillText("MODO " + dificuldade, 430, 860);
        gc.fillText("PONTOS: " + Integer.toString(Engine.getPontos()), 1000, 860);
    }
    
    /**
     * Essa função é responsável por mostrar uma mensagem
     *   quando o canhão é atingido por um tiro de um Alien.
     */
    public void canhaoAtingido(){
        
        gc.fillText("CANHAO ATINGIDO", 200, 200 );
        gc.fillText("VOCÊ AINDA TEM " + Engine.nave.getVida() + " VIDAS", 200, 300);
        gc.fillText("PRESSIONE 'SPACE' PARA CONTINUAR", 200, 400);
    }
    
    /** 
     * Essa função é responsável por mostrar uma mensagem 
     *  quando o jogador derrota todos os aliens e ultrapassa
     *  de fase.
     */
    public void msgGanhou(){
        gc.fillText("VOCÊ GANHOU.", 80, 100);
        gc.fillText("PRESSIONE 'SPACE' PARA JOGAR O PRÓXIMO NÍVEL.", 80,200);
        gc.fillText("PRESSIONE 'ESC' PARA SAIR", 80, 300);
    }
    
    /**
     * Função que mostra as vidas restantes e a próxima fase
     *  quando o canhão derrota todos os aliens.
     */
    public void msgProxFase(){
        gc.fillText("VIDAS RESTANTES: " + Engine.nave.getVida(), 80, 400);
        gc.fillText("PRÓXIMA FASE: " + (Engine.getFase() + 1), 80, 500);
        
    }

    /**
     * Essa função mostra uma mensagem quando os Aliens matam
     *  a nave ultrapassam a borda
     * Caso o Usuário queira, ele poderá apertar ESC e voltar ao menu.
     */
    public void msgPerdeu(){
        gc.fillText("DERROTA!", 250, 300);
        gc.fillText("VOCÊ CONCLUIU " + (Engine.getFase() - 1) + " FASES", 250, 400);
        gc.fillText("E FEZ " + Engine.getPontos() + " PONTOS", 250, 500);
        gc.fillText("APERTE ESC PARA VOLTAR PARA O MENU", 250, 600);
    }
}