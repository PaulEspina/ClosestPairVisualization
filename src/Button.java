import javax.swing.*;
import java.awt.*;

public class Button extends JButton
{
    public Button()
    {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 12));
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
    }

    public Button(String text)
    {
        this();
        setText(text);
    }
}
