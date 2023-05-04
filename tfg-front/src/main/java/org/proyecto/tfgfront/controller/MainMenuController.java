package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class MainMenuController implements Initializable {


    @FXML
    private Button btnGoCategorias;

    @FXML
    private Button btnLogout;
    @FXML
    private Button btnToLeft;

    @FXML
    private Button btnToRight;

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
    private StackPane contendorPaneles;

    private List<Node> paneles;
    private int indicePanelActual = -1;




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
            transitionOut();

        } else {
            // Si el panel no está visible, mostrarlo
            transitionIn();
        }
    }




//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/tableMainMenu-view.fxml"));
//        changeSceneMethod(loader, event);
//        TableMainMenuViewController tableMainMenuViewController = loader.getController();
//        tableMainMenuViewController.mostrarCategoria();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUser(); // Inicializa el usuario
        initTime(); // Inicializa el reloj
        initPanel(); // Inicializa el panel

    }

    private void mostrarPanelActual() {

        if (indicePanelActual < 0) {
            indicePanelActual = 0;
        } else if (indicePanelActual >= paneles.size()) {
            indicePanelActual = paneles.size() - 1;
        }
        for (int i = 0; i < paneles.size(); i++) {
            if (i == indicePanelActual) {
                paneles.get(i).setVisible(true);
            } else {
                paneles.get(i).setVisible(false);
            }
        }
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
    void moveToLeft(ActionEvent event) {
        if (indicePanelActual > 0) {
            indicePanelActual--;
            mostrarPanelActual();
        }

    }

    @FXML
    void moveToRight(ActionEvent event) {
        if (indicePanelActual < paneles.size() - 1) {
            indicePanelActual++;
            mostrarPanelActual();
        }
    }
    @FXML
    void exitMethod(MouseEvent event) {
        System.exit(0);
    }

    private void transitionOut() {
        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.75), categoriaPanel);
        slideOut.setFromX(0);
        slideOut.setToX(-categoriaPanel.getWidth());
        slideOut.setOnFinished(e -> categoriaPanel.setVisible(false));
        slideOut.play();
        btnToLeft.setVisible(false);
        btnToRight.setVisible(false);


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
    }


    private void transitionIn() {
        categoriaPanel.setVisible(true);
        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.75), categoriaPanel);
        slideIn.setFromX(-categoriaPanel.getWidth());
        slideIn.setToX(0);
        slideIn.play();
        btnToLeft.setVisible(true);
        btnToRight.setVisible(true);

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
    private void initPanel() {
        List<AnchorPane> listaPaneles = Arrays.asList(menuPanel, categoriaPanel, conducirPanel, damPanel, opoPanel);

        for (AnchorPane pane: listaPaneles) {
            pane.setVisible(false);
        }

        btnToLeft.setVisible(false);
        btnToRight.setVisible(false);
        paneles = contendorPaneles.getChildren();
    }

    private void initTime() {
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
    }

    private void initUser() {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());
    }
}
