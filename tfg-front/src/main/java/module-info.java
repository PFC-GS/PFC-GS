module org.proyecto.tfgfront {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires unirest.java;
    requires org.json;


    opens org.proyecto.tfgfront to javafx.fxml;
    exports org.proyecto.tfgfront;
    exports org.proyecto.tfgfront.controller;
    opens org.proyecto.tfgfront.model to javafx.base;
    exports org.proyecto.tfgfront.model;
    opens org.proyecto.tfgfront.controller to javafx.base, javafx.fxml;


}