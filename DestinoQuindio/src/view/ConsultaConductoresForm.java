package src.view;

import src.dao.ConductorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaConductoresForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaConductoresForm() {
        setTitle("Consulta de Conductores");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"CC", "Nombre", "Teléfono", "Correo", "Placa", "Marca"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarConductores();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarConductores() {
        modelo.setRowCount(0);
        ConductorDAO dao = new ConductorDAO();
        List<Object[]> conductores = dao.listarConductoresConDatos();

        for (Object[] fila : conductores) {
            modelo.addRow(fila);
        }
    }
}
