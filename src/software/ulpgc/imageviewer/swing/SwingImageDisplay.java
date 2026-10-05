package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Shift shift = Shift.Null;
    private Released released = Released.Null;
    private int initShift;
    private final List<Paint> paints = new ArrayList<>();
    private final Map<String, BufferedImage> cache = new HashMap<>();

    public SwingImageDisplay() {
        MouseAdapter adapter = mouseAdapter();
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
    }

    private MouseAdapter mouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initShift = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX() - initShift);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - initShift);
            }
        };
    }

    @Override
    public void paint(String id, int offset) {
        paints.add(new Paint(id, offset));
        repaint();
    }

    @Override
    public void clear() {
        paints.clear();
    }

    private static final Map<String,Color> colors = Map.of(
            "red", Color.RED,
            "green", Color.GREEN,
            "blue", Color.BLUE,
            "yellow", Color.YELLOW,
            "orange", Color.ORANGE
    );

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Paint paint : paints) {
            BufferedImage image = load(paint.id);
            if (image != null) drawFitted(g, image, paint.offset);
            else {
                g.setColor(colors.getOrDefault(paint.id, Color.GRAY));
                g.fillRect(paint.offset, 0, getWidth(), getHeight());
            }
        }
    }

    private BufferedImage load(String id) {
        if (colors.containsKey(id)) return null;
        return cache.computeIfAbsent(id, key -> {
            try {
                return javax.imageio.ImageIO.read(new File(key));
            } catch (IOException e) {
                return null;
            }
        });
    }

    private void drawFitted(Graphics g, BufferedImage image, int offset) {
        double scale = Math.min((double) getWidth() / image.getWidth(), (double) getHeight() / image.getHeight());
        int w = (int) (image.getWidth() * scale);
        int h = (int) (image.getHeight() * scale);
        g.drawImage(image, offset + (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }

    private record Paint(String id, int offset) {
    }
}
