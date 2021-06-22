import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;

public class ClosestPair implements Runnable
{
    private Boolean running;
    private Display display;
    private ButtonsPanel buttons;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private Thread thread;
    private int width, height;

    private Point[] points;
    private ArrayList<StepData> stepData;
    private Stack<int[]> pointersStack;
    private final int[][] processes;
    private int dataIndex;
    private int stepIndex;
    private int processIndex;
    private boolean play;
    private double answer;
    private double closest;

    public ClosestPair()
    {
        running = false;
        display = null;
        buttons = null;
        bufferStrategy = null;
        graphics = null;
        thread = null;
        closest = 0;
        pointersStack = new Stack<>();
        stepIndex = 0;
        processIndex = 0;
        processes = new int[][]
                {
                        {2, 3, 4, 5}, // bruteforce 0
                        {7, 8}, // get mid point 1
                        {9, 10}, // divide array 2
                        {12}, // compute dl 3
                        {13}, // compute dr 4
                        {14}, // compute d 5
                        {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29}, // strip array 6
                        {31}, // strip closest p 7
                        {32}, // strip closest q 8
                        {33}, // compute minA 9
                        {35} // compute minB 10
                };
        play = false;
    }

    private void createPoints()
    {
        pointersStack = new Stack<>();
        stepData = new ArrayList<>();

        // Creates a random set of points
        Random random = new Random();
        points = new Point[20];
        for(int i = 0; i < points.length; i++)
        {
            Integer x = null, y = null;
            boolean flag = true;
            while(flag)
            {
                flag = false;
                x = random.nextInt(500);
                y = random.nextInt(500);
                for(int j = 0; j < i; j++)
                {
                    Point p = points[j];
                    if(x == p.x && y == p.y)
                    {
                        flag = true;
                        break;
                    }
                }
            }
            points[i] = new Point(x, y);
        }

        QuickSort.sort(points, 0);
        Point[] pointsY = points.clone();
        QuickSort.sort(pointsY, 1);
        answer = Algorithm.solve(points, pointsY, stepData);
        Collections.reverse(stepData);

        System.out.println(Arrays.toString(points));
        System.out.println(answer);

        // Initializes values to be used in simulation
        closest = Double.MAX_VALUE;
        dataIndex = 0;
        stepIndex = 0;
        processIndex = 0;
        play = false;
        buttons.getPlayButton().setText("PLAY");
        buttons.getRestartButton().setEnabled(false);
        buttons.getPlayButton().setEnabled(true);
        buttons.getForwardButton().setEnabled(true);
        buttons.getSkipButton().setEnabled(true);
    }

    private void init()
    {
        display = new Display();
        width = display.getFrame().getWidth();
        height = display.getFrame().getHeight();
        display.getCodeSim().setLine(processes[processIndex]);

        buttons = display.getButtonsPanel();

        createPoints();
    }

