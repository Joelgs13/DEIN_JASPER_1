module joel.dein.ejercicio1_jasper {
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;


    opens joel.dein.ejercicio1_jasper to javafx.fxml;
    exports joel.dein.ejercicio1_jasper;
}