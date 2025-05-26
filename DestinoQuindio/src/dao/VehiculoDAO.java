package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public boolean insertarVehiculo(Vehiculo vehiculo) {
        String sql = "INSERT INTO Vehiculo (placa, marca) VALUES (?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getMarca());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar vehículo: " + e.getMessage());
            return false;
        }
    }

    public Vehiculo obtenerVehiculoPorPlaca(String placa) {
        String sql = "SELECT * FROM Vehiculo WHERE placa = ?";
        Vehiculo vehiculo = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                vehiculo = new Vehiculo();
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setMarca(rs.getString("marca"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener vehículo: " + e.getMessage());
        }

        return vehiculo;
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        String sql = "UPDATE Vehiculo SET marca = ? WHERE placa = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getPlaca());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarVehiculo(String placa) {
        String sql = "DELETE FROM Vehiculo WHERE placa = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }

    public List<Vehiculo> listarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Vehiculo";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar vehículos: " + e.getMessage());
        }

        return lista;
    }
    public List<Object[]> listarVehiculosDatos() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT placa, marca FROM Vehiculo";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[]{
                rs.getString("placa"),
                rs.getString("marca")
            };
            lista.add(fila);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al listar vehículos: " + e.getMessage());
    }

    return lista;
}


    
}
