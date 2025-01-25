# Space Invaders - Java

This is the **Space Invaders** game implemented in Java using the **JavaFX** library. The goal of the game is to defend Earth from waves of invading aliens by controlling a spaceship and shooting at the enemies.

## Space Invaders History

**Space Invaders** was released in 1978 and is considered one of the most influential video games in history. Developed by Tomohiro Nishikado and produced by Taito, the game features a simple yet addictive mechanic: the player controls a ship that moves horizontally to destroy alien invaders. As the enemies are destroyed, the game progressively becomes more difficult.

## How to Run the Game

To run the **Space Invaders** game in Java, follow these steps:

1. **Make sure JavaFX is installed on your system**. If not, download the JavaFX SDK [here](https://gluonhq.com/products/javafx/).

2. **Navigate to the `dist` folder**, where the compiled `.jar` file is located.

3. **Run the `.jar` file with the JavaFX module path**:
   ```bash
   java --module-path /path/to/javafx-sdk-<version>/lib --add-modules javafx.controls,javafx.fxml -jar dist/SpaceInvaders.jar```

     Note: Replace /path/to/javafx-sdk-<version> with the actual path to your JavaFX SDK.
