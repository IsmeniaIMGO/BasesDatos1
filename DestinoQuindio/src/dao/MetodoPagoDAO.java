package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {


    public List<MetodoPago> listarMetodosPago() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM MetodoPago"; // Ajusta la consulta a tu tabla

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MetodoPago metodo = new MetodoPago();
                metodo.setId(rs.getInt("id"));
                metodo.setNombre(rs.getString("nombre"));
                lista.add(metodo);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar métodos de pago: " + e.getMessage());
        }

        return lista;
    }
}
