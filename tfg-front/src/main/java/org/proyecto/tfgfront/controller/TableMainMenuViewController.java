package org.proyecto.tfgfront.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Categoria;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableMainMenuViewController implements Initializable {

    @FXML
    public Button btnAtras;
    @FXML
    public Button btnLogin;
    @FXML
    public TableView<Categoria> categoriaTabla;
    @FXML
    public TableColumn<Categoria, Integer> idCategoria;
    @FXML
    public TableColumn<Categoria, String> nombreCategoria;
    private UniRestController uniRest = new UniRestController();
    private Usuario user;



    public void mostrarCategoria() {

        List<Categoria> categorias = uniRest.httpCategoria();

        ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList(categorias);

        idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaTabla.setItems(listaCategorias);
    }

    public void irALogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/login-view.fxml"));
        changeSceneMethod(loader, event);
    }

    public void irAtras(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/proyecto/tfgfront/mainMenu-view.fxml"));
        changeSceneMethod(loader, event);
    }
    private static void changeSceneMethod(FXMLLoader loader, ActionEvent event) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = Session.getUsuario();
    }
}


