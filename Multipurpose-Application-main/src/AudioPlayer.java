
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import javax.swing.*;
import javax.swing.filechooser.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class AudioPlayer extends JFrame implements ActionListener {

private JTextField filePathField;
private JButton playButton, pauseButton, chooseButton, loopButton, searchButton, backButton;
private JFileChooser fileChooser;
private boolean isPaused = false, isLooping = false;
private Player player;
private FileInputStream fileInputStream;
private BufferedInputStream bufferedInputStream;
private Thread playerThread;
private long totalLength, pausePosition;
private String filePath;

public AudioPlayer() {
    super("Music Player");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 220);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());

    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/icon_256x256.jpg"))); // FOR ICON

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

    filePathField = new JTextField(20);
    playButton = createStyledButton("Play");
    pauseButton = createStyledButton("Pause");
    chooseButton = createStyledButton("Choose File");
    loopButton = createStyledButton("Loop");
    searchButton = createStyledButton("Search Spotify");
    backButton = createStyledButton("Back");

    playButton.addActionListener(this);
    pauseButton.addActionListener(this);
    chooseButton.addActionListener(this);
    loopButton.addActionListener(this);
    backButton.addActionListener(e -> back());

    searchButton.addActionListener(e -> {
        String songName = JOptionPane.showInputDialog(this, "Enter song name:");
        if (songName != null && !songName.trim().isEmpty()) {
            searchAndPlaySpotify(songName);
        }
    });

    fileChooser = new JFileChooser(".");
    fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));

    // Layout adjustments
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    add(filePathField, gbc);

    gbc.gridy = 1;
    gbc.gridwidth = 1;
    add(chooseButton, gbc);

    gbc.gridx = 1;
    add(playButton, gbc);

    gbc.gridx = 2;
    add(pauseButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    add(loopButton, gbc);

    gbc.gridx = 1;
    add(searchButton, gbc);

    gbc.gridx = 2;
    add(backButton, gbc);

    setVisible(true);
}

private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(Color.DARK_GRAY);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setPreferredSize(new Dimension(140, 40));
    return button;
}

public void actionPerformed(ActionEvent event) {
    if (event.getSource() == playButton) {
        if (isPaused) {
            resumeMusic();
        } else {
            playMusic();
        }
    } else if (event.getSource() == pauseButton) {
        pauseMusic();
    } else if (event.getSource() == chooseButton) {
        chooseFile();
    } else if (event.getSource() == loopButton) {
        toggleLoop();
    }
}

private void playMusic() {
    stopMusic();
    try {
        filePath = filePathField.getText();
        fileInputStream = new FileInputStream(filePath);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        player = new Player(bufferedInputStream);
        totalLength = fileInputStream.available();

        playerThread = new Thread(() -> {
            try {
                do {
                    player.play();
                    if (isLooping) {
                        fileInputStream = new FileInputStream(filePath);
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                        player = new Player(bufferedInputStream);
                    }
                } while (isLooping);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        playerThread.start();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error playing file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void pauseMusic() {
    if (player != null) {
        try {
            pausePosition = fileInputStream.available();
            player.close();
            isPaused = true;
            pauseButton.setText("Resume");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

private void resumeMusic() {
    try {
        fileInputStream = new FileInputStream(filePath);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        fileInputStream.skip(totalLength - pausePosition);
        player = new Player(bufferedInputStream);

        playerThread = new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        playerThread.start();

        isPaused = false;
        pauseButton.setText("Pause");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void back() {
    int id = Login.getUser_id();
    Dashboard dash = new Dashboard();
    dash.getDetails(id);
    dash.setVisible(true);
    this.dispose();
}

private void stopMusic() {
    if (player != null) {
        player.close();
    }
}

private void searchAndPlaySpotify(String songName) {
    try {
        String searchQuery = songName.replace(" ", "%20");
        String url = "https://open.spotify.com/search/" + searchQuery;
        Desktop.getDesktop().browse(new URI(url));
    } catch (IOException | java.net.URISyntaxException e) {
        e.printStackTrace();
    }
}

private void chooseFile() {
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        filePathField.setText(selectedFile.getAbsolutePath());
    }
}

private void toggleLoop() {
    isLooping = !isLooping;
    loopButton.setText(isLooping ? "Stop Loop" : "Loop");
}

public static void main(String[] args) {
    new AudioPlayer();
}
}
