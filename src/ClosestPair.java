import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;

public class ClosestPair implements Runnable
{
    private final String TITLE;
    private final int WIDTH, HEIGHT;

    private Boolean running;
    private GraphicDisplay graphicDisplay;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private Thread thread;

    private final int numOfPoints;
    private final Point[] points;
    ArrayList<Point[]> subPoints;

    public ClosestPair(Point[] points, ArrayList<Point[]> subPoints)
    {
        numOfPoints = points.length;
        this.points = points;
        this.subPoints = subPoints;
        TITLE = "Closest Pair Visualization";
        WIDTH = 600;
        HEIGHT = 500;
        running = false;
        graphicDisplay = null;
        bufferStrategy = null;
        graphics = null;
        thread = null;
    }

    private void init()
    {
        graphicDisplay = new GraphicDisplay(TITLE, WIDTH, HEIGHT);



    }

    private void update()
    {

    }

    private void render()
    {
        bufferStrategy = graphicDisplay.getCanvas().getBufferStrategy();
        if(bufferStrategy == null)
        {
            graphicDisplay.getCanvas().createBufferStrategy(3);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, WIDTH, HEIGHT);

        // START DRAW

        graphics.setColor(Color.RED);

        for(int i = 0; i < numOfPoints; i++)
        {
            graphics.fillOval(points[i].x, points[i].y, 10, 10);
        }

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
