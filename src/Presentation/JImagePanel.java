package Presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** A custom JPanel that renders a background image scaled to the panel's size, with configurable opacity. */
public class JImagePanel extends JPanel {

    // The image to render
    private BufferedImage image;
    private float opacity = 1.0f;

    /**
     * Loads the image from the given file path. Silently sets image to null if the path
     * is null or the file cannot be read.
     * @param path the file path to the image, or null for no image
     */
    public JImagePanel(String path) {
        if (path != null) {
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                image = null;
                e.printStackTrace();
            }
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

    /**
     * Sets the opacity of the background image and triggers a repaint.
     * @param opacity a value between 0.0 (transparent) and 1.0 (opaque)
     */
    public void setOpacityValue(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    /** Draws the background image scaled to fill the panel's current bounds, applying the set opacity. */
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

    /**
     * Replaces the current image by loading a new one from the given file path.
     * @param imagePath the file path to the new image
     */
    public void setImage(String imagePath) {
        if (imagePath != null) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                image = null;
                e.printStackTrace();
            }
        }
    }
}