package src.dao;

import src.model.Jefe;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JefeDAO{
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

    public Jefe obtenerJefePorCc(int cc) {
        String sql = "SELECT * FROM Jefe WHERE cc = ?";
        Jefe jefe = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                jefe = new Jefe();
                jefe.setCc(rs.getInt("cc"));
            }

        } catch (Exception e) {
            System.err.println("❌ Error al obtener jefe: " + e.getMessage());
        }

        return jefe;
    }
}
