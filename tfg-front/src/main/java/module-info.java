module org.proyecto.tfgfront {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires lombok;


    opens org.proyecto.tfgfront to javafx.fxml;
    exports org.proyecto.tfgfront;
}