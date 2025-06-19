
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class ToDoListReminderApp {

private JFrame frame;
private JTextField taskField;
private JDateChooser dateChooser;
private JSpinner timeSpinner;
private DefaultListModel<String> taskModel;
private JList<String> taskList;

public ToDoListReminderApp() {
    frame = new JFrame("To-Do List with Reminders");
    frame.setSize(400, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(null);

    JLabel taskLabel = new JLabel("Task:");
    taskLabel.setBounds(20, 20, 100, 25);
    frame.add(taskLabel);

    taskField = new JTextField();
    taskField.setBounds(100, 20, 200, 25);
    frame.add(taskField);

    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setBounds(20, 60, 100, 25);
    frame.add(dateLabel);

    dateChooser = new JDateChooser();
    dateChooser.setBounds(100, 60, 200, 25);
    frame.add(dateChooser);

    JLabel timeLabel = new JLabel("Time:");
    timeLabel.setBounds(20, 100, 100, 25);
    frame.add(timeLabel);

    timeSpinner = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
    timeSpinner.setEditor(timeEditor);
    timeSpinner.setBounds(100, 100, 100, 25);
    timeSpinner.setValue(new Date());
    frame.add(timeSpinner);

    JButton addButton = new JButton("Add Reminder");
    addButton.setBounds(100, 140, 150, 30);
    frame.add(addButton);

    taskModel = new DefaultListModel<>();
    taskList = new JList<>(taskModel);
    JScrollPane scrollPane = new JScrollPane(taskList);
    scrollPane.setBounds(20, 180, 350, 80);
    frame.add(scrollPane);

    addButton.addActionListener(e -> addReminder());

    frame.setVisible(true);
}

private void addReminder() {
    String task = taskField.getText();
    Date date = dateChooser.getDate();
    Date time = (Date) timeSpinner.getValue();

    if (task.isEmpty() || date == null) {
        JOptionPane.showMessageDialog(frame, "Please enter task and date.");
        return;
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    Calendar timeCal = Calendar.getInstance();
    timeCal.setTime(time);
    calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));

    Date reminderDateTime = calendar.getTime();
    if (reminderDateTime.before(new Date())) {
        JOptionPane.showMessageDialog(frame, "Cannot set a reminder in the past!");
        return;
    }

    String taskDetail = task + " - " + reminderDateTime;
    taskModel.addElement(taskDetail);

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
    @Override
    public void run() {
        showNotification("Reminder", task);
    }
    }, reminderDateTime);

    taskField.setText("");
    dateChooser.setDate(null);
    timeSpinner.setValue(new Date());
}

private void showNotification(String title, String message) {
    if (!SystemTray.isSupported()) {
        JOptionPane.showMessageDialog(frame, "System tray not supported. Cannot show notifications.");
        return;
    }

    SystemTray tray = SystemTray.getSystemTray();
    Image image = Toolkit.getDefaultToolkit().createImage(""); // No icon or provide path to icon
    TrayIcon trayIcon = new TrayIcon(image, "To-Do Reminder");
    trayIcon.setImageAutoSize(true);
    trayIcon.setToolTip("To-Do Reminder");

    try {
        tray.add(trayIcon);
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);

        // Remove the tray icon after displaying the message
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
            tray.remove(trayIcon);
        }
        }, 5000);
    } catch (AWTException e) {
        e.printStackTrace();
    }
}

public static void main(String[] args) {
    new ToDoListReminderApp();
}
}
