package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageDisplay;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ImageDisplay imageDisplay;
    private final JButton prevButton = new JButton("<");
    private final JButton nextButton = new JButton(">");

    public MainFrame()  {
        this.setTitle("Image Viewer");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(createImageDisplay(), BorderLayout.CENTER);
        this.add(createToolbar(), BorderLayout.SOUTH);
    }

    public ImageDisplay getImageDisplay() {
        return imageDisplay;
    }

    public void onPrev(Runnable action) {
        prevButton.addActionListener(e -> action.run());
    }

    public void onNext(Runnable action) {
        nextButton.addActionListener(e -> action.run());
    }

    private Component createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay();
        this.imageDisplay = display;
        return display;
    }

    private Component createToolbar() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(prevButton);
        panel.add(nextButton);
        return panel;
    }
}
