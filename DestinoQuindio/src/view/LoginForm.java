package src.view;

import src.dao.*;
import src.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class LoginForm extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCrearCuenta;

    public LoginForm() {
        setTitle("Login - Sistema de Transporte Turístico");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Iniciar sesión");
        add(btnLogin);

        btnCrearCuenta = new JButton("Crear cuenta");
        add(btnCrearCuenta);
        btnCrearCuenta.addActionListener(e -> {
            new RegistroForm();
            dispose(); // Cierra Login mientras registra
        });

        // Acción de login
        btnLogin.addActionListener(e -> autenticarUsuario());

        setVisible(true);
    }

    private void autenticarUsuario() {
        String correo = txtCorreo.getText();
        String contrasena = new String(txtPassword.getPassword());

        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuenta = cuentaDAO.obtenerCuentaPorCorreo(correo);

        if (cuenta != null && cuenta.getcontrasena().equals(contrasena)) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.obtenerUsuarioPorCuenta(correo);

            if (usuario != null) {
                registrarSesion(usuario);
                JOptionPane.showMessageDialog(this, "✅ Bienvenido, " + usuario.getNombre());

                // Redirigir según nivel
                switch (usuario.getNivel()) {
                    case 1:
                        new MenuAdmin(usuario); break;
                    case 2:
                        new MenuConductor(usuario); break;
                    case 3:
                        new MenuCliente(usuario); break;
                    default:
                        JOptionPane.showMessageDialog(this, "❌ Nivel de acceso no válido.");
                }
                dispose(); // Cerrar login
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuario no registrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Credenciales incorrectas.");
        }
    }

    private void registrarSesion(Usuario usuario) {
        HistorialSesionDAO historialDAO = new HistorialSesionDAO();
        HistorialSesion h = new HistorialSesion();
        h.setId((int) (System.currentTimeMillis() % 100000)); // ID temporal simple
        h.setFecha(new Date());
        h.setDescripcion("Inicio de sesión de " + usuario.getNombre());

        historialDAO.insertarSesion(h);
    }
}
