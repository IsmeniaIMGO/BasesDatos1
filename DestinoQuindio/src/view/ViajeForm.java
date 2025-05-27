package src.view;

import src.dao.*;
import src.model.Viaje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class ViajeForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> cbCliente, cbConductor, cbPago, cbEstado, cbVehiculo, cbOrigen, cbDestino;
    private JButton btnGuardar, btnActualizar, btnEliminar;

    public ViajeForm() {
        setTitle("Gestión de Viajes");
        setSize(1200, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Datos del Viaje"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // espacio entre componentes
        gbc.anchor = GridBagConstraints.LINE_END; // etiquetas alineadas a la derecha
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;

        cbCliente = new JComboBox<>();
        cbConductor = new JComboBox<>();
        cbPago = new JComboBox<>();
        cbEstado = new JComboBox<>();
        cbVehiculo = new JComboBox<>();
        cbOrigen = new JComboBox<>();
        cbDestino = new JComboBox<>();

        cargarCombos();

        panelForm.add(new JLabel("Cliente:")); panelForm.add(cbCliente);
        panelForm.add(new JLabel("Conductor:")); panelForm.add(cbConductor);
        panelForm.add(new JLabel("Pago:")); panelForm.add(cbPago);
        panelForm.add(new JLabel("Vehículo:")); panelForm.add(cbVehiculo);
        panelForm.add(new JLabel("Estado:")); panelForm.add(cbEstado);
        panelForm.add(new JLabel("Origen:")); panelForm.add(cbOrigen);
        panelForm.add(new JLabel("Destino:")); panelForm.add(cbDestino);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        modelo = new DefaultTableModel(new Object[]{
                "ID", "Fecha", "Pago", "Conductor", "Cliente", "Origen", "Destino", "Estado", "Vehiculo"
        }, 0);
        tabla = new JTable(modelo);

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarViajes();

        btnGuardar.addActionListener(e -> guardarViaje());
        btnActualizar.addActionListener(e -> actualizarViaje());
        btnEliminar.addActionListener(e -> eliminarViaje());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarCombos() {
        // ⚠️ Reemplaza estos métodos por consultas reales desde tus DAOs

        cbCliente.removeAllItems();
        for (Object[] c : new ClienteDAO().listarClientesConNombre()) {
            cbCliente.addItem(c[0] + " - " + c[1]); // cc - nombre
        }

        cbConductor.removeAllItems();
        for (Object[] c : new ConductorDAO().listarConductoresConNombre()) {
            cbConductor.addItem(c[0] + " - " + c[1]);
        }

        cbPago.removeAllItems();
        for (Object[] p : new PagoDAO().listarPagosConDetalle()) {
            cbPago.addItem(p[0] + " - $" + p[1]);
        }

        cbEstado.removeAllItems();
        for (Object[] e : new EstadoDAO().listarEstados()) {
            cbEstado.addItem(e[0] + " - " + e[1]);
        }

        cbVehiculo.removeAllItems();
        for (Object[] v : new VehiculoDAO().listarVehiculosDatos()) {
            cbVehiculo.addItem(v[0] + " - " + v[1]);
        }

        cbOrigen.removeAllItems();
        cbDestino.removeAllItems();
        for (Object[] u : new UbicacionDAO().listarUbicacionesConMunicipio()) {
            cbOrigen.addItem(u[0] + " - " + u[3]); // id - municipio
            cbDestino.addItem(u[0] + " - " + u[3]);
        }
    }

    private void cargarViajes() {
        modelo.setRowCount(0);
        for (Object[] fila : new ViajeDAO().listarViajesConDatos()) {
            // fila contiene: id, fecha, cliente, conductor, pago, origen, destino, estado, vehiculo
            Object[] filaCompleta = new Object[]{
                    fila[0], // ID
                    fila[1], // Fecha
                    fila[2], // Cliente
                    fila[3], // Conductor
                    fila[4], // Pago
                    fila[5], // Origen
                    fila[6], // Destino
                    fila[7], // Estado
                    fila[8]  // Vehículo
            };
            modelo.addRow(filaCompleta);
        }
    }



    private int obtenerIdDesdeCombo(JComboBox<String> cb) {
        return Integer.parseInt(cb.getSelectedItem().toString().split(" - ")[0]);
    }

    private String obtenerTextoDesdeCombo(JComboBox<String> cb) {
        return cb.getSelectedItem().toString().split(" - ")[0];
    }

    private void guardarViaje() {
        Viaje v = new Viaje();
        v.setFecha(new Date());
        v.setCliente(obtenerIdDesdeCombo(cbCliente));
        v.setConductor(obtenerIdDesdeCombo(cbConductor));
        v.setPago(obtenerIdDesdeCombo(cbPago));
        v.setUbicacionOrigen(obtenerIdDesdeCombo(cbOrigen));
        v.setUbicacionDestino(obtenerIdDesdeCombo(cbDestino));
        v.setEstado(obtenerIdDesdeCombo(cbEstado));
        v.setVehiculo(obtenerTextoDesdeCombo(cbVehiculo));
       

        if (new ViajeDAO().insertarViaje(v)) {
            JOptionPane.showMessageDialog(this, "✅ Viaje registrado.");
            cargarViajes();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar viaje.");
        }
    }

    private void actualizarViaje() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un viaje para actualizar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        Viaje v = new Viaje();
        v.setId(id);
        v.setFecha(new Date());
        v.setCliente(obtenerIdDesdeCombo(cbCliente));
        v.setConductor(obtenerIdDesdeCombo(cbConductor));
        v.setPago(obtenerIdDesdeCombo(cbPago));
        v.setEstado(obtenerIdDesdeCombo(cbEstado));
        v.setVehiculo(obtenerTextoDesdeCombo(cbVehiculo));
        v.setUbicacionOrigen(obtenerIdDesdeCombo(cbOrigen));
        v.setUbicacionDestino(obtenerIdDesdeCombo(cbDestino));

        if (new ViajeDAO().actualizarViaje(v)) {
            JOptionPane.showMessageDialog(this, "✅ Viaje actualizado.");
            cargarViajes();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar viaje.");
        }
    }

    private void eliminarViaje() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un viaje para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar viaje ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (new ViajeDAO().eliminarViaje(id)) {
                JOptionPane.showMessageDialog(this, "✅ Viaje eliminado.");
                cargarViajes();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar.");
            }
        }
    }
}
