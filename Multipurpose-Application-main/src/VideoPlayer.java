
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class VideoPlayer extends javax.swing.JFrame {

private final JFrame frame;
private final JPanel panel;
private final JButton selectFileButton;
private final JButton backButton;
private final String htmlFilePath = "video.html";

public VideoPlayer() {

    frame = new JFrame(" Video Player");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 300);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setLayout(new BorderLayout());
    frame.setIconImage(new ImageIcon(getClass().getResource("/icons/icon_256x256.jpg")).getImage());

    panel = new JPanel(new GridBagLayout()); // Center components
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Add spacing

    selectFileButton = new JButton("Select Video File");
    selectFileButton.setPreferredSize(new Dimension(200, 40));
    selectFileButton.addActionListener(e -> chooseVideoFile());

    backButton = new JButton("Back");
    backButton.setPreferredSize(new Dimension(200, 40));
    backButton.addActionListener(e -> goBack());

    // Add buttons to panel
    gbc.gridy = 0;
    panel.add(selectFileButton, gbc);

    gbc.gridy = 1;
    panel.add(backButton, gbc);

    frame.add(panel, BorderLayout.CENTER);
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/icon_256x256.jpg"))); //FOR ICON 
    frame.setVisible(true);

}

private void chooseVideoFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("MP4 Videos", "mp4"));
    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        updateHtmlFile(selectedFile.getAbsolutePath());
        openHtmlFile(); // Open the updated HTML file in the browser
    }
}

private void updateHtmlFile(String videoPath) {
    try {
        String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Video Player</title>
                    <style>
                        body { margin: 0; overflow: hidden; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: black; }
                        video { width: 100vw; height: 100vh; }
                    </style>
                </head>
                <body>
                    <video controls autoplay>
                        <source src='%s' type='video/mp4'>
                        Your browser does not support the video tag.
                    </video>
                </body>
                </html>
            """.formatted(new File(videoPath).toURI().toString());

        Files.write(Paths.get(htmlFilePath), htmlContent.getBytes());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void openHtmlFile() {
    try {
        File htmlFile = new File(htmlFilePath);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } else {
            JOptionPane.showMessageDialog(frame, "Desktop browsing not supported.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void goBack() {
    int id = Login.getUser_id();
    Dashboard dash = new Dashboard();
    dash.getDetails(id);
    dash.setVisible(true);
    this.dispose();

    frame.getContentPane().removeAll();
    frame.add(panel, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(VideoPlayer::new);
}
}
