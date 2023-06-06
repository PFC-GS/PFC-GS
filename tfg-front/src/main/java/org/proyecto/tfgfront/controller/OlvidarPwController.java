package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

/**
 * Clase controladora de la vista olvidarPw
 */
public class OlvidarPwController implements Initializable {

    @FXML
    private TextField emailUsuario;


    private String correo;
    private String textoRespuesta;

    private Boolean respuesta;

    /**
     * Método que comprueba si el email introducido es correcto y envia un correo con la contraseña,
     * despues ejecuta la vista login
     *
     * @param event evento
     */
    @FXML
    void buscaCorreo(ActionEvent event) {

        if (emailUsuario.getText().isEmpty()) {
            emailUsuario.getStyleClass().add("redText");
        } else {
            correo = emailUsuario.getText();
            UniRestController uniRestController = new UniRestController();
            respuesta = uniRestController.enviarCorreo(correo);
            if (respuesta) {
                textoRespuesta = "Se ha enviado un correo a su cuenta de correo electronico";
            } else {
                textoRespuesta = "No se ha podido enviar el correo electronico";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recuperar contraseña");
            alert.setContentText(textoRespuesta);
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
            changeSceneMethod(loader, event);
        }
    }

    /**
     * Método que ejecuta la vista login
     *
     * @param event evento
     */
    @FXML
    void goToLogin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Método que carga la vista al iniciar
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailUsuario.setPromptText("Introduce tu correo electronico");
        emailUsuario.getStyleClass().add("textField");

    }
}
