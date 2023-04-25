package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Usuario;
import java.io.IOException;

public class LoginController {

    public Button resultado;
    public TextField usuarioLogin;
    public TextField contrasenaLogin;
    private UniRestController uniRest = new UniRestController();

    public void accionBoton(ActionEvent event) throws IOException {
        boolean usuarioValido = false;

        Usuario user = uniRest.login2(usuarioLogin.getText(),contrasenaLogin.getText());
        if (user != null){
            usuarioValido = true;
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