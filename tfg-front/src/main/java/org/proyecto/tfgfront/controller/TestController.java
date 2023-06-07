package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.Pregunta;
import org.proyecto.tfgfront.model.Test;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;
import org.proyecto.tfgfront.session.TestConfigurator;
import org.proyecto.tfgfront.util.Constants;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;


public class TestController implements Initializable {


    @FXML
    private Label encabezadoPregunta;

    @FXML
    private Label numeroPregunta;
    @FXML
    private Label lbRespuesta1;

    @FXML
    private Label lbRespuesta2;

    @FXML
    private Label lbRespuesta3;
    @FXML
    private Label lbhora;
    @FXML
    private Label lbRecuperaNombre;
    @FXML
    private Label lbVersion;

    @FXML
    private AnchorPane panelEncabezado;

    @FXML
    private Pane enunciadoPregunta;

    @FXML
    private Pane panelRespuesta1;

    @FXML
    private Pane panelRespuesta2;

    @FXML
    private Pane panelRespuesta3;

    @FXML
    private Pane panelTest;

    @FXML
    private Button siguientePregunta;
    private Pane ultimoPanelClickeado = null;
    private UniRestController uniRest = new UniRestController();
    private Test test = uniRest.getTest(TestConfigurator.getUsuarioId(), TestConfigurator.getCategoriaTest(), TestConfigurator.getNumeroPreguntas());
    private List<Pregunta> preguntas = new ArrayList<>();
    private Pregunta preguntaActual;
    private String respuestaUser;
    private List<Pregunta> respuesta = new ArrayList<>();
    private int indicePreguntaActual = 0;
    private final String scaleYBorder = "; -fx-scale-x: 1.1; -fx-scale-y: 1.1; -fx-border-color: #1a181b; -fx-border-width: 4px; -fx-border-radius: ";
    private final String ultimoPanelClickeadoSettings = "-fx-background-radius: 25px; -fx-border-color: transparent;";
    private final String panelesDesmarcadosStyle = "-fx-background-radius: 25px; -fx-background-color: #f8efd7;";

    @FXML
    void seleccionPregunta1(MouseEvent event) {
        actualizarPanelClickeado(panelRespuesta1);
    }

    @FXML
    void seleccionPregunta2(MouseEvent event) {
        actualizarPanelClickeado(panelRespuesta2);
    }

    @FXML
    void seleccionPregunta3(MouseEvent event) {
        actualizarPanelClickeado(panelRespuesta3);
    }

