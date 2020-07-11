
package Main;

import Interfaz.Interfaz;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Ma. Cecilia
 */
public class Play extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Interfaz interfaz = new Interfaz();
        interfaz.start(primaryStage);
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
