import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ButtonsPanel extends JPanel implements ActionListener
{
    private HashMap<String, Boolean> buttonsPressed;

    private final JButton newButton;
    private final JButton restartButton;
    private final JButton playButton;
    private final JButton forwardButton;
    private final JButton skipButton;

    public ButtonsPanel()
    {
        setBorder(BorderFactory.createLineBorder(new Color(5, 3, 35)));
        setBackground(new Color(30, 10, 75));

        newButton = new Button("NEW");
        newButton.addActionListener(this);
        restartButton = new Button("RESTART");
        restartButton.addActionListener(this);
        playButton = new Button("PLAY");
        playButton.addActionListener(this);
        forwardButton = new Button("STEP");
        forwardButton.addActionListener(this);
        skipButton = new Button("SKIP");
        skipButton.addActionListener(this);

        add(newButton);
        add(restartButton);
        add(playButton);
        add(forwardButton);
        add(skipButton);

        // INIT
        buttonsPressed = new HashMap<>();
        buttonsPressed.put("New", false);
        buttonsPressed.put("Restart", false);
        buttonsPressed.put("Play", false);
        buttonsPressed.put("Forward", false);
        buttonsPressed.put("Skip", false);
    }

    public void resetButtons()
    {
        buttonsPressed.replace("New", false);
        buttonsPressed.replace("Restart", false);
        buttonsPressed.replace("Play", false);
        buttonsPressed.replace("Forward", false);
        buttonsPressed.replace("Skip", false);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == newButton)
        {
            buttonsPressed.replace("New", true);
        }
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

    public JButton getNewButton()
    {
        return newButton;
    }

    public JButton getRestartButton()
    {
        return restartButton;
    }

    public JButton getPlayButton()
    {
        return playButton;
    }

    public JButton getForwardButton()
    {
        return forwardButton;
    }

    public JButton getSkipButton()
    {
        return skipButton;
    }
}
