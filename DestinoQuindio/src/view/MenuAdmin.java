package src.view;

import src.model.*;

import javax.swing.*;

public class MenuAdmin extends JFrame {

    public MenuAdmin(Usuario usuario) {
        setTitle("Panel Administrador - " + usuario.getNombre());
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // CRUD de Entidades
        JMenu menuEntidades = new JMenu("Entidades");
        menuEntidades.add(new JMenuItem("Usuarios"));
        menuEntidades.add(new JMenuItem("Clientes"));
        menuEntidades.add(new JMenuItem("Conductores"));
        menuEntidades.add(new JMenuItem("Vehículos"));
        menuEntidades.add(new JMenuItem("Ubicaciones"));
        menuBar.add(menuEntidades);

        // Transacciones
        JMenu menuTransacciones = new JMenu("Transacciones");
        menuTransacciones.add(new JMenuItem("Viajes"));
        menuTransacciones.add(new JMenuItem("Pagos"));
        menuBar.add(menuTransacciones);

        // Reportes
        JMenu menuReportes = new JMenu("Reportes");
        menuReportes.add(new JMenuItem("Reporte de ingresos"));
        menuReportes.add(new JMenuItem("Viajes por municipio"));
        menuBar.add(menuReportes);

        // Consultas
        JMenu menuConsultas = new JMenu("Consultas");
        menuConsultas.add(new JMenuItem("Historial de usuario"));
        menuConsultas.add(new JMenuItem("Vehículos por conductor"));
        menuBar.add(menuConsultas);

        // Utilidades
        JMenu menuUtilidades = new JMenu("Utilidades");
        menuUtilidades.add(new JMenuItem("Calculadora"));
        menuUtilidades.add(new JMenuItem("Calendario"));
        menuBar.add(menuUtilidades);

        // Gestión de sistema
        JMenu menuSistema = new JMenu("Sistema");
        menuSistema.add(new JMenuItem("Gestión de usuarios"));
        menuSistema.add(new JMenuItem("Bitácora de ingreso/salida"));
        menuSistema.add(new JMenuItem("Cerrar sesión"));
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}

