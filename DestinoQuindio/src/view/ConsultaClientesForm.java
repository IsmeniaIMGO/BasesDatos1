package src.view;

import src.dao.ClienteDAO;
import src.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaClientesForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaClientesForm() {
        setTitle("Consulta de Clientes");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"CC", "Nombre", "Teléfono", "Correo"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarClientes();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarClientes() {
        modelo.setRowCount(0);
        ClienteDAO dao = new ClienteDAO();
        List<Usuario> clientes = dao.listarClientesConDatos();

        for (Usuario u : clientes) {
            modelo.addRow(new Object[]{
                    u.getCc(),
                    u.getNombre(),
                    u.getTelefono(),
                    u.getCuenta()
            });
        }
    }
}
