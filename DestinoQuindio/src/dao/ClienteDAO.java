package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (cc) VALUES (?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getCc());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public Cliente obtenerClientePorCc(int cc) {
        String sql = "SELECT * FROM Cliente WHERE cc = ?";
        Cliente cliente = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCc(rs.getInt("cc"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener cliente: " + e.getMessage());
        }

        return cliente;
    }

    public boolean eliminarCliente(int cc) {
        String sql = "DELETE FROM Cliente WHERE cc = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCc(rs.getInt("cc"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

    public List<Usuario> listarClientesConDatos() {
    List<Usuario> lista = new ArrayList<>();
    String sql = """
        SELECT u.cc, u.nombre, u.telefono, u.cuenta
        FROM Cliente c
        JOIN Usuario u ON c.cc = u.cc
        """;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setCc(rs.getInt("cc"));
            u.setNombre(rs.getString("nombre"));
            u.setTelefono(rs.getString("telefono"));
            u.setCuenta(rs.getString("cuenta"));
            lista.add(u);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al listar clientes con datos: " + e.getMessage());
    }

    return lista;
}


    public List<Object[]> listarClientesConNombre() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT c.cc, u.nombre
            FROM Cliente c
            JOIN Usuario u ON c.cc = u.cc
        """;

        try (Connection conn = ConexionBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{ rs.getInt("cc"), rs.getString("nombre") });
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

}
