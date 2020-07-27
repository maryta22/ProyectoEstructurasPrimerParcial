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
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private BorderPane root;

    private RunnablePersona hiloPersona;

    private MovimientoPersonas movimientoPersonas;
    private MovimientoSillas movimientoSillas;

    private ArrayList<Silla> sillas;
    private DobleCircular<Persona> personas;

    private ArrayList<Silla> sillasCopia;
    private ArrayList<Persona> personasCopia;

    private int numeroDePersonas;

    private String sentido;

    private Boolean juegoActivo;
    private Boolean musicaActiva;
    private Boolean cambioSentido;

    private VBox PanelIzquierdo;
    private Pane PanelCentral;
    private TextField numeroPersonas;
    private ComboBox<String> cmb;

    private Button enviarDatos;
    private Button actualizarSentido;
    private Button volverEmpezar;
    private Button musica;

    private Label mensajeNumeroPersonas;
    private Label confirmarDatos;

    private MediaPlayer player;

    ArrayList<Double> ySillas;
    ArrayList<Double> xSillas;

    ArrayList<Double> yPersonas;
    ArrayList<Double> xPersonas;

    public Interfaz() {

        juegoActivo = false;
        musicaActiva = false;
        cambioSentido = false;

        yPersonas = new ArrayList<>();
        xPersonas = new ArrayList<>();
        ySillas = new ArrayList<>();
        xSillas = new ArrayList<>();

        sillas = null;
        personas = null;

        personasCopia = new ArrayList();
        sillasCopia = new ArrayList();
        
        player = new MediaPlayer(new Media(new File("src/recursos/musica.mp3").toURI().toString()));

    }

    public void start(Stage primaryStage) {

        PanelIzquierdo = new VBox(10);
        PanelCentral = new Pane();
        PanelCentral.setPrefSize(750, 600);

        root = new BorderPane();
        root.setCenter(PanelCentral);
        root.setLeft(PanelIzquierdo);

        Scene scene = new Scene(root, 1000, 600);
        rellenarPanelIzquierdo();

        if (!juegoActivo) {
            actualizarSentido.setDisable(true);
            volverEmpezar.setDisable(true);
            musica.setDisable(true);
        }

        primaryStage.setTitle("Juego de Sillas");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * @MariaRivera Arma la parte derecha de la Interfaz
     */
    public void rellenarPanelIzquierdo() {
        Label lnumeroPersonas = new Label("Escriba el número de personas: ");
        mensajeNumeroPersonas = new Label("");
        Label lsentido = new Label("Sentido del movimiento de las personas: ");
        confirmarDatos = new Label("");

        numeroPersonas = new TextField();
        numeroPersonas.setPrefWidth(275);
        numeroPersonas.setAlignment(Pos.CENTER);

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

        numeroPersonasPanel.getChildren().addAll(numeroPersonas);
        sentidoPanel.getChildren().addAll(cmb, actualizarSentido);

        PanelIzquierdo.getChildren().addAll(lnumeroPersonas, numeroPersonasPanel, mensajeNumeroPersonas);
        PanelIzquierdo.getChildren().addAll(lsentido, sentidoPanel);
        PanelIzquierdo.getChildren().addAll(enviarDatos, confirmarDatos);
        PanelIzquierdo.getChildren().addAll(musica);
        PanelIzquierdo.getChildren().addAll(volverEmpezar);
        PanelIzquierdo.setAlignment(Pos.CENTER);

    }

    public void rellenarPanelCentral() {

        PanelCentral.getChildren().clear();

        if (personas == null) {

            sillas = movimientoSillas.getSillas();
            personas = movimientoPersonas.getPersonas();

            movimientoSillas.rellenarLista(numeroDePersonas - 1);
            movimientoPersonas.rellenarLista(numeroDePersonas);

            guardarCopia();

        }
        
        agregarPersonasPanel();
        agregarSillasPanel();

    }

    public void guardarCopia() {
        sillasCopia = sillas;
        Iterator<Persona> it = personas.iterador();
        int contador = 0;
        while (it.hasNext()) {
            personasCopia.add(contador, it.next());
            contador++;
        }
    }

    public void agregarPersonasPanel() {

        PanelCentral.getChildren().clear();

        if (yPersonas.isEmpty()) {
            restPos(personas.size(), xPersonas, yPersonas);
        }

        Double r1 = yPersonas.removeFirst();
        yPersonas.addLast(r1);

        Double r2 = xPersonas.removeFirst();
        xPersonas.addLast(r2);

        for (int n = 0; n < personas.size(); n++) {         
            personas.get(n).getCircle().setLayoutX(xPersonas.get(n));
            personas.get(n).getCircle().setLayoutY(yPersonas.get(n));

            PanelCentral.getChildren().add(personas.get(n).getCircle());

            //root.setCenter(PanelCentral);
        }
    }

    public void agregarSillasPanel() {

        if (ySillas.isEmpty()) {
            restPos(sillas.size(), xSillas, ySillas);
        }

        for (int n = 0; n < sillas.size(); n++) {
            sillas.get(n).getR().setLayoutX(xSillas.get(n) / 5);
            sillas.get(n).getR().setLayoutY(ySillas.get(n) / 5);

            PanelCentral.getChildren().add(sillas.get(n).getR());

        }
    }

    //Ordena las personas con las sillas en el Panel.
    public void restPos(int numero, ArrayList<Double> xLista, ArrayList<Double> yLista) {

        xLista.clear();
        yLista.clear();

        Double f = (2 * Math.PI) / numero;
        for (int h = 0; h < numero; h++) {
            yLista.add(h, 200 * Math.sin(f));
            xLista.add(h, 200 * Math.cos(f));
            f = f + (2 * Math.PI) / numero;
        }
    }

    //crea un hilo con el movimiento de las Personas.
    public void movimientoPersonas() { //Aqui es donde va a suceder la magia
        hiloPersona = new RunnablePersona();  //Comienza el hilo, esto creo que deberia de meterlo en un metodo
        Thread hiloEliminarPersona = new Thread(hiloPersona);
        hiloEliminarPersona.setDaemon(true);
        hiloEliminarPersona.start();

        volverEmpezar.setDisable(true);

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
                        actualizarSentido.setDisable(false);
                        volverEmpezar.setDisable(false);
                        musica.setDisable(false);

                        movimientoPersonas = new MovimientoPersonas();
                        movimientoSillas = new MovimientoSillas();

                        rellenarPanelCentral();

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

    public void nuevoJuego() {       
        PanelIzquierdo.getChildren().clear();
        PanelCentral.getChildren().clear();
        rellenarPanelIzquierdo();

        juegoActivo = false;
        musicaActiva = false;
        cambioSentido = false;

        yPersonas.clear();
        xPersonas.clear();
        ySillas.clear();
        xSillas.clear();

        sillas = null;
        personas = null;

        personasCopia.clear();
        sillasCopia.clear();

        actualizarSentido.setDisable(true);
        volverEmpezar.setDisable(true);
        musica.setDisable(true);
        
        player = new MediaPlayer(new Media(new File("src/recursos/musica.mp3").toURI().toString()));
    }

    public void asignarSillas(Persona persona) {
        PanelCentral.getChildren().clear();
        for (int n = 0; n < personas.size(); n++) {
            if (n < sillas.size()) {
                apartarEliminado(n, persona);
            } else {
                apartarEliminado(n, persona);
            }

            PanelCentral.getChildren().add(personas.get(n).getCircle());

        }

    }

    public void apartarEliminado(int n, Persona persona) {
        if (n == buscarPersona(persona)) {
            personas.get(n).getCircle().setLayoutX(xPersonas.get(n));
            personas.get(n).getCircle().setLayoutY(yPersonas.get(n));
        } else {
            personas.get(n).getCircle().setLayoutX(xPersonas.get(n) / 5);
            personas.get(n).getCircle().setLayoutY(yPersonas.get(n) / 5);
        }
    }

    public void presentarGanador() {
        Label ganador = new Label("El ganador es el jugador :" + personas.get(0).getNombre());
        PanelCentral.getChildren().clear();
        PanelCentral.getChildren().addAll(personas.get(0).getCircle(), ganador);
    }

    public int buscarPersona(Persona persona) {
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).equals(persona)) {
                return i;
            }
        }
        return 0;
    }

    ////////////////////////////////ACCIONES////////////////////////////////////
    //Acciones de los botones.
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
                restPos(personas.size(), xPersonas, yPersonas);
                restPos(sillas.size(), xSillas, ySillas);
                volverEmpezar.setDisable(false);

            } else {
                if (numeroDePersonas != 1) {
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
                } else {
                    presentarGanador();
                }
            }

        });

        enviarDatos.setOnMouseClicked((MouseEvent event) -> {
            actualizarDatos(enviarDatos);
        });

        volverEmpezar.setOnMouseClicked((MouseEvent event) -> {
            nuevoJuego();
        });

        actualizarSentido.setOnMouseClicked((MouseEvent event) -> {
            sentido = cmb.getValue();
            cambioSentido = true;
        });

    }

    ////////////////////////////////HILO///////////////////////////////////////
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
                while (musicaActiva && itePersona.hasNext()) {
                    while (musicaActiva) {
                        if (cambioSentido && sentido == "Horario") {
                            itePersona = personas.iteradorReverse();
                            cambioSentido = false;
                        } else if (cambioSentido) {
                            itePersona = personas.iteradorReverse();
                            cambioSentido = false;
                        }

                        eliminado = itePersona.next();
                        if (eliminado == null) {
                            eliminado = itePersona.next();
                        }
                        Platform.runLater(
                                () -> {
                                    rellenarPanelCentral();
                                }
                        );
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

                asignarSillas(eliminado);

                personas.remove(buscarPersona(eliminado));
                sillas.remove(buscarPersona(eliminado));

                System.out.println("El eliminado es: " + eliminado.getNumero());
                System.out.println("Size de personas:" + personas.size());

                if (personas.size() == 1) {
                    musica.setDisable(true);
                    presentarGanador();
                }

            } catch (NullPointerException excepcion) {
                excepcion.getCause();

            }

        }

    }

}
