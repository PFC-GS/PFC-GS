package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Usuario;

import java.io.IOException;

public class MainMenuController {


    @FXML
    private Button btnGoCategorias;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lbRecuperaNombre;


    @FXML
    void methodLogout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        try {
            changeSceneMethod(loader, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void verCategorias(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/tableMainMenu-view.fxml"));
        try {
            changeSceneMethod(loader, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
