package org.proyecto.tfgfront.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField contrasenaLogin;

    @FXML
    private Pane panelWrong;

    @FXML
    private TextField usuarioLogin;

    private UniRestController uniRest = new UniRestController();

    private Usuario user;



    @FXML
    void accionBoton(ActionEvent event) throws IOException {
        boolean usuarioValido = false;

        user = uniRest.login(usuarioLogin.getText(),contrasenaLogin.getText());
        if (user != null){
            if (user.getEmail().equals(usuarioLogin.getText()) && user.getPassword().equals(contrasenaLogin.getText())) {
                usuarioValido = true;
            }else {
                panelWrong.setVisible(true);
            }

        }
        if (usuarioValido) {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainmenu-view.fxml"));

                changeSceneMethod(loader, event);

        } else {
            panelWrong.setVisible(true);
        }
    }



    public void initialize(){
        usuarioLogin.setPromptText("Introduce Email");
        usuarioLogin.setStyle("-fx-prompt-text-fill: gray;");
        usuarioLogin.setAlignment(Pos.CENTER);
        contrasenaLogin.setPromptText("Introduce Contraseña");
        contrasenaLogin.setStyle("-fx-prompt-text-fill: gray;");
        contrasenaLogin.setAlignment(Pos.CENTER);

    }

    /**
     * Método que cambia de escena
     * @param loader cargador de la escena
     * @throws IOException
     */
    private static void changeSceneMethod(FXMLLoader loader,ActionEvent event) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        //cerrar la vista anterior
        Stage loginStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        loginStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLogin.setPromptText("Introduce Email");
        usuarioLogin.setStyle("-fx-prompt-text-fill: gray;");
        usuarioLogin.setAlignment(Pos.CENTER);
        contrasenaLogin.setPromptText("Introduce Contraseña");
        contrasenaLogin.setStyle("-fx-prompt-text-fill: gray;");
        contrasenaLogin.setAlignment(Pos.CENTER);
    }
}