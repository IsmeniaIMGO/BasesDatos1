import java.sql.Connection;
import src.dao.ConexionBD;


public class Main {
    public static void main(String[] args) {
        Connection conn = ConexionBD.getConnection();
        if (conn != null) {
            ConexionBD.cerrarConexion(conn);
        }
    }
}