    @FXML
    void irAMainMenu(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));
        changeSceneMethod(loader, event);
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("¿Estás seguro de que quieres cerrar sesión?");
        alert.setContentText("Se cerrará la sesión actual");
        alert.showAndWait();
        if (alert.getResult().getText().equals("Aceptar")) {
            Session.setUsuario(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
            changeSceneMethod(loader, event);
        }
    }

    /**
     * Método que actualiza el panel clickeado
     *
     * @param panelRespuesta panel que se ha clickeado
     */
    private void actualizarPanelClickeado(Pane panelRespuesta) {
        if (ultimoPanelClickeado != null && ultimoPanelClickeado != panelRespuesta) {
            ultimoPanelClickeado.setStyle(ultimoPanelClickeadoSettings);
        }
        double radioBorde = panelRespuesta.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius();
        panelRespuesta.setStyle("-fx-background-radius: " + radioBorde + scaleYBorder + radioBorde + ";");
        ultimoPanelClickeado = panelRespuesta;
    }

    /**
     * Método que asigna los eventos a los paneles de respuesta
     *
     * @param panelRespuesta
     * @param respuesta
     */
    private void setPanelRespuestaEvents(Pane panelRespuesta, String respuesta) {
        panelRespuesta.setOnMouseClicked(event -> {
            actualizarPanelClickeado(panelRespuesta);
            respuestaUser = respuesta;
            preguntaActual.setSolucion(respuestaUser);
        });
    }

    /**
     * Método que pasa a la siguiente pregunta
     *
     * @param event evento
     */
    @FXML
    void siguientePregunta(ActionEvent event) {
        procesarRespuesta(event);
        panelesDesmarcados();

        respuestaUser = null;

        // Actualizar la vista con el contenido de la pregunta actual
        encabezadoPregunta.setText(preguntaActual.getEnunciado());
        lbRespuesta1.setText(preguntaActual.getRespuestaA());
        lbRespuesta2.setText(preguntaActual.getRespuestaB());
        lbRespuesta3.setText(preguntaActual.getRespuestaC());
        numeroPregunta.setText(String.valueOf(indicePreguntaActual + 1));

        // Establecer los eventos de clic para cada panel de respuesta
        setPanelRespuestaEvents(panelRespuesta1, "a");
        setPanelRespuestaEvents(panelRespuesta2, "b");
        setPanelRespuestaEvents(panelRespuesta3, "c");
    }

    /**
     * Método que pone los paneles de respuesta en su estado inicial
     */
    private void panelesDesmarcados() {
        panelRespuesta1.setStyle(panelesDesmarcadosStyle);
        panelRespuesta2.setStyle(panelesDesmarcadosStyle);
        panelRespuesta3.setStyle(panelesDesmarcadosStyle);
    }

    /**
     * Metodo que procesa la respuesta del usuario y la guarda en una lista
     *
     * @param event
     */
    private void procesarRespuesta(ActionEvent event) {
        // Si el usuario no ha seleccionado ninguna respuesta, se guarda como respuesta "d" para evitar errores
        respuestaUser = (respuestaUser == null) ? "d" : respuestaUser;

        // Guardar la respuesta del usuario para la pregunta actual
        preguntaActual.setSolucion(respuestaUser);

        respuesta.add(preguntaActual);

        // Incrementar el índice de la pregunta actual
        indicePreguntaActual++;

        // Comprobar si se han contestado todas las preguntas
        if (indicePreguntaActual < preguntas.size()) {
            // Establecer la pregunta actual como la siguiente en la lista
            preguntaActual = preguntas.get(indicePreguntaActual);
        } else {
            // Guardar las respuestas del usuario en un objeto Test
            Set<Pregunta> respuestas = new HashSet<>(respuesta);
            test.setPreguntas(respuestas);
            TestConfigurator.setRespuestas(respuesta);

            // Enviar el test al servidor
            uniRest.postTest(test);

            // Mostrar diálogo de alerta indicando que se ha terminado el test
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test finalizado");
            alert.setHeaderText("Has finalizado el test");
            alert.setContentText("Pulsa aceptar para ver los resultados");

            // Comprueba si el usuario ha pulsado el botón aceptar
            Optional<ButtonType> result = alert.showAndWait();

            // Si el usuario ha pulsado el botón aceptar de la ventana alert, carga la vista de resultados
            if (result.isPresent() && result.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/resultadoTest-view.fxml"));
                changeSceneMethod(loader, event);
            }
        }
    }

    /**
     * Método que inicializa el reloj
     */
    private void initTime() {
        final LocalTime[] horaActual = {LocalTime.now()};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    horaActual[0] = LocalTime.now();
                    String horaFormateada = horaActual[0].format(formatter);
                    this.lbhora.setText(horaFormateada);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Método que recupera datos del usuario
     */
    private void initUser() {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());
    }

    /**
     * Método que inicializa la vista
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initUser();
        initTime();
        lbVersion.setText(Constants.version);
        // mostramos la primera pregunta al usuario y delvolvemos la respuesta segun el panel que se ha clickeado
        preguntas = new ArrayList<>(test.getPreguntas());

        if (!preguntas.isEmpty()) {
            preguntaActual = preguntas.get(0);
            encabezadoPregunta.setText(preguntaActual.getEnunciado());
            lbRespuesta1.setText(preguntaActual.getRespuestaA());
            lbRespuesta2.setText(preguntaActual.getRespuestaB());
            lbRespuesta3.setText(preguntaActual.getRespuestaC());
            numeroPregunta.setText("1");

            setPanelRespuestaEvents(panelRespuesta1, "a");
            setPanelRespuestaEvents(panelRespuesta2, "b");
            setPanelRespuestaEvents(panelRespuesta3, "c");
        }
    }
}

