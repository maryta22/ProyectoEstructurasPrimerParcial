/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Deja mi lapto ¬¬...!
 */
public class Registro {

    static String archivo = "src/recursos/usuarios.txt";

    Label lbUsername;
    Label lbPassword;
    Label lbNombre;
    Label lbEdad;

    Label lbUserLogin;
    Label lbPassLogin;

    TextField tfUsername;
    TextField tfPassword;
    TextField tfNombre;
    TextField tfEdad;

    TextField tfUserLogin;
    TextField tfPassLogin;

    Button btnRegresar;
    Button btnCrearUsuario;

    Button btnIniciarSesion;
    Button btnVentanaCracion; //Ventana de creacion de Usuario
    
    Usuario usu= new Usuario();
    Interfaz inte=new Interfaz();

    public void ventanaLogin(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        lbUserLogin = new Label("USAURIO: ");
        lbPassLogin = new Label("PASSWORD: ");

        tfUserLogin = new TextField();
        tfPassLogin = new TextField();

        btnIniciarSesion = new Button("INICIAR SESION");
        btnVentanaCracion = new Button("CREAR USUARIO");

        //EVENTHANLDER DE LOS BOTONES
        btnVentanaCracion.setOnMouseClicked((MouseEvent e) -> {
            Scene scenita= new Scene(this.ventanaRegistro(primaryStage), 500, 400);
            primaryStage.setScene(scenita);
            primaryStage.show();
        });
        
        btnIniciarSesion.setOnMouseClicked((MouseEvent e)->{
            if(this.verificarUsuario()){
                inte.start(primaryStage);
            }
            
        });
        
        
        
        root.addColumn(0, lbUserLogin, lbPassLogin, btnIniciarSesion);
        root.addColumn(1, tfUserLogin, tfPassLogin, btnVentanaCracion);

        root.setHgap(20);
        root.setVgap(20);
     
        
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Juego de Sillas");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public GridPane ventanaRegistro(Stage primaryStage){
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        

        lbUsername = new Label("USERNAME: ");
        lbPassword = new Label("PASSWORD: ");
        lbNombre = new Label("NOMBRE: ");
        lbEdad = new Label("EDAD: ");

        tfUsername = new TextField();
        tfPassword = new TextField();
        tfNombre = new TextField();
        tfEdad = new TextField();

        btnRegresar = new Button("REGRESAR");
        btnCrearUsuario = new Button("CREAR USUARIO");

        root.addColumn(0, lbUsername, lbPassword, lbNombre, lbEdad, btnRegresar);
        root.addColumn(1, tfUsername, tfPassword, tfNombre, tfEdad, btnCrearUsuario);

        root.setHgap(20);
        root.setVgap(20);

        //CONFIGURACION DE LOS BOTONES
        btnCrearUsuario.setOnMouseClicked((MouseEvent event) -> {
            try {
                this.crearNuevoUsuario();
                this.ventanaLogin(primaryStage);
            } catch (IOException ex) {
                Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnRegresar.setOnMouseClicked((MouseEvent e) -> {
            this.ventanaLogin(primaryStage);
        });

        return root;
    }

    public void crearNuevoUsuario() throws IOException {

        //Sentencia usada para escribir archivos txt
        BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo, true), "utf-8"));

        Usuario u1 = new Usuario(tfUsername.getText(), tfPassword.getText(), tfNombre.getText(), tfEdad.getText());
        Usuario.usuarios.addLast(u1);
        //Esta linea agrega el usaurio al txt
        outputStream.write(u1.getUsuario() + "," + u1.getPassword() + "," + u1.getNombre() + "," + u1.getEdad() + "\n");
        outputStream.close();

//        Planificador.getPlanificadores().add(p1);
//        //Esta linea agrega al usuario en el txt
//        outputStream.write("\n" + p1.getNombre() + "," + p1.getApellido() + "," + p1.getUsuario() + "," + p1.getContrasena() + ",planificador" + "\n");
//
//        outputStream.close();
    }
    
    public static void crearArreglos() {  //Lleno el arreglo de personas

        try (BufferedReader out = new BufferedReader(new FileReader("src/recursos/usuarios.txt"))) {
            String line;

            while ((line = out.readLine()) != null) {
                String[] personas = line.split(",");
                Usuario person = new Usuario(personas[0], personas[1], personas[2], personas[3]);
                person.getUsuarios().addLast(person);
                
            }

        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }

    }
    
    public boolean verificarUsuario() {    // Esto es para verificar usuarios

        //Recorre la lista personas que se encuentra en la clase personas
        Iterator<Usuario> ite= Usuario.usuarios.iterador();
        while(ite.hasNext()){
            Usuario usi= ite.next();
            if(usi.getUsuario().equalsIgnoreCase(tfUserLogin.getText()) && usi.getPassword().equalsIgnoreCase(tfPassLogin.getText())){
                return true;
            }
        }
//        for (Usuario p : Usuario.usuarios) {
//            if (usuario.equalsIgnoreCase(p.getUsuario()) && contrasena.equalsIgnoreCase(p.getContrasena())) {
//                return true;
//            }
//        }
        return false;
    }

}
