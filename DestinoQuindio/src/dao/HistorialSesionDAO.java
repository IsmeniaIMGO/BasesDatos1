package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistorialSesionDAO {

    public boolean insertarSesion(HistorialSesion h) {
        String sql = "INSERT INTO HistorialSesion (id, fecha, descripcion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, h.getId());
            stmt.setDate(2, new java.sql.Date(h.getFecha().getTime()));
            stmt.setString(3, h.getDescripcion());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al registrar historial de sesión: " + e.getMessage());
            return false;
        }
    }

    public List<HistorialSesion> listarHistorial() {
        List<HistorialSesion> lista = new ArrayList<>();
        String sql = "SELECT * FROM HistorialSesion";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HistorialSesion h = new HistorialSesion();
                h.setId(rs.getInt("id"));
                h.setFecha(rs.getDate("fecha"));
                h.setDescripcion(rs.getString("descripcion"));
                lista.add(h);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar historial de sesiones: " + e.getMessage());
        }

        return lista;
    }
}
