package src.view;

import src.dao.PagoDAO;
import src.model.Pago;
import src.model.MetodoPago; // Importa la clase MetodoPago
import src.dao.MetodoPagoDAO; // Importa el DAO para MetodoPago

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PagoForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtId, txtValor;
    private JComboBox<MetodoPago> comboMetodo; // Cambia el JTextField por un JComboBox

    public PagoForm() {
        setTitle("Gestión de Pagos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Valor", "Método"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridLayout(4, 2));
        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelForm.add(txtId);
        panelForm.add(new JLabel("Valor:"));
        txtValor = new JTextField();
        panelForm.add(txtValor);
        panelForm.add(new JLabel("Método de Pago:")); // Cambia el JLabel
        comboMetodo = new JComboBox<>(); // Inicializa el JComboBox
        panelForm.add(comboMetodo);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        panelForm.add(btnGuardar);
        panelForm.add(btnActualizar);
        add(panelForm, BorderLayout.NORTH);

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        add(btnEliminar, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(e -> guardarPago());
        btnActualizar.addActionListener(e -> actualizarPago());
        btnEliminar.addActionListener(e -> eliminarPago());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarCampos());

        cargarMetodosPago(); // Carga los métodos de pago en el JComboBox
        cargarPagos();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarMetodosPago() {
        MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
        List<MetodoPago> metodos = metodoPagoDAO.listarMetodosPago(); // Asegúrate de tener este método en MetodoPagoDAO
        for (MetodoPago metodo : metodos) {
            comboMetodo.addItem(metodo);
        }
    }

    private void cargarPagos() {
    modelo.setRowCount(0);
    PagoDAO dao = new PagoDAO();
    List<Object[]> lista = dao.listarPagosConDetalle();
    for (Object[] fila : lista) {
        // Obtener los valores de la fila
        int id = (int) fila[0];
        double valor = (double) fila[1];
        String nombreMetodoPago = (String) fila[2]; // Obtener el nombre del método de pago

        // Buscar el objeto MetodoPago correspondiente al nombre
        MetodoPago metodoPago = buscarMetodoPagoPorNombre(nombreMetodoPago);

        // Agregar la fila a la tabla con el objeto MetodoPago
        modelo.addRow(new Object[]{id, valor, metodoPago});
    }
    }

    private MetodoPago buscarMetodoPagoPorNombre(String nombre) {
    for (int i = 0; i < comboMetodo.getItemCount(); i++) {
        MetodoPago metodo = comboMetodo.getItemAt(i);
        if (metodo.getNombre().equals(nombre)) {
            return metodo;
        }
    }
    return null; // Si no se encuentra, retorna null
    }

    private void guardarPago() {
        try {
            double valor = Double.parseDouble(txtValor.getText());
            MetodoPago metodo = (MetodoPago) comboMetodo.getSelectedItem();

            if (metodo == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un método de pago.");
                return;
            }

            Pago p = new Pago(); // No se asigna ID
            p.setValor(valor);
            p.setMetodoPago(metodo.getId());

            boolean ok = new PagoDAO().insertarPago(p);
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Pago registrado.");
                cargarPagos();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❗ Verifica los campos numéricos.");
        }
    }

    private void actualizarPago() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pago para actualizar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            double valor = Double.parseDouble(txtValor.getText());
            MetodoPago metodo = (MetodoPago) comboMetodo.getSelectedItem();

            Pago p = new Pago(id, valor, metodo.getId());
            boolean ok = new PagoDAO().actualizarPago(p);
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Pago actualizado.");
                cargarPagos();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❗ Verifica los campos.");
        }
    }

    private void eliminarPago() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pago para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar pago ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (new PagoDAO().eliminarPago(id)) {
                JOptionPane.showMessageDialog(this, "✅ Pago eliminado.");
                cargarPagos();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar.");
            }
        }
    }

    private void cargarCampos() {
    int fila = tabla.getSelectedRow();
    if (fila != -1) {
        txtId.setText(modelo.getValueAt(fila, 0).toString());
        txtValor.setText(modelo.getValueAt(fila, 1).toString());
        // Selecciona el método de pago en el JComboBox
        MetodoPago metodoPago = (MetodoPago) modelo.getValueAt(fila, 2);
        if (metodoPago != null) {
            for (int i = 0; i < comboMetodo.getItemCount(); i++) {
                if (comboMetodo.getItemAt(i).getId() == metodoPago.getId()) {
                    comboMetodo.setSelectedIndex(i);
                    break;
                }
            }
        }
        txtId.setEnabled(false);
    }
    }

    private void limpiar() {
        txtId.setText("");
        txtValor.setText("");
        comboMetodo.setSelectedIndex(-1); // Deselecciona el JComboBox
        txtId.setEnabled(true);
    }
}