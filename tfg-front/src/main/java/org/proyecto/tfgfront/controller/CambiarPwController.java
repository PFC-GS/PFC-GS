package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import static org.proyecto.tfgfront.util.Util.changeSceneMethod;

public class CambiarPwController implements Initializable {

    @FXML
    private PasswordField passUser;

    @FXML
    private PasswordField passUser2;

    @FXML
    private PasswordField passUser3;
    @FXML
    private ImageView imgPw;

    @FXML
    private ImageView imgPw2;

    @FXML
    private ImageView imgPw3;

    @FXML
    private Label repitePw;
    @FXML
    private Label pwActual;

    @FXML
    private Label confimaPw;
    private UniRestController uniRest = new UniRestController();
    private Usuario user = Session.getUsuario();
    private boolean primeraVez = true;


    @FXML
    void cancelar(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/perfil-view.fxml"));
        changeSceneMethod(loader, event);
    }

    @FXML
    void enviarDatos(ActionEvent event) {

        if (!passUser.getText().equals(user.getPassword()) || passUser.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseña actual incorrecta");
            alert.setContentText("Por favor, ingrese su contraseña actual.");
            alert.showAndWait();
        } else if (passUser.getText().equals(user.getPassword())) {
            visibility(true);
            pwActual.setVisible(false);
            passUser.setVisible(false);
            imgPw.setVisible(false);

            if (primeraVez) {
                primeraVez = false;
            } else {

                if (!passUser2.getText().equals(passUser3.getText()) || passUser2.getText().isEmpty() || passUser3.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Las contraseñas no coinciden");
                    alert.setContentText("Por favor, ingrese la misma contraseña en ambos campos.");
                    alert.showAndWait();
                } else {

                    modificaUsuario(passUser2.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Contraseña");
                    alert.setHeaderText("Contraseña modificada con exito." + "\n" +
                            "Los cambios surtirán efecto en su próximo inicio de sesión");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/perfil-view.fxml"));
                    changeSceneMethod(loader, event);
                }
            }


        }

    }

    private void modificaUsuario(String pass) {
        Usuario userModificado = new Usuario();

        userModificado.setId(user.getId());
        userModificado.setNombre(user.getNombre());
        userModificado.setApellidos(user.getApellidos());
        userModificado.setEmail(user.getEmail());
        userModificado.setPassword(pass);
        userModificado.setAdmin(user.isAdmin());
        userModificado.setTests(new ArrayList<>());
        userModificado.setCategorias(new HashSet<>());
        System.out.println(userModificado);


        uniRest.modificaUsuario(userModificado);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visibility(false);
    }

    private void visibility(Boolean visible) {
        imgPw2.setVisible(visible);
        imgPw3.setVisible(visible);
        repitePw.setVisible(visible);
        confimaPw.setVisible(visible);
        passUser2.setVisible(visible);
        passUser3.setVisible(visible);
    }
}
