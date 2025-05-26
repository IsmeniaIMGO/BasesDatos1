package src.view;

import src.dao.VehiculoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaVehiculosForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaVehiculosForm() {
        setTitle("Consulta de Vehículos");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"Placa", "Marca"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarVehiculos();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarVehiculos() {
        modelo.setRowCount(0);
        VehiculoDAO dao = new VehiculoDAO();
        List<Object[]> vehiculos = dao.listarVehiculosDatos();

        for (Object[] fila : vehiculos) {
            modelo.addRow(fila);
        }
    }
}
