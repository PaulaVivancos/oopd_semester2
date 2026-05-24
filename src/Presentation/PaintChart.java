package Presentation;

import Business.entities.CoffeeStats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * A zoomable, pannable chart panel that plots coffee count over time for a game session.
 */
public class PaintChart extends JPanel {
    private static final int PADDING = 60;

    private List<CoffeeStats> stats;
    private final JLabel noDataLabel;

    private double worldMinX, worldMaxX;
    private double worldMinY, worldMaxY;
    private double scaleX, scaleY;

    private double zoom = 1.0;
    private double offsetX = 0;
    private double offsetY = 0;

    private int lastDragX, lastDragY;

    private static final String NO_STATS = "No statistics have been recorded yet for this game.";

    /**
     * Constructs the chart panel and sets up mouse controls for zoom and pan.
     */
    public PaintChart() {
        this.setLayout(new BorderLayout());

        noDataLabel = new JLabel(NO_STATS, SwingConstants.CENTER);
        noDataLabel.setFont(new Font("Arial", Font.BOLD, 14));
        noDataLabel.setForeground(Color.GRAY);

        noDataLabel.setVisible(false);
        this.add(noDataLabel, BorderLayout.CENTER);


        setupMouseControls();
        setBackground(Color.WHITE);
        setOpaque(true);

    }

    /**
     * Sets the stats data to display and repaints the chart.
     * @param stats the list of {@link CoffeeStats} to plot
     */
    public void setStats(List<CoffeeStats> stats) {
        this.stats = stats;

        stats.sort((a, b) ->
                Double.compare(a.getTime(), b.getTime()));

        computeWorldBounds();

        repaint();

        if (this.stats == null || this.stats.isEmpty()) {
            // Show the label text, hide any lines drawn by paintComponent
            noDataLabel.setVisible(true);
        } else {
            // Hide the text label and allow normal chart rendering
            noDataLabel.setVisible(false);
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Computes the world-space min/max bounds from the current stats data.
     */
    private void computeWorldBounds() {
        if (stats == null || stats.isEmpty()) return;

        worldMinX = Double.POSITIVE_INFINITY;
        worldMaxX = Double.NEGATIVE_INFINITY;

        worldMinY = Double.POSITIVE_INFINITY;
        worldMaxY = Double.NEGATIVE_INFINITY;

        for (CoffeeStats s : stats) {
            double x = s.getTime();
            double y = s.getNumCoffees();

            worldMinX = Math.min(worldMinX, x);
            worldMaxX = Math.max(worldMaxX, x);

            worldMinY = Math.min(worldMinY, y);
            worldMaxY = Math.max(worldMaxY, y);
        }

        if (worldMinX == worldMaxX) {
            worldMaxX += 1;
        }

        if (worldMinY == worldMaxY) {
            worldMaxY += 1;
        }

    }

    /**
     * Registers mouse wheel and drag listeners for zoom and pan interactions.
     */
    private void setupMouseControls() {
        addMouseWheelListener(e -> {
            double delta = 0.1 * e.getPreciseWheelRotation();
            zoom = Math.max(0.1, zoom - delta);
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastDragX = e.getX();
                lastDragY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                offsetX += e.getX() - lastDragX;
                offsetY += e.getY() - lastDragY;
                lastDragX = e.getX();
                lastDragY = e.getY();
                repaint();
            }
        });
    }

    /**
     * Computes the pixel-per-unit scale factors based on the panel size and zoom level.
     */
    private void computeScale() {

        double drawableWidth = getWidth() - 2.0 * PADDING;
        double drawableHeight = getHeight() - 2.0 * PADDING;

        scaleX = drawableWidth / (worldMaxX - worldMinX);
        scaleY = drawableHeight / (worldMaxY - worldMinY);

        scaleX *= zoom;
        scaleY *= zoom;
    }

    /**
     * Draws the X and Y axes with tick marks and labels.
     * @param g2 the graphics context to draw on
     */
    private void drawAxes(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1.5f));

        int x0 = PADDING;
        int y0 = getHeight() - PADDING;

        // X axis
        g2.drawLine(x0, y0, getWidth() - PADDING, y0);

        // Y axis
        g2.drawLine(x0, PADDING, x0, y0);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // ---- X TICKS ----
        int numXTicks = 5;
        for (int i = 0; i <= numXTicks; i++) {
            double worldX = worldMinX + i * (worldMaxX - worldMinX) / numXTicks;
            int screenX = toScreenX(worldX);

            g2.drawLine(screenX, y0 - 5, screenX, y0 + 5);
            g2.drawString(String.format("%.1f", worldX), screenX - 10, y0 + 20);
        }

        // ---- Y TICKS ----
        int numYTicks = 5;
        for (int i = 0; i <= numYTicks; i++) {
            double worldY = worldMinY + i * (worldMaxY - worldMinY) / numYTicks;
            int screenY = toScreenY(worldY);

            g2.drawLine(x0 - 5, screenY, x0 + 5, screenY);
            g2.drawString(String.format("%.0f", worldY), x0 - 40, screenY + 5);
        }

        // Axis labels
        g2.drawString("Time (minutes)", getWidth() - PADDING - 60, y0 + 30);
        g2.drawString("Coffees", x0 - 50, PADDING-40);
    }

    /**
     * Draws lines connecting consecutive data points on the chart.
     * @param g2 the graphics context to draw on
     */
    private void drawLines(Graphics2D g2) {
        g2.setColor(new Color(255, 140, 0));
        g2.setStroke(new BasicStroke(2f));

        for (int i = 0; i < stats.size() - 1; i++) {
            CoffeeStats a = stats.get(i);
            CoffeeStats b = stats.get(i + 1);

            int x1 = toScreenX(a.getTime());
            int y1 = toScreenY(a.getNumCoffees());
            int x2 = toScreenX(b.getTime());
            int y2 = toScreenY(b.getNumCoffees());

            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * Draws a filled circle at each data point on the chart.
     * @param g2 the graphics context to draw on
     */
    private void drawPoints(Graphics2D g2) {
        for (CoffeeStats s : stats) {
            int x = toScreenX(s.getTime());
            int y = toScreenY(s.getNumCoffees());
            g2.fillOval(x - 6, y - 6, 12, 12);

        }


    }

    /**
     * Converts a world X coordinate to a screen X coordinate.
     * @param worldX the world-space X value
     * @return the corresponding screen X pixel
     */
    private int toScreenX(double worldX) {
        return (int) (PADDING + (worldX - worldMinX) * scaleX + offsetX);
    }

    /**
     * Converts a world Y coordinate to a screen Y coordinate.
     * @param worldY the world-space Y value
     * @return the corresponding screen Y pixel
     */
    private int toScreenY(double worldY) {
        return (int) (PADDING + (worldMaxY - worldY) * scaleY + offsetY);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If there's no data, stop here so axes/lines aren't drawn over the text
        if (stats == null || stats.isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        computeScale();
        drawAxes(g2);
        drawLines(g2);
        drawPoints(g2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 600);
    }


}
