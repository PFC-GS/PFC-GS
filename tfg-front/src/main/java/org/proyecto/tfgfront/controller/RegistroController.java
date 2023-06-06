package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.proyecto.tfgfront.model.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

/**
 * Clase controladora de la vista registro
 */
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
    private final String pattern = "^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";

    /**
     * Método que ejecuta el radio button de la política de privacidad
     *
     * @param event evento
     */
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

    /**
     * Método que envia al backend el alta de un usuario
     *
     * @param event evento
     */
    @FXML
    void enviarAltaRegistro(ActionEvent event) {
        if (!radioPolitica.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Debe aceptar la politica de privacidad");
            soloBotonAceptar(alert);
        } else {
            conditionsRedText();
            compruebaDatosDeAlta(event);
        }
    }

    /**
     * Método que comprueba los datos de alta de un usuario
     *
     * @param event evento
     */
    private void compruebaDatosDeAlta(ActionEvent event) {
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

    /**
     * Método que para que el usuario no ponga caracteres especiales en los campos de email
     *
     * @param email
     * @return boolean
     */
    private boolean validateEmail(String email) {
        return email.matches(pattern);
    }

    /**
     * Método que crea un usuario con los datos introducidos
     *
     * @return Usuario
     */
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

    /**
     * Método que quita los botones que no sean aceptar dentro de un alert
     *
     * @param alert alert
     */
    private void soloBotonAceptar(Alert alert) {
        //quitar botones que no sean aceptar
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().clear();
        Button okButton = new Button("Aceptar");
        okButton.setOnAction(e -> alert.hide());
        buttonBar.getButtons().add(okButton);
        alert.showAndWait();
    }

    /**
     * Método que carga la vista de login
     *
     * @param event evento
     */
    @FXML
    void volverALogin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        changeSceneMethod(loader, event);
    }

    /**
     * Boton que acepta la ley de protección de datos
     *
     * @param event evento
     */
    @FXML
    void aceptarLey(ActionEvent event) {
        scrollpane.setVisible(false);
        btnAceptarLey.setVisible(false);
        lbLey.setVisible(false);
        radioPolitica.setVisible(true);
    }

    /**
     * Método que lee un archivo de texto (en este caso la ley de protección de datos)
     *
     * @param rutaArchivo ruta del archivo
     * @return String
     */
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

    /**
     * Método que inicializa la vista
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarTextField(nombreUsuario, "Nombre");
        configurarTextField(apellidosUsuario, "Apellidos");
        configurarTextField(emailUsuario, "Correo electrónico");
        configurarTextField(contrasenaAlta, "Contraseña");

        String ley = leerArchivoTexto("proteccionDatos.txt");
        lbLey.setText(ley);
    }

    /**
     * Método que configura los textfield
     *
     * @param textField       textfield
     * @param textoSugerencia texto sugerencia
     */
    private void configurarTextField(TextField textField, String textoSugerencia) {
        textField.setPromptText(textoSugerencia);
        textField.getStyleClass().add("textField");
    }

    /**
     * Método que pone en rojo los campos que no se han rellenado
     */
    private void conditionsRedText() {
        List<TextField> textFields = Arrays.asList(nombreUsuario, apellidosUsuario, emailUsuario, contrasenaAlta);
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.getStyleClass().add("redText");
            }
        }
    }
}
