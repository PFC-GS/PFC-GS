package org.proyecto.tfgfront.controller;

import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.util.List;

public class TableMainMenuViewController {

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



    public void mostrarCategoria() {

        List<Categoria> categorias = uniRest.httpCategoria();

        // Creamos un observable list a partir de la lista de categorías
        ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList(categorias);

        // Vinculamos las columnas de la tabla con los atributos de la clase Categoria
        idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Agregamos la lista de categorías a la tabla
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

    //metodo para leer la tabla de categorias de la base de datos sin retrofit
//    public void mostrarCategoria() {
//        try {
//            URL url = new URL("http://localhost:8080/categorias");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            String json = "";
//            String output;
//            while ((output = br.readLine()) != null) {
//                json += output;
//            }
//
//            conn.disconnect();
//
//            Gson gson = new Gson();
//            Categoria[] categorias = gson.fromJson(json, Categoria[].class);
//
//            ObservableList<Categoria> categoriaObservableList = FXCollections.observableArrayList(Arrays.asList(categorias));
//            idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
//            nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//            categoriaTabla.setItems(categoriaObservableList);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}


