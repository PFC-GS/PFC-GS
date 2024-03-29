package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.Pregunta;
import org.proyecto.tfgfront.model.TestGestor;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;
import org.proyecto.tfgfront.session.TestConfigurator;
import org.proyecto.tfgfront.util.Constants;
import org.proyecto.tfgfront.util.ExcelUtils;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

/**
 * Clase controladora de la vista resultadoTest
 */
public class ResultadoTestController implements Initializable {

    @FXML
    private Label encabezadoPregunta;

    @FXML
    private Label lbFecha;

    @FXML
    private Label lbRecuperaNombre;

    @FXML
    private Label lbRespuesta1;

    @FXML
    private Label lbRespuesta2;

    @FXML
    private Label lbRespuesta3;

    @FXML
    private Label lbResultado;

    @FXML
    private Label lbhora;

    @FXML
    private Label numeroPregunta;
    @FXML
    private Label lbNoContesta;
    @FXML
    private Label lbVersion;

    @FXML
    private Button siguientePreguntaResultado;
    @FXML
    private Button atrasPreguntaResultado;
    @FXML
    private Pane panelRespuesta1;

    @FXML
    private Pane panelRespuesta2;

    @FXML
    private Pane panelRespuesta3;
    private List<Pregunta> respuestaUsuario;

    private int indicePreguntaActual = 0;
    private int contadorPreguntas = 1;
    private UniRestController uniRest = new UniRestController();
    private TestGestor testCorreccion = uniRest.getCorreccion();
    private List<Pregunta> respuestasCorrectas = new ArrayList<>(testCorreccion.getPreguntasCorrectas());
    private String panelRojo = "-fx-background-color: #df2a33";
    private String panelPorDefecto = "-fx-background-color: #f8efd7";
    private String panelVerde = "-fx-background-color: #7eb400";

    /**
     * Método que al ejecutarse descarga el excel con las preguntas y respuestas del usuario
     *
     * @param event evento que se produce al pulsar el botón
     */
    @FXML
    void descargarExcel(ActionEvent event) {
        ExcelUtils.saveExcelToDownloads("RespuestasUsuario.xlsx");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Descarga");
        alert.setHeaderText("Descarga de Excel");
        alert.setContentText("El archivo se ha descargado correctamente en la carpeta de descargas");
        alert.showAndWait();

    }

    /**
     * Método que al ejecutarse mueve la pregunta a la anterior
     *
     * @param event evento que se produce al pulsar el botón
     */
    @FXML
    void atrasPreguntaResultado(ActionEvent event) {
        indicePreguntaActual--;
        contadorPreguntas--;
        siguientePreguntaResultado.setVisible(true);

        respuestaUsuario(0, atrasPreguntaResultado);
    }

    /**
     * Método que al ejecutarse mueve la pregunta a la siguiente
     *
     * @param event evento que se produce al pulsar el botón
     */
    @FXML
    void siguientePreguntaResultado(ActionEvent event) {
        indicePreguntaActual++;
        contadorPreguntas++;
        atrasPreguntaResultado.setVisible(true);

        respuestaUsuario(respuestaUsuario.size() - 1, siguientePreguntaResultado);
    }

    /**
     * Método que al ejecutarse pinta el panel de la respuesta correcta de verde y la incorrecta de rojo,
     * si no se ha contestado muestra un label con el texto "No contestada"
     *
     * @param x           numero de pregunta
     * @param quitarBoton botón que se oculta
     */
    private void respuestaUsuario(int x, Button quitarBoton) {
        respuestasCorrectas.sort(Comparator.comparing(Pregunta::getId));
        TestConfigurator.getRespuestas().sort(Comparator.comparing(Pregunta::getId));

        numeroPregunta.setText(Integer.toString(contadorPreguntas));
        encabezadoPregunta.setText(respuestaUsuario.get(indicePreguntaActual).getEnunciado());
        lbRespuesta1.setText(respuestaUsuario.get(indicePreguntaActual).getRespuestaA());
        lbRespuesta2.setText(respuestaUsuario.get(indicePreguntaActual).getRespuestaB());
        lbRespuesta3.setText(respuestaUsuario.get(indicePreguntaActual).getRespuestaC());

        Pregunta preguntaUsuario = respuestaUsuario.get(indicePreguntaActual);

        correccionPreguntas(preguntaUsuario);

        if (indicePreguntaActual == x) {
            quitarBoton.setVisible(false);
        }
    }

