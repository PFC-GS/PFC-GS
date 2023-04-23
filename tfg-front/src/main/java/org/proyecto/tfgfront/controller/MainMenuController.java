package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {


    @FXML
    public Button btnToLogin;
    @FXML
    public Button btnToTable;


    public void volverALogin(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        changeSceneMethod(loader, event);
    }

    public void goTable(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/tableMainMenu-view.fxml"));
        changeSceneMethod(loader, event);
        TableMainMenuViewController tableMainMenuViewController = loader.getController();
        tableMainMenuViewController.mostrarCategoria();
    }

    private static void changeSceneMethod(FXMLLoader loader, ActionEvent event) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
    }
}
