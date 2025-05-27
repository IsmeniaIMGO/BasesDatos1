package src.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendario extends JFrame implements ActionListener {

    private JButton prevMonthButton, nextMonthButton, prevYearButton, nextYearButton;
    private JLabel monthYearLabel;
    private JPanel calendarPanel;
    private JPanel controlPanel;

    private GregorianCalendar calendar; // Usaremos GregorianCalendar para manejar fechas

    public Calendario() {
        // --- Configuración de la Ventana ---
        setTitle("Calendario");
        setSize(500, 400); // Tamaño ajustado para el calendario
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cambiar a DISPOSE_ON_CLOSE
        setResizable(false);
        setLocationRelativeTo(null); // Centra la ventana

        // --- Inicialización del Calendario ---
        calendar = new GregorianCalendar(); // Inicia con la fecha actual

        // --- Panel de Control (Navegación) ---
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        prevYearButton = new JButton("<< Año");
        prevYearButton.addActionListener(this);
        nextYearButton = new JButton("Año >>");
        nextYearButton.addActionListener(this);
        prevMonthButton = new JButton("< Mes");
        prevMonthButton.addActionListener(this);
        nextMonthButton = new JButton("Mes >");
        nextMonthButton.addActionListener(this);

        monthYearLabel = new JLabel("", SwingConstants.CENTER); // Etiqueta para mostrar Mes y Año
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 20));

        controlPanel.add(prevYearButton);
        controlPanel.add(prevMonthButton);
        controlPanel.add(monthYearLabel);
        controlPanel.add(nextMonthButton);
        controlPanel.add(nextYearButton);

        add(controlPanel, BorderLayout.NORTH);

        // --- Panel del Calendario (Días) ---
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7, 5, 5)); // 7 columnas para los días de la semana, filas variables
        add(calendarPanel, BorderLayout.CENTER);

        // --- Dibujar el calendario inicial ---
        drawCalendar();

        setVisible(true);
    }

    private void drawCalendar() {
        calendarPanel.removeAll(); // Limpiar el panel antes de redibujar

        // --- Nombres de los días de la semana ---
        String[] dayNames = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (String dayName : dayNames) {
            JLabel label = new JLabel(dayName, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.BLUE);
            calendarPanel.add(label);
        }

        // --- Obtener información del mes actual ---
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        // Actualizar la etiqueta Mes/Año
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.getDefault());
        monthYearLabel.setText(monthName + " " + currentYear);

        // Establecer el calendario al primer día del mes para calcular el día de la semana
        GregorianCalendar tempCalendar = (GregorianCalendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK); // 1 = Domingo, 7 = Sábado

        // Obtener el número de días en el mes actual
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // --- Añadir espacios en blanco al inicio del mes ---
        for (int i = 1; i < firstDayOfWeek; i++) {
            calendarPanel.add(new JLabel("")); // Celdas vacías para los días antes del primer día del mes
        }

        // --- Añadir los días del mes ---
        for (int i = 1; i <= daysInMonth; i++) {
            JLabel dayLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            
            // Marcar el día actual
            GregorianCalendar today = new GregorianCalendar();
            if (i == today.get(Calendar.DAY_OF_MONTH) &&
                currentMonth == today.get(Calendar.MONTH) &&
                currentYear == today.get(Calendar.YEAR)) {
                dayLabel.setForeground(Color.RED); // Día actual en rojo
                dayLabel.setFont(new Font("Arial", Font.BOLD, 18));
            }

            calendarPanel.add(dayLabel);
        }

        calendarPanel.revalidate(); // Revalidar el panel para que los nuevos componentes se muestren
        calendarPanel.repaint();    // Repintar para asegurar el renderizado
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevMonthButton) {
            calendar.add(Calendar.MONTH, -1); // Retrocede un mes
        } else if (e.getSource() == nextMonthButton) {
            calendar.add(Calendar.MONTH, 1); // Avanza un mes
        } else if (e.getSource() == prevYearButton) {
            calendar.add(Calendar.YEAR, -1); // Retrocede un año
        } else if (e.getSource() == nextYearButton) {
            calendar.add(Calendar.YEAR, 1); // Avanza un año
        }
        drawCalendar(); // Redibujar el calendario con la nueva fecha
    }

    
}
