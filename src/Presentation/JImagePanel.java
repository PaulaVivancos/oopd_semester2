package Presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Custom JPanel that renders an image in the background
public class JImagePanel extends JPanel {

    // The image to render
    private BufferedImage image;
    private float opacity = 1.0f;

    // Constructor with parameters
    public JImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            // Not properly managed, sorry!
            e.printStackTrace();
        }
    }

    /* IMPORTANT: WE override this to scale the image in layouts that stretch it horizontally while respecting its preferred vertical size
    // THIS WILL NOT WORK IF YOU HAVE OTHER GOALS, DON'T REUSE WITHOUT THINKING
    @Override
    public Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();

        float width = image.getWidth();
        float height = image.getHeight();

        // Calculate the height needed to mantain aspect ratio
        preferred.height = Math.round(getWidth()*height/width);

        return preferred;
    }*/

    public void setOpacityValue(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    // Paint the image in the background, with the size the layout assigns to the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);

            g2d.dispose();
        }
    }
}