package org.proyecto.tfgfront.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TestController {


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
    private Pane ultimoPanelClickeado = null;




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


    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    } // TODO: 12/05/2023 implementar un método para salir de la aplicación


}
