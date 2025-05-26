package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import src.dao.ConexionBD;

import java.io.FileOutputStream;
import java.sql.*;

public class ReporteViajeConductor {

    public static void generar(String rutaArchivo) {
        String sql = """
            SELECT 
                u.cc,
                u.nombre,
                v.id AS id_viaje,
                v.fecha,
                mo.nombre AS origen,
                md.nombre AS destino,
                e.nombre AS estado,
                p.valor
            FROM Viaje v
            JOIN Conductor c ON v.conductor = c.cc
            JOIN Usuario u ON c.cc = u.cc
            JOIN Ubicacion uo ON v.ubicacionOrigen = uo.id
            JOIN Municipio mo ON uo.municipio = mo.id
            JOIN Ubicacion ud ON v.ubicacionDestino = ud.id
            JOIN Municipio md ON ud.municipio = md.id
            JOIN Estado e ON v.estado = e.id
            JOIN Pago p ON v.pago = p.id
            ORDER BY u.nombre, v.fecha DESC
        """;

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();

            Paragraph titulo = new Paragraph("Reporte de Viajes por Conductor",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(8);
            tabla.setWidthPercentage(100);
            tabla.addCell("CC Conductor");
            tabla.addCell("Nombre");
            tabla.addCell("ID Viaje");
            tabla.addCell("Fecha");
            tabla.addCell("Origen");
            tabla.addCell("Destino");
            tabla.addCell("Estado");
            tabla.addCell("Valor");

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tabla.addCell(rs.getString("cc"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("id_viaje"));
                    tabla.addCell(rs.getString("fecha"));
                    tabla.addCell(rs.getString("origen"));
                    tabla.addCell(rs.getString("destino"));
                    tabla.addCell(rs.getString("estado"));
                    tabla.addCell(String.valueOf(rs.getDouble("valor")));
                }
                 
            } catch (SQLException e) {
                System.err.println("❌ Error al consultar viajes: " + e.getMessage());
            }

            doc.add(tabla);
            doc.close();
            System.out.println("✅ Reporte generado en: " + rutaArchivo);

        } catch (Exception e) {
            System.err.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
    
}
