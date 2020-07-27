
package Main;

import Interfaz.Interfaz;
import Interfaz.Registro;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Ma. Cecilia
 */
public class Play extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Registro.crearArreglos();
        Registro re= new Registro();
        
//        Interfaz interfaz = new Interfaz();
//        interfaz.start(primaryStage);
        re.ventanaLogin(primaryStage);
        
    }
    public static void main(String[] args) {
        launch(args);
        
     
    }
    
}
