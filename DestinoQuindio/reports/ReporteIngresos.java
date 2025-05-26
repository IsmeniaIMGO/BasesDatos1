package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import src.dao.ConexionBD;

import java.io.FileOutputStream;
import java.sql.*;

public class ReporteIngresos {

    public static void generar(String rutaArchivo) {
        String sql = """
            SELECT p.id AS id, p.valor AS valor, mp.nombre AS metodoPago
            FROM Pago p
            JOIN MetodoPago mp ON p.metodoPago = mp.id
        """;

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();

            // Título
            Paragraph titulo = new Paragraph("Reporte de Ingresos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));

            // Tabla
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.addCell("ID");
            tabla.addCell("Valor");
            tabla.addCell("Método de Pago");

            double total = 0;

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    double valor = rs.getDouble("valor");
                    String metodo = rs.getString("metodoPago");

                    tabla.addCell(String.valueOf(id));
                    tabla.addCell(String.format("$%,.2f", valor));
                    tabla.addCell(metodo);

                    total += valor; // Acumular total
                }

            } catch (SQLException e) {
                System.err.println("❌ Error al obtener datos: " + e.getMessage());
            }

            doc.add(tabla);

            // Línea vacía
            doc.add(new Paragraph(" "));

            // Total
            Paragraph totalParrafo = new Paragraph("Total Ingresos: " + String.format("$%,.2f", total),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            doc.add(totalParrafo);

            doc.close();
            System.out.println("✅ Reporte generado en: " + rutaArchivo);

        } catch (Exception e) {
            System.err.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}
