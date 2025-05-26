package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {

    // Insertar nueva cuenta
    public boolean insertarCuenta(Cuenta cuenta) {
        String sql = "INSERT INTO Cuenta (correo, usuario, contrasena) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cuenta.getCorreo());
            stmt.setString(2, cuenta.getUsuario());
            stmt.setString(3, cuenta.getcontrasena());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar cuenta: " + e.getMessage());
            return false;
        }
    }

    // Obtener cuenta por correo
    public Cuenta obtenerCuentaPorCorreo(String correo) {
        String sql = "SELECT * FROM Cuenta WHERE correo = ?";
        Cuenta cuenta = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cuenta = new Cuenta();
                cuenta.setCorreo(rs.getString("correo"));
                cuenta.setUsuario(rs.getString("usuario"));
                cuenta.setcontrasena(rs.getString("contrasena"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener cuenta: " + e.getMessage());
        }

        return cuenta;
    }

    // Actualizar cuenta
    public boolean actualizarCuenta(Cuenta cuenta) {
        String sql = "UPDATE Cuenta SET usuario = ?, contrasena = ? WHERE correo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cuenta.getUsuario());
            stmt.setString(2, cuenta.getcontrasena());
            stmt.setString(3, cuenta.getCorreo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cuenta: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cuenta
    public boolean eliminarCuenta(String correo) {
        String sql = "DELETE FROM Cuenta WHERE correo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cuenta: " + e.getMessage());
            return false;
        }
    }

    // Listar todas las cuentas
    public List<Cuenta> listarCuentas() {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cuenta";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCorreo(rs.getString("correo"));
                cuenta.setUsuario(rs.getString("usuario")); 
                cuenta.setcontrasena(rs.getString("contrasena"));
                lista.add(cuenta);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar cuentas: " + e.getMessage());
        }

        return lista;
    }
}
