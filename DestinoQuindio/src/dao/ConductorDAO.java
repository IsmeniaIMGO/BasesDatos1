package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    public boolean insertarConductor(Conductor conductor) {
        String sql = "INSERT INTO Conductor (cc, vehiculo) VALUES (?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conductor.getCc());
            stmt.setString(2, conductor.getVehiculo());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar conductor: " + e.getMessage());
            return false;
        }
    }

    public Conductor obtenerConductorPorCc(int cc) {
        String sql = "SELECT * FROM Conductor WHERE cc = ?";
        Conductor conductor = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                conductor = new Conductor();
                conductor.setCc(rs.getInt("cc"));
                conductor.setVehiculo(rs.getString("vehiculo"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener conductor: " + e.getMessage());
        }

        return conductor;
    }

    public boolean actualizarConductor(Conductor conductor) {
        String sql = "UPDATE Conductor SET vehiculo = ? WHERE cc = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conductor.getVehiculo());
            stmt.setInt(2, conductor.getCc());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar conductor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarConductor(int cc) {
        String sql = "DELETE FROM Conductor WHERE cc = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar conductor: " + e.getMessage());
            return false;
        }
    }

    public List<Conductor> listarConductores() {
        List<Conductor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Conductor";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Conductor conductor = new Conductor();
                conductor.setCc(rs.getInt("cc"));
                conductor.setVehiculo(rs.getString("vehiculo"));
                lista.add(conductor);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar conductores: " + e.getMessage());
        }

        return lista;
    }
}
