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
        
        JMenuItem miConsultaUsuarios = new JMenuItem("Consulta de usuarios");
        miConsultaUsuarios.addActionListener(e -> new ConsultaUsuariosForm());
        menuEntidades.add(miConsultaUsuarios);

        JMenuItem miConsultaClientes = new JMenuItem("Consulta de clientes");
        miConsultaClientes.addActionListener(e -> new ConsultaClientesForm());
        menuEntidades.add(miConsultaClientes);

        JMenuItem miConsultaConductores = new JMenuItem("Conductores");
        miConsultaConductores.addActionListener(e -> new ConsultaConductoresForm());
        menuEntidades.add(miConsultaConductores);

        JMenuItem miConsultaVehiculos = new JMenuItem("Vehículos");
        miConsultaVehiculos.addActionListener(e -> new ConsultaVehiculosForm());
        menuEntidades.add(miConsultaVehiculos);

        JMenuItem miConsultaUbicaciones = new JMenuItem("Ubicaciones");
        miConsultaUbicaciones.addActionListener(e -> new ConsultaUbicacionesForm());
        menuEntidades.add(miConsultaUbicaciones);

        

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
        JMenuItem miGestionUsuarios = new JMenuItem("Gestión de usuarios");
        miGestionUsuarios.addActionListener(e -> new GestionUsuariosForm());
        menuSistema.add(miGestionUsuarios);


        menuSistema.add(new JMenuItem("Bitácora de ingreso/salida"));
        menuSistema.add(new JMenuItem("Cerrar sesión"));
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}

