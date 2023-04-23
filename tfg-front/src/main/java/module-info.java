module org.proyecto.tfgfront {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens org.proyecto.tfgfront to javafx.fxml;
    exports org.proyecto.tfgfront;
    exports org.proyecto.tfgfront.controller;
    opens org.proyecto.tfgfront.controller to javafx.fxml;
}