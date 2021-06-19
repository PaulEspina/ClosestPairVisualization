import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ButtonsPanel extends JPanel implements ActionListener
{
    private HashMap<String, Boolean> buttonsPressed;

    private final JButton restartButton;
    private final JButton playButton;
    private final JButton forwardButton;
    private final JButton skipButton;

    public ButtonsPanel()
    {
        setBorder(BorderFactory.createLineBorder(new Color(5, 3, 35)));
        setBackground(new Color(30, 10, 75));
        restartButton = new Button("<html><p>&#9198</p></html>");
        restartButton.addActionListener(this);
        playButton = new Button("<html><p>&#9654</p></html>");
        playButton.addActionListener(this);
        forwardButton = new Button("<html><p>&#10145</p></html>");
        forwardButton.addActionListener(this);
        skipButton = new Button("<html><p>&#9197</p></html>");
        skipButton.addActionListener(this);
        add(restartButton);
        add(playButton);
        add(forwardButton);
        add(skipButton);

        // INIT
        buttonsPressed = new HashMap<>();
        buttonsPressed.put("Restart", false);
        buttonsPressed.put("Play", false);
        buttonsPressed.put("Forward", false);
        buttonsPressed.put("Skip", false);
    }

    public void resetButtons()
    {
        buttonsPressed.replace("Restart", false);
        buttonsPressed.replace("Play", false);
        buttonsPressed.replace("Forward", false);
        buttonsPressed.replace("Skip", false);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == restartButton)
        {
            buttonsPressed.replace("Restart", true);
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

    public boolean isPressed(String key)
    {
        return buttonsPressed.get(key);
    }

    public boolean isPressed()
    {
        return buttonsPressed.containsValue(true);
    }
}
