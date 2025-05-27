package src.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConversorMonedas extends JFrame implements ActionListener {

    private JTextField amountTextField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JButton convertButton;
    private JLabel resultLabel;

    // Tasa de cambio (simplificada para el ejemplo)
    private static final double COP_TO_USD_RATE = 0.00025; // 1 COP = 0.00025 USD (ejemplo)
    private static final double USD_TO_COP_RATE = 4000.0;  // 1 USD = 4000 COP (ejemplo)

    public ConversorMonedas() {
        setTitle("Conversor de Monedas");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cambiar a DISPOSE_ON_CLOSE
        setLocationRelativeTo(null); // Centrar la ventana

        // Configurar el panel principal con un GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 filas, 2 columnas, espaciado

        // Componentes de la interfaz
        JLabel amountLabel = new JLabel("Cantidad:");
        amountTextField = new JTextField(10);

        JLabel fromCurrencyLabel = new JLabel("De:");
        String[] currencies = {"COP (Pesos Colombianos)", "USD (Dólares Estadounidenses)"};
        fromCurrencyComboBox = new JComboBox<>(currencies);

        JLabel toCurrencyLabel = new JLabel("A:");
        toCurrencyComboBox = new JComboBox<>(currencies);

        convertButton = new JButton("Convertir");
        convertButton.addActionListener(this); // Escuchador de eventos para el botón

        resultLabel = new JLabel("Resultado: ");

        // Añadir componentes al panel
        panel.add(amountLabel);
        panel.add(amountTextField);
        panel.add(fromCurrencyLabel);
        panel.add(fromCurrencyComboBox);
        panel.add(toCurrencyLabel);
        panel.add(toCurrencyComboBox);
        panel.add(new JLabel("")); // Espaciador
        panel.add(convertButton);
        panel.add(resultLabel);

        add(panel, BorderLayout.CENTER); // Añadir el panel al JFrame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertButton) {
            try {
                double amount = Double.parseDouble(amountTextField.getText());
                String fromCurrency = (String) fromCurrencyComboBox.getSelectedItem();
                String toCurrency = (String) toCurrencyComboBox.getSelectedItem();
                double convertedAmount = 0.0;

                if (fromCurrency.equals("COP (Pesos Colombianos)") && toCurrency.equals("USD (Dólares Estadounidenses)")) {
                    convertedAmount = amount * COP_TO_USD_RATE;
                    resultLabel.setText(String.format("Resultado: %.2f USD", convertedAmount));
                } else if (fromCurrency.equals("USD (Dólares Estadounidenses)") && toCurrency.equals("COP (Pesos Colombianos)")) {
                    convertedAmount = amount * USD_TO_COP_RATE;
                    resultLabel.setText(String.format("Resultado: %.2f COP", convertedAmount));
                } else if (fromCurrency.equals(toCurrency)) {
                    resultLabel.setText("Resultado: " + String.format("%.2f", amount) + " (misma moneda)");
                } else {
                    resultLabel.setText("Error: Conversión no soportada.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce una cantidad válida.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}