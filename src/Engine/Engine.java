package Engine;
import Elementos.*;
import InterfaceGrafica.*;


import java.util.Random;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Classe responsável pelo funcionamento do jogo. */
public class Engine{  
    
    /** Construtor da Classe Engine
     *@param gc - do tipo GraphicsContext do JavaFX
     */
    public Engine(GraphicsContext gc){
        this.gc = gc;
        fase = 1;
        pontos = 0;
    }
    
    /** GraphicsContext do JavaFX */
    GraphicsContext gc;
    
    /** Objeto da Classe tela responsável por criar o Display. */
    public Tela Tela;
    
    /**
    * Pontuação do Jogador.
    */
    private int pontos;
    
    
    /**
    * Fase em que o Jogador está.
    */
    private int fase;
    
    
    /**
    * Sequência de Vetores que serão utilizados .
    */
    private final ArrayList<Tiro> tiroCanhao = new ArrayList();
    private final ArrayList<Tiro> tiroAliens = new ArrayList();
    private final ArrayList<Barreira> barreiras = new ArrayList();
    public final ArrayList<Alien> aliens = new ArrayList();
    public final ArrayList<Alien> alien_especial = new ArrayList();
    
    /** Objeto da Classe Musica responsável pelos efeitos sonoros. .
    */
    private Musica musica;
    
    /** Objeto da Classe Canhao que representa o Jogador .
    */
    public Canhao nave;
    
  
    
    /** Os Tiros dos Aliens e do Canhão serão dados num certo intervalo de tempo. */  
    private long temporiza_tiro_canhao;
    private long temporiza_tiro_alien;
    
    
    /** Cria o vetor de Aliens e o preenche. 
     @param fase - é preciso saber a fase do jogo, pois, conforme maior a fase,
     *  mais embaixo os Aliens começam.
     */
    public void criaAliens(int fase){
           
        
        //Definição da imagem dos 3 tipos de Aliens
        Image alien1 = new Image("Imagens/alien1.png", 40, 40, false, false);
        Image alien2 = new Image("Imagens/alien2.png", 40, 40, false, false);
        Image alien3 = new Image("Imagens/alien3.png", 40, 40, false, false);
        Image imag = alien1;
        int i, j;
        
        //Variável para verificar qual tipo de Aliens deve ser inserido
        int tamanho = 0;
        for(j = 50; j < 300; j+=50){
            for(i = 50; i < 600; i+=50){
            
                if(tamanho == 11){
                    imag = alien2;
                }
                else if(tamanho == 33){
                    imag = alien3;
                }
                
                Alien a = new Alien(i , j + (fase-1)* 100, 0, 40, 10, 3, imag);
                aliens.add(a);
                
                tamanho ++;
            }
        }
    }
    
    /**
    * Cria o vetor de Barreiras e o preenche com 4 barreiras.
    */    

    public void criaBarreira(){
        
        //Inicialmente utiliza a imagem da barreira não danificada
        Image ImagBarreira = new Image("Imagens/barreira.png", 150, 150, false, false);
        Barreira b1 = new Barreira(100, 550, 110, 90, 15, ImagBarreira);
        Barreira b2 = new Barreira(450, 550, 110, 90, 15, ImagBarreira);
        Barreira b3 = new Barreira(800, 550, 110, 90, 15, ImagBarreira);
        Barreira b4 = new Barreira(1150, 550, 110, 90, 15, ImagBarreira);
        barreiras.add(b1);
        barreiras.add(b2);
        barreiras.add(b3);
        barreiras.add(b4);
    }

    /**
    * Cria o objeto Canhao.
    */
    public void criaCanhao(){
        Image ImagCanhao = new Image("Imagens/canhao.png", 40, 40, false, false);
        nave = new Canhao(200,700, 40, 35, 3, ImagCanhao);
    }
    
    /**
    *Cria o vetor de Tiros do Canhão conforme solicitado pelo Jogador.
    */
     public void criaTiro_Canhao(){
        
        //Para dificultar, o Player poderá atirar a cada 1,5 segundos
        if(System.currentTimeMillis() - temporiza_tiro_canhao > 1500){
            Image ImagTiro = new Image("Imagens/tirocanhao.png", 40, 40, false, false);
            Tiro tiro = new Tiro(nave.pos_x, nave.pos_y - 1 , 5, 15, 1, ImagTiro);
            tiroCanhao.add(tiro);
            musica = new Musica();
            musica.musicaTiro();
            
            temporiza_tiro_canhao = System.currentTimeMillis();
        }
    }
    
