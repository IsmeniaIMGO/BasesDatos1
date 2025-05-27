package src.view;

import src.dao.*;
import src.model.*;

import javax.swing.*;
import java.awt.*;

public class UsuarioForm extends JFrame {

    private JTextField txtCc, txtNombre, txtTelefono, txtCorreo, txtUsuario, txtPlaca, txtMarca;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JButton btnGuardar;

    public UsuarioForm() {
        setTitle("Registro de Usuario - Admin");
        setSize(400, 400);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Cédula (CC):"));
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

        add(new JLabel("Rol:"));
        cbRol = new JComboBox<>(new String[] { "Cliente", "Conductor", "Jefe", "Administrador" });
        add(cbRol);

        add(new JLabel("Placa (si es conductor):"));
        txtPlaca = new JTextField(); add(txtPlaca);

        add(new JLabel("Marca de automóvil:"));
        txtMarca = new JTextField();
        add(txtMarca);
        
        btnGuardar = new JButton("Guardar usuario");
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarUsuario());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void guardarUsuario() {
        try {
            int cc = Integer.parseInt(txtCc.getText());
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());
            String rol = cbRol.getSelectedItem().toString();
            String placa = txtPlaca.getText();
            String marca = txtMarca.getText();

            if (correo.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❗ Debes completar todos los campos obligatorios.");
                return;
            }

            // Insertar en Cuenta
            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = new Cuenta(correo, usuario, contrasena);
            if (!cuentaDAO.insertarCuenta(cuenta)) {
                JOptionPane.showMessageDialog(this, "❌ Error al crear la cuenta.");
                return;
            }

            Thread.sleep(1000); // Espera un segundo para simular la inserción

            // Insertar en Usuario
            UsuarioDAO usuarioDAO = new UsuarioDAO();
             // Determinar nivel
            int nivel = 2; // Nivel por defecto para clientes y conductores
            if (rol.equals("Conductor")) {
                nivel = 2; // Nivel para conductores
            } else if (rol.equals("Cliente")) {
                nivel = 2; // Nivel para clientes
            } else if (rol.equals("Jefe")) {
                nivel = 3; // Nivel para jefes
            } else if (rol.equals("Administrador")) {
                nivel = 1; // Nivel para administradores
            }
            Usuario newusuario = new Usuario(cc, nombre, telefono, nivel, correo, 1);
            if (!usuarioDAO.insertarUsuario(newusuario)) {
                JOptionPane.showMessageDialog(this, "❌ Error al crear el usuario.");
                return;
            }


            Thread.sleep(1000); // Espera un segundo para simular la inserción



            // Insertar según rol
            switch (rol) {
                case "Cliente":
                    new ClienteDAO().insertarCliente(new Cliente(cc));
                    break;
                case "Conductor":
                    if (placa.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "❗ La placa es obligatoria para conductores.");
                        return;
                    }

                    if (marca.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "❗ La marca del automóvil es obligatoria para conductores.");
                        return;
                    }
                    new VehiculoDAO().insertarVehiculo(new Vehiculo(placa, marca));

                    Thread.sleep(1000); // Espera un segundo para simular la inserción

                    new ConductorDAO().insertarConductor(new Conductor(cc, placa));
                    break;
                case "Jefe":
                    new JefeDAO().insertarJefe(new Jefe(cc));
                    break;
                case "Administrador":
                    new AdministradorDAO().insertarAdministrador(new AdministradorDB(cc));
                    break;
            }

            JOptionPane.showMessageDialog(this, "✅ Usuario registrado correctamente.");
            dispose(); // Cierra el formulario

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Cédula inválida.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }
}
