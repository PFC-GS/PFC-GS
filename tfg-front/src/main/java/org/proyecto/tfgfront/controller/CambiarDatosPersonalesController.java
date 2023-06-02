package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.net.URL;
import java.util.*;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class CambiarDatosPersonalesController implements Initializable {


    @FXML
    private TextField apellidosUsuario;

    @FXML
    private TextField emailUsuario;

    @FXML
    private TextField nombreUsuario;
    private UniRestController uniRest = new UniRestController();
    private Usuario user = Session.getUsuario();




    @FXML
    void cancelar(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/perfil-view.fxml"));
        changeSceneMethod(loader, event);
    }

    @FXML
    void enviarDatos(ActionEvent event) {

        String nombre = nombreUsuario.getText().isEmpty() ? user.getNombre() : nombreUsuario.getText();
        String apellidos = apellidosUsuario.getText().isEmpty() ? user.getApellidos() : apellidosUsuario.getText();
        String email = emailUsuario.getText().isEmpty() ? user.getEmail() : emailUsuario.getText();

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
                modificaUsuario(nombre, apellidos, email);

                uniRest.altaUsuario(user);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/perfil-view.fxml"));
                changeSceneMethod(loader, event);
            }
    }

    public void modificaUsuario(String nombre, String apellidos, String email) {
        Usuario userModificado = new Usuario();

        userModificado.setId(user.getId());
        userModificado.setNombre(nombre);
        userModificado.setApellidos(apellidos);
        userModificado.setEmail(email);
        userModificado.setPassword(user.getPassword());
        userModificado.setAdmin(user.isAdmin());
        userModificado.setTests(new ArrayList<>());
        userModificado.setCategorias(new HashSet<>());
        System.out.println(userModificado);


        uniRest.modificaUsuario(userModificado);

    }
    private boolean validateEmail(String email) {
        String emailPattern = "^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";

        return email.matches(emailPattern);
    }
    private void configurarTextField(TextField textField, String textoSugerencia) {
        textField.setPromptText(textoSugerencia);
        textField.getStyleClass().add("textField");
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTextField(nombreUsuario, user.getNombre());
        configurarTextField(apellidosUsuario, user.getApellidos());
        configurarTextField(emailUsuario, user.getEmail());
    }
}



