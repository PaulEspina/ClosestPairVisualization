import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm
{
    public static double solve(Point[] p, Point[] q,  ArrayList<StepData> stepData)
    {
        if(p.length <= 3)
        {
            return bruteForce(p);
        }

        int middle = p.length / 2;
        Point middlePoint = p[middle];
        Point[] leftPoints = Arrays.copyOfRange(p, 0, p.length / 2);
        Point[] rightPoints = Arrays.copyOfRange(p, p.length / 2, p.length);

        double dl = solve(leftPoints, q, stepData);
        double dr = solve(rightPoints, q, stepData);
        double d = Math.min(dl, dr);

        ArrayList<Point> stripP = new ArrayList<>();
        ArrayList<Point> stripQ = new ArrayList<>();
        for(int i = 0; i < p.length; i++)
        {
            if(Math.abs(p[i].x - middlePoint.x) < d)
            {
                stripP.add(p[i]);
            }
            if(Math.abs(q[i].x - middlePoint.x) < d)
            {
                stripQ.add(q[i]);
            }
        }

        Point[] staticStripP = new Point[stripP.size()];
        for(int i = 0; i < stripP.size(); i++)
        {
            staticStripP[i] = stripP.get(i);
        }
        Point[] staticStripQ = new Point[stripQ.size()];
        for(int i = 0; i < stripQ.size(); i++)
        {
            staticStripQ[i] = stripQ.get(i);
        }
        QuickSort.sort(staticStripQ, 1);

        double minA = Math.min(d, stripClosest(staticStripP, d));
        double minB = Math.min(d, stripClosest(staticStripQ, d));
        double min = Math.min(minA, minB);

        stepData.add(new StepData(middle, middlePoint, leftPoints, rightPoints, dl, dr, d, staticStripP, staticStripQ, minA, minB, min));

        return min;
    }

    private static double stripClosest(Point[] strip, double d)
    {
        double minVal = d;
        for(int i = 0; i < strip.length; i++)
        {
            for(int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < minVal; j++)
            {
                minVal = dist(strip[i], strip[j]);
            }
        }
        return minVal;
    }

    private static double bruteForce(Point[] points)
    {
        double minDistance = Double.MAX_VALUE;
        for(int i = 0; i < points.length; i++)
        {
            for(int j = i + 1; j < points.length; j++)
            {
                if(dist(points[i], points[j]) < minDistance)
                {
                    minDistance = dist(points[i], points[j]);
                }
            }
        }
        return minDistance;
    }

    private static double dist(Point p1, Point p2)
    {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }
}
