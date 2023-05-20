package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.proyecto.tfgfront.model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class LoginController implements Initializable {

    @FXML
    private PasswordField contrasenaLogin;

    @FXML
    private Pane panelWrong;

    @FXML
    private TextField emailLogin;
    @FXML
    private Button loginButton;

    private UniRestController uniRest = new UniRestController();

    private Usuario user;

    @FXML
    void olvidastePw(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/olvidarPw-view.fxml"));

        changeSceneMethod(loader, event);
    }
    @FXML
    void irARegistro(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/registro-view.fxml"));

        changeSceneMethod(loader, event);
    }



    @FXML
    void accionBoton(ActionEvent event) throws IOException {
        boolean usuarioValido = false;

        user = uniRest.login(emailLogin.getText(),contrasenaLogin.getText());
        conditionsRedText();
        if (user != null){
            if (user.getEmail().equals(emailLogin.getText()) && user.getPassword().equals(contrasenaLogin.getText())) {
                usuarioValido = true;
            }else {
                panelWrong.setVisible(true);

            }

        }
        if (usuarioValido) {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));

                changeSceneMethod(loader, event);

        } else {
            panelWrong.setVisible(true);
        }
    }

    private void conditionsRedText() {
        List<TextField> textFields = Arrays.asList(emailLogin, contrasenaLogin);

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.getStyleClass().add("redText");
            }
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTextField(emailLogin, "Correo electrónico");
        configurarTextField(contrasenaLogin, "Contraseña");




    }
    private void configurarTextField(TextField textField, String textoSugerencia) {
        textField.setPromptText(textoSugerencia);
        textField.getStyleClass().add("textField");
    }
}