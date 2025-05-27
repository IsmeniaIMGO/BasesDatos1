package src.view;

import src.dao.UbicacionDAO;
import src.model.Ubicacion;
import src.model.Municipio; // Importa la clase Municipio
import src.dao.MunicipioDAO; // Importa el DAO para Municipio

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UbicacionForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtId, txtLatitud, txtLongitud;
    private JComboBox<Municipio> comboMunicipio; // Cambia el JTextField por un JComboBox

    public UbicacionForm() {
        setTitle("Gestión de Ubicaciones");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Latitud", "Longitud", "Municipio"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField(); formPanel.add(txtId);

        formPanel.add(new JLabel("Latitud:"));
        txtLatitud = new JTextField(); formPanel.add(txtLatitud);

        formPanel.add(new JLabel("Longitud:"));
        txtLongitud = new JTextField(); formPanel.add(txtLongitud);

        formPanel.add(new JLabel("Municipio:"));
        comboMunicipio = new JComboBox<>(); // Inicializa el JComboBox
        formPanel.add(comboMunicipio);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        formPanel.add(btnGuardar);
        formPanel.add(btnActualizar);

        add(formPanel, BorderLayout.NORTH);

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        add(btnEliminar, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(e -> guardarUbicacion());
        btnActualizar.addActionListener(e -> actualizarUbicacion());
        btnEliminar.addActionListener(e -> eliminarUbicacion());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarCampos());

        cargarMunicipios(); // Carga los municipios en el JComboBox
        cargarUbicaciones();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarMunicipios() {
        MunicipioDAO municipioDAO = new MunicipioDAO();
        List<Municipio> municipios = municipioDAO.listarMunicipios(); // Asegúrate de tener este método en MunicipioDAO
        for (Municipio municipio : municipios) {
            comboMunicipio.addItem(municipio);
        }
    }

    private void cargarUbicaciones() {
        modelo.setRowCount(0);
        UbicacionDAO dao = new UbicacionDAO();
        List<Ubicacion> ubicaciones = dao.listarUbicaciones();

        for (Ubicacion u : ubicaciones) {
            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getLatitud(),
                    u.getLongitud(),
                    u.getMunicipio()
            });
        }
    }

    private void guardarUbicacion() {
        try {
            int id = Integer.parseInt(txtId.getText());
            double lat = Double.parseDouble(txtLatitud.getText());
            double lon = Double.parseDouble(txtLongitud.getText());
            Municipio municipio = (Municipio) comboMunicipio.getSelectedItem();

            Ubicacion u = new Ubicacion(id, lon, lat, municipio.getId());
            boolean ok = new UbicacionDAO().insertarUbicacion(u);

            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Ubicación registrada.");
                cargarUbicaciones();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❗ Verifica los campos numéricos.");
        }
    }

    private void actualizarUbicacion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una ubicación para actualizar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            double lat = Double.parseDouble(txtLatitud.getText());
            double lon = Double.parseDouble(txtLongitud.getText());
            Municipio municipio = (Municipio) comboMunicipio.getSelectedItem();

            Ubicacion u = new Ubicacion(id, lon, lat, municipio.getId());
            boolean ok = new UbicacionDAO().actualizarUbicacion(u);


            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Ubicación actualizada.");
                cargarUbicaciones();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❗ Verifica los campos.");
        }
    }

    private void eliminarUbicacion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una ubicación para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar ubicación ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (new UbicacionDAO().eliminarUbicacion(id)) {
                JOptionPane.showMessageDialog(this, "✅ Ubicación eliminada.");
                cargarUbicaciones();
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
            txtLatitud.setText(modelo.getValueAt(fila, 1).toString());
            txtLongitud.setText(modelo.getValueAt(fila, 2).toString());
            // Selecciona el municipio en el JComboBox
            int municipioId = (int) modelo.getValueAt(fila, 3); // Asumiendo que la columna 3 tiene el ID del municipio
            for (int i = 0; i < comboMunicipio.getItemCount(); i++) {
                if (comboMunicipio.getItemAt(i).getId() == municipioId) {
                    comboMunicipio.setSelectedIndex(i);
                    break;
                }
            }
            txtId.setEnabled(false);
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        comboMunicipio.setSelectedIndex(-1); // Deselecciona el JComboBox
        txtId.setEnabled(true);
    }
}