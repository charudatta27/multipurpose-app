import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class date_time_reminder extends JFrame {
String title=ToDoList.gettitle();
    private JTextField dateField;
    private JTextField timeField;
    private JButton setReminderButton;
    private JTextField noteField;
    private String taskTitle;

    public date_time_reminder(String title) {
       
        setTitle("Set Reminder:" +title);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    

    private void initUI() {
        setLayout(new GridLayout(4, 1, 10, 10));
        JLabel notesLabel= new JLabel("Enter title:");
        noteField=new JTextField();

        JLabel dateLabel = new JLabel("Enter Date (yyyy-MM-dd):");
        dateField = new JTextField();

        JLabel timeLabel = new JLabel("Enter Time (HH:mm):");
        timeField = new JTextField();

        setReminderButton = new JButton("Set Reminder");
        setReminderButton.addActionListener(new ReminderActionListener());

        add(notesLabel);
        add(noteField);
        add(dateLabel);
        add(dateField);
        add(timeLabel);
        add(timeField);
        add(setReminderButton);
    }

    private class ReminderActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            String title=ToDoList.gettitle();
            String notes= noteField.getText();
            String dateInput = dateField.getText();
            String timeInput = timeField.getText();
            String dateTime = dateInput + " " + timeInput;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date reminderDate = sdf.parse(dateTime);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showNotification("Reminder for "  +notes, "It's time for: " +notes);
                    }
                }, reminderDate);
                JOptionPane.showMessageDialog(date_time_reminder.this, "Reminder set successfully for " + dateTime);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(date_time_reminder.this, "Invalid date or time format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void showNotification(String title, String message) {
            if (SystemTray.isSupported()) {
                try {
                    SystemTray tray = SystemTray.getSystemTray();
                    TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(""), title);
                    trayIcon.setImageAutoSize(true);
                    trayIcon.setToolTip(title);
                    tray.add(trayIcon);
                    trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);

                    // Remove the tray icon after a few seconds
                    Timer removeTrayIconTimer = new Timer();
                    removeTrayIconTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            tray.remove(trayIcon);
                        }
                    }, 5000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(date_time_reminder.this, message, title, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String title=ToDoList.gettitle();
            date_time_reminder app = new date_time_reminder(title);
            app.setVisible(true);
        });
    }
}
