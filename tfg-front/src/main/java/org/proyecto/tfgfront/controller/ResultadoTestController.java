package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.Test;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class ResultadoTestController implements Initializable {

    @FXML
    private Label lbFecha;

    @FXML
    private Label lbResultado;
    @FXML
    private Label lbhora;
    @FXML
    private Label lbRecuperaNombre;

    private UniRestController uniRest = new UniRestController();
    private Test test = uniRest.getCorreccion();


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
    private void initUser() {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initTime();
        initUser();
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
