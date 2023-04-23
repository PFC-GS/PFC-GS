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

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usuarioLogin;
    @FXML
    private TextField contrasenaLogin;
    @FXML
    private Label resultado;

    public void accionBoton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));
    changeSceneMethod(loader,event);
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