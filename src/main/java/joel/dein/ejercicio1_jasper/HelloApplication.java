package joel.dein.ejercicio1_jasper;

import javafx.application.Application;
import javafx.stage.Stage;
import joel.dein.ejercicio1_jasper.BBDD.ConexionBBDD;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication{

    public static void main(String[] args) {
        ConexionBBDD bbdd;
        try {
            bbdd = new ConexionBBDD();
            InputStream reportStream = bbdd.getClass().getResourceAsStream("/JasperReport/Coffee.jasper");
            if (reportStream == null) {
                System.out.println("El archivo Coffee.jrxml no se encontró.");
            } else {
                System.out.println("Ruta del archivo: " + reportStream);
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", bbdd.getClass().getResource("/img/").toString());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ConexionBBDD.getConnection());
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } catch (SQLException | JRException e) {
            throw new RuntimeException(e);
        }


    }
}
