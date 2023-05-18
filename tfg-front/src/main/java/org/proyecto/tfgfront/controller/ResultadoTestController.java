package org.proyecto.tfgfront.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.proyecto.tfgfront.model.Test;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultadoTestController implements Initializable{

    @FXML
    private Label lbResultado;
    @FXML
    private Label lbFecha;
    private UniRestController uniRest = new UniRestController();
    private Test test = uniRest.getCorreccion();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            lbResultado.setText(String.valueOf(test.getCalificacion()));
            lbFecha.setText(test.getFecha());
        }

}
