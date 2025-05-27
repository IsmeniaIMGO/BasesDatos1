package src.view;

import src.dao.HistorialSesionDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BitacoraUsuariosForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public BitacoraUsuariosForm() {
        setTitle("Bitácora de Sesión de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"Cédula", "Nombre", "Fecha", "Descripción"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarDatos();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        HistorialSesionDAO dao = new HistorialSesionDAO();
        List<Object[]> datos = dao.listarBitacora();

        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }
}
