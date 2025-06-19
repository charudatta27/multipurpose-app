import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import javax.swing.*;
import javax.swing.filechooser.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class Music extends JFrame implements ActionListener {

private JFrame frame;
private JTextField filePathField;
private JButton playButton, pauseButton, chooseButton, loopButton, searchButton;
private JFileChooser fileChooser;
private boolean isPaused = false, isLooping = false;
private Player player;
private FileInputStream fileInputStream;
private BufferedInputStream bufferedInputStream;
private Thread playerThread;
private long totalLength, pausePosition;
private String filePath;

public Music() {

    super("Music Player");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/icon_256x256.jpg"))); //FOR ICON 
    filePathField = new JTextField(20);
    playButton = new JButton("Play");
    pauseButton = new JButton("Pause");
    chooseButton = new JButton("Choose File");
    loopButton = new JButton("Loop");
    searchButton = new JButton("Search Spotify");

    playButton.addActionListener(this);
    pauseButton.addActionListener(this);
    chooseButton.addActionListener(this);
    loopButton.addActionListener(this);
    searchButton.addActionListener(e -> {
        String songName = JOptionPane.showInputDialog(frame, "Enter song name:");
        if (songName != null && !songName.trim().isEmpty()) {
            searchAndPlaySpotify(songName);
        }
    });
    add(filePathField);
    add(chooseButton);
    add(playButton);
    add(pauseButton);
    add(loopButton);
    add(searchButton);

    fileChooser = new JFileChooser(".");
    fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));

    setSize(500, 120);
    setLocationRelativeTo(null);
    setVisible(true);

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
        System.out.println("Error playing file: " + e.getMessage());
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

private void stopMusic() {
    if (player != null) {
        player.close();
    }
}

private void searchAndPlaySpotify(String songName) {
    try {
        String searchQuery = songName.replace(" ", "%20"); // Convert spaces to URL format
        String url = "https://open.spotify.com/search/" + searchQuery;
        Desktop.getDesktop().browse(new URI(url)); // Opens in default web browser
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
    new Music();
}
}