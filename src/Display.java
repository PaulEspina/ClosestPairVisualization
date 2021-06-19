import javax.swing.*;
import java.awt.*;

public class Display
{
    private final int WIDTH = 500, HEIGHT = 500;

    private final JFrame frame;
    private final Canvas canvas;
    private final JPanel buttonPanel;
    private final JButton restartButton;
    private final JButton playButton;
    private final JButton stepBackButton;
    private final JButton stepForwardButton;
    private final JButton skipButton;

    public Display()
    {
        // FRAME SETTINGS
        frame = new JFrame("Closest Pair Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // CANVAS
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(false);

        // BUTTON PANEL
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 10, 75));
        restartButton = new Button("<html><p>&#9198</p></html>");
        stepBackButton = new Button("<");
        playButton = new Button("<html><p>&#9654</p></html>");
        stepForwardButton = new Button(">");
        skipButton = new Button("<html><p>&#9197</p></html>");
        buttonPanel.add(restartButton);
        buttonPanel.add(stepBackButton);
        buttonPanel.add(playButton);
        buttonPanel.add(stepForwardButton);
        buttonPanel.add(skipButton);



        frame.add(canvas, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
