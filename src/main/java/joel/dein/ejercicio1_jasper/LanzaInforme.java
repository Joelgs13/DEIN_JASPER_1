package joel.dein.ejercicio1_jasper;

import joel.dein.ejercicio1_jasper.BBDD.ConexionBBDD;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase principal que lanza un informe utilizando JasperReports.
 *
 * Esta clase se encarga de:
 * - Establecer la conexión con la base de datos.
 * - Cargar el archivo .jasper desde los recursos.
 * - Configurar los parámetros necesarios para el informe.
 * - Generar y mostrar el informe mediante JasperViewer.
 */
public class LanzaInforme {

    /**
     * Metodo principal que ejecuta el informe JasperReports.
     *
     * El metodo realiza las siguientes tareas:
     * 1. Se conecta a la base de datos utilizando la clase ConexionBBDD.
     * 2. Carga el archivo Jasper (.jasper) del directorio de recursos.
     * 3. Configura los parámetros necesarios para el informe, como la ruta de imágenes.
     * 4. Rellena el informe con los datos obtenidos de la base de datos.
     * 5. Muestra el informe al usuario mediante JasperViewer.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     * @throws RuntimeException Si ocurre un error de SQL o un error relacionado con JasperReports.
     */
    public static void main(String[] args) {
        ConexionBBDD bbdd;
        try {
            // Se establece la conexión a la base de datos
            bbdd = new ConexionBBDD();

            // Se carga el informe desde los recursos
            InputStream reportStream = bbdd.getClass().getResourceAsStream("/JasperReport/Coffee.jasper");
            if (reportStream == null) {
                System.out.println("El archivo Coffee.jrxml no se encontró.");
            } else {
                System.out.println("Ruta del archivo: " + reportStream);
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
            throw new RuntimeException(e);
        }
    }
}
