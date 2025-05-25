package src.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String USUARIO = "system"; // Cambia esto si creas otro usuario
    private static final String PASSWORD = "oracle"; // Reemplaza con tu contraseña real

    public static Connection getConnection() {

        Connection conexion = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("🔌 Cargando el driver de Oracle...")    ;
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("✅ Conectado exitosamente a Oracle.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔌 Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
