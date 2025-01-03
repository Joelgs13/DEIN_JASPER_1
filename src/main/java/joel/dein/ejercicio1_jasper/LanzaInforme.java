package joel.dein.ejercicio1_jasper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import joel.dein.ejercicio1_jasper.BBDD.ConexionBBDD;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase principal que lanza la aplicación de informes de JavaFX y genera un informe a partir de datos de una base de datos.
 * Extiende de {@link javafx.application.Application} para crear y mostrar la interfaz gráfica de usuario.
 */
public class LanzaInforme extends Application {

    /**
     * Metodo principal de ejecución de la aplicación. Llama al metodo {@link #start(Stage)}
     * para inicializar y mostrar el informe basado en JasperReports.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX
    }

    /**
     * Metodo encargado de la lógica principal para generar y mostrar el informe.
     * Este metodo establece la conexión a la base de datos, carga el informe JasperReport,
     * rellena el informe con datos de la base de datos y finalmente lo muestra usando un visor de informes.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        ConexionBBDD bbdd;
        try {
            // Se establece la conexión a la base de datos
            bbdd = new ConexionBBDD();

            // Se carga el informe desde los recursos
            InputStream reportStream = bbdd.getClass().getResourceAsStream("/JasperReport/Coffee.jasper");
            if (reportStream == null) {
                System.out.println("El archivo Coffee.jrxml no se encontró.");
                return; // Salimos si no encontramos el archivo
            }

            // Se carga el informe JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", bbdd.getClass().getResource("/img/").toString());

            // Se rellena el informe con los datos de la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ConexionBBDD.getConnection());

            // Se muestra el informe en un visor de JasperReports
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace(); // Para depuración en consola
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
        }
    }

    /**
     * Muestra una ventana emergente con un mensaje de error.
     *
     * @param titulo El título de la ventana emergente de error.
     * @param mensaje El mensaje que se mostrará en la ventana emergente.
     */
    private void mostrarError(String titulo, String mensaje) {
        // Crear una ventana emergente de tipo "error"
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // No queremos un encabezado
        alert.setContentText(mensaje); // El mensaje que queremos mostrar
        alert.showAndWait(); // Mostrar el mensaje y esperar a que el usuario lo cierre
    }
}