    /**
    * Cria a Tela do jogo.
    */
    public void criaTela(){
        
        Tela = new Tela(gc, this);
        Tela.inicia();  
    }
    
    /**
    * Aqui é criado o Tiro dos Invasores de maneira aleatória.
    * Os tiros acontecem com maior frequência conforme o jogador avança de fase
    * Além dos tiros aleatórios, é criado um tiro de um aliens 
    * que esteja na coluna mais próxima a do canhão
    */
    public void criaTiro_Aliens(){
        
        Alien aux_inv;
        Random gerador = new Random();
        
        int colunaCanhao, colunaInvasor;
        int invasor = -1;
        
        int i;
        
        /**Buscar um valor aleatório. */
        int valor = (int) Math.floor(Math.random() * 300) + 1500; 
        
        Image img = new Image("Imagens/tiroalien.png", 35, 45, false, false);
        
        /** Os tiros ocorrem num certo intervalo de tempo. */
        if(System.currentTimeMillis() - temporiza_tiro_alien > valor)
        {
            colunaCanhao = nave.getPos_x();
            
            /** Procura um invasor que esteja na coluna mais próxima da do Canhão. */
            for(i = 0; i < aliens.size(); i++){
                colunaInvasor = aliens.get(i).getPos_x();
                if(colunaInvasor >= colunaCanhao && colunaInvasor <= colunaCanhao + 4){
                    invasor = i;
                    break;
                }
            }
            
            /**Caso encontre um invasor, gera o Tiro. */
            if(invasor != -1){
                tiroAliens.add(new Tiro(aliens.get(invasor).getPos_x(), 
                            aliens.get(invasor).getPos_y() + 2 , 30, 30, 1, img));
            }
 
            
            /**Gera os tiros aleatórios. */
            for(i = 0; i < fase ; i++){
                
                int j = gerador.nextInt(aliens.size());
                aux_inv = aliens.get(j);
                tiroAliens.add(new Tiro(aux_inv.getPos_x(), 
                            aux_inv.getPos_y() + 2 , 30, 30, 1, img));
            }
            
            /** Resetar o temporizador de tiros de aliens. */
            temporiza_tiro_alien = System.currentTimeMillis();
        }
    }
    
    
    /**
     * Cria o vetor de Aliens vermelhos, os quais
     * aparecem periodicamente no topo da tela.
    */
    public void criaAlienEspecial(){
        
        Image imag = new Image("Imagens/alien_especial.png", 60, 60, false, false);    
        
        int valor = (int) Math.floor(Math.random() * 305) + 1500; 
        if(System.currentTimeMillis() - temporiza_tiro_alien > valor && alien_especial.isEmpty())
        {
            
            Alien a = new Alien(1300, 10, 1, 50, 50, 1, imag); 
            alien_especial.add(a);
            
            temporiza_tiro_alien = System.currentTimeMillis();
        }
    }

    /**
     * Mètodo que move os Aliens
     * @param dificuldade - é preciso saber a dificuldade do jogo
     *     na dificuldade Difícil, os aliens se movem mais rápido
     *      que no modo normal
     * @return fim - indica que os aliens chegaram na borda inferior e o jogo deve acabar.
     */
    public boolean moveAliens(String dificuldade){
        
        boolean inverte = false;
        boolean fim = false;
        
        int i;
        for(i = 0; i< aliens.size(); i++){
            
            /**
            * Caso algum invasor esteja prestes a sair
            *  da borda, devemos inverter o sentido da movimentação.
            */
            if(aliens.get(i).getPos_x() <= 5 && aliens.get(i).getDirecao() == 1){
                inverte = true;
            }
            if(aliens.get(i).getPos_x() >= 1340 && aliens.get(i).getDirecao() == 0){
                inverte = true;
            }
            
            /**
            * Se os aliens passarem o nível do canhão,
            *   o jogo acaba.
            */
            if(aliens.get(i).getPos_y() >= 650){
                fim = true;
            }
        }
        
        /** Efetivamente movimentando os aliens com o método movimenta. */
        for (i = 0; i < aliens.size(); i++){
            
            /** Se for o ultimo alien, deve mover mais rápido. */
            if(aliens.size() == 1){
                aliens.get(i).movimenta(inverte, true, dificuldade);
            }
            else{
                aliens.get(i).movimenta(inverte, false, dificuldade);
            }
        }
        
        return fim;
        
    }
    
