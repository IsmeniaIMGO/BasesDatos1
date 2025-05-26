package src.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    // Componentes de la interfaz
    private JTextField display;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton addButton, subButton, mulButton, divButton, equButton, clrButton;
    private JPanel panel;

    private double num1 = 0;
    private double num2 = 0;
    private String operator = "";
    private boolean newNumber = true; // Indica si se debe iniciar un nuevo número en el display

    public Calculadora() {
        // Configuración de la ventana
        setTitle("Calculadora Sencilla");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Display de la calculadora
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        // Panel para los botones
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 filas, 4 columnas, espaciado de 10px

        // Botones de números
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            numberButtons[i].addActionListener(this);
        }

        // Botones de operaciones
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        equButton = new JButton("=");
        clrButton = new JButton("C");

        operationButtons = new JButton[]{addButton, subButton, mulButton, divButton, equButton, clrButton};
        for (JButton button : operationButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            button.setBackground(new Color(200, 200, 200)); // Un color de fondo para las operaciones
        }

        // Añadir botones al panel
        // Fila 1
        panel.add(clrButton);
        panel.add(new JButton("")); // Espacio en blanco
        panel.add(new JButton("")); // Espacio en blanco
        panel.add(divButton);

        // Fila 2
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        // Fila 3
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        // Fila 4
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        // Fila 5
        panel.add(new JButton("")); // Espacio en blanco
        panel.add(numberButtons[0]);
        panel.add(new JButton("")); // Espacio en blanco
        panel.add(equButton);


        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Si es un número
        if (command.matches("[0-9]")) {
            if (newNumber) {
                display.setText(command);
                newNumber = false;
            } else {
                if (display.getText().equals("0")) { // Evitar múltiplos ceros al inicio
                    display.setText(command);
                } else {
                    display.setText(display.getText() + command);
                }
            }
        }
        // Si es una operación
        else if (command.matches("[+\\-*/]")) {
            num1 = Double.parseDouble(display.getText());
            operator = command;
            newNumber = true; // Preparar para el siguiente número
        }
        // Si es el botón de igual
        else if (command.equals("=")) {
            if (!operator.isEmpty() && !newNumber) { // Asegurarse de que hay una operación y un segundo número
                num2 = Double.parseDouble(display.getText());
                double result = 0;
                try {
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 == 0) {
                                display.setText("Error: Div por 0");
                                return;
                            }
                            result = num1 / num2;
                            break;
                    }
                    display.setText(String.valueOf(result));
                    num1 = result; // El resultado se convierte en el primer número para futuras operaciones
                    operator = ""; // Limpiar el operador
                    newNumber = true; // Preparar para un nuevo cálculo
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            }
        }
        // Si es el botón de limpiar (C)
        else if (command.equals("C")) {
            display.setText("0");
            num1 = 0;
            num2 = 0;
            operator = "";
            newNumber = true;
        }
    }

}