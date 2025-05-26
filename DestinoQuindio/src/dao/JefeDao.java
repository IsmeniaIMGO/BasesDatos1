package src.dao;

import src.model.Jefe;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JefeDao {
    public boolean insertarJefe(Jefe j) {
        String sql = "INSERT INTO Jefe (cc) VALUES (?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, j.getCc());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("❌ Error al insertar jefe: " + e.getMessage());
            return false;
        }
    }
}
