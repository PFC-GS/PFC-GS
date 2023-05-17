package org.proyecto.tfgfront.controller;

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
import org.proyecto.tfgfront.model.Pregunta;
import org.proyecto.tfgfront.model.Test;
import org.proyecto.tfgfront.session.Session;
import org.proyecto.tfgfront.session.TestConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.Timestamp;
import java.util.*;

import static java.lang.System.currentTimeMillis;
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
   private Test test = uniRest.getTest(TestConfigurator.getUsuarioId(),TestConfigurator.getCategoriaTest(), TestConfigurator.getNumeroPreguntas());
    private List<Pregunta> preguntas = new ArrayList<>();
    private Pregunta preguntaActual;
    private String respuestaUser;
    private List<Pregunta> respuesta = new ArrayList<>();
    private int indicePreguntaActual = 0;




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

    /**
     * Método que actualiza el panel clickeado
     *
     * @param panelRespuesta panel que se ha clickeado
     */
    private void actualizarPanelClickeado(Pane panelRespuesta) {
        if (ultimoPanelClickeado != null && ultimoPanelClickeado != panelRespuesta) {
            ultimoPanelClickeado.setStyle("-fx-background-radius: 25px; -fx-border-color: transparent;");
        }
        double radioBorde = panelRespuesta.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius();
        panelRespuesta.setStyle("-fx-background-radius: " + radioBorde + "; -fx-scale-x: 1.1; -fx-scale-y: 1.1; -fx-border-color: #1a181b; -fx-border-width: 4px; -fx-border-radius: " + radioBorde + ";");
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


    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    } // TODO: 12/05/2023 implementar un método para salir de la aplicación

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
        panelRespuesta1.setStyle("-fx-background-radius: 25px;\n" +
                "    -fx-background-color: #8794F0;");
        panelRespuesta2.setStyle("-fx-background-radius: 25px;\n" +
                "    -fx-background-color: #8794F0;");
        panelRespuesta3.setStyle("-fx-background-radius: 25px;\n" +
                "    -fx-background-color: #8794F0;");
    }


    /**
     * Metodo que procesa la respuesta del usuario y la guarda en una lista
     *
     * @param event
     */
    private void procesarRespuesta(ActionEvent event) {
        // Guardar la respuesta del usuario para la pregunta actual
        preguntaActual.setSolucion(respuestaUser);
        respuesta.add(preguntaActual);
        respuesta.forEach(System.out::println);
        System.out.println("-----------------------------------");

        // Incrementar el índice de la pregunta actual
        indicePreguntaActual++;

        // Comprobar si se han contestado todas las preguntas
        if (indicePreguntaActual < preguntas.size()) {
            // Establecer la pregunta actual como la siguiente en la lista
            preguntaActual = preguntas.get(indicePreguntaActual);
        } else {
            // Mostrar diálogo de alerta indicando que se ha terminado el test
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test finalizado");
            alert.setHeaderText("Has finalizado el test");
            alert.setContentText("Pulsa aceptar para ver los resultados");
            // Guardar las respuestas del usuario en un objeto Test
            Set<Pregunta> respuestas = new HashSet<>(respuesta);
            test.setPreguntas(respuestas);
            System.out.println(test);
            uniRest.postTest(test);

            // comprueba si el usuario ha pulsado el botón aceptar
            Optional<ButtonType> result = alert.showAndWait();
            // si el usuario ha pulsado el botón aceptar de la ventana alert, carga la vista de resultados
            if (result.isPresent() && result.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/resultadoTest-view.fxml"));
                changeSceneMethod(loader, event);
            }

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

