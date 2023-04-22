package org.proyecto.tfgfront;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FrontController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainMenu(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("mainMenuVista.fxml"));
            completeSceneMethod(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void switchToLogin(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("loginVista.fxml"));
          completeSceneMethod(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo se encarga de completar el cambio de escena ahorrando codigo
     * @param event
     */
    private void completeSceneMethod(ActionEvent event) {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}