package src.controller;

import src.model.*;
import src.utils.Calculadora;
import src.utils.Calendario;
import src.utils.ConversorMonedas;
import src.view.*;
import javax.swing.*;

public class MenuConductor extends JFrame {

    public MenuConductor(Usuario usuario) {
        setTitle("Panel Conductor - " + usuario.getNombre());
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Entidades (Creación de Vehículos)
        JMenu menuEntidades = new JMenu("Entidades");
        JMenuItem miCrearVehiculo = new JMenuItem("Crear Vehículo");
        miCrearVehiculo.addActionListener(e -> new VehiculoForm());
        menuEntidades.add(miCrearVehiculo);
        menuBar.add(menuEntidades);

    
        JMenuItem miGestionViajes = new JMenuItem("Gestionar Viajes");
        miGestionViajes.addActionListener(e -> new ViajeForm());
        menuEntidades.add(miGestionViajes);

    
        JMenu menuTransacciones = new JMenu("Transacciones");
        JMenuItem miConsultaViajes = new JMenuItem("viajes");
        miConsultaViajes.addActionListener(e -> new ConsultaViajesForm());
        menuTransacciones.add(miConsultaViajes);

        JMenuItem miConsultaPagos = new JMenuItem("Pagos");
        miConsultaPagos.addActionListener(e -> new ConsultaPagosForm());
        menuTransacciones.add(miConsultaPagos);

        menuBar.add(menuTransacciones);


        // Utilidades (Calculadora y Calendario)
        JMenu menuUtilidades = new JMenu("Utilidades");
        JMenuItem miCalculadora = new JMenuItem("Calculadora");
        miCalculadora.addActionListener(e -> SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculadora().setVisible(true);
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