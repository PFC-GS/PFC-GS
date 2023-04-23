module org.proyecto.tfgfront {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires lombok;
    requires retrofit2.converter.gson;
    requires okhttp3;


    opens org.proyecto.tfgfront to javafx.fxml;
    exports org.proyecto.tfgfront;
    exports org.proyecto.tfgfront.controller;
    opens org.proyecto.tfgfront.controller to javafx.fxml;
    opens org.proyecto.tfgfront.model to javafx.base;
    ///////////////////////////// probando esos opens y exports///////////////////////////
    opens org.proyecto.tfgfront.model.interfaz to retrofit2;
    exports org.proyecto.tfgfront.model;
    exports org.proyecto.tfgfront.model.interfaz;

}