package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.proyecto.tfgfront.model.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;


public class RegistroController implements Initializable {
    @FXML
    private TextField apellidosUsuario;

    @FXML
    private TextField emailUsuario;


    @FXML
    private TextField nombreUsuario;

    @FXML
    private TextField passwordUsuario;
    @FXML
    private Pane panelWrong;


    @FXML
    void enviarAltaRegistro(ActionEvent event) {

        conditionsRedText();
        if (!nombreUsuario.getText().isEmpty() && !apellidosUsuario.getText().isEmpty() && !emailUsuario.getText().isEmpty() && !passwordUsuario.getText().isEmpty()){
            panelWrong.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro realizado con exito");
            soloBotonAceptar(alert);
            Usuario user = new Usuario();
            user.setNombre(nombreUsuario.getText());
            user.setApellidos(apellidosUsuario.getText());
            user.setEmail(emailUsuario.getText());
            user.setPassword(passwordUsuario.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
            changeSceneMethod(loader, event);

           // user = uniRest.altaUsuario(user); // TODO: 27/04/2023 implementar altaUsuario en UniRestController

        }else {
            panelWrong.setVisible(true);
        }
    }

    private static void soloBotonAceptar(Alert alert) {
        //quitar botones que no sean aceptar
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().clear();
        Button okButton = new Button("Aceptar");
        okButton.setOnAction(e -> alert.hide());
        buttonBar.getButtons().add(okButton);
        alert.showAndWait();
    }

    private void conditionsRedText() {
        if (nombreUsuario.getText().isEmpty()){
               nombreUsuario.setStyle("-fx-prompt-text-fill: red;");
        }
        if (apellidosUsuario.getText().isEmpty()){
            apellidosUsuario.setStyle("-fx-prompt-text-fill: red;");
        }
        if (emailUsuario.getText().isEmpty()){
            emailUsuario.setStyle("-fx-prompt-text-fill: red;");
        }
        if (passwordUsuario.getText().isEmpty()){
            passwordUsuario.setStyle("-fx-prompt-text-fill: red;");
        }
    }

    @FXML
    void volverALogin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        changeSceneMethod(loader, event);
    }
    @FXML
    void exitMethod(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nombreUsuario.setPromptText("Introduce Nombre");
        nombreUsuario.setStyle("-fx-prompt-text-fill: gray;");
        nombreUsuario.setAlignment(Pos.CENTER);

        apellidosUsuario.setPromptText("Introduce Apellidos");
        apellidosUsuario.setStyle("-fx-prompt-text-fill: gray;");
        apellidosUsuario.setAlignment(Pos.CENTER);

        emailUsuario.setPromptText("Introduce Correo electronico");
        emailUsuario.setStyle("-fx-prompt-text-fill: gray;");
        emailUsuario.setAlignment(Pos.CENTER);

        passwordUsuario.setPromptText("Introduce Contraseña");
        passwordUsuario.setStyle("-fx-prompt-text-fill: gray;");
        passwordUsuario.setAlignment(Pos.CENTER);

    }

}
