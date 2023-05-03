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
import java.util.Arrays;
import java.util.List;
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

            // user = uniRest.altaUsuario(user);  TODO: 27/04/2023 implementar altaUsuario en UniRestController

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
            changeSceneMethod(loader, event);

        }else {
            panelWrong.setVisible(true);
        }
    }

    private void soloBotonAceptar(Alert alert) {
        //quitar botones que no sean aceptar
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().clear();
        Button okButton = new Button("Aceptar");
        okButton.setOnAction(e -> alert.hide());
        buttonBar.getButtons().add(okButton);
        alert.showAndWait();
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

        configurarTextField(nombreUsuario, "Nombre");
        configurarTextField(apellidosUsuario, "Apellidos");
        configurarTextField(emailUsuario, "Correo electrónico");
        configurarTextField(passwordUsuario, "Contraseña");

    }
    private void configurarTextField(TextField textField, String textoSugerencia) {
        textField.setPromptText(textoSugerencia);
        textField.getStyleClass().add("textField");
    }
    private void conditionsRedText() {
        List<TextField> textFields = Arrays.asList(nombreUsuario, apellidosUsuario, emailUsuario, passwordUsuario);

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.getStyleClass().add("redText");
            }
        }
    }

}