package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import src.dao.ConexionBD;

import java.io.FileOutputStream;
import java.sql.*;

public class ReporteViajesEstado {

    public static void generar(String rutaArchivo) {
        String sql = """
            SELECT 
                e.nombre AS estado,
                COUNT(v.id) AS cantidad,
                e.descripcion
            FROM Estado e
            LEFT JOIN Viaje v ON v.estado = e.id
            GROUP BY e.id, e.nombre, e.descripcion
            ORDER BY e.id
        """;

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();

            Paragraph titulo = new Paragraph("Reporte de Viajes por Estado",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.addCell("Estado");
            tabla.addCell("Cantidad de Viajes");
            tabla.addCell("Descripción");

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tabla.addCell(rs.getString("estado"));
                    tabla.addCell(String.valueOf(rs.getInt("cantidad")));
                    tabla.addCell(rs.getString("descripcion"));
                }

            } catch (SQLException e) {
                System.err.println("❌ Error al consultar viajes por estado: " + e.getMessage());
            }

            doc.add(tabla);
            doc.close();
            System.out.println("✅ Reporte generado en: " + rutaArchivo);

        } catch (Exception e) {
            System.err.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}
