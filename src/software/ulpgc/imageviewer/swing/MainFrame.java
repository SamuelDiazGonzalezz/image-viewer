package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageDisplay;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ImageDisplay imageDisplay;
    private final JButton prevButton = new JButton("<");
    private final JButton nextButton = new JButton(">");
    private final JButton addButton = new JButton("Añadir");

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

    public void onAdd(java.util.function.Consumer<java.util.List<java.io.File>> action) {
        addButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Imágenes", "png", "jpg", "jpeg", "gif", "bmp"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                action.accept(java.util.List.of(chooser.getSelectedFiles()));
        });
    }

    private Component createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay();
        this.imageDisplay = display;
        return display;
    }

    private Component createToolbar() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(addButton);
        panel.add(prevButton);
        panel.add(nextButton);
        return panel;
    }
}
