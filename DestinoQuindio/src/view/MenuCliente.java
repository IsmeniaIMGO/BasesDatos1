package src.view;

import src.model.*;
import javax.swing.*;

public class MenuCliente extends JFrame {

    public MenuCliente(Usuario usuario) {
        setTitle("Panel Cliente - " + usuario.getNombre());
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuConsultas = new JMenu("Consultas");
        menuConsultas.add(new JMenuItem("Mis viajes"));
        menuConsultas.add(new JMenuItem("Información turística"));
        menuBar.add(menuConsultas);

        JMenu menuUtilidades = new JMenu("Utilidades");
        menuUtilidades.add(new JMenuItem("Calculadora"));
        menuUtilidades.add(new JMenuItem("Calendario"));
        menuBar.add(menuUtilidades);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}
