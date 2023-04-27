package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.proyecto.tfgfront.model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class LoginController implements Initializable {

    @FXML
    private PasswordField contrasenaLogin;

    @FXML
    private Pane panelWrong;

    @FXML
    private TextField usuarioLogin;
    @FXML
    private Pane panelLogin;

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

        user = uniRest.login(usuarioLogin.getText(),contrasenaLogin.getText());
        if (user != null){
            if (user.getEmail().equals(usuarioLogin.getText()) && user.getPassword().equals(contrasenaLogin.getText())) {
                usuarioValido = true;
            }else {
                panelWrong.setVisible(true);
// TODO: 27/04/2023 hay que agregar un evento para que cuando se muestre el panel de error se oculte al pinchar fuera porque si no no se puede acceder a registro de usuario
            }

        }
        if (usuarioValido) {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));

                changeSceneMethod(loader, event);

        } else {
            panelWrong.setVisible(true);
        }
    }
    @FXML
    void exitMethod(MouseEvent event) {
        System.exit(0);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTextField(usuarioLogin, "Introduce Correo electrónico");
        configurarTextField(contrasenaLogin, "Introduce Contraseña");


    }
    private void configurarTextField(TextField textField, String textoSugerencia) {
        textField.setPromptText(textoSugerencia);
        textField.setStyle("-fx-prompt-text-fill: gray;");
        textField.setAlignment(Pos.CENTER);
    }
}