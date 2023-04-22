package org.proyecto.tfgfront;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class ExamToolFront extends Application {


    @Override
    public void start(Stage stage) throws IOException {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.ROUTE_APP_MAIN)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        GeneratorControllerClient generatorControllerClient = retrofit.create(GeneratorControllerClient.class);

try {
    Parent root = FXMLLoader.load(getClass().getResource("loginVista.fxml"));
    Scene scene1 = new Scene(root);
    stage.setScene(scene1);
    stage.show();
}catch (Exception e){
    e.printStackTrace();
}


//        FXMLLoader fxmlLoader = new FXMLLoader(ExamToolFront.class.getResource("loginVista.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("ExamTool App");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}