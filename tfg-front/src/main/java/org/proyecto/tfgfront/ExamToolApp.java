package org.proyecto.tfgfront;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamToolApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExamToolApp.class.getResource("login-view.fxml"));
try {


    Scene scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
   
    stage.show();
} catch (IOException e) {
    e.printStackTrace();
}

    }

    public static void main(String[] args) {

        launch();

    }
}