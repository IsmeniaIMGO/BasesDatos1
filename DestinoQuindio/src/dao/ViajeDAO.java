package src.dao;

import src.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAO {

    public boolean insertarViaje(Viaje viaje) {
        String sql = "INSERT INTO Viaje (id, fecha, pago, cliente, conductor, ubicacionOrigen, ubicacionDestino, estado, vehiculo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, viaje.getId());
            stmt.setDate(2, new java.sql.Date(viaje.getFecha().getTime()));
            stmt.setInt(3, viaje.getPago());
            stmt.setInt(4, viaje.getCliente());
            stmt.setInt(5, viaje.getConductor());
            stmt.setInt(6, viaje.getUbicacionOrigen());
            stmt.setInt(7, viaje.getUbicacionDestino());
            stmt.setInt(8, viaje.getEstado());
            stmt.setString(9, viaje.getVehiculo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar viaje: " + e.getMessage());
            return false;
        }
    }

    public Viaje obtenerViajePorId(int id) {
        String sql = "SELECT * FROM Viaje WHERE id = ?";
        Viaje viaje = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                viaje = new Viaje();
                viaje.setId(rs.getInt("id"));
                viaje.setFecha(rs.getDate("fecha"));
                viaje.setPago(rs.getInt("pago"));
                viaje.setCliente(rs.getInt("cliente"));
                viaje.setConductor(rs.getInt("conductor"));
                viaje.setUbicacionOrigen(rs.getInt("ubicacionOrigen"));
                viaje.setUbicacionDestino(rs.getInt("ubicacionDestino"));
                viaje.setEstado(rs.getInt("estado"));
                viaje.setVehiculo(rs.getString("vehiculo"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener viaje: " + e.getMessage());
        }

        return viaje;
    }

    public boolean actualizarViaje(Viaje viaje) {
        String sql = "UPDATE Viaje SET fecha = ?, pago = ?, cliente = ?, conductor = ?, ubicacionOrigen = ?, " +
                     "ubicacionDestino = ?, estado = ?, vehiculo = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(viaje.getFecha().getTime()));
            stmt.setInt(2, viaje.getPago());
            stmt.setInt(3, viaje.getCliente());
            stmt.setInt(4, viaje.getConductor());
            stmt.setInt(5, viaje.getUbicacionOrigen());
            stmt.setInt(6, viaje.getUbicacionDestino());
            stmt.setInt(7, viaje.getEstado());
            stmt.setString(8, viaje.getVehiculo());
            stmt.setInt(9, viaje.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar viaje: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarViaje(int id) {
        String sql = "DELETE FROM Viaje WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar viaje: " + e.getMessage());
            return false;
        }
    }

    public List<Viaje> listarViajes() {
        List<Viaje> lista = new ArrayList<>();
        String sql = "SELECT * FROM Viaje";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Viaje v = new Viaje();
                v.setId(rs.getInt("id"));
                v.setFecha(rs.getDate("fecha"));
                v.setPago(rs.getInt("pago"));
                v.setCliente(rs.getInt("cliente"));
                v.setConductor(rs.getInt("conductor"));
                v.setUbicacionOrigen(rs.getInt("ubicacionOrigen"));
                v.setUbicacionDestino(rs.getInt("ubicacionDestino"));
                v.setEstado(rs.getInt("estado"));
                v.setVehiculo(rs.getString("vehiculo"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar viajes: " + e.getMessage());
        }

        return lista;
    }

    public List<ViajeDetalle> listarViajesConDetalle() {
        List<ViajeDetalle> listaDetalles = new ArrayList<>();
        String sql = "SELECT " +
                     "    v.id AS id_viaje, " +
                     "    v.fecha, " +
                     "    u1.nombre AS nombre_cliente, " +
                     "    u2.nombre AS nombre_conductor, " +
                     "    est.nombre AS estado, " +
                     "    ve.marca AS vehiculo, " +
                     "    pago.valor, " +
                     "    mp.nombre AS metodo_pago " +
                     "FROM Viaje v " +
                     "JOIN Cliente c ON v.cliente = c.cc " +
                     "JOIN Usuario u1 ON c.cc = u1.cc " +
                     "JOIN Conductor con ON v.conductor = con.cc " +
                     "JOIN Usuario u2 ON con.cc = u2.cc " +
                     "JOIN Estado est ON v.estado = est.id " +
                     "JOIN Vehiculo ve ON v.vehiculo = ve.placa " +
                     "JOIN Pago pago ON v.pago = pago.id " +
                     "JOIN MetodoPago mp ON pago.metodoPago = mp.id";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ViajeDetalle vd = new ViajeDetalle();
                vd.setIdViaje(rs.getInt("id_viaje"));
                vd.setFecha(rs.getDate("fecha"));
                vd.setNombreCliente(rs.getString("nombre_cliente"));
                vd.setNombreConductor(rs.getString("nombre_conductor"));
                vd.setEstado(rs.getString("estado"));
                vd.setVehiculo(rs.getString("vehiculo"));
                vd.setValorPago(rs.getInt("valor"));
                vd.setMetodoPago(rs.getString("metodo_pago"));
                listaDetalles.add(vd);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar viajes con detalle: " + e.getMessage());
        }

        return listaDetalles;
    }

    
    public List<Object[]> listarViajesConDatos() {
    List<Object[]> lista = new ArrayList<>();
    String sql = """
        SELECT 
            v.id AS id,
            v.fecha AS fecha,
            p.valor AS pago,
            u1.nombre AS cliente,
            u2.nombre AS conductor,
            uo.id AS ubicacionOrigen,
            ud.id AS ubicacionDestino,
            e.nombre AS estado,
            ve.placa AS vehiculo
    
        FROM Viaje v
        JOIN Pago p ON v.pago = p.id
        JOIN Usuario u1 ON v.cliente = u1.cc
        JOIN Usuario u2 ON v.conductor = u2.cc
        JOIN Ubicacion uo ON v.ubicacionOrigen = uo.id
        JOIN Ubicacion ud ON v.ubicacionDestino = ud.id
        JOIN Estado e ON v.estado = e.id
        JOIN Vehiculo ve ON v.vehiculo = ve.placa
        
        """;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[]{
                rs.getInt("id"),
                rs.getDate("fecha"),
                rs.getDouble("pago"),
                rs.getString("cliente"),
                rs.getString("conductor"),
                rs.getString("ubicacionOrigen"),
                rs.getString("ubicacionDestino"),
                rs.getString("estado"),
                rs.getString("vehiculo")
                
            };
            lista.add(fila);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al listar viajes con datos: " + e.getMessage());
    }

    return lista;
}


}
