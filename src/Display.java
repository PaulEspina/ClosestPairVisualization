import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Display implements ActionListener
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

    private HashMap<String, Boolean> buttonsPressed;

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
        restartButton.addActionListener(this);
        backButton = new Button("<");
        backButton.addActionListener(this);
        playButton = new Button("<html><p>&#9654</p></html>");
        playButton.addActionListener(this);
        forwardButton = new Button(">");
        forwardButton.addActionListener(this);
        skipButton = new Button("<html><p>&#9197</p></html>");
        skipButton.addActionListener(this);
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

        // INIT
        buttonsPressed = new HashMap<>();
        buttonsPressed.put("Restart", false);
        buttonsPressed.put("Back", false);
        buttonsPressed.put("Play", false);
        buttonsPressed.put("Forward", false);
        buttonsPressed.put("Skip", false);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == restartButton)
        {
            buttonsPressed.replace("Restart", true);
        }
        if(e.getSource() == backButton)
        {
            buttonsPressed.replace("Back", true);
        }
        if(e.getSource() == playButton)
        {
            buttonsPressed.replace("Play", true);
        }
        if(e.getSource() == forwardButton)
        {
            buttonsPressed.replace("Forward", true);
        }
        if(e.getSource() == skipButton)
        {
            buttonsPressed.replace("Skip", true);
        }
    }

    public void resetButtons()
    {
        buttonsPressed.replace("Restart", false);
        buttonsPressed.replace("Back", false);
        buttonsPressed.replace("Play", false);
        buttonsPressed.replace("Forward", false);
        buttonsPressed.replace("Skip", false);
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public boolean isPressed(String key)
    {
        return buttonsPressed.get(key);
    }
}
