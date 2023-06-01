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

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

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



    @FXML
    void atrasPreguntaResultado(ActionEvent event) {

        indicePreguntaActual--;
        contadorPreguntas--;
        siguientePreguntaResultado.setVisible(true);

        respuestaUsuario(0, atrasPreguntaResultado);

    }

    @FXML
    void siguientePreguntaResultado(ActionEvent event) {

        indicePreguntaActual++;
        contadorPreguntas++;
        atrasPreguntaResultado.setVisible(true);

        respuestaUsuario(respuestaUsuario.size() - 1, siguientePreguntaResultado);

    }

    @FXML
    void repetirTest(ActionEvent event) {
        TestConfigurator.getCategoriaTest();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/test-view.fxml"));
        changeSceneMethod(loader, event);
    }

    private void respuestaUsuario(int x, Button quitarBoton) {

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

    private void correccionPreguntas(Pregunta respuestaUsuario) {
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

    private void colorPanelesPorDefecto() {
        panelRespuesta1.setStyle(panelPorDefecto);
        panelRespuesta2.setStyle(panelPorDefecto);
        panelRespuesta3.setStyle(panelPorDefecto);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initTime();
        initUser();
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


    }


}
