import java.sql.Connection;
import src.dao.ConexionBD;
import src.view.LoginForm;


public class Main {
    public static void main(String[] args) {
        Connection conn = ConexionBD.getConnection();

        if (conn != null) {
            System.out.println("✅ Conexión exitosa a la base de datos.");
            // Aquí puedes iniciar tu aplicación, por ejemplo, mostrando el formulario de login
            new LoginFormForm();
        } else {
            System.err.println("❌ Error al conectar a la base de datos.");
        }
        
    }

    private static class LoginFormForm extends LoginForm {
        // Esta clase es solo un marcador de posición para evitar conflictos de nombres.
        // En una aplicación real, LoginForm ya debería estar implementada correctamente.
        public LoginFormForm() {
            super();
        }
    }
}
