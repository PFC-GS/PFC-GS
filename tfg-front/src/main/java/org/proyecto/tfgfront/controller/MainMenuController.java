package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;
import org.proyecto.tfgfront.session.TestConfigurator;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;
import static org.proyecto.tfgfront.util.Util.changeSceneMethodWithMouseEvent;

public class MainMenuController implements Initializable {

    public AnchorPane menuPanel;
    @FXML
    private Button btnGoCategorias;
    @FXML
    private Label lbRecuperaNombre;
    @FXML
    private Label labelhora;
    @FXML
    private Label lbCategoria;

    @FXML
    private AnchorPane futbolPanel;
    @FXML
    private AnchorPane panelNorte;

    @FXML
    private AnchorPane panelSur;

    @FXML
    private AnchorPane damPanel;
    @FXML
    private AnchorPane opoPanel;
    @FXML
    private ComboBox<Integer> comboPreguntas;
    @FXML
    private ImageView imagenHobby;
    @FXML
    private ImageView imageStudiar;
    private boolean isDamPanelVisible = true;


    @FXML
    void numeroPreguntasSeleccionadas(MouseEvent event) {
        List<Integer> opciones = Arrays.asList(5, 10, 15, 20);

        ObservableList<Integer> listaOpciones = FXCollections.observableArrayList(opciones);
        comboPreguntas.setItems(listaOpciones);

        comboPreguntas.setOnAction(e -> {
            Integer seleccion = comboPreguntas.getValue();
            if (seleccion != null) {
                Integer numeroPreguntas = seleccion;
                TestConfigurator.setNumeroPreguntas(numeroPreguntas);
            }
        });
    }

    @FXML
    void pasaTiempo(ActionEvent event) {
        if (isDamPanelVisible) {
            damPanel.setVisible(false);
            opoPanel.setVisible(false);
            panelNorte.setStyle("-fx-background-color: #ffffff");
            imageStudiar.setVisible(false);
            panelSur.setStyle("-fx-background-color: #e1a46d");
            imagenHobby.setVisible(true);
            futbolPanel.setVisible(true);


        } else {

            damPanel.setVisible(true);
            opoPanel.setVisible(true);
            panelNorte.setStyle("-fx-background-color: #737b7e");
            imageStudiar.setVisible(true);
            panelSur.setStyle("-fx-background-color: #ffffff");
            imagenHobby.setVisible(false);
            futbolPanel.setVisible(false);
        }

        isDamPanelVisible = !isDamPanelVisible; // cambia el estado
    }

    @FXML
    void perfilUser(ActionEvent event) {

    }


    @FXML
    void irAOpo(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/test-view.fxml"));
        changeSceneMethodWithMouseEvent(loader, event);
    }


    @FXML
    void irADam(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/test-view.fxml"));
        changeSceneMethodWithMouseEvent(loader, event);
    }

    @FXML
    void irAfutbol(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/test-view.fxml"));
        changeSceneMethodWithMouseEvent(loader, event);
    }


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


        futbolPanel.setVisible(true);
        damPanel.setVisible(true);
        opoPanel.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUser(); // Inicializa el usuario
        initTime(); // Inicializa el reloj
        futbolPanel.setVisible(false);
        panelSur.setStyle("-fx-background-color: #ffffff");
        imagenHobby.setVisible(false);
    }

    @FXML
    void exitMethod(MouseEvent event) {
        System.exit(0);
    } // TODO: 12/05/2023 implementar un método para salir de la aplicación


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
