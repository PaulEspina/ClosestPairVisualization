import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
//        Point[] points = new Point[]
//                {
//                        new Point(25, 250),
//                        new Point(500, 400),
//                        new Point(450, 100),
//                        new Point(482, 10),
//                };

        Random random = new Random();
        Point[] points = new Point[100];
        for(int i = 0; i < points.length; i++)
        {
            points[i] = new Point(random.nextInt(500), random.nextInt(500));
        }

        ArrayList<StepData> stepData = new ArrayList<>();

        Point[] pointsY = points.clone();
        QuickSort.sort(points, 0);
        QuickSort.sort(pointsY, 1);
        double answer = Algorithm.solve(points, pointsY, stepData);
        System.out.println("Closest Distance: " + answer);

        ClosestPair closestPair = new ClosestPair(points, stepData, answer);
        closestPair.start();
    }
}
