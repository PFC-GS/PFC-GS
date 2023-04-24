package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Usuario;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usuarioLogin;
    @FXML
    private TextField contrasenaLogin;
    @FXML
    private Label resultado;
    private UniRestController uniRest = new UniRestController();

    public void accionBoton(ActionEvent event) throws IOException {
        List<Usuario> usuarios = uniRest.httpLogin(usuarioLogin.getText(), contrasenaLogin.getText());
        boolean usuarioValido = false;
        for (Usuario usuario : usuarios) {
            if (usuarioLogin.getText().equals(usuario.getNombre()) && contrasenaLogin.getText().equals(usuario.getPassword())) {
                usuarioValido = true;
                break;
            }
        }
        if (usuarioValido) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));
            changeSceneMethod(loader, event);

        } else {
            resultado.setText("Error");
        }
    }


    /**
     * Método que cambia de escena
     * @param loader cargador de la escena
     * @throws IOException
     */
    private static void changeSceneMethod(FXMLLoader loader,ActionEvent event) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        //cerrar la vista anterior
        Stage loginStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        loginStage.close();
    }


}