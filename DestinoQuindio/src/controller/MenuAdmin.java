package src.controller;

import src.model.*;
import src.utils.*;
import src.view.BitacoraUsuariosForm;
import src.view.ConsultaClientesForm;
import src.view.ConsultaConductoresForm;
import src.view.ConsultaPagosForm;
import src.view.ConsultaUbicacionesForm;
import src.view.ConsultaUsuariosForm;
import src.view.ConsultaVehiculosForm;
import src.view.ConsultaViajesForm;
import src.view.GestionUsuariosForm;
import src.view.GuiaUsuarioForm;
import src.view.PagoForm;
import src.view.UbicacionForm;
import src.view.VehiculoForm;
import src.view.ViajeForm;

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
        JMenu menuEntidades = new JMenu("CRUD Entidades");
        JMenuItem miCRUDUsuarios = new JMenuItem("usuarios");
        miCRUDUsuarios.addActionListener(e -> new GestionUsuariosForm());
        menuEntidades.add(miCRUDUsuarios);

        JMenuItem miCRUDClientes = new JMenuItem("Vehiculos");
        miCRUDClientes.addActionListener(e -> new VehiculoForm());
        menuEntidades.add(miCRUDClientes);

        JMenuItem miCRUDConductores = new JMenuItem("Ubicaciones");
        miCRUDConductores.addActionListener(e -> new UbicacionForm());
        menuEntidades.add(miCRUDConductores);

        JMenuItem miCRUDVehiculos = new JMenuItem("Pagos");
        miCRUDVehiculos.addActionListener(e -> new PagoForm());
        menuEntidades.add(miCRUDVehiculos);

        JMenuItem miCRUDUbicaciones = new JMenuItem("Viajes");
        miCRUDUbicaciones.addActionListener(e -> new ViajeForm());
        menuEntidades.add(miCRUDUbicaciones);

        
        menuBar.add(menuEntidades);

        // Transacciones
        JMenu menuTransacciones = new JMenu("Transacciones");
        
        JMenuItem miConsultaViajes = new JMenuItem("viajes");
        miConsultaViajes.addActionListener(e -> new ConsultaViajesForm());
        menuTransacciones.add(miConsultaViajes);

        JMenuItem miConsultaPagos = new JMenuItem("Pagos");
        miConsultaPagos.addActionListener(e -> new ConsultaPagosForm());
        menuTransacciones.add(miConsultaPagos);

        menuBar.add(menuTransacciones);

        // Reportes
        JMenu menuReportes = new JMenu("Reportes");
        
        JMenuItem miReporteIngresos = new JMenuItem("Reporte de ingresos");
        miReporteIngresos.addActionListener(e -> {
            String ruta = "resources/ReporteIngresos.pdf";
            ReporteIngresos.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteIngresos);

        JMenu menuConsultas = new JMenu("Consultas");
        JMenuItem miConsultaUsuarios = new JMenuItem("Consulta de usuarios");
        miConsultaUsuarios.addActionListener(e -> new ConsultaUsuariosForm());
        menuConsultas.add(miConsultaUsuarios);

        JMenuItem miConsultaClientes = new JMenuItem("Consulta de clientes");
        miConsultaClientes.addActionListener(e -> new ConsultaClientesForm());
        menuConsultas.add(miConsultaClientes);

        JMenuItem miConsultaConductores = new JMenuItem("Consulta de Conductores");
        miConsultaConductores.addActionListener(e -> new ConsultaConductoresForm());
        menuConsultas.add(miConsultaConductores);

        JMenuItem miConsultaVehiculos = new JMenuItem("Consulta de Vehículos");
        miConsultaVehiculos.addActionListener(e -> new ConsultaVehiculosForm());
        menuConsultas.add(miConsultaVehiculos);

        JMenuItem miConsultaUbicaciones = new JMenuItem("Consulta de Ubicaciones");
        miConsultaUbicaciones.addActionListener(e -> new ConsultaUbicacionesForm());
        menuConsultas.add(miConsultaUbicaciones);

        menuBar.add(menuConsultas);
        

        JMenuItem miReporteMunicipios = new JMenuItem("Viajes por municipio");
        miReporteMunicipios.addActionListener(e -> {
            String ruta = "resources/reporteViajesMunicipio.pdf";
            ReporteViajesMunicipio.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteMunicipios);
        

        JMenuItem miReporteViajesEstado = new JMenuItem("Viajes por estado");
        miReporteViajesEstado.addActionListener(e -> {
            String ruta = "resources/ReporteViajesPorEstado.pdf";
            ReporteViajesEstado.generar(ruta);
            JOptionPane.showMessageDialog(null, "✅ Reporte generado: " + ruta);
        });
        menuReportes.add(miReporteViajesEstado);


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
                new Calculadora().setVisible(true);;
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


        JMenuItem miConversorMonedas = new JMenuItem("Conversor de monedas");
        miConversorMonedas.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            new ConversorMonedas().setVisible(true);
        }));
        menuUtilidades.add(miConversorMonedas);

        JMenuItem miGestionUsuarios = new JMenuItem("Bitacora de usuarios");
        miGestionUsuarios.addActionListener(e -> new BitacoraUsuariosForm());
        menuUtilidades.add(miGestionUsuarios);

        menuBar.add(menuUtilidades);

        // Gestión de sistema
        JMenu menuSistema = new JMenu("Ayudas");
        JMenuItem miGuia = new JMenuItem("Guia de usuario");
        miGuia.addActionListener(e -> new GuiaUsuarioForm());
        menuSistema.add(miGuia);

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

