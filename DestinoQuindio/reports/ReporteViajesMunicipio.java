package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import src.dao.ConexionBD;

import java.io.FileOutputStream;
import java.sql.*;

public class ReporteViajesMunicipio {

    public static void generar(String rutaArchivo) {
        String sql = """
            SELECT 
                m.nombre AS municipio,
                COUNT(DISTINCT vo.id) AS origen,
                COUNT(DISTINCT vd.id) AS destino
            FROM Municipio m
            LEFT JOIN Ubicacion uo ON m.id = uo.municipio
            LEFT JOIN Viaje vo ON uo.id = vo.ubicacionOrigen
            LEFT JOIN Ubicacion ud ON m.id = ud.municipio
            LEFT JOIN Viaje vd ON ud.id = vd.ubicacionDestino
            GROUP BY m.nombre
            ORDER BY m.nombre
        """;

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();

            Paragraph titulo = new Paragraph("Reporte de Viajes por Municipio",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.addCell("Municipio");
            tabla.addCell("Viajes de Origen");
            tabla.addCell("Viajes de Destino");

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tabla.addCell(rs.getString("municipio"));
                    tabla.addCell(String.valueOf(rs.getInt("origen")));
                    tabla.addCell(String.valueOf(rs.getInt("destino")));
                }

            } catch (SQLException e) {
                System.err.println("❌ Error al obtener datos: " + e.getMessage());
            }

            doc.add(tabla);
            doc.close();
            System.out.println("✅ Reporte generado en: " + rutaArchivo);

        } catch (Exception e) {
            System.err.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}
