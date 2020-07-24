package Interfaz;

import Movimientos.MovimientoPersonas;
import Movimientos.MovimientoSillas;
import Objetos.Persona;
import Objetos.Silla;
import TDAs.ArrayList;
import TDAs.DobleCircular;
import java.util.Iterator;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interfaz extends Application {

    MovimientoPersonas movimientoPersonas;
    MovimientoSillas movimientoSillas;
    
    ArrayList<Silla> sillas;
    DobleCircular<Persona> personas;

    int numeroDePersonas;
    String sentido;

    Boolean juegoActivo;
    Boolean datosListos;
    Boolean musicaActiva;

    VBox PanelDerecho;
    Pane PanelIzquierdo;
    TextField numeroPersonas;
    ComboBox<String> cmb;

    Button enviarDatos;
    Button actualizarNumeroPersonas;
    Button actualizarSentido;
    Button volverEmpezar;
    Button musica;

    Label mensajeNumeroPersonas;
    Label confirmarDatos;

    public Interfaz() {
        PanelDerecho = new VBox(5);
        PanelIzquierdo = new Pane();
        juegoActivo = false;
        datosListos = false;
        musicaActiva = false;

    }

    public void start(Stage primaryStage) {
        HBox root = new HBox(5, PanelIzquierdo, PanelDerecho);
        Scene scene = new Scene(root, 1000, 600);
        rellenarPanelDerecho();

        if (!juegoActivo) {
            actualizarNumeroPersonas.setDisable(true);
            actualizarSentido.setDisable(true);
            volverEmpezar.setDisable(true);
            musica.setDisable(true);
        }

        primaryStage.setTitle("Juego de Sillas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @MariaRivera Arma la parte derecha de la Interfaz
     */
    public void rellenarPanelDerecho() {
        Label lnumeroPersonas = new Label("Escriba el número de personas: ");
        mensajeNumeroPersonas = new Label("");
        Label lsentido = new Label("Escoga el sentido del movimiento de las personas: ");
        confirmarDatos = new Label("");

        enviarDatos = new Button("¡Jugar!");
        enviarDatos.setOnAction((event) -> {
            actualizarDatos(enviarDatos);
        });

        numeroPersonas = new TextField();
        numeroPersonas.setPrefWidth(200);      

        actualizarNumeroPersonas = new Button("Actualizar");
        actualizarSentido = new Button("Actualizar");
        volverEmpezar = new Button("Nuevo Juego");

        musica = new Button("Activar Música");
        musica.setOnAction((event) -> {
            if(musicaActiva){
                musicaActiva = false;
            }else{
                musicaActiva = true;
            }            
        });

        cmb = new ComboBox<>();
        cmb.setPrefWidth(200);
        cmb.getItems().addAll("Horario", "Antihorario");

        HBox numeroPersonasPanel = new HBox(5);
        HBox sentidoPanel = new HBox(5);

        numeroPersonasPanel.getChildren().addAll(numeroPersonas, actualizarNumeroPersonas);
        sentidoPanel.getChildren().addAll(cmb, actualizarSentido);

        PanelDerecho.getChildren().addAll(lnumeroPersonas, numeroPersonasPanel, mensajeNumeroPersonas);
        PanelDerecho.getChildren().addAll(lsentido, sentidoPanel);
        PanelDerecho.getChildren().addAll(enviarDatos, confirmarDatos);
        PanelDerecho.getChildren().addAll(volverEmpezar);
        PanelDerecho.getChildren().addAll(musica);
        
        setActions();

    }

    public void rellenarPanelIzquierdo() {
        movimientoSillas.rellenarLista(numeroDePersonas - 1);
        movimientoPersonas.rellenarLista(numeroDePersonas);

        sillas = movimientoSillas.getSillas();
        personas = movimientoPersonas.getPersonas();

        System.out.println(sillas.size());
        System.out.println(personas.size());

    }
    
    public void movimientoPersonas(){
        Persona aEliminar = null;
        Iterator iterator = personas.iterador();

        while (musicaActiva) {
            while (iterator.hasNext()) {
                aEliminar = (Persona) iterator.next();
                System.out.println("hola");
            }
        }

        System.out.println(aEliminar);
    }

    public void actualizarDatos(Button boton) {
        try {
            if (!juegoActivo) {
                if (!numeroPersonas.getText().trim().isEmpty()) {
                    if (cmb.getValue() != null) {
                        numeroDePersonas = (int) Integer.parseInt(numeroPersonas.getText());
                        sentido = cmb.getValue();
                        juegoActivo = true;
                        enviarDatos.setDisable(true);
                        actualizarNumeroPersonas.setDisable(false);
                        actualizarSentido.setDisable(false);
                        volverEmpezar.setDisable(false);
                        musica.setDisable(false);

                        movimientoPersonas = new MovimientoPersonas();
                        movimientoSillas = new MovimientoSillas();

                        rellenarPanelIzquierdo();

                    } else {
                        confirmarDatos.setText("DatosIncompletos");
                    }

                } else {
                    confirmarDatos.setText("DatosIncompletos");
                }
            } else {

            }
        } catch (NumberFormatException excepcion) {
            confirmarDatos.setText("Cantidad ingresada muy grande");
        }

    }
    
    private void setActions() {
        
        numeroPersonas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                try {
                    Integer.parseInt(ke.getText());
                } catch (Exception exception) {
                    numeroPersonas.clear();
                    mensajeNumeroPersonas.setText("Solo ingrese valores numéricos. ");
                }
            }
        });

    }
}
