
package InterfaceGrafica;


import Engine.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @author pedro
 */
public class ControlaTela extends Application {
    
    /**Atributo auxiliar para saber se o jogo já começou, isto é,
     *  saiu do menu.
    */
    public boolean comecou = false;
    
    /**Atributo auxliar para saber se o usuário quer ver o guia. */
    public boolean guia = false;
    
    /** Atributo auxiliar para saber se o usuário quer ver as dificuldas. */
    public boolean dificuldades = false;
    
    /** Atributo auxiliar para saber se o usuário pausou o jogo. */
    public boolean pausado = false;
    
    /** Atributo para saber se o usuário derrotou todos os aliens. */
    public boolean venceu = false;
    
    /** Atributo auxiliar no momento de derrota do jogador. */
    public boolean perdeu = false;
    
    /** Atributo auxiliar para saber se o canhão foi atingido por um tiro. */
    public boolean atingido = false;
    
    /** Atributo para auxiliar na criação da próxima fase. */
    public boolean reseta = false;
    
    /** Atributo que define a dificuldade do jogo: Normal ou Dificil. */
    private String dificuldade;
    
    /** Tamanho da largura da tela (foi obtido experimentalmente). */
    public final int largura = 1400;
    
    /** Tamanho da altura da tela (foi obtido experimentalmente). */
    public final int altura = 900;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(largura, altura);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        /**
         * Criação do jogo.
         */
        Engine engine = new Engine(gc);
        engine.IniciaGame();
        
        /**
         * Definição da fonte a ser utilizada por meio
         *   da função gc.fillText().
         */
        Font theFont = Font.font("SansSerif", FontWeight.BOLD, 50);
        gc.setFill(Color.PINK);
        gc.setFont(theFont);
        gc.setStroke(Color.BLACK);
        
   
        new AnimationTimer(){
            @Override
            public void handle(long time){

                lerTeclas(scene, engine);
                loopJogo(gc, engine);
  
            }            
        }.start();
                
        stage.setTitle("SPACE INVADERS");
        stage.setScene(scene);
        stage.getIcons().add(new Image("Imagens/canhao.png"));
        stage.setResizable(false);
        stage.show();
        
    }
    
    /**
     * Função que altera o boolean pausado.
     */
    public void pause(){
        pausado = pausado != true;
    }
    
    /**
     * Função que reconhece as teclas pressionadas pelo jogador
     * @param scene
     * @param engine 
     */
    public void lerTeclas(Scene scene, Engine engine){
        
        scene.setOnKeyPressed((KeyEvent e) -> {
            switch(e.getCode()){
                
                /**
                 * Ações relacionadas ao Menu
                 */
                case ENTER:     
                    dificuldades = true;
                    break;
                case G:
                    if(comecou == false){
                        guia = true;
                        dificuldades = false;
                    }
                    break;
                case ESCAPE:                 
                    if(comecou == false){
                        guia = false;
                        dificuldades = false;
                    }
                    
                    /**Caso o usuário queria voltar para o menu ao morrer. */
                    else if(comecou == true && perdeu == true){
                        comecou = false;
                        dificuldades = false;
                        perdeu = false;
                        atingido = false;
                        engine.apagaElementos();
                        engine.nave.reseta();
                        engine.criaAliens(1);
                        engine.criaBarreira();
                    }
                    break;
                case DIGIT1:
                    if(dificuldades == true && comecou == false){
                        comecou = true;
                        dificuldade = "NORMAL";
                    }
                    break;
                case DIGIT2:
                    if(dificuldades == true && comecou == false){
                        comecou = true;
                        dificuldade = "DIFÍCIL";
                    }
                    break;
                    
                /**
                 * Ações possíveis durante o loop do jogo
                 */
                case LEFT:
                    engine.moveCanhao(0);
                    break;
                case RIGHT:
                    engine.moveCanhao(1);
                    break;
                case SPACE:
                    if(comecou == true && pausado == false){
                        engine.criaTiro_Canhao();
                        atingido = false;
                        if(venceu == true){
                            reseta = true;
                        }
                    }
                    break;
                case P:
                    if(comecou == true){
                        pause();
                        break;
                    }
            }
        });
    }
     
    /**
     * Método principal que define, de fato, o loop do jogo
     * Nele, é controlado o que deve aparecer na tela.
     * @param gc do tipo GraphicsContext do JavaFx
     * @param engine Objeto do tipo Engine
     */
    public void loopJogo(GraphicsContext gc, Engine engine){
        
        /**
         * Se o jogo tiver sido ainda iniciado, desenha-se 
         *   a tela do menu.
         */
        if(comecou == false){
            engine.Tela.desenhaComeco(gc);
            
            /** Se o usuário solicitou o guia, então chama a função DesenhaGuia(). */
            if(guia == true){
                engine.Tela.desenhaGuia(gc);
            }
            
            /** Se o usuário apertou o botão de jogar, mostra quais são as dificuldades possíveis. */
            else if(dificuldades == true){
                engine.Tela.desenhaDificuldades(gc);
            }
        } 
        
        /**
         * Caso o jogo estiver de fato rodando.
         */
        else{
            
            /** Verificamos se o jogo está pausado. */
            if(pausado == true){
                gc.fillText("CONTINUAR (P)", 500, 40);
            }
            
            
            /** Ver se o canhão foi atingido e se ele ainda tem vidas. */
            else if(atingido == true && engine.nave.getVida() > 0){
                engine.Tela.canhaoAtingido();
            }
            
            /** Verificar se o usuário derrotou todos os aliens. */
            else if(engine.aliens.size() <= 0){
                engine.Tela.msgProxFase();
                venceu = true;
                
                /** Se o usuario solicitou jogar a proxima fase, resetamos o jogo. */
                if(reseta){
                    engine.setFase();
                    engine.criaAliens(engine.getFase());
                    
                    venceu = false;
                    reseta = false;
                }    
            }
            
            /**
             * Aqui é, de fato, o loop do jogo
             * Enquanto o jogador tiver vidas e existirem aliens, o jogo rodará.
             */
            else if(engine.nave.getVida() > 0 && engine.aliens.size() >  0 && dificuldades == true && perdeu == false){

                engine.Tela.desenhaFundo(gc, dificuldade);
                engine.desenhaElementos(gc);
                
                engine.criaTiro_Aliens();
                engine.criaAlienEspecial();
                
                perdeu = engine.moveElementos(dificuldade);
                atingido = engine.colisaoElementos();
                
            }
            
            /** Caso o usuário derrote todos os aliens, aparece a mensagem de ganhou na tela. */
            else if(venceu == true){
                engine.Tela.msgGanhou();
            }
            
            
            /** Caso contrário, o usuário perdeu. */
            else{
                engine.Tela.msgPerdeu();
                perdeu = true;
            }       
        }
    }
}