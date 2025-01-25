package SpaceInvaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import InterfaceGraphic.ScreenController;

public class SpaceInvadersMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        ScreenController screenController = new ScreenController();

        primaryStage.setTitle("SPACE INVADERS");
        primaryStage.setScene(new Scene(new Group()));  
        primaryStage.setResizable(false);
        

        screenController.start(primaryStage); 

        primaryStage.show();
    }
}