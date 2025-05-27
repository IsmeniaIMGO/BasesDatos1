package src.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame {

    private JTextField display;
    private JPanel panel;
    private String lastCommand;
    private double result;
    private boolean start;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cambiar a DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);

        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        for (String label : buttons) {
            JButton b = new JButton(label);
            b.addActionListener(label.matches("[0-9.]") ? insert : command);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);

        lastCommand = "=";
        result = 0;
        start = true;
    }

    private void calculate(double x) {
        switch (lastCommand) {
            case "+":
                result += x;
                break;
            case "-":
                result -= x;
                break;
            case "*":
                result *= x;
                break;
            case "/":
                if (x == 0) {
                    result = 0;
                } else {
                    result /= x;
                }
                break;
            case "=":
                result = x;
                break;
        }
        display.setText("" + result);
    }

    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }

    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();

            if (start) {
                lastCommand = command;
            } else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }
}