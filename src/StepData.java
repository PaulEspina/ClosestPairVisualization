import java.awt.*;

public class StepData
{
    public Point[] p;
    public Point[] q;
    public int middle;
    public Point middlePoint;
    public Point[] leftPoints;
    public Point[] rightPoints;
    public double dl;
    public double dr;
    public double d;
    public Point[] stripP;
    public Point[] stripQ;
    public double minA;
    public double minB;
    public double min;

    public StepData(Point[] p, Point[] q, int middle, Point middlePoint, Point[] leftPoints, Point[] rightPoints, double dl, double dr, double d, Point[] stripP, Point[] stripQ, double minA, double minB, double min)
    {
        this.p = p;
        this.q = q;
        this.middle = middle;
        this.middlePoint = middlePoint;
        this.leftPoints = leftPoints;
        this.rightPoints = rightPoints;
        this.dl = dl;
        this.dr = dr;
        this.d = d;
        this.stripP = stripP;
        this. stripQ = stripQ;
        this.minA = minA;
        this.minB = minB;
        this.min = min;
    }
}
