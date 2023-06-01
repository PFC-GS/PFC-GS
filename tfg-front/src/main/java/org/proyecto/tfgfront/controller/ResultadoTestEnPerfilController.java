package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.proyecto.tfgfront.model.Pregunta;
import org.proyecto.tfgfront.model.TestGestor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class ResultadoTestEnPerfilController implements Initializable {


    @FXML
    private Button atras;

    @FXML
    private Button siguiente;
    @FXML
    private Label encabezadoPregunta;

    @FXML
    private Label lbRespuesta1;

    @FXML
    private Label lbRespuesta2;

    @FXML
    private Label lbRespuesta3;

    @FXML
    private Label numeroPregunta;

    @FXML
    private Pane panelRespuesta1;

    @FXML
    private Pane panelRespuesta2;

    @FXML
    private Pane panelRespuesta3;
    private TestGestor gestor;
    @FXML
    private Circle circuloPregunta;

    private int indicePreguntaActual = 0;
    private int contadorPreguntas = 1;

    private String panelPorDefecto = "-fx-background-color: #f8efd7";
    private String panelVerde = "-fx-background-color: #7eb400";


    @FXML
    void atras(ActionEvent event) {
        indicePreguntaActual--;
        contadorPreguntas--;
        siguiente.setVisible(true);

        colorPanelesPorDefecto();

        List<Pregunta> preguntas = new ArrayList<>(gestor.getPreguntasCorrectas());
        atras.setVisible(true);

        respuestasCorrectas(0,atras,preguntas);



    }



    @FXML
    void salir(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/perfil-view.fxml"));
        changeSceneMethod(loader, event);
    }

    @FXML
    void siguiente(ActionEvent event) {
        visibility(true);
        colorPanelesPorDefecto();

        List<Pregunta> preguntas = new ArrayList<>(gestor.getPreguntasCorrectas());
        atras.setVisible(true);

        respuestasCorrectas(preguntas.size() - 1,siguiente,preguntas);


        indicePreguntaActual++;
        contadorPreguntas++;

    }
    private void respuestasCorrectas(int x, Button quitarBoton, List<Pregunta> preguntas) {

        numeroPregunta.setText(Integer.toString(contadorPreguntas));
        encabezadoPregunta.setText(preguntas.get(indicePreguntaActual).getEnunciado());
        lbRespuesta1.setText(preguntas.get(indicePreguntaActual).getRespuestaA());
        lbRespuesta2.setText(preguntas.get(indicePreguntaActual).getRespuestaB());
        lbRespuesta3.setText(preguntas.get(indicePreguntaActual).getRespuestaC());
        compruebaRespuestaCorrecta(preguntas.get(indicePreguntaActual), panelVerde);

        if (indicePreguntaActual == x) {
            quitarBoton.setVisible(false);
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



    public void setlistaTestGestor(TestGestor testGestor) {
        if (testGestor != null) {
            this.gestor = testGestor;
            System.out.println("El testGestor se ha asignado correctamente");
        } else {
            System.out.println("El testGestor es nulo");
        }
    }


    private void colorPanelesPorDefecto() {
        panelRespuesta1.setStyle(panelPorDefecto);
        panelRespuesta2.setStyle(panelPorDefecto);
        panelRespuesta3.setStyle(panelPorDefecto);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atras.setVisible(false);
        visibility(false);


    }

    private void visibility(Boolean visible) {
        lbRespuesta1.setVisible(visible);
        lbRespuesta2.setVisible(visible);
        lbRespuesta3.setVisible(visible);
        encabezadoPregunta.setVisible(visible);
        numeroPregunta.setVisible(visible);
        panelRespuesta1.setVisible(visible);
        panelRespuesta2.setVisible(visible);
        panelRespuesta3.setVisible(visible);
        circuloPregunta.setVisible(visible);
    }


}

