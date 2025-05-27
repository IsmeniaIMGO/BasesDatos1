package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {

    public List<Estado> listarEstados1() {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estado";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estado e = new Estado();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setDescripcion(rs.getString("descripcion"));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar estados: " + e.getMessage());
        }

        return lista;
    }

    public List<Object[]> listarEstados() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM Estado";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{ rs.getInt("id"), rs.getString("nombre") });
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar estados: " + e.getMessage());
        }

        return lista;
    }

    
}
