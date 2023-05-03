package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class MainMenuController implements Initializable {


    @FXML
    private Button btnGoCategorias;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lbRecuperaNombre;
    @FXML
    private Label labelhora;
    @FXML
    private Label practicaLabel;
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private AnchorPane categoriaPanel;

    @FXML
    private AnchorPane conducirPanel;

    @FXML
    private AnchorPane damPanel;
    @FXML
    private ImageView imageMenu;
    @FXML
    private AnchorPane opoPanel;



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
            changeSceneMethod(loader, event);
        }
    }

    @FXML
    void verCategorias(ActionEvent event) {
        if (categoriaPanel.isVisible()) {
            // Si el panel está visible, ocultarlo
            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.75), categoriaPanel);
            slideOut.setFromX(0);
            slideOut.setToX(-categoriaPanel.getWidth());
            slideOut.setOnFinished(e -> categoriaPanel.setVisible(false));
            slideOut.play();

            TranslateTransition slideOut2 = new TranslateTransition(Duration.seconds(0.75), opoPanel);
            slideOut2.setFromX(0);
            slideOut2.setToX(-opoPanel.getWidth());
            slideOut2.setOnFinished(e -> opoPanel.setVisible(false));
            slideOut2.play();

            TranslateTransition slideOut3 = new TranslateTransition(Duration.seconds(0.75), conducirPanel);
            slideOut3.setFromX(0);
            slideOut3.setToX(-conducirPanel.getWidth());
            slideOut3.setOnFinished(e -> conducirPanel.setVisible(false));
            slideOut3.play();

            TranslateTransition slideOut4 = new TranslateTransition(Duration.seconds(0.75), damPanel);
            slideOut4.setFromX(0);
            slideOut4.setToX(-damPanel.getWidth());
            slideOut4.setOnFinished(e -> damPanel.setVisible(false));
            slideOut4.play();

        } else {
            // Si el panel no está visible, mostrarlo
            categoriaPanel.setVisible(true);
            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.75), categoriaPanel);
            slideIn.setFromX(-categoriaPanel.getWidth());
            slideIn.setToX(0);
            slideIn.play();

            opoPanel.setVisible(true);
            TranslateTransition slideIn2 = new TranslateTransition(Duration.seconds(0.75), opoPanel);
            slideIn2.setFromX(-opoPanel.getWidth());
            slideIn2.setToX(0);
            slideIn2.play();

            conducirPanel.setVisible(true);
            TranslateTransition slideIn3 = new TranslateTransition(Duration.seconds(0.75), conducirPanel);
            slideIn3.setFromX(-conducirPanel.getWidth());
            slideIn3.setToX(0);
            slideIn3.play();

            damPanel.setVisible(true);
            TranslateTransition slideIn4 = new TranslateTransition(Duration.seconds(0.75), damPanel);
            slideIn4.setFromX(-damPanel.getWidth());
            slideIn4.setToX(0);
            slideIn4.play();
        }
    }


//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/tableMainMenu-view.fxml"));
//        changeSceneMethod(loader, event);
//        TableMainMenuViewController tableMainMenuViewController = loader.getController();
//        tableMainMenuViewController.mostrarCategoria();
    


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());

        final LocalTime[] horaActual = {LocalTime.now()};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    horaActual[0] = LocalTime.now();
                    String horaFormateada = horaActual[0].format(formatter);
                    this.labelhora.setText(horaFormateada);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        menuPanel.setVisible(false);
        categoriaPanel.setVisible(false);
        conducirPanel.setVisible(false);
        damPanel.setVisible(false);
        opoPanel.setVisible(false);


    }
    @FXML
    void openMenu(MouseEvent event) {
        if (menuPanel.isVisible()) {
            // Si el panel está visible, ocultarlo
            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.75), menuPanel);
            slideOut.setFromX(0);
            slideOut.setToX(-menuPanel.getWidth());
            slideOut.setOnFinished(e -> menuPanel.setVisible(false));
            slideOut.play();
        } else {
            // Si el panel no está visible, mostrarlo
            menuPanel.setVisible(true);
            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.75), menuPanel);
            slideIn.setFromX(-menuPanel.getWidth());
            slideIn.setToX(0);
            slideIn.play();
        }
    }
    @FXML
    void exitMethod(MouseEvent event) {
        System.exit(0);
    }
}
