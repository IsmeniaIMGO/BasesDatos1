package src.dao;

import src.model.Municipio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {

    public List<Municipio> listarMunicipios() {
        List<Municipio> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM Municipio"; // Ajusta la consulta a tu tabla

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("id"));
                municipio.setNombre(rs.getString("nombre"));
                lista.add(municipio);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar municipios: " + e.getMessage());
        }

        return lista;
    }
}