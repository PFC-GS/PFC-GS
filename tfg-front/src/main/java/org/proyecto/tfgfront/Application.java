package org.proyecto.tfgfront;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.proyecto.tfgfront.clients.GeneratorControllerClient;
import org.proyecto.tfgfront.constants.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class Application extends javafx.application.Application {


    @Override
    public void start(Stage stage) throws IOException {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.ROUTE_APP_MAIN)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        GeneratorControllerClient generatorControllerClient = retrofit.create(GeneratorControllerClient.class);


        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ExamTool App");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}