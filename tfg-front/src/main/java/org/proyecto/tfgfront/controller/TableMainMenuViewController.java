package org.proyecto.tfgfront.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.proyecto.tfgfront.model.Categoria;
import org.proyecto.tfgfront.model.TestRetrofit;

import java.io.IOException;

public class TableMainMenuViewController {

    @FXML
    public Button btnAtras;
    @FXML
    public Button btnLogin;
    @FXML
    public TableView categoriaTabla;
    @FXML
    public TableColumn idCategoria;
    @FXML
    public TableColumn nombreCategoria;



    public void mostrarCategoria() {
        TestRetrofit testRetrofit = new TestRetrofit();
        testRetrofit.getDataFromJson();
        idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ObservableList<Categoria> listaCategorias = testRetrofit.getCategoriaObservableList();
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
//metodo con lista hardcodeada para probar la tabla
//    public void mostrarCategoria() {
//        idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//
//        // Creamos una lista de objetos Categoria hardcodeados
//        ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList(
//                new Categoria(1, "Categoría 1"),
//                new Categoria(2, "Categoría 2")
//        );
//
//        // Establecemos la lista en la tabla
//        categoriaTabla.setItems(listaCategorias);
//    }
}


