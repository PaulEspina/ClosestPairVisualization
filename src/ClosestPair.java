import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;

public class ClosestPair implements Runnable
{
    private Boolean running;
    private Display display;
    private ButtonsPanel buttons;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private Thread thread;
    private int width, height;

    private final int numOfPoints;
    private final Point[] points;
    private final ArrayList<StepData> stepData;
    private int stepIndex;
    private int lineIndex;
    private boolean play;

    public ClosestPair(Point[] points, ArrayList<StepData> stepData)
    {
        running = false;
        display = null;
        buttons = null;
        bufferStrategy = null;
        graphics = null;
        thread = null;

        numOfPoints = points.length;
        this.points = points;
        this.stepData = stepData;
        Collections.reverse(this.stepData);
        stepIndex = 0;
        play = false;
    }

    private void init()
    {
        display = new Display();
        width = display.getFrame().getWidth();
        height = display.getFrame().getHeight();

        buttons = display.getButtonsPanel();

        stepIndex = 0;
        play = false;
    }

    private void update()
    {
        CodeSim codeSim = display.getCodeSim();
        JScrollBar scrollBar = codeSim.getScrollPane().getVerticalScrollBar();
        int lineCount = codeSim.getLineCount(0) - 1;
        if(buttons.isPressed("Restart") && !play)
        {
            lineIndex = 0;
            stepIndex = 0;
            scrollBar.setValue(0);
        }
        if(buttons.isPressed("FastBack") && !play && stepIndex > 0)
        {
            lineIndex = 0;
            stepIndex--;
            scrollBar.setValue(0);
        }
        if(buttons.isPressed("Back") && !play && lineIndex >= 0)
        {
            if(lineIndex == 0 && stepIndex > 0)
            {
                lineIndex = lineCount;
                stepIndex--;
                scrollBar.setValue(scrollBar.getMaximum());
            }
            else
            {
                lineIndex--;
            }
        }
        if(buttons.isPressed("Play"))
        {
            play = !play;
        }
        if(buttons.isPressed("Forward") && !play && lineIndex <= lineCount)
        {
            lineIndex++;
            if(lineIndex % (lineCount + 1) == 0 && stepIndex < stepData.size() - 1)
            {
                lineIndex = 0;
                stepIndex++;
                scrollBar.setValue(0);
            }
        }
        if(buttons.isPressed("FastForward") && !play && stepIndex < stepData.size() - 1)
        {
            lineIndex = 0;
            stepIndex++;
            scrollBar.setValue(0);
        }
        if(buttons.isPressed("Skip") && !play)
        {
            lineIndex = lineCount;
            stepIndex = stepData.size() - 1;
            scrollBar.setValue(0);
        }
        if(play && lineIndex <= lineCount)
        {
            lineIndex++;
            if(lineIndex % (lineCount + 1) == 0 && stepIndex < stepData.size() - 1)
            {
                lineIndex = 0;
                stepIndex++;
            }
        }
        codeSim.setLine(lineIndex);

        buttons.resetButtons();
    }

    private void render()
    {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null)
        {
            display.getCanvas().createBufferStrategy(2);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);

        // START DRAW

        // Draw Points
        for(int i = 0; i < numOfPoints; i++)
        {
            graphics.drawOval(points[i].x - 3, points[i].y - 3, 6, 6);
        }

        graphics.setColor(Color.RED);
        Point middlePoint = stepData.get(stepIndex).middlePoint;
        graphics.drawLine(middlePoint.x, 0, middlePoint.x, height);


        // END DRAW

        bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void run()
    {
        init();

        int fps = 60;
        double timePerTick = 1000000000 / (double) fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        while(running)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1)
            {
                update();
                render();
                delta--;
            }
            if(timer >= 1000000000)
            {
                timer = 0;
            }
        }
    }

    public synchronized void start()
    {
        if(running)
        {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop()
    {
        if(!running)
        {
            return;
        }
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
