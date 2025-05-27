package src.view;

import src.dao.ViajeDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaViajesForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaViajesForm() {
        setTitle("Consulta de Viajes");
        setSize(1000, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Fecha", "Valor", "Cliente", "Conductor", "Origen", "Destino", "Estado", "Vehículo" }, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarViajes();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarViajes() {
        modelo.setRowCount(0);
        ViajeDAO dao = new ViajeDAO();
        List<Object[]> viajes = dao.listarViajesConDatos();

        for (Object[] fila : viajes) {
            modelo.addRow(fila);
        }
    }
}
