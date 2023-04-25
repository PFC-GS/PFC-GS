package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {


    @FXML
    private Button btnGoCategorias;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lbRecuperaNombre;



    @FXML
    void methodLogout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("¿Estás seguro de que quieres cerrar sesión?");
        alert.setContentText("Se cerrará la sesión actual");
        alert.showAndWait();
        if (alert.getResult().getText().equals("Aceptar")) {
            Session.setUsuario(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
            try {
                changeSceneMethod(loader, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());
    }
}
