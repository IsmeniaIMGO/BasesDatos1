package src.view;

import src.dao.UbicacionDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaUbicacionesForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaUbicacionesForm() {
        setTitle("Consulta de Ubicaciones");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Latitud", "Longitud", "Municipio"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarUbicaciones();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarUbicaciones() {
        modelo.setRowCount(0);
        UbicacionDAO dao = new UbicacionDAO();
        List<Object[]> ubicaciones = dao.listarUbicacionesConMunicipio();

        System.out.println("Ubicaciones encontradas: " + ubicaciones.size()); // <-- este log
        
        for (Object[] fila : ubicaciones) {
            modelo.addRow(fila);
        }
    }
}
