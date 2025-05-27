package src.dao;

import src.model.AdministradorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdministradorDAO {
    public boolean insertarAdministrador(AdministradorDB a) {
        String sql = "INSERT INTO AdministradorDB (cc) VALUES (?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getCc());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("❌ Error al insertar administrador: " + e.getMessage());
            return false;
        }
    }


    public AdministradorDB obtenerAdministradorPorCc(int cc) {
        String sql = "SELECT * FROM AdministradorDB WHERE cc = ?";
        AdministradorDB administrador = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                administrador = new AdministradorDB();
                administrador.setCc(rs.getInt("cc"));
            }

        } catch (Exception e) {
            System.err.println("❌ Error al obtener administrador: " + e.getMessage());
        }

        return administrador;
    }
}
