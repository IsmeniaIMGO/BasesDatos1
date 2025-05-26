package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Insertar un nuevo usuario
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (cc, nombre, telefono, nivel, cuenta, historialSesion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getCc());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getTelefono());
            stmt.setInt(4, usuario.getNivel());
            stmt.setString(5, usuario.getCuenta());
            stmt.setInt(6, usuario.getHistorialSesion());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Consultar un usuario por cc
    public Usuario obtenerUsuarioPorCc(int cc) {
        String sql = "SELECT * FROM Usuario WHERE cc = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setCc(rs.getInt("cc"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setNivel(rs.getInt("nivel"));
                usuario.setCuenta(rs.getString("cuenta"));
                usuario.setHistorialSesion(rs.getInt("historialSesion"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener usuario: " + e.getMessage());
        }

        return usuario;
    }

    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
    String sql = "UPDATE Usuario SET nombre = ?, telefono = ? WHERE cc = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getNombre());
        stmt.setString(2, usuario.getTelefono());
        stmt.setInt(3, usuario.getCc());

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar usuario: " + e.getMessage());
        return false;
    }

    }

    // Eliminar usuario por cc
    public boolean eliminarUsuario(int cc) {
    Connection conn = null;
    try {
        conn = ConexionBD.getConnection();
        conn.setAutoCommit(false);

        // 1. Obtener el correo desde Usuario
        String correo = null;
        PreparedStatement stmtCorreo = conn.prepareStatement("SELECT cuenta FROM Usuario WHERE cc = ?");
        stmtCorreo.setInt(1, cc);
        ResultSet rs = stmtCorreo.executeQuery();
        if (rs.next()) {
            correo = rs.getString("cuenta");
        }

        // 2. Eliminar posibles dependencias
        conn.prepareStatement("DELETE FROM Cliente WHERE cc = " + cc).executeUpdate();
        conn.prepareStatement("DELETE FROM Conductor WHERE cc = " + cc).executeUpdate();
        conn.prepareStatement("DELETE FROM Jefe WHERE cc = " + cc).executeUpdate();
        conn.prepareStatement("DELETE FROM AdministradorDB WHERE cc = " + cc).executeUpdate();

        // 3. Eliminar Usuario
        PreparedStatement stmtUsuario = conn.prepareStatement("DELETE FROM Usuario WHERE cc = ?");
        stmtUsuario.setInt(1, cc);
        stmtUsuario.executeUpdate();

        // 4. Eliminar Cuenta
        if (correo != null) {
            PreparedStatement stmtCuenta = conn.prepareStatement("DELETE FROM Cuenta WHERE correo = ?");
            stmtCuenta.setString(1, correo);
            stmtCuenta.executeUpdate();
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        System.err.println("❌ Error al eliminar usuario y dependencias: " + e.getMessage());
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex2) {
            System.err.println("❌ Error al hacer rollback: " + ex2.getMessage());
        }
        return false;
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
        }
    }
}


    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM Usuario";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setCc(rs.getInt("cc"));
            u.setNombre(rs.getString("nombre"));
            u.setTelefono(rs.getString("telefono"));
            u.setNivel(rs.getInt("nivel"));
            u.setCuenta(rs.getString("cuenta"));
            u.setHistorialSesion(rs.getInt("historialSesion"));
            lista.add(u);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al listar usuarios: " + e.getMessage());
    }

    System.out.println("Usuarios encontrados: " + lista.size());

    return lista;


    }


    public Usuario obtenerUsuarioPorCuenta(String correoCuenta) {
    String sql = "SELECT * FROM Usuario WHERE cuenta = ?";
    Usuario usuario = null;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, correoCuenta);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setCc(rs.getInt("cc"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setNivel(rs.getInt("nivel"));
            usuario.setCuenta(rs.getString("cuenta"));
            usuario.setHistorialSesion(rs.getInt("historialSesion"));
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al buscar usuario por cuenta: " + e.getMessage());
    }

    return usuario;
}

}
