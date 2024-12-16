module joel.dein.ejercicio1_jasper {
    requires javafx.controls;
    requires javafx.fxml;


    opens joel.dein.ejercicio1_jasper to javafx.fxml;
    exports joel.dein.ejercicio1_jasper;
}