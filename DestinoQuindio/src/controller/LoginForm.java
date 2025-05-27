package src.controller;

import src.dao.*;
import src.model.*;
import src.view.*;

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

            // Obtener la cédula del usuario
            int cc = usuario.getCc();

            // Verificar si es Cliente o Conductor
            ClienteDAO clienteDAO = new ClienteDAO();
            ConductorDAO conductorDAO = new ConductorDAO();
            AdministradorDAO administradorDAO = new AdministradorDAO();
            JefeDAO jefeDAO = new JefeDAO();

            if (clienteDAO.obtenerClientePorCc(cc) != null) {
                new MenuCliente(usuario);
            } else if (conductorDAO.obtenerConductorPorCc(cc) != null) {
                new MenuConductor(usuario);
            } else if (administradorDAO.obtenerAdministradorPorCc(cc) != null) {
                new MenuAdmin(usuario);
            } else if (jefeDAO.obtenerJefePorCc(cc) != null) {
                new MenuAdmin(usuario);
            
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo determinar el tipo de usuario.");
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
