package src.view;

import src.dao.UsuarioDAO;
import src.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionUsuariosForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public GestionUsuariosForm() {
        setTitle("Gestión de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        modelo = new DefaultTableModel(new Object[]{"CC", "Nombre", "Teléfono", "Nivel", "Correo"}, 0);
        tabla = new JTable(modelo);
        cargarUsuarios();

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCrear = new JButton("Crear");
        panelBotones.add(btnCrear);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnEditar.addActionListener(e -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnCrear.addActionListener(e -> new UsuarioForm());

        setVisible(true);
    }

    private void cargarUsuarios() {
        modelo.setRowCount(0); // limpiar tabla
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.listarUsuarios();

        modelo.setRowCount(0);

        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getCc(), u.getNombre(), u.getTelefono(), u.getNivel(), u.getCuenta()
            });
        }
    }

    private void editarUsuario() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
            return;
        }

        int cc = (int) modelo.getValueAt(fila, 0);
        String nombre = (String) modelo.getValueAt(fila, 1);
        String telefono = (String) modelo.getValueAt(fila, 2);
        int nivel = (int) modelo.getValueAt(fila, 3);
        String cuenta = (String) modelo.getValueAt(fila, 4);

        String nuevoNombre = JOptionPane.showInputDialog("Nombre:", nombre);
        String nuevoTelefono = JOptionPane.showInputDialog("Teléfono:", telefono);

        if (nuevoNombre != null && nuevoTelefono != null) {
            Usuario u = new Usuario(cc, nuevoNombre, nuevoTelefono, nivel, cuenta, 0);
            new UsuarioDAO().actualizarUsuario(u);
            cargarUsuarios();
            JOptionPane.showMessageDialog(this, "✅ Usuario actualizado.");
        }
    }

    private void eliminarUsuario() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
            return;
        }

        int cc = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario " + cc + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new UsuarioDAO().eliminarUsuario(cc);
            cargarUsuarios();
            JOptionPane.showMessageDialog(this, "✅ Usuario eliminado.");
        }
    }
}
