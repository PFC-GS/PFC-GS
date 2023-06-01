package org.proyecto.tfgfront.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.TestGestor;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;
import static org.proyecto.tfgfront.util.Util.changeSceneMethodResultadoTestEnPerfil;

public class PerfilController implements Initializable {

    @FXML
    private Label lbRecuperaNombre;

    @FXML
    private Label lbhora;

    @FXML
    private Label lbDatosApellidos;

    @FXML
    private Label lbDatosCorreo;

    @FXML
    private Label lbDatosNombre;

    @FXML
    private GridPane resultadoGrid;

    private UniRestController uniRest = new UniRestController();
    private List<TestGestor> listaTest = uniRest.getTestByUserId(Session.getUsuario().getId());

    private int testCount = 0;


    @FXML
    void irAMainMenu(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));
        changeSceneMethod(loader, event);
    }

    @FXML
    void logout(ActionEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUser(); // Inicializa el usuario
        initTime(); // Inicializa el reloj


        listaTest.sort(new Comparator<TestGestor>() {
            @Override
            public int compare(TestGestor t1, TestGestor t2) {
                return t2.getTest().getId().compareTo(t1.getTest().getId());
            }
        });

        int i = 0;
        int x = listaTest.size();
        if (listaTest != null) {
            for (TestGestor test : listaTest) {
                if (x == 0) {
                    x = 1;
                }
                // Create a new VBox for each test.
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                vbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                HBox hboxNombre = createHBox("Numero de Test: " + x, true, 16);
                HBox hboxFecha = createHBox("Fecha: " + test.getTest().getFecha(), false, 14);
                HBox hboxCalificacion = createHBox("Calificación: " + test.getTest().getCalificacion(), false, 14);
                HBox hboxNumeroPreguntas = createHBox("Número de preguntas: " + test.getTest().getPreguntas().size(), false, 14);
                HBox hboxTestResult = createHBox(test.getTest().getCalificacion() >= 5 ? "Apto" : "No apto", false, 14);


                // Create a Hyperlink.
                Hyperlink detallesLink = new Hyperlink("Ver Test");
                detallesLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/resultadoTestEnPerfil-view.fxml"));
                        changeSceneMethodResultadoTestEnPerfil(loader, event);
                        ResultadoTestEnPerfilController controller = loader.getController();
                        controller.setlistaTestGestor(test); // Asigna el testGestor a la instancia del controlador cargado


                    }

                });
                HBox hboxDetallesLink = new HBox(detallesLink);
                hboxDetallesLink.setAlignment(Pos.CENTER);

                // Add the HBoxes to the VBox.
                vbox.getChildren().addAll(hboxNombre, hboxFecha, hboxCalificacion, hboxNumeroPreguntas, hboxTestResult, hboxDetallesLink);

                // Add the VBox to your GridPane.
                resultadoGrid.add(vbox, i % 5, i / 5);
                i++;
                x--;
            }


        }
    }

    private HBox createHBox(String text, boolean isBold, int fontSize) {
        Label label = new Label(text);
        String fontWeight = isBold ? "bold" : "normal";
        label.setStyle("-fx-font-weight: " + fontWeight + "; -fx-font-size: " + fontSize + "px; -fx-text-alignment: center;");
        HBox hbox = new HBox(label);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }


    private void initTime() {
        final LocalTime[] horaActual = {LocalTime.now()};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    horaActual[0] = LocalTime.now();
                    String horaFormateada = horaActual[0].format(formatter);
                    this.lbhora.setText(horaFormateada);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void initUser() {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());
        lbDatosNombre.setText(user.getNombre());
        lbDatosApellidos.setText(user.getApellidos());
        lbDatosCorreo.setText(user.getEmail());
    }
}
