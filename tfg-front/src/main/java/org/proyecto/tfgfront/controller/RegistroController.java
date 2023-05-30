package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.proyecto.tfgfront.model.Usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;


public class RegistroController implements Initializable {
    @FXML
    private TextField apellidosUsuario;

    @FXML
    private TextField emailUsuario;
    @FXML
    private Label lbLey;

    @FXML
    private TextField nombreUsuario;

    @FXML
    private PasswordField contrasenaAlta;
    @FXML
    private Pane panelWrong;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Button btnAceptarLey;
    @FXML
    private RadioButton radioPolitica;
    private UniRestController uniRest = new UniRestController();


    @FXML
    void mostrarPoliticaPrivacidad(MouseEvent event) {

        scrollpane.setVisible(true);
        lbLey.setVisible(true);
        radioPolitica.setVisible(false);


        scrollpane.setOnScroll(eventScroll -> {
            if (scrollpane.getVvalue() == 1.0) {
                btnAceptarLey.setVisible(true);
                scrollpane.setOnScroll(null);// Desactivar el evento después de mostrar el botón
                // Volver a la parte superior
            }
        });
        scrollpane.setVvalue(0.0); //solventamos bug del scroll en la parte baja si se ha visto antes
    }

    @FXML
    void enviarAltaRegistro(ActionEvent event) {

        if (!radioPolitica.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Debe aceptar la politica de privacidad");
            soloBotonAceptar(alert);
        } else {
            conditionsRedText();
            if (!nombreUsuario.getText().isEmpty() && !apellidosUsuario.getText().isEmpty() && !emailUsuario.getText().isEmpty() && !contrasenaAlta.getText().isEmpty()) {
                panelWrong.setVisible(false);
                String email = emailUsuario.getText();
                if (!validateEmail(email)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Formato de correo electrónico incorrecto");
                    alert.setContentText("Por favor, ingrese un correo electrónico con formato válido." + "\n" +
                            "Ejemplo: xxxx@xxxx.xxx");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registro");
                    alert.setHeaderText("Registro realizado con exito");
                    soloBotonAceptar(alert);
                    Usuario user = createUsuario();

                    uniRest.altaUsuario(user);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
                    changeSceneMethod(loader, event);
                }
            } else {
                panelWrong.setVisible(true);
            }
        }
    }

    private boolean validateEmail(String email) {
        String emailPattern = "^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";

        return email.matches(emailPattern);
    }

    private Usuario createUsuario() {
        Usuario user = new Usuario();
        user.setNombre(nombreUsuario.getText());
        user.setApellidos(apellidosUsuario.getText());
        user.setEmail(emailUsuario.getText());
        user.setPassword(contrasenaAlta.getText());
        user.setAdmin(false);
        user.setTests(new ArrayList<>());
        user.setCategorias(new HashSet<>());
        return user;
    }

    private void soloBotonAceptar(Alert alert) {
        //quitar botones que no sean aceptar
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().clear();
        Button okButton = new Button("Aceptar");
        okButton.setOnAction(e -> alert.hide());
        buttonBar.getButtons().add(okButton);
        alert.showAndWait();
    }


    @FXML
    void volverALogin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        changeSceneMethod(loader, event);
    }

    @FXML
    void aceptarLey(ActionEvent event) {
        scrollpane.setVisible(false);
        btnAceptarLey.setVisible(false);
        lbLey.setVisible(false);
        radioPolitica.setVisible(true);


    }

    public String leerArchivoTexto(String rutaArchivo) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaArchivo);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            StringBuilder contenido = new StringBuilder();
            while (scanner.hasNextLine()) {
                contenido.append(scanner.nextLine()).append("\n");
            }
            return contenido.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Devuelve una cadena vacía en caso de error
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarTextField(nombreUsuario, "Nombre");
        configurarTextField(apellidosUsuario, "Apellidos");
        configurarTextField(emailUsuario, "Correo electrónico");
        configurarTextField(contrasenaAlta, "Contraseña");

        String ley = leerArchivoTexto("proteccionDatos.txt");
        lbLey.setText(ley);


    }

    private void configurarTextField(TextField textField, String textoSugerencia) {
        textField.setPromptText(textoSugerencia);
        textField.getStyleClass().add("textField");
    }

    private void conditionsRedText() {
        List<TextField> textFields = Arrays.asList(nombreUsuario, apellidosUsuario, emailUsuario, contrasenaAlta);

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.getStyleClass().add("redText");
            }
        }
    }

}
