package src.view;

import src.model.*;
import javax.swing.*;

public class MenuConductor extends JFrame {

    public MenuConductor(Usuario usuario) {
        setTitle("Panel Conductor - " + usuario.getNombre());
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuEntidades = new JMenu("Entidades");
        menuEntidades.add(new JMenuItem("Clientes"));
        menuEntidades.add(new JMenuItem("Vehículos"));
        menuEntidades.add(new JMenuItem("Ubicaciones"));
        menuBar.add(menuEntidades);

        JMenu menuTransacciones = new JMenu("Transacciones");
        menuTransacciones.add(new JMenuItem("Viajes"));
        menuTransacciones.add(new JMenuItem("Pagos"));
        menuBar.add(menuTransacciones);

        JMenu menuConsultas = new JMenu("Consultas");
        menuConsultas.add(new JMenuItem("Mis viajes"));
        menuBar.add(menuConsultas);

        JMenu menuUtilidades = new JMenu("Utilidades");
        menuUtilidades.add(new JMenuItem("Calculadora"));
        menuBar.add(menuUtilidades);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}
