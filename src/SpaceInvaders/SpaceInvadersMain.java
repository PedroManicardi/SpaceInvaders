package SpaceInvaders;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the project
 * @author pedro
 */
public class SpaceInvadersMain extends Application {
    public static void main(String[] args) {
        launch(args);  // Chama o método launch() da classe Application
    }

    @Override
    public void start(Stage primaryStage) {
        // Inicialização da interface gráfica (Exemplo)
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();
    }
}
