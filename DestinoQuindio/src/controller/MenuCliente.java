package src.controller;

import src.model.*;
import src.view.*;
import src.utils.*;

import javax.swing.*;

public class MenuCliente extends JFrame {

    public MenuCliente(Usuario usuario) {
        setTitle("Panel Cliente - " + usuario.getNombre());
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // CRUD de Entidades (Creación solamente)
        JMenu menuEntidades = new JMenu("Entidades");
        JMenuItem miCrearPago = new JMenuItem("Crear Pago");
        miCrearPago.addActionListener(e -> new PagoForm()); // Usar PagoForm para crear
        menuEntidades.add(miCrearPago);

        JMenuItem miCrearUbicacion = new JMenuItem("Crear Ubicación");
        miCrearUbicacion.addActionListener(e -> new UbicacionForm()); // Usar UbicacionForm para crear
        menuEntidades.add(miCrearUbicacion);

        JMenuItem miCrearViaje = new JMenuItem("Crear Viaje");
        miCrearViaje.addActionListener(e -> new ViajeForm()); // Usar ViajeForm para crear
        menuEntidades.add(miCrearViaje);

        menuBar.add(menuEntidades);

        // Transacciones (Consultas personalizadas)
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
                new Calendario().setVisible(true);
            }
        }));
        menuUtilidades.add(miCalendario);
        menuBar.add(menuUtilidades);

        // Ayuda (Guía de Usuario)
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem miGuiaUsuario = new JMenuItem("Guía de Usuario");
        miGuiaUsuario.addActionListener(e -> new GuiaUsuarioForm());
        menuAyuda.add(miGuiaUsuario);

        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        cerrarSesion.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea cerrar sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                dispose(); // Cierra el menú actual
                new LoginForm(); // Abre el formulario de login
            }
        });

        menuAyuda.add(cerrarSesion);


        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}