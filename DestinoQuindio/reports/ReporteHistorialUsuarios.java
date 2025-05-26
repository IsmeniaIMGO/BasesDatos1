package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import src.dao.ConexionBD;

import java.io.FileOutputStream;
import java.sql.*;

public class ReporteHistorialUsuarios {

    public static void generar(String rutaArchivo) {
        String sql = """
            SELECT 
                u.cc, 
                u.nombre, 
                h.fecha,
                h.descripcion
            FROM HistorialSesion h
            JOIN Usuario u ON h.id = u.historialsesion
            ORDER BY h.fecha DESC
        """;

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();

            // Título
            Paragraph titulo = new Paragraph("Historial de Sesión de Usuarios",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));

            // Tabla
            PdfPTable tabla = new PdfPTable(4); // 4 columnas
            tabla.setWidthPercentage(100);
            tabla.addCell("Cédula");
            tabla.addCell("Nombre");
            tabla.addCell("Fecha");
            tabla.addCell("Descripción");

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tabla.addCell(String.valueOf(rs.getInt("cc")));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("fecha"));
                    tabla.addCell(rs.getString("descripcion"));
                }

            } catch (SQLException e) {
                System.err.println("❌ Error al obtener historial: " + e.getMessage());
            }

            doc.add(tabla);
            doc.close();
            System.out.println("✅ Reporte generado en: " + rutaArchivo);

        } catch (Exception e) {
            System.err.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}
