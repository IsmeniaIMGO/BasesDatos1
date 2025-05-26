package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {

    public boolean insertarUbicacion(Ubicacion u) {
        String sql = "INSERT INTO Ubicacion (id, longitud, latitud, municipio) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, u.getId());
            stmt.setDouble(2, u.getLongitud());
            stmt.setDouble(3, u.getLatitud());
            stmt.setInt(4, u.getMunicipio());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar ubicación: " + e.getMessage());
            return false;
        }
    }

    public Ubicacion obtenerUbicacionPorId(int id) {
        String sql = "SELECT * FROM Ubicacion WHERE id = ?";
        Ubicacion u = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                u = new Ubicacion();
                u.setId(rs.getInt("id"));
                u.setLongitud(rs.getDouble("longitud"));
                u.setLatitud(rs.getDouble("latitud"));
                u.setMunicipio(rs.getInt("municipio"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener ubicación: " + e.getMessage());
        }

        return u;
    }

    public List<Ubicacion> listarUbicaciones() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Ubicacion";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ubicacion u = new Ubicacion();
                u.setId(rs.getInt("id"));
                u.setLongitud(rs.getDouble("longitud"));
                u.setLatitud(rs.getDouble("latitud"));
                u.setMunicipio(rs.getInt("municipio"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar ubicaciones: " + e.getMessage());
        }

        return lista;
    }

    public boolean eliminarUbicacion(int id) {
        String sql = "DELETE FROM Ubicacion WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar ubicación: " + e.getMessage());
            return false;
        }
    }
}
