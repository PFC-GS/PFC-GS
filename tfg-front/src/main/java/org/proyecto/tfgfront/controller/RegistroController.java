package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.proyecto.tfgfront.model.Usuario;

import java.net.URL;
import java.util.*;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;


public class RegistroController implements Initializable {
    @FXML
    private TextField apellidosUsuario;

    @FXML
    private TextField emailUsuario;


    @FXML
    private TextField nombreUsuario;

    @FXML
    private PasswordField contrasenaAlta;
    @FXML
    private Pane panelWrong;
    private UniRestController uniRest = new UniRestController();

    @FXML
    void enviarAltaRegistro(ActionEvent event) {

        conditionsRedText();
        if (!nombreUsuario.getText().isEmpty() && !apellidosUsuario.getText().isEmpty() && !emailUsuario.getText().isEmpty() && !contrasenaAlta.getText().isEmpty()){
            panelWrong.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro realizado con exito");
            soloBotonAceptar(alert);
            Usuario user = createUsuario();

            uniRest.altaUsuario(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
            changeSceneMethod(loader, event);

        }else {
            panelWrong.setVisible(true);
        }
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarTextField(nombreUsuario, "Nombre");
        configurarTextField(apellidosUsuario, "Apellidos");
        configurarTextField(emailUsuario, "Correo electrónico");
        configurarTextField(contrasenaAlta, "Contraseña");

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
