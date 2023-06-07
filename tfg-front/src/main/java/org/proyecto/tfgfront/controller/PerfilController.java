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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.proyecto.tfgfront.model.TestGestor;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;
import org.proyecto.tfgfront.util.Constants;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;
import static org.proyecto.tfgfront.util.Util.changeSceneMethodResultadoTestEnPerfil;

/**
 * Clase controladora de la vista perfil
 */
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
    private Label lbTotalTest;
    @FXML
    private Label lbVersion;
    @FXML
    private Button seguridad;
    @FXML
    private Button datosPersonales;

    @FXML
    private GridPane resultadoGrid;

    private UniRestController uniRest = new UniRestController();
    private List<TestGestor> listaTest = uniRest.getTestByUserId(Session.getUsuario().getId());

    private int testCount = 0;

    /**
     * Método que carga la vista de cambiar datos personales
     *
     * @param event evento
     */
    @FXML
    void cambiarDatosPersonales(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/cambiarDatosPersonales-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Método que carga la vista de cambiar contraseña
     *
     * @param event evento
     */
    @FXML
    void cambiarPw(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/cambiarPw-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Método que carga la vista de mainMenu
     *
     * @param event evento
     */
    @FXML
    void irAMainMenu(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Método que carga la vista de login
     *
     * @param event evento
     */
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

    /**
     * Método que inicializa la vista
     * Carga la lista de test hecha por el usuario y la muestra en la vista
     * Carga los datos del usuario y los muestra en la vista
     * Si se quiere ver los detalles de un test, se carga la vista de resultadoTestEnPerfil
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUser(); // Inicializa el usuario
        initTime(); // Inicializa el reloj
        lbVersion.setText(Constants.version);

        //compara los test por id de forma descendente
        listaTest.sort(Comparator.comparing((TestGestor t) -> t.getTest().getId()).reversed());

        int i = 0;
        int x = listaTest.size();
        if (listaTest != null) {
            for (TestGestor test : listaTest) {
                if (x == 0) {
                    x = 1;
                }
                // crear un nuevo VBox para cada test
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                vbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                HBox hboxNombre = createHBox("Numero de Test: " + x, true, 16);
                HBox hboxFecha = createHBox("Fecha: " + test.getTest().getFecha(), false, 14);
                HBox hboxCalificacion = createHBox("Calificación: " + test.getTest().getCalificacion(), false, 14);
                HBox hboxNumeroPreguntas = createHBox("Número de preguntas: " + test.getTest().getPreguntas().size(), false, 14);
                HBox hboxTestResult = createHBox(test.getTest().getCalificacion() >= 5 ? "Apto" : "No apto", false, 14);

                // crear el link para ver los detalles del test
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

                // añadir los HBox al VBox
                vbox.getChildren().addAll(hboxNombre, hboxFecha, hboxCalificacion, hboxNumeroPreguntas, hboxTestResult, hboxDetallesLink);

                // Añadir el VBox al GridPane
                resultadoGrid.add(vbox, i % 5, i / 5);
                i++;
                x--;
            }
        }
    }

    /**
     * Método que crea un HBox con un texto, un booleano para indicar si es negrita y un int para indicar el tamaño de la fuente
     *
     * @param text     texto
     * @param isBold   booleano para indicar si es negrita
     * @param fontSize int para indicar el tamaño de la fuente
     * @return HBox
     */
    private HBox createHBox(String text, boolean isBold, int fontSize) {
        Label label = new Label(text);
        String fontWeight = isBold ? "bold" : "normal";
        label.setStyle("-fx-font-weight: " + fontWeight + "; -fx-font-size: " + fontSize + "px; -fx-text-alignment: center;");
        HBox hbox = new HBox(label);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * Método que inicializa el reloj
     */
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

    /**
     * Método que recupera el usuario y lo muestra en la vista
     */
    private void initUser() {
        Usuario user = Session.getUsuario();
        lbRecuperaNombre.setText(user.getNombre() + " " + user.getApellidos());
        lbDatosNombre.setText(user.getNombre());
        lbDatosApellidos.setText(user.getApellidos());
        lbDatosCorreo.setText(user.getEmail());
        int totalTest = uniRest.getTestByUserId(Session.getUsuario().getId()).size();
        lbTotalTest.setText(String.valueOf(totalTest));
    }
}
