package joel.dein.ejercicio1_jasper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Ruta del archivo JRXML (puede ser compilado .jasper o .jrxml)
        String reportPath = HelloApplication.class.getResource("/Coffee.jasper").getPath();
        if (reportPath == null) {
            System.out.println("El archivo Coffee.jrxml no se encontró.");
        } else {
            System.out.println("Ruta del archivo: " + reportPath);
        }

        // Crear los parámetros para el informe (si es necesario)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parametro1", "valor"); // Aquí puedes agregar parámetros si los necesitas

        // Cargar el informe y llenar los datos
        try {
            // Cargar el informe
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);

            // Llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Mostrar el informe usando JasperViewer en un JFXPanel
            // Esto abrirá una ventana que muestra el informe
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