    /**
    * Movimentação do Alien especial.
    */
    public void moveAlienEspecial(){
        int i;
        int pos_x;
        ArrayList<Alien> remove_alien = new ArrayList<>();
        Alien alien_aux;
        for(i = 0; i < alien_especial.size(); i++){
            alien_aux =  alien_especial.get(i);
            
            /** Se o alien atingir a borda da tela, deve ser removido. */
            if(alien_aux.getPos_x() <= 2){
                remove_alien.add(alien_aux);
            }
            
            /** Mover efetivamente o alien, -4 foi obtido experimentalmente. */
            else{
               pos_x = alien_aux.getPos_x();
               alien_aux.setPosicao(pos_x - 4,10);
            }
        }
        
        /** Remover os Aliens que chegarem na borda. */
        if(!remove_alien.isEmpty()){
            alien_especial.removeAll(remove_alien);
            remove_alien.clear();
        } 
    }
    
    /**
    * Esse método verifica, primeiramente, se é possível
    * realizar a movimentação do canhao, ou seja, se ele
    * ainda não chegou na borda.
    * @param direcao - precisa-se saber se o Canhao está indo para a direita
    *   ou para a esqueda.
    */
    public void moveCanhao(int direcao){
        
        //Move para a direita
        if(nave.getPos_x() >=2 && direcao == 0){
            nave.acrescentaPosicao(-9, 0);
        }
        
        //Move para a esquerda
        if(nave.getPos_x() <= 1340  && direcao == 1){
            nave.acrescentaPosicao(9, 0);
        }
    }

    
    /**
    *   Altera as posições dos tiros do canhao 
    *   Verifica-se se o tiro esta ultrapassando o limite do display para
    *   removê-lo
    *   Também verifica-se colisões dos tiros e dos aliens
    *   Caso ocorra, os tiros e os aliens são removidos.
    */
    public void moveTirosCanhao(){

        Tiro tiro_aux;
        Alien alien_aux;
        ArrayList<Tiro> remove_tiro = new ArrayList<>();
        ArrayList<Alien> remove_alien = new ArrayList<>();

        int i,j;
        
        /**
        * Percorre o vetor de tiros e verica-se se ele está
        *   apto a se mover.
        */
        for(i = 0; i < tiroCanhao.size(); i++){
            tiro_aux = tiroCanhao.get(i);
            if(tiro_aux.getPos_y() >= 0){
               tiro_aux.acrescentaPosicao(0, -3);
                for(j = 0; j < aliens.size(); j++){
                   alien_aux = aliens.get(j);
                   if(alien_aux.intersects(tiro_aux)){
                       remove_tiro.add(tiro_aux);
                       remove_alien.add(alien_aux);  
                       
                       /** Se acertar o tiro no alien comum, recebe 10 pontos. */
                       pontos +=10;
                   }
                }
                
                
                for(j = 0; j < alien_especial.size(); j++){
                   alien_aux = alien_especial.get(j);
                   if(alien_aux.intersects(tiro_aux)){
                       
                       remove_tiro.add(tiro_aux);
                       remove_alien.add(alien_aux);  
                       
                       /** Se acertar o alien especial, ganha 100 pontos. */
                       pontos += 100;
                   }
                }
            }
            
            /** Caso o tiro esteja saindo por cima da tela. */
            else{
                remove_tiro.add(tiro_aux); 
            }
        }
        
        /**
        * Remove os aliens e os tiros marcados por participarem
        *  de uma colisão.
        */
        if(!remove_tiro.isEmpty()){
            tiroCanhao.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        if(!remove_alien.isEmpty()){
            aliens.removeAll(remove_alien);
            alien_especial.removeAll(remove_alien);
            remove_alien.clear();
        }        
    }
    
    /**
     * Método responsável pela movimentação dos tiros dos Aliens
     * @param dificuldade - indica a dificuldade do jogo.
     * Caso a dificuldade seja a Difícil, a movimentação deve ser
     *    mais rápida que a no modo Normal.
     */
    public void moveTirosAlien(String dificuldade){

        Tiro tiro_aux;
        ArrayList<Tiro> remove_tiro = new ArrayList<>();
        
        int i,j;
        for(i = 0; i < tiroAliens.size(); i++){
            tiro_aux = tiroAliens.get(i);
            if(tiro_aux.getPos_y() <= 740){
               if("NORMAL".equals(dificuldade)){
                    tiro_aux.acrescentaPosicao(0, 2);
               }
               else{
                    tiro_aux.acrescentaPosicao(0, 3);
               }
            }
            else{
                remove_tiro.add(tiro_aux);
            }
        }
        if(!remove_tiro.isEmpty()){
            tiroAliens.removeAll(remove_tiro);
            remove_tiro.clear();
        }
    }
    
    /** 
     * Método que detecta colisão entre os tiros dos Aliens e do Canhão
     * Nese caso, remove-se os dois tiros.
     */
    public void colisaoTiroTiro(){
        Tiro nave_tiro;
        Tiro alien_tiro;
        ArrayList<Tiro> tirocanhao_remove = new ArrayList<>();
        ArrayList<Tiro> tiroalien_remove = new ArrayList<>();
        int i, j;
        
        for(i = 0; i < tiroCanhao.size(); i++){
            nave_tiro = tiroCanhao.get(i);
            for(j = 0; j < tiroAliens.size(); j++){
                alien_tiro = tiroAliens.get(j);
                if(nave_tiro.intersects(alien_tiro)){
                    tirocanhao_remove.add(nave_tiro);
                    tiroalien_remove.add(alien_tiro);
                }
            }
        }
        if(tirocanhao_remove.isEmpty() == false){
            tiroCanhao.removeAll(tirocanhao_remove);
            tirocanhao_remove.clear();
        }
        if(tiroalien_remove.isEmpty() == false){
            tiroAliens.removeAll(tiroalien_remove);
            tiroalien_remove.clear();
        }
    }

    /** Método que processará a colisão entre Tiro do Canhão e dos Aliens com Barreiras
     Nesse caso, o Tiro será removido e a barreira perdera uma vida.
    */
    public void colisaoTiroBarreira(){

        Tiro canhao_tiro, alien_tiro;
        Barreira barreira_aux;
        ArrayList<Tiro> remove_tiro = new ArrayList<>();
        ArrayList<Barreira> remove_barreira = new ArrayList<>();
        
       /**
        * A cada 5 tiros, a imagem da barreira mudará.
        */
        Image barreiradanificada1 = new Image("Imagens/barreiradanificada.png", 150, 150, false, false);
        Image barreiradanificada2 = new Image("Imagens/barreiradanificada2.png", 130, 130, false, false);
  
        int i, j;
        
        for(i = 0; i < tiroCanhao.size(); i++){
            canhao_tiro = tiroCanhao.get(i);
            for(j = 0; j < barreiras.size(); j++){
                barreira_aux = barreiras.get(j);
                if(canhao_tiro.intersects(barreira_aux)){
                    remove_tiro.add(canhao_tiro);
                    barreira_aux.setVida();
                    
                    /** Se receber 5 tiros, a imagem da barreira muda */
                    if(barreira_aux.getVida() <= 10 && barreira_aux.getVida() > 5 ){                            
                        barreira_aux.setImag(barreiradanificada1);
                    }
                    
                    /** Se receber 10 tiros, a imagem da barreira muda novamente */
                    else if(barreira_aux.getVida() <= 5 && barreira_aux.getVida() > 0){
       
                        barreira_aux.setImag(barreiradanificada2);
                        barreira_aux.setPosicao(barreira_aux.getPos_x(),570);
                    }
                    
                    /** Recebendo 15 tiros, a barreira desaparece */
                    else if(barreira_aux.getVida() <= 0){
                        remove_barreira.add(barreira_aux);
                    }
                }
            }
        } 
        
        /**
        * Verifica-se, de maneira análogo ao Tiro do Canhão,
        *   a colisão entre os tiros dos aliens com as barreiras.
        */
        for(i = 0; i < tiroAliens.size(); i++){
            alien_tiro = tiroAliens.get(i);
            for(j = 0; j < barreiras.size(); j++){
                barreira_aux = barreiras.get(j);
                if(alien_tiro.intersects(barreira_aux)){
                    remove_tiro.add(alien_tiro);
                    
                    barreira_aux.setVida();
                    if(barreira_aux.getVida() <= 10 && barreira_aux.getVida() > 5 ){                            
                        barreira_aux.setImag(barreiradanificada1);
                    }
                    else if(barreira_aux.getVida() <= 5 && barreira_aux.getVida() > 0){
       
                        barreira_aux.setImag(barreiradanificada2);
                        barreira_aux.setPosicao(barreira_aux.getPos_x(),570);
                        
                    }
                    else if(barreira_aux.getVida() <= 0){
                        remove_barreira.add(barreira_aux);
                    }
                }
            }
        }    
        
        /**
        * Deve-se remover os tiros e as barreiras marcadas para remoção.
        */
        if(remove_tiro.isEmpty() == false){
            tiroCanhao.removeAll(remove_tiro);
            tiroAliens.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        if(remove_barreira.isEmpty() == false){
            barreiras.removeAll(remove_barreira);
            remove_barreira.clear();
        }
    }
    
    /**
    * Método que verifica colisões entre os tiros dos aliens
    *   e o Canhão
    * @return atingido -  um boolean para pausar o jogo caso ocorra a colisão.
    */
    public boolean ColisaoCanhaoTiro(){
        int i;
        boolean atingido = false;
        Tiro tiro_aux;
        ArrayList<Tiro> remove_tiro = new ArrayList();
        for(i = 0; i < tiroAliens.size(); i++){
            tiro_aux = tiroAliens.get(i);
            if(tiro_aux.intersects(nave)){
                remove_tiro.add(tiro_aux);
                tiroAliens.removeAll(remove_tiro);
                remove_tiro.clear();
                nave.setVida();
                
                /** O canhão volta para o início. */
                nave.setPosicao(200,700);
                atingido = true;
                
            }
        }
        return atingido;
    }
    
    /**
    * Esse método cria os objetos necessário para o início do jogo
    * Também cria o objeto música, a qual toca de fundo durante o jogo
    *   e tem um efeito sonoro caso o Player atire.
    */
    public void IniciaGame() {
        
        criaTela();
        criaCanhao();
        criaBarreira();
        criaAliens(fase);
        musica = new Musica();
        musica.iniciaMusica();
    }
    
    /**
     * Métodos que desenham os elementos: Canhão, Aliens, Barreiras e Tiros. 
     * @param gc - do GraphicsContext do JavaFX
     */
    public void desenhaCanhao(GraphicsContext gc){

       gc.drawImage( nave.imagem, nave.pos_x, nave.pos_y );
    } 
    
    public void desenhaAliens(GraphicsContext gc){
        int i;
        for(i = 0; i < aliens.size(); i++){
            gc.drawImage( aliens.get(i).imagem, aliens.get(i).pos_x, aliens.get(i).pos_y );
        }
        
        for(i = 0; i < alien_especial.size(); i++){
             gc.drawImage( alien_especial.get(i).imagem, alien_especial.get(i).pos_x, alien_especial.get(i).pos_y );
        }
    }
    
    public void desenhaTiroCanhao(GraphicsContext gc){
        int i;
        for(i = 0; i < tiroCanhao.size(); i++){
            gc.drawImage( tiroCanhao.get(i).imagem, tiroCanhao.get(i).pos_x, tiroCanhao.get(i).pos_y );
        }
    }
    
     public void desenhaTiroAliens(GraphicsContext gc){
        int i;
        for(i = 0; i < tiroAliens.size(); i++){
            gc.drawImage( tiroAliens.get(i).imagem, tiroAliens.get(i).pos_x, tiroAliens.get(i).pos_y );
        }
    }
    
    public void desenhaBarreiras(GraphicsContext gc){
        
        int i;
       
        for(i = 0; i < barreiras.size(); i++){
            
            gc.drawImage(barreiras.get(i).imagem, barreiras.get(i).pos_x, barreiras.get(i).pos_y);
           
        }
    }
    
    /**
     * Este método desenha as Vidas atuais do canhão na borda inferior da tela
     * Caso o jogador seja atingido, uma vida some e o jogo pausa.
     */
    public void desenhaVidas(GraphicsContext gc){
        
        Image vida_imagem = new Image("Imagens/vida.png", 50, 50, false, false);
        switch (nave.getVida()) {
            case 3:
                gc.drawImage(vida_imagem, 30, 810);
                gc.drawImage(vida_imagem, 85, 810);
                gc.drawImage(vida_imagem, 140, 810);
                break;
            case 2:
                gc.drawImage(vida_imagem, 30, 810);
                gc.drawImage(vida_imagem, 85, 810);
                break;
            case 1:
                gc.drawImage(vida_imagem, 30, 810);
                break;
            default:
                break;
        }  
    }
    
    /**
     * Reune os métodos de desenhar, para facilitar na compreensão.
     */
    public void desenhaElementos(GraphicsContext gc){

        desenhaCanhao(gc);
        desenhaAliens(gc);
        desenhaTiroCanhao(gc);
        desenhaBarreiras(gc);
        desenhaTiroAliens(gc);
        desenhaVidas(gc);
    }
    
    /**
     * Método que reúne os métodos de mover, para facilitar a leitura. 
     * @return fim - indica que os aliens chegaram na borda da tela e o jogo acaba.
     */
    public boolean moveElementos(String dificuldade){
        boolean fim;
        moveTirosCanhao();
        fim = moveAliens(dificuldade);
        moveTirosAlien(dificuldade);
        moveAlienEspecial();
        return fim;
    }
    
    /**
     * Método que reúne os métodos de colisão
     * @return  atingido - caso o canhão seja atingido, devemos dar uma pause no jogo.
     */
    public boolean colisaoElementos(){
        boolean atingido;
        colisaoTiroBarreira(); 
        atingido =  ColisaoCanhaoTiro();
        colisaoTiroTiro();
        return atingido;
    }
    
    /**
     * Método que apaga os Elementos Tiro, Barreira e Alien
     * O método será útil para resetar o jogo quando o usuário morrer
     *   e desejar recomeçar o jogo do zero.
     */
    public void apagaElementos(){
        
        ArrayList<Tiro> remove_tiro = new ArrayList<>();
        ArrayList<Barreira> remove_barreira = new ArrayList<>();
        ArrayList<Alien> remove_aliens = new ArrayList<>();
        Tiro tiro_aux;
        Barreira barreira_aux;
        Alien  alien_aux;

        int i;
        
        for(i = 0; i < tiroCanhao.size(); i++){
            tiro_aux = tiroCanhao.get(i);
            remove_tiro.add(tiro_aux);
        }
        for(i = 0; i < tiroAliens.size(); i++){
            tiro_aux = tiroAliens.get(i);
            remove_tiro.add(tiro_aux);
        }
         for(i = 0; i < barreiras.size(); i++){
            barreira_aux =  barreiras.get(i);
            remove_barreira.add(barreira_aux);
        }
        for(i = 0; i < aliens.size(); i++){
            alien_aux =  aliens.get(i);
            remove_aliens.add(alien_aux);
        }

        if(remove_tiro.isEmpty() == false){
            tiroCanhao.removeAll(remove_tiro);
            tiroAliens.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        if(remove_barreira.isEmpty() == false){
            barreiras.removeAll(remove_barreira);
            remove_barreira.clear();
        }
         if(remove_aliens.isEmpty() == false){
            aliens.removeAll(remove_aliens);
            remove_aliens.clear();
        }
    }
    
    
    /** Retorna a fase que o jogador está. */
    public int getFase(){
        return fase;
    }
    
    /**Acrescenta a fase do jogador. */
    public void setFase(){
        this.fase++;
    }
    
    /** Retorna os pontos obtidos pelo jogador até o momento. */
    public int getPontos(){
        return pontos;
    }
}