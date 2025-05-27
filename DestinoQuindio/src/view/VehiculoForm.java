package src.view;

import src.dao.VehiculoDAO;
import src.model.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VehiculoForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtPlaca, txtMarca;

    public VehiculoForm() {
        setTitle("Gestión de Vehículos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabla
        modelo = new DefaultTableModel(new Object[]{"Placa", "Marca"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 2));
        panelForm.add(new JLabel("Placa:"));
        txtPlaca = new JTextField(); panelForm.add(txtPlaca);
        panelForm.add(new JLabel("Marca:"));
        txtMarca = new JTextField(); panelForm.add(txtMarca);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        panelForm.add(btnGuardar);
        panelForm.add(btnActualizar);
        add(panelForm, BorderLayout.NORTH);

        // Botón eliminar
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        add(btnEliminar, BorderLayout.SOUTH);

        // Listeners
        btnGuardar.addActionListener(e -> guardarVehiculo());
        btnActualizar.addActionListener(e -> actualizarVehiculo());
        btnEliminar.addActionListener(e -> eliminarVehiculo());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarCampos());

        cargarVehiculos();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarVehiculos() {
        modelo.setRowCount(0);
        VehiculoDAO dao = new VehiculoDAO();
        List<Vehiculo> lista = dao.listarVehiculos();
        for (Vehiculo v : lista) {
            modelo.addRow(new Object[]{v.getPlaca(), v.getMarca()});
        }
    }

    private void guardarVehiculo() {
        String placa = txtPlaca.getText().trim().toUpperCase();
        String marca = txtMarca.getText().trim();

        if (placa.isEmpty() || marca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❗ Debes completar todos los campos.");
            return;
        }

        VehiculoDAO dao = new VehiculoDAO();
        Vehiculo v = new Vehiculo(placa, marca);
        if (dao.insertarVehiculo(v)) {
            JOptionPane.showMessageDialog(this, "✅ Vehículo registrado.");
            cargarVehiculos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el vehículo.");
        }
    }

    private void actualizarVehiculo() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo para actualizar.");
            return;
        }

        String placa = (String) modelo.getValueAt(fila, 0);
        String nuevaMarca = txtMarca.getText().trim();

        if (nuevaMarca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La marca no puede estar vacía.");
            return;
        }

        VehiculoDAO dao = new VehiculoDAO();
        if (dao.actualizarVehiculo(new Vehiculo(placa, nuevaMarca))) {
            JOptionPane.showMessageDialog(this, "✅ Vehículo actualizado.");
            cargarVehiculos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar.");
        }
    }

    private void eliminarVehiculo() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo para eliminar.");
            return;
        }

        String placa = (String) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar vehículo " + placa + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (new VehiculoDAO().eliminarVehiculo(placa)) {
                JOptionPane.showMessageDialog(this, "✅ Vehículo eliminado.");
                cargarVehiculos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar.");
            }
        }
    }

    private void cargarCampos() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtPlaca.setText((String) modelo.getValueAt(fila, 0));
            txtMarca.setText((String) modelo.getValueAt(fila, 1));
            txtPlaca.setEnabled(false); // La placa no se edita
        }
    }

    private void limpiarCampos() {
        txtPlaca.setText("");
        txtMarca.setText("");
        txtPlaca.setEnabled(true);
    }
}
