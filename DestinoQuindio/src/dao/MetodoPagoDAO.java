package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {

    public List<MetodoPago> listarMetodos() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM MetodoPago";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setId(rs.getInt("id"));
                mp.setNombre(rs.getString("nombre"));
                lista.add(mp);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar métodos de pago: " + e.getMessage());
        }

        return lista;
    }
}
