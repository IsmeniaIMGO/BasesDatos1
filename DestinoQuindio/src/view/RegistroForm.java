package src.view;

import src.dao.*;
import src.model.*;
import javax.swing.*;
import java.awt.*;
import src.controller.*;;

public class RegistroForm extends JFrame {
    private JTextField txtNombre, txtTelefono, txtCorreo, txtUsuario, txtPlaca;
    private JPasswordField txtContrasena;
    private JRadioButton rbCliente, rbConductor;
    private JButton btnRegistrar;
    private JTextField txtCc;

    public RegistroForm() {
        setTitle("Registro de Nuevo Usuario");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        add(new JLabel("CC:"));
        txtCc = new JTextField(); add(txtCc);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField(); add(txtNombre);

        add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField(); add(txtTelefono);

        add(new JLabel("Correo:"));
        txtCorreo = new JTextField(); add(txtCorreo);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(); add(txtUsuario);

        add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField(); add(txtContrasena);

        add(new JLabel("Tipo de usuario:"));
        JPanel panelTipo = new JPanel(new FlowLayout());
        rbCliente = new JRadioButton("Cliente");
        rbConductor = new JRadioButton("Conductor");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbCliente);
        grupo.add(rbConductor);
        panelTipo.add(rbCliente); panelTipo.add(rbConductor);
        add(panelTipo);

        add(new JLabel("Placa (si es conductor):"));
        txtPlaca = new JTextField(); add(txtPlaca);

        btnRegistrar = new JButton("Registrar");
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrar());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void registrar() {
        try {
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());
            String placa = txtPlaca.getText();

            // Validaciones básicas
            if (correo.isEmpty() || contrasena.isEmpty() || (!rbCliente.isSelected() && !rbConductor.isSelected())) {
                JOptionPane.showMessageDialog(this, "❗ Todos los campos obligatorios deben estar completos.");
                return;
            }

            // 1. Insertar en Cuenta
            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = new Cuenta(correo, usuario, contrasena);
            if (!cuentaDAO.insertarCuenta(cuenta)) {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar cuenta.");
                return;
            }

            // 2. Insertar en Usuario con nivel 2
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            int cc;
            try {
                cc = Integer.parseInt(txtCc.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ La cédula debe ser un número válido.");
                return;
            }
            Usuario newusuario = new Usuario(cc, nombre, telefono, 2, correo, 0);
            if (!usuarioDAO.insertarUsuario(newusuario)) {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar usuario.");
                return;
            }

            // 3. Insertar en Cliente o Conductor
            if (rbCliente.isSelected()) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = new Cliente(cc);
                clienteDAO.insertarCliente(cliente);
            } else {
                if (placa.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❗ Debes ingresar la placa del vehículo.");
                    return;
                }
                ConductorDAO conductorDAO = new ConductorDAO();
                Conductor conductor = new Conductor(cc, placa);
                conductorDAO.insertarConductor(conductor);
            }

            JOptionPane.showMessageDialog(this, "✅ Registro exitoso. Ahora inicia sesión.");
            new LoginForm(); // Vuelve al login
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error en el registro: " + e.getMessage());
        }
    }
}
