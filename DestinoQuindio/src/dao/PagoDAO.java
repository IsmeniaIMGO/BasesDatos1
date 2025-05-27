package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    public boolean insertarPago(Pago pago) {
        String sql = "INSERT INTO Pago (id, valor, metodoPago) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pago.getId());
            stmt.setDouble(2, pago.getValor());
            stmt.setInt(3, pago.getMetodoPago());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar pago: " + e.getMessage());
            return false;
        }
    }

    public Pago obtenerPagoPorId(int id) {
        String sql = "SELECT * FROM Pago WHERE id = ?";
        Pago pago = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pago = new Pago();
                pago.setId(rs.getInt("id"));
                pago.setValor(rs.getDouble("valor"));
                pago.setMetodoPago(rs.getInt("metodoPago"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener pago: " + e.getMessage());
        }

        return pago;
    }

    public boolean actualizarPago(Pago pago) {
        String sql = "UPDATE Pago SET valor = ?, metodoPago = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, pago.getValor());
            stmt.setInt(2, pago.getMetodoPago());
            stmt.setInt(3, pago.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar pago: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPago(int id) {
        String sql = "DELETE FROM Pago WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar pago: " + e.getMessage());
            return false;
        }
    }

    public List<Pago> listarPagos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pago";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pago p = new Pago();
                p.setId(rs.getInt("id"));
                p.setValor(rs.getDouble("valor"));
                p.setMetodoPago(rs.getInt("metodoPago"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar pagos: " + e.getMessage());
        }

        return lista;
    }

    public List<Object[]> listarPagosConDetalle() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT 
                p.id AS id,
                p.valor AS valor,
                mp.nombre AS metodoPago
            FROM Pago p
            JOIN MetodoPago mp ON p.metodoPago = mp.id
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[]{
                    rs.getInt("id"),
                    rs.getDouble("valor"),
                    rs.getString("metodoPago")
                };
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar pagos con detalle: " + e.getMessage());
        }

        return lista;
    }


public List<Object[]> listarPagosConDetallePorCliente(int clienteCc) {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT 
                p.id AS id,
                p.valor AS valor,
                mp.nombre AS metodoPago
            FROM Pago p
            JOIN MetodoPago mp ON p.metodoPago = mp.id
            JOIN Viaje v ON p.id = v.pago
            WHERE v.cliente = ?
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteCc);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[]{
                    rs.getInt("id"),
                    rs.getDouble("valor"),
                    rs.getString("metodoPago")
                };
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar pagos con detalle: " + e.getMessage());
        }

        return lista;
    }
}
