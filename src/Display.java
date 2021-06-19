import javax.swing.*;
import java.awt.*;

public class Display
{
    private final int WIDTH = 500, HEIGHT = 500;

    private final JFrame frame;
    private final Canvas canvas;
    private final CodeSim codeSim;
    private final JPanel buttonPanel;
    private final JButton restartButton;
    private final JButton playButton;
    private final JButton backButton;
    private final JButton forwardButton;
    private final JButton skipButton;

    public Display()
    {
        // FRAME START
        frame = new JFrame("Closest Pair Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Canvas
        canvas = new Canvas();
        canvas.setBackground(new Color(175, 175, 175));
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(false);

        // Code Sim Panel
        codeSim = new CodeSim("Code");
        codeSim.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        codeSim.setForeground(Color.WHITE);
        codeSim.setBackground(new Color(40, 40, 40));
        codeSim.init();
        codeSim.setPreferredSize(new Dimension((int) (WIDTH * 1.25), HEIGHT));

        // Buttons Panel
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(5, 3, 35)));
        buttonPanel.setBackground(new Color(30, 10, 75));
        restartButton = new Button("<html><p>&#9198</p></html>");
        backButton = new Button("<");
        playButton = new Button("<html><p>&#9654</p></html>");
        forwardButton = new Button(">");
        skipButton = new Button("<html><p>&#9197</p></html>");
        buttonPanel.add(restartButton);
        buttonPanel.add(backButton);
        buttonPanel.add(playButton);
        buttonPanel.add(forwardButton);
        buttonPanel.add(skipButton);

        frame.add(canvas, BorderLayout.CENTER);
        frame.add(codeSim, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        // END FRAME
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
