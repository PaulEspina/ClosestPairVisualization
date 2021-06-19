import javax.swing.*;
import java.awt.*;

public class Display
{
    private final int WIDTH = 500, HEIGHT = 500;

    private final JFrame frame;
    private final Canvas canvas;
    private final CodeSim codeSim;
    private final ButtonsPanel buttonsPanel;

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
        buttonsPanel = new ButtonsPanel();

        frame.add(canvas, BorderLayout.CENTER);
        frame.add(codeSim, BorderLayout.EAST);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
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

    public ButtonsPanel getButtonsPanel()
    {
        return buttonsPanel;
    }

    public CodeSim getCodeSim()
    {
        return codeSim;
    }
}
