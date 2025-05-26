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
}
