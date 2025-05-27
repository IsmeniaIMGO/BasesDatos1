package src.view;

import javax.swing.*;
import java.awt.*;

public class GuiaUsuarioForm extends JFrame {

    public GuiaUsuarioForm() {
        setTitle("Guía de Usuario");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setText("""
        ┌──────────────────────────────┐
        │        GUÍA DE USUARIO       │
        └──────────────────────────────┘

        ▸ INICIO DE SESIÓN:
          - Ingresar con correo y contraseña.
          - Si no tienes cuenta, puedes registrarte.
        
        ▸ ROLES:
          - Administrador: acceso total (CRUD, reportes, gestión).
          - Cliente: solicitud de viajes.
          - Conductor: aceptación y realización de viajes.
          - Jefe: acceso de consulta y control.

        ▸ CRUD DE ENTIDADES:
          - Usuarios, Clientes, Conductores, Vehículos, Ubicaciones.
          - Permite crear, editar o eliminar registros.

        ▸ TRANSACCIONES:
          - Viajes: historial de todos los viajes con detalle.
          - Pagos: registro de pagos y métodos.

        ▸ CONSULTAS:
          - Visualización en tabla de usuarios, clientes, conductores, etc.

        ▸ REPORTES:
          - Generados en PDF (almacenados en /resources).
            - Ingresos
            - Viajes por estado / cliente / municipio / conductor

        ▸ UTILIDADES:
          - Calculadora, calendario, historial de sesión (Bitácora)

        ▸ AYUDA:
          - Esta guía.

        ▸ CIERRE DE SESIÓN:
          - Disponible desde el menú “Ayudas”.

        Recomendación: utilizar los menús de izquierda a derecha, 
        comenzando por entidades antes de generar reportes.
        """);

        JScrollPane scroll = new JScrollPane(area);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
