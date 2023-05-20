package org.proyecto.tfgfront.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.proyecto.tfgfront.model.Test;
import org.proyecto.tfgfront.session.TestConfigurator;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ResultadoTestController implements Initializable {

    @FXML
    private Label lbFecha;

    @FXML
    private Label lbResultado;

    private UniRestController uniRest = new UniRestController();
    private Test test = uniRest.getCorreccion();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatoNuevo = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fecha = formatoOriginal.parse(test.getFecha());
            String fechaFormateada = formatoNuevo.format(fecha);
            lbFecha.setText(fechaFormateada);
            lbResultado.setText(test.getNota(test.getCalificacion()));
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

}