    private void update()
    {
        CodeSim codeSim = display.getCodeSim();
        JScrollBar scrollBar = codeSim.getScrollPane().getVerticalScrollBar();

        if(pointersStack.empty() && processIndex >= processes.length - 1 && dataIndex >= stepData.size() - 1) // this will be true when the simulation reaches the end
        {
            play = false;
            buttons.getPlayButton().setText("PLAY");
            buttons.getNewButton().setEnabled(true);
            buttons.getPlayButton().setEnabled(false);
            buttons.getRestartButton().setEnabled(true);
            buttons.getForwardButton().setEnabled(false);
            buttons.getSkipButton().setEnabled(false);
        }
        if(buttons.isPressed("Restart") && !play) // this will restart the simulation
        {
            while(!pointersStack.empty())
            {
                pointersStack.pop();
            }
            dataIndex = 0;
            stepIndex = 0;
            processIndex = 0;
            closest = Double.MAX_VALUE;
            buttons.getPlayButton().setEnabled(true);
            buttons.getPlayButton().setEnabled(true);
            buttons.getRestartButton().setEnabled(false);
            buttons.getForwardButton().setEnabled(true);
            buttons.getSkipButton().setEnabled(true);
        }
        if(buttons.isPressed("Play")) // this will start the simulation
        {
            play = !play;
            buttons.getPlayButton().setText(play ? "PAUSE" : "PLAY");
            buttons.getRestartButton().setEnabled(!play);
            buttons.getForwardButton().setEnabled(!play);
            buttons.getSkipButton().setEnabled(!play);
            buttons.getNewButton().setEnabled(!play);
        }
        if((buttons.isPressed("Forward") && stepIndex < stepData.size()) || play) // will step the simulation forward as long as it is possible to step forward
        {

            if(!pointersStack.empty() && (processIndex >= processes.length - 1 || stepData.get(stepIndex).p.length <= 3)) // if the length of the points array is less than 3, go back to previous step (pop the stack)
            {
                int[] indices = pointersStack.pop();
                stepIndex = indices[0];
                processIndex = indices[1];
            }
            else if(processIndex < processes.length - 1)
            {
                if(processIndex == 3 || processIndex == 4) // if a recursive call is reached, push the step to the stack
                {
                    pointersStack.push(new int[]{stepIndex, processIndex + 1});
                    stepIndex = ++dataIndex;
                    processIndex = 0;
                }
                else
                {
                    processIndex++;
                }
            }
        }
        if(buttons.isPressed("Skip") && !play) // skips to end of simulation
        {
            while(!pointersStack.empty())
            {
                pointersStack.pop();
            }
            processIndex = processes.length - 1;
            stepIndex = 0;
            dataIndex = stepData.size() - 1;
            buttons.getNewButton().setEnabled(true);
            buttons.getPlayButton().setEnabled(false);
            buttons.getRestartButton().setEnabled(true);
            buttons.getForwardButton().setEnabled(false);
            buttons.getSkipButton().setEnabled(false);
        }

        // Sets scrollbar to follow process index
        if(buttons.isPressed() || play)
        {
            if(processIndex == 0 || processIndex == 5  || processIndex == 3 || processIndex == 4)
            {
                scrollBar.setValue(0);
            }
            else if(processIndex == processes.length || processIndex == 6 || processIndex == 10)
            {
                scrollBar.setValue(scrollBar.getMaximum());
            }
        }
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
        graphics.setFont(new Font("Consolas", Font.PLAIN, 10));

        // START DRAW

        // Draw Code Sim Line Highlights
        if(buttons.isPressed() || play)
        {
            display.getCodeSim().resetLines();
            display.getCodeSim().setLine(processes[processIndex]); // highlights the lines
        }

        // Draw Points
        for(Point point : points)
        {
            graphics.fillRect(point.x - 1, point.y - 1, 2, 2);
        }

        // Draw Previous Array Bounds
        if(!pointersStack.empty())
        {
            StepData data = stepData.get(pointersStack.peek()[0]);
            Point lowPoint = data.leftPoints[0];
            Point highPoint = data.rightPoints[data.rightPoints.length - 1];
            graphics.setColor(Color.MAGENTA);
            graphics.drawLine(lowPoint.x, 0, lowPoint.x, height);
            graphics.drawLine(highPoint.x, 0, highPoint.x, height);
        }

        StepData data = stepData.get(stepIndex);
        // Draw Step's Middle Point
        if(data.middlePoint != null)
        {
            Point middlePoint = data.middlePoint;
            Point lowPoint = data.leftPoints[0];
            Point highPoint = data.rightPoints[data.rightPoints.length - 1];
            switch(processIndex)
            {
                case 11:
                case 10:
                    graphics.setColor(Color.ORANGE);
                    graphics.drawString("Min: " + Math.round(data.min), middlePoint.x, 100);
                    if(data.min < closest)
                    {
                        closest = data.min;
                    }
                case 9:
                    graphics.setColor(Color.YELLOW);
                    if(data.stripQ != null && data.stripQ.length != 0)
                    {
                        graphics.drawString("Min B: " + Math.round(data.minB), data.stripQ[data.stripQ.length / 2].x, data.stripQ[data.stripQ.length / 2].y);
                    }
                case 8:
                    graphics.setColor(Color.GREEN);
                    if(data.stripP != null && data.stripP.length != 0)
                    {
                        graphics.drawString("Min A: " + Math.round(data.minA), data.stripP[data.stripP.length / 2].x, data.stripP[data.stripP.length / 2].y);
                    }
                case 7:
                    graphics.setColor(Color.GREEN);
                    for(Point p : data.stripP)
                    {
                        graphics.fillRect(p.x - 1, p.y - 1, 2, 2);

                    }
                    graphics.setColor(Color.YELLOW);
                    for(Point q : data.stripQ)
                    {
                        graphics.fillRect(q.x - 1, q.y - 1, 2, 2);
                    }
                case 6:
                    graphics.setColor(Color.BLACK);
                    graphics.drawString("Min: " + Math.round(data.d), middlePoint.x, height / 2);
                case 5:
                    graphics.setColor(Color.RED);
                    graphics.drawString("Left Min: " + Math.round(data.dl), lowPoint.x, height / 3);
                    graphics.setColor(Color.BLUE);
                    graphics.drawString("Right Min: " + Math.round(data.dr), middlePoint.x, (int) (height / 1.5));
                case 3:
                    graphics.setColor(Color.RED);
                    graphics.drawLine(lowPoint.x, 0, lowPoint.x, height);
                    graphics.drawLine(lowPoint.x, height / 2, middlePoint.x, height /2);
                    graphics.setColor(Color.BLUE);
                    graphics.drawLine(highPoint.x, 0, highPoint.x, height);
                    graphics.drawLine(highPoint.x, height / 2, middlePoint.x, height /2);
                case 2:
                    graphics.setColor(Color.BLACK);
                    graphics.drawLine(middlePoint.x, 0, middlePoint.x, height);
            }
        }

        graphics.setFont(new Font("Consolas", Font.BOLD, 20));
        if(pointersStack.empty() && dataIndex >= stepData.size() - 1 && processIndex >= processes.length - 1)
        {
            // Print Final Answer
            graphics.setColor(Color.BLACK);
            graphics.drawString("Answer:" + answer, 10, 20);
        }
        else if(closest != Double.MAX_VALUE)
        {
            // Print Current Closest Distance
            graphics.setColor(Color.BLACK);
            graphics.drawString("Closest So Far:" + closest, 10, 20);
        }

        // END DRAW

        bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void run()
    {
        init();

        // this block of code set's up the rendering timing so that it is consistent
        int fps = 5; // the simulation will run at 5 frames per second
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
                if(buttons.isPressed("New"))
                {
                    createPoints();
                }
                else
                {
                    update();
                    render();
                }
                buttons.resetButtons();
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
