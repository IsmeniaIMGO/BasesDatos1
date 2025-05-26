package src.view;

import src.dao.UsuarioDAO;
import src.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaUsuariosForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaUsuariosForm() {
        setTitle("Consulta de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"CC", "Nombre", "Teléfono", "Nivel", "Correo"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarUsuarios();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarUsuarios() {
        modelo.setRowCount(0); // Limpiar tabla
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.listarUsuarios();

        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getCc(),
                    u.getNombre(),
                    u.getTelefono(),
                    u.getNivel(),
                    u.getCuenta()
            });
        }
    }
}