    /**
     * Método extraido del metodo respuestaUsuario que comprueba si la respuesta es correcta o no
     *
     * @param respuestaUsuario respuesta del usuario
     */
    private void correccionPreguntas(Pregunta respuestaUsuario) {
        respuestasCorrectas.sort(Comparator.comparing(Pregunta::getId));
        TestConfigurator.getRespuestas().sort(Comparator.comparing(Pregunta::getId));
        for (Pregunta respuestaCorrecta : respuestasCorrectas) {
            if (respuestaUsuario.getId().equals(respuestaCorrecta.getId())) {
                colorPanelesPorDefecto();
                lbNoContesta.setVisible(false);
                if (respuestaUsuario.getSolucion().equals("d")) {
                    lbNoContesta.setVisible(true);
                }
                if (respuestaUsuario.getSolucion().equals(respuestaCorrecta.getSolucion())) {
                    compruebaRespuestaCorrecta(respuestaCorrecta, panelVerde);
                }
                if (!respuestaUsuario.getSolucion().equals(respuestaCorrecta.getSolucion())) {
                    compruebaRespuestaCorrecta(respuestaCorrecta, panelVerde);
                    compruebaRespuestaCorrecta(respuestaUsuario, panelRojo);
                }
            }
        }
    }

    /**
     * Método extraido de correccionPreguntas que comprueba si la respuesta es correcta o no
     *
     * @param respuestaCorrecta respuesta correcta
     * @param colorPanel        color del panel
     */
    private void compruebaRespuestaCorrecta(Pregunta respuestaCorrecta, String colorPanel) {
        if (respuestaCorrecta.getSolucion().equals("a")) {
            panelRespuesta1.setStyle(colorPanel);
        }
        if (respuestaCorrecta.getSolucion().equals("b")) {
            panelRespuesta2.setStyle(colorPanel);
        }
        if (respuestaCorrecta.getSolucion().equals("c")) {
            panelRespuesta3.setStyle(colorPanel);
        }
    }

    /**
     * Método que pone los paneles de las respuestas a su color por defecto
     */
    private void colorPanelesPorDefecto() {
        panelRespuesta1.setStyle(panelPorDefecto);
        panelRespuesta2.setStyle(panelPorDefecto);
        panelRespuesta3.setStyle(panelPorDefecto);
    }

    /**
     * Método que al ejecutarse repite un test (es un test nuevo)
     *
     * @param event evento que se produce al pulsar el botón
     */
    @FXML
    void repetirTest(ActionEvent event) {
        ExcelUtils.deleteFile("RespuestasUsuario.xlsx");
        TestConfigurator.getCategoriaTest();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/test-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Método que al ejecutarse vuelve al menú principal
     *
     * @param event evento que se produce al pulsar el botón
     */
    @FXML
    void irAMainMenu(ActionEvent event) {
        ExcelUtils.deleteFile("RespuestasUsuario.xlsx");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Método que al ejecutarse cierra la sesión actual y carga la vista de login
     *
     * @param event evento que se produce al pulsar el botón
     */
    @FXML
    void logout(ActionEvent event) {
        ExcelUtils.deleteFile("RespuestasUsuario.xlsx");
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
     * Recupera la fecha actual y la muestra en un label
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
     * Recupera el nombre del usuario de la sesión y lo muestra en un label
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
        initTime();
        initUser();
        lbVersion.setText(Constants.version);
        lbNoContesta.setVisible(false);
        atrasPreguntaResultado.setVisible(false);
        lbFecha.setText(testCorreccion.getTest().getFecha());
        lbResultado.setText(String.valueOf(testCorreccion.getTest().getCalificacion()));
        numeroPregunta.setText(Integer.toString(contadorPreguntas));

        respuestaUsuario = TestConfigurator.getRespuestas();

        encabezadoPregunta.setText(respuestaUsuario.get(indicePreguntaActual).getEnunciado());
        lbRespuesta1.setText(respuestaUsuario.get(indicePreguntaActual).getRespuestaA());
        lbRespuesta2.setText(respuestaUsuario.get(indicePreguntaActual).getRespuestaB());
        lbRespuesta3.setText(respuestaUsuario.get(indicePreguntaActual).getRespuestaC());

        Pregunta preguntaUsuario = respuestaUsuario.get(indicePreguntaActual);
        correccionPreguntas(preguntaUsuario);
        //ordenamos las respuestas para que coincidan con las correctas y se pinten en el excel
        respuestasCorrectas.sort(Comparator.comparing(Pregunta::getId));
        TestConfigurator.getRespuestas().sort(Comparator.comparing(Pregunta::getId));
        //generamos el excel con las respuestas correctas y las del usuario
        ExcelUtils.generateExcel(respuestasCorrectas, TestConfigurator.getRespuestas(), "RespuestasUsuario.xlsx");
    }
}
