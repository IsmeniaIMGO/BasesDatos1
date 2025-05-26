package src.view;

import src.model.*;
import src.utils.*;

import javax.swing.*;

import reports.*;


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

        JMenuItem miConsultaConductores = new JMenuItem("Consulta de Conductores");
        miConsultaConductores.addActionListener(e -> new ConsultaConductoresForm());
        menuEntidades.add(miConsultaConductores);

        JMenuItem miConsultaVehiculos = new JMenuItem("Consulta de Vehículos");
        miConsultaVehiculos.addActionListener(e -> new ConsultaVehiculosForm());
        menuEntidades.add(miConsultaVehiculos);

        JMenuItem miConsultaUbicaciones = new JMenuItem("Consulta de Ubicaciones");
        miConsultaUbicaciones.addActionListener(e -> new ConsultaUbicacionesForm());
        menuEntidades.add(miConsultaUbicaciones);

        

        menuBar.add(menuEntidades);

        // Transacciones
        JMenu menuTransacciones = new JMenu("Transacciones");
        
        JMenuItem miConsultaViajes = new JMenuItem("Consulta de viajes");
        miConsultaViajes.addActionListener(e -> new ConsultaViajesForm());
        menuTransacciones.add(miConsultaViajes);

        JMenuItem miConsultaPagos = new JMenuItem("Pagos");
        miConsultaPagos.addActionListener(e -> new ConsultaPagosForm());
        menuTransacciones.add(miConsultaPagos);

        menuBar.add(menuTransacciones);

        // Reportes
        JMenu menuReportes = new JMenu("Reportes y Consultas");
        
        JMenuItem miReporteIngresos = new JMenuItem("Reporte de ingresos");
        miReporteIngresos.addActionListener(e -> {
            String ruta = "resources/ReporteIngresos.pdf";
            ReporteIngresos.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteIngresos);


        JMenuItem miReporteMunicipios = new JMenuItem("Viajes por municipio");
        miReporteMunicipios.addActionListener(e -> {
            String ruta = "resources/reporteViajesMunicipio.pdf";
            ReporteViajesMunicipio.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteMunicipios);
        

        JMenuItem miReporteHistorial = new JMenuItem("Historial de usuarios");
        miReporteHistorial.addActionListener(e -> {
            String ruta = "resources/ReporteHistorialUsuarios.pdf";
            ReporteHistorialUsuarios.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteHistorial);


        JMenuItem miReporteViajesCliente = new JMenuItem("Viajes por cliente");
        miReporteViajesCliente.addActionListener(e -> {
            String ruta = "resources/ReporteViajesPorCliente.pdf";
            ReporteViajesCliente.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteViajesCliente);

        JMenuItem miReporteViajesConductor = new JMenuItem("Viajes por conductor");
        miReporteViajesConductor.addActionListener(e -> {
            String ruta = "resources/ReporteViajesPorConductor.pdf";
            ReporteViajeConductor.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteViajesConductor);

        menuBar.add(menuReportes);


        // Utilidades
        JMenu menuUtilidades = new JMenu("Utilidades");
        JMenuItem miCalculadora = new JMenuItem("Calculadora");
        miCalculadora.addActionListener(e -> SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculadora();
            }
        }));
        menuUtilidades.add(miCalculadora);

        JMenuItem miCalendario = new JMenuItem("Calendario");
        miCalendario.addActionListener(e -> SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calendario();
            }
        }));
        menuUtilidades.add(miCalendario);

        menuBar.add(menuUtilidades);

        // Gestión de sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem miGestionUsuarios = new JMenuItem("Gestión de usuarios");
        miGestionUsuarios.addActionListener(e -> new GestionUsuariosForm());
        menuSistema.add(miGestionUsuarios);

        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        cerrarSesion.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea cerrar sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                dispose(); // Cierra el menú actual
                new LoginForm(); // Abre el formulario de login
            }
        });

        menuSistema.add(cerrarSesion);

        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}

