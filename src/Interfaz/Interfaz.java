package Interfaz;

import Movimientos.MovimientoPersonas;
import Movimientos.MovimientoSillas;
import Objetos.Persona;
import Objetos.Silla;
import TDAs.ArrayList;
import TDAs.DobleCircular;
import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Interfaz extends Application {

    RunnablePersona hiloPersona;

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

    Media media = new Media(new File("src/recursos/musica.mp3").toURI().toString());
    MediaPlayer player = new MediaPlayer(media);
    MediaView mv = new MediaView(player);

    Persona ganador;

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

        numeroPersonas = new TextField();
        numeroPersonas.setPrefWidth(200);

        actualizarNumeroPersonas = new Button("Actualizar");
        actualizarSentido = new Button("Actualizar");
        volverEmpezar = new Button("Nuevo Juego");
        musica = new Button("Activar Música");
        enviarDatos = new Button("¡Jugar!");

        cmb = new ComboBox<>();
        cmb.setPrefWidth(200);
        cmb.getItems().addAll("Horario", "Antihorario");

        HBox numeroPersonasPanel = new HBox(5);
        HBox sentidoPanel = new HBox(5);

        setActions();

        numeroPersonasPanel.getChildren().addAll(numeroPersonas, actualizarNumeroPersonas);
        sentidoPanel.getChildren().addAll(cmb, actualizarSentido);

        PanelDerecho.getChildren().addAll(lnumeroPersonas, numeroPersonasPanel, mensajeNumeroPersonas);
        PanelDerecho.getChildren().addAll(lsentido, sentidoPanel);
        PanelDerecho.getChildren().addAll(enviarDatos, confirmarDatos);
        PanelDerecho.getChildren().addAll(volverEmpezar);
        PanelDerecho.getChildren().addAll(musica);
    }

    public void rellenarPanelIzquierdo() {
        sillas = movimientoSillas.getSillas();
        movimientoSillas.rellenarLista(numeroDePersonas - 1);

        personas = movimientoPersonas.getPersonas();  //Inicializar persona
        movimientoPersonas.rellenarLista(numeroDePersonas);  //Rellenar persona con la cantidad indicada

        System.out.println(personas.size());

    }

    public void movimientoPersonas() { //Aqui es donde va a suceder la magia
        hiloPersona = new RunnablePersona();  //Comienza el hilo, esto creo que deberia de meterlo en un metodo
        Thread hiloEliminarPer = new Thread(hiloPersona);
        hiloEliminarPer.setDaemon(true);
        hiloEliminarPer.start();
        volverEmpezar.setDisable(true);
        actualizarNumeroPersonas.setDisable(true);
        actualizarSentido.setDisable(true);
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

                        rellenarPanelIzquierdo();   //Aqui se agrega la parte DERECHA

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

        numeroPersonas.setOnKeyReleased((KeyEvent ke) -> {
            try {
                Integer.parseInt(ke.getText());
            } catch (Exception exception) {
                numeroPersonas.clear();
                mensajeNumeroPersonas.setText("Solo ingrese valores numéricos. ");
            }
        });

        musica.setOnMousePressed((MouseEvent event) -> {
            if (musicaActiva) {
                musicaActiva = false;
                musica.setText("Activar Musica");
                player.pause();

                hiloPersona.terminar();
                volverEmpezar.setDisable(false);
                actualizarNumeroPersonas.setDisable(false);
                actualizarSentido.setDisable(false);

                ganador = hiloPersona.eliminado;

            } else {
                musicaActiva = true;
                musica.setText("Desactivar Musica");
                player.setAutoPlay(true);
                player.setVolume(1);
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.play();

                if (personas.size() != 0) {
                    movimientoPersonas();
                } else {
                    musica.setDisable(true);
                }

            }

        });

        enviarDatos.setOnMouseClicked((MouseEvent event) -> {
            actualizarDatos(enviarDatos);
        });

        volverEmpezar.setOnMouseClicked((MouseEvent event) -> {
            actualizarDatos(volverEmpezar);
        });

    }

    public class RunnablePersona implements Runnable {

        Persona eliminado = null;

        @Override
        public void run() {
            try {
                Iterator<Persona> itePersona;

                if (sentido == "Horario") {
                    itePersona = personas.iterador();
                } else {
                    itePersona = personas.iteradorReverse();
                }
                while (itePersona.hasNext() && musicaActiva) {
                    while (musicaActiva) {
                        eliminado = itePersona.next();
                        if (eliminado == null) {
                            eliminado = personas.get(personas.size() - 1);
                        }
                        System.out.println(eliminado);
                        Thread.sleep(500);

                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void terminar() {
            try {

                if (personas.size() == 2) {
                    musica.setDisable(true);
                }
                System.out.println("El eliminado es: " + eliminado.getNumero());
                if (eliminado.getNumero() < personas.size()) {
                    personas.remove(eliminado.getNumero());
                    sillas.removeLast();
                } else {
                    personas.removeLast();
                    sillas.removeLast();
                }
                System.out.println("Size de personas:" + personas.size());
            } catch (NullPointerException excepcion) {
                System.out.println("Excepcion en terminar()");

            }

        }

    }
}
