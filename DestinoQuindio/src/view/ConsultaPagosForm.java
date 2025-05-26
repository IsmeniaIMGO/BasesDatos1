package src.view;

import src.dao.PagoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaPagosForm extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public ConsultaPagosForm() {
        setTitle("Consulta de Pagos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Valor", "Método de Pago"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarPagos();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarPagos() {
        modelo.setRowCount(0);
        PagoDAO dao = new PagoDAO();
        List<Object[]> pagos = dao.listarPagosConDetalle();

        for (Object[] fila : pagos) {
            modelo.addRow(fila);
        }
    }
}
