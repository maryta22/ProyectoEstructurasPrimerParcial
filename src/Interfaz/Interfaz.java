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
import static javafx.scene.paint.Color.BLACK;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private BorderPane root;

    private RunnablePersona hiloPersona;

    private MovimientoPersonas movimientoPersonas;
    private MovimientoSillas movimientoSillas;

    private ArrayList<Silla> sillas;
    private DobleCircular<Persona> personas;

    private int numeroDePersonas;

    private String sentido;

    private Boolean juegoActivo;
    private Boolean datosListos;
    private Boolean musicaActiva;
    private Boolean cambioSentido;
    private Boolean nuevoRound;

    private VBox PanelDerecho;
    private Pane PanelCentral;
    private TextField numeroPersonas;
    private ComboBox<String> cmb;

    private Button enviarDatos;
    private Button actualizarSentido;
    private Button volverEmpezar;
    private Button musica;

    private Label mensajeNumeroPersonas;
    private Label confirmarDatos;

    private Media media = new Media(new File("src/recursos/musica.mp3").toURI().toString());
    private MediaPlayer player = new MediaPlayer(media);
    private MediaView mv = new MediaView(player);

    private ImageView imageView = new ImageView();

    private Persona ganador;

    ArrayList<Double> ySillas;
    ArrayList<Double> xSillas;

    ArrayList<Double> yPersonas;
    ArrayList<Double> xPersonas;

    public Interfaz() {
        PanelDerecho = new VBox(5);
        PanelCentral = new Pane();
        PanelCentral.setPrefSize(750, 600);

        juegoActivo = false;
        datosListos = false;
        musicaActiva = false;
        cambioSentido = false;
        nuevoRound = true;

        imageView.setImage(new Image("file:src/recursos/f.gif"));

        yPersonas = new ArrayList<>();
        xPersonas = new ArrayList<>();
        ySillas = new ArrayList<>();
        xSillas = new ArrayList<>();

        sillas = null;
        personas = null;

    }

    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setCenter(PanelCentral);
        root.setLeft(PanelDerecho);

        Scene scene = new Scene(root, 1000, 600);
        rellenarPanelDerecho();

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
    public void rellenarPanelDerecho() {
        Label lnumeroPersonas = new Label("Escriba el número de personas: ");
        mensajeNumeroPersonas = new Label("");
        Label lsentido = new Label("Sentido del movimiento de las personas: ");
        confirmarDatos = new Label("");

        numeroPersonas = new TextField();
        numeroPersonas.setPrefWidth(200);

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

        PanelDerecho.getChildren().addAll(lnumeroPersonas, numeroPersonasPanel, mensajeNumeroPersonas);
        PanelDerecho.getChildren().addAll(lsentido, sentidoPanel);
        PanelDerecho.getChildren().addAll(enviarDatos, confirmarDatos);
        PanelDerecho.getChildren().addAll(volverEmpezar);
        PanelDerecho.getChildren().addAll(musica);

    }

    public void rellenarPanelCentral() {
        
        PanelCentral.getChildren().clear();

        if (personas == null) {

            sillas = movimientoSillas.getSillas();
            personas = movimientoPersonas.getPersonas();

            movimientoSillas.rellenarLista(numeroDePersonas - 1);
            movimientoPersonas.rellenarLista(numeroDePersonas);
        }

        //PanelCentral.getChildren().add(imageView);
        agregarPersonasPanel();
        agregarSillasPanel();

    }

    public void agregarPersonasPanel() {

        if (yPersonas.isEmpty()|| nuevoRound) {
            Double f = (2 * Math.PI) / personas.size();
            for (int h = 0; h < personas.size(); h++) {
                yPersonas.add(h, 200 * Math.sin(f));
                xPersonas.add(h, 200 * Math.cos(f));
                f = f + (2 * Math.PI) / personas.size();
            }
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

        if (ySillas.isEmpty()|| nuevoRound) {
            Double f = (2 * Math.PI) / sillas.size();
            for (int h = 0; h < sillas.size(); h++) {
                ySillas.add(h, 200 * Math.sin(f));
                xSillas.add(h, 200 * Math.cos(f));
                f = f + (2 * Math.PI) / sillas.size();
            }
        }

        for (int n = 0; n < sillas.size(); n++) {

            sillas.get(n).getR().setLayoutX(xSillas.get(n) / 2);
            sillas.get(n).getR().setLayoutY(ySillas.get(n) / 2);
            PanelCentral.getChildren().add(sillas.get(n).getR());

            //root.setCenter(PanelCentral);
        }
    }

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
                volverEmpezar.setDisable(false);

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

        actualizarSentido.setOnMouseClicked((MouseEvent event) -> {
            sentido = cmb.getValue();
            cambioSentido = true;
        });

    }

    public void vaciarPanelDerecho() {
        PanelDerecho.getChildren().clear();
    }

    public void juegoTerminado() {
        ganador = personas.get(0);
        mostrarGanador(ganador);
    }

    public void mostrarGanador(Persona persona) {

    }

    public void mover() {

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
                        if (cambioSentido && sentido == "Horario") {
                            itePersona = personas.iteradorReverse();
                            cambioSentido = false;
                        } else if (cambioSentido) {
                            itePersona = personas.iteradorReverse();
                            cambioSentido = false;
                        }
                        eliminado = itePersona.next();
                        if (eliminado == null) {
                            eliminado = personas.get(personas.size() - 1);
                        }
                        Platform.runLater(
                                () -> {
                                    rellenarPanelCentral();
                                }
                        );
                        nuevoRound = false;
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
                if (eliminado.getNumero() < personas.size()) {
                    personas.remove(eliminado.getNumero());
                    sillas.removeLast();
                } else {
                    personas.removeLast();
                    sillas.removeLast();
                }
                System.out.println("El eliminado es: " + eliminado.getNumero());
                System.out.println("Size de personas:" + personas.size());
                nuevoRound = true;
            } catch (NullPointerException excepcion) {
                System.out.println("Excepcion en terminar()");

            }

        }

    }

}
